### 프로젝트 생성

1. [start.spring.io](http://start.spring.io) 들어가기
2. project : gradle -groovy
3. language : java
4. Spring boot : 3.3.0
5. Project Metadata
    - ….
6. Dependencies
    - Spring web
    - Tyhmeleaf
    - Spring data JPA
    - H2 Database
    - lombok

### 프로젝트에서 lombok plugin 설치

1. 인텔리j 인터넷 찾아서 하기
2. 어노테이션 프로세서 활성화 시키기
### 

### Spring boot starter 핵심 라이브러리

- 스프링 MVC
- 스프링 ORM
- JPA, 하이버네이트
- 스프링 데이터 JPA

### 기타 라이브러리

- H2 db
- 커넥션 풀: 부트 기본은 HikariCP
- WEB(timeleaf)
- 로깅 SLF4J(인터페이스) & LogBack
- 테스트

### 참고 사이트

- thymeleaf : https://www.thymeleaf.org/
- 스프링 공식 튜토리얼 : https://spring.io/guides/gs/serving-web-content
- 스프링 부트 메뉴얼 : https://docs.spring.io/spring-boot/reference/web/servlet.html#web.servlet.spring-mvc.template-engines

*개발 시 도움이 되는 라이브러리

```jsx
implementation 'org.springframework.boot:spring-boot-starter-devtools'
```

- 소스코드를 수정하고 build > recomplie ~~ (ctrl + shift + F9)