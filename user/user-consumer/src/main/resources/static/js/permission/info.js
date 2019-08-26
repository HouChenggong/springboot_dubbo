layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    $.post("/permission/typeSelectData",function(data){
        var resourceTypeList = data.data;
        resourceTypeList.forEach(function(e){
            $("#resourceTypeSelect").append("<option value='"+e.code+"'>"+e.name+"</option>");
        });
        //编辑
        if($("#resourceType").val()!==""){
            $("#resourceTypeSelect").val($("#resourceType").val());//默认选中
            $("#resourceTypeSelect").attr("disabled","disabled");
            getParentSelectData($("#resourceType").val());
        }
        form.render('select');//重新渲染
    });

    form.on('select(resourceType)', function(data){
        console.log(data.value);//得到被选中的值
        if(data.value!=="menu"){
            $("#url").attr("disabled","disabled");
            $("#url").removeAttr("lay-verify");
            $("#url").removeAttr("placeholder");
        }else{
            $("#url").removeAttr("disabled");
            $("#url").attr("lay-verify","required");
            $("#url").attr("placeholder","请输入资源路径");
        }
        if(data.value==="top_directory"){
            $("#parentIdSelect").attr("disabled","disabled");
            $("#parentIdSelect").removeAttr("lay-verify");
            form.render('select');//重新渲染
            return;
        }else{
            $("#parentIdSelect").removeAttr("disabled");
            $("#parentIdSelect").attr("lay-verify","required");
            form.render('select');//重新渲染
            getParentSelectData(data.value);
        }
    });

    function getParentSelectData(resourceType){
        $.post("/permission/parentSelectData",{resourceType: resourceType},function(data){
            var parentSelectList = data.data;
            $("#parentIdSelect").empty();//清空下拉列表
            $("#parentIdSelect").append("<option value=''>请选择权限</option>");
            parentSelectList.forEach(function(e){
                $("#parentIdSelect").append("<option value='"+e.id+"'>"+e.permissionName+"</option>");
            });
            //编辑
            if($("#parentId").val()!==""){
                $("#parentIdSelect").val($("#parentId").val());//默认选中
                $("#parentIdSelect").attr("disabled","disabled");
                if(resourceType!=="menu"){
                    $("#url").attr("disabled","disabled");
                    $("#url").removeAttr("lay-verify");
                    $("#url").removeAttr("placeholder");
                }
            }
            form.render('select');//重新渲染
        });
    }

    form.on("submit(addPermission)",function(data){
        //弹出loading
        var index = top.layer.msg('数据保存中，请稍候...',{icon: 16,time:false,shade:0.8});
        $.post("/permission/save",data.field,function(res){
            layer.close(index);
            if (res.data){
                layer.msg("保存成功！");
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            } else {
                layer.msg(res.msg);
            }
        });
        return false;
    });

})