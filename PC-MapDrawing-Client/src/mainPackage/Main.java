package mainPackage;

import java.io.DataInputStream;
import java.net.Socket;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		System.out.println("Starting PC map drawing client - waiting for connections");

		JFrame frame = new JFrame();
		frame.setTitle("Robot Map Drawer");
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		MapComponent mc = new MapComponent();
		frame.add(mc);
		frame.setVisible(true);

		// This is the address for the EV3 but to work with the Fake robot we set it to
		// localhost.
		// String ev3Address = "10.0.1.1";

		try (Socket connection = new Socket("localhost", 60010);
				DataInputStream in = new DataInputStream(connection.getInputStream());) {
			while (true) {
				if (!connection.isConnected()) {
					System.out.println("No longer connected to socket.");
					break;
				}

				String MapDataType = in.readUTF();
				int x = Math.round(in.readFloat());
				int y = Math.round(in.readFloat());

				System.out.println("MapDataType = " + MapDataType);
				System.out.println("x = " + x);
				System.out.println("y = " + y);

				switch (MapDataType) {
					case "Obstacle":
						int range = Math.round(in.readFloat());
						System.out.println("range = " + range);

						mc.addObstacle(x, y, range);
						break;
					case "MapData":
						mc.addPosition(x, y);
						break;
					default:
						System.out.println("Unknown data type - " + MapDataType);
						break;
				}
			}
		} catch (Exception e) {
			System.err.println("Error - " + e.getMessage());
		}

		System.exit(0);
	}
}