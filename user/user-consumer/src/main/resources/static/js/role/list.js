layui.use(['form','layer','table'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    //角色列表
    var tableIns = table.render({
        elem: '#roleList',
        url : '/role/listData',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,20,30],
        limit : 10,
        id : "roleListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'roleCode', title: '角色编码', minWidth:100, align:"center"},
            {field: 'roleName', title: '角色名称', minWidth:200, align:'center'},
            {field: 'available', title: '角色状态',  align:'center',templet:function(d){
                if (d.available === 1) {
                    return '<span class="layui-badge layui-bg-green">启用</span>';
                } else {
                    return '<span class="layui-badge layui-bg-cyan">禁用</span>';
                }
            }},
            {field: 'updateTime', title: '修改时间', align:'center',minWidth:150},
            {field: 'createTime', title: '创建时间', align:'center',minWidth:150}
        ]]
    });

    //搜索
    $(".search_btn").on("click",function(){
        table.reload("roleListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                roleCode: $(".roleCode").val(),
                roleName: $(".roleName").val(),
                available: $(".available").val()
            }
        })
    });

    //添加角色
    function addRole(edit){
        var title = edit===null?"添加角色":"编辑角色";
        layui.layer.open({
            title : title,
            type : 2,
            area : ["550px","700px"],
            content : "info.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".roleCode").val(edit.roleCode);
                    body.find(".roleName").val(edit.roleName);
                    body.find(".available").val(edit.available);
                    body.find("#id").val(edit.id);
                    body.find("#permissionIds").val(edit.permissionIds);
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回角色列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
    }

    $(".add_btn").click(function(){
        addRole(null);
    });

    $(".edit_btn").click(function(){
        var checkStatus = table.checkStatus('roleListTable'),
            data = checkStatus.data;
        if(data.length > 0){
            addRole(data[0]);
        }else{
            layer.msg("请选择需要修改的角色");
        }
    });

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('roleListTable'),
            data = checkStatus.data,
            idArr = [];
        if(data.length > 0) {
            for (var i in data) {
                idArr.push(data[i].id);
            }
            layer.confirm('确定删除选中的角色？', {icon: 3, title: '提示信息'}, function (index) {
                $.get("/role/delBatch",{
                    idArr : idArr.toString()
                },function(data){
                    layer.close(index);
                    tableIns.reload();
                    if (data.data){
                        layer.msg("删除成功！");
                    } else {
                        layer.msg(data.msg);
                    }
                })
            })
        }else{
            layer.msg("请选择需要删除的角色");
        }
    })

})
