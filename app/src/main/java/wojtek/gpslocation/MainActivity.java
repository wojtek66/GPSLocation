package wojtek.gpslocation;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationInterface{

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        View rootView = (View) findViewById(android.R.id.content);
        tv = (TextView) findViewById(R.id.textView);

        final LocationManager lm = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);
        //if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        //    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        //}else {
            try {
                List<String> providers = lm.getProviders(true);

                Location l = null;

                for (int i = providers.size() - 1; i >= 0; i--) {
                    try {
                        l = lm.getLastKnownLocation(providers.get(i));
                    } catch (SecurityException e) {
                        tv.setText(e.toString());
                    }
                    if (l != null) break;
                }

                double[] gpsData = new double[2];
                if (l != null) {
                    gpsData[0] = l.getLatitude();
                    gpsData[1] = l.getLongitude();
                }
                if (l != null && l.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
                    if (tv != null)
                        tv.setText("Latitude: " + gpsData[0] + " Longitude: " + gpsData[1] + " from LastKnownLocation");
                } else {
                    Snackbar.make(rootView, "LastKnownLocation too old!", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    LocationManager locationManager;
                    MyLocationListener locListener, netLocListener;
                    locListener = new MyLocationListener(MainActivity.this);
                    netLocListener = new MyLocationListener(MainActivity.this);
                    locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, netLocListener);
                }
            } catch (SecurityException e) {
                tv.setText(e.toString());
                Snackbar.make(rootView, e.toString(), Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
       // }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      .setAction("Action", null).show();
                try {
                    List<String> providers = lm.getProviders(true);

                    Location l = null;

                    for (int i = providers.size() - 1; i >= 0; i--) {
                        try {
                            l = lm.getLastKnownLocation(providers.get(i));
                        } catch (SecurityException e) {
                            tv.setText(e.toString());
                        }
                        if (l != null) break;
                    }

                    double[] gpsData = new double[2];
                    if (l != null) {
                        gpsData[0] = l.getLatitude();
                        gpsData[1] = l.getLongitude();
                    }
                    if (l != null && l.getTime() > Calendar.getInstance().getTimeInMillis() - 2 * 60 * 1000) {
                        if (tv != null)
                            tv.setText("Latitude: " + gpsData[0] + " Longitude: " + gpsData[1] + " from LastKnownLocation");
                    }
                }catch (SecurityException e){}
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void whenLocationChanged(Location location) {
        if(tv != null) tv.setText("Latitude: "+location.getLatitude()+" Longitude: "+location.getLongitude());
    }

    @Override
    public void whenProviderEnabled() {
        if(tv != null) tv.setText("Provider enabled\n"+tv.getText().toString());
    }
}
