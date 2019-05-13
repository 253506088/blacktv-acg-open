<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>管理用户</title>
    <link rel="stylesheet" href="/layui/css/layui.css">
    <script src="/layui/layui.js"></script>
    <script src="/js/jquery-3.2.1.js"></script>
    <script src="/js/jquery.cookie.js"></script>
    <link rel="shortcut icon" href="/tvacg/images/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="/tvacg/images/favicon-apple.png">
</head>
<body>
<blockquote class="layui-elem-quote layui-text">
    注意：1未删除、0已删除。
</blockquote>
<div class="layui-upload">
    <table class="layui-hide" id="test" lay-filter="test"></table>

    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="del1">恢复用户</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del0">删除用户</a>
    </script>
    <br/>
    <br/> <br/>

</div>

<script>
    layui.use('table', function () {
        var table = layui.table;
        table.render({
            elem: '#test'
            , url: '/blacktv/acg/admin/user/get/adminDate'
            , toolbar: '#toolbarDemo'
            , title: '管理用户表'
            , page: true
            , cols: [[
                {field: 'id', title: 'ID', width: 80, fixed: 'left', unresize: true, sort: true}
                , {field: 'username', title: '用户名', width: 200}
                , {field: 'title', title: '标题', width: 200}
                , {field: 'email', title: 'email', width: 200}
                , {field: 'gmt_create', title: '创建时间', width: 180}
                , {field: 'gmt_modified', title: '上次修改', width: 180}
                , {field: 'isDlete', title: '删除', width: 180}
                , {field: 'isActivation', title: '激活', width: 180}
                , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 165}
            ]]
        });

        //监听行工具事件
        table.on('tool(test)', function (obj) {
            var data = obj.data;//dara是该行的全部数据，json格式的
            if (obj.event === 'del0') {
                layer.confirm('请确认是否删除该用户', function (index) {
                    if (data.isDlete != 0 || data.isDlete != "0") {
                        //ajax发起请求,这里就不在页面里删除本条了
                        ajax("/blacktv/acg/admin/user/set/isDelete", "id=" + data.id + "&isDlete=0");
                        //修改数据表格中的值
                        obj.update({
                            isDlete: '0'
                        });
                    }
                    layer.close(index);
                });
            } else if (obj.event === 'del1') {
                layer.confirm('请确认是否恢复该用户', function (index) {
                    if (data.isDlete != 1 || data.isDlete != "1") {
                        //ajax发起请求,这里就不在页面里删除本条了
                        ajax("/blacktv/acg/admin/user/set/isDelete", "id=" + data.id + "&isDlete=1");
                        //修改数据表格中的值
                        obj.update({
                            isDlete: '1'
                        });
                    }
                    layer.close(index);
                });
            }
        });
    });

    //发起设置审核状态的请求
    function ajax(url, data) {
        $.post(url, data, function (result) {
            switch (result.msg) {
                case "msg027":
                    //成功就不做提示了
                    break;
                case "msg028":
                    layer.msg('资源删除状态设置失败');
                    break;
            }
        }, "json");
    }

    layui.use(['form', 'layedit', 'laydate', 'upload', 'table'], function () {
        var $ = layui.jquery, upload = layui.upload;
        var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit
                , laydate = layui.laydate;
    });
</script>
</body>
</html>