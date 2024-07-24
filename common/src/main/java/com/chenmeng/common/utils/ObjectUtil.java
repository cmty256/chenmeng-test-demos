package com.chenmeng.common.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

import static com.chenmeng.common.constants.Constants.INVALID_USER_NAME;
import static com.chenmeng.common.constants.NumberConstant.MINUS_ONE;

/**
 * 对象工具类
 *
 * @author chenmeng
 */
@Slf4j
public class ObjectUtil {

    /**
     * 将对象中的所有 null 值替换为指定的默认值（对接APP时可能会用到）
     */
    public static void replaceNullValues(Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            // 允许访问私有字段
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value == null) {
                    if (field.getType().equals(String.class)) {
                        // 替换为指定的默认字符串
                        field.set(object, "");
                    } else if (field.getType().equals(Integer.class)) {
                        // 替换为指定的默认整数值(-1)
                        field.set(object, Integer.parseInt(String.valueOf(MINUS_ONE)));
                    }else if (field.getType().equals(Long.class)) {
                        // 替换为指定的默认整数值(-1)
                        field.set(object, Long.parseLong(String.valueOf(MINUS_ONE)));
                    }
                }
            } catch (IllegalAccessException | NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 为对象设置创建人和更新人的字段值（对接APP时可能会用到）
     *
     * @param obj 对象
     * @param setCreateUser 是否设置创建人字段
     * @param setUpdateUser 是否设置更新人字段
     */
    public static void setUserNameInObj(Object obj, boolean setCreateUser, boolean setUpdateUser) {
        // 这里用了一个固定的用户名字段名，需要根据实际情况修改
        String userName = "userName";
        if (StrUtil.isBlank(userName)) {
            throw new RuntimeException(INVALID_USER_NAME);
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            // 设置创建用户的字段
            if ("createUser".equals(field.getName()) && setCreateUser) {
                try {
                    field.set(obj, userName);
                } catch (IllegalAccessException e) {
                    log.error("Failed to set 'createUser' field", e);
                }
            }

            // 设置更新用户的字段
            if ("updateUser".equals(field.getName()) && setUpdateUser) {
                try {
                    field.set(obj, userName);
                } catch (IllegalAccessException e) {
                    log.error("Failed to set 'updateUser' field", e);
                }
            }
        }
    }
}
