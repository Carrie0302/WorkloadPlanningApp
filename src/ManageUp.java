import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Button;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.TextField;
import javax.swing.JLabel;
import java.awt.SystemColor;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;


public class ManageUp extends JFrame {

	public Connection m_conn = null;
	private static final String DB_URL = "jdbc:mysql://localhost:3306/voterelection";
	private static final String DB_DRV = "com.mysql.jdbc.Driver";  // "com.mysql.cj.jdbc.Driver";
	private String m_error = "";
	private ArrayList<String> m_resultList;
	private int m_columnCount = 0;
	private int m_rows = 0;

	private JPanel contentPane;
	private JPanel panel_login;
	private JTextField userName;
	private Button db_connect_button;
	private JLabel lblPassword;
	private JLabel lblUserName;
	private JPanel panel_employees;
	private JPanel panel_welcome;
	private JLabel lbImageHomePage;
	private JPasswordField passWord;
	private JLabel lblWelcome;
	private String user;
	private JLabel labelLogo;
	private JPanel panel_pm_overview;
	
	/**
	 * Create the initial frame.
	 */
	public ManageUp() {
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1118, 723);
		initContentPane();
		initPanelLogIn();
		initUserNameAndPassword();
		initWelcomeLogo();
		initImageHomePage();
		initEmployeePanel();
		initPMOverviewPanel();
	}

	
	public JPanel getPanel() {
		return panel_login;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManageUp frame = new ManageUp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	/**
	 * Creates connection to database
	 * 
	 * @return
	 */
	public int ConnectToDatabase() {
		try {
			if( checkUserNameAndPassword() ) {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				user = userName.getText();
				String pwd = passWord.getText();
				m_conn = DriverManager.getConnection(DB_URL, user, pwd);
			}
		} catch (SQLException ex) {
			m_error = "SQLException: " + ex.getMessage();
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return 0;
		} catch (Exception ex) {
			System.out.println("Error was " + ex.toString());
			return 0;
		}
		return 1;
	}
	
	
	
	/**
	 * Closes database connection
	 * 
	 * @return
	 */
	public int CloseDatabase() {
		try {
			m_conn.close();
			user = "";
			
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} catch (Exception ex) {
			// handle the error
			System.out.println("Error was " + ex.toString());
		}
		return 1;
	}

	
	private void initContentPane() {
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("TabbedPane.background"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
	
	private void initPanelLogIn() {
		panel_login = new JPanel();
		panel_login.setBounds(0, 0, 321, 693);
		panel_login.setBackground(UIManager.getColor("SplitPaneDivider.draggingColor"));
		contentPane.add(panel_login);
		panel_login.setLayout(null);
	}
	
	private void initUserNameAndPassword() {
		userName = new JTextField();
		userName.setBounds(24, 48, 268, 35);
		panel_login.add(userName);
		userName.setColumns(10);

		db_connect_button = new Button("Connect");
		db_connect_button.setBounds(24, 164, 143, 35);
		panel_login.add(db_connect_button);
		db_connect_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				dbConnectButtonActionPerformed(evt);
			}
		});
		db_connect_button.setForeground(UIManager.getColor("Panel.background"));
		db_connect_button.setBackground(UIManager.getColor("RadioButtonMenuItem.acceleratorForeground"));
		
		passWord = new JPasswordField();
		passWord.setColumns(10);
		passWord.setBounds(24, 112, 268, 35);
		panel_login.add(passWord);
		
		lblUserName = new JLabel("User Name");
		lblUserName.setForeground(UIManager.getColor("SplitPane.highlight"));
		lblUserName.setBounds(35, 25, 113, 15);
		panel_login.add(lblUserName);
		
		lblPassword = new JLabel("Password");
		lblPassword.setForeground(UIManager.getColor("SplitPane.highlight"));
		lblPassword.setBounds(34, 87, 102, 23);
		panel_login.add(lblPassword);
	}
	

	private void initWelcomeLogo() {
		lblWelcome = new JLabel("");
		lblWelcome.setFont(new Font("Dialog", Font.BOLD, 14));
		lblWelcome.setForeground(UIManager.getColor("controlLtHighlight"));
		lblWelcome.setBounds(35, 112, 257, 15);
		panel_login.add(lblWelcome);
		
		labelLogo = new JLabel("");
		labelLogo.setIcon(new ImageIcon(ManageUp.class.getResource("/image/logodark_small.png")));
		labelLogo.setBounds(24, 12, 280, 107);
		
		panel_welcome = new JPanel();
		panel_welcome.setBackground(UIManager.getColor("Tree.textBackground"));
		panel_welcome.setBounds(319, -5, 799, 697);
		contentPane.add(panel_welcome);
	}
	
	private void initImageHomePage() {
		lbImageHomePage = new JLabel("");
		lbImageHomePage.setIcon(new ImageIcon(ManageUp.class.getResource("/image/homepage_7by6v2.png")));
		panel_welcome.add(lbImageHomePage);
	}
	

	private void initPMOverviewPanel() {		
		panel_pm_overview = new JPanel();
		panel_pm_overview.setBackground(new java.awt.Color(220,186,196));
		panel_pm_overview.setBounds(319, 0, 799, 693);
		contentPane.add(panel_pm_overview);
	}
	
	private void initEmployeePanel() {
		panel_employees = new JPanel();
		panel_employees.setBackground( new java.awt.Color(192, 249, 233));
		panel_employees.setBounds(319, 0, 799, 693);
		contentPane.add(panel_employees);
	}
	
	
	/**
	 * When the db connection button is clicked it will call the code to connect to
	 * the database
	 * 
	 * @param evt
	 */
	private void dbConnectButtonActionPerformed(java.awt.event.ActionEvent evt) {
		int nValue = 0;
		if (db_connect_button.getLabel() == "Connect") {
			nValue = this.ConnectToDatabase();
			
			if (nValue == 1) {
				db_connect_button.setLabel("Disconnect");
				panel_welcome.hide();
				lblPassword.hide();
				lblUserName.hide();
				userName.hide();
				passWord.hide();
				lblWelcome.setText("Welcome " + user);
				panel_login.add(labelLogo);
			}
		} else {
			nValue = this.CloseDatabase();
			if (nValue == 1) {
				db_connect_button.setLabel("Connect");
				lblWelcome.hide();
				panel_login.remove(labelLogo);
				panel_welcome.show();
				lblPassword.show();
				lblUserName.show();
				userName.show();
				passWord.show();
				
			}
		}
	}
	
	
	/**
	 * Checks the user name and password
	 * @return
	 */
	private boolean checkUserNameAndPassword() {
		String user = userName.getText();
		String pwd = passWord.getText();
		if( user == ""  ||  pwd == "" ) {
			throw new IllegalArgumentException("You need to enter a password");
		}
		
		//TODO: More rigorous checks for user name and password
		return true;
	}
}
