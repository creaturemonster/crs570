package rain;

/**
 *  This class represents a track (ie a song).  This class describes
 *  the track's title and duration.
 *
 *   <pre>
 *     Usage Example:
 *
 *     Duration firstDuration = new Duration(0, 4, 30);   // hr, min, sec
 *     Track firstTrack = new Track("Tooty Fruity", firstDuration);
 *
 *   </pre>
 *
 *  @author 570 Development Team
 */
public class Track implements java.io.Serializable {

	//
	// FIELDS
	//

	/**
	 *  The track title
	 */
	protected String title;

	/**
	 *  The track duration
	 */
	protected Duration duration;

  	//
	// CONSTRUCTORS
  	//

	/**
	 *  Default constructor.  Simply creates an empty track.
	 */
	public Track() {
		title = "empty";
		duration = new Duration();
	}

  	/**
	 *  Creates a Track with the given parameter values
	 */
	public Track(String aTitle, Duration aDuration) {
		title = aTitle;
		duration = aDuration;
	}


  	//
	// METHODS
	//

	/**
	 *  Returns the title of the track
	 */
	public String getTitle() {
		return title;
	}

	/**
	 *  Sets the title of the track
	 */
	public void setTitle(String	aTitle) {
		title = aTitle;
	}

	/**
	 *  Returns the duration of the track
	 */
	public Duration getDuration() {
		return duration;
	}

	/**
	 *  Sets the duration of the track
	 */
	public void setDuration(Duration aDuration) {
		duration = aDuration;
	}

	/**
	 *  Returns a string representation of the Track.  It
	 *  includes the track title and running time. <p>
	 *
	 *  The string has the following format:
	 *
	 *   <pre>
	 *    title, duration
	 *   </pre>
	 */
	public String toString() {

		int totalSeconds = duration.getTotalSeconds();
		int min = totalSeconds / 60;
		int secs = totalSeconds % 60;

		String secsStr = "";

		// pad the seconds w/ leading zero if single digit
		if (secs < 10)
		{
			secsStr = "0";
		}

		secsStr += Integer.toString(secs);

		return title + ",  " + min + ":" + secsStr;
	}
}