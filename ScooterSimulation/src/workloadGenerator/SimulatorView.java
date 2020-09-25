package workloadGenerator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class SimulatorView extends JFrame{
	JLabel inputFileLabel, numScootersLabel, maxDistanceLabel, chargingThresholdLabel, numDaysLabel, maxWalkingDistanceLabel,
			chargingShiftTimesLabel;
	JTextField inputFileInput, numScootersInput, maxDistanceInput, chargingThresholdInput, numDaysInput, maxWalkingDistanceInput,
			chargingShiftTimesInput;
	JButton runSimulator;
	JTextArea displaySimulation, displaySimulationResults;
	JScrollPane jsp2;
	GraphicsPanel graphics;

	final int WIDTH =1800;
	final int HEIGHT = 650;
	final Color BACKGROUND = new Color(235,235,235);
	final Color PANEL = new Color(225,225,225);
	final Color BORDER = new Color(215,215,215);


	/**
	*Constructor for GreenhouseView objects
	*/
	public SimulatorView(){
		super();
		setSize(WIDTH, HEIGHT);
		setTitle("Scooter Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		Container c = getContentPane();
		c.setBackground(BACKGROUND);
		c.setLayout(new BorderLayout());
		//Temperature
		JPanel simulationInputs = new JPanel();
		simulationInputs.setLayout(new GridLayout(0,1));
		simulationInputs.setBackground(PANEL);
		simulationInputs.setBorder(new LineBorder(BORDER,3, true));
		JLabel inputTitle = new JLabel("INPUT", JLabel.LEFT);
		inputTitle.setFont(new Font("Courier", Font.BOLD,12));
		JPanel input1 = new JPanel();
		input1.setBackground(PANEL);
		inputFileLabel = new JLabel("Input File: ");
		inputFileInput = new JTextField(Simulator.defaultInputFile, 5);
		JPanel input2 = new JPanel();
		input2.setBackground(PANEL);
		numScootersLabel = new JLabel("Number of Scooters:");
		numScootersInput = new JTextField("" + Simulator.defaultNumScooters, 5);
		JPanel input3 = new JPanel();
		input3.setBackground(PANEL);
		maxDistanceLabel = new JLabel("Maximum distance per battery charge (meters):");
		maxDistanceInput = new JTextField("" + Simulator.defaultRange, 5);
		JPanel input4 = new JPanel();
		input4.setBackground(PANEL);
		chargingThresholdLabel = new JLabel("Battery % to begin chargin scooters (between 0 and 1): ");
		chargingThresholdInput = new JTextField("" + Simulator.defaultChargingThreshold, 5);
		JPanel input8 = new JPanel();
		input8.setBackground(PANEL);
		chargingShiftTimesLabel = new JLabel("Charging shift times:");
		chargingShiftTimesInput = new JTextField("5,12,19", 5);
		JPanel input5 = new JPanel();
		input5.setBackground(PANEL);
		numDaysLabel = new JLabel("Number of days simulated: ");
		numDaysInput = new JTextField("" + Simulator.defaultNumDays, 5);
		JPanel input6 = new JPanel();
		input6.setBackground(PANEL);
		runSimulator = new JButton("Run Simulation");
		JPanel input7 = new JPanel();
		input7.setBackground(PANEL);
		maxWalkingDistanceLabel = new JLabel("Maximum walking distance: ");
		maxWalkingDistanceInput = new JTextField("" + Simulator.defaultMaxWalkingDistance, 5);
		//add simulation input components
		input1.add(inputFileLabel);
		input1.add(inputFileInput);
		input2.add(numScootersLabel);
		input2.add(numScootersInput);
		input3.add(maxDistanceLabel);
		input3.add(maxDistanceInput);
		input4.add(chargingThresholdLabel);
		input4.add(chargingThresholdInput);
		input8.add(chargingShiftTimesLabel);
		input8.add(chargingShiftTimesInput);
		input5.add(numDaysLabel);
		input5.add(numDaysInput);
		input6.add(runSimulator);
		input7.add(maxWalkingDistanceLabel);
		input7.add(maxWalkingDistanceInput);
		
		

		simulationInputs.add(inputTitle);
		simulationInputs.add(input1);
		simulationInputs.add(input2);
		simulationInputs.add(input3);
		simulationInputs.add(input4);
		simulationInputs.add(input8);
		simulationInputs.add(input5);
		simulationInputs.add(input7);
		simulationInputs.add(input6);
		
		//Outputs
		JPanel simulationOutputs = new JPanel();
		simulationInputs.setLayout(new GridLayout(0,1));
		simulationOutputs.setBorder(new LineBorder(BORDER,3, true));
		simulationOutputs.setBackground(PANEL);
		JLabel outputTitle = new JLabel("RESULTS", JLabel.LEFT);
		outputTitle.setFont(new Font("Courier", Font.BOLD,12));
		outputTitle.setBackground(PANEL);
		
		displaySimulationResults = new JTextArea(17,33);
		displaySimulationResults.setBackground(PANEL);
		JScrollPane jsp1 = new JScrollPane(displaySimulationResults);
		jsp1.setBorder(new LineBorder(BORDER,0, true));
		
		simulationOutputs.setBackground(BACKGROUND);
		simulationOutputs.setLayout(new BorderLayout());
		simulationOutputs.add(outputTitle);
		simulationOutputs.add(jsp1, BorderLayout.SOUTH);
		
		JPanel simulation = new JPanel();
		simulation.setBackground(PANEL);
		displaySimulation = new JTextArea(15, 70);
		jsp2 = new JScrollPane(displaySimulation);
		jsp2.setBackground(Color.WHITE);
		jsp2.setBorder(new LineBorder(BORDER, 3, true));
		simulation.add(jsp2, BorderLayout.CENTER);

		graphics = new GraphicsPanel();
		//Canvas canvas = new Canvas();
		//graphics.add(canvas);
		graphics.setBackground(Color.PINK);
		
		//Display
		JPanel display = new JPanel();
		display.setBackground(BACKGROUND);
		display.add(simulationInputs);
		display.add(Box.createRigidArea(new Dimension(0,5)));
		display.add(simulationOutputs);
		
		JPanel gui = new JPanel();
		gui.setLayout(new GridLayout(2, 1));
		
		
		
		//Add main panels to content pane
		gui.add(display);
		gui.add(simulation);
		gui.setPreferredSize(new Dimension(820,650));
		graphics.setMinimumSize(new Dimension(820,650));
		c.add(gui, BorderLayout.WEST);
		c.add(graphics, BorderLayout.CENTER);
	}
}
