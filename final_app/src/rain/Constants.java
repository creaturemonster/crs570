package rain;

/**
 *  Collection of constants for the RainForest application
 *
 *  @author 570 Development Team
 */
public interface Constants {

	/**
	 * String constant for HTML5 table style
	 */
	public static final String TABLE_CSS = 
			  "<style>"
			+ "  table {"
			+ "	   border-collapse: collapse;"
			+ "	 }"
			+ "	 td, th {"
			+ "    border: 1px solid #999;"
			+ "    padding: 0.5rem;"
			+ "    text-align: left;"
			+ "  }"
			+ "</style>";

	/**
	 *  Alias for the shopping cart
	 */
    public static final String LTREE_CART_KEY		= "LTREE_CART";

	/**
	 *  Alias for the video data accessor
	 */
    public static final String VIDEO_DAO_KEY	= "VIDEO_DAO";

	/**
	 *  Alias for the video list
	 */
    public static final String VIDEO_LIST_KEY	= "VIDEO_LIST";

	/**
	 *  Alias for the video recording
	 */
    public static final String VIDEO_RECORDING_KEY	= "VIDEO_RECORDING";

	/**
	 *  Alias for the music data accessor
	 */
    public static final String MUSIC_DAO_KEY	= "MUSIC_DAO";

	/**
	 *  Alias for the music list
	 */
    public static final String MUSIC_LIST_KEY	= "MUSIC_LIST";

	/**
	 *  Alias for the music recording
	 */
    public static final String MUSIC_RECORDING_KEY	= "MUSIC_RECORDING";
    
	/**
	 * Alias for the data source name
	 */
	public static final String DATA_SOURCE_NAME	= "java:comp/env/jdbc/RainForestDB";
   

}