import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

// Step 1: Define a subclass of HttpServlet
public class HelloWorldServlet extends HttpServlet {

	// Step 2: Override appropriate doXXX(...) method
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		  throws ServletException, IOException {

		// Step 3.  Set the MIME content of the response
		response.setContentType("text/html");

		// Step 4.  Get a print writer to send information back to client
		PrintWriter out = response.getWriter();

		// Step 5.  Now "print" the information back to the client
		out.print("<html>");
		out.print("<head><title>Hello World</title></head>");
		out.print("<body>");
		out.print("<h1>Hello World</h1>");
		out.print("Current time is " + new java.util.Date());
		out.println("</body></html>");
	}
}
