# 일상을 공유하는 일상 게시판


<img src="/uploads/78cd93ddc32431e23d4c62e6acbf36e8/image.png" align="center" height="350" width="600"/>

- 배포 URL : http://34.47.115.99:8080/boards


## 프로젝트 소개

- 각 카테고리 별로 일상과 관련된 게시판을 만들고, 게시글과 댓글을 통해 서로 소통하는 웹페이지 프로젝트입니다.
- 로그인을 통해 본인만의 게시글과 댓글을 관리하여 등록하고 수정, 삭제 할 수 있습니다.
- 로그인하지 않은 비회원은 게시글과 댓글을 등록할 수 있으나 수정, 삭제를 할 수 없습니다.
- 회원은 개인정보 수정, 탈퇴하기 버튼을 통해 수정 및 탈퇴를 할 수 있습니다.
- 관리자는 모든 게시판, 게시글, 댓글에 대해 CRUD 할수 있습니다.


## 팀원 구성

- 나용진 
- 한현
- 이찬진
- 임서현


## ERD와 와이어프레임

- ERD

<img src="/uploads/1ce1a1008b89bc570a5d931b47a08421/image.png" align="center" height="600" width="600"/>

- 와이어프레임

<img src="/uploads/d4a83e4575efe9be1fc2f295b1018f0c/image.png" align="center" height="350" width="600"/>


## 개발환경


- 스프링부트 : ver3.3.2
- 자바 : JDK22
- MySQL : ver8.0.39

## 의존성 라이브러리


- JPA : org.springframework.boot:spring-boot-starter-data-jpa
- thymeleaf : org.springframework.boot:spring-boot-starter-thymeleaf
- Web : org.springframework.boot:spring-boot-starter-web
- Lombok : org.projectlombok:lombok
- mapStruct : org.mapstruct:mapstruct:1.6.0
- mysql : com.mysql:mysql-connector-j
- devtools(선택) : org.springframework.boot:spring-boot-devtools
- h2(선택) : com.h2database:h2

## 주요 기능


### 회원

- 로그인, 로그아웃 기능
  - 세션을 이용하여 구현
  - 중복되지 않은 닉네임과 이메일을 사용 그렇지않으면 예외발생시키기
  - 비밀번호 입력시 8~16자 이상 특수문자를 적어야만 회원가입 가능
  - 관리자 선택시 관리자 코드 입력
- 개인정보 수정 및 회원 탈퇴 기능
  - 비밀번호 입력시 8~16자 이상 특수문자를 적어야만 수정가능
  - 관리자 선택시 관리자코드입력
  - soft delete를 사용하여 회원 탈퇴하기 기능 구현
- 관리자, 회원 역할 기능
  - 관리자는 모든 게시판, 게시글, 댓글에 대해 추가, 수정, 삭제가 가능하고 회원목록을 조회할 수 있음
  - 회원은 게시글과 댓글에 대해 추가, 수정, 삭제가 가능하나 본인이 작성한 글에 한정함.
  - 비회원은 게시글과 댓글에 대해 추가할 수 있으나 수정, 삭제가 불가능
- 비밀번호 찾기
  - 사용자의 이름과 이메일을 입력할 경우 비밀번호 제공하기
  - 탈퇴한 이메일인 경우에는 제공하지 않기

### 게시판

- 게시판 추가 및 수정, 삭제
  - 중복된 게시판 제목이 있는 경우 다시 작성하기
  - 이미지 추가 기능
  - 이미지를 넣지 않은 경우 기본적인 그림 보여주기

### 게시글

- 검색기능
- 페이지네이션
  - 10개의 페이지가 넘어가는경우 페이지를 넘기도록 구현
  - 이전, 다음 버튼을 추가하여 최대 10페이지만 보이도록 구현
- 게시글 추가 및 수정, 삭제
  - tinyMCE editor를 사용하여 게시글 안에도 이미지를 추가 가능

### 댓글

- 댓글 추가 및 수정, 삭제
- 댓글 최신순 정렬
- 조회수
  - 쿠키를 사용하여 새로고침을 해도 조회수가 계속 늘어나는 것을 방지
  
