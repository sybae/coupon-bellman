# Coupon Bellman
> Do you want some coupons?

쿠폰을 발행하여 사용할 수 있는 서비스입니다.

## Environments etc
Spring Boot, JPA, H2, Lombok, Batch, Java 8

 - 서비스 구동시 Post Construct 통해서 임의의 쿠폰들을 생성/발행 합니다.
 - 이후 제공되는 API 통해서 처리할 수 있습니다.
 - 쿠폰은 `XXXX-YYYY-ZZZ` 형태로 생성됩니다.  
   각 자리는 숫자로 구성되어 있으며 `CodeGenerator`에 의해 항상 난수 발생합니다.
 - 쿠폰의 상태는 다음과 같이 구성되어 있습니다.
   - `0: STANDBY ----- 생성되어 대기 중인 상태`
   - `1: PUBLISHED --- 발행되어 사용가능한 상태`
   - `2: USED -------- 사용된 상태 (사용취소될 경우 PUBLISHED 상태로 변경)`
   - `3: EXPIRED ----- 만료시점이 지나 더 이상 사용할 수 없는 상태`

## REST API Requests

#### 새로운 쿠폰 생성 (단건)
 - Request  
   `POST /coupon HTTP/1.1`
 - Response  
   ```
   HTTP/1.1 200 OK
   Content-Type: application/json;charset=UTF-8
   {
       "code": "3984-2741-064"
   }
   ```

#### 새로운 쿠폰 생성 (다건)
 - Request  
   `POST /coupon/{size} HTTP/1.1`
 - Response  
   ```
   HTTP/1.1 200 OK
   Content-Type: application/json;charset=UTF-8
   {
       "posted": 100
   }
   ```

#### 대기 중인 쿠폰 한건 발행
 - Request  
   `PUT /coupon HTTP/1.1`
 - Response  
   ```
   HTTP/1.1 200 OK
   Content-Type: application/json;charset=UTF-8
   {
       "code": "1743-3978-208"
   }
   ```

#### 발행된 쿠폰 목록 조회
 - Request  
   `GET /coupon/published HTTP/1.1`
 - Response  
   ```
   HTTP/1.1 200 OK
   Content-Type: application/json;charset=UTF-8
   [
       {
           "code": "1743-3978-208"
       },
       {
           "code": "3984-2741-064"
       }
   ]
   ```

#### 발행된 특정 쿠폰 사용
 - Request  
   `PUT /coupon/{code}/use HTTP/1.1`
 - Response  
   ```
   HTTP/1.1 200 OK
   Content-Type: application/json;charset=UTF-8
   {
       "code": "1743-3978-208"
   }
   ```

#### 사용한 특정 쿠폰 사용 취소
 - Request  
   `PUT /coupon/{code}/cancel HTTP/1.1`
 - Response  
   ```
   HTTP/1.1 200 OK
   Content-Type: application/json;charset=UTF-8
   {
       "code": "3984-2741-064"
   }
   ```

#### 오늘 만료될 쿠폰 목록 조회
 - Request  
   `GET /coupon/expired HTTP/1.1`
 - Response  
   ```
   HTTP/1.1 200 OK
   Content-Type: application/json;charset=UTF-8
   [
       {
           "code": "1743-3978-208"
       },
       {
           "code": "3984-2741-064"
       }
   ]
   ```

#### 3일 뒤에 만료될 쿠폰 대상 메시지 발송
 - Request  
   `POST /expired/message HTTP/1.1`
 - Response  
   ```
   HTTP/1.1 200 OK
   Content-Type: application/json;charset=UTF-8
   {
       "posted": 100
   }
   ```

