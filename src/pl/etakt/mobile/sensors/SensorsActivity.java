package pl.etakt.mobile.sensors;

import java.util.List;

import pl.etakt.mobile.sensors.audio.AverageNoise;
import pl.etakt.mobile.sensors.data.MySensorsManager;
import pl.etakt.mobile.sensors.data.SensorsList;
import pl.etakt.mobile.sensors.engine.AdMobShared;
import pl.etakt.mobile.sensors.engine.DebugIndicator;
import pl.etakt.mobile.sensors.engine.DrawableFactory;
import pl.etakt.mobile.sensors.engine.ScreenSize;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class SensorsActivity extends Activity {

	private static final String TAG = "SensorsActivity";

	/*************************************************
	 * Hardware classes, Sensors management
	 * 
	 * @author Oskar
	 * @since 1.0
	 *************************************************/
	protected MySensorsManager mySensorsManager;
	private SensorsList list;
	private List<Sensor> listSensors;
	private AverageNoise averageNoise;

	private DrawableFactory drawableFactory;
	private ScreenSize screenSize;

	protected static SensorsActivity instance;

	private TableLayout layout;
	private ScrollView scrollViewMain;
	private RelativeLayout mainRelativeLayout;

	private TableRow acceleratorRow;
	private TableRow magneticfieldRow;
	private TableRow orientationSensorRow;
	private TableRow gyroscopeRow;
	private TableRow lightRow;
	private TableRow pressureRow;
	private TableRow temperatureRow;
	private TableRow proximityRow;
	private TableRow gravityRow;
	private TableRow linearAccelerationRow;
	private TableRow rotationvectorRow;
	private TableRow relativeHumidityRow;
	private TableRow ambientTemperatureRow;
	private TableRow soundRow;

	private static final int LIGHT_SENSOR = -10;

	private TelephonyManager tm;
	private String deviceid;

	private static final boolean FIRST_METHOD_INFLATE = false;

	private AdView adView;
	private String ADMOB_UNIT_ID = AdMobShared.ADMOB_UNIT_ID;

	private int SCREEN_WIDTH;

	private int SCREEN_HEIGHT;

	private TextView sensors_text_accelerometer;
	private TextView sensors_text_gravity;
	private TextView sensors_text_gyroscope;
	private TextView sensors_text_light;
	private TextView sensors_text_linear_acceleration;
	private TextView sensors_text_magnetic_field;
	private TextView sensors_text_orientation;
	private TextView sensors_text_pressure;
	private TextView sensors_text_proximity;
	private TextView sensors_text_temperature;
	private TextView sensors_text_rotation_vector;

	private Button exitApplicationButton;

	private boolean INCLUDE_NOISE_METER = true;

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

		initiate_factories();

		setComponentsReferences();
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
		mainRelativeLayout.addView(adView);

		AdRequest adRequest = new AdRequest();

		if (DebugIndicator.DEBUG) {
			adRequest.addTestDevice(AdRequest.TEST_EMULATOR); // Emulator
			adRequest.addTestDevice(deviceid); // Test Android Device
			adRequest.addTestDevice("c16079314a5512f"); // Test Android
			// Device
			adRequest.addTestDevice("2cf3765d"); // Test Android Device
			adRequest.addTestDevice("FC4AA4F51610C2029A55E3E9A323617A");
			adRequest.addTestDevice("66B6ABDCE2873B94D565100F9A48D2AA");
			adRequest.addTestDevice("F689A02E3E7A326E5DB17B55966360DF");

		}

		// Initiate a generic request to load it with an ad
		adView.loadAd(adRequest);
	}
	
    // Initiating Menu XML file (menu.xml)
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.layout.menu, menu);
        return true;
    }
 
    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
 
        switch (item.getItemId())
        {
        case R.id.menu_tabs:
            // Single menu item is selected do something
            // Ex: launching new activity/screen or show alert message
            Toast.makeText(SensorsActivity.this, "Tabs is Selected", Toast.LENGTH_SHORT).show();
            
        	Intent intent = new Intent(SensorsActivity.this,
        			SensorsTabActivity.class);
        	startActivity(intent);
            
            return true;
 
        case R.id.menu_settings:
            Toast.makeText(SensorsActivity.this, "Settings is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        case R.id.menu_about:
            Toast.makeText(SensorsActivity.this, "About is Selected", Toast.LENGTH_SHORT).show();
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
    }  

	private void initiate_factories() {
		Log.d(TAG, "initiate_factories() launched in "
				+ getClass().getCanonicalName());

		drawableFactory = new DrawableFactory(getPackageName());

		screenSize = new ScreenSize(getWindowManager().getDefaultDisplay());

		SCREEN_WIDTH = screenSize.getScreenWidth();
		SCREEN_HEIGHT = screenSize.getScreenHeight();

	}

	private void initiate_internals() {
		Log.i(TAG, "initiate_internals() in " + getClass().getCanonicalName());

		// TO DO: it will be a bridge-container after SensorsList initiated
		// the sensors will be held below
		mySensorsManager = new MySensorsManager(instance);
		mySensorsManager.initiate(); // initiating manager

		list = new SensorsList(instance);
		list.init(); // initing sensors
		list.init_listeners(); // initing sensors listeners

		listSensors = list.getSensorList(); // getting sensor list

		averageNoise = new AverageNoise();
	}

	protected static Handler catUpdater = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			float[] values = (float[]) msg.obj;
			switch (msg.what) {
			case Sensor.TYPE_ACCELEROMETER:
				instance.sensors_text_accelerometer.setText("" + values[0]
						+ " " + values[1] + " " + values[2]);
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				instance.sensors_text_magnetic_field.setText("" + values[0]);
				//instance.sensors_text_magnetic_field2.setText(values[1]);
				//instance.sensors_text_magnetic_field3.setText(values[2]);
				break;
			case Sensor.TYPE_LINEAR_ACCELERATION:
				instance.sensors_text_linear_acceleration.setText(""
						+ values[0] + " " + values[1] + " " + values[2]);
				break;
			case Sensor.TYPE_ORIENTATION:
				instance.sensors_text_orientation.setText("" + values[0] + " "
						+ values[1] + " " + values[2]);
				break;
			case Sensor.TYPE_PROXIMITY:
				instance.sensors_text_proximity.setText("" + values[0] + " "
						+ values[1] + " " + values[2]);
				break;
			case Sensor.TYPE_TEMPERATURE:
				instance.sensors_text_temperature.setText("" + values[0] + " "
						+ values[1] + " " + values[2]);
				break;
			case Sensor.TYPE_GRAVITY:
				instance.sensors_text_gravity.setText("" + values[0] + " "
						+ values[1] + " " + values[2]);
				break;
			case Sensor.TYPE_GYROSCOPE:
				instance.sensors_text_gyroscope.setText("" + values[0] + " "
						+ values[1] + " " + values[2]);
				break;
			case Sensor.TYPE_PRESSURE:
				instance.sensors_text_pressure.setText("" + values[0] + " "
						+ values[1] + " " + values[2]);
				break;
			case Sensor.TYPE_ROTATION_VECTOR:
				instance.sensors_text_rotation_vector.setText("" + values[0]
						+ " " + values[1] + " " + values[2]);
				break;
			case LIGHT_SENSOR:
				instance.sensors_text_light.setText("" + values[0] + " "
						+ values[1] + " " + values[2]);
				break;
			default:
				Log.e(TAG, "Something wrong, default case in messanger receive");
				break;
			}
		}
	};

	public static Handler getUpdater() {
		Log.e(TAG, "getUpdater called");
		return catUpdater;
	}

	private void setComponentsReferences() {
		Log.d(TAG, "setComponentsReferences() lunched");

		// Lookup your LinearLayout assuming it’s been given
		// the attribute android:id="@+id/mainLayout"
		layout = (TableLayout) findViewById(R.id.main_linear_layout);
		scrollViewMain = (ScrollView) findViewById(R.id.ScrollViewMain);
		mainRelativeLayout = (RelativeLayout) findViewById(R.id.main_relative_layout);

		acceleratorRow = ((TableRow) findViewById(R.id.accelerometer_row));
		magneticfieldRow = ((TableRow) findViewById(R.id.magneticfield_row));
		orientationSensorRow = ((TableRow) findViewById(R.id.orientation_sensor_row));
		gyroscopeRow = ((TableRow) findViewById(R.id.gyroscope_row));
		lightRow = ((TableRow) findViewById(R.id.light_row));
		pressureRow = ((TableRow) findViewById(R.id.pressure_row));
		temperatureRow = ((TableRow) findViewById(R.id.temperature_row));
		proximityRow = ((TableRow) findViewById(R.id.proximity_row));
		gravityRow = ((TableRow) findViewById(R.id.gravity_row));
		linearAccelerationRow = ((TableRow) findViewById(R.id.linear_acceleration_row));
		rotationvectorRow = ((TableRow) findViewById(R.id.rotation_vector_row));
		relativeHumidityRow = ((TableRow) findViewById(R.id.relative_humidity_row));
		ambientTemperatureRow = ((TableRow) findViewById(R.id.ambient_temperature_row));
		soundRow = ((TableRow) findViewById(R.id.sound_row));

		exitApplicationButton = (Button) findViewById(R.id.exitApplicationButton);
	}

	@SuppressLint("ParserError")
	private void setComponentsGui() {

		Log.i(TAG, "setComponentsGui() in " + getClass().getCanonicalName());

		final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.i(TAG, "inflater service called");

		int w = (int) (SCREEN_WIDTH * 0.3);
		int h = (int) (SCREEN_HEIGHT * 0.2);

		for (Sensor s : listSensors) {
			switch (s.getType()) {
			case Sensor.TYPE_ACCELEROMETER:

				Log.i(TAG, "case Sensor.TYPE_ACCELEROMETER");

				if (FIRST_METHOD_INFLATE)
					/*
					 * ((ViewStub) findViewById(R.id.include_accelerometer))
					 * .setVisibility(View.VISIBLE);
					 */;
				else {

					View b = (View) inflater.inflate(
							R.layout.type_accelerometer, null);
					ImageView image = ((ImageView) b
							.findViewById(R.id.icon_accelerometer));

					Drawable d = drawableFactory.getDrawable(image,
							getResources());
					Drawable n = drawableFactory.resize(d, w, h);

					image.setImageDrawable(n);

					image.setMaxHeight(h);
					image.setMaxWidth(w);

					sensors_text_accelerometer = ((TextView) b
							.findViewById(R.id.sensors_text_accelerometer));

					// layout.addView(b);
					acceleratorRow.addView(b);
				}

				break;
			case Sensor.TYPE_GRAVITY:

				Log.i(TAG, "case Sensor.TYPE_GRAVITY:");

				if (FIRST_METHOD_INFLATE)
					/*
					 * ((ViewStub) findViewById(R.id.include_gravity))
					 * .setVisibility(View.VISIBLE)
					 */;
				else {

					View b = (View) inflater.inflate(R.layout.type_gravity,
							null);
					ImageView image = ((ImageView) b
							.findViewById(R.id.icon_gravity));

					Drawable d = drawableFactory.getDrawable(image,
							getResources());
					Drawable n = drawableFactory.resize(d, w, h);

					image.setImageDrawable(n);

					image.setMaxHeight(h);
					image.setMaxWidth(w);

					sensors_text_gravity = ((TextView) b
							.findViewById(R.id.sensors_text_gravity));

					gravityRow.addView(b);
				}

				break;
			case Sensor.TYPE_GYROSCOPE:

				Log.i(TAG, "case Sensor.TYPE_GYROSCOPE:");

				if (FIRST_METHOD_INFLATE)
					// ((ViewStub) findViewById(R.id.include_gyroscope))
					// .setVisibility(View.VISIBLE);
					;
				else {

					View b = (View) inflater.inflate(R.layout.type_gyroscope,
							null);
					ImageView image = ((ImageView) b
							.findViewById(R.id.icon_gyroscope));

					Drawable d = drawableFactory.getDrawable(image,
							getResources());
					Drawable n = drawableFactory.resize(d, w, h);

					image.setImageDrawable(n);

					image.setMaxHeight(h);
					image.setMaxWidth(w);

					sensors_text_gyroscope = ((TextView) b
							.findViewById(R.id.sensors_text_gyroscope));

					gyroscopeRow.addView(b);
				}

				break;
			case Sensor.TYPE_LIGHT:

				Log.i(TAG, "case Sensor.TYPE_LIGHT:");

				if (FIRST_METHOD_INFLATE)
					// ((ViewStub) findViewById(R.id.include_light))
					// .setVisibility(View.VISIBLE);
					;
				else {

					View b = (View) inflater.inflate(R.layout.type_light, null);
					ImageView image = ((ImageView) b
							.findViewById(R.id.icon_light));

					Drawable d = drawableFactory.getDrawable(image,
							getResources());
					Drawable n = drawableFactory.resize(d, w, h);

					image.setImageDrawable(n);

					image.setMaxHeight(h);
					image.setMaxWidth(w);

					sensors_text_light = ((TextView) b
							.findViewById(R.id.sensors_text_light));

					lightRow.addView(b);
				}

				break;
			case Sensor.TYPE_LINEAR_ACCELERATION:

				Log.i(TAG, "case Sensor.TYPE_LINEAR_ACCELERATION:");

				if (FIRST_METHOD_INFLATE)
					// ((ViewStub)
					// findViewById(R.id.include_linear_acceleration))
					// .setVisibility(View.VISIBLE);
					;
				else {

					View b = (View) inflater.inflate(
							R.layout.type_linear_acceleration, null);
					ImageView image = ((ImageView) b
							.findViewById(R.id.icon_accelerometer));

					Drawable d = drawableFactory.getDrawable(image,
							getResources());
					Drawable n = drawableFactory.resize(d, w, h);

					image.setImageDrawable(n);

					image.setMaxHeight(h);
					image.setMaxWidth(w);

					sensors_text_linear_acceleration = ((TextView) b
							.findViewById(R.id.sensors_text_linear_acceleration));

					linearAccelerationRow.addView(b);
				}

				break;
			case Sensor.TYPE_MAGNETIC_FIELD:

				Log.i(TAG, "case Sensor.TYPE_MAGNETIC_FIELD:");

				if (FIRST_METHOD_INFLATE)
					/*
					 * ((ViewStub) findViewById(R.id.include_magneticfield))
					 * .setVisibility(View.VISIBLE)
					 */;
				else {

					View b = (View) inflater.inflate(
							R.layout.type_magneticfield, null);
					ImageView image = ((ImageView) b
							.findViewById(R.id.icon_magneticfield));

					Drawable d = drawableFactory.getDrawable(image,
							getResources());
					Drawable n = drawableFactory.resize(d, w, h);

					image.setImageDrawable(n);

					image.setMaxHeight(h);
					image.setMaxWidth(w);

					sensors_text_magnetic_field = ((TextView) b
							.findViewById(R.id.sensors_text_magneticfield));

					magneticfieldRow.addView(b);
				}

				break;
			case Sensor.TYPE_ORIENTATION:

				Log.i(TAG, "case Sensor.TYPE_ORIENTATION:");

				if (FIRST_METHOD_INFLATE)
					/*
					 * ((ViewStub)
					 * findViewById(R.id.include_orientation_sensor))
					 * .setVisibility(View.VISIBLE)
					 */;
				else {

					View b = (View) inflater.inflate(R.layout.type_orientation,
							null);
					ImageView image = ((ImageView) b
							.findViewById(R.id.icon_orientation));

					Drawable d = drawableFactory.getDrawable(image,
							getResources());
					Drawable n = drawableFactory.resize(d, w, h);

					image.setImageDrawable(n);

					image.setMaxHeight(h);
					image.setMaxWidth(w);

					sensors_text_orientation = ((TextView) b
							.findViewById(R.id.sensors_text_orientation));

					orientationSensorRow.addView(b);
				}

				break;
			case Sensor.TYPE_PRESSURE:

				Log.i(TAG, "case Sensor.TYPE_PRESSURE:");

				if (FIRST_METHOD_INFLATE)
					/*
					 * ((ViewStub) findViewById(R.id.include_pressure))
					 * .setVisibility(View.VISIBLE)
					 */;
				else {
					View b = (View) inflater.inflate(R.layout.type_pressure,
							null);
					ImageView image = ((ImageView) b
							.findViewById(R.id.icon_pressure));

					Drawable d = drawableFactory.getDrawable(image,
							getResources());
					Drawable n = drawableFactory.resize(d, w, h);

					image.setImageDrawable(n);

					image.setMaxHeight(h);
					image.setMaxWidth(w);

					sensors_text_pressure = ((TextView) b
							.findViewById(R.id.sensors_text_pressure));

					pressureRow.addView(b);
				}

				break;
			case Sensor.TYPE_PROXIMITY:

				Log.i(TAG, "case Sensor.TYPE_PROXIMITY");

				if (FIRST_METHOD_INFLATE)
					/*
					 * ((ViewStub) findViewById(R.id.include_pressure))
					 * .setVisibility(View.VISIBLE)
					 */;
				else {
					View b = (View) inflater.inflate(R.layout.type_proximity,
							null);
					ImageView image = ((ImageView) b
							.findViewById(R.id.icon_proximity));

					Drawable d = drawableFactory.getDrawable(image,
							getResources());
					Drawable n = drawableFactory.resize(d, w, h);

					image.setImageDrawable(n);

					image.setMaxHeight(h);
					image.setMaxWidth(w);

					sensors_text_proximity = ((TextView) b
							.findViewById(R.id.sensors_text_proximity));

					proximityRow.addView(b);
				}

				break;
			default:
				Log.e(TAG, "Default case !!!");
				break;
			}
		}

		if (INCLUDE_NOISE_METER) {

			Log.i(TAG, "INCLUDE_NOISE_METER");

			if (FIRST_METHOD_INFLATE)
				/*
				 * ((ViewStub) findViewById(R.id.include_pressure))
				 * .setVisibility(View.VISIBLE)
				 */;
			else {
				View b = (View) inflater.inflate(
						R.layout.type_sound_intensivity, null);
				ImageView image = ((ImageView) b
						.findViewById(R.id.icon_sound_intensivity));

				Drawable d = drawableFactory.getDrawable(image, getResources());
				Drawable n = drawableFactory.resize(d, w, h);

				image.setImageDrawable(n);

				image.setMaxHeight(h);
				image.setMaxWidth(w);

				soundRow.addView(b);
			}
		}
		
		exitApplicationButton.setMinWidth((int) (SCREEN_WIDTH * 0.7));
		exitApplicationButton.setMaxWidth((int) (SCREEN_WIDTH * 0.7));
		exitApplicationButton.setWidth((int) (SCREEN_WIDTH * 0.7));
	}

	private void setComponentsActions() {
		Log.i(TAG, "setComponentsActions() in SensorsActivity called");

		exitApplicationButton.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// exit application
				finish();
				if (Log.isLoggable(TAG, Log.INFO))
					Log.i(TAG,
							"User clicked Exit, Application called finish(). Goodbye.");
			}
		});
	}

	@Override
	public void onPause() {
		Log.i(TAG, "onPause() in SensorsActivity called");
		super.onPause();
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy() in SensorsActivity called");
		if (adView != null) {
			Log.i(TAG, "adView != null");
			adView.destroy();
		}
		super.onDestroy();
	}

}