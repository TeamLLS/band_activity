# API

| API | 설명 | DB | 이벤트 |
|-----|------|----|--------|
|[활동 생성](#활동-생성)|클럽 활동 생성|Activity 생성|ActivityCreated|
|[활동 목록 조회](#활동-목록-조회)|대상 클럽의 활동 목록 반환|||
|[활동 조회](#활동-조회)|대상 활동 정보 반환|||
|[활동 취소](#활동-취소)|대상 활동 취소|Activity 변경|ActivityCanceled|
|[활동 종료](#활동-종료)|대상 활동의 모집 종료|Activity 변경|ActivityClosed, ParticipantConfrimed|
|[활동 참가](#활동-참가)|대상 활동에 Member 참가|Participant 생성, 변경|ParticipantCreated, ParticipantStatusChanged|
|[활동 불참](#활동-불참)|대상 활동의 참가 취소|Participant 변경|ParticipantStatusChanged|
|[활동 추가참가](#회원-추가참가)|활동 모집 종료 이후 참가 Member 추가|Participant 생성, 변경|ParticipantCreated, ParitipantStatusChanged, ParticipantConfirmed|
|[활동 추가불참](#활동-추가불참)|활동 모집 종료 이후 참가 Member 취소|Participant 변경|ParitipantStatusChanged, ParticipantConfirmed|]
|[참가자 조회](#참가자-조회)|대상 활동의 참가자 조회|||
|[참가 활동 조회](#참가-할동-조회)|내가 참가한 활동 조회|||



## ▶활동 생성
### POST /activity
```
header: {  
  Authorization: Bearer ${accessToken value}
}

form-data: {
  clubId: Club Id, (Long)
  name, 활동 이름, (String)
  description: 활동 설명, (String)
  image: 활동 이미지, (MulitPartFile)
  startTime: 활동 시작 시간, (Instant, ISO 8601)
  endTime: 활동 종료 시간 (Instnat, ISO 8601)
} 
```

### 응답
```
```


## ▶활동 목록 조회
### Get /activity/{clubId}/list?pageNo={page 번호}
```
header: {  
  Authorization: Bearer ${accessToken value}
}
```

### 응답
```
body: {
  list: [
    {
      id: Activity Id, (Long)
      name: 활동 이름,  (String)
      image: 이미지 url,  (String)
      status: 상태,  (String)
      participantNum: 참가자 수 (Integer)
    },
    ...
  ]
}
```


## ▶활동 조회
### Get /activity/{activityId}
```
header: {  
  Authorization: Bearer ${accessToken value}
}
```

### 응답
```
body: {
  id: Activity Id, (Long)
  clubId: Club Id, (Long)
  name: 활동 이름,  (String)
  image: 이미지 url,  (String)
  description: 설명, (String)
  startTime: 시작 시간, (Instnat, ISO 8601)
  endTime: 종료 시간, (Instnat, ISO 8601)
  status: 상태,  (String)
  participantNum: 참가자 수 (Integer)
}
```


## ▶활동 취소
### PATCH /activity/{clubId}/{activityId}/cancel
```
header: {  
  Authorization: Bearer ${accessToken value}
}
```

### 응답
```
```


## ▶활동 종료
### POST /activity/{clubId}/{activityId}/close
```
header: {  
  Authorization: Bearer ${accessToken value}
}
```

### 응답
```
```


## ▶활동 참가
### POST /activity/{clubId}/{activityId}/attend
```
header: {  
  Authorization: Bearer ${accessToken value}
}
```

### 응답
```
```


## ▶활동 불참
### POST /activity/{clubId}/{activityId}/not-attend
```
header: {  
  Authorization: Bearer ${accessToken value}
}
```

### 응답
```
```


## ▶활동 추가참가
### POST /activity/{clubId}/{activityId}/attend/additional?tartget={대상 username}
```
header: {  
  Authorization: Bearer ${accessToken value}
}
```

### 응답
```
```


## ▶활동 추가불참
### POST /activity/{clubId}/{activityId}/not-attend/additional?tartget={대상 username}
```
header: {  
  Authorization: Bearer ${accessToken value}
}
```

### 응답
```
```



## ▶참가자 조회
### Get /participant/{activityId}/list?pageNo={page 번호}
```
header: {  
  Authorization: Bearer ${accessToken value}
}
```

### 응답
```
body: {
  list: [
    {
      id: Participant Id, (Long)
      activityId: Activity Id, (Long)
      username: username,  (String)
      memberId: Member Id, (Long)
      memberName: Member 이름, (String)
      status: 참가 상태 (String)
    },
    ...
  ],
  attend: 내 참가 여부 (Boolean)
}
```


## ▶참가 활동 조회
### Get /participant/{clubId}/activity/list?pageNo={page 번호}
```
header: {  
  Authorization: Bearer ${accessToken value}
}
```

### 응답
```
body: {
  list: [
    {
      activityId: Activity Id, (Long)
      activity name: 활동 이름,  (String)
      image: 이미지 url,  (String)
      startTime: 시작 시간, (Instnat, ISO 8601)
      memberId: Member Id, (Long)
      participantStatus: 참가 상태 (String)
    },
    ...
  ]
}
```
