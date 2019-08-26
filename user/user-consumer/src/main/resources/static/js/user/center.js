layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    $.post("/user/centerDate",function(res){
        $("#id").val(res.data.id);
        $("#name").val(res.data.name);
        $("#userName").val(res.data.userName);
        $("#roleName").val(res.data.roleInfo.roleName);
        $("#state").val(res.data.state);
        $("#stateStr").val(res.data.state===1?"正常使用":"限制使用");
        $("#userFace").attr("src","/static/images/face.jpg");
    });

    form.on("submit(editUser)",function(data){
        //弹出loading
        var index = top.layer.msg('数据保存中，请稍候...',{icon: 16,time:false,shade:0.8});
        $.post("/user/userEdit",data.field,function(res){
            if (res.data){
                layer.close(index);
                layer.msg("修改成功！");
                //刷新父页面
                parent.location.reload();
            } else {
                layer.msg(data.msg);
            }
        })
        return false;
    })

})