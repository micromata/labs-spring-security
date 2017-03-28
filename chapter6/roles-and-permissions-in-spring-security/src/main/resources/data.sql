insert into user(id,username,password,role) values (0,'max','password', 'ROLE_USER');
insert into user(id,username,password,role) values (1,'tom','password', 'ROLE_USER');
insert into user(id,username,password,role) values (2,'admin','password', 'ROLE_ADMIN');

insert into message(id,user_id,title,text) values (100,0,'Message for Max','This message is for Max. Under /message/100 only Max or an admin should see this message. Under /privateMessage/100 only Max should see this message.');
insert into message(id,user_id,title,text) values (110,1,'Message for Tom','This message is for Tom. Under /message/110 only Tom or an admin should see this message. Under /privateMessage/110 only Tom should see this message.');