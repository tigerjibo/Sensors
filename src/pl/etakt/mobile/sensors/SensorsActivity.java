package pl.etakt.mobile.sensors;

import java.util.List;

import pl.etakt.mobile.sensors.data.MySensorsManager;
import pl.etakt.mobile.sensors.data.SensorsList;
import pl.etakt.mobile.sensors.engine.AdMobShared;
import pl.etakt.mobile.sensors.engine.DebugIndicator;
import pl.etakt.mobile.sensors.engine.DrawableFactory;
import pl.etakt.mobile.sensors.engine.ScreenSize;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;

public class SensorsActivity extends Activity {

	private static final String TAG = "SensorsActivity";

	protected MySensorsManager mySensorsManager;
	private SensorsList list;
	private List<Sensor> listSensors;

	private DrawableFactory drawableFactory;

	private ScreenSize screenSize;

	protected SensorsActivity instance;

	private TableLayout layout;

	private TelephonyManager tm;
	private String deviceid;

	private static final boolean FIRST_METHOD_INFLATE = false;

	private AdView adView;
	private String ADMOB_UNIT_ID = AdMobShared.ADMOB_UNIT_ID;

	private int SCREEN_WIDTH;

	private int SCREEN_HEIGHT;
	
	TextView sensors_text_accelerometer;

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

