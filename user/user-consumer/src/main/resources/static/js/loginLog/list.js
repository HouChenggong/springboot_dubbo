layui.use(['form','layer','table','laydate'],function(){
    var $ = layui.jquery,
        table = layui.table,
        laydate = layui.laydate;

    //日期范围
    laydate.render({
        elem: '#loginTime'
        ,range: true
    });

    //用户列表
    table.render({
        elem: '#loginLogList',
        url : '/loginLog/listData',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,20,30],
        limit : 10,
        id : "loginLogListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'userName', title: '用户名', minWidth:100, align:"center"},
            {field: 'ipAddress', title: 'IP地址', minWidth:100, align:'center'},
            {field: 'geographyLocation', title: '登录地点', minWidth:100, align:'center'},
            {field: 'createTime', title: '登录时间', minWidth:100, align:'center'}
        ]]
    });

    //搜索
    $(".search_btn").on("click",function(){
        table.reload("loginLogListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: $(".userName").val(),
                loginTime: $("#loginTime").val()
            }
        })
    });

})