# Api

### 搜索订单
* URL /api/orders/search?customerId=1&teaSalerId=1&state=1&isSend=0&isConfirm=0&isComment=0&type=0&customerDelete=0&adminDelete=0&salerDelete=0&Refund_state=0&name=""&address=""&tel=""&beginDateStr=2016-10-10&endDateStr=2016-12-10&pageIndex=0&pageSize=10&sortField=id&sortOrder=ASC
* Method: GET
* 参数说明:
```
teaSalerName:茶农名字,当不传茶农id时使用该参数
state:订单状态 0 未完成, 1已付款,2已完成
isSend:是否发货 0 否, 1是
isConfirm:是否确认收货 0 否, 1是
isComment:是否评论 0 否, 1是
type:订单类型（一般，众筹，众包） (0, 1, 2)
Refund_state:(未支付，全支付，部分支付)(0, 1, 2)
beginDateStr=2016-10-10 订单创建起始时间
endDateStr=2016-12-10 订单创建结束时间
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
* URL /api/orders/add
* Method: POST
* 参数:
* 
```
[
    {
        "teaSalerId":1,
        "customerId":1,
        "name":"1111",
        "address":"1111",
        "zip":"111",
        "tel":"11111111111",
        "createOrderItemModels":[
            {
                "productId":2,
                "num":3
            },
            {
                "productId":1,
                "num":300
            }
        ]
    }
]

