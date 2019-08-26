layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    //添加验证规则
    form.verify({
        newPwd : function(value, item){
            if(value.length < 6){
                return "密码长度不能小于6位";
            }
        },
        confirmPwd : function(value, item){
            if(!new RegExp($("#newPwd").val()).test(value)){
                return "两次输入密码不一致，请重新输入！";
            }
        }
    })

    form.on("submit(changePwd)",function(data){
        //弹出loading
        var index = top.layer.msg('修改密码中，请稍候...',{icon: 16,time:false,shade:0.8});
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