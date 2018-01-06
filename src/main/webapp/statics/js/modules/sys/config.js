$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/config/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'uid', width: 30, key: true, hidden: true},
            {label: '参数名', name: 'keyword', width: 60},
            {label: '参数值', name: 'value', width: 100},
            {label: '备注', name: 'remarks', width: 80}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

});

layui.use('table', function(){
    var table = layui.table;
    console.log('aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');

    table.render({
        elem: '#test'
        ,url:baseURL + 'sys/config/list'
        ,where: {sidx: '', order: ''}
        ,response: {countName: 'totalCount', dataName: 'list'}
        ,cellMinWidth: 80 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        ,cols: [[
            {field:'uid', width:80, title: 'ID', sort: true}
            ,{field:'keyword', width:80, title: '参数名'}
            ,{field:'value', width:80, title: '参数值'}
        ]]
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
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'keyword': vm.q.keyword},
                page: page
            }).trigger("reloadGrid");
        }
    }
});