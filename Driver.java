import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
* This is the Driver Class for the Corona Tracker Program
*
* @author Vincent Ilagan
* @author Ralph Sanson
*  
* SECTION: S15A
*/
public class Driver 
{

	private static ArrayList<Citizen> accounts = GovernmentOfficial.getAccounts();

	/**
	 * Instantiates the GUI and Controller objects.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		GUI gui = new GUI();
		Controller ctrllr = new Controller(accounts, gui);
	}
}

