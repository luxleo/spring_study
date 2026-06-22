# Section 1 : Job & Step

## Job과 step

spring batch를 실행하는 두 단위이다.

### Job

'Job' 은 하나의 완전한 배치처리를 의미한다. 접하는 모든 배치처리의 단위가 Job이다.

- 매일 심야에 수행되는 '일일 매출 집계'
- 매월 1일에 실행되는 '정기 결제'

### Step

'Step' 은 Job을 구성하는 실행단위이다.
Job은 하나 이상의 Step으로 구성된다. 예를 들어 '일일 매출 집계' Job 은 다음과 같은 Step으로 구성될 수 있다.

1. 매출 집계 Step

    - 전일 주문 데이터를 읽고(Read)
    - 결제 완료된 것만 필터링하여(Process)
    - 상품별/카테고리별로 집계해서 저장(Write)

2. 알림 발송 Step

    - 집계 요약 정보를 생성하여 관리자에게 전달.

3. 캐시 갱신 Step

## Spring boot Application 실행

```shell
./gradlew bootRun --args='--spring.batch.job.name=[targetName]'
```
