package workloadGenerator;

import java.util.ArrayList;

public class Vertex {
	int id, weight, x, y;
	String name;
	ArrayList<Scooter> scooters;
	
	public Vertex(String name, int id, int weight,int x, int y){
		this.id = id;
		this.name = name;
		this.weight = weight;
		this.x = x;
		this.y = y;
		scooters = new ArrayList<Scooter>();
	}
}