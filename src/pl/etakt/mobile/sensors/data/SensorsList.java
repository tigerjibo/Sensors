package pl.etakt.mobile.sensors.data;

import java.util.List;

import pl.etakt.mobile.sensors.R;
import pl.etakt.mobile.sensors.SensorsActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

/***
 * 
 * Lista sensorow
 * 
 * @since 1.0
 * @author Oskar
 *
 */
public class SensorsList {
	
	private SensorManager sensorManager;
	private int count;
	
	private SensorsActivity sensorsActivityInstance;
	protected static SensorsList instance;
	
	private List<Sensor> sensorList;
	
	private final static String TAG = "SensorsList";
	
	public SensorsList(SensorsActivity sensorsActivityInstance){
		this.sensorsActivityInstance = sensorsActivityInstance;
		instance = this;
		Log.d(TAG, "SensorsList object created");
	}
	
	public Handler getCatMessenger(){
		return sensorsActivityInstance.getUpdater();
	}
	
	public List<Sensor> getSensorList(){
		return sensorList;
	}
	
	public void setSensorList(List<Sensor> sensorList){
		this.sensorList = sensorList;
	}
	
	public int getCount(){
		return count;
	}
	
	public void init(){
	    // Get the SensorManager 
		sensorManager = (SensorManager) sensorsActivityInstance.getSystemService(sensorsActivityInstance.SENSOR_SERVICE);

	    // List of Sensors Available
		sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

	    // Print how may Sensors are there
		count = sensorList.size();
	    
	    Log.d(TAG, "Size of sensorlist collection: " + count);
	    
	    for(Sensor s : sensorList){
	    	Log.d(TAG, "Sensor name is: " + s.getName());
	    	Log.d(TAG, "Sensor type is: " + s.getType());
	    }
	}
	
	public void init_listeners(){
		
		SensorEventListener sel = new SensorEventListener(){
			Handler h = getCatMessenger();
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				/* Isn't required for this example */

			}
			public void onSensorChanged(SensorEvent event) {
				/* Write the accelerometer values to the TextView */
				float[] values = event.values;
				//accText.setText("x: "+values[0]+"\ny: "+values[1]+"\nz: "+values[2]);
				h.sendMessage(h.obtainMessage(event.sensor.getType(), values));
			}

		};
		
		for(Sensor s : sensorList){
	    	Log.d(TAG, "Sensor name is: " + s.getName() + " registering listeners...");
	    	sensorManager.registerListener(sel, s, SensorManager.SENSOR_DELAY_NORMAL);
	    	Log.i(TAG, "Sensor registered");
	    	Log.d(TAG, "Sensor type is: " + s.getType());
	    }
	}

}
