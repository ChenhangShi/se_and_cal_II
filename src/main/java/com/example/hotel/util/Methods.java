package com.example.hotel.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

public class Methods {
    public static String md5Encryption(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("md5");

            // 通过md5计算摘要,返回一个字节数组
            byte[] bytes = md.digest(src.getBytes("UTF-8"));

            // 再将字节数编码为用a-z A-Z 0-9 / *一共64个字符表示的要存储到数据库的字符串，所以又叫BASE64编码算法
            String res = Base64.getEncoder().encodeToString(bytes);
            return res;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;// 发生异常返回空
    }

    public static String getTodayStr(){
        LocalDate today=LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return formatter.format(today);
    }

    public static String getNowStr(){
        LocalDateTime now=LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(now);
    }
}
