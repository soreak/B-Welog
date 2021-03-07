package com.soreak.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @program: welog
 * @author: soreak
 * @description: MD5加密
 * @create: 2020-12-18 20:36
 **/
public class MD5Utils {

    public static String code(String str){
        try{
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[]byteDigest = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for(int offset = 0;offset<byteDigest.length;offset++){
                i=byteDigest[offset];
                if (i<0)
                    i+=256;
                if(i<16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));

            }
            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void main(String[] args) {
        System.out.println(code("123123"));
        System.out.println(UUID.randomUUID().toString());
    }
}