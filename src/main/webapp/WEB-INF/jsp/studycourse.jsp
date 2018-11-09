<%@ page language="java" pageEncoding="UTF-8"%>
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
<script src="/oss/plugin/script/jquery-1.10.2.min.js"></script>
<script src="/oss/plugin/script/video.min.js"></script>
<script src="/oss/plugin/script/jquery.stepy.js"></script>
<link href="/oss/plugin/css/style.css" rel="stylesheet">
<link href="/oss/plugin/css/jquery.stepy.css" rel="stylesheet">
<link href="/oss/plugin/css/style-responsive.css" rel="stylesheet">
<link href="/oss/plugin/css/video-js.min.css" rel="stylesheet">

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
		var data1;
		$.ajax({
			url : "/oss/getWare",
			data : {
				id : getQueryString("courseId")
			},
			type : "post",
			dataType : "json",
			success : function(data) {
				console.log(data);
				data1=data;
				$.each(data,function(i,v){
					$("#gallery").append('<div class="images item"><a class="scourse" href="#"><img id="'+v.coursewareId+'" src="'+v.picPath+'" alt=""></a><p>'+v.coursewareName+'</p></div>')
				}) 
				 
			},
			error : function() { 
				console.log("失败"); 
			} 
		})  
		 /*
		<video id="example_video_1" class="video-js vjs-default-skin vjs-big-play-centered"
		controls preload="none"
		poster="/media/尚学堂马士兵_XML_DOM4J视频教程_02.jpg" 
		data-setup="{
		}">										 
	<source id="ssc" src="/media2/尚学堂马士兵_XML_DOM4J视频教程_01.mp4" /> 
	</video>
		 */
		$("#gallery").on("click",".scourse",function(){ 
			var ii=Number($(this).children().attr("id"))
			$.each(data1,function(i,v){ 
				console.log(v.coursewareId) 
				if(v.coursewareId==ii){
					$("#ccenter").empty();
					$("#ccenter").append('<video id="example_video_1" class="video-js vjs-default-skin vjs-big-play-centered"controls preload="none"poster="'+v.picPath+'" data-setup="{}"><source id="ssc" src="'+v.videoPath+'" /> </video>')
					$("#example_video_1").height($(window).height()*0.8);
					$("#example_video_1").width($(window).height()*0.8/0.75); 
				}
				 
			})
			$("#default-title-1").click();
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
		   
		   function getQueryString(name) {
			    var result = window.location.search.match(new RegExp("[\?\&]" + name + "=([^\&]+)", "i"));
			    if (result == null || result.length < 1) {
			        return "";
			    }
			    return result[1];
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
		<div class="page-heading"><h3>课程课件</h3></div>
		<!-- page heading end-->

		<!--body wrapper start-->
		<div class="wrapper">




			<div class="row">
				<div class="col-md-10">  
					<div class="square-widget">
						<div class="widget-container">   
							<div class="stepy-tab"></div> 
							<form id="default" class="form-horizontal">
								<fieldset title="Initial Info">
									<legend>选择课件</legend>
									<div class="panel-body">
										<div id="gallery" class="media-gal isotope"
											style="position: relative; overflow: hidden; height: 749.297px;">
										</div>
									</div>  
								</fieldset>  
								<fieldset title="Contact Info"> 
									<legend>课件学习</legend>
									<center id="ccenter">
										</center>
								</fieldset>
 
								<button class="btn btn-info finish">Finish</button>
							</form> 
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
	<script src="/oss/plugin/script/bootstrap.min.js"></script>
	<script src="/oss/plugin/script/modernizr.min.js"></script>
	<script src="/oss/plugin/script/jquery.nicescroll.js"></script>


	<script src="/oss/plugin/script/scripts.js"></script>
<script>
    /*=====STEPY WIZARD====*/
    $(function() {
        $('#default').stepy({
            block: true,
            titleClick: true,
            titleTarget: '.stepy-tab'
        });
    });
    </script>
</body>
</html>