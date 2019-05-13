package blacktv.tvacg.controller.admin;

import blacktv.tvacg.database.pojo.User;
import blacktv.tvacg.database.service.UserService;
import blacktv.tvacg.tool.GetMd5;
import blacktv.tvacg.tool.LayuiJsonUtil;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台用户相关的控制器
 */

@Log4j
@RestController()
@RequestMapping(value = "/blacktv/acg/admin")
public class AdminUserController {
    @Autowired
    private UserService userService;
    @Value("${tvacg.user.adminLoginTime}")
    private int adminLoginTime;//管理员用户session的有效期，单位小时
    private String error404Url = "/error/404";

    /**
     * 后台登录接口
     *
     * @param user
     * @param request
     * @param session
     * @return
     */
    @PostMapping(value = "/user/login", produces = "text/html;charset=utf-8", params = {"password", "email"})
    public String login(User user, HttpServletRequest request, HttpSession session) {
        //验证表单与登录
        user.setPassword(GetMd5.md5(user.getPassword()));
        user = userService.loginByEmail(user);
        Map<String, Object> msg = new HashMap<>();
        //验证登录状态
        if (user == null)
            msg.put("loginFlag", false);
        else {
            //验证权限
            if (user.getIsAdmin() < 1) {
                log.info("IP:" + request.getRemoteAddr() + "\t非法请求后台登录接口,请求内容为:" + JSONArray.toJSONString(user));
                return null;
            }
            log.info("IP:" + request.getRemoteAddr() + "\t登录后台登录接口,登录用户id为:" + user.getId());
            session.setAttribute("adminUser", user);
            session.setMaxInactiveInterval(adminLoginTime * 60 * 60);
            //buffer用来json化想展示给用户的数据，这里由于保存到session中，不能像前台登录接口
            User buffer = new User();
            buffer.setUsername(user.getUsername());
            buffer.setEmail(user.getEmail());
            buffer.setId(user.getId());
            msg.put("loginFlag", true);
            msg.put("adminUser", buffer);
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 登出接口
     *
     * @param logout
     * @param session
     * @return
     */
    @PostMapping(value = "/user/logout", produces = "text/html;charset=utf-8")
    public String logout(String logout, HttpSession session, HttpServletRequest request) {
        if (session.getAttribute("adminUser") == null) {
            log.info("IP:" + request.getRemoteAddr() + "\t非法请求后台登出接口");
            return null;
        }
        User adminUser = (User) session.getAttribute("adminUser");
        log.info("id为 " + adminUser.getId() + " 的管理员登出");
        session.invalidate();
        return "{\"logoutFlag\" : \"ture\"}";
    }

    @GetMapping(value = "/user/get/adminDate", produces = "text/html;charset=utf-8", params = {"page", "limit"})
    public String getUserData(int page, int limit, HttpServletRequest request) {
        if (!this.checkLogin(request)) {
            log.info("IP:" + request.getRemoteAddr() + "\t非法请求用户数据");
            return "redirect:" + error404Url;
        }
        User userAdmin = (User) request.getSession().getAttribute("adminUser");
        PageInfo<User> userPageInfo = null;
        Long count = null;
        if (userAdmin.getIsAdmin() == 1) {//普通管理员只能获取用户数据
            userPageInfo = userService.getUserPage(page, limit, new Byte("0"));
            count = userService.getUserMapper().getNumberByIsAdmin(new Byte("0"));
        } else if (userAdmin.getIsAdmin() == 2) {//普通管理员只能获取用户数据
            userPageInfo = userService.getUserPage(page, limit, null);
            count = userService.getUserMapper().getAllNumber();
        }
        return LayuiJsonUtil.getLayuiJson(0,count , "", userPageInfo.getList());
    }

    /**
     * 设置指定用户的删除状态
     * @param user
     * @param request
     * @return
     */
    @PostMapping(value = "/user/set/isDelete", produces = "text/html;charset=utf-8", params = {"id", "isDlete"})
    public String setIsDelete(User user,HttpServletRequest request){
        if (!this.checkLogin(request)) {
            log.info("IP:" + request.getRemoteAddr() + "\t非法请求设置用户删除状态接口");
            return "redirect:" + error404Url;
        }
        User userAdmin = (User) request.getSession().getAttribute("adminUser");
        Map<String,Object> msg = new HashMap<>();
        if(userService.setIsDelete(user,userAdmin,request))
            msg.put("msg", "msg029");//成功
        else
            msg.put("msg", "msg030");//失败
        return JSONArray.toJSONString(msg);
    }

    /**
     * 登录验证
     *
     * @param request
     * @return
     */
    private boolean checkLogin(HttpServletRequest request) {
        //如果没登录转发到404
        if (request.getSession().getAttribute("adminUser") == null) {
            String path = request.getContextPath();
            String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
                    + "/";
            log.info("ip:" + request.getRemoteAddr() + "\t非法请求后台接口,请求的url为: " + basePath);
            return false;
        }
        return true;
    }
}
