package com.teamwork.interceptor;

import com.teamwork.consts.EnvironmentParam;
import com.teamwork.utils.DateUtil;
import com.teamwork.utils.GuidUtils;
import com.teamwork.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Map;

/**
 * 拦截器-接口执行时间
 */
@Component
public class ExecutionTimeInterceptor implements HandlerInterceptor {

    public static final Logger LOGGER = LoggerFactory.getLogger(ExecutionTimeInterceptor.class);
    private static ThreadLocal<Long> startTimeThreadLocal = new ThreadLocal<>();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("startTime", startTime);
        startTimeThreadLocal.set(startTime);

        if(handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);

            sb.append("------------开始计时:").append(DateUtil.format(startTime, "HH:mm:ss.SSS")).append("------------").append(EnvironmentParam.NEW_LINE);

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            sb.append("Controller: ").append(handlerMethod.getBean().getClass().getName() + EnvironmentParam.NEW_LINE);
            sb.append("Method: ").append(handlerMethod.getMethod()).append(EnvironmentParam.NEW_LINE);
            sb.append("Params: ").append(getParamString(request.getParameterMap())).append(EnvironmentParam.NEW_LINE);
            sb.append("URI: ").append(request.getRequestURI());

            System.out.println(sb.toString());

            //生成logId，用于后续记录日志，方便追踪
            ThreadLocalUtil.setLogId(Thread.currentThread().getId() + "-" + GuidUtils.getUUID());
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (long)request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        if(handler instanceof HandlerMethod) {
            StringBuilder sb = new StringBuilder(1000);
            sb.append("CostTime  : ").append(executionTime).append("ms").append(EnvironmentParam.NEW_LINE);
            sb.append("-------------------------------------------------------------------------------");
            System.out.println(sb.toString());
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
        long endTime = System.currentTimeMillis(); // 2、结束时间

        // 如果controller报错，则记录异常错误
        if (ex != null) {
            LOGGER.debug("Controller异常: " + getStackTraceAsString(ex));
        }

        LOGGER.debug("计时结束：" + DateUtil.format(endTime,"HH:mm:ss.SSS") + " 耗时：" + (endTime - beginTime)
                + " URI:" + request.getRequestURI());
        startTimeThreadLocal.remove();
    }

    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> e : map.entrySet()) {
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if (value != null && value.length == 1) {
                sb.append(value[0]).append("\t");
            } else {
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }

    public static String getStackTraceAsString(Throwable e) {
        if (e == null) {
            return "";
        }
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
