package pl.etakt.mobile.sensors.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.joda.time.DateTime;

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
	private SensorsFactory sensorsFactory;
	protected static SensorsList instance;

	private List<Sensor> sensorList;
	private List<SensorEventListener> sensorListenersList;
	private Boolean ACTION_SUCCESS = true;

	private final static String TAG = "SensorsList";

	public SensorsList(SensorsActivity sensorsActivityInstance) {
		this.sensorsActivityInstance = sensorsActivityInstance;
		instance = this;
		sensorListenersList = new ArrayList<SensorEventListener>();
		Log.d(TAG, "SensorsList object created");
	}

	public Handler getCatMessenger() {
		return sensorsActivityInstance.getUpdater();
	}

	public List<Sensor> getSensorList() {
		return sensorList;
	}

	public void setSensorList(List<Sensor> sensorList) {
		this.sensorList = sensorList;
	}

	public int getCount() {
		return count;
	}

	public void init() {
		// Get the SensorManager
		sensorManager = (SensorManager) sensorsActivityInstance
				.getSystemService(sensorsActivityInstance.SENSOR_SERVICE);

		// List of Sensors Available
		sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);

		// Print how may Sensors are there
		count = sensorList.size();

		Log.d(TAG, "Size of sensorlist collection: " + count);

		for (Sensor s : sensorList) {
			Log.d(TAG, "Sensor name is: " + s.getName());
			add_to_containers(s);
			Log.d(TAG, "Sensor type is: " + s.getType());
		}
	}

	@SuppressWarnings(value = "static-access")
	private void add_to_containers(Sensor s) {

		switch (s.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			sensorsFactory.mySensorsManager.setACCELEROMETER(s);
			break;
		case Sensor.TYPE_GRAVITY:
			sensorsFactory.mySensorsManager.setGRAVITY(s);
			break;
		case Sensor.TYPE_GYROSCOPE:
			sensorsFactory.mySensorsManager.setGYROSCOPE(s);
			break;
		case Sensor.TYPE_LIGHT:
			sensorsFactory.mySensorsManager.setLIGHT(s);
			break;
		case Sensor.TYPE_LINEAR_ACCELERATION:
			sensorsFactory.mySensorsManager.setLINEAR_ACCELERATION(s);
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			sensorsFactory.mySensorsManager.setMAGNETIC_FIELD(s);
			break;
		case Sensor.TYPE_ORIENTATION:
			sensorsFactory.mySensorsManager.setORIENTATION(s);
			break;
		case Sensor.TYPE_PRESSURE:
			sensorsFactory.mySensorsManager.setPRESSURE(s);
			break;
		case Sensor.TYPE_PROXIMITY:
			sensorsFactory.mySensorsManager.setPROXIMITY(s);
			break;
		case Sensor.TYPE_ROTATION_VECTOR:
			sensorsFactory.mySensorsManager.setROTATION_VECTOR(s);
			break;
		case Sensor.TYPE_TEMPERATURE:
			sensorsFactory.mySensorsManager.setTEMPERATURE(s);
			break;
		default:

			break;
			
		}
	}

	public void init_listeners() {

		for (Sensor s : sensorList) {
			
			SensorEventListener sel = new SensorEventListener() {
				Handler h = getCatMessenger();

				public void onAccuracyChanged(Sensor sensor, int accuracy) {
					/* Isn't required for this example */
					Log.i(TAG, "Accurancy of sensor " + sensor.getName()
							+ " changed to " + accuracy);
				}

				public void onSensorChanged(SensorEvent event) {
					/* Write the accelerometer values to the TextView */
					float[] values = { event.values[0], event.values[1], event.values[2], event.timestamp };
					//d = ;
					// accText.setText("x: "+values[0]+"\ny: "+values[1]+"\nz: "+values[2]);
					/*h.sendMessage(h.obtainMessage(event.sensor.getType(), (new DateTime(event.timestamp)).getSecondOfDay(),
							event.accuracy, values));*/
					/*h.sendMessage(h.obtainMessage(event.sensor.getType(), (int) event.timestamp,
							event.accuracy, values));*/
					h.sendMessage(h.obtainMessage(event.sensor.getType(), (DateTime.now()).getSecondOfDay(),
							event.accuracy, values));
				}

			};
			
			Log.d(TAG, "Sensor name is: " + s.getName()
					+ " registering listeners...");
			sensorManager.registerListener(sel, s,
					SensorManager.SENSOR_DELAY_NORMAL);
			Log.i(TAG, "Sensor registered");
			sensorListenersList.add(sel);
			Log.d(TAG, "Sensor type is: " + s.getType());
		}
	}
	
	public Boolean stopAll(){
		for(SensorEventListener s : sensorListenersList){
			sensorManager.unregisterListener(s);
		}
		return ACTION_SUCCESS ;
	}

}
