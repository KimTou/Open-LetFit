package com.letfit.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.letfit.shiro.auth.MyCredentialsMatcher;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author cjt
 */
@Configuration
public class ShiroConfig {

    /**
     * 过滤器工厂（配置过滤规则）
     * @param defaultWebSecurityManager
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //设置安全管理器
        bean.setSecurityManager(defaultWebSecurityManager);
        //添加shiro的内置过滤器
        Map<String,String> filterMap = new LinkedHashMap<>();
        filterMap.put("/swagger**/**","anon");
        filterMap.put("/v3/**","anon");
        filterMap.put("/doc.html","anon");
        filterMap.put("/admin/login","anon");
        filterMap.put("/admin/addAdmin","roles[root]");
        //权限拦截
        filterMap.put("/admin/**","authc");
        bean.setFilterChainDefinitionMap(filterMap);
        bean.setLoginUrl("/toLogin");
        return bean;
    }

    /**
     * 安全管理器
     * @param userRealm
     * @return
     */
    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //关联UserRealm
        defaultWebSecurityManager.setRealm(userRealm);
        return defaultWebSecurityManager;
    }

    /**
     * 身份认证 Realm
     * @return
     */
    @Bean
    public UserRealm userRealm(){
        UserRealm userRealm = new UserRealm();
        userRealm.setAuthenticationTokenClass(AuthenticationToken.class);
        userRealm.setCredentialsMatcher(new MyCredentialsMatcher());
        return userRealm;
    }

    /**
     * thymeleaf整合shiro
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }
}
