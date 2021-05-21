package com.letfit.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author cjt
 * @date 2021/4/10 11:18
 */
public class DateUtil {

    /**
     * 获取当前时间
     * @return String
     */
    public static String getFileCurrentDate(){
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        return dateTimeFormatter.format(date);
    }

}
