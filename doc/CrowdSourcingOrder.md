####参与众包订单的生成
* URL:http://localhost:7000/api/crowdSourcingOder/new
* Method:POST
* 输入：跟参与众筹类似逻辑类似
 <pre>
{
    "teaSalerId":1,
    "customerId":2,
    "name":"李神",
    "address":"东川路软件学院",
    "zip":"435100",
    "tel":"15821527766",
    "crowdSourcingId":1,
    "num":"10"
}
</pre>
* 输出：
 <pre>
{
  "code": 200,
  "data": {
    "id": 1,
    "createDate": "2016-12-24",
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
      "id": 2,
      "level": 0,
      "nickname": "金初阳",
      "account": {
        "id": 3,
        "tel": "15721326860",
        "password": "123456",
        "label": 2,
        "alive": 1,
        "headURL": "h3.png",
        "money": 900
      },
      "address": "浙江 宁波 江东区 主任家",
      "zip": "1231324",
      "tel": "15721326860",
      "money": 1020,
      "headUrl": "h3.png",
      "alive": 1,
      "createDate": "2016-11-19"
    },
    "crowdSourcing": {
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
      "remainderNum": 990,
      "joinNum": 1
    },
    "name": "李神",
    "address": "东川路软件学院",
    "zip": "435100",
    "tel": "15821527766",
    "totalPrice": 100,
    "logistic": 0,
    "num": 10,
    "state": 2,
    "isSend": 0,
    "SendDate": null,
    "isConfirm": 0,
    "confirmDate": null,
    "score": 0,
    "customerDelete": 0,
    "adminDelete": 0,
    "salerDelete": 0,
    "alive": 1,
    "refund_state": 1,
    "sendDate": null
  }
} 
</pre>
#### 更新订单 确认发货与确认收货
* URL:http://localhost:7000/api/crowdSourcingOder/update
* Method:PUT
* 输入：
 <pre>
{
    "orderId":1,  众包订单的id
    "isConfirm":1,  确认收货填1 ，否则填不是1的数
    "isSend":0  确认发货填1 ，否则填不是1的数
}
</pre>
* 输出：
 <pre>
{
  "code": 200,
  "data": {
    "id": 1,
    "createDate": "2016-12-24",
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
        "money": 2735
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
      "id": 2,
      "level": 0,
      "nickname": "金初阳",
      "account": {
        "id": 3,
        "tel": "15721326860",
        "password": "123456",
        "label": 2,
        "alive": 1,
        "headURL": "h3.png",
        "money": 800
      },
      "address": "浙江 宁波 江东区 主任家",
      "zip": "1231324",
      "tel": "15721326860",
      "money": 1020,
      "headUrl": "h3.png",
      "alive": 1,
      "createDate": "2016-11-19"
    },
    "crowdSourcing": {
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
            "money": 2735
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
      "remainderNum": 970,
      "joinNum": 3
    },
    "name": "李神",
    "address": "东川路软件学院",
    "zip": "435100",
    "tel": "15821527766",
    "totalPrice": 100,
    "logistic": 0,
    "num": 10,
    "state": 2,
    "isSend": 0,
    "SendDate": null,
    "isConfirm": 1,
    "confirmDate": "2016-12-24",
    "score": 0,
    "customerDelete": 0,
    "adminDelete": 0,
    "salerDelete": 0,
    "alive": 1,
    "refund_state": 1,
    "sendDate": null
  }
}
</pre>
#### 取消订单
* URL:http://localhost:7000/api/crowdSourcingOder/cancel
* Method:PUT
* 输入：
 <pre>
{
    "id":1
}
</pre>
* 输出
 <pre>
{
  "code": 200,
  "data": {
    "id": 1,
    "createDate": "2016-12-24",
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
        "money": 2735
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
      "id": 2,
      "level": 0,
      "nickname": "金初阳",
      "account": {
        "id": 3,
        "tel": "15721326860",
        "password": "123456",
        "label": 2,
        "alive": 1,
        "headURL": "h3.png",
        "money": 800
      },
      "address": "浙江 宁波 江东区 主任家",
      "zip": "1231324",
      "tel": "15721326860",
      "money": 1020,
      "headUrl": "h3.png",
      "alive": 1,
      "createDate": "2016-11-19"
    },
    "crowdSourcing": {
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
            "money": 2735
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
      "remainderNum": 970,
      "joinNum": 3
    },
    "name": "李神",
    "address": "东川路软件学院",
    "zip": "435100",
    "tel": "15821527766",
    "totalPrice": 100,
    "logistic": 0,
    "num": 10,
    "state": 3,
    "isSend": 0,
    "SendDate": null,
    "isConfirm": 1,
    "confirmDate": "2016-12-24",
    "score": 0,
    "customerDelete": 0,
    "adminDelete": 0,
    "salerDelete": 0,
    "alive": 1,
    "refund_state": 1,
    "sendDate": null
  }
}
</pre>
#### 众包订单的条件查询
* URL:http://localhost:7000/api/crowdSourcingOder/search
* Method:GET
* 输入： 不需要的条件不用传，如果传了就数字写－1，字符串写“”
  customerId:消费者id
  teaSalerId:茶农id
  crowdSourcingId:众包id
  teaSalerName:茶农名字,当不传茶农id时使用该参数
  state:订单状态 0 未完成, 1已付款,2已完成
  isSend:是否发货 0 否, 1是
  isConfirm:是否确认收货 0 否, 1是
  isComment:是否评论 0 否, 1是
  Refund_state:(未支付，全支付，部分支付)(0, 1, 2)
  beginDateStr=2016-10-10 订单创建起始时间
  endDateStr=2016-12-10 订单创建结束时间
  name: 收件人姓名
  address:地址
  tel:电话
  pageIndex， pageSize，sortField，sortOrder：4个分页参数
