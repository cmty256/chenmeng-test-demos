package com.chenmeng.project.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 沉梦听雨
 */
@Data
public class UserVO implements Serializable {

    /**
     * 名字
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别：0=男，1=女
     */
    private Integer sex;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}