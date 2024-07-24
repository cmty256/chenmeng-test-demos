package com.chenmeng.common.constants.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.chenmeng.common.constants.Constants.NULL;

/**
 * 性别枚举
 *
 * @author chenmeng
 */
@Getter
public enum SexEnum {

    /**
     * 男
     */
    MAN(0, "男"),

    /**
     * 女
     */
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
     * @param key key
     * @return value
     */
    public static String getValueByKey(Integer key) {
        for (SexEnum value : values()) {
            if (value.getKey().equals(key)) {
                return value.value;
            }
        }
        return NULL;
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
}
