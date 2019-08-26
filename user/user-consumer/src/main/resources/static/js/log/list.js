layui.use(['form','layer','table','laydate'],function(){
    var $ = layui.jquery,
        table = layui.table,
        laydate = layui.laydate;

    //日期范围
    laydate.render({
        elem: '#operTime'
        ,range: true
    });

    //用户列表
    table.render({
        elem: '#logList',
        url : '/log/listData',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limits : [10,20,30],
        limit : 10,
        id : "logListTable",
        cols : [[
            {type: "checkbox", fixed:"left", width:50},
            {field: 'userName', title: '用户名', minWidth:100, align:"center"},
            {field: 'operMethod', title: '操作方法', minWidth:100, align:'center'},
            {field: 'requestParam', title: '请求参数', minWidth:100, align:'center'},
            {field: 'operDesc', title: '操作说明', minWidth:100, align:'center'},
            {field: 'createTime', title: '操作时间', minWidth:100, align:'center'}
        ]]
    });

    //搜索
    $(".search_btn").on("click",function(){
        table.reload("logListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: $(".userName").val(),
                operTime: $("#operTime").val()
            }
        })
    });

})