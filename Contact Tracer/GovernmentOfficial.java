 import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
* This is the GovernmentOfficial Class for the Corona Tracker Program
*
* @author Vincent Ilagan
* @author Ralph Sanson
*  
* SECTION: S15A
*/
public class GovernmentOfficial extends Citizen
{
	/**
	 * Initializes the newly created GovernmentOfficial with the username and password of the
	 * Government Official as its login credentials.
	 * 
	 * @param user - the username of the account. 
	 * @param pass - the password of the account.
	 */
	public GovernmentOfficial(String user, String pass)
	{
		super(user, pass);
	}
	/**
	 * Creates a new ContactTracer object. It stores the account's PersonalDetails into a temporary
	 * variable, deletes the account from the ArrayList, then instantiatess the new Contact Tracer
	 * with the same username and password while also assigning the PersonalDetails.
	 * 
	 * @param citizen - the Citizen object to be remade into a ContactTracer.
	 */
	public void create_ContactTracer(Citizen citizen)
	{
		PersonalDetails details = citizen.getDetails(); 
		String user = citizen.getUsername(), 
			   pass = citizen.getPassword();
		
		accounts.remove(citizen);
		accounts.add(new ContactTracer(user, pass));
		accounts.get(accounts.size() - 1).setPersonalDetails(details);
	}
	/**
	 * Creates a new GovernmentOfficial object. It stores the account's GovernmentOfficial into a temporary
	 * variable, deletes the account from the ArrayList, then instantiatess the new Government Official
	 * with the same username and password while also assigning the PersonalDetails.
	 * 
	 * @param citizen - the Citizen object to be remade into a Government Official.
	 */
	public void create_GovOfficial(Citizen citizen)
	{
		PersonalDetails details = citizen.getDetails(); 
		String user = citizen.getUsername(), 
			   pass = citizen.getPassword();
		
		accounts.remove(citizen);
		accounts.add(new GovernmentOfficial(user, pass));
		accounts.get(accounts.size() - 1).setPersonalDetails(details);
	}
	/**
	 * Demotes an account into a Citizen by deleting the Citizen object from the ArrayList,
	 * and instantiates a new Citizen account with the same username and password.
	 * 
	 * @param citizen - the account to be demoted into a Citizen.
	 */
	public void terminate_Account(Citizen citizen)
	{
		PersonalDetails details = citizen.getDetails();
		String user = citizen.getUsername(), 
			   pass = citizen.getPassword();
		
		accounts.remove(citizen);
		accounts.add(new Citizen(user, pass));
		accounts.get(accounts.size() - 1).setPersonalDetails(details);
	}
	/**
	 * Adds a new case for all Government Officials to see. A case is added whenever a Citizen
	 * declares themselves positive by being tested.
	 * 
	 * @param citizen - the Citizen who is positive.
	 * @param date - the date that the case has been noted.
	 * @param caseNumber - the case number that will be assigned.
	 * @param status - the status of the case for Contact Tracers to distinguish between a case being "Traced" or "Untraced"
	 * @param tracerUsername - the Contact Tracer's username assigned to the case. 
	 */
    public static void addCase(Citizen citizen, LocalDate date, int caseNumber, String status, String tracerUsername)
    {
		boolean duplicate=false;
		int i;
        if(caseNumber!=-1)
        {
            caseCtr=caseNumber;
            for(i=0;i<coronaCase.size();i++)
            	if(coronaCase.get(i).getUsername().equals(citizen.getUsername()))
            		if(coronaCase.get(i).getDate().equals(date))
            			if(coronaCase.get(i).getCaseNumber()==(caseNumber))
            				duplicate=true;

            if(!duplicate)
            	coronaCase.add(new CoronaCase(citizen, date, caseNumber, status, tracerUsername));
        }
        else if(caseNumber==-1){
            caseCtr++;
            coronaCase.add(new CoronaCase(citizen, date,  caseCtr, "P","000"));
        }

        //System.out.println("Date recorded: " + date);
		//Removed this for now^^
    }
    /**
     * Displays the ArrayList of UnassignedCases.
     * 
     */
	public void Unassigned_Cases()
	{
		//assigning a ContactTracer to a case
		if(coronaCase.size() > 0)
		{
			System.out.println("\nDisplaying unassigned cases: ");
			for(int i = 0; i < coronaCase.size(); i++)
				if(coronaCase.get(i).isAssignedCase() == false)
					System.out.println(coronaCase.get(i).toString());

		}
		else
			System.out.println("No unassigned cases to display.");
		
	}
	/**
	 * Assigns a tracer into a specific case.
	 * 
	 * @param tracer - the Contact Tracer to be assigned to a case.
	 * @param caseIndex - the specific case number to be assigned to the Contact Tracer.
	 * @return returns true if the case has been successfully assigned, false if otherwise.
	 */
	public boolean assignTracer(ContactTracer tracer, int caseIndex)
	{
		boolean assigned=false;
		if(!(coronaCase.get(caseIndex).isAssignedCase())|| coronaCase.get(caseIndex) == null){

			tracer.getAssignedCases().add(coronaCase.get(caseIndex));
			coronaCase.get(caseIndex).addTracer(tracer.getUsername());
			assigned=true;
		}

		return assigned;

	}
	/**
	 * Returns the CoronaCases with a lowerbound and upperbound dates with a City as filters.
	 * 
	 * @param lowerbound - the starting date.
	 * @param upperbound - the end date.
	 * @param city - the City.
	 * @return returns the ArrayList of CoronaCases with the specified filters.
	 */
	public ArrayList<CoronaCase> Analytics(LocalDate lowerbound, LocalDate upperbound, String city)
	{
		ArrayList<CoronaCase> temp = new ArrayList<CoronaCase>();
		
		for(int i = 0; i < coronaCase.size(); i++)
			if(coronaCase.get(i).getCity().toLowerCase().contains(city.toLowerCase()) &&
			   coronaCase.get(i).isCaseDateWithin(lowerbound, upperbound, coronaCase.get(i).getDate()))
					temp.add(coronaCase.get(i));
		
		return temp;
	}
	/**
	 * Returns the CoronaCases with a lowerbound and upperbound dates as filters.
	 * 
	 * @param lowerbound - the starting date.
	 * @param upperbound - the end date.
	 * @return returns the ArrayList of CoronaCases with the specified filters.
	 */
	public ArrayList<CoronaCase> Analytics(LocalDate lowerbound, LocalDate upperbound)
	{
		ArrayList<CoronaCase> temp = new ArrayList<CoronaCase>();
		
		for(int i = 0; i < coronaCase.size(); i++)
			if(coronaCase.get(i).isCaseDateWithin(lowerbound, upperbound, coronaCase.get(i).getDate()))
				temp.add(coronaCase.get(i));
		
		return temp;
	}
	/**
	 * Returns the CoronaCases with City as a filter.
	 * 
	 * @param city - the City.
	 * @return returns the ArrayList of CoronaCases with the specified filters.
	 */
	public ArrayList<CoronaCase> Analytics(String city)
	{
		ArrayList<CoronaCase> temp = new ArrayList<CoronaCase>();
		
		for(int i = 0; i < coronaCase.size(); i++)
			if(coronaCase.get(i).getCity().toLowerCase().contains(city.toLowerCase()))
				temp.add(coronaCase.get(i));	
		
		return temp;
	}
	/**
	 * Flags all the Citizens that might have been exposed.
	 * 
	 * @param exposed - the list of Records that is used to index through the Citizens that might have been in contact with each other.
	 */
    public static void notifyCitizen(ArrayList<Record> exposed){
        int i,j;
        String usernameE, usernameC;

        for(i=0;i<exposed.size();i++)
        {
            usernameE = exposed.get(i).getUsername();
            for(j=0;j<accounts.size();j++)
            {
                usernameC = accounts.get(j).getUsername();

                if(usernameE.equals(usernameC)){
                    accounts.get(j).setExposed();
                }
            }
        }
    }
    /**
     * Retrieves all Visit Recrods from all Citizen accounts.
     * 
     * @return the ArrayList of Records from all accounts.
     */
    public ArrayList<Record> getAllVisits()
    {
        int i,j;

        for(i=0;i<accounts.size();i++)
            for(j=0;j<accounts.get(i).getRecords().size();j++)
                allVisits.add(accounts.get(i).getRecords().get(j));
        
        return allVisits;
    }
    /**
     * Returns the reference to the ArrayList of accounts.
     * 
     * @return - the ArrayList of Citizen accounts.
     */
	public static ArrayList<Citizen> getAccounts()
	{
		return accounts;
	}
	/**
	 * Returns the reference to the ArrayList of cases.
	 * 
	 * @return - the ArrayList of CoronaCases.
	 */
	public static ArrayList<CoronaCase> getCases()
	{
		return coronaCase;
	}
	
	private static ArrayList<Citizen> accounts = new ArrayList<Citizen>();
	private static ArrayList<CoronaCase> coronaCase = new ArrayList<CoronaCase>();
    private static ArrayList<Record> allVisits = new ArrayList<Record>();
	private static int caseCtr = 0; 
}