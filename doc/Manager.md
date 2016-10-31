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
    "lever":1,
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
    "lever": 1,
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