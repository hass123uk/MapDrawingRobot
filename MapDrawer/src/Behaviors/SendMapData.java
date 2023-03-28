package Behaviors;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.hardware.lcd.LCD;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;

public class SendMapData implements Behavior {

	private boolean _suppressed = false;
	private DataOutputStream out;
	private OdometryPoseProvider pose;
	private long lastTimeDataSent;
	private Pose position;

	public SendMapData(OdometryPoseProvider pose, DataOutputStream out) {
		this.pose = pose;
		this.out = out;
		lastTimeDataSent = 0;
	}

	@Override
	public boolean takeControl() {
		if (System.currentTimeMillis() - lastTimeDataSent > 2000) {

			// System.out.println("SendMapData");
			position = pose.getPose();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void action() {
		try {
			_suppressed = false;
			out.writeUTF("MapData");
			out.writeFloat(position.getX());
			out.writeFloat(position.getY());
			out.flush();

			lastTimeDataSent = System.currentTimeMillis();

		} catch (IOException e) {
			System.out.println("Error - " + e.getMessage());
		}

	}

	@Override
	public void suppress() {
		_suppressed = true;
	}
}
