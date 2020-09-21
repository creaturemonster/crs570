package rain;


/**
 *  This class represents a Recording.  It describes the recording
 *  title, price, category, imageName and duration.  <p>
 *
 *  Subclasses must implement the method <code>getDuration()</code> to
 *  return the total duration for the recording.
 *
 *  @author 570 Development Team
 */
@SuppressWarnings("serial")
public abstract class Recording extends Product implements Comparable<Recording> {

	//
	//  DATA MEMBERS
	//

	/**
	 *  The recording title
	 */
	private String title;

	/**
	 *  The recording price
	 */
	private double price;

	/**
	 *  The recording category
	 */
	private String category;

	/**
	 *  The recording image name
	 */
	private String imageName;

	/**
	 *	The recording id
	 */
	private int recordingId;

	/**
	 *  The quantity of stock on hand
	 */
	private int stockCount;


	//
	//  CONSTRUCTORS
	//

	/**
	 *  Basic default constructor
	 */
	public Recording() {
		// default constructor
	}


	/**
	 *  Constructs a recording w/ given parameter values
	 */
	public Recording(String theTitle, double thePrice,
					 String theCategory, String theImageName) {

		title = theTitle;
		price = thePrice;
		category = theCategory;
		imageName = theImageName;
	}

	/**
	 *  Constructs a recording w/ given parameter values
	 */
	public Recording(String theTitle, double thePrice,
					 String theCategory, String theImageName,
					 int theRecordingId) {

		this(theTitle, thePrice, theCategory, theImageName);

		recordingId = theRecordingId;
	}


	/**
	 *  Constructs a recording w/ given parameter values
	 */
	public Recording(String theTitle, double thePrice,
					 String theCategory, String theImageName,
					 int theRecordingId, int theStockCount) {

		this(theTitle, thePrice, theCategory, theImageName, theRecordingId);

		stockCount = theStockCount;
	}



	//
	//  ABSTRACT METHOD(S)
	//

	/**
	 *  Returns the recording duration.  Subclasses must
	 *  override this method to return the total duration.
	 */
	public abstract Duration getDuration();



	//
	//  GETTER / SETTER METHODS
	//

	/**
	 *  Returns the recording title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 *  Sets the recording title
	 */
	public void setTitle(String theTitle) {
		title = theTitle;
	}


	/**
	 *  Returns the recording price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 *  Sets the recording price
	 */
	public void setPrice(double thePrice) {
		price = thePrice;
	}


	/**
	 *  Returns the recording category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 *  Sets the recording category
	 */
	public void setCategory(String theCategory) {
		category = theCategory;
	}


	/**
	 *  Returns the recording image name
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 *  Sets the recording image name
	 */
	public void setImageName(String theImageName) {
		imageName = theImageName;
	}

	/**
	 *  Returns the recording id.
	 */
	public int getId() {
		return recordingId;
	}

	/**
	 *  Sets the recording id
	 */
	public void setId(int theRecordingId) {
		recordingId = theRecordingId;
	}


	/**
	 *  Returns the name of the product
	 */
	public String getName() {
		return toString();
	}

	/**
	 *  Sets the name / title
	 */
	public void setName(String theName) {
		setTitle(theName);
	}

	/**
	 *  Returns the stock count - quantity on hand
	 */
	public int getStockCount() {
		return stockCount;
	}

	/**
	 *  Sets the stock count
	 */
	public void setStockCount(int theCount) {
		stockCount  = theCount;
	}

	/**
	 *  Returns true if the product is in stock
	 */
	public boolean isInStock() {
		return stockCount > 0;
	}


	/**
	 *  Allow us to sort the recordings by title
	 */
	public int compareTo(Recording recording) {
		return title.compareTo(recording.getTitle());
	}

}