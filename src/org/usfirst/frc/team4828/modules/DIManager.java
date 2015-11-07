package org.usfirst.frc.team4828.modules;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.DigitalInput;

public class DIManager {
	private Map<String, DigitalInput> limits;
	
	private DIManager() {
		limits = new HashMap<String, DigitalInput>();
	}
	
	public static final DIManager INSTANCE = new DIManager();
	
	public void add(String name, int port){
		limits.put(name, new DigitalInput(port));
	}
	
	public boolean get(String name){
		DigitalInput result = limits.get(name);
		if(result != null)
			return limits.get(name).get();
		else {
			System.out.println("ERROR: LIMITMANAGER - NAME NOT FOUND IN MAP " + name);
			return true;
		}
	}
}
