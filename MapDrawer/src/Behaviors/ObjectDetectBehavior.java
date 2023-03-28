package Behaviors;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import lejos.hardware.Sound;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.localization.OdometryPoseProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.robotics.navigation.Navigator;
import lejos.robotics.navigation.Pose;
import lejos.robotics.navigation.Waypoint;
import lejos.robotics.subsumption.Behavior;

public class ObjectDetectBehavior implements Behavior {
    private boolean suppressed = false;
    private DifferentialPilot pilot;
    private EV3IRSensor IRSensor;
    private float[] Distances;
    private DataOutputStream out;
    private OdometryPoseProvider pose;
    private Pose position;

    public static ArrayList<Waypoint> waypoints;

    public static int ObstacleCount;

    public ObjectDetectBehavior(DifferentialPilot pilot, OdometryPoseProvider pose, DataOutputStream out)
            throws IOException {
        this.pilot = pilot;
        this.IRSensor = new EV3IRSensor(SensorPort.S4);
        this.pose = pose;
        this.out = out;
        ObstacleCount = 0;

        this.waypoints = new ArrayList<Waypoint>();
        this.waypoints.add(new Waypoint(0, 0));
    }

    @Override
    public boolean takeControl() {
        // System.out.println("ObjectDetectBehavior");
        Distances = new float[IRSensor.sampleSize()];
        IRSensor.fetchSample(Distances, Distances.length - 1);

        position = pose.getPose();
        return (Distances[Distances.length - 1] <= 50);
    }

    @Override
    public void action() {
        // try {
        // Pose position = pose.getPose();
        // out.writeUTF("Obstacle");
        // out.writeFloat(position.getX());
        // out.writeFloat(position.getY());
        // out.writeFloat(Distances[Distances.length-1]);
        //
        // out.flush();

        Waypoint wayP = new Waypoint(position.getX(), position.getY());
        waypoints.add(wayP);

        pilot.rotate(90);
        // Sound.twoBeeps();
        ObstacleCount++;

        // } catch (IOException e) {
        // e.printStackTrace();
        // }
    }

    @Override
    public void suppress() {
        suppressed = true;
    }

}
