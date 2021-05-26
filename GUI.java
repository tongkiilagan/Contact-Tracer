import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
/**
* This is the GUI Class for the Corona Tracker Program
*
* @author Vincent Ilagan
* @author Ralph Sanson
*  
* SECTION: S15A
*/
public class GUI extends JFrame
{
	private CardLayout cards = new CardLayout();
	private GridBagConstraints gbc = new GridBagConstraints();
	
	private ArrayList<JPanel> arrPanels;
	private ArrayList<JButton> arrButtons;
	private ArrayList<JLabel> arrLabels;
	private ArrayList<JTextField> arrTextFields;
	private ArrayList<JPasswordField> arrPasswordFields;
	private ArrayList<JComboBox> arrComboBoxes;
	private ArrayList<JTextArea> arrTextAreas;
	private JPanel buttonPanels, interfacePanels;
	
	/**
	 * Initializes a newly created GUI object. The parent constructor, frame size, panel layout, 
	 * program icon is initialized, among other things.
	 */
	public GUI()
	{
		super("Corona Tracker v1.0");
		
		//initializes the ArrayList of JComponents. It is used for searching of a particular component later on.
		arrPanels = new ArrayList<JPanel>();
		arrButtons = new ArrayList<JButton>();
		arrLabels = new ArrayList<JLabel>();
		arrTextFields = new ArrayList<JTextField>(); 
		arrPasswordFields = new ArrayList<JPasswordField>();
		arrComboBoxes = new ArrayList<JComboBox>();
		arrTextAreas = new ArrayList<JTextArea>();
		
		/**
		 * The two main panels of the GUI. We can think of these panels as the deck of cards that consist of
		 * "cards" or panels that the programmers have designed using the init() methods.
		 */
		buttonPanels = new JPanel();
		buttonPanels.setLayout(cards);
		interfacePanels = new JPanel();
		interfacePanels.setLayout(cards);

		setLayout(new GridBagLayout());
		setSize(900, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ImageIcon ico = new ImageIcon("src/maskicon.png");
		Image img = ico.getImage();
		setIconImage(img);
		
		/*
		 * By creating multiple init() methods, the authors of the program can refer back to its design and easily 
		 * make changes down the road. The names in quotes appended to the methods in comments are the names of the 
		 * panel that is refered back to whenever the Controller needs to display a particular panel.  
		 */
		
		init_emptyPanel(); //"emptyPanel"
		init_startMenu(); //"mainMenu"
		init_loginMenu(); //"loginPrompt"
		init_Register_username(); //"register_usernameAttempt"
		init_Register(); //"Register"
		
		init_OfficialAccountOptions(); //"OfficialOptions"
		init_TracerAccountOptions(); //"TracerOptions"
		init_CitizenAccountOptions(); //"CitizenOptions"
		
		init_GovMenu(); //"OfficialMenu"
		init_TracerMenu(); //"TracerMenu"
		init_CitizenMenu(); //"CitizenMenu"
		
		init_CheckIn(); //"checkIn_submissionForm"
		init_ReportPositive();
		init_UpdateInformation(); //"PersonalDetails"
		
		init_UnassignedCases(); //"unassignedCases"
		init_showUnassignedCases(); //"showUnassignedCases"
		init_AssignTracer(); //"assignTracer"
		init_AnalyticsOptions(); //"analytics"
		init_showAnalytics();
		init_CityAndDuration(); //"cases_CityAndDate"
		init_Duration();
		init_City(); //"cases_city"
		init_CreateOfficial(); //"createOfficial_ExistingAccount" OR "createOfficial_NoAccount"
		init_CreateTracer(); //"createTracer_ExistingAccount" OR "createTracer_NoAccount"

		init_TerminateAccount(); //"terminateAccount"
		
		init_AssignedCases();//"showAssignedCases"
		init_TraceCase();//"traceCase"
		init_CTupdates();//"CTupdates"
		
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = 0;
		gbc.gridy = 0;
		buttonPanels.setPreferredSize(new Dimension(250, 350));
		buttonPanels.setBorder(BorderFactory.createLineBorder(Color.black));
		add(buttonPanels, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		interfacePanels.setPreferredSize(new Dimension(600, 350));
		interfacePanels.setBorder(BorderFactory.createLineBorder(Color.black));
		add(interfacePanels, gbc);
		
		setLocationRelativeTo(null);
		setVisible(true);

	}
	/**
	 * Creates a panel with no content. It is called to be displayed whenever the GUI
	 * does not need to display anything. 
	 */
	public void init_emptyPanel()
	{
		JPanel panel = new JPanel();
		panel.setName("emptyPanel");
		interfacePanels.add(panel, "emptyPanel");
	}
	/**
	 * Creates a panel that the user sees upon opening the program. The user can select between 
	 * the options of Registering, Logging in, or Exiting by pressing the corresponding buttons. 
	 */
	public void init_startMenu()
	{
		JPanel panel = new JPanel();
		JButton btn;
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		panel.add(Box.createRigidArea(new Dimension(0, 15)));
		
		btn = new JButton();
		btn.setName("Register");
		btn.setText("Register");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel.add(btn);
		
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("Login");
		btn.setText("Login");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel.add(btn);
		
		panel.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("Exit");
		btn.setText("Exit");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel.add(btn);
		
		panel.setName("mainMenu");
		arrPanels.add(panel);
		buttonPanels.add(panel, "mainMenu");
	}
	/**
	 * The user is prompted to enter the Username and Password fields upon selecting the
	 * "Login" button in the start-up menu. 
	 */
	public void init_loginMenu()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2;
		JLabel label;
		JTextField txtField;
		JPasswordField passwordField;
		JButton btn;
		panel1.setLayout(new BorderLayout());
		
		panel2 = new JPanel();
		panel2.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		label = new JLabel();
		label.setText("Username: ");
		panel2.add(label, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		txtField = new JTextField(10);
		txtField.setName("mainMenu_username");
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		label = new JLabel();
		label.setText("Password: ");
		panel2.add(label, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		passwordField = new JPasswordField(10);
		passwordField.setName("mainMenu_password");
		arrPasswordFields.add(passwordField);
		panel2.add(passwordField, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		btn = new JButton();
		btn.setName("mainMenu_login");
		btn.setText("Login");
		arrButtons.add(btn);
		panel2.add(btn, gbc);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		
		label = new JLabel();
		label.setName("mainMenu_wrongUserPass");
		label.setText("Username and/or passwords do not match!");
		label.setForeground(Color.red);
		label.setVisible(false);
		arrLabels.add(label);
		panel2.add(label);
		panel1.add(panel2, BorderLayout.NORTH);
		
		panel1.setName("loginPrompt");
		arrPanels.add(panel1);
		interfacePanels.add(panel1, "loginPrompt");
	}
	/**
	 * Upon selecting "Register" in the main menu, the user is prompted to enter a username
	 * that he/she wishes the account to be created on. The Controller will check the availability
	 * of the username.
	 */
	public void init_Register_username()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JLabel lbl;
		JTextField txtField;
		JButton btn;
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lbl = new JLabel();
		lbl.setText("Enter username: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		txtField = new JTextField(10);
		txtField.setName("register_usernameAttempt");
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setName("register_usernameAttempt");
		btn.setText("OK");
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.SOUTH);
		panel1.setName("register_usernameAttempt");
		interfacePanels.add(panel1, "register_usernameAttempt");
	}
	/**
	 * The user is prompted to enter the Register form after entering a vacant username.
	 * The register form consist of text fields for the password, first name, middle name, surname,
	 * home address, office address, telephone, and email address. The Controller will need to check
	 * the validity of the password by determining if the password input is a string of characters
	 * greater than 6, and consist of at least one special character.
	 */
	public void init_Register() 
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JLabel lbl;
		JTextField txtField;
		JPasswordField passwordField;
		JButton btn;
		panel1.setLayout(new GridBagLayout());
		
		panel1.add(Box.createRigidArea(new Dimension(0, 15)));
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lbl = new JLabel("Username: ");
		lbl.setHorizontalAlignment(JLabel.LEADING);
		panel1.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		txtField = new JTextField(10);
		txtField.setName("register_username");
		txtField.setEditable(false);
		arrTextFields.add(txtField);
		panel1.add(txtField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		lbl = new JLabel("Password: ");
		panel1.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		passwordField = new JPasswordField(10);
		passwordField.setName("register_password");
		arrPasswordFields.add(passwordField);
		panel1.add(passwordField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		lbl = new JLabel("First Name: ");
		panel1.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		txtField = new JTextField(10);
		txtField.setName("register_firstname");
		arrTextFields.add(txtField);
		panel1.add(txtField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		lbl = new JLabel("Middle name: ");
		panel1.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		txtField = new JTextField(10);
		txtField.setName("register_middlename");
		arrTextFields.add(txtField);
		panel1.add(txtField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		lbl = new JLabel("Surname: ");
		panel1.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		txtField = new JTextField(10);
		txtField.setName("register_surname");
		arrTextFields.add(txtField);
		panel1.add(txtField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		lbl = new JLabel("Home Address: ");
		panel1.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 7;
		txtField = new JTextField(10);
		txtField.setName("register_homeAddress");
		arrTextFields.add(txtField);
		panel1.add(txtField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		lbl = new JLabel("Office Address: ");
		panel1.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 8;
		txtField = new JTextField(10);
		txtField.setName("register_officeAddress");
		arrTextFields.add(txtField);
		panel1.add(txtField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 9;
		lbl = new JLabel("Telephone: ");
		panel1.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 9;
		txtField = new JTextField(10);
		txtField.setName("register_telephone");
		arrTextFields.add(txtField);
		panel1.add(txtField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 10;
		lbl = new JLabel("Email: ");
		panel1.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 10;
		txtField = new JTextField(10);
		txtField.setName("register_email");
		arrTextFields.add(txtField);
		panel1.add(txtField, gbc);
		
		panel2.setLayout(new BorderLayout());
		panel2.add(panel1, BorderLayout.CENTER);
		
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		lbl = new JLabel();
		lbl.setText("Account Creation"); //TODO: set invisible, 
		lbl.setName("register_title");
		arrLabels.add(lbl);
		panel1.add(lbl);
		
		panel2.add(panel1, BorderLayout.NORTH);
		
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setName("register_AccountCreationAttempt");
		btn.setText("OK");
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel2.add(panel1, BorderLayout.SOUTH);
		panel2.setName("Register");
		arrPanels.add(panel2);
		interfacePanels.add(panel2, "Register");
	}
	/**
	 * Upon a successful login, the Controller will check whether the user account is 
	 * an instance of GovernmentOfficial class. This panel shows the buttons that allows
	 * a Government Official to swap between Government Official privileges and Citizen options.
	 */
	public void init_OfficialAccountOptions()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JButton btn;
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		
		panel1.add(Box.createRigidArea(new Dimension(0, 15)));
		
		btn = new JButton();
		btn.setName("TracerOptions");
		btn.setText("Government Official Options");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("CitizenMenu");
		btn.setText("Citizen Options");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel2.setLayout(new BorderLayout());
		panel2.add(panel1, BorderLayout.CENTER);
		
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setText("Logout");
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel2.add(panel1, BorderLayout.SOUTH);
		panel2.setName("OfficialOptions");
		buttonPanels.add(panel2, "OfficialOptions");		
	}
	/**
	 * Upon a successful login, the Controller will check whether the user account is 
	 * an instance of ContactTracer class. This panel shows the buttons that allows
	 * a Contact Tracer to swap between Contact Tracer privileges and Citizen options.
	 */
	public void init_TracerAccountOptions()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JButton btn;
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		
		panel1.add(Box.createRigidArea(new Dimension(0, 15)));
		
		btn = new JButton();
		btn.setName("TracerOptions");
		btn.setText("Contact Tracer Options");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("CitizenMenu");
		btn.setText("Citizen Options");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel2.setLayout(new BorderLayout());
		panel2.add(panel1, BorderLayout.CENTER);
		
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setText("Logout");
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel2.add(panel1, BorderLayout.SOUTH);
		panel2.setName("TracerOptions");
		buttonPanels.add(panel2, "TracerOptions");
	}
	/**
	 * This method creates a panel displays the button for a user to view the Citizen options.
	 */
	public void init_CitizenAccountOptions()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JButton btn;
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		
		panel1.add(Box.createRigidArea(new Dimension(0, 15)));
		
		btn = new JButton();
		btn.setName("CitizenMenu");
		btn.setText("Citizen Options");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel2.setLayout(new BorderLayout());
		panel2.add(panel1, BorderLayout.CENTER);
		
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setText("Logout");
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel2.add(panel1, BorderLayout.SOUTH);
		panel2.setName("CitizenOptions");
		buttonPanels.add(panel2, "CitizenOptions");	
	}
	/**
	 * If the Government Official has selected to view his/her account privileges,
	 * the panel that consists of Government Official options is displayed. 
	 */
	public void init_GovMenu()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JButton btn;
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		
		panel1.add(Box.createRigidArea(new Dimension(0, 15)));
		
		btn = new JButton();
		btn.setName("UnassignedCases");
		btn.setText("Show Unassigned Cases");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("TracingUpdates");
		btn.setText("Show Contact Tracing Updates");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("Analytics");
		btn.setText("Analytics");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("CreateGovOfficial");
		btn.setText("Create Government Official");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("CreateTracer");
		btn.setText("Create Contact Tracer");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("Terminate");
		btn.setText("Terminate Account");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		panel2.setLayout(new BorderLayout());
		panel2.add(panel1, BorderLayout.CENTER);
		
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setText("Exit");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel2.add(panel1, BorderLayout.SOUTH);
		panel2.setName("OfficialMenu");
		buttonPanels.add(panel2, "OfficialMenu");
	}
	/**
	 * If the Contact Tracer has selected to view his/her account privileges,
	 * the panel that consists of Contact Tracer options is displayed. 
	 */
	public void init_TracerMenu()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JButton btn;
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		
		panel1.add(Box.createRigidArea(new Dimension(0, 15)));
		
		btn = new JButton();
		btn.setName("ShowAssignedCases");
		btn.setText("Show Cases");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("TraceSpecificCase");
		btn.setText("Trace Specific Case");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("InformCitizens");
		btn.setText("Inform Citizens Possibly Exposed");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		panel2.setLayout(new BorderLayout());
		panel2.add(panel1, BorderLayout.CENTER);
		
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setText("Exit");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel2.add(panel1, BorderLayout.SOUTH);
		panel2.setName("TracerMenu");
		buttonPanels.add(panel2, "TracerMenu");
	}
	/**
	 * If the Citizen has selected to view his/her account privileges,
	 * the panel that consists of Citizen options is displayed. 
	 */
	public void init_CitizenMenu()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JButton btn;
		
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
		
		panel1.add(Box.createRigidArea(new Dimension(0, 15)));
		
		btn = new JButton();
		btn.setName("CheckIn");
		btn.setText("Check In");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("ReportPositive");
		btn.setText("Report Positive Test Result");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("UpdateProfile");
		btn.setText("Update Profile Information");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		
		panel2.setLayout(new BorderLayout());
		panel2.add(panel1, BorderLayout.CENTER);
		
		panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setText("Exit");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel1.add(btn);
		
		panel2.add(panel1, BorderLayout.SOUTH);
		panel2.setName("CitizenMenu");
		buttonPanels.add(panel2, "CitizenMenu");
	}
	/**
	 * Upon pressing the "Check In" button from the Citizen account options, the user is 
	 * prompted to fill in the establishment code and the date.
	 */
	public void init_CheckIn()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		JComboBox<String> box;
		JLabel lbl;
		JTextField txtField;
		JButton btn;
		
		String[] months = {"1", "2", "3", "4", "5", "6",
						   "7", "8", "9", "10", "11", "12"};

		String[] dates = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", 
						  "14", "15", "16","17", "18", "19", "20", "21", "22", "23", "24", "25", 
						  "26", "27", "28", "29", "30", "31"};
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel3.setLayout(new FlowLayout());
		
		lbl = new JLabel("Establishment Code");
		panel3.add(lbl);
		
		txtField = new JTextField(10);
		txtField.setName("checkIn_establishmentCode");
		arrTextFields.add(txtField);
		panel3.add(txtField);
		
		panel2.add(panel3);
		
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		
		lbl = new JLabel("Date: ");
		panel3.add(lbl);
		
		box = new JComboBox<String>(months);
		box.setName("checkIn_months");
		arrComboBoxes.add(box);
		panel3.add(box);
		
		box = new JComboBox<String>(dates);
		box.setName("checkIn_days");
		arrComboBoxes.add(box);
		panel3.add(box);
		
		txtField = new JTextField(4);
		txtField.setName("checkIn_years");
		arrTextFields.add(txtField);
		panel3.add(txtField);
		
		panel2.add(panel3);
		
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		lbl = new JLabel("Time [HH:mm]");
		panel3.add(lbl);
		
		txtField = new JTextField(2);
		txtField.setName("checkIn_hours");
		arrTextFields.add(txtField);
		panel3.add(txtField);
		
		lbl = new JLabel(":");
		panel3.add(lbl);
		
		txtField = new JTextField(2);
		txtField.setName("checkIn_minutes");
		arrTextFields.add(txtField);
		panel3.add(txtField);
		
		panel2.add(panel3);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setName("checkIn_submit");
		btn.setText("Submit");
		arrButtons.add(btn);
		panel3.add(btn);
		
		panel1.add(panel3, BorderLayout.SOUTH);
		panel1.setName("checkIn");
		interfacePanels.add(panel1, "checkIn");
		//"checkIn_submit, OK"
	}
	/**
	 * Upon pressing the "Report Positive Test Result" button from the Citizen account options, the user is 
	 * prompted to fill in the date and time.
	 */
	public void init_ReportPositive()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JComboBox<String> box;
		JTextField txtField;
		JButton btn;
		JLabel lbl;
		
		String[] months = {"1", "2", "3", "4", "5", "6",
				   		   "7", "8", "9", "10", "11", "12"};

		String[] dates = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", 
						  "14", "15", "16","17", "18", "19", "20", "21", "22", "23", "24", "25", 
						  "26", "27", "28", "29", "30", "31"};
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new FlowLayout());
		
