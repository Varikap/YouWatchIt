DROP SCHEMA IF EXISTS webprojekat;
CREATE SCHEMA webprojekat DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE webprojekat;

CREATE TABLE USERS (
	username VARCHAR(30) NOT NULL,
	password VARCHAR(30) NOT NULL,
	name VARCHAR(30) NOT NULL,
	lastname VARCHAR(30) NOT NULL,
	email VARCHAR(50) NOT NULL,
	description VARCHAR(500) NOT NULL,
	created DATE NOT NULL,
	user_role ENUM('USER', 'ADMINISTRATOR') NOT NULL DEFAULT 'USER',
	banned BIT NOT NULL,
	profile_picture_URL VARCHAR(100),
	deleted BIT NOT NULL,
	INDEX username(username ASC)

);

INSERT INTO USERS(username, password, name, lastname,email,description,created,user_role,banned,profile_picture_URL,deleted) VALUES('nikola','123','Nikola','Popadic', 'pop@gmail.com','dsadsa','2018-01-01','ADMINISTRATOR',0,null,0);
INSERT INTO USERS(username, password, name, lastname,email,description,created,user_role,banned,profile_picture_URL,deleted) VALUES('pera','123','Pera','Perovic', 'ppera123@gmail.com','sdastesadasgsdf','2018-07-01','ADMINISTRATOR',0,null,0);
INSERT INTO USERS(username, password, name, lastname,email,description,created,user_role,banned,profile_picture_URL,deleted) VALUES('milos','123','Milos','Milosevic', 'milos123@gmail.com','hsawryegdsfa','2018-01-04','USER',0,null,0);
INSERT INTO USERS(username, password, name, lastname,email,description,created,user_role,banned,profile_picture_URL,deleted) VALUES('djura','123','Djordje','Djuric', 'djuki123@gmail.com','sdasgfwqrewASFAS','2018-01-10','USER',0,null,0);
INSERT INTO USERS(username, password, name, lastname,email,description,created,user_role,banned,profile_picture_URL,deleted) VALUES('sima','123','Sima','Simic', 'simke123@gmail.com','dasgasdfasdasdsad','2018-03-01','USER',0,null,0);
INSERT INTO USERS(username, password, name, lastname,email,description,created,user_role,banned,profile_picture_URL,deleted) VALUES('lazar','123','Lazar','Lazarevic', 'lazo123@gmail.com','sdagdwdasdsa','2016-02-01','USER',0,null,0);
INSERT INTO USERS(username, password, name, lastname,email,description,created,user_role,banned,profile_picture_URL,deleted) VALUES('marko','123','Marko','Markovic', 'masja123@gmail.com','dasghsrgfsdfd','2017-01-01','USER',0,null,0);
INSERT INTO USERS(username, password, name, lastname,email,description,created,user_role,banned,profile_picture_URL,deleted) VALUES('steva','123','Stevan','Stevic', 'steva123@gmail.com','Sdasfgasdfdwadas','2018-01-01','USER',0,null,0);


CREATE TABLE FOLLOWING(
	followed_username VARCHAR(30) NOT NULL,
	following_username VARCHAR(30 )NOT NULL,
	FOREIGN KEY(followed_username) REFERENCES USERS(username),
	FOREIGN KEY(following_username) REFERENCES USERS(username),
	PRIMARY KEY(followed_username,following_username)
);

INSERT INTO FOLLOWING(followed_username, following_username) VALUES('nikola', 'pera');
INSERT INTO FOLLOWING(followed_username, following_username) VALUES('nikola', 'djura');
INSERT INTO FOLLOWING(followed_username, following_username) VALUES('nikola', 'sima');
INSERT INTO FOLLOWING(followed_username, following_username) VALUES('nikola', 'lazar');
INSERT INTO FOLLOWING(followed_username, following_username) VALUES('milos', 'pera');
INSERT INTO FOLLOWING(followed_username, following_username) VALUES('milos', 'sima');
INSERT INTO FOLLOWING(followed_username, following_username) VALUES('pera', 'marko');
INSERT INTO FOLLOWING(followed_username, following_username) VALUES('djura', 'marko');
INSERT INTO FOLLOWING(followed_username, following_username) VALUES('steva', 'marko');


