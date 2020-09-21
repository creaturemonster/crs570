<%
	String contextPath = request.getContextPath();
%>

<html>
<head>
   <title>RainForest - Free T-Shirt GiveAway</title>
</head>

<body>

<h3>RainForest - Free T-Shirt GiveAway</h3>

<hr>
<br>Sign up for a free T-shirt. Hurry while supplies last!

<form method="POST" action="<%= contextPath %>/GiveAwayServlet">

<h3>Address</h3>

<table>
<tr>
	<td>E-mail Address</td>
	<td><input type="text" name="email_address" size=20></td>
</tr>

<tr>
	<td>Name</td>
	<td><INPUT type="text" size=20 name="user_name"></td>
</tr>

<tr>
	<td>Street Address</td>
	<td><INPUT type="text" size=20 name="street_address"></td>
</tr>

<tr>
	<td>City</td>
	<td><INPUT type="text" size=20 name="city"></td>
</tr>

<tr>
	<td>State/Province</td>
	<td><INPUT type="text" size=20 name="state_province"></td>
</tr>

<tr>
	<td>Postal/Zip Code</td>
	<td><INPUT type="text" size=20 name="postal_zip_code"></td>
</tr>

<tr>
	<td>Country</td>
	<td>
		<SELECT name="country" size=3>
			<OPTION>Canada
			<OPTION>France
			<OPTION>Japan
			<OPTION>Mexico
			<OPTION>Spain
			<OPTION>Sweden
			<OPTION>United Kingdom
			<OPTION>United States
		</SELECT>
	</td>
</tr>
</table>

<h3>T-Shirt Size</h3>
Please select a shirt size<br>
<table CELLSPACING=15 >
	<tr>
		<td>Small <INPUT type="radio" name="shirt_size" value="small"> </td>

		<td>Medium <INPUT type="radio" name="shirt_size" value="medium"> </td>

		<td>Large <INPUT type="radio" name="shirt_size" value="large"> </td>

		<td>X-Large <INPUT type="radio" name="shirt_size" value="x-large"> </td>

	</tr>
</table>

<INPUT type=submit value="Send"> <INPUT type=reset value="Clear">

</form>

</body>
</html>
