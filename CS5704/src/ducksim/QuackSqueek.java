package ducksim;

public class QuackSqueek implements QuackBehavior{

	public void quack(Duck d) {
		d.setState(State.QUACKING);
	}

}
