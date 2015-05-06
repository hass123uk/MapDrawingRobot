package Behaviors;

import lejos.hardware.Button;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Behavior;

public class ReturnToStart implements Behavior {
	
	private DifferentialPilot pilot;
	private OdometryPoseProvider pose;
	private Navigator nav;
	private boolean _suppressed = false;
	
	public ReturnToStart(DifferentialPilot pilot, OdometryPoseProvider pose, Navigator nav){
		this.pilot = pilot;
		this.pose = pose;
		this.nav = nav;
	}

	@Override
	public boolean takeControl() {
		if (ObjectDetectBehavior.ObstacleCount >= 1) {
//			System.out.println(ObjectDetectBehavior.ObstacleCount);
//			System.out.println(pose.getPose());
			return true;
	      } else {
	         return false;
	      }
	}

	@Override
	public void action() {
		_suppressed = false;
		 System.out.println("Time to go home.");
		 nav.goTo(0, 0);
		 ObjectDetectBehavior.ObstacleCount = 0;
	}

	@Override
	public void suppress() {
		_suppressed = true;
	}

}
