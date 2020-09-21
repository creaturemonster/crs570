<%@ page isErrorPage="true"%>

<html>
<head>
<title>Safety Net</title>
</head>

<body>

Exception information:  <b> <%= exception %> </b>

<hr>
<h3>Here's the stack trace</h3>

<pre>
<%
	java.io.PrintWriter myWriter = new java.io.PrintWriter(out);
	exception.printStackTrace(myWriter);
%>
</pre>

</body>
</html>