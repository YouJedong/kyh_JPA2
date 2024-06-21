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

2. 라이브러리 설치