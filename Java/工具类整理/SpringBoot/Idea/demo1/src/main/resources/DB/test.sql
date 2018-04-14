/*
Navicat MySQL Data Transfer

Source Server         : MySql
Source Server Version : 50067
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50067
File Encoding         : 65001

Date: 2017-10-28 15:39:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes` (
  `classesid` int(11) NOT NULL auto_increment,
  `classesname` varchar(255) default NULL,
  `classesaddress` varchar(255) default NULL,
  PRIMARY KEY  (`classesid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES ('1', '一班', '北京市');
INSERT INTO `classes` VALUES ('2', '二班', '河北省保定市');
INSERT INTO `classes` VALUES ('3', '三班', '测试班级');

-- ----------------------------
-- Table structure for ee
-- ----------------------------
DROP TABLE IF EXISTS `ee`;
CREATE TABLE `ee` (
  `testword` varchar(255) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of ee
-- ----------------------------

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `studentid` int(11) NOT NULL auto_increment,
  `studentname` varchar(255) default NULL,
  `studentage` int(11) default NULL,
  `studentsex` varchar(255) default NULL,
  `classesid` int(11) NOT NULL,
  PRIMARY KEY  (`studentid`),
  KEY `FK_CLASSESID` (`classesid`),
  CONSTRAINT `FK_CLASSESID` FOREIGN KEY (`classesid`) REFERENCES `classes` (`classesid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '小明', '18', '男', '1');
INSERT INTO `student` VALUES ('2', '小黑', '18', '女', '1');
INSERT INTO `student` VALUES ('7', '小花', '18', '女', '1');
INSERT INTO `student` VALUES ('10', '张三', '12', '男', '1');
INSERT INTO `student` VALUES ('11', 'Mr.薛', '12', '男', '1');
INSERT INTO `student` VALUES ('13', '123', '122', '男', '2');
INSERT INTO `student` VALUES ('14', '张三', '12', '男', '2');
INSERT INTO `student` VALUES ('15', '123', '123', '男', '3');
INSERT INTO `student` VALUES ('16', '123123', '12312', '男', '1');
