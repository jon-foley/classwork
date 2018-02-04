import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

//
// This is the <<control>> class for TemperatureCtrl
// This class will be the primary focus for the implementation assignment
// You will need to override the run() method and add other methods as necessary
// TemperatureCtrl should check the queue from Central Control for mode change messages
// While in any mode other than OFF, you will be monitoring current/desired temperatures
//    and controlling the fan, cooling, primary heating, and backup heating units
//    through the respective <<boundary>> classes.


/**
 * Implementation of the Temperature Control class that turns on or off the 
 * Cooling, Heating, and Fan components to match the desired temperature and
 * mode.
 * 
 * @author jfoley
 */
public class TemperatureCtrl extends Thread {
	
	/**
	 * The queue for receiving messages from the Central Control clas
	 */
	protected BlockingQueue<Mode> queue; 
	
	/**
	 * The current HVAC mode for this control class
	 */
	private Mode currentMode;
	
	/**
	 * The Cooling unit
	 */
	private final Cooling coolingUnit;
	
	/**
	 * The Fan unit
	 */
	private final Fan fanUnit;
	
	/**
	 * The Primary Heat unit
	 */
	private final PrimaryHeat primaryHeatUnit;
	
	/**
	 * The Backup Heat unit
	 */
	private final BackupHeat backupHeatUnit;
	
	/**
	 * Whether the Primary Heat is on
	 */
	private boolean primaryHeatOn;
	
	/**
	 * Whether the Backup Heat is on
	 */
	private boolean backupHeatOn;
	
	/**
	 * Whether the Fan is on
	 */
	private boolean fanOn;
	
	/**
	 * Whether the the Cooling unit is on
	 */
	private boolean coolingOn;
	
	/**
	 * The time at which the backup heater cycle was started
	 */
	private long backupHeatStartTime = 0;
	
	/**
	 * The maximum duration for one cycle of the backup heater
	 */
	private static final long backupHeatMaxDuration = 2000 * 60;

	/**
	 * The current temperature
	 */
	private int currentTemp;
	
	/**
	 * The desired temperature
	 */
	private int desiredTemp;
	
	/**
	 * Sets up the queue and initial state of the system
	 * 
	 * @param queue the queue used for asynchronous messaging
	 */
	public TemperatureCtrl(BlockingQueue<Mode> queue){
		this.queue = queue;
		currentMode = Mode.OFF;
		coolingUnit = new Cooling();
		fanUnit = new Fan();
		primaryHeatUnit = new PrimaryHeat();
		backupHeatUnit = new BackupHeat();
		primaryHeatOn = false;
		backupHeatOn = false;
		fanOn = false;
		coolingOn = false;
	}	
	
	/**
	 * Starts the TemperatureCtrl thread to monitor temperatures and change HVAC modes as necessary.
	 */
	@Override
	public void run(){
		//Set up the monitor to check the current and desired temperatures every second
		Timer tempTimer = new Timer();
		tempTimer.schedule(new TempMonitor(), 0, 1000);
		while(true){
			//Waits up to one second to see if any messages are in the queue
			retrieveAndStoreModeMessage();
			switch(currentMode){
				case COOLING:
					handleCoolingMode();
					break;
				case HEATING:
					handleHeatingMode();
					break;
				case OFF:
					handleOffMode();
					break;
				default:
					break;
			}
			
		}
	}
	
	/**
	 * Sets the current mode of the temperature controller
	 * 
	 * @param m the mode to set
	 */
	public void setCurrentMode(Mode m){
		currentMode = m;
	}
	
	/**
	 * Sets the current temperature field
	 * 
	 * @param temp the temperature to set
	 */
	private synchronized void setCurrentTemp(int temp){
		currentTemp = temp;
	}
	
	/**
	 * Gets the current temperature field's value
	 * 
	 * @return the current temperature
	 */
	private synchronized int getCurrentTemp(){
		return currentTemp;
	}
	
	/**
	 * Sets the desired temperature field
	 * 
	 * @param temp the temperature to set
	 */
	private synchronized void setDesiredTemp(int temp){
		desiredTemp = temp;
	}
	
	/**
	 * Gets the desired temperature field's value
	 * 
	 * @return the desired temperature
	 */
	private synchronized int getDesiredTemp(){
		return desiredTemp;
	}
	
