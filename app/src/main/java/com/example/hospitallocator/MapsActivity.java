package com.example.hospitallocator;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import android.content.Intent;
import android.graphics.Color;
import android.provider.Settings;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.hospitallocator.AppConfig.*;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,

        LocationListener{

    private GoogleMap mMap;
    LocationManager locationManager;
    CoordinatorLayout mainCoordinatorLayout;
    Button search_btn;
    TextView txtNameHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isGooglePlayServicesAvailable()) {
            return;
        }
        setContentView(R.layout.activity_maps);
        search_btn = findViewById(R.id.btn_search);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mainCoordinatorLayout = findViewById(R.id.mainCoordinatorLayout);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showLocationSettings();
        }

//        Toolbar toolbar = findViewById(R.id.toolbar);
////        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////        toolbar.setTitle("Navigation Sample");
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
//                R.string.open_drawer, R.string.close_drawer);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.setItemIconTintList(null);
//        try {
//            View headerView = navigationView.getHeaderView(0);
//            txtNameHeader = headerView.findViewById(R.id.txtNameHeader);
//        } catch (Exception ex) {
//
//        }
//
//        Fragment fragment = new Profile();
////        toolbar.setTitle(R.string.lblHeader_Profile);
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.add(R.id.content_frame, fragment);
//        ft.commit();
//    }
//
//
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        Fragment fragment = null;
//        Intent intent = null;
//
//        switch (id) {
//            case R.id.nav_profile:
//                fragment = new Profile();
//                break;
//            case R.id.nav_Reviews:
//                fragment = new Reviews();
//                break;
//            case R.id.nav_contactus:
//                fragment = new ContactUs();
//                break;
//            case R.id.nav_aboutus:
//                fragment = new AboutUs();
//                break;
//            case R.id.nav_services:
//                fragment = new Services();
//                break;
//            default:
//                fragment = new Profile();
//        }
//
//        if (fragment != null) {
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.replace(R.id.content_frame, fragment);
//            ft.commit();
//        } else {
//            startActivity(intent);
//        }
//
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//
//        return true;
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//
//    }
    }

        private void showLocationSettings() {
        Snackbar snackbar = Snackbar
                .make(mainCoordinatorLayout, "Location Error: GPS Disabled!",

                        Snackbar.LENGTH_LONG)
                .setAction("Enable", new View.OnClickListener() {
                    @Override                    public void onClick(View v) {

                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);

        View sbView = snackbar.getView();
        TextView textView = sbView

                .findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)

                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,

                android.Manifest.permission.ACCESS_COARSE_LOCATION)

                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        showCurrentLocation();
    }

    private void showCurrentLocation() {
        Criteria criteria = new Criteria();
        String bestProvider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(this,

                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&

                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)

                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(bestProvider);

        if (location != null) {
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(bestProvider, MIN_TIME_BW_UPDATES,

                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    }

    private void loadNearByPlaces(final double latitude, final double longitude) {
//YOU Can change this type at your own will, e.g hospital, cafe, restaurant.... and see how it all works

        String type = "hospital";
        StringBuilder googlePlacesUrl =

                new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=").append(latitude).append(",").append(longitude);
        googlePlacesUrl.append("&radius=").append(PROXIMITY_RADIUS);
        googlePlacesUrl.append("&types=").append(type);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + GOOGLE_BROWSER_API_KEY);

        JsonObjectRequest request = new JsonObjectRequest(googlePlacesUrl.toString(), null,

                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject result) {

                        Log.i(TAG, "onResponse: Result= " + result.toString());
                        parseLocationResult(result);
                    }
                },
                new Response.ErrorListener() {
                    @Override                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: Error= " + error);
                        Log.e(TAG, "onErrorResponse: Error= " + error.getMessage());
                    }
                });

        AppController.getInstance().addToRequestQueue(request);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadNearByPlaces(latitude, longitude);
            }
        });
    }

    private void parseLocationResult(JSONObject result) {

        String id, place_id, placeName = null, reference, icon, vicinity = null;
        double latitude, longitude;

        try {
            JSONArray jsonArray = result.getJSONArray("results");

            if (result.getString(STATUS).equalsIgnoreCase(OK)) {

                mMap.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject place = jsonArray.getJSONObject(i);

                    id = place.getString(HOSPITAL_ID);
                    place_id = place.getString(PLACE_ID);
                    if (!place.isNull(NAME)) {
                        placeName = place.getString(NAME);
                    }
                    if (!place.isNull(VICINITY)) {
                        vicinity = place.getString(VICINITY);
                    }
                    latitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)

                            .getDouble(LATITUDE);
                    longitude = place.getJSONObject(GEOMETRY).getJSONObject(LOCATION)

                            .getDouble(LONGITUDE);
                    reference = place.getString(REFERENCE);
                    icon = place.getString(ICON);

                    MarkerOptions markerOptions = new MarkerOptions();
                    LatLng latLng = new LatLng(latitude, longitude);
                    markerOptions.position(latLng);
                    markerOptions.title(placeName + " : " + vicinity);

                    mMap.addMarker(markerOptions);
                }

                Toast.makeText(getBaseContext(), jsonArray.length() + " Hospital found!",

                        Toast.LENGTH_LONG).show();
            } else if (result.getString(STATUS).equalsIgnoreCase(ZERO_RESULTS)) {
                Toast.makeText(getBaseContext(), "No Hospital found in 5KM radius!!!",

                        Toast.LENGTH_LONG).show();
            }

        } catch (JSONException e) {

            e.printStackTrace();
            Log.e(TAG, "parseLocationResult: Error=" + e.getMessage());
        }
    }

    @Override

    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        LatLng latLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        loadNearByPlaces(latitude, longitude);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {
    }

    @Override
    public void onProviderDisabled(String s) {
    }

    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

}

