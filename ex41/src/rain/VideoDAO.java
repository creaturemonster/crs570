package rain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.*;

import javax.sql.DataSource;

/**
 *  This class provides a data access mechanism for the video recordings database.
 *  Methods are available to get a list of video recordings.   <p>
 *
 *  Usage Example:
 *  <pre>
 *
 *    // create the data accessor
 *    VideoDAO myVideoDAO = new VideoDAO(theDataSource);
 *
 *    ...
 *
 *    // get a list of "Comedy" video recordings
 *    List&lt;VideoRecording&gt; videoList = myVideoDAO.getVideoRecordings("Comedy");
 *    ...
 *
 *    // get a list of "Comedy" video recordings sorted by price (ascending)
 *    List&lt;VideoRecording&gt; videoList = myVideoDAO.getVideoRecordings("Comedy", VideoDAO.SORT_BY_PRICE);
 *    ...
 *  </pre>
 *
 *  @author 570 Development Team
 */
public class VideoDAO extends DAO {

	/**
	 *  Constructs the data accessor with the given data source
	 */
	public VideoDAO(DataSource theDataSource) {
		super(theDataSource);
	}

	/**
	 *  Get a list of video categories from the database
	 *
	 *  @return a list of video categories
	 *  @throws DAOException error accessing the data source
	 */
	public List<String> getVideoCategories() throws DAOException {

		List<String> theList = new ArrayList<String>();

		Connection tempConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			tempConn = dataSource.getConnection();
			myStmt = tempConn.createStatement();

			String videoSql = "SELECT name FROM Video_Categories";

			myRs = myStmt.executeQuery(videoSql);

			while (myRs.next()) {
				String cat = myRs.getString("name");
				theList.add(cat);
			}
		} catch (SQLException exc) {
			logError(exc);
			throw new DAOException(exc);
		} finally {
			cleanup(myRs, myStmt, tempConn);
		}

