package blacktv.tvacg.controller.reception;

import blacktv.tvacg.database.pojo.User;
import blacktv.tvacg.database.service.CheckJWT;
import blacktv.tvacg.database.service.JwtUtil;
import blacktv.tvacg.database.service.RedisUtil;
import blacktv.tvacg.database.service.UserService;
import blacktv.tvacg.tool.Check;
import blacktv.tvacg.tool.GetEmailVerificationCode;
import blacktv.tvacg.tool.GetMd5;
import blacktv.tvacg.tool.SerializeUtils;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户相关的控制器
 */
@Log4j
@RestController()
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CheckJWT checkJWT;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${tvacg.user.loginTime}")
    private Integer loginTime;//用户登录的时效，单位天
    @Value("${tvacg.user.updateUsernameKey}")
    private String updateUsernameKey;
    @Value("${tvacg.user.updateEmeailKey}")
    private String updateEmeailKey;
    @Value("${tvacg.user.updatePasswordKey}")
    private String updatePasswordKey;
    @Value("${tvacg.user.verificationCodeLength}")
    private int verificationCodeLength;
    @Value("${tvacg.user.registerEmailTime}")
    private Integer registerEmailTime;//注册邮件有效期

    /**
     * 登录接口，可根据用户名或邮箱登录
     *
     * @param user
     * @return
     */
    @PutMapping(value = "/user/login", produces = "text/html;charset=utf-8", params = {"password"})
    public String login(User user, HttpServletRequest request) throws Exception {
        Map<String, Object> msg = new HashMap<>();
        User loginUser = null;
        //验证表单与登录
        user.setPassword(GetMd5.md5(user.getPassword()));
        if (user.getEmail() != null && user.getEmail().length() <= 30 && Check.checkEmail(user.getEmail()))
            loginUser = userService.loginByEmail(user);
        else if (user.getUsername() != null && user.getUsername().length() <= 20)
            loginUser = userService.loginByUsername(user);
        else {
            msg.put("loginMsg", "msg004");//非法請求
            log.info("IP:" + request.getRemoteAddr() + "\t非法请求登录接口,请求内容为:" + JSONArray.toJSONString(user));
            return JSONArray.toJSONString(msg);
        }

        //登陆成功生成jwt，登录失败给予提示
        if (loginUser != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("loginIp", request.getRemoteAddr());//添加登录时的ip
            String jwt = jwtUtil.createJWT(loginUser.getId().toString(), new JSONArray().toJSONString(loginUser),
                    loginTime * 24 * 60 * 60 * 1000, claims);
            msg.put("jwt", jwt);
            //不想展示给用户的信息设置为null，fastJson会自动忽略为null的属性
            loginUser.setPassword(null);
            loginUser.setIsAdmin(null);
            loginUser.setIsActivation(null);
            loginUser.setIsDlete(null);
            loginUser.setGmt_create(null);
            loginUser.setGmt_modified(null);
            msg.put("user", loginUser);
            msg.put("loginMsg", "msg006");//登录成功
            msg.put("rememberTime", loginTime);//jwt的保存时间
            log.info("IP:" + request.getRemoteAddr() + "\t登录成功,请求内容为:" + JSONArray.toJSONString(user));
        } else {
            msg.put("loginMsg", "msg005");//登录失败，账号(邮箱)或密码错误
            log.info("IP:" + request.getRemoteAddr() + "\t登录失败,请求内容为:" + JSONArray.toJSONString(user));
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * 注册接口，并发起注册验证邮件
     *
     * @param user
     * @return
     */
    @PutMapping(value = "/user/register", produces = "text/html;charset=utf-8", params = {"username", "password", "email"})
    public String register(User user) throws Exception {
        Map<String, Object> msg = new HashMap<>();
        user.setPassword(GetMd5.md5(user.getPassword()));
        //验证数据是否合法
        if (!Check.checkEmail(user.getEmail()) || user.getUsername().length() > 20 || user.getEmail().length() > 30)
            msg.put("registerMsg", "Error");//非法请求!
        else if (userService.getUserByUsername(user.getUsername()) != null)
            msg.put("registerMsg", "usernameError");//已存在该用户
        else if (userService.getUserByEmail(user.getEmail()) != null)
            msg.put("registerMsg", "emailError");//邮箱已被注册
        else
            msg.put("registerMsg", userService.addUser(user));//新增用户，成功返回true
        return JSONArray.toJSONString(msg);
    }

    /**
     * 验证邮件jwt，验证通过则激活用户
     *
     * @param jwt
     * @return
     */
    @GetMapping(value = "/user/setIsActivation", produces = "text/html;charset=utf-8", params = {"jwt"})
    public String setIsActivation(String jwt) {
        String msg = "";
        User user = userService.chekEmail(jwt);
        if (user != null) {
            user.setIsActivation(new Byte("1"));//激活账号
            msg = userService.setIsActivation(user);
        } else
            msg = "msg003";//注册激活邮件失效
        //由于本控制器是返回字符串的，所以无法重定向和转发，只好折中一下返回一个跳转的js，通过js来跳转页面,通过get字符串传递值
        return "<script type='text/javascript'>window.location.href = '/user/loginPage?msg=" + msg + "';</script>";
    }

    /**
     * 检查该用户是否存在，不存在返回true
     *
     * @param username
     * @return
     */
    @GetMapping(value = "/user/checkUsername", produces = "text/html;charset=utf-8", params = {"username"})
    public String checkUsername(String username) {
        Map<String, Object> msg = new HashMap<>();
        if (username.length() <= 20 && userService.getUserByUsername(username) == null)
            msg.put("checkUsername", true);
        else
            msg.put("checkUsername", false);
        return JSONArray.toJSONString(msg);
    }

    /**
     * 检查该邮箱是否被注册，未被注册返回true
     *
     * @param email
     * @return
     */
    @GetMapping(value = "/user/checkEmail", produces = "text/html;charset=utf-8", params = {"email"})
    public String checkEmail(String email) {
        Map<String, Object> msg = new HashMap<>();
        if (Check.checkEmail(email) && email.length() <= 30 && userService.getUserByEmail(email) == null)
            msg.put("checkEmail", true);
        else
            msg.put("checkEmail", false);
        return JSONArray.toJSONString(msg);
    }

    /**
     * 发送修改密码邮件，要修改的密码就在jwt里，由于是通过注册优先验证，所以不需要原密码
     *
     * @param user
     * @return
     */
    @PutMapping(value = "/user/setPasswordSendEmail", produces = "text/html;charset=utf-8", params = {"username", "password", "email"})
    public String setPasswordSendEmail(User user) throws Exception {
        Map<String, Object> msg = new HashMap<>();
        user.setPassword(GetMd5.md5(user.getPassword()));
        if (userService.setPasswordSendEmail(user))
            msg.put("resetPasswordMsg", "msg008");//找回密码邮件发送成功
        else
            msg.put("resetPasswordMsg", "msg007");//找回密码邮件发送失败
        return JSONArray.toJSONString(msg);
    }

    /**
     * 验证修改密码邮件，验证通过则根据jwt里的内容修改密码
     *
     * @param jwt
     * @return
     */
    @GetMapping(value = "/user/setPassword", produces = "text/html;charset=utf-8", params = {"jwt"})
    public String setPassword(String jwt) {
        String msg = "";
        User user = userService.chekEmail(jwt);
        if (user != null)
            msg = userService.setPassword(user) ? "msg010" : "msg011";//010为成功，011为失败
        else
            msg = "msg009";//找回密码邮件失效
        //由于本控制器是返回字符串的，所以无法重定向和转发，只好折中一下返回一个跳转的js，通过js来跳转页面,通过get字符串传递值
        return "<script type='text/javascript'>window.location.href = '/user/loginPage?msg=" + msg + "';</script>";
    }

    /**
     * 发起修改用户邮箱请求邮件
     *
     * @param email
     * @param jwt
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping(value = "/user/setEmailSendEmail", produces = "text/html;charset=utf-8", params = {"email", "jwt"})
    public String setEmailSendEmail(String email, String jwt, HttpServletRequest request) throws Exception {
        User user = checkJWT.checkJWT(jwt, request);
        if (user == null)
            return null;
        Map<String, Object> msg = new HashMap<>();
        String oldEmail = user.getEmail();
        user.setEmail(email);
        String code = GetEmailVerificationCode.getVerificationCode(verificationCodeLength);//获取验证码
        redisUtil.stringSet(updateEmeailKey + code + user.getId(), SerializeUtils.serialize(user), registerEmailTime * 60 * 60);
        if (userService.setEmailSendEmail(oldEmail, code))
            msg.put("msg", "msg021");//修改邮箱邮件发送成功
        else {
            msg.put("msg", "msg021");//修改邮箱邮件发送成功
            redisUtil.delete(updateEmeailKey + code + user.getId());//发送失败就删除掉key
        }

        return JSONArray.toJSONString(msg);
    }

    /**
     * 验证修改邮箱邮件，验证通过则根据jwt里的内容修改邮箱
     *
     * @param jwt
     * @return
     */
    @GetMapping(value = "/user/setEmail", produces = "text/html;charset=utf-8", params = {"jwt"})
    public String setEmail(String jwt) {
        Map<String, Object> msg = new HashMap<>();
        User user = userService.chekEmail(jwt);
        if (user != null)
            msg.put("msg", userService.setEmail(user));
        else
            msg.put("msg", "邮件已过期");
        return JSONArray.toJSONString(msg);
    }

    /**
     * 修改用户昵称接口，修改成功后重新返回用户信息和jwt
     *
     * @param username
     * @param jwt
     * @return
     */
    @PutMapping(value = "/user/update/username", produces = "text/html;charset=utf-8", params = {"username", "jwt"})
    public String updateUsername(String username, String jwt, HttpServletRequest request) throws Exception {
        User user = checkJWT.checkJWT(jwt, request);
        if (user == null)
            return null;
        Map<String, Object> msg = new HashMap<>();

        //验证当前用户是否处于修改昵称的cd中
        if (!redisUtil.exists(updateUsernameKey + user.getId())) {
            //验证该昵称是否已存在
            if (userService.getUserByUsername(username) == null) {
                String oldUsername = user.getUsername();
                user.setUsername(username);
                if (userService.setUsername(user)) {
                    //然后将该用户修改昵称进行一周的冷却
                    redisUtil.stringSet(updateUsernameKey + user.getId(), "1", 60 * 60 * 24 * 7);
                    log.info("用户id: " + user.getId() + " 修改昵称为: " + username + " 曾用名: " + oldUsername);
                    msg.put("msg", "msg018");//昵称修改成功
                    msg.put("rememberTime", this.loginTime);//jwt和user的有效时间
                    msg.put("user", user);//这个user对象是从jwt里解析到的，所以不该给用户看的信息已经在登录接口被过滤了
                    Map<String, Object> claims = new HashMap<>();
                    claims.put("loginIp", request.getRemoteAddr());//添加登录时的ip
                    String newJwt = jwtUtil.createJWT(user.getId().toString(), new JSONArray().toJSONString(user),
                            loginTime * 24 * 60 * 60 * 1000, claims);
                    msg.put("jwt", newJwt);
                } else {
                    log.info("用户id: " + user.getId() + " 修改昵称失败");
                    msg.put("msg", "msg019");//昵称修改失败
                }
            } else
                msg.put("msg", "msg017");//改昵称已存在
        } else {
            log.info("用户id:" + user.getId() + " 尝试在cd未冷却完修改昵称");
            msg.put("msg", "msg020");//昵称修改CD中
        }
        return JSONArray.toJSONString(msg);
    }

    /**
     * MD5计算
     *
     * @param string
     * @return
     */
    @GetMapping(value = "/getMd5", params = {"string"}, produces = "text/html;charset=utf-8")
    public String getMd5(String string) {
        return GetMd5.md5(string);
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        return "ip:+" + request.getRemoteAddr();
    }
}