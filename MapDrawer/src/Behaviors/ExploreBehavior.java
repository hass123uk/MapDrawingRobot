package Behaviors;
import java.util.ArrayList;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.pathfinding.Path;
import lejos.robotics.subsumption.Behavior;

public class ExploreBehavior implements Behavior {
	
	private DifferentialPilot pilot;
	private Navigator nav;
	private Waypoint next;
	
	private boolean _suppressed = false;
	
	
	public ExploreBehavior(DifferentialPilot pilot, Navigator nav){
		this.pilot = pilot;
		this.nav = nav;
	}

	@Override
	public boolean takeControl() {
//		System.out.println("ExplorerBehavior");
		return true;
	}

	@Override
	public void action() {
		_suppressed = false;

		if (ObjectDetectBehavior.ObstacleCount >= 3) {
			
			Path path = new Path();
			System.out.println("Array Size: " + ObjectDetectBehavior.waypoints.size());
			for(int i = ObjectDetectBehavior.waypoints.size() - 1; i >= 0; i--){
				
				path.add(ObjectDetectBehavior.waypoints.get(i));
				
//				System.out.println(SendMapData.waypoints.get(i).toString());
			}
//			LCD.drawInt(SendMapData.waypoints.size(), 0, 1);
			nav.setPath(path);
			
			System.out.println("Start loop");
			while(!nav.pathCompleted()){
				nav.followPath(path);
				next = nav.getWaypoint();
				System.out.println("Moving to...");
				System.out.println("(" + (int)next.getX() +
			      "," + (int)next.getY() + ")");
//				LCD.drawString("Going to " + nav.getWaypoint(), 0, 5);
			}
			System.out.println("Stop loop");
			System.out.println("Array Size: " + ObjectDetectBehavior.waypoints.size());
			ObjectDetectBehavior.waypoints = new ArrayList<Waypoint>();
			
			if(nav.pathCompleted()){
				System.exit(0);
			}
			
			ObjectDetectBehavior.ObstacleCount = 0;
			
	      } else {
	  		
	    pilot.forward();
	    
		while(!_suppressed)
			Thread.yield();
		pilot.stop();
	      }
	}

	@Override
	public void suppress() {
		_suppressed = true;
	}

}