* 输出：
<pre>
{
  "code": 200,
  "data": {
    "content": [
      {
        "id": 1,
        "createDate": "2016-12-24",
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
            "money": 2735
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
          "id": 2,
          "level": 0,
          "nickname": "金初阳",
          "account": {
            "id": 3,
            "tel": "15721326860",
            "password": "123456",
            "label": 2,
            "alive": 1,
            "headURL": "h3.png",
            "money": 800
          },
          "address": "浙江 宁波 江东区 主任家",
          "zip": "1231324",
          "tel": "15721326860",
          "money": 1020,
          "headUrl": "h3.png",
          "alive": 1,
          "createDate": "2016-11-19"
        },
        "crowdSourcing": {
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
                "money": 2735
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
          "remainderNum": 970,
          "joinNum": 3
        },
        "name": "李神",
        "address": "东川路软件学院",
        "zip": "435100",
        "tel": "15821527766",
        "totalPrice": 100,
        "logistic": 0,
        "num": 10,
        "state": 3,
        "isSend": 0,
        "SendDate": null,
        "isConfirm": 1,
        "confirmDate": "2016-12-24",
        "score": 0,
        "customerDelete": 0,
        "adminDelete": 0,
        "salerDelete": 0,
        "alive": 1,
        "refund_state": 1,
        "sendDate": null
      },
      {
        "id": 2,
        "createDate": "2016-12-24",
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
            "money": 2735
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
          "id": 2,
          "level": 0,
          "nickname": "金初阳",
          "account": {
            "id": 3,
            "tel": "15721326860",
            "password": "123456",
            "label": 2,
            "alive": 1,
            "headURL": "h3.png",
            "money": 800
          },
          "address": "浙江 宁波 江东区 主任家",
          "zip": "1231324",
          "tel": "15721326860",
          "money": 1020,
          "headUrl": "h3.png",
          "alive": 1,
          "createDate": "2016-11-19"
        },
        "crowdSourcing": {
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
                "money": 2735
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
          "remainderNum": 970,
          "joinNum": 3
        },
        "name": "李神",
        "address": "东川路软件学院",
        "zip": "435100",
        "tel": "15821527766",
        "totalPrice": 100,
        "logistic": 0,
        "num": 10,
        "state": 2,
        "isSend": 0,
        "SendDate": null,
        "isConfirm": 0,
        "confirmDate": null,
        "score": 0,
        "customerDelete": 0,
        "adminDelete": 0,
        "salerDelete": 0,
        "alive": 1,
        "refund_state": 1,
        "sendDate": null
      }
    ],
    "totalElements": 2,
    "totalPages": 1,
    "last": true,
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
    "numberOfElements": 2
  }
}
</pre>
### 众筹支付未完成的
* URL /api/crowdFundOrder/payUnFinished
* Method: PUT
* 参数:
```
{
    "id":1
}
```