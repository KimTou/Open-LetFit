package com.openletfit.shiro;

import com.openletfit.mapper.AdminMapper;
import com.openletfit.pojo.entity.Admin;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm
 * @author cjt
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private AdminMapper adminMapper;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前管理员
        Admin admin = (Admin) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加角色
        info.addRole(admin.getRole());
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        Admin admin = adminMapper.findAdminByName(token.getUsername());
        if(admin == null){
            return null;
        }
        return new SimpleAuthenticationInfo(admin,admin.getPassword(),null,getName());
    }

}
