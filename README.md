# Coupon Bellman
쿠폰을 발행하고 관리하는 서비스

## Environments


## REST API Requests

### 새로운 쿠폰 생성 (단건)
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

### 새로운 쿠폰 생성 (다건)
 - Request  
   `POST /coupon/{size} HTTP/1.1`
 - Response  
   ```
   HTTP/1.1 200 OK
   Content-Type: application/json;charset=UTF-8
   {
       "posted": "100"
   }
   ```

### 대기 중인 쿠폰 한건 발행
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

### 발행된 쿠폰 목록 조회
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

### 발행된 특정 쿠폰 사용
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

### 사용한 특정 쿠폰 사용 취소
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

### 오늘 만료될 쿠폰 목록 조회
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

### 3일 뒤에 만료될 쿠폰 대상 메시지 발송
 - Request  
   `POST /expired/message HTTP/1.1`
 - Response  
   ```
   HTTP/1.1 200 OK
   Content-Type: application/json;charset=UTF-8
   {
       "posted": "100"
   }
   ```