package com.gbbeard.gpsbygbbeard;

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
    private Boolean isSatellite;

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
        SharedPreferences prefs = getSharedPreferences("com.gbbeard.gpsbygbbeard", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("mapType", CURRENT_MAP_TYPE_INDEX);
        editor.putFloat("lat", (float) mMap.getCameraPosition().target.latitude);
        editor.putFloat("long", (float) mMap.getCameraPosition().target.longitude);
        editor.putFloat("zoom", mMap.getCameraPosition().zoom);

        editor.commit();

    }

    public void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences("com.gbbeard.gpsbygbbeard", Context.MODE_PRIVATE);

        CURRENT_MAP_TYPE_INDEX = prefs.getInt("mapType", 1);

        float latitude = prefs.getFloat("lat", (float) 47);
        float longitude = prefs.getFloat("long", (float) -122);
        float zoom = prefs.getFloat("zoom", 4);

        setMapType();
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
    }

    @OnClick(R.id.toggleMap)
    public void toggleMapType() {
        CURRENT_MAP_TYPE_INDEX++;
        CURRENT_MAP_TYPE_INDEX = CURRENT_MAP_TYPE_INDEX % MAP_TYPES.length;
        setMapType();
    }

    public void setMapType() {
        mMap.setMapType(MAP_TYPES[CURRENT_MAP_TYPE_INDEX]);
    }

    @OnClick(R.id.zoomIn)
    public void ZoomIn() {
        float zoom = mMap.getCameraPosition().zoom;
        setZoom(zoom + 1);
    }
    @OnClick(R.id.zoomOut)
    public void ZoomOut() {
        float zoom = mMap.getCameraPosition().zoom;
        setZoom(zoom - 1);
    }


    public void setZoom(float zoom) {
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    @OnClick(R.id.seattleMarker)
    public void goToSeattle() {
        LatLng seattle = new LatLng(47.6062, -122.3320);
        mMap.addMarker(new MarkerOptions().position(seattle).title("Marker in Seattle"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seattle));
    }

    @OnClick(R.id.geiselwindMarker)
    public void gotToGeiselwind() {
        LatLng seattle = new LatLng(49.7731, 10.4706);
        mMap.addMarker(new MarkerOptions().position(seattle).title("Marker in Geiselwind"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(seattle));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        loadPreferences();
    }
}