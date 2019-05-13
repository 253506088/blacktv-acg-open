<!DOCTYPE html>
<html>
<head>
    <!-- Basic Page Needs -->
    <meta http-equiv="content-type" content="text/html; charset=UTF-8"/>
    <title>彩电& ACG</title>

    <!-- Mobile Specific Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <!-- Bootstrap  -->
    <link rel="stylesheet" type="text/css" href="/tvacg/stylesheets/bootstrap.css">

    <!-- Theme Style -->
    <link rel="stylesheet" type="text/css" href="/tvacg/stylesheets/style.css">
    <link rel="stylesheet" type="text/css" href="/tvacg/stylesheets/responsive.css">
    <link rel="stylesheet" type="text/css" href="/tvacg/stylesheets/colors/color1.css" id="colors">

    <link rel="shortcut icon" href="/tvacg/images/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="/tvacg/images/favicon-apple.png">
    <link rel="stylesheet" href="/tvacg/lib/layui/css/layui.css" media="all"/>
    <link rel="stylesheet" href="/tvacg/css/login.css"/>
    <link rel="stylesheet" href="/tvacg/css/tooltips.css"/>


    <script type="text/javascript" src="/tvacg/js/jquery.min.js"></script>
    <script type="text/javascript" src="/tvacg/js/jquery.pure.tooltips.js"></script>
    <script type="text/javascript" src="/tvacg/lib/layui/layui.js"></script>
    <style>
        body {
            margin: 0;
            padding: 0;
            overflow: hidden;
            background: #2d9b95;
            background: -moz-radial-gradient(center, ellipse cover, #2d9b95 0%, #0e1329 100%);
            background: -webkit-radial-gradient(center, ellipse cover, #2d9b95 0%, #0e1329 100%);
            background: -o-radial-gradient(center, ellipse cover, #2d9b95 0%, #0e1329 100%);
            background: -ms-radial-gradient(center, ellipse cover, #2d9b95 0%, #0e1329 100%);
            background: radial-gradient(ellipse at center, #2d9b95 0%, #0e1329 100%);
            filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#2d9b95', endColorstr='#0e1329', GradientType=1);
            background: -webkit-gradient(radial, center center, 0px, center center, 100%, color-stop(0%, #2d9b95), color-stop(100%, #0e1329));
        }

        .box {
            background: #000000;
            position: absolute;
            margin-left: -14%;
            z-index: 3;
            opacity: 0.6;
            left: 50%;
        }

        .box-login {
            width: 450px;
            height: 295px;
            margin-top: -10%;
            top: 50%;
        }

        .box-register {
            width: 450px;
            height: 385px;
            margin-top: -8%;
            top: 46%;
        }

        .box-reset {
            width: 450px;
            height: 385px;
            margin-top: -8%;
            top: 46%;
        }

        #register {
            display: none;
        }

        #reset {
            display: none;
        }
    </style>
</head>
<style>
    body {
        margin: 0;
        padding: 0;
        overflow: hidden;
        background: #2d9b95;
        background: -moz-radial-gradient(center, ellipse cover, #2d9b95 0%, #0e1329 100%);
        background: -webkit-radial-gradient(center, ellipse cover, #2d9b95 0%, #0e1329 100%);
        background: -o-radial-gradient(center, ellipse cover, #2d9b95 0%, #0e1329 100%);
        background: -ms-radial-gradient(center, ellipse cover, #2d9b95 0%, #0e1329 100%);
        background: radial-gradient(ellipse at center, #2d9b95 0%, #0e1329 100%);
        filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#2d9b95', endColorstr='#0e1329', GradientType=1);
        background: -webkit-gradient(radial, center center, 0px, center center, 100%, color-stop(0%, #2d9b95), color-stop(100%, #0e1329));
    }

    .box {
        background: #000000;
        position: absolute;
        margin-left: -14%;
        z-index: 3;
        opacity: 0.6;
        left: 50%;
    }

    .box-login {
        width: 450px;
        height: 310px;
        margin-top: -10%;
        top: 50%;
    }

    .box-register {
        width: 450px;
        height: 385px;
        margin-top: -8%;
        top: 46%;
    }

    .box-reset {
        width: 450px;
        height: 385px;
        margin-top: -8%;
        top: 46%;
    }

    #register {
        display: none;
    }

    #reset {
        display: none;
    }
</style>

<body class="bg-body2">

<div class="boxed">
    <div class="preloader">
                <span class="loader">
                    <span class="loader-inner"></span>
                </span>
    </div>


<#--引入头部导航公共文件-->
            <#include "public/headNavigation.ftl">

    <!-- /.flat-title-page -->
    <!-- /.content-wrap -->


    <section class="flat-free-month parallax parallax9 style2">

    </section>

    <footer id="footer">
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <div class="widget-about">
                        <div id="logo-ft">
                            <a href="/index"><img src="/tvacg/images/logo/logo-i.png" alt="返回首页"
                                                  width="157" height="29" data-retina="/tvacg/images/logo/logo-i.png"
                                                  data-width="157" data-height="29"></a>
                        </div>
                        <p class="description"><br/><br/><br/><br/>彩电ACG只为了学习经验而搭建</p>
                    </div>
                    <div class="col-md-4">
                        <div class="widget-link widget-ft">
                        </div>
                    </div>
                    <div class="col-md-4">
                    </div>
                </div>
            </div>
    </footer>

<#--引入网页足部公共文件-->
    <#include "public/footer.ftl">

    <a id="scroll-top"></a>
</div>

<script src="/tvacg/javascript/jquery.min.js"></script>
<script src="/tvacg/javascript/parallax.js"></script>
<script src="/tvacg/javascript/owl.carousel.min.js"></script>
<script src="/tvacg/javascript/jquery-fancybox.js"></script>
<script src="/tvacg/javascript/imagesloaded.min.js"></script>
<script src="/tvacg/javascript/jquery-isotope.js"></script>
<script src="/tvacg/javascript/waypoints.min.js"></script>
<script src="/tvacg/javascript/jquery.easing.js"></script>
<script src="/tvacg/javascript/jquery.cookie.js"></script>
<script src="/tvacg/javascript/smoothscroll.js"></script>
<script src="/tvacg/javascript/main.js"></script>
<script type="text/javascript" src="/tvacg/lib/layui/layui.js"></script>

</body>
<body>


<div class="beg-login-box box box-login layui-anim-up" id="login">
    <header>
        <h1 style="color:#FFFFFF">欢迎登录</h1>
    </header>
    <div class="beg-login-main">

        <form action="" class="layui-form" method="post"><input name="__RequestVerificationToken" type="hidden"
                                                                value="fkfh8D89BFqTdrE2iiSdG_L781RSRtdWOH411poVUWhxzA5MzI8es07g6KPYQh9Log-xf84pIR2RIAEkOokZL3Ee3UKmX0Jc8bW8jOdhqo81"/>
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <em class="layui-icon"></em>
                </label>
                <input id="username" type="text" name="userName" lay-verify="userName" autocomplete="off"
                       placeholder="请输入用户名或邮箱" class="layui-input">
            </div>

            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe642;</i>
                </label>
                <input id="password" type="password" name="password" lay-verify="password" autocomplete="off"
                       placeholder="请输入密码" class="layui-input">
            </div>

            <div class="layui-form-item">
                <div class="beg-pull-left beg-login-remember" style="color:#FFFFFF;margin-top: 4%;">
                    <label> 记住帐号？ </label>
                    <input type="checkbox" name="close" id="rememberUser" lay-skin="switch" lay-text="ON|OFF" checked>

                </div>

                <div class="beg-pull-right" style="margin-top: 4%;">
                    <a class="btn pull-left btn-link text-muted" onClick="goto_forget()"
                       style="color:#FFFFFF;cursor:pointer;">忘记密码?</a>
                </div>
                <div class="beg-clear"></div>
            </div>


            <div class="layui-form-item">
                <div class="beg-pull-left beg-login-remember" style="color:#FFFFFF;margin-top: -2%;">
                    <button class="layui-btn layui-btn-radius layui-btn-primary"
                            onclick="goto_register();return false;">注册
                </div>
                <div class="beg-pull-right">
                    <button class="layui-btn layui-btn-radius" style="margin-top: 4%;" id="loginButton"
                            onClick="return false;">
                        登录
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<div class="beg-login-box box box-register" id="register">

    <header>
        <h1 style="color:#FFFFFF">欢迎注册</h1>
    </header>
    <div class="beg-login-main">
        <form action="" class="layui-form" method="post"><input name="__RequestVerificationToken" type="hidden"
                                                                value="fkfh8D89BFqTdrE2iiSdG_L781RSRtdWOH411poVUWhxzA5MzI8es07g6KPYQh9Log-xf84pIR2RIAEkOokZL3Ee3UKmX0Jc8bW8jOdhqo81"/>
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe612;</i>
                </label>
                <input id="username_register" type="text" name="userName" lay-verify="userName" autocomplete="off"
                       placeholder="请输入用户名，支持中文" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe642;</i>
                </label>
                <input id="password_register" type="password" name="password" lay-verify="password" autocomplete="off"
                       placeholder="请输入密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe642;</i>
                </label>
                <input id="determine_password_register" type="password" name="password" lay-verify="password"
                       autocomplete="off" placeholder="确认密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe6b2;</i>
                </label>
                <input id="email_register" type="text" name="regcode" lay-verify="regcode" autocomplete="off"
                       placeholder="请输入邮箱" class="layui-input">
            </div>
            <div class="layui-form-item">
                <div class="beg-pull-left beg-login-remember" style="color:#FFFFFF;margin-top: 6%;">
                    <button class="layui-btn" onClick="return false;" id="registerButton">注册
                </div>

                <div class="beg-pull-right">
                    <button class="layui-btn layui-btn layui-btn-primary" style="margin-top: 18%;"
                            onClick="goto_login();return false;">返回登录
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<div class="beg-login-box box box-reset" id="reset">
    <header>
        <h1 style="color:#FFFFFF">重置密码</h1>
    </header>
    <div class="beg-login-main">
        <form action="" class="layui-form" method="post"><input name="__RequestVerificationToken" type="hidden"
                                                                value="fkfh8D89BFqTdrE2iiSdG_L781RSRtdWOH411poVUWhxzA5MzI8es07g6KPYQh9Log-xf84pIR2RIAEkOokZL3Ee3UKmX0Jc8bW8jOdhqo81"/>
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe612;</i>
                </label>
                <input id="username_reset" type="text" name="userName" lay-verify="userName" autocomplete="off"
                       placeholder="请输入用户名" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe6b2;</i>
                </label>
                <input id="email_reset" type="text" name="regcode" lay-verify="regcode" autocomplete="off"
                       placeholder="请输入邮箱" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe642;</i>
                </label>
                <input id="password_reset" type="password" name="password" lay-verify="password" autocomplete="off"
                       placeholder="请输入重置密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="beg-login-icon">
                    <i class="layui-icon">&#xe642;</i>
                </label>
                <input id="determine_password_reset" type="password" name="password" lay-verify="password"
                       autocomplete="off" placeholder="确认密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <div class="beg-pull-left beg-login-remember" style="color:#FFFFFF;margin-top: 6%;">
                    <button class="layui-btn" onclick="return false;" id="resetButton">重置密码
                </div>

                <div class="beg-pull-right">
                    <button class="layui-btn layui-btn layui-btn-primary" style="margin-top: 18%;"
                            onClick="goto_login();return false;">返回登录
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
    var signUpUsernameError = false;//账号是否合理
    var signUpEmail2Error = false;//邮箱是否合理
    var signUpPasswordError = false;//密码长度是否符合要求
    var signUpPassword2Error = false;//两次密码是否相同
    $(document).ready(function () {
        showMsg();//展示给用户后台返回的数据

        readCookie();//读取cookie

        //检查登录用户名或邮箱
        $("#username").blur(function () {
            loginCheckUsernameOrEmail($("#username").val());
        });

        //检查登录密码
        $("#password").blur(function () {
            loginCheckPassword();
        });

        //发起登录
        $("#loginButton").click(function () {
            tvACGLogin();
        });

        //检验注册用户名
        $("#username_register").blur(function () {
            signUpCheckUsername();
        });

        //检验注册时密码长度
        $("#password_register").blur(function () {
            signUpPassword($("#password_register").val());
        });

        //检验注册时两次密码是否相同
        $("#determine_password_register").blur(function () {
            signUpPassword2($("#password_register").val(), $("#determine_password_register").val());
        });

        //检验注册邮箱
        $("#email_register").blur(function () {
            signUpCheckEmail();
        });

        //发起注册
        $("#registerButton").click(function () {
            tvACGRegister();
        });

        //找回密码检查用户名
        $("#username_reset").blur(function () {
            loginCheckUsernameOrEmail($("#username_reset").val());
        });

        //找回密码检查邮箱
        $("#email_reset").blur(function () {
            if (isEmail($("#email_reset").val()))
                loginCheckUsernameOrEmail($("#email_reset").val());
            else {
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.msg('请输入正确的邮箱');
                });
            }
        });

        //找回密码检查密码
        $("#password_reset").blur(function () {
            signUpPassword($("#password_reset").val());
        });

        //找回密码检查两次密码是否相同
        $("#determine_password_reset").blur(function () {
            signUpPassword2($("#password_reset").val(), $("#determine_password_reset").val());
        });

        //发起找回密码请求
        $("#resetButton").click(function () {
            tvACGResetPassword();
        });
    });

    //检查用户名或邮箱
    function loginCheckUsernameOrEmail(loginUsernameOrEmeail) {
        var lengthMax = 20;//默认账号长度
        if (isEmail(loginUsernameOrEmeail)) {
            lengthMax = 30;//切换成邮箱长度
        }
        //检查长度是否符合要求
        if (loginUsernameOrEmeail.length < 1 || loginUsernameOrEmeail.length > lengthMax) {
            layui.use('layer', function () {
                var layer = layui.layer;
                if (lengthMax == 30)
                    layer.msg('邮箱长度要大于6小于30');
                else
                    layer.msg('账号长度要大于1小于20');
            });
            return false;
        }
        return true;
    }

    //检查登录密码
    function loginCheckPassword(loginPassword) {
        var loginPassword = $("#password").val();
        if (loginPassword.length < 6 || loginPassword.length > 20) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('密码长度要大于6小于20');
            });
            return false;
        }
        return true;
    }

    var oldLoginUsernameOrEmeail = "";
    var oldLoginPassword = "";

    //发起登录
    function tvACGLogin() {
        //先重新验证一遍
        if (loginCheckPassword() && loginCheckUsernameOrEmail($("#username").val()) && oldLoginUsernameOrEmeail != $("#username").val() && oldLoginPassword != $("#password").val()) {
            var data = "password=" + $("#password").val();
            //判断是邮箱登录还是账号登录
            if (isEmail($("#username").val()))
                data = data + "&email=" + $("#username").val();
            else
                data = data + "&username=" + $("#username").val();

            $.ajax({
                "url": "/user/login",
                "type": "put",
                "data": data,
                "dataType": "json",
                "success": function (result) {
                    switch (result.loginMsg) {
                        case "msg004":
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.msg('非法请求');
                            });
                            break;
                        case "msg006":
                            // 添加两个会话cookie,随着浏览器关闭而消逝
                            var rememberUser = $("#rememberUser").is(':checked');//获取是否记住密码
                            if (rememberUser) {
                                $.cookie("username", $("#username").val(), {
                                    expires: result.rememberTime,
                                    path: '/user/loginPage'
                                });
                                $.cookie("password", $("#password").val(), {
                                    expires: result.rememberTime,
                                    path: '/user/loginPage'
                                });
                            }

                            //将json对象序列化为字符串再存储到cookie中
                            var user_String = JSON.stringify(result.user);
                            $.cookie("jwt", result.jwt, {expires: result.rememberTime, path: '/'});
                            $.cookie("user", user_String, {expires: result.rememberTime, path: '/'});

                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.msg('登录成功,即将跳转到首页');
                                setTimeout(function () {
                                    window.location.href = "/index";
                                }, 1500);// 1.5秒后跳转到首页
                            });
                            break;
                        case "msg005":
                            //记录下登录失败的账号和密码，下次登录时不做修改就不发起请求
                            oldLoginUsernameOrEmeail = $("#username").val();
                            oldLoginPassword = $("#password").val();
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.msg('登录失败，账号(邮箱)或密码错误');
                            });
                            break;
                        default:
                            console.log("未知信息:" + result.loginMsg);
                    }
                },
                "error": function () {
                    console.log("登录接口异常");
                }
            });
        }
    }

    var oldNikeName = "";

    //检验注册用户名
    function signUpCheckUsername() {
        var signUpUsername = $("#username_register").val();
        //检查长度
        if (signUpUsername.length < 1 || signUpUsername.length > 20) {
            signUpUsernameError = false;
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('账号长度要大于1小于20');
            });
            return;
        }

        //检查该用户名是否已存在
        // 为了防止同一个昵称多次访问数据库检验上是否已存在
        if (oldNikeName != signUpUsername) {
            $.get("/user/checkUsername", "username=" + signUpUsername, function (result) {
                if (result.checkUsername == true) {
                    signUpUsernameError = true;
                } else {
                    signUpUsernameError = false;
                    layui.use('layer', function () {
                        var layer = layui.layer;
                        layer.msg('该账号已被注册');
                    });
                }
            }, "json");
            oldNikeName = signUpUsername;
        }
    }

    var oldEmail = "";

    //检验注册邮箱
    function signUpCheckEmail() {
        var email = $("#email_register").val();
        if (!isEmail(email)) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('请输入正确的邮箱');
            });
            signUpEmail2Error = false;
            return
        }
        if (email.length < 6 || email.length > 30) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('邮箱长度要大于6小于30');
            });
            signUpEmail2Error = false;
            return;
        }
        // 防止同一个邮箱多次请求数据库检验是否已存在
        if (email != oldEmail) {
            $.get("/user/checkEmail", "email=" + email, function (result) {
                if (result.checkEmail == true) {
                    signUpEmail2Error = true;
                } else {
                    signUpEmail2Error = false;
                    layui.use('layer', function () {
                        var layer = layui.layer;
                        layer.msg('该邮箱已被注册');
                    });
                }
            }, "json");
            oldEmail = email;
        }
    }

    // 验证密码长度是否正确
    function signUpPassword(signUpPassword) {
        if (signUpPassword.length < 6 || signUpPassword.length > 20) {
            signUpPasswordError = false;
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('密码长度要大于6小于20');
            });
        } else {
            signUpPasswordError = true;
        }
    }

    // 验证两次密码是否相同
    function signUpPassword2(password1, password2) {
        if (password1 != password2) {
            signUpPassword2Error = false;
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('两次密码不相同');
            });
        } else {
            signUpPassword2Error = true;
        }
    }

    //发起注册
    function tvACGRegister() {
        //先重新验证一遍
        signUpCheckUsername();
        signUpPassword($("#password_register").val());
        signUpPassword2($("#password_register").val(), $("#determine_password_register").val());
        signUpCheckEmail();
        //没有问题了就发起注册
        if (signUpUsernameError && signUpEmail2Error && signUpPasswordError && signUpPassword2Error) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('正在注册，由于服务器性能有限，发送验证邮件耗时可能较长');
            });
            var data = "username=" + $("#username_register").val() + "&password=" + $("#password_register").val() + "&email=" + $("#email_register").val();

            $.ajax({
                "url": "/user/register",
                "type": "put",
                "data": data,
                "dataType": "json",
                "success": function (result) {
                    switch (result.registerMsg) {
                        case "Error":
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.msg('非法请求');
                            });
                            break;
                        case "usernameError":
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.msg('该用户名已被注册');
                            });
                            break;
                        case "emailError":
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.msg('该邮箱已被注册');
                            });
                            break;
                        case true:
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.msg('已发送验证邮件到指定邮箱');
                            });
                            break;
                        case false:
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.msg('验证邮件发送失败');
                            });
                            break;
                        default:
                            console.log("未知信息:" + result.registerMsg);
                    }
                },
                "error": function () {
                    console.log("注册接口异常");
                }
            });
        }
    }

    //发起找回密码请求
    function tvACGResetPassword() {
        //先重新验证一遍
        if (!isEmail($("#email_reset").val())) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('请输入正确的邮箱');
            });
            return;
        }
        signUpPassword($("#password_reset").val());
        signUpPassword2($("#password_reset").val(), $("#determine_password_reset").val());
        if (loginCheckUsernameOrEmail($("#username_reset").val()) && loginCheckUsernameOrEmail($("#email_reset").val()) && signUpPasswordError && signUpPassword2Error) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('正在找回密码，由于服务器性能有限，发送验证邮件耗时可能较长');
            });
            var data = "username=" + $("#username_reset").val() + "&password=" + $("#password_reset").val() + "&email=" + $("#email_reset").val();

            $.ajax({
                "url": "/user/setPasswordSendEmail",
                "type": "put",
                "data": data,
                "dataType": "json",
                "success": function (result) {
                    switch (result.resetPasswordMsg) {
                        case "Error":
                            layui.use('msg007', function () {
                                var layer = layui.layer;
                                layer.msg('找回密码邮件发送失败');
                            });
                            break;
                        case "msg008":
                            layui.use('layer', function () {
                                var layer = layui.layer;
                                layer.msg('找回密码邮件发送成功');
                            });
                            break;
                        default:
                            console.log("未知信息:" + result.resetPasswordMsg);
                    }
                },
                "error": function () {
                    console.log("找回密码接口异常");
                }
            });
        }
    }

    // 验证是否是邮箱,是邮箱返回true,反之返回false
    function isEmail(email) {
        var reg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
        isok = reg.test(email);
        if (!isok)
            return false;
        return true;
    }

    //展示给用户后台返回的信息
    function showMsg() {
        layui.use('layer', function () {
            var layer = layui.layer;
            var msg = UrlSearch("msg");//url里获取msg的值
            //根据编码展示信息，编码最小
            var alertMsg = "";
            switch (msg) {
                case "msg001":
                    alertMsg = "注册成功";
                    break;
                case "msg002":
                    alertMsg = "注册失败";
                    break;
                case "msg003":
                    alertMsg = "注册激活邮件失效";
                    break;
                case "msg009":
                    alertMsg = "找回密码邮件失效";
                    break;
                case "msg010":
                    alertMsg = "重置密码成功";
                    break;
                case "msg011":
                    alertMsg = "重置密码失败";
                    break;
            }
            //有提示信息才展示，没有就不弹窗了
            if (alertMsg.length > 0)
                layer.msg(alertMsg);
        });
    }

    //根绝key读取url中的参数(只能读取get协议字符串)
    function UrlSearch(key) {
        var name, value;
        var str = location.href; //取得整个地址栏
        var num = str.indexOf("?")
        str = str.substr(num + 1); //取得所有参数   stringvar.substr(start [, length ]

        var arr = str.split("&"); //根据&分割字符串为数组，将各个参数的键值对放到数组里
        for (var i = 0; i < arr.length; i++) {
            buffer = arr[i].split("=");//根据=分割键值对字符串到数组中，下标为0的是key，下标为2的是value
            if (buffer.length == 2 && buffer[0] == key)
                return buffer[1];
        }
    }

    //读取cookie里的内容
    function readCookie() {
        var username = $.cookie('username');
        var password = $.cookie('password');
        //如果账号和密码符合规则就添加到登录的input里
        if (username != null && password != null && username.length > 1 && username.length < 20 && password.length > 6 && password.length < 20) {
            $("#username").val(username);
            $("#password").val(password);
        }
    }
