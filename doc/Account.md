# Api

### 上传头像
* URL /api/image/head/upload?accountId=1
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
### 充值
* URL /api/account/recharge?money=10000&accountId=1
* Method: GET
* 参数:
```
money
accountId
```
* 返回:
```
{
  "code": 200,
  "data": "recharge success"
}
```
