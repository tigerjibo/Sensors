package pl.etakt.mobile.sensors.tabs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
 
public class GravityActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        TextView textview = new TextView(this);
        textview.setText("This is Gravity sensor");
        setContentView(textview);
    }
}