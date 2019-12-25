
CREATE TABLE user
(
	id BIGINT(20) NOT NULL COMMENT '主键ID',
	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
	token VARCHAR(50) NULL DEFAULT NULL COMMENT '登陆token',
	PRIMARY KEY (id)
);

INSERT INTO user (id, name, age, email,token) VALUES
(1, 'Jone', 18, 'test1@rizon.top', 'token-1'),
(2, 'Jack', 20, 'test2@rizon.top', 'token-2'),
(3, 'Tom', 28, 'test3@rizon.top', 'token-3'),
(4, 'Sandy', 21, 'test4@rizon.top', 'token-4'),
(5, 'Billie', 24, 'test5@rizon.top', 'token-5');