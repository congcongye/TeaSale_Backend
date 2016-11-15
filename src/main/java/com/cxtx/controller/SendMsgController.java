//package com.cxtx.controller;
//
//import com.cxtx.model.ServiceResult;
//
///**
// * Created by ycc on 16/11/13.
// */
//public class SendMsgController {
//
//    private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
//
//    public static void main(String [] args) {
//
//        HttpClient client = new HttpClient();
//        PostMethod method = new PostMethod(Url);
//
//        client.getParams().setContentCharset("GBK");
//        method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=GBK");
//
//        int mobile_code = (int)((Math.random()*9+1)*100000);
//
//        String content = new String("ÄúµÄÑéÖ¤ÂëÊÇ£º" + mobile_code + "¡£Çë²»Òª°ÑÑéÖ¤ÂëÐ¹Â¶¸øÆäËûÈË¡£");
//
//        NameValuePair[] data = {
//                new NameValuePair("account", "ÓÃ»§Ãû"),
//                new NameValuePair("password", "ÃÜÂë"),
//                new NameValuePair("mobile", "ÊÖ»úºÅÂë"),
//                new NameValuePair("content", content),
//        };
//        method.setRequestBody(data);
//
//        try {
//            client.executeMethod(method);
//
//            String SubmitResult =method.getResponseBodyAsString();
//
//            //System.out.println(SubmitResult);
//
//            Document doc = DocumentHelper.parseText(SubmitResult);
//            Element root = doc.getRootElement();
//
//            String code = root.elementText("code");
//            String msg = root.elementText("msg");
//            String smsid = root.elementText("smsid");
//
//            System.out.println(code);
//            System.out.println(msg);
//            System.out.println(smsid);
//
//            if("2".equals(code)){
//                System.out.println("¶ÌÐÅÌá½»³É¹¦");
//            }
//
//        } catch (HttpException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (DocumentException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//    }
//}
