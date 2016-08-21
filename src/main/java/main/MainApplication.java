package main;

import javax.swing.UIManager;

import view.searchPluggedInDevice;

public class MainApplication {
	
	
	public static void main(String[] args) {
		try { 
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		new searchPluggedInDevice();
		
		
	
	}
}


