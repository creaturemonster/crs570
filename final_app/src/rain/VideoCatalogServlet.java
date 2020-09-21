package rain;

import java.io.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.util.*;


/**
 *  This servlet allows the user to browse the video catalog.  The servlet reads
 *  parameters and displays a table of the products.
 *
 *  <p> The servlet expects the following parameter:
 *
 *  <ul>
 *    <li>  video_category:  Describes the category to search
 *  </ul>
 *
 *  @author 570 Development Team
 *
 */
@SuppressWarnings("serial")
@WebServlet("/VideoCatalogServlet")
public class VideoCatalogServlet extends HttpServlet {

	/**
	 *  Reference to the video data accessor
	 */
	private VideoDAO myVideoDAO;

	public void init() throws ServletException {

		// Retrieve the VideoDAO from the servlet context
		// The VideoDAO was initialized by the listener class: RainAppListener
		ServletContext application = getServletContext();
		myVideoDAO  = (VideoDAO) application.getAttribute(Constants.VIDEO_DAO_KEY);
	}

	/**
	 *  This method reads the form parameter and
	 *  displays a list of recordings matching the search.
	 */
    public void doGet (HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(Constants.TABLE_CSS);
		
		String contextPath = request.getContextPath();

		//  Retrieve the selected video category from the HTML form
		String category = request.getParameter("video_category");

		// 	Get a list of recordings from the VideoDAO
		try {
			List<VideoRecording> theList = null;

			String sortByString = request.getParameter("sort_by");

			if (sortByString == null) {
				theList = myVideoDAO.getVideoRecordings(category);
			}
			else {

				try {
					int sortByFlag = Integer.parseInt(sortByString);
					theList = myVideoDAO.getVideoRecordings(category, sortByFlag);
				}
				catch (NumberFormatException exc) {
					// non-numeric sort flag...
					log(exc.getMessage());
					exc.printStackTrace();

					// just give them the basic list
					theList = myVideoDAO.getVideoRecordings(category);
				}
			}

			//	Display the category in the HTML page
			out.println("<h3>Category: " + category + "</h3>");
			
			// Display the number of items in the user's shopping cart
			HttpSession session = request.getSession();
			ShoppingCart theCart = (ShoppingCart) session.getAttribute(Constants.LTREE_CART_KEY);

			int numItems = theCart.getNumberOfProducts();
			if(numItems == 0) {
				out.println("<i> Your cart is empty.</i>");
			}
			else {
				out.println("<i> Total items in cart: <b>" + numItems + "</b></i> &nbsp;&nbsp;&nbsp; ($" + ShoppingCart.formatTotal(theCart.getTotalPrice()) + ")");
			}
			out.println("<p/>");

			//	Build an HTML table for the list of products.
			buildHtmlTable(theList, out, category, response, contextPath);
		}
		catch (DAOException exc) {
			log(exc.toString());
			exc.printStackTrace();
		}

    }

	protected void buildHtmlTable(List<VideoRecording> theList, PrintWriter out, String category, HttpServletResponse response, String contextPath) {

		// URL-encode the base link to take care of categories w/ spaces and special characters
		String baseLink = contextPath + "/VideoCatalogServlet?video_category=" + encode(category);

		String titleLink = "<a href=" + response.encodeURL(baseLink + "&sort_by=" + VideoDAO.SORT_BY_TITLE) + ">Title</a>";
		String priceLink = "<a href=" + response.encodeURL(baseLink + "&sort_by=" + VideoDAO.SORT_BY_PRICE) + ">Price</a>";
		String stockCountLink = "<a href=" + response.encodeURL(baseLink + "&sort_by=" + VideoDAO.SORT_BY_STOCK_COUNT) + ">Stock Count</a>";

		out.println("<table border=1 cellpadding=5>");

		out.println("<tr>");
			out.println("<th>" + titleLink + "</th>");
			out.println("<th>" + priceLink + "</th>");
			out.println("<th>" + stockCountLink + "</th>");
			out.println("<th>Cart</th>");
		out.println("</tr>");

		String detailsLink;

		for (VideoRecording tempRecording : theList) {

			detailsLink = "<a href=" + response.encodeURL(contextPath + "/VideoDetailsServlet?id=" + tempRecording.getId()) + ">" + tempRecording.getTitle() + "</a>";
			out.println("<tr>");
			out.println("<td>" + detailsLink + "</td>");
			out.println("<td>" + tempRecording.getPrice() + "</td>");
			out.println("<td>" + tempRecording.getStockCount() + "</td>");

			out.print("<td>");
			if (tempRecording.isInStock()) {
				out.print("<a href=" + response.encodeURL(contextPath + "/ShoppingCartServlet?command=add&product_type=video&id=" + tempRecording.getId()) + ">");
					out.print("<img border=0 src=" + contextPath + "/images/add_to_cart_button.gif alt=\"" + tempRecording.getTitle() +"\">");
				out.println("</a>");
			}
			else {
				out.print("Out Of Stock");
			}
			out.println("</td>");

			out.println("</tr>");
		}

		out.println("</table>");

	}


	/**
	 * Simple helper method to encode the String using UTF-8 encoding. <b>
	 * JDK 1.4 deprecated the method java.net.URLEncoder.encode(String). Now requires
	 * you to explicitly provide the encoding type.
	 */
	private String encode(String theCategory) {

		String result = null;
		try {
			result = java.net.URLEncoder.encode(theCategory, "UTF-8");
		}
		catch (java.io.UnsupportedEncodingException exc) {
			exc.printStackTrace();
		}
		return result;
	}


	/**
	 *  Forwards requests to the doGet(...) method
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		doGet(request, response);
	}

}
