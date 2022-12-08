# 페이히어 과제테스트 - MoneyBook

Swagger-ui : 
http://moneybook.chanwookim.me/swagger-ui/index.html


## 요구사항

```text
고객은 본인의 소비내역을 기록/관리하고 싶습니다.
아래의 요구사항을 만족하는 DB 테이블과 REST API를 만들어주세요. 
```

- 회원
  - 회원가입 기능 (이메일, 비밀번호)
  - 토큰(JWT) 기반 로그인, 로그아웃 기능

- 가계부 
  - 가계부(사용한 돈, 관련 메모) 관리 기능
    - 기록을 추가(POST)
    - 기록을 수정(UPDATE) 
    - 기록을 삭제(DELETE) 
    - 기록 목록 가져오기(GET List) 
    - 기록 가져오기(GET One)
  - 각 기능 호출 시 인증 처리(로그인 하지 않은 사람 접근 제한)
- 테스트 코드 작성
- Docker를 이용하여 실행할 수 있도록 구성(docker-compose)
- Swagger 설정

## 환경 및 의존성 패키지

- openjdk-11.0.17
- docker-1.13.1
- docker-compose-1.27.4


- SpringBoot-2.7.7
  - spring-boot-starter
    - web
    - data jpa
    - validation
  - jjwt
  - jbcrypt
  - openapi-ui(swagger)
  - h2 driver
  - mysql driver


- mysql 5.7


## 빌드 및 실행
- docker, docker-compose 설치

### 프로젝트 실행

```shell
git clone https://github.com/chanwooo/payhere-test.git
cd payhere-test
./start.sh
```

### 종료
```shell
./stop.sh
```

## 예상 실행순서

1. /auth/signup : 회원가입
2. /auth/login : 로그인 해서 token을 받음.
3. 2에서 받은 access token을 Authorization Header에 설정하여 각 api를 요청

