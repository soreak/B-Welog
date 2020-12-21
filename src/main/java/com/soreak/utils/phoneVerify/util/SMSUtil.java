package com.soreak.utils.phoneVerify.util;

import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;

import java.util.Map;

/**
 * @program: welog
 * @author: soreak
 * @description: 云片验证码
 * @create: 2020-12-21 16:31
 **/
public class SMSUtil {
    private final static String YUNPIAN_APIKEY="82c722ecc5e610c73af1ba48bd33cc56"; //key
    private final static int RANDOMSIZE=6; //验证码长度

    /**
     * 云片网发送手机验证码
     * @param mobile 手机号码
     * @return 成功的话返回验证码，失败的话返回 error字符串
     */
    public static String sendSMS(String mobile){
        //初始化clnt,使用单例方式
        YunpianClient clnt = new YunpianClient(YUNPIAN_APIKEY).init();
        String verifyCode = RandomNum.createRandomBySize(RANDOMSIZE);//生成短信验证码
        //发送短信API
        Map<String, String> param = clnt.newParam(2);
        param.put(YunpianClient.MOBILE, mobile);
        param.put(YunpianClient.TEXT, "【蔡泽华】您的验证码是"+verifyCode+"。如非本人操作，请忽略本短信");
        Result<SmsSingleSend> r = clnt.sms().single_send(param);
        clnt.close();
        if (r.getCode()!=0){
            return "error";
        }
        return verifyCode;
    }

}
