<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title id="htmlTitle"></title>
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
            </li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="test" id="layuiNavigation">
                <li class="layui-nav-item"><a href="javascript:;" onclick="openWindow('/user/update/msg','账号管理')">账号管理</a></li>
                <li class="layui-nav-item"><a href="javascript:;" onclick="openWindow('/resources/uploadPage','投稿资源')">投稿资源</a></li>
                <li class="layui-nav-item"><a href="javascript:;" onclick="openWindow('/resources/user/admin','投稿管理')">投稿管理</a></li>
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
        //初始化
        ini();
        //登录提示
        checkLogin();
        //登出
        $("#logout").click(function () {
            logout();
        });
    });

    //初始化
    function ini(){
        var msg = UrlSearch("page");
        switch (msg) {
            case "msg012":
                openWindow("/user/update/msg", "账号管理");
                break;
            case "msg013":
                openWindow("/resources/uploadPage", "投稿资源");
                break;
            case "msg014":
                openWindow("/resources/user/admin", "投稿管理");
                break;
            default:
                openWindow("https://cn.bing.com/", "必应");//初始显示必应页面
        }
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

    //登录提示
    function checkLogin() {
        //读取序列化为字符串的json对象
        var user_String = $.cookie('user');
        if (user_String != null) {
            //反序列化为json对象
            var user = JSON.parse(user_String);
            $("#username").text(user.username);//导航栏上的用户名
            $("#htmlTitle").text(user.username + "的个人空间");//导航栏上的用户名
        } else {
            window.location.href = "/404";//没登录无法访问的，随便请求一个url，进入404页面
        }
    }

    //根绝key读取url中的参数(只能读取get协议字符串)
    function UrlSearch(key) {
        var name, value;
        var str = location.href; //取得整个地址栏
        var num = str.indexOf("?");
        str = str.substr(num + 1); //取得所有参数   stringvar.substr(start [, length ]

        var arr = str.split("&"); //根据&分割字符串为数组，将各个参数的键值对放到数组里
        for (var i = 0; i < arr.length; i++) {
            buffer = arr[i].split("=");//根据=分割键值对字符串到数组中，下标为0的是key，下标为2的是value
            if (buffer.length == 2 && buffer[0] == key)
                return buffer[1];
        }
    }
</script>
</body>
</html>