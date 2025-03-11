-- 创建库
create database if not exists chenmeng_test;

-- 切换库
use chenmeng_test;

-- 基础表
drop table if exists chenmeng_test.`t_serviceType_name`;
create table chenmeng_test.`t_serviceType_name`
(
    id          bigint auto_increment comment '主键' primary key,

    create_user bigint                             null comment '创建人标识',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user bigint                             null comment '修改人标识',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted  tinyint  default 0                 not null comment '是否已删除 0=否，1=是（删除）',
    tenant_id   bigint   default 0                 not null comment '租户编号'
) comment '基础表' collate = utf8mb4_unicode_ci;

-- 用户表
create table if not exists chenmeng_test.user
(
    id            bigint auto_increment comment '主键' primary key,
    user_account  varchar(256)                           not null comment '账号',
    user_password varchar(512)                           not null comment '密码',
    user_name     varchar(256)                           null comment '用户昵称',
    user_avatar   varchar(1024)                          null comment '用户头像',
    gender        tinyint                                null comment '性别：0 - 未知；1 - 男；2 -女',
    phone         varchar(128)                           null comment '手机号',
    email         varchar(256)                           null comment '邮箱',
    user_profile  varchar(512)                           null comment '用户简介',
    user_role     varchar(5) default 'user'              not null comment '用户角色：user/admin',

    create_user   bigint                                 null comment '创建人标识',
    create_time   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user   bigint                                 null comment '修改人标识',
    update_time   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted    tinyint      default 0                 not null comment '是否已删除 0=否，1=是（删除）',
    tenant_id     bigint       default 0                 not null comment '租户编号',
    UNIQUE KEY uk_userAccount (user_account),
    INDEX idx_userName (user_name)
) comment '用户表' collate = utf8mb4_unicode_ci;

-- 接口信息
create table if not exists chenmeng_test.`interface_info`
(
    id              bigint auto_increment comment '主键' primary key,
    name            varchar(256)                       not null comment '名称',
    description     varchar(256)                       null comment '描述',
    url             varchar(512)                       not null comment '接口地址',
    request_params  text                               not null comment '请求参数',
    request_header  text                               null comment '请求头',
    response_header text                               null comment '响应头',
    status          int      default 0                 not null comment '接口状态（0-关闭，1-开启）',
    method          varchar(256)                       not null comment '请求类型',

    create_user     bigint                             null comment '创建人标识',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user     bigint                             null comment '修改人标识',
    update_time     datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted      tinyint  default 0                 not null comment '是否已删除 0=否，1=是（删除）',
    tenant_id       bigint   default 0                 not null comment '租户编号'
) comment '接口信息表';

-- 图片表
create table if not exists chenmeng_test.picture
(
    id           bigint auto_increment comment '主键' primary key,
    url          varchar(512)                       not null comment '图片 url',
    name         varchar(128)                       not null comment '图片名称',
    introduction varchar(512)                       null comment '简介',
    category     varchar(64)                        null comment '分类',
    tags         varchar(512)                       null comment '标签（JSON 数组）',
    pic_size     bigint                             null comment '图片体积',
    pic_width    int                                null comment '图片宽度',
    pic_height   int                                null comment '图片高度',
    pic_scale    double                             null comment '图片宽高比例',
    pic_format   varchar(32)                        null comment '图片格式',

    create_user  bigint                             null comment '创建人标识',
    create_time  datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user  bigint                             null comment '修改人标识',
    update_time  datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   tinyint  default 0                 not null comment '是否已删除 0=否，1=是（删除）',
    tenant_id    bigint   default 0                 not null comment '租户编号',
    INDEX idx_name (name),                 -- 提升基于图片名称的查询性能
    INDEX idx_introduction (introduction), -- 用于模糊搜索图片简介
    INDEX idx_category (category),         -- 提升基于分类的查询性能
    INDEX idx_tags (tags),                 -- 提升基于标签的查询性能
    INDEX idx_createUser (create_user)     -- 提升基于用户 ID 的查询性能
) comment '图片' collate = utf8mb4_unicode_ci;

