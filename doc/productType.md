###产品类型的新增
* url: http://localhost:8080/api/productType/new
* Method:POST
* 参数：在body里
* 新增：
   <pre>
    [
        {
        "name":"红茶1",
        "descript":"红茶的描述信息",
        "url":"图片地址url1",
        "multipartFile":图片文件（用控件上传）
        }
    ]
</pre>
* 返回：
 <pre>{
 	 "code": 200,
  	 "data": "all succeed"
	}
	</pre>
### 产品类型的修改
* URL:http://localhost:8080/api/productType/update
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
