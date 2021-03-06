<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="ThemeBucket">
<title>Welcome</title>
<script src="/weiduo/plugin/script/jquery-1.10.2.min.js"></script>
<link href="/weiduo/plugin/css/style.css" rel="stylesheet">
<link href="/weiduo/plugin/css/style-responsive.css" rel="stylesheet">
<link href="/weiduo/plugin/css/jasny-bootstrap.min.css" rel="stylesheet">


<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="/weiduo/plugin/script/html5shiv.js"></script>
  <script src="/weiduo/plugin/script/respond.min.js"></script>
  <![endif]-->
<script>
	$(document).ready(function() {
		if(!<%=session.getAttribute("role_id")%>){
			alert("登录信息过期，请登录");
			location.href = "/weiduo/login"
		}else{
		var role_id = '<%=session.getAttribute("role_id")%>';
		var account_name = '<%=session.getAttribute("account_name")%>' ;
		$("#account_name").html(account_name+'<span class="caret"></span>');
		$("#account_name2").html(account_name+'<span class="caret"></span>');
		$.ajax({
			url : "/weiduo/menux",
			data : {
				i : role_id
			},
			type : "post",
			dataType : "json",
			success : function(data) {
				console.log(data);
				$.each(data,function(i,v){  
					var $li; 
					if(v.pid==0){
						$li='<li class="menu-list"><a href="#"><i class="'+v.icon+'"></i><span>'+v.menuText+'</span></a><ul class="sub-menu-list"></ul></li>';
						$("#menuBar").append($li);
					}if(v.children && v.children.length > 0){ 
						var m= $("#menuBar li:last ul")
						$.each(v.children,function(ii,vv){ 
						m.append('<li><a href="'+vv.url+'"> '+vv.menuText+'</a></li>') 
						})  
						
					} 
					
				})

			},
			error : function() {
				console.log("失败");
			}
		})
		 jQuery('#menuBar').on("click",".menu-list > a",function() {
		      
		      var parent = jQuery(this).parent();
		      var sub = parent.find('> ul');
		      
		      if(!jQuery('body').hasClass('left-side-collapsed')) {
		         if(sub.is(':visible')) {
		            sub.slideUp(200, function(){
		               parent.removeClass('nav-active');
		               jQuery('.main-content').css({height: ''});
		               mainContentHeightAdjust();
		            });
		         } else {
		            visibleSubMenuClose();
		            parent.addClass('nav-active');
		            sub.slideDown(200, function(){
		                mainContentHeightAdjust();
		            });
		         }
		      } 
		      return false;
		   });
		
		$("#upload_sub").on("click", function() {
			console.log(jsonobj1);
			var jsonstr = JSON.stringify(jsonobj1);
			console.log(jsonstr);
			$.ajax({
				type : "POST",
				url : "/weiduo/testUpload",
				data : {
					ListSrt : jsonstr,
				},
				dataType : "json",
				success : function(data) {
					$("#update-success-alert").remove();
					$("#update-fail-alert").remove();
					var $updatesuc = $("<div class='alert alert-success' id='update-success-alert' style='margin:0px 0px 0px 30px;width:25%;display:inline-block;background-color: #70e04b;'><button type='button' class='close' data-dismiss='alert'>×</button> <h4> 操作成功! </h4> 成功插入" + data + "条数据" + " </div>")
					$("#box1").after($updatesuc);
				},
				error : function(data) {
					$("#update-success-alert").remove();
					$("#update-fail-alert").remove();
					var $updatefail = $("<div class='alert alert-fail' id='update-fail-alert' style='margin:0px 0px 0px 30px;width:25%;display:inline-block;background-color: #ffafaf;'><button type='button' class='close' data-dismiss='alert'>×</button> <h4> 操作失败! </h4> 插入失败</div>")
					$("#box1").after($updatefail);
				}
			});

		})
		
		
		
		}
	})
	   function mainContentHeightAdjust() {
      var docHeight = jQuery(document).height();
      if(docHeight > jQuery('.main-content').height())
         jQuery('.main-content').height(docHeight);
   }
		   function visibleSubMenuClose() {
		      jQuery('.menu-list').each(function() {
		         var t = jQuery(this);
		         if(t.hasClass('nav-active')) {
		            t.find('> ul').slideUp(200, function(){
		               t.removeClass('nav-active');
		            });
		         }
		      });
		   }
