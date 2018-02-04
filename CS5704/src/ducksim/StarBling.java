package ducksim;

public class StarBling extends DuckDecorator {
	
	public StarBling(Duck d){
		super(d);
	}
	
	public String display(){
		return duck.display() + ":*";
	}
}
