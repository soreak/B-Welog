package com.soreak.utils.phoneVerify.util;

import java.util.Random;

/**
 * @program: welog
 * @author: soreak
 * @description: 随机数生成器
 * @create: 2020-12-20 13:20
 **/
public class RandomNum {
    /**
     * 根据size长度生成随机数字字符串
     * @param size 长度
     * @return
     */
    public static String createRandomBySize(int size){
        if (size<=0){
            size=1;
        }
        int bound=1;
        for (int i=1;i<size;i++){
            bound=bound*10;
        }
        String verifyCode = String
                .valueOf(new Random().nextInt((bound*10)-(bound+1)) + bound);//生成短信验证码
        return verifyCode;
    }


    public static void main(String[] args) {
        System.out.println(createRandomBySize(6));
    }
}
