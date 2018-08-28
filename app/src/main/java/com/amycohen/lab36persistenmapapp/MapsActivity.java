package com.amycohen.lab36persistenmapapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
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
    private Boolean isSatellite = false;

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

    public void savePreferences() {
        SharedPreferences prefs = getSharedPreferences("com.amycohen.lab36persistenmapapp", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("isSatellite", isSatellite);
        editor.putFloat("lat", (float) mMap.getCameraPosition().target.latitude);
        editor.putFloat("long", (float) mMap.getCameraPosition().target.longitude);
        editor.putFloat("zoom", mMap.getCameraPosition().zoom);

        editor.commit();

    }

    public void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences("com.amycohen.lab36persistenmapapp", Context.MODE_PRIVATE);

        isSatellite = prefs.getBoolean("isSatellite", false);
        float latitude = prefs.getFloat("lat", (float) 47.6062095);
        float longitude = prefs.getFloat("long", (float) -122.3320708);
        float zoom = prefs.getFloat("zoom", 4);
    }

    @OnClick(R.id.zoomOut)
    public void ZoomOut () {
        float zoom = mMap.getCameraPosition().zoom;
        setZoom (zoom - 1);
    }

    @OnClick(R.id.zoomIn)
    public void ZoomIn () {
        float zoom = mMap.getCameraPosition().zoom;
        setZoom (zoom + 1);
    }

    public void setZoom (float zoom) {
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    @OnClick(R.id.goToSeattle)
    public void goToSeattle () {
        LatLng seattle = new LatLng(47.6062095, -122.3320708);
        mMap.addMarker(new MarkerOptions().position(seattle).title("Marker in Seattle"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seattle));
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
