package rain;

import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSession;

public class ShoppingCartSessionListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session = event.getSession();

		//	Create a shopping cart and add it to the session
		ShoppingCart theCart = new ShoppingCart();
		session.setAttribute(Constants.LTREE_CART_KEY, theCart);
	}


	public void sessionDestroyed(HttpSessionEvent event) {
	}

}