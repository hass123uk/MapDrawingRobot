package src;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting fake robot");
        try (Scanner sc = new Scanner(System.in);
                ServerSocket server = new ServerSocket(60010);
                Socket connection = server.accept();
                DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {

            System.out.println("Connection established - press to send data");
            sc.nextLine();

            int iteration = 1;
            int x = 0;
            int y = 5;
            int range = 10;

            while (true) {
                String type = iteration % 4 == 1 ? "MapData" : "Obstacle";
                out.writeUTF(type);
                out.writeFloat(x += 2);
                out.writeFloat(y);

                if (type == "Obstacle")
                    out.writeFloat(range);

                System.out.println("Type = " + type);

                out.flush();

                System.out.println("Enter anything to send the same data again");
                sc.nextLine();

                iteration++;
            }
        }
    }
}
