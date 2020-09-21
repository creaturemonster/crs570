package rain;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("/ShoppingCartServlet")
public class ShoppingCartServlet extends HttpServlet {

	private VideoDAO myVideoDAO;

	public void init() throws ServletException {
		ServletContext application = getServletContext();
		//
		// TODO 1: Retrieve the VideoDAO instance from the application
		//         -- Use Constants.VIDEO_DAO_KEY as the key
		//          -- Remember to downcast to VideoDAO
		//
		myVideoDAO = (VideoDAO) application.getAttribute(Constants.VIDEO_DAO_KEY);
	}

    public void doGet (HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {
		//
		//	TODO 2: Get the user's session from the request object
		//
		HttpSession session = request.getSession();

		//	TODO 3: Retrieve the user's shopping cart from the session object
		//			-- Use Constants.LTREE_CART_KEY as the key
		//          -- Remember to downcast to ShoppingCart
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
				String videoId = request.getParameter("id");
				//
				//	TODO 6: Use the id parameter to retrieve the video recording from "myVideoDAO"
				//
				VideoRecording tempRecording = myVideoDAO.getVideoRecording(videoId);
				//
				//	TODO 7: Add the tempRecording to the shopping cart
				//
				cart.add(tempRecording);

				// continue shopping...
				response.sendRedirect(request.getHeader("Referer"));
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
		}
		catch (DAOException | IOException e) {
			log("Error in Shopping Cart Servlet", e);
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
		out.println("</tr>");

		//
		//  TODO 9: Study the code for looping over the items in the cart
		//          (nothing to change)
		//
		for (ShoppingCartItem item : userCart.getItems()) {
			out.println("<tr>");
			out.println("<td>" + item.getQuantity() + "</td>");
			out.println("<td>" + item.getProduct().getName() + "</td>");
			out.println("<td>$ " + ShoppingCart.formatTotal(item.getProduct().getPrice() * item.getQuantity()) + "</td>");
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
