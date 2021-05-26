import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
* This is the Contact Tracer Class for the Corona Tracker Program
*
* @author Vincent Ilagan
* @author Ralph Sanson
*  
* SECTION: S15A
*/
public class ContactTracer extends Citizen{

    /** Constructor for the Contact Tracer Class. Assigns the username and password
    * through the superclass Citizen.
    * 
    * @param user: the username
    * @param pass: the password of the account
    */
    public ContactTracer(String user, String pass) { super(user, pass); }

    /** Displays the assigned cases of the current instance of Contact Tracer
    * 
    */
    public void showCases(){

        for(int i=0;i<assignedCases.size();i++)
            System.out.println("[" + (i+1) + "] " + assignedCases.get(i).getCaseNumber() + "  || Status: " + assignedCases.get(i).getStatus());
    }

    /** Traces a specific COVID-positive case assigned to the current instance
    * of Contact Tracer. Possible exposed citizens are taken note of by
    * inserting their Record Object in the Possibly Exposed ArrayList
    *
    *@param caseNum: Case Number of the specific case to be traced
    *@param govt: Reference variable of Government Official that is used to access 
    *specific methods used to trace the case provided.
    * 
    *@return boolean: The result of the case whether it has been traced or 
    * provided case number is invalid.
    */
    public boolean traceCase(int caseNum, GovernmentOfficial govt) {
        CoronaCase positiveCase = null;
        ArrayList<Record> allVisits = govt.getAllVisits();
        Record posVisit = null;
        String usernameP, estCodeP = null, estCode;
        LocalTime timeP = null, time;
        LocalDate dateP = null, date;
        boolean traced;
        int i, j;

        if (checkValidCase(caseNum)) {
            //Assigns object with matching caseNum to a reference variable for easier access
            for (i = 0; i < assignedCases.size(); i++)
                if (assignedCases.get(i).getCaseNumber() == caseNum)
                    positiveCase = assignedCases.get(i);

            usernameP = positiveCase.getUsername();

            for (i = 0; i < allVisits.size(); i++) {

                if (positiveCase.getUsername().equals(allVisits.get(i).getUsername())) {

                    posVisit = allVisits.get(i);
                    if (isPositiveVisit(posVisit, positiveCase)) {


                        estCodeP = posVisit.getEstablishmentCode();
                        dateP = posVisit.getVisitDate();
                        timeP = posVisit.getVisitTime();

                        for (j = 0; j < allVisits.size(); j++) {

                            estCode = allVisits.get(j).getEstablishmentCode();
                            date = allVisits.get(j).getVisitDate();
                            time = allVisits.get(j).getVisitTime();
                            if (!(usernameP.equals(allVisits.get(j).getUsername()))) {

                                if (dateP.equals(date))
                                    if (estCodeP.equals(estCode))
                                        if (timeP.isBefore(time) || timeP.equals(time))
                                            possiblyExposed.add(allVisits.get(j));
                            }
                        }
                    }

                }

            }
            positiveCase.setStatus("T");
            traced=true;
        }
        else
            traced=false;

        return traced;
    }

    /** Determines if the COVID-positive user visit has been before or after they 
    *have declared positive.
    *
    *@param allVisits: Record object that contains the visit of the COVID-positive 
    *user in the Citizen ArrayList.
    *@param posCase: Corona Case object that contains the date of when the 
    *COVID-positive user has declared positive.
    *
    *@return boolean: if the visit was a when the COVID-positive user has 
    * declared positive.
    */
    public  boolean isPositiveVisit(Record allVisits,CoronaCase posCase){
        int i,j;
        boolean isPosVisit = false;
        System.out.println(posCase.getDate()+" "+allVisits.getVisitDate());
        if(allVisits.getVisitDate().isAfter(posCase.getDate()))
            isPosVisit=true;

        return isPosVisit;
    }

    /** Retrieves the Corona Case ArrayList of assigned cases to specific
    *instance of Contact Tracer.
    *
    * @return ArrayList<CoronaCase>: the ArrayList of assigned cases.
    */
    public ArrayList<CoronaCase> getAssignedCases(){
        return assignedCases;
    }

    /** Informs the exposed citizens by using the Government Official static
    * method and passing the Possibly Exposed ArrayList.
    *
    */
    public void informCitizen(){
        GovernmentOfficial.notifyCitizen(possiblyExposed);
    }

    /** Checks if given case number exists within the assigned cases of the
    * current instance of the Contact Tracer.
    *
    *@param caseNum: the case number of the case to be checked.
    *
    *@return boolean: representing the result of the checking
    */
    public boolean checkValidCase(int caseNum){
        boolean valid = false;
        int i;
        for(i=0;i<assignedCases.size();i++)
            if(caseNum==assignedCases.get(i).getCaseNumber())
                valid = true;

        return valid;
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }

    private ArrayList<CoronaCase> assignedCases = new ArrayList<CoronaCase>();
    private  ArrayList<Record> possiblyExposed = new ArrayList<Record>();
}