package workloadGenerator;

import java.util.Random;

//have to generate arrival times, stay times, and location choices
public class RNG {
	Random duration;
	Random location;
	Random time;
	double meanTripDuration;
	double meanTripDistance;
	
	public RNG( double meanDuration, double meanDistance){
		duration = new Random(1234567);
		location = new Random(2345679);
		time = new Random(3456789);
		meanTripDuration = meanDuration;
		meanTripDistance = meanDistance;
	}
	
	double newTripTime(double meanIntertripTime){
		return exponentialVariate(meanIntertripTime, time);
	}
	
//	double[] tripDurationDistance(){
//		double[] stats = new double[2];
//		double u = uniform01(duration);
//		
//		stats[0] = -(meanTripDuration)*Math.log(u);
//		stats[1] = -(meanTripDistance)*Math.log(u);
//		return stats;
//	}
	
//	double tripDuration(){
//		return exponentialVariate(meanTripDuration, duration);
//	}
//	double tripSpeed(){
////		double u = exponentialVariate(meanTripSpeed,speed);
//		double u = speed.nextGaussian() + meanTripSpeed;
//		return u < maxSpeed? maxSpeed: u;
//	}

	double location(){
		return uniform01(location);
	}
	
	double uniform01(Random r){
		return Math.abs(r.nextInt())/(double)Integer.MAX_VALUE;
	}
	
	double exponentialVariate(double mean, Random r){
		double u = uniform01(r);
		return -(mean)*Math.log(u);
		
	}

	public double speed(double[][] speedDist) {
		int length = speedDist[0].length;
		double u = uniform01(duration);
		for (int i = 0; i < length; i++){
			if (u <= speedDist[1][i]){
				return speedDist[0][i];
			}
		}
		return speedDist[0][length-1];
	}
}
