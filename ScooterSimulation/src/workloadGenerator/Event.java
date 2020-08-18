package workloadGenerator;

public class Event {
	public char value;
	public double time;
	public Scooter scooter;
	public int trip;
	public int[] path;
	public int location;
	public Event next;
	
	public Event(char value, double time, int trip, int[] path, Scooter scooter, int location) {
		this.value = value;
		this.time = time;
		this.trip = trip;
		this.path = path;
		this.scooter = scooter;
		this.location = location;
	}
	
	public Event(char value, double time){
		this.value = value;
		this.time = time;
	}
}