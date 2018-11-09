<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
 <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="ThemeBucket">
    <script src="/oss/plugin/script/jquery-1.10.2.min.js"></script>
    <script src="/oss/plugin/script/md5.js"></script>
    <link href="/oss/plugin/css/style.css" rel="stylesheet">
  <link href="/oss/plugin/css/style-responsive.css" rel="stylesheet">
<title>Login</title>
<script>
	$(document).ready(function() {
		$("#login_sub").on("click",function(){
			var username=$("#username").val();
			var password=$("#password").val();
			password =md5(password);
			if (password != null && username != "" && password != null && username != "") {
				var url = "/oss/ajax_login";
				var param = {
					name : username,
					pass : password
				};
				$.post(url, param, function(data) {
					alert(data);
					if (data != "用户名或密码错误") {
						location.href = "/oss/welcome"
					}
				});
			}
		})
	})
	
	
	</script>


</head>

<body class="login-body">

<div class="container">

    <form class="form-signin" action="">
        <div class="form-signin-heading text-center">
            <h1 class="sign-title">登录 </h1>
            <img src="img/login-logo.png" alt=""/>
        </div>
        <div class="login-wrap">
            <input id="username" type="text" class="form-control" placeholder="用户名" autofocus>
            <input id="password" type="password" class="form-control" placeholder="密码">

            <button class="btn btn-lg btn-login btn-block" id="login_sub" type="button">
                <i class="fa fa-check"></i>
            </button>

            <div class="registration">
              		 还没有帐号？
                <a class="" href="registration.html">
                   	 注册一个
                </a>
            </div>


        </div>

    </form>

</div>


<script src="/oss/plugin/script/bootstrap.min.js"></script>
<script src="/oss/plugin/script/modernizr.min.js"></script>

</body>
</html>