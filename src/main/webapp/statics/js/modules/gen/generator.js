layui.use(['form', 'layer', 'table', 'laytpl'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laytpl = layui.laytpl,
        table = layui.table;

    // 列表
    var tableIns = table.render({
        elem: '#table',
        url: baseURL + 'sys/generator/list',
        cellMinWidth: 95,
        page: true,
        height: "full-200",
        limits: [10, 20, 50, 100],
        limit: 10,
        cols: [[
            {type: "checkbox", fixed: "left", width: 50},
            {field: 'tableName', minWidth: 100, title: '表名', sort: true},
            {field: 'engine', minWidth: 100, title: 'Engine'},
            {field: 'tableComment', minWidth: 100, title: '表备注'},
            {field: 'createTime', minWidth: 100, title: '创建时间'}
        ]]
    });

    // 搜索
    $(".search_btn").on("click", function () {
        table.reload("table", {
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                keyword: $(".search_tableName").val()  //搜索的关键字
            }
        })
    });

    // 生成代码
    $(".generator_btn").click(function () {
        var checkStatus = table.checkStatus('table'),
            data = checkStatus.data,
            tableNames = [];
        if (data.length > 0) {
            for (var i in data) {
                tableNames.push(data[i].tableName);
            }
            location.href = baseURL + "sys/generator/code?tables=" + JSON.stringify(tableNames);
        } else {
            return ;
        }
    })
})