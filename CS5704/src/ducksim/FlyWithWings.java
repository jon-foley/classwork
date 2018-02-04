package ducksim;

public class FlyWithWings implements FlyBehavior {

	public void fly(Duck d) {
		d.setState(State.FLYING);
	}

}
