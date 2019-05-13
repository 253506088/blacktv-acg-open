<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>投稿资源</title>
    <link rel="stylesheet" href="/layui/css/layui.css">
    <script src="/layui/layui.js"></script>
    <script src="/js/jquery-3.2.1.js"></script>
    <script src="/js/jquery.cookie.js"></script>
    <link rel="shortcut icon" href="/tvacg/images/favicon.png">
    <link rel="apple-touch-icon-precomposed" href="/tvacg/images/favicon-apple.png">
</head>
<body>
<blockquote class="layui-elem-quote layui-text">
    注意：请勿上传淫秽色情内容，否则后果自负，与本网站无关，网站资源全部人工审核。如果您修改投稿时出现某一项的值为空，请按F5刷新一下本页面。
</blockquote>
<div class="layui-upload">
    <form class="layui-form" method="put" lay-filter="example" id="form">
        <div class="layui-form-item">
            <label class="layui-form-label">稿件标题</label>
            <div class="layui-input-block">
                <input type="text" name="title" lay-verify="title" id="title" autocomplete="off" placeholder="请输入标题"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">资源类型</label>
            <div class="layui-input-block">
                <select name="typeId" lay-filter="aihao" id="typeId">
                    <option value='1'>动漫</option>
                    <option value='2'>图片</option>
                    <option value='3'>音乐</option>
                    <option value='4'>影视</option>
                    <option value='5'>游戏</option>
                    <option value='7'>小说</option>
                    <option value='6'>其他</option>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">资源来源</label>
            <div class="layui-input-block">
                <input type="text" name="from" id="from" lay-verify="title" autocomplete="off" placeholder="请输入来源，例如互联网"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">稿件备注</label>
            <div class="layui-input-block">
                <input type="text" name="remarks" id="remarks" lay-verify="title" autocomplete="off"
                       placeholder="请输入备注，一般填写提取码、解压密码等"
                       class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">下载地址</label>
            <div class="layui-input-block">
                <input type="text" name="download" id="download" lay-verify="title" autocomplete="off"
                       placeholder="请输入下载地址，各大网盘、ed2k、磁力链接皆可"
                       class="layui-input">
            </div>
        </div>

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>上传投稿封面，最大支持2mb</legend>
        </fieldset>
        <button type="button" class="layui-btn" id="test1">上传图片</button>
        <div class="layui-upload-list">
            <img class="layui-upload-img" id="demo1" height="120px">
            <p id="demoText"></p>
        </div>

        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
            <legend>请编写资源描述</legend>
        </fieldset>
        <!-- 富文本编辑器 -->
        <textarea class="layui-textarea" id="describe" style="display: none"
                  name="describe"><#--在这里写入内容可以初始化富文本--></textarea>
        <div style="margin-bottom: 20px; width: 500px;">
            <textarea class="layui-textarea" id="LAY_demo2" style="display: none"></textarea>
        </div>

        <div class="layui-form-item">
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
            </div>
        </div>
    </form>
</div>

