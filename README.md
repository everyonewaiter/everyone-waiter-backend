# 모두의 웨이터 백엔드

**모두의 웨이터는 수수료가 부담되는 사장님들을 위한 테이블 오더 서비스입니다.**

- **NO 약정**, **NO 위약금**으로 부담없이 사용할 수 있습니다.
- 필요한 홀 인원의 감소로 **인건비 절감**이 가능합니다.

## 목차

- [기술 스택](#기술-스택)
- [설정 파일](#설정-파일)
- [시작하기](#시작하기)
- [도커 컴포즈로 시작하기](#도커-컴포즈로-시작하기)
- [코드 컨벤션](#코드-컨벤션)

## 기술 스택

- 런타임: JDK 21
- 프레임워크: Spring Boot 3.4.0
- ORM: Spring Data JDBC 3.4.0
- DB
    - MySQL 8.4.3
    - Redis 7.4.1
- 테스트: Mockito
- API 문서: Spring REST Docs

## 설정 파일

프로젝트의 `src/main/resources`의 `application.properties`를 설정합니다.

- 프로필
    - `spring.profiles.active`: `local`, `dev`, `prod` 중 하나를 입력하여야 합니다.
- 메일
    - `spring.mail.host`: 메일 서버 SMTP 호스트를 입력합니다.
        - 예) `spring.mail.host=smtp.gmail.com`
    - `spring.mail.username`: 메일 서버 사용자 이름을 입력합니다.
        - 예) `spring.mail.username=handwoong@gmail.com`
    - `spring.mail.password`: 메일 서버 사용자 비밀번호를 입력합니다.
        - 예) `spring.mail.password=<구글 계정의 앱 비밀번호>`
- 알림톡
    - 상세 내용은 [네이버 클라우드 플랫폼 알림톡 API](https://api.ncloud-docs.com/docs/ko/ai-application-service-sens-alimtalkv2)를 참고하세요.
        - `naver.sens.access-key`: 네이버 클라우드 플랫폼 SENS 액세스 키를 입력합니다.
        - `naver.sens.secret-key`: 네이버 클라우드 플랫폼 SENS 시크릿 키를 입력합니다.
        - `naver.sens.service-id`: 네이버 클라우드 플랫폼 SENS 프로젝트 서비스 ID를 입력합니다.
        - `naver.sens.channel-id`: 카카오톡 채널 ID를 입력합니다.

이후 `application.properties`에서 설정한 프로필에 맞는 `properties` 파일을 설정합니다. 예) `application-local.properties`

- 클라이언트 CORS 허용 목록
    - `allow.client.urls`: CORS 정책을 허용할 클라이언트 URL들을 입력합니다.
        - 예) `allow.client.urls=http://localhost:3000,http://localhost:5173`
- 데이터베이스 연결
    - `spring.datasource.url`: MySQL 데이터베이스 주소를 입력합니다.
    - `spring.datasource.username`: MySQL 사용자를 입력합니다.
    - `spring.datasource.password`: MySQL 비밀번호를 입력합니다.
    - `spring.data.redis.host`: Redis 호스트 주소를 입력합니다.
    - `spring.data.redis.port`: Redis 포트를 입력합니다.
    - `spring.data.redis.password`: Redis 비밀번호를 입력합니다.
- JWT
    - `jwt.secret.key`: JWT 시크릿 키를 입력합니다. [여기](https://jwt-keys.21no.de)에서 간단하게 시크릿 키를 생성할 수 있습니다.

## 시작하기

JDK 21버전, MySQL, Redis 설치가 되어있어야 합니다.

1. 저장소 복제하기

```bash
git clone https://github.com/everyonewaiter/everyone-waiter-backend.git
```

2. [설정 파일](#설정-파일)을 참고하여 설정 파일 작성
3. 빌드

```bash
cd everyone-waiter-backend
./gradlew clean build
```

4. 실행하기

```bash
java -jar ./build/libs/everyonewaiter-<version>.jar
```

## 도커 컴포즈로 시작하기

JDK 21버전 및 도커가 설치되어 있어야 합니다.

1. 저장소 복제하기

```bash
git clone https://github.com/everyonewaiter/everyone-waiter-backend.git
```

2. [설정 파일](#설정-파일)을 참고하여 설정 파일 작성

**설정 파일의 MySQL 데이터베이스 주소 및 Redis 호스트 주소는 아래와 같이 입력해야합니다.**

```properties
spring.datasource.url=jdbc:mysql://mysql:3306/everyone_waiter?rewriteBatchedStatements=true
spring.data.redis.host=redis
```

3. 빌드

```bash
cd everyone-waiter-backend
./gradlew clean build
```

4. 실행하기

```bash
docekr compose up --build -d
```

5. 종료하기

```bash
docker compose down
```

## 코드 컨벤션

[네이버 캠퍼스 핵데이 Java 코딩 컨벤션](https://naver.github.io/hackday-conventions-java/)을 준수합니다.

아래는 `IntelliJ IDEA`를 기준으로 설정하는 방법입니다.

1. [여기](https://github.com/naver/hackday-conventions-java/tree/master/rule-config)에서 `naver-intellij-formatter.xml`,
   `naver-checkstyle-rules.xml`,
   `naver-checkstyle-suppressions.xml`를 다운로드 합니다.
2. IntelliJ 플러그인 설치: `CheckStyle-IDEA`, `SonarQube for IDE`, `Save Actions X`
3. 포매터 설정

`Settings` -> `Editor` -> `Code Style`로 이동합니다. (아래 사진 참고)

<img width="979" alt="image" src="https://github.com/user-attachments/assets/000bd9ec-8ec8-40ef-8757-719e343cde1b" />

위 사진의 설정 경로에서 다운로드 받은 `naver-intellij-formatter.xml`을 적용합니다.

4. `CheckStyle-IDEA` 설정

`Settings` -> `Tools` -> `Checkstyle`로 이동하여 `naver-checkstyle-rules.xml` 및 `naver-checkstyle-suppressions.xml`을 적용합니다.

적용 결과는 아래 사진과 같아야 합니다.

<img width="980" alt="image" src="https://github.com/user-attachments/assets/7ddbcef4-4d04-4db0-aa66-20436c18aea6" />

5. `Save Actions X` 설정

`Settings` -> `Other Settings` -> `Save Action`으로 이동하여 아래 사진과 같이 설정합니다.

![image](https://github.com/user-attachments/assets/9eef81e6-491f-46bc-a3b3-85d4b78d39f4)

<img width="746" alt="image" src="https://github.com/user-attachments/assets/05ea0ad2-45a3-4336-a7a9-dd3e455d8617" />
