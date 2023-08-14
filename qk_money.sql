/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : qk_money

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 21/05/2023 21:52:11
*/

CREATE DATABASE IF NOT EXISTS `qk_money` CHARACTER SET 'utf8';
USE qk_money;


SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for demo
-- ----------------------------
DROP TABLE IF EXISTS `demo`;
CREATE TABLE `demo`  (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `tenant_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of demo
-- ----------------------------

-- ----------------------------
-- Table structure for gms_goods
-- ----------------------------
DROP TABLE IF EXISTS `gms_goods`;
CREATE TABLE `gms_goods`  (
  `id` bigint NOT NULL,
  `barcode` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '条码',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `pic` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品图片',
  `unit` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '单位',
  `size` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '规格',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '描述',
  `purchase_price` decimal(10, 2) NOT NULL COMMENT '进价',
  `sale_price` decimal(10, 2) NOT NULL COMMENT '售价',
  `sale_points` int NOT NULL DEFAULT 0 COMMENT '消耗积分',
  `stock` bigint NOT NULL DEFAULT 0 COMMENT '库存',
  `sales` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '销量',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'SALE' COMMENT '状态',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tenant_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of gms_goods
-- ----------------------------
INSERT INTO `gms_goods` VALUES (1631502170120650754, '12345', '麦尼', 'goods/1682172854906.jpg', '个', '170*130', '普通商品', 10.00, 99999.00, 0, 99, 3, 'SALE', 'money', '2023-03-03 11:50:04', 'money', '2023-03-03 11:50:04', 0);

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order`  (
  `id` bigint UNSIGNED NOT NULL,
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `member` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '零售' COMMENT '会员名',
  `member_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '会员id',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
  `cost_amount` decimal(10, 2) UNSIGNED NOT NULL COMMENT '总成本',
  `total_amount` decimal(10, 2) UNSIGNED NOT NULL COMMENT '总价',
  `pay_amount` decimal(10, 2) UNSIGNED NOT NULL COMMENT '实付款',
  `points_amount` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '消耗积分',
  `final_sales_amount` decimal(10, 2) UNSIGNED NOT NULL COMMENT '最终销售金额',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `payment_time` datetime NOT NULL COMMENT '支付时间',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_detail
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_detail`;
CREATE TABLE `oms_order_detail`  (
  `id` bigint NOT NULL,
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '订单号',
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
  `goods_id` bigint NOT NULL COMMENT '商品id',
  `goods_barcode` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品条码',
  `goods_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品名称',
  `goods_price` decimal(10, 2) NOT NULL COMMENT '实际单价',
  `quantity` int UNSIGNED NOT NULL COMMENT '数量',
  `sale_price` decimal(10, 2) NOT NULL COMMENT '售价',
  `purchase_price` decimal(10, 2) NOT NULL COMMENT '进价',
  `sale_points` int NOT NULL COMMENT '消耗积分',
  `return_quantity` int NOT NULL DEFAULT 0 COMMENT '退货数量',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_log
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_log`;
CREATE TABLE `oms_order_log`  (
  `id` bigint UNSIGNED NOT NULL,
  `order_id` bigint NOT NULL COMMENT '订单id',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oms_order_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名',
  `description` varchar(125) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '字典描述',
  `sort` int NOT NULL DEFAULT 999 COMMENT '排序',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1639859949399162883 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (1, 'switch', '开关', 1, '', '2022-03-05 16:22:32', '', '2022-03-05 16:22:34');
INSERT INTO `sys_dict` VALUES (2, 'permissionType', '权限类型', 1, '', '2022-03-06 12:02:55', '', '2022-03-06 12:02:58');
INSERT INTO `sys_dict` VALUES (3, 'yesOrNo', '是否', 2, '', '2022-03-05 16:22:32', '', '2022-03-05 16:22:34');
INSERT INTO `sys_dict` VALUES (1629418139531063298, 'memberType', '会员类型', 999, 'money', '2023-02-25 17:48:52', 'money', '2023-02-25 17:48:52');
INSERT INTO `sys_dict` VALUES (1629745205996666882, 'goodsStatus', '商品状态', 999, 'money', '2023-02-26 15:28:31', 'money', '2023-02-26 15:28:31');
INSERT INTO `sys_dict` VALUES (1629751225758216194, 'orderStatus', '订单状态', 999, 'money', '2023-02-26 15:52:26', 'money', '2023-02-26 15:52:32');
INSERT INTO `sys_dict` VALUES (1639859949399162882, 'demoStatus', 'Demo状态', 999, 'money', '2023-03-26 13:20:54', 'money', '2023-03-26 13:20:54');

-- ----------------------------
-- Table structure for sys_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_detail`;
CREATE TABLE `sys_dict_detail`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `dict` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典名',
  `label` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典标签',
  `value` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典值',
  `sort` int NOT NULL DEFAULT 999 COMMENT '排序',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1639860131993993219 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '字典明细表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_detail
-- ----------------------------
INSERT INTO `sys_dict_detail` VALUES (1, 'switch', '开', 'true', 1, '', '2022-03-30 22:13:11', '', '2022-03-30 22:13:17');
INSERT INTO `sys_dict_detail` VALUES (2, 'switch', '关', 'false', 2, '', '2022-03-30 22:13:11', '', '2022-03-30 22:13:17');
INSERT INTO `sys_dict_detail` VALUES (3, 'permissionType', '目录', 'DIR', 1, '', '2022-03-30 22:13:11', '', '2022-03-30 22:13:17');
INSERT INTO `sys_dict_detail` VALUES (4, 'permissionType', '菜单', 'MENU', 2, '', '2022-03-30 22:13:11', '', '2022-03-30 22:13:17');
INSERT INTO `sys_dict_detail` VALUES (5, 'permissionType', '按钮', 'BUTTON', 3, '', '2022-03-30 22:13:11', '', '2022-03-30 22:13:17');
INSERT INTO `sys_dict_detail` VALUES (6, 'yesOrNo', '是', 'true', 1, '', '2022-03-30 22:13:11', '', '2022-03-30 22:13:17');
INSERT INTO `sys_dict_detail` VALUES (7, 'yesOrNo', '否', 'false', 2, '', '2022-03-30 22:13:11', '', '2022-03-30 22:13:17');
INSERT INTO `sys_dict_detail` VALUES (1629418407601614850, 'memberType', '普通会员', 'MEMBER', 1, 'money', '2023-02-25 17:49:56', 'money', '2023-02-25 17:49:56');
INSERT INTO `sys_dict_detail` VALUES (1629418527323828226, 'memberType', '黄金会员', 'HJ_VIP', 2, 'money', '2023-02-25 17:50:25', 'money', '2023-02-25 17:50:25');
INSERT INTO `sys_dict_detail` VALUES (1629418584148258818, 'memberType', '铂金会员', 'BJ_VIP', 3, 'money', '2023-02-25 17:50:38', 'money', '2023-02-25 17:50:38');
INSERT INTO `sys_dict_detail` VALUES (1629745263274082306, 'goodsStatus', '在售', 'SALE', 1, 'money', '2023-02-26 15:28:45', 'money', '2023-02-26 15:28:45');
INSERT INTO `sys_dict_detail` VALUES (1629745293364019202, 'goodsStatus', '售罄', 'SOLD_OUT', 2, 'money', '2023-02-26 15:28:52', 'money', '2023-02-26 15:28:52');
INSERT INTO `sys_dict_detail` VALUES (1629745331712540673, 'goodsStatus', '下架', 'UN_SHELVE', 3, 'money', '2023-02-26 15:29:01', 'money', '2023-02-26 15:29:01');
INSERT INTO `sys_dict_detail` VALUES (1629751322239791106, 'OrderStatus', '已支付', 'PAID', 2, 'money', '2023-02-26 15:52:49', 'money', '2023-02-26 15:52:49');
INSERT INTO `sys_dict_detail` VALUES (1629751418805252098, 'OrderStatus', '已退单', 'RETURN', 4, 'money', '2023-02-26 15:53:12', 'money', '2023-02-26 15:53:20');
INSERT INTO `sys_dict_detail` VALUES (1639860083046465537, 'demoStatus', '开启', 'ON', 1, 'money', '2023-03-26 13:21:26', 'money', '2023-03-26 13:21:26');
INSERT INTO `sys_dict_detail` VALUES (1639860131993993218, 'demoStatus', '关闭', 'OFF', 2, 'money', '2023-03-26 13:21:37', 'money', '2023-03-26 13:21:37');

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` bigint UNSIGNED NOT NULL,
  `permission_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `permission_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '资源类型',
  `parent_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '父编码',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '图标',
  `permission` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '权限标识',
  `router_path` varchar(125) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '路由地址',
  `iframe` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否外链菜单',
  `hidden` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否隐藏',
  `component_name` varchar(125) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '组件名称',
  `component_path` varchar(125) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '组件路径',
  `sub_count` int NOT NULL DEFAULT 0 COMMENT '子节点数',
  `sort` int NOT NULL DEFAULT 999 COMMENT '排序',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime NOT NULL,
  `tenant_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '资源权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (1501921151197130754, '系统管理', 'DIR', 0, 'system', '', 'system', 0, 0, '', '', 5, 1, 'admin', '2022-03-10 22:01:21', 'admin', '2022-03-10 23:06:45', 0);
INSERT INTO `sys_permission` VALUES (1502278787507806210, '用户管理', 'MENU', 1501921151197130754, 'peoples', 'user:list', 'user', 0, 0, 'User', 'system/user/index', 3, 1, 'admin', '2022-03-11 21:42:29', 'admin', '2022-03-11 21:42:29', 0);
INSERT INTO `sys_permission` VALUES (1502863016289398785, '角色管理', 'MENU', 1501921151197130754, 'people', 'role:list', 'role', 0, 0, 'Role', 'system/role/index', 2, 2, 'admin', '2022-03-13 12:24:00', 'admin', '2022-03-13 12:24:00', 0);
INSERT INTO `sys_permission` VALUES (1502863270971731970, '权限管理', 'MENU', 1501921151197130754, 'permission', 'permission:list', 'permission', 0, 0, 'Permission', 'system/permission/index', 3, 3, 'admin', '2022-03-13 12:25:00', 'admin', '2022-03-13 12:25:00', 0);
INSERT INTO `sys_permission` VALUES (1503736683986800642, '新增用户', 'BUTTON', 1502278787507806210, '', 'user:add', '', 0, 0, '', '', 0, 1, 'admin', '2022-03-15 22:15:38', 'admin', '2022-03-15 22:15:38', 0);
INSERT INTO `sys_permission` VALUES (1503738104236822529, '修改用户', 'BUTTON', 1502278787507806210, '', 'user:edit', '', 0, 0, '', '', 0, 2, 'admin', '2022-03-15 22:21:17', 'admin', '2022-03-15 22:21:17', 0);
INSERT INTO `sys_permission` VALUES (1503738191579009025, '删除用户', 'BUTTON', 1502278787507806210, '', 'user:del', '', 0, 0, '', '', 0, 3, 'admin', '2022-03-15 22:21:38', 'admin', '2022-03-15 22:21:38', 0);
INSERT INTO `sys_permission` VALUES (1503753702563991553, '新增角色', 'BUTTON', 1502863016289398785, '', 'role:add', '', 0, 0, '', '', 0, 1, 'admin', '2022-03-15 23:23:16', 'admin', '2022-03-15 23:23:16', 0);
INSERT INTO `sys_permission` VALUES (1503753930130149377, '修改角色', 'BUTTON', 1502863016289398785, '', 'role:edit', '', 0, 0, '', '', 0, 2, 'admin', '2022-03-15 23:24:10', 'admin', '2022-03-15 23:24:10', 0);
INSERT INTO `sys_permission` VALUES (1503754013445804034, '删除角色', 'BUTTON', 1502863016289398785, '', 'role:del', '', 0, 0, '', '', 0, 3, 'admin', '2022-03-15 23:24:30', 'admin', '2022-03-15 23:24:30', 0);
INSERT INTO `sys_permission` VALUES (1503754297878335489, '新增权限', 'BUTTON', 1502863270971731970, '', 'permission:add', '', 0, 0, '', '', 0, 1, 'admin', '2022-03-15 23:25:38', 'admin', '2022-03-15 23:25:38', 0);
INSERT INTO `sys_permission` VALUES (1503754393558798337, '修改权限', 'BUTTON', 1502863270971731970, '', 'permission:edit', '', 0, 0, '', '', 0, 2, 'admin', '2022-03-15 23:26:00', 'admin', '2022-03-15 23:26:00', 0);
INSERT INTO `sys_permission` VALUES (1503754468678782978, '删除权限', 'BUTTON', 1502863270971731970, '', 'permission:del', '', 0, 0, '', '', 0, 3, 'admin', '2022-03-15 23:26:18', 'admin', '2022-03-15 23:26:18', 0);
INSERT INTO `sys_permission` VALUES (1507371326556450818, '字典管理', 'MENU', 1501921151197130754, 'dictionary', 'dict:list', 'dict', 0, 0, 'Dict', 'system/dict/index', 3, 4, 'money', '2022-03-25 22:58:25', 'money', '2022-03-25 22:58:25', 0);
INSERT INTO `sys_permission` VALUES (1507371669973479425, '新增字典', 'BUTTON', 1507371326556450818, '', 'dict:add', '', 0, 0, '', '', 0, 1, 'money', '2022-03-25 22:59:46', 'money', '2022-03-25 22:59:46', 0);
INSERT INTO `sys_permission` VALUES (1507371725170520065, '修改字典', 'BUTTON', 1507371326556450818, '', 'dict:edit', '', 0, 0, '', '', 0, 2, 'money', '2022-03-25 23:00:00', 'money', '2022-03-25 23:00:00', 0);
INSERT INTO `sys_permission` VALUES (1507371776840151041, '删除字典', 'BUTTON', 1507371326556450818, '', 'dict:del', '', 0, 0, '', '', 0, 3, 'money', '2022-03-25 23:00:12', 'money', '2022-03-25 23:00:12', 0);
INSERT INTO `sys_permission` VALUES (1507555956060450818, '租户管理', 'MENU', 1501921151197130754, 'tenant', 'tenant:list', 'tenant', 0, 0, 'Tenant', 'system/tenant/index', 3, 5, 'money', '2022-03-26 11:12:04', 'money', '2022-03-26 11:12:04', 0);
INSERT INTO `sys_permission` VALUES (1507556070254571522, '新增租户', 'BUTTON', 1507555956060450818, '', 'tenant:add', '', 0, 0, '', '', 0, 1, 'money', '2022-03-26 11:12:31', 'money', '2022-03-26 11:12:31', 0);
INSERT INTO `sys_permission` VALUES (1507556151250776065, '修改租户', 'BUTTON', 1507555956060450818, '', 'tenant:edit', '', 0, 0, '', '', 0, 2, 'money', '2022-03-26 11:12:50', 'money', '2022-03-26 11:12:50', 0);
INSERT INTO `sys_permission` VALUES (1507556213058039809, '删除租户', 'BUTTON', 1507555956060450818, '', 'tenant:del', '', 0, 0, '', '', 0, 3, 'money', '2022-03-26 11:13:05', 'money', '2022-03-26 11:13:05', 0);
INSERT INTO `sys_permission` VALUES (1629388418109894657, '会员管理', 'DIR', 0, 'ums', '', 'ums', 0, 0, '', '', 1, 3, 'money', '2023-02-25 15:50:46', 'money', '2023-02-25 15:50:46', 0);
INSERT INTO `sys_permission` VALUES (1629390135195037697, '会员', 'MENU', 1629388418109894657, 'ums-member', 'umsMember:list', 'member', 0, 0, 'Member', 'ums/member/index', 2, 1, 'money', '2023-02-25 15:57:35', 'money', '2023-02-25 15:57:35', 0);
INSERT INTO `sys_permission` VALUES (1629390281492361218, '新增', 'BUTTON', 1629390135195037697, '', 'umsMember:add', '', 0, 0, '', '', 0, 1, 'money', '2023-02-25 15:58:10', 'money', '2023-02-25 15:58:10', 0);
INSERT INTO `sys_permission` VALUES (1629390745160085505, '修改', 'BUTTON', 1629390135195037697, '', 'umsMember:edit', '', 0, 0, '', '', 0, 2, 'money', '2023-02-25 16:00:01', 'money', '2023-02-25 16:00:01', 0);
INSERT INTO `sys_permission` VALUES (1629390817495052289, '删除', 'BUTTON', 1629390135195037697, '', 'umsMember:del', '', 0, 0, '', '', 0, 3, 'money', '2023-02-25 16:00:18', 'money', '2023-02-25 16:00:18', 0);
INSERT INTO `sys_permission` VALUES (1629707272975482881, '商品管理', 'DIR', 0, 'gms', '', 'gms', 0, 0, '', '', 2, 4, 'money', '2023-02-26 12:57:47', 'money', '2023-02-26 12:57:47', 0);
INSERT INTO `sys_permission` VALUES (1629731562059952129, '商品', 'MENU', 1629707272975482881, 'gms-goods', 'gmsGoods:list', 'goods', 0, 0, 'Goods', 'gms/goods/index', 1, 2, 'money', '2023-02-26 14:34:18', 'money', '2023-02-26 14:34:18', 0);
INSERT INTO `sys_permission` VALUES (1629731649087565825, '新增', 'BUTTON', 1629731562059952129, '', 'gmsGoods:add', '', 0, 0, '', '', 0, 1, 'money', '2023-02-26 14:34:39', 'money', '2023-02-26 14:34:39', 0);
INSERT INTO `sys_permission` VALUES (1629731729861472258, '修改', 'BUTTON', 1629731562059952129, '', 'gmsGoods:edit', '', 0, 0, '', '', 0, 2, 'money', '2023-02-26 14:34:58', 'money', '2023-02-26 14:34:58', 0);
INSERT INTO `sys_permission` VALUES (1629731810069147650, '删除', 'BUTTON', 1629731562059952129, '', 'gmsGoods:del', '', 0, 0, '', '', 0, 3, 'money', '2023-02-26 14:35:17', 'money', '2023-02-26 14:35:17', 0);
INSERT INTO `sys_permission` VALUES (1629732061718999041, '订单管理', 'DIR', 0, 'oms', '', 'oms', 0, 0, '', '', 1, 5, 'money', '2023-02-26 14:36:17', 'money', '2023-02-26 14:36:17', 0);
INSERT INTO `sys_permission` VALUES (1629732239595237378, '订单', 'MENU', 1629732061718999041, 'oms-order', 'omsOrder:list', 'order', 0, 0, 'Order', 'oms/order/index', 1, 1, 'money', '2023-02-26 14:37:00', 'money', '2023-02-26 14:37:00', 0);
INSERT INTO `sys_permission` VALUES (1629732461629108226, '修改', 'BUTTON', 1629732239595237378, '', 'omsOrder:edit', '', 0, 0, '', '', 0, 1, 'money', '2023-02-26 14:37:52', 'money', '2023-02-26 14:37:52', 0);
INSERT INTO `sys_permission` VALUES (1629767886267736065, '日常', 'DIR', 0, 'calendar', '', 'pos', 0, 0, '', '', 1, 2, 'money', '2023-02-26 16:58:38', 'money', '2023-02-26 16:58:38', 0);
INSERT INTO `sys_permission` VALUES (1629768001384603649, '收银台', 'MENU', 1629767886267736065, 'pos', 'pos:cashier', 'pos', 0, 0, 'Pos', 'pos/index', 0, 1, 'money', '2023-02-26 16:59:06', 'money', '2023-02-26 16:59:06', 0);
INSERT INTO `sys_permission` VALUES (1634214613527801857, '订单详情', 'MENU', 1629732061718999041, 'oms', 'omsOrder:detail', 'order/detail/:id', 0, 1, 'OrderDetail', 'oms/order/detail', 0, 2, 'money', '2023-03-10 23:28:21', 'money', '2023-03-10 23:28:21', 0);
INSERT INTO `sys_permission` VALUES (1639618080955469825, 'Demo', 'MENU', 1639854047191351298, 'demo', 'demo:list', 'demo', 0, 0, 'Demo', 'demo/index', 3, 1, 'money', '2023-03-25 13:19:47', 'money', '2023-03-25 13:19:47', 0);
INSERT INTO `sys_permission` VALUES (1639618292646187010, '新增', 'BUTTON', 1639618080955469825, '', 'demo:add', '', 0, 0, '', '', 0, 1, 'money', '2023-03-25 13:20:38', 'money', '2023-03-25 13:20:38', 0);
INSERT INTO `sys_permission` VALUES (1639618438662492161, '修改', 'BUTTON', 1639618080955469825, '', 'demo:edit', '', 0, 0, '', '', 0, 2, 'money', '2023-03-25 13:21:13', 'money', '2023-03-25 13:21:13', 0);
INSERT INTO `sys_permission` VALUES (1639618548108660738, '删除', 'BUTTON', 1639618080955469825, '', 'demo:del', '', 0, 0, '', '', 0, 3, 'money', '2023-03-25 13:21:39', 'money', '2023-03-25 13:21:39', 0);
INSERT INTO `sys_permission` VALUES (1639854047191351298, 'Demo管理', 'DIR', 0, 'demo', '', 'demo', 0, 1, '', '', 0, 999, 'money', '2023-03-26 12:57:27', 'money', '2023-03-26 12:57:27', 0);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint UNSIGNED NOT NULL,
  `role_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色编码',
  `role_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `level` int NOT NULL COMMENT '角色级别',
  `description` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '角色描述',
  `count` bigint NOT NULL DEFAULT 0 COMMENT '角色人数',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '可用状态：0-禁用；1-启用',
  `sort` int NOT NULL DEFAULT 999 COMMENT '排序',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL,
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime NOT NULL,
  `tenant_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, 'SUPER_ADMIN', '超级管理员', 0, '拥有全部权限的人', 1, 1, 999, '', '2021-09-07 22:49:27', 'admin', '2022-03-06 11:40:47', 0);
INSERT INTO `sys_role` VALUES (1502845638751055873, 'ADMIN', '管理员', 1, '管理员', 1, 1, 999, 'admin', '2022-03-13 11:14:56', 'admin', '2022-03-13 11:14:56', 0);
INSERT INTO `sys_role` VALUES (1502845786646409218, 'GUEST', '游客', 99, '只能查不能改', 1, 1, 999, 'admin', '2022-03-13 11:15:32', 'admin', '2022-03-13 11:15:42', 0);

-- ----------------------------
-- Table structure for sys_role_permission_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission_relation`;
CREATE TABLE `sys_role_permission_relation`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `permission_id` bigint UNSIGNED NOT NULL COMMENT '资源权限id',
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色id',
  `tenant_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1657587211200319520 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色资源权限关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_permission_relation
-- ----------------------------
INSERT INTO `sys_role_permission_relation` VALUES (1507375871487504386, 1501921151197130754, 1502845786646409218, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1507375871487504387, 1502278787507806210, 1502845786646409218, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1507375871487504388, 1507371326556450818, 1502845786646409218, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1507375871487504389, 1502863270971731970, 1502845786646409218, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1507375871487504390, 1502863016289398785, 1502845786646409218, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466114, 1501921151197130754, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466115, 1502278787507806210, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466116, 1629731649087565825, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466117, 1629732461629108226, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466118, 1503738104236822529, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466119, 1507556213058039809, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466120, 1629767886267736065, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466121, 1507555956060450818, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466122, 1629768001384603649, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466123, 1503736683986800642, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466124, 1629731729861472258, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466125, 1503754013445804034, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466126, 1503753702563991553, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466127, 1629390281492361218, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466128, 1629732239595237378, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466129, 1629390817495052289, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466130, 1629732061718999041, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466131, 1503753930130149377, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466132, 1502863016289398785, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466133, 1507556070254571522, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466134, 1629388418109894657, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466135, 1629707272975482881, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466136, 1629390745160085505, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466137, 1503738191579009025, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466138, 1629731562059952129, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466139, 1634214613527801857, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466140, 1629731810069147650, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466141, 1507556151250776065, 1502845638751055873, 0);
INSERT INTO `sys_role_permission_relation` VALUES (1657039915685466142, 1629390135195037697, 1502845638751055873, 0);

-- ----------------------------
-- Table structure for sys_tenant
-- ----------------------------
DROP TABLE IF EXISTS `sys_tenant`;
CREATE TABLE `sys_tenant`  (
  `id` bigint UNSIGNED NOT NULL,
  `tenant_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '租户code',
  `logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'https://wpimg.wallstcn.com/69a1c46c-eb1c-4b46-8bd4-e9e686ef5251.png' COMMENT 'logo',
  `ico` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'ico',
  `domain` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '域名',
  `tenant_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '租户名称',
  `tenant_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '租户描述',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sort` int NOT NULL DEFAULT 99,
  `tenant_id` bigint UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '租户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_tenant
-- ----------------------------
INSERT INTO `sys_tenant` VALUES (0, 'M', 'https://wpimg.wallstcn.com/69a1c46c-eb1c-4b46-8bd4-e9e686ef5251.png', '', 'www.money.com', '麦尼收银', '主租户', 0, '', '2022-05-29 10:15:22', '', '2022-03-26 14:06:28', 99, 0);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint UNSIGNED NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif' COMMENT '头像',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号码',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `remark` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '可用状态：0-禁用；1-启用',
  `init_login` tinyint(1) NOT NULL DEFAULT 1 COMMENT '初次登录：0-不是；1-是',
  `last_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后登录时间',
  `sort` int NOT NULL DEFAULT 99,
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tenant_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uniq_username`(`username`, `tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'money', '$2a$10$W6oaOSARIA3DsZy1DkdfUuqI3L7a885Ci7AYvpQK.9NGbeVhcZihi', 'money', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '18120800000', 'money@qq.com', '俺是一个超级管理员！', 1, 1, '2023-05-20 22:07:21', 99, '', '2022-03-03 23:12:57', 'money', '2022-03-25 23:41:26', 0);
INSERT INTO `sys_user` VALUES (1502254138862391297, 'admin', '$2a$10$630Mdca6BcyUJpKC2LNT7eT93.k9pmpcQoes4qm/j2o.pnb725zE6', 'admin', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '18120803972', 'admin@qq.com', '', 1, 1, '2023-05-12 23:03:17', 99, 'admin', '2022-03-11 20:04:32', 'money', '2022-03-25 23:41:06', 0);
INSERT INTO `sys_user` VALUES (1504612500111388673, 'guest', '$2a$10$Nj/4Tn.cj2SEdoIUqMz7FOczatNV/AltEu07ieTpAO.5hEGV7lZqC', 'guest', 'https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif', '18120800002', 'guest@qq.com', '', 1, 1, '2022-09-03 12:07:24', 99, '001', '2022-03-18 08:15:49', 'money', '2022-03-25 23:41:00', 0);

-- ----------------------------
-- Table structure for sys_user_role_relation
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role_relation`;
CREATE TABLE `sys_user_role_relation`  (
  `id` bigint UNSIGNED NOT NULL,
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户id',
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色id',
  `tenant_id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role_relation
-- ----------------------------
INSERT INTO `sys_user_role_relation` VALUES (1507382045234470913, 1504612500111388673, 1502845786646409218, 0);
INSERT INTO `sys_user_role_relation` VALUES (1507382069087477762, 1502254138862391297, 1502845638751055873, 0);
INSERT INTO `sys_user_role_relation` VALUES (1507382155225899009, 1, 1, 0);

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member`  (
  `id` bigint UNSIGNED NOT NULL,
  `name` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '会员名称',
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT 'MEMBER' COMMENT '会员类型',
  `phone` varchar(24) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '联系电话',
  `points` int UNSIGNED NOT NULL DEFAULT 0 COMMENT '积分',
  `consume_amount` decimal(12, 2) NOT NULL DEFAULT 0.00 COMMENT '总消费金额',
  `consume_times` int NOT NULL DEFAULT 0 COMMENT '消费次数',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '备注',
  `create_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_by` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '会员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ums_member
-- ----------------------------
INSERT INTO `ums_member` VALUES (1631500305802960897, '麦尼', 'BJ_VIP', '18100004321', 99999, 0.00, 0, '老顾客了', 'money', '2023-03-03 11:42:39', 'money', '2023-04-09 12:24:19', 0);

SET FOREIGN_KEY_CHECKS = 1;
