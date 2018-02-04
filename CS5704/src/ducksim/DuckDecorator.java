package ducksim;

import java.awt.Color;
import java.util.Observable;

public abstract class DuckDecorator extends Duck{
	
	protected Duck duck;
	
	public DuckDecorator(Duck d){
		duck = d;
	}

	@Override
	public void fly(){
		duck.fly();
	}
	
	@Override
	public void quack(){
		duck.quack();
	}
	
	@Override
	public String getQuack(){
		return duck.getQuack();
	}
	
	@Override
	public void swim(){
		duck.swim();
	}
	
	@Override
	public State getState(){
		return duck.getState();
	}
	
	@Override
	public void setState(State state){
		duck.setState(state);
	}
	
	@Override
	public Color getColor(){
		return duck.getColor();
	}
	
	@Override
	public void setColor(Color color){
		duck.setColor(color);
	}
	
	@Override
	public void joinDSWC() {
        duck.joinDSWC();
    }
    
	@Override
    public void quitDSWC() {
        duck.quitDSWC();
    }
    
	@Override
    public boolean isOnDSWC() {
        return duck.isOnDSWC();
    }
    
	@Override
    public void capture() {
        duck.capture();
    }
    
	@Override
    public void release() {
        duck.release();
    }
    
	@Override
    public boolean isFree() {
        return duck.isFree();
    }
	
	@Override
	public void update(Observable o, Object arg){
		duck.update(o, arg);
	}
}
