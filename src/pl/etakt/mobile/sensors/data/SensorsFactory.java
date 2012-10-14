package pl.etakt.mobile.sensors.data;

import java.util.List;

import android.hardware.Sensor;
import android.util.Log;
import pl.etakt.mobile.sensors.SensorsActivity;

public class SensorsFactory {

	public static MySensorsManager mySensorsManager;
	public static SensorsList sensorsList;
	public static SensorsActivity sensorsActivity;

	private static String TAG = "SensorsFactory";
	private Boolean WARNING = false;

	public SensorsFactory() {
		Log.i(TAG, "SensorsFactory object created");
	}

	public Boolean stopAll() {
		Log.i(TAG, "stopAll() action called");
		try {
			return sensorsList.stopAll();
		} catch (Exception exc) {
			Log.w(TAG, exc.toString());
		}
		return WARNING ;
	}

}
