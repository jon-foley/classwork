package ducksim;

public class CrossBling extends DuckDecorator{
	
	public CrossBling(Duck d){
		super(d);
	}
	
	public String display(){
		return duck.display() + ":+";
	}
	
}
