package pers.guzx.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Guzx
 * @version 1.0
 * @date 2021/7/8 11:55
 * @describe
 */
public class MobileUtil {

    public static boolean isMobileNO(String mobile) {
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }
}
