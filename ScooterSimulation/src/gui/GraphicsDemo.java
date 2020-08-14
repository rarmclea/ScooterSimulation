package gui;

import javax.swing.JPanel;
import java.awt.*;

public class GraphicsDemo extends JPanel {
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		
		Graphics2D g2D = (Graphics2D) g;
//		
//		g2D.setColor(Color.MAGENTA);
//		g2D.setStroke(new BasicStroke(20));
//		g2D.drawLine(0, 0, 400, 400);
		
//		int[] xPoints = {50, 100, 150, 200, 250, 300, 350};
//		int[] yPoints = {350, 250, 275, 200, 274, 150, 100};
//		int nPoints = xPoints.length;
//		g2D.setColor(Color.GREEN);
//		g2D.setStroke(new BasicStroke(10));
//		g2D.drawPolyline(xPoints, yPoints, nPoints);
//		g2D.setFont(new Font("Comic Sans", Font.ITALIC, 25));
//		g2D.setColor(Color.MAGENTA);
//		g2D.drawString("STONKS", 100, 100);
		
//		int[] x = {100, 200, 300};
//		int[] y = {300, 127, 300};
//		g2D.setColor(Color.YELLOW);
//		g2D.fillPolygon(x,y,3);
//		g2D.setColor(Color.PINK);
//		g2D.fillRect(100, 100, 200, 200);
		
//		g2D.fillOval(50, 50, 100, 100);
		GradientPaint paint = new GradientPaint(0,0,Color.RED, 420, 0, Color.BLUE);
		g2D.setPaint(paint);
		g2D.drawArc(50, 50, 300, 300, 0, 270);
		
	}
}
