//package com.chenmeng.project.config;
//
//import cn.hutool.core.util.StrUtil;
//import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
//
///**
// * 此插件影响性能，可使用SqlLogInterceptor替换此功能
// *
// * @author 沉梦听雨
// */
//public class P6SpyLogger implements MessageFormattingStrategy {
//
//    public P6SpyLogger() {
//    }
//
//    /**
//     * 重写日志格式方法
//     * <p>
//     * now:当前时间
//     * elapsed:执行耗时
//     * category：执行分组
//     * prepared：预编译sql语句
//     * sql:执行的真实SQL语句，已替换占位
//     * url:数据库连接信息
//     */
//    @Override
//    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
//
//        return StrUtil
//                .format("\n==============  Sql Start  ==============\nExecute ID  ：{}\nExecute SQL ：{}\nExecute Time：{} ms\n==============  Sql  End   ==============\n", connectionId, sql, elapsed);
//        // return StringUtils.isNotEmpty(sql) ? " Consume Time：" + elapsed + " ms " + now + "\n Execute SQL：" + sql.replaceAll("[\\s]+", " ") + "\n" : null;
//    }
//}
