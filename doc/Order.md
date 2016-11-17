# Api

### 搜索订单
* URL /api/orders/search?customerId=1&teaSalerId=1&state=1&isSend=0&isConfirm=0&isComment=0&type=0&customerDelete=0&adminDelete=0&salerDelete=0&Refund_state=0&name=""&address=""&tel=""&pageIndex=0&pageSize=10&sortField=id&sortOrder=ASC
* Method: GET
* 参数:
```
说明
state:订单状态 0 未完成, 1已付款,2已完成
isSend:是否发货 0 否, 1是
isConfirm:是否确认收货 0 否, 1是
isComment:是否评论 0 否, 1是
type:订单类型（一般，众筹，众包） (0, 1, 2)
Refund_state:(未支付，全支付，部分支付)(0, 1, 2)
```
* 返回:
```
{
  "code": 200,
  "data": {
    "content": [],
    "totalElements": 0,
    "last": true,
    "totalPages": 0,
    "size": 10,
    "number": 0,
    "sort": [
      {
        "direction": "ASC",
        "property": "id",
        "ignoreCase": false,
        "nullHandling": "NATIVE",
        "ascending": true
      }
    ],
    "first": true,
    "numberOfElements": 0
  }
}
```
### 新增订单
* URL /api/order/add
* Method: POST
* 参数:
```
{
    "teaSalerId":1,
    "customerId":2,
    "name":"李神",
    "address":"交大",
    "zip":"123434",
    "logistic":12.23,
    "type":0,
    "tel":1391896653
}
```
* 返回:
```
{
  "code": 200,
  "data": {
    "id": 13,
    "createDate": "2016-11-17",
    "teaSaler": {
      "id": 1,
      "name": "金初阳",
      "level": 0,
      "nickname": "zizi",
      "account": {
        "id": 3,
        "tel": "15907823456",
        "password": "1111111",
        "label": 0,
        "alive": 1,
        "headURL": null,
        "money": 0
      },
      "address": "zizi",
      "tel": "15907823456",
      "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
      "money": 10000,
      "licenseUrl": "7f839a068dab48aeb97a7a220c23abe4.png",
      "zip": "315200",
      "idCard": "12345678",
      "state": 1,
      "alive": 1,
      "createDate": "2016-11-10"
    },
    "customer": {
      "id": 2,
      "level": 0,
      "nickname": "李神",
      "account": {
        "id": 9,
        "tel": "13018906537",
        "password": "123456",
        "label": 2,
        "alive": 1,
        "headURL": null,
        "money": 0
      },
      "address": "闵行东川路",
      "zip": "12344567",
      "tel": "13018906537",
      "money": 0,
      "headUrl": null,
      "alive": 1,
      "createDate": "2016-11-17"
    },
    "name": null,
    "address": "交大",
    "zip": "123434",
    "tel": "1391896653",
    "totalPrice": 0,
    "logistic": 0,
    "state": 0,
    "isSend": 0,
    "SendDate": null,
    "isConfirm": 0,
    "confirmDate": null,
    "isComment": 0,
    "score": -1,
    "comment": null,
    "type": 0,
    "customerDelete": 0,
    "adminDelete": 0,
    "salerDelete": 0,
    "alive": 1,
    "sendDate": null,
    "refund_state": 0
  }
}
```