		lbl = new JLabel("Enter date: ");
		panel2.add(lbl);
		
		box = new JComboBox<String>(months);
		box.setName("reportPositive_months");
		arrComboBoxes.add(box);
		panel2.add(box);
		
		box = new JComboBox<String>(dates);
		box.setName("reportPositive_days");
		arrComboBoxes.add(box);
		panel2.add(box);
		
		txtField = new JTextField(4);
		txtField.setName("reportPositive_year");
		arrTextFields.add(txtField);
		panel2.add(txtField);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setName("reportPositive_submit");
		btn.setText("Submit");
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.SOUTH);
		panel1.setName("reportPositive");
		interfacePanels.add(panel1, "reportPositive");
	}
	/**
	 * Upon pressing the "Update Profile Information" button from the Citizen account options, the user 
	 * is presented with his/her personal data that they can edit. In order to make an edit, the "Change"
	 * button of a particular field must be pressed in order to make changes. The user will not be able
	 * to exit the menu until all changes are saved.
	 */
	public void init_UpdateInformation()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JTextField txtField;
		JPasswordField passwordField;
		JButton btn;
		JLabel lbl;
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lbl = new JLabel("Username: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		txtField = new JTextField(10);
		txtField.setName("PersonalDetails_username");
		txtField.setHorizontalAlignment(JTextField.LEFT);
		txtField.setEditable(false);
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		lbl = new JLabel("Password: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 1;
		passwordField = new JPasswordField(10);
		passwordField.setEditable(false);
		passwordField.setName("PersonalDetails_password");
		arrPasswordFields.add(passwordField);
		panel2.add(passwordField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 1;
		btn = new JButton();
		btn.setName("PersonalDetails_editPassword");
		btn.setText("Change");
		arrButtons.add(btn);
		panel2.add(btn, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		lbl = new JLabel("First name: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		txtField = new JTextField(10);
		txtField.setName("PersonalDetails_firstName");
		txtField.setHorizontalAlignment(JTextField.LEFT);
		txtField.setEditable(false);
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 2;
		btn = new JButton();
		btn.setName("PersonalDetails_editFirstName");
		btn.setText("Change");
		arrButtons.add(btn);
		panel2.add(btn, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		lbl = new JLabel("Middle name: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 3;
		txtField = new JTextField(10);
		txtField.setName("PersonalDetails_middleName");
		txtField.setHorizontalAlignment(JTextField.LEFT);
		txtField.setEditable(false);
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 3;
		btn = new JButton();
		btn.setName("PersonalDetails_editMiddleName");
		btn.setText("Change");
		arrButtons.add(btn);
		panel2.add(btn, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 4;
		lbl = new JLabel("Surname: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 4;
		txtField = new JTextField(10);
		txtField.setName("PersonalDetails_surname");
		txtField.setHorizontalAlignment(JTextField.LEFT);
		txtField.setEditable(false);
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 4;
		btn = new JButton();
		btn.setName("PersonalDetails_editSurname");
		btn.setText("Change");
		arrButtons.add(btn);
		panel2.add(btn, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 5;
		lbl = new JLabel("Home address: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 5;
		txtField = new JTextField(10);
		txtField.setName("PersonalDetails_home");
		txtField.setHorizontalAlignment(JTextField.LEFT);
		txtField.setEditable(false);
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 5;
		btn = new JButton();
		btn.setName("PersonalDetails_editHome");
		btn.setText("Change");
		arrButtons.add(btn);
		panel2.add(btn, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 6;
		lbl = new JLabel("Office address: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 6;
		txtField = new JTextField(10);
		txtField.setName("PersonalDetails_office");
		txtField.setHorizontalAlignment(JTextField.LEFT);
		txtField.setEditable(false);
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 6;
		btn = new JButton();
		btn.setName("PersonalDetails_editOffice");
		btn.setText("Change");
		arrButtons.add(btn);
		panel2.add(btn, gbc);
		
		
		gbc.gridx = 0;
		gbc.gridy = 7;
		lbl = new JLabel("Telephone: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 7;
		txtField = new JTextField(10);
		txtField.setName("PersonalDetails_telephone");
		txtField.setHorizontalAlignment(JTextField.LEFT);
		txtField.setEditable(false);
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 7;
		btn = new JButton();
		btn.setName("PersonalDetails_editTelephone");
		btn.setText("Change");
		arrButtons.add(btn);
		panel2.add(btn, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 8;
		lbl = new JLabel("Email: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 8;
		txtField = new JTextField(10);
		txtField.setName("PersonalDetails_email");
		txtField.setHorizontalAlignment(JTextField.LEFT);
		txtField.setEditable(false);
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 8;
		btn = new JButton();
		btn.setName("PersonalDetails_editEmail");
		btn.setText("Change");
		arrButtons.add(btn);
		panel2.add(btn, gbc);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		lbl = new JLabel("Account Information");
		panel2.add(lbl);
		
		panel1.add(panel2, BorderLayout.NORTH);
		panel1.setName("PersonalDetails");
		arrPanels.add(panel1);
		interfacePanels.add(panel1, "PersonalDetails");
	}
	/**
	 * This is a JTextArea template that can display text. It is used to display all cases
	 * for the Government Officials to view the Citizens who have declared to be positive.
	 */
	public void init_showCases()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JTextArea txtArea;
		JScrollPane jsp;
		JButton btn;
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new FlowLayout());
		txtArea = new JTextArea(20,30);
		jsp = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						  	  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		txtArea.setEditable(false);
		txtArea.setName("showCases_txtArea");
		arrTextAreas.add(txtArea);
		panel2.add(jsp);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setText("Back");
		btn.setName("showCase_back");
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.SOUTH);
		panel1.setName("showCases");
		interfacePanels.add(panel1, "showCases");
	}
	/**
	 * This is a JTextArea template that can display text. It is used to display all unassigned cases
	 * for the Government Officials to view the Citizens who have declared to be positive, but hasn't been
	 * assigned to a Contact Tracer.
	 */
	public void init_UnassignedCases()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JButton btn;
		JLabel lbl;
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		
		panel2.add(Box.createRigidArea(new Dimension(0, 50)));
		
		btn = new JButton();
		btn.setName("unassignedCases_showCases");
		btn.setText("Unassigned Cases");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel2.add(Box.createRigidArea(new Dimension(0, 10)));
		
		btn = new JButton();
		btn.setName("unassignedCases_assignTracer");
		btn.setText("Assign Tracer");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		lbl = new JLabel("Unassigned Cases");
		panel2.add(lbl);
		
		panel1.add(panel2, BorderLayout.NORTH);
		panel1.setName("unassignedCases");
		interfacePanels.add(panel1, "unassignedCases");
	}
	/**
	 * Upon selecting "Show Unassigned Cases", the Government Official will see the options
	 * between showing the unassigned cases, and the option to assign a Contact Tracer a particular case
	 */
	public void init_showUnassignedCases()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JTextArea txtArea;
		JScrollPane jsp;
		JButton btn;
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new FlowLayout());
		txtArea = new JTextArea("Unassigned Cases:\n\n",20,30);
		jsp = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						  	  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		txtArea.setEditable(false);
		txtArea.setName("showUnassignedCases_txtArea");
		arrTextAreas.add(txtArea);
		panel2.add(jsp);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setText("Back");
		btn.setName("showUnassignedCases_back");
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.SOUTH);
		panel1.setName("showUnassignedCases");
		interfacePanels.add(panel1, "showUnassignedCases");
	}
	/**
	 * This panel will display two JTextAreas. The left text area will contain the list of unassigned cases,
	 * similar to the implementation of init_UnassignedCases(). The right text area will display all Contact Tracers.
	 */
	public void init_AssignTracer()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JTextArea txtArea;
		JScrollPane jsp;
		JLabel lbl;
		JButton btn;
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new GridBagLayout());
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		panel4.setLayout(new FlowLayout());
		
		lbl = new JLabel("Unassigned Cases");
		lbl.setHorizontalAlignment(JLabel.CENTER);
		panel3.add(lbl);
		
		txtArea = new JTextArea(10,20);
		txtArea.setName("UnassignedCases_txtArea");
		txtArea.setEditable(false);
		arrTextAreas.add(txtArea);
		jsp = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
							  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel3.add(jsp);
		panel2.add(panel3, gbc);
		
		panel3 = new JPanel();
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
		
		lbl = new JLabel("Available Contact Tracers");
		lbl.setHorizontalAlignment(JLabel.CENTER);
		panel3.add(lbl);
		
		txtArea = new JTextArea(10,20);
		txtArea.setName("ContactTracers_txtArea");
		txtArea.setEditable(false);
		arrTextAreas.add(txtArea);
		jsp = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
							  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		panel3.add(jsp);
		panel2.add(panel3, gbc);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		btn = new JButton();
		btn.setName("assignTracer_back");
		btn.setText("Back");
		arrButtons.add(btn);
		panel4.add(btn);
		
		btn = new JButton();
		btn.setName("assignTracer_assign");
		btn.setText("Assign");
		arrButtons.add(btn);
		panel4.add(btn);
		
		panel1.add(panel4, BorderLayout.SOUTH);
		panel1.setName("assignTracer");
		interfacePanels.add(panel1, "assignTracer");
	}
	/**
	 * Upon selecting "Show Unassigned Cases", the Government Official will see the options
	 * between showing cases within a City and Duration, within a Duration, or within a City.
	 * Entering dates must be done by selecting the lowerbound and upperbound date, while the field
	 * for City must be entered as well. 
	 */
	public void init_AnalyticsOptions()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JButton btn;
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		
		panel2.add(Box.createRigidArea(new Dimension(0, 15)));
		
		btn = new JButton();
		btn.setName("analytics_cityAndDuration");
		btn.setText("Cases within City and Duration");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel2.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("analytics_duration");
		btn.setText("Cases within Duration");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel2.add(Box.createRigidArea(new Dimension(0, 5)));
		
		btn = new JButton();
		btn.setName("analytics_city");
		btn.setText("Cases within City");
		btn.setAlignmentX(CENTER_ALIGNMENT);
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		
		btn = new JButton();
		btn.setText("Exit");
		btn.setName("cases_City_exit");
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.SOUTH);
		panel1.setName("analytics");
		arrPanels.add(panel1);
		buttonPanels.add(panel1, "analytics");
	}
	/**
	 * This is a JTextArea template that can display text. It is used to display all cases within certain parameters
	 * for the Government Officials to view the Citizens who have declared to be positive, but hasn't been
	 * assigned to a Contact Tracer. 
	 */
	public void init_showAnalytics()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JTextArea txtArea;
		JScrollPane jsp;
		JButton btn;
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new FlowLayout());
		txtArea = new JTextArea(20,30);
		jsp = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						  	  JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		txtArea.setEditable(false);
		txtArea.setName("showAnalytics_txtArea");
		arrTextAreas.add(txtArea);
		panel2.add(jsp);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setText("Back");
		btn.setName("showAnalytics_back");
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.SOUTH);
		panel1.setName("showAnalytics");
		interfacePanels.add(panel1, "showAnalytics");
	}
	/**
	 * The Government Official is presented to enter the form that will require him/her 
	 * to enter two dates: lowerbound and upperbound, and a City. 
	 */
	public void init_CityAndDuration()
	{
		//TODO: format ComboBox, fix everything here
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JComboBox<String> box;
		JTextField txtField;
		JButton btn;
		JLabel lbl;
		String[] months = {"1", "2", "3", "4", "5", "6",
						  "7", "8", "9", "10", "11", "12"};
		//getselecteditems //getselectedindex
		String[] dates = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", 
						  "14", "15", "16","17", "18", "19", "20", "21", "22", "23", "24", "25", 
				          "26", "27", "28", "29", "30", "31"};
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel3.setLayout(new FlowLayout());
		
		lbl = new JLabel("From: ");
		panel3.add(lbl);
		
		box = new JComboBox<String>(months);
		box.setName("cases_CityAndDuration_fromMonth");
		arrComboBoxes.add(box);
		panel3.add(box);
		
		box = new JComboBox<String>(dates);
		box.setName("cases_CityAndDuration_fromDay");
		arrComboBoxes.add(box);
		panel3.add(box);
		
		txtField = new JTextField(4);
		txtField.setName("cases_CityAndDuration_fromYear");
		arrTextFields.add(txtField);
		panel3.add(txtField);
		
		panel2.add(panel3);
		
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		lbl = new JLabel("To: ");
		panel3.add(lbl);
		
		box = new JComboBox<String>(months);
		box.setName("cases_CityAndDuration_toMonth");
		arrComboBoxes.add(box);
		panel3.add(box);
		
		box = new JComboBox<String>(dates);
		box.setName("cases_CityAndDuration_toDay");
		arrComboBoxes.add(box);
		panel3.add(box);
		
		txtField = new JTextField(4);
		txtField.setName("cases_CityAndDuration_toYear");
		arrTextFields.add(txtField);
		panel3.add(txtField);
		
		panel2.add(panel3);
		
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		lbl = new JLabel("City: ");
		panel3.add(lbl);
		
		txtField = new JTextField(10);
		txtField.setName("cases_CityAndDuration_City");
		arrTextFields.add(txtField);
		panel3.add(txtField);
		
		panel2.add(panel3);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setText("OK");
		btn.setName("cases_CityAndDuration_OK");
		arrButtons.add(btn);
		panel3.add(btn);
		
		panel1.add(panel3, BorderLayout.SOUTH);
		panel1.setName("cases_CityAndDuration");
		arrPanels.add(panel1);
		interfacePanels.add(panel1, "cases_CityAndDuration");
	}
	/**
	 * The Government Official is presented to enter the form that will require him/her 
	 * to enter two dates: lowerbound and upperbound. 
	 */
	public void init_Duration()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JComboBox<String> box;
		JTextField txtField;
		JButton btn;
		JLabel lbl;
		String[] months = {"1", "2", "3", "4", "5", "6",
						  "7", "8", "9", "10", "11", "12"};

		String[] dates = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", 
						  "14", "15", "16","17", "18", "19", "20", "21", "22", "23", "24", "25", 
				          "26", "27", "28", "29", "30", "31"};
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
		panel3.setLayout(new FlowLayout());
		
		lbl = new JLabel("From: ");
		panel3.add(lbl);
		
		box = new JComboBox<String>(months);
		box.setName("cases_Duration_fromMonth");
		arrComboBoxes.add(box);
		panel3.add(box);
		
		box = new JComboBox<String>(dates);
		box.setName("cases_Duration_fromDay");
		arrComboBoxes.add(box);
		panel3.add(box);
		
		txtField = new JTextField(4);
		txtField.setName("cases_Duration_fromYear");
		arrTextFields.add(txtField);
		panel3.add(txtField);
		
		panel2.add(panel3);
		
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		lbl = new JLabel("To: ");
		panel3.add(lbl);
		
		box = new JComboBox<String>(months);
		box.setName("cases_Duration_toMonth");
		arrComboBoxes.add(box);
		panel3.add(box);
		
		box = new JComboBox<String>(dates);
		box.setName("cases_Duration_toDay");
		arrComboBoxes.add(box);
		panel3.add(box);
		
		txtField = new JTextField(4);
		txtField.setName("cases_Duration_toYear");
		arrTextFields.add(txtField);
		panel3.add(txtField);
		
		panel2.add(panel3);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setText("OK");
		btn.setName("cases_Duration_OK");
		arrButtons.add(btn);
		panel3.add(btn);
		
		panel1.add(panel3, BorderLayout.SOUTH);
		panel1.setName("cases_Duration");
		arrPanels.add(panel1);
		interfacePanels.add(panel1, "cases_Duration");
	}
	/**
	 * The Government Official is presented to enter the form that will require him/her 
	 * to enter a City. 
	 */
	public void init_City()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JLabel lbl;
		JTextField txtField;
		JButton btn;
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lbl = new JLabel("City: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		txtField = new JTextField(10);
		txtField.setName("cases_City_City");
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setName("cases_City_OK");
		btn.setText("OK");
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.SOUTH);
		panel1.setName("cases_City");
		interfacePanels.add(panel1, "cases_City");
	}
	/**
	 * The Government Official can enter a username for the creation of an account new 
	 * Government Official account.
	 */
	public void init_CreateOfficial()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JLabel lbl;
		JTextField txtField;
		JButton btn;
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lbl = new JLabel("Username: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		txtField = new JTextField(10);
		txtField.setName("createOfficial_ExistingAccount_username");
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setName("createOfficial_ExistingAccount_submit");
		btn.setText("Submit");
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.SOUTH);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		lbl = new JLabel("Promote a Citizen to Government Official");
		panel2.add(lbl);
		
		panel1.add(panel2, BorderLayout.NORTH);
		panel1.setName("createOfficial_ExistingAccount");
		interfacePanels.add(panel1, "createOfficial_ExistingAccount");
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lbl = new JLabel("Username: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		txtField = new JTextField(10);
		txtField.setName("createOfficial_NoAccount_username");
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setName("createOfficial_NoAccount_submit");
		btn.setText("Submit");
		arrButtons.add(btn);
		panel2.add(btn);
				
		panel1.add(panel2, BorderLayout.SOUTH);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		lbl = new JLabel("Promote a Citizen to Government Official");
		panel2.add(lbl);
		
		panel1.add(panel2, BorderLayout.NORTH);
		panel1.setName("createOfficial_NoAccount");
		interfacePanels.add(panel1, "createOfficial_NoAccount");
	}
	/**
	 * The Government Official can enter a username for the creation of an account new 
	 * Contact Tracer account.
	 */
	public void init_CreateTracer()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JLabel lbl;
		JTextField txtField;
		JButton btn;
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lbl = new JLabel("Username: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		txtField = new JTextField(10);
		txtField.setName("createTracer_ExistingAccount_username");
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setName("createTracer_ExistingAccount_submit");
		btn.setText("Submit");
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.SOUTH);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		lbl = new JLabel("Promote a Citizen to Contact Tracer");
		panel2.add(lbl);
		
		panel1.add(panel2, BorderLayout.NORTH);
		panel1.setName("createTracer_ExistingAccount");
		interfacePanels.add(panel1, "createTracer_ExistingAccount");
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lbl = new JLabel("Username: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		txtField = new JTextField(10);
		txtField.setName("createTracer_NoAccount_username");
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setName("createTracer_NoAccount_submit");
		btn.setText("Submit");
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.SOUTH);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		lbl = new JLabel("Promote a Citizen to Contact Tracer");
		panel2.add(lbl);
		
		panel1.add(panel2, BorderLayout.NORTH);
		panel1.setName("createTracer_NoAccount");
		interfacePanels.add(panel1, "createTracer_NoAccount");		
	}
	/**
	 * The Government Official can enter a username for the termination of a  
	 * Government Official or a Contact Tracer account.
	 */
	public void init_TerminateAccount()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JLabel lbl;
		JTextField txtField;
		JButton btn;
		
		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		lbl = new JLabel("Username of Account to demote: ");
		panel2.add(lbl, gbc);
		
		gbc.gridx = 1;
		gbc.gridy = 0;
		txtField = new JTextField(10);
		txtField.setName("terminateAccount_username");
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);
		
		panel1.add(panel2, BorderLayout.CENTER);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setName("terminateAccount_submit");
		btn.setText("OK");
		arrButtons.add(btn);
		panel2.add(btn);
		
		panel1.add(panel2, BorderLayout.SOUTH);
		
		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		lbl = new JLabel("Cannot delete your own account!");
		lbl.setName("terminateAccount_errorText");
		lbl.setForeground(Color.red);
		lbl.setVisible(false);
		arrLabels.add(lbl);
		panel2.add(lbl);
		
		panel1.add(panel2, BorderLayout.NORTH);
		panel1.setName("terminateAccount");
		interfacePanels.add(panel1, "terminateAccount");
	}
	/**
	 * This is a JTextArea template that can display text. It is used to display all untraced cases
	 * for the Contact Tracer to view the Citizens who have declared to be positive, but hasn't been
	 * traced by the Contact Tracer.
	 */
	public void init_AssignedCases()
    {
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JTextArea txtArea;
        JScrollPane jsp;
        JButton btn;

        panel1.setLayout(new BorderLayout());
        panel2.setLayout(new FlowLayout());
        txtArea = new JTextArea("Assigned Cases:\n\n",20,50);
        jsp = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        txtArea.setEditable(false);
        txtArea.setName("showAssignedCases_txtArea");
        arrTextAreas.add(txtArea);
        panel2.add(jsp);

        panel1.add(panel2, BorderLayout.CENTER);
        panel1.add(panel2, BorderLayout.SOUTH);
        panel1.setName("showAssignedCases");
        interfacePanels.add(panel1, "showAssignedCases");
    }
	/**
	 * The Contact Tracer will be able to enter the case number of the case
	 * that has been assigned to him/her
	 */
    public void init_TraceCase()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JLabel lbl;
		JTextField txtField;
		JButton btn;

		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		lbl = new JLabel("Case Number: ");
		panel2.add(lbl, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		txtField = new JTextField(10);
		txtField.setName("traceCase_caseNum");
		arrTextFields.add(txtField);
		panel2.add(txtField, gbc);

		panel1.add(panel2, BorderLayout.CENTER);

		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		btn = new JButton();
		btn.setName("traceCase_submit");
		btn.setText("Submit");
		arrButtons.add(btn);
		panel2.add(btn);

		panel1.add(panel2, BorderLayout.SOUTH);

		panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		lbl = new JLabel("Trace a specific case");
		panel2.add(lbl);

		panel1.add(panel2, BorderLayout.NORTH);
		panel1.setName("traceCase");
		interfacePanels.add(panel1, "traceCase");
	}
	/**
	 * This is a JTextArea template that can display text. It is used to display all Contact Tracing updates
	 * for the Contact Tracer to view the Citizens who have either been traced or not.
	 */
	public void init_CTupdates()
	{
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JTextArea txtArea;
		JScrollPane jsp;
		JButton btn;

		panel1.setLayout(new BorderLayout());
		panel2.setLayout(new FlowLayout());
		txtArea = new JTextArea("Contact Tracer Updates:\n\n",20,50);
		jsp = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		txtArea.setEditable(false);
		txtArea.setName("CTupdates_txtArea");
		arrTextAreas.add(txtArea);
		panel2.add(jsp);

		panel1.add(panel2, BorderLayout.CENTER);
		panel1.setName("CTupdates");
		interfacePanels.add(panel1, "CTupdates");
	}
	/**
	 * Creates a pop-up panel that will display an error for any Citizen who might have been exposed.
	 */
	public void init_popUpNotif(){
		JFrame frame = new JFrame();
		JOptionPane.showMessageDialog(frame,"You may have been exposed to the virus, Please take a swab test as soon as possible");
	}
	/**
	 * Iterates through all the buttons and assigns it an Action Listener that is implemented in the
	 * Controller class.
	 * @param al - the Action Listener implemented by the Controller.
	 */
	public void addListener(ActionListener al)
	{
		for(JButton btn: arrButtons)
			btn.addActionListener(al);
	}
	/**
	 * Returns the JPanel with the same name specified by the String parameter. If the panel
	 * has not been found, this method will return null.
	 * 
	 * @param x - the name of the panel to be searched for.
	 * @return: the JPanel with the same matching name as the parameter.
	 */
	public JPanel getPanel(String x)
	{
		for(JPanel panel: arrPanels)
			if(panel.getName().equals(x))
				return panel;
		
		return null;
	}
	/**
	 * Returns the JTextField with the same name specified by the String parameter. If the text field
	 * has not been found, this method will return null.
	 * 
	 * @param field - the name of the text field to be searched for.
	 * @return the text field that the Controller can change its text.
	 */
	public JTextField getTextField(String field)
	{
		for(JTextField fields: arrTextFields)
			if(fields.getName().equals(field))
				return fields;
		
		return null;
	}
	/**
	 * Returns the JLabel with the same name specified by the String parameter. If the label
	 * has not been found, this method will return null.
	 * 
	 * @param lbl - the name of the label to be searched for.
	 * @return the label that the Controller can change its color or visibility.
	 */
	public JLabel getLabel(String lbl)
	{
		for(JLabel label: arrLabels)
			if(label.getName().equals(lbl))
				return label;
		
		return null;
	}
	/**
	 * Returns the JButton with the same name specified by the String parameter. If the button
	 * has not been found, this method will return null.
	 * 
	 * @param btn - the name of the button to be searched for.
	 * @return the button that the Controller can change its text.
	 */
	public JButton getButton(String btn)
	{
		for(JButton btns: arrButtons)
			if(btns.getName().equals(btn))
				return btns;
		
		return null;
	}
	/**
	 * Returns the JComboBox with the same name specified by the String parameter. If the combo box
	 * has not been found, this method will return null.
	 * 
	 * @param box - the name of the text field to be searched for.
	 * @return the combo box that the Controller can change retrieve the value of the selected index.
	 */
	public JComboBox getBox(String box)
	{
		for(JComboBox boxes: arrComboBoxes)
			if(boxes.getName().equals(box))
				return boxes;
		
		return null;
	}
	/**
	 * Returns the JPasswordField with the same name specified by the String parameter. If the password field
	 * has not been found, this method will return null.
	 * 
	 * @param field - the name of the password field to be searched for.
	 * @return the password field that the Controller can retrieve the content.
	 */
	public JPasswordField getPasswordField(String field)
	{
		for(JPasswordField fields: arrPasswordFields)
			if(fields.getName().equals(field))
				return fields;
		
		return null;
	}
	/**
	 * Returns the JTextArea with the same name specified by the String parameter. If the text area
	 * has not been found, this method will return null.
	 * 
	 * @param txtArea - the name of the text area to be searched for.
	 * @return the text area that the Controller can set its text to display.
	 */
	public JTextArea getTextArea(String txtArea)
	{
		for(JTextArea txtAreas: arrTextAreas)
			if(txtAreas.getName().equals(txtArea))
				return txtAreas;
		
		return null;
	}
	/**
	 * Allows the Controller to display a particular button menu panel after an Event.
	 * 
	 * @param x - the name of the panel to be displayed into the GUI interface.
	 */
	public void showMenu(String x)
	{
		cards.show(buttonPanels, x);
	}
	/**
	 * Allows the Controller to display a particular button interface panel after an Event.
	 * 
	 * @param x - the name of the panel to be displayed into the GUI interface.
	 */
	public void showInterface(String x)
	{
		cards.show(interfacePanels, x);
	}
	/**
	 * Retrieves the name of the current visible menu panel in the GUI interface. It is used by
	 * other methods to index through the button panels that is visible.
	 * 
	 * @return the name of the visible button panel.
	 */
	public String currentMenu()
	{
		for(Component component: buttonPanels.getComponents())
			if(component.isVisible())
				return ((JPanel) component).getName();
		
		return null;
	}
	/**
	 * Retrieves the name of the current visible menu panel in the GUI interface. It is used by
	 * other methods to index through the button panels that is visible.
	 * 
	 * @return the name of the visible button panel.
	 */
	public String getCurrentMenu()
	{
		for(Component component: buttonPanels.getComponents())
			if(component.isVisible())
				return ((JPanel) component).getName();
		
		return null;
	}
	/**
	 * Changes the text of the JTextField to a specified text.
	 * 
	 * @param txtField - the JTextField object that the method will set its text with the name parameter.
	 * @param name - the String that will be set to the text field.
	 */
	public void setTextFieldText(JTextField txtField, String name)
	{
		txtField.setText(name);
	}
	/**
	 * Attempts to parse a String input to its int value. It will return 0 if the user makes an invalid
	 * non-number input as String.
	 * 
	 * @param num - the input String that will be attempted to be converted into an int.
	 * @return the specified int value of the String is returned. The int value 0 is returned if the String is not a valid input. 
	 */
	public int parseInt(String num)
	{
		try
		{
			return Integer.parseInt(num);
		}
		catch(NumberFormatException e)
		{
			return 0;
		}
	}
	/**
	 * Displays the previous menu of the buttonPanels.
	 */
	public void displayPreviousMenu()
	{
		
		if(getCurrentMenu().equals("OfficialMenu"))
			cards.show(buttonPanels, "OfficialOptions");
		else
		if(getCurrentMenu().equals("TracerMenu"))
			cards.show(buttonPanels, "TracerOptions");
		else
		if(getCurrentMenu().equals("CitizenMenu"))
			cards.show(buttonPanels, "CitizenOptions");
		else
		if(getCurrentMenu().equals("OfficialOptions"))

			cards.show(buttonPanels, "mainMenu");
		else
		if(getCurrentMenu().equals("TracerOptions"))
			cards.show(buttonPanels, "mainMenu");
		else
		if(getCurrentMenu().equals("CitizenOptions"))
			cards.show(buttonPanels, "mainMenu");
		else
		if(getCurrentMenu().equals("analytics"))
			cards.show(buttonPanels, "OfficialMenu");
		else
		if(getCurrentMenu().equals("mainMenu"))
			System.exit(0);
		
	}
	/**
	 * Retrieves the name of the current visible interface panel in the GUI interface. It is used by
	 * other methods to index through the interface panels that is visible.
	 * 
	 * @return the name of the visible button panel.
	 */
	public String getCurrentInterface()
	{
		for(Component component: interfacePanels.getComponents())
			if(component.isVisible())
				return ((JPanel) component).getName();
		
		return null;
	}
	/**
	 * Clears the content of all objects of JTextField, JPasswordField, and JComboBox.
	 */
	public void clearTextFields()
	{
		for(JTextField txtField: arrTextFields)
			txtField.setText("");
		
		for(JPasswordField passField: arrPasswordFields)
			passField.setText("");
		
		for(JComboBox comboBoxes: arrComboBoxes)
			comboBoxes.setSelectedIndex(0);
	}
}
