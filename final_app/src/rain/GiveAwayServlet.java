package rain;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@SuppressWarnings("serial")
@WebServlet("/GiveAwayServlet")
public class GiveAwayServlet extends HttpServlet {

    public void doPost (HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {

		// set the MIME content of the response
		response.setContentType("text/html");

		// get a print writer to send information back to client
		PrintWriter out = response.getWriter();

		// display confirmation page
		out.println("<html>");
		out.println("<head><title>Thanks</title></head>");
		out.println("<body>");
		out.println("<h3>Thanks for signing up</h3><hr>");
		out.println("We will send your shirt out to:");

		out.println("<ul>");
		out.println("<li> E-mail: <b>" + request.getParameter("email_address") + "</b>");
		out.println("<li> Name: <b>" + request.getParameter("user_name") + "</b>");
		out.println("<li> Shirt Size: <b>" + request.getParameter("shirt_size") + "</b>");
		out.println("</ul>");

		try {
			String smtpHost = "localhost";
			String fromEmail = "solutions@localhost";
			String toEmail = request.getParameter("email_address");
			String subject = "T-Shirt Confirmation";
			String messageText = request.getParameter("user_name") + ", your " + request.getParameter("shirt_size") + " t-shirt is on the way!";

			log("toEmail = " + toEmail);

			EzMailer.sendMessage(smtpHost, fromEmail, toEmail, subject, messageText);

			log("Successfully sent e-mail to " + toEmail);

			out.println("<p>An e-mail confirmation is on the way!");
		}
		catch (javax.mail.MessagingException exc) {
			String errorMsg = "Couldn't send e-mail: " + exc.toString();
			log(errorMsg);
			out.println(errorMsg);
		}

		out.println("</body></html>");


    }

    public void doGet (HttpServletRequest request, HttpServletResponse response)
	  throws ServletException, IOException {

		// perform same operation for POST or GET
		doPost(request, response);
	}

}
