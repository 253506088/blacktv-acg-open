<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>登录页面</title>
    <meta name="keywords" content=""/>
    <meta name="description" content=""/>
    <link rel="shortcut icon" href="/tvacg/images/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="/tvacg/images/favicon-apple.png">
    <link rel="stylesheet" type="text/css" href="/adminLogin/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="/adminLogin/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/adminLogin/fonts/iconic/css/material-design-iconic-font.min.css">
    <link rel="stylesheet" type="text/css" href="/adminLogin/css/util.css">
    <link rel="stylesheet" type="text/css" href="/adminLogin/css/main.css">
    <script src="/js/jquery-3.2.1.js"></script>
    <script src="/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/tvacg/lib/layui/layui.js"></script>
</head>

<body>

<div class="limiter">
    <div class="container-login100" id="img" style="background-image: url('/adminLogin/images/bg-03.jpg');">
        <div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
            <form class="login100-form validate-form">
                <span class="login100-form-title p-b-49">登录</span>

                <div class="wrap-input100 validate-input m-b-23" data-validate="请输入邮箱">
                    <span class="label-input100">邮箱</span>
                    <input class="input100" type="text" name="email" placeholder="请输入邮箱" autocomplete="off"
                           id="email">
                    <span class="focus-input100" data-symbol="&#xf206;"></span>
                </div>

                <div class="wrap-input100 validate-input" data-validate="请输入密码">
                    <span class="label-input100">密码</span>
                    <input class="input100" type="password" name="password" placeholder="请输入密码" id="password">
                    <span class="focus-input100" data-symbol="&#xf190;"></span>
                </div>

                <div class="text-right p-t-8 p-b-31"></div>

                <div class="container-login100-form-btn">
                    <div class="wrap-login100-form-btn">
                        <div class="login100-form-bgbtn"></div>
                        <button class="login100-form-btn" id="loginButton" onClick="return false;">登 录</button>
                    </div>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
<script>
    $(document).ready(function () {
        //检查用户名或邮箱
        $("#email").blur(function () {
            loginCheckEmail();
        });

        //检查密码长度
        $("#password").blur(function () {
            loginCheckPassword();
        });

        $("#loginButton").click(function () {
            login();
        });
    });

    //检查邮箱
    function loginCheckEmail() {
        var loginemailOrEmeail = $("#email").val();
        if(!isEmail(loginemailOrEmeail)){
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('请输入正确的邮箱');
            });
            return false;
        }
        //检查长度是否符合要求
        if (loginemailOrEmeail.length < 1 || loginemailOrEmeail.length > 30) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('邮箱长度要大于6小于30');
            });
            return false;
        }
        return true;
    }

    //检查登录密码
    function loginCheckPassword() {
        var loginPassword = $("#password").val();
        if (loginPassword.length < 6 || loginPassword.length > 30) {
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg('密码长度要大于6小于30');
            });
            return false;
        }
        return true;
    }

    //发起登录
    function login() {
        //先重新验证一遍
        if (loginCheckPassword() && loginCheckEmail()) {
            var data = "password=" + $("#password").val() + "&email=" + $("#email").val();
            $.post("/blacktv/acg/admin/user/login", data, function (result) {
                if (result.loginFlag == true) {
                    $.cookie("adminUser", JSON.stringify(result.adminUser), {expires: 1, path: '/blacktv/acg/admin'});
                    window.location.href = "/blacktv/acg/admin/index";
                } else {
                    layui.use('layer', function () {
                        var layer = layui.layer;
                        layer.msg('账号(邮箱)或密码错误');
                    });
                }
            }, "json");
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
</script>

</html>