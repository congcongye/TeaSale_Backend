### 众包的新增
* URL:http://localhost:7000/api//crowdSourcing/new?product_id=17&customer_id=1
* Method:POST
* 输入
 <pre>
{
    "earnest":1,
    "unitNum":1,
    "unitMoney":10,
    "dealDate":"2016-12-25 08:30:30",
    "createDate":"2016-12-23 08:30:30",
    "deliverDate":"2016-12-30 08:30:30",
    "totalNum":100
}
</pre>
* 输出
<pre>
{
  "code": 200,
  "data": {
    "id": 1,
    "product": {
      "id": 17,
      "productType": {
        "id": 3,
        "name": "普洱茶",
        "descript": "普洱茶,又称大乔木，高达16米，嫩枝有微毛，顶芽有白柔毛。叶薄革质，椭圆形，上面干后褐绿色，略有光泽，下面浅绿色，中肋上有柔毛，其余被短柔毛，老叶变秃；侧脉8-9对，在上面明显。花腋生，被柔毛。苞片2，早落。萼片5，近圆形，外面无毛。花瓣6-7片，倒卵形，无毛。雄蕊长8-10毫米，离生，无毛。子房3室，被茸毛；花柱长8毫米，先端3裂。蒴果扁三角球形。种子每室1个，近圆形，直径1厘米。",
        "url": "31.png",
        "state": 1,
        "alive": 1
      },
      "remark": "出天然，优质土壤栽培",
      "name": "有机普洱茶",
      "level": 2,
      "locality": "浙江 嘉兴 桐乡市",
      "stock": 2000,
      "price": 20,
      "startNum": 20,
      "discount": 0.9,
      "isFree": 1,
      "postage": 0,
      "deliverLimit": 8,
      "createDate": "2016-12-23",
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
      "state": 1,
      "alive": 1,
      "url": "31.png",
      "type": 2
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
        "money": 4009.2
      },
      "address": "上海 虹口区 虹口足球场",
      "zip": "446543",
      "tel": "13918966539",
      "money": 1000,
      "headUrl": "h4.png",
      "alive": 1,
      "createDate": "2016-11-11"
    },
    "earnest": 1,
    "unitNum": 1,
    "unitMoney": 10,
    "createDate": "2016-12-23",
    "dealDate": "2016-12-25",
    "deliverDate": null,
    "state": 0,
    "alive": 1,
    "totalNum": 100,
    "remainderNum": 100,
    "joinNum": 0
  }
}
</pre>
### 众包修改
* URL:http://localhost:7000/api//crowdSourcing/update?id=1
* Method:PUT
* 输入
 <pre>
{
    "earnest":1,
    "unitNum":1,
    "unitMoney":10,
    "dealDate":"2016-12-25 08:30:30",
    "createDate":"2016-12-23 08:30:30",
    "deliverDate":"2016-12-30 08:30:30",
    "totalNum":1000
}
</pre>
* 输入  
<pre>
{
  "code": 200,
  "data": {
    "id": 1,
    "product": {
      "id": 17,
      "productType": {
        "id": 3,
        "name": "普洱茶",
        "descript": "普洱茶,又称大乔木，高达16米，嫩枝有微毛，顶芽有白柔毛。叶薄革质，椭圆形，上面干后褐绿色，略有光泽，下面浅绿色，中肋上有柔毛，其余被短柔毛，老叶变秃；侧脉8-9对，在上面明显。花腋生，被柔毛。苞片2，早落。萼片5，近圆形，外面无毛。花瓣6-7片，倒卵形，无毛。雄蕊长8-10毫米，离生，无毛。子房3室，被茸毛；花柱长8毫米，先端3裂。蒴果扁三角球形。种子每室1个，近圆形，直径1厘米。",
        "url": "31.png",
        "state": 1,
        "alive": 1
      },
      "remark": "出天然，优质土壤栽培",
      "name": "有机普洱茶",
      "level": 2,
      "locality": "浙江 嘉兴 桐乡市",
      "stock": 2000,
      "price": 20,
      "startNum": 20,
      "discount": 0.9,
      "isFree": 1,
      "postage": 0,
      "deliverLimit": 8,
      "createDate": "2016-12-23",
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
      "state": 1,
      "alive": 1,
      "url": "31.png",
      "type": 2
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
        "money": 4009.2
      },
      "address": "上海 虹口区 虹口足球场",
      "zip": "446543",
      "tel": "13918966539",
      "money": 1000,
      "headUrl": "h4.png",
      "alive": 1,
      "createDate": "2016-11-11"
    },
    "earnest": 1,
    "unitNum": 1,
    "unitMoney": 10,
    "createDate": "2016-12-23",
    "dealDate": "2016-12-25",
    "deliverDate": null,
    "state": 0,
    "alive": 1,
    "totalNum": 1000,
    "remainderNum": 1000,
    "joinNum": 0
  }
}
</pre>
#### 众包删除
URL:http://localhost:7000/api//crowdSourcing/delete?id=1
Method：DELETE
输入：
id：要删除的众包id
输出：
<pre>
{
  "code": 200,
  "data": "all succeed"
}
</pre>
#### 众包查询
* URL:http://localhost:7000/api//crowdSourcing/search?customer_id=1&productType_id=1&state=0&productName=1&pageSize=10&pageIndex=0&sortField=id&sortOrder=ASC
* Method:GET
* 输入：这些参数都可以不填，如果要填不需要的参数数字写－1，字符串传“”
customer_id:用户id
productType_id:产品类型id
state：0开始状态  1众包成功
productName:产品名字
pageSize:每页的大小
pageIndex:分页起始指
sortField:排序字段
sortOrder:排序顺序
* 输出：
 <pre>
{
  "code": 200,
  "data": {
    "content": [
      {
        "id": 1,
        "product": {
          "id": 17,
          "productType": {
            "id": 3,
            "name": "普洱茶",
            "descript": "普洱茶,又称大乔木，高达16米，嫩枝有微毛，顶芽有白柔毛。叶薄革质，椭圆形，上面干后褐绿色，略有光泽，下面浅绿色，中肋上有柔毛，其余被短柔毛，老叶变秃；侧脉8-9对，在上面明显。花腋生，被柔毛。苞片2，早落。萼片5，近圆形，外面无毛。花瓣6-7片，倒卵形，无毛。雄蕊长8-10毫米，离生，无毛。子房3室，被茸毛；花柱长8毫米，先端3裂。蒴果扁三角球形。种子每室1个，近圆形，直径1厘米。",
            "url": "31.png",
            "state": 1,
            "alive": 1
          },
          "remark": "出天然，优质土壤栽培",
          "name": "有机普洱茶",
          "level": 2,
          "locality": "浙江 嘉兴 桐乡市",
          "stock": 2000,
          "price": 20,
          "startNum": 20,
          "discount": 0.9,
          "isFree": 1,
          "postage": 0,
          "deliverLimit": 8,
          "createDate": "2016-12-23",
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
          "state": 1,
          "alive": 1,
          "url": "31.png",
          "type": 2
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
            "money": 4009.2
          },
          "address": "上海 虹口区 虹口足球场",
          "zip": "446543",
          "tel": "13918966539",
          "money": 1000,
          "headUrl": "h4.png",
          "alive": 1,
          "createDate": "2016-11-11"
        },
        "earnest": 1,
        "unitNum": 1,
        "unitMoney": 10,
        "createDate": "2016-12-23",
        "dealDate": "2016-12-25",
        "deliverDate": "2016-12-30",
        "state": 0,
        "alive": 1,
        "totalNum": 1000,
        "remainderNum": 1000,
        "joinNum": 0
      }
    ],
    "totalElements": 1,
    "totalPages": 1,
    "last": true,
    "size": 10,
    "number": 0,
    "numberOfElements": 1,
    "sort": [
      {
        "direction": "ASC",
        "property": "id",
        "ignoreCase": false,
        "nullHandling": "NATIVE",
        "ascending": true
      }
    ],
    "first": true
  }
}
</pre>
#### 众包的根据Id查
* URL:http://localhost:7000/api/crowdSourcing/getById?id=1
* Method:GET
* 输入:
id:众包id
* 输出:
<pre>
{
  "code": 200,
  "data": null
}
</pre>
#### 众包产品的新增
* URL:http://localhost:7000/api/crowdSourcing/newProduct?productType_id=1
* Method:POST
* 输入:
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
    "deliverLimit":10,
    "unit":"斤" 
}
</pre>
* 输出:
 <pre>
{
  "code": 200,
  "data": {
    "id": 22,
    "productType": {
      "id": 1,
      "name": "红茶",
      "descript": "红茶属全发酵茶，是以适宜的茶树新牙叶为原料，经萎凋、揉捻（切）、发酵、干燥等一系列工艺过程精制而成的茶。萎凋是红茶初制的重要工艺，红茶在初制时称为“乌茶”。红茶因其干茶冲泡后的茶汤和叶底色呈红色而得名。",
      "url": "11.png",
      "state": 0,
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
    "postage": 5,
    "deliverLimit": 10,
    "createDate": null,
    "unit": "斤",
    "teaSaler": null,
    "state": 0,
    "alive": 1,
    "url": "11.png",
    "type": 0
  }
}
</pre>
#### 获得众包的参与者(茶农)
  * URL:/api/crowdSourcing/participant/{crowdSourcingId}
  * Method: GET
  * 输入：
  * 输出
  ```
  {
    "code": 200,
    "data": [
      {
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
        "address": "上海 虹口区 虹口足球场",
        "tel": "15821527768",
        "headUrl": "h1.png",
        "money": 1000,
        "licenseUrl": "c1.png",
        "zip": "200000",
        "idCard": "420281199311118111",
        "state": 1,
        "alive": 1,
        "createDate": "2016-11-11"
      },
      {
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
        "address": "上海 虹口区 虹口足球场",
        "tel": "15821527768",
        "headUrl": "h1.png",
        "money": 1000,
        "licenseUrl": "c1.png",
        "zip": "200000",
        "idCard": "420281199311118111",
        "state": 1,
        "alive": 1,
        "createDate": "2016-11-11"
      }
    ]
  }
  ```