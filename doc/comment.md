### 新增评论
* URL：http://localhost:7000/api/comment/new
* Method：POST
* 输入：
 <pre>
{
    "orderItem_id":1,
    "customer_id":1,
    "content":"这茶很好喝，价格实惠",
    "score":90
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
      "createDate": "2016-11-22",
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
          "money": 1923
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
      "url": "12.png"
    },
    "orderItem": {
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
        "createDate": "2016-11-22",
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
            "money": 1923
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
        "url": "12.png"
      },
      "orderen": {
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
            "money": 1923
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
            "money": 2223
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
      "num": 4,
      "totalPrice": 48,
      "alive": 1,
      "isComment": 1
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
        "money": 2223
      },
      "address": "上海 虹口区 虹口足球场",
      "zip": "446543",
      "tel": "13918966539",
      "money": 1000,
      "headUrl": "h4.png",
      "alive": 1,
      "createDate": "2016-11-11"
    },
    "content": "这茶很好喝，价格实惠",
    "createDate": 1479875364307,
    "score": 90,
    "alive": 1
  }
}
</pre>
### 评论删除
* URL:http://localhost:7000/api/comment/delete
* Method:PUT
* 输入：
 <pre>
[
{
 "id":1 
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
### 评论的条件查询
* URL:http://localhost:7000/api/comment/search?customer_id=1
* Method:GET
* 输入：customer_id 消费者id，content：评论内容，lowScore：最低分数，highScore：最高的分数，startDate：最早评论时间，endDate：最晚评论时间，orderItem_id：订单项id，teaSaler_id：茶农id，product_id：产品id，pageIndex：分页起始指，pageSize：分页大小，sortField：排序字段，sortOrder：升序还是降序
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
          "createDate": "2016-11-22",
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
              "money": 1923
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
          "url": "12.png"
        },
        "orderItem": {
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
            "createDate": "2016-11-22",
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
                "money": 1923
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
            "url": "12.png"
          },
          "orderen": {
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
                "money": 1923
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
                "money": 2223
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
          "num": 4,
          "totalPrice": 48,
          "alive": 1,
          "isComment": 1
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
            "money": 2223
          },
          "address": "上海 虹口区 虹口足球场",
          "zip": "446543",
          "tel": "13918966539",
          "money": 1000,
          "headUrl": "h4.png",
          "alive": 1,
          "createDate": "2016-11-11"
        },
        "content": "这茶很好喝，价格实惠",
        "createDate": 1479875364000,
        "score": 90,
        "alive": 1
      }
    ],
    "totalElements": 1,
    "last": true,
    "totalPages": 1,
    "size": 10,
    "number": 0,
    "sort": [
      {
        "direction": "ASC",
        "property": "score",
        "ignoreCase": false,
        "nullHandling": "NATIVE",
        "ascending": true
      }
    ],
    "first": true,
    "numberOfElements": 1
  }
}
</pre>
