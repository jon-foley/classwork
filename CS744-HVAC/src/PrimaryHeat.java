//
// This is the <<boundary>> class for PrimaryHeat
// This class acts as the device driver for the primary heating unit.
// For this assignment, the on() and off() methods are just 
//   stubs.
//
// Students - Do Not Modify
//
// R. Pettit - 2016
//

public class PrimaryHeat {
	
	private static boolean isOn = false;
	
	public void on(){
		
		if (isOn){
			System.out.println("Primary Heater was already on");
		}
		else{
			isOn = true;
			SystemStatus.setState(SystemState.HEATING);
			System.out.println("Primary Heater is On");
		}
	}
	
	public void off(){
		if (!isOn){
			System.out.println("Primary Heater was already off");
		}
		else{
			isOn = false;
			SystemStatus.setState(SystemState.OFF);
			System.out.println("Primary Heater is Off");
		}
	}

}
