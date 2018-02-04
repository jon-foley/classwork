//
// Driver class for the HVAC Smart Home implementation assignment
//
// R. Pettit 2016
//

import java.util.Scanner;
import java.util.Timer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;




/**
 * Driver class to test the implementation of the HVAC subsystem
 * 
 * @author jfoley
 */
public class HVAC {
	
	// Setup queue between CentralControl and TemperatureControl
	// Implemented as a BlockingQueue (specifically ArrayBlockingQueue)
	//   with a capacity of 100 elements
		
	static BlockingQueue<Mode> tcQueue = new ArrayBlockingQueue<Mode>(100); 
	
	/**
	 * The user's choice of interface
	 */
	public static int choice;
	
	/**
	 * Drives the test of the HVAC subsystem
	 * 
	 * @param args string arguments
	 */
	public static void main(String[] args) {
		//Start the test console
		testConsole();
		
		Timer tempUpdate = new Timer(); // For the periodically activated Temperature Sensor
		tempUpdate.schedule(new TempSensor(),0, 1000); //1 Hz activation period
		
		CentralControl centralCtrl = new CentralControl(tcQueue);
		TemperatureCtrl tempCtrl = new TemperatureCtrl(tcQueue);
		
		// Start the tasks
		centralCtrl.start();
		tempCtrl.start();
	}
	
	/**
	 * Console UI allowing users to run pre-made console test, launch the GUI, or quit.
	 */
	public static void testConsole(){
		Scanner scan = new Scanner(System.in);
		choice = 0;
		while(true){
			System.out.println("Welcome! Would you like to see the output of pre-made tests in the console, or \n" 
					+ "interact with the Central Monitor via the GUI?\nNote that there are three standard tests \n" 
					+ "that require approximately 3 minutes to run (and then begin again in a loop).\n");
			System.out.println("1) Run standard tests in the console.");
			System.out.println("2) Open the Central Monitor GUI");
			System.out.println("99) Quit.");
			try{
				choice = Integer.valueOf(scan.nextLine());
			}
			catch (NumberFormatException e){
				System.out.println("Unable to read your choice. Please enter an integer!!");
				continue;
			}
			if (choice == 99){
				System.out.println("Goodbye!");
				System.exit(0);
			}
			else if (choice == 1){
				break;
			}
			else if (choice == 2){
				break;
			}
			else{
				System.out.println("The entry '" + choice + "' is not a valid selection!");
			}
		}
		
	}
}
