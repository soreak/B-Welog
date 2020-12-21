package com.soreak.utils.phoneVerify.service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * @program: welog
 * @author: soreak
 * @description: 短信服务
 * @create: 2020-12-20 12:42
 **/
public class SMSService {

    //产品名称：云提醒短信api
    static final String product="Dysmsapi";

    //产品域名
    static final String domain = "dysmsapi.aliyuncs.com";


    //签名
    static final String signName = "juice";

    //短信模板
    static final String  Template= "SMS_207500910";


    /*阿里云的AK*/
    static final String accessKeyId = "LTAI4G9n5HEDDTf1XZQk9u3u";
    static final String accessKeySecret = "lYZfGIpeJEqB9OONLIxzPwJjLnzoG5";

    public static SendSmsResponse sendSms(String phone,String code) throws ClientException {
        //超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout","10000");
        System.setProperty("sun.net.client.defaultReadTimeout","10000");

        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",accessKeyId,accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou","cn-hangzhou",product,domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        SendSmsRequest request = new SendSmsRequest();

        request.setPhoneNumbers(phone);
        request.setTemplateCode(Template);
        request.setTemplateParam("{\"code\":\"" + code + "\"}");
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return sendSmsResponse;

    }



}
