package mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class MapComponent extends JComponent {
	private static final long serialVersionUID = 1L;
	private List<Point> points = new ArrayList<>();
	private List<Point> obstacles = new ArrayList<>();

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		Graphics2D gObstacles = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		gObstacles.setColor(Color.RED);
		g2d.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2);
		g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());

		for (Point p : new ArrayList<>(points)) {
			g2d.fillOval(getWidth() / 2 + p.x, getHeight() / 2 - p.y, 3, 3);
		}

		for (Point p : new ArrayList<>(obstacles)) {
			gObstacles.fillOval(getWidth() / 2 + p.x, getHeight() / 2 - p.y, 3, 3);
		}
	}

	public void addPosition(int x, int y) {
		points.add(new Point(x, y));
		repaint();
	}

	public void addObstacle(int x, int y, int range) {
		int newX = x;
		int newY = y + range;

		obstacles.add(new Point(newX, newY));
		repaint();
	}
}
