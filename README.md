# E-Commerce
2024 이커머스 프로젝트


# 개요
    간단한 커머스 프로젝트

    Use : Spring, Jpa, Mysql, Redis, Docker(도커를 이용해 어떻게 AWS에 배포할지 알아본다.), AWS

    Goal : 셀러와 구매자 사이를 중개해 주는 커머스 서버를 구축한다.


## 회원서버 1차 구현 대상

### 공통
- [x] 이메일을 통한 인증번호 발송

### 고객(구매자) 
- [x] 회원 가입
- [x] 인증 처리 (이메일 인증)
    - 이메일 인증 외 여러 방법이 존재하지만 서버에 대한 dependency도 있고 하면 AWS 올릴 때 등 새로운 셋팅을 해야하는 불편함이 존재한다.
    이메일 인증을 하면 핸드폰을 통한 인증 방법 등은 방식이 유사하므로 추후 도움이 될 듯.
    (추가적으로 핸드폰번호 인증은 비용이 발생함.)
- [x] 로그인 토큰 발생
- [x] 로그인 토큰을 이용한 제어 확인  
  - (로그인한 상태에서만 페이지에 접근가능 \ JWT, Filter 이용해 간략하게 진행)  
  - 토큰을 생성해 full로 보안을 적용하지는 않지만 로그인, api제어에 대한 flow를 이해한다.  
- [ ] 예치금 관리  
  - 예치금 관리를 통해 트랜잭션을 어떻게 관리할지 알아본다.

### 셀러
- [ ] 회원가입

### 주문
- [ ] 장바구니
- [ ] 고객이 주문


## 회원서버 2차 구현 대상  
    (구매자 중심으로 우선 개발할 것이기 때문에 셀러의 편의는 2차 구현대상으로 지정.)

### 셀러
- [ ] 매출 조회
- [ ] 잔액 추가 (정산 전용)


## 주문서버 1차 구현 대상

### 고객(구매자)  
- [ ] 장바구니를 위한 Redis 추가  
  - DB에 저장해도 되지만 기술 스펙트럼을 넓히기 위해 Redis Cache 서버를 통해 생성.
- [ ] 상품 검색 & 상세 페이지
- [ ] 장바구니에 추가
- [ ] 장바구니 확인하기
- [ ] 주문하기
- [ ] 주문 내역 메일로 발송하기

### 셀러
- [ ] 상품 등록 및 수정
- [ ] 상품 삭제


## 주문서버 2차 구현 대상

### 셀러
- [ ] 매출 조회
- [ ] 잔액 추가 (정산 전용)









