package workloadGenerator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GraphicsPanel extends JPanel{	
	
	int[][] points = new int[0][0];
	int[] pointScooters = new int[0];
	int[][] edges = new int[0][0];
	int[] edgeScooters = new int [0];
	int increment = 0;
	

	public void initialize(LocationGraph l, int numScooters){
		points = new int[l.vertices.size()][2];
		pointScooters = new int[l.vertices.size()];
		edges = new int[l.edges.size()][2];
		edgeScooters = new int[l.edges.size()];
		for (int i = 0; i < l.vertices.size(); i ++){
			Vertex v = l.vertices.get(i);
			points[i][0] = v.y;
			points[i][1] = v.x;
			pointScooters[i] = v.scooters.size();
			
		}
		for (int i = 0; i <l.edges.size(); i++){
			Edge e = l.edges.get(i);
			edges[i][0] = e.v1ID-1;
			edges[i][1] = e.v2ID-1;
			edgeScooters[i] = e.numScooters;
		}
		increment = 50 / Math.max(numScooters / 100, 1);
		
	}
	public void updateScooters(int index, int numScooters){
		pointScooters[index] = numScooters;
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(new Color(18, 52, 86));
		
		Graphics2D g2D = (Graphics2D) g;	
		g2D.setStroke(new BasicStroke(3));
		g2D.setColor(Color.GRAY);
		for (int i = 0; i < edges.length; i++){
			g2D.setColor(new Color(225, Math.max(100, 225 - edgeScooters[i]*2*increment), Math.max(25,225 - edgeScooters[i] * 2*increment)));
			int p1 = edges[i][0];
			int p2 = edges[i][1];
			g2D.drawLine(points[p1][0], points[p1][1], points[p2][0], points[p2][1]);
		}
		int vertexSize = 20;
		for (int i = 0; i < points.length; i++){
			int intensity = pointScooters[i] * increment;
			g2D.setColor(new Color(Math.max(225 - intensity,25),Math.max(225 - intensity,25),250));
			g2D.fillOval(points[i][0]-vertexSize/2, points[i][1]-vertexSize/2, vertexSize, vertexSize);
			g2D.setColor(intensity > 150? Color.WHITE : Color.BLACK);
			g2D.drawString(""+pointScooters[i], points[i][0]-4, points[i][1]+4);
		} 
		
	}
	public void updateEdge(int index, int numScooters) {
		edgeScooters[index] = numScooters;
		
	}

}
