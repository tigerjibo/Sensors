package pl.etakt.mobile.sensors;

import pl.etakt.mobile.sensors.tabs.AccelerometerActivity;
import pl.etakt.mobile.sensors.tabs.MagneticActivity;
import pl.etakt.mobile.sensors.tabs.OrientationActivity;
import pl.etakt.mobile.sensors.tabs.ProximityActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class SensorsTabActivity extends TabActivity {

	private static final String TAG = "SensorsTabActivity";
	private static final boolean MAKE_TOASTS = false;

	// Initiating Menu XML file (menu.xml)
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.layout.tab_menu, menu);
		return true;
	}

	/**
	 * Event Handling for Individual menu item selected Identify single menu
	 * item by it's id
	 * */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.menu_tabs:
			// Single menu item is selected do something
			// Ex: launching new activity/screen or show alert message
			if (MAKE_TOASTS)
				Toast.makeText(SensorsTabActivity.this,
						"Classic view is Selected", Toast.LENGTH_SHORT).show();

			Intent intent = new Intent(SensorsTabActivity.this,
					SensorsActivity.class);
			startActivity(intent);

			return true;

		case R.id.menu_settings:
			if (MAKE_TOASTS)
				Toast.makeText(SensorsTabActivity.this, "Settings is Selected",
						Toast.LENGTH_SHORT).show();
			return true;

		case R.id.menu_about:
			if (MAKE_TOASTS)
				Toast.makeText(SensorsTabActivity.this, "About is Selected",
						Toast.LENGTH_SHORT).show();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);

		Resources ressources = getResources();
		TabHost tabHost = getTabHost();

		// Accelerometer tab
		Intent intentAndroid = new Intent().setClass(this,
				AccelerometerActivity.class);
		TabSpec tabSpecAndroid = tabHost
				.newTabSpec("Accelerometer")
				.setIndicator(
						"",
						ressources
								.getDrawable(R.drawable.accelerometer_icon_big))
				.setContent(intentAndroid);

		// Magnetic tab
		Intent intentApple = new Intent()
				.setClass(this, MagneticActivity.class);
		TabSpec tabSpecApple = tabHost
				.newTabSpec("Magnetic field")
				.setIndicator("",
						ressources.getDrawable(R.drawable.magnetic_sensor))
				.setContent(intentApple);

		// Orientation tab
		Intent intentWindows = new Intent().setClass(this,
				OrientationActivity.class);
		TabSpec tabSpecWindows = tabHost
				.newTabSpec("Orientation")
				.setIndicator("",
						ressources.getDrawable(R.drawable.orientation_sensor))
				.setContent(intentWindows);

		// Orientation tab
		Intent intentProxima = new Intent().setClass(this,
				ProximityActivity.class);
		TabSpec tabSpecProxima = tabHost.newTabSpec("Proximity")
				.setIndicator("", ressources.getDrawable(R.drawable.proximity))
				.setContent(intentProxima);

		// add all tabs
		tabHost.addTab(tabSpecAndroid);
		tabHost.addTab(tabSpecApple);
		tabHost.addTab(tabSpecWindows);
		tabHost.addTab(tabSpecProxima);

		// set Windows tab as default (zero based)
		tabHost.setCurrentTab(0);
	}

}