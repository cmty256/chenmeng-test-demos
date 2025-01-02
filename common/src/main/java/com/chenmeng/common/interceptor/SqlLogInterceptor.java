package com.chenmeng.common.interceptor;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * SQL日志拦截器，用于记录执行的SQL语句和执行时间。
 *
 * @author chenmeng
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
public class SqlLogInterceptor implements Interceptor {
    private static final Logger log = LoggerFactory.getLogger(SqlLogInterceptor.class);

    // 定义支持的数据库驱动类名
    private static final String DRUID_POOLED_PREPARED_STATEMENT = "com.alibaba.druid.pool.DruidPooledPreparedStatement";
    private static final String T4C_PREPARED_STATEMENT = "oracle.jdbc.driver.T4CPreparedStatement";
    private static final String DM_PREPARED_STATEMENT = "DmdbPreparedStatement";
    private static final String ORACLE_PREPARED_STATEMENT_WRAPPER = "oracle.jdbc.driver.OraclePreparedStatementWrapper";

    // 用于存储反射方法
    private Method oracleGetOriginalSqlMethod;
    private Method druidGetSqlMethod;

    /**
     * 拦截方法，执行 SQL 日志记录。
     *
     * @param invocation 当前的拦截器调用
     * @return 执行结果
     * @throws Throwable 可能抛出的异常
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 提取Statement对象
        Statement statement = extractStatement(invocation.getArgs()[0]);
        // 获取SQL语句
        String originalSql = getOriginalSql(statement);

        // 如果SQL为空，则使用statement的toString()
        if (originalSql == null) {
            originalSql = statement.toString();
        }

        // 格式化SQL，去掉多余的空格
        originalSql = originalSql.replaceAll("\\s+", " ");
        // 查找SQL开始的位置，去掉前面的无关部分
        int sqlStartIndex = indexOfSqlStart(originalSql);
        if (sqlStartIndex > 0) {
            originalSql = originalSql.substring(sqlStartIndex);
        }

        // 记录SQL执行开始时间
        long startNs = System.nanoTime();
        // 执行目标方法
        Object result = invocation.proceed();
        // 记录SQL执行耗时
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        // 记录SQL日志
        logSql(originalSql, tookMs, invocation);

        return result;
    }

    /**
     * 从代理对象中提取Statement对象。
     *
     * @param firstArg 方法的第一个参数
     * @return Statement对象
     */
    private Statement extractStatement(Object firstArg) {
        // 如果参数是代理对象，则获取代理对象的实际Statement
        if (Proxy.isProxyClass(firstArg.getClass())) {
            return (Statement) SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            return (Statement) firstArg;
        }
    }

    /**
     * 获取SQL语句，根据不同类型的Statement提取SQL。
     *
     * @param statement 当前的Statement对象
     * @return 提取到的SQL语句
     */
    private String getOriginalSql(Statement statement) {
        String stmtClassName = statement.getClass().getName();
        // 判断具体的PreparedStatement类型，并调用相应的方法获取SQL
        if (DRUID_POOLED_PREPARED_STATEMENT.equals(stmtClassName)) {
            return getDruidSql(statement);
        } else if (T4C_PREPARED_STATEMENT.equals(stmtClassName) || ORACLE_PREPARED_STATEMENT_WRAPPER.equals(stmtClassName)) {
            return getOracleSql(statement);
        } else if (stmtClassName.contains(DM_PREPARED_STATEMENT)) {
            return getDmSql(statement);
        }
        return null;
    }

    /**
     * 获取Druid数据库的SQL语句。
     *
     * @param statement Druid数据库的Statement
     * @return Druid的SQL语句
     */
    private String getDruidSql(Statement statement) {
        try {
            if (druidGetSqlMethod == null) {
                // 通过反射获取DruidPreparedStatement的getSql方法
                Class<?> clazz = Class.forName(DRUID_POOLED_PREPARED_STATEMENT);
                druidGetSqlMethod = clazz.getMethod("getSql");
            }
            return (String) druidGetSqlMethod.invoke(statement);
        } catch (Exception e) {
            log.error("Error extracting SQL from Druid statement", e);
        }
        return null;
    }

