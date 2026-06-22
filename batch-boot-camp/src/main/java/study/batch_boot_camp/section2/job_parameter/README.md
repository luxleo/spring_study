# JobParameter - 동적으로 배치 실행 설정값 전달하기

배치 실행시 처리해야할 파일의 path, 삭제해야할 파일의 기준 시점등
배치 실행시 전달해주어야할 값들이 있다.

JobParameter는 이러한 값을 동적으로 전달해주는 역할을한다.

그런데 -D 옵션으로 실행시 프로퍼티로 전달하면 되지 않을까?

## JobParameter vs 프로퍼티

프로퍼티와 JobParameter는 완전히 다른 목적을 가진다.

1. 입력값 동적 변경

    웹 요청에 응답하는 배치 작업을 진행한다고 가정해보자.
    
    단순 프로퍼티 전달로 배치작업의 인자들을 동적으로 전달할 수는 없다.
    
    프로퍼티는 배치 어플리케이션을 최초 실행할 때 단 한번만 전달할 수 있기 떄문이다.

2. 메타데이터

    Spring batch는 JobParameter와 관련된 모든 데이터를 JobRepository의 메타데이터로 저장하여
    아래와 같은 기능을 제공할 수 있다.
    - JobInstance 식별 및 재시작 처리
    - Job 실행 이력 추적
    
## JobParameter 전달하기

### 커맨드라인으로 전달하기

```bash
./gradlew bootRun --args="--spring.batch.job.name=jobName [jobParameter]"
```
와 같이 배치 실행문 이후에 jobParameter를 전달한다. 전달시 아래의 순서에 맞추어 전달한다.

```text
parameterName=parameterValue,parameterType,identificationFlag
```

- parameterName: 배치 잡에서 파라미터를 찾을때 key역할을 한다.
- parameterType: 파라미터의 실제 값이다. defaultValue: java.lang.String
- identificationFlag: 해당 파라미터가 JobInstance의 식별에 사용될 파라미터인지 결정하는 옵션이다 defaultValue: true
    
만일 여러 parameter를 전달한다면 아래와 같이 띄어쓰기로 구분한다.
```bash
./gradlew bootRun --args="--spring.batch.job.name=jobName param1=value1,java.lang.String,true param2=value2,java.lang.Integer,true"
```

## JobParameter 이용하기

# JobScope, StepScope - 지연 바인딩, 격리, 동시성
## Step이 아닌 Tasklet에 Scope 할당하기
# ExecutionContext - StepExecution, JobExecution