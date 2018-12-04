/*
 * Team 5
 * CPSC 5021, Seattle University
 * This is free and unencumbered software released into the public domain.
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
	   // queryArray[0]
		queryArray.add(new QueryData("SELECT * FROM Employees_OverOneYearofExprience_bySkill"
				, null, null, false, false)); 
	   // queryArray[1]
		queryArray.add(new QueryData("SELECT * FROM Employees_OverOneYearofExprience_bySkill "
				+ "WHERE employee LIKE ?", new String[] { "Employee Name" }, new boolean[] { true }, 
				false, true)); 
	   // queryArray[2]
		queryArray.add(new QueryData("SELECT * FROM Employees_OverOneYearofExprience_bySkill WHERE "
				+ "skill_name LIKE ?", new String[] { "Skill Name" },
				new boolean[] { true }, false, true));		
	}
	
	
	/**
	 * Top 5 Skills Query
	 * Shows the top five skills owned by employees in descending order
	 * based on the number of employees related to each skill.
	 */
	private void top5SkillsQuery() {
	   // queryArray[3]
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
	   // queryArray[4]
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
	
	/**
	 * Incomplete tasks query.
	 */
	private void IncompleteTasksQuery() {
	   // queryArray[5]
      queryArray.add(new QueryData("SELECT task_name as 'Task',   "
      		+ "  days_before_project_end as 'Days before Project Ends',"
      		+ "	 project_end_date as 'Project Ends',"
      		+ "  blockers_flagged as 'Blockers Flagged',"
      		+ "  deliverable_name as 'Deliverable', "
      		+ "  project_name as 'Project', project_manager as 'Manager'"
      		+ "  FROM IncompleteTasks_ProjectsEndingThisMonth"
      , null, null, false, false));
   }
   
	
   /**
    * Employee tasks query.
    */
   private void EmployeeTasksQuery() {
      // queryArray[6]
      queryArray.add(new QueryData("SELECT "
      		+ "task_name as 'Task', end_time as 'Finish Date', "
      		+ "expected_time_to_complete as 'Excpected Completion Time', "
      		+ "real_time_to_complete 'Real Completion Time', "
      		+ "blockers_flagged as 'Blockers', "
      		+ "difficulty_rate_id as 'Difficulty Rating',"
      		+ "employee as 'Employee'"
      		+ " FROM Employee_TasksOrderByEndDate ", null, null, false, false));
      // queryArray[7]
      queryArray.add(new QueryData("SELECT "
    			+ "task_name as 'Task', end_time as 'Finish Date', "
          		+ "expected_time_to_complete as 'Excpected Completion Time', "
          		+ "real_time_to_complete 'Real Completion Time', "
          		+ "blockers_flagged as 'Blockers', "
          		+ "difficulty_rate_id as 'Difficulty Rating',"
          		+ "employee as 'Employee'"
      		+ " FROM Employee_TasksOrderByEndDate "
      + "WHERE employee LIKE ?", new String[] { "First Name" }, new boolean[] { true }, false, true));
   }
   
   /**
    * Insert task query.
    */
   private void InsertTaskQuery() {
      // queryArray[8]
      queryArray.add(new QueryData(
      "INSERT INTO Tasks (task_name, deliverable_id, task_owner_user_id) VALUES (?,?,?)"
      , new String [] {"Task Name", "Deliverable ID", "User ID"}, new boolean [] {false, false, false}, true, true));
   }
	
	/**
	 * Instantiates a new query runner.
	 */
	public QueryRunner() {
		this.jdbcData = new QueryJDBC();
		updateAmount = 0;
		queryArray = new ArrayList<>();
		error = "";
		expertsQuery();
		top5SkillsQuery();
		recentSkillUpdatesebyPMQuery();
		IncompleteTasksQuery();
        EmployeeTasksQuery();
        InsertTaskQuery();
	}


	/**
	 * Gets the parameter amount for a query.
	 *
	 * @param queryChoice the query choice
	 * @return the index of the query in the array
	 */
	public int GetParameterAmtForQuery(int queryChoice) {
		QueryData e = queryArray.get(queryChoice);
		return e.GetParmAmount();
	}

	/**
	 * Gets the parameter text.
	 *
	 * @param queryChoice the query choice
	 * @param parmnum the parmnum
	 * @return the string
	 */
	public String GetParamText(int queryChoice, int parmnum) {
		QueryData e = queryArray.get(queryChoice);
		return e.GetParamText(parmnum);
	}

	/**
	 * Function will return how many rows were updated 
	 * as a result of the update query.
	 *
	 * @return Returns how many rows were updated
	 */
	public int GetUpdateAmount() {
		return updateAmount;
	}

	/**
	 * Function will return ALL of the Column Headers from the query.
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

	/**
	 * Checks if is parameter query.
	 *
	 * @param queryChoice the query choice
	 * @return true, if is parameter query
	 */
	public boolean isParameterQuery(int queryChoice) {
		QueryData e = queryArray.get(queryChoice);
		return e.IsQueryParm();
	}

	/**
	 * Execute query.
	 *
	 * @param queryChoice the query choice
	 * @param parms the parms
	 * @return true, if successful
	 */
	public boolean ExecuteQuery(int queryChoice, String[] parms) {
		boolean bOK = true;
		QueryData e = queryArray.get(queryChoice);
		bOK = jdbcData.ExecuteQuery(e.GetQueryString(), parms, e.GetAllLikeParams());
		return bOK;
	}

	/**
	 * Execute update.
	 *
	 * @param queryChoice the query choice
	 * @param parms the parms
	 * @return true, if successful
	 */
	public boolean ExecuteUpdate(int queryChoice, String[] parms) {
		boolean bOK = true;
		QueryData e = queryArray.get(queryChoice);
		bOK = jdbcData.ExecuteUpdate(e.GetQueryString(), parms);
		updateAmount = jdbcData.GetUpdateCount();
		return bOK;
	}

	/**
	 * Connect.
	 *
	 * @param szHost the sz host
	 * @param szUser the sz user
	 * @param szPass the sz pass
	 * @param szDatabase the sz database
	 * @return true, if successful
	 */
	public boolean Connect(String szHost, String szUser, String szPass, String szDatabase) {
		boolean bConnect = jdbcData.ConnectToDatabase(szHost, szUser, szPass, szDatabase);
		if (bConnect == false)
			error = jdbcData.GetError();
		return bConnect;
	}

	/**
	 * Disconnect.
	 *
	 * @return true, if successful
	 */
	public boolean Disconnect() {
		boolean bConnect = jdbcData.CloseDatabase();
		if (bConnect == false)
			error = jdbcData.GetError();
		return true;
	}

	/**
	 * Gets the error.
	 *
	 * @return the string
	 */
	public String GetError() {
		return error;
	}

	/** The jdbc data. */
	private QueryJDBC jdbcData;
	
	/** The error. */
	private String error;
	
	/** The query array. */
	private ArrayList<QueryData> queryArray;
	
	/** The update amount. */
	private int updateAmount;

	/**
	 * The main method.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		final QueryRunner queryrunner = new QueryRunner();
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ManageUp(queryrunner).setVisible(true);
			}
		});
	}
}
