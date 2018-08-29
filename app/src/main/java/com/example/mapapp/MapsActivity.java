package com.example.mapapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private int CURRENT_MAP_TYPE_INDEX = 1;
    int[] MAP_TYPES = {
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_TERRAIN,
            GoogleMap.MAP_TYPE_SATELLITE,
    };

    private GoogleMap mMap;

    private boolean isSatellite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ButterKnife.bind(this);

        if (mMap != null) {
            loadPreferences();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        savePreferences();
    }

    public void savePreferences() {
        SharedPreferences prefs = getSharedPreferences(
                "com.example.mapapp",
                Context.MODE_PRIVATE
        );

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("mapType", CURRENT_MAP_TYPE_INDEX);
        editor.putFloat("lat", (float) mMap.getCameraPosition().target.latitude);
        editor.putFloat("long", (float) mMap.getCameraPosition().target.longitude);
        editor.putFloat("zoom", mMap.getCameraPosition().zoom);
        editor.commit();
    }

    public void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences(
                "com.example.mapapp",
                Context.MODE_PRIVATE
        );

        CURRENT_MAP_TYPE_INDEX = prefs.getInt("mapType", GoogleMap.MAP_TYPE_NORMAL);

        float lat = prefs.getFloat("lat", 41);
        float longg = prefs.getFloat("long", -81);
        float zoom = prefs.getFloat("zoom", 4);

        setMapType();
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, longg)));
    }

    @OnClick(R.id.toggleView)
    public void toggleSatellite() {
        CURRENT_MAP_TYPE_INDEX++;
        CURRENT_MAP_TYPE_INDEX = CURRENT_MAP_TYPE_INDEX % MAP_TYPES.length;
        setMapType();
    }

    public void setMapType() {
        mMap.setMapType(MAP_TYPES[CURRENT_MAP_TYPE_INDEX]);
    }

    @OnClick(R.id.zoomin)
    public void zoomIn() {
        float zoom = mMap.getCameraPosition().zoom;
        setZoom(zoom + 1);
    }

    @OnClick(R.id.zoomout)
    public void zoomOut() {
        float zoom = mMap.getCameraPosition().zoom;
        setZoom(zoom - 1);
    }

    public void setZoom(float zoom) {
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    @OnClick(R.id.goToSeattle)
    public void goToSeattle() {
        LatLng cleveland = new LatLng(41, -81);
        mMap.addMarker(new MarkerOptions().position(cleveland).title("Cleveland"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(cleveland));

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
        loadPreferences();
    }
}
