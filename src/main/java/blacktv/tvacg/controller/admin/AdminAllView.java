package blacktv.tvacg.controller.admin;

import blacktv.tvacg.database.pojo.User;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 负责后台的全部视图
 */
@Controller
@RequestMapping(value = "/blacktv/acg/admin")
@Log4j
public class AdminAllView {
    private String error404Url = "/error/404";

    /**
     * 登录页面
     *
     * @return
     */
    @GetMapping(value = "/loginPage")
    public String loginPage() {
        return "admin/login";
    }

    /**
     * 后台首页
     *
     * @return
     */
    @GetMapping(value = "/index")
    public String index(HttpServletRequest request) {
        if (!this.checkLogin(request)) {
            log.info("IP:" + request.getRemoteAddr() + "\t非法请求后台首页");
            return "redirect:" + error404Url;
        }
        return "admin/index";
    }

    /**
     * 根据权限获取后台导航栏的列表
     * 目前该方法只有假数据
     *
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/get/page/msg", produces = "text/html;charset=utf-8")
    public String getPageMsg(HttpSession session, HttpServletRequest request) {
        if (!this.checkLogin(request)) {
            log.info("IP:" + request.getRemoteAddr() + "\t非法请求后台导航栏列表");
            return "redirect:" + error404Url;
        }

        User admiUser = (User) session.getAttribute("adminUser");
        Map<String, Object> msg = new HashMap<>();
        //用户相关
        List<AdminPage> user = new ArrayList<>();
        //资源相关
        List<AdminPage> resources = new ArrayList<>();
        user.add(new AdminPage("管理用户", "/blacktv/acg/admin/user/adminPage"));
        resources.add(new AdminPage("审核资源", "/blacktv/acg/admin/resources/examinePage"));
        resources.add(new AdminPage("管理资源", "/blacktv/acg/admin/resources/adminPage"));

        //超级管理员才有权限
        if (admiUser.getIsAdmin() >= 2) {
            user.add(new AdminPage("添加管理员", "https://cn.bing.com/"));
            user.add(new AdminPage("管理管理员", "https://cn.bing.com/"));
        }

        AdminPage userAdminPage = new AdminPage("用户", user);
        AdminPage resourcesAdminPage = new AdminPage("资源", resources);

        msg.put("user", userAdminPage);
        msg.put("resources", resourcesAdminPage);
        return JSONArray.toJSONString(msg);
    }

    /**
     * 资源审核页面
     *
     * @return
     */
    @GetMapping(value = "/resources/examinePage")
    public String resourcesExaminePage(HttpServletRequest request) {
        if (this.checkLogin(request))
            return "admin/resourcesExamine";
        return "redirect:" + error404Url;
    }

    /**
     * 资源管理界面
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/resources/adminPage")
    public String resourcesAdmin(HttpServletRequest request) {
        if (this.checkLogin(request))
            return "admin/resourcesAdmin";
        return "redirect:" + error404Url;
    }

    /**
     * 用户管理界面
     * @param request
     * @return
     */
    @GetMapping(value = "/user/adminPage")
    public String userAdminPage(HttpServletRequest request){
        if (this.checkLogin(request))
            return "admin/userAdmin";
        return "redirect:" + error404Url;
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

    /**
     * getPageMsg方法用的内部类
     */
    public class AdminPage {
        public String pageName;
        //url和pages必须有一个为空
        public String url;
        public List<AdminPage> pages;

        public AdminPage(String pageName, String url) {
            this.pageName = pageName;
            this.url = url;
            pages = null;
        }

        public AdminPage(String pageName, List<AdminPage> pages) {
            this.pageName = pageName;
            this.pages = pages;
            url = null;
        }
    }
}
