/*
SQLyog Ultimate v12.08 (64 bit)
MySQL - 5.7.24 : Database - spausers
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`spausers` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `spausers`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT '' COMMENT '文章内容',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `delete_status` varchar(1) DEFAULT '1' COMMENT '是否有效  1.有效  2无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='发布号作者表';

/*Data for the table `article` */

insert  into `article`(`id`,`content`,`create_time`,`update_time`,`delete_status`) values (5,'莎士比亚','2017-10-25 09:08:45','2017-10-30 17:59:41','1'),(6,'亚里士多德','2017-10-26 10:49:28','2017-11-18 09:54:15','1'),(10,'亚历山大','2017-10-26 14:57:45','2017-11-08 13:28:52','1'),(11,'李白','2017-10-26 15:23:42','2017-10-26 15:23:42','1'),(19,'文章test2','2017-11-18 13:37:07','2017-11-18 13:37:11','1');

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` int(11) NOT NULL DEFAULT '0' COMMENT '自定id,主要供前端展示权限列表分类排序使用.',
  `menu_code` varchar(255) DEFAULT '' COMMENT '归属菜单,前端判断并展示菜单使用,',
  `menu_name` varchar(255) DEFAULT '' COMMENT '菜单的中文释义',
  `permission_code` varchar(255) DEFAULT '' COMMENT '权限的代码/通配符,对应代码中@RequiresPermissions 的value',
  `permission_name` varchar(255) DEFAULT '' COMMENT '本权限的中文释义',
  `required_permission` tinyint(1) DEFAULT '2' COMMENT '是否本菜单必选权限, 1.必选 2非必选 通常是"列表"权限是必选',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='后台权限表';

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`menu_code`,`menu_name`,`permission_code`,`permission_name`,`required_permission`) values (101,'article','文章管理','article:list','列表',1),(102,'article','文章管理','article:add','新增',2),(103,'article','文章管理','article:update','修改',2),(601,'user','用户','user:list','列表',1),(602,'user','用户','user:add','新增',2),(603,'user','用户','user:update','修改',2),(701,'role','角色权限','role:list','列表',1),(702,'role','角色权限','role:add','新增',2),(703,'role','角色权限','role:update','修改',2),(704,'role','角色权限','role:delete','删除',2);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) DEFAULT NULL COMMENT '角色名',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_status` varchar(1) DEFAULT '1' COMMENT '是否有效  1有效  2无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='后台角色表';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`role_name`,`create_time`,`update_time`,`delete_status`) values (1,'管理员','2017-11-22 16:24:34','2017-11-22 16:24:52','1'),(2,'作家','2017-11-22 16:24:34','2017-11-22 16:24:52','1'),(3,'程序员','2017-11-22 16:28:47','2017-11-22 16:28:47','1');

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) DEFAULT NULL COMMENT '权限id',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `delete_status` varchar(1) DEFAULT '1' COMMENT '是否有效 1有效     2无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8 COMMENT='角色-权限关联表';

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`id`,`role_id`,`permission_id`,`create_time`,`update_time`,`delete_status`) values (1,2,101,'2017-11-22 16:26:21','2017-11-22 16:26:32','1'),(2,2,102,'2017-11-22 16:26:21','2017-11-22 16:26:32','1'),(5,2,602,'2017-11-22 16:28:28','2017-11-22 16:28:28','1'),(6,2,601,'2017-11-22 16:28:28','2017-11-22 16:28:28','1'),(7,2,603,'2017-11-22 16:28:28','2017-11-22 16:28:28','1'),(8,2,703,'2017-11-22 16:28:28','2017-11-22 16:28:28','1'),(9,2,701,'2017-11-22 16:28:28','2017-11-22 16:28:28','1'),(10,2,702,'2017-11-22 16:28:28','2017-11-22 16:28:28','1'),(11,2,704,'2017-11-22 16:28:31','2017-11-22 16:28:31','1'),(12,2,103,'2017-11-22 16:28:31','2017-11-22 16:28:31','1'),(13,3,601,'2017-11-22 16:28:47','2017-11-22 16:28:47','1'),(14,3,701,'2017-11-22 16:28:47','2017-11-22 16:28:47','1'),(15,3,702,'2017-11-22 16:35:01','2017-11-22 16:35:01','1'),(16,3,704,'2017-11-22 16:35:01','2017-11-22 16:35:01','1'),(17,3,102,'2017-11-22 16:35:01','2017-11-22 16:35:01','1'),(18,3,101,'2017-11-22 16:35:01','2017-11-22 16:35:01','1'),(19,3,603,'2017-11-22 16:35:01','2017-11-22 16:35:01','1');

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) DEFAULT NULL COMMENT '昵称',
  `role_id` int(11) DEFAULT '0' COMMENT '角色ID',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `delete_status` varchar(1) DEFAULT '1' COMMENT '是否有效  1有效  2无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10008 DEFAULT CHARSET=utf8 COMMENT='运营后台用户表';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`password`,`nickname`,`role_id`,`create_time`,`update_time`,`delete_status`) values (10003,'admin','123456','超级用户23',1,'2017-10-30 11:52:38','2017-11-17 23:51:40','1'),(10004,'user','123456','莎士比亚',2,'2017-10-30 16:13:02','2017-11-18 02:48:24','1'),(10005,'aaa','123456','abba',1,'2017-11-15 14:02:56','2017-11-17 23:51:42','1'),(10007,'test','123456','就看看列表',3,'2017-11-22 16:29:41','2017-11-22 16:29:41','1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
