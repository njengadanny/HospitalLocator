package com.example.hospitallocator;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

public class NavMapsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener {
//        extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener {


    private GoogleMap mMap;

    private SupportMapFragment mapFragment;
    private final static int MY_PERMISSIONS_FINE_LOCATION = 101;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    LocationRequest nLocationRequest;
    GoogleApiClient nGoogleApiClient;
    Location nLastLocation;
    Marker mCurrLocationMarker;

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_maps);
        Toolbar toolbar = findViewById(R.id.toolbar_nav);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //Implement the InfoWindowAdapter interface to inflate the layout and load the info window content

        // Do other setup activities here too, as described elsewhere in this tutorial.
//        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
//
//            @Override
//            // Return null here, so that getInfoContents() is called next.
//            public View getInfoWindow(Marker arg0) {
//                return null;
//            }
//
//            @Override
//            public View getInfoContents(Marker marker) {
//                // Inflate the layouts for the info window, title and snippet.
//                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
//
//                TextView title = ((TextView) infoWindow.findViewById(R.id.title));
//                title.setText(marker.getTitle());
//
//                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
//                snippet.setText(marker.getSnippet());
//
//                return infoWindow;
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_maps, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            // Handle the camera action
            Intent intent = new Intent(NavMapsActivity.this, ImportActivity.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_Reviews) {

            Intent intent = new Intent(this, GalleryActivity.class);
            startActivity(intent);
            finish();
//            menuFragment = MenuFragment.newInstance("Menu 2");

        } else if (id == R.id.nav_services) {

        } else if (id == R.id.nav_aboutus) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_contactus) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

//    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

//    @Override
    public void onProviderEnabled(String s) {

    }

//    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in current_location and move the camera
        LatLng current_location = new LatLng(-1.232896390015213, 36.8754109045938);
        mMap.addMarker(new MarkerOptions()
                .position(current_location)
                .title("current_location: -1.232896390015213, 36.8754109045938 "));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(current_location));


        // Add a marker in hospital and move the camera
        LatLng hospital = new LatLng(-1.22656922467476, 36.885272937187096);
        mMap.addMarker(new MarkerOptions()
                .position(hospital)
                .title("hospital: -1.22656922467476, 36.885272937187096 "));


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(NavMapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_FINE_LOCATION);
            mMap.setMyLocationEnabled(true);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_FINE_LOCATION);
//            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
            // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

    }
//
//    protected void loadMap(GoogleMap googleMap) {
//        if (googleMap != null) {
//            // Attach marker click listener to the map here
//            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//                public boolean onMarkerClick(Marker marker) {
//                    // Handle marker click here
//                }
//            });
//
//        }
//    }
}

