package workloadGenerator;

public class Event {
	public char value;
	public double time;
	public Scooter scooter;
	public int trip;
	public int location;
	public Event next;
	
	public Event(char value, double time, int trip, Scooter scooter, int location) {
		this.value = value;
		this.time = time;
		this.trip = trip;
		this.scooter = scooter;
		this.location = location;
	}
	
	public Event(char value, double time){
		this.value = value;
		this.time = time;
	}
}