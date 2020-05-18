package com.teamwork.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.teamwork.consts.ShiroConsts;
import com.teamwork.token.JwtToken;
import com.teamwork.utils.JsonUtil;
import com.teamwork.utils.JwtUtil;
import com.teamwork.vo.Result;
import com.teamwork.vo.ResultCode;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 判断请求头是否含有Authorization
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(ShiroConsts.REQUEST_AUTH_HEADER);
        return authorization != null;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("JwtFilter--executeLogin");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(ShiroConsts.REQUEST_AUTH_HEADER);

        JwtToken token = new JwtToken(authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);

        //绑定上下文获取账号
        String account = JwtUtil.getClaim(authorization, ShiroConsts.ACCOUNT);

        //检查是否需要更换token，需要则重新颁发
        this.refreshTokenIfNeed(account, authorization, response);

        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.println("JwtFilter--isAccessAllowed");
        if (this.isLoginAttempt(request, response)) {
            try {
                this.executeLogin(request, response);
                return true;
            } catch (Exception e) {
                String msg = "";
                if (e.getCause() instanceof TokenExpiredException) {
                    msg = String.format("Token已过期(%s)", e.getCause().getMessage());
                }
                this.response401(response, msg);
                return false;
            }
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.sendChallenge(request, response);
        return false;
    }

    private boolean refreshTokenIfNeed(String account, String authorization, ServletResponse response) {
        Long currentTimeMillis = System.currentTimeMillis();

        if (refreshCheck(authorization, currentTimeMillis)) {
            String strCurrentTimeMillis = String.valueOf(currentTimeMillis);
            String newToken = JwtUtil.sign(account, strCurrentTimeMillis);

            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setHeader(ShiroConsts.REQUEST_AUTH_HEADER, newToken);
            httpServletResponse.setHeader("Access-Control-Expose-Headers", ShiroConsts.REQUEST_AUTH_HEADER);
        }
        return true;
    }

    /**
     * 检查是否需要更新Token
     *
     * @param authorization
     * @param currentTimeMillis
     * @return
     */
    private boolean refreshCheck(String authorization, Long currentTimeMillis) {
        String tokenMillis = JwtUtil.getClaim(authorization, ShiroConsts.CURRENT_TIME_MILLIS);
        if (currentTimeMillis - Long.parseLong(tokenMillis) > (2 * 60 * 1000L)) {
            return true;
        }
        return false;
    }

    /**
     * 401非法请求
     *
     * @param resp
     * @param msg
     */
    private void response401(ServletResponse resp, String msg) {
        HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            out = httpServletResponse.getWriter();

            Result result = Result.failure(ResultCode.FAILURE, msg);
            out.append(JsonUtil.objectToJson(result));
        } catch (Exception e) {
            //logger.error("返回Response信息出现IOException异常:" + e.getMessage());
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