		return theList;
	}

	/**
	 *  Get a sorted list of video recordings from the database for the given category. <br>
	 *  The list is sorted by title.
	 *
	 *	@param theCategory the category
	 *  @return a list of VideoRecording objects
	 *  @throws DAOException error accessing the data source
	 *	@see #getVideoRecordings(String theCategory, int sortBy)
	 */
	public List<VideoRecording> getVideoRecordings(String theCategory) throws DAOException {

		return getVideoRecordings(theCategory, VideoDAO.SORT_BY_TITLE);
	}

	/**
	 *  Get a sorted list of video recordings from the database for the given category. <br>
	 *  The sort order is ascending.
	 *
	 *  @param theCategory the category name
	 *  @param sortBy the key to sort by
	 *  @return a list of sorted <code>VideoRecording</code> objects
	 *  @throws DAOException error accessing the data source
	 *	@see #SORT_BY_TITLE
	 *	@see #SORT_BY_PRICE
	 *	@see #SORT_BY_STOCK_COUNT
	 */
	public List<VideoRecording> getVideoRecordings(String theCategory, int sortBy)
		throws DAOException {

		List<VideoRecording> theList = new ArrayList<VideoRecording>();

		Connection tempConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			// get a connection from the pool and create statement
			tempConn = dataSource.getConnection();
			myStmt = tempConn.createStatement();

			// setup the base SQL query
			String videoSql =
				"SELECT recording_id, director, title, category, image_name, duration, rating, year_released, price, stock_count FROM Video_Recordings";

			// setup the where clause for the category
			if (!theCategory.equalsIgnoreCase("all")) {
				videoSql += " WHERE category LIKE '" + theCategory + "%'";
			}

			// determine the field to sort on
			String field = null;
			switch (sortBy) {
				case VideoDAO.SORT_BY_TITLE :
					field = "title";
					break;
				case VideoDAO.SORT_BY_PRICE :
					field = "price";
					break;
				case VideoDAO.SORT_BY_STOCK_COUNT :
					field = "stock_count";
					break;
				default :
					field = "title";
			}
			videoSql += " ORDER BY " + field;

			// now execute the query
			myRs = myStmt.executeQuery(videoSql);

			// build a list based on the query
			while (myRs.next()) {
				VideoRecording tempRecording = new VideoRecording(myRs);
				theList.add(tempRecording);
			}
		} catch (SQLException exc) {
			logError(exc);
			throw new DAOException(exc);
		} finally {
			cleanup(myRs, myStmt, tempConn);
		}

		return theList;
	}

	/**
	 *  Returns a video recording based on the id
	 *
	 *	@param recordingId the recording id
	 *	@return a <code>VideoRecording</code> object for the given recording id
	 *  @throws DAOException error accessing the data source
	 * 	@see #getVideoRecording
	 */
	public VideoRecording getVideoRecording(String recordingId)
		throws DAOException {

		int rid = Integer.parseInt(recordingId);
		return getVideoRecording(rid);
	}

	/**
	 *  Returns a video recording based on the id
	 *
	 *  @throws DAOException error accessing the data source
	 */
	public VideoRecording getVideoRecording(int recordingId)
		throws DAOException {

		VideoRecording tempRecording = null;

		Connection tempConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			tempConn = dataSource.getConnection();
			myStmt = tempConn.createStatement();
			myRs =
				myStmt.executeQuery(
					"SELECT recording_id, director, title, category, image_name, duration, rating, year_released, price, stock_count FROM Video_Recordings WHERE recording_id="
						+ recordingId);

			while (myRs.next()) {
				tempRecording = new VideoRecording(myRs);
			}
		} catch (SQLException exc) {
			logError(exc);
			throw new DAOException(exc);
		} finally {
			cleanup(myRs, myStmt, tempConn);
		}

		return tempRecording;
	}

	/**
	 * Simple method to add a video recording
	 *
	 * @return the recording id of new video
	 */
	public int addVideoRecording(String title, String category, double price)
	  throws DAOException {

		int recordingId;

		String director = "Joe Director";
		String imageName = "570.jpg";
		int durationMinutes = 90;
		String rating = "PG-13";
		int currentYear = getCurrentYear();
		int stockCount = 100;

		recordingId = addVideoRecording(director, title, category, imageName, durationMinutes, rating, currentYear, price, stockCount);

		return recordingId;
	}

	/**
	 * Add a video recording.
	 *
     * @return the recording id of new video
	 */
	public int addVideoRecording(String director, String title, String category, String imageName, int durationMinutes, String rating, int yearReleased, double price, int stockCount)
	  throws DAOException {

		int recordingId;
		Connection tempConn = null;
		PreparedStatement myStmt = null;
		ResultSet myRs = null;

		try {
			tempConn = dataSource.getConnection();

			// set up the basic data
			recordingId = getNextRecordingId();

			String sql = "insert into Video_Recordings"
					   + " (recording_id, director, title, category, image_name, duration, rating, year_released, price, stock_count)"
					   + " values"
					   + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			myStmt = tempConn.prepareStatement(sql);

			// set the parameters
			myStmt.setInt(1, recordingId);
			myStmt.setString(2, director);
			myStmt.setString(3, title);
			myStmt.setString(4, category);
			myStmt.setString(5, imageName);
			myStmt.setInt(6, durationMinutes * 60);  // store as total seconds
			myStmt.setString(7, rating);
			myStmt.setInt(8, yearReleased);
			myStmt.setDouble(9, price);
			myStmt.setInt(10, stockCount);

			myStmt.executeUpdate();

			myStmt.close();
		} catch (SQLException exc) {
			logError(exc);
			throw new DAOException(exc);
		} finally {
			cleanup(myRs, myStmt, tempConn);
		}

		return recordingId;
	}

	/**
	 * Get the next recording id. This is a hack...for testing only.
	 */
	private int getNextRecordingId() {

		int nextRecordingId;

		Calendar now = new GregorianCalendar();
		now.setTime(new java.util.Date());

		int second = now.get(Calendar.SECOND);
		int milli = now.get(Calendar.MILLISECOND);

		String stamp = "" + second+ milli + (int) (Math.random() * 999);

		nextRecordingId = Integer.parseInt(stamp);

		return nextRecordingId;
	}


	/**
	 * Helper method to get current year
	 */
	private int getCurrentYear() {
		int year;

		Calendar now = new GregorianCalendar();
		now.setTime(new java.util.Date());
		year = now.get(Calendar.YEAR);

		return year;
	}

}