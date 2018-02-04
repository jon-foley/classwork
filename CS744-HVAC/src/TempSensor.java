//
// Temperature Sensor <<boundary>> class
// For HVAC implementation assignment framework
// Periodically activated by driver
//
// R. Pettit - 2016
//


import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class TempSensor extends TimerTask {
	
	private SystemState hvacMode = SystemState.OFF;
	
	// Method to simulate monitoring temperature sensor
	private void monitor(){
	    
		// Start with the current temperature
		int curr; 
		curr = Temperature.getCurrent();
		
		// Do some simple simulation based on system state
		hvacMode = SystemStatus.getState();

		switch(hvacMode){
			case OFF: Temperature.setCurrent(curr+ThreadLocalRandom.current().nextInt(-1,2));
					  break;
			case COOLING: Temperature.setCurrent(curr+ThreadLocalRandom.current().nextInt(-2,0));
						  break;
			case HEATING: Temperature.setCurrent(curr+ThreadLocalRandom.current().nextInt(0,2));
						  break;
			case BACKUP:  Temperature.setCurrent(curr+ThreadLocalRandom.current().nextInt(1,2));
						  break;
		}		  
    }
	public void run() {
		monitor();
	}

}
