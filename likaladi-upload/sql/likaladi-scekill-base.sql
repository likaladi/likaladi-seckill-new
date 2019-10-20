/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50638
Source Host           : localhost:3306
Source Database       : likaladi-scekill-base

Target Server Type    : MYSQL
Target Server Version : 50638
File Encoding         : 65001

Date: 2019-10-20 23:20:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for file_info
-- ----------------------------
DROP TABLE IF EXISTS `file_info`;
CREATE TABLE `file_info` (
  `id` varchar(32) NOT NULL COMMENT '文件md5',
  `name` varchar(128) NOT NULL COMMENT '文件名',
  `isImg` tinyint(1) NOT NULL COMMENT '是否是图片',
  `content_type` varchar(128) NOT NULL COMMENT '文件类型',
  `size` bigint(11) NOT NULL COMMENT '文件大小',
  `path` varchar(255) DEFAULT NULL COMMENT '物理路径',
  `url` varchar(1024) NOT NULL COMMENT '文件网络url',
  `source` varchar(32) NOT NULL COMMENT '文件存储地方',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `createTime` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件信息表';
