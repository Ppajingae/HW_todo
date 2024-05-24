# N + 1 현상

- `1:N` , `N:1` 관계 에서 부모 Entity가 특정 데이터를 조회 할 때 연관된 Entity를 조회 하기 위해서 또 한번 조회를 하는 비효율 적인 상황

- 주로 `1:N`, `N:1` 상황에서 발생을 한다

- 보통 즉시 로딩을 할 경우 에는 `N+1` 문제가 바로 발생을 하고 지연 로딩을 할 경우 에는 데이터를 가져온 이 후에 가져온 데이터 에서 자식 Entity를 조회 할 경우 발생 한다

## 해결책

- `Fetch Join`쿼리를 사용 하여 연관된 데이터를 한번에 가져오는 방법 (데이터가 중복이 되는 경우 `distinct`를 통해서 중복을 제거 해준다
  - `inner join`이 발생 한다
  - 쿼리문 실행 으로 바로 `N+1`문제를 바로 해결 할 수 있지만 JPA 에서 제공 하는 Pageable을 사용 하지 못하는 단점이 있다 

- `@EntityGraph`를 사용 한다
  - EntityGraph 상에 있는 Entity들의 연관 관계 속에서 필요한 내용을 함께 조회 하려고 할때 사용
  - `outer join`이 발생 한다
  - `attributePaths`에 쿼리 수행 시 바로 가져올 필드명을 지정 하면 즉시 로딩 으로 조회
  - `outer join`으로 사용할 경우 중복 데이터가 발생할 수 있는데 이는 `Set`저장 방식 으로 해결 한다

# offset 기반 pagination과 cursor 기반 pagination

- pagination은 데이터의 양이 많을 경우 메모리를 효율적으로 사용하기 위해서 여러 페이지로 쪼개서 특정 페이지로 이동 할 수 있도록 하는 방법

## offset 기반 PageNation

- `LIMIT`쿼리와 `OFFSET`쿼리를 이용 하여 SQL 통해서 페이지네이션을 하는 기법
  - 빠르다는 장점은 있지만 데이터의 양이 많아질 수록 성능이 떨어진다는 단점이 있다

## cursor 기반 PageNation

- 클라이언트 에게 보낸 마지막 `row`를 기점 으로 해당 `row`로 부터 n개의 데이터를 주는 페이지네이션 기법
- `OFFSET`쿼리를 이용 하여 페이지네이션을 한 offset 기반 PageNation 과 달리 `WHERE`절에서 `#{pagingCursor} > id`를 사용 한다는 특징이 있다
