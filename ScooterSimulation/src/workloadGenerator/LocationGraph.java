package workloadGenerator;

import java.util.ArrayList;


public class LocationGraph {
	public ArrayList<Vertex> vertices;
	public ArrayList<Edge> edges;
	
	public LocationGraph(){
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
	}
	
	public void addVertex(Vertex v){
		vertices.add(v);
	}
	
	public void addEdge(Edge e){
		edges.add(e);
	}

	
	public Edge getEdge(int v1, int v2){
		for (Edge e: edges){
			if ((e.v1ID == v1 && e.v2ID == v2) || (e.v1ID == v2 && e.v2ID == v1)){
				return e;
			}
		}
		return null;
	}
	
	public boolean isEdge(int v1, int v2){
		for (Edge e: edges){
			if ((e.v1ID == v1 && e.v2ID == v2) || (e.v1ID == v2 && e.v2ID == v1)){
				return true;
			}
		}
		return false;
	}
	
	private int minDistance(double dist[], boolean processed[]){
		double min = Double.MAX_VALUE;
		int minIndex = -1;
		for (int i = 0; i < dist.length; i++){
			if (dist[i] < min && processed[i] == false){
				min = dist[i];
				minIndex = i;
			}
		}
		return minIndex;
	}
	

	public int[] getPath(int v1, int v2){
		if (v1 == v2) return null;
		//Dijkstra's
		int prev[] = new int[vertices.size()];
		double dist[] = new double[vertices.size()];
		boolean processed[] = new boolean[vertices.size()];
		for (int i = 0; i < vertices.size(); i++){
			if (i == (v1)){
				dist[i] = 0;
			} else {
				dist[i] = Double.MAX_VALUE;
			}
			prev[i] = -1;
			processed[i] = false;
		}
		
		for (int i = 0; i < vertices.size()-1; i++){
			int u = minDistance(dist, processed);
			processed[u] = true;
			if (u == v2) break;
			for (int v = 0; v < vertices.size(); v ++){
				Edge e = getEdge(u+1,v+1);
				if (!processed[v] && e != null && dist[u] + e.distance < dist[v]){
					dist[v] = dist[u]+e.distance;
					prev[v] = u;
				}
			}
		}
		
		
		return prev;
		
	}
	
	public double getDist(int v1, int v2){
		if (v1 == v2) return 0;
		int[] prev = getPath(v1, v2);
		
		double pathDistance = 0;
		int index = (v2);
		while (prev[index] != -1){
			pathDistance += getEdge(index+1, prev[index]+1).distance;
			index = prev[index];
		}
		return pathDistance;
		
	}
	
	public String getVertexName(int id){
		return vertices.get(id).name;
	}
	
	public Vertex getVertex(int id){
		return vertices.get(id);
	}
	
	public Vertex getFavouritVertex(){
		int favouriteWeight = -1;
		Vertex favourite = vertices.get(0);
		for (Vertex v : vertices){
			if (v.weight>favouriteWeight){
				favouriteWeight = v.weight;
				favourite = v;
			}
		}
		return favourite;
		
	}

	public int getNumLocations() {
		return vertices.size();
	}

	public int getSumLocationWeights() {
		int sum = 0;
		for (Vertex v : vertices){
			sum += v.weight;
		}
		return sum;
	}

	
	public Vertex getWeightedRandomVertex(double r, int current, boolean countScooters) {
        double completeWeight = 0.0;
        for (Vertex v : vertices){
        	if (v.id == current){
        		continue;
        	}
            completeWeight += v.weight;
            if (countScooters) completeWeight += v.scooters.size();
        }
        r *= completeWeight;
        double countWeight = 0.0;
        for (Vertex v : vertices) {
        	if (v.id == current){
        		r+=v.weight;
        	}
            countWeight += v.weight;
            if (countScooters) countWeight += v.scooters.size();
            if (countWeight >= r)
                return v;
        }
        throw new RuntimeException("Should never be shown.");
    }
	public ArrayList<Vertex> getNearestVertices(int root, double maxDist){
		ArrayList<Vertex> neighbours = new ArrayList<Vertex>();
		int prev[] = new int[vertices.size()];
		double dist[] = new double[vertices.size()];
		boolean processed[] = new boolean[vertices.size()];
		for (int i = 0; i < vertices.size(); i++){
			if (i == root){
				dist[i] = 0;
			} else {
				dist[i] = Double.MAX_VALUE;
			}
			processed[i] = false;
		}
		
		for (int i = 0; i < vertices.size()-1; i++){
			int u = minDistance(dist, processed);
			processed[u] = true;
			for (int v = 0; v < vertices.size(); v ++){
				Edge e = getEdge(u+1,v+1);
				if (!processed[v] && e != null && dist[u] + e.distance < dist[v]){
					dist[v] = dist[u]+e.distance;
				}
			}
		}

		for (int i = 0; i < vertices.size(); i++){
			double minValue = Double.MAX_VALUE;
			int minIndex = -1;
			for (int j = 0; j < vertices.size(); j++){
				if (dist[j] > maxDist) continue;
				else if (dist [j] < minValue){
					minValue = dist[j];
					minIndex = j;
				}
			}
			if (minIndex == -1) break;
			neighbours.add(vertices.get(minIndex));
			dist[minIndex] = Double.MAX_VALUE;
		}
		
		return neighbours;
	}

	public ArrayList<Vertex> getNeighbours(int id) {
		ArrayList<Vertex> neighbours = new ArrayList<Vertex>();
		for (Edge e : edges){
			if (e.v1ID == id){
				neighbours.add(getVertex(e.v2ID));
			} else if (e.v2ID == id){
				neighbours.add(getVertex(e.v1ID));
			}
		}
		return neighbours;
	}
}

