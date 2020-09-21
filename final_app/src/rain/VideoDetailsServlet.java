package rain;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  This servlet displays the details of a video recording.
 *
 *  <p> The servlet expects one parameter:
 *
 *  <ul>
 *    <li>  id:  the recording id
 *  </ul>
 *
 *
 *  @author 570 Development Team
 *
 */
@SuppressWarnings("serial")
@WebServlet("/VideoDetailsServlet")
public class VideoDetailsServlet extends HttpServlet {

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
	 *  Reads the "id" parameter and displays the recording details.
	 */
    public void doGet (HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {

		response.setContentType("text/html");

		String videoIdStr = (request.getParameter("id"));

		int videoId = Integer.parseInt(videoIdStr);

		String contextPath = request.getContextPath();

		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Video Recording Details</title></head>");
		out.println("<body>");

		try {

			VideoRecording theRecording = myVideoDAO.getVideoRecording(videoId);

			out.println("<table cellpadding=10>");

				out.println("<tr colspan=2>");
					out.println("<td><b>" + theRecording + "</b></td>");
				out.println("</tr>");

				out.println("<tr>");
					out.println("<td><img src=" + contextPath + "/images/video/" +  theRecording.getImageName() + "></td>");

					out.println("<td>");
						out.println("<table>");
							out.println(buildRow("Title", theRecording.getTitle()));
							out.println(buildRow("Director", theRecording.getDirector()));
							out.println(buildRow("Category", theRecording.getCategory()));

							out.println(buildRow("Rating", theRecording.getRating()));
							int minutes = theRecording.getDuration().getTotalSeconds() / 60;
							out.println(buildRow("Duration", minutes + " mins"));
							int yearReleased = theRecording.getYearReleased();
							out.println(buildRow("Year Released", "" + Integer.toString(yearReleased)));

							out.println(buildRow("Price", "$" + theRecording.getPrice() + " USD"));
							out.println(buildRow("Stock Count", "" + theRecording.getStockCount()));
						out.println("</table>");
					out.println("</td>");

				out.println("</tr>");

			out.println("</table>");

			// bonus work
			if (theRecording.getStockCount() > 0) {

				String cartLink = "<a href=" +
								   response.encodeURL(contextPath + "/ShoppingCartServlet?command=add&product_type=video&id=" + theRecording.getId()) +
								   ">Add To Cart</a>";

				out.println(cartLink);
			}
			else {
				out.println("Sorry.  This item is currently out of stock.");
			}

		}
		catch (DAOException exc) {
			out.println(exc);
		}

		out.println("</body></html>");

    }

	/**
	 *  Helper method to format the label and value as a table row. <p>
	 *
	 *  Returns the formatted string for a table row
	 */
    protected String buildRow(String label, String value) {

		return "<tr><td><b>" + label + "</b></td><td>" + value + "</td></tr>";
	}

	/**
	 *  Forwards to doGet()
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		doGet(request, response);
	}

    public String getServletInfo() {
		return "VideoDetailsServlet - shows the details for a given video recording";
    }

}