//import androidx.core.app.ActivityCompat;
//import androidx.fragment.app.FragmentActivity;
//
//import android.os.Bundle;
//
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import android.content.pm.PackageManager;
//import android.location.Criteria;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.util.Log;
//
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GoogleApiAvailability;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,LocationListener {
//
//    private static final String TAG = "CurrentLocation";
//    private GoogleMap mMap;
//    protected LocationManager locationManager;
//    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
//
//
//    @Override    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (!isGooglePlayServicesAvailable()) {
//            return;
//        }
//        setContentView(R.layout.activity_maps);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//    }
//
//
//    /**     * Manipulates the map once available.
//
//     * This callback is triggered when the map is ready to be used.
//
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//
//     * we just add a marker near Sydney, Australia.
//
//     * If Google Play services is not installed on the device, the user will be prompted to install
//
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//
//     * installed Google Play services and returned to the app.
//
//     */
//
//    @Override
//
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        displayCurrentLocation(mMap);
//    }
//
//    private void displayCurrentLocation(GoogleMap mMap) {
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
//
//                != PackageManager.PERMISSION_GRANTED
//
//                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
//
//                != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//
//            //    ActivityCompat#requestPermissions
//
//            // here to request the missing permissions, and then overriding
//
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//
//            //                                          int[] grantResults)
//
//            // to handle the case where the user grants the permission. See the documentation
//
//            // for ActivityCompat#requestPermissions for more details.
//
//            return;
//        }
//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setCompassEnabled(true);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//
//        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        Criteria criteria = new Criteria();
//        String bestProvider = locationManager.getBestProvider(criteria, true);
//        Location location = locationManager.getLastKnownLocation(bestProvider);
//
//        if (location != null) {
//            onLocationChanged(location);
//
//        }
//
//        locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
//
//    }
//
//    private boolean isGooglePlayServicesAvailable() {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if (apiAvailability.isUserResolvableError(resultCode)) {
//                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            } else {
//                Log.i(TAG, "This device is not supported.");
//                finish();
//            }
//            return false;
//        }
//        return true;
//    }
//
//    @Override    public void onLocationChanged(Location location) {
//        double latitude = location.getLatitude();
//        double longitude = location.getLongitude();
//
//        LatLng currentLocation = new LatLng(latitude, longitude);
//        mMap.addMarker(new MarkerOptions().position(currentLocation).title("My Location"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
//    }
//
//    @Override
//
//    public void onStatusChanged(String s, int i, Bundle bundle) {
//
//    }
//
//    @Override
//
//    public void onProviderEnabled(String s) {
//
//    }
//
//    @Override
//
//    public void onProviderDisabled(String s) {
//
//    }
//}