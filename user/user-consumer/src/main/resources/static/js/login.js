layui.config({
    base : "/static/js/modules/"
}).extend({
    "common" : "common"
})
layui.use(['form','layer','jquery','common'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        common = layui.common;

    //表单输入效果
    $(".loginBody .input-item").click(function(e){
        e.stopPropagation();
        $(this).addClass("layui-input-focus").find(".layui-input").focus();
    })
    $(".loginBody .layui-form-item .layui-input").focus(function(){
        $(this).parent().addClass("layui-input-focus");
    })
    $(".loginBody .layui-form-item .layui-input").blur(function(){
        $(this).parent().removeClass("layui-input-focus");
        if($(this).val() != ''){
            $(this).parent().addClass("layui-input-active");
        }else{
            $(this).parent().removeClass("layui-input-active");
        }
    });

    $("#imgCode img").click(function() {
        this.src = "/kaptcha/getKaptchaImage?rnd="+Math.random();
    });

    //登录按钮
    form.on("submit(login)",function(data){
        $("#login").text("登录中...").attr("disabled","disabled").addClass("layui-disabled");
        $.ajax({
            url: "/login",
            type: "post",
            data: data.field,
            success: function(){
                location.href = "/";
            },
            error: function (xmlHttpRequest) {
                $("#login").text("登录").removeAttr("disabled").removeClass("layui-disabled");
                common.outErrorMsg(xmlHttpRequest);
            }
        });
        return false;
    });

})