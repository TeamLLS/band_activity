# 1. 패키지 
```
band_activity
    ├─activity
    │  ├─command
    │  ├─event
    │  ├─exception
    │  └─form
    ├─core
    ├─external
    │  └─kafka
    ├─participant
    │  ├─command
    │  ├─event
    │  └─form
    └─policy
```

# 2. 도메인

## 2-1. Activity

| 도메인 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|Activity|      |      |      |      |
|        |id|Long|Activity Id|Primary Key|
|        |clubId|Long|Club Id|Club 추적키, NotNull|
|        |image|String|이미지 url||
|        |name|String|이름|NotNull|
|        |description|String|설명||
|        |status|ActivityStatus|상태|NotNull|
|        |startTime|Instant|활동 시작일||
|        |endTime|Instant|활동 종료일||
|        |participantNum|Integer|참가자수||
|        |createdAt|Instant|생성일||
|        |closedAt|Instant|마감일||


| ENUM | 값 | 설명 | 비고 |  
|------|----|------|------|
|ActivityStatus||  |      |
|          |OPENED|모집중||
|          |CLOSED|모집종료||
|          |CANCELED|취소됨||


## 2-2. Participant

| 도메인 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|Participant|   |      |      |      |
|        |id|Long|Participant Id|Primary Key|
|        |activity|Activity|참가 Activity|Foreginer Key, NotNull|
|        |username|String|username|User 추적키, NotNull|
|        |memberId|Long|Member Id|Member 추적키, NotNull|
|        |memberName|String|Member 이름||
|        |status|ParticipantStauts|상태||



| ENUM | 값 | 설명 | 비고 |  
|------|----|------|------|
|ParticipantStatus||     ||
|            |ATTEND|참가||
|            |NOT_ATTEND|불참||
|            |TADDITIONAL_ATTEND|추가 참가||
|            |TADDITIONAL_NOT_ATTEND|추가 불참||



# 3. 이벤트

## 3-1. Club 이벤트


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|ActivityEvent|     |      |      |      |
|         |eventId|String|Event Id|Event 추적키|
|         |triggerdBy|String|생성자||
|         |activityId|Long|Activity Id|Activity 추적키|
|         |clubId|Long|Club Id|Club 추적키|
|         |time|Instnat|발생 시간||


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|ActivityCreated|      |      |발생 API: Activity 생성|ActivityEvent 상속|
|           |name|String|Activity 이름||
|           |image|String|Activity 이미지 url||
|           |description|String|Activity 설명||
|           |startTime|Instant|활동 시작일||
|           |endTime|Instant|활동 종료일||


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|ActivityClosed|      |      |발생 API: Activity 종료|ActivityEvent 상속|
|          |status|ActivityStatus|Actitivity 상태|=CLOSED|

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|ActivityCanceled|      |      |발생 API: Activity 취소|ActivityEvent 상속|
|          |status|ActivityStatus|Actitivity 상태|=CANCELED|


## 3-2. Participant 이벤트

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|ParticipantEvent|      |      |      |      |
|           |eventId|String|event Id|Event 추적키|
|           |triggerdBy|String|생성자||
|           |activityId|Long|Activity Id|Activity 추적키|
|           |memberId|Long|Member Id|Member 추적키|
|           |time|Instnat|발생 시간||


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|ParticipantCreated|      |      |발생 API: Activity 참가 관련|ParticipantEvent 상속|
|             |memberName|String|Member 이름||


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|ParticiapntStatusChanged|      |      |발생 API: Acitivity 참가 관련|ParticipantEvent 상속|
|                 |status|ParticipantStatus|변경 상태||


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|participantConfirmed|      |      |발생 API: Activity 종료|ParticipantEvent 상속|
|               |status|ParticipantStatus|확정 상태||


# 4. 예외

| Exception | 속성 | 타입 | 설명 | 비고 |  
|-----------|------|------|------|------|
|ActivityNotOpenedException|      |      |Acitivity가 OPENED가 아님을 표시|RuntimeException|
|                  |activityId|Long|Activity Id||
|                  |current|String|현재 상태|


| Exception | 속성 | 타입 | 설명 | 비고 |  
|-----------|------|------|------|------|
|ActivityNotClosedException|      |      |Acitivity가 CLOSED가 아님을 표시|RuntimeException|
|                  |activityId|Long|Activity Id||
|                  |current|String|현재 상태|

| Exception | 속성 | 타입 | 설명 | 비고 |  
|-----------|------|------|------|------|
|ActivityNotInCLubException|      |      |해당 Club의 Activity가 아님을 표시|RuntimeException|
|                  |activityId|Long|Activity Id||
|                  |clubId|String|Club Id|



# 5. 주요 컴포넌트

| 컴포넌트 | 설명 | 비고 |  
|----------|------|------|
|KafkaConsumerService|kafka 메시지 소비용 컴포넌트||
|ActivityController|Activity 관련 엔드포인트||
|ActivityService|Activity 관련 비즈니스 로직 수행||
|ActivityStore|Activity 관련 DB 접근||
|ParticipantController|Participant 관련 엔드포인트||
|ParticipantService|Participant 관련 비즈니스 로직 수행||
|ParticipantStore|Participant 관련 DB 접근||


# 6. ERD
![c1](https://github.com/user-attachments/assets/201c0793-7359-4f02-a1f9-422bc3e04dbf)
