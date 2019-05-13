<!DOCTYPE html>
<html>
<head>
    <!-- Basic Page Needs -->
    <meta charset="utf-8">
    <!--[if IE]>
    <meta http-equiv='X-UA-Compatible' content='IE=edge,chrome=1'><![endif]-->
    <title>彩电& ACG</title>

    <meta name="author" content="themesflat.com">

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

</head>
<body class="bg-body2">
<div class="boxed">
    <div class="preloader">
                <span class="loader">
                    <span class="loader-inner"></span>
                </span>
    </div>

<#--引入头部导航公共文件-->
            <#include "public/headNavigation.ftl">
<#--引入Banna图公共文件-->
            <#include "public/banna.ftl">

    <div class="content-wrap  courses-grid-v1-page">
        <div class="container-fluid">
            <div class="content-page-wrap">
                <ul class="flat-filter-isotype style1 text-center " id="resourcesTypeList2">
                    <li id="typeId0"><a href="#" data-filter="*" onclick="setTypeId(0);openWindow('0','new');">不分类</a></li>
                    <li id="typeId1"><a href="#" data-filter=".1" onclick="setTypeId(1);openWindow('1','new');">动漫</a></li>
                    <li id="typeId2"><a href="#" data-filter=".2" onclick="setTypeId(2);openWindow('1','new');">图片</a></li>
                    <li id="typeId3"><a href="#" data-filter=".3" onclick="setTypeId(3);openWindow('1','new');">音乐</a></li>
                    <li id="typeId4"><a href="#" data-filter=".4" onclick="setTypeId(4);openWindow('1','new');">影视</a></li>
                    <li id="typeId5"><a href="#" data-filter=".5" onclick="setTypeId(5);openWindow('1','new');">游戏</a></li>
                    <li id="typeId7"><a href="#" data-filter=".7" onclick="setTypeId(7);openWindow('1','new');">小说</a></li>
                    <li id="typeId6"><a href="#" data-filter=".6" onclick="setTypeId(6);openWindow('1','new');">其他</a></li>
                </ul>
                <div id="resourcesListDiv" class="flat-courses clearfix isotope-courses">

                <#list resourcesList as resources>
                    <div class="course one-of-four text-2ebd59 ${resources.typeId}">
                        <div class="course-border border-f-e6f3ff border-ra4 transition-vline">
                            <div class="course-img img-vline">
                                <a href="/resources?id=${resources.id}"><img src="${resources.coverFilename}"
                                                                             alt="bookflare"></a>
                                <div class="overlay">
                                    <span class="vline"></span>
                                    <span class="vline vline-bottom"></span>
                                </div>
                            </div>
                            <div class="course-content">
                                <div class="text-wrap border-bt-e6f3ff">
                                    <h6 class="title">点击量：${resources.clicks}</h6>
                                    <p class="teacher">投稿日期：${resources.gmt_create?string("yyyy-MM-dd HH:mm:ss")}</p>
                                    <p class="description"><a
                                            href="/resources?id=${resources.id}">${resources.title}</a></p>
                                </div>
                                <div class="wrap-rating-price">
                                    <div class="wrap-rating">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>

                </div> <!-- /.flat-courses -->
            </div> <!-- /.content-page-wrap -->
            <div class="flat-paginations style2">
                <ul class="list flat-text-center pagination-wrap">
                    <#if prePage != page>
                        <li><a href="javascript:;" class="btn-navs">PRE</a></li>
                    <#else>
                        <li class="disabled"><a href="javascript:;" onclick="openWindow('${prePage}',null)" class="btn-navs">PRE</a>
                        </li>
                    </#if>
                    <#list pageList as pageBuffer>
                        <#if pageBuffer != page>
                            <li><a href="javascript:;" onclick="openWindow('${pageBuffer}',null)">${pageBuffer}</a></li>
                        <#else>
                            <li class="active"><a href="javascript:;">${pageBuffer}</a></li>
                        </#if>
                    </#list>

                    <#if nextPage != page>
                        <li><a href="javascript:;" class="btn-navs">NEXT</a></li>
                    <#else>
                        <li class="disabled"><a href="javascript:;" onclick="openWindow('${nextPage}',null)"
                                                class="btn-navs">PRE</a></li>
                    </#if>
                </ul>
            </div> <!-- .flat-paginations -->
        </div> <!-- /.container -->
    </div> <!-- /.content-wrap -->

    <footer id="footer">
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <div class="widget-about">
                        <div id="logo-ft">
                            <a href="/index"><img src="/tvacg/images/logo/logo-i.png" alt="bookflare" width="157"
                                                  height="29" data-retina="/tvacg/images/logo/logo-i.png"
                                                  data-width="157" data-height="29"></a>
                        </div>
                        <p class="description">彩电ACG只为了学习经验而搭建</p>
                    </div>
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
<#--引入根据是否登录来决定头部导航栏的显示的公共文件-->
<#include "public/SetHeadNavigationDom.ftl">
</body>
<script>
    var user = null;
    var typeId = 0;//默认不分类
    var page = 1;//初始页面为1
    var limit = 12;//页面容量为12
    var texts = new Array(
            "text-2ebd59", "text-ea0042", "text-c100ea", "text-256cff", "text-8828ff"
            , "text-0dacff", "text-536dfe", "text-ffbf13", "text-2ebd59", "text-ff562f"
            , "text-0dacff", "text-2ebd59"
    );//texts的长度要与limit一样
    $(document).ready(function () {
        checkLogin();//检查是否已登录
        typeIdIni();
    });

    //提示登录状态
    function checkLogin() {
        if ($.cookie('loginFlag') == null) {
            //读取序列化为字符串的json对象
            var user_String = $.cookie('user');
            $.cookie("loginFlag", true, {expires: 1, path: '/'});//一天以内不再提醒
            if (user_String != null) {
                //反序列化为json对象
                user = JSON.parse(user_String);
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.msg(user.username + "\t欢迎回来");
                });
            } else {
                layui.use('layer', function () {
                    var layer = layui.layer;
                    layer.msg("您还没登录");
                });
            }
        }
    }

    //初始化typeId,与type选项卡
    function typeIdIni() {
        var typeId_String = $.cookie('typeId');
        if (typeId_String != null)
            typeId = typeId_String;
        switch (typeId) {
            case "0":
                $("#typeId0").addClass("active");//让指定的type选项卡被选中
                break;
            case "1":
                $("#typeId1").addClass("active");//让指定的type选项卡被选中
                break;
            case "2":
                $("#typeId2").addClass("active");//让指定的type选项卡被选中
                break
            case "3":
                $("#typeId3").addClass("active");//让指定的type选项卡被选中
                break;
            case "4":
                $("#typeId4").addClass("active");//让指定的type选项卡被选中
                break;
            case "5":
                $("#typeId5").addClass("active");//让指定的type选项卡被选中
                break;
            case "6":
                $("#typeId6").addClass("active");//让指定的type选项卡被选中
                break;
            case "7":
                $("#typeId7").addClass("active");//让指定的type选项卡被选中
                break;
        }
    }

    //设置typeId
    function setTypeId(newTypeId) {
        if (newTypeId != typeId) {
            $.cookie("typeId", newTypeId);//只在当前页面有用，关闭浏览器后cookie就失效了
            typeId = newTypeId;
        }
    }

    function goPAGE() {
        if ((navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i))) {
            // window.location.href="移动端url";
        }
        else {
            // window.location.href="pc端url";
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg("该站点是面向于移动端的");
            });
        }
    }

    //打开新页面
    function openWindow(page,flag) {
        var search = null;
        var url = "";
        //flag != null代表不打算继续查看搜索数据了，而是改为根据typeId搜索
        if(flag == null){
            search = UrlSearch("search")
        }
        //根据当前搜索条件是typeId还是关键字search来生成url
        if (search != null && search.length > 0){
            url = "/index?page=" + page + "&limit=" + limit + "&search=" + search;
        }else{
            url = "/index?page=" + page + "&limit=" + limit + "&typeId=" + typeId;
        }
        window.location.href = url;
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

    /**
     * 获取资源的json数据,已废弃，因为该页面用前后分离的方式样式会崩塌，只能后台渲染
     */
    function getData() {
        var data = "page=" + page + "&limit=" + limit + "&typeId=" + typeId;
        $.get("/resources/index/get/date", data, function (result) {
            var html = "";
            if (result != null) {
                for (var i = 0; i < result.data.length; i++) {
                    html += "<div class='course one-of-four " + texts[i] + " " + result.data[i].typeId + "'>";
                    html += "<div class='course-border border-f-e6f3ff border-ra4 transition-vline'><div class='course-img img-vline'><a href='/resources?id=" + result.data[i].id + "'>";
                    html += "<img src='" + result.data[i].coverFilename + "' s alt='blacktv'></a><div class='overlay'><span class='vline'></span><span class='vline vline-bottom'></span></div></div><div class='course-content'><div class='text-wrap border-bt-e6f3ff'>";
                    html += "<h6 class='title'>点击量：" + result.data[i].clicks + "</h6><p class='teacher'>投稿日期：" + format(result.data[i].gmt_create) + "</p><p class='description'><a href='/resources?id=" + result.data[i].id + "'>" + result.data[i].title + "</a></p></div><div class='wrap-rating-price'><div class='wrap-rating'></div></div></div></div></div>";
                }
            } else
                html = "<h1>暂无数据</h1>";
            $("#resourcesListDiv").append(html);
            // $("#resourcesListDiv").html(html);
            console.log(result);
            console.log(html);
        }, "json");
    }

    /**
     * 将时间戳转换为日期
     * @param shijianchuo
     * @returns {string}
     */
    function format(shijianchuo) {
        //shijianchuo是整数，否则要parseInt转换
        var time = new Date(shijianchuo);
        var y = time.getFullYear();
        var m = time.getMonth() + 1;
        var d = time.getDate();
        var h = time.getHours();
        var mm = time.getMinutes();
        var s = time.getSeconds();
        return y + '-' + setTime(m) + '-' + setTime(d) + ' ' + setTime(h) + ':' + setTime(mm) + ':' + setTime(s);
    }

    /**
     * format方法里用的格式化日期的工具方法
     * @param m
     * @returns {string}
     */
    function setTime(m) {
        return m < 10 ? '0' + m : m
    }
</script>
</html>