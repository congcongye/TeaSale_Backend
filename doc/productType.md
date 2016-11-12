###产品类型的新增
* url: http://localhost:7000/api/productType/new?name=green&descript=great green
* Method:POST
* 参数：name:图片名称,descript:图片描述信息,file在requestboy里,类型MultipartFile
* 返回：
  <pre>{
        "code": 200,
        "data": {
          "id": 2,
          "name": "green",
          "descript": "great green",
          "url": "ff35420abb144047b7ab6fddcf94c8c7.png",
          "state": 1,
          "alive": 1
        }
      }
	</pre>
### 产品类型的修改
* URL:http://localhost:8080/api/productTypes/update
* Method:PUT
* 参数：传入需要修改的产品类型的id和state（产品类型的状态）state＝1，茶农新增时可用，state＝0，茶农新增时不可用
* 注意事项：（修改只能把state改成0，其它的不改变）
	<pre>
	[
        {
        “id”:1,
        "state":1
        }
    ]
	</pre>
* 返回
	<pre>{
 	 "code": 200,
  	 "data": "all succeed"
	}
	</pre>
####2.茶产品类型的查询
* url: http://localhost:8080/api/productTypes/getAllProductType?state=1
* Method:GET
* 参数：在url上
state =1 获得所有可以使用的茶产品
state＝0获得所有不能使用的茶产品
* 返回：
 <pre>
{
	  "code": 200,
	  "data": [
    {
      "id": 1,
      "name": "红茶",
      "descript": "红茶的描述信息",
      "url": "图片地址url",
      "state": 1,
      "alive": 1
    },
    {
      "id": 2,
      "name": "绿茶",
      "descript": "绿茶的描述信息",
      "url": "图片地址url",
      "state": 1,
      "alive": 1
    }
  	]
	}
</pre>
