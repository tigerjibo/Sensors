package pl.etakt.mobile.sensors.engine;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

public class DrawableFactory {

	public static final String TAG = "DrawableFactory";
	protected String packageName;

	@Deprecated
	public DrawableFactory() {
		Log.i(TAG, "DrawableFactory object created");
	}

	public DrawableFactory(String packageName) {
		this.packageName = packageName;
		Log.i(TAG, "DrawableFactory object created");
	}

	public Drawable resize(Drawable image, int width, int height) {
		Bitmap d = ((BitmapDrawable) image).getBitmap();
		Bitmap bitmapOrig = Bitmap.createScaledBitmap(d, width, height, false);
		return new BitmapDrawable(bitmapOrig);
	}

	public Drawable resize(Drawable myDrawable, double width, double height) {
		return resize(myDrawable, (int) width, (int) height);
	}

	public Drawable getDrawable(ImageView pic, Resources r) {

		String uri = (String) pic.getTag();
		int imageResource = r.getIdentifier(uri, "drawable", packageName);

		Drawable myDrawable = r.getDrawable(imageResource);

		return myDrawable;

	}
}
