layui.use(['form', 'layer'], function () {
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(saveUser)", function (data) {
        //弹出loading
        var index = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
        $.ajax({
            type: "POST",
            url: baseURL + "sys/config/save",
            contentType: "application/json",
            data: JSON.stringify(
                {
                    uid: $(".uid").val(),
                    keyword: $(".keyword").val(),
                    value: $(".value").val(),
                    remarks: $(".remarks").text(),
                }
            ),
            success: function (r) {
                if (r.code === 0) {
                    top.layer.close(index);
                    top.layer.msg("提交成功", {icon: 1});
                    layer.closeAll("iframe");
                    //刷新父页面
                    parent.location.reload();
                } else {
                    top.layer.msg(r.msg, {icon: 2});
                }
            }
        });
        return false;
    })
})