package rain;

import java.sql.*;

/**
 *  This class represents a music recording.  It contains additional
 *  data members for the artist name and a list of tracks (ie songs).
 *
 *   <pre>
 *     Usage Example:
 *
 *        MusicRecording myRecording = new MusicRecording("John Lennon", myTrackList, "Double Fantasy",
 *                                                         12.99, "Rock", "my_image.gif");
 *
 *   </pre>
 *
 *  @author 570 Development Team
 */
public class MusicRecording extends Recording {


	/**
	 *  The name of the artist/band
	 */
	private String artist;

	/**
	 *  The list of tracks/songs
	 */
	private Track trackList[];

	/**
	 *  Default constructor
	 */
	public MusicRecording() {
		// set the data members using the setter methods
	}

	/**
	 *  Creates a MusicRecording object with given parameter values
	 *
	 */
	public MusicRecording(String theArtist, Track[] theTrackList,
						  String theTitle, double thePrice,
						  String theCategory, String theImageName) {

		super(theTitle, thePrice, theCategory, theImageName);

		artist = theArtist;
		trackList = theTrackList;
	}

	/**
	 *  Creates a MusicRecording object with given parameter values
	 *
	 */
	public MusicRecording(String theArtist, Track[] theTrackList,
						  String theTitle, double thePrice,
						  String theCategory, String theImageName,
						  int theRecordingId) {

		super(theTitle, thePrice, theCategory, theImageName, theRecordingId);

		artist = theArtist;
		trackList = theTrackList;
	}

	/**
	 *  Creates a MusicRecording object with given parameter values
	 *
	 */
	public MusicRecording(String theArtist,
						  String theTitle, double thePrice,
						  String theCategory, String theImageName,
						  int theRecordingId) {

		super(theTitle, thePrice, theCategory, theImageName, theRecordingId);

		artist = theArtist;
		trackList = null;
	}

	/**
	 *  Constructs an music recoding based on a result set row.
	 *
	 *  <p>The result set should contain the following columns:
	 *
	 *  <ul>
	 *		<li> recording_id
	 *		<li> artist_name
	 *		<li> title
	 *		<li> category
	 *		<li> image_name
	 *		<li> price
	 *		<li> stock_count
	 *  </ul>
	 *
	 *  When using this constructor, you'll have to set the track list by calling the
	 *  <code>setTrackList(Track[] theTrackList)</code> method.
	 *
	 *  @exception SQLException thrown if column doesn't exist
	 */
	public MusicRecording(ResultSet theResultSet) throws SQLException {

		setId(theResultSet.getInt("recording_id"));
		setArtist(theResultSet.getString("artist_name"));
		setTitle(theResultSet.getString("title"));
		setCategory(theResultSet.getString("category"));
		setImageName(theResultSet.getString("image_name"));

		setPrice(theResultSet.getDouble("price"));
		setStockCount(theResultSet.getInt("stock_count"));

		trackList = null;
	}

	/**
	 *  Returns the artist name
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 *  Sets the artist name
	 */
	public void setArtist(String theArtist) {
		artist = theArtist;
	}

	/**
	 *  Returns the track list
	 */
	public Track[] getTrackList() {

		return trackList;
	}

	/**
	 *  Sets the track list
	 */
	public void setTrackList(Track[] theTrackList) {
		trackList = theTrackList;
	}

	/**
	 *  Returns the recording duration.
	 *  Overrides the method from Recording.
	 *
	 *  Iterates over the list of tracks and keeps a running
	 *  total of each track's duration.
	 */
	public Duration getDuration() {

		Track tempTrack;
		Duration tempDuration;
		int total = 0;

		if (trackList == null) {
			return new Duration(0);
		}

		for (int i=0; i < trackList.length; i++) {
			tempTrack = trackList[i];
			tempDuration = 	tempTrack.getDuration();
			total += tempDuration.getTotalSeconds();
		}

		return new Duration(total);
	}

	/**
	 *  Returns the artist name and title of recording
	 */
	public String toString() {
		return artist + "  -  " + getTitle();
	}

	/**
	 *  Allow us to sort the recordings by artist name
	 */
	public int compareTo(Object object) {

		MusicRecording recording = (MusicRecording) object;
		String targetArtist = recording.getArtist();

		return artist.compareTo(targetArtist);
	}
	
	@Override
	public String getType() {
		return "music";
	}

}