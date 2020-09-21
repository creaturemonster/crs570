<%
	String contextPath = request.getContextPath();
	String baseLink = contextPath + "/VideoCatalogServlet?video_category=";
%>


<body>
<table border="0" bgcolor="#FFFFCC" cellspacing="0" cellpadding="0" width="100%" >

	<tr>
		<td><a HREF="<%= contextPath %>/ShoppingCartServlet?command=view" TARGET="main"><img src="../images/cart.gif" ALT="View Your Shopping Cart" BORDER="0"></a></td>
	</tr>

	<tr><td>&nbsp;</td>
	</tr>


	<%
		String data = java.net.URLEncoder.encode("Action & Adventure"); 
	%>
	
	<tr>
		<td>
		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
						<a href="../index.html" TARGET="_parent"><b>Home</b></a>
			</font><br>


		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
						<a href="<%= baseLink %><%= data %>" TARGET="main">Action & Adventure</a>
					</font><br>
		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
						<a href="<%= baseLink %>Comedy" TARGET="main">Comedy</a>
					</font><br>
		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
						<a href="<%= baseLink %>Drama" TARGET="main">Drama</a>
					</font><br>
		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
						<a href="<%= baseLink %>Horror" TARGET="main">Horror</a>
					</font><br>
		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
						<a href="<%= baseLink %>Science%20Fiction" TARGET="main">Science Fiction</a>
					</font><br>
		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
						<a href="<%= baseLink %>Suspense" TARGET="main">Suspense</a>
					</font><br>
		</td>
	</tr>
	<tr>
		<td>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
			<br>
		</td>
	</tr>


</table>
</body>