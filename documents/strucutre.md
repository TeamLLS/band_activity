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

## 2-1. Club 

| 도메인 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|Club    |      |      |      |      |
|        |id|Long|Club Id|Primary Key|
|        |image|String|이미지 url||
|        |name|String|이름|NotNull|
|        |description|String|설명||
|        |contactInfo|String|연락처||
|        |status|ClubStatus|상태||
|        |createdAt|Instant|생성일||
|        |closedAt|Instant|폐쇄일||


| ENUM | 값 | 설명 | 비고 |  
|------|----|------|------|
|ClubStatus||      |      |
|          |ACTIVE|운영중||
|          |CLOSED|운영종료||
|          |RECRUITING|회원 모집중||


## 2-2. Member

| 도메인 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|Member  |      |      |      |      |
|        |id|Long|Member Id|Primary Key|
|        |club|Club|가입 Club|Foreginer Key, NotNull|
|        |username|String|username|User 추적키, NotNull|
|        |role|Role|권한|NotNull|
|        |ststus|MemberStatus|Member 상태|NotNukk|
|        |name|String|이름||
|        |gender|String|성별|"male" or "female"|
|        |birthYear|Integer|출생년도||
|        |createdAt|Instant|가입일||
|        |terminatedAt|Instant|탈퇴일||



| ENUM | 값 | 설명 | 비고 |  
|------|----|------|------|
|MemberStatus|     |     ||
|            |ACTIVE|활동중||
|            |INACTIVE|휴면||
|            |TERMIATED|탈퇴됨||



| ENUM | 값 | 설명 | 비고 |  
|------|----|------|------|
|Role  |    |      |      |
|      |OWNER|회장|3|
|      |MANAGER|관리자|2|
|      |REGULAR|일반|1|


# 3. 이벤트

## 3-1. Club 이벤트


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|ClubEvent|      |      |      |      |
|         |eventId|String|event Id|Event 추적키|
|         |triggerdBy|String|생성자||
|         |clubId|Long|Club Id|Club 추적키|
|         |time|Instnat|발생 시간||


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|ClubCreated|      |      |발생 API: Club 생성|ClubEvent 상속|
|           |name|String|Club 이름||
|           |image|String|Club 이미지 url||
|           |description|String|Club 설명||
|           |contactInfo|String|Club 연락처||


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|ClubClosed|      |      |발생 API: Club 폐쇄|ClubEvent 상속|
|          |status|ClubStatus|Club 상태|=TERMINATED|


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|ClubChanged|      |      |발생 API: Club 정보 변경|ClubEvent 상속|
|           |name|String|Club 이름||
|           |image|String|Club 이미지 url||
|           |description|String|Club 설명||
|           |contactInfo|String|Club 연락처||
|           |status|ClubStatus|Club 상태||


## 3-2. Member 이벤트

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|MemberEvent|      |      |      |      |
|           |eventId|String|event Id|Event 추적키|
|           |triggerdBy|String|생성자||
|           |clubId|Long|Club Id|Club 추적키|
|           |memberId|Long|Member Id|Member 추적키|
|           |time|Instnat|발생 시간||


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|MemberCreated|      |      |발생 API: Club 생성, Member 등록|MemberEvent 상속|
|             |username|String|username|User 추적키|
|             |role|Role|Member 권한||
|             |name|String|Member 이름||
|             |gender|String|Member 성별|"male" or "female"|
|             |birthYear|Integer|Member 출생년도||

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|MemberRoleChanged|      |      |발생 API: Member 권한 변경|MemberEvent 상속|
|                 |role|Role|Member 권한||


| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|MemberWithDrawn|      |      |발생 API: Member 탈퇴|MemberEvent 상속|
|               |status|MemberStatus|Member상태|=TERMINATED|

| 이벤트 | 속성 | 타입 | 설명 | 비고 |  
|--------|------|------|------|------|
|MemberBanned|      |      |발생 API: Member 강퇴|MemberEvent 상속|
|            |status|MemberStatus|Member상태|=TERMINATED|


# 4. 예외


| Exception | 속성 | 타입 | 설명 | 비고 |  
|-----------|------|------|------|------|
|NotMemberException|      |      |해당 Member가 활동 중이 아님을 표시|RuntimeException, 반환코드=404|
|                  |clubId|Long|Club Id||
|                  |usernmae|String|username|

| Exception | 속성 | 타입 | 설명 | 비고 |  
|-----------|------|------|------|------|
|NotActiveMemberException|      |      |해당 User가 Club의 Member가 아님을 표시|RuntimeException, 반환코드=403|
|                  |memberId|Long|Member Id||
|                  |status|String|Member 상태|


| Exception | 속성 | 타입 | 설명 | 비고 |  
|-----------|------|------|------|------|
|InsufficientMemberException|      |      |해당 Member가 접근권한이 없음을 표시|RuntimeException, 반환코드=403|
|                           |memberId|Long|Member Id||
|                           |role|String|Member 권한|



| Exception | 속성 | 타입 | 설명 | 비고 |  
|-----------|------|------|------|------|
|DuplicatedMemberException|      |      |해당 User가 이미 Club의 Member임을 표시|RuntimeException, 반환코드=409|
|                  |clubId|Long|Club Id||
|                  |usernmae|String|username|



# 5. 주요 컴포넌트

| 컴포넌트 | 설명 | 비고 |  
|----------|------|------|
|UserServiceClient|User server 접근 목적 utils|FeignClient 기반 rest api 통신|
|S3Service|S3 접근 목적 utils||
|ClubController|Club 관련 엔드포인트||
|ClubService|Club 관련 비즈니스 로직 수행||
|ClubStore|Club 관련 DB 접근||
|MemberController|Member 관련 엔드포인트||
|MemberService|Member 관련 비즈니스 로직 수행||
|MemberStore|Member 관련 DB 접근||




# 6. ERD
