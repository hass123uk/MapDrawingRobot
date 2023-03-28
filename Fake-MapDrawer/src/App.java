package src;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting fake robot");
        try (Scanner sc = new Scanner(System.in);
                Socket connection = new Socket("localhost", 60010)) {

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());

            System.out.println("Connection established");
            while (true) {
                out.writeUTF("MapData");
                out.writeFloat(5);
                out.writeFloat(5);
                out.writeUTF("MapData");
                out.writeFloat(7);
                out.writeFloat(5);
                out.writeUTF("MapData");
                out.writeFloat(10);
                out.writeFloat(5);
                out.writeUTF("MapData");
                out.writeFloat(15);
                out.writeFloat(5);

                out.writeUTF("Obstacle");
                out.writeFloat(20);
                out.writeFloat(5);

                out.flush();

                System.out.println("Enter anything to send the same data again");
                sc.nextInt();
            }
        }
    }
}
