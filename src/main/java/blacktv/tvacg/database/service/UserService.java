package blacktv.tvacg.database.service;

import blacktv.tvacg.database.mapper.UserMapper;
import blacktv.tvacg.database.pojo.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户相关的业务逻辑类
 */
@Service
@Log4j
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SendEmail sendEmail;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SendEmail email;
    @Value("${tvacg.user.registerEmailTime}")
    private Integer registerEmailTime;//注册邮件有效期，单位小时
    @Value("${tvacg.page.pageSize}")
    private Integer pageSize;

    /**
     * 发送修改密码验证邮件,
     *
     * @param user
     * @return
     */
    public boolean setPasswordSendEmail(User user) throws Exception {
        //获取用户信息之前备份一下用户提交的用户名，和要修改的密码
        String password = user.getPassword();
        String username = user.getUsername();
        user = userMapper.getUserByEmail(user.getEmail());//获取该邮箱的用户信息
        //是否存在该用户，以及提交的用户名是否正确
        if (user == null || !user.getUsername().equals(username))
            return false;
        user.setPassword(password);//修改密码
        String jwt = jwtUtil.createJWT(user.getId().toString(), new JSONArray().toJSONString(user),
                registerEmailTime * 60 * 60 * 1000, null);
        String url = this.getURL("user/setPassword?jwt=" + jwt);
        String html = "<html><head><meta charset='utf-8'><title>彩电ACG修改密码验证</title></head><body><h2>如果不是您发起的请求，请无视本邮件，本邮件有效期为" + registerEmailTime + "小时</h2><br><a href='" + url + "'>点击完成修改</a></body></html>";
        if (this.sendEmail(user.getEmail(), html, "彩电ACG修改密码验证", "彩电ACG修改密码验证"))
            return true;
        else {
            log.error("发送给" + user.getEmail() + "的修改密码验证邮件发送失败");//回滚事务
            return false;
        }
    }

    /**
     * 修改指定用户密码
     *
     * @param user
     * @return
     */
    public boolean setPassword(User user) {
        return userMapper.setPassword(user) > 0;
    }

    /**
     * 发送修改邮箱的验证邮件
     *
     * @param oldEmail
     * @param code
     * @return
     * @throws Exception
     */
    public boolean setEmailSendEmail(String oldEmail, String code) {
        String html = "<html><head><meta charset='utf-8'><title>彩电ACG修改邮箱验证</title></head><body><h2>如果不是您发起的请求，请无视本邮件，本邮件有效期为" + registerEmailTime + "小时</h2><h3>验证码：" + code + "</h3></body></html>";
        if (this.sendEmail(oldEmail, html, "彩电ACG修改邮箱验证", "彩电ACG修改邮箱验证"))//要发往旧邮箱，不能发给待修改邮箱
            return true;
        else {
            log.error("发送给" + oldEmail + "的修改邮箱验证邮件发送失败");
            return false;
        }
    }

    /**
     * 修改指定用户的邮箱
     *
     * @param user
     * @return
     */
    public boolean setEmail(User user) {
        return userMapper.setEmail(user) > 0;
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     * @throws Exception
     */
    @Transactional
    public boolean addUser(User user) throws Exception {
        if (userMapper.addUser(user) < 1)
            return false;//新增失败
        user = userMapper.getUserByUsername(user.getUsername());//主要是为了查询出id
        String jwt = jwtUtil.createJWT(user.getId().toString(), new JSONArray().toJSONString(user),
                registerEmailTime * 60 * 60 * 1000, null);
        String url = this.getURL("user/setIsActivation?jwt=" + jwt);
        String html = "<html><head><meta charset='utf-8'><title>彩电ACG注册验证</title></head><body><h2>如果不是您发起的注册，请无视本邮件，本邮件有效期为" + registerEmailTime + "小时</h2><br><a href='" + url + "'>点击完成注册</a></body></html>";
        if (this.sendEmail(user.getEmail(), html, "彩电ACG注册验证", "彩电ACG注册验证"))
            return true;
        else {
            log.error("发送给" + user.getEmail() + "的注册验证邮件发送失败");//回滚事务
            throw new Exception("邮件发送失败");
        }
    }

    /**
     * 发送注册验证邮件
     *
     * @param to    收件人
     * @param html  html邮件内容
     * @param title 邮件标题
     * @param msg   邮件消息
     * @return
     */
    public Boolean sendEmail(String to, String html, String title, String msg) {
        if (email.sendHtmlAndMessageEmail(to, title, msg, html)) {
            log.info("发往" + to + "的邮件成功");
            return true;
        }
        log.info("发往" + to + "的邮件失败");
        return false;
    }

    /**
     * 传入站内链接，获取完整的url
     *
     * @param link
     * @return
     */
    private String getURL(String link) {
        ServletRequestAttributes app = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = app.getRequest();
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        return basePath + link;
    }

    /**
     * 检查验证邮件
     *
     * @param jwt
     * @return
     */
    public User chekEmail(String jwt) {
        User user = null;
        try {
            Claims c = jwtUtil.parseJWT(jwt);// 解析JWT
            user = JSON.parseObject(c.getSubject(), User.class);// 将json转换为实体类
            log.info("解析完毕发送给\t" + user.getEmail() + "\t的验证邮件");
        } catch (Exception e) {
            log.info("解析已过期的验证邮件");
        }
        return user;
    }

    /**
     * 修改用户名
     *
     * @param user
     */
    public boolean setUsername(User user) {
        return userMapper.setUsername(user) > 0;
    }

    /**
     * 设置账号激活状态
     *
     * @param user
     * @return
     */
    public String setIsActivation(User user) {
        if (userMapper.setIsActivation(user) > 0) {
            log.info("userId:" + user.getId() + ",email:" + user.getEmail() + "激活成功");
            return "msg001";//注册成功
        }
        log.info("userId:" + user.getId() + ",email:" + user.getEmail() + "激活失败");
        return "msg002";
    }

    /**
     * 设置用户删除状态
     *
     * @param user      要设置删除状态的用户
     * @param userAdmin 操作的管理员用户
     * @param request
     * @return
     */
    public boolean setIsDelete(User user, User userAdmin, HttpServletRequest request) {
        Byte isDelete = user.getIsDlete();
        user = userMapper.getUserById(user.getId());
        if (user == null) {
            log.info("ip:" + request.getRemoteAddr() + " 使用id为:" + userAdmin.getId() + " 试图修改一个不存在的用户的删除状态");
            return false;
        }
        //不得修改同级或更高级用户的删除状态
        if (userAdmin.getIsAdmin() - user.getIsAdmin() <= 0) {
            log.info("ip:" + request.getRemoteAddr() + " 使用id为:" + userAdmin.getId() + " 试图修改不在他权限管理内的用户删除状态，被修改的用户id:" + user.getId());
            return false;
        }
        user.setIsDlete(isDelete);
        if (userMapper.setIsDelete(user) > 0) {
            log.info("ip:" + request.getRemoteAddr() + " 使用id为:" + userAdmin.getId() + " 修改用户id:" + user.getId() + " 的删除状态成功，修改后的状态为:" + isDelete);
            return true;
        }
        log.info("ip:" + request.getRemoteAddr() + " 使用id为:" + userAdmin.getId() + " 修改用户id:" + user.getId() + " 的删除状态失败，尝试修改删除状态为:" + isDelete);
        return false;
    }

    /**
     * 根据用户名获取用户，不存在返回null
     *
     * @param username
     * @return
     */
    public User getUserByUsername(String username) {
        return userMapper.getUserByUsername(username);
    }

    /**
     * 根据邮箱获取用户，不存在返回null
     *
     * @param email
     * @return
     */
    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    /**
     * 根据邮箱登录，登录成功返回用户对象
     *
     * @param user
     * @return
     */
    public User loginByEmail(User user) {
        return userMapper.loginByEmail(user);
    }

    /**
     * 根据用户名登录
     *
     * @param user
     * @return
     */
    public User loginByUsername(User user) {
        return userMapper.loginByUsername(user);
    }


    /**
     * 分页查询用户
     *
     * @param pageNun  当前页面
     * @param pageSize 页面容量
     * @param isAdmin  传入具体的值就是根据管理员等级查询用户，传入null就查询全部
     * @return
     */
    public PageInfo<User> getUserPage(int pageNun, Integer pageSize, Byte isAdmin) {
        //如果传入的pageSize为空或小于等于0则用默认的pageSize
        if (pageSize == null || pageSize <= 0)
            PageHelper.startPage(pageNun, pageSize);
        List<User> userList = null;
        if (isAdmin != null)
            userList = userMapper.getUserByIsAdmin(isAdmin);//普通管理员只能获取用户数据
        else
            userList = userMapper.getAllUser();//超级管理员可以管理全部人
        PageInfo<User> studentPageInfo = new PageInfo<>(userList);
        return studentPageInfo;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }
}
