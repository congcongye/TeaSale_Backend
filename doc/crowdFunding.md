#### 发起众筹
* URL:http://localhost:7000/api/crowdFund/new
* Method: POST
* 输入：将整个众包对象传入，具体参数的含义，请参考数据库表
 <pre>
{
    "product": {
      "id": 1,
      "productType": {
        "id": 1,
        "name": "红茶",
        "descript": "红茶",
        "url": "1.jpg",
        "state": 1,
        "alive": 1
      },
      "remark": "产品1",
      "name": "红茶",
      "level": 1,
      "locality": "上海闵行区",
      "stock": 1000,
      "price": 100,
      "startNum": 5,
      "discount": 0.8,
      "isFree": 1,
      "postage": 5,
      "deliverLimit": 10,
      "createDate": "2016-11-11",
      "unit": "两",
      "teaSaler": {
        "id": 1,
        "name": "ycc",
        "level": 1,
        "nickname": "ycc",
        "account": {
          "id": 1,
          "tel": "12314",
          "password": "123",
          "label": 1,
          "alive": 1,
          "headURL": null,
          "money": 0
        },
        "address": "上海市",
        "tel": "12341341",
        "headUrl": "1.jpg",
        "money": 100,
        "licenseUrl": "1.jpg",
        "zip": "14134",
        "idCard": "14rwerq",
        "state": 1,
        "alive": 1,
        "createDate": "2016-11-11"
      },
      "state": 0,
      "alive": 1,
      "url": "1.jpg",
      "type": 0
    },
    "type":0,
    "earnest":1,
    "unitNum":1,
    "unitMoney":10,
    "JoinNum":1,
    "createDate":"",
    "dealDate":"",
    "deliverDate":"",
    "payDate":"",
    "totalNum":100
}
</pre>    
* 输出:    
  <pre>
 {
  "code": 200,
  "data": {
    "id": 1,
    "product": {
      "id": 1,
      "productType": {
        "id": 1,
        "name": "红茶",
        "descript": "红茶",
        "url": "1.jpg",
        "state": 1,
        "alive": 1
      },
      "remark": "产品1",
      "name": "红茶",
      "level": 1,
      "locality": "上海闵行区",
      "stock": 1000,
      "price": 100,
      "startNum": 5,
      "discount": 0.8,
      "isFree": 1,
      "postage": 5,
      "deliverLimit": 10,
      "createDate": "2016-11-27",
      "unit": "两",
      "teaSaler": {
        "id": 1,
        "name": "ycc",
        "level": 1,
        "nickname": "ycc",
        "account": {
          "id": 1,
          "tel": "12314",
          "password": "123",
          "label": 1,
          "alive": 1,
          "headURL": null,
          "money": 0
        },
        "address": "上海市",
        "tel": "12341341",
        "headUrl": "1.jpg",
        "money": 100,
        "licenseUrl": "1.jpg",
        "zip": "14134",
        "idCard": "14rwerq",
        "state": 1,
        "alive": 1,
        "createDate": "2016-11-11"
      },
      "state": 1,
      "alive": 1,
      "url": "1.jpg",
      "type": 1
    },
    "type": 0,
    "earnest": 0,
    "unitNum": 0,
    "unitMoney": 0,
    "createDate": null,
    "dealDate": null,
    "deliverDate": null,
    "payDate": null,
    "state": 0,
    "alive": 0,
    "joinNum": 0
  }
}
</pre>
#### 修改众筹
* URL:http://localhost:7000/api/crowdFund/update
* Method:PUT
* 输入：
 <pre>
{
    "id": 1,
    "product": {
      "id": 1,
      "productType": {
        "id": 1,
        "name": "红茶",
        "descript": "红茶",
        "url": "1.jpg",
        "state": 1,
        "alive": 1
      },
      "remark": "产品1",
      "name": "红茶",
      "level": 1,
      "locality": "上海闵行区",
      "stock": 1000,
      "price": 100,
      "startNum": 5,
      "discount": 0.8,
      "isFree": 1,
      "postage": 5,
      "deliverLimit": 10,
      "createDate": "2016-11-27",
      "unit": "两",
      "teaSaler": {
        "id": 1,
        "name": "ycc",
        "level": 1,
        "nickname": "ycc",
        "account": {
          "id": 1,
          "tel": "12314",
          "password": "123",
          "label": 1,
          "alive": 1,
          "headURL": null,
          "money": 0
        },
        "address": "上海市",
        "tel": "12341341",
        "headUrl": "1.jpg",
        "money": 100,
        "licenseUrl": "1.jpg",
        "zip": "14134",
        "idCard": "14rwerq",
        "state": 1,
        "alive": 1,
        "createDate": "2016-11-11"
      },
      "state": 1,
      "alive": 1,
      "url": "1.jpg",
      "type": 1
    },
    "type": 0,
    "earnest": 0,
    "unitNum": 0,
    "unitMoney": 0,
    "createDate": null,
    "dealDate": null,
    "deliverDate": null,
    "payDate": null,
    "state": 0,
    "alive": 0,
    "joinNum": 0
  }
