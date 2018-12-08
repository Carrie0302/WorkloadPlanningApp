/*
 * Team 5
 * CPSC 5021, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package queryrunner;

import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * The Class QueryJDBC makes a connection to the database.
 *
 * @author mckeem, carrie
 */
public class QueryJDBC {

	/** The connection. */
	public Connection connection = null;

	/**
	 * Instantiates a new query JDBC.
	 */
	QueryJDBC() {
		updateAmount = 0;
	}

	/**
	 * Gets the error.
	 *
	 * @return the string
	 */
	public String GetError() {
		return conError;
	}

	/**
	 * Gets the headers.
	 *
	 * @return the string[] headers
	 */
	public String[] GetHeaders() {
		return this.headers;
	}

	/**
	 * Gets the data.
	 *
	 * @return the string[][] with data
	 */
	public String[][] GetData() {
		return this.allRows;
	}

	/**
	 * Gets the update count.
	 *
	 * @return the amount of rows updated
	 */
	public int GetUpdateCount() {
		return updateAmount;
	}

	/**
	 * Execute query.
	 *
	 * @param szQuery   the query
	 * @param parms     the parameters
	 * @param likeparms the parameters using like
	 * @return true if executed successfully
	 */
	public boolean ExecuteQuery(String szQuery, String[] parms,
			boolean[] likeparms) {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int nColAmt;

		// Try to get the columns and the amount of columns
		try {
			preparedStatement = this.connection.prepareStatement(szQuery);
			int nParamAmount = parms.length;
			for (int i = 0; i < nParamAmount; i++) {
				String parm = parms[i];
				if (likeparms[i] == true) {
					parm += "%";
				}
				preparedStatement.setString(i + 1, parm);
			}
			resultSet = preparedStatement.executeQuery();
			ResultSetMetaData rsmd = resultSet.getMetaData();
			nColAmt = rsmd.getColumnCount();

			pullHeaders(rsmd, nColAmt);
			pullData(resultSet, nColAmt);

			preparedStatement.close();
			resultSet.close();
		} catch (SQLException ex) {
			this.conError = "SQLException: " + ex.getMessage();
			this.conError += "SQLState: " + ex.getSQLState();
			this.conError += "VendorError: " + ex.getErrorCode();

			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;

		}
		return true;
	}

	/**
	 * Execute update.
	 *
	 * @param szQuery the query
	 * @param parms   the parameters
	 * @return true if executed successfully
	 */
	public boolean ExecuteUpdate(String szQuery, String[] parms) {
		PreparedStatement preparedStatement = null;
		updateAmount = 0;
		// Get the columns and the amount of columns
		try {
			preparedStatement = this.connection.prepareStatement(szQuery);
			int nParamAmount = parms.length;

			for (int i = 0; i < nParamAmount; i++) {
				preparedStatement.setString(i + 1, parms[i]);
			}
			updateAmount = preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException ex) {
			this.conError = "SQLException: " + ex.getMessage();
			this.conError += "SQLState: " + ex.getSQLState();
			this.conError += "VendorError: " + ex.getErrorCode();
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}

		return true;
	}

	/**
	 * Connect to database.
	 *
	 * @param host     the host
	 * @param user     the user
	 * @param pass     the pass
	 * @param database the database
	 * @return true if connected to database successfully
	 */
	public boolean ConnectToDatabase(String host, String user, String pass, 
			String database) {
		url = "jdbc:mysql://";
		url += host;
		url += ":3306/";
		url += database;
		url += "?useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode="
				+ "false&serverTimezone=UTC";
		try {
			Class.forName(DB_DRV).newInstance();
			connection = DriverManager.getConnection(url, user, pass);

		} catch (SQLException ex) {
			conError = "SQLException: " + ex.getMessage() + ex.getSQLState()
				+ ex.getErrorCode();
			return false;
		} catch (Exception ex) {
			// handle the error
			conError = "SQLException: " + ex.getMessage();
		}
		return true;
	}

	/**
	 * Closes the database connection and throws .
	 *
	 * @return true, if successful
	 * @returns false if the connection was not closed
	 */
	public boolean CloseDatabase() {
		try {
			connection.close();
		} catch (SQLException ex) {
			conError = "SQLException: " + ex.getMessage();
			conError = "SQLState: " + ex.getSQLState();
			conError = "VendorError: " + ex.getErrorCode();
			return false;
		} catch (Exception ex) {
			conError = "Error was " + ex.toString();
			return false;
		}

		return true;
	}

	/** The database drive. */
	private static final String DB_DRV = "com.mysql.cj.jdbc.Driver";

	/** The connection error. */
	private String conError = "";

	/** The url. */
	private String url;

	/** The headers. */
	private String[] headers;

	/** All rows from query results. */
	private String[][] allRows;

	/** The update amount. */
	private int updateAmount = 0;

	/**
	 * Populates the array with the queries data
	 * 
	 * @param resultSet SQL results
	 * @param nColAmt   column count in results
	 * @throws SQLException
	 */
	private void pullData(ResultSet resultSet, int nColAmt) 
			throws SQLException {
		// Get row count
		resultSet.last();
		int amtRow = resultSet.getRow();
		resultSet.beforeFirst();

		this.allRows = new String[amtRow][nColAmt];

		// Add data to array
		int nCurRow = 0;
		while (resultSet.next()) {
			for (int i = 0; i < nColAmt; i++) {
				allRows[nCurRow][i] = resultSet.getString(i + 1);
			}
			nCurRow++;
		}
	}

	/**
	 * Populates the array with column names from query
	 * 
	 * @param rsmd    the meta data of the result set
	 * @param nColAmt number of columns
	 * @throws SQLException
	 */
	private void pullHeaders(ResultSetMetaData rsmd, int nColAmt) 
			throws SQLException {
		// Get headers
		String colName = "";
		headers = new String[nColAmt];
		for (int i = 0; i < nColAmt; i++) {
			colName = rsmd.getColumnLabel(i + 1);
			headers[i] = colName.replaceAll("_", " ");
		}
	}

}
