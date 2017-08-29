/*
Navicat MySQL Data Transfer

Source Server         : young
Source Server Version : 50710
Source Host           : 127.0.0.1:3306
Source Database       : anyviewdb

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2016-09-01 14:56:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for anyview_version_table
-- ----------------------------
DROP TABLE IF EXISTS `anyview_version_table`;
CREATE TABLE `anyview_version_table` (
  `vid` int(11) NOT NULL,
  `version` varchar(10) DEFAULT NULL,
  `vupdatetime` datetime DEFAULT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of anyview_version_table
-- ----------------------------
INSERT INTO `anyview_version_table` VALUES ('1', '1.6.5', '2015-08-03 15:39:36');

-- ----------------------------
-- Table structure for attendancetable
-- ----------------------------
DROP TABLE IF EXISTS `attendancetable`;
CREATE TABLE `attendancetable` (
  `AID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '考勤ID,关键字',
  `SID` int(11) DEFAULT NULL COMMENT '学生ID，外键（级联），索引',
  `IP` varchar(255) DEFAULT NULL COMMENT 'IP地址',
  `Port` int(11) DEFAULT NULL COMMENT '端口',
  `LoginTime` datetime DEFAULT NULL COMMENT '登录时间',
  `LogoutTime` datetime DEFAULT NULL COMMENT '退出时间',
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`AID`),
  KEY `attendance_fk_sid` (`SID`) USING BTREE,
  CONSTRAINT `attendancetable_ibfk_1` FOREIGN KEY (`SID`) REFERENCES `studenttable` (`SID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of attendancetable
-- ----------------------------

-- ----------------------------
-- Table structure for classtable
-- ----------------------------
DROP TABLE IF EXISTS `classtable`;
CREATE TABLE `classtable` (
  `CID` int(11) NOT NULL AUTO_INCREMENT COMMENT '班级ID,关键字，递增',
  `CName` varchar(255) NOT NULL COMMENT '班级名称，可以重复，但（CeID +CName）唯一',
  `CeID` int(11) NOT NULL COMMENT '所在学院ID,外键（NoAction）',
  `Specialty` varchar(255) NOT NULL COMMENT '所在专业',
  `StartYear` int(11) NOT NULL COMMENT '年届',
  `Kind` int(11) NOT NULL COMMENT '类型 (0 普通班级 1 教师映射班级)',
  `Enabled` int(11) NOT NULL COMMENT '有效状态：（0-停用 1-正常（见教师表说明））',
  `EPID` int(11) NOT NULL COMMENT '考试编排ID，考试锁定状态下起作用',
  `Status` int(11) NOT NULL COMMENT '状态：\r\n0：未锁定\r\n1：登录锁定，该状态下不能登录\r\n2：做题锁定，该状态下不能做题\r\n3：考试锁定，该状态下不能查看其他作业表\r\n',
  `UpdateTime` datetime NOT NULL,
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`CID`),
  UNIQUE KEY `ClassTable_unique_key` (`CName`,`CeID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of classtable
-- ----------------------------
INSERT INTO `classtable` VALUES ('1', '旋风班3啦啦', '1', '计算机', '2015', '0', '0', '0', '0', '2015-09-17 11:38:58', '2015-09-20 16:02:37');
INSERT INTO `classtable` VALUES ('2', '烈火班喷水版', '1', '嵌入式', '2015', '0', '1', '0', '2', '2015-09-20 21:35:54', '2015-09-20 16:02:42');
INSERT INTO `classtable` VALUES ('3', '火影班', '2', '英语', '2015', '0', '1', '0', '0', '2015-08-06 11:18:41', null);
INSERT INTO `classtable` VALUES ('4', '卡卡西班', '2', '阿拉伯语', '2015', '0', '1', '0', '0', '2015-07-17 12:50:03', null);
INSERT INTO `classtable` VALUES ('5', '聪明班', '2', '聪明算术', '2015', '0', '1', '0', '1', '2015-07-30 14:39:45', null);
INSERT INTO `classtable` VALUES ('6', '漂亮班', '2', '装饰班', '2015', '0', '1', '0', '0', '2015-07-30 14:41:30', null);
INSERT INTO `classtable` VALUES ('7', '呼啦啦班', '2', '呼啦圈', '2015', '0', '1', '0', '0', '2015-08-07 10:34:37', null);
INSERT INTO `classtable` VALUES ('8', '神马班', '2', '绘图技术', '2015', '0', '1', '0', '0', '2015-08-07 10:35:15', null);
INSERT INTO `classtable` VALUES ('10', '盏鬼班', '2', '演戏技巧', '2015', '0', '1', '0', '0', '2015-08-07 10:36:51', null);
INSERT INTO `classtable` VALUES ('11', '飞翔是班', '2', '飞翔', '2015', '0', '1', '0', '0', '2015-10-26 20:48:35', null);
INSERT INTO `classtable` VALUES ('12', '华贵版', '2', '数钱', '2015', '0', '1', '0', '0', '2015-08-10 12:48:44', null);
INSERT INTO `classtable` VALUES ('13', '是梵蒂冈是', '2', '阿斯钢', '2015', '0', '1', '0', '0', '2015-08-10 12:49:12', null);
INSERT INTO `classtable` VALUES ('14', '干啥都管', '2', '是个', '2015', '0', '1', '0', '0', '2015-08-10 12:49:30', null);
INSERT INTO `classtable` VALUES ('16', '教师映射班级', '1', '教师映射班级', '2015', '1', '1', '1', '1', '2015-08-15 20:39:39', null);
INSERT INTO `classtable` VALUES ('17', '蓝精灵班', '2', '土木', '2015', '0', '1', '0', '0', '2015-10-26 20:43:56', '2015-10-26 20:43:56');
INSERT INTO `classtable` VALUES ('18', '蓝精灵班', '1', '土木', '2015', '0', '1', '0', '0', '2015-10-26 20:47:02', '2015-10-26 20:47:02');

-- ----------------------------
-- Table structure for class_coursetable
-- ----------------------------
DROP TABLE IF EXISTS `class_coursetable`;
CREATE TABLE `class_coursetable` (
  `CID` int(20) NOT NULL,
  `CourseID` int(20) NOT NULL,
  `TID` int(20) DEFAULT NULL,
  `StartYear` datetime(6) DEFAULT NULL,
  `Status` int(1) DEFAULT NULL,
  `UpdateTime` datetime(6) DEFAULT NULL,
  `VID` int(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_coursetable
-- ----------------------------
INSERT INTO `class_coursetable` VALUES ('1', '1', '1', '2015-08-01 17:29:17.000000', '1', '2015-08-01 17:29:27.000000', '1');
INSERT INTO `class_coursetable` VALUES ('2', '2', '2', '2015-08-01 17:29:45.000000', '1', '2015-08-01 17:29:50.000000', '1');

-- ----------------------------
-- Table structure for class_course_schemetable
-- ----------------------------
DROP TABLE IF EXISTS `class_course_schemetable`;
CREATE TABLE `class_course_schemetable` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '中间表逻辑ID，无业务作用，hibernate配置需要',
  `CID` int(11) NOT NULL,
  `CourseID` int(11) NOT NULL,
  `VID` int(11) NOT NULL,
  `TID` int(11) NOT NULL,
  `Status` int(1) NOT NULL,
  `UpdateTime` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `unique_key_cid_vid` (`CID`,`VID`) USING BTREE,
  KEY `cid_courseId` (`CID`,`CourseID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_course_schemetable
-- ----------------------------
INSERT INTO `class_course_schemetable` VALUES ('1', '1', '1', '1', '1', '1', '2015-08-12 20:23:44.000000');
INSERT INTO `class_course_schemetable` VALUES ('2', '1', '1', '2', '2', '1', '2015-08-12 20:23:58.000000');
INSERT INTO `class_course_schemetable` VALUES ('3', '1', '2', '3', '1', '1', '2015-12-01 19:01:36.000000');
INSERT INTO `class_course_schemetable` VALUES ('6', '1', '1', '4', '1', '1', '2015-12-01 19:01:58.000000');

-- ----------------------------
-- Table structure for class_studenttable
-- ----------------------------
DROP TABLE IF EXISTS `class_studenttable`;
CREATE TABLE `class_studenttable` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) DEFAULT NULL COMMENT '学生ID,关键字，外键（级联）',
  `SAttr` int(11) DEFAULT NULL COMMENT '属性：\r\n0休学学生：没有权限登录到系统中\r\n1普通学生：普通权限\r\n2班长：    普通权限+可以查看全班级学生的成绩等\r\n3教师专用：教师专用帐号，不参与成绩的统计等\r\n4教师专属：只属于某个教师，不允许修改密码\r\n',
  `cid` int(11) DEFAULT NULL COMMENT '班级ID,关键字，外键（级联）',
  `status` int(11) DEFAULT NULL COMMENT '状态：\r\n0：无效\r\n1：有效\r\n',
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_key_sid_cid` (`sid`,`cid`) USING BTREE,
  KEY `foreign_key_cid` (`cid`) USING BTREE,
  CONSTRAINT `class_studenttable_ibfk_1` FOREIGN KEY (`cid`) REFERENCES `classtable` (`CID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `class_studenttable_ibfk_2` FOREIGN KEY (`sid`) REFERENCES `studenttable` (`SID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_studenttable
-- ----------------------------
INSERT INTO `class_studenttable` VALUES ('1', '2', '1', '1', '1', '2015-09-20 23:03:32');
INSERT INTO `class_studenttable` VALUES ('2', '4', '1', '1', '1', '2015-09-20 23:03:52');
INSERT INTO `class_studenttable` VALUES ('3', '19', '2', '1', '1', '2015-09-22 01:01:39');
INSERT INTO `class_studenttable` VALUES ('4', '20', '0', '1', '0', '2015-09-22 01:07:09');
INSERT INTO `class_studenttable` VALUES ('5', '21', '1', '1', '1', '2015-09-20 23:04:31');
INSERT INTO `class_studenttable` VALUES ('6', '22', '1', '1', '1', '2015-09-20 23:04:40');
INSERT INTO `class_studenttable` VALUES ('7', '25', '1', '1', '1', '2015-11-02 23:43:55');
INSERT INTO `class_studenttable` VALUES ('8', '26', '1', '1', '1', '2015-09-20 23:04:57');
INSERT INTO `class_studenttable` VALUES ('9', '27', '1', '1', '1', '2015-09-20 23:05:05');
INSERT INTO `class_studenttable` VALUES ('10', '28', '1', '1', '1', '2015-09-20 23:05:18');
INSERT INTO `class_studenttable` VALUES ('11', '29', '1', '1', '1', '2015-09-20 23:05:30');
INSERT INTO `class_studenttable` VALUES ('12', '60', '1', '1', '1', '2015-11-02 23:44:19');
INSERT INTO `class_studenttable` VALUES ('13', '21', '1', '14', '1', '2015-11-03 13:54:30');
INSERT INTO `class_studenttable` VALUES ('14', '22', '1', '14', '1', '2015-11-03 13:54:30');
INSERT INTO `class_studenttable` VALUES ('15', '26', '1', '14', '1', '2015-11-03 13:54:30');
INSERT INTO `class_studenttable` VALUES ('16', '27', '1', '14', '1', '2015-11-03 13:54:30');
INSERT INTO `class_studenttable` VALUES ('17', '28', '1', '14', '1', '2015-11-03 13:54:30');
INSERT INTO `class_studenttable` VALUES ('18', '29', '1', '14', '1', '2015-11-03 13:54:30');

-- ----------------------------
-- Table structure for class_teachertable
-- ----------------------------
DROP TABLE IF EXISTS `class_teachertable`;
CREATE TABLE `class_teachertable` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '逻辑主键,hibernate需要',
  `TID` int(11) NOT NULL COMMENT '教师ID,关键字，外键（级联）',
  `CID` int(11) NOT NULL COMMENT '班级ID,关键字，外键（级联）',
  `TCRight` int(11) NOT NULL COMMENT '权限集：【】\r\n1：在线查看学生状态\r\n2：设置班级状态（考试用）\r\n4：重置学生密码\r\n8：管理学生（增删改查等）\r\n',
  `UpdateTime` datetime NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `class_id` (`CID`) USING BTREE,
  KEY `unique_key_tid_cid` (`TID`,`CID`) USING BTREE,
  CONSTRAINT `class_teachertable_ibfk_1` FOREIGN KEY (`CID`) REFERENCES `classtable` (`CID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `class_teachertable_ibfk_2` FOREIGN KEY (`TID`) REFERENCES `teachertable` (`TID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_teachertable
-- ----------------------------
INSERT INTO `class_teachertable` VALUES ('1', '1', '1', '15', '2015-08-15 20:31:56');
INSERT INTO `class_teachertable` VALUES ('2', '1', '2', '10', '2015-08-15 20:32:45');
INSERT INTO `class_teachertable` VALUES ('3', '1', '3', '3', '2015-08-15 20:33:35');
INSERT INTO `class_teachertable` VALUES ('4', '2', '4', '5', '2015-08-15 20:33:57');
INSERT INTO `class_teachertable` VALUES ('5', '2', '5', '5', '2015-08-15 20:34:07');
INSERT INTO `class_teachertable` VALUES ('6', '3', '6', '5', '2015-08-15 20:34:29');
INSERT INTO `class_teachertable` VALUES ('7', '3', '7', '10', '2015-08-15 20:34:44');

-- ----------------------------
-- Table structure for class_teacher_coursetable
-- ----------------------------
DROP TABLE IF EXISTS `class_teacher_coursetable`;
CREATE TABLE `class_teacher_coursetable` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT ' 逻辑主键，hibernate映射需要',
  `TID` int(20) NOT NULL COMMENT '教师ID,关键字，外键（级联）',
  `CourseID` int(20) NOT NULL,
  `CID` int(20) NOT NULL,
  `CTCRight` int(1) NOT NULL,
  `UpdateTime` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `class_teacher_course_unique_key` (`TID`,`CourseID`,`CID`) USING BTREE,
  KEY `class_teacher_course_cid` (`CID`) USING BTREE,
  KEY `class_teacher_course_courseId` (`CourseID`) USING BTREE,
  CONSTRAINT `class_teacher_coursetable_ibfk_1` FOREIGN KEY (`CID`) REFERENCES `classtable` (`CID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `class_teacher_coursetable_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `coursetable` (`CourseID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `class_teacher_coursetable_ibfk_3` FOREIGN KEY (`TID`) REFERENCES `teachertable` (`TID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of class_teacher_coursetable
-- ----------------------------
INSERT INTO `class_teacher_coursetable` VALUES ('2', '1', '1', '1', '1', '2015-08-14 02:38:55.000000');
INSERT INTO `class_teacher_coursetable` VALUES ('4', '2', '1', '1', '1', '2015-08-14 02:39:33.000000');
INSERT INTO `class_teacher_coursetable` VALUES ('5', '3', '2', '3', '1', '2015-08-14 02:40:31.000000');
INSERT INTO `class_teacher_coursetable` VALUES ('6', '1', '3', '1', '2', '2015-09-04 13:21:47.000000');
INSERT INTO `class_teacher_coursetable` VALUES ('7', '1', '7', '1', '2', '2015-09-04 13:22:09.000000');
INSERT INTO `class_teacher_coursetable` VALUES ('8', '1', '9', '2', '3', '2015-09-04 13:22:54.000000');
INSERT INTO `class_teacher_coursetable` VALUES ('9', '1', '6', '4', '3', '2015-11-11 01:53:55.000000');
INSERT INTO `class_teacher_coursetable` VALUES ('10', '1', '13', '10', '3', '2015-11-11 01:54:19.000000');

-- ----------------------------
-- Table structure for collegetable
-- ----------------------------
DROP TABLE IF EXISTS `collegetable`;
CREATE TABLE `collegetable` (
  `CeID` int(11) NOT NULL AUTO_INCREMENT COMMENT '学院ID,关键字，递增',
  `CeName` varchar(255) NOT NULL COMMENT '学院名称，可以有重复，但（UnID +CeName）唯一',
  `UnID` int(11) NOT NULL COMMENT '所在学校ID,当这个修改时,通过触发器同步其他冗余',
  `Enabled` int(11) NOT NULL COMMENT '有效状态：0停用 1正常',
  `UpdateTime` datetime NOT NULL COMMENT '记录更新时间（默认值GetDate（））',
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`CeID`),
  UNIQUE KEY `CollegeTable_unique_key` (`CeName`,`UnID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of collegetable
-- ----------------------------
INSERT INTO `collegetable` VALUES ('-1', '匿名学院', '-1', '1', '2015-08-04 14:41:07', null);
INSERT INTO `collegetable` VALUES ('1', '宠物小精灵学院', '1', '1', '2015-07-17 12:45:39', null);
INSERT INTO `collegetable` VALUES ('2', '数码宝贝学院', '1', '1', '2015-07-17 12:46:07', null);
INSERT INTO `collegetable` VALUES ('3', '巴拉拉小魔仙学院', '1', '1', '2015-08-04 16:43:20', null);
INSERT INTO `collegetable` VALUES ('4', '天魔花露水学院', '2', '1', '2015-08-28 10:33:26', null);
INSERT INTO `collegetable` VALUES ('5', '灌篮研究院', '2', '1', '2015-08-28 10:33:52', null);
INSERT INTO `collegetable` VALUES ('6', '红豆牛奶外国语学院', '3', '1', '2015-08-28 10:34:32', null);

-- ----------------------------
-- Table structure for college_teachertable
-- ----------------------------
DROP TABLE IF EXISTS `college_teachertable`;
CREATE TABLE `college_teachertable` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CeID` int(11) DEFAULT NULL COMMENT '学院ID,外键（级联）',
  `TID` int(11) DEFAULT NULL COMMENT '教师ID,外键（级联）',
  `Tiden` int(11) DEFAULT NULL COMMENT '身份：\r\n0普通教师：普通权限\r\n1学院领导：可以管理和查看全学院教师和班级\r\n2学校领导：可以管理和查看全校教师和班级\r\n',
  `Status` int(11) DEFAULT NULL COMMENT '状态：\r\n0：无效\r\n1：有效\r\n',
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `College_Teacher_CeID_TID_unique` (`CeID`,`TID`) USING BTREE,
  KEY `foreign_key_teacher` (`TID`) USING BTREE,
  CONSTRAINT `college_teachertable_ibfk_1` FOREIGN KEY (`CeID`) REFERENCES `collegetable` (`CeID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `college_teachertable_ibfk_2` FOREIGN KEY (`TID`) REFERENCES `teachertable` (`TID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of college_teachertable
-- ----------------------------
INSERT INTO `college_teachertable` VALUES ('1', '1', '1', null, '1', '2015-10-20 01:32:12');
INSERT INTO `college_teachertable` VALUES ('2', '1', '2', null, '1', '2015-10-20 01:32:28');
INSERT INTO `college_teachertable` VALUES ('3', '1', '3', null, '1', '2015-10-20 01:32:45');
INSERT INTO `college_teachertable` VALUES ('4', '2', '2', null, '1', '2015-10-20 01:32:56');
INSERT INTO `college_teachertable` VALUES ('5', '2', '4', null, '1', '2015-10-20 01:33:08');
INSERT INTO `college_teachertable` VALUES ('6', '2', '5', null, '1', '2015-10-20 01:33:19');
INSERT INTO `college_teachertable` VALUES ('7', '3', '3', null, '1', '2015-10-20 01:33:29');
INSERT INTO `college_teachertable` VALUES ('8', '3', '2', null, '1', '2015-10-20 01:33:41');
INSERT INTO `college_teachertable` VALUES ('9', '4', '7', null, '1', '2015-10-20 01:33:51');
INSERT INTO `college_teachertable` VALUES ('10', '4', '8', null, '1', '2015-10-20 01:34:01');
INSERT INTO `college_teachertable` VALUES ('11', '4', '9', null, '1', '2015-10-20 01:34:11');
INSERT INTO `college_teachertable` VALUES ('12', '1', '13', null, '1', '2015-10-26 21:02:50');
INSERT INTO `college_teachertable` VALUES ('14', '2', '13', null, '1', '2015-10-26 21:04:18');
INSERT INTO `college_teachertable` VALUES ('15', '3', '13', null, '1', '2015-10-26 21:04:35');
INSERT INTO `college_teachertable` VALUES ('16', '5', '16', null, '1', '2015-11-03 13:37:52');

-- ----------------------------
-- Table structure for coursetable
-- ----------------------------
DROP TABLE IF EXISTS `coursetable`;
CREATE TABLE `coursetable` (
  `CourseID` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程ID,关键字，递增',
  `CourseName` varchar(255) NOT NULL COMMENT '课程名称，可以重复，但（UnID+CeID+CourseName）唯一',
  `CeID` int(11) NOT NULL COMMENT '所在学院ID,可以为 -1',
  `UnID` int(11) NOT NULL COMMENT '所在学校ID, 可以为 -1',
  `Category` varchar(255) NOT NULL COMMENT '分类名称',
  `Enabled` int(11) NOT NULL COMMENT '有效状态：（0-停用 1-正常）',
  `UpdateTime` datetime NOT NULL,
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`CourseID`),
  UNIQUE KEY `CourseTable_unique_key` (`CourseName`,`CeID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of coursetable
-- ----------------------------
INSERT INTO `coursetable` VALUES ('1', '离散数学', '1', '2', '学院级课程', '1', '2015-07-30 16:38:41', null);
INSERT INTO `coursetable` VALUES ('2', 'c程序设计', '1', '1', '校级课程', '1', '2015-07-30 16:39:08', null);
INSERT INTO `coursetable` VALUES ('3', '数据结构', '-1', '-1', '公共课程', '0', '2015-08-07 16:37:20', null);
INSERT INTO `coursetable` VALUES ('6', '计算机系统结构', '3', '1', '学院级课程', '1', '2015-08-04 16:45:57', null);
INSERT INTO `coursetable` VALUES ('7', '操作系统', '-1', '1', '校内共享课程', '1', '2015-08-04 20:49:44', null);
INSERT INTO `coursetable` VALUES ('9', '美少女哈哈', '3', '1', '校级课程', '1', '2015-08-07 16:33:12', null);
INSERT INTO `coursetable` VALUES ('13', '美少女战士', '2', '1', '学院级课程', '1', '2015-08-07 15:48:20', null);
INSERT INTO `coursetable` VALUES ('16', '你爱我', '3', '1', '校级课程', '1', '2015-08-07 17:59:50', null);

-- ----------------------------
-- Table structure for customfiletable
-- ----------------------------
DROP TABLE IF EXISTS `customfiletable`;
CREATE TABLE `customfiletable` (
  `CFID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目文件ID,关键字',
  `CFName` varchar(255) DEFAULT NULL COMMENT '文件名称,唯一',
  `CPID` bigint(20) DEFAULT NULL COMMENT '所在项目目录ID，外键（NoAction），索引',
  `EContent` longtext COMMENT '文件内容，XML格式',
  `EComment` longtext COMMENT '教师评语，XML格式',
  `Kind` int(11) DEFAULT NULL COMMENT '文件类型：待定',
  `CmpStatus` int(11) DEFAULT NULL COMMENT '编译状态：\r\n0 未编译 1编译成功 2编译错误\r\n',
  `Memo` varchar(4000) DEFAULT NULL COMMENT '文件说明',
  `Score` float DEFAULT NULL COMMENT '得分',
  `UpdateTime` datetime DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`CFID`),
  UNIQUE KEY `customFile_uk_cfname` (`CFName`) USING BTREE,
  KEY `customFile_uk_cpid` (`CPID`) USING BTREE,
  CONSTRAINT `customfiletable_ibfk_1` FOREIGN KEY (`CPID`) REFERENCES `customprjtable` (`CPID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customfiletable
-- ----------------------------
INSERT INTO `customfiletable` VALUES ('8', 'update.h', '9', 'update hello', 'easy', '1', '3', 'ss', '89', '2016-07-19 13:45:30', '2016-07-15 18:32:08');
INSERT INTO `customfiletable` VALUES ('9', 'tou.hasa', '10', 'error', 'meadium', '1', '1', 'aassa', '78', '2016-07-15 18:33:03', '2016-07-15 18:33:07');
INSERT INTO `customfiletable` VALUES ('18', 'asaf', '21', null, null, null, null, null, null, null, null);
INSERT INTO `customfiletable` VALUES ('19', 'young.h', '35', 'hello world', null, null, '0', null, null, '2016-07-19 13:12:32', '2016-07-19 13:12:32');

-- ----------------------------
-- Table structure for customprjtable
-- ----------------------------
DROP TABLE IF EXISTS `customprjtable`;
CREATE TABLE `customprjtable` (
  `CPID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '项目目录ID,关键字',
  `CPName` varchar(255) DEFAULT NULL COMMENT '项目名称',
  `SID` int(11) DEFAULT NULL COMMENT '学生ID，外键（NoAction）',
  `CourseID` int(11) DEFAULT NULL COMMENT '课程ID，外键（NoAction）',
  `ParentID` int(11) DEFAULT NULL COMMENT '父项目目录ID',
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`CPID`),
  KEY `customprj_fk_courseid` (`CourseID`) USING BTREE,
  KEY `CustomPrj_uk_cpname` (`CPName`) USING BTREE,
  KEY `customprj_uk_sid_courseid_parenetid` (`SID`,`CourseID`,`ParentID`) USING BTREE,
  CONSTRAINT `customprjtable_ibfk_1` FOREIGN KEY (`CourseID`) REFERENCES `coursetable` (`CourseID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `customprjtable_ibfk_2` FOREIGN KEY (`SID`) REFERENCES `studenttable` (`SID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customprjtable
-- ----------------------------
INSERT INTO `customprjtable` VALUES ('8', 'AnyviewServer', '20', '1', '-1', '2016-07-19 13:45:30');
INSERT INTO `customprjtable` VALUES ('9', '源文件', '20', '1', '8', '2016-07-19 13:45:30');
INSERT INTO `customprjtable` VALUES ('10', '头文件', '20', '1', '8', '2016-07-15 18:31:03');
INSERT INTO `customprjtable` VALUES ('19', 'asa', null, null, '-1', null);
INSERT INTO `customprjtable` VALUES ('20', '源文件', null, null, '19', null);
INSERT INTO `customprjtable` VALUES ('21', '头文件', null, null, '19', null);
INSERT INTO `customprjtable` VALUES ('31', 'anyview', '20', '1', '-1', '2016-07-18 22:58:54');
INSERT INTO `customprjtable` VALUES ('32', '源文件', '20', '1', '31', '2016-07-18 22:58:54');
INSERT INTO `customprjtable` VALUES ('33', '头文件', '20', '1', '31', '2016-07-18 22:58:54');
INSERT INTO `customprjtable` VALUES ('34', 'anyview', '20', '1', '-1', '2016-07-18 22:59:16');
INSERT INTO `customprjtable` VALUES ('35', '源文件', '20', '1', '34', '2016-07-18 22:59:16');
INSERT INTO `customprjtable` VALUES ('36', '头文件', '20', '1', '34', '2016-07-18 22:59:16');

-- ----------------------------
-- Table structure for examplantable
-- ----------------------------
DROP TABLE IF EXISTS `examplantable`;
CREATE TABLE `examplantable` (
  `EPID` int(11) NOT NULL AUTO_INCREMENT,
  `EPName` varchar(255) DEFAULT NULL COMMENT '考试名称',
  `TID` int(11) DEFAULT NULL COMMENT '创建教师ID。只有创建者可以看到该编排记录。当多个考试编排ID发生冲突时，以权限高的为标准，外键（级联）',
  `CID` int(11) DEFAULT NULL COMMENT '班级ID，外键（级联）',
  `CourseID` int(11) DEFAULT NULL COMMENT '课程ID，外键（级联）',
  `VID` int(11) DEFAULT NULL COMMENT '考试计划表ID，外键（级联）',
  `Duration` int(11) DEFAULT NULL COMMENT '考试持续时间,以分钟为单位',
  `StartTime` datetime DEFAULT NULL COMMENT '考试开始时间\r\n类型为自动，此为开始时间；\r\n类型为手动，此为手动开始后填写的时间。\r\n',
  `Status` int(11) DEFAULT NULL COMMENT '状态：0未使用 1中止 2完成',
  `Kind` int(11) DEFAULT NULL COMMENT '类型：0手动 1自动',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL,
  `automaticStartFlag` int(11) DEFAULT NULL COMMENT '考试自动开始标识，0不允许，1允许',
  `automaticEndFlag` int(11) DEFAULT NULL COMMENT '考试自动结束标识， 0不允许，1允许',
  PRIMARY KEY (`EPID`),
  UNIQUE KEY `unique` (`EPName`,`TID`) USING BTREE,
  KEY `teacher` (`TID`) USING BTREE,
  KEY `class` (`CID`) USING BTREE,
  KEY `course` (`CourseID`) USING BTREE,
  KEY `scheme` (`VID`) USING BTREE,
  CONSTRAINT `examplantable_ibfk_1` FOREIGN KEY (`CID`) REFERENCES `classtable` (`CID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `examplantable_ibfk_2` FOREIGN KEY (`CourseID`) REFERENCES `coursetable` (`CourseID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `examplantable_ibfk_3` FOREIGN KEY (`VID`) REFERENCES `schemetable` (`VID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `examplantable_ibfk_4` FOREIGN KEY (`TID`) REFERENCES `teachertable` (`TID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of examplantable
-- ----------------------------
INSERT INTO `examplantable` VALUES ('6', '111', '1', '1', '1', '1', '111', '2015-11-20 01:23:51', '2', '1', '2015-11-20 01:23:59', null, null, null);
INSERT INTO `examplantable` VALUES ('10', '高数考试第一次改', '1', '1', '1', '1', '23', '2015-11-24 02:09:29', '2', '0', '2015-11-16 20:03:25', '2015-11-17 23:41:49', null, null);
INSERT INTO `examplantable` VALUES ('11', '高数考试第二次改', '1', '1', '1', '1', '23', '2015-11-24 02:08:57', '2', '0', '2015-11-16 20:03:48', '2015-11-17 22:14:50', null, null);
INSERT INTO `examplantable` VALUES ('12', '222', '1', '1', '1', '1', '23', '2015-11-24 02:09:41', '2', '1', '2015-11-16 21:01:50', null, null, null);
INSERT INTO `examplantable` VALUES ('13', '测试任务1', '1', '1', '1', '1', '1', null, '1', '0', '2015-11-17 19:39:09', '2015-11-17 22:15:05', null, null);
INSERT INTO `examplantable` VALUES ('14', '测试考试任务2', '1', '1', '1', '1', '1', null, '1', '0', '2015-11-17 19:48:24', '2015-11-19 04:29:01', null, null);
INSERT INTO `examplantable` VALUES ('17', '测试修改1改', '1', '1', '1', '2', '1', null, '2', '0', '2015-11-17 21:50:29', '2015-11-17 21:51:06', null, null);
INSERT INTO `examplantable` VALUES ('18', '测试手动类型考试自动结束', '1', '1', '1', '1', '1', null, '2', '0', '2015-11-24 01:48:59', '2015-11-24 01:49:08', null, null);
INSERT INTO `examplantable` VALUES ('19', '测试中止考试1', '1', '1', '1', '1', '1', '2015-11-29 20:42:49', '1', '0', '2015-11-29 20:42:37', '2015-11-29 20:43:01', '1', '0');

-- ----------------------------
-- Table structure for exercisetable
-- ----------------------------
DROP TABLE IF EXISTS `exercisetable`;
CREATE TABLE `exercisetable` (
  `EID` bigint(11) NOT NULL AUTO_INCREMENT,
  `SID` int(11) NOT NULL,
  `VID` int(11) NOT NULL,
  `PID` int(11) NOT NULL,
  `CID` int(11) DEFAULT NULL,
  `EContent` text,
  `EComment` text,
  `AccumTime` int(5) DEFAULT NULL,
  `Score` float(5,2) DEFAULT NULL,
  `RunResult` int(1) DEFAULT NULL,
  `RunErrCount` int(5) DEFAULT NULL,
  `CmpCount` int(5) DEFAULT NULL,
  `CmpRightCount` int(5) DEFAULT NULL,
  `CmpErrorCount` int(5) DEFAULT NULL,
  `FirstPastTime` datetime(6) DEFAULT NULL,
  `LastTime` datetime(6) DEFAULT NULL,
  `UpdateTime` datetime(6) DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`EID`),
  KEY `exercise_fk_sid` (`SID`) USING BTREE,
  KEY `exercise_fk_pid` (`PID`) USING BTREE,
  KEY `exercise_fk_vid` (`VID`) USING BTREE,
  KEY `exercise_fk_cid` (`CID`) USING BTREE,
  CONSTRAINT `exercisetable_ibfk_1` FOREIGN KEY (`CID`) REFERENCES `classtable` (`CID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `exercisetable_ibfk_2` FOREIGN KEY (`PID`) REFERENCES `problemtable` (`PID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `exercisetable_ibfk_3` FOREIGN KEY (`SID`) REFERENCES `studenttable` (`SID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `exercisetable_ibfk_4` FOREIGN KEY (`VID`) REFERENCES `schemetable` (`VID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exercisetable
-- ----------------------------
INSERT INTO `exercisetable` VALUES ('1', '2', '1', '2', '1', 'answer1', null, '1', '1.00', '1', null, null, null, null, null, null, null, null);
INSERT INTO `exercisetable` VALUES ('2', '2', '1', '3', '1', 'answer2', null, '2', '2.00', '0', null, null, null, null, null, null, null, null);
INSERT INTO `exercisetable` VALUES ('3', '2', '1', '4', '1', 'answer3', null, '3', '3.00', '1', null, null, null, null, null, null, null, null);
INSERT INTO `exercisetable` VALUES ('4', '4', '2', '5', '2', null, null, '4', null, '0', null, null, null, null, null, null, null, null);
INSERT INTO `exercisetable` VALUES ('5', '4', '2', '8', '2', null, null, '5', null, '1', null, null, null, null, null, null, null, null);
INSERT INTO `exercisetable` VALUES ('6', '19', '1', '2', '1', null, null, '6', null, '0', null, null, null, null, null, null, null, null);
INSERT INTO `exercisetable` VALUES ('7', '20', '1', '3', '1', null, null, '7', null, '1', null, null, null, null, null, null, null, null);
INSERT INTO `exercisetable` VALUES ('8', '19', '1', '5', '1', null, null, '8', null, '0', null, null, null, null, null, null, null, null);
INSERT INTO `exercisetable` VALUES ('9', '2', '5', '17', '5', null, null, '9', null, '1', null, null, null, null, null, null, null, null);
INSERT INTO `exercisetable` VALUES ('10', '4', '2', '2', '1', null, null, '10', null, '1', null, null, null, null, null, null, null, null);

-- ----------------------------
-- Table structure for exercisetracktable
-- ----------------------------
DROP TABLE IF EXISTS `exercisetracktable`;
CREATE TABLE `exercisetracktable` (
  `ETID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '作业跟踪记录ID,关键字',
  `EID` bigint(20) DEFAULT NULL COMMENT '学生作业答案ID，外键（级联），索引',
  `EContent` longtext COMMENT '答案内容,采用XML格式',
  `Kind` int(11) DEFAULT NULL COMMENT '类型',
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ETID`),
  KEY `exercisetrack_fk_eid` (`EID`) USING BTREE,
  CONSTRAINT `exercisetracktable_ibfk_1` FOREIGN KEY (`EID`) REFERENCES `exercisetable` (`EID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exercisetracktable
-- ----------------------------

-- ----------------------------
-- Table structure for managertable
-- ----------------------------
DROP TABLE IF EXISTS `managertable`;
CREATE TABLE `managertable` (
  `MID` int(11) NOT NULL AUTO_INCREMENT COMMENT '管理员ID,关键字，递增',
  `MNo` varchar(255) NOT NULL COMMENT '管理员编号（登录名）,唯一索引( UnID +MNo)',
  `MPsw` varbinary(255) NOT NULL COMMENT '登录密码，（MID+原密码）的MD5值*',
  `CeID` int(11) NOT NULL COMMENT '所在学院ID, 学校管理员放在专用学院中',
  `UnID` int(11) NOT NULL COMMENT '所在学校ID，冗余，为加快登录查询速度',
  `MIden` int(11) NOT NULL COMMENT '身份：\r\n0学院管理员：可以管理全学院教师和班级\r\n1学校管理员：可以管理全校教师和班级\r\n-1超级管理员：可以管理所有学校，CeID和UnID=-1，为保证安全，作其他特殊处理\r\n',
  `Enabled` int(11) NOT NULL COMMENT '有效状态：0 停用 1正常',
  `UpdateTime` datetime DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`MID`),
  UNIQUE KEY `ManagerTable_unique_key` (`MNo`,`UnID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of managertable
-- ----------------------------
INSERT INTO `managertable` VALUES ('1', '2015001', '', '-1', '-1', '-1', '1', '2015-07-28 20:09:57', null);
INSERT INTO `managertable` VALUES ('2', '2015002', '', '-1', '1', '1', '1', '2015-07-29 11:33:59', null);
INSERT INTO `managertable` VALUES ('3', '2015003', '', '3', '1', '0', '1', '2015-07-30 15:14:49', null);
INSERT INTO `managertable` VALUES ('4', '1', '', '-1', '-1', '-1', '1', '2015-08-05 15:55:34', '2015-10-17 11:19:44');
INSERT INTO `managertable` VALUES ('5', '2015004', '', '-1', '1', '-1', '1', '2015-08-06 00:51:45', null);
INSERT INTO `managertable` VALUES ('8', '7777888', 0x4336463645443137453734364433313342443642373343373844303645343630424535444332313531323043354242444636374439304442, '5', '2', '1', '1', null, '2015-10-17 11:13:06');
INSERT INTO `managertable` VALUES ('9', '133333', 0x4144394441303936343441453937334446383541413333423938373537414345394342453136333238373330464536353834364236453944, '4', '2', '0', '0', '2015-10-17 16:24:50', '2015-10-17 11:43:08');
INSERT INTO `managertable` VALUES ('10', '4545454545', 0x4537374245374639393137424237424230433842424235324234413930363041414630453830443933333636424645334535333636433938, '1', '1', '0', '1', '2015-10-17 18:25:15', '2015-10-17 16:25:57');
INSERT INTO `managertable` VALUES ('11', '123456', 0x3636453043303641443341333042323345413236463345384541393836363130384637393641313031364236303437324530333039323141, '6', '3', '0', '1', null, '2015-10-18 00:54:00');
INSERT INTO `managertable` VALUES ('12', '11', 0x4343453732434139344144384630343733334332313137324342304242304434324638414239434244363046384536374444434533363044, '-1', '1', '1', '1', null, '2015-10-19 19:30:26');
INSERT INTO `managertable` VALUES ('13', '111', 0x3335424333303732393039343341394637453341353342313035413737334537463443383444443135373131423531314244303142323245, '1', '1', '0', '1', null, '2015-11-06 17:00:46');
INSERT INTO `managertable` VALUES ('14', '3333', 0x4144343130443238353441463239393131423836413242343530383533414536463736384243343342464235304335373137393544423343, '2', '1', '0', '1', null, '2015-11-06 17:00:57');
INSERT INTO `managertable` VALUES ('15', '5555', 0x4635453733463544443236444635464639464238383744324237364131424631383345413330463637333745353230393442354230463132, '1', '1', '0', '1', null, '2015-11-06 17:01:11');
INSERT INTO `managertable` VALUES ('16', '555555555', 0x3544423632303635384646333139413342413535394146323930363441423734363641393142414431334535323437303336343634364543, '-1', '7', '1', '1', null, '2015-11-06 17:01:33');

-- ----------------------------
-- Table structure for manager_teachertable
-- ----------------------------
DROP TABLE IF EXISTS `manager_teachertable`;
CREATE TABLE `manager_teachertable` (
  `MID` int(11) NOT NULL,
  `TID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of manager_teachertable
-- ----------------------------
INSERT INTO `manager_teachertable` VALUES ('1', '1');
INSERT INTO `manager_teachertable` VALUES ('1', '2');

-- ----------------------------
-- Table structure for problemchaptable
-- ----------------------------
DROP TABLE IF EXISTS `problemchaptable`;
CREATE TABLE `problemchaptable` (
  `ChID` int(11) NOT NULL AUTO_INCREMENT COMMENT '题目目录ID,关键字，递增',
  `ChName` varchar(255) DEFAULT NULL COMMENT '题目目录名称,可以有重复,但（LID+ParentID+ChName）唯一',
  `LID` int(11) DEFAULT NULL COMMENT '所在题库ID，外键（NoAction）',
  `ParentID` int(11) DEFAULT NULL COMMENT '父目录ID，最上层为-1',
  `Memo` varchar(255) DEFAULT NULL COMMENT '说明',
  `Visit` int(11) DEFAULT NULL COMMENT '访问级别：0私有 1公开',
  `UpdateTime` datetime DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ChID`),
  UNIQUE KEY `ProblemChapTable_unique_key` (`ChName`,`LID`,`ParentID`) USING BTREE,
  KEY `ProblemChapTable_lid` (`LID`) USING BTREE,
  KEY `ProblemChapTable_parentId` (`ParentID`) USING BTREE,
  CONSTRAINT `problemchaptable_ibfk_1` FOREIGN KEY (`LID`) REFERENCES `problemlibtable` (`LID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `problemchaptable_ibfk_2` FOREIGN KEY (`ParentID`) REFERENCES `problemchaptable` (`ChID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of problemchaptable
-- ----------------------------
INSERT INTO `problemchaptable` VALUES ('-1', '根目录', null, null, null, '1', null, null);
INSERT INTO `problemchaptable` VALUES ('1', '第一章', '1', '-1', '防辐射的分手房顶上', '1', '2015-08-12 22:00:01', null);
INSERT INTO `problemchaptable` VALUES ('2', '第二章', '1', '-1', '范德萨范德萨哥哥', '1', '2015-08-12 22:00:51', null);
INSERT INTO `problemchaptable` VALUES ('3', '第一节', '1', '1', '凤飞飞凤飞飞', '1', '2015-08-12 22:01:26', null);
INSERT INTO `problemchaptable` VALUES ('4', '第二节', '1', '1', '任天堂天天', '1', '2015-08-12 22:01:47', null);
INSERT INTO `problemchaptable` VALUES ('5', '2.1', '1', '2', 'uuu', '0', '2015-08-28 11:25:07', null);
INSERT INTO `problemchaptable` VALUES ('6', '2.2', '1', '2', 'uuuy', '1', '2015-08-28 11:25:30', null);
INSERT INTO `problemchaptable` VALUES ('7', '1', '4', '-1', '135', '1', '2015-08-28 11:26:36', null);
INSERT INTO `problemchaptable` VALUES ('8', '2', '4', '-1', '也一样', '0', '2015-08-28 11:26:54', null);
INSERT INTO `problemchaptable` VALUES ('9', '第一小节', '1', '3', '发反反复复', '1', '2015-08-31 16:16:50', null);
INSERT INTO `problemchaptable` VALUES ('10', '第二小节', '1', '3', '飞凤飞飞柔柔弱弱', '0', '2015-08-31 16:17:13', null);
INSERT INTO `problemchaptable` VALUES ('11', '第一小节', '1', '4', '急急急', '1', '2015-08-31 16:17:37', null);
INSERT INTO `problemchaptable` VALUES ('12', '奥特曼防线A', '2', '-1', '这里是奥特曼', '1', '2015-09-08 01:51:20', null);
INSERT INTO `problemchaptable` VALUES ('13', '奥特曼防线B', '2', '-1', '这里是奥特曼', '1', '2015-09-08 01:51:55', null);
INSERT INTO `problemchaptable` VALUES ('14', 'bbbbb', '1', '-1', 'aaaa12311', '1', '2015-10-25 11:54:15', '2015-10-23 00:30:17');
INSERT INTO `problemchaptable` VALUES ('15', 'bbbb', '1', '-1', 'bbbbb', '1', null, '2015-10-23 00:31:25');
INSERT INTO `problemchaptable` VALUES ('17', '4444', '1', '1', '4444', '0', null, '2015-10-23 00:31:59');
INSERT INTO `problemchaptable` VALUES ('19', '123', '1', '14', '123', '1', null, '2015-10-23 00:34:53');
INSERT INTO `problemchaptable` VALUES ('31', 'ssss', '1', '15', 'sss', '1', null, '2015-10-24 17:21:46');
INSERT INTO `problemchaptable` VALUES ('35', 'sssss123', '1', '15', 'jhhhh', '1', '2015-10-25 11:54:39', '2015-10-25 11:54:32');

-- ----------------------------
-- Table structure for problemlibtable
-- ----------------------------
DROP TABLE IF EXISTS `problemlibtable`;
CREATE TABLE `problemlibtable` (
  `LID` int(11) NOT NULL AUTO_INCREMENT COMMENT '题库ID,关键字',
  `LName` varchar(255) DEFAULT NULL COMMENT '题库名称，可以重复，但（TID +LName）唯一',
  `TID` int(11) DEFAULT NULL COMMENT '创建教师ID',
  `UnID` int(11) DEFAULT NULL COMMENT '创建教师所在学校ID,冗余,加快查询,通过触发器同步，索引',
  `Visit` int(11) DEFAULT NULL COMMENT '访问级别：(0私有 1部分公开 2本学院公开 3 本校公开 4 完全公开)注:1部分公开 见”题库-教师访问权限表”\r\n访问级别：\r\na．私有：只有创建者本人可见\r\nb．部分公开：只有添加到题库-教师访问权限表中的教师能看到（只读）\r\nc．本学院公开：与创建者属同一个学院的所有教师都可见（只读）\r\nd．本学校公开：与创建者属同一所学校的所有教师都可见（只读）\r\ne．完全公开：在本服务器中的所有学校的教师都可见（只读）\r\nf．除了创建者，其他人都只有只读权限，没有增删改的权限\r\n',
  `Kind` varchar(255) DEFAULT NULL COMMENT '类别\r\n题库类别：为便于题库跨课程用，这里没有规定题库属于某一课程，不过可以在类别这里进行提示',
  `UpdateTime` datetime DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`LID`),
  UNIQUE KEY `ProblemLibTable_unique_key` (`LName`,`TID`) USING BTREE,
  KEY `ProblemLibTable_tid` (`TID`) USING BTREE,
  KEY `ProblemLibTable_unid` (`UnID`) USING BTREE,
  CONSTRAINT `problemlibtable_ibfk_1` FOREIGN KEY (`TID`) REFERENCES `teachertable` (`TID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `problemlibtable_ibfk_2` FOREIGN KEY (`UnID`) REFERENCES `universitytable` (`UnID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of problemlibtable
-- ----------------------------
INSERT INTO `problemlibtable` VALUES ('1', '题库一', '1', '1', '0', '私有的', '2015-08-03 21:37:33', null);
INSERT INTO `problemlibtable` VALUES ('2', '题库二', '2', '1', '3', '本校公开的', '2015-08-03 21:38:22', null);
INSERT INTO `problemlibtable` VALUES ('3', '题库三', '3', '1', '0', '私有的', '2015-08-28 10:24:33', null);
INSERT INTO `problemlibtable` VALUES ('4', '题库四', '4', '1', '2', '本院公开的', '2015-08-28 10:38:20', null);
INSERT INTO `problemlibtable` VALUES ('5', '题库五', '15', '2', '4', '完全公开的', '2015-08-28 10:39:49', null);
INSERT INTO `problemlibtable` VALUES ('6', '题库6', '16', '2', '0', '私有的', '2015-08-28 10:40:53', null);
INSERT INTO `problemlibtable` VALUES ('7', '更新111', '1', '1', '4', '测试', '2015-09-17 16:00:23', null);
INSERT INTO `problemlibtable` VALUES ('8', '坑哦哦哦', '1', '1', '3', '谁谁谁', '2015-10-20 01:40:46', null);

-- ----------------------------
-- Table structure for problemlib_teachertable
-- ----------------------------
DROP TABLE IF EXISTS `problemlib_teachertable`;
CREATE TABLE `problemlib_teachertable` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `TID` int(11) DEFAULT NULL COMMENT '教师ID,关键字，外键（级联）',
  `LID` int(11) DEFAULT NULL COMMENT '题库ID,关键字，外键（级联）',
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `unique` (`TID`,`LID`) USING BTREE,
  KEY `ProblemLib_TeacherTable_lid` (`LID`) USING BTREE,
  CONSTRAINT `problemlib_teachertable_ibfk_1` FOREIGN KEY (`LID`) REFERENCES `problemlibtable` (`LID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `problemlib_teachertable_ibfk_2` FOREIGN KEY (`TID`) REFERENCES `teachertable` (`TID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of problemlib_teachertable
-- ----------------------------
INSERT INTO `problemlib_teachertable` VALUES ('1', '1', '6', '2015-08-28 10:41:11');

-- ----------------------------
-- Table structure for problemtable
-- ----------------------------
DROP TABLE IF EXISTS `problemtable`;
CREATE TABLE `problemtable` (
  `PID` int(11) NOT NULL AUTO_INCREMENT COMMENT '题目ID,关键字',
  `PName` varchar(255) DEFAULT NULL COMMENT '题目名称,可以有重复,但（ChID+PName）唯一',
  `ChID` int(11) DEFAULT NULL COMMENT '所在目录ID,必须存在，外键（NoAction）',
  `Degree` float DEFAULT NULL COMMENT '难度',
  `Kind` int(11) DEFAULT NULL COMMENT '题目类型:\r\n0：程序题      3：单项选择题\r\n1：例题        4：多项选择题\r\n2：填空题      5：判断题\r\n',
  `Status` int(11) DEFAULT NULL COMMENT '状态：（0停用 1测试 2正式）\r\n注：在题库和题目目录公开的情况下，只有状态为正式的题目才能被其他老师访问\r\n',
  `Visit` int(11) DEFAULT NULL COMMENT '访问级别：0 私有 1公开',
  `PMemo` text COMMENT '备注，题目简介',
  `PTip` text COMMENT '提示 （采用XML格式保存数据）',
  `PContent` text COMMENT '题目内容(采用XML格式保存数据)\r\n数据结构题目包括：\r\n1.	主文件名和主文件内容\r\n2.	答案文件名和答案文件内容\r\n3.	用户文件名和用户文件头\r\n4.	零个或多个（头文件名+头文件内容）\r\n5.	题目内容和文档\r\n6.	题目初始化设置\r\n',
  `CacheSync` tinyint(4) DEFAULT NULL COMMENT '缓存同步状态：0未同步 1已同步',
  `Cache` varbinary(255) DEFAULT NULL COMMENT '题目缓存，非XML格式，用于加快应用程序速度，由应用程序生成。',
  `UpdateTime` datetime DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`PID`),
  UNIQUE KEY `ProblemTable_unique_key` (`PName`,`ChID`) USING BTREE,
  KEY `ProblemTable_chid` (`ChID`) USING BTREE,
  CONSTRAINT `problemtable_ibfk_1` FOREIGN KEY (`ChID`) REFERENCES `problemchaptable` (`ChID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of problemtable
-- ----------------------------
INSERT INTO `problemtable` VALUES ('2', '第二题', '2', '3', '2', '2', '1', '丰盛饭店', '否的诉讼费地方', 'ffff', '0', null, '2015-08-12 22:05:00', null);
INSERT INTO `problemtable` VALUES ('3', '第三题', '3', '5', '2', '2', '1', '灌灌灌灌灌', '哈哈哈', 'fffff', '0', null, '2015-08-12 22:05:31', null);
INSERT INTO `problemtable` VALUES ('4', '第四题', '4', '4', '1', '1', '1', '烦烦烦', '嘎嘎嘎嘎', '', '0', null, '2015-08-12 22:05:56', null);
INSERT INTO `problemtable` VALUES ('5', '奥特曼考试A', '12', '3.5', '3', '2', '1', '呵呵哒', '狮子连弹', '如果我是奥特你会爱我吗', '0', null, '2015-09-08 01:53:12', null);
INSERT INTO `problemtable` VALUES ('8', '最后测试一下', '31', '222', '4', '0', '0', '4444', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ptip> 冯绍峰松岛枫多舒服</ptip>', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<choice><choiceContent>飞凤飞飞凤飞飞凤飞飞凤飞飞凤飞飞凤飞飞凤飞飞凤飞飞凤飞飞</choiceContent><options><option isRight=\"false\"><sequence>A</sequence><optContent>22222211</optContent></option><option isRight=\"true\"><sequence>B</sequence><optContent>放松放松</optContent></option><option isRight=\"true\"><sequence>C</sequence><optContent>冯绍峰是双方都</optContent></option><option isRight=\"false\"><sequence>D</sequence><optContent> 发松岛枫飞 复试</optContent></option></options></choice>', null, null, null, '2015-11-03 03:50:57');
INSERT INTO `problemtable` VALUES ('15', '1111', '1', '1', '0', '1', '1', '1111', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ptip>1111111</ptip>', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<program><mainFile fileName=\"111\">11111</mainFile><userFile fileName=\"222\">2222</userFile><answerFile fileName=\"555\">5555</answerFile><programContent>555555</programContent><document>666666</document><headFiles><headFile fileName=\"44444\">44444</headFile><headFile fileName=\"33\">3333</headFile></headFiles></program>', null, null, null, '2015-11-06 16:35:30');
INSERT INTO `problemtable` VALUES ('17', 'singleChoiceone', null, '99', '0', '2', '1', '111111', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ptip>wrewre</ptip>', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<program><mainFile fileName=\"rrr\">rrrrr</mainFile><userFile fileName=\"eee\">eee</userFile><answerFile fileName=\"rer\">er</answerFile><programContent>eree</programContent><document>rwerewr</document><headFiles><headFile fileName=\"hhh\">hhhh</headFile><headFile fileName=\"ggg\">ggggg</headFile></headFiles></program>', null, null, '2015-11-08 16:28:12', '2015-11-06 18:38:43');
INSERT INTO `problemtable` VALUES ('18', '额鹅鹅鹅', '1', '3', '0', '0', '0', '嘎嘎嘎嘎', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<ptip>鬼地方个梵蒂冈房顶上</ptip>', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<program><mainFile fileName=\"过的法国反对\">个地方 </mainFile><userFile fileName=\"个地方个地方\">广东的个广东</userFile><answerFile fileName=\"个蛋糕蛋糕的\">个蛋糕发的个地方 </answerFile><programContent>个蛋糕个的个</programContent><document> 的广泛地地方个广东飞 </document><headFiles><headFile fileName=\"个地方个地方\"> 地方个地方广东</headFile><headFile fileName=\"广东个地方\"> 当官的盖饭大哥哥</headFile></headFiles></program>', null, null, '2015-11-08 17:07:51', '2015-11-06 21:15:50');

-- ----------------------------
-- Table structure for schemecachetable
-- ----------------------------
DROP TABLE IF EXISTS `schemecachetable`;
CREATE TABLE `schemecachetable` (
  `VID` int(11) NOT NULL COMMENT '作业表ID,作为缓存ID，关键字',
  `Cache` varbinary(255) DEFAULT NULL COMMENT '缓存内容，非XML格式',
  `CacheXML` longtext COMMENT 'XML格式缓存内容',
  `SyncFlag` int(11) DEFAULT NULL COMMENT '是否已同步更新：0未同步 1同步',
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`VID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schemecachetable
-- ----------------------------

-- ----------------------------
-- Table structure for schemecontenttable
-- ----------------------------
DROP TABLE IF EXISTS `schemecontenttable`;
CREATE TABLE `schemecontenttable` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '中间表逻辑ID，Hibernate映射需要',
  `VID` int(11) DEFAULT NULL COMMENT '作业计划ID,关键字，外键（级联）',
  `PID` int(11) DEFAULT NULL COMMENT '题目ID，关键字，外键（级联）',
  `VPName` varchar(120) DEFAULT NULL COMMENT '新的题目名,VID+目录+题目名唯一',
  `VChapName` varchar(120) DEFAULT NULL COMMENT '虚拟目录名',
  `Status` int(11) DEFAULT NULL COMMENT '状态：0停用 1启用',
  `Score` float DEFAULT NULL COMMENT '分值',
  `StartTime` datetime DEFAULT NULL COMMENT '允许开始时间',
  `FinishTime` datetime DEFAULT NULL COMMENT '要求完成时间',
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `SchemeContentTable_unique_key` (`VID`,`VPName`,`VChapName`) USING BTREE,
  UNIQUE KEY `unique` (`VID`,`PID`) USING BTREE,
  KEY `SchemeContentTable_pid` (`PID`) USING BTREE,
  CONSTRAINT `schemecontenttable_ibfk_1` FOREIGN KEY (`PID`) REFERENCES `problemtable` (`PID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `schemecontenttable_ibfk_2` FOREIGN KEY (`VID`) REFERENCES `schemetable` (`VID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schemecontenttable
-- ----------------------------
INSERT INTO `schemecontenttable` VALUES ('4', '17', '2', '那你呢', '跳跳糖', null, '55', '2015-09-05 12:26:27', '2015-09-05 12:26:29', '2015-09-05 12:26:54');
INSERT INTO `schemecontenttable` VALUES ('7', '18', '2', '打豆豆', '哇哇哇', null, '30', '2015-09-05 12:37:29', '2015-09-26 12:37:31', '2015-09-05 12:37:52');
INSERT INTO `schemecontenttable` VALUES ('10', '21', '2', '毒贩夫妇', '毒贩夫妇', null, '10', '2015-09-05 14:35:38', '2015-09-19 14:35:39', '2015-09-05 14:37:06');
INSERT INTO `schemecontenttable` VALUES ('11', '21', '3', '嘎 嘎嘎', '嘎嘎嘎', null, '60', '2015-09-05 14:36:06', '2015-09-05 14:36:08', '2015-09-05 14:37:06');
INSERT INTO `schemecontenttable` VALUES ('16', '22', '2', '复仇者B题strong', '复仇者家园aaa', null, '100', '2015-09-09 20:15:40', '2015-09-09 20:15:41', '2015-09-09 20:15:49');
INSERT INTO `schemecontenttable` VALUES ('17', '1', '2', 'kkk', 'kkkk', '1', '99', '2015-12-08 01:03:10', '2015-12-08 01:03:14', '2016-07-09 22:04:19');
INSERT INTO `schemecontenttable` VALUES ('18', '1', '3', 'tttt', 'tttt', '1', '11', '2015-12-08 01:03:33', '2015-12-08 01:03:36', '2016-07-08 22:04:30');
INSERT INTO `schemecontenttable` VALUES ('19', '1', '4', 'yyy', 'yyyy', '1', '1111', '2015-12-08 01:03:52', '2015-12-08 01:03:54', '2016-07-03 22:04:34');
INSERT INTO `schemecontenttable` VALUES ('20', '1', '5', 'tttt', 'ttttt', '1', '333', '2015-12-08 01:04:11', '2015-12-08 01:04:13', '2016-07-09 22:04:39');

-- ----------------------------
-- Table structure for schemetable
-- ----------------------------
DROP TABLE IF EXISTS `schemetable`;
CREATE TABLE `schemetable` (
  `VID` int(11) NOT NULL AUTO_INCREMENT COMMENT '作业计划ID',
  `TID` int(11) NOT NULL COMMENT '创建教师ID，外键（NoAction）',
  `VName` varchar(255) NOT NULL COMMENT '计划名称,可以有重复,但（TID+VName）唯一',
  `CourseID` int(11) NOT NULL COMMENT '课程ID，外键（NoAction',
  `Kind` int(11) NOT NULL COMMENT '作业计划类型:\r\n0：作业题\r\n1：考试题\r\n2：资源表\r\n',
  `Status` int(11) NOT NULL COMMENT '状态：（0停用 1测试 2正式）',
  `Visit` int(11) NOT NULL COMMENT '访问级别 (0私有 1部分公开 2本学院公开 3 本校公开 4 完全公开)注:1部分公开 见”作业表-教师访问权限表”',
  `FullScore` float NOT NULL COMMENT '满分值',
  `UpdateTime` datetime DEFAULT NULL,
  `CreateTime` datetime DEFAULT NULL,
  `totalNum` int(11) DEFAULT NULL COMMENT '总题数',
  PRIMARY KEY (`VID`),
  UNIQUE KEY `SchemeTable_unique_key` (`TID`,`VName`) USING BTREE,
  KEY `SchemeTable_courseid` (`CourseID`) USING BTREE,
  CONSTRAINT `schemetable_ibfk_1` FOREIGN KEY (`CourseID`) REFERENCES `coursetable` (`CourseID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `schemetable_ibfk_2` FOREIGN KEY (`TID`) REFERENCES `teachertable` (`TID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schemetable
-- ----------------------------
INSERT INTO `schemetable` VALUES ('1', '1', '作业表一', '1', '0', '2', '4', '100', '2015-07-30 16:43:18', null, '10');
INSERT INTO `schemetable` VALUES ('2', '2', '作业表二', '2', '1', '2', '4', '100', '2015-07-30 16:44:20', null, null);
INSERT INTO `schemetable` VALUES ('3', '1', '作业表三', '1', '1', '1', '0', '99', '2015-08-05 01:43:30', null, null);
INSERT INTO `schemetable` VALUES ('4', '2', '作业表四', '1', '0', '0', '0', '77', '2015-08-05 01:44:27', null, null);
INSERT INTO `schemetable` VALUES ('5', '1', '作业表五', '2', '1', '1', '1', '22', '2015-08-05 01:45:07', null, null);
INSERT INTO `schemetable` VALUES ('6', '3', '作业表六', '2', '1', '2', '1', '55', '2015-08-05 01:55:15', null, null);
INSERT INTO `schemetable` VALUES ('7', '1', '作业表七', '2', '2', '2', '2', '100', '2015-08-05 01:59:16', null, null);
INSERT INTO `schemetable` VALUES ('8', '4', '作业表八', '1', '2', '1', '3', '100', '2015-08-05 01:59:51', null, null);
INSERT INTO `schemetable` VALUES ('9', '3', '作业表九', '2', '2', '1', '3', '100', '2015-08-05 02:00:19', null, null);
INSERT INTO `schemetable` VALUES ('10', '1', '作业表十000', '9', '2', '2', '4', '20', '2015-09-05 14:15:27', null, null);
INSERT INTO `schemetable` VALUES ('11', '2', '作业表十一', '2', '2', '2', '1', '100', '2015-08-05 02:01:04', null, null);
INSERT INTO `schemetable` VALUES ('17', '1', '55555', '3', '0', '0', '0', '55', '2015-09-09 21:26:52', null, null);
INSERT INTO `schemetable` VALUES ('18', '1', '嘎嘎嘎嘎', '7', '0', '0', '0', '50', '2015-09-05 12:37:52', null, null);
INSERT INTO `schemetable` VALUES ('19', '1', '事实上事实上', '9', '1', '2', '1', '22', '2015-09-05 13:24:14', null, null);
INSERT INTO `schemetable` VALUES ('21', '1', '测试NNN', '9', '2', '2', '4', '70', '2015-09-09 21:13:36', null, null);
INSERT INTO `schemetable` VALUES ('22', '1', '复仇者考试题', '9', '1', '2', '3', '1300', '2015-09-09 20:15:49', null, null);

-- ----------------------------
-- Table structure for scheme_teachertable
-- ----------------------------
DROP TABLE IF EXISTS `scheme_teachertable`;
CREATE TABLE `scheme_teachertable` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `TID` int(11) DEFAULT NULL COMMENT '教师ID,关键字，外键（级联）',
  `VID` int(11) DEFAULT NULL COMMENT '作业表ID,关键字，外键（级联）',
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `unique` (`TID`,`VID`) USING BTREE,
  KEY `Scheme_TeacherTable_vid` (`VID`) USING BTREE,
  CONSTRAINT `scheme_teachertable_ibfk_1` FOREIGN KEY (`TID`) REFERENCES `teachertable` (`TID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `scheme_teachertable_ibfk_2` FOREIGN KEY (`VID`) REFERENCES `schemetable` (`VID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of scheme_teachertable
-- ----------------------------
INSERT INTO `scheme_teachertable` VALUES ('1', '1', '1', '2015-11-12 16:33:54');
INSERT INTO `scheme_teachertable` VALUES ('2', '1', '8', '2015-11-12 16:34:46');

-- ----------------------------
-- Table structure for scoretable
-- ----------------------------
DROP TABLE IF EXISTS `scoretable`;
CREATE TABLE `scoretable` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID,关键字',
  `SID` int(11) DEFAULT NULL COMMENT '学生ID,外键（级联）',
  `VID` int(11) DEFAULT NULL COMMENT '作业计划表ID,外键（级联）',
  `CID` int(11) DEFAULT NULL COMMENT '班级ID,外键（级联）',
  `UpdateTime` datetime DEFAULT NULL,
  `rank` int(11) DEFAULT NULL COMMENT '排名',
  `score` float DEFAULT NULL COMMENT '得分，即成绩',
  `totalTime` int(11) DEFAULT NULL COMMENT '总用时（分钟）',
  `passNum` int(11) DEFAULT NULL COMMENT '正确题数',
  `correctFlag` tinyint(4) DEFAULT NULL COMMENT '批改标志，0表示未批改，1表示已批改',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `uk_sid_vid_cid` (`SID`,`VID`,`CID`) USING BTREE,
  KEY `fk_vid` (`VID`) USING BTREE,
  KEY `fk_cid` (`CID`) USING BTREE,
  CONSTRAINT `scoretable_ibfk_1` FOREIGN KEY (`CID`) REFERENCES `classtable` (`CID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `scoretable_ibfk_2` FOREIGN KEY (`SID`) REFERENCES `studenttable` (`SID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `scoretable_ibfk_3` FOREIGN KEY (`VID`) REFERENCES `schemetable` (`VID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of scoretable
-- ----------------------------
INSERT INTO `scoretable` VALUES ('1', '2', '1', '1', null, '1', '88', '300', '6', '1');
INSERT INTO `scoretable` VALUES ('2', '4', '1', '1', null, '2', '45', '55', '3', '1');
INSERT INTO `scoretable` VALUES ('3', '19', '1', '1', null, '3', '24', '55', '4', '0');

-- ----------------------------
-- Table structure for semestertable
-- ----------------------------
DROP TABLE IF EXISTS `semestertable`;
CREATE TABLE `semestertable` (
  `SID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键，自增',
  `SName` varchar(255) NOT NULL COMMENT '学期名',
  `StartTime` datetime NOT NULL COMMENT '开始时间',
  `EndTime` datetime NOT NULL COMMENT '结束时间',
  `UpdateTime` datetime NOT NULL,
  PRIMARY KEY (`SID`),
  UNIQUE KEY `semester_unique_key` (`SName`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of semestertable
-- ----------------------------
INSERT INTO `semestertable` VALUES ('28', '2015年春', '2015-02-03 10:35:00', '2015-07-08 01:00:00', '2015-08-04 17:35:20');
INSERT INTO `semestertable` VALUES ('29', '2015年秋', '2015-08-10 17:08:14', '2015-08-22 17:08:17', '2015-08-10 17:08:19');
INSERT INTO `semestertable` VALUES ('30', '2014年春', '2014-02-28 00:00:00', '2014-06-20 00:00:00', '2015-08-04 17:38:08');
INSERT INTO `semestertable` VALUES ('31', '2014年秋', '2014-09-01 00:00:00', '2015-01-01 00:00:00', '2015-08-04 17:38:53');
INSERT INTO `semestertable` VALUES ('32', '2013年春', '2013-02-14 00:00:00', '2013-07-01 00:00:00', '2015-08-04 17:40:02');
INSERT INTO `semestertable` VALUES ('33', '2013年秋', '2013-09-04 00:00:00', '2013-12-13 00:00:00', '2015-08-04 17:40:40');
INSERT INTO `semestertable` VALUES ('36', '2012年第一学期', '2012-09-01 00:00:00', '2013-01-01 00:00:00', '2015-08-04 17:42:41');
INSERT INTO `semestertable` VALUES ('37', '2012年第二学期', '2013-02-01 00:00:00', '2013-06-01 00:00:00', '2015-08-04 17:43:26');
INSERT INTO `semestertable` VALUES ('38', '2012年春季', '2012-02-01 00:00:00', '2012-06-08 00:00:00', '2015-08-04 17:43:56');
INSERT INTO `semestertable` VALUES ('39', '2012年秋季', '2012-09-01 00:00:00', '2012-12-01 00:00:00', '2015-08-04 17:44:18');
INSERT INTO `semestertable` VALUES ('40', '2015年测试学期', '2015-01-01 00:00:00', '2015-12-31 00:00:00', '2015-08-04 17:44:48');
INSERT INTO `semestertable` VALUES ('41', '432', '2015-09-09 21:41:37', '2015-09-09 21:41:40', '2015-09-01 21:41:44');
INSERT INTO `semestertable` VALUES ('42', '00', '2015-09-09 16:42:50', '2015-10-26 16:42:53', '2015-09-09 16:43:00');

-- ----------------------------
-- Table structure for studenttable
-- ----------------------------
DROP TABLE IF EXISTS `studenttable`;
CREATE TABLE `studenttable` (
  `SID` int(11) NOT NULL AUTO_INCREMENT COMMENT '学生ID,关键字',
  `SName` varchar(255) NOT NULL COMMENT '学生姓名，可以有重复',
  `UnID` int(11) NOT NULL COMMENT '所在学校ID，冗余，为加快登录查询速度',
  `Sno` varchar(255) NOT NULL COMMENT '学生编号（登录名），可以有重复，但(UnID +Sno)唯一',
  `SPsw` varbinary(255) NOT NULL COMMENT '登录密码，（SID+原密码）的MD5值',
  `Ssex` char(1) NOT NULL COMMENT '性别（M-男 F-女）',
  `Enabled` int(11) NOT NULL COMMENT '有效状态：0 停用 1正常',
  `LoginStatus` int(11) NOT NULL COMMENT '登录状态（0未登录，1已登录）',
  `LogTime` datetime DEFAULT NULL COMMENT '最后一次登录/退出时间',
  `LogIP` varchar(255) DEFAULT NULL COMMENT '登录IP,可表示Ipv6',
  `LogPort` int(11) DEFAULT NULL COMMENT '登录端口',
  `SaccumTime` int(11) DEFAULT '0' COMMENT '累积做题时间',
  `SiniInfo` text COMMENT '初始化信息',
  `SEncryptKey` varbinary(255) DEFAULT NULL COMMENT '加密用的密钥',
  `SVerification` varbinary(255) DEFAULT NULL COMMENT 'WEB使用的验证数据',
  `CreateTime` datetime DEFAULT NULL COMMENT '创建时间',
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`SID`),
  UNIQUE KEY `StudentTable_unique_key` (`UnID`,`Sno`) USING BTREE,
  CONSTRAINT `studenttable_ibfk_1` FOREIGN KEY (`UnID`) REFERENCES `universitytable` (`UnID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of studenttable
-- ----------------------------
INSERT INTO `studenttable` VALUES ('2', '白绝2号嘎嘎嘎', '1', '20150101554', '', 'M', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '10', '', '', '', '2015-07-22 17:59:19', '2015-07-30 20:39:14');
INSERT INTO `studenttable` VALUES ('4', '白绝号哈哈哈哈哈', '1', '20150101004', '', 'F', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-10-26 21:21:09');
INSERT INTO `studenttable` VALUES ('19', '白绝号', '1', '20150101019', '', 'F', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-09-22 01:01:39');
INSERT INTO `studenttable` VALUES ('20', '白绝没有号', '1', '4444', '', 'F', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-09-22 01:07:09');
INSERT INTO `studenttable` VALUES ('21', '白绝21号', '1', '20150101021', '', 'M', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-07-22 17:59:24');
INSERT INTO `studenttable` VALUES ('22', '白绝22号', '1', '20150101022', '', 'F', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-09-01 13:52:39');
INSERT INTO `studenttable` VALUES ('25', '白绝甘五号', '1', '25252252', '', 'F', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-09-22 01:53:15');
INSERT INTO `studenttable` VALUES ('26', '白绝26号', '1', '20150101026', '', 'M', '1', '1', '2016-09-01 14:55:26', '127.0.0.1', '56340', '0', '', '', '', '2015-07-22 17:59:19', '2015-07-22 17:59:24');
INSERT INTO `studenttable` VALUES ('27', '白绝27号', '1', '20150101027', '', 'M', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-07-22 17:59:24');
INSERT INTO `studenttable` VALUES ('28', '白绝28号', '1', '20150101028', '', 'M', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-07-22 17:59:24');
INSERT INTO `studenttable` VALUES ('29', '白绝29号', '1', '20150101029', 0x3644414330394441443234334135433139384446353032334531393231393039324641324434323639443438414434363838393741303734, 'M', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-07-22 17:59:24');
INSERT INTO `studenttable` VALUES ('30', '白绝30号', '1', '20150101030', '', 'M', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-07-22 17:59:24');
INSERT INTO `studenttable` VALUES ('31', '白绝31号', '1', '20150101031', '', 'M', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-07-22 17:59:24');
INSERT INTO `studenttable` VALUES ('32', '白绝32号', '1', '20150101032', '', 'M', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-07-22 17:59:24');
INSERT INTO `studenttable` VALUES ('33', '白绝33号', '1', '20150101033', '', 'M', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-07-22 17:59:24');
INSERT INTO `studenttable` VALUES ('34', '白绝34号', '1', '20150101034', '', 'M', '1', '0', '2015-07-22 17:59:39', '10.1.1.101', '8080', '0', '', '', '', '2015-07-22 17:59:19', '2015-07-22 17:59:24');
INSERT INTO `studenttable` VALUES ('40', '测试添加学生2', '1', '2015111', 0x717171, 'F', '1', '0', null, null, null, '0', null, null, null, '2015-08-01 15:45:57', null);
INSERT INTO `studenttable` VALUES ('46', 'munjhb', '1', '566', 0x6268, 'M', '1', '0', null, null, null, '0', null, null, null, '2015-08-02 21:33:18', null);
INSERT INTO `studenttable` VALUES ('47', 'i89i89', '1', '252', 0x2C6D6B2C, 'F', '1', '0', null, null, null, '0', null, null, null, '2015-08-02 21:33:33', null);
INSERT INTO `studenttable` VALUES ('51', '教师映射班级', '1', '624', 0x363234, 'M', '1', '0', null, null, null, null, null, null, null, '2015-08-15 20:39:39', '2015-08-15 20:39:39');
INSERT INTO `studenttable` VALUES ('52', '他', '1', '32321', '', 'M', '1', '0', null, null, null, null, null, null, null, '2015-09-01 13:52:57', null);
INSERT INTO `studenttable` VALUES ('53', '', '3', '348', '', 'F', '1', '0', null, null, null, null, null, null, null, '2015-09-01 13:56:17', null);
INSERT INTO `studenttable` VALUES ('54', '', '3', '347', '', 'M', '1', '0', null, null, null, null, null, null, null, '2015-09-01 13:56:17', null);
INSERT INTO `studenttable` VALUES ('55', '', '3', '12448', '', 'M', '1', '0', null, null, null, null, null, null, null, '2015-09-01 14:00:55', null);
INSERT INTO `studenttable` VALUES ('56', '', '3', '12449', '', 'M', '1', '0', null, null, null, null, null, null, null, '2015-09-01 14:00:55', null);
INSERT INTO `studenttable` VALUES ('60', '小木木', '1', '1111', 0x31313131, 'M', '1', '0', null, null, null, null, null, null, null, '2015-10-26 21:28:53', '2015-10-26 21:28:53');

-- ----------------------------
-- Table structure for teachertable
-- ----------------------------
DROP TABLE IF EXISTS `teachertable`;
CREATE TABLE `teachertable` (
  `TID` int(11) NOT NULL AUTO_INCREMENT COMMENT '教师ID,关键字',
  `TName` varchar(255) NOT NULL COMMENT '教师姓名，可以有重复',
  `UnID` int(11) NOT NULL COMMENT '所在学校ID，冗余，为加快登录查询速度',
  `TNo` varchar(255) NOT NULL COMMENT '教师编号（登录名），可以有重复，但(UnID +TNo)唯一',
  `TPsw` varbinary(255) NOT NULL COMMENT '登录密码，（TID+原密码）的MD5值，修改密码时需要同时修改映射学生的密码，使用触发器',
  `TSex` char(1) NOT NULL COMMENT '性别（M-男 F-女）',
  `TCID` int(11) NOT NULL COMMENT '映射班级ID (默认-1),为每个教师建立一个测试班',
  `TSID` int(11) NOT NULL COMMENT '映射学生ID (默认-1),教师帐号可以直接登录学生端，其班级ID是TCID',
  `Enabled` int(11) NOT NULL COMMENT '有效状态：0 停用 1正常',
  `UpdateTime` datetime NOT NULL,
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`TID`),
  UNIQUE KEY `TeacherTable_unique_key` (`UnID`,`TNo`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teachertable
-- ----------------------------
INSERT INTO `teachertable` VALUES ('1', '老王', '1', '2015001', '', 'M', '2', '2', '1', '2015-07-15 15:28:48', null);
INSERT INTO `teachertable` VALUES ('2', '测试者1', '1', '1', '', 'M', '-1', '-1', '1', '2015-08-12 16:38:28', null);
INSERT INTO `teachertable` VALUES ('3', '老白', '1', '101', '', 'M', '-1', '-1', '1', '2015-08-03 13:29:50', null);
INSERT INTO `teachertable` VALUES ('4', '老黄', '1', '102', '', 'M', '-1', '-1', '1', '2015-08-16 15:18:58', null);
INSERT INTO `teachertable` VALUES ('5', '牢牢', '1', '103', '', 'M', '-1', '-1', '1', '2015-08-03 13:31:57', null);
INSERT INTO `teachertable` VALUES ('6', 'KK', '1', '104', '', 'M', '-1', '-1', '1', '2015-08-03 13:32:40', null);
INSERT INTO `teachertable` VALUES ('7', '老贝777', '1', '555', 0x383838, 'M', '-1', '-1', '1', '2015-08-10 14:44:02', null);
INSERT INTO `teachertable` VALUES ('8', '牢牢', '1', '666', '', 'F', '-1', '-1', '1', '2015-08-06 22:51:13', null);
INSERT INTO `teachertable` VALUES ('9', '框框', '1', '777', '', 'M', '-1', '-1', '1', '2015-08-06 22:51:48', null);
INSERT INTO `teachertable` VALUES ('10', '欧欧', '1', '888', '', 'F', '-1', '-1', '1', '2015-08-06 22:52:22', null);
INSERT INTO `teachertable` VALUES ('11', '晶晶', '1', '999', '', 'M', '-1', '-1', '1', '2015-08-06 22:53:20', null);
INSERT INTO `teachertable` VALUES ('12', '劳拉', '1', '6523', 0x36353233, 'F', '-1', '-1', '1', '2015-08-14 16:48:14', null);
INSERT INTO `teachertable` VALUES ('13', '增加', '1', '0652', 0x30363532, 'M', '15', '50', '1', '2015-08-15 20:11:00', null);
INSERT INTO `teachertable` VALUES ('14', '增加', '1', '624', 0x363234, 'M', '16', '51', '1', '2015-08-15 20:39:39', null);
INSERT INTO `teachertable` VALUES ('15', '别的学校滴一', '2', '55553', '', 'M', '-1', '-1', '1', '2015-08-28 10:27:04', null);
INSERT INTO `teachertable` VALUES ('16', '别的学校滴二', '2', '432', '', 'F', '-1', '-1', '1', '2015-08-28 10:36:01', null);
INSERT INTO `teachertable` VALUES ('17', '别的学校滴三', '3', '4243242', '', 'M', '-1', '-1', '1', '2015-08-28 10:36:45', null);

-- ----------------------------
-- Table structure for universitytable
-- ----------------------------
DROP TABLE IF EXISTS `universitytable`;
CREATE TABLE `universitytable` (
  `UnID` int(11) NOT NULL AUTO_INCREMENT COMMENT '学校ID,关键字.(不作为其他表的外键)，递增',
  `UnName` varchar(255) NOT NULL COMMENT '学校名称，无重复，唯一索引',
  `IP` varchar(255) DEFAULT NULL COMMENT '独立服务器IP地址',
  `Port` int(11) DEFAULT NULL COMMENT '独立服务器端口',
  `Attr` int(11) NOT NULL COMMENT '属性(0 本服务器 1使用独立服务器)',
  `Enabled` int(11) NOT NULL COMMENT '有效状态：0停用 1正常',
  `Verification` varchar(255) DEFAULT NULL COMMENT '验证码,其他学校将IP地址和端口登记到此处时使用',
  `UpdateTime` datetime DEFAULT NULL COMMENT '记录更新时间（默认值GetDate（））',
  `CreateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`UnID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of universitytable
-- ----------------------------
INSERT INTO `universitytable` VALUES ('-1', '匿名学校', '12.12.12.12', '12345', '0', '1', null, '2015-08-07 14:42:29', null);
INSERT INTO `universitytable` VALUES ('1', '广东工业大学', '127.0.0.1', '12345', '0', '1', 'lll', '2015-08-07 21:38:14', null);
INSERT INTO `universitytable` VALUES ('2', '宇宙供热大学', '192.128.1.1', '12345', '0', '1', '', '2015-08-08 13:48:54', null);
INSERT INTO `universitytable` VALUES ('3', '北极星码农学院', '155.111.11.11', '45678', '0', '1', 'f7', '2015-09-11 16:14:29', null);
INSERT INTO `universitytable` VALUES ('5', '测试学校二谁谁谁', '', null, '0', '0', 'ddd', '2015-10-04 19:08:37', null);
INSERT INTO `universitytable` VALUES ('6', '测试学校三烦烦烦修改', '33.33.33.33', '333', '1', '0', 'ttt', '2015-10-26 20:32:22', '2015-10-04 19:09:36');
INSERT INTO `universitytable` VALUES ('7', '美少女大学', null, null, '0', '1', 'qaaa', '2015-10-26 20:31:54', '2015-10-26 20:18:11');

-- ----------------------------
-- Table structure for university_schemestable
-- ----------------------------
DROP TABLE IF EXISTS `university_schemestable`;
CREATE TABLE `university_schemestable` (
  `ID` int(11) NOT NULL,
  `UnID` int(11) DEFAULT NULL COMMENT '学校ID,外键（级联）',
  `VID` int(11) DEFAULT NULL COMMENT '作业表ID,外键（级联）',
  `ValidTime` datetime DEFAULT NULL COMMENT '有效时间（结束时间）',
  `UpdateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `unique` (`UnID`,`VID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of university_schemestable
-- ----------------------------

-- ----------------------------
-- Function structure for queryChildrenChap
-- ----------------------------
DROP FUNCTION IF EXISTS `queryChildrenChap`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `queryChildrenChap`(`nodeId` int) RETURNS varchar(4000) CHARSET utf8
BEGIN
	DECLARE sTemp VARCHAR(4000);
	DECLARE sTempChd VARCHAR(4000);

	SET sTemp = '$';
	SET sTempChd = cast(nodeId as char);
	WHILE sTempChd is not NULL 
	DO
		SET sTemp = CONCAT(sTemp,',',sTempChd);
		SELECT group_concat(chId) INTO sTempChd FROM problemchaptable where FIND_IN_SET(parentId,sTempChd)>0;
	END WHILE;
	return sTemp;
END
;;
DELIMITER ;
