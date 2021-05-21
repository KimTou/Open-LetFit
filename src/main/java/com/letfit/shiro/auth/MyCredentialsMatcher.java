package com.letfit.shiro.auth;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author cjt
 * @date 2021/4/28 22:55
 * description:密码比较器
 */
public class MyCredentialsMatcher extends SimpleCredentialsMatcher {
    /**
     * 密码比较
     * @param token
     * @param info
     * @return
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        //前端传来的密码
        UsernamePasswordToken admin = (UsernamePasswordToken) token;
        String password = new String(admin.getPassword());
        //数据库中查询出的加密后的密码
        String encodePassword = (String) info.getCredentials();
        //密码比较
        return BcryptUtil.match(password, encodePassword);
    }
}
