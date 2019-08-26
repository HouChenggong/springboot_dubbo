layui.use(['form','layer','table'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;

    var roleList;
    $.post("/role/selectListData",{
        available : 1
    },function(data){
        roleList = data.data;
        roleList.forEach(function(e){
            $("#roleSelect").append("<option value='"+e.id+"'>"+e.roleName+"</option>");
        });
        form.render('select');//刷新select选择框渲染
    });

    //用户列表
    var tableIns = table.render({
        elem: '#userList',
        url : '/user/listData',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,20,30],
        limit : 10,
        id : "userListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'userName', title: '登录名称', minWidth:100, align:"center"},
            {field: 'name', title: '用户名称', minWidth:100, align:'center'},
            {field: 'roleId', title: '角色名称', minWidth:100, align:'center',templet:function(d){
                var name = "";
                roleList.forEach(function(e){
                    if (e.id === d.roleId){
                        name = e.roleName;
                    }
                });
                return name;
            }},
            {field: 'state', title: '用户状态', minWidth:100, align:'center',templet:function(d){
                if (d.state === 1) {
                    return '<span class="layui-badge layui-bg-green">启用</span>';
                } else if (d.state === 0) {
                    return '<span class="layui-badge layui-bg-cyan">禁用</span>';
                } else {
                    return '<span class="layui-badge layui-bg-orange">锁定</span>';
                }
            }},
            {field: 'updateTime', title: '修改时间', align:'center',minWidth:100},
            {field: 'createTime', title: '创建时间', align:'center',minWidth:100}
        ]]
    });

    //搜索
    $(".search_btn").on("click",function(){
        table.reload("userListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: $(".userName").val(),
                name: $(".name").val(),
                state: $(".state").val(),
                roleId: $(".roleId").val()
            }
        })
    });

    //添加用户
    function addUser(edit){
        var h = "440px";
        var title = "添加用户";
        if (edit){
            h = "280px";
            title = "编辑用户";
        }
        layui.layer.open({
            title : title,
            type : 2,
            area : ["420px",h],
            content : "info.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find("#une").remove();
                    body.find("#pwd1").remove();
                    body.find("#pwd2").remove();
                    body.find("#id").val(edit.id);
                    body.find("#name").val(edit.name);
                    body.find("#roleId").val(edit.roleId);
                    body.find("#stateSelect").val(edit.state);
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
    }

    $(".add_btn").click(function(){
        addUser();
    });

    $(".edit_btn").click(function(){
        var checkStatus = table.checkStatus('userListTable'),
            data = checkStatus.data;
        if(data.length > 0){
            addUser(data[0]);
        }else{
            layer.msg("请选择需要修改的用户");
        }
    });

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('userListTable'),
            data = checkStatus.data,
            idArr = [];
        if(data.length > 0) {
            for (var i in data) {
                idArr.push(data[i].id);
            }
            layer.confirm('确定删除选中的用户？', {icon: 3, title: '提示信息'}, function (index) {
                $.get("/user/delBatch",{
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
            layer.msg("请选择需要删除的用户");
        }
    })

})
