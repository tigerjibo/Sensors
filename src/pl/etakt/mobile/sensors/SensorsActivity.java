package pl.etakt.mobile.sensors;

import java.util.List;

import pl.etakt.mobile.sensors.data.MySensorsManager;
import pl.etakt.mobile.sensors.data.SensorsList;
import pl.etakt.mobile.sensors.engine.AdMobShared;
import pl.etakt.mobile.sensors.engine.DebugIndicator;
import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class SensorsActivity extends Activity {

	private static final String TAG = "SensorsActivity";

	protected MySensorsManager mySensorsManager;
	private SensorsList list;
	private List<Sensor> listSensors;

	protected SensorsActivity instance;

	private LinearLayout layout;

	private TelephonyManager tm;
	private String deviceid;

	private AdView adView;
	private String ADMOB_UNIT_ID = AdMobShared.ADMOB_UNIT_ID;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		Log.i(TAG, "after super.onCreate()");

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.i(TAG, "requestWindowFeature(Window.FEATURE_NO_TITLE)");

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		Log.i(TAG, "setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN");

		setContentView(R.layout.main);
		Log.i(TAG, "After setContentView(R.layout.main)");

		instance = this;
		Log.i(TAG, "After instance = this;");

		initiate_internals();

		// Lookup your LinearLayout assuming it�s been given
		// the attribute android:id="@+id/mainLayout"
		layout = (LinearLayout) findViewById(R.id.main_linear_layout);

		setComponentsGui();
		setComponentsActions();

		try {
			tm = (TelephonyManager) getBaseContext().getSystemService(
					Context.TELEPHONY_SERVICE);
			deviceid = tm.getDeviceId();
		} catch (NullPointerException nexc) {
			tm = null;
			deviceid = "DEVICE_TEST_ID";
		} catch (Exception exc) {
			tm = null;
			deviceid = "DEVICE_TEST_ID";
		}

		// Create the adView
		adView = new AdView(this, AdSize.BANNER, ADMOB_UNIT_ID);

		// Add the adView to it
		layout.addView(adView);

		AdRequest adRequest = new AdRequest();

		if (DebugIndicator.DEBUG) {
			adRequest.addTestDevice(AdRequest.TEST_EMULATOR); // Emulator
			adRequest.addTestDevice(deviceid); // Test Android Device
			adRequest.addTestDevice("c16079314a5512f"); // Test Android
			// Device
			adRequest.addTestDevice("2cf3765d"); // Test Android Device
			adRequest.addTestDevice("FC4AA4F51610C2029A55E3E9A323617A");
			adRequest.addTestDevice("66B6ABDCE2873B94D565100F9A48D2AA");

		}

		// Initiate a generic request to load it with an ad
		adView.loadAd(adRequest);
	}

	private void initiate_internals() {
		mySensorsManager = new MySensorsManager(instance);
		mySensorsManager.initiate();

		list = new SensorsList(instance);
		list.init();

		listSensors = list.get_sensors_list();
	}

	private void setComponentsGui() {
		for (Sensor s : listSensors) {
			switch (s.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				View layout1 = (View) findViewById(R.id.include_accelerometer);
				layout.setVisibility(View.VISIBLE);
				break;
			case Sensor.TYPE_GRAVITY:
				View layout9 = (View) findViewById(R.id.include_gravity);
				layout9.setVisibility(View.VISIBLE);
				break;
			case Sensor.TYPE_GYROSCOPE:
				View layout4 = (View) findViewById(R.id.include_gyroscope);
				layout4.setVisibility(View.VISIBLE);
				break;
			case Sensor.TYPE_LIGHT:
				View layout5 = (View) findViewById(R.id.include_light);
				layout5.setVisibility(View.VISIBLE);
				break;
			case Sensor.TYPE_LINEAR_ACCELERATION:
				View layout10 = (View) findViewById(R.id.include_linear_acceleration);
				layout10.setVisibility(View.VISIBLE);
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				View layout2 = (View) findViewById(R.id.include_magneticfield);
				layout2.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
	}

	private void setComponentsActions() {
		;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onDestroy() {
		if (adView != null) {
			adView.destroy();
		}
		super.onDestroy();
	}

}