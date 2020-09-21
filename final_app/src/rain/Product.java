package rain;

/**
 *  This abstract class represents a basic Product.  It describes the
 *  name, price and product id.  <p>
 *
 *  @author 570 Development Team
 */
public abstract class Product implements java.io.Serializable {


	/**
	 *  Returns the product name
	 */
	public abstract String getName();

	/**
	 *  Sets the product name
	 */
	public abstract void setName(String theName);

	/**
	 *  Returns the product type
	 */
	public abstract String getType();

	/**
	 *  Returns the product price
	 */
	public abstract double getPrice();

	/**
	 *  Sets the price
	 */
	public abstract void setPrice(double thePrice);

	/**
	 *  Returns the product id
	 */
	public abstract int getId();

	/**
	 *  Sets the id
	 */
	public abstract void setId(int theId);

	/**
	 *  Returns the current stock count
	 */
	public abstract int getStockCount();

	/**
	 *  Returns true if the product is in stock
	 */
	public abstract boolean isInStock();

}