package com.chenmeng.common.constants.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 性别枚举
 *
 * @author 沉梦听雨
 */
public enum SexEnum {

    /**
     * 性别枚举
     */
    MAN(0, "男"),
    WOMAN(1, "女"),
    ;

    private final Integer key;

    private final String value;

    SexEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 通过key获取value，默认字符串"null"
     *
     * @param key
     * @return
     */
    public static String getValueByKey(Integer key) {
        for (SexEnum value : values()) {
            if (value.getKey().equals(key)) {
                return value.value;
            }
        }
        return "null";
    }

    /**
     * 获取值列表
     *
     * @return List<String>
     */
    public static List<String> getValues() {
        return Arrays.stream(values())
                .map(item -> item.value)
                .collect(Collectors.toList());
    }

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