```

* 返回:

```
{
  "code": 200,
  "data": [
    {
      "id": 18,
      "createDate": "2016-11-20",
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
          "money": 10000000
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
        "id": 1,
        "level": 0,
        "nickname": "123",
        "account": {
          "id": 8,
          "tel": "13918966539",
          "password": "123456",
          "label": 0,
          "alive": 1,
          "headURL": "836e8e3a0af54847902cc1d31cb68e89.jpg",
          "money": 9781840
        },
        "address": "闵行东川路",
        "zip": "12344567",
        "tel": "13918966539",
        "money": 0,
        "headUrl": null,
        "alive": 1,
        "createDate": "2016-11-11"
      },
      "name": null,
      "address": "1111",
      "zip": "111",
      "tel": "11111111111",
      "totalPrice": 218160,
      "logistic": 0,
      "state": 1,
      "isSend": 0,
      "SendDate": null,
      "isConfirm": 0,
      "confirmDate": null,
      "isComment": 0,
      "score": -1,
      "comment": null,
      "customerDelete": 0,
      "adminDelete": 0,
      "salerDelete": 0,
      "alive": 1,
      "sendDate": null,
      "refund_state": 0
    }
  ]
}
```

### 搜索订单
* URL /api/orders/orderItems/search?customerId=1&teaSalerId=1&state=1&isSend=0&isConfirm=0&isComment=0&type=0&customerDelete=0&adminDelete=0&salerDelete=0&Refund_state=0&name=""&address=""&tel=""&beginDateStr=2016-10-10&endDateStr=2016-12-10&pageIndex=0&pageSize=10&sortField=id&sortOrder=ASC
* Method: GET
* 参数:

```
说明
teaSalerName:茶农名字,当不传茶农id时使用该参数
state:订单状态 0 未完成, 1已付款,2已完成,3已取消
isSend:是否发货 0 否, 1是
isConfirm:是否确认收货 0 否, 1是
isComment:是否评论 0 否, 1是
type:订单类型（一般，众筹，众包） (0, 1, 2)
Refund_state:(未支付，全支付，部分支付)(0, 1, 2)
beginDateStr=2016-10-10 订单创建起始时间
endDateStr=2016-12-10 订单创建结束时间
```

* 返回

```
{
  "code": 200,
  "data": {
    "totalCount": 1,
    "totalPage": 1,
    "list": [
      {
        "orderEn": {
          "id": 2,
          "createDate": "2016-11-17",
          "teaSaler": {
            "id": 1,
            "name": "李桐宇",
            "level": 0,
            "nickname": "413",
            "account": {
              "id": 3,
              "tel": "15907823456",
              "password": "1111111",
              "label": 0,
              "alive": 1,
              "headURL": null,
              "money": 10000000
            },
            "address": "x36-4041",
            "tel": "15907823456",
            "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
            "money": 10000,
            "licenseUrl": "7f839a068dab48aeb97a7a220c23abe4.png",
            "zip": "210000",
            "idCard": "12345678",
            "state": 1,
            "alive": 1,
            "createDate": "2016-11-10"
          },
          "customer": {
            "id": 1,
            "level": 0,
            "nickname": "123",
            "account": {
              "id": 8,
              "tel": "13918966539",
              "password": "123456",
              "label": 0,
              "alive": 1,
              "headURL": "836e8e3a0af54847902cc1d31cb68e89.jpg",
              "money": 10000000
            },
            "address": "闵行东川路",
            "zip": "12344567",
            "tel": "13918966539",
            "money": 0,
            "headUrl": null,
            "alive": 1,
            "createDate": "2016-11-11"
          },
          "name": "订单2",
          "address": "交大",
          "zip": "123434",
          "tel": "1391896653",
          "totalPrice": 3600,
          "logistic": 0,
          "state": 1,
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
        },
        "orderItems": [
          {
            "id": 3,
            "product": {
              "id": 1,
              "productType": {
                "id": 1,
                "name": "绿茶",
                "descript": "绿茶的描述信息",
                "url": "图片地址url",
                "state": 1,
                "alive": 1
              },
              "remark": "iiii",
              "name": "qwer",
              "level": 1,
              "locality": null,
              "stock": 999400,
              "price": 900,
              "startNum": 10,
              "discount": 0.8,
              "isFree": 1,
              "postage": 11,
              "deliverLimit": 1,
              "createDate": "2016-11-08",
              "unit": "两",
              "teaSaler": {
                "id": 2,
                "name": "金初阳",
                "level": 0,
                "nickname": "413",
                "account": {
                  "id": 4,
                  "tel": "15907823451",
                  "password": "1111111",
                  "label": 0,
                  "alive": 1,
                  "headURL": null,
                  "money": 10000000
                },
                "address": "x36-4041",
                "tel": "15907823451",
                "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
                "money": 10000,
                "licenseUrl": null,
                "zip": "210000",
                "idCard": "12345678",
                "state": 1,
                "alive": 1,
                "createDate": "2016-11-10"
              },
              "state": 1,
              "alive": 1,
              "url": null
            },
            "orderen": {
              "id": 2,
              "createDate": "2016-11-17",
              "teaSaler": {
                "id": 1,
                "name": "李桐宇",
                "level": 0,
                "nickname": "413",
                "account": {
                  "id": 3,
                  "tel": "15907823456",
                  "password": "1111111",
                  "label": 0,
                  "alive": 1,
                  "headURL": null,
                  "money": 10000000
                },
                "address": "x36-4041",
                "tel": "15907823456",
                "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
                "money": 10000,
                "licenseUrl": "7f839a068dab48aeb97a7a220c23abe4.png",
                "zip": "210000",
                "idCard": "12345678",
                "state": 1,
                "alive": 1,
                "createDate": "2016-11-10"
              },
              "customer": {
                "id": 1,
                "level": 0,
                "nickname": "123",
                "account": {
                  "id": 8,
                  "tel": "13918966539",
                  "password": "123456",
                  "label": 0,
                  "alive": 1,
                  "headURL": "836e8e3a0af54847902cc1d31cb68e89.jpg",
                  "money": 10000000
                },
                "address": "闵行东川路",
                "zip": "12344567",
                "tel": "13918966539",
                "money": 0,
                "headUrl": null,
                "alive": 1,
                "createDate": "2016-11-11"
              },
              "name": "订单2",
              "address": "交大",
              "zip": "123434",
              "tel": "1391896653",
              "totalPrice": 3600,
              "logistic": 0,
              "state": 1,
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
            },
            "num": 3,
            "totalPrice": 2160,
            "alive": 1,
            "isComment": 0
          },
          {
            "id": 4,
            "product": {
              "id": 1,
              "productType": {
                "id": 1,
                "name": "绿茶",
                "descript": "绿茶的描述信息",
                "url": "图片地址url",
                "state": 1,
                "alive": 1
              },
              "remark": "iiii",
              "name": "qwer",
              "level": 1,
              "locality": null,
              "stock": 999400,
              "price": 900,
              "startNum": 10,
              "discount": 0.8,
              "isFree": 1,
              "postage": 11,
              "deliverLimit": 1,
              "createDate": "2016-11-08",
              "unit": "两",
              "teaSaler": {
                "id": 2,
                "name": "金初阳",
                "level": 0,
                "nickname": "413",
                "account": {
                  "id": 4,
                  "tel": "15907823451",
                  "password": "1111111",
                  "label": 0,
                  "alive": 1,
                  "headURL": null,
                  "money": 10000000
                },
                "address": "x36-4041",
                "tel": "15907823451",
                "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
                "money": 10000,
                "licenseUrl": null,
                "zip": "210000",
                "idCard": "12345678",
                "state": 1,
                "alive": 1,
                "createDate": "2016-11-10"
              },
              "state": 1,
              "alive": 1,
              "url": null
            },
            "orderen": {
              "id": 2,
              "createDate": "2016-11-17",
              "teaSaler": {
                "id": 1,
                "name": "李桐宇",
                "level": 0,
                "nickname": "413",
                "account": {
                  "id": 3,
                  "tel": "15907823456",
                  "password": "1111111",
                  "label": 0,
                  "alive": 1,
                  "headURL": null,
                  "money": 10000000
                },
                "address": "x36-4041",
                "tel": "15907823456",
                "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
                "money": 10000,
                "licenseUrl": "7f839a068dab48aeb97a7a220c23abe4.png",
                "zip": "210000",
                "idCard": "12345678",
                "state": 1,
                "alive": 1,
                "createDate": "2016-11-10"
              },
              "customer": {
                "id": 1,
                "level": 0,
                "nickname": "123",
                "account": {
                  "id": 8,
                  "tel": "13918966539",
                  "password": "123456",
                  "label": 0,
                  "alive": 1,
                  "headURL": "836e8e3a0af54847902cc1d31cb68e89.jpg",
                  "money": 10000000
                },
                "address": "闵行东川路",
                "zip": "12344567",
                "tel": "13918966539",
                "money": 0,
                "headUrl": null,
                "alive": 1,
                "createDate": "2016-11-11"
              },
              "name": "订单2",
              "address": "交大",
              "zip": "123434",
              "tel": "1391896653",
              "totalPrice": 3600,
              "logistic": 0,
              "state": 1,
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
            },
            "num": 2,
            "totalPrice": 1440,
            "alive": 1,
            "isComment": 0
          }
        ]
      }
    ],
    "pageSize": 10,
    "pageIndex": 0
  }
}
```

### 更新订单 确认发货与确认收货
* URL /api/order/update
* Method: PUT
* 参数:

```
{
    "orderId":1,
    "isConfirm":"0",     确认收货isConfrim = 1, 默认为0   
    "isSend":1     确认发货isSend = 1, 默认为0
}

