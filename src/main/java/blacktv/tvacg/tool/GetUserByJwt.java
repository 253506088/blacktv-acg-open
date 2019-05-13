package blacktv.tvacg.tool;

import blacktv.tvacg.database.pojo.User;
import blacktv.tvacg.database.service.JwtUtil;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * 根据jwt获取用户对象，也起到了验证登录的作用
 */
@Service
@Log4j
public class GetUserByJwt {
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 根据jwt获取用户对象，本方法会判断当前请求ip和登录时ip是否相同，不相同无法获取用户对象
     *
     * @param jwt
     * @param request
     * @return
     */
    public User getUserByJwt(String jwt, HttpServletRequest request) {
        User user = null;
        try {
            Claims c = jwtUtil.parseJWT(jwt);// 解析JWT
            String loginIp = c.get("loginIp", String.class);
            user = JSON.parseObject(c.getSubject(), User.class);// 将json转换为实体类
            //只有登录时的ip和当前发起请求的ip相同才返回用户对象
            if (loginIp.equals(request.getRemoteAddr()))
                log.info("ip:" + loginIp + "\t登录，操作的用户id为:" + user.getId());
            else {
                log.info("登录时ip:" + loginIp + "\t发起请求的ip:" + request.getRemoteAddr() + "\t，操作的用户id为:" + user.getId());
                return null;
            }
        } catch (Exception e) {
            log.info("已过期的jwt:" + jwt);
        }
        return user;
    }
}
