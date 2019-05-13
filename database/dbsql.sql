/*
SQLyog Ultimate v12.4.1 (64 bit)
MySQL - 5.5.53 : Database - tvacg
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`tvacg` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `tvacg`;

/*Table structure for table `resources` */

DROP TABLE IF EXISTS `resources`;

CREATE TABLE `resources` (
  `id` bigint(20) NOT NULL COMMENT '资源id',
  `userId` bigint(20) DEFAULT NULL COMMENT '资源作者id',
  `typeId` bigint(20) DEFAULT NULL COMMENT '资源分类id',
  `title` varchar(150) DEFAULT NULL COMMENT '资源标题',
  `describe` text COMMENT '资源描述，小作文',
  `coverFilename` varchar(100) DEFAULT NULL COMMENT '资源封面文件名',
  `from` varchar(40) DEFAULT NULL COMMENT '资源来源',
  `remarks` varchar(60) DEFAULT NULL COMMENT '资源备注，一般写提取码和解压密码',
  `download` varchar(100) DEFAULT NULL COMMENT '资源下载链接',
  `isDlete` tinyint(3) DEFAULT '1' COMMENT '1为存在，0为删除',
  `toExamine` tinyint(3) DEFAULT '0' COMMENT '1为审核通过，0为待审核，-1为未通过',
  `clicks` bigint(20) DEFAULT '0' COMMENT '点击量',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '上次修改时间',
  PRIMARY KEY (`id`),
  KEY `resourcesTypeId` (`typeId`),
  CONSTRAINT `resourcesTypeId` FOREIGN KEY (`typeId`) REFERENCES `resourcestype` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `resourcestype` */

DROP TABLE IF EXISTS `resourcestype`;

CREATE TABLE `resourcestype` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '资源类型id',
  `name` varchar(30) DEFAULT NULL COMMENT '资源名称',
  `isDlete` tinyint(3) DEFAULT '1' COMMENT '1为存在，0为不存在',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

/*Table structure for table `sequence` */

DROP TABLE IF EXISTS `sequence`;

CREATE TABLE `sequence` (
  `NAME` varchar(30) NOT NULL,
  `current_value` bigint(20) NOT NULL,
  `increment` bigint(20) NOT NULL DEFAULT '1',
  PRIMARY KEY (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL COMMENT '账号',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `isDlete` tinyint(3) DEFAULT '1' COMMENT '0为删除，1为存在',
  `isAdmin` tinyint(3) DEFAULT '0' COMMENT '0为用户，1为管理员，2为超级管理员',
  `isActivation` tinyint(3) DEFAULT '0' COMMENT '0为未激活，1为激活',
  `gmt_create` datetime DEFAULT NULL COMMENT '开户时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '上次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

/* Function  structure for function  `currval` */

/*!50003 DROP FUNCTION IF EXISTS `currval` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `currval`(seq_name VARCHAR(30)) RETURNS int(11)
    DETERMINISTIC
BEGIN
     DECLARE value INTEGER; 
     SET value = 0; 
     SELECT current_value INTO value 
          FROM sequence
          WHERE name = seq_name; 
     RETURN value; 
END */$$
DELIMITER ;

/* Function  structure for function  `nextval` */

/*!50003 DROP FUNCTION IF EXISTS `nextval` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `nextval`(seq_name VARCHAR(50)) RETURNS int(11)
    DETERMINISTIC
BEGIN
     UPDATE sequence
          SET current_value = current_value + increment 
          WHERE name = seq_name; 
     RETURN currval(seq_name); 
END */$$
DELIMITER ;

/* Function  structure for function  `setval` */

/*!50003 DROP FUNCTION IF EXISTS `setval` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `setval`(seq_name VARCHAR(50), value INTEGER) RETURNS int(11)
    DETERMINISTIC
BEGIN
     UPDATE sequence
          SET current_value = value 
          WHERE name = seq_name; 
     RETURN currval(seq_name); 
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

INSERT INTO sequence VALUES ('resourcesId', 1, 1); 