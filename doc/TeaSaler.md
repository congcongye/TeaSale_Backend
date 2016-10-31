# Api

### 查询茶农
* URL /api/teaSalers/search?name=ziz&level=1&tel=152&pageIndex=0&pageSize=10&sortField=id&sortOrder=ASC
* Method: GET
* 参数:
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