		// Lookup your LinearLayout assuming it’s been given
		// the attribute android:id="@+id/mainLayout"
		layout = (TableLayout) findViewById(R.id.main_linear_layout);

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
			adRequest.addTestDevice("F689A02E3E7A326E5DB17B55966360DF");

		}

		// Initiate a generic request to load it with an ad
		adView.loadAd(adRequest);
	}

	private void initiate_factories() {

		drawableFactory = new DrawableFactory(getPackageName());

		screenSize = new ScreenSize(getWindowManager().getDefaultDisplay());

		SCREEN_WIDTH = screenSize.getScreenWidth();
		SCREEN_HEIGHT = screenSize.getScreenHeight();

	}

	private void initiate_internals() {
		Log.i(TAG, "initiate_internals() in " + getClass().getCanonicalName());

		mySensorsManager = new MySensorsManager(instance);
		mySensorsManager.initiate();

		list = new SensorsList(instance);
		list.init();

		list.init_listeners();

		listSensors = list.get_sensors_list();
	}

	static Handler updater1 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Sensor.TYPE_ACCELEROMETER:

				break;
			default:

				break;
			}
		}
	};

	@SuppressLint("ParserError")
	private void setComponentsGui() {

		Log.i(TAG, "setComponentsGui() in " + getClass().getCanonicalName());

		final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		int w = (int) (SCREEN_WIDTH * 0.3);
		int h = (int) (SCREEN_HEIGHT * 0.2);

		for (Sensor s : listSensors) {
			switch (s.getType()) {
			case Sensor.TYPE_ACCELEROMETER:
				// View layout1 = (View)
				// findViewById(R.id.include_accelerometer);
				// layout.setVisibility(View.VISIBLE);
				Log.i(TAG, "case Sensor.TYPE_ACCELEROMETER");

				if (FIRST_METHOD_INFLATE)
					/*
					 * ((ViewStub) findViewById(R.id.include_accelerometer))
					 * .setVisibility(View.VISIBLE);
					 */;
				else {
					/*
					 * View importPanel1 = ((ViewStub)
					 * findViewById(R.id.include_accelerometer)) .inflate();
					 */
					// ListView importPanel1 = ((ListView)
					// findViewById(R.id.list_view_accelerometer));
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

					layout.addView(b);
				}

				break;
			case Sensor.TYPE_GRAVITY:
				// View layout9 = (View) findViewById(R.id.include_gravity);
				// layout9.setVisibility(View.VISIBLE);
				Log.i(TAG, "case Sensor.TYPE_GRAVITY:");

				if (FIRST_METHOD_INFLATE)
					/*
					 * ((ViewStub) findViewById(R.id.include_gravity))
					 * .setVisibility(View.VISIBLE)
					 */;
				else {
					/*
					 * View importPanel9 = ((ViewStub)
					 * findViewById(R.id.include_gravity)) .inflate();
					 */
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

					layout.addView(b);
				}

				break;
			case Sensor.TYPE_GYROSCOPE:
				// View layout4 = (View) findViewById(R.id.include_gyroscope);
				// layout4.setVisibility(View.VISIBLE);
				Log.i(TAG, "case Sensor.TYPE_GYROSCOPE:");

				if (FIRST_METHOD_INFLATE)
					// ((ViewStub) findViewById(R.id.include_gyroscope))
					// .setVisibility(View.VISIBLE);
					;
				else {
					// View importPanel4 = ((ViewStub)
					// findViewById(R.id.include_gyroscope))
					// .inflate();
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

					layout.addView(b);
				}

				break;
			case Sensor.TYPE_LIGHT:
				// View layout5 = (View) findViewById(R.id.include_light);
				// layout5.setVisibility(View.VISIBLE);
				Log.i(TAG, "case Sensor.TYPE_LIGHT:");

				if (FIRST_METHOD_INFLATE)
					// ((ViewStub) findViewById(R.id.include_light))
					// .setVisibility(View.VISIBLE);
					;
				else {
					// View importPanel5 = ((ViewStub)
					// findViewById(R.id.include_light))
					// .inflate();
					View b = (View) inflater.inflate(R.layout.type_light, null);
					ImageView image = ((ImageView) b
							.findViewById(R.id.icon_light));

					Drawable d = drawableFactory.getDrawable(image,
							getResources());
					Drawable n = drawableFactory.resize(d, w, h);

					image.setImageDrawable(n);

					image.setMaxHeight(h);
					image.setMaxWidth(w);

					layout.addView(b);
				}

				break;
			case Sensor.TYPE_LINEAR_ACCELERATION:
				// View layout10 = (View)
				// findViewById(R.id.include_linear_acceleration);
				// layout10.setVisibility(View.VISIBLE);
				Log.i(TAG, "case Sensor.TYPE_LINEAR_ACCELERATION:");

				if (FIRST_METHOD_INFLATE)
					// ((ViewStub)
					// findViewById(R.id.include_linear_acceleration))
					// .setVisibility(View.VISIBLE);
					;
				else {
					// View importPanel10 = ((ViewStub)
					// findViewById(R.id.include_linear_acceleration))
					// .inflate();
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

					layout.addView(b);
				}

				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				// View layout2 = (View)
				// findViewById(R.id.include_magneticfield);
				// layout2.setVisibility(View.VISIBLE);
				Log.i(TAG, "case Sensor.TYPE_MAGNETIC_FIELD:");

				if (FIRST_METHOD_INFLATE)
					/*
					 * ((ViewStub) findViewById(R.id.include_magneticfield))
					 * .setVisibility(View.VISIBLE)
					 */;
				else {
					/*
					 * View importPanel2 = ((ViewStub)
					 * findViewById(R.id.include_magneticfield)) .inflate();
					 */
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

					layout.addView(b);
				}

				break;
			case Sensor.TYPE_ORIENTATION:
				// View layout3 = (View)
				// findViewById(R.id.include_orientation_sensor);
				// layout3.setVisibility(View.VISIBLE);
				Log.i(TAG, "case Sensor.TYPE_ORIENTATION:");

				if (FIRST_METHOD_INFLATE)
					/*
					 * ((ViewStub)
					 * findViewById(R.id.include_orientation_sensor))
					 * .setVisibility(View.VISIBLE)
					 */;
				else {
					/*
					 * View importPanel3 = ((ViewStub)
					 * findViewById(R.id.include_orientation_sensor))
					 * .inflate();
					 */
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

					layout.addView(b);
				}

				break;
			case Sensor.TYPE_PRESSURE:
				// View layout6 = (View) findViewById(R.id.include_pressure);
				// layout6.setVisibility(View.VISIBLE);
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

					layout.addView(b);
				}

				break;
			case Sensor.TYPE_PROXIMITY:
				// View layout6 = (View) findViewById(R.id.include_pressure);
				// layout6.setVisibility(View.VISIBLE);
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

					layout.addView(b);
				}

				break;
			default:
				Log.e(TAG, "Default case !!!");
				break;
			}
		}
	}

	private void setComponentsActions() {
		Log.i(TAG, "setComponentsActions() in SensorsActivity called");
		;
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