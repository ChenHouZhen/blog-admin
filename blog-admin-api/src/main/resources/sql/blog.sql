
-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50)  NOT NULL COMMENT '用户名',
  `password` varchar(100)  DEFAULT NULL COMMENT '密码',
  `salt` varchar(20)  DEFAULT NULL COMMENT '盐',
  `email` varchar(100)  DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100)  DEFAULT NULL COMMENT '手机号',
  `status` tinyint(1) DEFAULT 1 COMMENT '状态  0：禁用   1：正常',
  `dept_id` int(11) DEFAULT NULL COMMENT '部门ID',
  `avatar` varchar(255) DEFAULT NULL COMMENT '用户头像',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',

PRIMARY KEY (`user_id`) USING BTREE,
UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = '系统用户';



-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200)  DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT '' COMMENT '菜单图标',
  `order_num` int(11) DEFAULT 0 COMMENT '排序',
PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4  COMMENT = '菜单管理' ;



-- ----------------------------
-- Table structure for sys_file
-- ----------------------------
DROP TABLE IF EXISTS `sys_file`;
CREATE TABLE `sys_file`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100)  NOT NULL COMMENT '名称',
  `url` text NOT NULL COMMENT 'URL地址',
  `size` bigint(11) NOT NULL COMMENT '大小,字节',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '0为正常状态，1为删除状态，默认为0',
  `type` int(100) NOT NULL DEFAULT 0 COMMENT '类型，, -1:无法解析，0:图片，1:文件，默认为0',
  `source` int(100) NOT NULL DEFAULT 0 COMMENT '存储位置，0:阿里云，默认为0',
  `desc` varchar(100)  DEFAULT NULL COMMENT '文件描述',
  `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int(11) DEFAULT NULL COMMENT '创建人',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT = '文件资源表';