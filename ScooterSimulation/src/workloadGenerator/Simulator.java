package workloadGenerator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Simulator{
	//Simulation Tools
	SimulatorView view;
	Simulation sim;
	int simCount = 0;
	
	static double defaultRange = 32186.88;
	static double  defaultChargingThreshold = 0.25;
	static int[] defaultChargingTimes = {5,12,19};
	static int defaultNumDays = 28;
	static double defaultMaxWalkingDistance = 200;//m
	static int defaultNumScooters = 100;
	static String defaultInputFile = "inputs1";

	
	
	
	public static void main(String[] args){

		Simulator s = new Simulator();
	}
	
	public Simulator(){
		view = new SimulatorView();
		view.setVisible(true);	
		view.runSimulator.addActionListener(new RunListener());
	}
	
	public class RunListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				view.displaySimulation.setText("");
				int numScooters = Integer.parseInt(view.numScootersInput.getText());
				double range = Double.parseDouble(view.maxDistanceInput.getText());
				double chargingThreshold = Double.parseDouble(view.chargingThresholdInput.getText());
				String[] split = view.chargingShiftTimesInput.getText().split(",");
				int[] chargingTimes = new int[split.length];
				for (int i = 0; i < split.length; i++){
					chargingTimes[i] = Integer.parseInt(split[i].trim());
				}
				double stopTime = Integer.parseInt(view.numDaysInput.getText()) * 86400;
				double maxWalkingDistance = Double.parseDouble(view.maxWalkingDistanceInput.getText());
				String filename = view.inputFileInput.getText();
				simCount++;
				view.displaySimulationResults.append("============= Simulation #" + simCount + " =============\n");
				view.displaySimulationResults.setCaretPosition(view.displaySimulationResults.getDocument().getLength());
				view.displaySimulation.setText(null);
				sim = new Simulation(numScooters, range, chargingThreshold, stopTime, maxWalkingDistance, chargingTimes, filename, view.displaySimulation, view.displaySimulationResults, view.graphics);
				sim.start();
				
			}catch (NumberFormatException e2){
				view.displaySimulationResults.append("Error reading simulation values\n");
			}
		}
	}

}
