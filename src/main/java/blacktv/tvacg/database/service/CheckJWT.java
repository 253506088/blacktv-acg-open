package blacktv.tvacg.database.service;

import blacktv.tvacg.database.pojo.User;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 用于登录检查，解析jwt的工具
 */
@Service
@Log4j
public class CheckJWT {
    @Autowired
    private JwtUtil jwtUtil;

    public User checkJWT(String jwt, HttpServletRequest request) {
        Claims c = null;
        User user = null;
        try {
            c = jwtUtil.parseJWT(jwt);//解析JWT
            user = JSON.parseObject(c.getSubject(), User.class);//将json转换为实体类
            if (user == null)
                throw new Exception("非法jwt");
            //这里考虑到动态ip所以就没用
//            if(!request.getRemoteAddr().equals(c.get("loginIp", String.class)))
//                throw new Exception("请求ip与jwt里记录的ip不一致");

        } catch (Exception e) {
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                    + "/";
            log.info("ip:" + request.getRemoteAddr() + "\t非法请求,请求的url为: " + basePath);
            log.error(e.toString(), e);
        } finally {
            return user;
        }
    }
}
