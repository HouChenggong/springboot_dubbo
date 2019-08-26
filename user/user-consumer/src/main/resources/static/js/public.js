layui.use(['jquery',"layer"],function() {
    var $ = layui.jquery,
        layer = parent.layer === undefined ? layui.layer : top.layer;

    //错误信息提示
    function outErrorMsg(xmlHttpRequest){
        // 状态码
        console.log(xmlHttpRequest.status);
        // 返回信息
        var jsonObj = $.parseJSON(xmlHttpRequest.responseText);
        console.log(jsonObj.msg);
        layer.msg(jsonObj.msg);
    }

});