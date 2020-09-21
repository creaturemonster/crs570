package rain;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  This servlet acts as the Controller for the music catalog.
 *  It uses input data from the request to pull data
 *  from the Model, represented as the MusicDAO.
 *  It then forwards the model data to the View (JSP)
 *
 *  @author 570 Development Team
 *
 */
@SuppressWarnings("serial")
//
//  TODO 1: add an annotation that maps HTTP requests bound
//          for "/MusicCatalogServlet" to this actual servlet
//
@WebServlet("/MusicCatalogServlet")
public class MusicCatalogServlet extends HttpServlet {

	private MusicDAO musicDAO;

	public void init() throws ServletException {
		ServletContext application = getServletContext();
		//
		//  TODO 2: populate the musicDAO reference
		//          by retrieving it from the servlet context
		//          -- Use the MUSIC_DAO_KEY constant
		//          (Hint: remember to downcast to MusicDAO)
		//
		musicDAO = (MusicDAO) application.getAttribute(Constants.MUSIC_DAO_KEY);
	}


    public void doGet (HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {

		String cat = request.getParameter("music_category");

		List<MusicRecording> musicList = null;

		try {
			//
			//  TODO 3: consult the Model to populate the musicList
			//          (on the MusicDAO, call a method that takes a music
			//           category and returns a list of MusicRecording)
			//
			musicList = musicDAO.getMusicRecordings(cat);

			//
			//  TODO 4: Set the musicList as an attribute in the "request" object
			//          -- Use the MUSIC_LIST_KEY constant
			//
			request.setAttribute(Constants.MUSIC_LIST_KEY,musicList);
			
			//
			//  TODO 5: Dispatch/forward the catalog data to the View (/music_catalog.jsp)
			//
			request.getRequestDispatcher("/music_catalog.jsp").forward(request, response);
			
		}	
		catch (DAOException e) {
			log("DAO Exception in MusicCatalogServlet", e);
			response.getWriter().println("<h3>Something went wrong! Please try your request later...</h3>");
		}
    }
}

