package rain;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

/**
 * DAO: Base class for the data accessors
 *
 * @author 570 Development Team
 */
public abstract class DAO {

	/**
	 * Reference to the data source
	 */
	protected DataSource dataSource;

	/**
	 * Reference to logger
	 */
	protected Logger logger = Logger.getLogger("rain");

	/**
	 * Flag to sort by title
	 */
	public static final int SORT_BY_TITLE = 0;

	/**
	 * Flag to sort by price
	 */
	public static final int SORT_BY_PRICE = 1;

	/**
	 * Flag to sort by stock count
	 */
	public static final int SORT_BY_STOCK_COUNT = 2;

	/**
	 * Construct the DAO based on data source
	 *
	 * @param theDataSource
	 */
	public DAO(DataSource theDataSource) {
		setDataSource(theDataSource);
	}

	/**
	 * Set the data source
	 *
	 * @param theDataSource
	 */
	protected void setDataSource(DataSource theDataSource) {
		dataSource = theDataSource;
	}

	/**
	 * Utility method to clean up database resources.
	 *
	 * @param myRs the result set
	 * @param myStmt the statment
	 * @param tempConn the connection
	 */
	protected void cleanup(
		ResultSet myRs,
		Statement myStmt,
		Connection tempConn) {
		try {
			if (myRs != null) {
				myRs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if (myStmt != null) {
				myStmt.close();
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		try {
			if (tempConn != null) {
				tempConn.close();
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Log a message
	 * @param message
	 */
	protected void log(Object message) {
		logger.info(message.toString());
	}

	/**
	 * Log an error w/ the given message
	 * @param message
	 * @param thrown
	 */
	protected void logError(Object message, Throwable thrown) {
		logger.log(Level.SEVERE, message.toString(), thrown);
	}

	/**
	 * Log an error
	 * @param thrown
	 */
	protected void logError(Throwable thrown) {
		logError("", thrown);
	}
}
