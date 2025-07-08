-- 为用户表添加提醒设置字段
ALTER TABLE `user` 
ADD COLUMN `in_site` BOOLEAN DEFAULT FALSE COMMENT '是否开启站内提醒',
ADD COLUMN `email_reminder` BOOLEAN DEFAULT FALSE COMMENT '是否开启邮件提醒';

-- 为现有用户设置默认值
UPDATE `user` SET `in_site` = FALSE, `email_reminder` = FALSE WHERE `in_site` IS NULL OR `email_reminder` IS NULL; 