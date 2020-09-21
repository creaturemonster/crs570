package rain;

/**
 *  This class describes a basic shopping cart item. <p>
 *
 *  A shopping cart item is composed of:
 *
 *  <ul>
 *    <li> a product  (ie video recording)
 *    <li> a quantity
 *  </ul>
 *
 */
public class ShoppingCartItem implements java.io.Serializable {

	/**
	 *  The actual product
	 */
    private Product theProduct;

    /**
     *  The number of "products"
     */
    private int quantity;

	/**
	 *  Creates a shopping cart item based on the product passed in
	 */
    public ShoppingCartItem(Product aProduct) {
        theProduct = aProduct;
        quantity = 1;
    }

	/**
	 *  Simply returns the product
	 */
    public Product getProduct() {
        return theProduct;
    }

	/**
	 *  Returns the quantity for this product
	 */
    public int getQuantity() {
        return quantity;
    }

	/**
	 *  Increments the quantity for this product
	 */
    public void incrementQuantity() {
        quantity++;
    }

	/**
	 *  Decrements the quantity for this product
	 */
    public void decrementQuantity() {
        quantity--;
    }

}









