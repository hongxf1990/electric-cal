$(function() {
  var table =  $('#recentPay').DataTable({
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
      "columns": [
          {"sClass": "text-center", "data": "time", "title":'日期', "width": "20%"},
          {"sClass": "text-center", "data": "hxf", "title":'洪学飞', "width": "20%"},
          {"sClass": "text-center", "data": "jx", "title":"金潇", "width": "20%"},
          {"sClass": "text-center", "data": "wc", "title":"魏超", "width": "20%"},
          {"sClass": "text-center", "data": "totalOfPay","总额":'操作',"width":"20%"}
       ],
       //"order": [[1, 'asc']],
       "ajax": {
         "data":{
        },
          "url" : bp() + '/findSubscribeTimeList_TeacherLearn.action',
          "dataSrc": "avaliableCamps",
          "error": function(){
              //sweetAlert("请求数据出错，请稍后再试....")
          }
      },
      "columnDefs":[]
  });
});
