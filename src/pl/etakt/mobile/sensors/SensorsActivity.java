package pl.etakt.mobile.sensors;

import java.util.List;

import pl.etakt.mobile.sensors.audio.AverageNoise;
import pl.etakt.mobile.sensors.controls.NewFontFitTextView;
import pl.etakt.mobile.sensors.data.MySensorsManager;
import pl.etakt.mobile.sensors.data.SensorsFactory;
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
import android.hardware.SensorManager;
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
	
	private TableRow acceleratorRowSeparator;
	private TableRow magneticfieldRowSeparator;
	private TableRow orientationSensorRowSeparator;
	private TableRow gyroscopeRowSeparator;
	private TableRow lightRowSeparator;
	private TableRow pressureRowSeparator;
	private TableRow temperatureRowSeparator;
	private TableRow proximityRowSeparator;
	private TableRow gravityRowSeparator;
	private TableRow linearAccelerationRowSeparator;
	private TableRow rotationvectorRowSeparator;
	private TableRow relativeHumidityRowSeparator;
	private TableRow ambientTemperatureRowSeparator;
	private TableRow soundRowSeparator;

	private static final int LIGHT_SENSOR = -10;

	private TelephonyManager tm;
	private String deviceid;

	private static final boolean FIRST_METHOD_INFLATE = false;

	private AdView adView;
	private String ADMOB_UNIT_ID = AdMobShared.ADMOB_UNIT_ID;

	private int SCREEN_WIDTH;

	private int SCREEN_HEIGHT;

	private NewFontFitTextView sensors_text_accelerometer;
	private NewFontFitTextView sensors_text_accelerometer2;
	private NewFontFitTextView sensors_text_accelerometer3;
	private NewFontFitTextView sensors_text_accelerometer_stamp;
	
	private NewFontFitTextView sensors_text_gravity;
	private NewFontFitTextView sensors_text_gravity2;
	private NewFontFitTextView sensors_text_gravity3;
	private NewFontFitTextView sensors_text_gravity_stamp;
	
	private NewFontFitTextView sensors_text_gyroscope;
	private NewFontFitTextView sensors_text_gyroscope2;
	private NewFontFitTextView sensors_text_gyroscope3;
	private NewFontFitTextView sensors_text_gyroscope_stamp;
	
	private NewFontFitTextView sensors_text_light;
	private NewFontFitTextView sensors_text_light2;
	private NewFontFitTextView sensors_text_light3;
	private NewFontFitTextView sensors_text_light_stamp;
	
	private NewFontFitTextView sensors_text_linear_acceleration;
	private NewFontFitTextView sensors_text_linear_acceleration2;
	private NewFontFitTextView sensors_text_linear_acceleration3;
	private NewFontFitTextView sensors_text_linear_acceleration_stamp;
	
	private NewFontFitTextView sensors_text_magnetic_field;
	private NewFontFitTextView sensors_text_magnetic_field2;
	private NewFontFitTextView sensors_text_magnetic_field3;
	private NewFontFitTextView sensors_text_magnetic_field_stamp;
	
	private NewFontFitTextView sensors_text_orientation;
	private NewFontFitTextView sensors_text_orientation2;
	private NewFontFitTextView sensors_text_orientation3;
	private NewFontFitTextView sensors_text_orientation_stamp;
	
	private NewFontFitTextView sensors_text_pressure;
	private NewFontFitTextView sensors_text_pressure2;
	private NewFontFitTextView sensors_text_pressure3;
	private NewFontFitTextView sensors_text_pressure_stamp;
	
	private NewFontFitTextView sensors_text_proximity;
	private NewFontFitTextView sensors_text_proximity2;
	private NewFontFitTextView sensors_text_proximity3;
	private NewFontFitTextView sensors_text_proximity_stamp;
	
	private NewFontFitTextView sensors_text_temperature;
	private NewFontFitTextView sensors_text_temperature2;
	private NewFontFitTextView sensors_text_temperature3;
	private NewFontFitTextView sensors_text_temperature_stamp;
	
	private NewFontFitTextView sensors_text_rotation_vector;
	private NewFontFitTextView sensors_text_rotation_vector2;
	private NewFontFitTextView sensors_text_rotation_vector3;
	private NewFontFitTextView sensors_text_rotation_vector_stamp;
	
	private NewFontFitTextView sensors_text_sound;
	private NewFontFitTextView sensors_text_sound2;
	private NewFontFitTextView sensors_text_sound3;
	private NewFontFitTextView sensors_text_sound_stamp;

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
	
	static float[] gravity = {0, 0, 0};
	
	private static float[] accelerometer2(float v1, float v2, float v3){
		final float alpha = (float) 0.8;

        gravity[0] = alpha * gravity[0] + (1 - alpha) * v1;
        gravity[1] = alpha * gravity[1] + (1 - alpha) * v2;
        gravity[2] = alpha * gravity[2] + (1 - alpha) * v3;

        float linear_acceleration[] = {0.0f, 0.0f, 0.0f};
        linear_acceleration[0] = v1 - gravity[0];
        linear_acceleration[1] = v2 - gravity[1];
        linear_acceleration[2] = v3 - gravity[2];
        
        return linear_acceleration;
	}
	
	private static final float NS2S = 1.0f / 1000000000.0f;
    private static final float[] deltaRotationVector = {0, 0, 0, 0};//new float[4]();
    private static float timestamp;
    final static double EPSILON = 0.0000001;
	
	@SuppressLint("NewApi") private static float[] gyroscope2(float et, float[] ev){
		
		// This timestep's delta rotation to be multiplied by the current rotation
        // after computing it from the gyro sample data.
        if (timestamp != 0) {
            final float dT = (et - timestamp) * NS2S;
            // Axis of the rotation sample, not normalized yet.
            float axisX = ev[0];
            float axisY = ev[1];
            float axisZ = ev[2];

            // Calculate the angular speed of the sample
            float omegaMagnitude = (float) Math.sqrt(axisX*axisX + axisY*axisY + axisZ*axisZ);

            // Normalize the rotation vector if it's big enough to get the axis
            if (omegaMagnitude > EPSILON) {
                axisX /= omegaMagnitude;
                axisY /= omegaMagnitude;
                axisZ /= omegaMagnitude;
            }

            // Integrate around this axis with the angular speed by the timestep
            // in order to get a delta rotation from this sample over the timestep
            // We will convert this axis-angle representation of the delta rotation
            // into a quaternion before turning it into the rotation matrix.
            float thetaOverTwo = omegaMagnitude * dT / 2.0f;
            float sinThetaOverTwo = (float) Math.sin(thetaOverTwo);
            float cosThetaOverTwo = (float) Math.cos(thetaOverTwo);
            deltaRotationVector[0] = sinThetaOverTwo * axisX;
            deltaRotationVector[1] = sinThetaOverTwo * axisY;
            deltaRotationVector[2] = sinThetaOverTwo * axisZ;
            deltaRotationVector[3] = cosThetaOverTwo;
        }
        timestamp = et;
        float[] deltaRotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(deltaRotationMatrix, deltaRotationVector);
        // User code should concatenate the delta rotation we computed with the current rotation
        // in order to get the updated rotation.
        // rotationCurrent = rotationCurrent * deltaRotationMatrix;
        return deltaRotationMatrix;
	}

	protected static Handler catUpdater = new Handler() {
		
		String timestamp = "Reading number ";
		String timestamp2 = " (a second of a day)";

		@Override
		public void handleMessage(Message msg) {
			float[] values = (float[]) msg.obj;
			switch (msg.what) {
			case Sensor.TYPE_ACCELEROMETER:
				float[] x = accelerometer2(values[0], values[1], values[2]);
				instance.sensors_text_accelerometer.setText("Ad=" + values[0] + " (-g-sigmaF/mass), " + x[0] + " acceleration minus Gx on the x-axis");
				instance.sensors_text_accelerometer2.setText("Ad=" + values[1] + " (-g-sigmaF/mass), " + x[1] + " acceleration minus Gx on the y-axis");
				instance.sensors_text_accelerometer3.setText("Ad=" + values[2] + " (-g-sigmaF/mass), " + x[2] + " acceleration minus Gx on the z-axis");
				instance.sensors_text_accelerometer_stamp.setText(timestamp + msg.arg1 + timestamp2);
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				instance.sensors_text_magnetic_field.setText(values[0] + " uT on X Axis");
				instance.sensors_text_magnetic_field2.setText(values[1] + " uT on Y Axis");
				instance.sensors_text_magnetic_field3.setText(values[2] + " uT on Z Axis");
				instance.sensors_text_magnetic_field_stamp.setText(timestamp + msg.arg1 + timestamp2);
				break;
			case Sensor.TYPE_LINEAR_ACCELERATION:
				instance.sensors_text_linear_acceleration.setText("" + values[0]);
				instance.sensors_text_linear_acceleration2.setText("" + values[1]);
				instance.sensors_text_linear_acceleration3.setText("" + values[2]);
				instance.sensors_text_linear_acceleration_stamp.setText(timestamp + msg.arg1 + timestamp2);
				break;
			case Sensor.TYPE_ORIENTATION:
				instance.sensors_text_orientation.setText("" + values[0]);
				instance.sensors_text_orientation2.setText("" + values[1]);
				instance.sensors_text_orientation3.setText("" + values[2]);
				instance.sensors_text_orientation_stamp.setText(timestamp + msg.arg1 + timestamp2);
				break;
			case Sensor.TYPE_PROXIMITY:
				instance.sensors_text_proximity.setText("" + values[0] + " Proximity sensor distance measured in centimeters");
				instance.sensors_text_proximity2.setText("" + values[1]);
				instance.sensors_text_proximity3.setText("" + values[2]);
				instance.sensors_text_proximity_stamp.setText(timestamp + msg.arg1 + timestamp2);
				break;
			case Sensor.TYPE_TEMPERATURE:
				instance.sensors_text_temperature.setText("" + values[0]);
				instance.sensors_text_temperature2.setText("" + values[1]);
				instance.sensors_text_temperature3.setText("" + values[2]);
				instance.sensors_text_temperature_stamp.setText(timestamp + msg.arg1 + timestamp2);
				break;
			case Sensor.TYPE_GRAVITY:
				instance.sensors_text_gravity.setText("" + values[0] + " m/s^2");
				instance.sensors_text_gravity2.setText("" + values[1] + " m/s^2");
				instance.sensors_text_gravity3.setText("" + values[2] + " m/s^2");
				instance.sensors_text_gravity_stamp.setText(timestamp + msg.arg1 + timestamp2);
				break;
			case Sensor.TYPE_GYROSCOPE:
				instance.sensors_text_gyroscope.setText("" + values[0] + " Angular speed around the x-axis");
				instance.sensors_text_gyroscope2.setText("" + values[1] + " Angular speed around the y-axis");
				instance.sensors_text_gyroscope3.setText("" + values[2] + " Angular speed around the z-axis");
				instance.sensors_text_gyroscope_stamp.setText(timestamp + msg.arg1 + timestamp2);
				break;
			case Sensor.TYPE_PRESSURE:
				instance.sensors_text_pressure.setText("" + values[0] + " Atmospheric pressure in hPa (millibar)");
				instance.sensors_text_pressure2.setText("" + values[1]);
				instance.sensors_text_pressure3.setText("" + values[2]);
				instance.sensors_text_pressure_stamp.setText(timestamp + msg.arg1 + timestamp2);
				break;
			case Sensor.TYPE_ROTATION_VECTOR:
				instance.sensors_text_rotation_vector.setText("" + values[0] + " x*sin(fi/2)");
				instance.sensors_text_rotation_vector2.setText("" + values[1] + " y*sin(fi/2)");
				instance.sensors_text_rotation_vector3.setText("" + values[2] + " z*sin(fi/2)");
				instance.sensors_text_rotation_vector_stamp.setText(timestamp + msg.arg1 + timestamp2);
				break;
			case LIGHT_SENSOR:
				instance.sensors_text_light.setText("" + values[0]);
				instance.sensors_text_light2.setText("" + values[1]);
				instance.sensors_text_light3.setText("" + values[2]);
				instance.sensors_text_light_stamp.setText(timestamp + msg.arg1 + timestamp2);
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
		
		acceleratorRowSeparator = ((TableRow) findViewById(R.id.seperator1));
		magneticfieldRowSeparator = ((TableRow) findViewById(R.id.seperator2));
		orientationSensorRowSeparator = ((TableRow) findViewById(R.id.seperator3));
		gyroscopeRowSeparator = ((TableRow) findViewById(R.id.seperator4));
		lightRowSeparator = ((TableRow) findViewById(R.id.seperator5));
		pressureRowSeparator = ((TableRow) findViewById(R.id.seperator6));
		temperatureRowSeparator = ((TableRow) findViewById(R.id.seperator7));
		proximityRowSeparator = ((TableRow) findViewById(R.id.seperator8));
		gravityRowSeparator = ((TableRow) findViewById(R.id.seperator9));
		linearAccelerationRowSeparator = ((TableRow) findViewById(R.id.seperator10));
		rotationvectorRowSeparator = ((TableRow) findViewById(R.id.seperator11));
		relativeHumidityRowSeparator = ((TableRow) findViewById(R.id.seperator12));
		ambientTemperatureRowSeparator = ((TableRow) findViewById(R.id.seperator13));

		exitApplicationButton = (Button) findViewById(R.id.exitApplicationButton);
	}

	@SuppressLint("ParserError")
	private void setComponentsGui() {

		Log.i(TAG, "setComponentsGui() in " + getClass().getCanonicalName());

		final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		Log.i(TAG, "inflater service called");

		int w = (int) (SCREEN_WIDTH * 0.3);
		int h = (int) (SCREEN_HEIGHT * 0.2);
		
		int paddingright = (int)(SCREEN_WIDTH * 0.6);
		int paddingbottom = 6;
		int maxsize = 12;

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

					sensors_text_accelerometer = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_accelerometer));
					sensors_text_accelerometer2 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_accelerometer2));
					sensors_text_accelerometer3 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_accelerometer3));
					sensors_text_accelerometer_stamp = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_accelerometer_stamp));
					
					sensors_text_accelerometer.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_accelerometer2.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_accelerometer3.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_accelerometer_stamp.setPadding(0, 0, paddingright, paddingbottom);
					
					sensors_text_accelerometer.setMaxTextSize(maxsize);
					sensors_text_accelerometer2.setMaxTextSize(maxsize);
					sensors_text_accelerometer3.setMaxTextSize(maxsize);
					sensors_text_accelerometer_stamp.setMaxTextSize(maxsize);

					// layout.addView(b);
					acceleratorRow.addView(b);
				}
				
				acceleratorRowSeparator.setVisibility(View.VISIBLE);

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

					sensors_text_gravity = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_gravity));
					sensors_text_gravity2 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_gravity2));
					sensors_text_gravity3 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_gravity3));
					sensors_text_gravity_stamp = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_gravity_stamp));
					
					sensors_text_gravity.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_gravity2.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_gravity3.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_gravity_stamp.setPadding(0, 0, paddingright, paddingbottom);
					
					sensors_text_gravity.setMaxTextSize(maxsize);
					sensors_text_gravity2.setMaxTextSize(maxsize);
					sensors_text_gravity3.setMaxTextSize(maxsize);
					sensors_text_gravity_stamp.setMaxTextSize(maxsize);

					gravityRow.addView(b);
				}
				
				gravityRowSeparator.setVisibility(View.VISIBLE);

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

					sensors_text_gyroscope = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_gyroscope));
					sensors_text_gyroscope2 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_gyroscope2));
					sensors_text_gyroscope3 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_gyroscope3));
					sensors_text_gyroscope_stamp = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_gyroscope_stamp));
					
					sensors_text_gyroscope.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_gyroscope2.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_gyroscope3.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_gyroscope_stamp.setPadding(0, 0, paddingright, paddingbottom);
					
					sensors_text_gyroscope.setMaxTextSize(maxsize);
					sensors_text_gyroscope2.setMaxTextSize(maxsize);
					sensors_text_gyroscope3.setMaxTextSize(maxsize);
					sensors_text_gyroscope_stamp.setMaxTextSize(maxsize);

					gyroscopeRow.addView(b);
				}
				
				gyroscopeRowSeparator.setVisibility(View.VISIBLE);

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

					sensors_text_light = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_light));
					sensors_text_light2 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_light2));
					sensors_text_light3 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_light3));
					sensors_text_light_stamp = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_light_stamp));
					
					sensors_text_light.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_light2.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_light3.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_light_stamp.setPadding(0, 0, paddingright, paddingbottom);
					
					sensors_text_light.setMaxTextSize(maxsize);
					sensors_text_light2.setMaxTextSize(maxsize);
					sensors_text_light3.setMaxTextSize(maxsize);
					sensors_text_light_stamp.setMaxTextSize(maxsize);

					lightRow.addView(b);
				}
				
				lightRowSeparator.setVisibility(View.VISIBLE);

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

					sensors_text_linear_acceleration = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_linear_acceleration));
					sensors_text_linear_acceleration2 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_linear_acceleration2));
					sensors_text_linear_acceleration3 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_linear_acceleration3));
					sensors_text_linear_acceleration_stamp = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_linear_acceleration_stamp));
					
					sensors_text_linear_acceleration.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_linear_acceleration2.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_linear_acceleration3.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_linear_acceleration_stamp.setPadding(0, 0, paddingright, paddingbottom);
					
					sensors_text_linear_acceleration.setMaxTextSize(maxsize);
					sensors_text_linear_acceleration2.setMaxTextSize(maxsize);
					sensors_text_linear_acceleration3.setMaxTextSize(maxsize);
					sensors_text_linear_acceleration_stamp.setMaxTextSize(maxsize);

					linearAccelerationRow.addView(b);
				}
				
				linearAccelerationRowSeparator.setVisibility(View.VISIBLE);

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

					sensors_text_magnetic_field = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_magneticfield));
					sensors_text_magnetic_field2 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_magneticfield2));
					sensors_text_magnetic_field3 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_magneticfield3));
					sensors_text_magnetic_field_stamp = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_magneticfield_stamp));
					
					sensors_text_magnetic_field.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_magnetic_field2.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_magnetic_field3.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_magnetic_field_stamp.setPadding(0, 0, paddingright, paddingbottom);

					magneticfieldRow.addView(b);
				}
				
				magneticfieldRowSeparator.setVisibility(View.VISIBLE);

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

					sensors_text_orientation = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_orientation));
					sensors_text_orientation2 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_orientation2));
					sensors_text_orientation3 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_orientation3));
					sensors_text_orientation_stamp = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_orientation_stamp));
					
					sensors_text_orientation.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_orientation2.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_orientation3.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_orientation_stamp.setPadding(0, 0, paddingright, paddingbottom);

					orientationSensorRow.addView(b);
				}
				
				orientationSensorRowSeparator.setVisibility(View.VISIBLE);

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

					sensors_text_pressure = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_pressure));
					sensors_text_pressure2 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_pressure2));
					sensors_text_pressure3 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_pressure3));
					sensors_text_pressure_stamp = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_pressure_stamp));
					
					sensors_text_pressure.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_pressure2.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_pressure3.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_pressure_stamp.setPadding(0, 0, paddingright, paddingbottom);

					pressureRow.addView(b);
				}
				
				pressureRowSeparator.setVisibility(View.VISIBLE);

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

					sensors_text_proximity = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_proximity));
					sensors_text_proximity2 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_proximity2));
					sensors_text_proximity3 = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_proximity3));
					sensors_text_proximity_stamp = ((NewFontFitTextView) b
							.findViewById(R.id.sensors_text_proximity_stamp));
					
					sensors_text_proximity.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_proximity2.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_proximity3.setPadding(0, 0, paddingright, paddingbottom);
					sensors_text_proximity_stamp.setPadding(0, 0, paddingright, paddingbottom);

					proximityRow.addView(b);
				}
				
				proximityRowSeparator.setVisibility(View.VISIBLE);

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
				
				sensors_text_sound = ((NewFontFitTextView) b
						.findViewById(R.id.sensors_text_sound));
				sensors_text_sound2 = ((NewFontFitTextView) b
						.findViewById(R.id.sensors_text_sound2));
				sensors_text_sound3 = ((NewFontFitTextView) b
						.findViewById(R.id.sensors_text_sound3));
				sensors_text_sound_stamp = ((NewFontFitTextView) b
						.findViewById(R.id.sensors_text_sound_stamp));
				
				sensors_text_sound.setPadding(0, 0, paddingright, paddingbottom);
				sensors_text_sound2.setPadding(0, 0, paddingright, paddingbottom);
				sensors_text_sound3.setPadding(0, 0, paddingright, paddingbottom);
				sensors_text_sound_stamp.setPadding(0, 0, paddingright, paddingbottom);

				soundRow.addView(b);
			}
		}
		
		exitApplicationButton.setMinWidth((int) (SCREEN_WIDTH * 0.7));
		exitApplicationButton.setMaxWidth((int) (SCREEN_WIDTH * 0.7));
		exitApplicationButton.setWidth((int) (SCREEN_WIDTH * 0.7));
		
		int w1 = (int) (SCREEN_WIDTH * 0.7);
		int h1 = (int) (SCREEN_HEIGHT * 0.10);
		
		RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(
				w1, h1);
				params1.leftMargin = (int) (0.15 * SCREEN_WIDTH);
				params1.topMargin = (int) (SCREEN_HEIGHT * 0.87);
				
		exitApplicationButton.setLayoutParams(params1);
		
		exitApplicationButton.setVisibility(View.VISIBLE);
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
		(new SensorsFactory()).stopAll();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "onDestroy() in SensorsActivity called");
		if (adView != null) {
			Log.i(TAG, "adView != null");
			adView.destroy();
		}
		(new SensorsFactory()).stopAll();
		super.onDestroy();
	}

}