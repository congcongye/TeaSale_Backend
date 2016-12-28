# Api

### 查询用户
* URL /api/customers/search
* Method: GET
* 参数:
```
name和tel:以上支持模糊查询
level:不填则全查
pageIndex,pageSize,sortField,sortOrder:用于分页查询
```
* 返回:
```
{
  "code": 200,
  "data": {
    "content": [
      {
        "id": 1,
        "level": 1,
        "nickname": "利拉德",
        "account": {
          "id": 1,
          "tel": "15200837678",
          "password": "zizi",
          "label": 0,
          "alive": 1
        },
        "address": null,
        "zip": null,
        "tel": "15200837678",
        "money": 0,
        "headUrl": null,
        "alive": 1,
        "createDate": null
      },
      ...
    ],
    "totalPages": 1,
    "totalElements": 5,
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
    "numberOfElements": 5,
    "first": true
  }
}
```
### 获得某一客户的具体信息
* URL /api/customer/{customerId}
* Method: GET
* 参数:
customerId:PathVariable
* 返回:
```
{
  "code": 200,
  "data": {
    "id": 1,
    "level": 1,
    "nickname": "利拉德",
    "account": {
      "id": 1,
      "tel": "15200837678",
      "password": "zizi",
      "label": 0,
      "alive": 1
    },
    "address": null,
    "zip": null,
    "tel": "15200837678",
    "headUrl": null,
    "alive": 1,
    "createDate": null
  }
}
```
### 客户登录
* URL /api/customer/login
* Method: POST
* 参数: RequestBody
```
{
    "tel":"15907823432",
    "password":"1111111"
}
```
* 返回:
```
成功:
{
  "code": 200,
  "data": {
    "id": 5,
    "level": 1,
    "nickname": "zizi",
    "account": {
      "id": 12,
      "tel": "15907823432",
      "password": "1111111",
      "label": 0,
      "alive": 1
    },
    "address": "zizi",
    "zip": "315200",
    "tel": "15907823432",
    "money": 10000,
    "headUrl": null,
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
### 客户注册
* URL /api/customer/register
* Method: POST
* 参数: RequestBody
```
{
    "nickname":"zizi",
    "tel":"15907823432",
    "password":"1111111",
    "address":"zizi",
    "zip":"315200",
    "money":10000.00,
    "name":"金初阳",
    "level":1
}
```
* 返回:
```
成功:
{
  "code": 200,
  "data": {
    "id": 5,
    "level": 1,
    "nickname": "zizi",
    "account": {
      "id": 12,
      "tel": "15907823432",
      "password": "1111111",
      "label": 0,
      "alive": 1
    },
    "address": "zizi",
    "zip": "315200",
    "tel": "15907823432",
    "money": 10000,
    "headUrl": null,
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
### 客户更新
* URL /api/customer/update
* Method: PUT
* 参数: RequestBody
```
{
    "nickname":"123",
    "address":"闵行东川路",
    "zip":"12344567",
    "tel":13918966539,
    "password":123456
}
```
* 返回:
```
成功:更新之后的customer
{
  "code": 200,
  "data": {
    "id": 1,
    "level": 0,
    "nickname": "123",
    "account": {
      "id": 8,
      "tel": "13918966539",
      "password": "123456",
      "label": 0,
      "alive": 1,
      "headURL": "836e8e3a0af54847902cc1d31cb68e89.jpg"
    },
    "address": "闵行东川路",
    "zip": "12344567",
    "tel": "13918966539",
    "money": 0,
    "headUrl": null,
    "alive": 1,
    "createDate": "2016-11-11"
  }
}
```
失败:1
```
{
  "code": 500,
  "data": "没有该帐号"
}
```
失败:2
```
{
  "code": 500,
  "data": "修改失败"
}
```

### 修改密码
* URL /api/customer/updatePassword
* Method: PUT
* 参数: RequestBody
```
{
    "verificationCode":"123",
    "tel":13918966539,
    "password":123456
}
```
* 返回:
```
成功:更新之后的customer

```