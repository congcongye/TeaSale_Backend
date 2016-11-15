### 添加商品到购物车
* URL:http://localhost:7000/api/cart/addToCart?product_id=1&num=12&price=50&customer_id=1
* Method:POST
* 输入：    
product_id： 商品id    
num：商品的数量    
customer_id：消费者
* 输出：
 <pre>
{
  "code": 200,
  "data": {
    "id": 2,
    "customer": {
      "id": 1,
      "level": 1,
      "nickname": "ycc",
      "account": {
        "id": 2,
        "tel": "14524354",
        "password": "123",
        "label": 2,
        "alive": 1,
        "headURL": null,
        "money": 1000
      },
      "address": "上海闵行区",
      "zip": "12341",
      "tel": "124324",
      "money": 1000,
      "headUrl": "234143",
      "alive": 1,
      "createDate": "2016-11-15"
    },
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
      "url": "1.jpg"
    },
    "num": 24,
    "price": 50,
    "joinDate": "2016-11-15",
    "alive": 1
  }
}
</pre>
#### 购物车的批量删除
* URL：http://localhost:7000/api/carts/delete
* Method:PUT
* 输入：
 <pre>
[
    {
        "id":2   需要删除的商品id
    }
]
</pre>
* 输出
 <pre>
{
  "code": 200,
  "data": "all succeed"
}
</pre>
### 购物车批量修改
* URL:http://localhost:7000/api/carts/update
* Method:PUT
* 输入：
 <pre>
[
    {
        "id":2, 购车id
        "num":10  商品修改之后的数量
    }
]
</pre>
* 输出：
 <pre>
{
  "code": 200,
  "data": [
    {
      "id": 2,
      "customer": {
        "id": 1,
        "level": 1,
        "nickname": "ycc",
        "account": {
          "id": 2,
          "tel": "14524354",
          "password": "123",
          "label": 2,
          "alive": 1,
          "headURL": null,
          "money": 1000
        },
        "address": "上海闵行区",
        "zip": "12341",
        "tel": "124324",
        "money": 1000,
        "headUrl": "234143",
        "alive": 1,
        "createDate": "2016-11-15"
      },
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
        "price": 110,
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
        "url": "1.jpg"
      },
      "num": 10,
      "price": 110,
      "joinDate": "2016-11-15",
      "alive": 1
    }
  ]
}
</pre>
#### 购物车的查询
* URL:http://localhost:7000/api/carts/searchAll?customer_id=1
* Method：GET
* 输入：
customer_id:消费者的id
* 输出：
  <pre>
{
  "code": 200,
  "data": [
    {
      "id": 2,
      "customer": {
        "id": 1,
        "level": 1,
        "nickname": "ycc",
        "account": {
          "id": 2,
          "tel": "14524354",
          "password": "123",
          "label": 2,
          "alive": 1,
          "headURL": null,
          "money": 1000
        },
        "address": "上海闵行区",
        "zip": "12341",
        "tel": "124324",
        "money": 1000,
        "headUrl": "234143",
        "alive": 1,
        "createDate": "2016-11-15"
      },
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
        "price": 110,
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
        "url": "1.jpg"
      },
      "num": 10,
      "price": 110,
      "joinDate": "2016-11-15",
      "alive": 1
    }
  ]
}
</pre>