    /**
     * 获取Oracle数据库的SQL语句。
     *
     * @param statement Oracle数据库的Statement
     * @return Oracle的SQL语句
     */
    private String getOracleSql(Statement statement) {
        try {
            if (oracleGetOriginalSqlMethod == null) {
                // 通过反射获取OraclePreparedStatementWrapper的getOriginalSql方法
                Class<?> clazz = Class.forName(ORACLE_PREPARED_STATEMENT_WRAPPER);
                oracleGetOriginalSqlMethod = getMethodRegular(clazz, "getOriginalSql");
            }
            assert oracleGetOriginalSqlMethod != null;
            return (String) oracleGetOriginalSqlMethod.invoke(statement);
        } catch (Exception e) {
            log.error("Error extracting SQL from Oracle statement", e);
        }
        return null;
    }

    /**
     * 获取DM数据库的SQL语句。
     *
     * @param statement DM数据库的Statement
     * @return DM的SQL语句
     */
    private String getDmSql(Statement statement) {
        try {
            Class<?> clazz = statement.getClass();
            // 获取字段originalSql
            Field originalSqlField = clazz.getSuperclass().getDeclaredField("originalSql");
            originalSqlField.setAccessible(true);
            String sql = (String) originalSqlField.get(statement);

            // 获取字段params
            Field paramsField = clazz.getDeclaredField("params");
            paramsField.setAccessible(true);
            Object params = paramsField.get(statement);

            if (sql != null) {
                // 返回SQL及其参数
                return sql + "\t  params: " + (params == null ? "[]" : params.toString());
            }
        } catch (Exception e) {
            log.error("Error extracting SQL from DM statement", e);
        }
        return null;
    }

    /**
     * 记录SQL日志，输出SQL执行的相关信息。
     *
     * @param originalSql 执行的SQL语句
     * @param tookMs      执行时间（毫秒）
     * @param invocation  当前的拦截器调用
     */
    private void logSql(String originalSql, long tookMs, Invocation invocation) {
        try {
            MetaObject metaObject = SystemMetaObject.forObject(PluginUtils.realTarget(invocation.getTarget()));
            MappedStatement ms = (MappedStatement) metaObject.getValue("delegate.mappedStatement");

            // 使用System.err打印SQL执行信息
            System.err.println(StrUtil.format("\n==============  SQL Start  ==============\n" +
                            "Execute ID  ：{}\n" +
                            "Execute SQL ：{}\n" +
                            "Execute Time：{} ms\n" +
                            "==============  SQL End   ==============\n",
                    ms.getId(), originalSql, tookMs));
        } catch (Exception e) {
            log.error("Error logging SQL", e);
        }
    }

    /**
     * 插件包装，确保拦截的目标是StatementHandler类型。
     *
     * @param target 目标对象
     * @return 包装后的对象
     */
    @Override
    public Object plugin(Object target) {
        return target instanceof StatementHandler ? Plugin.wrap(target, this) : target;
    }

    /**
     * 递归查找指定方法，支持父类查找。
     *
     * @param clazz      类
     * @param methodName 方法名
     * @return 找到的方法
     */
    private Method getMethodRegular(Class<?> clazz, String methodName) {
        if (Object.class.equals(clazz)) {
            return null;
        }

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }

        return getMethodRegular(clazz.getSuperclass(), methodName);
    }

    /**
     * 查找SQL语句的开始位置。
     *
     * @param sql SQL语句
     * @return SQL语句的开始位置
     */
    private int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> indexes = new HashSet<>();
        indexes.add(upperCaseSql.indexOf("SELECT "));
        indexes.add(upperCaseSql.indexOf("UPDATE "));
        indexes.add(upperCaseSql.indexOf("INSERT "));
        indexes.add(upperCaseSql.indexOf("DELETE "));

        // 移除无效的索引
        indexes.remove(-1);
        if (CollectionUtils.isEmpty(indexes)) {
            return -1;
        }

        List<Integer> indexList = new ArrayList<>(indexes);
        indexList.sort(Comparator.naturalOrder());
        return indexList.get(0);
    }
}
