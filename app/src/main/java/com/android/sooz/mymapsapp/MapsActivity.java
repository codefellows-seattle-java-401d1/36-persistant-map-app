package com.android.sooz.mymapsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private int CURRENT_MAP_TYPE_INDEX = 1;
    int[] MAP_TYPES = {
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_SATELLITE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ButterKnife.bind(this);
    }

    @Override
    public void onSaveInstanceState (Bundle outState){
        super.onSaveInstanceState(outState);
        savePreferences();
    }

    public void savePreferences () {

        SharedPreferences pref = getSharedPreferences(
                "com.android.sooz.mymapsapp",
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("mapType", CURRENT_MAP_TYPE_INDEX);
        editor.putFloat("lat", (float) mMap.getCameraPosition().target.latitude);
        editor.putFloat("long", (float) mMap.getCameraPosition().target.longitude);
        editor.putFloat("zoom", mMap.getCameraPosition().zoom);
        editor.commit();

    }

    public void loadPreferences () {
        SharedPreferences pref = getSharedPreferences(
                "com.android.sooz.mymapsapp",
                Context.MODE_PRIVATE
        );
        CURRENT_MAP_TYPE_INDEX = pref.getInt("mapType", GoogleMap.MAP_TYPE_NORMAL);
        float lat = pref.getFloat("lat", 40);
        float longg = pref.getFloat("long",  -122);
        float zoom = pref.getFloat("zoom", 4);

        setMapType(CURRENT_MAP_TYPE_INDEX);
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, longg)));
    }

    @OnClick(R.id.toggleMapType)
    public void toggleMapType() {
        CURRENT_MAP_TYPE_INDEX++;
        CURRENT_MAP_TYPE_INDEX = CURRENT_MAP_TYPE_INDEX % MAP_TYPES.length;
        setMapType(MAP_TYPES[CURRENT_MAP_TYPE_INDEX]);
    }

    public void setMapType(int index) {
        mMap.setMapType((index));

    }

    @OnClick(R.id.zoomOut)
    public void zoomOut () {
        float zoom = mMap.getCameraPosition().zoom;
        setZoom(zoom - 1);
    }

    @OnClick(R.id.zoomIn)
    public void zoomIn () {
        float zoom = mMap.getCameraPosition().zoom;
        setZoom(zoom + 1);
    }

    public void setZoom ( float zoom){
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    @OnClick(R.id.goToBoracay)
    public void goToBoracay () {
        LatLng boracay = new LatLng(12, 122);
        mMap.addMarker(new MarkerOptions().position(boracay).title("Marker on Boracay"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(boracay));
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady (GoogleMap googleMap){
        mMap = googleMap;

        //on default load, will go to pacific NW USA
        loadPreferences();
    }

    //adding from Friday lecture so I can see activity in Logcat
    @Override
    public void onStart() {
        super.onStart();
        Log.d("LIFECYCLE", "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LIFECYCLE", "onResume");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d("LIFECYCLE", "onRestart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("LIFECYCLE", "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("LIFECYCLE", "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("LIFECYCLE", "onDestroy");
    }
}
