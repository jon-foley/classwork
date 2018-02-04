package ducksim;

public class GooseDuck extends Duck{
	
	private Goose goose;
	
	public GooseDuck(){
		goose = new Goose();
		flyBehavior = new FlyWithWings();
		quackBehavior = new QuackNormal();
	}
	
	public String getQuack(){
		return goose.getHonk();
	}
	
	public String display(){
		return goose.getName();
	}
}
