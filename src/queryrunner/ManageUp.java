/*
 * Team 5

 * CPSC 5021, Seattle University
 * This is free and unencumbered software released into the public domain.
 */
package queryrunner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import jiconfont.icons.FontAwesome;
import jiconfont.swing.IconFontSwing;

public class ManageUp extends JFrame {

	/**
	 * This is the constructor for QueryFrame. It will initialize the Combobox with
	 * the various queries that are part of the QueryData that has been passed to
	 * it. It will also set the default state of the Command Buttons and combo
	 * boxes.
	 * 
	 * @param queryrunnerObj
	 */
	public ManageUp(QueryRunner queryrunnerObj) {
		initContentPane();
		initPanels();
		initUserNameAndPassword();
		initWelcomeLogo();
		queryrunner = queryrunnerObj;
	}

    /**
	 * This is the constructor for the ContentPane. It will initialize the content pane and background colors.
	 * 
	 */
	private void initContentPane() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1118, 723);
		contentPane = new JPanel();
		contentPane.setBackground(paleGreenMU);
		contentPane.setOpaque(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}

	 /**
	 * This is the constructor for the Home Page of the application.
	 * 
	 */
	private void initHomePage() {
		panelWelcome = new JPanel();
		panelWelcome.setBackground(UIManager.getColor("Tree.textBackground"));
		panelWelcome.setBounds(319, -5, 799, 697);
		contentPane.add(panelWelcome);
		lbImageHomePage = new JLabel("");
		lbImageHomePage.setIcon(new ImageIcon(ManageUp.class.getResource("/image/homepage_7by6v2.png")));
		panelWelcome.add(lbImageHomePage);
		panelLogin = new JPanel();
		panelLogin.setBounds(0, 0, 321, 693);
		panelLogin.setBackground(greyMU);
		contentPane.add(panelLogin);
		panelLogin.setLayout(null);
		lblWelcome = new JLabel("");
		lblWelcome.setFont(new Font("Dialog", Font.BOLD, 14));
		lblWelcome.setForeground(UIManager.getColor("controlLtHighlight"));
		lblWelcome.setBounds(35, 112, 257, 15);
		panelLogin.add(lblWelcome);
		userName = new JTextField();
		userName.setBounds(24, 48, 268, 35);
		panelLogin.add(userName);
		userName.setColumns(10);

		// User name and Password
		lblUserName = new JLabel("User Name");
		lblUserName.setForeground(UIManager.getColor("SplitPane.highlight"));
		lblUserName.setBounds(35, 25, 113, 15);
		panelLogin.add(lblUserName);
		lblPassword = new JLabel("Password");
		lblPassword.setForeground(UIManager.getColor("SplitPane.highlight"));
		lblPassword.setBounds(34, 87, 102, 23);
		panelLogin.add(lblPassword);

		// Project Status Report Button
		btnCheckProjects = new JButton("Project Status");
		btnCheckSkills = new JButton("Skill Assessment");
		btnAbout = new JButton("About");
		btnNavigation = new JButton[] { btnCheckProjects, btnCheckSkills, btnAbout };

		Icon iconProject = IconFontSwing.buildIcon(FontAwesome.CALENDAR_CHECK_O, 15, Color.WHITE);
		btnCheckProjects.setIcon(iconProject);
		btnCheckProjects.setVisible(false);
		btnCheckProjects.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		btnCheckProjects.setBackground(greyMU);
		btnCheckProjects.setForeground(whiteMU);
		btnCheckProjects.setOpaque(true);
		btnCheckProjects.setBorderPainted(true);
		btnCheckProjects.setBounds(56, 384, 200, 50);
		panelLogin.add(btnCheckProjects);
		btnCheckProjects.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				showPanelPM(evt);
				changeBackgroundColorClick(btnCheckProjects);
			}
		});

		// Skill Assessment Report Button
		Icon iconWrench = IconFontSwing.buildIcon(FontAwesome.WRENCH, 15, Color.WHITE);
		btnCheckSkills.setIcon(iconWrench);
		btnCheckSkills.setVisible(false);
		btnCheckSkills.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		btnCheckSkills.setBackground(greyMU);
		btnCheckSkills.setForeground(whiteMU);
		btnCheckSkills.setOpaque(true);
		btnCheckSkills.setBorderPainted(true);
		btnCheckSkills.setBounds(56, 322, 200, 50);
		panelLogin.add(btnCheckSkills);
		btnCheckSkills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				showPanelSkills(evt);
				changeBackgroundColorClick(btnCheckSkills);
			}
		});

		// About Button
		Icon iconAbout = IconFontSwing.buildIcon(FontAwesome.WRENCH, 15, Color.WHITE);
		btnAbout.setIcon(iconAbout);
		btnAbout.setVisible(false);
		btnAbout.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		btnAbout.setBackground(greyMU);
		btnAbout.setForeground(whiteMU);
		btnAbout.setOpaque(true);
		btnAbout.setBorderPainted(true);
		btnAbout.setBounds(56, 446, 200, 50);
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// TODO : show about panel
				changeBackgroundColorClick(btnAbout);
			}
		});
		panelLogin.add(btnAbout);

		// Objects created for the skill panel
		panelSkills = new JPanel();
		
		
		// Removed and moved
		textFieldEntry1 = new JTextField();
		textFieldEntry1.setBounds(137, 45, 191, 26);
		textFieldEntry1.setColumns(10);
		textFieldEntry1.setVisible(false);

		// Objects created for the PM panel
		panelPMReview = new JPanel();

		txtEnterName = new JTextField();
		txtEnterName.setForeground(Color.GRAY);
		txtEnterName.setText("Enter Employee Name");
		txtEnterName.setBounds(557, 5, 187, 35);
		txtEnterName.setColumns(10);
		txtEnterName.setVisible(false);

		txtEnterTaskName = new JTextField();
		txtEnterTaskName.setForeground(Color.GRAY);
		txtEnterTaskName.setText("Enter TaskName");
		txtEnterTaskName.setBounds(206, 74, 164, 35);
		txtEnterTaskName.setColumns(10);

		txtEnterDeliver = new JTextField();
		txtEnterDeliver.setForeground(Color.GRAY);
		txtEnterDeliver.setText("Enter DeliverableID");
		txtEnterDeliver.setBounds(382, 74, 164, 35);
		txtEnterDeliver.setColumns(10);

		txtEnterEmployeeid = new JTextField();
		txtEnterEmployeeid.setForeground(Color.GRAY);
		txtEnterEmployeeid.setText("Enter EmployeeID");
		txtEnterEmployeeid.setBounds(558, 74, 158, 35);
		txtEnterEmployeeid.setColumns(10);

	}

	/**
	 * This changes the color of a button when it is clicked to show 
	 * that it is clicked.
	 * 
	 */
	private void changeBackgroundColorClick(JButton btn) {
		Color currentColor = btn.getBackground();
		if (currentColor == greyMU) {
			for (int i = 0; i < btnNavigation.length; i++) {
				btnNavigation[i].setBackground(greyMU);
			}
			btn.setBackground(blueMU);
		} else {
			btn.setBackground(greyMU);
		}
	}

   /**
	 * This is the constructor that creates the three different panels that 
	 * will be accessed during the application.
	 * 
	 */
	private void initPanels() {
		IconFontSwing.register(FontAwesome.getIconFont());
		initHomePage();
		initSkillsPanel();
		initPMPanel();
	}

	/**
	 * This is the constructor for the Skills Panel.
	 * 
	 */
	private void initSkillsPanel() {
		JLabel lblSkillsAssesment = new JLabel("Skills Assesment Report");
		lblSkillsAssesment.setForeground(UIManager.getColor("ComboBox.foreground"));
		lblSkillsAssesment.setFont(new Font("NanumSquareRound Bold", Font.BOLD, 22));
		lblSkillsAssesment.setBounds(42, 12, 422, 37);
		panelSkills.add(lblSkillsAssesment);
		
		skillListModel = new DefaultListModel<String>();
		panelTableSkills = new JPanel();
		panelTableSkills.setBounds(35, 224, 731, 457);

		String[] columnHeader = { "Years of Experience", "User Id", "Employee Name",
				"Year Skill Acquired", "Skill Id",
				"Skill" };
		model = new DefaultTableModel(columnHeader, 0);
		skillsTable = new JTable(model);
		JTableHeader header = skillsTable.getTableHeader();
		header.setBackground(whiteMU);
		header.setForeground(Color.black);
		panelTableSkills.add(skillsTable);
		panelSkills.add(panelTableSkills);
		addTableFormat(skillsTable, panelTableSkills, columnHeader);

		panelTitleAndParams = new JPanel();
		panelTitleAndParams.setBounds(35, 113, 614, 83);
		panelTitleAndParams.setBackground(paleGreenMU);

		panelTitleAndParams.setOpaque(true);
		panelSkills.add(panelTitleAndParams);
		panelTitleAndParams.setLayout(null);

		panelTitleAndParams.add(textFieldEntry1);
		textFieldEntry1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				expertSkillsQueryActionPerformed(event);
				System.out.println("The entered text is: " + textFieldEntry1.getText());
			}
		});

		lblSkillsQueryTitle = new JLabel("Experts: Employees with Over One "
				+ "Year of Experience by Skill Set");
		lblSkillsQueryTitle.setBounds(0, 0, 614, 41);
		panelTitleAndParams.add(lblSkillsQueryTitle);
		lblSkillsQueryTitle.setFont(new Font("DejaVu Sans Light", Font.BOLD, 14));
		lblEmployeeName = new JLabel("Employee Name");
		lblEmployeeName.setBounds(0, 41, 614, 41);
		panelTitleAndParams.add(lblEmployeeName);

		addTop5Skills();
		addExpertSkills();

	}

    /**
	 * This function adds the expert skills query and button to the application.
	 * 
	 */
	private void addExpertSkills() {
		btnExpertSkills = new JButton("Experts");
		btnExpertSkills.setForeground(whiteMU);
		btnExpertSkills.setBackground(greenMU);
		btnExpertSkills.setOpaque(true); // Fix for macs
		btnExpertSkills.setBorderPainted(false); // Fix for macs
		btnExpertSkills.setBounds(36, 61, 114, 40);
		String title = "Experts: Employees with Over One Year of Experience by Skill Set";
		// Returns to default view
		btnExpertSkills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				executeSkillQuery(0, parameters);
				// on click change title
				lblSkillsQueryTitle.setText(title);
				lblEmployeeName.setVisible(true);
				textFieldEntry1.setVisible(true);
			}
		});
		panelSkills.add(btnExpertSkills);
		btnExpertSkills.setEnabled(false);
	}

	/**
	 * This function adds the top 5 skills query and button to the application.
	 * 
	 */
	private void addTop5Skills() {
		btnTop5Skills = new JButton("Top 5 Skills");
		String title = "Top 5 Skills: The Most Common Skillsets Shared Among Employees";
		btnTop5Skills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeSkillQuery(3, parameters);
				// on click change title
				lblSkillsQueryTitle.setText(title);
				lblEmployeeName.setVisible(false);
				textFieldEntry1.setVisible(false);
			}
		});
		btnTop5Skills.setForeground(whiteMU);
		btnTop5Skills.setBackground(greenMU);
		btnTop5Skills.setOpaque(true); // Fix for macs
		btnTop5Skills.setBorderPainted(false); // Fix for macs
		btnTop5Skills.setBounds(186, 61, 114, 40);
		panelSkills.add(btnTop5Skills);
		btnTop5Skills.setEnabled(false);
	}

    /**
	 * This creates the table format for the data that will be pulled from 
	 * the database.
	 * 
	 * @param table the table in which the data will be transferred to
	 * @param panel the panel to which the data will be transferred
	 * @param columnHeader the array of column titles from the database
	 */ 
	private void addTableFormat(JTable table, JPanel panel, String[] columnHeader) {
		for (int i = 0; i < columnHeader.length; i++) {
			TableColumn tc = table.getColumnModel().getColumn(i);
			System.out.println(columnHeader[i]);
			tc.setHeaderValue(columnHeader[i]);
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			tc.setCellRenderer(dtcr);
		}
		panel.setLayout(new BorderLayout());
		panel.add(table, BorderLayout.CENTER);
		panel.add(table.getTableHeader(), BorderLayout.NORTH);
	}

	/**
	 * This event handler recognizes when the expert skills button has been called
	 * 
	 * @param evt
	 */
	private void expertSkillsQueryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_expertSkillsQueryActionPerformed
		m_queryChoice = 1;
		String[] parameters = new String[1];
		parameters[0] = "%" + textFieldEntry1.getText() + "%";
		executeSkillQuery(m_queryChoice, parameters);
	}// GEN-LAST:event_expertSkillsQueryActionPerformed

	/**
	 * Takes in a query number and a parameter string to populate the Skills Table
	 * with the appropriate query
	 * 
	 * @param m_queryChoice
	 * @param parameters
	 */
	private void executeSkillQuery(int m_queryChoice, String[] parameters) {
		boolean bOK = true;
		String[] headers;
		String[][] allData;
		bOK = queryrunner.ExecuteQuery(m_queryChoice, parameters);
		if (bOK == true) {
			headers = queryrunner.GetQueryHeaders();
			allData = queryrunner.GetQueryData();
			updateSkillsTableView(headers, allData);
		} else {
			System.out.println("Not executable.");
		}
	}

	/**
	 * Updates the Skills Table with the headers and data
	 * 
	 * @param headers column header
	 * @param allData values from skills query
	 */
	private void updateSkillsTableView(String[] headers, String[][] allData) {
		int countRows = 0;
		DefaultTableModel model = new DefaultTableModel(headers, 0);
		skillListModel.clear();
		for (int i = 0; i < allData.length; i++) {
			model.addRow(allData[i]);
			countRows++;
		}
		skillsTable.setModel(model);
		((AbstractTableModel) model).fireTableDataChanged();
		System.out.println("Data for Skills, pulled " + countRows + " rows");
	}

	 /**
	 * This function initiates the projects panel.
	 * 
	 */
	private void initPMPanel() {

		taskListModel = new DefaultListModel<String>();

		String[] columnHeader = { "TaskID", "TaskName", "DeliverableName", 
				"DaysBeforeEnd", "Blocker", "ProjectName" };
		model = new DefaultTableModel(columnHeader, 0);
		taskTable = new JTable(model);
		JTableHeader header = taskTable.getTableHeader();
		header.setBackground(whiteMU);
		header.setForeground(Color.black);

		panelTableTasks = new JPanel();
		addTableFormat(taskTable, panelTableTasks, columnHeader);
		panelPMReview.add(panelTableTasks);
		panelTableTasks.setBounds(22, 204, 760, 449);
		panelTableTasks.setLayout(null);

		JLabel lblProjectStatus = new JLabel("Project Status Report");
		lblProjectStatus.setForeground(UIManager.getColor("ComboBox.foreground"));
		lblProjectStatus.setFont(new Font("NanumSquareRound Bold", Font.BOLD, 22));
		lblProjectStatus.setBounds(29, 12, 422, 37);
		panelPMReview.add(lblProjectStatus);
		
		scrollPane = new JScrollPane();
		scrollPane.setMaximumSize(new Dimension(50000, 50000));
		scrollPane.setBounds(0, 0, 760, 450);
		panelTableTasks.add(scrollPane);
		scrollPane.setViewportView(taskTable);

		panelTasksParams = new JPanel();
		panelTasksParams.setBounds(22, 61, 750, 106);
		panelTasksParams.setBackground(paleGreenMU);
		panelPMReview.add(panelTasksParams);
		panelTasksParams.setLayout(null);

		panelTasksParams.add(txtEnterName);
		txtEnterName.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				txtEnterName.setText("Enter Employee Name");
			}
		});

		// JTextField - Enter employee name
		txtEnterName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				employeeTaskQueryPerformed(event);
				System.out.println("The entered text is: " + txtEnterName.getText());
			}
		});

		// JTextField - Enter Task name
		panelTasksParams.add(txtEnterTaskName);
		txtEnterTaskName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("The entered text is: " + txtEnterTaskName.getText());
			}
		});

		// JTextField - Enter Deliverable ID
		panelTasksParams.add(txtEnterDeliver);
		txtEnterDeliver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.out.println("The entered text is: " + txtEnterDeliver.getText());
			}
		});

		// JTextField - Enter Employee ID (Perform Insert by Enter key)
		panelTasksParams.add(txtEnterEmployeeid);
		txtEnterEmployeeid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				insertTaskPerformed(event);
				String[] head = { "Tip for Check Your Insert: " };
				DefaultTableModel model = new DefaultTableModel(head, 0);
				taskListModel.clear();
				head[0] = "Type in task owner name in EmployeeName " 
						+ "textfield and press Enter⏎. "
						+ "You will see the newly-inserted task at the end.";
				model.addRow(head);
				taskTable.setModel(model);
				System.out.println("The entered text is: " + txtEnterEmployeeid.getText());
			}
		});

		addIncompleteTasks();
		addTasks();
		insertNewTask();
		txtEnterTaskName.setVisible(false);
		txtEnterDeliver.setVisible(false);
		txtEnterEmployeeid.setVisible(false);
	}

	/**
	 * This function adds the incomplete tasks query and button to the application.
	 * 
	 */
	private void addIncompleteTasks() {
		btnIncompletetasks = new JButton("Incomplete Tasks This Month");
		btnIncompletetasks.setBounds(0, 6, 257, 35);
		btnIncompletetasks.setForeground(whiteMU);
		btnIncompletetasks.setBackground(greenMU);
		btnIncompletetasks.setOpaque(true); // Fix for macs
		btnIncompletetasks.setBorderPainted(false); // Fix for macs
		panelTasksParams.add(btnIncompletetasks);

		btnIncompletetasks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				executeTasksQuery(5, parameters);
				txtEnterName.setVisible(false);
				txtEnterTaskName.setVisible(false);
				txtEnterDeliver.setVisible(false);
				txtEnterEmployeeid.setVisible(false);
			}
		});
		btnIncompletetasks.setEnabled(false);
	}

	/**
	 * This function adds the "All Tasks" query and button to the application.
	 * 
	 */
	private void addTasks() {

		btnTasksOrderBy = new JButton("All Tasks");
		btnTasksOrderBy.setBounds(374, 6, 178, 35);
		btnTasksOrderBy.setForeground(whiteMU);
		btnTasksOrderBy.setBackground(greenMU);
		btnTasksOrderBy.setOpaque(true); // Fix for macs
		btnTasksOrderBy.setBorderPainted(false); // Fix for macs
		panelTasksParams.add(btnTasksOrderBy);

		btnTasksOrderBy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				executeTasksQuery(6, parameters);
				txtEnterName.setVisible(true);
				txtEnterTaskName.setVisible(false);
				txtEnterDeliver.setVisible(false);
				txtEnterEmployeeid.setVisible(false);
			}
		});
		btnTasksOrderBy.setEnabled(false);
	}

	/**
	 * This function shows the tasks linked to an employee's name.
	 * 
	 * @param evt the Java AWT action event of entering an employee's name.
	 */
	private void employeeTaskQueryPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_expertSkillsQueryActionPerformed
		m_queryChoice = 7;
		String[] parameters = new String[1];
		parameters[0] = txtEnterName.getText();
		executeTasksQuery(m_queryChoice, parameters);
	}

	/**
	 * This function allows a new task to be inserted into the database with three parameters.
	 * 
	 */
	private void insertNewTask() {

		btnInserttask = new JButton("Insert New Task");
		btnInserttask.setBounds(6, 74, 170, 35);
		btnInserttask.setForeground(whiteMU);
		btnInserttask.setBackground(greenMU);
		btnInserttask.setOpaque(true); // Fix for macs
		btnInserttask.setBorderPainted(false); // Fix for macs
		
		btnInserttask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtEnterName.setVisible(true);
				txtEnterTaskName.setVisible(true);
				txtEnterDeliver.setVisible(true);
				txtEnterEmployeeid.setVisible(true);

				String[] head = { "Tip for Insert New Task: " };
				DefaultTableModel model = new DefaultTableModel(head, 0);
				taskListModel.clear();
				head[0] = "Please type in TaskName, DeliverableID and EmployeeID. " + "Press Enter⏎ to finish insert.";
				model.addRow(head);
				taskTable.setModel(model);
			}
		});
		panelTasksParams.add(btnInserttask);
		btnInserttask.setEnabled(false);
	}


	/**
	 * This function allows a new task to be inserted into the database with three parameters.
	 * 
	 * @param evt The Java AWT event of clicking the button to enter the data.
	 */
	private void insertTaskPerformed(java.awt.event.ActionEvent evt) {
		m_queryChoice = 8;
		String[] param = new String[3];
		param[0] = txtEnterTaskName.getText();
		param[1] = txtEnterDeliver.getText();
		param[2] = txtEnterEmployeeid.getText();
		executeTasksUpdate(m_queryChoice, param);
	}

	/**
	 * Takes in a query number and a parameter string to populate the Tasks Table
	 * with the appropriate query
	 * 
	 * @param m_queryChoice
	 * @param parameters
	 */
	private void executeTasksQuery(int m_queryChoice, String[] parameters) {
		boolean bOK = true;
		String[] headers;
		String[][] allData;
		bOK = queryrunner.ExecuteQuery(m_queryChoice, parameters);
		if (bOK == true) {
			headers = queryrunner.GetQueryHeaders();
			allData = queryrunner.GetQueryData();
			// Show output in the taskTable
			updateTasksTableView(headers, allData);
		} else {
			System.out.println("Not executable.");
		}
	}

	/**
	 * Takes in a query number and parameter strings to execute Insert action query.
	 * 
	 * @param m_queryChoice
	 * @param parameters
	 */
	private void executeTasksUpdate(int m_queryChoice, String[] parameters) {
		boolean bOK = true;
		bOK = queryrunner.ExecuteUpdate(m_queryChoice, parameters);
		if (bOK == true) {
			int updateAmount = queryrunner.GetUpdateAmount();
			System.out.println("Number of rows updated: " + updateAmount);
		} else {
			System.out.println("Not executable.");
		}
	}

	/**
	 * Updates the Tasks Table with the headers and data
	 * 
	 * @param headers column header
	 * @param allData values from tasks query
	 */
	private void updateTasksTableView(String[] headers, String[][] allData) {
		int countRows = 0;
		DefaultTableModel model = new DefaultTableModel(headers, 0);
		taskListModel.clear();
		for (int i = 0; i < allData.length; i++) {
			model.addRow(allData[i]);
			countRows++;
		}
		taskTable.setModel(model);
		((AbstractTableModel) model).fireTableDataChanged();
		System.out.println("Data for Tasks, pulled " + countRows + " rows");
	}

	/**
	 * Creates the Welcome Logo for the Home page.
	 * 
	 */
	private void initWelcomeLogo() {
		lbLogo = new JLabel("");
		lbLogo.setIcon(new ImageIcon(ManageUp.class.getResource("/image/logodark_small.png")));
		lbLogo.setBounds(24, 12, 280, 107);
	}


	/**
	 * Creates the setup for the user to enter their user name and password.
	 * 
	 */
	private void initUserNameAndPassword() {
		btnDBConnect = new JButton("Connect");
		btnDBConnect.setBounds(24, 164, 143, 35);
		panelLogin.add(btnDBConnect);
		btnDBConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				databaseConnectActionPerformed(evt);
			}
		});
		btnDBConnect.setForeground(whiteMU);
		btnDBConnect.setBackground(blueMU);
		btnDBConnect.setOpaque(true); // Fix for macs
		btnDBConnect.setBorderPainted(false); // Fix for macs

		passWord = new JPasswordField();
		passWord.setColumns(10);
		passWord.setBounds(24, 112, 268, 35);
		panelLogin.add(passWord);
	}

	/**
	 * Shows the project panel once the user clicks on that panel
	 *
	 * @param evt - the act of the user clicking on the button.
	 */
	private void showPanelPM(java.awt.event.ActionEvent evt) {
		/// Switch panel
		getContentPane().remove(panelSkills);
		getContentPane().add(panelPMReview);
		repaint();

		// Set background
		panelPMReview.setBackground(paleGreenMU);
		panelPMReview.setBounds(319, 0, 799, 693);
		contentPane.add(panelPMReview);
		panelPMReview.setLayout(null);

		btnIncompletetasks.setEnabled(true);
		btnInserttask.setEnabled(true);
		btnTasksOrderBy.setEnabled(true);
		txtEnterTaskName.setVisible(false);
		txtEnterDeliver.setVisible(false);
		txtEnterEmployeeid.setVisible(false);
	}

	/**
	 * Shows the skills panel once the user clicks on that panel
	 *
	 * @param evt - the act of the user clicking on the button.
	 */
	private void showPanelSkills(java.awt.event.ActionEvent evt) {

		// Switch panel
		getContentPane().remove(panelPMReview);
		getContentPane().add(panelSkills);
		repaint();

		// Set background
		panelSkills.setBackground(paleGreenMU);
		panelSkills.setBounds(319, 0, 799, 693);
		contentPane.add(panelSkills);
		panelSkills.setLayout(null);

		// Set buttons and entry
		executeSkillQuery(0, parameters);
		btnTop5Skills.setEnabled(true);
		btnExpertSkills.setEnabled(true);
		textFieldEntry1.setVisible(true);
	}

	/**
	 * This button will use the data from the textboxes, and attempt to connect to
	 * the MYSQL Server. If it is not connected, it will call the CONNECT function,
	 * otherwise it will call the DISCONNECT Function.
	 * 
	 * @param evt
	 */
	private void databaseConnectActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_databaseConnectActionPerformed
		boolean bOK = true;
		String host = "cssql.seattleu.edu";
		String pwd = passWord.getText();
		String db = "mm_sttest5b";
		user = userName.getText();
		if (btnDBConnect.getLabel() == "Connect") {
			bOK = queryrunner.Connect(host, user, pwd, db);
			if (bOK == true) {
				btnDBConnect.setLabel("Disconnect");
				panelWelcome.setVisible(false);
				lblPassword.setVisible(false);
				lblUserName.setVisible(false);
				userName.setVisible(false);
				passWord.setVisible(false);
				lblWelcome.setText("Welcome " + user);
				panelLogin.add(lbLogo);
				btnCheckProjects.setVisible(true);
				btnCheckSkills.setVisible(true);
				btnAbout.setVisible(true);

				btnTop5Skills.setEnabled(true);
				btnExpertSkills.setEnabled(true);
				btnIncompletetasks.setEnabled(true);
				btnInserttask.setEnabled(true);
				btnTasksOrderBy.setEnabled(true);
				// Default data view after user logs in

			}
		} else {
			bOK = queryrunner.Disconnect();
			if (bOK == true) {
				btnDBConnect.setLabel("Connect");
				lblWelcome.setVisible(false);
				panelLogin.remove(lbLogo);
				panelWelcome.setVisible(true);
				lblPassword.setVisible(true);
				lblUserName.setVisible(true);
				userName.setVisible(true);
				userName.setText("");
				passWord.setVisible(true);
				passWord.setText("");

				btnTop5Skills.setEnabled(false);
				btnExpertSkills.setEnabled(false);
				btnIncompletetasks.setEnabled(false);
				btnInserttask.setEnabled(false);
				btnTasksOrderBy.setEnabled(false);

				textFieldEntry1.setVisible(false);
				txtEnterName.setVisible(false);
				txtEnterTaskName.setVisible(false);
				txtEnterDeliver.setVisible(false);
				txtEnterEmployeeid.setVisible(false);
				btnCheckProjects.setVisible(false);
				btnCheckSkills.setVisible(false);
				btnAbout.setVisible(false);
			}
		}
	}// GEN-LAST:event_databaseConnectActionPerformed

	/**
	 * Colors
	 */
	final private Color paleGreenMU = new Color(192, 249, 233);
	final private Color greenMU = new Color(32, 178, 170);
	final private Color lightPinkMU = new Color(220, 186, 196);
	final private Color whiteMU = new Color(255, 255, 255);
	final private Color blueMU = new Color(103, 128, 194);
	final private Color greyMU = new Color(64, 64, 64);

	/*
	 * Content pane and panels
	 */
	private JPanel contentPane;
	private JPanel panelLogin;
	private JPanel panelSkills;
	private JPanel panelPMReview;
	private JPanel panelWelcome;

	private JPanel panelTableSkills;
	private JPanel panelTitleAndParams;

	private JPanel panelTableTasks;
	private JPanel panelTasksParams;
	private JScrollPane scrollPane;

	/*
	 * User name and password
	 */
	private JTextField userName;
	private JPasswordField passWord;
	private String user;

	/*
	 * Labels and icons
	 */
	private JLabel lblPassword;
	private JLabel lblUserName;
	private JLabel lbImageHomePage;
	private JLabel lblWelcome;
	private JLabel lbLogo;
	private JLabel lblEmployeeName;
	private JLabel lblSkillsQueryTitle;

	/*
	 * Buttons
	 */
	// private Button btnDBConnect;
	private JButton btnDBConnect;
	private JButton btnExpertSkills;
	private JButton btnTop5Skills;
	private DefaultListModel<String> skillListModel;
	private JTable skillsTable;
	private DefaultTableModel model;
	private JTable taskTable;
	private JButton btnIncompletetasks;
	private JButton btnInserttask;
	private JButton btnTasksOrderBy;
	
	// For PM panel
	private DefaultListModel<String> taskListModel;

	// Login panel button options
	private JButton btnCheckProjects;
	private JButton btnCheckSkills;
	private JButton btnAbout;
	private JButton[] btnNavigation;

	/*
	 * Database Queries
	 */
	private QueryRunner queryrunner;
	private String[] parameters = {};

	private int m_queryChoice = 0; // This is the default query that was selected
	private JTextField textFieldEntry1;
	private JTextField txtEnterName;
	private JTextField txtEnterTaskName;
	private JTextField txtEnterDeliver;
	private JTextField txtEnterEmployeeid;
}
