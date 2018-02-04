package ducksim;

import java.awt.Color;

public class DecoyDuck extends Duck{
	
	public DecoyDuck(){
		flyBehavior = new FlyNoWay();
		quackBehavior = new QuackNoWay();
		Color brown = new Color(165, 42, 42);
		setColor(brown);
	}
	
	@Override
	public String display(){
		return "Decoy";
	}
}
