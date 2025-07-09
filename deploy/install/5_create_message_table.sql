-- 创建消息表
CREATE TABLE IF NOT EXISTS `message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `sender_id` bigint NOT NULL COMMENT '发送者用户ID',
  `receiver_id` bigint NOT NULL COMMENT '接收者用户ID',
  `content` text NOT NULL COMMENT '消息内容',
  `is_read` boolean DEFAULT FALSE COMMENT '是否已读',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_receiver_id` (`receiver_id`),
  KEY `idx_sender_id` (`sender_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息表';

-- 插入测试数据
INSERT INTO `message` (`sender_id`, `receiver_id`, `content`, `is_read`, `create_time`) VALUES 
(1, 2, '测试消息1', false, NOW()),
(2, 1, '测试消息2', false, NOW()); 