package rain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 *  This class provides a data access mechanism for the music database.
 *  Methods are available to get a list of the music categories and a
 *  list of recordings.   <p>
 *
 *  Usage Example:
 *  <pre>
 *
 *    // create the data accessor
 *    MusicDAO myMusicDAO = new MusicDAO(theDataSource);
 *
 *    // get a list of available categories;
 *    List&lt;String&gt; cats = myMusicDAO.getMusicCategories();
 *    ...
 *
 *    // get a list of jazz recordings
 *    List&lt;MusicRecording&gt; jazzRecordingList = myMusicDAO.getMusicRecordings("Jazz");
 *    ...
 *
 *    // get a list of jazz recordings sorted by price (ascending)
 *    List&lt;MusicRecording&gt; jazzRecordingList = myMusicDAO.getMusicRecordings("Jazz", MusicDAO.SORT_BY_PRICE);
 *    ...
 *
 *  </pre>
 *
 *  @author 570 Development Team
 */
public class MusicDAO extends DAO {

	/**
	 * Flag to sort by artist name
	 */
	public static final int SORT_BY_ARTIST = 10;


	/**
	 *  Constructs the data accessor with the given data source
	 *
	 */
	public MusicDAO(DataSource theDataSource) {
		super(theDataSource);
	}



	/**
	 *  Get a list of music categories from the database
	 *
	 *  @return a list of categories
	 *  @throws DAOException error accessing the data source
	 */
	public List<String> getMusicCategories() throws DAOException {

		List<String> categoryList = new ArrayList<String>();

		Connection tempConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			tempConn = dataSource.getConnection();
			myStmt = tempConn.createStatement();

			myRs = myStmt.executeQuery("SELECT name FROM Music_Categories");

			while (myRs.next()) {
				String category = myRs.getString("name");
				categoryList.add(category);
			}
		}
		catch (SQLException exc) {
			logError(exc);
			throw new DAOException(exc);
		}
		finally {
			cleanup(myRs, myStmt, tempConn);
		}

		return categoryList;
	}

	/**
	 *  Get a list of video recordings from the database for the given category. <br>
	 *  The list is sorted by artist name.
	 *
	 *  @return a list of MusicRecording objects
	 *  @throws DAOException error accessing the data source
	 *	@see #getMusicRecordings(String theCategory, int sortBy)
	 */
	public List<MusicRecording> getMusicRecordings(String theCategory) throws DAOException {

		return getMusicRecordings(theCategory, MusicDAO.SORT_BY_ARTIST);
	}


	/**
	 *  Get a list of music recordings from the database for the given category
	 *  The sort order is ascending.
	 *
	 *  @return a list of sorted MusicRecording objects
	 *	@see #SORT_BY_TITLE
	 *	@see #SORT_BY_PRICE
	 *	@see #SORT_BY_STOCK_COUNT
	 *	@see #SORT_BY_ARTIST
	 *
	 *  @return a list of MusicRecording objects
	 *  @throws DAOException error accessing the data source
	 */
	public List<MusicRecording> getMusicRecordings(String theCategory, int sortBy) throws DAOException {

		List<MusicRecording> recordingList = new ArrayList<MusicRecording>();

		Connection tempConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			tempConn = dataSource.getConnection();
			myStmt = tempConn.createStatement();

			String musicSql = "SELECT recording_id, artist_name, title, image_name, category, num_tracks, price, stock_count FROM Music_Recordings";

			if (!theCategory.equalsIgnoreCase("all")) {
				musicSql += " WHERE category LIKE '" + theCategory + "%'";
			}

			// determine the field to sort on
			String field = null;
			switch (sortBy) {
				case DAO.SORT_BY_TITLE:
					field = "title";
					break;
				case DAO.SORT_BY_PRICE:
					field = "price";
					break;
				case DAO.SORT_BY_STOCK_COUNT:
					field = "stock_count";
					break;
				case MusicDAO.SORT_BY_ARTIST:
					field = "artist_name";
					break;
				default:
					field = "artist_name";
			}
			musicSql += " ORDER BY " + field;

			myRs = myStmt.executeQuery(musicSql);

			while (myRs.next()) {
				MusicRecording tempRecording = new MusicRecording(myRs);
				recordingList.add(tempRecording);
			}

			//log(recordingList);
		}
		catch (SQLException exc) {
			logError(exc);
			throw new DAOException(exc);
		}
		finally {
			cleanup(myRs, myStmt, tempConn);
		}

		return recordingList;
	}


	/**
	 *  Returns a music recording based on the id
	 *
	 *  @throws DAOException error accessing the data source
	 *	@see #getMusicRecording(int recordingId)
	 */
	public MusicRecording getMusicRecording(String recordingId)
	  throws DAOException {

		int rid = Integer.parseInt(recordingId);
		return getMusicRecording(rid);
	}

	/**
	 *  Returns a music recording based on the id
	 *
	 *  @throws DAOException error accessing the data source
	 */
	public MusicRecording getMusicRecording(int recordingId)
	  throws DAOException {

		MusicRecording tempRecording = null;
		Connection tempConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			tempConn = dataSource.getConnection();
			myStmt = tempConn.createStatement();
			myRs = myStmt.executeQuery("SELECT recording_id, artist_name, title, category, image_name, num_tracks, price, stock_count FROM Music_Recordings WHERE recording_id=" + recordingId);

			if (myRs.next()) {

				int numTracks = myRs.getInt("num_tracks");

				tempRecording = new MusicRecording(myRs);

				// now let's build a list of tracks for the recording
				String trackSql = "SELECT title, duration FROM Music_Tracks WHERE recording_id=" + recordingId;
				Track[] trackList = new Track[numTracks];

				Statement trackStmt = tempConn.createStatement();
				ResultSet trackRs = trackStmt.executeQuery(trackSql);
				int index = 0;
				while (trackRs.next()) {
					String trackTitle = trackRs.getString("title");
					int trackSeconds = trackRs.getInt("duration");

					trackList[index] = new Track(trackTitle, new Duration(trackSeconds));
					index++;
				}

				trackRs.close();
				trackStmt.close();

				tempRecording.setTrackList(trackList);
			}
			else {
				throw new DAOException("Could not find recording id: " + recordingId);
			}
		}
		catch (SQLException exc) {
			logError(exc);
			throw new DAOException(exc);
		}
		finally {
			cleanup(myRs, myStmt, tempConn);
		}

		return tempRecording;
	}

}