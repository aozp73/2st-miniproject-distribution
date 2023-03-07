# 구인구직 사이트 프로젝트

## 기술스택
- JDK 11
- Springboot 2.7.8
- MyBatis
- 테스트 h2 디비
- JSP
- Tomcat Jasper
- JSTL
- Redis

## 모델링
### 1단계 (완료)
- User
- Company
- Employee
- Board
### 2단계 (완료)
- Apply
- Tech
- Resume
### 3단계 (완료)
- Love

## 기능정리
### 1단계 (완료)
- [x]  1단계 테이블 기본적인 CRUD 쿼리 생성
- [x]  기업로그인
- [x]  기업회원가입
- [x]  기업회원정보수정
- [x]  일반회원로그인
- [x]  일반회원가입
- [x]  일반회원정보수정
- [x]  로그아웃
- [x]  채용공고 목록보기
- [x]  채용공고 상세보기
- [x]  구직자 목록 페이지
- [x]  구직자 상세 페이지
- [x]  자소서 쓰기
- [x]  채용공고 쓰기
- [x]  채용공고 수정
### 2단계 (완료)
- [x]  내가 작성한채용 공고 목록 이동 board 3
- [x]  지원자 테이블 생성 apply 1
- [x]  보유 기술 테이블 생성 tech 1
- [x]  채용공고 지원하기 apply 1
- [x]  채용공고 지원자 목록 apply 1
- [x]  구직자 목록 정렬하기(보유 기술이 유사한 순으로 정렬 기능 추가) user 2
- [x]  채용공고 목록 정렬하기(보유 기술이 유사한 순으로 정렬 기능 추가) board 3
- [x]  지원하지 않은 구직자 추천 board 3
- [x]  프로필 사진 변경 user, company 2 3
- [x]  아이디 중복 체크(서버에서도 체크) user, company 2 3
- [x]  비밀번호 확인 user, company 1
- [x]  이력서 테이블 생성
### 3단계 (완료)
- [x]  이력서 목록 페이지
- [x]  자신의 이력서 관리 기능
- [x]  채용 공고 지원시 이력서 선택 제출
- [x]  페이징 처리
- [x]  구직자 구독 기능
- [x]  공고 구독 기능
- [x]  비밀번호 SHA-256을 사용하여 해시화
### 4단계
- [ ]  회사 별점
- [x]  공고 지원 남은 날짜 표시
- [x]  AOP 사용
- [x]  Redis 사용
- [ ]  지도 API
- [ ]  주소입력 API
- [ ]  이메일 유효성 검사