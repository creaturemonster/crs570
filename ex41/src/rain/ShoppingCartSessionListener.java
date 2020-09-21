package rain;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

public class ShoppingCartSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();

		//
		//  TODO: Construct an instance of ShoppingCart and
		//        store it as an attribute in the just-created session object
		//        -- Use Constants.LTREE_CART_KEY for the key
		//
		session.setAttribute("Constants.LTREE_CART_KEY",new ShoppingCart());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
	}
}