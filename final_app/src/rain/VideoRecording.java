package rain;

import java.sql.*;

/**
 *  This class represents a VideoRecording.  It is a specialized version
 *  of the Recording class and adds the following data members:
 *  director, actors, rating, yearReleased.  <p>
 *
 *
 *  @author 570 Development Team
 */
public class VideoRecording extends Recording {

	//
	//  DATA MEMBERS
	//

	/**
	 *  The director's name
	 */
	private String director;

	/**
	 *  A list of actors starring in the video recording
	 */
	private String[] actors;

	/**
	 *  The rating:  G, PG, R, YYY
	 */
	private String rating;

	/**
	 *  The year the video recording was released
	 */
	private int yearReleased;

	/**
	 *  The length of video recording
	 */
	private Duration duration;


	//
	//  CONSTRUCTORS
	//

	/**
	 *  Default constructor
	 */
	public VideoRecording() {
		// set the data members using the setter methods
	}

	/**
	 *  Constructs an video product based on a result set row.
	 *
	 *  <p>The result set should contain the following columns:
	 *
	 *  <ul>
	 *		<li> recording_id
	 *		<li> director
	 *		<li> title
	 *		<li> category
	 *		<li> image_name
	 *		<li> duration
	 *		<li> rating
	 *		<li> year_released
	 *		<li> price
	 *		<li> stock_count
	 *  </ul>
	 *
	 *  @exception SQLException thrown if column doesn't exist
	 */
	public VideoRecording(ResultSet theResultSet) throws SQLException {

		setId(theResultSet.getInt("recording_id"));
		setDirector(theResultSet.getString("director"));
		setTitle(theResultSet.getString("title"));
		setCategory(theResultSet.getString("category"));
		setImageName(theResultSet.getString("image_name"));

		setDuration(new Duration(theResultSet.getInt("duration")));

		setRating(theResultSet.getString("rating"));
		setYearReleased(theResultSet.getInt("year_released"));
		setPrice(theResultSet.getDouble("price"));
		setStockCount(theResultSet.getInt("stock_count"));

		actors = null;
	}

	/**
	 *  Creates a VideoRecording object with given parameter values
	 */
	public VideoRecording(String theDirector, String theRating, int theYearReleased,
						  String theTitle, double thePrice,
						  String theCategory, String theImageName,
						  Duration theDuration) {

		this(theDirector, null, theRating, theYearReleased, theTitle, thePrice, theCategory, theImageName, theDuration);
	}

	/**
	 *  Creates a VideoRecording object with given parameter values
	 */
	public VideoRecording(String theDirector, String[] theActors, String theRating, int theYearReleased,
						  String theTitle, double thePrice,
						  String theCategory, String theImageName,
						  Duration theDuration) {

		super(theTitle, thePrice, theCategory, theImageName);

		director = theDirector;
		actors = theActors;
		rating = theRating;
		yearReleased = theYearReleased;
		duration = theDuration;
	}

	/**
	 *  Creates a VideoRecording object with given parameter values
	 */
	public VideoRecording(String theDirector, String[] theActors, String theRating, int theYearReleased,
						  String theTitle, double thePrice,
						  String theCategory, String theImageName,
						  Duration theDuration, int theRecordingId) {

		super(theTitle, thePrice, theCategory, theImageName, theRecordingId);

		director = theDirector;
		actors = theActors;
		rating = theRating;
		yearReleased = theYearReleased;
		duration = theDuration;

	}



	//
	//  GETTER / SETTER METHODS
	//

	/**
	 *  Returns the director's name
	 */
	public String getDirector() {
		return director;
	}

	/**
	 *  Sets the director
	 */
	public void setDirector(String theDirector) {
		director = theDirector;
	}

	/**
	 *  Returns the list of actors
	 */
	public String[] getActors() {
		return actors;
	}

	/**
	 *  Sets the list of actors
	 */
	public void setActors(String[] theActors) {
		actors = theActors;
	}

	/**
	 *  Returns the rating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 *  Sets the rating
	 */
	public void setRating(String theRating) {
		rating = theRating;
	}

	/**
	 *  Returns the year released
	 */
	public int getYearReleased() {
		return yearReleased;
	}

	/**
	 *  Sets the year released
	 */
	public void setYearReleased(int year) {
		yearReleased = year;
	}

	/**
	 *  Returns the duration of the video recording
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 *  Sets the duration
	 */
	public void setDuration(Duration theDuration) {
		duration = theDuration;
	}

	/**
	 *  Returns the title of recording
	 */
	public String toString() {
		return getTitle();
	}

	@Override
	public String getType() {
		return "video";
	}

}