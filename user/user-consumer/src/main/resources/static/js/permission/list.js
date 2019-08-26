layui.config({
    base : "/static/js/modules/"
}).extend({
    "treetable" : "treetable/treetable",
    "common" : "common"
});
layui.use(['form', 'table', 'treetable', 'common'], function () {
    var form = layui.form;
    var $ = layui.jquery;
    var table = layui.table;
    var treetable = layui.treetable;
    var common = layui.common;

    // 渲染表格
    layer.load(2);
    treetable.render({
        treeColIndex: 1,
        treeSpid: 0,
        treeIdName: 'id',
        treePidName: 'parentId',
        elem: '#auth-table',
        url: '/permission/listData',
        page: false,
        id: 'permissionListTable',
        cols: [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'permissionName', minWidth: 100, title: '权限名称'},
            {field: 'permissionCode', width: 200, title: '权限编码'},
            {field: 'url', minWidth: 100, title: '资源路径'},
            {
                field: 'resourceType', width: 200, align: 'center', templet: function (d) {
                    if(d.resourceType === "top_directory"){
                        return '<span class="layui-badge layui-bg-blue">顶级目录</span>';
                    }else if (d.resourceType === "directory") {
                        return '<span class="layui-badge layui-bg-blue">目录</span>';
                    }else if (d.resourceType === "menu") {
                        return '<span class="layui-badge layui-badge-rim">菜单</span>';
                    } else if (d.resourceType === "button")  {
                        return '<span class="layui-badge layui-bg-gray">按钮</span>';
                    }
                }, title: '资源类型'
            },
            {
                field: 'available', width: 200, align: 'center', templet: function (d) {
                    if (d.available === 1) {
                        return '<span class="layui-badge layui-bg-green">启用</span>';
                    } else {
                        return '<span class="layui-badge layui-bg-cyan">禁用</span>';
                    }
                }, title: '权限状态'
            }
        ]],
        done: function () {
            layer.closeAll('loading');
        }
    });

    //添加权限
    function addPermission(edit){
        var title = edit===null?"添加权限":"编辑权限";
        layui.layer.open({
            title : title,
            type : 2,
            area : ["500px","450px"],
            content : "info.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find("#id").val(edit.id);
                    body.find(".permissionName").val(edit.permissionName);
                    body.find(".permissionCode").val(edit.permissionCode);
                    body.find("#url").val(edit.url);
                    body.find("#availableSelect").val(edit.available);
                    body.find("#resourceType").val(edit.resourceType);
                    body.find("#parentId").val(edit.parentId);
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回权限列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
    }

    $(".add_btn").click(function(){
        addPermission(null);
    });

    $(".edit_btn").click(function(){
        var checkStatus = table.checkStatus('permissionListTable'),
            data = checkStatus.data;
        console.log(data);
        if(data.length > 0){
            addPermission(data[0]);
        }else{
            layer.msg("请选择需要修改的权限");
        }
    });

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('permissionListTable'),
            data = checkStatus.data,
            idArr = [];
        if(data.length > 0) {
            for (var i in data) {
                if(data.length>1&&data[i].resourceType!=="button"){
                    layer.msg("抱歉，只支持批量删除按钮！");
                    return;
                }
                idArr.push(data[i].id);
            }
            layer.confirm('确定删除选中的权限？', {icon: 3, title: '提示信息'}, function (index) {
                $.ajax({
                    url: "/permission/delBatch",
                    type: "post",
                    data: {idArr : idArr.toString()},
                    success: function(res){
                        layer.close(index);
                        if (res.data){
                            layer.msg("删除成功！");
                        } else {
                            layer.msg(res.msg);
                        }
                    },
                    error: function (xmlHttpRequest) {
                        layer.close(index);
                        common.outErrorMsg(xmlHttpRequest);
                    }
                });
                setTimeout(function(){
                    location.reload();//刷新页面
                },1500);
            });
        }else{
            layer.msg("请选择需要删除的权限");
        }
    });

});