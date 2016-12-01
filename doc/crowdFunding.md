#### 发起众筹
* URL:http://localhost:7000/api/crowdFund/new?product_id=7
* Method: POST
* 输入：众筹分成现货和预售，两者中传入的参数不同
 RequestBody中

```
{
    "type":0,  现货0，预售1
    "earnest":1, 现货的话，不用定金
    "unitNum":1,  每份数量
    "unitMoney":10, 每份金额
    "dealDate":"",众筹结束时间
    "deliverDate":"", 开始发货的时间
    "payDate":"",  交付剩余金钱的时间（如果是现货，则不需要）
    "totalNum":100  商品总量
}
```



* 输入：  
 <pre>
{
  "code": 200,
  "data": {
    "id": 1,
    "product": {
      "id": 7,
      "productType": {
        "id": 1,
        "name": "红茶",
        "descript": "红茶属全发酵茶，是以适宜的茶树新牙叶为原料，经萎凋、揉捻（切）、发酵、干燥等一系列工艺过程精制而成的茶。萎凋是红茶初制的重要工艺，红茶在初制时称为“乌茶”。红茶因其干茶冲泡后的茶汤和叶底色呈红色而得名。",
        "url": "11.png",
        "state": 0,
        "alive": 1
      },
      "remark": "红茶富含胡萝卜素、维生素A、钙、磷、镁、钾、咖啡碱、异亮氨酸、亮氨酸、赖氨酸、谷氨酸、丙氨酸、天门冬氨酸等多种营养元素。红茶在发酵过程中多酚类物质的化学反应使鲜叶中的化学成分变化较大，会产生茶黄素、茶红素等成分，其香气比鲜叶明显增加，形成红茶特有的色、香、味。",
      "name": "红茶",
      "level": 1,
      "locality": "浙江 湖州 长兴县",
      "stock": 9996,
      "price": 50,
      "startNum": 1,
      "discount": 0.9,
      "isFree": 0,
      "postage": 5,
      "deliverLimit": 5,
      "createDate": "2016-11-30",
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
          "money": 2460
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
      "url": "12.png",
      "type": 1
    },
    "type": 0,
    "earnest": 1,
    "unitNum": 1,
    "unitMoney": 10,
    "createDate": null,
    "dealDate": null,
    "deliverDate": null,
    "payDate": null,
    "state": 0,
    "alive": 0,
    "totalNum": 100,
    "remainderNum": 100,
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
    "type": 0,
    "earnest": 1,
    "unitNum": 1,
    "unitMoney": 10,
    "dealDate": "",
    "deliverDate":"",
    "payDate":"",
    "joinNum":5,
    "totalNum": 1000
}
 </pre>
* 输出：
 <pre>
{
  "code": 200,
  "data": {
    "id": 1,
    "product": {
      "id": 7,
      "productType": {
        "id": 1,
        "name": "红茶",
        "descript": "红茶属全发酵茶，是以适宜的茶树新牙叶为原料，经萎凋、揉捻（切）、发酵、干燥等一系列工艺过程精制而成的茶。萎凋是红茶初制的重要工艺，红茶在初制时称为“乌茶”。红茶因其干茶冲泡后的茶汤和叶底色呈红色而得名。",
        "url": "11.png",
        "state": 0,
        "alive": 1
      },
      "remark": "红茶富含胡萝卜素、维生素A、钙、磷、镁、钾、咖啡碱、异亮氨酸、亮氨酸、赖氨酸、谷氨酸、丙氨酸、天门冬氨酸等多种营养元素。红茶在发酵过程中多酚类物质的化学反应使鲜叶中的化学成分变化较大，会产生茶黄素、茶红素等成分，其香气比鲜叶明显增加，形成红茶特有的色、香、味。",
      "name": "红茶",
      "level": 1,
      "locality": "浙江 湖州 长兴县",
      "stock": 9996,
      "price": 50,
      "startNum": 1,
      "discount": 0.9,
      "isFree": 0,
      "postage": 5,
      "deliverLimit": 5,
      "createDate": "2016-11-30",
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
          "money": 2460
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
      "url": "12.png",
      "type": 1
    },
    "type": 0,
    "earnest": 1,
    "unitNum": 1,
    "unitMoney": 10,
    "createDate": "2016-11-30",
    "dealDate": null,
    "deliverDate": null,
    "payDate": "2016-12-03",
    "state": 0,
    "alive": 1,
    "totalNum": 1000,
    "remainderNum": 1000,
    "joinNum": 5
  }
}
</pre>
#### 众筹删除
* URL:http://localhost:7000/api/crowdFund/delete
* Method:PUT
* 输入：
	<pre>[
{
    "id":1  众筹的id
}
]</pre>
* 输出：
 <pre>
{
  "code": 200,
  "data": "all succeed!"
} 
</pre>
#### 众筹的查询
* URL:http://localhost:7000/api/crowdFund/search?product_id=7&teaSaler_id=1&type=0&lowEarnest=1&highEarnest=100&lowUnitNum=1&highUnitNum=100&lowUnitMoney=1&highUnitMoney=1000&state=0&lowRemainderNum=1&highRemainderNum=1000&productType_id=1&productType_name=“ haha”&product_name="haha"
* Method: GET
* 输入：url中的参数可以都不填，如果填写的话，不需要该查询条件时，数字写－1，字符串写“”
* 输出：
 <pre>
{
  "code": 200,
  "data": {
    "content": [
      {
        "id": 1,
        "product": {
          "id": 7,
          "productType": {
            "id": 1,
            "name": "红茶",
            "descript": "红茶属全发酵茶，是以适宜的茶树新牙叶为原料，经萎凋、揉捻（切）、发酵、干燥等一系列工艺过程精制而成的茶。萎凋是红茶初制的重要工艺，红茶在初制时称为“乌茶”。红茶因其干茶冲泡后的茶汤和叶底色呈红色而得名。",
            "url": "11.png",
            "state": 0,
            "alive": 1
          },
          "remark": "红茶富含胡萝卜素、维生素A、钙、磷、镁、钾、咖啡碱、异亮氨酸、亮氨酸、赖氨酸、谷氨酸、丙氨酸、天门冬氨酸等多种营养元素。红茶在发酵过程中多酚类物质的化学反应使鲜叶中的化学成分变化较大，会产生茶黄素、茶红素等成分，其香气比鲜叶明显增加，形成红茶特有的色、香、味。",
          "name": "红茶",
          "level": 1,
          "locality": "浙江 湖州 长兴县",
          "stock": 9996,
          "price": 50,
          "startNum": 1,
          "discount": 0.9,
          "isFree": 0,
          "postage": 5,
          "deliverLimit": 5,
          "createDate": "2016-11-30",
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
              "money": 2460
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
          "url": "12.png",
          "type": 1
        },
        "type": 0,
        "earnest": 1,
        "unitNum": 1,
        "unitMoney": 10,
        "createDate": "2016-11-30 09:12:31",
        "dealDate": "2016-12-01 10:56:55",
        "deliverDate": "2016-11-30 09:38:02",
        "payDate": "2016-12-03 09:14:15",
        "state": 0,
        "alive": 1,
        "totalNum": 1000,
        "remainderNum": 1000,
        "joinNum": 5
      }
    ],
    "last": true,
    "totalElements": 1,
    "totalPages": 1,
    "first": true,
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
    "size": 10,
    "number": 0
  }
}
</pre>