A project to learn robot programming using the LEGO EV3 Mindstorms brick and [LeJOS](https://lejos.sourceforge.io/index.php) to do the programming in Java.

The robot could be controlled to perform multiple behaviors based on user input.

One of the behaviors was to explore the area and report back it's coordinates which would be drawn on screen using the companion application. During exploration it would report position and any obstacles encountered.

There is a fake map drawer that was added years later just to see what this can do. It cannot do much and it is not very interesting to work with without the actual robot.

Project structure:

- MapDrawer: this is the application that is run on the robot and performs different tasks/behaviors.
- PC-MapDrawing-Client: this is the companion app that uses JFrame to draw the coordinates of the robots position and if the robot detects an obstacle using it's sensor it reports that separately with a range so that it is plotted on the map differently.
- Fake-MapDrawer: A simple server that was added later to test this PC-MapDrawing-Client without having a robot anymore. Sends coordinates to the companion app.
