 package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

 /**
  * Displays a map showing where sales are
  */
 public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private double target_latitude, target_longitude, currentLatitude, currentLongitude;
    private int tempId = -1;
    private int index = 0;
    private View tempV = null;
    private List<Address> Addresses = null;
    private static final String TAG = MapsActivity.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private String loCation;

    /**
     * Creates the map activity to display
     * @param savedInstanceState - saved information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        CheckBox hybrid_mode, satellite_mode, terrain_mode, none_mode;
        Intent intent = getIntent();

        loCation = (String) intent.getSerializableExtra("Location_info");
        hybrid_mode = (CheckBox) findViewById(R.id.hybrid);
        satellite_mode = (CheckBox) findViewById(R.id.satellite);
        terrain_mode = (CheckBox) findViewById(R.id.terrain);
        none_mode = (CheckBox) findViewById(R.id.none);

        setUpMapIfNeeded();

        hybrid_mode.setOnClickListener(CheckBoxOnClickListener);
        satellite_mode.setOnClickListener(CheckBoxOnClickListener);
        terrain_mode.setOnClickListener(CheckBoxOnClickListener);
        none_mode.setOnClickListener(CheckBoxOnClickListener);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setInterval(10 * 1000).setFastestInterval(1000);
    }

    /**
     * Resume the map activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        mGoogleApiClient.connect();
    }

    /**
     * Pause the connection
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        LatLng latLng = new LatLng(target_latitude, target_longitude);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        mMap.addMarker(new MarkerOptions().position(new LatLng(target_latitude, target_longitude)).title("HERE"));
    }

    /**
     * Creating listener for checkboxes and call setCheckB
     */
    private final CheckBox.OnClickListener CheckBoxOnClickListener = new CheckBox.OnClickListener() {
        public void onClick(View v) {
            setCheckB(v);
        }
    };
    /**
     * Setting the check boxes to change map types
     *
     * @param v view of the app
     */
    private void setCheckB(View v) {
        boolean checked = ((CheckBox) v).isChecked();

        if(tempId != v.getId() && tempV != null) ((CheckBox) tempV).setChecked(false);
        switch (v.getId()) {
            case R.id.hybrid:
                if (checked)
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                break;
            case R.id.satellite:
                if (checked)
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                break;
            case R.id.terrain:
                if (checked)
                    mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                break;
            case R.id.none:
                if (checked)
                    mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                else {
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                break;
        }
        tempId = v.getId();
        tempV = v;
    }

    /**
     * Called when {@code mGoogleApiClient} connection is suspended.
     */
    @Override
    public void onConnectionSuspended(int i) {
    }

    /**
     * This is called when location is changed.
     * Then call handleNewLocation method
     *
     * @param location changed location
     */
    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location);
    }

    /**
     * Getting the current place's latitude and longitude
     * then find addresses of sales locations
     *
     * @param location location of current location
     */
    private void handleNewLocation(Location location) {
        Log.d(TAG, location.toString());
        List<Address> BoundAddress;
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        try{
            Addresses = new Geocoder(this, Locale.getDefault()).getFromLocationName(loCation,100);
            if(Addresses.size() > 1) {
                Addresses.clear();
                BoundAddress = new Geocoder(this, Locale.getDefault()).getFromLocation(currentLatitude, currentLongitude, 1);
                String stateName = BoundAddress.get(0).getAddressLine(1);
                StringBuilder bound = new StringBuilder(" " + stateName).replace(1, 7, "");
                loCation = loCation + bound;
                Addresses = new Geocoder(this, Locale.getDefault()).getFromLocationName(loCation, 100);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if ((Addresses == null) || (Addresses.isEmpty())) {
            Toast.makeText(MapsActivity.this, "Could not find the place!", Toast.LENGTH_SHORT).show();
        } else {
            if(Addresses.size() > 1) {index = Dist_Calc(Addresses);}
            target_latitude = Addresses.get(index).getLatitude();
            target_longitude = Addresses.get(index).getLongitude();
        }
        setUpMap();
    }

    /**
     * Called when {@code mGoogleApiClient} is connected.
     */
    @Override
    public void onConnected(Bundle bundle) {
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
        else {
            handleNewLocation(location);
        }
    }

    /**
     * Called when {@code mGoogleApiClient} is trying to connect but failed.
     * Handle {@code result.getResolution()} if there is a resolution
     * available.
     *
     * @param connectionResult result of connection of mGoogleApiClient
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            Log.i(TAG, "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * Calculate distances between candidate places and current location.
     * Then choose a closest place.
     *
     * @param list A list of candidate addresses
     * @return pos An index of the closest place
     */
    private int Dist_Calc(List<Address> list) {
        double dif_lat, dif_long, temp_dist, short_dist;
        int pos = 0;
        int i;
        double stan_lat = currentLatitude;
        double stan_long = currentLongitude;
        short_dist = 0;

        for(i = 0; i < list.size(); i++) {
            dif_lat = stan_lat - (list.get(i).getLatitude());
            dif_long = stan_long - (list.get(i).getLongitude());
            temp_dist = Math.sqrt(dif_lat*dif_lat + dif_long*dif_long);
            if(temp_dist < short_dist) {
                short_dist = temp_dist;
                pos = i;
            }
        }
        return pos;
    }
}