# region 可用于测试 RABC 权限
-- 用户信息表
DROP TABLE IF EXISTS `system_users`;
CREATE TABLE `system_users`
(
    `id`         bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`   varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户账号',
    `password`   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
    `nickname`   varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '用户昵称',
    `remark`     varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '备注',
    `dept_id`    bigint                                                        NULL     DEFAULT NULL COMMENT '部门ID',
    `post_ids`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '岗位编号数组',
    `email`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT '' COMMENT '用户邮箱',
    `mobile`     varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT '' COMMENT '手机号码',
    `sex`        tinyint                                                       NULL     DEFAULT 0 COMMENT '用户性别',
    `avatar`     varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT '' COMMENT '头像地址',
    `status`     tinyint                                                       NOT NULL DEFAULT 0 COMMENT '帐号状态（0正常 1停用）',
    `login_ip`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NULL     DEFAULT '' COMMENT '最后登录IP',
    `login_date` datetime                                                      NULL     DEFAULT NULL COMMENT '最后登录时间',

    create_user  bigint                                                        null comment '创建人标识',
    create_time  datetime                                                               default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user  bigint                                                        null comment '修改人标识',
    update_time  datetime                                                               default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted   tinyint                                                                default 0 not null comment '是否已删除 0=否，1=是（删除）',
    tenant_id    bigint                                                                 default 0 not null comment '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户信息表';

-- 角色信息表
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`
(
    `id`                  bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `name`                varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '角色名称',
    `code`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串，使用逗号分隔',
    `sort`                int                                                           NOT NULL COMMENT '显示顺序',
    `data_scope`          tinyint                                                       NOT NULL DEFAULT 1 COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
    `data_scope_dept_ids` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据范围(指定部门数组)',
    `status`              tinyint                                                       NOT NULL COMMENT '角色状态（0正常 1停用）',
    `type`                tinyint                                                       NOT NULL COMMENT '角色类型',
    `remark`              varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL COMMENT '备注',

    create_user           bigint                                                        null comment '创建人标识',
    create_time           datetime default CURRENT_TIMESTAMP                            not null comment '创建时间',
    update_user           bigint                                                        null comment '修改人标识',
    update_time           datetime default CURRENT_TIMESTAMP                            not null on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted            tinyint  default 0                                            not null comment '是否已删除 0=否，1=是（删除）',
    tenant_id             bigint   default 0                                            not null comment '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '角色信息表';

-- 用户和角色关联表
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`
(
    `id`        bigint                             NOT NULL AUTO_INCREMENT COMMENT '自增编号',
    `user_id`   bigint                             NOT NULL COMMENT '用户ID',
    `role_id`   bigint                             NOT NULL COMMENT '角色ID',

    create_user bigint                             null comment '创建人标识',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user bigint                             null comment '修改人标识',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted  tinyint  default 0                 not null comment '是否已删除 0=否，1=是（删除）',
    tenant_id   bigint   default 0                 not null comment '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '用户和角色关联表';

-- 菜单权限表
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`
(
    `id`             bigint                                                        NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
    `name`           varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci  NOT NULL COMMENT '菜单名称',
    `permission`     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '权限标识，一般格式为 ${大模块}:${小模块}:{操作}',
    `type`           tinyint                                                       NOT NULL COMMENT '菜单类型（M目录 C菜单 F按钮）',
    `sort`           int                                                           NOT NULL DEFAULT 0 COMMENT '显示顺序',
    `parent_id`      bigint                                                        NOT NULL DEFAULT 0 COMMENT '父菜单ID',
    `path`           varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT '' COMMENT '路由地址',
    `icon`           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT '#' COMMENT '菜单图标',
    `component`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '组件路径',
    `component_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL     DEFAULT NULL COMMENT '组件名',
    `status`         tinyint                                                       NOT NULL DEFAULT 0 COMMENT '菜单状态',
    `visible`        bit(1)                                                        NOT NULL DEFAULT b'1' COMMENT '是否可见',
    `keep_alive`     bit(1)                                                        NOT NULL DEFAULT b'1' COMMENT '是否缓存',
    `always_show`    bit(1)                                                        NOT NULL DEFAULT b'1' COMMENT '是否总是显示',

    create_user      bigint                                                        null comment '创建人标识',
    create_time      datetime                                                               default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user      bigint                                                        null comment '修改人标识',
    update_time      datetime                                                               default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted       tinyint                                                                default 0 not null comment '是否已删除 0=否，1=是（删除）',
    tenant_id        bigint                                                                 default 0 not null comment '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '菜单权限表';

-- 角色和菜单关联表
DROP TABLE IF EXISTS `system_role_menu`;
CREATE TABLE `system_role_menu`
(
    `id`        bigint                             NOT NULL AUTO_INCREMENT COMMENT '自增编号',
    `role_id`   bigint                             NOT NULL COMMENT '角色ID',
    `menu_id`   bigint                             NOT NULL COMMENT '菜单ID',

    create_user bigint                             null comment '创建人标识',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    update_user bigint                             null comment '修改人标识',
    update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '修改时间',
    is_deleted  tinyint  default 0                 not null comment '是否已删除 0=否，1=是（删除）',
    tenant_id   bigint   default 0                 not null comment '租户编号',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT = '角色和菜单关联表';
# endregion