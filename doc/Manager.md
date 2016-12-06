# Api

### 管理员注册
* URL /api/manager/register
* Method: POST
* 参数:
```
{
    "name":"金初阳",
    "tel":"15200837336",
    "password":"zizi",
    "level":1,
    "money":100000.00
}
```
* 返回:
```
{
  "code": 200,
   "data": {
     "id": 2,
     "name": "金初阳",
     "tel": null,
     "level": 1,
     "money": 100000,
     "headUrl": null,
     "account": {
       "id": 5,
       "tel": "15200837336",
       "password": "zizi",
       "label": 0,
       "alive": 1
     },
     "alive": 1
   }
 }
```
### 管理员登陆
* URL /api/manager/login
* Method: POST
* 参数:
```
{
  "tel":"13201716308",
  "password":"123456"
}
```
* 返回:
```
{
  "code": 200,
  "data": {
    "id": 1,
    "name": "llf1",
    "tel": null,
    "level": 1,
    "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
    "account": {
      "id": 2,
      "tel": "13201716308",
      "password": "123456",
      "label": 0,
      "alive": 1
    },
    "alive": 1,
    "createDate": "2016-11-10"
  }
```
### 管理员更新
* URL /api/manager/update
* Method: PUT
* 参数:
```
{
    "name":"abc",
    "password":"123",
    "tel":"13201716308"
}
```
* 返回:
```
{
  "code": 200,
  "data": {
    "id": 1,
    "name": "abc",
    "tel": null,
    "level": 1,
    "money": 100000,
    "headUrl": "/home/administrator/CXTX/upload/picture//default.jpg",
    "account": {
      "id": 2,
      "tel": "13201716308",
      "password": "123",
      "label": 0,
      "alive": 1,
      "headURL": null,
      "money": 10000000
    },
    "alive": 1,
    "createDate": "2016-11-10"
  }
}