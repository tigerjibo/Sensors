package pl.etakt.mobile.sensors.data;

import java.util.List;

import pl.etakt.mobile.sensors.R;
import pl.etakt.mobile.sensors.SensorsActivity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorsList {
	
	private SensorManager mSensorManager;
	private int how_many;
	
	private SensorsActivity instance;
	
	private final static String TAG = "SensorsList";
	
	public SensorsList(SensorsActivity instance){
		this.instance = instance;
		Log.d(TAG, "SensorsList object created");
	}
	
	public int how_many(){
		return how_many;
	}
	
	public void init(){
	    // Get the SensorManager 
	    mSensorManager= (SensorManager) instance.getSystemService(instance.SENSOR_SERVICE);

	    // List of Sensors Available
	    List<Sensor> msensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

	    // Print how may Sensors are there
	    how_many = msensorList.size();
	    
	    Log.d(TAG, "size of sensorlist: " + how_many);
	    
	    for(Sensor s : msensorList){
	    	Log.d(TAG, "!! name s: " + s.getName());
	    	Log.d(TAG, "!! type s: " + s.getType());
	    }
	}

}