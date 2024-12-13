package com.chenmeng.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chenmeng.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户和角色关联表
 *
 * @author chenmeng
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "system_user_role")
@Data
public class SystemUserRoleDO extends BaseDO {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 角色ID
     */
    private Long roleId;
}