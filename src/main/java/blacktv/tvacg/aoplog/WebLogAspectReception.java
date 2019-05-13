package blacktv.tvacg.aoplog;

import lombok.extern.log4j.Log4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 用AOP记录前台WEB请求日志
 */
@Aspect
@Component
@Log4j
public class WebLogAspectReception {
    /**
     * 定义切点，切入指定包下的全部类的全部方法
     */
    @Pointcut("execution(* blacktv.tvacg.controller.reception.*.*(*))")
    public void webLog() { }

    /**
     * 前置增强拦截请求参数信息
     */
    @Before("webLog()")
    public void webLogBefore(JoinPoint joinPoint) {
        ServletRequestAttributes app = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        /**
         * 如果在控制器里调用了异步线程，这里必须加上非空判断，异步线程的启动也被视为一次请求但application为空，
         * 如果这里不加一个非空判断，会抛出null异常导致异步线程启动失败。
         */
        if (app == null)
            return;
        HttpServletRequest request = app.getRequest();
        //记录URL、IP、访问方法
        log.info("URL:" + request.getRequestURI());
        log.info("IP:" + request.getRemoteAddr());
        log.info("HTTP_METHOD:" + request.getMethod());
        //记录请求参数
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            log.info("key:" + key + " ,value:" + request.getParameter(key));
        }
    }

    /**
     * 后置增强，记录返回内容
     *
     * @param result
     */
    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void webLohA(Object result) {
        // 后置增强也添加非空判断
        if (result == null)
            return;
        log.info("result:" + result);
    }
}
