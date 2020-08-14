package workloadGenerator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GraphicsPanel extends JPanel{	
	
	int[][] points = new int[0][0];
	int[] scooters = new int[0];;
	int[][] edges = new int[0][0];;
	int increment = 0;
	

	public void initialize(LocationGraph l, int numScooters){
		points = new int[l.vertices.size()][2];
		scooters = new int[l.vertices.size()];
		edges = new int[l.edges.size()][2];
		for (int i = 0; i < l.vertices.size(); i ++){
			Vertex v = l.vertices.get(i);
			points[i][0] = v.y;
			points[i][1] = v.x;
			scooters[i] = v.scooters.size();
			
		}
		for (int i = 0; i <l.edges.size(); i++){
			Edge e = l.edges.get(i);
			edges[i][0] = e.v1ID-1;
			edges[i][1] = e.v2ID-1;
		}
		increment = 25;//(int) (250 / numScooters);
		
	}
	public void updateScooters(int index, int numScooters){
		scooters[index] = numScooters;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(new Color(18, 52, 86));
		
		Graphics2D g2D = (Graphics2D) g;	
		g2D.setStroke(new BasicStroke(3));
		g2D.setColor(Color.GRAY);
		for (int i = 0; i < edges.length; i++){
			int p1 = edges[i][0];
			int p2 = edges[i][1];
			g2D.drawLine(points[p1][0], points[p1][1], points[p2][0], points[p2][1]);
		}
		int vertexSize = 20;
		for (int i = 0; i < points.length; i++){
			int green = scooters[i] * increment;
			g2D.setColor(new Color(Math.max(250 - green,50), Math.min(green,200), 0));
			g2D.fillOval(points[i][0]-vertexSize/2, points[i][1]-vertexSize/2, vertexSize, vertexSize);
			g2D.setColor(Color.black);
			g2D.drawString(""+scooters[i], points[i][0]-4, points[i][1]+4);
		} 
		
	}

}
