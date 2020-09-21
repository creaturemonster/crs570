package rain;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;

/**
 *  This servlet handles a customer's shopping cart.
 *
 *  <p>The servlet accesses a user's shopping cart by retrieving the session value named <b>the_cart</b>
 *
 *
 *  <p>The servlet supports the following parameters:
 *	<ul>
 *  	<li><b>command</b> - Command to "add" an item or "view" the cart's contents.  See usage examples below
 *  	<li><b>id</b> - The product id...used when adding products
 *	</ul>
 *
 *
 *  <p> Usage examples.  If you want to link to the servlet from a web page:
 *
 *	<pre>
 *
 *		//
 *		// link to "add" an item to the cart
 *		//
 *		&lt;a href=contextPath + /ShoppingCartServlet?command=add&id= + tempProduct.getId() + ">
 *
 *
 *		//
 *		// link to "view" the contents of the cart
 *		//
 *		&lt;a href=contextPath + "/ShoppingCartServlet?command=view">
 *
 *	</pre>
 *
 *  @author 570 Development Team
 */
@SuppressWarnings("serial")
@WebServlet("/ShoppingCartServlet")
public class ShoppingCartServlet extends HttpServlet {

	private MusicDAO myMusicDAO;
	private VideoDAO myVideoDAO;

	public void init() throws ServletException {
		ServletContext application = getServletContext();
		myMusicDAO  = (MusicDAO) application.getAttribute(Constants.MUSIC_DAO_KEY);
		myVideoDAO  = (VideoDAO) application.getAttribute(Constants.VIDEO_DAO_KEY);
	}

