package com.dengzh.shop.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by dengzh on 2016/12/14.
 */

public class StringUtils {

    /**
     * 字符过滤
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public  static String stringFilter(String str)throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx  =  "[^a-zA-Z0-9\u4E00-\u9FA5]";
        // 清除掉所有特殊字符
        //String regEx = "[`~!@#$%^&*()+_=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p   =   Pattern.compile(regEx);
        Matcher m   =   p.matcher(str);
        return   m.replaceAll("").trim();
    }

    /**
     * 字符串省略
     * @param str  字符串
     * @param index  长度大于index的部分，用...代替
     * @return
     */
    public static String stringOmit(String str, int index){
        if(str==null){
            return "";
        }
        if(str.length()<=index){
            return str;
        }else{
            String temp = str.substring(0,index) + "...";
            return temp;
        }
    }

    public static DecimalFormat format = new DecimalFormat("0.00");

    /**
     * 转换成两位小数点string
     * @param string
     * @return
     */
    public static String formatTwoDecimal(String string){
        if(TextUtils.isEmpty(string)){
            return "0.00";
        }
        return format.format(Double.valueOf(string));
    }

    public static String formatTwoDecimal(Double string){
        return format.format(string);
    }


    /**
     * str为空或者空字符串则返回true。否则返回false
     */
    public static boolean isEmpty(String string) {
        return null == string || "".equals(string.trim()) || "null".equals(string.trim());
    }

    /**
     * 四舍五入
     * @param d
     * @return
     */
    public static String formatDouble(double d) {
        return String.format("%.2f", d);
    }

}
