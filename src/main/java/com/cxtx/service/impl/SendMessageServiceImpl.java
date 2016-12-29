package com.cxtx.service.impl;

import com.cxtx.utils.Constant;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

import java.io.IOException;


/**
 * Created by ycc on 16/11/16.
 */
@Service("SendMessageServiceImpl")
public class SendMessageServiceImpl {

    String postUrl="http://106.ihuyi.com/webservice/sms.php?method=Submit";
    String APIKEY="dc7f6ce2b5bfe190c6388524b6ecc6af";
    String count ="cf_congcong";

    public  Map<String, String> createMsg(String telephone) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(postUrl);

        client.getParams().setContentCharset("UTF-8");
        method.setRequestHeader("ContentType",
                "application/x-www-form-urlencoded;charset=UTF-8");
        String mobile_code;
        mobile_code = (int) ((Math.random() * 9 + 1) * 100000) + "";
        String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

        NameValuePair[] data = {// 提交短信
                new NameValuePair("account", "cf_congcong"),//注册的用户名
                new NameValuePair("password", "123456ycc"), // 注册的密码,可以使用明文密码或使用32位MD5加密
                new NameValuePair("mobile", telephone),
                new NameValuePair("content", content),};
        method.setRequestBody(data);
        try {
            client.executeMethod(method);
            String SubmitResult = method.getResponseBodyAsString();
            System.out.print(SubmitResult);
            org.dom4j.Document doc = DocumentHelper.parseText(SubmitResult);
            Element root = doc.getRootElement();
            String code = root.elementText("code");
            String msg = root.elementText("msg");
            String smsid = root.elementText("smsid");
            System.out.println(code);
            System.out.println(msg);
            System.out.println(smsid);
            if ("2".equals(code)) {
                System.out.println("短信提交成功");
                Map<String, String> map = new HashMap<String, String>();
                map.put("phone",telephone);
                map.put("code",mobile_code);
                Constant.vCodes.put(telephone, mobile_code);
                return map;
            }
        } catch (HttpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public String strToJsonStr (SortedMap<String, String> paramMap) {  //把参数转换成json格式字符串
        JSONObject jsonParam = new JSONObject();
        for (Map.Entry<String, String> entry : paramMap.entrySet()){
            try {
                jsonParam.put(entry.getKey(),entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonParam.toString();
    }



}
