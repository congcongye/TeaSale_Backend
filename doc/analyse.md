### 查询茶农某个时间段的所有产品的销售情况
* URL:http://localhost:7000/api/statistics/teasalerProduct?teaSaler_id=1&startDate=2016-07-10&endDate=2017-02-14
* Method:GET
* 输入：茶农ID，订单创建的最早时间，订单最晚开始时间
* 输出：
<pre>
{
  "code": 200,
  "data": {
    "4": {
      "productName": "龙井",
      "number": 184
    },
    "5": {
      "productName": "龙井",
      "number": 106
    },
    "7": {
      "productName": "红茶",
      "number": 3
    },
    "8": {
      "productName": "绿茶",
      "number": 12
    },
    "9": {
      "productName": "普洱茶",
      "number": 52
    },
    "10": {
      "productName": "乌龙茶",
      "number": 39
    },
    "18": {
      "productName": "乌龙茶",
      "number": 29
    }
  }
}
</pre>
#### 统计某个茶产品类型某个时间段中的各个地区的销售情况
* URL:http://localhost:7000/api/statistics/addressSearch?productType_id=4&startDate=2016-07-10&endDate=2017-02-14
* Method: GET
* 输入：productType_id 产品类型id  startDate 最早创建时间  endDate 最晚创建时间
* 输出：
<pre>
{
  "code": 200,
  "data": {
    "山东": {
      "productName": "乌龙茶",
      "number": 7
    },
    "上海": {
      "productName": "乌龙茶",
      "number": 15
    },
    "吉林": {
      "productName": "乌龙茶",
      "number": 1
    },
    "河南": {
      "productName": "乌龙茶",
      "number": 10
    },
    "浙江": {
      "productName": "乌龙茶",
      "number": 8
    },
    "江苏": {
      "productName": "乌龙茶",
      "number": 30
    }
  }
}
</pre>
### 统计各个省各种产品类型所卖的数量
* URL:http://localhost:7000/api/statistics/searchAllProducts?startDate=2016-07-10&endDate=2017-02-14
* Method: GET
* 输入：startDate 最早订单创建时间  endDate 最晚订单创建时间
* 输出：
<pre>
{
  "code": 200,
  "data": {
    "山东": {
      "2": {  //2是指产品类型的id，不用管
        "productName": "绿茶", 产品类型的名称
        "number": 10
      },
      "3": {
        "productName": "普洱茶",
        "number": 23
      },
      "4": {
        "productName": "乌龙茶",
        "number": 7
      },
      "5": {
        "productName": "玫瑰花茶",
        "number": 1
      }
    },
    "上海": {
      "2": {
        "productName": "绿茶",
        "number": 265
      },
      "3": {
        "productName": "普洱茶",
        "number": 14
      },
      "4": {
        "productName": "乌龙茶",
        "number": 15
      },
      "5": {
        "productName": "玫瑰花茶",
        "number": 18
      }
    },
    "吉林": {
      "2": {
        "productName": "绿茶",
        "number": 8
      },
      "3": {
        "productName": "普洱茶",
        "number": 20
      },
      "4": {
        "productName": "乌龙茶",
        "number": 1
      },
      "5": {
        "productName": "玫瑰花茶",
        "number": 3
      }
    },
    "河南": {
      "3": {
        "productName": "普洱茶",
        "number": 40
      },
      "4": {
        "productName": "乌龙茶",
        "number": 10
      },
      "5": {
        "productName": "玫瑰花茶",
        "number": 5
      }
    },
    "浙江": {
      "2": {
        "productName": "绿茶",
        "number": 7
      },
      "3": {
        "productName": "普洱茶",
        "number": 79
      },
      "4": {
        "productName": "乌龙茶",
        "number": 8
      }
    },
    "江苏": {
      "3": {
        "productName": "普洱茶",
        "number": 10
      },
      "4": {
        "productName": "乌龙茶",
        "number": 30
      },
      "5": {
        "productName": "玫瑰花茶",
        "number": 20
      }
    }
  }
}
</pre>
#### 统计所有茶农所有产品类型的销售数量
* URL:http://localhost:7000/api/statistics/allproductTypes?startDate=2016-07-10&endDate=2017-02-14
* Method:GET
* 输入：startDate 最早订单创建时间  endDate 最晚订单创建时间
* 输出：
 <pre>
{
  "code": 200,
  "data": {
    "1": {
      "teaSalerName": "叶聪聪",
      "allproductTypes": {
        "3": {
          "productName": "普洱茶",
          "number": 5
        }
      }
    }
  }
}
</pre>
#### 统计某个茶农的所有产品类型的销量
* URL:http://localhost:7000/api/statistics/teaSalerAllProductTypes?startDate=2016-07-10&endDate=2017-02-14&teaSaler_id=1
* Method:GET
* 输入：茶农id，stateDate最早时间  endDate 最晚时间
* 输出：
 <pre>
 {
  "code": 200,
  "data": {
    "2": {
      "productName": "绿茶",
      "number": 1
    },
    "3": {
      "productName": "普洱茶",
      "number": 5
    }
  }
}
 </pre>