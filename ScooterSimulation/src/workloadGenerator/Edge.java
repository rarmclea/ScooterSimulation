package workloadGenerator;

public class Edge {
	int v1ID, v2ID, numScooters, id;
	double distance;
	
	public Edge(int id, int v1, int v2, double dist ){
		this.id = id;
		v1ID = v1;
		v2ID = v2;
		distance = dist;
		numScooters = 0;
	}

}
