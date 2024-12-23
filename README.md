# 🌟 LastNyam Server

### 📖 LastNyam 프로젝트 소개

  ![image](https://github.com/user-attachments/assets/968f40b6-70de-4b74-9f88-e9c187da5530)

폐기 직전 식품을 값싸게 거래하는 음식물 폐기 방지 모바일 앱

-> [앱 주요 기능 확인 - 프론트엔드 repo](https://github.com/LastNyam/last_nyam_fe?tab=readme-ov-file#-%EF%B8%8F%EC%A3%BC%EC%9A%94-%EA%B8%B0%EB%8A%A5)

<br/>

## ✨ 주요 기능
- JWT를 사용한 사용자(업주 사용자 및 소비자 사용자) 인증 및 관리
- 데이터 저장 및 검색
- AWS와의 통합으로 확장성과 안정성 제공
- Spring Boot 프레임워크를 활용한 효율적인 백엔드 운영

<br/>

## 🛠️ 시스템 구성
시스템 구성은 클라이언트-서버 모델을 따른다.

![System Architecture](https://github.com/user-attachments/assets/2768f5d2-4b63-4361-bdc8-29c8a455c896)

1. **사용자**: 모바일 또는 웹 클라이언트를 통해 애플리케이션과 상호작용.
2. **Spring 서버**: Spring Boot로 구축되었으며, AWS EC2에서 호스팅되어 비즈니스 로직과 데이터 처리를 담당.
3. **MySQL 데이터베이스**: 애플리케이션 데이터를 저장하며, AWS RDS에서 호스팅되어 Spring 서버와 연결됨.
4. **개발자**: Spring Boot와 데이터베이스 기술을 활용하여 백엔드를 유지 관리하고 개선.

<br/>

## 📂 프로젝트 구조

```
last_nyam_server/
├── .github/
│   └── workflows/          # GitHub Actions 워크플로우 설정
├── scripts/                # 배포 및 기타 스크립트
├── src/
│   ├── main/
│   │   ├── java/com/lastnyam/lastnyam_server
│   │   │   ├── domain/          # 도메인 별 폴더
│   │   │   │   ├── owner/         # 업주 사용자 도메인 관련 로직 (폴더 내 항목은 다른 도메인에서도 동일 구조 반복)
│   │   │   │   │   ├── controller/  # HTTP 요청 처리
│   │   │   │   │   ├── service/     # 비즈니스 로직
│   │   │   │   │   ├── repository/  # 데이터베이스 인터페이스
│   │   │   │   │   ├── dto/         # 데이터 전송 객체
│   │   │   │   │   └── domain/      # 데이터 모델 정의
│   │   │   │   ├── post/          # 음식 게시물 도메인 관련 로직(카테고리 포함)
│   │   │   │   ├── reservation/   # 예약(주문) 도메인 관련 로직(리뷰 포함)
│   │   │   │   ├── sms/           # 휴대폰 인증 관련 로직
│   │   │   │   ├── store/         # 가게 도메인 관련 로직
│   │   │   │   └── user/          # 일반 사용자 도메인 관련 로직(관심 매장 포함)
│   │   │   └── global/
│   │   │       ├── auth/           # 인증 관련
│   │   │       ├── config/         # 설정 파일
│   │   │       ├── domain/         # 공통 데이터 모델
│   │   │       ├── exception/      # 예외 핸들러
│   │   │       ├── request/        # 공통 request 필드에 대한 정규식
│   │   │       └── response/       # 공통 response 형식
│   │   └── resources/
│   │       └── application.yml      # 설정 파일
│   └── test/                        # 프로젝트 테스트 케이스
├── build.gradle                     # 빌드 설정 파일
└── README.md                        # 프로젝트 문서

```

<br/>

## 🛠️ 프레임워크 및 도구

- **프레임워크**: Spring Boot (v3.3.5)
- **데이터베이스**: MySQL (v8.0.39) / AWS RDS 사용
- **어플리케이션 호스팅**: AWS EC2 (ubuntu 24.04)
- **빌드 도구**: Gradle (v8.10.2)
- **버전 관리**: Git
- **CI/CD 도구**: GitHub Action, AWS CodeDeploy

<br/>
