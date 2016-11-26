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