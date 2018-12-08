/*
 * Team 5
 * CPSC 5021, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package queryrunner;
import java.util.ArrayList;

/**
 * The Class QueryData creates query objects 
 * with the associated parameters.
 *
 * @author mckeem, carrie
 */
public class QueryData {
	
	/**
	 * Instantiates a new query data.
	 *
	 * @param query the query
	 * @param parms the parameters
	 * @param likeparms the parameters using like
	 * @param isAction the is action
	 * @param isParm the is parameters
	 */
	QueryData(String query, String[] parms, boolean[] likeparms, 
			boolean isAction, boolean isParm) {
		this.queryString = query;
		this.arrayParms = parms;
		this.arrayLikeParms = likeparms;
		this.isParms = isParm;
	}

	/**
	 * Gets the query string.
	 *
	 * @return the string
	 */
	String GetQueryString() {
		return queryString;
	}

	/**
	 * Gets the amount of parameters.
	 *
	 * @return the parameters
	 */
	int GetParmAmount() {
		if (arrayParms == null)
			return 0;
		else
			return arrayParms.length;
	}

	/**
	 * Gets the parameters text.
	 *
	 * @param index the index
	 * @return the string
	 */
	String GetParamText(int index) {
		return arrayParms[index];
	}

	/**
	 * Gets the like parameters.
	 *
	 * @param index the index
	 * @return true, if successful
	 */
	boolean GetLikeParam(int index) {
		return arrayLikeParms[index];
	}

	/**
	 * Gets the all parameters that use like.
	 *
	 * @return the boolean[]
	 */
	boolean[] GetAllLikeParams() {
		return arrayLikeParms;
	}


	/**
	 * Checks if is query parameter.
	 *
	 * @return true, if it contains parameters
	 */
	boolean IsQueryParm() {
		return isParms;
	}

	/** The query string. */
	private String queryString;
	
	/** The array of all parameters. */
	private String[] arrayParms;
	
	/** Does it have parameters */
	private boolean isParms;
	
	/** The array contains like parameters */
	private boolean[] arrayLikeParms;
}
