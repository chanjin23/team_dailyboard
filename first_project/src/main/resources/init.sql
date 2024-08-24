-- insert 1 board id=1

INSERT INTO board
(description,name,type)
VALUES ('description','board','자유');

-- insert 1 member id=1

INSERT INTO member
(created_time,updated_time,email,name,nickname,password)
VALUES (now(),now(),'test@example.com','홍길동','monkey','1234');

-- insert 2 member id =2

INSERT INTO member
(created_time,updated_time,email,name,nickname,password)
VALUES (now(),now(),'chanjin23@naver.com','이찬진','이찬진','123');

-- insert 14 post (member_id=1, board_id=1)

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

INSERT INTO post
(member_id,board_id,created_time,updated_time,content,title)
VALUES (1,1,now(),now(),'content','title');

-- insert 1 comment (member_id=1, post_id=1)

INSERT INTO comment
(member_id,post_id,created_time, updated_time, content)
VALUES (1,1,now(), now(), 'test');

