DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `customer`;
DROP TABLE IF EXISTS `product`;
DROP TABLE IF EXISTS `transaction`;

CREATE TABLE IF NOT EXISTS `role`(
    `id` int not NULL AUTO_INCREMENT,
    `name` VARCHAR(15) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `customer` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `login` VARCHAR(15) NOT NULL UNIQUE,
    `password` VARCHAR(15) NOT NULL,
    `name`VARCHAR(15) DEFAULT NULL,
    `role_id` int NOT NULL default 1,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS `product`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(15) NOT NULL,
    `category` VARCHAR(15) NOT NULL,
    `date_added` DATE NOT NULL,
    `price` DECIMAL(10,2) NOT NULL,
    `price_discount` DECIMAL(10,2),
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `transaction`(
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `customer_id` BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    `purch_tmst` TIMESTAMP NOT NULL,
    `payment_type` VARCHAR(15) NOT NULL,
    `status` VARCHAR(15) NOT NULL,
    `crd_card_number` VARCHAR(20),
    PRIMARY KEY (`id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
);