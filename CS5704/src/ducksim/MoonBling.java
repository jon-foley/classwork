package ducksim;

public class MoonBling extends DuckDecorator{
	
	public MoonBling(Duck d){
		super(d);
	}
	
	public String display(){
		return duck.display() + ":)"; 
	}
	
}
