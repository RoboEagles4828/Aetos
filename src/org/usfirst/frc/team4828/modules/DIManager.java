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
	
	public void add(String key, int port){
		limits.put(key, new DigitalInput(port));
	}

	public boolean get(String key){
		DigitalInput result = limits.get(key);
		if(result != null)
			return result.get();
		else {
			System.out.println("ERROR: LIMITMANAGER - KEY NOT FOUND IN MAP " + key);
			return true;
		}
	}
	
	public String getDebugString(){
		return limits.toString();
	}
}
