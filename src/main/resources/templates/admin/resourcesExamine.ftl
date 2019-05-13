<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>审核资源</title>
    <link rel="stylesheet" href="/layui/css/layui.css">
    <script src="/layui/layui.js"></script>
    <script src="/js/jquery-3.2.1.js"></script>
    <script src="/js/jquery.cookie.js"></script>
    <link rel="shortcut icon" href="/tvacg/images/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="/tvacg/images/favicon-apple.png">
</head>
<body>
<div class="layui-upload">
    <table class="layui-hide" id="test" lay-filter="test"></table>

    <script type="text/html" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="edit">审核通过</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">审核失败</a>
    </script>
    <br/>
    <br/> <br/>

</div>

<script>
    layui.use('table', function () {
        var table = layui.table;
        table.render({
            elem: '#test'
            , url: '/blacktv/acg/admin/resources/get/examineDate'
            , toolbar: '#toolbarDemo'
            , title: '审核资源表'
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
                , {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 165}
            ]]
        });

        //监听行工具事件
        table.on('tool(test)', function (obj) {
            var data = obj.data;//dara是该行的全部数据，json格式的
            //console.log(obj)
            if (obj.event === 'del') {
                layer.confirm('请确认该资源是否审核失败？', function (index) {
                    //在该页面删除这条属性，并ajax发起请求
                    ajax("id=" + data.id + "&toExamine=-1");
                    obj.del();
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                layer.confirm('请确认该资源是否通过审核？', function (index) {
                    //在该页面删除这条属性，并ajax发起请求
                    ajax("id=" + data.id + "&toExamine=1");
                    obj.del();//先在该页面删除这条属性，再用ajax发起请求
                    layer.close(index);
                });
            }
        });
    });

    layui.use(['form', 'layedit', 'laydate', 'upload', 'table'], function () {
        var $ = layui.jquery, upload = layui.upload;
        var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit
                , laydate = layui.laydate;
    });

    //发起设置审核状态的请求
    function ajax(data) {
        $.post("/blacktv/acg/admin/resources/set/examine", data, function (result) {
            switch (result.msg) {
                case "msg025":
                    //成功就不做提示了
                    break;
                case "msg026":
                    layui.use('layer', function () {
                        var layer = layui.layer;
                        layer.msg('资源审核状态设置失败');
                    });
                    break;
            }
        }, "json");
    }
</script>
</body>
</html>