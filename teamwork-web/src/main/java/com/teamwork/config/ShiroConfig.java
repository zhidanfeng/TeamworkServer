package com.teamwork.config;

import com.teamwork.shiro.JwtFilter;
import com.teamwork.shiro.ShiroCacheManager;
import com.teamwork.shiro.ShiroRealm;
import com.teamwork.shiro.SystemLogoutFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

//@Configuration
public class ShiroConfig {
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator getLifecycleBeanPostProcessor() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(ShiroRealm shiroRealm, ShiroCacheManager shiroCacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        securityManager.setRealm(shiroRealm);

        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        //自定义缓存管理
        securityManager.setCacheManager(shiroCacheManager);
        return securityManager;
    }

    /**
     * 需要在web.xml中设置filter
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);

        // 添加jwt过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwt", jwtFilter());
        filterMap.put("logout", new SystemLogoutFilter());
        shiroFilter.setFilters(filterMap);

        //拦截器，不能用HashMap，因为HashMap是无需的，无法保证url的拦截顺序
        Map<String, String> filterRuleMap = new LinkedHashMap<>();
        // user/login直接放行，需要放行的url放在/**上面声明
        filterRuleMap.put("/user/login", "anon");
        filterRuleMap.put("/logout", "logout");
        filterRuleMap.put("/**", "jwt");
        shiroFilter.setFilterChainDefinitionMap(filterRuleMap);

        return shiroFilter;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }
}
