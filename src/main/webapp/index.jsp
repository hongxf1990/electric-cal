<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>宿舍电费计算系统</title>
    <link href="jslib/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="jslib/datatables/css/jquery.dataTables.min.css" media="screen" title="no title">
</head>
<body>
<h1 class="text-center">电费计算系统</h1>
<h2>近期缴费记录</h2>
<table id="recentPay"></table>
<h2>近期用电情况</h2>
<table id="recentElec"></table>
<h2>计算本月用电情况</h2>
<form id="calForm">
    总费用：<input type="text" name="totalOfPay">
    总度数：<input type="text" name="totalOfElectric">
    洪电表数：<input type="text" name="hxf">
    金电表数：<input type="text" name="jx">
    魏电表数：<input type="text" name="wc">
    <input type="button" value="计算" onclick="calculate()">
</form>

<script src="jslib/datatables/js/jquery.js" charset="utf-8"></script>
<script src="jslib/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="jslib/datatables/js/jquery.dataTables.min.js" charset="utf-8"></script>
<script src="js/index.js" charset="utf-8"></script>
</body>
</html>