    public void doGet (HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {
		//
		//	TODO 2: Get the user's session from the request object
		//
		HttpSession session = request.getSession();

		//	TODO 3: Retrieve the user's shopping cart from the session object
		//			-- Use the LTREE_CART_KEY constant
		//          -- Don't forget the downcast
		//
		ShoppingCart cart = (ShoppingCart) session.getAttribute(Constants.LTREE_CART_KEY);

		// normal HTML header work
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Shopping Cart Contents</title>");
		out.println(Constants.TABLE_CSS);
		out.println("</head>");
		out.println("<body>");
		
		try {
			//
			//	TODO 4: Get the request parameter named "command"
			//
			String command = request.getParameter("command");
			
			//
			// USE CASE #1
			//
			if (command.equals("add")) {
				//
				//  TODO 5: Get the request parameter named "id"
				//
				//String videoId = request.getParameter("id");
				//
				//	TODO 6: Use the id parameter to retrieve the video recording from "myVideoDAO"
				//
				//VideoRecording tempRecording = myVideoDAO.getVideoRecording(videoId);
				//
				//	TODO 7: Add the video recording to the shopping cart
				//
				String productId = request.getParameter("id");
				String productType = request.getParameter("product_type");
				Recording recording = null;
				if(productType.equalsIgnoreCase("video")) {
					recording = myVideoDAO.getVideoRecording(productId);
				}
				else {
					recording = myMusicDAO.getMusicRecording(productId);
				}
				cart.add(recording);
				//
				// BONUS WORK
				//
				String referer = request.getHeader("Referer");
				if(referer.contains("Details")) {
					referer = referer.replace("Details", "Catalog");
					referer = referer.substring(0, referer.indexOf('?'));
					if(productType.equalsIgnoreCase("video")) {
						referer += "?video_category=";
					}
					else {
						referer += "?music_category=";
					}
					referer += java.net.URLEncoder.encode(recording.getCategory(), "UTF-8");
				}
				
				// continue shopping...
				response.sendRedirect(referer);
				return;

			}
			//
			// USE CASE #2
			//
			else if (command.equals("view")) {
				//
				//  TODO 8: Show the cart
				//          Hint: use the showCart() method defined below
				//
				showCart(cart, request, response);
			}

			//
			// BONUS WORK
			//
			else if (command.equals("remove")) {
				String productId = request.getParameter("id");
				String productType = request.getParameter("product_type");
				Recording recording = null;
				if(productType.equalsIgnoreCase("video")) {
					recording = myVideoDAO.getVideoRecording(productId);
				}
				else {
					recording = myMusicDAO.getMusicRecording(productId);
				}
				cart.remove(productId);
				
				//
				// BONUS WORK
				//
				String referer = request.getHeader("Referer");
				if(referer.contains("Details")) {
					referer = referer.replace("Details", "Catalog");
					referer = referer.substring(0, referer.indexOf('?'));
					if(productType.equalsIgnoreCase("video")) {
						referer += "?video_category=";
					}
					else {
						referer += "?music_category=";
					}
					referer += java.net.URLEncoder.encode(recording.getCategory(), "UTF-8");
				}
				response.sendRedirect(referer);
				return;
			}
		}
		catch (DAOException | IOException exc) {
			log(exc.toString());
			exc.printStackTrace(out);
		}

		out.println("</body></html>");
    }


	/**
	 *  Shows the contents of the shopping cart in a table.
	 *
	 *  <pre>
	 *
	 *    +---------------------------------------+
	 *    |  Quantity   |  Product  |    Amount   |
	 *    =========================================
	 *    |     ...     |    ...    |     ...     |
	 *    |----------------------------------------
	 *    |     ...     |    ...    |     ...     |
	 *    |----------------------------------------
	 *    |   Total     |           |             |
	 *    +---------------------------------------+
	 *
	 *  </pre>
     *
	 * @throws IOException 
	 *
	 */
	public void showCart(ShoppingCart userCart, HttpServletRequest request, HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		out.println("<h4>Shopping Cart Contents</h4>");
		if(userCart.getItems().size() == 0) {
			out.println("<i>Your cart is empty.</i>");
			return;
		}

		//	table w/ the columns "Quantity", "Product", "Amount"
		out.println("<table>");

		out.println("<tr>");
		out.println("<th>Quantity</th>");
		out.println("<th>Product</th>");
		out.println("<th>Amount</th>");

		//
		// BONUS WORK
		//
		out.println("<th>Modify</th>");

		out.println("</tr>");

		//
		//  TODO 9: Study the code for looping over the items in the cart
		//          (nothing to change)
		//

		//
		// BONUS WORK
		//
		String contextPath = request.getContextPath();
		String removeBaseLink = contextPath + "/ShoppingCartServlet?command=remove&id=";
		String addBaseLink = contextPath + "/ShoppingCartServlet?command=add&id=";

		for (ShoppingCartItem item : userCart.getItems()) {
			out.println("<tr>");
			out.println("<td>" + item.getQuantity() + "</td>");
			out.println("<td>" + item.getProduct().getName() + "</td>");
			out.println("<td>$ " + ShoppingCart.formatTotal(item.getProduct().getPrice() * item.getQuantity()) + "</td>");

			//
			//  BONUS WORK
			//
			out.println("<td>");
			out.println("<a href=");
			out.println(response.encodeURL(addBaseLink + item.getProduct().getId() + "&product_type=" + item.getProduct().getType()));
			out.println(">Add</a>");
			out.println(" -- ");

			out.println("<a href=");
			out.println(response.encodeURL(removeBaseLink + item.getProduct().getId() + "&product_type=" + item.getProduct().getType()));
			out.println(">Remove</a>");
			out.println("</td>");

			out.println("</tr>");
		}

		//  shows a table row with totals for: price, items
		//  display a table row with totals for: price, items
		displayTotals(userCart, out);

		out.println("</table>");
	}

	protected void displayTotals(ShoppingCart userCart, PrintWriter out) {
		out.println("<tr>");
		out.println("<td><b>" +  userCart.getNumberOfProducts() + " items</b></td>");
		out.println("<td><b>TOTAL</b></td>");
		out.println("<td><b>$ " +  ShoppingCart.formatTotal(userCart.getTotalPrice()) + "</b></td>");
		out.println("</tr>");
	}
}
