###茶产品的新增(图片暂未处理)
 * URL：http://localhost:8080/api/product/new?productType_id=1&teaSeller_id=1
 * Method:POST
 * 注意事项：    
  name如果用户选择默认，可以从productType的name中获得，不能为空;   
  level 1 2 3分别对应（一般，中等，上等);    
  locality：产地 省＋市＋街道，方便后面做地图的地位使用，可以用控件，让用户选；    
  stock:库存，是double型，但前台要保证它是数字且大于0    
  price:单价，double型，前台要保证是数字且大于0    
  startNum:起售数量，double型，前台要保证是数字且大于0    
  discount：折扣，double型，前台保证是数字，且在0<discount<=1    
  isFree:是否包邮，0不包邮，1包邮 填写0时，要写上邮费       
  postage:邮费，double型，前台保证是数字且大于0    
  deleverLimit:发货的时间间隔，int型，前台保证是数字且大于0    
  unit:计数单位，可以用下拉框供用户选择    
  以上的属性都必须传递到后台，用户不填的，要给予默认值
     
 * 参数：    
  body中：
 <pre> 
 { 
    "remark":"红茶好喝有营养",
    "name":"顶级红茶",
    "level":3,
    "locality":"浙江杭州",
    "stock":100,
    "price":56,
    "startNum":1.0,
    "discount":0.8,
    "isFree":0,
    "postage":5.0,
    "deliverLimit":10,
    "unit":"斤" 
	} </pre>
 url中：  
   productType_id：茶产品类型的id；     
   teaSeller_id茶农的id    
   
* 输出：
  <pre> 
  {
  "code": 200,
  "data": {
    "id": 1,
    "productType": {
      "id": 1,
      "name": "绿茶",
      "descript": "绿茶的描述信息",
      "url": "图片地址url",
      "state": 1,
      "alive": 1
    },
    "remark": "红茶好喝有营养",
    "name": "顶级红茶",
    "level": 3,
    "locality": "浙江杭州",
    "stock": 100,
    "price": 56,
    "startNum": 1,
    "discount": 0.8,
    "isFree": 0,
    "postate": 0,
    "deliverLimit": 10,  发货时间间隔：10或者5或者其它（代表天数）
    "createDate": null,
    "unit": "斤",
    "teaSaler": {
      "id": 1,
      "name": "ycc",
      "level": 1,
      "nickname": "ycc",
      "account": {
        "id": 1,
        "tel": "15821432414",
        "password": "123",
        "label": 0,
        "alive": 1
      },
      "address": "上海市闵行区",
      "tel": "15832341341413",
      "headUrl": "url",
      "money": 100,
      "licenseUrl": "licenseurl",
      "zip": "2341341",
      "idCard": "423432543254523",
      "alive": 1
    },
    "state": 0,
    "alive": 1
  }
}</pre>

### 茶产品的批量修改
* URL:http://localhost:8080/api/products/update
* Method:PUT
* 注意事项：    
只能对stock,price,startNum,discount,isFree,postage,deliverLimit,unit）进行修改，其它不能进行修改。前台必须传的数据是id和修改的字段，未修改的，可以不传。但是仍然要保证数据格式的正确性。
* 参数：
 <pre>
[
{
    "id":1,（必须传）
    "stock":100,（可以不传）
    "price":10,（同上）
    "startNum":5,（同上）
    "discount":0.8（同上）
}
]
</pre>
* 返回值：
 <pre>
{
  "code": 200,
  "data": "all succeed"
}
</pre>
###茶产品的批量上架
* URL:http://localhost:8080/api/products/startSell
* Method:PUT
* 注意事项：
只需要传入需要上架的商品id即可，可以进行上架的产品是存在且未上架的产品
* 参数：
 <pre>
[
    {
        "id":4
    }
]
</pre>
* 输出：
 <pre>
{
  "code": 200,
  "data": "all succeed"
}
</pre>
### 茶产品的多条件组合查询
* URL:http://localhost:8080/api/products/search
* Method:GET
* 注意事项：这个可以输入的条件很多，所有的条件都可以不传或者数字传－1，字符传“”。 对于字符串都是进行模糊匹配   
  productType_id：茶产品类型的id，不需要该条件时可以不传或者传－1； 
  remark：描述信息，name：茶的名字，level：等级1，2，3；locality：产地；    
  isFree：是否包邮, teaSeller_name：茶农的名字，state：产品状态（0未上架  1上架），    
  stock：库存,查找库存大于stock的产品， lowPrice：低单价，查找大于lowPrice的产品，highPrice: 高单价，查找highPrice的产品，startNum：起售数量，查找大于startNum的产品，  discount：折扣，查找折扣力度大于disCount，输入0.8，则查找disCount<=0.8
  （pageIndex，pageSize,sortField,sortOrder）后面四个为分页参数，可以不写
