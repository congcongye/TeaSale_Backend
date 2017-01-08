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
* 
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
* 
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
* 
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
 
 #### 确认众筹形成,等待发货(改变状态state->4)
 * URL:/api/crowdFund/confirm
 * Method: PUT
 * 输入：
  <pre>
 {
         "id":4
     }
 </pre>    
 * 输出:    
 <pre> 
  {
    "code": 200,
    "data": {
      "id": 4,
      "product": {
        "id": 20,
        "productType": {
          "id": 2,
          "name": "绿茶",
          "descript": "绿茶是中国的主要茶类之一，是指采取茶树的新叶或芽，未经发酵，经杀青、整形、烘干等工艺而制作的饮品。其制成品的色泽和冲泡后的茶汤较多的保存了鲜茶叶的绿色格调。常饮绿茶能防癌，降脂和减肥，对吸烟者也可减轻其受到的尼古丁伤害。",
          "url": "21.png",
          "state": 1,
          "alive": 1
        },
        "remark": "传统名茶龙井茶",
        "name": "西湖龙井",
        "level": 3,
        "locality": "浙江 杭州 萧山区",
        "stock": 6700,
        "price": 34,
        "startNum": 20,
        "discount": 0.6,
        "isFree": 1,
        "postage": 0,
        "deliverLimit": 5,
        "createDate": "2016-12-07",
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
            "money": 2535
          },
          "address": "上海市 闵行区",
          "tel": "15821527768",
          "headUrl": "h1.png",
          "money": 1000,
          "licenseUrl": "c1.png",
          "zip": "435100",
          "idCard": "420281199311118111",
          "state": 4,
          "alive": 1,
          "createDate": "2016-11-11"
        },
        "state": 1,
        "alive": 1,
        "url": "21.png",
        "type": 1
      },
      "type": 0,
      "earnest": 0,
      "unitNum": 20,
      "unitMoney": 300,
      "createDate": "2016-12-07 11:05:14",
      "dealDate": "2017-03-21 00:00:00",
      "deliverDate": "2017-04-01 00:00:00",
      "payDate": null,
      "state": 1,
      "alive": 1,
      "totalNum": 200,
      "remainderNum": 200,
      "joinNum": 0
    }
  }
  </pre>
  
  ### 根据销量推荐众筹
  * URL /api/crowdFunds/commend/rankBySalesVolume
  * Method:GET 
  * 参数: 无
  
  * 返回:
  ```
  {
    "code": 200,
    "data": [
      {
        "id": 4,
        "product": {
          "id": 20,
          "productType": {
            "id": 2,
            "name": "绿茶",
            "descript": "绿茶是中国的主要茶类之一，是指采取茶树的新叶或芽，未经发酵，经杀青、整形、烘干等工艺而制作的饮品。其制成品的色泽和冲泡后的茶汤较多的保存了鲜茶叶的绿色格调。常饮绿茶能防癌，降脂和减肥，对吸烟者也可减轻其受到的尼古丁伤害。",
            "url": "21.png",
            "state": 1,
            "alive": 1
          },
          "remark": "传统名茶龙井茶",
          "name": "西湖龙井",
          "level": 3,
          "locality": "浙江 杭州 萧山区",
          "stock": 6700,
          "price": 34,
          "startNum": 20,
          "discount": 0.6,
          "isFree": 1,
          "postage": 0,
          "deliverLimit": 5,
          "createDate": "2016-12-07",
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
          "type": 1
        },
        "type": 0,
        "earnest": 0,
        "unitNum": 20,
        "unitMoney": 300,
        "createDate": "2016-12-07 11:05:14",
        "dealDate": "2017-03-21 00:00:00",
        "deliverDate": "2017-04-01 00:00:00",
        "payDate": null,
        "state": 1,
        "alive": 1,
        "totalNum": 200,
        "remainderNum": 200,
        "joinNum": 0
      },
      {
        "id": 6,
        "product": {
          "id": 19,
          "productType": {
            "id": 4,
            "name": "乌龙茶",
            "descript": "乌龙茶（oolong tea），亦称青茶、半发酵茶及全发酵茶，品种较多，是中国几大茶类中，独具鲜明汉族特色的茶叶品类。乌龙茶是经过采摘、萎凋、摇青、炒青、揉捻、烘焙等工序后制出的品质优异的茶类。乌龙茶由宋代贡茶龙团、凤饼演变而来，创制于1725年（清雍正年间）前后。品尝后齿颊留香，回味甘鲜。乌龙茶的药理作用，突出表现在分解脂肪、减肥健美等方面。在日本被称之为“美容茶”、“ 健美茶”。",
            "url": "41.png",
            "state": 1,
            "alive": 1
          },
          "remark": "纯天然，手工炒制",
          "name": "有机古树乌龙茶",
          "level": 2,
          "locality": "浙江 温州 瓯海区 龙门路",
          "stock": 3400,
          "price": 19,
          "startNum": 19,
          "discount": 0.9,
          "isFree": 1,
          "postage": 0,
          "deliverLimit": 26,
          "createDate": "2016-12-01",
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
          "url": "41.png",
          "type": 1
        },
        "type": 1,
        "earnest": 20,
        "unitNum": 10,
        "unitMoney": 100,
        "createDate": "2016-12-08 12:48:19",
        "dealDate": "2016-12-23 12:48:23",
        "deliverDate": null,
        "payDate": null,
        "state": 2,
        "alive": 1,
        "totalNum": 200,
        "remainderNum": 199,
        "joinNum": 1
      },
      {
        "id": 3,
        "product": {
          "id": 19,
          "productType": {
            "id": 4,
            "name": "乌龙茶",
            "descript": "乌龙茶（oolong tea），亦称青茶、半发酵茶及全发酵茶，品种较多，是中国几大茶类中，独具鲜明汉族特色的茶叶品类。乌龙茶是经过采摘、萎凋、摇青、炒青、揉捻、烘焙等工序后制出的品质优异的茶类。乌龙茶由宋代贡茶龙团、凤饼演变而来，创制于1725年（清雍正年间）前后。品尝后齿颊留香，回味甘鲜。乌龙茶的药理作用，突出表现在分解脂肪、减肥健美等方面。在日本被称之为“美容茶”、“ 健美茶”。",
            "url": "41.png",
            "state": 1,
            "alive": 1
          },
          "remark": "纯天然，手工炒制",
          "name": "有机古树乌龙茶",
          "level": 2,
          "locality": "浙江 温州 瓯海区 龙门路",
          "stock": 3400,
          "price": 19,
          "startNum": 19,
          "discount": 0.9,
          "isFree": 1,
          "postage": 0,
          "deliverLimit": 26,
          "createDate": "2016-12-01",
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
          "url": "41.png",
          "type": 1
        },
        "type": 0,
        "earnest": 0,
        "unitNum": 1,
        "unitMoney": 10,
        "createDate": "2016-12-01 11:34:40",
        "dealDate": "2016-12-01 08:00:00",
        "deliverDate": null,
        "payDate": null,
        "state": 2,
        "alive": 1,
        "totalNum": 2000,
        "remainderNum": 2000,
        "joinNum": 5
      },
      {
        "id": 1,
        "product": {
          "id": 7,
          "productType": {
            "id": 1,
            "name": "红茶",
            "descript": "红茶属全发酵茶，是以适宜的茶树新牙叶为原料，经萎凋、揉捻（切）、发酵、干燥等一系列工艺过程精制而成的茶。萎凋是红茶初制的重要工艺，红茶在初制时称为“乌茶”。红茶因其干茶冲泡后的茶汤和叶底色呈红色而得名。",
            "url": "11.png",
            "state": 1,
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
        "state": 2,
        "alive": 1,
        "totalNum": 1000,
        "remainderNum": 997,
        "joinNum": 1
      },
      {
        "id": 2,
        "product": {
          "id": 8,
          "productType": {
            "id": 2,
            "name": "绿茶",
            "descript": "绿茶是中国的主要茶类之一，是指采取茶树的新叶或芽，未经发酵，经杀青、整形、烘干等工艺而制作的饮品。其制成品的色泽和冲泡后的茶汤较多的保存了鲜茶叶的绿色格调。常饮绿茶能防癌，降脂和减肥，对吸烟者也可减轻其受到的尼古丁伤害。",
            "url": "21.png",
            "state": 1,
            "alive": 1
          },
          "remark": "茗帝龙井，茶叶采自西湖名水滋养的秀丽深山，经由雨露雾岚浸润，叶条纤细紧直，茶汤香气绵长清淡又不失醇厚，历来受到皇家喜爱，专奉宫廷之用。乾隆六下江南，泛舟西湖，听弦音笙歌，观江南胜景，皆以此茶陪伴左右，兴之所至，赋诗“西湖泛舟涟漪散，却把茗帝话江山”是其深爱此茶的最好注解。御茶园茗帝沿袭宫廷制茶秘方，生产加工工艺与口味亦丝毫不变，是一款令茶客心神望之的龙井佳品。",
          "name": "绿茶",
          "level": 1,
          "locality": "浙江 杭州 余杭区",
          "stock": 495,
          "price": 68,
          "startNum": 5,
          "discount": 1,
          "isFree": 0,
          "postage": 7,
          "deliverLimit": 7,
          "createDate": "2016-12-01",
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
          "url": "22.png",
          "type": 1
        },
        "type": 0,
        "earnest": 1,
        "unitNum": 1,
        "unitMoney": 10,
        "createDate": "2016-12-01 10:43:53",
        "dealDate": "2016-12-01 10:43:30",
        "deliverDate": "2016-12-01 10:55:37",
        "payDate": "2016-12-01 10:56:58",
        "state": 2,
        "alive": 1,
        "totalNum": 100,
        "remainderNum": 100,
        "joinNum": 0
      },
      {
        "id": 5,
        "product": {
          "id": 21,
          "productType": {
            "id": 5,
            "name": "玫瑰花茶",
            "descript": "玫瑰花茶为中国再加工茶类中花茶的一种，是由茶叶和玫瑰鲜花窨制而成。玫瑰花茶所采用的茶坯有红茶、绿茶，鲜花除玫瑰外，蔷薇和现代月季也具有甜美、浓郁的花香，也可用来窨制花茶，其中半开放的玫瑰花，品质最佳。成品茶甜香扑鼻、香气浓郁、滋味甘美。玫瑰花茶制作工艺为茶坯与鲜花处理、窨花拼和、起花、复火、提花。",
            "url": "51.png",
            "state": 1,
            "alive": 1
          },
          "remark": "玫瑰花茶养颜",
          "name": "玫瑰花茶",
          "level": 3,
          "locality": "江苏 徐州 云龙区",
          "stock": 1000,
          "price": 50,
          "startNum": 2,
          "discount": 1,
          "isFree": 1,
          "postage": 0,
          "deliverLimit": 2,
          "createDate": "2016-12-07",
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
          "url": "51.png",
          "type": 1
        },
        "type": 1,
        "earnest": 50,
        "unitNum": 20,
        "unitMoney": 200,
        "createDate": "2016-12-07 11:15:50",
        "dealDate": "2017-03-01 00:00:00",
        "deliverDate": "2017-03-15 00:00:00",
        "payDate": "2017-03-20 00:00:00",
        "state": 0,
        "alive": 1,
        "totalNum": 200,
        "remainderNum": 195,
        "joinNum": 1
      }
    ]
  }
  ```
  
  #### 发获得众筹的参与者(消费者)
  * URL:/api/crowdFund/participant/{crowdFundingId}
  * Method: GET
  * 输入：crowdFundingId
  * 输出
  ```
  {
    "code": 200,
    "data": [
      {
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
          "money": 36211.2
        },
        "address": "上海 杨浦区 仁德路",
        "zip": "446543",
        "tel": "13918966539",
        "money": 1000,
        "headUrl": "h4.png",
        "alive": 1,
        "createDate": "2016-11-11"
      },
      {
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
          "money": 36211.2
        },
        "address": "上海 杨浦区 仁德路",
        "zip": "446543",
        "tel": "13918966539",
        "money": 1000,
        "headUrl": "h4.png",
        "alive": 1,
        "createDate": "2016-11-11"
      }
    ]
  }
  ```