layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    // 列表
    var tableIns = table.render({
        elem: '#table',
        url: baseURL + 'systest/list',
        cellMinWidth: 95,
        page: true,
        height: "full-200",
        limits: [10, 20, 50, 100],
        limit: 10,
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
							                    {field: 'uid', minWidth: 100, title: 'uid', sort: true},
											                    {field: 'keyword', minWidth: 100, title: '参数名', sort: true},
											                    {field: 'value', minWidth: 100, title: '参数值', sort: true},
											                    {field: 'status', minWidth: 100, title: '状态   0：隐藏   1：显示', sort: true},
							            {title: '操作', minWidth: 100, templet: '#tableListBar', fixed: "right", align: "center"}
        ]]
    });

    // 搜索
    $(".search_btn").on("click", function () {
        table.reload("table", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                keyword: $(".search_uid").val()  //搜索的关键字
            }
        })
    });

    // 添加或修改
    function info(edit) {
        if (edit) {
            var loadData = layer.load(2, {shade: 0.1});

            $.get(baseURL + 'systest/info/' + edit.uid, function (data) {
                info_submit(edit, data, loadData);
            });
        } else {
            info_submit(edit)
        }
    }

    function info_submit(edit, data, loadData) {
        var info_title = "";
        if (edit) {
            info_title = "新增"
        } else {
            info_title = "修改"
		}
        var index = layui.layer.open({
            title: info_title,
            type: 2,
            content: "sysTestForm.html",
            success: function (layero, index) {
                var body = layui.layer.getChildFrame('body', index);

                if (edit) {
					                        body.find(".uid").val(data.sysTest.uid);
					                        body.find(".keyword").val(data.sysTest.keyword);
					                        body.find(".value").val(data.sysTest.value);
					                        body.find(".status").val(data.sysTest.status);
					                    layer.close(loadData);
                }

                form.render();
                setTimeout(function () {
                    layui.layer.tips('点击此处返回列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                }, 500)
            }
        })
        layui.layer.full(index);
        //改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
        $(window).on("resize", function () {
            layui.layer.full(index);
        })
    }

    $(".add_btn").click(function () {
        info();
    })

    // 批量删除
    $(".delete_all_btn").click(function () {
        var checkStatus = table.checkStatus('table'),
            data = checkStatus.data,
            uid = [];
        if (data.length > 0) {
            for (var i in data) {
                uid.push(data[i].uid);
            }
            delete_submit(uid);
        } else {
            layer.msg("请选择需要删除的信息");
        }
    })

    function delete_submit(uid) {
        layer.confirm('确定删除？', {icon: 3, title: '提示信息'}, function (index) {
            $.ajax({
                type: "POST",
                url: baseURL + 'systest/delete',
                contentType: "application/json",
                data: JSON.stringify(uid),
                success: function (r) {
                    if (r.code === 0) {
                        layer.msg('删除成功', {icon: 1});
                        tableIns.reload();
                        layer.close(index);
                    } else {
                        layer.msg(r.msg, {icon: 2});
                    }
                }
            });
        })
    }

    //列表操作
    table.on('tool(table)', function (obj) {
        var layEvent = obj.event, data = obj.data;

        if (layEvent === 'edit_btn') { //编辑
            info(data);
        } else if (layEvent === 'delete_btn') { //删除
            var uid = [];
            uid.push(data.uid);
            delete_submit(uid);
        }
    });

})