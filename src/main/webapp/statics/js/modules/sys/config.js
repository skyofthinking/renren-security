layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;
    var opt = {
        elem: '#table'
        , url: baseURL + 'sys/config/list'
        , cellMinWidth: 100 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , height: 410
        , page: true //开启分页,
        , limit: 10
        , limits: [5, 10, 20, 50]
        , cols: [[
            {type: 'checkbox'}
            , {field: 'uid', width: 100, title: 'ID', sort: true}
            , {field: 'keyword', width: 100, title: '参数名'}
            , {field: 'value', width: 100, title: '参数值'}
        ]],
        request: {
            pageName: 'page', //页码的参数名称，默认：page
            limitName: 'limit' //每页数据量的参数名，默认：limit
        },
        response: {
            countName: 'count', //数据总数的字段名称，默认：count
            dataName: 'data' //数据列表的字段名称，默认：data
        }
    };
    var tableIns = table.render(opt);
    table.on('sort(table)', function (obj) {
        debugger;
        tableIns.reload({
            initSort: obj,
            where: {
                sortName: obj.field,
                sortOrder: obj.type,
                pageName: 'page', //页码的参数名称，默认：page
                limitName: 'limit' //每页数据量的参数名，默认：limit
            }
        })
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            keyword: null
        },
        showList: true,
        title: null,
        config: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.config = {};
        },
        update: function () {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }

            $.get(baseURL + "sys/config/info/" + id, function (r) {
                vm.showList = false;
                vm.title = "修改";
                vm.config = r.config;
            });
        },
        del: function (event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/config/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.config.uid == null ? "sys/config/save" : "sys/config/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.config),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            layui.use('table', function () {
                var table = layui.table;
                //执行重载
                table.reload('table', {
                    page: {
                        curr: 1 //重新从第 1 页开始
                    }
                    , where: {
                        'keyword': vm.q.keyword
                    }
                });
            });
        }
    }
});