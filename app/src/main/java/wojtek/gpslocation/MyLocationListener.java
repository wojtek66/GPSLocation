package wojtek.gpslocation;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by wojte on 18.03.2016.
 */
interface LocationInterface {
    public void whenLocationChanged(Location location);
    public void whenProviderEnabled();
}

public class MyLocationListener implements LocationListener{

    LocationInterface parentActivity;
    public MyLocationListener(LocationInterface activity){
        this.parentActivity = activity;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.i("location", "onLocationChanged");
        parentActivity.whenLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.i("location","onProviderEnabled");
        parentActivity.whenProviderEnabled();
    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}