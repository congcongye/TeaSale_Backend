#### 众筹订单的生成
* URL:/crowdFundOrder/new
* Method: POST
* 输入：
 <pre>
{
        "teaSalerId":1,
        "customerId":1,
        "name":"test",
        "address":"dongchuanlu",
        "zip":"213000",
        "tel":"12345678908",
        "crowdFundingId":1,
        "num":10
    }
</pre>    
* 输出:    
<pre> 
 {
   "code": 200,
   "data": {
     "id": 1,
     "createDate": "2016-12-07",
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
         "money": 9999700
       },
       "address": "闵行东川路",
       "zip": "12344567",
       "tel": "13918966539",
       "money": 0,
       "headUrl": null,
       "alive": 1,
       "createDate": "2016-11-11"
     },
     "crowdFunding": {
       "id": 1,
       "product": {
         "id": 4,
         "productType": {
           "id": 1,
           "name": "绿茶",
           "descript": "绿茶的描述信息",
           "url": "图片地址url",
           "state": 1,
           "alive": 1
         },
         "remark": "",
         "name": "",
         "level": 1,
         "locality": "",
         "stock": 1000,
         "price": 90,
         "startNum": 1,
         "discount": 0.8,
         "isFree": 1,
         "postage": 0,
         "deliverLimit": 10,
         "createDate": "2016-12-05",
         "unit": "liang",
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
         "state": 1,
         "alive": 1,
         "url": "图片地址url",
         "type": 1
       },
       "type": 1,
       "earnest": 10,
       "unitNum": 1,
       "unitMoney": 20,
       "createDate": "2016-12-06 15:22:51",
       "dealDate": "2016-12-17 15:22:44",
       "deliverDate": null,
       "payDate": null,
       "state": 1,
       "alive": 1,
       "totalNum": 10,
       "remainderNum": 0,
       "joinNum": 2
     },
     "name": "test",
     "address": "dongchuanlu",
     "zip": "213000",
     "tel": "12345678908",
     "totalPrice": 200,
     "logistic": 0,
     "num": 0,
     "state": 2,
     "isSend": 0,
     "SendDate": null,
     "isConfirm": 0,
     "confirmDate": null,
     "score": -1,
     "customerDelete": 0,
     "adminDelete": 0,
     "salerDelete": 0,
     "alive": 1,
     "sendDate": null,
     "refund_state": 0
   }
 }
 </pre>
 

 #### 众筹订单搜索
 * URL:/api//crowdFundOrders/search?customerId&teaSalerId&crowdFundingId&teaSalerName&state&isSend&isConfirm&customerDelete&adminDelete&salerDelete&Refund_state&name&address&beginDateStr&endDateStr&pageIndex&pageSize&sortField&sortOrder
 * Method: GET
 * 参数：
 <pre>
  teaSalerName:茶农名字,当不传茶农id时使用该参数
  state:订单状态 0 未完成, 1已付款,2已完成
  isSend:是否发货 0 否, 1是
  isConfirm:是否确认收货 0 否, 1是
  isComment:是否评论 0 否, 1是
  Refund_state:(未支付，全支付，部分支付)(0, 1, 2)
  beginDateStr=2016-10-10 订单创建起始时间
  endDateStr=2016-12-10 订单创建结束时间
 </pre>    
 * 输出:    
 <pre> 
  {
    "code": 200,
    "data": {
      "content": [
        {
          "id": 1,
          "createDate": "2016-12-07",
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
              "money": 953
            },
            "address": "上海 虹口区 虹口足球场",
            "zip": "446543",
            "tel": "13918966539",
            "money": 1000,
            "headUrl": "h4.png",
            "alive": 1,
            "createDate": "2016-11-11"
          },
          "crowdFunding": {
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
          },
          "name": "孙晏",
          "address": "上海 虹口区 虹口足球场",
          "zip": "446543",
          "tel": "13918966539",
          "totalPrice": 1000,
          "logistic": 0,
          "num": 0,
          "state": 2,
          "isSend": 0,
          "SendDate": null,
          "isConfirm": 0,
          "confirmDate": null,
          "score": -1,
          "customerDelete": 0,
          "adminDelete": 0,
          "salerDelete": 0,
          "alive": 1,
          "sendDate": null,
          "refund_state": 0
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
          "property": "id",
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
  
  
  #### 众筹订单支付全款
   * URL:/api/crowdFundOrder/payRemain
   * Method: PUT
   * 参数：
    <pre>
    {
        "id":9
    }
   </pre>    
   * 输出:    
   <pre> 
    {
      "code": 200,
      "data": {
        "id": 9,
        "createDate": "2016-12-08",
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
            "money": 3933
          },
          "address": "上海 虹口区 虹口足球场",
          "zip": "446543",
          "tel": "13918966539",
          "money": 1000,
          "headUrl": "h4.png",
          "alive": 1,
          "createDate": "2016-11-11"
        },
        "crowdFunding": {
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
                "money": 2535
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
          "state": 0,
          "alive": 1,
          "totalNum": 200,
          "remainderNum": 199,
          "joinNum": 1
        },
        "name": "test",
        "address": "dongchuanlu",
        "zip": "213000",
        "tel": "12345678908",
        "totalPrice": 100,
        "logistic": 0,
        "num": 1,
        "state": 1,
        "isSend": 0,
        "SendDate": null,
        "isConfirm": 0,
        "confirmDate": null,
        "score": -1,
        "customerDelete": 0,
        "adminDelete": 0,
        "salerDelete": 0,
        "alive": 1,
        "refund_state": 2,
        "sendDate": null
      }
    }
  </pre>

 #### 众筹订单更新(确认发货)
   * URL:/api/crowdFundOrder/update
   * Method: PUT
   * 参数：
   <pre>
    {
        "orderId":9,
        "isConfirm":-1,
        "isSend":1
    }
   </pre>    
   * 输出:    
   <pre> 
   {
     "code": 200,
     "data": {
       "id": 9,
       "createDate": "2016-12-08",
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
           "money": 3913
         },
         "address": "上海 虹口区 虹口足球场",
         "zip": "446543",
         "tel": "13918966539",
         "money": 1000,
         "headUrl": "h4.png",
         "alive": 1,
         "createDate": "2016-11-11"
       },
       "crowdFunding": {
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
               "money": 2535
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
         "state": 0,
         "alive": 1,
         "totalNum": 200,
         "remainderNum": 199,
         "joinNum": 1
       },
       "name": "test",
       "address": "dongchuanlu",
       "zip": "213000",
       "tel": "12345678908",
       "totalPrice": 100,
       "logistic": 0,
       "num": 1,
       "state": 1,
       "isSend": 1,
       "SendDate": "2016-12-08",
       "isConfirm": 0,
       "confirmDate": null,
       "score": -1,
       "customerDelete": 0,
       "adminDelete": 0,
       "salerDelete": 0,
       "alive": 1,
       "refund_state": 2,
       "sendDate": 1481177859129
     }
   }
   </pre>
   
   #### 众筹订单更新(确认收货)
  * URL:/api/crowdFundOrder/update
  * Method: PUT
  * 参数：
 <pre>
  {
      "orderId":9,
      "isConfirm":1,
      "isSend":-1
  }
 </pre>    
 * 输出:    
 <pre> 
   {
     "code": 200,
     "data": {
       "id": 9,
       "createDate": "2016-12-08",
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
           "money": 3913
         },
         "address": "上海 虹口区 虹口足球场",
         "zip": "446543",
         "tel": "13918966539",
         "money": 1000,
         "headUrl": "h4.png",
         "alive": 1,
         "createDate": "2016-11-11"
       },
       "crowdFunding": {
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
         "state": 0,
         "alive": 1,
         "totalNum": 200,
         "remainderNum": 199,
         "joinNum": 1
       },
       "name": "test",
       "address": "dongchuanlu",
       "zip": "213000",
       "tel": "12345678908",
       "totalPrice": 100,
       "logistic": 0,
       "num": 1,
       "state": 1,
       "isSend": 1,
       "SendDate": "2016-12-08",
       "isConfirm": 1,
       "confirmDate": "2016-12-08",
       "score": -1,
       "customerDelete": 0,
       "adminDelete": 0,
       "salerDelete": 0,
       "alive": 1,
       "refund_state": 2,
       "sendDate": 1481177859000
     }
   }
 </pre>
   
   #### 众筹订单取消
   * URL:/api/crowdFundOrder/cancel
   * Method: PUT
   * 参数：
  <pre>
   {
       "id":2
   }
  </pre>    
  * 输出:    
  <pre>
  {
    "code": 200,
    "data": {
      "id": 2,
      "createDate": "2016-12-07",
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
      "crowdFunding": {
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
      "name": "孙晏",
      "address": "上海 虹口区 虹口足球场",
      "zip": "446543",
      "tel": "13918966539",
      "totalPrice": 0,
      "logistic": 0,
      "num": 10,
      "state": 3,
      "isSend": 0,
      "SendDate": null,
      "isConfirm": 0,
      "confirmDate": null,
      "score": -1,
      "customerDelete": 0,
      "adminDelete": 0,
      "salerDelete": 0,
      "alive": 1,
      "refund_state": 0,
      "sendDate": null
    }
  }
</pre>