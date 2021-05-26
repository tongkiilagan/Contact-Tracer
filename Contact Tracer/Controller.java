import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
/**
* This is the Controller Class for the Corona Tracker Program
*
* @author Vincent Ilagan
* @author Ralph Sanson
*  
* SECTION: S15A
*/
public class Controller implements ActionListener
{
	private GUI gui;
	private static ArrayList<Citizen> accounts;
	
	/**
	 * Initializes the newly created Controller object. The Controller retrieves the same
	 * reference of the ArrayList of accounts from the Government Official. The Constructor
	 * will read the files from the system in order to retrieve previously-saved accounts into 
	 * the program. The Constructor is also necessary to implement the Action Listener to the 
	 * ArrayList of JButtons found at the GUI Class.
	 */
	public Controller(ArrayList<Citizen> accounts, GUI gui)
	{
		readFile(accounts);
		Controller.accounts = accounts;
		
		accounts.add(new GovernmentOfficial("Admin2020", "@Dm1n0202"));
		
		this.gui = gui;
	
		gui.addListener(this);
	}
	/**
	 * Listens to the button presses made by the user from the GUI interface. The buttons defined 
	 * in the GUI has been given unique names as identifiers in order for the Controller to distinguish 
	 * between each buttons with the same Text.
	 */
	public void actionPerformed(ActionEvent e)
	{
		Citizen currentUser;
		String tempString;
		String tempUsername;
		String tempPassword;
		
		JFrame frame;
		JPanel panel;
		JButton btn;
		JTextField txtField, txtField_x, txtField_y;
		JLabel lbl;
		GridBagConstraints gbc = new GridBagConstraints();
		
		LocalDate fromDate, toDate;
		String time;
		String city;
		int fromMonth, fromDay, fromYear;
		int toMonth, toDay, toYear;
		
		/*
		 * "Login" is found at startup and during login prompt. 
		 */
		
		if(e.getActionCommand().equals("Login"))
		{
			btn = (JButton) e.getSource();
			/*
			 * Displays the interface that allows the user to enter his/her username and password
			 */
			if(btn.getName().equals("Login"))
			{
				gui.clearTextFields();
				
				if(gui.getLabel("mainMenu_wrongUserPass").isVisible())
					gui.getLabel("mainMenu_wrongUserPass").setVisible(false);
				
				gui.showInterface("loginPrompt");
			}
			/*
			 * Retrieves the information found at the Username and Password fields. After a successful login, 
			 * Controller will update the Profile Information, which will subsequently be used to retrieve
			 * the username of the logged in user.
			 */
			else
			if(btn.getName().equals("mainMenu_login"))
			{
				String username = gui.getTextField("mainMenu_username").getText();
				String password = new String(gui.getPasswordField("mainMenu_password").getPassword());
				
				currentUser = getAccount(username, password);
				
				if(currentUser != null)
				{
					if(currentUser instanceof GovernmentOfficial)
					{
						
						readUserDetails(currentUser);
						gui.showInterface("emptyPanel");
						gui.showMenu("OfficialOptions");
						if(currentUser.getIsExposed())
							gui.init_popUpNotif();
						
					}
					else
					if(currentUser instanceof ContactTracer)
					{
						readUserDetails(currentUser);
						gui.showInterface("emptyPanel");
						gui.showMenu("TracerOptions");
						if(currentUser.getIsExposed())
							gui.init_popUpNotif();

					}
					else
					{
						readUserDetails(currentUser);
						gui.showInterface("emptyPanel");
						gui.showMenu("CitizenOptions");
						if(currentUser.getIsExposed())
							gui.init_popUpNotif();
					}
				}
				else
				{
					gui.getLabel("mainMenu_wrongUserPass").setVisible(true);
				}
			}
		}
		/*
		 * Displays the interface that allows the user to check if a username is vacant.
		 */
		else
		if(e.getActionCommand().equals("Register"))
		{
			gui.clearTextFields();
			gui.showInterface("register_usernameAttempt");
		}
		/*
		 * Creates a Confirmation Dialogue that asks the Government Official to input the case
		 * number and the Contact Tracer username.
		 */
		else
		if(e.getActionCommand().equals("Assign"))
		{
			Citizen tempUser;
			int caseNum;
			String username;
			
			panel = new JPanel();
			panel.setLayout(new GridBagLayout());

			gbc.gridx = 0;
			gbc.gridy = 0;
			lbl = new JLabel("Case number ");
			lbl.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lbl, gbc);

			gbc.gridx = 1;
			gbc.gridy = 0;
			txtField_x = new JTextField(4);
			txtField_x.setHorizontalAlignment(JTextField.LEFT);
			panel.add(txtField_x, gbc);

			gbc.gridx = 0;
			gbc.gridy = 1;
			lbl = new JLabel("Contact tracer username ");
			lbl.setHorizontalAlignment(SwingConstants.LEFT);
			panel.add(lbl, gbc);

			gbc.gridx = 1;
			gbc.gridy = 1;
			txtField_y = new JTextField(10);
			panel.add(txtField_y, gbc);
			
			int result = JOptionPane.showConfirmDialog(null, panel, 
													   "Please fill up the form",
													   JOptionPane.OK_CANCEL_OPTION);
			
			if(result == JOptionPane.OK_OPTION)
			{
				if(!txtField_x.getText().equals("") && !txtField_y.getText().equals(""))
				{
					frame = new JFrame("Fail");
					boolean unassigned;
					try{

						username = txtField_y.getText();
						caseNum = Integer.parseInt(txtField_x.getText());
						tempUser = getAccount(username);

						if(tempUser!=null){
							if(tempUser instanceof ContactTracer)
							{
								username = gui.getTextField("mainMenu_username").getText();
								currentUser = getAccount(username);
								if(currentUser != null)
								{
									if(currentUser instanceof GovernmentOfficial)
									{
										unassigned=((GovernmentOfficial) currentUser).assignTracer((ContactTracer) tempUser, caseNum-1);
										if(unassigned){
											updateTracerList(accounts);
											updateUnassignedCases(GovernmentOfficial.getCases());
										}
										else
											JOptionPane.showMessageDialog(frame,"Case is assigned to another tracer");
									}
									else
										System.out.println("Huge bug getting account and assigning tracer");
								}
								else
									System.out.println("Account does not exist");
							}
							else
								JOptionPane.showMessageDialog(frame, "Cannot assign to a Non-Contact Tracer");
						}

					}catch(Exception ec) {
						JOptionPane.showMessageDialog(frame, "Invalid Values");
					}
				}
			}
		}
		/*
		 * Displays the previous interface relative to the current visible interface panel.
		 */
		else
		if(e.getActionCommand().equals("Back"))
		{
			btn = (JButton)e.getSource();
			
			if(btn.getName().equals("showUnassignedCases_back"))
				gui.showInterface("unassignedCases");
			else
			if(btn.getName().equals("assignTracer_back"))
				gui.showInterface("unassignedCases");
			else
			if(btn.getName().equals("showAnalytics_back"))
			{
				gui.showInterface("emptyPanel");
			}
			
		}
		else
		if(e.getActionCommand().equals("OK"))
		{
			btn = (JButton) e.getSource();
			/*
			 * Submits the form that displays the cases in a City with a given Duration.
			 */
			if(btn.getName().equals("cases_CityAndDuration_OK"))
			{
				currentUser = getAccount(gui.getTextField("PersonalDetails_username").getText());
				if(currentUser != null)
				{
					fromMonth = Integer.parseInt((String) gui.getBox("cases_CityAndDuration_fromMonth").getSelectedItem());
					fromDay = Integer.parseInt((String) gui.getBox("cases_CityAndDuration_fromDay").getSelectedItem());
					fromYear = parseInt(gui.getTextField("cases_CityAndDuration_fromYear").getText());
					
					toMonth = Integer.parseInt((String) gui.getBox("cases_CityAndDuration_toMonth").getSelectedItem());
					toDay = Integer.parseInt((String) gui.getBox("cases_CityAndDuration_toDay").getSelectedItem());
					toYear = parseInt_year(gui.getTextField("cases_CityAndDuration_toYear").getText());
					
					city = gui.getTextField("cases_CityAndDuration_City").getText();
					if(checkDate(fromMonth, fromDay, fromYear) && checkDate(toMonth, toDay, toYear))
					{
						frame = new JFrame();

							fromDate = LocalDate.of(fromYear, fromMonth, fromDay);
							toDate = LocalDate.of(toYear, toMonth, toDay);

							tempString = getCases(((GovernmentOfficial) currentUser).Analytics(fromDate, toDate, city));
							
							gui.getTextArea("showAnalytics_txtArea").setText("");
							gui.getTextArea("showAnalytics_txtArea").append(tempString+"\n");

							gui.showInterface("showAnalytics");
					}
					else
					{
						frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "Invalid date combination");
					}
				}
				else
				{
					frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Cannot find current user");
				}

			}
			/*
			 * Submits the form that displays the cases in a given Duration.
			 */
			else
			if(btn.getName().equals("cases_Duration_OK"))
			{
				currentUser = getAccount(gui.getTextField("PersonalDetails_username").getText());
				if(currentUser != null)
				{
					fromMonth = Integer.parseInt((String) gui.getBox("cases_Duration_fromMonth").getSelectedItem());
					fromDay = Integer.parseInt((String) gui.getBox("cases_Duration_fromDay").getSelectedItem());
					fromYear = parseInt(gui.getTextField("cases_Duration_fromYear").getText());
					
					toMonth = Integer.parseInt((String) gui.getBox("cases_Duration_toMonth").getSelectedItem());
					toDay = Integer.parseInt((String) gui.getBox("cases_Duration_toDay").getSelectedItem());
					toYear = parseInt_year(gui.getTextField("cases_Duration_toYear").getText());
					
					if(checkDate(fromMonth, fromDay, fromYear) && checkDate(toMonth, toDay, toYear))
					{
						fromDate = LocalDate.of(fromYear, fromMonth, fromDay);
						toDate = LocalDate.of(toYear, toMonth, toDay);
						
						tempString = getCases(((GovernmentOfficial) currentUser).Analytics(fromDate, toDate));
						
						gui.getTextArea("showAnalytics_txtArea").setText("");
						gui.getTextArea("showAnalytics_txtArea").append(tempString+"\n");
						gui.getTextArea("showAnalytics_txtArea").append("Total Number of Cases: " + ((GovernmentOfficial) currentUser).Analytics(fromDate, toDate).size()+"\n");
						gui.showInterface("showAnalytics");
					}
					else
					{
						frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "Invalid date combination");
					}
				}
				else
				{
					frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Cannot find current user");
				}
			}
			/*
			 * Submits the form that displays the cases in a given City.
			 */
			else
			if(btn.getName().equals("cases_City_OK"))
			{
				currentUser = getAccount(gui.getTextField("PersonalDetails_username").getText());
				if(currentUser != null)
				{
					city = gui.getTextField("cases_City_City").getText();
					
					tempString = getCases(((GovernmentOfficial) currentUser).Analytics(city));
					
					gui.getTextArea("showAnalytics_txtArea").setText("");
					gui.getTextArea("showAnalytics_txtArea").append(tempString+"\n");
					gui.getTextArea("showAnalytics_txtArea").append("Total Number of Cases: " + ((GovernmentOfficial) currentUser).Analytics(city).size()+"\n");
					gui.showInterface("showAnalytics");
				}
				else
				{
					frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Cannot find current user!");
				}
			}
			/*
			 * Determines if the user is a unique username. If unique username, the user is prompted to fill
			 * up a registration form for the creation of his/her account. Else, the user has to enter another username.
			 */
			else
			if(btn.getName().equals("register_usernameAttempt"))
			{
				if(!gui.getTextField("register_usernameAttempt").getText().equals(""))
				{
					
					txtField = gui.getTextField("register_usernameAttempt");
					if(isUserAvailable(txtField.getText()))
					{
						gui.setTextFieldText(gui.getTextField("register_username"), txtField.getText());
						gui.showInterface("Register");
					}
					else
					{
						frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "Username already taken!");
						gui.clearTextFields();
					}

				}
			}
			/*
			 * Submits the form for the account creation. attemp_registrationAccount() will determine if all
			 * fields of text contains valid inputs from the user.
			 */
			else
			if(btn.getName().equals("register_AccountCreationAttempt"))
			{
	
				if(attempt_registerAccount())
				{
					registerAccount();
					frame = new JFrame("Success!");
					
					JOptionPane.showMessageDialog(frame, 
					"\'" + accounts.get(accounts.size() - 1).getUsername() + "\' has been created!");
					
					gui.clearTextFields();
					gui.showInterface("emptyPanel");
				}
				else
				{
					
					lbl = gui.getLabel("register_title");
					lbl.setText("Error creating account!");
					lbl.setForeground(Color.red);
				}

			}
			else
