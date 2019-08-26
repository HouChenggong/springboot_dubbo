layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    var permissionXtree;
    $.post("/permission/xtreeData",function(res){
        var json = res.data;
        permissionXtree = new xtree({
            elem: 'permissionXtree', //(必填) 放置xtree的容器
            form: form, //(必填) layui 的 from
            data: json,  //(必填) json数据
            icon: {
                end: "" //末尾节点的图标
            }
        });
        setAllChecked(); //默认选中
    })

    //获得所有选中的checkbox
    function getAllChecked(){
        var permissionStr = "";
        var oCks = permissionXtree.GetAllCheckBox(); //获取全部checkbox
        for (var i = 0; i < oCks.length; i++) {
            if(oCks[i].checked){
                permissionStr += ","+oCks[i].value;
            }
        }
        if (permissionStr!==""){
            permissionStr += ",";
        }
        console.log(permissionStr);
        return permissionStr;
    }

    //设置所有需要选中的checkbox
    function setAllChecked(){
        var permissionIds = $("#permissionIds").val();
        if (permissionIds===""){
            return;
        }
        var oCks = permissionXtree.GetAllCheckBox(); //获取全部checkbox
        for (var i = 0; i < oCks.length; i++) {
            if(permissionIds.indexOf(","+oCks[i].value+",") != -1){
                oCks[i].checked = true;
            }
        }
        form.render(); //重新渲染
    }

    form.on("submit(addRole)",function(data){
        data.field.permissionIds = getAllChecked();
        //弹出loading
        var index = top.layer.msg('数据保存中，请稍候...',{icon: 16,time:false,shade:0.8});
        $.post("/role/save",data.field,function(res){
            if (res.data){
                layer.close(index);
                layer.msg("保存成功！");
                layer.closeAll("iframe");
                //刷新父页面
                parent.location.reload();
            } else {
                layer.msg(data.msg);
            }
        });
        return false;
    })

})