package rain;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

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
	protected VideoDAO myVideoDAO;

	//
	// TODO: 1. Use a Resource annotation to inject
	//          the connection pool "jdbc/RainForestDB"
	//          into the myDataSource field (slide 3-37)
	//          (Hint: replace the ???)
	//
	@Resource(name="jdbc/RainForestDB")
	private DataSource myDataSource;
	
	public void init() throws ServletException {

		//
		//  TODO: 2. Create an instance of VideoDAO using myDataSource
		//           and assign it to myVideoDAO (slide 3-37)
		//
		myVideoDAO = new VideoDAO(myDataSource);
	}


	/**
	 *  This method reads the form parameter and
	 *  displays a list of recordings matching the search.
	 */
    public void doGet (HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {

		//
		//  TODO 3.  Set the content type of the response to "text/html"
		//
    	response.setContentType("text/html");
    	//
    	//  TODO 4.  Retrieve the selected video_category parameter from the HTTP request
		//
    	String cat = request.getParameter("video_category");

    	List<VideoRecording> recordingList = null;
    	try {
    		//
        	// 	TODO 5.	From the DAO, get a list of videos
    		//		    for the user-selected category and
    		//          assign the result to the recordingList
    		//		    (Hint: use the "myVideoDAO" object)
    		//
    		recordingList=myVideoDAO.getVideoRecordings(cat);
    		
		} catch (DAOException e) {
			log("ERROR getting video recordings for category: " + cat, e);
		}
		PrintWriter out = response.getWriter();
    	//
		//	TODO 6.	Display the category on the returned HTML page
		//
		out.println("<h3>Category: " + cat + "</h3><p/>");
		//
		//  TODO 7.  Build an HTML table for the list of products.
		//		     (Hint: call buildHtmlTable(), below) Slide 3-28.
		//
		buildHtmlTable(recordingList,out);
    }

	protected void buildHtmlTable(List<VideoRecording> theList, PrintWriter out) {
		out.println(Constants.TABLE_CSS);

		out.println("<table>");
		
		out.println("<tr>");
			out.println("<th>Title</th>");
			out.println("<th>Price</th>");
			out.println("<th>Stock Count</th>");
		out.println("</tr>");


		for (VideoRecording tempRecording : theList) {
			out.println("<tr>");
			out.println("<td>" + tempRecording.getTitle() + "</td>");
			out.println("<td>" + tempRecording.getPrice() + "</td>");
			out.println("<td>" + tempRecording.getStockCount() + "</td>");
			out.println("</tr>");
		}

		out.println("</table>");
	}

}
