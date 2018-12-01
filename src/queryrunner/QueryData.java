/*
 * Carrie Schaden
 * CPSC 5011, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package queryrunner;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class QueryData.
 *
 * @author mckeem
 */
public class QueryData {
	
	/**
	 * Instantiates a new query data.
	 */
	QueryData() {
	}

	/**
	 * Instantiates a new query data.
	 *
	 * @param query the query
	 * @param parms the parms
	 * @param likeparms the likeparms
	 * @param isAction the is action
	 * @param isParm the is parm
	 */
	QueryData(String query, String[] parms, boolean[] likeparms, boolean isAction, boolean isParm) {
		m_queryString = query;
		m_arrayParms = parms;
		m_arrayLikeParms = likeparms;
		m_isAction = isAction;
		m_isParms = isParm;
	}

	/**
	 * Gets the query string.
	 *
	 * @return the string
	 */
	String GetQueryString() {
		return m_queryString;
	}

	/**
	 * Gets the parm amount.
	 *
	 * @return the int
	 */
	int GetParmAmount() {
		if (m_arrayParms == null)
			return 0;
		else
			return m_arrayParms.length;
	}

	/**
	 * Gets the param text.
	 *
	 * @param index the index
	 * @return the string
	 */
	String GetParamText(int index) {
		return m_arrayParms[index];
	}

	/**
	 * Gets the like param.
	 *
	 * @param index the index
	 * @return true, if successful
	 */
	boolean GetLikeParam(int index) {
		return m_arrayLikeParms[index];
	}

	/**
	 * Gets the all like params.
	 *
	 * @return the boolean[]
	 */
	boolean[] GetAllLikeParams() {
		return m_arrayLikeParms;
	}

	/**
	 * Checks if is query action.
	 *
	 * @return true, if successful
	 */
	boolean IsQueryAction() {
		return m_isAction;
	}

	/**
	 * Checks if is query parm.
	 *
	 * @return true, if successful
	 */
	boolean IsQueryParm() {
		return m_isParms;
	}

	/** The m query string. */
	private String m_queryString;
	
	/** The m array parms. */
	private String[] m_arrayParms;
	
	/** The m is action. */
	private boolean m_isAction;
	
	/** The m is parms. */
	private boolean m_isParms;
	
	/** The m array like parms. */
	private boolean[] m_arrayLikeParms;
}
