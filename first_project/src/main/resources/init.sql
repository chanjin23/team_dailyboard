INSERT INTO board
(description,name,type)
VALUES ('description','board','자유');

INSERT INTO member
(created_time,updated_time,email,name,nickname,password)
VALUES (now(),now(),'test@example.com','홍길동','monkey','1234');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');


INSERT INTO comment
(member_id,post_id,created_time, updated_time, content)
VALUES (1,1,now(), now(), 'test');

