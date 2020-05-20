

## 개발 환경

* IDEA: Intellij Ultimate

* 언어: Java (jdk8)

* 웹 프레임워크: Spring boot 2.3

* ORM: JPA (Hibernate)

  * gradle dependency

    ``` groovy
    dependencies {
    	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    	runtimeOnly 'com.h2database:h2'
      ...
    }
    ```

* 테스트 프레임워크: JUnit 5

  * gradle dependency

    ``` groovy
    dependencies {
      testImplementation('org.springframework.boot:spring-boot-starter-test')
      ...
    }
    
    test {
        useJUnitPlatform()
    }
    ```

    



## 개발

* Java
  * Annotation (e.g. @Override)
  * Java 8 (stream 등)
* 설계
  * DDD, TDD
  * 객체지향 프로그래밍
  * 클린 코드
  * 디자인패턴
  * SOLID 원칙 [(e.g. DIP)](https://velog.io/@amobmocmo/Software-Design-DIP-Dependency-Inversion-Principle-mok20fv2y3)

* 스프링 프레임워크
  * Spring vs Spring boot
  * Dependency Injection (★ x 550000000)
  * MVC
  * @RestController vs @Controller
  * @RequestBody, @RequestParam, @ResponseBody, @PathVariable
  * @Controller, @Service, @Repository
  * @Bean, @Component
  * @Configuration
* 웹
  * HTTP
  * 요청 메서드(GET, POST, PUT, DELETE ...)
  * 헤더
  * 바디
  * Server-side rendering

* src/main/resources/application.yml 파일 만들기

  ```
  server.port: 8080
  spring:
    jpa:
      hibernate:
        ddl-auto: update
      properties:
        hibernate:
          dialect:
    h2:
      console:
        enabled: true
    datasource:
      url: jdbc:h2:mem:testdb
      driver-class-name: org.h2.Driver
      username: sa
      password: password
  ```

  

## 테스트

* 프로젝트 열어보면 src/test/java ~~ 있는데, src/main/java~~와 같은 경로에 테스트 만들면 됨 (인텔리제이쓰면 클래스 들어가서 클래스 이름 위에서 alt + enter 누르면 만들 수 있음)

  ```shell
  src/main/java/com/.../AClass.java
  src/test/java/com/.../AClassTest.java
  ```

* JUnit 5부터는 테스트 클래스에 접근지정자 안 붙임

* 메서드 위에 @Test Annotation 붙여야됨

* ``` java
  public class Email {
    private String email;
    
    public Email(String email) {
      validateEmail(email);
      this.email = email;
    }
    
    public void validateEmail(String email) {
      if (Objects.isNull(email)) {
        throw new IllegalArgumentException("email이 없습니다.");
      }
    }
    
    public getLength() {
      return this.email.size();
    }
  }
  
  class EmailTest {
    @Test
    void getLength() {
      Email email = new Email("email@test.com");
      assertThat(email.getLength()).isEqualTo(14);
    }
    
    @Test
    void validateEmail_success() {
      String email = "email@test.com";
      assertDoesNotThrow(() -> new Email(email));
    }
    
    @Test
    void validateEmail_fail_null() {
      String email = null;
      assertThrows(IllegalArgumentException.class, () -> new Email(email));
    }
  }
  ```



## DB

* JPA 사용 (spring-data-jpa)

* ```java
  @Repository
  public interface UserRepository extends JpaRepository<User, Long> {
  }
  ```

* 위와 같이 인터페이스 만들고, 사용할 곳(대부분 Service)에서 위 인터페이스 DI 해서 사용가능
  * 메서드는 DI해서 보셈
* 로컬에서만 할거니까 DB는 h2 쓰셈 (on memory db)
  * build.gradle dependencies에`runtimeOnly 'com.h2database:h2'` 추가
  * 서버 띄우고 웹에서 http://localhost:8080/h2-console 들어가서 위에 application.yml 파일에 username, password 입력하면 db 볼 수 있음



 

## 과제 - 블로그 만들기

* [Git](https://github.com/woowacourse/jwp-blog) Clone

* ## 블로그 관련 기능 TODO

  ### 게시글 작성
  - [ ] 게시글 작성페이지 이동 : ``GET /writing`` 요청 시 200 OK
  - [ ] 게시글 작성 : ``POST /articles``로 요청시 작성한 게시글 페이지로 이동

  ### 게시글 생성
  - [ ] 제목, 배경 이미지 url, 내용이 모두 입력되었을 때 -> 정상 처리
  - [ ] 제목, 내용이 입력되지 않았을 때 -> 에러 발생
  - [ ] 배경 이미지 url이 입력되지 않았을 때 -> 정상 처리 (기본 값을 설정)

  ### 게시글 조회
  - [ ] 존재하는 게시글 id에 접근 했을 때 -> 정상 처리
  - [ ] 존재하지 않는 게시글 id에 접근 했을 때 -> 에러 발생

  ### 게시글 수정
  - [ ] 게시글 수정 페이지 이동 : ``GET /articles/{articleId}/edit``으로 요청 시 200 OK 
          <br>(게시글 수정 페이지(article-edit.html)로 이동)
  - [ ] 게시글 수정 : ``PUT /articles/{articleId}``로 요청시 작성한 게시글 페이지로 이동
  - [ ] 존재하는 게시글 수정 (update) -> 정상 처리
  - [ ] 존재하지 않는 게시글 수정 요청 -> 에러 발생

  ### 게시글 삭제
  - [ ] 게시글 페이지(article.html)에서 삭제 버튼 클릭 시 ``DELETE /articles/{articleId}`` 으로 요청
  - [ ] 게시글 삭제 후 게시글 목록 조회 페이지(index.html)로 이동
  - [ ] 존재하는 게시글 삭제 (delete) 요청 -> 정상 처리
  - [ ] 존재하지 않는 게시글 삭제 요청 -> 에러 발생

  ### 제약조건
  - [ ] HTML 중복 제거
  - [ ] 정적 파일 수정 시 재시작하지 않고 변경사항 반영하기
  - [ ] class 파일 수정 시 자동으로 재시작하기

  ### 기타
  - [ ] 커스텀 익셉션 만들기

  ### 피드백
  - [ ] Article 클래스에 update구현
  - [ ] 테스트 코드 중복 제거
  - [ ] 필드 인젝션 -> 생성자 인젝션
  - [ ] PathVariable 자료형 통일 (Integer/int)

  ## 회원 관련 기능

  ### 회원 등록
  - [ ] 회원가입페이지(signup.html) 에서 ``POST /users`` 로 요청하면
      DB에 user정보 저장
  - [ ] 저장 후 로그인 화면으로 redirect
  - [ ] 실패 시 사용자에게 알려준다
  - [ ] 회원가입 관련 html 수정 (signup.html)

  ### 회원 가입 규칙
  - [ ] 동일한 email 중복 가입 시도 -> 에러
  - [ ] 이름은 2~10자, 숫자나 특수문자가 포함된 경우 -> 에러
  - [ ] 비밀번호는 8자 이상, 소문자, 대문자, 숫자, 특수문자의 조합이 아닌 경우 -> 에러
  - [ ] 비밀번호 확인 기능이 동작

  ### 회원 조회
  - [ ] ``GET /users``로 요청하여 회원 목록 페이지(user-list.html) 이동
  - [ ] 회원 목록 페이지에서 DB에 저장된 회원 정보 확인
  - [ ] user-list.html 수정

  ### 기타
  - [ ] MySql로 DB 변경
  - [ ] 실행 쿼리 보기 설정하기
  - [ ] update CrudRepository의 save 대안 찾아보기
  - [ ] 테스트 코드 독립성 - (테스트 메서드마다 DB 롤백할 수 있는 방법 찾아보기)

  ### 로그인
  - [ ] 로그인 성공 시 메인 화면(index.html)으로 redirect
  - [ ] 로그인 성공 시 메인화면 우측 상단에 사용자 이름을 띄운다.
  - [ ] 해당 이메일이 없는 경우 -> 에러
  - [ ] 비밀번호 불일치 경우 -> 에러
  - [ ] 로그아웃 요청 시 메인 화면(index.html)으로 redirect
  - [ ] 로그인 한 유저가 로그인/회원가입 화면에 접근할 경우 메인 화면(index.html)으로 redirect
  - [ ] 로그인 한 유저가 로그인/회원가입 화면에 접근할 경우 메인 화면(index.html)으로 redirect 테스트!
  - [ ] 로그인하지 않은 유저가 로그인/회원가입 화면에 접근할 경우 200 OK

  ### 회원 수정
  - [ ] ``GET /mypage`` 로 요청시 회원 정보 페이지로 이동 (200 OK)
  - [ ] ``GET /mypage/edit`` 로 요청시 회원정보 수정페이지로 이동 (200 OK)
  - [ ] ``POST /mypage``로 요청시 회원 정보 수정하고 ``GET /mypage`` 로 redirect  
  - [ ] 회원 정보 수정 테스트 코드!

  ### 회원 탈퇴
  - [ ] MyPage > profile 하단 > 탈퇴 버튼 추가
  - [ ] 탈퇴버튼 클릭시 ``DELETE /users`` 로 요청 ~ 탈퇴 처리 후 ``GET /`` 로 redirect

  ## 게시글 추가 기능

  1. 게시글 작성 관련 요청 (``GET /articles/writing``, ``POST /articles/write``)
      - [ ] 로그인한 사용자 -> 정상 동작 (원래대로)
      - [ ] 로그인하지 않은 사용자 -> 로그인 요청 (로그인페이지로 redirect)
      
  2. 게시글 수정 / 삭제 관련 요청 (``GET /articles/{articleId}/edit``, ``PUT /articles/{articleId}``, ``DELETE /articles/{articleId}``)
      - [ ] 로그인하지 않은 사용자 -> 에러 발생 (로그인페이지로 redirect)
      - [ ] 작성자인 경우 -> 정상 동작 (원래대로)
      - [ ] 작성자가 아닌 경우 -> 에러 발생 (홈으로 이동할때 alert 창 발생)
      
  ## 댓글 관련 기능

  - [ ] 댓글 보기 : 게시글 조회할 때 연관 댓글 함께 보여주기
  ### 댓글 작성
  - [ ] ``POST /comment/{articleId}``
  - [ ] ``PUT /comment/{commentId}/``
  - [ ] ``DELETE /comment/{commentId}/``

  ### 예외처리 나누기
  - [ ] 서비스 / 도메인 영역 분리하여 예외처리 다르게 하기
      - [ ] LoginFailedException.class service / argument resolver
      - [ ] MismatchAuthorException comment/ article 분리 및 findByIdAnd*** 삭제   

  ### 댓글 Ajax 구현
  - [ ] ``POST /comment/{articleId}``
      - [ ] 해당 내용이 포함되었는지 테스트
  - [ ] ``PUT /comment/{articleId}``
      - [ ] 수정 내용이 포함되었는지 테스트
      - [ ] 작성자 일 경우 HttpStatus 200 확인
      - [ ] 작성자가 아닐 경우 HttpStatus 403 확인
  - [ ] ``DELETE /comment/{articleId}``
      - [ ] 작성자 일 경우 HttpStatus 200 확인 
      - [ ] 작성자가 아닐 경우 HttpStatus 403 확인

  ### 추가 Ajax 구현
  - [ ] ``POST /login`` 로그인 Ajax로 구현
  - [ ] ``POST /users`` 회원가입 Ajax로 구현