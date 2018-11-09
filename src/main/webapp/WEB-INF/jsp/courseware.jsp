<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
 
<meta charset="utf-8"> 
<meta name="viewport" 
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<meta name="description" content="">
<meta name="author" content="ThemeBucket">
<title>Welcome</title>
<script src="/oss/plugin/script/jquery-1.10.2.min.js"></script>
	<script src="/oss/plugin/script/dropzone/dropzone.js"></script>
<link href="/oss/plugin/css/style.css" rel="stylesheet">
	<script src="/oss/plugin/script/bootstrap.min.js"></script> 
	<script src="/oss/plugin/script/bootstrap-select.js"></script>
<link href="/oss/plugin/css/style-responsive.css" rel="stylesheet">
<link href="/oss/plugin/css/bootstrap-select.min.css" rel="stylesheet">
<link href="/oss/plugin/script/dropzone/css/dropzone.css" rel="stylesheet"/>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="/oss/plugin/script/html5shiv.js"></script>
  <script src="/oss/plugin/script/respond.min.js"></script>
  <![endif]-->

<script>
	$(document).ready(function() {
		if(!<%=session.getAttribute("role_id")%>){
			alert("登录信息过期，请登录");
			location.href = "/oss/login"
		}else{
		var role_id = '<%=session.getAttribute("role_id")%>';
		var account_name = '<%=session.getAttribute("account_name")%>' ;
		$("#account_name").html(account_name+'<span class="caret"></span>');
		$("#account_name2").html(account_name+'<span class="caret"></span>');
		$.ajax({
			url : "/oss/menux",
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
		 
		$(".edit").on("click",function(){  
			$.ajax({
				url : "/oss/getClazz",
				data : {
				},  
				type : "post",
				dataType : "json",
				success : function(data) {
					console.log(data)
					var str="";
					$.each(data,function(i,v){	
						str += '<option value="' + data[i].courseId + '">' + data[i].courseName + '</option>'
					})
					$("#select1").html(str);
					$('#select1').selectpicker('refresh');
				},
				error : function() {
					console.log("失败");
				}
			})
			
		})
		 
		$("#upanel").on("click",".edit",function(){
			$("#u1").val($(this).parent().parent().children().eq(0).text().trim());
			$("#u10").val($(this).parent().parent().children().eq(1).text().trim());
			$("#u2").val($(this).parent().parent().children().eq(2).text().trim());
		})
		
		$("#add2sub").on("click",function(){
			$.ajax({
				url : "/oss/editCourseWare",
				data : {
					id:$("#u10").val().trim(),
					name:$("#u1").val().trim(),
					text:$("#u2").val().trim(),
					select1:$("#select1").val().trim()
				},  
				type : "post",
				dataType : "json",
				success : function(data) {
					window.location.reload();
				},
				error : function() {
					console.log("失败");
				}
			})
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
			<a href="welcome"><img src="/oss/img/logo.png" alt=""></a>
		</div>

		<div class="logo-icon text-center">
			<a href="welcome"><img src="/oss/img/logo_icon.png" alt=""></a>
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
		<div class="page-heading"><h3>课件管理</h3></div>
		<!-- page heading end-->

		<!--body wrapper start-->
		<div class="wrapper"> 
		   
 
 
<form id ="myAwesomeDropzone" action="upload_submit" class="dropzone" method="POST" enctype="multipart/form-data"></form> 
		<script type="text/javascript">   
     
			Dropzone.options.myAwesomeDropzone = {
				url : "upload_submit", 
				paramName : "saveFiles",
				acceptedFiles : ".txt,.jpg,.gif,.png,.mp4,.avi",
				maxFilesize : 1000, 
			};
		</script> 
		
		<header class="panel-heading">课件编辑</header>
		<table border="1" style="text-align:center;margin-left:30px"> 
					<strong>按课件名搜索：</strong>
						<form action="courseWareTable" method="get" class="form-search"> 
							<input class="input-medium search-query" name="queryCondition" 
								value="${page.queryCondition}" id="condition" type="text">
							<button class="btn btn-info" type="submit">查询</button>
						</form>
					</table> 
	 			<section class="panel">
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                             </span>
                    </header>
                    <div class="panel-body" id="upanel">
                        <table class="table table-striped">
                            <thead> 
                            <tr>
                                <th>课件名</th>
                                <th>课件编号</th>
                                <th>课件描述</th>
                                <th>所属课程</th> 
                                <th>视频</th>
                                <th>图片</th>
                                <th>视频时长</th>
                                <th>操作</th> 
                            </tr>  
                            </thead> 
                            <tbody>
                            <c:forEach items="${coursewares}" var="s">
                            <tr>
                                <td>${s.coursewareName}</td>
                                <td>${s.coursewareId}</td>
                                <td><c:if test="${s.coursewareText==null }"> 
      							未编辑 
   								</c:if><c:if test="${s.coursewareText!=null || s.coursewareText!=''}"> 
      							${s.coursewareText} 
   								</c:if></td>
                                <td><c:if test="${s.courseId=='0' }"> 
      							未编辑 
   								</c:if><c:if test="${s.courseId!='0' }">
      							 ${s.courseId}
   								</c:if></td> 
                                <td>${s.videoPath}</td>
                                <td>${s.picPath}</td>
                                <td><fmt:formatNumber type="number" value="${s.videoTime/60}" pattern="0" maxFractionDigits="0"/>分钟</td>
   								<td><a href="#myModal" id="cedit" class="edit" data-toggle="modal">课件编辑 </a></td>                               
                            </tr>
                            </c:forEach>
                          
                            </tbody>
                        </table>
                    </div>
					<center>  
						<label>第${page.currentPage}/${page.totalPage}页
							共${page.totalRows}条</label> <a href="courseWareTable?currentPage=0">首页</a> <a
							href="courseWareTable?currentPage=${page.currentPage-1}" 
							onclick="return checkFirst()">上一页</a> <a 
							href="courseWareTable?currentPage=${page.currentPage+1}"
							onclick="return checkNext()">下一页</a> <a
							href="courseWareTable?currentPage=${page.totalPage}">尾页</a> 跳转到: <input
							type="text" style="width:30px" id="turnPage" />页 <button 
							class="btn"  onclick="startTurn()">跳转</button> 
					</center>
                </section>
		
		
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header"> 
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                            <h4 class="modal-title">批次课程管理</h4>
                                        </div>
                                        <div class="modal-body row"> 
                          <div class="panel-body">
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <label class="col-lg-3 col-sm-3 control-label">课件名:</label>
                                <div class="col-lg-9">
                                    <div class="iconic-input">
                                        <input type="text" name="coursewareName" id="u1" class="form-control" placeholder="课件名">
                                        <input type="hidden" name="coursewareId" id="u10">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-3 col-sm-3 control-label">课件描述:</label>
                                <div class="col-lg-9"> 
                                    <div class="iconic-input right">    
                                        <input type="text" name="coursewareText" id="u2"  class="form-control" placeholder="课件描述">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-3 col-sm-3 control-label">所属课程:</label> 
                                 <div class="col-lg-9"> 
								<select id="select1" name="select"
									class="selectpicker show-tick form-control"
									data-live-search="true" data-actions-box="true">
								</select>
								</div>
                            </div>

                            
                        </form>
                    </div> 
                                        	<button type="button" id="add2sub" class="btn btn-primary btn-lg btn-block">确认添加</button>
                                        </div> 
                                    </div>
                                </div>
                            </div>
		
		</div>
		<!--body wrapper end-->

		<!--footer section start-->
		<footer class="sticky-footer"> </footer>
		<!--footer section end-->


	</div>
	<!-- main content end--> </section>


	<script src="/oss/plugin/script/jquery-ui-1.9.2.custom.min.js"></script>
	<script src="/oss/plugin/script/jquery-migrate-1.2.1.min.js"></script>
	<script src="/oss/plugin/script/modernizr.min.js"></script>
	<script src="/oss/plugin/script/jquery.nicescroll.js"></script>




	<script src="/oss/plugin/script/scripts.js"></script>
	<script type="text/javascript">
    
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