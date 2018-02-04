package ducksim;

public class FlyNoWay implements FlyBehavior {

	public void fly(Duck d) {
		d.setState(State.SWIMMING);
	}

}
