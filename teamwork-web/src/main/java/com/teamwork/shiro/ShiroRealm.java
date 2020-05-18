package com.teamwork.shiro;

import com.teamwork.consts.ShiroConsts;
import com.teamwork.token.JwtToken;
import com.teamwork.utils.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

@Service
public class ShiroRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("ShiroRealm--doGetAuthorizationInfo");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        return authorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("ShiroRealm--doGetAuthenticationInfo");
        String token = (String) authenticationToken.getPrincipal();
        String account = JwtUtil.getClaim(token, ShiroConsts.ACCOUNT);

        if(account == null)
            throw new AuthenticationException("Token invalid");

        if(JwtUtil.verify(token)) {
            return new SimpleAuthenticationInfo(token, token, "shiroRealm");
        }
        throw new AuthenticationException("Token expired or incorrect.");
    }
}
