import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
/**
* This is the Citizen Class for the Corona Tracker Program
*
* @author Vincent Ilagan
* @author Ralph Sanson
*  
* SECTION: S15A
*/
public class Citizen
{
	/**
	 * Initializes the newly created Citizen with the username and password of the
	 * Citizen as its login credentials.
	 * 
	 * @param user - the username of the account.
	 * @param pass - the password of the account.
	 */
	public Citizen(String user, String pass)
	{
		USERNAME = user;
		password = pass;
		
		accountInformation = new PersonalDetails();
	}
	/**
	 * Allows the user to check in to an establishment by entering its
	 * establishment code, the date, and time.
	 * 
	 * @param code - the establishment code.
	 * @param date - the date entry.
	 * @param time - the time entry.
	 * @return returns true if the the code-time-date combination is unique, false if otherwise.
	 */
    public boolean checkIn(String code, LocalDate date, LocalTime time)
    {
    	boolean duplicate = false;
    	for(int i = 0 ;i<records.size();i++)
    		if(records.get(i).getEstablishmentCode().equals(code))
    			if(records.get(i).getVisitDate().equals(date))
    				if(records.get(i).getVisitTime().equals(time))
    					duplicate=true;

    	if(!duplicate)
    		records.add(new Record(code, date, time, USERNAME));

    	return duplicate;
    }
    /**
     * Declares the user to be positive.
     */
	public void declarePositive()
	{
		isPositive = true;
	}
	/**
	 * Sets the user to be exposed. It is used for contact tracing features used by
	 * Contact Tracers.
	 */
    public void setExposed(){ isExposed = true; }
    /**
     * Allows the changing of password for the account.
     * 
     * @param password - the new password.
     */
	public void setPassword(String password)
	{
		this.password = password;
	}
	/**
	 * Assigns a new PersonalDetails object to the account.
	 * 
	 * @param details - the new PersonalDetails object.
	 */
	public void setPersonalDetails(PersonalDetails details)
	{
		accountInformation = details;
	}
	/**
	 * Retrieves the Visit records that contains the establishment code, date, and time/
	 * 
	 * @return - the ArrayList of Records made by the Citizen.
	 */
	public ArrayList<Record> getRecords()
	{
		return records;
	}
	/**
	 * Retrieves the username.
	 * 
	 * @return - the username of the account.
	 */
	public String getUsername()
	{
		return USERNAME;
	}
	/**
	 * Retrieves the password.
	 * 
	 * @return - the password of the account.
	 */
	public String getPassword()
	{
		return password;
	}
	/**
	 * Returns if the Citizen account has been exposed based past Visit records.
	 * 
	 * @return - returns true if the Citizen might have been exposed, false if otherwise.. 
	 */
	public boolean getIsExposed() {return isExposed;}
	/**
	 * Returns if the Citizen has declared themselves as positive.
	 * 
	 * @return - returns true if the Citizen has been tested to be positive, false if otherwise.
	 */
	public boolean getIsPositive()
	{
		return isPositive;
	}
	/**
	 * Returns the PersonalDetails of the account.
	 * 
	 * @return - returns the PersonalDetails of the account to be used for editing.
	 */
	public PersonalDetails getDetails()
	{
		return accountInformation;
	}
	/**
	 * Displays the PersonalDetails of the account along with the username.
	 * 
	 */
	public void displayDetails()
	{
		System.out.println(USERNAME + "'s account information: \n");
		accountInformation.displayFullName();
	}
	
	private final String USERNAME;
	private String password;
	private PersonalDetails accountInformation;
	
	private ArrayList<Record> records = new ArrayList<Record>();
	private boolean isPositive = false;
	private boolean isExposed = false;
}