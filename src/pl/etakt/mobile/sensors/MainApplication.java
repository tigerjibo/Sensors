package pl.etakt.mobile.sensors;

import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;
import android.util.Log;
import android.view.Window;

@ReportsCrashes(formKey = "dFRSUFNpLVZuNHoxdFJjRnFiRkFGNkE6MQ")
public class MainApplication extends Application {

	private static final String TAG = "MainApplication";

	@Override
	public void onCreate() {
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		// The following line triggers the initialization of ACRA
		if (isAntiLVL()) {
			// initializes networking, must be included
			// if not found - exit
			System.exit(-44);
		} else {
			try {
				makeMiau();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ACRA.init(this);
		Log.i(TAG, "ACRA for Bling-Bling app initiated");
		super.onCreate();
	}

	public void makeMiau() throws IOException {
		say("Miau!");
		crcTest();
	}

	private void crcTest() throws IOException {
		boolean modified = false;

		// required dex crc value stored as a text string.
		// it could be any invisible layout element
		long dexCrc = Long.parseLong(getApplicationContext().getString(
				R.string.dex_crc));

		ZipFile zf = new ZipFile(getApplicationContext().getPackageCodePath());
		ZipEntry ze = zf.getEntry("classes.dex");
		Log.d(TAG, "cpt. obvious strikes back: " + ze.getCrc());

		if (ze.getCrc() != dexCrc) {
			// dex has been modified
			Log.d(TAG, "Red alert, CRC test failed !");
			modified = true;
		} else {
			// dex not tampered with
			Log.d(TAG, "Fine, CRC test passed !");
			modified = false;
		}
	}

	private void say(String s) {
		Log.d(TAG, "cat says: " + s);
	}

	protected boolean isAntiLVL() {
		try {
			Class.forName("smaliHook");
			return true;
		} catch (Exception e) {
		}
		return false;
	}

}
