# API

|   API   | 설명 | DB | 이벤트 |
|-------|------|----|--------|
|[활동 생성](#활동-생성)|클럽 활동 생성|Activity 생성|ActivityCreated|
|[활동 목록 조회](#활동-목록-조회)|대상 클럽의 활동 목록 반환|||
|[활동 정보 조회](#활동-정보-조회)|대상 활동 정보 반환|||
|[활동 취소](#활동-취소)|대상 활동 취소|Activity 변경|ActivityCanceled|
|[활동 종료](#활동-종료)|대상 활동의 모집 종료|Activity 변경|ActivityClosed, ParticipantConfrimed|
|[활동 참가](#활동-참가)|대상 활동에 Member 참가|Participant 생성, 변경|ParticipantCreated, ParticipantStatusChanged|
|[활동 불참](#활동-불참)|대상 활동의 참가 취소|Participant 변경|ParticipantStatusChanged|
|[활동 추가참가](#회원-추가참가)|활동 모집 종료 이후 참가 Member 추가|Participant 생성, 변경|ParticipantCreated, ParitipantStatusChanged, ParticipantConfirmed|
|[활동 추가불참](#활동-추가불참)|활동 모집 종료 이후 참가 Member 취소|Participant 변경|ParitipantStatusChanged, ParticipantConfirmed|
|[참가자 조회](#참가자-조회)|대상 활동의 참가자 조회|||
|[참가 활동 조회](#참가-할동-조회)|내가 참가한 활동 조회|||


## ▶클럽 생성
### POST /club
```
header: {  
  Authorization: Bearer ${accessToken value},
}

form-data: {
  name: 모임 이름, (String)
  description: 모임 설명, (String)
  image: 모임 이미지, (MulitPartFile)
  contactInfo: 컨택 정보 (String)
} 
```

### 응답
```
body: {  
  clubId: 생성 클럽 ID (Long)
}
```

