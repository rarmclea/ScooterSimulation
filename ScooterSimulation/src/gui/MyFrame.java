package gui;

import javax.swing.JFrame;

public class MyFrame extends JFrame {
	
	GraphicsDemo graphicsDemo = new GraphicsDemo();
	
	public MyFrame(){
		this.setSize(420, 420);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.add(graphicsDemo);
		
		
	}
}
