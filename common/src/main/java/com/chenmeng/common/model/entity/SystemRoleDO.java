package com.chenmeng.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chenmeng.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色信息表
 *
 * @author chenmeng
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "system_role")
@Data
public class SystemRoleDO extends BaseDO {

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色权限字符串，使用逗号分隔
     */
    private String code;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）
     */
    private Integer dataScope;

    /**
     * 数据范围(指定部门数组)
     */
    private String dataScopeDeptIds;

    /**
     * 角色状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 角色类型
     */
    private Integer type;

    /**
     * 备注
     */
    private String remark;
}