//			if(btn.getName().equals("cases_City"))
//			{
//				
//				tempString = gui.getTextField("cases_City").getText();
//				System.out.println(tempString);
//			}
			/*
			 * The following block of code allows the user to save changes the text fields of his/her personal information. 
			 */
			if(btn.getName().equals("PersonalDetails_editPassword"))
			{
				//if the password is valid
				gui.getPasswordField("PersonalDetails_password").setEditable(false);
				btn.setText("Change");
			}
			else
			if(btn.getName().equals("PersonalDetails_editFirstName"))
			{
				//ERROR if empty
				gui.getTextField("PersonalDetails_firstName").setEditable(false);
				btn.setText("Change");
			}
			else
			if(btn.getName().equals("PersonalDetails_editMiddleName"))
			{
				//ERROR if empty
				gui.getTextField("PersonalDetails_middleName").setEditable(false);
				btn.setText("Change");
			}
			else
			if(btn.getName().equals("PersonalDetails_editSurname"))
			{
				//ERROR if empty
				gui.getTextField("PersonalDetails_surname").setEditable(false);
				btn.setText("Change");
			}
			else
			if(btn.getName().equals("PersonalDetails_editHome"))
			{
				if(!gui.getTextField("PersonalDetails_home").getText().equals(""))
				{
					gui.getTextField("PersonalDetails_home").setEditable(false);
					btn.setText("Change");
				}
				else
				{
					frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Home address cannot be empty");
				}
			}
			else
			if(btn.getName().equals("PersonalDetails_editOffice"))
			{
				gui.getTextField("PersonalDetails_office").setEditable(false);
				btn.setText("Change");
			}
			else
			if(btn.getName().equals("PersonalDetails_editTelephone"))
			{

				gui.getTextField("PersonalDetails_telephone").setEditable(false);
				btn.setText("Change");
			}
			else
			if(btn.getName().equals("PersonalDetails_editEmail"))
			{
				gui.getTextField("PersonalDetails_email").setEditable(false);
				btn.setText("Change");
			}
			/*
			 * Submits the form for account termination. It checks if the account exist. The account cannot be terminated
			 * if the account type is of Citizen.
			 */
			else
			if(btn.getName().equals("terminateAccount_submit"))
			{
				frame = new JFrame();
				try{

					tempUsername = gui.getTextField("terminateAccount_username").getText();
					Citizen tempUser = getAccount(tempUsername);

					tempUsername = gui.getTextField("PersonalDetails_username").getText();
					currentUser = getAccount(tempUsername);

					if(!tempUser.getUsername().equals(currentUser.getUsername()))
					{
						if(tempUser instanceof GovernmentOfficial || tempUser instanceof ContactTracer)
						{
							((GovernmentOfficial) currentUser).terminate_Account(tempUser);
							frame = new JFrame();
							JOptionPane.showMessageDialog(frame, "Account Termination successful");
							gui.getTextField("terminateAccount_username").setText("");
							gui.showInterface("emptyPanel");
						}
						else {
							frame = new JFrame();
							JOptionPane.showMessageDialog(frame, "Cannot demote a Citizen account");

							gui.getTextField("terminateAccount_username").setText("");
						}
					}
					else
						gui.getLabel("terminateAccount_errorText").setVisible(true);

				}catch(Exception exc){
					JOptionPane.showMessageDialog(frame, "Account does not exist");
				}

				
			}
			
		}
		/*
		 * Shows the interface buttons for selecting to view between viewing Unassigned Cases 
		 * or Assigning a Contact Tracer to a case.
		 */
		else
		if(e.getActionCommand().equals("Show Unassigned Cases"))
		{
			gui.showInterface("unassignedCases");
		}
		/*
		 * Displays the text area with a list of Unassigned Cases with the updateUnassignedCases() method.
		 */
		else
		if(e.getActionCommand().equals("Unassigned Cases"))
		{
			updateUnassignedCases(GovernmentOfficial.getCases());
			gui.showInterface("showUnassignedCases");
		}
		/*
		 * Displays the text area with a list of Unassigned Cases with the updateUnassignedCases() method,
		 * and allows the Government Official to submit a form for assigning a Contact Tracer to a case number.
		 */
		else
		if(e.getActionCommand().equals("Assign Tracer"))
		{
			updateUnassignedCases(GovernmentOfficial.getCases());
			updateTracerList(accounts);
			gui.showInterface("assignTracer");
		}
		/*
		 * Displays a pop-up window that will ask if the account to be promoted to Government Official exists or not.
		 * If it does exist, the Government Official is prompted to enter the username of the existing account.
		 */
		else
		if(e.getActionCommand().equals("Create Government Official"))
		{
			int reply = JOptionPane.showConfirmDialog(null, "Does the account exist?", 
													  "Create Government Official", JOptionPane.YES_NO_OPTION);
			
			if(reply == JOptionPane.YES_OPTION)
			{
				gui.showInterface("createOfficial_ExistingAccount");
			
			}
			else
			if(reply == JOptionPane.NO_OPTION)
			{	
				gui.showInterface("createOfficial_NoAccount");

			}
		}
		/*
		 * Displays a pop-up window that will ask if the account to be promoted to Contact Tracer exists or not.
		 * If it does exist, the Government Official is prompted to enter the username of the existing account.
		 */
		else
		if(e.getActionCommand().equals("Create Contact Tracer"))
		{
			int reply = JOptionPane.showConfirmDialog(null, "Does the account exist?", 
													  "Create Contact Tracer", JOptionPane.YES_NO_OPTION);

			if(reply == JOptionPane.YES_OPTION)
			{
				gui.showInterface("createTracer_ExistingAccount");
			}
			else
			if(reply == JOptionPane.NO_OPTION)
			{
				gui.showInterface("createTracer_NoAccount");
				System.out.println("Promote from scratch [DEBUG]");
			}
		}
		/*
		 * Displays the submission form for a Government Official to submit form to input the account to be
		 * terminated. 
		 */
		if(e.getActionCommand().equals("Terminate Account"))
		{
			gui.showInterface("terminateAccount");
		}
		else
		if(e.getActionCommand().equals("Submit"))
		{
			btn = (JButton) e.getSource();
			/*
			 * Submits the form that will submit the Establishment Code and the Date input.
			 */
			if(btn.getName().equals("checkIn_submit"))
			{
				currentUser = getAccount(gui.getTextField("PersonalDetails_username").getText());
				if(currentUser != null)
				{
					if(!gui.getTextField("checkIn_establishmentCode").getText().equals(""))
					{
						DateTimeFormatter df, tf;
						fromMonth = Integer.parseInt((String) gui.getBox("checkIn_months").getSelectedItem());
						fromDay = Integer.parseInt((String) gui.getBox("checkIn_days").getSelectedItem());
						fromYear = parseInt(gui.getTextField("checkIn_years").getText());
						
						time = gui.getTextField("checkIn_hours").getText() + ":" +
							   gui.getTextField("checkIn_minutes").getText();
						
						if(checkDate(fromMonth, fromDay, fromYear) && !(gui.getTextField("checkIn_hours").getText().equals("")) &&
						   !(gui.getTextField("checkIn_minutes").getText().equals("")))
						{
							frame = new JFrame("Success");
							boolean duplicate;
							tf = DateTimeFormatter.ofPattern("HH:mm");
							try{
								duplicate=currentUser.checkIn(gui.getTextField("checkIn_establishmentCode").getText(), LocalDate.of(fromYear, fromMonth, fromDay),
										LocalTime.parse(time, tf));
								if(!duplicate)
									JOptionPane.showMessageDialog(frame, "Establishment Code, Date, and Time recorded");
								else
									JOptionPane.showMessageDialog(frame, "Entry is duplicated");
							}catch (Exception exc){
								JOptionPane.showMessageDialog(frame, "Illegal Date or Time Input");
							}

							
							frame = new JFrame("Success");


							gui.showInterface("emptyPanel");
						}
						else
						{
							frame = new JFrame("Fail");
							JOptionPane.showMessageDialog(frame, "Invalid inputs for Date and/or Time");
							
							gui.getBox("checkIn_months").setSelectedIndex(0);
							gui.getBox("checkIn_days").setSelectedIndex(0);
							gui.getTextField("checkIn_years").setText("");;
							gui.getTextField("checkIn_establishmentCode").setText("");
							gui.getTextField("checkIn_years").setText("");
						}
					}
				}
				else
					System.out.println("[DEBU] cannot find account for Checking In");
			}
			/*
			 * Attempts to promote a currently existing Citizen to a Government Official. An error will display
			 * if the account does not exist.
			 */
			else
			if(btn.getName().equals("createOfficial_ExistingAccount_submit"))
			{
				tempUsername = gui.getTextField("createOfficial_ExistingAccount_username").getText();
				Citizen tempUser = getAccount(tempUsername);
				
				currentUser = getAccount(gui.getTextField("PersonalDetails_username").getText());
				
				if(tempUser != null)
				{
					if(tempUser instanceof GovernmentOfficial || tempUser instanceof ContactTracer)
					{
						frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "User is already either a Government Official or Contact Tracer");
						
						gui.getTextField("createOfficial_ExistingAccount_username").setText("");
					}
					else
					{
						((GovernmentOfficial) currentUser).create_GovOfficial(tempUser);
						frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "Promotion successful");
						
						gui.getTextField("createOfficial_ExistingAccount_username").setText("");
						gui.showInterface("emptyPanel");
					}
				}
				else
				{
					frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "User " + tempUsername + " does not exist");
					
					gui.getTextField("createOfficial_ExistingAccount_username").setText("");
				}
			}
			/*
			 * Attempts to promote a non-existing Citizen to a Government Official. A random password is generated and 
			 * is displayed as popup to the user. An error will display if the account username is not available.
			 */
			else
			if(btn.getName().equals("createOfficial_NoAccount_submit"))
			{
				tempUsername = gui.getTextField("createOfficial_NoAccount_username").getText();
				if(isUserAvailable(tempUsername))
				{
					tempPassword = generatePassword();
					accounts.add(new GovernmentOfficial(tempUsername, tempPassword));
					
					frame = new JFrame();
					JOptionPane.showMessageDialog(frame, 
												  "Username: " + tempUsername + ", Password: " + tempPassword);
					
					gui.getTextField("createOfficial_NoAccount_username").setText("");
					gui.showInterface("emptyPanel");
				}
				else
				{
					frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Username already taken");
					
					gui.getTextField("createOfficial_NoAccount_username").setText("");
				}
			}
			/*
			 * Attempts to promote a currently existing Citizen to a Contact Tracer. An error will display
			 * if the account does not exist.
			 */
			else
			if(btn.getName().equals("createTracer_ExistingAccount_submit"))
			{
				tempUsername = gui.getTextField("createTracer_ExistingAccount_username").getText();
				Citizen tempUser = getAccount(tempUsername);
				
				currentUser = getAccount(gui.getTextField("PersonalDetails_username").getText());
				
				if(tempUser != null)
				{
					if(tempUser instanceof GovernmentOfficial || tempUser instanceof ContactTracer)
					{
						frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "User is already either a Government Official or Contact Tracer");
						
						gui.getTextField("createTracer_ExistingAccount_username").setText("");
					}
					else
					{
						((GovernmentOfficial) currentUser).create_ContactTracer(tempUser);
						frame = new JFrame();
						JOptionPane.showMessageDialog(frame, "Promotion successful");
						
						gui.getTextField("createTracer_ExistingAccount_username").setText("");
						gui.showInterface("emptyPanel");
					}
				}
				else
				{
					frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "User " + tempUsername + " does not exist");
					
					gui.getTextField("createTracer_ExistingAccount_username").setText("");
				}
			}
			/*
			 * Attempts to promote a non-existing Citizen to a Contact Tracer. A random password is generated and 
			 * is displayed as popup to the user. An error will display if the account username is not available.
			 */
			else
			if(btn.getName().equals("createTracer_NoAccount_submit"))
			{
				tempUsername = gui.getTextField("createTracer_NoAccount_username").getText();
				if(isUserAvailable(tempUsername))
				{
					tempPassword = generatePassword();
					accounts.add(new ContactTracer(tempUsername, tempPassword));
					
					frame = new JFrame();
					JOptionPane.showMessageDialog(frame, 
												  "Username: " + tempUsername + ", Password: " + tempPassword);
					
					gui.getTextField("createTracer_NoAccount_username").setText("");
					gui.showInterface("emptyPanel");
				}
				else
				{
					frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Username already taken");
					
					gui.getTextField("createTracer_NoAccount_username").setText("");
				}				
			}
			/*
			 * Submits the form for the Citizen to declare themselves positive. The Date and Time is retrieved
			 * from his/her input.
			 */
			else
			if(btn.getName().equals("reportPositive_submit"))
			{
				currentUser = getAccount(gui.getTextField("PersonalDetails_username").getText());
				if(currentUser != null)
				{
					if(!currentUser.getIsPositive())
					{
						//TODO
						fromMonth = Integer.parseInt((String) gui.getBox("reportPositive_months").getSelectedItem());
						fromDay =Integer.parseInt((String) gui.getBox("reportPositive_days").getSelectedItem());
						fromYear = parseInt_year(gui.getTextField("reportPositive_year").getText());
						
						currentUser.declarePositive();
						GovernmentOfficial.addCase(currentUser, LocalDate.of(fromYear, fromMonth, fromDay),-1,"P","000");
						
						
						frame = new JFrame("Success");
						JOptionPane.showMessageDialog(frame, "Date and Time has been noted");
						
						gui.showInterface("emptyPanel");
					}
					else
					{
						frame = new JFrame("Fail");
						JOptionPane.showMessageDialog(frame, "You already have tested positive");
					}
				}
				else
					System.out.println("Error searching for account for Report Positive Test Result");
			}
			/*
			 * Attempts to trace a case given the username as input.
			 */
			else
			if(btn.getName().equals("traceCase_submit"))
			{
				frame = new JFrame();
				try{
					String caseNum = gui.getTextField("traceCase_caseNum").getText();
					int casenum;
					GovernmentOfficial temp = null;
					JFrame frame1 = new JFrame();
					boolean process=false;
					currentUser = getAccount(gui.getTextField("PersonalDetails_username").getText());
					for(int i =0;i<accounts.size();i++)
						if(accounts.get(i) instanceof GovernmentOfficial){
							temp = (GovernmentOfficial) accounts.get(i);
							break;
						}
					casenum = Integer.parseInt(caseNum);

					process = ((ContactTracer)currentUser).traceCase(casenum,temp);
					if(process)
						JOptionPane.showMessageDialog(frame1, "Case Number "+ Integer.parseInt(caseNum) +" has been traced");
					else
						JOptionPane.showMessageDialog(frame1, "Case Number is either unassigned or assigned to another tracer");
				}catch(Exception ex){
					JOptionPane.showMessageDialog(frame,"INVALID VALUE");
				}
			}
		}
		/*
		 * Allows the user to input the Establishment Code and Date with time.
		 */
		else
		if(e.getActionCommand().equals("Check In"))
		{
			if(isDoneUpdating())
			{
				gui.getTextField("checkIn_establishmentCode").setText("");
				gui.getTextField("checkIn_years").setText("");
				gui.showInterface("checkIn");
			}
			else
			{
				frame = new JFrame("Fail");
				JOptionPane.showMessageDialog(frame, "Please save changes to your Account Information");
			}
		}
		/*
		 * Displays the interface that allows the user to view and make changes to his/her Profile Information.
		 */
		else
		if(e.getActionCommand().equals("Update Profile Information"))
		{
			gui.showInterface("PersonalDetails");
		}
		/*
		 * Displays the interface that allows the user to declare themselves positive.
		 */
		else
		if(e.getActionCommand().equals("Report Positive Test Result"))
		{
			gui.showInterface("reportPositive");
		}
		/*
		 * Displays the account privileges for Government Officials.
		 */
		else
		if(e.getActionCommand().equals("Government Official Options"))
		{
			gui.showMenu("OfficialMenu");
		}
		/*
		 * Displays the cases with the Case number and the username. It also shows the status
		 * of trace casing under the supervision of its corresponding Contact Tracer.
		 */
		else
		if(e.getActionCommand().equals("Show Contact Tracing Updates"))
		{
			gui.showInterface("CTupdates");
			showCTupdates();
		}
		/*
		 * Displays the options for Government Official to "narrow-down" the Unassigned cases
		 * within a City and Duration, Duration, or City.
		 */
		else
		if(e.getActionCommand().equals("Analytics"))
		{
			gui.showInterface("emptyPanel");
			gui.showMenu("analytics");
		}
		/*
		 * Displays the form where a Government Official can input the city.
		 */
		else
		if(e.getActionCommand().equals("Cases within City"))
		{	
			gui.getTextField("cases_City_City").setText("");
			gui.showInterface("cases_City");
		}
		/*
		 * Displays the form where a Government Official can input the city and the lowerbound and upperbound dates.
		 */
		else
		if(e.getActionCommand().equals("Cases within City and Duration"))
		{
			
			gui.getBox("cases_CityAndDuration_fromMonth").setSelectedIndex(0);
			gui.getBox("cases_CityAndDuration_fromDay").setSelectedIndex(0);
			gui.getTextField("cases_CityAndDuration_fromYear").setText("");
			
			gui.getBox("cases_CityAndDuration_toMonth").setSelectedIndex(0);
			gui.getBox("cases_CityAndDuration_toDay").setSelectedIndex(0);
			gui.getTextField("cases_CityAndDuration_toYear").setText("");
			
			gui.getTextField("cases_CityAndDuration_City").setText("");
			
			gui.showInterface("cases_CityAndDuration");
		}
		/*
		 * Displays the form where a Government Official can input the lowerbound and upperbound dates.
		 */
		else
		if(e.getActionCommand().equals("Cases within Duration"))
		{
			
			gui.getBox("cases_Duration_fromMonth").setSelectedIndex(0);
			gui.getBox("cases_Duration_fromDay").setSelectedIndex(0);
			gui.getTextField("cases_Duration_fromYear").setText("");
			
			gui.getBox("cases_Duration_toMonth").setSelectedIndex(0);
			gui.getBox("cases_Duration_toDay").setSelectedIndex(0);
			gui.getTextField("cases_Duration_toYear").setText("");
			
			gui.showInterface("cases_Duration");
		}
		/*
		 * Displays the account privileges for Contact Tracers.
		 */
		else
		if(e.getActionCommand().equals("Contact Tracer Options"))
		{
			gui.showMenu("TracerMenu");
		}
		/*
		 * Displays the cases assigned to the Contact Tracer
		 */
		else
		if(e.getActionCommand().equals("Show Cases"))
		{
			gui.showInterface("showAssignedCases");
			currentUser = getAccount(gui.getTextField("PersonalDetails_username").getText());
			showAssignedCases((ContactTracer)currentUser);
		}
		/*
		 * Displays the list of cases that has not gone Contact Tracing yet.
		 */
		else
		if(e.getActionCommand().equals("Trace Specific Case"))
		{
			gui.showInterface("traceCase");
		}
		/*
		 * Sends a notification to all Citizen accounts that might have been exposed in certain
		 * Establishment codes and in a particular time period.
		 */
		else
		if(e.getActionCommand().equals("Inform Citizens Possibly Exposed"))
		{
			frame = new JFrame();
			currentUser = getAccount(gui.getTextField("PersonalDetails_username").getText());
			((ContactTracer)currentUser).informCitizen();
			JOptionPane.showMessageDialog(frame, "Exposed Citizens have been informed");
		}
		/*
		 * Displays the account privileges for Citizens.
		 */
		else
		if(e.getActionCommand().equals("Citizen Options"))
		{
			gui.showMenu("CitizenMenu");
		}
		/*
		 * Allows a Citizen to edit and make changes to their Profile Information.
		 */
		else
		if(e.getActionCommand().equals("Change"))
		{
			btn = (JButton)e.getSource();
			//btn change name to OK
			if(btn.getName().equals("PersonalDetails_editUsername"))
			{
				gui.getTextField("PersonalDetails_username").setEditable(true);
				btn.setText("OK");
			}
			else
			if(btn.getName().equals("PersonalDetails_editPassword"))
			{
				gui.getPasswordField("PersonalDetails_password").setEditable(true);
				btn.setText("OK");
			}
			else
			if(btn.getName().equals("PersonalDetails_editFirstName"))
			{
				gui.getTextField("PersonalDetails_firstName").setEditable(true);
				btn.setText("OK");
			}
			else
			if(btn.getName().equals("PersonalDetails_editMiddleName"))
			{
				gui.getTextField("PersonalDetails_middleName").setEditable(true);
				btn.setText("OK");
			}
			else
			if(btn.getName().equals("PersonalDetails_editSurname"))
			{
				gui.getTextField("PersonalDetails_surname").setEditable(true);
				btn.setText("OK");
			}
			else
			if(btn.getName().equals("PersonalDetails_editHome"))
			{
				gui.getTextField("PersonalDetails_home").setEditable(true);
				btn.setText("OK");
			}
			else
			if(btn.getName().equals("PersonalDetails_editOffice"))
			{
				gui.getTextField("PersonalDetails_office").setEditable(true);
				btn.setText("OK");
			}
			else
			if(btn.getName().equals("PersonalDetails_editTelephone"))
			{
				gui.getTextField("PersonalDetails_telephone").setEditable(true);
				btn.setText("OK");
			}
			else
			if(btn.getName().equals("PersonalDetails_editEmail"))
			{
				gui.getTextField("PersonalDetails_email").setEditable(true);
				btn.setText("OK");
			}
		}
		/*
		 * Logs the user out of the program. Their profile information is also saved.
		 */
		else
		if(e.getActionCommand().equals("Logout"))
		{

			if(gui.getCurrentMenu().equals("OfficialOptions") || 
			   gui.getCurrentMenu().equals("TracerOptions") ||
			   gui.getCurrentMenu().equals("CitizenOptions"))
			{
				currentUser = getAccount(gui.getTextField("PersonalDetails_username").getText());
				if(currentUser != null)
				{
					updateUserDetails(currentUser);
					
					gui.clearTextFields();
					gui.displayPreviousMenu();
					gui.showInterface("emptyPanel");
					
					if(currentUser.getIsExposed())
						gui.init_popUpNotif();
				}
				else
					System.out.println("SAVING ERROR HAS OCCURED [Exit -> mainMenu]");
			}
		}
		else
		if(e.getActionCommand().equals("Exit"))
		{
			if(gui.getCurrentMenu().equals("analytics"))
			{
				gui.displayPreviousMenu();
				gui.showInterface("emptyPanel");
			}
			else
			if(gui.getCurrentMenu().equals("OfficialMenu") ||
			   gui.getCurrentMenu().equals("TracerMenu") ||
			   gui.getCurrentMenu().equals("CitizenMenu"))
			{
				if(isDoneUpdating())
				{
					currentUser = getAccount(gui.getTextField("PersonalDetails_username").getText());
					if(currentUser != null)
					{
						if(currentUser instanceof GovernmentOfficial)
						{
							gui.showMenu("OfficialOptions");
							gui.showInterface("emptyPanel");

						}
						else
						if(currentUser instanceof ContactTracer)
						{
							gui.showMenu("TracerOptions");
							gui.showInterface("emptyPanel");

						}
						else
						{
							gui.showMenu("CitizenOptions");
							gui.showInterface("emptyPanel");
						}
					}
					else
						System.out.println("Displaying specific Menu Options failed!! [DEBUG]");
				}
				else
				{
					frame = new JFrame("Fail");
					JOptionPane.showMessageDialog(frame, "Please save changes to your Account Information");
				}
			}
			/*
			 * The program will perform file writing in order to store text files that is used for future 
			 * use. It stores information such as the username and password, the user's visit records and the
			 * Corona cases.
			 */
			else
			if(gui.getCurrentMenu().equals("mainMenu"))
			{
				Citizen admin = getAccount("Admin2020");
				if(admin != null)
				{
					accounts.remove(admin);
					writeFile();
					System.exit(0);
				}
				else
				{
					writeFile();
					System.exit(0);
				}
			}
		}
		
	}
	/**
	 * Retrieves the Citizen object that belongs to the user with the same username
	 * and password.
	 * 
	 * @param username - the username of the account that will check ownership.
	 * @param password - the password of the account that will verify ownership.
	 * @return the Citizen object that allows the user to use its Class Methods.
	 */
	public Citizen getAccount(String username, String password)
	{
		for(Citizen acc: accounts)
			if(acc.getUsername().equals(username) && 
			   acc.getPassword().equals(password))
				return acc;
		
		return null;
	}
	/**
	 * Retrieves the Citizen object that belongs to the user with the same username.
	 * 
	 * @param username - the username of the account that will check ownership.
	 * @return the Citizen object that allows the user to use its Class Methods.
	 */
	public Citizen getAccount(String username)
	{
		for(Citizen acc: accounts)
			if(acc.getUsername().equals(username))
				return acc;
		
		return null;
	}
	/**
	 * Verifies if the specified username is vacant or not.
	 * 
	 * @param username - the username that is checked to be vacant.
	 * @return returns true if the username is free, false if a Citizen owns it.
	 */
	public boolean isUserAvailable(String username)
	{
		for(int i = 0; i < accounts.size(); i++)
			if(accounts.get(i).getUsername().equals(username))
				return false;
		
		return true;
	}
	/**
	 * Updates a text area to contain a list of Contact Tracers.
	 * 
	 * @param accounts - the ArrayList of Citizens that might contain Contact Tracers.
	 */
	public void updateTracerList(ArrayList<Citizen> accounts)
	{
		JTextArea txtAreas = gui.getTextArea("ContactTracers_txtArea");
		String tempString = "";
		
		for(Citizen user: accounts)
			if(user instanceof ContactTracer)
				tempString = tempString + user.getUsername() + "\n";
		
		txtAreas.setText(tempString);
	}
	/**
	 * Updates a text area to contain a list of Unassigned Cases.
	 * 
	 * @param cases - the ArrayList of cases that might be unassigned to a Contact Tracer.
	 */
	public void updateUnassignedCases(ArrayList<CoronaCase> cases)
	{
		JTextArea txtAreas = gui.getTextArea("showUnassignedCases_txtArea");
		txtAreas.setText(" ");
		String tempString = "";
		for(CoronaCase coronaCase: cases)
			if(!coronaCase.isAssignedCase())
				tempString = tempString + coronaCase.toString() + "\n";
		
		txtAreas.append(tempString);
		
		txtAreas = gui.getTextArea("UnassignedCases_txtArea");
		txtAreas.setText(tempString);
	}
	/**
	 * Updates the information of the currently logged in user.
	 * 
	 * @param currentUser - the Citizen object that has logged in to the program.
	 */
	public void readUserDetails(Citizen currentUser)
	{
		PersonalDetails details = currentUser.getDetails();
		
		gui.getTextField("PersonalDetails_username").setText(currentUser.getUsername());
		gui.getPasswordField("PersonalDetails_password").setText(currentUser.getPassword());
		
		gui.getTextField("PersonalDetails_firstName").setText(details.getFirstName());
		gui.getTextField("PersonalDetails_middleName").setText(details.getMiddleName());
		gui.getTextField("PersonalDetails_surname").setText(details.getSurname());
		
		gui.getTextField("PersonalDetails_home").setText(details.getHome());
		gui.getTextField("PersonalDetails_office").setText(details.getOffice());
		gui.getTextField("PersonalDetails_telephone").setText(Integer.toString(details.getTelephone()));
		gui.getTextField("PersonalDetails_email").setText(details.getEmail());
	}
	/**
	 * Updates a text area to contain a list of Assigned Cases assigned to a particular
	 * Contact Tracer.
	 * 
	 * @param user - the Contact Tracer that possesses the list of CoronaCases. 
	 */
	public void showAssignedCases(ContactTracer user)
	{
		ArrayList<CoronaCase> assignedCases= user.getAssignedCases();
		JTextArea txtArea = gui.getTextArea("showAssignedCases_txtArea");
		txtArea.setText(" ");

		if(assignedCases==null)
			txtArea.append("[#] You have no assigned cases [#]");
		else {
			for(int i=0;i<assignedCases.size();i++)
				txtArea.append("["+(i+1)+"] Case Number: " + assignedCases.get(i).getCaseNumber() +" || Username: " + assignedCases.get(i).getUsername() +" || Date Recorded: "+ assignedCases.get(i).getDate() +" || Status: "+assignedCases.get(i).getStatus()+"\n" );
		}
	}
	/**
	 * Displays the list of cases that has not been through Contact Tracing into a text area.
	 */
	public void showCTupdates()
	{
		ArrayList<CoronaCase> allCases = GovernmentOfficial.getCases();
		JTextArea txtArea = gui.getTextArea("CTupdates_txtArea");
		txtArea.setText(" ");

		if(allCases==null)
			txtArea.append("[#] There are no active Coronavirus Cases [#]");
		else {
			for(int i=0;i<allCases.size();i++)
				txtArea.append("["+(i+1)+"] Case Number: " + allCases.get(i).getCaseNumber() +" || Username: " + allCases.get(i).getUsername() +" || Date Recorded: "+ allCases.get(i).getDate() +" || Status: "+ allCases.get(i).getStatus()+"\n" );
		}
	}
	/**
	 * Returns a String with the list of all CoronaCases in the parameter.
	 * 
	 * @param CoronaCases - the ArrayList of cases from the GovernmentOfficial.
	 * @return String with the list of all CoronaCases.
	 */
	public String getCases(ArrayList<CoronaCase> CoronaCases)
	{
		StringBuilder tempString = new StringBuilder();
		
		for(CoronaCase cases: CoronaCases)
			tempString.append(cases.toString()).append("\n");
		
		return tempString.toString();
	}
	/**
	 * Retrieves all information from the panel in the Registration form and creates a new account.
	 */
	public void registerAccount()
	{
		String username, password;
		String firstName, middleName, surname;
		String home, office, email;
		int telephone;
		
		username = gui.getTextField("register_username").getText();
		password = new String(gui.getPasswordField("register_password").getPassword());
		
		firstName = gui.getTextField("register_firstname").getText();
		middleName = gui.getTextField("register_middlename").getText();
		surname = gui.getTextField("register_surname").getText();
		
		home = gui.getTextField("register_homeAddress").getText();
		office = gui.getTextField("register_officeAddress").getText();
		telephone = parseInt(gui.getTextField("register_telephone").getText());
		email = gui.getTextField("register_email").getText();
		
		accounts.add(new Citizen(username, password));
		accounts.get(accounts.size() -1).getDetails().updateAllDetails(firstName, middleName, surname,
																	   home, office, telephone, email);
	}
	/**
	 * Updates the Profile Information of the currently logged-in user whenever the user
	 * logs out or makes changes to the profile.
	 * 
	 * @param currentUser - the currently logged-in user of the program.
	 */
	public void updateUserDetails(Citizen currentUser)
	{
		PersonalDetails details = currentUser.getDetails();
		String pass, first, middle, last,
			   home, office, telephone, email;
		
		pass = new String(gui.getPasswordField("PersonalDetails_password").getPassword());
		currentUser.setPassword(pass);
		
		first = gui.getTextField("PersonalDetails_firstName").getText();
		middle = gui.getTextField("PersonalDetails_middleName").getText();
		last = gui.getTextField("PersonalDetails_surname").getText();
		
		home = gui.getTextField("PersonalDetails_home").getText();
		office = gui.getTextField("PersonalDetails_office").getText();
		telephone = gui.getTextField("PersonalDetails_telephone").getText();
		email = gui.getTextField("PersonalDetails_email").getText();
		
		details.updateAllDetails(first, middle, last, home, office, parseInt(telephone), email);
		
	}
	/**
	 * Rejects the submission form of Registration if any of the text fields are empty.
	 * 
	 * @return returns true if the text fields are not empty, false if otherwise.
	 */
	public boolean attempt_registerAccount()
	{
		JFrame errorFrame;
		boolean bFirst = true, bMiddle = true, bLast = true, bHome = true, 
				bOffice = true, bTelephone = true, bEmail = true;
		
		if(gui.getTextField("register_firstname").getText().equals(""))
		{
			errorFrame = new JFrame("Error");
			JOptionPane.showMessageDialog(errorFrame, "Please fill up First Name");
			bFirst = false;
		}
		if(gui.getTextField("register_middlename").getText().equals(""))
		{
			errorFrame = new JFrame("Error");
			JOptionPane.showMessageDialog(errorFrame, "Please fill up Middle Name");
			bMiddle = false;
		}
		if(gui.getTextField("register_surname").getText().equals(""))
		{
			errorFrame = new JFrame("Error");
			JOptionPane.showMessageDialog(errorFrame, "Please fill up Surname");
			bLast = false;
		}
		if(gui.getTextField("register_homeAddress").getText().equals(""))
		{
			errorFrame = new JFrame("Error");
			JOptionPane.showMessageDialog(errorFrame, "Please fill up Home Address");
			bHome = false;
		}
		if(gui.getTextField("register_officeAddress").getText().equals(""))
		{
			errorFrame = new JFrame("Error");
			JOptionPane.showMessageDialog(errorFrame, "Please fill up Office Address");
			bOffice = false;
		}
		if(gui.getTextField("register_telephone").getText().equals(""))
		{
			errorFrame = new JFrame("Error");
			JOptionPane.showMessageDialog(errorFrame, "Please fill up Telephone");
			bTelephone = false;
		}
		if(gui.getTextField("register_email").getText().equals(""))
		{
			errorFrame = new JFrame("Error");
			JOptionPane.showMessageDialog(errorFrame, "Please fill up Email");
			bEmail = false;
		}
		
		return bFirst && bMiddle && bLast && bHome && bOffice && bTelephone && bEmail;
	}
	/**
	 * Rejects the user from switching interfaces or logging out of the program if the user
	 * has not saved the changes in his/her Profile Information.
	 * 
	 * @return returns true if all text fields are non-editable, false if otherwise.
	 */
	public boolean isDoneUpdating()
	{
		boolean pass = true, first = true, middle = true, surname = true,
				home = true, office = true, telephone = true, email = true;
		
		if(gui.getPasswordField("PersonalDetails_password").isEditable())
			pass = false;
		if(gui.getTextField("PersonalDetails_firstName").isEditable())
			first = false;
		if(gui.getTextField("PersonalDetails_middleName").isEditable())
			middle = false;
		if(gui.getTextField("PersonalDetails_surname").isEditable())
			surname = false;
		if(gui.getTextField("PersonalDetails_home").isEditable())
			home = false;
		if(gui.getTextField("PersonalDetails_office").isEditable())
			office = false;
		if(gui.getTextField("PersonalDetails_telephone").isEditable())
			telephone = false;
		if(gui.getTextField("PersonalDetails_email").isEditable())
			email = false;
		
		return pass && first && middle && surname && home && office && telephone && email;
	}
	/**
	 * Determines if the password from a JPasswordField is a valid. A valid password is a 
	 * password that is 6 characters long with at least one special character.
	 * 
	 * @param pass - the char[] retrieved from JPasswordField.getPassword() method.
	 * @return returns true if the password is valid, false if otherwise.
	 */
	public boolean isValidPassword(char[] pass)
	{
		String temp = new String(pass);
		
		boolean hasLetters = false, hasSpecialChar = false;
		if(temp.length() > 5)
		{
			for(int i = 0; i < temp.length(); i++)
				if(Character.isLetter(temp.charAt(i)))
					hasLetters = true;
				else
				if(!Character.isWhitespace(temp.charAt(i)))
					hasSpecialChar = true;
		}
		
		return hasLetters && hasSpecialChar;
	}
	/**
	 * Determines if the password from a JTextField is a valid. A valid password is a 
	 * password that is 6 characters long with at least one special character.
	 * 
	 * @param pass - the String from a text field.
	 * @return returns true if the password is valid, false if otherwise.
	 */
	public boolean isValidPassword(String pass)
	{
		boolean hasLetters = false, hasSpecialChar = false;
		if(pass.length() > 5)
		{
			for(int i = 0; i < pass.length(); i++)
				if(Character.isLetter(pass.charAt(i)))
					hasLetters = true;
				else
				if(!Character.isWhitespace(pass.charAt(i)))
					hasSpecialChar = true;
		}
		
		return hasLetters && hasSpecialChar;
	}
	/**
	 * Generates a random password anywhere from 6 to 13 characters.
	 * 
	 * @return the password randomly generated. It is for the creation of a Government Official or Contact Tracers.
	 * 
	 */
	public String generatePassword()
	{
		String alphabet = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz";
		String special = "1!2@3#4$5%6^7&8*9(0)-+.,<>/?[}{]";
		int aN = alphabet.length();
		int sN = special.length();
		int i,j;

		//Generates char array with a random size, meaning random length of pass with at least six characters
		Random rand = new Random();
		int randomsize= rand.nextInt(13-6+1)+6;
		char[] pass = new char[randomsize];

		//Generates how many indices are NOT letters
		int randomSpecialchar = rand.nextInt(randomsize-1+1)+1;
		int[] position = new int[randomSpecialchar];

		//Generates random indices with respect to the positions of the char array
		for(i=0;i<position.length;i++)
			position[i] = rand.nextInt(randomsize);

		//Generates the password itself
		for(i=0;i<pass.length;i++)
			pass[i]=alphabet.charAt(rand.nextInt(aN));

		for(j=0;j< position.length;j++)
			pass[position[j]]=special.charAt((rand.nextInt(sN)));

		return new String(pass);
	}
	/**
	 * Checks the date if the date is a valid combination.
	 * 
	 * @param month - the month of the year
	 * @param day - the day of a month
	 * @param year - the year
	 * @return returns true if the date combination is correct, false if otherwise.
	 */
	public boolean checkDate(int month, int day, int year)
	{
		if(month == 4 || month == 6 || month == 9 || month == 11)
		{
			if(day > 30)
				return false;
		}
		else
		if(month == 2)
		{
			if(year % 4 != 0)
				if(day != 0)
					return false;
		}
		
		return true;
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
			System.out.println("[ParseInt Error]: returned number 0 instead.");
			return 0;
		}
	}
	/**
	 * Attempts to parse a String input to its int value. It will return 1970 if the user makes an invalid
	 * non-number input as String.
	 * 
	 * @param num - the input String that will be attempted to be converted into an int.
	 * @return the specified int value of the String is returned. The int value 1970 is returned if the String is not a valid input. 
	 */
	public int parseInt_year(String year)
	{
		try
		{
			return Integer.parseInt(year);
		}
		catch(NumberFormatException e)
		{
			return 1970;
		}
	}
	/**
	 * Reads Master.txt, PositiveCases.txt, VisitRecords.txt, and the *.act files that belong to a 
	 * particular user. 
	 * 
	 * @param accounts - the ArrayList of accounts for the information from the files to be copied into.
	 */
	public static void readFile(ArrayList<Citizen> accounts)
	{
		PersonalDetails tempDetails = null;

		String line = null;
		String username, role;
		String password, first, middle, surname, home, office, email;
		String caseNumber, date, tracerUsername, status, estCode, time;
		String[] positiveCases;
		String[] visitRecords;
		Citizen citizen = null;
		int phone;

		try
		{
			//opens Master for reading
			BufferedReader br1 = new BufferedReader(new FileReader("Master.txt"));
			while((line = br1.readLine()) != null)
			{
				//each line it reads indicates the username and the account type
				String[] tempString1 = line.split(" ");
				username = tempString1[0];
				role = tempString1[1];

				try
				{
					//opens the file with the corresponding username as its file name
					BufferedReader br2 = new BufferedReader(new FileReader(username + ".txt"));

					password = br2.readLine();
					String[] tempString2 = br2.readLine().split(",");

					first = tempString2[0];
					middle = tempString2[1];
					surname = tempString2[2];
					home = br2.readLine().replace("HOME:", "");
					office = br2.readLine().replace("OFFICE:", "");
					phone = Integer.parseInt(br2.readLine().replace("PHONE:", ""));
					email = br2.readLine().replace("EMAIL:", "");
					br2.close();

					//initializes the accounts depending on their role and updates the details
					if(role.equals("official"))
					{
						accounts.add(new GovernmentOfficial(username, password));
						tempDetails = accounts.get(accounts.size() - 1).getDetails();

						tempDetails.updateAllDetails(first, middle, surname, home,
								office, phone, email);
					}
					else
					if(role.equals("tracer"))
					{
						accounts.add(new ContactTracer(username, password));
						tempDetails = accounts.get(accounts.size() - 1).getDetails();

						tempDetails.updateAllDetails(first, middle, surname, home,
								office, phone, email);
					}
					else
					{
						accounts.add(new Citizen(username, password));
						tempDetails = accounts.get(accounts.size() - 1).getDetails();

						tempDetails.updateAllDetails(first, middle, surname, home, office, phone, email);
					}
				}
				catch(Exception e)
				{
					System.out.println("Read User accoount ERROR: " + e.toString());
				}
			}

			br1.close();
		}
		catch(Exception e)
		{
			System.out.println("MasterList ERROR: " + e.toString());
		}
		try
		{
			BufferedReader br3 = new BufferedReader(new FileReader("PositiveCases.txt"));
			DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			ContactTracer tracer = null;
			ArrayList<CoronaCase> Cases;

			while((line = br3.readLine())!=null){
				positiveCases=line.split(" ");

				caseNumber = positiveCases[0];
				username = positiveCases[1];
				date = positiveCases[2];
				tracerUsername = positiveCases[3];
				status = positiveCases[4];

				for(int i =0;i<accounts.size();i++)
					if(accounts.get(i).getUsername().equals(username))
						citizen = accounts.get(i);
				GovernmentOfficial.addCase(citizen, LocalDate.parse(date,df),Integer.parseInt(caseNumber),status,tracerUsername);
			}
			Cases = GovernmentOfficial.getCases();
			for(int i =0;i<Cases.size();i++){
				for(int j=0;j<accounts.size();j++){
					if(Cases.get(i).getContactTracer().equals(accounts.get(j).getUsername())){
						((ContactTracer)accounts.get(j)).getAssignedCases().add(Cases.get(i));
					}
				}
			}
			br3.close();
		}catch(Exception e){
			System.out.println("PositiveCases read ERROR: " + e.toString());
		}
		try
		{
			BufferedReader br4 = new BufferedReader(new FileReader("VisitRecords.txt"));
			DateTimeFormatter df = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			DateTimeFormatter tf = DateTimeFormatter.ofPattern("HH:mm");

			while((username = br4.readLine())!=null){

				for(int i =0;i<accounts.size();i++)
					if(accounts.get(i).getUsername().equals(username))
						citizen = accounts.get(i);

				while(((line = br4.readLine())!=null)){

					if(!(line.isEmpty())){
						visitRecords=line.split(" ");

						estCode = visitRecords[0];
						date = visitRecords[1];
						time = visitRecords[2];
						citizen.checkIn(estCode, LocalDate.parse(date,df), LocalTime.parse(time,tf));
					}else
						break;
				}
			}
			br4.close();
		}
		catch(Exception e)
		{
			System.out.println("VisitRecords Read ERROR: " + e.toString());
		}
	}
	/**
	 * Writes Master.txt, PositiveCases.txt, VisitRecords.txt, and the *.act files that belong to a 
	 * particular user. 
	 */
	public static void writeFile()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		int i;

		try
		{
			BufferedWriter bw1 = new BufferedWriter(new FileWriter("Master.txt"));
			//opens Master for writing/overwriting, containing the username and roles
			for(i = 0; i < accounts.size(); i++)
				if(accounts.get(i) instanceof GovernmentOfficial)
					bw1.write(accounts.get(i).getUsername() + " official\n");
				else
				if(accounts.get(i) instanceof ContactTracer)
					bw1.write(accounts.get(i).getUsername() + " tracer\n");
				else
					bw1.write(accounts.get(i).getUsername() + " citizen\n");

			bw1.close();
		}
		catch(Exception e)
		{
			System.out.println("Master Write ERROR: " + e.toString());
		}

		try
		{
			for(i = 0; i < accounts.size(); i++)
			{
				PersonalDetails temp = accounts.get(i).getDetails();

				//creates/overwrites a file of the account containing their details
				BufferedWriter bw2 = new BufferedWriter(new FileWriter(accounts.get(i).getUsername() + ".act"));

				//writes all the details
				bw2.write(accounts.get(i).getPassword() + "\n");
				bw2.write(temp.getFirstName() + "," + temp.getMiddleName() +"," + temp.getSurname() + "\n");
				bw2.write("HOME:" + temp.getHome() + "\n");
				bw2.write("OFFICE:" + temp.getOffice() + "\n");
				bw2.write("PHONE:" + temp.getTelephone() + "\n");
				bw2.write("EMAIL:" + temp.getEmail() + "\n");

				bw2.close();
			}

		}
		catch(Exception e)
		{
			System.out.println("Personal Details write ERROR: " + e.toString());
		}

		try
		{
			BufferedWriter bw3 = new BufferedWriter(new FileWriter("PositiveCases.txt"));

			ArrayList<CoronaCase> cases = GovernmentOfficial.getCases();
			for(i = 0; i < cases.size(); i++)
			{
				if(cases.get(i).isAssignedCase())
					bw3.write(cases.get(i).getCaseNumber() + " " + cases.get(i).getUsername() + " " +
							cases.get(i).getDate().format(dtf) + " " + cases.get(i).getContactTracer() + " " +
							cases.get(i).getStatus() + "\n");
				else
					bw3.write(cases.get(i).getCaseNumber() + " " + cases.get(i).getUsername() + " " +
							cases.get(i).getDate().format(dtf) + " " + "000" + " " +
							cases.get(i).getStatus() + "\n");
			}

			bw3.close();
		}
		catch(Exception e)
		{
			System.out.println("Positive Cases write ERROR: " + e.toString());
		}

		try
		{
			BufferedWriter bw4 =  new BufferedWriter(new FileWriter("VisitRecords.txt"));
			ArrayList<Citizen> citizen = GovernmentOfficial.getAccounts();
			ArrayList<Record> visitRecord;

			for(i=0;i<citizen.size();i++){
				visitRecord = citizen.get(i).getRecords();

				if(!(visitRecord.isEmpty())){
					bw4.write(citizen.get(i).getUsername()+"\n");
					for(int j =0;j<visitRecord.size();j++)
						bw4.write(visitRecord.get(j).getEstablishmentCode() + " " + visitRecord.get(j).getVisitDate().format(dtf) + " " + visitRecord.get(j).getVisitTime()+ "\n");
					bw4.write("\n");

				}
			}
			bw4.close();
		}
		catch(Exception e){
			System.out.println("VisitRecords write ERROR: "+e.toString());
		}
	}

}