</pre>
* 输出：
<pre>
{
"code":200,
"data":
{
    "id": 1,
    "product": {
      "id": 1,
      "productType": {
        "id": 1,
        "name": "红茶",
        "descript": "红茶",
        "url": "1.jpg",
        "state": 1,
        "alive": 1
      },
      "remark": "产品1",
      "name": "红茶",
      "level": 1,
      "locality": "上海闵行区",
      "stock": 1000,
      "price": 100,
      "startNum": 5,
      "discount": 0.8,
      "isFree": 1,
      "postage": 5,
      "deliverLimit": 10,
      "createDate": "2016-11-27",
      "unit": "两",
      "teaSaler": {
        "id": 1,
        "name": "ycc",
        "level": 1,
        "nickname": "ycc",
        "account": {
          "id": 1,
          "tel": "12314",
          "password": "123",
          "label": 1,
          "alive": 1,
          "headURL": null,
          "money": 0
        },
        "address": "上海市",
        "tel": "12341341",
        "headUrl": "1.jpg",
        "money": 100,
        "licenseUrl": "1.jpg",
        "zip": "14134",
        "idCard": "14rwerq",
        "state": 1,
        "alive": 1,
        "createDate": "2016-11-11"
      },
      "state": 1,
      "alive": 1,
      "url": "1.jpg",
      "type": 1
    },
    "type": 0,
    "earnest": 0,
    "unitNum": 0,
    "unitMoney": 0,
    "createDate": null,
    "dealDate": null,
    "deliverDate": null,
    "payDate": null,
    "state": 0,
    "alive": 0,
    "joinNum": 0,
    "totalNum":100
  }
}
</pre>
#### 众包删除
* URL:http://localhost:7000/api/crowdFund/delete
* Method:PUT
* 输入：
	<pre>[
{
    "id":1
}
]</pre>
* 输出：


#### 众筹订单的生成
* URL:http://localhost:7000/api/orders/add
* Method: POST
* 输入：
 <pre>
[
    {
        "teaSalerId":1,
        "customerId":1,
        "name":"test",
        "address":"dongchuanlu",
        "zip":"213000",
        "tel":"12345678908",
        "type":1,
        "crowdFundingId":1,
        "createOrderItemModels":[
            {
                "productId":4,
                "num":10
            }
         ]
    }
    ]
</pre>    
* 输出:    
<pre> 
 {
   "code": 200,
   "data": [
     {
       "id": 22,
       "createDate": "2016-12-05",
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
           "money": 10436320
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
       "name": "test",
       "address": "dongchuanlu",
       "zip": "213000",
       "tel": "12345678908",
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
       "type": 1,
       "customerDelete": 0,
       "adminDelete": 0,
       "salerDelete": 0,
       "alive": 1,
       "sendDate": null,
       "refund_state": 0
     }
   ]
 }
</pre>