```

* 返回

```
{
  "code": 200,
  "data": {
    "id": 1,
    "createDate": "2016-11-16",
    "teaSaler": {
      "id": 1,
      "name": "李桐宇",
      "level": 0,
      "nickname": "413",
      "account": {
        "id": 3,
        "tel": "15907823456",
        "password": "1111111",
        "label": 0,
        "alive": 1,
        "headURL": null,
        "money": 10000000
      },
      "address": "x36-4041",
      "tel": "15907823456",
      "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
      "money": 10000,
      "licenseUrl": "7f839a068dab48aeb97a7a220c23abe4.png",
      "zip": "210000",
      "idCard": "12345678",
      "state": 1,
      "alive": 1,
      "createDate": "2016-11-10"
    },
    "customer": {
      "id": 1,
      "level": 0,
      "nickname": "123",
      "account": {
        "id": 8,
        "tel": "13918966539",
        "password": "123456",
        "label": 0,
        "alive": 1,
        "headURL": "836e8e3a0af54847902cc1d31cb68e89.jpg",
        "money": 10000000
      },
      "address": "闵行东川路",
      "zip": "12344567",
      "tel": "13918966539",
      "money": 0,
      "headUrl": null,
      "alive": 1,
      "createDate": "2016-11-11"
    },
    "name": "订单1",
    "address": "交大",
    "zip": "123434",
    "tel": "1391896653",
    "totalPrice": 218160,
    "logistic": 0,
    "state": 1,
    "isSend": 1,
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

 #### 订单取消
 * URL:/api/order/cancel
 * Method: PUT
 * 参数：
<pre>
 {
     "id":1
 }
</pre>    
* 输出:    
<pre> 
  {
    "code": 200,
    "data": {
      "id": 1,
      "createDate": "2016-11-20",
      "teaSaler": {
        "id": 1,
        "name": "叶聪聪",
        "level": 1,
        "nickname": "ycc",
        "account": {
          "id": 1,
          "tel": "15821527768",
          "password": "123456",
          "label": 1,
          "alive": 1,
          "headURL": "h1.png",
          "money": 2635
        },
        "address": "上海市 闵行区",
        "tel": "15821527768",
        "headUrl": "h1.png",
        "money": 1000,
        "licenseUrl": "c1.png",
        "zip": "435100",
        "idCard": "420281199311118111",
        "state": 1,
        "alive": 1,
        "createDate": "2016-11-11"
      },
      "customer": {
        "id": 1,
        "level": 0,
        "nickname": "孙晏",
        "account": {
          "id": 4,
          "tel": "13918966539",
          "password": "123456",
          "label": 2,
          "alive": 1,
          "headURL": "h4.png",
          "money": 3961
        },
        "address": "上海 虹口区 虹口足球场",
        "zip": "446543",
        "tel": "13918966539",
        "money": 1000,
        "headUrl": "h4.png",
        "alive": 1,
        "createDate": "2016-11-11"
      },
      "name": "",
      "address": "浙江 宁波 江东区 主任家",
      "zip": "446543",
      "tel": "13918966539",
      "totalPrice": 48,
      "logistic": 0,
      "state": 3,
      "isSend": 1,
      "SendDate": null,
      "isConfirm": 0,
      "confirmDate": null,
      "isComment": 0,
      "score": -1,
      "comment": null,
      "customerDelete": 0,
      "adminDelete": 0,
      "salerDelete": 0,
      "alive": 1,
      "refund_state": 0,
      "sendDate": null
    }
  }
</pre>

### 支付未完成的
* URL /api/order/payUnFinished
* Method: PUT
* 参数:
```
{
    "id":1
}
```