# Api

### 查询茶农(lever=1 查询的是经销商 lever为2是茶农)
* URL /api/teaSalers/search?name=ziz&level=1&tel=152&state=0&pageIndex=0&pageSize=10&sortField=id&sortOrder=ASC
* Method: GET
* 参数:
```
name
tel
以上支持模糊查询
state与level
不填则全查
pageIndex,pageSize,sortField,sortOrder
用于分页查询
```
* 返回:
```
{{
   "code": 200,
   "data": {
     "content": [
       {
         "id": 1,
         "name": "zizi",
         "level": 1,
         "nickname": null,
         "account": {
           "id": 2,
           "tel": "15200837632",
           "password": "zizi",
           "label": 0,
           "alive": 1
         },
         "address": null,
         "tel": "15200837632",
         "headUrl": null,
         "money": 0,
         "licenseUrl": null,
         "zip": null,
         "idCard": null,
         "state": 1,
         "alive": 1,
         "createDate": null
       },
       {
         "id": 2,
         "name": "zizi",
         "level": 1,
         "nickname": null,
         "account": {
           "id": 8,
           "tel": "15207808609",
           "password": "zizi",
           "label": 0,
           "alive": 1
         },
         "address": null,
         "tel": "15207808609",
         "headUrl": null,
         "money": 0,
         "licenseUrl": null,
         "zip": null,
         "idCard": null,
         "state": 0,
         "alive": 1,
         "createDate": "2016-11-01"
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
```
### 获得某一茶农的具体信息
* URL /api/teaSaler/{teaSalerId}
* Method: GET
* 参数:
teaSalerId:PathVariable
* 返回: level 1 为经销商 2为茶农
```
{
  "code": 200,
  "data": {
    "id": 1,
    "name": "zizi",
    "level": 1,
    "nickname": null,
    "account": {
      "id": 2,
      "tel": "15200837632",
      "password": "zizi",
      "label": 0,
      "alive": 1
    },
    "address": null,
    "tel": "15200837632",
    "headUrl": null,
    "money": 0,
    "licenseUrl": null,
    "zip": null,
    "idCard": null,
    "state": 1,
    "alive": 1,
    "createDate": null
  }
}
```
### 茶农登录
* URL /api/teaSaler/login
* Method: POST
* 参数: RequestBody
```
{
    "tel":"15207808609",
    "password":"zizi"
}
```
* 返回:
```
成功:
{
  "code": 200,
  "data": {
    "id": 2,
    "name": "zizi",
    "level": 1,
    "nickname": null,
    "account": {
      "id": 8,
      "tel": "15207808609",
      "password": "zizi",
      "label": 0,
      "alive": 1
    },
    "address": null,
    "tel": "15207808609",
    "headUrl": null,
    "money": 0,
    "licenseUrl": null,
    "zip": null,
    "idCard": null,
    "state": 0,
    "alive": 1,
    "createDate": "2016-11-01"
  }
}
失败:
{
  "code": 500,
  "data": "no account record !"
}
```
### 茶农注册
* URL /api/teaSaler/register
* Method: POST
* 参数: RequestBody
```
{
    "nickname":"zizi",
    "tel":"15907823456",
    "password":"1111111",
    "address":"zizi",
    "zip":"315200",
    "money":10000.00,
    "name":"金初阳",
    "idCard":12345678,
    "level":1 //1代表经销商2代表个体茶农
}
```
* 返回:
```
成功:
{
  "code": 200,
  "data": {
    "id": 3,
    "name": "金初阳",
    "level": 0,
    "nickname": "zizi",
    "account": {
      "id": 11,
      "tel": "15907823456",
      "password": "1111111",
      "label": 0,
      "alive": 1
    },
    "address": "zizi",
    "tel": "15907823456",
    "headUrl": null,
    "money": 10000,
    "licenseUrl": null,
    "zip": "315200",
    "idCard": "12345678",
    "state": 0,
    "alive": 1,
    "createDate": "2016-11-01"
  }
}
失败:
{
  "code": 500,
  "data": "register failed, the tel already has account!"
}
```
### 批量审批茶农(lever 1的为经销商, 2为茶农,经销商需要有营业执照)
* URL /api/teaSalers/approve
* Method: PUT
* 参数: RequestBody(List<TeaSaler>)
```
[
      {
        "id": 1,
        "name": "金初阳",
        "level": 0,
        "nickname": "zizi",
        "account": {
          "id": 3,
          "tel": "15907823456",
          "password": "1111111",
          "label": 0,
          "alive": 1
        },
        "address": "zizi",
        "tel": "15907823456",
        "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
        "licenseUrl": null,
        "zip": "315200",
        "idCard": "12345678",
        "state": 0,
        "alive": 1,
        "createDate": "2016-11-10"
      },
      {
        "id": 2,
        "name": "金初阳",
        "level": 0,
        "nickname": "zizi",
        "account": {
          "id": 4,
          "tel": "15907823451",
          "password": "1111111",
          "label": 0,
          "alive": 1
        },
        "address": "zizi",
        "tel": "15907823451",
        "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
        "money": 10000,
        "licenseUrl": null,
        "zip": "315200",
        "idCard": "12345678",
        "state": 0,
        "alive": 1,
        "createDate": "2016-11-10"
      },
      {
        "id": 3,
        "name": "金初阳",
        "level": 0,
        "nickname": "zizi",
        "account": {
          "id": 5,
          "tel": "15907823452",
          "password": "1111111",
          "label": 0,
          "alive": 1
        },
        "address": "zizi",
        "tel": "15907823452",
        "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
        "money": 10000,
        "licenseUrl": null,
        "zip": "315200",
        "idCard": "12345678",
        "state": 0,
        "alive": 1,
        "createDate": "2016-11-10"
      },
      {
        "id": 4,
        "name": "金初阳",
        "level": 0,
        "nickname": "zizi",
        "account": {
          "id": 6,
          "tel": "15907823453",
          "password": "1111111",
          "label": 0,
          "alive": 1
        },
        "address": "zizi",
        "tel": "15907823453",
        "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
        "money": 10000,
        "licenseUrl": null,
        "zip": "315200",
        "idCard": "12345678",
        "state": 0,
        "alive": 1,
        "createDate": "2016-11-10"
      },
      {
        "id": 5,
        "name": "金初阳",
        "level": 0,
        "nickname": "zizi",
        "account": {
          "id": 7,
          "tel": "15907823454",
          "password": "1111111",
          "label": 0,
          "alive": 1
        },
        "address": "zizi",
        "tel": "15907823454",
        "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
        "money": 10000,
        "licenseUrl": null,
        "zip": "315200",
        "idCard": "12345678",
        "state": 0,
        "alive": 1,
        "createDate": "2016-11-10"
      }
    ] "level":1
}
```
* 返回:
```
成功:
{
  "code": 200,
  "data": "all succeed"
}
失败:
{
  "code": 500,
  "data": "the num of succeed is 0 ; the fail number is 5"
}
```
### 农户上传营业执照照片
* URL /api/image/licence/upload?teaSalerId=1
* Method: POST
* 参数: 
```
picture:MultipartFile
```
* 返回:
```
{
  "code": 200,
  "data": "head pic upload succeed "
}
```
### 更新茶农信息
* URL /api/teaSaler/{teaSalerId}
* Method: PUT
* 参数:
teaSaler:
{
    "nickname":"413",
    "address":"x36-4041",
    "zip":"210000"
}
只能更改昵称、地址、与邮编;
```
{
  "code": 200,
  "data": {
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
      "money": 10000000
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
  }
}
```
### 茶农删除
* URL /api/teaSaler/delete/{teaSalerId}
* Method: PUT

* 返回:成功:
```
{
  "code": 200,
  "data": "删除成功"
}
```
失败:
```
{
  "code": 500,
  "data": "存在未完成的订单,无法删除"
}
```