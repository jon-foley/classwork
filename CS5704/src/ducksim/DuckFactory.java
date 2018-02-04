package ducksim;

import java.util.Observable;

public class DuckFactory extends Observable {
	
	private static final DuckFactory factory = new DuckFactory();
	
	private DuckFactory(){
		super();
	}
	
	public static DuckFactory getInstance(){
		return factory;
	}
	
	public Duck createDuck(String duckType, int starCount, int moonCount, int crossCount){
		Duck returnedDuck = null;
		switch (duckType){
			case "Decoy":
				returnedDuck = new DecoyDuck();
				break;
			case "Mallard":
				returnedDuck = new MallardDuck();
				break;
			case "Redhead":
				returnedDuck = new RedheadDuck();
				break;
			case "Rubber":
				returnedDuck = new RubberDuck();
				break;
			case "Goose":
				returnedDuck = new GooseDuck();
			default:
				break;
		}
		for (int i = 0; i < starCount; i++){
			returnedDuck = new StarBling(returnedDuck);
		}
		for (int i = 0; i < moonCount; i++){
			returnedDuck = new MoonBling(returnedDuck);
		}
		for (int i = 0; i < crossCount; i++){
			returnedDuck = new CrossBling(returnedDuck);
		}
		if (returnedDuck != null){
			setChanged();
		}
		return returnedDuck;	
	}
}
