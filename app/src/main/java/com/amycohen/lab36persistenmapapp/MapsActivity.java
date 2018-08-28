package com.amycohen.lab36persistenmapapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
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
        SharedPreferences prefs = getSharedPreferences("com.amycohen.lab36persistenmapapp", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putInt("mapType", CURRENT_MAP_TYPE_INDEX);
        editor.putFloat("lat", (float) mMap.getCameraPosition().target.latitude);
        editor.putFloat("long", (float) mMap.getCameraPosition().target.longitude);
        editor.putFloat("zoom", mMap.getCameraPosition().zoom);

        editor.commit();

    }

    public void loadPreferences() {
        SharedPreferences prefs = getSharedPreferences("com.amycohen.lab36persistenmapapp", Context.MODE_PRIVATE);

        CURRENT_MAP_TYPE_INDEX = prefs.getInt("mapType", 1);

        float latitude = prefs.getFloat("lat", (float) 47.6062095);
        float longitude = prefs.getFloat("long", (float) -122.3320708);
        float zoom = prefs.getFloat("zoom", 4);

        setMapType();
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
    }

    @OnClick(R.id.toggleMapType)
    public void toggleMapType () {
        CURRENT_MAP_TYPE_INDEX ++;
        CURRENT_MAP_TYPE_INDEX = CURRENT_MAP_TYPE_INDEX % MAP_TYPES.length;
        setMapType();
    }

    public void setMapType() {
        mMap.setMapType(MAP_TYPES[CURRENT_MAP_TYPE_INDEX]);
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
        loadPreferences();
        }

    //Currently not working. Will build out during lecture on Tuesday
    @OnClick(R.id.search)
    public void search () {
        //key — Your application's API key. This key identifies your application. See Get a key for more information.
       // input — The text input specifying which place to search for (for example, a name, address, or phone number).
       // inputtype — The type of input. This can be one of either textquery or phonenumber. Phone numbers must be in international format (prefixed by a plus sign ("+"), followed by the country code, then the phone number itself). See E.164 ITU recommendation for more information.

//        String url = "";
//        url += "?key=";
//        url += "?input=";
//        url += "?inputtype=textquery";
    }

}
