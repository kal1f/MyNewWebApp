DROP TABLE IF EXISTS `customer`;

CREATE TABLE IF NOT EXISTS `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer` char(20) NOT NULL UNIQUE,
  `pass_` char(10) NOT NULL,
  `name_` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=129 DEFAULT CHARSET=utf8mb4;