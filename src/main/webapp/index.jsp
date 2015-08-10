<%@page import="com.sirius.skymall.redis.service.*"%>
<%@page import="org.springframework.web.context.support.*"%>
<%@page import="org.springframework.context.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lnua platform spy page</title>
</head>
<body>
	<%
		ApplicationContext ctx = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		RedisMonitor rm = (RedisMonitor) ctx.getBean("redisMonitor");
	%>
	服务器联通情况：<br/><%=rm.ping()%>
	<br/>
	队列存储情况：<br/><%=rm.keyMap()%>
	<br/>
	服务器运行情况：<br/><%=rm.info()%>
</body>
</html>
