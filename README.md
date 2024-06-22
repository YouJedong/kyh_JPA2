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
### H2 and JPA 셋팅

- application.yml 생성

    ```sql
    spring:
      datasource:
        url: jdbc:h2:tcp://localhost/~/jpashop;MVCC=TRUE
        username: sa
        password:
        driver-class-name: org.h2.Driver
    
      jpa:
        hibernate:
          ddl-auto: create
        properties:
          hibernate:
            #show_sql: true // sysou으로 로그를 찍어주기 때문에 이거보다는 logging으로 찍는게 좋다.
            format_sql: true
            
     logging:
      level:
        org.hibernate.SQL: debug   // 하이버네이트가 실행한 모든 SQL이 보임
    ```


### Entity 생성 후 Repository 만들기

```java
@Repository
public class MemberRepository {

    @PersistenceContext // 이렇게 셋팅해주면 EntityManager 설정 끝
    private EntityManager em;
    
    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
```

- 테스트 해보기 → Junit4
   1. repository에서 cmd + shift + t
   2. Junit4 선택하기 finish

    ```java
    @RunWith(SpringRunner.class) // Spring과 관련된 테스트를 할꺼야! 라고 알림
    @SpringBootTest // SpringBoot로 테스트 하겠다!
    public class MemberRepositoryTest {
    
    		// jpaShop > MemberRepositoryTest.java 확인
    
    }
    ```


### 로깅 좀 더 자세하게 나오는 방법 2가지

1. ?으로 나오는 쿼리 파라미터의 값을 바로 밑에 로그로 알려줌

    ```java
    // application.yml
    
    logging:
      level:
        org.hibernate.SQL: debug
        org.hibernate.orm.jdbc.bind: trace //이렇게 셋팅
    ```

2. 라이브러리 설치 (p6spy)
    - 로그에서 쿼리 파라미터를 포함한 실제 쿼리로 보여주는 라이브러리
    - 스프링부트 3.0 이상부터는 설정이 더 추가된다
        - 참고 사이트 https://curiousjinan.tistory.com/entry/spring-boot-3-p6spy-jpa-logging
        1. 의존성 추가

            ```java
            //build.gradle
            implementation 'p6spy:p6spy:3.9.1'
            implementation 'com.github.gavlyukovskiy:datasource-decorator-spring-boot-autoconfigure:1.9.0'
            ```

        2. src/main/resource 에 META-INF/spring/ 폴더 추가
        3. org.springframework.boot.autoconfigure.AutoConfiguration.imports 라는 이름으로 파일 생성

            ```java
            // org.springframework.boot.autoconfigure.AutoConfiguration.imports파일 안에 아래 코드 입력
            com.github.gavlyukovskiy.boot.jdbc.decorator.DataSourceDecoratorAutoConfiguration
            ```

        4. application.yml 파일에 설정 추가

            ```yaml
            spring:
              datasource:
                url: jdbc:h2:tcp://localhost/~/jpashop;
                driver-class-name: org.h2.Driver
                username: sa
                password:
                // 아래 부분을 추가해라
                p6spy: 
                  # JDBC 이벤트 로깅을 위한 P6LogFactory 등록
                  enable-logging: true
                  # com.p6spy.engine.spy.appender.MultiLineFormat 사용 (SingleLineFormat 대신)
                  multiline: true
                  # 기본 리스너들을 위한 로깅 사용 [slf4j, sysout, file, custom]
                  logging: slf4j
                  # 실제 값으로 '?'를 대체한 효과적인 SQL 문자열을 추적 시스템에 보고
                  # 참고: 이 설정은 로깅 메시지에 영향을 주지 않음
                  tracing:
                    include-parameter-values: true
            ```