SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_id` tinyint(4) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(255) DEFAULT NULL COMMENT '管理员名',
  `password` varchar(255) DEFAULT NULL COMMENT '管理员密码',
  `role` varchar(255) DEFAULT NULL COMMENT '权限',
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL COMMENT '父评论id（0表示一级评论）',
  `record_id` int(11) DEFAULT NULL COMMENT '打卡记录id',
  `user_id` int(11) DEFAULT NULL COMMENT '评论者id',
  `replied_user_name` varchar(255) DEFAULT NULL COMMENT '被回复的用户的name',
  `content` varchar(255) DEFAULT NULL COMMENT '评论内容',
  `gmt_create` timestamp NULL DEFAULT NULL COMMENT '发表时间',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `record_id` int(11) NOT NULL COMMENT '打卡记录id',
  `liker_id` int(11) NOT NULL COMMENT '点赞者id',
  `gmt_create` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for module
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `module_id` int(11) NOT NULL AUTO_INCREMENT,
  `module_name` varchar(255) DEFAULT NULL COMMENT '模块名',
  `module_cover` varchar(255) DEFAULT NULL COMMENT '模块封面路径',
  PRIMARY KEY (`module_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for record
-- ----------------------------
DROP TABLE IF EXISTS `record`;
CREATE TABLE `record` (
  `record_id` int(11) NOT NULL AUTO_INCREMENT,
  `module_id` int(11) NOT NULL COMMENT '模块id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `record_content` varchar(255) DEFAULT NULL COMMENT '打卡文本',
  `record_img` varchar(255) DEFAULT NULL COMMENT '图片路径',
  `total_like` smallint(6) DEFAULT NULL COMMENT '被点赞数',
  `gmt_create` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`record_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for source
-- ----------------------------
DROP TABLE IF EXISTS `source`;
CREATE TABLE `source` (
  `source_id` int(11) NOT NULL AUTO_INCREMENT,
  `source_title` varchar(255) DEFAULT NULL COMMENT '资料标题',
  `source_path` varchar(255) DEFAULT NULL COMMENT '资料七牛云路径',
  `source_cover` varchar(255) DEFAULT NULL COMMENT '视频封面',
  `module_id` int(11) NOT NULL COMMENT '模块id',
  PRIMARY KEY (`source_id`)
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_openid` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户openid',
  `user_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名',
  `user_avatar` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户头像url',
  `user_point` int(11) DEFAULT NULL COMMENT '用户积分',
  `total_like` smallint(6) DEFAULT NULL COMMENT '用户被点赞数',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
