package pers.guzx.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/12 11:00
 * @describe
 */
public class EmailUtil {

    public static boolean isEmail(String mobile) {
        String regExp = "^([a-z0-9A-Z]+[-|_|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]" +
                "+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
}
