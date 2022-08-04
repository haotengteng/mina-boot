create database mina;

use mina;

CREATE TABLE `example_info` (
                                `name` varchar(32) DEFAULT NULL,
                                `address` varchar(64) DEFAULT NULL,
                                `is_deleted` bit(1) DEFAULT NULL,
                                `id` int NOT NULL AUTO_INCREMENT,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

