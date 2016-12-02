$(function() {
  $('#recentPay').DataTable({
      "paging":   true,
      "dom": '<"top"fi>rt<"bottom"lp><"clear">',
      "info":     false,
      "searching":false,
      "ordering":false,
      "language": {
          "lengthMenu": "每页显示 _MENU_ 条记录",
          "zeroRecords": "暂无记录",
          "info": "当前显示页 _PAGE_, 共 _PAGES_ 页",
          "infoEmpty": "暂无记录",
          'sSearch':'搜索:',
          'sProcessing':'正在加载中...',
          'oPaginate':{
              'sFirst':'首页',
              'sPrevious':'上一页',
              'sNext':'下一页',
              'sLast':'尾页'
          }
      },
      "ajax": {
          "url": "/recent-pays",
          "dataSrc": ""
      },
      "columns": [
          {"sClass": "text-center","data": "time", "title":'日期'},
          {"sClass": "text-center","data": "hxf", "title":'洪学飞'},
          {"sClass": "text-center","data": "jx", "title":"金潇"},
          {"sClass": "text-center","data": "wc", "title":"魏超"},
          {"sClass": "text-center","data": "totalOfPay","title":'总额'}
       ]
  });
    $('#recentElec').DataTable({
        "paging":   true,
        "dom": '<"top"fi>rt<"bottom"lp><"clear">',
        "info":     false,
        "searching":false,
        "ordering":false,
        "language": {
            "lengthMenu": "每页显示 _MENU_ 条记录",
            "zeroRecords": "暂无记录",
            "info": "当前显示页 _PAGE_, 共 _PAGES_ 页",
            "infoEmpty": "暂无记录",
            'sSearch':'搜索:',
            'sProcessing':'正在加载中...',
            'oPaginate':{
                'sFirst':'首页',
                'sPrevious':'上一页',
                'sNext':'下一页',
                'sLast':'尾页'
            }
        },
        "ajax": {
            "url": "/recent-electrics",
            "dataSrc": ""
        },
        "columns": [
            {"sClass": "text-center","data": "time", "title":'日期'},
            {"sClass": "text-center","data": "hxf", "title":'洪学飞'},
            {"sClass": "text-center","data": "jx", "title":"金潇"},
            {"sClass": "text-center","data": "wc", "title":"魏超"},
            {"sClass": "text-center","data": "pub", "title":"公共"},
            {"sClass": "text-center","data": "totalOfElec","title":'总度数'}
        ]
    });
});

function calculate() {
    $.ajax({
        url: '/calculate',
        type: "POST",
        data: $("#calForm").serialize(),
        dataType:"json",
        success: function(result) {
            if (result.success == true) {
                $('#recentPay').DataTable().ajax.reload();
                $('#recentElec').DataTable().ajax.reload();
            }
        }
    });
}
