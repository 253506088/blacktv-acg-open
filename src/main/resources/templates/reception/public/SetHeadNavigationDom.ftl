<#--根据是否登录来决定头部导航栏的显示，一定要在文档的最后引用-->
<script>
    $(document).ready(function () {
        checkLoginDom();
    });

    //根据是否登录来决定头部导航栏的显示
    function checkLoginDom() {
        //读取序列化为字符串的json对象
        var user_String = $.cookie('user');
        //反序列化为json对象
        var user = JSON.parse(user_String);
        var html = "";
        if (user == null) {
            html = "<a href=\"/user/loginPage\">登录</a>";
        } else {
            html = "<a href='javascript:void(0);'>" + user.username + "</a><ul class='submenu'>";
            html += "<li><a href='/user/index?page=msg012'>账号管理</a></li>";
            html += "<li><a href='/user/index?page=msg013'>投稿资源</a></li>";
            html += "<li><a href='/user/index?page=msg014'>投稿管理</a></li>";
            html += "<li><a href='javascript:void(0);' onclick = 'logout()'>退出登录</a></li></ul>";
        }
        $("#loginStateNavigation").append(html);
    }

    //登出方法，消除jwt、user、loginFlag的cookie
    function logout() {
        $.cookie('user', null);
        $.cookie('jwt', null);
        $.cookie("loginFlag", null);
        window.location.reload();//刷新当前页面
    }

    //获取资源分类列表,并设置分类列表
    function getResourcesTypeList() {
        //读取序列化为字符串的json对象,反序列化为json对象
        var resourcesTypeList = JSON.parse($.cookie('resourcesTypeList'));
        //没cookie的话就去查一下
        if (resourcesTypeList == null) {
            $.get("/resources/get/type", null, function (result) {
                if (result.list != null) {
                    $.cookie("resourcesTypeList", JSON.stringify(result), {expires: 1, path: '/'});
                    setResourcesTypeList(result);
                }
            }, "json");
        } else {
            setResourcesTypeList(resourcesTypeList);
        }
    }

    //设置导航栏的资源分类列表
    function setResourcesTypeList(resourcesTypeList) {
        var html = "";
        for (var i = 0; i < resourcesTypeList.list.length; i++)
            html += "<li><a href='javascript:void(0);'>" + resourcesTypeList.list[i].name + "</a></li>";
        $("#resourcesTypeList").append(html);
    }
</script>