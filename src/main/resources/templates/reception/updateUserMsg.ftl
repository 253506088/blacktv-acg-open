<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>账号管理</title>
    <link rel="stylesheet" href="/layui/css/layui.css">
    <script src="/layui/layui.js"></script>
    <script src="/js/jquery-3.2.1.js"></script>
    <script src="/js/jquery.cookie.js"></script>
    <link rel="shortcut icon" href="/tvacg/images/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="/tvacg/images/favicon-apple.png">
</head>
<body>
<div class="layui-upload">
    <form class="layui-form" method="put" lay-filter="example" id="form">
        <blockquote class="layui-elem-quote layui-text">
            注意：一周只能修改一次名称，如果不打算修改的项请不要填写，修改邮箱和密码需要邮箱验证。
        </blockquote>

        <div class="layui-form-item">
            <label class="layui-form-label">新名称</label>
            <div class="layui-input-block">
                <input type="text" name="username" lay-verify="title" id="username" autocomplete="off"
                       placeholder="请输入新名称，不打算修改可不填"
                       class="layui-input">
            </div>
        </div>

    <#--
    后端跟上之后可以解开这里的注释
            <div class="layui-form-item">
        <label class="layui-form-label">新邮箱</label>
        <div class="layui-input-block">
            <input type="text" name="email" id="email" lay-verify="title" autocomplete="off"
                   placeholder="请输入新邮箱，不打算修改可不填"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">旧密码</label>
        <div class="layui-input-block">
            <input type="text" name="oldPassword" id="oldPassword" lay-verify="title" autocomplete="off"
                   placeholder="请输入旧密码，不打算修改可不填"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">新密码</label>
        <div class="layui-input-block">
            <input type="text" name="password" id="password" lay-verify="title" autocomplete="off"
                   placeholder="请输入新密码，不打算修改可不填"
                   class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">确认新密码</label>
        <div class="layui-input-block">
            <input type="text" id="password2" lay-verify="title" autocomplete="off" placeholder="请输入确认新密码，不打算修改可不填"
                   class="layui-input">
        </div>
    </div>
    -->

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">修改信息</button>
            </div>
        </div>

    </form>
</div>

<script>
    layui.use(['form', 'layedit', 'laydate', 'upload'], function () {
        var $ = layui.jquery, upload = layui.upload;
        var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit
                , laydate = layui.laydate;

        //监听提交,这里不用layui的提交方法，自己写一个ajax，只是用了layui监听提交按钮
        form.on('submit(demo1)', function (data) {
            updateUsername();//修改用户昵称
            //这里顺序执行的代码先暂时封存起来，以后版本更新的时候可能会用到
            if (false) {
                var falg1 = updateUsername();//修改用户昵称
                var falg2 = false;
                var falg3 = false;
                //如果修改用户昵称发起了请求，等一段时间再修改邮箱
                if (falg1) {
                    setTimeout(function () {
                        falg2 = updateEmail();
                    }, 1500);// 1.5秒后再修改邮箱
                } else
                    falg2 = updateEmail();
                //如果修改用户邮箱发起了请求，等一段时间再修改密码
                if (falg2) {
                    setTimeout(function () {
                        falg3 = updateEmail();
                    }, 1500);// 1.5秒后再修改邮箱
                } else
                    falg3 = updateEmail();
            }
            return false;//true 允许layui提交，false则反之
        });
    });

    /**
     * 修改用户昵称方法,发起请求返回true，未发起返回false
     */
    function updateUsername() {
        var username = $("#username").val();
        if (username.length >= 1 && username.length < 20) {
            var data = "username=" + username + "&jwt=+" + $.cookie("jwt");
            ajaxUpdate("/user/update/username", "put", data);
            return true;
        }
        if (username.length != 0)
            layer.msg('账号长度要大于1小于20');
        return false;
    }

    /**
     *  修改用户邮箱方法,发起请求返回true，未发起返回false
     */
    function updateEmail() {
        var email = $("#email").val();
        if (email.length >= 1 && email.length < 30) {
            var data = "email=" + email + "&jwt=+" + $.cookie("jwt");
            ajaxUpdate("/user/setEmailSendEmail", "put", data);
            return true;
        }
        if (email.length != 0)
            layer.msg('邮箱长度要大于6小于30');
        return false;
    }

    /**
     * 用于修改用户信息的ajax，返回数据类型只有json
     * @param url
     * @param type
     * @param data
     * @param username  该属性只在修改昵称时有用，其他用途传入null就可
     */
    function ajaxUpdate(url, type, data, username) {
        $.ajax({
            "url": url,
            "type": type,
            "data": data,
            "dataType": "json",
            "success": function (result) {
                switch (result.msg) {
                    case "msg017":
                        layer.msg('该昵称已存在');
                        break;
                    case "msg018":
                        layer.msg('昵称修改成功');
                        //修改本地缓存中的用户昵称和jwt，并刷新有效时间
                        //将json对象序列化为字符串再存储到cookie中
                        var user_String = JSON.stringify(result.user);
                        $.cookie("jwt", result.jwt, {expires: result.rememberTime, path: '/'});
                        $.cookie("user", user_String, {expires: result.rememberTime, path: '/'});
                        break;
                    case "msg019":
                        layer.msg('昵称修改失败');
                        break;
                    case "msg020":
                        layer.msg('昵称修改CD中');
                        break;
                    case "msg021":
                        layer.msg('修改邮箱邮件发送成功');
                        break;
                    case "msg022":
                        layer.msg('修改邮箱邮件发送失败');
                        break;
                    default:
                        console.log("未知信息:" + result.msg);
                }
            },
            "error": function () {
                console.log("接口异常");
            }
        });
    }
</script>
</body>
</html>