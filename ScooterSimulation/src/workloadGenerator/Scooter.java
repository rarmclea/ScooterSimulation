package workloadGenerator;

public class Scooter {
	static int scooterCounter;
	int id;
	double remainingBattery = 1.0;
	double distanceTravelled = 0;
	float revenue = 0;
	double rate;
	boolean inUse = false;
	int currentLocation;
	boolean charging = false;
	int numTimesRecharged = 0;
	
	public Scooter(){
		id = scooterCounter++;
	}
	
	public Scooter(int currentLocation){
		id = scooterCounter++;
		this.currentLocation = currentLocation;
	}

	

}
