package workloadGenerator;

public class Trip {
	int id;
	//int scooterId;
	double duration;
	double distance;
	int day;
	int hour;
	int date;
	
	public Trip(int date, int day, int hour){
		this.date = date;
		this.day = day;
		this.hour = hour;
	}
}
