package com.chenmeng.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chenmeng.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色和菜单关联表
 *
 * @author chenmeng
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "system_role_menu")
@Data
public class SystemRoleMenuDO extends BaseDO {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 菜单ID
     */
    private Long menuId;
}