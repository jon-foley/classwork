
package ducksim;

import java.awt.Color;

public class RubberDuck extends Duck {
    
    public RubberDuck() {
        setColor(Color.YELLOW);
        quackBehavior = new QuackSqueek();
        flyBehavior = new FlyNoWay();
    }
    
    @Override
    public String getQuack() {
        return "Squeek!";
    }
    
    @Override
    public String display() {
        return "Rubber";
    }
}
