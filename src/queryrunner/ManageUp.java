/*
 * Team 5
 * CPSC 5021, Seattle University
 * This is free and unencumbered software released into the public domain.
 */

package queryrunner;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.GridLayout;


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
	
	
	private void initContentPane() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1118, 723);
		contentPane = new JPanel();
		contentPane.setBackground( paleGreenMU );
		contentPane.setOpaque(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	
	private void initHomePage() {
		panelWelcome = new JPanel();
		panelWelcome.setBackground(UIManager.getColor("Tree.textBackground"));
		panelWelcome.setBounds(319, -5, 799, 697);
		contentPane.add(panelWelcome);
		lbImageHomePage = new JLabel("");
		lbImageHomePage.setIcon(new ImageIcon(ManageUp.class.getResource("/image/homepage_7by6v2.png")));
		panelWelcome.add(lbImageHomePage);
		panelSkills = new JPanel();
		panelSkills.setBackground( paleGreenMU );
		panelSkills.setOpaque(true);
		panelSkills.setBounds(319, 0, 799, 693);
		contentPane.add(panelSkills);
		panelSkills.setLayout(null);
		
		textFieldEntry1 = new JTextField();
		textFieldEntry1.setBounds(137, 45, 191, 26);
		textFieldEntry1.setColumns(10);
    	textFieldEntry1.setVisible(false);
	}
	
	
	private void initPanels() {
		initHomePage();
		initSkillsPanel();
		initPanelLogIn();
	}

	
	private void initSkillsPanel() {	
		skillListModel = new DefaultListModel<String>();
		panelTableSkills = new JPanel();
		panelTableSkills.setBounds(41, 157, 731, 457);
		
		String[] columnHeader = {"Years of Experience", "User Id", "Employee Name", "Year Skill Acquired", "Skill Id", "Skill"};
		skillModel = new DefaultTableModel(columnHeader, 0);
		skillsTable = new JTable( skillModel );
		JTableHeader header = skillsTable.getTableHeader();
	    header.setBackground(whiteMU);
	    header.setForeground(Color.black);
		panelTableSkills.add(skillsTable);
		panelSkills.add(panelTableSkills);
		addTableFormat(skillsTable, panelTableSkills, columnHeader );
		
		panelTitleAndParams = new JPanel();
		panelTitleAndParams.setBounds(41, 62, 614, 83);
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
		
		lblSkillsQueryTitle = new JLabel("Experts: Employees with Over One Year of Experience by Skill Set");
		lblSkillsQueryTitle.setBounds(0, 0, 614, 41);
		panelTitleAndParams.add(lblSkillsQueryTitle);
		lblSkillsQueryTitle.setFont(new Font("DejaVu Sans Light", Font.BOLD, 14));
		lblEmployeeName = new JLabel("Employee Name");
		lblEmployeeName.setBounds(0, 41, 614, 41);
		panelTitleAndParams.add(lblEmployeeName);
		
		addTop5Skills();
		addExpertSkills();

	}
	
	private void addExpertSkills() {
		btnExpertSkills = new JButton("Experts");
		btnExpertSkills.setForeground(whiteMU);
		btnExpertSkills.setBackground(greenMU);
		btnExpertSkills.setOpaque(true); //Fix for macs
		btnExpertSkills.setBorderPainted(false); //Fix for macs
		btnExpertSkills.setBounds(41, 19, 114, 40);
		String title = "Experts: Employees with Over One Year of Experience by Skill Set";
		//Returns to default view
		btnExpertSkills.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent event) {
		    	executeSkillQuery(0, parameters);
		    	//on click change title
		    	lblSkillsQueryTitle.setText(title);
		    	lblEmployeeName.setVisible(true);
		    	textFieldEntry1.setVisible(true);
		    }
		});
		panelSkills.add(btnExpertSkills);
		btnExpertSkills.setEnabled(false);
	}
	
	
	private void addTop5Skills() {
		btnTop5Skills = new JButton("Top 5 Skills");
		String title = "Top 5 Skills: The Most Common Skillsets Shared Among Employees";
		btnTop5Skills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		    	executeSkillQuery(3, parameters);
		    	//on click change title
		    	lblSkillsQueryTitle.setText(title);
		    	lblEmployeeName.setVisible(false);
		    	textFieldEntry1.setVisible(false);
			}
		});
		btnTop5Skills.setForeground(whiteMU);
		btnTop5Skills.setBackground(greenMU);
		btnTop5Skills.setOpaque(true); //Fix for macs
		btnTop5Skills.setBorderPainted(false); //Fix for macs

		btnTop5Skills.setBounds(191, 19, 114, 40);
		panelSkills.add(btnTop5Skills);
		btnTop5Skills.setEnabled(false);
	}
	
	
	private void initPanelLogIn() {
		panelLogin = new JPanel();
		panelLogin.setBounds(0, 0, 321, 693);
		panelLogin.setBackground(UIManager.getColor("SplitPaneDivider.draggingColor"));
		contentPane.add(panelLogin);
		panelLogin.setLayout(null);
		panelPMReview = new JPanel();
		panelPMReview.setBackground( lightPinkMU );
		panelPMReview.setBounds(319, 0, 799, 693);
		contentPane.add(panelPMReview);
		panelPMReview.setLayout(null);
	}

	private void addTableFormat(JTable table, JPanel panel, String[] columnHeader ) {
		for(int i=0;i<columnHeader.length;i++){
			TableColumn tc = table.getColumnModel().getColumn(i);
			System.out.println( columnHeader[i] );
			tc.setHeaderValue(columnHeader[i]);
			DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
			tc.setCellRenderer(dtcr);
		}
		panel.setLayout(new BorderLayout());
		panel.add(table, BorderLayout.CENTER);
		panel.add(table.getTableHeader(), BorderLayout.NORTH);
	}
	
	private void initWelcomeLogo() {
		lblWelcome = new JLabel("");
		lblWelcome.setFont(new Font("Dialog", Font.BOLD, 14));
		lblWelcome.setForeground(UIManager.getColor("controlLtHighlight"));
		lblWelcome.setBounds(35, 112, 257, 15);
		panelLogin.add(lblWelcome);
		lbLogo = new JLabel("");
		lbLogo.setIcon(new ImageIcon(ManageUp.class.getResource("/image/logodark_small.png")));
		lbLogo.setBounds(24, 12, 280, 107);
	}

	
	private void initUserNameAndPassword() {
		userName = new JTextField();
		userName.setBounds(24, 48, 268, 35);
		panelLogin.add(userName);
		userName.setColumns(10);

		btnDBConnect = new JButton("Connect");
		btnDBConnect.setBounds(24, 164, 143, 35);
		panelLogin.add(btnDBConnect);
		btnDBConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				databaseConnectActionPerformed(evt);			}
		});
		btnDBConnect.setForeground(whiteMU);
		btnDBConnect.setBackground(blueMU);
		btnDBConnect.setOpaque(true);  //Fix for macs
		btnDBConnect.setBorderPainted(false); //Fix for macs
		
		passWord = new JPasswordField();
		passWord.setColumns(10);
		passWord.setBounds(24, 112, 268, 35);
		panelLogin.add(passWord);

		lblUserName = new JLabel("User Name");
		lblUserName.setForeground(UIManager.getColor("SplitPane.highlight"));
		lblUserName.setBounds(35, 25, 113, 15);
		panelLogin.add(lblUserName);

		lblPassword = new JLabel("Password");
		lblPassword.setForeground(UIManager.getColor("SplitPane.highlight"));
		lblPassword.setBounds(34, 87, 102, 23);
		panelLogin.add(lblPassword);
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
		System.out.println("Try to connect");
		
		if (btnDBConnect.getLabel() == "Connect") {
			bOK = queryrunner.Connect( host , user, pwd, db);
			if (bOK == true) {
				btnDBConnect.setLabel("Disconnect");
				panelWelcome.setVisible(false);
				lblPassword.setVisible(false);
				lblUserName.setVisible(false);
				userName.setVisible(false);
				passWord.setVisible(false);
				lblWelcome.setText("Welcome " + user);
				panelLogin.add(lbLogo);
				//Default data view after user logs in
				executeSkillQuery(0, parameters);
				btnTop5Skills.setEnabled(true);
				btnExpertSkills.setEnabled(true);
		    	textFieldEntry1.setVisible(true);
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
		    	textFieldEntry1.setVisible(false);		    	
			}
		}
	}// GEN-LAST:event_databaseConnectActionPerformed

	
	/**
	 * This event handler recognizes when the expert skills button has been called
	 * @param evt
	 */
	private void expertSkillsQueryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_expertSkillsQueryActionPerformed
		m_queryChoice = 1;
		String[] parameters = new String[1];
		parameters[0] = "%" + textFieldEntry1.getText() + "%";
		executeSkillQuery( m_queryChoice, parameters );
	}// GEN-LAST:event_expertSkillsQueryActionPerformed

	
	
	/**
	 * Takes in a query number and a parameter string to populate the 
	 * Skills Table with the appropriate query
	 * @param m_queryChoice
	 * @param parameters
	 */
	private void executeSkillQuery( int m_queryChoice, String[] parameters ) {
		boolean bOK = true;
		String[] headers;
		String[][] allData;
		bOK = queryrunner.ExecuteQuery(m_queryChoice, parameters);
		if (bOK == true) {
			headers = queryrunner.GetQueryHeaders();
			allData = queryrunner.GetQueryData();
			updateSkillsTableView(headers, allData);
		}
		else {
			System.out.println("Not executable.");
		}
	}
	
	
	/**
	 * Updates the Skills Table with the headers and data
	 * @param headers column header
	 * @param allData values from skills query
	 */
	private void updateSkillsTableView( String[] headers, String[][] allData ) {
		int countRows = 0;
		DefaultTableModel model = new DefaultTableModel(headers, 0);
		skillListModel.clear();
		for( int i = 0; i < allData.length; i++) {
			model.addRow(allData[i]);
			countRows++;
		}
		skillsTable.setModel(model);
        ((AbstractTableModel) model).fireTableDataChanged();
		System.out.println("Data for Skills, pulled " +  countRows + " rows");
	}
	
	
	
	/**
	 * Colors
	 */
	final private Color paleGreenMU = new java.awt.Color(192, 249, 233);
	final private Color greenMU = new Color(32, 178, 170);
	final private Color lightPinkMU = new java.awt.Color(220, 186, 196);
	final private Color whiteMU = new Color(255, 255, 255);
	final private Color blueMU = new Color(103, 128, 194);

	
	/*
	 * Content pane and panels
	 */
	private JPanel contentPane;
	private JPanel panelLogin;
	private JPanel panelSkills;
	private JPanel panelWelcome;
	private JPanel panelPMReview;
	private JPanel panelTableSkills;
	private JPanel panelTitleAndParams;
	
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
	//private Button btnDBConnect;
	private JButton btnDBConnect; 
	private JButton btnExpertSkills;
	private JButton btnTop5Skills;
	private DefaultListModel<String> skillListModel;
	private JTable skillsTable;
	private DefaultTableModel skillModel;
	
	/*
	 * Database Queries
	 */
	private QueryRunner queryrunner;
	private String[] parameters = {};
	
	private int m_queryChoice = 0; // This is the default query that was selected
	private JTextField textFieldEntry1;
}
