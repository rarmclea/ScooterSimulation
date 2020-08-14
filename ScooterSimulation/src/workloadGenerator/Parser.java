package workloadGenerator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
	private BufferedReader in;
	
	public Parser(String filename) throws FileNotFoundException{
		in = new BufferedReader(new FileReader(filename));
	}
	
	public int readIntValue() throws IOException{
		String value = in.readLine().split(":")[1].trim();
		return Integer.parseInt(value);
	}
	
	public double readDoubleValue() throws IOException{
		String value = in.readLine().split(":")[1].trim();
		return Double.parseDouble(value);
	}
	
	public double[][] readSpeedDistribution() throws NumberFormatException, IOException{
		double[][] speedDist = new double[2][20];
		String line;
		int i = 0;
		while ((line = in.readLine()) != null){
			if (line.contains("</Speed Distribution>")) break;
			String[] split = line.split("\t");
			speedDist[0][i] = Double.parseDouble(split[0]);
			speedDist[1][i] = Double.parseDouble(split[1]);
			i++;
		}
		return speedDist;
	}
	
	public double[][] readTimes() throws NumberFormatException, IOException{
		double[][] meanIntertripTimes = new double[7][24];
		String line;
		String[] split;
		int i = 0;
		while ((line = in.readLine()) != null){
			if (line.contains("</Intertrip Time Distribution>")) break;
			split = line.split("\t");
			for (int j = 0; j < 7; j++){
				meanIntertripTimes[j][i] = Double.parseDouble(split[j]);
			}	
			i++;
		}
		return meanIntertripTimes;
	}	
	
	public LocationGraph readGraph() throws IOException{
		LocationGraph l = new LocationGraph();
		String line; 
		String[] values;
		while ((line = in.readLine()) != null){
			if (line.contains("</Location Graph>")) break;
			String name = "";
			values = line.split("\t");
			if (values[0].equals("v")){
//				for (int i = 5; i < values.length; i++){
//					name += values[i];
//					if (i < values.length - 1){
//						name += " ";
//					}
//				}
				l.addVertex(new Vertex(values[5], Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4])));
			}
			if (values[0].equals("e")){
				l.addEdge(new Edge(Integer.parseInt(values[1]), Integer.parseInt(values[2]),Double.parseDouble(values[3])));
			}
		}
		return l;
	}

	public String getLine() throws IOException {
		return in.readLine();
	}
	

}
