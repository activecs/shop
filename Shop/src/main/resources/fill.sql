USE derezashop;

SET SQL_SAFE_UPDATES=0;

DELETE FROM user;
DELETE FROM product;
DELETE FROM manufacturer;
DELETE FROM categorie;
          
INSERT INTO user(email,password,avatar,firstName,lastName,birthDate,address1,address2,city,postcode,additionalInfo,phone,role)
	VALUES("user1@yandex.ru","24c9e15e52afc47c225b757e7bee1f9d","user1@yandex.ru.png","Eduard","Dereza","1983-05-26","Kharkiv city","Pushkinskaya str", "Kharkiv",61024,"When you need more functionality, something beyond what you can get with the standard actions or EL, you don’t have to resort to scripting. In the next chapter, you’ll learn how to use the JSP Standard Tag Library 1.1 (JSTL 1.1) to do just about everything you’ll ever need, using a combination of tags and EL. Here’s a sneak peek of how to do our conditional forward without scripting.","0930238984", 'USER');

INSERT INTO manufacturer(name)
		VALUES("Asus"),
			("D-Link"),
			("TP-Link"),
			("Zyxel"),
			("Edimax");

INSERT INTO categorie(name)
		VALUES("wireless access point"),
				("wireless router"),
				("wi-fi adapters");

INSERT INTO product(name,price,photo,manufacturer_id,category_id,description)
		VALUES("RT-N14U", 367,  "asus_rt_n14u.jpg", 1, 2, "WAN-порт	Ethernet; Интерфейсы	4 x RJ-45 100 Мбит/с;  1 x RJ-45 100 Мбит/с (WAN); USB 2.0; Беспроводные возможности	IEEE 802.11n; Поддержка протоколов	PPPoE, IPsec, L2TP, PPTP; Частота работы Wi-Fi	2.4 ГГц; Поддержка IPTV; Количество антенн	2; Конструкция антенн	Встроенные"),
				("RT-N16", 569, "asus_rt_n16.jpg", 1, 2,  "WAN-порт	Ethernet; Интерфейсы	4 x RJ-45 100 Мбит/с;  1 x RJ-45 100 Мбит/с (WAN); 2 x USB 2.0; Беспроводные возможности	IEEE 802.11n; Поддержка протоколов	PPPoE, IPsec, L2TP, PPTP; Частота работы Wi-Fi	2.4 ГГц; Поддержка IPTV; Количество антенн	3; Конструкция антенн	Сьемные"),
				("RT-N10P", 173, "asus_rt-n10p.jpg",  1, 2, "WAN-порт	Ethernet; Интерфейсы	4 x RJ-45 100 Мбит/с;  1 x RJ-45 100 Мбит/с (WAN); Беспроводные возможности	IEEE 802.11n; Поддержка протоколов	PPPoE, IPsec, L2TP, PPTP; Частота работы Wi-Fi	2.4 ГГц; Поддержка IPTV; Количество антенн	1; Конструкция антенн	Сьемные"),
				("DIR-615/A", 165, "d_link_dir_615.jpg", 2, 2, "WAN-порт	Ethernet; Интерфейсы	4 x RJ-45 10/100BASE-TX LAN портов с автоматическим определением полярности MDI/MDIX; Беспроводные возможности	IEEE 802.11n, IEEE 802.11g, IEEE 802.11b;Поддержка протоколов	PPPoE, IPsec, L2TP, PPTP; Скорость LAN портов	100 Мбит/с; Скорость Wi-Fi	300 Мбит/с ; Частота работы Wi-Fi	2.4 ГГц; Количество антенн	2; Конструкция антенн	Несъемные"),
				("DIR-636L", 536, "d_link_dir_636l.jpg", 2, 2, "WAN-порт	Ethernet; Интерфейсы	4 x гигабитных LAN-порта; 1 x гигабитный WAN-порт; 1 х USB 2.0 порт; Беспроводные возможности	IEEE 802.11n, IEEE 802.11g, IEEE 802.11b; Поддержка протоколов	IPsec, L2TP, PPTP; Скорость LAN портов	1 Гбит/с; Скорость Wi-Fi	300 Мбит/с; Частота работы Wi-Fi	2.4 ГГц; Конструкция антенн	Встроенные"),
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