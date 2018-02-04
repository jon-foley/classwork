package ducksim;

public class QuackNoWay implements QuackBehavior {

	public void quack(Duck d) {
		d.setState(State.SWIMMING);
	}

}
