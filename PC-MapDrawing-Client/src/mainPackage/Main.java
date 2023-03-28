package mainPackage;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) throws IOException {
		System.out.println("Starting PC map drawing client - waiting for connection from 10.0.1.1:8080");

		JFrame frame = new JFrame();
		frame.setTitle("Robot Map Drawer");
		frame.setSize(400, 400);
		frame.setLocationRelativeTo(null);
		MapComponent mc = new MapComponent();
		frame.add(mc);
		frame.setVisible(true);

		Socket connection = new Socket("10.0.1.1", 8080);
		DataInputStream in = new DataInputStream(connection.getInputStream());

		if (connection.isConnected()) {
			boolean done = false;
			while (!done) {
				try {
					String MapDataType = in.readUTF();
					int x = Math.round(in.readFloat());
					int y = Math.round(in.readFloat());

					switch (MapDataType) {
						case "Obstacle":
							int range = Math.round(in.readFloat());
							mc.addObstacle(x, y, range);
							break;
						case "MapData":
							mc.addPosition(x, y);
							break;
					}

					mc.addPosition(x, y);
					System.out.println("x = " + x);
					System.out.println("y = " + y);

					if (!connection.isConnected()) {
						done = true;
					}
				} catch (IOException e) {
					System.out.println("ERROR - " + e.getMessage());
				}
			}
		}

		connection.close();
	}
}