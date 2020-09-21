<%
	String contextPath = request.getContextPath();
	String baseLink = contextPath + "/MusicCatalogServlet?music_category=";
%>

<body>
<table border="0" bgcolor="#FFFFCC" cellspacing="0" cellpadding="0" width="100%" >


	<tr>
		<td><a HREF="<%= contextPath %>/ShoppingCartServlet?command=view" TARGET="main"><img src="../images/cart.gif" ALT="View Your Shopping Cart" BORDER="0"></a></td>
	</tr>

	<tr>
		<td><br></td>
	</tr>

	<tr>
		<td>
		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
						<a href="<%= contextPath %>/index.html" TARGET="_parent"><b>Home</b></a>
			</font><br>

		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
				<a href="<%= baseLink %>Classical" TARGET="main">Classical</a>
			  </font><br>

		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
				<a href="<%= baseLink %>Country" TARGET="main">Country</a>
			  </font><br>

		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
				<a href="<%= baseLink %>Hip-Hop" TARGET="main">Hip-Hop</a>
			  </font><br>

		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
				<a href="<%= baseLink %>Jazz" TARGET="main">Jazz</a>
			  </font><br>

		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
				<a href="<%= baseLink %>Pop" TARGET="main">Pop</a>
			  </font><br>

		&nbsp;<font face="Verdana,Arial,Helvetica" size="1">
				<a href="<%= baseLink %>Rock" TARGET="main">Rock</a>
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