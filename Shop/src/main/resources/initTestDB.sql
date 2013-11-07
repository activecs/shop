CREATE SCHEMA IF NOT EXISTS testbase DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

SET SQL_SAFE_UPDATES=0;

USE testbase;

DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS manufacturer;
DROP TABLE IF EXISTS category;

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
		
DELETE FROM user;
DELETE FROM product;
DELETE FROM manufacturer;
DELETE FROM category;
          
INSERT INTO user(email,password,avatar,firstName,lastName,birthDate,address1,address2,city,postcode,additionalInfo,phone,role)
	VALUES("user1@yandex.ru","24c9e15e52afc47c225b757e7bee1f9d","user1@yandex.ru.png","Eduard","Dereza","1983-05-26","Kharkiv city","Pushkinskaya str", "Kharkiv",61024,"When you need more functionality, something beyond what you can get with the standard actions or EL, you don’t have to resort to scripting. In the next chapter, you’ll learn how to use the JSP Standard Tag Library 1.1 (JSTL 1.1) to do just about everything you’ll ever need, using a combination of tags and EL. Here’s a sneak peek of how to do our conditional forward without scripting.","0930238984", 'USER');

INSERT INTO manufacturer(name)
		VALUES("Asus"),
			("D-Link"),
			("TP-Link"),
			("Zyxel"),
			("Edimax");

INSERT INTO category(name)
		VALUES("wireless access point"),
				("wireless router"),
				("wi-fi adapter");

INSERT INTO product(name,price,photo,manufacturer_id,category_id,description)
		VALUES("RT-N14U", 367,  "asus_rt_n14u.jpg", 1, 2, ""),
				("RT-N16", 569, "asus_rt_n16.jpg", 1, 2,  ""),
				("RT-N10P", 173, "asus_rt-n10p.jpg",  1, 2, ""),
				("DIR-615/A", 165, "d_link_dir_615.jpg", 2, 2, ""),
				("DIR-636L", 536, "d_link_dir_636l.jpg", 2, 2, ""),
				("TL-WDR3600", 627, "tp_link_tl_wdr3600.jpg",  3, 2, ""),
				("TL-WDN4800", 325, "tp_link_tl_wdn4800.jpg", 3,  3, ""),
				("PCE-N15", 169, "asus_pce_n15.jpg", 1, 3, ""),
				("EW-7238RPD", 421, "edimax_ew_7238rpd.jpg", 5, 1, ""),
				("EW-7416APN", 322, "edimax_ew_7416apn.jpg", 5, 1, ""),
				("BR-6478AC", 817, "edimax_br_6478ac.jpg", 5, 2, ""),
				("Keenetic Giga II", 784, "zyxel_keenetic_giga_2.jpg", 4, 2, ""),
				("Keenetic II", 644, "zyxel_keenetic_2.jpg", 4,2, ""),
				("TL-WA801ND", 297, "tp_link_tl_wa801nd.jpg", 3, 1, ""),
				("CV-7428NS", 297, "edimax_cv_7428ns.jpg", 5, 1, "");