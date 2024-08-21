INSERT INTO board
(id,description,name,type)
VALUES (1,'description','board','자유');

INSERT INTO member
(id,created_time,updated_time,email,name,nickname,password)
VALUES (1,now(),now(),'test@example.com','홍길동','monkey','1234');

INSERT INTO post
(member_id,board_id,created_time,updated_time,id,content,title)
VALUES (1,1,now(),now(),1,'content','title');


INSERT INTO comment
(member_id,post_id,created_time, updated_time, content)
VALUES (1,1,now(), now(), 'test');