	/**
	 * Retrieves a message from the Mode blocking queue, waiting up to one second
	 * for a message to become available. If a message is available, it is retrieved and
	 * stored as the current mode. If a message is not available within one second, nothing
	 * happens.
	 */
	private void retrieveAndStoreModeMessage(){
		try {
			Mode modeMessage = (Mode) queue.poll(1, TimeUnit.SECONDS);
			if (modeMessage != null){
				setCurrentMode(modeMessage);
			}
		} catch (InterruptedException e) {
			//Do nothing
		}
	}
	
	/**
	 * Monitors the current and desired temperatures in the system
	 */
	private void monitorTemps(){
		setCurrentTemp(Temperature.getCurrent());
		setDesiredTemp(Temperature.getDesired());
	}
	
	/**
	 * Performs the necessary steps to control the HVAC temperature system in COOLING mode.
	 */
	private void handleCoolingMode(){
		if (primaryHeatOn){
			primaryHeatUnit.off();
			primaryHeatOn = false;
		}
		if (backupHeatOn){
			backupHeatUnit.off();
			backupHeatOn = false;
		}
		if (getCurrentTemp() > getDesiredTemp()){
			if (!coolingOn){
				coolingUnit.on();
				coolingOn = true;
			}
			if (!fanOn){
				fanUnit.on();
				fanOn = true;
			}
		}
		else{
			if (coolingOn){
				coolingUnit.off();
				coolingOn = false;
			}
			if (fanOn){
				fanUnit.off();
				fanOn = false;
			}
		}
	}
	
	/**
	 * Performs the necessary steps to control the HVAC temperature system in HEATING mode.
	 */
	public void handleHeatingMode(){
		if (coolingOn){
			coolingUnit.off();
			coolingOn = false;
		}
		if (getCurrentTemp() < getDesiredTemp()){
			if (!primaryHeatOn){
				primaryHeatUnit.on();
				primaryHeatOn = true;
			}
			if (!fanOn){
				fanUnit.on();
				fanOn = true;
			}
			if ((getDesiredTemp() - getCurrentTemp()) > 5){
				if (!backupHeatOn){
					backupHeatUnit.on();
					backupHeatOn = true;
					backupHeatStartTime = System.currentTimeMillis();
				}
				else if ((System.currentTimeMillis() - backupHeatStartTime) >= backupHeatMaxDuration){
					/*
					 * The backup heat has completed the full 2 minute cycle, but the current temp is still
					 * more than five degrees below the desired temp so we should reset the start time to begin a new
					 * backup heat cycle.
					 */
					backupHeatStartTime = System.currentTimeMillis();
					System.out.println("Backup heater 2 minute cycle refreshed");
				}
			}
			else{
				if (backupHeatOn){
					if ((System.currentTimeMillis() - backupHeatStartTime) >= backupHeatMaxDuration){
						/*
						 * The backup heat has completed the full 2 minute cycle, and the current temp is within five 
						 * degrees of the desired temp so we should deactivate the backup heat.
						 */
						backupHeatUnit.off();
						backupHeatOn = false;
					}
				}
			}
		}
		else{
			if (backupHeatOn){
				backupHeatUnit.off();
				backupHeatOn = false;
			}
			if (primaryHeatOn){
				primaryHeatUnit.off();
				primaryHeatOn = false;
			}
			if (fanOn){
				fanUnit.off();
				fanOn = false;
			}
		}
	}
	
	/**
	 * Performs the necessary steps to control the HVAC temperature system in OFF mode.
	 */
	public void handleOffMode(){
		if (primaryHeatOn){
			primaryHeatUnit.off();
			primaryHeatOn = false;
		}
		if (backupHeatOn){
			backupHeatUnit.off();
			backupHeatOn = false;
		}
		if (fanOn){
			fanUnit.off();
			fanOn = false;
		}
		if (coolingOn){
			coolingUnit.off();
			coolingOn = false;
		}
	}
	
	/**
	 * Private class for monitoring remote and updating local current and desired temperatures.
	 * @author jfoley
	 */
	private class TempMonitor extends TimerTask{
		
		/**
		 * Monitors the current and desired temperatures
		 */
		@Override
		public void run() {
			monitorTemps();
		}
	}
	
}