</script>
<#--引入根据是否登录来决定头部导航栏的显示的公共文件-->
<#include "public/SetHeadNavigationDom.ftl">
<#--自带的js不要动-->
<script type="text/javascript">
    window.requestAnimFrame = (function () {
        return window.requestAnimationFrame ||
                window.webkitRequestAnimationFrame ||
                window.mozRequestAnimationFrame ||
                window.oRequestAnimationFrame ||
                window.msRequestAnimationFrame ||
                function (callback) {
                    window.setTimeout(callback, 1000 / 60);
                };
    })();

    var canvas = document.getElementsByTagName("canvas")[0];
    var ctx = canvas.getContext("2d");
    var w = $(document).width();
    var h = $(document).height();
    canvas.width = w;
    canvas.height = h;

    var mols = [];

    function init() {
        for (var i = 0; i < 18; i++) {
            var mol = new generate_mol("C8H10N4O2");
            mols.push(mol);
            var mol = new generate_mol("C6H6O");
            mols.push(mol);
            var mol = new generate_mol("C6H6");
            mols.push(mol);
        }
    }

    function draw() {
        canvas.width = canvas.width;
        for (var i = 0; i < mols.length; i++) {
            var m = mols[i];
            m.x += m.vx;
            if (m.x >= w - 100 || m.x <= 0) {
                m.vx = -m.vx;
            }
            m.y += m.vy;
            if (m.y >= h - 100 || m.y <= 0) {
                m.vy = -m.vy;
            }

            m.r += 0.005;
            m.draw();
        }
    }

    function generate_mol(mol) {
        this.x = Math.random() * w;
        this.y = Math.random() * h;
        this.vx = Math.random() * -2;
        this.vy = Math.random() * 2;
        this.vr = 0.1;
        this.r = Math.random() * Math.PI;
        this.draw = function () {
            if (mol == "C6H6O") {
                //Phenol
                ctx.save();
                ctx.translate(this.x + 20, this.y + 80);
                ctx.rotate(this.r);
                ctx.translate(-this.x + 20, -this.y - 80);
                ctx.beginPath();
                ctx.moveTo(this.x, this.y + 5);
                ctx.lineTo(this.x, this.y + 30);
                ctx.lineTo(this.x - 26, this.y + 45);
                ctx.lineTo(this.x - 26, this.y + 75);
                ctx.lineTo(this.x, this.y + 90);
                ctx.lineTo(this.x + 26, this.y + 75);
                ctx.lineTo(this.x + 26, this.y + 45);
                ctx.lineTo(this.x, this.y + 30);
                ctx.moveTo(this.x - 20, this.y + 47);
                ctx.lineTo(this.x - 20, this.y + 73);
                ctx.moveTo(this.x, this.y + 83);
                ctx.lineTo(this.x + 22, this.y + 70);
                ctx.moveTo(this.x, this.y + 36);
                ctx.lineTo(this.x + 22, this.y + 49);
                ctx.strokeStyle = "rgba(255,255,255,0.2)";
                ctx.lineWidth = 3;
                ctx.stroke();
                ctx.fillStyle = "rgba(255,255,255,0.2)";
                ctx.font = "15px Arial";
                ctx.fillText("OH", this.x - 5, this.y);
                ctx.closePath();
                ctx.restore();
            }
            else if (mol == "C8H10N4O2") {
                //Caffeine
                ctx.save();
                ctx.translate(this.x + 20, this.y + 80);
                ctx.rotate(this.r);
                ctx.translate(-this.x + 20, -this.y - 80);
                ctx.beginPath();
                ctx.moveTo(this.x, this.y + 5);
                ctx.lineTo(this.x, this.y + 22);
                ctx.moveTo(this.x - 9, this.y + 35);
                ctx.lineTo(this.x - 26, this.y + 45);
                ctx.lineTo(this.x - 26, this.y + 75);
                ctx.lineTo(this.x, this.y + 90);
                ctx.lineTo(this.x + 18, this.y + 80);
                ctx.moveTo(this.x + 26, this.y + 68);
                ctx.lineTo(this.x + 26, this.y + 45);
                ctx.lineTo(this.x + 9, this.y + 35);
                ctx.moveTo(this.x - 20, this.y + 47);
                ctx.lineTo(this.x - 20, this.y + 73);
                ctx.moveTo(this.x + 23, this.y + 42);
                ctx.lineTo(this.x + 36, this.y + 32);
                ctx.moveTo(this.x + 26, this.y + 46);
                ctx.lineTo(this.x + 39, this.y + 36);
                ctx.moveTo(this.x + 34, this.y + 81);
                ctx.lineTo(this.x + 48, this.y + 90);
                ctx.moveTo(this.x - 2, this.y + 88);
                ctx.lineTo(this.x - 2, this.y + 110);
                ctx.moveTo(this.x + 3, this.y + 88);
                ctx.lineTo(this.x + 3, this.y + 110);
                ctx.moveTo(this.x - 26, this.y + 45);
                ctx.lineTo(this.x - 46, this.y + 38);
                ctx.moveTo(this.x - 60, this.y + 44);
                ctx.lineTo(this.x - 74, this.y + 58);
                ctx.lineTo(this.x - 61, this.y + 77);
                ctx.moveTo(this.x - 58, this.y + 49);
                ctx.lineTo(this.x - 68, this.y + 59);
                ctx.moveTo(this.x - 46, this.y + 82);
                ctx.lineTo(this.x - 26, this.y + 73);
                ctx.moveTo(this.x - 60, this.y + 86);
                ctx.lineTo(this.x - 70, this.y + 100);
                ctx.strokeStyle = "rgba(255,255,255,0.2)";
                ctx.lineWidth = 3;
                ctx.stroke();
                ctx.fillStyle = "rgba(255,255,255,0.2)";
                ctx.font = "15px Arial";
                ctx.fillText("CH", this.x - 5, this.y);
                ctx.fillText("3", this.x + 18, this.y + 6);
                ctx.fillText("N", this.x - 5, this.y + 37);
                ctx.fillText("O", this.x + 38, this.y + 35);
                ctx.fillText("N", this.x + 21, this.y + 81);
                ctx.fillText("CH", this.x + 50, this.y + 99);
                ctx.fillText("3", this.x + 72, this.y + 105);
                ctx.fillText("O", this.x - 5, this.y + 124);
                ctx.fillText("N", this.x - 59, this.y + 42);
                ctx.fillText("N", this.x - 59, this.y + 84);
                ctx.fillText("H  C", this.x - 98, this.y + 114);
                ctx.fillText("3", this.x - 87, this.y + 119);
                ctx.closePath();
                ctx.restore();
            }
            else if (mol == "C6H6") {
                //Benzene
                ctx.save();
                ctx.translate(this.x + 20, this.y + 80);

                ctx.translate(-this.x + 20, -this.y - 80);
                ctx.beginPath();
                ctx.moveTo(this.x, this.y + 30);
                ctx.lineTo(this.x - 26, this.y + 45);
                ctx.lineTo(this.x - 26, this.y + 75);
                ctx.lineTo(this.x, this.y + 90);
                ctx.lineTo(this.x + 26, this.y + 75);
                ctx.lineTo(this.x + 26, this.y + 45);
                ctx.lineTo(this.x, this.y + 30);
                ctx.moveTo(this.x - 20, this.y + 47);
                ctx.lineTo(this.x - 20, this.y + 73);
                ctx.moveTo(this.x, this.y + 83);
                ctx.lineTo(this.x + 22, this.y + 70);
                ctx.moveTo(this.x, this.y + 36);
                ctx.lineTo(this.x + 22, this.y + 49);
                ctx.strokeStyle = "rgba(255,255,255,0.2)";
                ctx.lineWidth = 3;
                ctx.stroke();
                ctx.closePath();
                ctx.restore();
            }
        }
    }

    init();

    function animloop() {
        draw();
        requestAnimFrame(animloop);
    }

    $(function () {
        layui.use(['layer', 'form'], function () {
            var layer = layui.layer,
                    $ = layui.jquery,
                    form = layui.form();
        });
    })

    function goto_register() {
        $("#register").show();
        $("#login").hide();
        $("#reset").hide();
    }

    function goto_login() {
        $("#register").hide();
        $("#login").show();
        $("#reset").hide();
    }

    function goto_forget() {
        $("#register").hide();
        $("#login").hide();
        $("#reset").show();
    }

    animloop();
</script>
</body>
</html>