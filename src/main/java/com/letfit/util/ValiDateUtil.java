package com.letfit.util;

/**
 * @author cjt
 * @date 2021/4/17 16:00
 */
public class ValiDateUtil {

    /**
     * 判断字符串是否合法（不为空）
     * @param dataString
     * @return
     */
    public static boolean isLegalString(String dataString){
        if (dataString == null || "".equals(dataString)) {
            return false;
        }
        return true;
    }

}
