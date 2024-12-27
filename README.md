## NewsFeed 만들기 프로젝트

## ❓ JPA를 사용한 일정 관리 앱?
> 뉴스피드와 사용자의 C/R/U/D를 작성하여 실력향상.

## 🙋‍♀️ 조금 더 구체적으로 가르쳐주세요!
1. API 명세 및 ERD 작성
2. 와이어 프레임 작성
3. 사용자 C/R/U/D 작성
4. 뉴스피드 C/R/U/D 작성
5. 연관 관계 설정
6. 페이징
7. 예외 처리
8. null 체크 및 특정 패턴에 대한 검증 수행


## 📌 목차

1. [❓ 일정 관리 앱 만들기 (feat.JPA)](#-JPA를-사용한-일정-관리-앱)
2. [🙋‍♀️ 조금 더 구체적으로 가르쳐주세요!](#-조금-더-구체적으로-가르쳐주세요)
3. [📌 목차](#-목차)
    - [🗂️ Project info](#-Project-info)
    - [🧾 API 명세서](#-API-명세서)
    - [📂 ERD](#-ERD)
    - [🚀 Stacks](#-Stacks)
    - [💻 Program operation screen](#-Program-operation-screen)
    - [☄️ Major functional code](#-Major-functional-code)
    - [📂 Architecture](#-Architecture)
    - [🛠️ Trouble shooting](#-Trouble-shooting)

## 🗂️ Project info

- 프로젝트 이름 : Project < NewsFeed >
- 개발기간 : 2024.12.20 - 2024.12.27

## 🧾 API 명세서
|담당자|Method|기능|URL|request|response|
|------|------|----|---|-------|--------|
|홍은기|POST|사용자 생성(회원가입)|/api/users|RequestBody <br> <br> username → 필수 ⭕ <br> email → 필수 ⭕ <br>  password → 필수 ⭕ |{<br>&nbsp;&nbsp;email,<br>&nbsp;&nbsp;username,<br>&nbsp;&nbsp;createdDate,<br>&nbsp;&nbsp;modifiedDate<br>}|
|홍은기|GET|조회|/api/users|RequestParam <br> <br> username → 필수 ❌ <br> email → 필수 ❌|{<br>&nbsp;&nbsp;email,<br>&nbsp;&nbsp;username,<br>&nbsp;&nbsp;createdDate,<br>&nbsp;&nbsp;modifiedDate<br>},<br>{<br>&nbsp;&nbsp;email,<br>&nbsp;&nbsp;username,<br>&nbsp;&nbsp;createdDate,<br>&nbsp;&nbsp;modifiedDate<br>}, <br> ...|
|박지안|PATCH|사용자 수정|/api/users/{id}|RequestBody <br> <br> username → 필수 ❌ <br> email → 필수 ⭕ <br>  password → 필수 ❌ <br> <br> 수정 전/후가 같으면 수정 되지 않음|{<br>&nbsp;&nbsp;email,<br>&nbsp;&nbsp;username,<br>&nbsp;&nbsp;createdDate,<br>&nbsp;&nbsp;modifiedDate<br>}|
|박지안|DELETE|사용자 삭제 (회원탈퇴)|/api/users/|RequestBody <br> <br> email → 필수 ⭕ <br> password → 필수 ⭕|계정 삭제가 완료되었습니다.|
|황서호|POST|게시글 생성|/api/posts|RequestBody <br> <br> title → 필수 ⭕ <br> contents → 필수 ⭕ <br>  username → 필수 ⭕ |{<br>&nbsp;&nbsp;id,<br>&nbsp;&nbsp;title,<br>&nbsp;&nbsp;contents,<br>&nbsp;&nbsp;username,<br>&nbsp;&nbsp;createdDate,<br>&nbsp;&nbsp;modifiedDate<br>}|
|이현우|GET|게시글 조회<br>(전체조회 / 특정 유저 조회)|/api/posts|RequestParam <br> <br> username → 필수 ❌ <br> email → 필수 ❌|{<br>&nbsp;&nbsp;email,<br>&nbsp;&nbsp;username,<br>&nbsp;&nbsp;createdDate,<br>&nbsp;&nbsp;modifiedDate<br>},<br>{<br>&nbsp;&nbsp;email,<br>&nbsp;&nbsp;username,<br>&nbsp;&nbsp;createdDate,<br>&nbsp;&nbsp;modifiedDate<br>}, <br> ...|
|이현우|PATCH|게시글 수정|/api/posts/{id}|RequestBody <br> <br> username → 필수 ❌ <br> email → 필수 ⭕ <br>  password → 필수 ❌ <br> <br> 수정 전/후가 같으면 수정 되지 않음|{<br>&nbsp;&nbsp;email,<br>&nbsp;&nbsp;username,<br>&nbsp;&nbsp;createdDate,<br>&nbsp;&nbsp;modifiedDate<br>}|
|이현우|DELET|게시글 삭제|/api/posts/{id}||삭제가 완료되었습니다.|
|황서호|GET|관심유저 설정|/api/users/follow|RequestParam<br><br>followedUserEmail(관심 유저로 등록하고 싶은 사용자 email) → 필수 ⭕|팔로우가 완료되었습니다.|
|홍은기||전역 예외 처리|||{<br>&nbsp;&nbsp;code:400,<br>&nbsp;&nbsp;httpStatus:BAD_REQUEST,<br>&nbsp;&nbsp;message:"에러 메시지"<br>}|
|홍은기|POST|로그인|/api/login||{username}님이 로그인하셨습니다.|
|황서호|POST|로그아웃|/api/logout||로그아웃이 완료되었습니다.|

## 📂 ERD

<br>

## 🚀 Stacks

### Environment
![Git](https://img.shields.io/badge/GIT-E44C30?style=for-the-badge&logo=git&logoColor=white)
![GitGub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)


### Skills
![](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
### OS
![Mac os](https://img.shields.io/badge/mac%20os-000000?style=for-the-badge&logo=apple&logoColor=white)

### Workspace Specs
![MacBookPro M2](https://img.shields.io/badge/Apple-MacBook_Pro_M2_2022-999999?style=for-the-badge&logo=apple&logoColor=white)

### Blog
[![Velog's GitHub stats](https://velog-readme-stats.vercel.app/api/badge?name=kirby_y)](https://velog.io/@kirby_y)

<br>

## 💻 Program operation screen

<details>
   <summary>📍 실행 화면 보기</summary>

### 📍 실행 화면

</details>



## ☄️ Major functional code

### 일정 생성
```java

```

### 일정 조회
```java

```

### 일정 수정
```java

```

### 일정 삭제
```java

```

<br>

## 📂 Architecture

```java
📂

```


## 🛠️ Trouble shooting

[![Velog's GitHub stats](https://velog-readme-stats.vercel.app/api?name=kirby_y&tag=CalendarJPA)](https://velog.io/@kirby_y)
