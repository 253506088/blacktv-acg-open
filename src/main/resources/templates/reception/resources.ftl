<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
<head>
    <!-- Basic Page Needs -->
    <meta charset="utf-8">
    <title id="title1"></title>
    <!-- Mobile Specific Metas -->
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <script type="text/javascript" src="/tvacg/js/jquery-1.9.1.min.js"></script>
    <!-- <script type="text/javascript" src="js/back-endPage.js"></script> --> <!-- 后台分页 -->
    <script type="text/javascript" src="/tvacg/js/front-endPage.js"></script><!-- 前台分页 -->
    <script type="text/javascript" src="/tvacg/js/shujv2.js"></script>

    <link rel="stylesheet" type="text/css" href="/tvacg/css/table.css">

    <!-- Bootstrap  -->
    <link rel="stylesheet" type="text/css" href="/tvacg/stylesheets/bootstrap.css">

    <!-- Theme Style -->
    <link rel="stylesheet" type="text/css" href="/tvacg/stylesheets/style.css">
    <link rel="stylesheet" type="text/css" href="/tvacg/stylesheets/responsive.css">
    <link rel="stylesheet" type="text/css" href="/tvacg/stylesheets/colors/color1.css" id="colors">

    <link rel="shortcut icon" href="/tvacg/images/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="/tvacg/images/favicon-apple.png">

    <style>
        .hahe {
            margin: 10px 0px;
        }

        .haha {
            margin: 10px 0px;
        }

    </style>
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

    <div id="header" class="bg-192836 style2">

    </div> <!-- #header -->

<#--引入Banna图公共文件-->
            <#include "public/banna.ftl">

    <div class="content-wrap  courses-single-page">
        <h3 style="text-align:center" id="title2"></h3>
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-md-12">
                    <div class="content-page-wrap">
                        <div class="course-single">


                        </div> <!-- /.course-single -->
                        <div class="flat-tabs bg-fff border-ra4 border-f-e6f3ff">
                            <ul class="tab-title style1 clearfix border-bt-e6f3ff">
                                <li class="item-title  overview">
                                    <span class="inner">资源介绍</span>
                                </li>


                                <li class="item-title review" id="showDownload">
                                    <span class="inner">下载地址</span>
                                </li>
                            </ul>
                            <div class="tab-content-wrap">
                                <div class="tab-content">
                                <#--资源介绍栏 start-->
                                    <div class="item-content" id="describe">

                                    </div>
                                <#--资源介绍栏 end-->
                                </div>
                            <#--下载地址栏 start-->
                                <div class="tab-content" id="downloadHtml">
                                    <div class="link" id="linklink">
                                        <div id="histroyBox">
                                            <table class="CJJ-Table">
                                                <thead>
                                                <tr>
                                                    <th></th>
                                                    <th class="qipa">下载链接
                                                    <th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr class="new_productBox">
                                                    <td class="taskCode" style="display:table-cell">上传时间</td>
                                                    <td class="startAddr" style="display:table-cell"
                                                        id="gmt_create"></td>
                                                    <td class="endAddr" style="display:table-cell"></td>
                                                    <td class="varietiesTypeName" style="display:table-cell"></td>
                                                    <td class="createDate" style="display:table-cell"></td>
                                                    <td class="customerName" style="display:none">彩电agc</td>
                                                </tr>
                                                <tr class="new_productBox">
                                                    <td class="taskCode" style="display:table-cell">文件来源</td>
                                                    <td class="startAddr" style="display:table-cell" id="from"></td>
                                                    <td class="endAddr" style="display:table-cell"></td>
                                                    <td class="varietiesTypeName" style="display:table-cell"></td>
                                                    <td class="createDate" style="display:table-cell"></td>
                                                    <td class="customerName" style="display:none">彩电acg</td>
                                                </tr>
                                                <tr class="new_productBox">
                                                    <td class="taskCode" style="display:table-cell">备注信息</td>
                                                    <td class="startAddr" style="display:table-cell" id="remarks"></td>
                                                </tr>
                                                </tbody>
                                            </table>
                                        </div>
                                        <div id="histroyBoxy"><a id="download" href="">
                                            <table class="CJJ-Table">
                                                <thead>
                                                <tr>
                                                    <th></th>
                                                    <th>下载链接</th>
                                                    <th></th>
                                                    <th></th>
                                                    <th></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <tr class="new_productBox">
                                                    <td class="endAddr" style="display:table-cell"></td>
                                                </tr>
                                                <tr class="new_productBox">
                                                    <td class="endAddr" style="display:table-cell"></td>
                                                </tr>
                                                <tr class="new_productBox">
                                                    <td class="endAddr" style="display:table-cell"></td>
                                                </tr>
                                                </tbody>
                                                <tfoot>
                                                <tr></tr>
                                                </tfoot>
                                                <div class="baidu"><img src="/tvacg/images/lianjie.png" alt=""></div>
                                            </table>
                                        </a>
                                        </div>
                                    </div>
                                    <br/>
                                </div>
                            <#--下载地址栏 end-->
                                <div class="tab-content">
                                    <div class="item-content">
                                        <h1>No Review</h1>
                                    </div>
                                </div>
                            </div>
                        </div> <!-- /.flat-tabs -->

                    </div> <!-- /.content-page-wrap -->
                </div>
                <div class="col-lg-4 col-md-12">
                    <div class="sidebar-right"> <!-- /.widget-subscribe -->                            </div>
                </div>
            </div>
        </div> <!-- /.container -->
    </div>


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
<link rel="stylesheet" href="/layui/css/layui.css">
<script src="/layui/layui.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        getData();
    });

    //提示登录状态
    function getData() {
        var data = "id=" + UrlSearch("id") + "&visit=" + true;
        if ($.cookie('jwt') != null) {
            data += "&jwt=" + $.cookie('jwt');
        }else{
            layui.use('layer', function () {
                var layer = layui.layer;
                layer.msg("登陆后才可以下载文件");
            });
        }
        $.get("/resources/get/byId", data, function (result) {
            if (result != null) {
                $("#describe").append(result.describe);
                $("#title1").text(result.title);
                $("#title2").text(result.title);
                if (result.download != null && result.download.length > 1) {
                    $("#remarks").text(result.remarks);
                    $("#from").text(result.from);
                    $("#download").attr({"href": result.download});
                    $("#gmt_create").text(format(result.gmt_create));
                } else {
                    //说明没有下载链接，就隐藏掉下载的控件
                    $("#showDownload").hide();
                    $("#downloadHtml").hide();
                }
            }
        }, "json");
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
</body>
<#--引入根据是否登录来决定头部导航栏的显示的公共文件-->
    <#include "public/SetHeadNavigationDom.ftl">
</html>