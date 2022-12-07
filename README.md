# 페이히어 과제테스트 - MoneyBook
http://moneybook.chanwookim.me/


## 요구사항
```text
고객은 본인의 소비내역을 기록/관리하고 싶습니다.
아래의 요구사항을 만족하는 DB 테이블과 REST API를 만들어주세요. 
```

- 회원
  - jwt
  - email / password
  - signup / login / logout

- 가계부 
  - 가계부(사용한 돈, 관련 메모)
  - POST / UPDATE / DELETE / GET List / GET One
  - 로그인 하지 않은 사람 접근 제한

- Docker를 이용하여 실행할 수 있도록 구성



## 개발 환경

---
- java-11-openjdk-devel
- docker-compose-1.27.4
- docker-1.13.1
- SpringBoot-2.7.7
- mysql 5.7

- spring-boot-starter
  - web
  - data jpa
  - validation
- jjwt
- jbcrypt
- openapi-ui(swagger)
- h2
- mysql


## 빌드 및 실행
- jdk11 설치
- docker, docker-compose 설치

시작
> docker-compose up -d

종료
> docker-compose stop


## 예상 실행순서
1. /auth/signup : 회원가입
2. /auth/login : 로그인 해서 token을 받음.
3. 2에서 받은 access token을 Authorization Header에 설정하여 각 api를 요청
