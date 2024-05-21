# TODO 앱 기본 구성

## 목차
1. [전체 적인 앱 구상도
](#1-앱-구상도)

2. [ERD](#2-erd)

3. [각 클래스의 구조도](#3-각-클래스의-구조도)

4. [HotsPot](#4-hotspot)

5. [기타 버그 및 수정 사항](#5-기타-버그-및-수정-사항)


### 1. 앱 구상도
![앱 기획 다이어그램](https://github.com/Ppajingae/HW_todo/blob/dev/src/main/resources/templates/image/Application%20%EA%B8%B0%ED%9A%8D.svg)

<details>
<summary style="font-size: 18px; font-weight: bold"> 1. 전체 적인 앱 기능</summary>
<div markdown="1">     

**전체 적으로 크게 2개의 aggregate 로 분류를 하였습니다**

#### TODO List Aggregate

- TODO List Aggregate 는 큰 시각 에서 봤을 때 유저의 할일을 CRUD 로 받아 주는 기능이 있습니다

- 크게 2가지의 기능 으로 나뉘며 메인 으로 할 일들을 받아주고 처리 해주는 TODO 기능과 해당 기능에 코맨트를 달 수 있는 코맨트 기능이 있습니다

##### TODO

- TODO 는 전체 적인 할 일을 CRUD 해주는 기능 입니다

- 각 유저는 자신의 정보를 CRUD 할 수 있고 관리자는 전체 정보를 CRUD 할 수 있습니다

- 추가적 으로 관리자는 특정 유저의 글을 수정 / 삭제가 가능 합니다

- 전체 정보 조회는 금일 날짜만 조회하는 기능과 전체 할일 리스트를 조회하는 기능을 추가할 예정 입니다

##### Comment

- Comment 는 user의 댓글을 CRUD 해주는 기능 입니다

- 댓글의 수정은 user 만 할 수 있습니다, 관리자는 댓글을 수정 하지 못하고 바로 삭제만 가능 합니다

##### User Aggregate

- User는 사용자의 이메일과 비밀번호를 로그인 하여 프로필을 수정 후에 유저가 로그아웃 하는 작업을 진행 합니다

- 추가로 관리자는 프로필을 수정할 시에 일반 유저의 정보를 수정할 수 있으며 (비밀번호 제외) 유저 에게 관리자 권한을 부여할 수 도 있다

- 관리자는 일반 유저의 정보를 삭제할 수 있다
</div>
</details>


### 2. ERD

```mermaid
erDiagram

User ||--o{ Comment : userId
Todo ||--o{ Comment : todoId
User ||--o{ Todo : userId


User{
    BIGINT id PK

    TEXT email

    TEXT password

    TEXT nickname

    TEXT is_admin

}

Comment{
    BIGINT id PK

    BIGINT User FK

    TEXT comment

    TIMESTAMP create_at
  
    TIMESTAMP update_at

    BIGINT todo FK
}

Todo{
    BIGINT id PK

    BIGINT user FK 

    TEXT title

    TEXT type

    TEXT importance

    TEXT content

    DATE start_time

    DATE end_time

    TIMESTAMP create_at

    TIMESTAMP update_at

}
```
- #### User
  
  - PK인 ID와 ```email```, ```password```, ```nickName``` 을 기본 적인 데이터로 사용 합니다

  - ```isAdminister``` 는 기본적 으로 ```false``` 값으로 부여할 예정 이며 관리자 권한 아래 true로 바꾸게끔 설정 된다

  - ```Comment``` 테이블과 ```Todo``` 테이블의 관계는 1 : N 관계를 유지할 생각 입니다 

  - 다만 ```Comment```와 ```Todo```는 ```null```일 수도 있기 때문에 위와 같이 작성 했습니다

  - 각각의 테이블은 ```userId```로 외래키 관계를 맺게 됩니다

- #### Comment

  - PK인 ID와 `comment` 를 기본 적인 데이터로 사용 합니다

  - `Comment` 테이블과 `Todo` 테이블, `User` 테이블의 관계는 N : 1 관계 이므로 각각 `user`와 `todo`를 사용 하여 외래키를 보관 합니다
  
  - `createAt`과 `updateAt`을 이용 하여 작성 시간과 업데이트 시간을 업데이트 예정 입니다

- #### Todo
    
  - PK인 ID와 `title`, `type`, `importance`, `content`, `startTime`, `endTime` 을 기본 적인 데이터로 사용 합니다

  - `type`과 `importance`를 통해서 할 일의 구조와 중요도를 파악 할 예정 입니다

  - `startTime`과 `endTime`을 통해서 할 일의 시작 시간과 종료 시간을 파악할 예정 입니다

  - `Comment` 테이블과는 1 : N 관계라 외래키를 `Comment`가 보관 할 예정 이지만 `User` 테이블의 관계는 N : 1 관계로 `User` 에 대한 외래키를 보관 합니다

  - `createAt`과 `updateAt`을 이용 하여 작성 시간과 업데이트 시간을 업데이트 예정 입니다

### 3. 각 클래스의 구조도

#### 3-1. TODO List Aggregate

```mermaid
classDiagram

DateTime <|-- Comment
DateTime <|-- Todo

class DateTime{
    <<abstractClass>>
    LocalDateTime createAt 
    LocalDateTime updateAt 

    fun firstDateTime()
    fun setUpdateAt()
    fun getCreateAt()LocalDateTime
    fun getUpdateAt()LocalDateTime
}

class Comment{
    <<Entity>>

    String comment
    User user
    Todo todo 
    Long? id  
    LocalDateTime createAt 
    LocalDateTime updateAt 

    @JoinColumn("user_id")
    @JoinColumn("todo_id")
    @Id("id")

    fun Comment.toResponse()CommentResponseDto
}

class Todo{
    <<Entity>>
    
    Long? id
    String title 
    TodoType type 
    Importance importance
    String? content
    User user
    MutableList[comment] comment
    LocalDateTime startTime
    LocalDateTime endTime
    LocalDateTime createAt
    LocalDateTime updateAt

    @JoinColumn("user_id")
    @OneToMany("comment")
    @Id("id")

    fun Todo.toResponse()TodoResponseDto
}
```
  - `Comment` Entity 와 `Todo` Entity 모두 `createAt`, `updateAt`을 모두 필요로 하고 `createAt`, `updateAt`을 구현 하는 로직도 비슷 합니다 따라서 `abstract class` `DateTime`을 만들어 이 둘을 동시에 관리함은 물론 추후에 필요할 경우 재정의 할 수 있는 부분 까지 대비 하였습니다
  - 각각 `toResponse`라는 확장 함수 를 구현 하여 ResponseDto 를 Return 할 수 있도록 구현 하였습니다

```mermaid
classDiagram

TodoService <|-- TodoServiceImpl

class TodoService{
    fun getTodo(todoId: Long): TodoResponseDto

    fun getTodoList()List[TodoResponseDto]

    fun getTodayTodoList(): List[TodoResponseDto]

    fun createTodo(todoCreateRequestDto: TodoCreateRequestDto): TodoResponseDto

    fun updateTodo(todoId: Long, todoUpdateRequestDto: TodoUpdateRequestDto): TodoResponseDto

    fun deleteTodo(todoId: Long)

    fun createComment(todoId: Long, commentCreateRequestDto: CommentCreateRequestDto): CommentResponseDto

    fun getAllComments(todoId: Long): List[CommentResponseDto]

    fun updateComment(todoId: Long, commentId: Long, commentUpdateRequestDto: CommentUpdateRequestDto): CommentResponseDto

    fun deleteComment(todoId: Long, commentId: Long)
}

class TodoServiceImpl{
    private TodoRepository todoRepository
    private CommentRepository commentRepository

    fun getTodo(todoId: Long): TodoResponseDto

    fun getTodoList()List[TodoResponseDto]

    fun getTodayTodoList(): List[TodoResponseDto]

    fun createTodo(todoCreateRequestDto: TodoCreateRequestDto): TodoResponseDto

    fun updateTodo(todoId: Long, todoUpdateRequestDto: TodoUpdateRequestDto): TodoResponseDto

    fun deleteTodo(todoId: Long)

    fun createComment(todoId: Long, commentCreateRequestDto: CommentCreateRequestDto): CommentResponseDto

    fun getAllComments(todoId: Long): List[CommentResponseDto]

    fun updateComment(todoId: Long, commentId: Long, commentUpdateRequestDto: CommentUpdateRequestDto): CommentResponseDto

    fun deleteComment(todoId: Long, commentId: Long)
}
```
  - 서비스는 `TodoService`를 `TodoServiceImpl`가 상속을 받으면서 안에서 `Comment` 및 `Todo`의 비즈니스 로직을 관리 합니다
  - 그외 컨트롤러 구성은 위에 서비스에 맞게 맵핑하여 구성하였습니다

#### 3-2. User Aggregate

```mermaid
classDiagram


class User{

    String email

    String password

    String nickname

    Admin isAdmin

    MutableList[Todo] todo

    MutableList[Comment] comment

    Long? id

    @OneToMany("todo")
    @OneToMany("comment")
    @Id("id")

    fun User.toResponse()UserResponseDto
}
```
 - Entity는 다른 Entity와 마찬가지로 필요한 상황에 맞게 맵핑을 해서 그게 다른 부분은 없지만 `createAt`, `updateAt`이 필요 하지 않을 것으로 판단 되어 따로 `DateTime()` 함수를 따로 상속 받지 않았습니다

 - `toResponse`라는 확장 함수 를 구현 하여 ResponseDto 를 Return 할 수 있도록 구현 하였습니다

```mermaid
classDiagram

UserService <|-- UserServiceImpl

class UserService {
    <<interface>>
    fun signUp(membershipRequestDto: MembershipRequestDto) UserResponseDto

    fun login(loginRequestDto: LoginRequestDto) UserResponseDto

    fun updateUserProfile(userId: Long, memberUpdateRequestDto: MembershipUpdateRequestDto) UserResponseDto

    fun updateAdminUserProfile(correctionId:Long, userId: Long, membershipUpdateAdminRequestDto: MembershipUpdateAdminRequestDto) UserResponseDto

    fun logout()

    fun deleteAdminUserProfile(correctionId:Long, userId: Long) UserResponseDto

    fun getAdminUserProfileList(correctionId:Long) List[UserResponseDto]
}

    



    
class UserServiceImpl{
    private UserRepository userRepository

    fun signUp(membershipRequestDto: MembershipRequestDto) UserResponseDto

    fun login(loginRequestDto: LoginRequestDto) UserResponseDto

    fun updateUserProfile(userId: Long, memberUpdateRequestDto: MembershipUpdateRequestDto) UserResponseDto

    fun updateAdminUserProfile(correctionId:Long, userId: Long, membershipUpdateAdminRequestDto: MembershipUpdateAdminRequestDto) UserResponseDto

    fun logout()

    fun deleteAdminUserProfile(correctionId:Long, userId: Long) UserResponseDto

    fun getAdminUserProfileList(correctionId:Long) List[UserResponseDto]
}
```
 - 서비스는 `UserService`를 `UserServiceImpl`가 상속을 받으면서 안에서 `User` 의 비즈니스 로직을 관리 합니다
 - 그외 컨트롤러 구성은 위에 서비스에 맞게 맵핑하여 구성하였습니다
 - `User`는 현재 시간 부족 으로 `Signup` 함수만 구현한 후에 나머지 함수는 아직 구현 되지 않았습니다 

### 4. HotsPot

 -  `user`와 `administer` 사이에 중간 권한을 추가 하여 해당 권한을 부여 받은 유저는 Todo 의 조회 / 수정만 진행 하는 방식

 - `administer` 기준 전체`user`의 할 일 전체 리스트를 조회 할 것인지 아니면 개인 것만 조회 하고 필요에 따라 다른 API 를 추가 할 것 인지에 대한 확정 사항 없음

 - 현재는 구현 되어 있지 않지만 관리자 외 접근 권한 예외를 따로 만들어서 예외 처리 할 예정 입니다


### 5. 기타 버그 및 수정 사항

#### 5-1 BeanCreationException: Ambiguous mapping. 애러 발생
- 해당 애러는 맵핑이 겹치 면서 발생한 애러 라고 한다 따라서 애러 코드를 따라서 맵핑이 겹칠 만한 부분을 찾아서 해결해 주었습니다
- 해당 애러가 발생한 원인 으로 API 설계도를 작성 할 때에 `todo`와 `comment` 사이의 URL을 중복되게 작성하여 발생한 현상 으로 해당 부분은 설계도 작성시 한번더 고려 해서 작성이 필요해 보입니다
#### AS-IS

```
@RestController
@RequestMapping("/todo")
class TodoController {

@RestController
@RequestMapping("/todo/{todo_id}")
class CommentController {
```

//2
#### TO-BE
```
@RestController
@RequestMapping("/todo")
class TodoController {

@RestController
@RequestMapping("/todo/{todo_id}/comment")
class CommentController {
```

//1
#### 5-2 LoggingFailureAnalysisReporter 애러 발생

  - 애러 내용
  ```
  Description:
  
  Parameter 0 of constructor in com.example.mytodo.domain.todo.service.TodoServiceImpl required a bean of type 'com.example.mytodo.domain.todo.repository.TodoRepository' that could not be found.
  ```
  - 해결 과정 <- 해당 문제는 튜터님들과 같이 고민 했습니다

    1. 최초 원인은 위에 애러 내용과 같이 Bean 주입의 문제로 확인 했습니다 이에 에러가 발생한 지점인 `Repository`와 `Service`, `Entity`의 Bean 등록 상태을 확인 하였습니다 : 특이 사항 없음 확인 
    2. DB의 Entity 연결 여부를 확인 하였습니다 DB가 Entity에 데이터 주입이 되어 있지 않은 상태에서 발생할 애러일 가능성도 고려하여 데이터 주입을 시도하였습니다 : 특이 사항 없음 확인
    3. Import 문을 확인 하여 외부 라이브러리가 잘못 연결 되어있는지 확인 하였습니다 : 특이 사항 없음 확인
    4. 기존에 붙어 있던 Annotation 문제가 있을 수도 있을 것으로 판단되어 Annotation 지우고 재주입 시도하였습니다 : 특이 사항 없음 확인
    5. `@AutoWired` Annotation `@ComponentScan`등을 사용 하여 Bean 직접 주입하거나 Repository 경로를 수동 으로 지정해 주었습니다 : 같은 애러 현상 지속적으로 발생 확인
    6. 코드 검사 과정에서 `build.gradle.kts`에서 빼 먹은 부분이 확인되어 빼 먹은 부분 전부 추가 진행 : 특이 사항 없음 확인
       - 추가한 코드
       ```
       noArg {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("jakarta.persistence.Embeddable")
        }
    
       allOpen {
        annotation("jakarta.persistence.Entity")
        annotation("jakarta.persistence.MappedSuperclass")
        annotation("jakarta.persistence.Embeddable")
        }
       ```
    7. Spring Boot 버전 다운그레이드 3.2.5 -> 3.1.0 : 특이 사항 없음 확인
    8. `Comment`와 `Todo` Entity가 상속 받고 있는 `DateTime` 클래스 를 상속 해제 및 해당 클래스에도 수동으로 Bean 주입을 시도하였습니다 : 특이 사항 없음 확인
    9. 새로운 프로젝트에 기본 값만 추가 후에 기존에 코드 복사 후에 붙여넣기 시도 : 같은 애러 발생 하였으나 기존 프로젝트를 처음 시도할 때 발생한 애러 발생 하여 구글링하여 찾아본 값을 `application.yml` 에 주입 <- 해당 문제는 프로젝트를 만들때 빌드하자마자 발생하였습니다
       - 발생한 애러
       ```
        org.springframework.beans.factory.UnsatisfiedDependencyException: 
       Error creating bean with name 'dataSourceScriptDatabaseInitializer' defined in class path resource [org/springframework/boot/autoconfigure/sql/init/DataSourceInitializationConfiguration.class]: 
       Unsatisfied dependency expressed through method 'dataSourceScriptDatabaseInitializer' 
       parameter 0: Error creating bean with name 'dataSource' defined in class path resource [org/springframework/boot/autoconfigure/jdbc/DataSourceConfiguration$Hikari.class]: 
       Failed to instantiate [com.zaxxer.hikari.HikariDataSource]: Factory method 'dataSource' threw exception with message: 
       URL must start with 'jdbc'
       ```
       ```
        spring:
           autoconfigure:
              exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
       ```
    10. 가장 문제가 되는 `TodoRepository`를 주석 처리 한 후에 해당 생성자를 받고 있는 다른 클래스도 생성자 제거를 진행하였습니다 : `CommentRepository`에서 위와 같은 애러 확인
    11. 위와 같은 애러 확인 후에 다른 `Repository`에도 같은 식으로 주석 및 생성자 삭제를 진행하였습니다 : 정상 빌드 확인, Repository 연결 문제 확인
    12. 환경 변수 문제 가능성도 있는 상황에서 환경 변수를 확인 하는 과정에서 환경 변수에 Port 번호가 누락이 되어 있어서 작성 해주었습니다 : 애러 로그 변경
    13. `application.yml` 파일 에서 아래의 문구 삭제 : 정상 작동 확인
      ```
       spring:
           autoconfigure:
              exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
      ```
  - 결론
    - 처음에는 Bean 주입 문제로 확인 하고 해결을 시도 하였으나 확인해보니 환경변수의 Port 번호가 누락된 시점 에서 `application.yml`의 변경을 시도 하면서 빌드 과정에서 꼬인 것으로 확인 하였습니다
#### AS-IS

  ```
    SPRING_DATASOURCE_URL=jdbc:postgresql://aws-0-ap-northeast-2.pooler.supabase.com/postgres?user=postgres.jisckkofuptsqjyfhklh&password=PASSWORD
  
  ```
- `application.yml`
  ```
    spring:
       autoconfigure:
           exclude: org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
  ```
#### TO-BE
- 환경 변수
  ```
    SPRING_DATASOURCE_URL=jdbc:postgresql://aws-0-ap-northeast-2.pooler.supabase.com:5432/postgres?user=postgres.jisckkofuptsqjyfhklh&password=PASSWORD
  
  ```
- `application.yml` 파일에 위 문구를 삭제 하였습니다

#### 5-3 POST 요청 시에 HttpMessageNotReadableException 애러 발생

- 애러 내용
```
JSON parse error: Illegal unquoted character ((CTRL-CHAR, code 8)): has to be escaped using backslash to be included in string value]
```
- 원인
  - 문자열 앞에 공백 등이 있을 경우에 JSON이 Parse 되지 않는 현상

- 해결
  - 확인 중

#### AS-IS

#### 5-4 Delete 삭제가 안되는 애러 발생 <- Todo, Comment 전체 확인됨

- 현재 확인중입니다


### 6. 환경 설정

- Swagger.ui 접근 주소 : http://localhost:9090/swagger-ui/index.html

- IDE : IntelliJ unlimited

- Language : Kotlin

- JDK.ver : temurin-18.0.2.1

- Kotlin.ver : 1.9.20

- SpringBoot.ver : 3.2.5

- Spring.ver : 1.9.23

- JPA.ver : 1.9.23