<script>
    var url = "/resources/add";//默认是上传资源
    $(document).ready(function () {
        console.log(url);
        ini();
        console.log(url);
    });

    //初始化方法，如果是修改稿件内容则初始化表单
    function ini() {
        var id = UrlSearch("id");
        if (id > 0) {
            url = "/resources/update";//url修改为修改资源
            $.get("/resources/get/byId", "id=" + id+"&jwt="+$.cookie('jwt'), function (result) {
                if (result != null) {
                    //初始化表单
                    $("#title").val(result.title);
                    $("#from").val(result.from);
                    $("#remarks").val(result.remarks);
                    $("#download").val(result.download);
                    $("#describe").text(result.describe);
                    $('#demo1').attr('src', result.coverFilename); //图片链接（base64）
                    document.getElementById('typeId').value = result.typeId;//让下拉菜单选中当前资源的分类
                    imgUpload = true;//因为是修改资源，默认是有旧封面的

                    //完事后重新渲染layui表单
                    layui.use('form', function(){
                        var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
                        form.render();
                    });
                }
            }, "json");
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

    //获取资源分类，并加载到选择框的选项中——已破产，layui不支持dom修改，修改后无效果
    function getTypes() {
        $.get("/resources/get/type", null, function (result) {
            console.log(result);
            console.log(result.length);
            for (var i = 0; i < result.length; i++) {
                console.log("<option value='" + result[i].id + "'>" + result[i].name + "</option>")
                $("#typeId").append("<option value='" + result[i].id + "'>" + result[i].name + "</option>");
            }
        }, "json");
    }

    var imgUpload = false;//默认未上传封面
    var flag = false;//投稿成功后为true
    layui.use(['form', 'layedit', 'laydate', 'upload'], function () {
        var $ = layui.jquery, upload = layui.upload;

        var form = layui.form
                , layer = layui.layer
                , layedit = layui.layedit
                , laydate = layui.laydate;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#test1'
            , url: '/resources/upload/cover'
            , field: 'file'
            , method: 'post'
            , data: { //用data提交jwt属性
                jwt: $.cookie("jwt")
            }
            , accept: 'images' //只允许上传图片
            , size: 2048 //上传文件最大2mb
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            }
            , done: function (res) {
                //如果上传失败
                if (res.uoloadFlag == false) {
                    return layer.msg('上传失败');
                    imgUpload = false;//设置为未上传封面
                } else {
                    imgUpload = true;//设置为已上传封面
                    //如果是修改资源，上传封面后，之前的旧封面就没必要了，所以这里删除掉
                    if ($("#coverFilename").length > 0) {
                        $("#coverFilename").remove();
                    }
                }
            }
            , error: function () {
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-mini demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function () {
                    uploadInst.upload();
                });
            }
        });

        //富文本
        var layedit = layui.layedit, $ = layui.jquery;

        layedit.set({
            uploadImage: {
                url: '/resources/upload/describe/img' //接口url
                , type: 'post' //默认post
            }
        });

        //构建一个默认的编辑器
        var index = layedit.build('describe');

        //编辑器外部操作
        var active = {};

        $('.site-demo-layedit').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //监听提交,这里不用layui的提交方法，自己写一个ajax，只是用了layui监听提交按钮
        form.on('submit(demo1)', function (data) {

            if (!imgUpload) {
                layer.msg("请上传封面后再投稿");
                return false;//true 允许layui提交，false则反之
            }
            if ($("#title").val().length < 1) {
                layer.msg("请输入稿件标题");
                return false;//true 允许layui提交，false则反之
            }
            if ($("#from").val().length < 1) {
                layer.msg("请输入资源来源");
                return false;//true 允许layui提交，false则反之
            }
            if ($("#remarks").val().length < 1) {
                layer.msg("请输入资源备注");
                return false;//true 允许layui提交，false则反之
            }
            if ($("#download").val().length < 1) {
                layer.msg("请输入下载链接");
                return false;//true 允许layui提交，false则反之
            }
            if (layedit.getContent(index).length < 1) {
                layer.msg("请输入资源描述");
                return false;//true 允许layui提交，false则反之
            }
            if (flag == true) {
                layer.msg("请不要连续提交同一资源");
                return false;//true 允许layui提交，false则反之
            }
            /**
             * jquery无法获取富文本框的值，但是能获取到name，用layui获取富文本框值的方法获取值.
             * jquery是根据文档从上到下的顺序获取值，富文本是最后一个，所以在最后.
             * 随便输入一些值，serialize方法获取到的内容：
             * title=aaaa&typeId=1&from=cccc&remarks=ddd&download=kkklklkll&describe=
             * 随便输入一些值，layedit.getContent方法获取到的值
             * askdjaksldasdassadasdas
             */
            var data = $("#form").serialize() + layedit.getContent(index);
            data += "&jwt=" + $.cookie('jwt');
            //判断是否是更新资源
            if (url == "/resources/update") {
                data += "&id=" + UrlSearch("id");
            }
            $.ajax({
                "url": url,
                "type": "put",
                "data": data,
                "dataType": "json",
                "success": function (result) {
                    switch (result.msg) {
                        case "msg015":
                            layer.msg('未上传封面或封面失效，请重新上传封面');
                            break;
                        case "msg016":
                            layer.msg('投稿成功，请等待审核');
                            flag = true;
                            setTimeout(function () {
                                window.location.reload();
                            }, 1500);// 1.5秒后刷新本页面
                            break;
                        case "msg031":
                            layer.msg('资源修改成功，请等待审核');
                            flag = true;
                            setTimeout(function () {
                                window.location.href = "/resources/user/admin";
                            }, 1500);// 1.5秒后回到资源管理页面
                            break;
                        case "msg032":
                            layer.msg('资源修改失败');
                            break;
                        default:
                            console.log("未知信息:" + result.msg);
                    }
                },
                "error": function () {
                    console.log("接口异常");
                }
            });
            return false;//true 允许layui提交，false则反之
        });
    });
</script>
</body>
</html>