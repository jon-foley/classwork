
package ducksim;

import java.awt.Color;
import java.util.Observable;
import java.util.Observer;

public abstract class Duck implements Observer {
    
    private Color color = Color.BLACK;
    private State state = State.SWIMMING;
    private boolean isFree = true;
    private boolean isOnDSWC = false;
    protected QuackBehavior quackBehavior = new QuackNormal();
    protected FlyBehavior flyBehavior = new FlyWithWings();
    
    // typical duck commands
    
    public void swim() {
        state = State.SWIMMING;
    }
    
    public void quack() {
        if (isFree){
        	quackBehavior.quack(this);
        }
        else{
        	new QuackNoWay().quack(this);
        }
    }
    
    public String getQuack() {
        return "Quack!";
    }
    
    public void fly() {
        if (isFree){
        	flyBehavior.fly(this);
        }
        else{
        	new FlyNoWay().fly(this);
        }
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
        this.state = state;
    }
    
    public void setColor(Color c) {
        color = c;
    }
    
    public Color getColor() {
        return color;
    }
    
    // join / quit and capture / release commands
    
    public void joinDSWC() {
        isOnDSWC = true;
    }
    
    public void quitDSWC() {
        isOnDSWC = false;
    }
    
    public boolean isOnDSWC() {
        return isOnDSWC;
    }
    
    public void capture() {
        isFree = false;
    }
    
    public void release() {
        isFree = true;
    }
    
    public boolean isFree() {
        return isFree;
    }
    
    public void update(Observable o, Object arg){
    	if (o == DuckFactory.getInstance()){
    		if (isOnDSWC){
    			if (isFree){
    				setState(State.WELCOMING);
    			}
    			else{
    				setState(State.WARNING);
    			}
    		}
    	}
    }
    
    public abstract String display();
    
}
