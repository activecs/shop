CREATE SCHEMA IF NOT EXISTS derezashop DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE derezashop;

DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS manufacturer;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS order_info;
DROP TABLE IF EXISTS user;

CREATE TABLE user(
    	id             		INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
        email          		VARCHAR(25) NOT NULL UNIQUE,
        password       		VARCHAR(32) NOT NULL,
        avatar		   		VARCHAR(90) NOT NULL,
        firstName      		VARCHAR(20) NOT NULL,
        lastName       		VARCHAR(20) NOT NULL,
        birthDate      		DATE NOT NULL,
        company 	   		VARCHAR(20),
        address1	   		VARCHAR(100) NOT NULL,
        address2	   		VARCHAR(100) NOT NULL,
        city		   		VARCHAR(40) NOT NULL,
        postcode	   		INTEGER NOT NULL,
        additionalInfo 		TEXT,
        phone          		VARCHAR(20) NOT NULL,
        role           	    ENUM('USER','ADMIN') DEFAULT 'USER',
        enabled        		BOOLEAN DEFAULT true,
        loginAttemptCount 	INTEGER DEFAULT 0,
        lastSuccessLogin 	DATETIME,
        nextUnban 			DATETIME);

CREATE TABLE manufacturer(
		id             		INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
		name          		VARCHAR(40));

CREATE TABLE category(
		id            		INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
		name           		VARCHAR(40));

CREATE TABLE product(
		id             		INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
		name				VARCHAR(50),
		price				DECIMAL(5,2) NOT NULL DEFAULT 0,
		photo				VARCHAR(100),
		description			TEXT,
		manufacturer_id		INTEGER NOT NULL,
		category_id			INTEGER NOT NULL,
		FOREIGN KEY (manufacturer_id) REFERENCES manufacturer (id) ON UPDATE RESTRICT ON DELETE CASCADE,
		FOREIGN KEY (category_id) REFERENCES category (id) ON UPDATE RESTRICT ON DELETE CASCADE,
		CHECK(price>0));
		
CREATE TABLE order_info(
		id					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
		user_id				INTEGER NOT NULL,
		state				ENUM('GOT', 'IN_PROGRESS', 'SENT', 'DONE', 'CANCELED') DEFAULT 'GOT',
		details				VARCHAR(300),
		method				ENUM('CASH', 'BANK_TRANSFER') DEFAULT 'CASH',
		city		   		VARCHAR(40) NOT NULL,
		address		   		VARCHAR(100) NOT NULL,
		phone          		VARCHAR(20) NOT NULL,
		date				DATETIME,
		FOREIGN KEY (user_id) REFERENCES user (id) ON UPDATE RESTRICT ON DELETE CASCADE);

CREATE TABLE order_item(
		id					INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
		order_info_id		INTEGER NOT NULL,
		product_id			INTEGER NOT NULL,
		quantity			INTEGER NOT NULL,
		price				DECIMAL(5,2) NOT NULL DEFAULT 0,
		FOREIGN KEY (order_info_id) REFERENCES order_info (id) ON UPDATE RESTRICT ON DELETE CASCADE,
		UNIQUE KEY (order_info_id,product_id));		