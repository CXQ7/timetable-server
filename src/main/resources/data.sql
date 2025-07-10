-- 创建user表
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `email` varchar(100),
  `role` varchar(20) DEFAULT 'STUDENT',
  `avatar_url` LONGTEXT,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
);

-- 插入测试数据
INSERT INTO `user` (`username`, `password`, `email`, `role`) VALUES 
('admin', 'admin123', 'admin@example.com', 'ADMIN'),
('testuser', '123456', 'test@example.com', 'STUDENT'); 