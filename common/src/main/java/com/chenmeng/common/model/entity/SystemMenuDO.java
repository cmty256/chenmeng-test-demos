package com.chenmeng.common.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chenmeng.common.model.base.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单权限表
 *
 * @author chenmeng
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "system_menu")
@Data
public class SystemMenuDO extends BaseDO {

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 权限标识，一般格式为 ${大模块}:${小模块}:{操作}
     */
    private String permission;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    private Integer type;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 父菜单ID
     */
    private Long parentId;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 组件名
     */
    private String componentName;

    /**
     * 菜单状态
     */
    private Integer status;

    /**
     * 是否可见
     */
    private Boolean visible;

    /**
     * 是否缓存
     */
    private Boolean keepAlive;

    /**
     * 是否总是显示
     */
    private Boolean alwaysShow;
}