</script>

</head>
<body class="sticky-header">

	<section> <!-- left side start-->
	<div class="left-side sticky-left-side">

		<!--logo and iconic logo start-->
		<div class="logo">
			<a href="welcome"><img src="/weiduo/img/logo.png" alt=""></a>
		</div>

		<div class="logo-icon text-center">
			<a href="welcome"><img src="/weiduo/img/logo_icon.png" alt=""></a>
		</div>
		<!--logo and iconic logo end-->


		<div class="left-side-inner">

			<!-- visible to small devices only -->
			<div class="visible-xs hidden-sm hidden-md hidden-lg">
				<div class="media logged-user">
					<div class="media-body">
						<h4>
							<a href="#" id="account_name2">John Doe</a>
						</h4>
						<span>"Hello There..."</span>
					</div>
				</div>

				<h5 class="left-nav-title">Account Information</h5>
				<ul class="nav nav-pills nav-stacked custom-nav">
					<li><a href="#"><i class="fa fa-user"></i> <span>Profile</span></a></li>
					<li><a href="#"><i class="fa fa-cog"></i> <span>Settings</span></a></li>
					<li><a href="#"><i class="fa fa-sign-out"></i> <span>Sign
								Out</span></a></li>
				</ul>
			</div>

			<!--sidebar nav start-->
			<ul class="nav nav-pills nav-stacked custom-nav" id="menuBar">
				<li id="welcomeli"><a href="welcome"><i class="fa fa-home"></i> <span>主页</span></a></li>
				
				
			</ul>
			<!--sidebar nav end-->

		</div>
	</div>
	<!-- left side end--> <!-- main content start-->
	<div class="main-content">

		<!-- header section start-->
		<div class="header-section">

			<!--toggle button start-->
			<a class="toggle-btn"><i class="fa fa-bars"></i></a>
			<!--toggle button end-->

			<!--notification menu start -->
			<div class="menu-right">
				<ul class="notification-menu">
					<li><a href="#"
						class="btn btn-default dropdown-toggle info-number"
						data-toggle="dropdown"> <i class="fa fa-tasks"></i>
					</a></li>
					<li><a href="#"
						class="btn btn-default dropdown-toggle info-number"
						data-toggle="dropdown"> <i class="fa fa-envelope-o"></i>
					</a></li>
					<li><a href="#"
						class="btn btn-default dropdown-toggle info-number"
						data-toggle="dropdown"> <i class="fa fa-bell-o"></i>
					</a></li> 
					<li>Hello!<a href="#" class="btn btn-default dropdown-toggle"
						data-toggle="dropdown" id="account_name"> John Doe <span class="caret"></span>
					</a>
						<ul class="dropdown-menu dropdown-menu-usermenu pull-right">
							<li><a href="#"><i class="fa fa-user"></i> Profile</a></li>
							<li><a href="#"><i class="fa fa-cog"></i> Settings</a></li>
							<li><a href="logout"><i class="fa fa-sign-out"></i> Log Out</a></li>
						</ul></li>
 
				</ul>  
			</div>
			<!--notification menu end -->

		</div>
		<!-- header section end-->

		<!-- page heading start-->
		<div class="page-heading">Page Tittle goes here</div>
		<!-- page heading end-->

		<!--body wrapper start-->
		<div class="wrapper">
<div id="demo"></div>  
	<div id="box1" style="margin-top:25px;display:inline-block">
					选择excel文件
					<div class="fileinput fileinput-new" data-provides="fileinput">
						<span class="btn btn-default btn-file"><span
							class="fileinput-new">Select file</span><span
							class="fileinput-exists">Change</span><input type="file"
							onchange="importf(this)" name="..."></span> <span
							class="fileinput-filename"></span> <a href="#"
							class="close fileinput-exists" data-dismiss="fileinput"
							style="float: none">&times;</a>
					</div>
					<button type="button" id="upload_sub" class="btn">上传</button>
				</div>


