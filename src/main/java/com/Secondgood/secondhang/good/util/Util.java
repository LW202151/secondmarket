package com.Secondgood.secondhang.good.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class Util {


    private static Random random = new Random(getUniqueId().hashCode());

    /**
     * 得到唯一id
     * @return String 计算结果
     */
    public static String geFulltUniqueId() {

        return Util.getMD5(String.valueOf(new Date().getTime()));

    }


    /**
     * 得到唯一id
     * @return String 计算结果
     */
    public static String getUniqueId() {

        return Util.getMD5(String.valueOf(new Date().getTime())).substring(0, 20);

    }

    /**
     * 得到唯一id
     * @return String 计算结果
     */
    public static String getInformationUniqueId() {

        return Util.getMD5(String.valueOf(new Date().getTime())).substring(0, 20);

    }


    /**
     * 根据文件名得到文件类型
     * @param name 文件名
     * @return 文件类型
     */
    public static String fromNameGetType(String name) {
        String[] list = name.split("\\.");
        if (list.length == 0) {
            return null;
        }

        return list[list.length - 1];
    }

    /**

     * 获取随机数

     * @return int

     */

    public static long rand() {
        return random.nextLong();
    }



    /**

     * MD5

     * @param str 带计算字符串

     * @return String 计算结果

     */

    public static String getMD5(String str) {

        try {

            // 生成一个MD5加密计算摘要

            MessageDigest md = MessageDigest.getInstance("MD5");

            // 计算md5函数

            md.update(str.getBytes());



            return new BigInteger(1, md.digest()).toString(16);

        } catch (Exception e) {

            // 出现异常返回时间戳

            return String.valueOf(new Date().getTime());

        }

    }



    /**

     * 获取本地时间String

     * @return String

     */

    public static String getNowTime() {



        Calendar cal = java.util.Calendar.getInstance(java.util.Locale.CHINA);

        SimpleDateFormat sf = new SimpleDateFormat("YYYY-MM-dd_HH:mm:ss");

        return sf.format(cal.getTime());

    }

    public static Date stringToDate(String dateString) {


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");

        Date date = null; //初始化date

        try {
            date = sdf.parse(dateString); //Mon Jan 14 00:00:00 CST 2013
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    /**
     * 比较字符串时间
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDateFromString(String date1, String date2) {

        Date realDate_1 = stringToDate(date1);
        Date realDate_2 = stringToDate(date2);

        return -realDate_1.compareTo(realDate_2);
    }



}

