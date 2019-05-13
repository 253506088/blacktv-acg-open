<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>彩电ACG后台</title>
    <link rel="stylesheet" href="/layui/css/layui.css">
    <script src="/layui/layui.js"></script>
    <script src="/js/jquery-3.2.1.js"></script>
    <script src="/js/jquery.cookie.js"></script>
    <link rel="shortcut icon" href="/tvacg/images/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="/tvacg/images/favicon-apple.png">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">彩电ACG</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <ul class="layui-nav layui-layout-left">
            <li class="layui-nav-item"><a href="/index">前台首页</a></li>
        </ul>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;" id="username"></a>
                <dl class="layui-nav-child">
                    <dd><a href="javascript:;">基本资料</a></dd>
                    <dd><a href="javascript:;">安全设置</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="javascript:;" id="logout">登出</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="test" id="layuiNavigation">

            </ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
    <#--选项卡-->
        <div class="layui-tab layui-tab-brief" lay-filter="demo" lay-allowclose="true">
            <ul class="layui-tab-title layui-tab-card">
            </ul>
            <div class="layui-tab-content">
            </div>
        </div>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © Copyright 2018 <a href="/index">彩电acg</a>. All Rights Reserved.
    </div>
</div>

<script>
    $(document).ready(function () {
        //获取后台导航栏数据
        getAdminPageMsg();
        //初始显示必应页面
        openWindow("https://cn.bing.com/", "必应");
        //登录提示
        checkLogin();
        //登出
        $("#logout").click(function(){
            adminLogout();
        });
    });

    /**
     * 获取后台导航栏数据
     */
    function getAdminPageMsg() {
        $.get("/blacktv/acg/admin/get/page/msg", null, function (result) {
            var html = "";
            console.log(result);
            if (result.user != null) {
                html += "<li class='layui-nav-item layui-nav-itemed'><a class='' href='javascript:;'>" + result.user.pageName + "</a><dl class='layui-nav-child'>";
                for (var i = 0; i < result.user.pages.length; i++) {
                    html += "<dd><a href='javascript:;' onclick=\"openWindow('" + result.user.pages[i].url + "','" + result.user.pages[i].pageName + "')\">" + result.user.pages[i].pageName + "</a></dd>";
                }
                html += "</dl></li>";
            }

            if (result.resources != null) {
                html += "<li class='layui-nav-item layui-nav-itemed'><a class='' href='javascript:;'>" + result.resources.pageName + "</a><dl class='layui-nav-child'>";
                for (var i = 0; i < result.resources.pages.length; i++) {
                    html += "<dd><a href='javascript:;' onclick=\"openWindow('" + result.resources.pages[i].url + "','" + result.resources.pages[i].pageName + "')\">" + result.resources.pages[i].pageName + "</a></dd>";
                }
                html += "</dl></li>";
            }
            html += "<li class='layui-nav-item'><a href='javascript:;' onclick=\"window.location.reload()\">关闭所有</a></li>";//说是关闭所有，实际上就是刷新一下页面
            $("#layuiNavigation").append(html);
            console.log(html);
        }, "json");
    }

    /**
     * 用于在选项卡里打开新页面的方法
     * @param Url，新页面的url，跳转外链要带上http或https，通过<iframe>标签来引入其他网页
     * @param Name ，新页面在选项卡里的名字
     * 关于选项卡的内容在base/admin.html里面
     */
    function openWindow(Url, Name) {
        //layer.msg(Url);
        layui.use('element', function () {
            var $ = layui.jquery
                    , element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块
            //新增一个Tab项
            var layid = new Date().getTime();
            element.tabAdd('demo', {
                title: Name //选项卡名称
                ,
                content: "<iframe src=" + Url + " scrolling='auto' frameborder='0' height='733px' width='100%'></iframe>"//选项卡内容
                ,
                id: layid //选项卡id，实际使用一般是规定好的id，这里以时间戳模拟下
            })
            element.tabChange('demo', layid);//切换到指定layid的选项卡页面上
        });
    }

    //登出
    function adminLogout(){
        $.post("/blacktv/acg/admin/user/logout", null, function (result) {
            $.cookie("adminLoginFlag", null);
            $.cookie("adminUser", null);
            window.location.href = "/index";
        }, "json");
    }

    //登录提示
    function checkLogin() {
        //读取序列化为字符串的json对象，反序列化为json对象
        user = JSON.parse($.cookie('adminUser'));
        console.log(user);
        if (user != null) {
            if($.cookie('adminLoginFlag') == null){
                $.cookie("adminLoginFlag", true, {expires: 1, path: '/blacktv/acg/admin'});
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.msg(user.username + "\t欢迎回来");
                });
            }
            $("#username").text(user.username);//导航栏上的用户名
        }
    }
</script>
</body>
</html>