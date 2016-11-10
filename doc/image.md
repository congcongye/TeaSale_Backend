###image新建和修改
* URL:http://localhost:7000/api/image/upload?product_id=1&image_id=3
*  Method:POST
* 注意事项：要先创建产品之后才能再上传图片
* 参数：
  pictures :多个图片   
  product_id:产品的id 
  image_id:需要修改的图片id，如果是新增就改变量不需要传或者写－1
  type:1主图  0不是主图   
* 返回值:
 <pre>{
  "code": 200,
  "data": "all succeed"
}
</pre>

###图片显示
* URL:http://localhost:7000/api/image/getByUrl?url=3bb95882182f4fccbbd0e006e76383f6.jpg
* Method=GET
* 参数：
  url:image对象的url属性
* 返回值：无法显示，后台会把图片写入到response中
  os.write(FileUtils.readFileToByteArray(file));
###图片的批量删除
* URL:http://localhost:7000/api/image/delete
* Method:PUT
* 参数：
 <pre>
[
{
    "id":2   图片id
}
]</pre>
* 返回值：
 <pre>
{
  "code": 200,
  "data": "all succeed"
}
</pre>
  
###获得某个商品的所有图片
* URL:http://localhost:7000/api/image/getImageByProduct?product_id=1&type=-1
* Method:GET
* 参数：product_id:产品的id
type:＝1为主图 ＝0 普通图片 －1或不传查全部图片
* 返回值：
 <pre>
{
  "code": 200,
  "data": [
    {
      "id": 3,
      "name": "test.png",
      "url": "806dbdebdb064959b8464248afafb246.png",
      "product": {
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
        "isFree": 1,
        "postage": 0,
        "deliverLimit": 10,
        "createDate": "2016-11-09",
        "unit": "斤",
        "teaSaler": {
          "id": 1,
          "name": "ycc",
          "level": 1,
          "nickname": "ycc",
          "account": {
            "id": 1,
            "tel": "189841341",
            "password": "123123",
            "label": 1,
            "alive": 1
          },
          "address": "上海闵行区",
          "tel": "143241341324",
          "headUrl": null,
          "money": 1000,
          "licenseUrl": "134134321",
          "zip": "341343",
          "idCard": "23432143",
          "state": 1,
          "alive": 1,
          "createDate": "2016-11-09"
        },
        "state": 1,
        "alive": 1
      },
      "createDate": 1478697713000,
      "alive": 1
    }
  ]
}
</pre>

