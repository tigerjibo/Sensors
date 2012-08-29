package pl.etakt.mobile.sensors.engine;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.Display;

/**
 * Zak≥adam s≥uchawki, prosze muzo prowadü
 * For getting screen size
 * 
 * internal use only
 * 
 * @author o.jarczyk
 * 
 */
public class ScreenSize {

	private static final String TAG = "ScreenSize";

	protected Display display;

	public ScreenSize(Display display) {
		Log.i(TAG, "Creating ScreenSize object");
		this.display = display; //
		Log.d(TAG, "ScreenSize object created");
		SCREEN_WIDTH = display.getWidth(); // deprecated
		SCREEN_HEIGHT = display.getHeight(); // deprecated
		Log.d(TAG, "Display system object returned values successfully");
	}

	static boolean isTablet(Context context) {
		// TODO: This hacky stuff goes away when we allow users to target
		// devices
		int xlargeBit = 4; // Configuration.SCREENLAYOUT_SIZE_XLARGE; // upgrade
							// to HC SDK to get this
		Configuration config = context.getResources().getConfiguration();
		return (config.screenLayout & xlargeBit) == xlargeBit;
	}

	static boolean isSmartphone(Context context) {
		// TODO: This hacky stuff goes away when we allow users to target
		// devices
		int xlargeBit = 4; // Configuration.SCREENLAYOUT_SIZE_XLARGE; // upgrade
							// to HC SDK to get this
		Configuration config = context.getResources().getConfiguration();
		return (config.screenLayout & xlargeBit) == xlargeBit;
	}

	private int SCREEN_WIDTH;
	private int SCREEN_HEIGHT;

	// @SuppressWarnings("unused")
	public int getScreenWidth() {
		return SCREEN_WIDTH;
	}

	// @SuppressWarnings("unused")
	public void setScreenWidth(int sCREEN_WIDTH) {
		SCREEN_WIDTH = sCREEN_WIDTH;
	}

	// @SuppressWarnings("unused")
	public int getScreenHeight() {
		return SCREEN_HEIGHT;
	}

	// @SuppressWarnings("unused")
	public void setScreenHeight(int sCREEN_HEIGHT) {
		SCREEN_HEIGHT = sCREEN_HEIGHT;
	}

}
