-- 测试课程提醒功能
-- 1. 查看用户表结构
DESCRIBE `user`;

-- 2. 查看现有用户的提醒设置
SELECT username, in_site, email_reminder FROM `user`;

-- 3. 更新某个用户的提醒设置进行测试
UPDATE `user` SET in_site = TRUE, email_reminder = TRUE WHERE username = 'testuser';

-- 4. 验证更新结果
SELECT username, in_site, email_reminder FROM `user` WHERE username = 'testuser'; 