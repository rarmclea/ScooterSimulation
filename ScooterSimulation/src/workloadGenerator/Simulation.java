package workloadGenerator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class Simulation extends Thread{
	//Simulation Tools
	EventList eventList;
	RNG random;
	BufferedReader tripTimeReader;
	BufferedWriter tripStats;
	BufferedWriter scooterStats;
	String[] days = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
	LocationGraph locations;
	JTextArea out, results;
	GraphicsPanel display;
	
	private boolean DEBUG = true;
	
	//Simulation Variables
	double meanTripDistance = 1846.29; //meters
	double meanTripDuration = 771.033; //seconds
	double[][] meanIntertripTimes = new double[7][24];
	double meanIntertripTime;
	double range = 32186.88;
	static double defaultRange = 32186.88;
	double maxSpeed = 8.9; //m/s
	double avgSpeed = 2.761345643;
	static double  defaultChargingThreshold = 0.25;
	double chargingThreshold = 0.25;
	static int defaultNumDays = 28;
	double[][] speedDist;
	static double defaultWalkingRadius = 200;//m
	double walkingRadius;
	
	double stopTime = defaultNumDays * 86400; //x * day
	
	static int defaultNumScooters = 100;
	int numScooters = 100;
	
	static String defaultInputFile = "inputs1";
	
	//State variables
	int numScootersInUse = 0;
	int hour = 0;
	int date = 0;
	int day = 0;
	
	//Statistic Variables
	double totalDistance = 0;
	double totalTime = 0;
	int numTrips = 0;
	int numFailedTrips = 0;
	int numWalkTrips = 0;
	double distanceWalked = 0;
	
	public Simulation(int numScooters, double range, double chargingThreshold, double stopTime, double walkingRadius, 
					  String filename, JTextArea out, JTextArea results, GraphicsPanel graphics){
		random = new RNG(meanTripDuration, meanTripDistance);
		this.numScooters = numScooters;
		this.range = range;
		this.chargingThreshold = chargingThreshold;
		this.stopTime = stopTime;
		this.walkingRadius = walkingRadius;
		this.out = out;
		this.results = results;
		this.display = graphics;
		load(filename);
	}
	
	public void run(){
		try {
			tripStats = new BufferedWriter(new FileWriter("TripStatistics.dat"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		meanIntertripTime = meanIntertripTimes[0][0];
		for (int i = 0; i < numScooters; i++){
			Vertex v = locations.getWeightedRandomVertex(random.location(), -1, false);
			v.scooters.add(new Scooter(v.id));
		}

		display.initialize(locations, numScooters);
		display.repaint();
		eventList = new EventList();
		eventList.add(new Event('X', stopTime));
		eventList.add(new Event('S', meanIntertripTime));
		Event current;
		while ((current = eventList.getEvent()).value != 'X'){
			if (current.value == 'S'){
				handleTripStart(current);
			} else if (current.value == 'E'){
				handleTripEnd(current);
			}
			if (current.time - date * 86400 > (hour + 1) * 3600){
				hour = (hour + 1) % 24;
				if (hour == 22){
					for (Vertex v : locations.vertices){
						for (Scooter s : v.scooters){
							if (s.remainingBattery < chargingThreshold){
								s.charging = true;
							}
						}
					}
				} else if (hour == 6){
					for (Vertex v : locations.vertices){
						for (Scooter s : v.scooters){
							if (s.charging){
								s.charging = false;
								s.numTimesRecharged++;
								s.remainingBattery = 0.95;
							}
						}
					}
				}
			}
			if (current.time >= ((date + 1) * 86400)){
				date++;
				day = date % 7;
			}
			meanIntertripTime = meanIntertripTimes[day][hour];
//			if (current.time > next){
//				System.out.print("|");
//				next += increment;
//			}
//			try {
//				sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
		handleSimulationEnd();
		try {
			tripStats.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load(String filename){
		try {
			Parser parser = new Parser(filename);
			String line;
			while ((line = parser.getLine())!= null){
				if (line.contains("<Speed Distribution>")){
					speedDist = parser.readSpeedDistribution();
				} else if (line.contains("<Intertrip Time Distribution>")) {
					meanIntertripTimes = parser.readTimes();					
				} else if (line.contains("<Location Graph>")){
					locations = parser.readGraph();
				}
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void reset(){
		numScootersInUse = 0;
		hour = 0;
		day = 0;
		date = 0;
		
		totalDistance = 0;
		totalTime = 0;
		numTrips = 0;
		numFailedTrips = 0;
		numWalkTrips = 0;
		distanceWalked = 0;
		for (Vertex v : locations.vertices){
			v.scooters.clear();
		}
	}
	
	public void printScooters() {
		
		try {

			scooterStats = new BufferedWriter(new FileWriter("ScooterStatistics.dat"));
			scooterStats.write("ID\tInUse\tDistance\t\tRemaining Battery\t # Charges\tCharging\tRevenue\r\n");
			for (Vertex v : locations.vertices){
				for (Scooter s : v.scooters){
				scooterStats.write(s.id + "\t" + s.inUse + "\t" + s.distanceTravelled + "\t\t" + s.remainingBattery + "\t" + s.numTimesRecharged + "\t" + s.charging + "\t$" + s.revenue);
				scooterStats.newLine();
				}
			}

			scooterStats.close();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		
	}
}

	public String printTime(double time){
		int days = (int) time / 86400;
		int hours = (int) (time - days*86400) / 3600;
		int minutes = (int)(time - days * 86400 - hours * 3600)/60;
		int seconds = (int)(time - (days * 86400) - (hours * 3600) - (minutes * 60));
		return "Day " + days + " - " + hours + ":" + minutes + ":" + seconds;
	}
	
	public boolean tripStartHelper(Vertex origin, double time, int destination){
		Trip t = new Trip(date, day, hour);
		boolean success = false;
		for (Scooter s : origin.scooters){
			if (s.remainingBattery > 0 && !s.charging){
				success = true;
				s.inUse = true;
				numScootersInUse++;
				t.distance = locations.getDist(s.currentLocation, destination);
				t.duration = t.distance /random.speed(speedDist);
				s.distanceTravelled += t.distance;
				totalDistance += t.distance;
				totalTime += t.duration;
				s.revenue += (t.duration / 60) * 0.3 + 1;
				s.remainingBattery -= t.distance / range;
				numTrips++;
				origin.scooters.remove(s);
				eventList.add(new Event('E', time+t.duration, t.id, s, destination));

				if (DEBUG){
					out.append("\n" + printTime(time) + " --> Trip " + t.id + " started at " + locations.getVertexName(s.currentLocation) + " on scooter " + s.id);
					out.append("\n\t- scooters in use " + numScootersInUse + "/" + numScooters);
					out.append("\n\t- trip duration " + t.duration);
					out.append("\n\t- trip distance " + t.distance);
					out.setCaretPosition(out.getDocument().getLength());
				}
				try {
					tripStats.write(numTrips + "\t" + t.date + "\t" + days[t.day] + "\t" + t.hour + "\t" + t.duration + "\t" + t.distance + "\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}	
				break;
			}
			
		}
		return success;
	}
	
	public void handleTripStart(Event start){
		Vertex origin = locations.getWeightedRandomVertex(random.location(), -1, true);
		int destination = locations.getWeightedRandomVertex(random.location(), origin.id, false).id;
		boolean success = tripStartHelper(origin, start.time, destination);
		if (!success){

			double[] distances = locations.getShortestSpanningTree(origin.id);
			ArrayList<Vertex> neighbours = getNearestNeighbours(distances);
//			ArrayList<Vertex> neighbours = locations.getNeighbours(origin.id);

			for (Vertex v : neighbours){
				if (distances[v.id-1] > walkingRadius || v.id == destination){
					break;
				}
				success = tripStartHelper(v, start.time, destination);
				if (success) {
					distanceWalked += distances[v.id - 1];
//					distanceWalked += locations.getEdge(origin.id, v.id).distance;
					numWalkTrips++;
					break;
				}
			}
		}
		if (!success){
			numFailedTrips++;
			if (DEBUG){
				out.append("\n" + printTime(start.time) + " --> Trip failed: Scooter not available");
				out.setCaretPosition(out.getDocument().getLength());
			}
		}
		display.updateScooters(origin.id-1, origin.scooters.size());
		display.repaint();
		eventList.add(new Event('S', start.time+random.newTripTime(meanIntertripTime)));	
	}
	
	private ArrayList<Vertex> getNearestNeighbours(double[] distances) {
		double[] dist = new double[distances.length];
		for (int i = 0; i < distances.length; i++){
			dist[i] = distances[i];
		}
		ArrayList<Vertex> neighbours = new ArrayList<Vertex>();
		for (int i = 0; i < dist.length; i++){
			double minDist = Double.MAX_VALUE;
			int minIndex = -1;
			for (int j = 0; j < dist.length; j++){
				if (dist[j] < minDist && dist[j] != 0){
					minDist = dist[j];
					minIndex = j;
				}
			}
			if (minIndex == -1) continue;
			dist[minIndex] = Double.MAX_VALUE;
			neighbours.add(locations.getVertex(minIndex+1));			
		}
		
		return neighbours;
	}

	public void handleTripEnd(Event end){
		numScootersInUse--;
		Scooter s = end.scooter;
		s.inUse = false;
		s.currentLocation = end.location;
		Vertex v = locations.getVertex(end.location);
		v.scooters.add(s);
		display.updateScooters(v.id-1, v.scooters.size());
		display.repaint();
		if (DEBUG){
			out.append("\n" + printTime(end.time) + "--> Trip " + end.trip + " ended at " + locations.getVertexName(end.location) + " on scooter " + s.id);
			out.append("\n\t- remaining battery: " + s.remainingBattery);
			out.append("\n\t- scooters in use " + numScootersInUse + "/" + numScooters);
			out.setCaretPosition(out.getDocument().getLength());
			
		}
	}
	
	public void handleSimulationEnd(){
		double totalRevenue = 0;
		for (Vertex v : locations.vertices){
			for (Scooter s : v.scooters){
				totalRevenue += s.revenue;
			}
		}
		//System.out.println("\n============================================================\n");
		results.append("Simulation ended at " + printTime(stopTime));
		results.append("\nScooters in use: " + numScootersInUse + "/" + numScooters);
		results.append("\nAvg trips per day: " + numTrips/(date+1) + " (" + numTrips + " trips total)");
		results.append("\nAvg trip distance: " + totalDistance/numTrips + " m (" + totalDistance + " m total)");
		results.append("\nAvg trip duration: " + totalTime/numTrips + " s (" + totalTime + " s total)");
		results.append("\nAverage scooter revenue: " + totalRevenue/numScooters + "(" + totalRevenue + " total)");

		results.append("\n\nNumber of trips requiring walking: " + numWalkTrips);
		results.append("\nAverage distance walked: " + distanceWalked/numWalkTrips);
		
		results.append("\n\nNumber of failed trips: " + numFailedTrips + "\n\n");
		
		//unused scooters
		//unsatisfied demand
		//scooter distribution
		//walk time/distance 

//		printScooters();
		
	}

}
