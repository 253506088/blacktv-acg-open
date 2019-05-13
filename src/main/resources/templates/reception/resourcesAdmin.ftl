<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>管理资源</title>
    <link rel="stylesheet" href="/layui/css/layui.css">
    <script src="/layui/layui.js"></script>
    <script src="/js/jquery-3.2.1.js"></script>
    <script src="/js/jquery.cookie.js"></script>
    <link rel="shortcut icon" href="/tvacg/images/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="/tvacg/images/favicon-apple.png">
</head>
<body>
<blockquote class="layui-elem-quote layui-text">
    注意：-1审核失败、0未审核、1审核通过；1未删除、0已删除。
</blockquote>
<div class="layui-upload">
    <table class="layui-hide" id="test" lay-filter="test"></table>

    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="update">修改资源</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del0">删除资源</a>
    </script>
    <br/>
    <br/> <br/>

</div>

<script>
    layui.use('table', function () {
        var table = layui.table;
        table.render({
            elem: '#test'
            , url: '/resources/get/date?jwt=' + $.cookie('jwt')
            , toolbar: '#toolbarDemo'
            , title: '管理资源表'
            , page: true
            , cols: [[
                {field: 'id', title: 'ID', width: 80, fixed: 'left', unresize: true, sort: true}
                , {field: 'typeName', title: '分类', width: 70}
                , {field: 'title', title: '标题', width: 200}
                , {field: 'describe', title: '描述', width: 180}
                , {field: 'coverFilename', title: '封面', width: 120}
                , {field: 'from', title: '来源', width: 120}
                , {field: 'remarks', title: '备注', width: 180}
                , {field: 'download', title: '链接', width: 180}
                , {field: 'toExamine', title: '审核', width: 60}
                , {field: 'isDlete', title: '删除', width: 60}
                , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 165}
            ]]
        });

        //监听行工具事件
        table.on('tool(test)', function (obj) {
            var data = obj.data;//dara是该行的全部数据，json格式的
            if (obj.event === 'del0') {
                layer.confirm('请确认是否删除该资源', function (index) {
                    if (data.isDlete != 0 || data.isDlete != "0") {
                        //ajax发起请求,这里就不在页面里删除本条了
                        ajax("/resources/set/isDelete", "id=" + data.id + "&isDlete=0&jwt="+$.cookie('jwt'));
                        //修改数据表格中的值
                        obj.update({
                            isDlete: '0'
                        });
                    }
                    layer.close(index);
                });
            }else if (obj.event === 'update') {
                window.location.href = "/resources/uploadPage?id="+data.id;
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