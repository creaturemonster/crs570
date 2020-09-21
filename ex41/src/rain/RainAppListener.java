package rain;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

public class RainAppListener implements ServletContextListener {

	//
	//  TODO 1: Note the resource injection of the
	//          connection pool into myDataSource
	//          (nothing to change)
	//
	@Resource(name="jdbc/RainForestDB")
	DataSource myDataSource;

	public void contextInitialized(ServletContextEvent event) {
		ServletContext application = event.getServletContext();

		//
		//  TODO 2: Construct an instance of the VideoDAO
		//          and store it in the application
		//          -- Use Constants.VIDEO_DAO_KEY as the key
		//          (slide 4-36)
		//
		application.setAttribute(Constants.VIDEO_DAO_KEY, new VideoDAO(myDataSource));
		
		log(application, "Application Context Initialized: " + new java.util.Date());
	}

	public void contextDestroyed(ServletContextEvent event) {
		ServletContext application = event.getServletContext();

		log(application, "Application Context Destroyed: " + new java.util.Date());
	}

	private void log(ServletContext application, Object message) {
		// contextName is the <display-name> for the web app defined in web.xml
		String applicationName = application.getServletContextName();

		// log data to server's event log and stdout
		application.log(applicationName + ": " + message.toString());
		System.out.println(applicationName + ": " + message);
	}
}