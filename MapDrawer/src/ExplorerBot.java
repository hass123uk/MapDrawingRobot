import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import Behaviors.ExitBehavior;
import Behaviors.ExploreBehavior;
import Behaviors.ObjectDetectBehavior;
import Behaviors.ReturnToStart;
import Behaviors.SendMapData;
		
public class ExplorerBot {
	
	private static DifferentialPilot pilot;
    /**
     * @param args
     */
	public static void main(String [] args) throws IOException { 

			pilot = new DifferentialPilot(3.2f, 18.25f, Motor.B, Motor.C, false);
			OdometryPoseProvider pose = new OdometryPoseProvider(pilot);

			Navigator nav = new Navigator(pilot, pose);
	    	ServerSocket serv;
				serv = new ServerSocket(8080);
				Socket con = serv.accept();
				DataOutputStream out = new DataOutputStream(con.getOutputStream());


//			Behavior b4 = new ReturnToStart(pilot, pose, nav);
			Behavior b1 = new ExploreBehavior(pilot, nav);
			Behavior b2 = new ObjectDetectBehavior(pilot, pose, out);
			Behavior b3 = new SendMapData(pose, out);
			Behavior b5 = new ExitBehavior();
			Behavior[] behaviorList = {b1, b2, b3, b5};
			Arbitrator arbitrator = new Arbitrator(behaviorList);
			
			LCD.clear();
			LCD.drawString("ExplorerBot -", 0, 1);
			LCD.drawString("Press any button to start!", 0, 3);
			Button.waitForAnyPress();
			LCD.clear();
			arbitrator.start();
		    }
	}


