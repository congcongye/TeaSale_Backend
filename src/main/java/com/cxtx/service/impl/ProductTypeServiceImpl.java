package com.cxtx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ycc on 16/10/22.
 */
public class ProductTypeServiceImpl {

    public Map<String,Object> newProductType(List<Map<String,Object>> productTypes){
        Map<String,Object> result =new HashMap<String,Object>();
        if(productTypes==null || productTypes.isEmpty()){
            result.put("code",2);
            result.put("msg","信息未传入");
            return result;
        }
        for(Map<String,Object> map:productTypes){
            String name="",descript="",url="";
            try{
                name = (String)map.get("name");
                descript =(String)map.get("descript");
                url =(String)map.get("url");
            }catch (Exception e){
                result.put("code",2);
                result.put("msg","新增的产品类型信息不完整");
                return result;
            }


        }
        return result;
    }
}
