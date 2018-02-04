//
// This is the <<boundary>> class for Cooling
// This class acts as the device driver for the cooling unit.
// For this assignment, the on() and off() methods are just 
//   stubs.
//
// Students - Do Not Modify
//
// R. Pettit - 2016
//

public class Cooling {
	
	private static boolean isOn = false;
	
	public void on(){
		
		if (isOn){
			System.out.println("A/C was already on");
		}
		else{
			isOn = true;
			SystemStatus.setState(SystemState.COOLING);
			System.out.println("A/C is On");
		}
	}
	
	public void off(){
		if (!isOn){
			System.out.println("A/C was already off");
		}
		else{
			isOn = false;
			SystemStatus.setState(SystemState.OFF);
			System.out.println("A/C is Off");
		}
	}

}
