package com.chenmeng.common.constants;

/**
 * 通用常量
 *
 * @author chenmeng
 */
public interface Constants {

    /**
     * 成功状态码
     */
    Integer SUCCESS_CODE = 0;

    /**
     * 默认失败状态码
     */
    Integer FAIL_CODE = -1;

    /**
     * 请求id
     */
    String REQ_ID = "reqId";

    /**
     * null字符串
     */
    String NULL = "null";

    /**
     * 分隔符
     */
    String SEPARATOR = "-";

    /**
     * 连字符
     */
    String HYPHEN = "-";

    /**
     * 逗号
     */
    String COMMA = ",";

    /**
     * 掩码字符（星号）
     */
    char MASK_CHAR = '*';

    String EMAIL_AT_SYMBOL = "@";

    /**
     * 用户名无效
     */
    String INVALID_USER_NAME = "用户名无效";
}
