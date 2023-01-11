## 예외 처리

- [x]  예상한 예외(BaseException 상속 받은 예외)는 400 BAD_Request를 낸다.
- [x] 예상되지 않은 예외는 500 에러를 사용한다.

## UserArgumentResolver

- [x] 유저정보가 있는지 확인한다
    -[x] 유저 정보가 없으면 예외를 반환한다.#NotFoundMemberException

## 찜 상품등록

- [x] user가 찜을햇다는 정보를 저장한다.
    - [x] 잘못된 {productId}로 요청을 했거나 이미 찜한 상품일 경우 400 Bad Request 로 응답합니다. #각각에 맞는 NotFoundXXXException
- [] 정상적으로 등록이 완료되면,201로 응답한다.

## ViewCountAOP

aop에 트랙잭션이 제대로 적용되지않아서, service레이어의 호출로 하는것으로 바꿈.

- [x] 찜을 할때마다 상품 조회수 1도 증가한다.

## 찜 상품 조회

-[x] 페이징 된 상품 목록을 조회합니다.
-[x] liked 파라미터가 없으면 모든 상품을 조회하되 {user}가 찜한 상품에 liked: true를 추가하고
-[x] liked=false 이면 찜하지 않은 상품만 조회
-[x] liked=true 이면 {user}가 찜한 상품만 조회합니다.
-[x] 잘못된 파라미터가 들어오면 400 Bad Request 로 응답합니다.
-[ ] 정상인 경우 200 OK 로 응답하며, 응답 본문은 antigravity.payload.ProductResponse 를 참고하여 작성합니다.
   