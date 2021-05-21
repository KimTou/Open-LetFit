package com.letfit.shiro.auth;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author cjt
 * @date 2021/4/28 22:19
 * description:密码加密工具类（Bcrypt+盐）
 */
public class BcryptUtil {

    /**
     * 加密
     * @param password
     * @return
     */
    public static String encode(String password){
        //对明文密码进行加密,并返回加密后的密码
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * 密码校验
     * @param password
     * @param encodePassword
     * @return
     */
    public static boolean match(String password, String encodePassword){
        //将明文密码跟加密后的密码进行匹配，如果一致返回true,否则返回false
        return BCrypt.checkpw(password, encodePassword);
    }

}