* 参数：在注意事项中，提到的都可以写
* 输入：
 <pre>
{
  "content": [
    {
      "id": 4,
      "productType": {
        "id": 1,
        "name": "绿茶",
        "descript": "绿茶的描述信息",
        "url": "图片地址url",
        "state": 1,
        "alive": 1
      },
      "remark": "红茶好喝有营养",
      "name": "顶级红茶",
      "level": 3,
      "locality": "浙江杭州",
      "stock": 1000,
      "price": 100,
      "startNum": 5,
      "discount": 0.8,
      "isFree": 1,
      "postage": 0,
      "deliverLimit": 10,
      "createDate": "2016-10-31",
      "unit": "斤",
      "teaSaler": {
        "id": 1,
        "name": "ycc",
        "level": 1,
        "nickname": "ycc",
        "account": {
          "id": 1,
          "tel": "15821432414",
          "password": "123",
          "label": 0,
          "alive": 1
        },
        "address": "上海市闵行区",
        "tel": "15832341341413",
        "headUrl": "url",
        "money": 100,
        "licenseUrl": "licenseurl",
        "zip": "2341341",
        "idCard": "423432543254523",
        "alive": 1
      },
      "state": 1,
      "alive": 1
    }
  ],
  "totalElements": 1,
  "totalPages": 1,
  "last": true,
  "size": 10,
  "number": 0,
  "sort": [
    {
      "direction": "ASC",
      "property": "price",
      "ignoreCase": false,
      "nullHandling": "NATIVE",
      "ascending": true
    }
  ],
  "first": true,
  "numberOfElements": 1
}
</pre>
### 根据id查询商品
* URL:http://localhost:7000/api/products/getById?id=1
* Method:GET
* 参数：id 商品id
* 返回值：d
 <pre>
{
  "code": 200,
  "data": {
    "id": null,
    "productType": null,
    "remark": null,
    "name": null,
    "level": 0,
    "locality": null,
    "stock": 0,
    "price": 0,
    "startNum": 0,
    "discount": 0,
    "isFree": 1,
    "postage": 0,
    "deliverLimit": 0,
    "createDate": null,
    "unit": null,
    "teaSaler": null,
    "state": 0,
    "alive": 1,
    "url": null
  }
}
</pre>
### 根据茶农和state查询商品
* URL：http://localhost:7000/api/products/getByTeaSalerAndState?teaSaler_id=1&state=1
* Method:GET
* 输入：    
state:1上架，0未上架的 －1或不传查全部    
teaSaler_id:茶农id
* 返回值：    
 <pre>
 {
  "code": 200,
  "data": [
    {
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
          "headURL": null
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
    }
  ]
}
</pre>

### 根据销量推荐商品
* URL /api/products/commend/rankBySalesVolume
* Method:GET 
* 参数: 无

* 返回:
```
{
  "code": 200,
  "data": [
    {
      "id": 4,
      "productType": {
        。。。
      },
      "remark": "好喝不贵",
      "name": "龙井",
      "level": 2,
      "locality": "浙江 湖州 长兴县",
      "stock": 22821,
      "price": 12,
      "startNum": 10,
      "discount": 1,
      "isFree": 1,
      "postage": 0,
      "deliverLimit": 12,
      "createDate": "2016-11-18",
      "unit": "两",
      "teaSaler": {
        。。。
      },
      "state": 1,
      "alive": 1,
      "url": "21.png",
      "type": 0
    },{
    "id": 5,
    。。。
    }
  ]
}

```

### 某一商品的评分与评价
* URL /api/products/comment/getById?id=4
* Method:GET 
* 参数: 无

* 返回:
```
{
  "code": 200,
  "data": {
    "product": {
      "id": 4,
      "productType": {
        "id": 1,
        "name": "红茶",
        "descript": "红茶属全发酵茶，是以适宜的茶树新牙叶为原料，经萎凋、揉捻（切）、发酵、干燥等一系列工艺过程精制而成的茶。萎凋是红茶初制的重要工艺，红茶在初制时称为“乌茶”。红茶因其干茶冲泡后的茶汤和叶底色呈红色而得名。",
        "url": "11.png",
        "state": 1,
        "alive": 1
      },
      "remark": "好喝不贵",
      "name": "龙井",
      "level": 2,
      "locality": "浙江 湖州 长兴县",
      "stock": 22813,
      "price": 12,
      "startNum": 10,
      "discount": 1,
      "isFree": 1,
      "postage": 0,
      "deliverLimit": 12,
      "createDate": "2016-11-18",
      "unit": "两",
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
          "money": 2857.1
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
      "state": 1,
      "alive": 1,
      "url": "21.png",
      "type": 0
    },
    "averageScore": 50,
    "numOfComment": 2
  }
}

```
### 茶产品价格预测
* URL:/api/products/price/predicte
* Method:GET
* 注意事项：    
* 参数：
 <pre>
无

</pre>
* 返回值：
 <pre>
{
  "code": 200,
  "data": {
    "West Lake Longjing": 514.16,
    "Tieguanyin": 506.34,
    "Biluochun": 476.97
  }
}
</pre>