<%@ page import="rain.*" errorPage="oops.jsp" %>

<!-- id= -->

<html><body>

<%
		// Retrieve the MusicDAO from the application
		// The MusicDAO was initialized by the listener class: RainAppListener
		MusicDAO myMusicDAO  = (MusicDAO) application.getAttribute(Constants.MUSIC_DAO_KEY);
		String contextPath = request.getContextPath();
%>

<%
  String recordingIdStr = request.getParameter("id");

  int recordingId = Integer.parseInt(recordingIdStr);
  MusicRecording theRecording = myMusicDAO.getMusicRecording(recordingId);

  Duration theDuration = theRecording.getDuration();
  int runningTime = theDuration.getTotalSeconds() / 60;
%>

<h3> <%= theRecording %> </h3>

<table cellpadding=10>

	<tr>

		<td><img src="<%= contextPath %>/images/music/<%= theRecording.getImageName() %>"></td>

		<td>
		<table>
			<tr>
				<td><b>Artist</b></td>
				<td> <%= theRecording.getArtist() %> </td>
			</tr>

			<tr>
				<td><b>Title</b></td>
				<td> <%= theRecording.getTitle() %> </td>
			</tr>

			<tr>
				<td><b>Category</b></td>
				<td> <%= theRecording.getCategory() %> </td>
			</tr>

			<tr>
				<td><b>Duration</b></td>
				<td> <%= runningTime %> mins.</td>
			</tr>

			<tr>
				<td><b>Price</b></td>
				<td>$ <%= theRecording.getPrice() %> USD</td>
			</tr>

		</table>
		</td>

	<tr>

</table>

<%
	// Add a link for "Adding To Cart" if in stock
	
	if (theRecording.getStockCount() > 0) {

		String cartLink = "<a href=" +
				   response.encodeURL(contextPath + "/ShoppingCartServlet?command=add&product_type=Music&id=" + theRecording.getId()) +
				   ">Add To Cart</a>";

		out.println(cartLink);
	}
	else {
		out.println("Sorry.  This item is currently out of stock.");
	}

%>

<p>
<hr>
<p>

<b>Song List</b>
<ol>
<%
	Track[] trackList = theRecording.getTrackList();

	if (trackList != null) {
	
		for (int i=0; i < trackList.length; i++) {
%>
			
			<li> <%= trackList[i] %>
<%
		}
	}
%>
</ol>

</body>
</html>