<%@ page import="java.util.*, rain.*" errorPage="oops.jsp" %>

<%
	String contextPath = request.getContextPath();
%>

<html>
<%= Constants.TABLE_CSS %>
<body>

<h3>Category: <%= request.getParameter("music_category") %></h3>

<%
	ShoppingCart theCart = (ShoppingCart) session.getAttribute(Constants.LTREE_CART_KEY);

	int numItems = theCart.getNumberOfProducts();
	if(numItems == 0) {
		out.println("<i> Your cart is empty.</i>");
	}
	else {
		out.println("<i> Total items in cart: <b>" + numItems + "</b></i> &nbsp;&nbsp;&nbsp; ($" + ShoppingCart.formatTotal(theCart.getTotalPrice()) + ")");
	}
	out.println("<p/>");

	// Retrieve the music list
	List musicList = (List) request.getAttribute(Constants.MUSIC_LIST_KEY);

	String baseLink = contextPath + "/MusicCatalogServlet?music_category=" + request.getParameter("music_category");

	String artistLink = "<a href=" + response.encodeURL(baseLink + "&sort_by=" + MusicDAO.SORT_BY_ARTIST) + ">Artist</a>";
	String titleLink = "<a href=" + response.encodeURL(baseLink + "&sort_by=" + MusicDAO.SORT_BY_TITLE) + ">Title</a>";
	String priceLink = "<a href=" + response.encodeURL(baseLink + "&sort_by=" + MusicDAO.SORT_BY_PRICE) + ">Price</a>";
	String stockCountLink = "<a href=" + response.encodeURL(baseLink + "&sort_by=" + MusicDAO.SORT_BY_STOCK_COUNT) + ">Stock Count</a>";
%>

<table>

	<tr>
		<th><%= artistLink %></th>
		<th><%= titleLink %></th>
		<th><%= priceLink %></th>
		<th><%= stockCountLink %></th>
		<th>Cart</th>
	</tr>

<%
	Iterator myIterator = musicList.iterator();
	MusicRecording tempRecording = null;

	while (myIterator.hasNext()) {
		tempRecording = (MusicRecording) myIterator.next();
%>
		
		<tr>
			<td> <%= tempRecording.getArtist() %> </td>
			<td> <a href="<%= contextPath %>/music/music_details.jsp?id=<%= tempRecording.getId() %>"><%= tempRecording.getTitle() %></a> </td>
			<td>$ <%= tempRecording.getPrice() %> USD</td>
			<td> <%= tempRecording.getStockCount() %> </td>

			<td>
			<%
				if (!tempRecording.isInStock()) {
					out.println("Out of Stock");
				}
				else {
					String addLink = contextPath + "/ShoppingCartServlet?command=add&product_type=music&id=" + tempRecording.getId();
					out.print("<a href=" + addLink + ">");
					out.print("<img border=0 src=" + contextPath + "/images/add_to_cart_button.gif alt=\"" + tempRecording.getTitle() +"\">");
					out.println("</a>");
				}
			%>
			</td>
		</tr>

<%
	}
%>

</table>

</body></html>

