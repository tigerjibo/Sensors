package pl.etakt.mobile.sensors.data;

import pl.etakt.mobile.sensors.SensorsActivity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

public class MySensorsManager {
	
	private SensorsActivity instance;
	
	private Sensor accelSensor = null;
	private Sensor magnetoSensor = null;
	private Sensor orientationSensor = null;
	private Sensor gyroscopeSensor = null;
	private Sensor lightSensor = null;
	private Sensor presureSensor = null;
	private Sensor temperatureSensor = null;
	private Sensor proximitySensor = null;
	private Sensor gravitySensor = null; //9
	private Sensor linearAccelerationSensor = null;
	private Sensor rotationVectorSensor = null;
	
	private static final String TAG = "MySensorsManager";
	
	private SensorManager sensors;
	
	public MySensorsManager(SensorsActivity instance){
		this.instance = instance;
	}
	
	public boolean initiate(){
		sensors = (SensorManager) instance.getSystemService(Context.SENSOR_SERVICE);
		
		accelSensor = sensors.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		return true;
	}

	/**
	 * @return the accelSensor
	 */
	public Sensor getAccelSensor() {
		return accelSensor;
	}

	/**
	 * @param accelSensor the accelSensor to set
	 */
	public void setAccelSensor(Sensor accelSensor) {
		this.accelSensor = accelSensor;
	}

}
