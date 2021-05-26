import java.time.*;
/** This is the Contact Tracer Class for the Corona Tracker Program
*
* @author Vincent Ilagan
* @author Ralph Sanson
*  
* SECTION: S15A
*/
public class CoronaCase
{
    /** Contsructor for the Corona Case Object. 
    *
    *@param citizen: Citizen object, used to get username and City.
    *@param date: date of report.
    *@param caseNum: case number of the report.
    *@param status: status of the case, whether "P" or "T".
    *@param tracer: username of the tracer 
    *
    */
    public CoronaCase(Citizen citizen, LocalDate date, int caseNum, String status, String tracer)
    {
        USERNAME = citizen.getUsername();
        this.date=date;
        this.status = status;
        isAssigned = false;
        CASENUMBER = caseNum;
        City = citizen.getDetails().getCity();

        if(tracer.equals("000")){
            this.tracer = "000";
            isAssigned = false;
        }
        else{
            this.tracer=tracer;
            isAssigned=true;
        }
    }

    /** Adds the the username of the tracer to current instance of Corona Case
    * 
    *@param tracer: username of the Contact Tracer.
    */
    public void addTracer(String tracer)
    {
        if(this.tracer.equals("000"))
        {
            this.tracer = tracer;
            isAssigned = true;
        }
    }

    /** Checks if the report date of the case is between a given range.
    *
    *@param lowerbound: lowerbound of the date range.
    *@param upperbound: upperbound of the date range.
    *@param caseDate: provided date of the case.
    *
    *@return boolean: the result of the checking.
    */
    public boolean isCaseDateWithin(LocalDate lowerbound, LocalDate upperbound, LocalDate caseDate)
    {
        return caseDate.isBefore(upperbound) && caseDate.isAfter(lowerbound);
    }

    /** Checks if the current instance of Corona Case is assigned to a tracer.
    *
    *@return boolean: the boolean value of isAssigned.
    */
    public boolean isAssignedCase()
    {
        return isAssigned;
    }

    /** Gets the case number of the current instance of Corona Case.
    *
    *@return int: case number.
    */
    public int getCaseNumber()
    {
        return CASENUMBER;
    }

    /** Gets the report date of the case.
    *
    *@return LocalDate: date of the case.
    */
    public LocalDate getDate()
    {
        return date;
    }

    /** Gets the city of the current instance of Corona Case.
    *
    *@return String: string data of the city of the current instance of Corona Case.
    */
    public String getCity()
    {
        return City;
    }

    /** Gets the status of the current instance of Corona Case.
    *
    *@return String: status of the case, whether "P" or "T".
    */
    public String getStatus(){ return status; }

    /** Gets the username of the Contact Tracer assigned to the current instance of Corona Case.
    * 
    *@return String: username of the assigned Contact Tracer.
    */
    public String getContactTracer() { return tracer; }

    /** Gets the Username of the COVID-positive user.
    * 
    *@return String: username of the COVID-positive user.
    */
    public String getUsername(){ return USERNAME;}

    /** Sets the status of the current instance of Corona Case.
    *
    *@param status: status of the case. 
    */
    public void setStatus(String status){this.status= status;}

    /** Concatenates specific attributes used for printing.
    *
    *@return String: concatenated string of specific attributes.
    */
    public String toString() { return "Case number " + CASENUMBER + ": " + USERNAME; }

    private final String USERNAME;
    private final int CASENUMBER;
    private LocalDate date;
    private String City;
    private String status;
    private String tracer;
    private boolean isAssigned;

}