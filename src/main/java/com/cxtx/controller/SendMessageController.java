package com.cxtx.controller;

import com.cxtx.model.ServiceResult;
import com.cxtx.service.impl.SendMessageServiceImpl;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by ycc on 16/11/16.
 */
@Component
public class SendMessageController extends  ApiController {
    @Autowired
    private SendMessageServiceImpl sendMessageService;
    private Map<String, Object> dataMap;

    @RequestMapping(value = "/sendMessage/password", method = RequestMethod.GET)
    @ResponseBody
    public ServiceResult send(@RequestParam (value = "mobile",defaultValue = "")String mobile){
        Map<String, String> result =sendMessageService.createMsg(mobile);
        return ServiceResult.success(result);
    }

//    /**
//     * 用户注册获取验证码
//     *
//     * @return 验证码
//     * @throws IOException
//     */
//    @RequestMapping(value = "/code", method = RequestMethod.POST)
//    public @ResponseBody
//    Map<String, Object> verifycode() throws IOException {
//        dataMap = new LinkedHashMap<String, Object>();
//        String payloadRequest = this.getBody(request);
//        JSONObject jsonObject = JsonUtil.toJSONObject(payloadRequest);
//        String msg = "0";
//        if (jsonObject.containsKey("phoneNumber")) {	// 注册时的获取验证码
//            String phoneNumber = jsonObject.getString("phoneNumber");
//            String code = sendsms(phoneNumber);
//            if (code != null) {
//                Jedis jedis = RedisUtil.getJedis();
//                jedis.set(phoneNumber, code);
//                jedis.expire(phoneNumber, 60 * 30);	//设置过期时间为30分钟
//                RedisUtil.returnResource(jedis);
//                msg = "1";
//            }
//        } else if (jsonObject.containsKey("username")
//                && jsonObject.containsKey("role")) {	//	找回秘密时的获取验证码
//            String username = jsonObject.getString("username");
//            String role = jsonObject.getString("role");
//            String phoneNumber = null;
//            if ("4".equals(role)) {
//                Teacher teacher = teacherService.getByUsername(username);
//                phoneNumber = teacher.getPhone();
//            } else if ("5".equals(role)) {
//                Student student = studentService.getByUsername(username);
//                phoneNumber = student.getPhone();
//            }
//            if (phoneNumber != null) {
//                String code = sendsms(phoneNumber);
//                if (code != null) {
//                    Jedis jedis = RedisUtil.getJedis();
//                    jedis.set(phoneNumber, code);
//                    jedis.expire(phoneNumber, 60 * 30);	//设置过期时间为30分钟
//                    RedisUtil.returnResource(jedis);
//                    msg = "1";
//                }
//            }
//        }
//        dataMap.put("msg", msg);
//        return dataMap;
//    }
}
