/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package queryrunner;

import java.util.ArrayList;

/**
 * 
 * QueryRunner takes a list of Queries that are initialized in it's constructor
 * and provides functions that will call the various functions in the QueryJDBC
 * class which will enable MYSQL queries to be executed. It also has functions
 * to provide the returned data from the Queries. Currently the eventHandlers in
 * QueryFrame call these functions in order to run the Queries.
 */
public class QueryRunner {
	
	
	/**
	 * Experts Query
	 * Shows employee names and the number of years of experience
	 * by skill for those employees with over one year of experience in that skill.
	 */
	private void expertsQuery() {
		queryArray.add(new QueryData("SELECT * FROM Employees_OverOneYearofExprience_bySkill"
				, null, null, false, false)); 
		queryArray.add(new QueryData("SELECT * FROM Employees_OverOneYearofExprience_bySkill "
				+ "WHERE employee LIKE ?", new String[] { "Employee Name" }, new boolean[] { true }, 
				false, true)); 
		queryArray.add(new QueryData("SELECT * FROM Employees_OverOneYearofExprience_bySkill WHERE "
				+ "skill_name LIKE ?", new String[] { "Skill Name" },
				new boolean[] { true }, false, true));		
	}
	
	
	/**
	 * Top 5 Skills Query
	 * Shows the top five skills owned by employees in descending order
	 * based on the number of employees related to each skill
	 */
	private void top5SkillsQuery() {
		queryArray.add(new QueryData("SELECT * FROM Employees_TopFiveSkills"
				, null, null, false, false)); 
	}
	
	
	/**
	 * Recent Skill Updates For Staff Working on Projects for a Manager
	 * Shows the most recent skill updates for each staff working on the
	 * projects managed by certain project manager. Note: The query first identified the staff working on
	 * tasks under deliverables that belong to each project managed by Aarika Paike. Then
	 * the query determined the most recent skill acquired by each staff among their other
	 * skills.
	 */
	private void recentSkillUpdatesebyPMQuery() {
		//recent skill updates for employees under PM
		queryArray.add(new QueryData(
				"   SELECT"   + 
				"    Tasks.task_owner_user_id AS project_employee_id,"   + 
				"    Users_has_Skills.date_skill_acquired,"   + 
				"    Users_has_Skills.skill_id,"   + 
				"    Skills.skill_name"   + 
				"  FROM Projects,"   + 
				"  Deliverables,"   + 
				"  Tasks,"   + 
				"  Users_has_Skills,"   + 
				"  Skills"   + 
				"  WHERE"   + 
				"    Skills.skill_id = Users_has_Skills.skill_id"   + 
				"    AND Projects.project_id = Deliverables.project_id"   + 
				"    AND Deliverables.deliverable_id = Tasks.deliverable_id"   + 
				"    AND Projects.project_manager_id ="   + 
				"     (SELECT Users.user_ID FROM Users"   + 
				"     WHERE"   + 
				"     (Users.user_last_name LIKE ?) )"   + 
				"     AND Users_has_Skills.date_skill_acquired ="   + 
				"       (SELECT MAX(Users_has_Skills.date_skill_acquired)"   + 
				"       FROM Users_has_Skills"   + 
				"       WHERE"   + 
				"       Tasks.task_owner_user_id = Users_has_Skills.user_id)"   + 
				"  GROUP BY Tasks.task_owner_user_id;"  , 
				new String[] { "PM First Name" }, new boolean[] { true }, false, true));
	}
	
	
	public QueryRunner() {
		this.jdbcData = new QueryJDBC();
		updateAmount = 0;
		queryArray = new ArrayList<>();
		error = "";
		this.projectTeamApplication = "ManageUp"; 
		
		// Each row that is added to queryArray is a separate query. It does not work
		// on Stored procedure calls.
		// The 'new' Java keyword is a way of initializing the data that will be added
		// to QueryArray. Please do not change
		// Format for each row of queryArray is: (QueryText, ParamaterLabelArray[],
		// LikeParameterArray[], IsItActionQuery, IsItParameterQuery)

		// QueryText is a String that represents your query. It can be anything but
		// Stored Procedure
		// Parameter Label Array (e.g. Put in null if there is no Parameters in your
		// query, otherwise put in the Parameter Names)
		// LikeParameter Array is an array I regret having to add, but it is necessary
		// to tell QueryRunner which parameter has a LIKE Clause. If you have no
		// parameters, put in null. Otherwise put in false for parameters that don't use
		// 'like' and true for ones that do.
		// IsItActionQuery (e.g. Mark it true if it is, otherwise false)
		// IsItParameterQuery (e.g.Mark it true if it is, otherwise false)
		
		expertsQuery();
		top5SkillsQuery();
		recentSkillUpdatesebyPMQuery();

//		queryArray.add(new QueryData("insert into contact (contact_id, contact_name, contact_salary) values (?,?,?)",
//				new String[] { "CONTACT_ID", "CONTACT_NAME", "CONTACT_SALARY" }, new boolean[] { false, false, false },
//				true, true));// THIS NEEDS TO CHANGE FOR YOUR APPLICATION

	}

	public int GetTotalQueries() {
		return queryArray.size();
	}

