package blacktv.tvacg.error;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Log4j
/**
 * 全局异常处理，主要处理返回json和返回页面的方法的异常。
 */
@ControllerAdvice(basePackages = "blacktv.tvacg.controller")//指定对哪个包进行统一异常处理
public class Error {
//    @ResponseBody//以json格式返回处理结果
//    @ExceptionHandler(RuntimeException.class)//指定拦截的异常类型，这里指定运行时异常
//    /**
//     * 捕获指定包里的运行时异常并处理，处理结果以JSON的格式返回
//     * @return
//     */
//    public Map<String, Object> catchRuntimeException() {
//        Map<String, Object> map = new HashMap<>();
//        map.put("error", "捕获到异常");
//        log.error("捕获到运行时异常");//添加到错误日志中
//        return map;
//    }
}
