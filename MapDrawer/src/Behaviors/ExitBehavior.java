package Behaviors;

import lejos.hardware.*;
import lejos.robotics.subsumption.*;

public class ExitBehavior implements Behavior {
    private boolean suppressed = false;

    @Override
    public boolean takeControl() {
        return (Button.ESCAPE.isDown());
    }

    @Override
    public void action() {
        System.exit(0);
    }

    @Override
    public void suppress() {
        suppressed = true;
    }

}
