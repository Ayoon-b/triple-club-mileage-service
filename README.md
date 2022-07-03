# ⭐ [Triple Backend Homework] Club Mileage Service
## Index
- [개발 환경](#-개발-환경)
- [DB 설정](#-db-설정)
- [프로젝트 구조](#-프로젝트-구조)
- [빌드 방법](#-빌드-방법)
- [API 명세](#-api-명세)

## 🛠 개발 환경
- Java 11
- MySQL
- Spring Boot
- Gradle
- IntelliJ

## 💾 DB 설정
- MySQL
- version 8.0.13
- Database : mileage_db

<br>


### 테이블 구조  

### - review

|         컬럼 명         | 컬럼 타입 | 설명               |
|:--------------------:|-------|------------------|
|      review_id       | VARCHAR(255)  | 리뷰 고유 id         |
|  attached_photo_ids  | VARCHAR(255)  | 첨부된 사진 파일 명 리스트  |
|       content        | VARCHAR(255)  | 작성된 텍스트          |
|       user_id        | VARCHAR(255)  | 리뷰를 작성한 user의 id |
|       place_id       | VARCHAR(255)  | 리뷰가 작성된 장소의 id   |
|      created_at      | DATETIME  | 리뷰가 작성된 시간       |
|      updated_at      | DATETIME  | 리뷰가 수정된 시간       |
- index

|        인덱스 명         | 인덱스 컬럼            |
|:--------------------:|-------------------|
|      idxPlaceId      | place_id          |
| idxPlaceIdAndUserId  | place_id, user_id |  
  


### - point

|         컬럼 명         | 컬럼 타입 | 설명               |
|:--------------------:|-------|------------------|
|          id          | int  | autoincrement, 포인트 고유 id         |
|       review_id        | VARCHAR(255)  | 리뷰 고유 id |
|  amount  | int  | 작성된 리뷰에 따라 계산된 포인트  |
|       remarks        | VARCHAR(255)  | 포인트 증감 이유          |
|       user_id        | VARCHAR(255)  | 리뷰를 작성한 user의 id |
|       place_id       | VARCHAR(255)  | 리뷰가 작성된 장소의 id   |
|      created_at      | DATETIME  | 포인트 내역이 추가된 시간       |
- index

|        인덱스 명         | 인덱스 컬럼    |
|:--------------------:|-----------|
|      idxReviewId      | review_id |

<br>


### ER 다이어그램
<img src="https://user-images.githubusercontent.com/58851760/177027516-686a7ee1-d422-479a-bf7a-85aaf465d9f0.png" width=500></img>


## 🗂 프로젝트 구조
```
├─main
│  ├─generated
│  ├─java
│  │  └─com
│  │      └─yoonbin
│  │          └─triple
│  │              └─club
│  │                  └─mileage
│  │                      ├─point
│  │                      │  ├─controller
│  │                      │  ├─domain
│  │                      │  ├─repository
│  │                      │  └─service
│  │                      └─review
│  │                          ├─domain
│  │                          ├─repository
│  │                          └─service
│  └─resources
│      ├─static
│      └─templates
└─test
    └─java
        └─com
            └─yoonbin
                └─triple
                    └─club
                        └─mileage
                            ├─point
                            │  ├─repository
                            │  └─service
                            └─review
                                ├─repository
                                └─service

```  

## 🔨 빌드 방법

### 터미널에서 실행
```sh
./gradlew build
cd build/libs
ls

Mode                 LastWriteTime         Length Name
----                 -------------         ------ ----
-a----      2022-07-03   오후 3:26          23436 triple-club-mileage-0.0.1-SNAPSHOT-plain.jar
-a----      2022-07-03   오후 3:26       44566375 triple-club-mileage-0.0.1-SNAPSHOT.jar


java -jar triple-club-mileage-0.0.1-SNAPSHOT.jar
```

## 📜 API 명세
### - 포인트 적립
- method : POST
- URL
```
 /events
```

### action : ADD

- Request body
```json
{
  "type": "REVIEW",
  "action": "ADD",
  "content": "좋아요!",
  "review_id": "240a0658-dc5f-4878-9381-ebb7b2667772",
  "attached_photo_ids": [
    "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
  ],
  "user_id": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
  "place_id": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}

```
- Response body
```json
{
  "type": "REVIEW",
  "action": "ADD",
  "content": "좋아요!",
  "review_id": "240a0658-dc5f-4878-9381-ebb7b2667772",
  "attached_photo_ids": [
    "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
  ],
  "user_id": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
  "place_id": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}

```
<br>


### action : MOD, 텍스트 삭제

- Request body
```json
{
  "type": "REVIEW",
  "action": "MOD",
  "content": "",
  "review_id": "240a0658-dc5f-4878-9381-ebb7b2667772",
  "attached_photo_ids": [
    "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
  ],
  "user_id": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
  "place_id": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```
- Response body
```json
{
  "type": "REVIEW",
  "action": "MOD",
  "content": "",
  "review_id": "240a0658-dc5f-4878-9381-ebb7b2667772",
  "attached_photo_ids": [
    "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
  ],
  "user_id": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
  "place_id": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```
  
<br>


### action : DELETE, 텍스트 삭제

- Request body
```json
{
  "type": "REVIEW",
  "action": "DELETE",
  "content": "",
  "review_id": "240a0658-dc5f-4878-9381-ebb7b2667772",
  "attached_photo_ids": [
    "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
  ],
  "user_id": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
  "place_id": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```
- Response body
```json
{
  "type": "REVIEW",
  "action": "DELETE",
  "content": "",
  "review_id": "240a0658-dc5f-4878-9381-ebb7b2667772",
  "attached_photo_ids": [
    "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8",
    "afb0cef2-851d-4a50-bb07-9cc15cbdc332"
  ],
  "user_id": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
  "place_id": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```

<br>


### - 포인트 조회
- method : GET
- URL
```
 /points
```
- Response body
```json
[
  {
    "id": 1,
    "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
    "amount": 3,
    "remarks": "1자 이상 텍스트 작성, 1장 이상 사진 첨부, 특정 장소에 첫 리뷰 작성",
    "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
    "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f",
    "createdAt": "2022-07-03T15:54:10.816897"
  },
  {
    "id": 2,
    "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
    "amount": -1,
    "remarks": "텍스트 삭제",
    "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
    "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f",
    "createdAt": "2022-07-03T16:00:46.996268"
  },
  {
    "id": 3,
    "reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
    "amount": -2,
    "remarks": "리뷰 삭제",
    "userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
    "placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f",
    "createdAt": "2022-07-03T16:04:31.821037"
  }
]
```
  
<br>


### Swagger를 이용한 API 명세서
서버 실행 후, localhost:8080/swagger-ui/index.html 에 접속

<img src="https://user-images.githubusercontent.com/58851760/177027913-92645d1a-dcfb-4481-b50f-e2b24b01c281.png"></img>



