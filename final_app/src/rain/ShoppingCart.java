package rain;

import java.text.DecimalFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *  This class describes a shopping cart.
 *
 *  <p>
 *  A shopping cart is a collection of shopping cart items.  You can
 *  add items to the cart and remove them.  Methods are available to
 *  get a list of the cart items.
 *  </p>
 *
 *  Usage Example:
 *
 *  <pre>
 *
 *		//
 *		//  Create the shopping cart
 *		//
 *		ShoppingCart userCart = new ShoppingCart();
 *
 *
 *		//
 *		//  Add to the cart
 *		Product theProduct = ...
 *		userCart.add(theProduct);
 *
 *
 *		//
 *		//  Remove items from the cart
 *		//
 *		userCart.remove(theProductId);
 *
 *
 *		//
 *		//  Get a list of shopping cart items
 *		//
 *		Collection&lt;ShoppingCartItem&gt; items = userCart.getItems();
 *
 *
 *		//
 *		//  Show the contents of the shopping cart
 *		//
 *
 *		for (ShoppingCartItem tempItem : items) {
 *			Product tempProduct = tempItem.getProduct();
 *
 *			System.out.println(tempProduct);
 *			System.out.println("Quantity: " + tempItem.getQuantity());
 *		}
 *
 *  </pre>
 *
 */
@SuppressWarnings("serial")
public class ShoppingCart implements java.io.Serializable {

	/**
	 *  A collection of items in the shopping cart
	 */
    private Map<String, ShoppingCartItem> items;

    /**
     *  The number of products in the shopping cart
     */
    private int numberOfProducts;

	/**
	 *  Constructs an empty shopping cart
	 */
    public ShoppingCart() {
        items = new HashMap<String, ShoppingCartItem>();
    	numberOfProducts = 0;
    }

	/**
	 *  Add to the shopping cart.
	 *
	 *  This is accomplished with the following steps:
	 *
	 *  <ol>
	 *  <li> Check to see if the product id exists in the cart </li>
	 *  <li> If so then grab that item and increment its quantity </li>
	 *  <li> Else create the cart item and add it to the cart </li>
	 *  </ol>
	 */
    public void add(Product theProduct) {

		ShoppingCartItem cartItem = null;
		String theProductId = Integer.toString(theProduct.getId());

        if (items.containsKey(theProductId)) {
            cartItem = items.get(theProductId);
            cartItem.incrementQuantity();
        } else {
            cartItem = new ShoppingCartItem(theProduct);
            items.put(theProductId, cartItem);
        }

        numberOfProducts++;
	}


	/**
	 *  Add to the shopping cart.
	 *
	 *  @see #add(Product theProduct)
	 */
    public void add(ShoppingCartItem item) {
		add(item.getProduct());
    }

	/**
	 *  Removes a product from the shopping cart.
	 *
	 *  <p> This is accomplished with the following steps:
	 *
	 *  <ol>
	 *  	<li>  Check to see if the product id exists in the cart
	 *  	<li>  If so then grab that item and decrement its quantity
	 *  	<li>  If this is the last cartItem then remove it from the shopping cart
	 *  </ol>
	 */
    public void remove(String theProductId) {
        if (items.containsKey(theProductId)) {
            ShoppingCartItem cartItem = items.get(theProductId);
            cartItem.decrementQuantity();

            if (cartItem.getQuantity() <= 0) {
                items.remove(theProductId);
			}

            numberOfProducts--;
        }
    }


	/**
	 *  Returns an collection of <code>ShoppingCartItem</code>s
	 */
    public Collection<ShoppingCartItem> getItems() {
        return items.values();
    }

	/**
	 *  Returns the number of products in the shopping cart
	 */
    public int getNumberOfProducts() {
        return numberOfProducts;
    }

	/**
	 *  Returns the total price of all products in the shopping cart
	 */
	public double getTotalPrice() {

		double totalPrice = 0.0;

		Product tempProduct = null;
		int quantity = 0;

		for (ShoppingCartItem cartItem : items.values()) {
			tempProduct = cartItem.getProduct();
			quantity = cartItem.getQuantity();

			totalPrice += (tempProduct.getPrice() * quantity);
		}

		return totalPrice;
	}

	/**
	 *  Clears out the cart
	 */
    public void clear() {
        items.clear();
        numberOfProducts = 0;
    }

    /**
     *  Returns true if number of products == 0, else false
     */
    public boolean isEmpty() {

		return (numberOfProducts == 0);
	}

	public static String formatTotal(double total) {
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(total);
	}

}

