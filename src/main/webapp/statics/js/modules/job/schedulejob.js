$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'schedulejob/list',
        datatype: "json",
        colModel: [
            {label: 'uid', name: 'uid', index: 'uid', width: 50, key: true, hidden: true},
            {label: '任务组', name: 'jobGroup', index: 'job_group', width: 80},
            {label: '定时任务类', name: 'jobClass', index: 'job_class', width: 80},
            {label: 'Cron表达式', name: 'cronExpression', index: 'cron_expression', width: 80},
            {
                label: '状态', name: 'status', width: 60, formatter: function (value, options, row) {
                    return value === 0 ?
                        '<span class="label label-success">正常</span>' :
                        '<span class="label label-danger">暂停</span>';
                }
            },
            {label: '描述', name: 'description', index: 'description', width: 80},
            {label: '', name: 'data', index: 'data', width: 80, hidden: true}
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

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        scheduleJob: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.scheduleJob = {};
        },
        update: function (event) {
            var uid = getSelectedRow();
            if (uid == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(uid)
        },
        saveOrUpdate: function (event) {
            var url = vm.scheduleJob.uid == null ? "schedulejob/insert" : "schedulejob/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.scheduleJob),
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
        del: function (event) {
            var uids = getSelectedRows();
            if (uids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "schedulejob/delete",
                    contentType: "application/json",
                    data: JSON.stringify(uids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (uid) {
            $.get(baseURL + "schedulejob/info/" + uid, function (r) {
                vm.scheduleJob = r.scheduleJob;
            });
        },
        pause: function (event) {
            var jobIds = getSelectedRows();
            if(jobIds == null){
                return ;
            }

            confirm('确定要暂停选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "schedulejob/pause",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        resume: function (event) {
            var jobIds = getSelectedRows();
            if(jobIds == null){
                return ;
            }

            confirm('确定要恢复选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "schedulejob/resume",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        runOnce: function (event) {
            var jobIds = getSelectedRows();
            if(jobIds == null){
                return ;
            }

            confirm('确定要立即执行选中的记录？', function(){
                $.ajax({
                    type: "POST",
                    url: baseURL + "schedulejob/run",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
                    success: function(r){
                        if(r.code == 0){
                            alert('操作成功', function(index){
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});