<div>
	<table border="1" style="text-align: center; margin-left: 30px">
				<strong>按批次搜索：</strong>
				<form action="testTable" method="get" class="form-search">
					<input class="input-medium search-query" name="queryCondition"
						value="${page.queryCondition}" id="condition" type="text">
					<button class="btn btn-info" type="submit">查询</button>
				</form>
			</table>
			</div> 
			<section class="panel"> <header class="panel-heading">
			课程信息表 <span class="tools pull-right"> <a href="javascript:;"
				class="fa fa-chevron-down"></a>
			</span> </header>
			<div class="panel-body" id="upanel">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>批次名</th>
							<th>真实姓名</th>
							<th>用户名</th>
							<th>课程名</th>
							<th>分数</th>
							<th>考试时间</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${test_Tables}" var="s">
							<tr>
								<td>${s.batchName}</td>
								<td>${s.realName}</td>
								<td>${s.userName}</td>
								<td>${s.courseName}</td>
								<td>${s.score}</td>
								<td>${s.testTime}</td>
							</tr>
						</c:forEach>

					</tbody>
				</table>
			</div>
			<center>
				<label>第${page.currentPage}/${page.totalPage}页
					共${page.totalRows}条</label> <a href="testTable?currentPage=0">首页</a> <a
					href="testTable?currentPage=${page.currentPage-1}"
					onclick="return checkFirst()">上一页</a> <a
					href="testTable?currentPage=${page.currentPage+1}"
					onclick="return checkNext()">下一页</a> <a
					href="testTable?currentPage=${page.totalPage}">尾页</a> 跳转到: <input
					type="text" style="width: 30px" id="turnPage" />页
				<button class="btn" onclick="startTurn()">跳转</button>
			</center>
			</section>



			</div>
		<!--body wrapper end-->

		<!--footer section start-->
		<footer class="sticky-footer"> </footer>
		<!--footer section end-->


	</div>
	<!-- main content end--> </section>


	<script src="/weiduo/plugin/script/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="/weiduo/plugin/script/jquery-migrate-1.2.1.min.js"></script>
	<script src="/weiduo/plugin/script/bootstrap.min.js"></script>
	<script src="/weiduo/plugin/script/modernizr.min.js"></script>
	<script src="/weiduo/plugin/script/jquery.nicescroll.js"></script>
	<script src="/weiduo/plugin/script/xlsx.full.min.js"></script>
<script src="/weiduo/plugin/script/jasny-bootstrap.min.js"></script>

	<script src="/weiduo/plugin/script/scripts.js"></script>
<script>
var wb;
var rABS = false;
var jsonobj1;
function importf(obj) {
	if (!obj.files) {
		return;
	}
	var f = obj.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		var data = e.target.result;
		if (rABS) {
			wb = XLSX.read(btoa(fixdata(data)), {
				type : 'base64'
			});
		} else {
			wb = XLSX.read(data, {
				type : 'binary'
			});
		}
		document.getElementById("demo").innerHTML = JSON.stringify(XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]));
		jsonobj1 = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);
	};
	if (rABS) {
		reader.readAsArrayBuffer(f);
	} else {
		reader.readAsBinaryString(f);
	}
}

function fixdata(data) {
	var o = "",
		l = 0,
		w = 10240;
	for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)));
	o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)));
	return o;
}


function checkFirst(){
     if(${page.currentPage>1}){
     
       return true;
     
     }
     alert("已到页首,无法加载更多");
    
   return false;
}

function checkNext(){

if(${page.currentPage<page.totalPage}){

  return true;

}
alert("已到页尾，无法加载更多页");
return false;

}
 

function startTurn(){

var turnPage=document.getElementById("turnPage").value;

if(turnPage>${page.totalPage}){

  alert("对不起已超过最大页数");
 
  return false;

}

var shref="initt.do?currentPage="+turnPage;

window.location.href=shref;
}
</script>
</body>
</html>