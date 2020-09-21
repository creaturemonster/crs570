package rain;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;

/**
 * Servlet implementation class GiveAwayServlet
 */
@WebServlet("/give_away")
public class GiveAwayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email_address=request.getParameter("email_address");
		String user=request.getParameter("user_name");
		String size=request.getParameter("shirt_size");
		try {
			EzMailer.sendMessage("localhost","no-reply@localhost",email_address,"T-Shirt",user+",your"+ size+"T-Shirt is on its way!");
			System.out.println("Message successfully sent to" + email_address);
			
		}catch (javax.mail.MessagingException exc) {
			String message="Couldn't read email";
			log(message, exc);
			System.out.println(message);
			System.out.println("Couldn't send email: "+ exc.toString());
		}
		
		String [] fileNameList=new String[2];
		
		ServletContext application=getServletContext();
		fileNameList[0]=application.getRealPath("/pubs/java_course.pdf");
		fileNameList[1]=application.getRealPath("/pubs/xml_course.pdf");
		
		for(int i=0;i<fileNameList.length;i++) {
			log("File Name: " + i + "fileNameList[i]");
		}
		try {
			EzMailer.sendMessage("localhost",
	        		  "no-reply@localhost",
	        		  email_address,
	        		  "T-Shirt",
	        		  user + ", your " + size + " T-shirt is on its way!");
			
			System.out.println("Message successfully sent to: " + email_address);
	    }
	    catch (javax.mail.MessagingException exc) {
	    	System.out.println("Couldn't send e-mail: " + exc.toString());
	    }
		System.out.println("</body></html>");
	}

}