CREATE TABLE VIDEOS(
	video_id BIGINT AUTO_INCREMENT,
	video_name VARCHAR(200) NOT NULL,
	video_embedURL VARCHAR(300) NOT NULL,
	imageURL VARCHAR(150) NOT NULL,
	description VARCHAR(500) NOT NULL,
	visibility ENUM('PUBLIC', 'UNLISTED', 'PRIVATE') NOT NULL DEFAULT 'PUBLIC',
	rating_visibility BIT NOT NULL,
    comments_allowed BIT NOT NULL,
	blocked BIT NOT NULL,
	views BIGINT NOT NULL,
	created DATE NOT NULL,
    owner_username VARCHAR(30),
    deleted BIT NOT NULL,
	FOREIGN KEY(owner_username) REFERENCES USERS(username),
	PRIMARY KEY(video_id)
);

INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('DC-Justice League','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/r9-DM9uBtVI" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/DC - Justice League.jpg', 'New trailer for movie avangers', 'PUBLIC',1,1,0,15,'2018-01-01','nikola',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('DC-Batman meets flash','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/T2mXBtT7Nf0" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/DC - Batman meets Flash.jpg', 'New trailer for movie batman meets flash', 'PUBLIC',1,1,0,20,'2018-01-01','nikola',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('DC-Black lightning','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/dvbAwl9NeWU" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/DC - Black Lightning.jpg', 'DCs movie black lightning', 'PUBLIC',1,1,0,25,'2018-01-01','milos',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('DC-Suicide squad','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/CmRih_VtVAs" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/DC - Suicide Squad.jpg', 'Sick suicide squad', 'PUBLIC',1,1,0,30,'2018-01-01','milos',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('HIM-Right here in my arms','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/-SQGJ0rfIEk" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/Him - Right Here In My Arms.jpg', 'Music video Him', 'UNLISTED',1,1,0,35,'2018-01-01','nikola',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('HIM-Dont fear the reaper','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/jvxRTs9bVoo" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/Him - Dont Fear The Reaper.jpg', 'Music video Him', 'PRIVATE',1,1,0,40,'2018-01-01','nikola',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('HIM-Gone with the sin','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/22lVr2II7as" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/Him - Gone With The Sin.jpg', 'Music video Him', 'PUBLIC',1,1,0,45,'2018-01-01','nikola',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('HIM-Funeral of hearts','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/PMD1k16baVE" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/Him - The Funeral Of Hearts.jpg', 'Music video Him', 'PUBLIC',1,1,0,58,'2018-01-01','nikola',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('HIM-Wicked games','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/pBW--bgyezg" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/Him - Wicked Games.jpg', 'Music video Him', 'PUBLIC',1,1,0,60,'2018-01-01','pera',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('10 magic tricks','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/dn1LU7IQ7x8" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/How to do 10 magic trics.jpg', 'How to trick your friends', 'PUBLIC',1,1,0,65,'2018-01-01','pera',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('Make fire with lemon','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/Bv2vT665bGI" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/How to make fire with lemon.jpg', 'Not verry usefull tutorial', 'PUBLIC',1,1,0,70,'2018-01-01','djura',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('How to draw faces','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/iMEBSQJYaAY" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/How to draw faces for begginers.jpg', 'How to draw faces for begginers', 'PUBLIC',1,1,0,75,'2018-01-01','djura',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('How to make kvass','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/k1UTJKBMvgc" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/How to make kvass.jpg', 'How to make delicios beverage kvass', 'PUBLIC',1,1,0,80,'2018-01-01','pera',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('Korn-Freak On A Leash','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/jRGrNDV2mKc" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/Korn-Freak.jpg', 'Music video from Korn', 'PUBLIC',1,1,0,85,'2018-01-01','pera',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('LOL-Kayn','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/zmdkmPsmEnA" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/LOL - Kayn.jpg', 'Kayn - champion spotlight', 'PUBLIC',1,1,0,90,'2018-01-01','pera',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('LOL-Swain','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/f-Uqzp1AVDM" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/LOL - Swain.jpg', 'Swain - champion spotlight', 'PUBLIC',1,1,0,95,'2018-01-01','sima',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('LOL-The climb','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/3Eu7NzzHC84" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/LOL - The Climb.jpg', 'The 8 season climb', 'PUBLIC',1,1,0,100,'2018-01-01','sima',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('LOL-Zoe','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/lZnVHNJfFMY" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/LOL - ZOE.jpg', 'Zoe - champion spotlight', 'PUBLIC',1,1,0,105,'2018-01-01','marko',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('LOL-Ahri','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/vzHrjOMfHPY" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/LOL - Ahri.jpg', 'League of legends cinematic trailer', 'PUBLIC',1,1,0,110,'2018-01-01','steva',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('LOL-Xayah and Rakan','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/O4PDzBnBMU4" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/LOL - Xayah And Rakan.jpg', 'Xayah and Rakan - champions spotlight', 'PUBLIC',1,1,0,115,'2018-01-01','steva',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('Marvel-Deadpool 2','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/I4tFNfROlqk" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/Marvel - Deadpool2.jpg', 'Deadpool 2 trailer', 'PUBLIC',1,1,0,120,'2018-01-01','steva',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('Marvel-Infinity war','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/jRGrNDV2mKc" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/Avengers - Infinity War.jpg', 'Infinity war trailer', 'PUBLIC',1,1,0,125,'2018-01-01','nikola',0);
INSERT INTO VIDEOS(video_name,video_embedURL,imageURL,description,visibility,rating_visibility,comments_allowed,blocked,views,created,owner_username,deleted) VALUES('Marvel-Black panther','<iframe width="100%" height="100%" src="https://www.youtube.com/embed/xjDjIWPwcPU" frameborder="0" allow="autoplay; encrypted-media" allowfullscreen></iframe>', 'images/videoImages/Marvel - Black Panther.jpg', 'Black panther movie trailer', 'PUBLIC',1,1,0,130,'2018-01-01','milos',0);


CREATE TABLE COMMENT(
	comment_id BIGINT AUTO_INCREMENT,
	content VARCHAR(1000) NOT NULL,
	created DATE NOT NULL,
	user_username VARCHAR(30) NOT NULL,
	video_id BIGINT NOT NULL,
	PRIMARY KEY(comment_id),
	FOREIGN KEY(video_id) REFERENCES VIDEOS(video_id)
);

INSERT INTO COMMENT(content, created, user_username, video_id) VALUES('This video is awesome', '2018-01-01', 'nikola', 1);
INSERT INTO COMMENT(content, created, user_username, video_id) VALUES('Yeah really', '2018-01-01', 'marko', 1);
INSERT INTO COMMENT(content, created, user_username, video_id) VALUES('Can we share this ????', '2018-01-01', 'pera', 1);
INSERT INTO COMMENT(content, created, user_username, video_id) VALUES('This video is awesome', '2018-01-01', 'lazar', 2);
INSERT INTO COMMENT(content, created, user_username, video_id) VALUES('This video is awesome', '2018-01-01', 'steva', 2);
INSERT INTO COMMENT(content, created, user_username, video_id) VALUES('This video is awesome', '2018-01-01', 'marko', 3);
INSERT INTO COMMENT(content, created, user_username, video_id) VALUES('This video is awesome', '2018-01-01', 'sima', 3);
INSERT INTO COMMENT(content, created, user_username, video_id) VALUES('This video is awesome', '2018-01-01', 'djura', 3);




CREATE TABLE LIKE_DISLIKE(
	like_dislike_id BIGINT AUTO_INCREMENT,
	is_like BIT NOT NULL,
	user_username VARCHAR(30) NOT NULL,
	created DATE NOT NULL,
	video_comment ENUM('VIDEO', 'COMMENT'),
	video_comment_id BIGINT NOT NULL,
	PRIMARY KEY(like_dislike_id),
	FOREIGN KEY (user_username) REFERENCES USERS(username)
);

INSERT INTO LIKE_DISLIKE(is_like,user_username,created,video_comment,video_comment_id) VALUES(1,'djura', '2018-02-02', 'VIDEO', 1);
INSERT INTO LIKE_DISLIKE(is_like,user_username,created,video_comment,video_comment_id) VALUES(1,'milos', '2018-02-02', 'COMMENT', 1);
INSERT INTO LIKE_DISLIKE(is_like,user_username,created,video_comment,video_comment_id) VALUES(1,'marko', '2018-02-02', 'COMMENT', 2);
INSERT INTO LIKE_DISLIKE(is_like,user_username,created,video_comment,video_comment_id) VALUES(1,'lazar', '2018-02-02', 'COMMENT', 2);
