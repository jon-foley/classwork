package ducksim;

public class QuackNormal implements QuackBehavior{

	public void quack(Duck d) {
		d.setState(State.QUACKING);
	}

}
