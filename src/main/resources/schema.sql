DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) NOT NULL,
  `password` varchar(32) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ;

DROP TABLE IF EXISTS `idol`;
CREATE TABLE `idol` (
  `id` int(11) NOT NULL AUTO_INCREMENT ,
  `name` varchar(255) NOT NULL ,
  `type` varchar(20) DEFAULT NULL ,
  `specs` varchar(30) DEFAULT NULL ,
  `size` varchar(10) DEFAULT NULL,
  `cost` decimal(7,2) NOT NULL DEFAULT '0.00',
  `price` decimal(7,2) NOT NULL DEFAULT '0.00' ,
  `quantity` int(11) NOT NULL DEFAULT '0' ,
  `reparable_qty` int(11) NOT NULL DEFAULT '0' ,
  `damaged_qty` int(11) NOT NULL DEFAULT '0' ,
  `comments` varchar(255) DEFAULT NULL ,
  PRIMARY KEY (`id`)
  ) ;
  
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL ,
  `primary_mobile` varchar(10) NOT NULL DEFAULT '0' ,
  `secondary_mobile` varchar(10) DEFAULT '0' ,
  `landline` varchar(20) DEFAULT NULL ,
  `address` varchar(255) DEFAULT NULL ,
  `info` varchar(255) DEFAULT NULL ,
  `comments` varchar(255) DEFAULT NULL ,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `primary_mobile_UNIQUE` (`primary_mobile`)
) ;

DROP TABLE IF EXISTS `booking`;
CREATE TABLE `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `customer_id` int(11) NOT NULL ,
  `idol_id` int(11) NOT NULL,
  `payment_mode` varchar(25) NOT NULL DEFAULT 'CASH',
  `booking_amt` decimal(7,2) NOT NULL DEFAULT '0.00' ,
  `balance_amt` decimal(7,2) NOT NULL DEFAULT '0.00' ,
  `discount_amt` decimal(7,2) NOT NULL DEFAULT '0.00' ,
  `total_amt` decimal(7,2) NOT NULL DEFAULT '0.00' ,
  `status` varchar(25) NOT NULL DEFAULT 'BOOKED' ,
  `reason` varchar(50) DEFAULT NULL ,
  `location` varchar(10) DEFAULT NULL,
  `shipment_date` timestamp NULL DEFAULT NULL ,
  `user_id` int(11) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;

DROP TABLE IF EXISTS `daily_booking`;
CREATE TABLE `daily_booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) NOT NULL DEFAULT 0,
  `booking_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ,
  `customer_id` int(11) NOT NULL ,
  `idol_id` int(11) NOT NULL,
  `payment_mode` varchar(25) NOT NULL DEFAULT 'CASH',
  `booking_amt` decimal(7,2) NOT NULL DEFAULT '0.00' ,
  `balance_amt` decimal(7,2) NOT NULL DEFAULT '0.00' ,
  `discount_amt` decimal(7,2) NOT NULL DEFAULT '0.00' ,
  `total_amt` decimal(7,2) NOT NULL DEFAULT '0.00' ,
  `status` varchar(25) NOT NULL DEFAULT 'BOOKED' ,
  `reason` varchar(50) DEFAULT NULL ,
  `location` varchar(10) DEFAULT NULL,
  `shipment_date` timestamp NULL DEFAULT NULL ,
  `user_id` int(11) DEFAULT NULL,
  `comments` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;
