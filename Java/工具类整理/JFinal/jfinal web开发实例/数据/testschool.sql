/*
Navicat MySQL Data Transfer

Source Server         : 192.168.1.2
Source Server Version : 50615
Source Host           : 192.168.1.2:3306
Source Database       : testschool

Target Server Type    : MYSQL
Target Server Version : 50615
File Encoding         : 65001

Date: 2015-01-23 10:35:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for classes
-- ----------------------------
DROP TABLE IF EXISTS `classes`;
CREATE TABLE `classes` (
  `classesid` int(11) NOT NULL AUTO_INCREMENT,
  `classesname` varchar(255) DEFAULT NULL,
  `classesaddress` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`classesid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classes
-- ----------------------------
INSERT INTO `classes` VALUES ('1', '一班', '北京市');
INSERT INTO `classes` VALUES ('2', '二班', '河北省保定市');
INSERT INTO `classes` VALUES ('3', '三班', '测试班级');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `studentid` int(11) NOT NULL AUTO_INCREMENT,
  `studentname` varchar(255) DEFAULT NULL,
  `studentage` int(11) DEFAULT NULL,
  `studentsex` varchar(255) DEFAULT NULL,
  `classesid` int(11) NOT NULL,
  PRIMARY KEY (`studentid`),
  KEY `FK_CLASSESID` (`classesid`),
  CONSTRAINT `FK_CLASSESID` FOREIGN KEY (`classesid`) REFERENCES `classes` (`classesid`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('1', '小明', '18', '男', '1');
INSERT INTO `student` VALUES ('2', '测试', '18', '女', '1');
INSERT INTO `student` VALUES ('7', '用的', '18', '女', '1');