	public int GetParameterAmtForQuery(int queryChoice) {
		QueryData e = queryArray.get(queryChoice);
		return e.GetParmAmount();
	}

	public String GetParamText(int queryChoice, int parmnum) {
		QueryData e = queryArray.get(queryChoice);
		return e.GetParamText(parmnum);
	}

	public String GetQueryText(int queryChoice) {
		QueryData e = queryArray.get(queryChoice);
		return e.GetQueryString();
	}

	/**
	 * Function will return how many rows were updated as a result of the update
	 * query
	 * 
	 * @return Returns how many rows were updated
	 */

	public int GetUpdateAmount() {
		return updateAmount;
	}

	/**
	 * Function will return ALL of the Column Headers from the query
	 * 
	 * @return Returns array of column headers
	 */
	public String[] GetQueryHeaders() {
		return jdbcData.GetHeaders();
	}

	/**
	 * After the query has been run, all of the data has been captured into a
	 * multi-dimensional string array which contains all the row's. For each row it
	 * also has all the column data. It is in string format
	 * 
	 * @return multi-dimensional array of String data based on the resultset from
	 *         the query
	 */
	public String[][] GetQueryData() {
		return jdbcData.GetData();
	}

	public String GetProjectTeamApplication() {
		return projectTeamApplication;
	}

	public boolean isActionQuery(int queryChoice) {
		QueryData e = queryArray.get(queryChoice);
		return e.IsQueryAction();
	}

	public boolean isParameterQuery(int queryChoice) {
		QueryData e = queryArray.get(queryChoice);
		return e.IsQueryParm();
	}

	public boolean ExecuteQuery(int queryChoice, String[] parms) {
		boolean bOK = true;
		QueryData e = queryArray.get(queryChoice);
		bOK = jdbcData.ExecuteQuery(e.GetQueryString(), parms, e.GetAllLikeParams());
		return bOK;
	}

	public boolean ExecuteUpdate(int queryChoice, String[] parms) {
		boolean bOK = true;
		QueryData e = queryArray.get(queryChoice);
		bOK = jdbcData.ExecuteUpdate(e.GetQueryString(), parms);
		updateAmount = jdbcData.GetUpdateCount();
		return bOK;
	}

	public boolean Connect(String szHost, String szUser, String szPass, String szDatabase) {

		boolean bConnect = jdbcData.ConnectToDatabase(szHost, szUser, szPass, szDatabase);
		if (bConnect == false)
			error = jdbcData.GetError();
		return bConnect;
	}

	public boolean Disconnect() {
		// Disconnect the JDBCData Object
		boolean bConnect = jdbcData.CloseDatabase();
		if (bConnect == false)
			error = jdbcData.GetError();
		return true;
	}

	public String GetError() {
		return error;
	}

	private QueryJDBC jdbcData;
	private String error;
	private String projectTeamApplication;
	private ArrayList<QueryData> queryArray;
	private int updateAmount;

	/**
	 * @param args the command line arguments
	 */

	// Console App will Connect to Database
	// It will run a single select query without Parameters
	// It will display the results
	// It will close the database session
	public static void main(String[] args) {
		// TODO code application logic here
		final QueryRunner queryrunner = new QueryRunner();

		if (args.length == 0) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {

					new ManageUp(queryrunner).setVisible(true);
				}
			});
		} else {
			if (args[0] == "-console") {
				// TODO
				// You should code the following functionality:

				// You need to determine if it is a parameter query. If it is, then
				// you will need to ask the user to put in the values for the Parameters in your
				// query
				// you will then call ExecuteQuery or ExecuteUpdate (depending on whether it is
				// an action query or regular query)
				// if it is a regular query, you should then get the data by calling
				// GetQueryData. You should then display this
				// output.
				// If it is an action query, you will tell how many row's were affected by it.
				//
				// This is Psuedo Code for the task:
				// Connect()
				// n = GetTotalQueries()
				// for (i=0;i < n; i++)
				// {
				// Is it a query that Has Parameters
				// Then
				// amt = find out how many parameters it has
				// Create a paramter array of strings for that amount
				// for (j=0; j< amt; j++)
				// Get The Paramater Label for Query and print it to console. Ask the user to
				// enter a value
				// Take the value you got and put it into your parameter array
				// If it is an Action Query then
				// call ExecuteUpdate to run the Query
				// call GetUpdateAmount to find out how many rows were affected, and print that
				// value
				// else
				// call ExecuteQuery
				// call GetQueryData to get the results back
				// print out all the results
				// end if
				// }
				// Disconnect()

				// NOTE - IF THERE ARE ANY ERRORS, please print the Error output
				// NOTE - The QueryRunner functions call the various JDBC Functions that are in
				// QueryJDBC. If you would rather code JDBC
				// functions directly, you can choose to do that. It will be harder, but that is
				// your option.
				// NOTE - You can look at the QueryRunner API calls that are in QueryFrame.java
				// for assistance. You should not have to
				// alter any code in QueryJDBC, QueryData, or QueryFrame to make this work.
				System.out.println("Please write the non-gui functionality");
			} else {
				System.out.println(
						"usage: you must use -console as your argument to get non-gui functionality. If you leave it out it will be GUI");
			}
		}

	}
}
