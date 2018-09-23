package assignment.panos_lab;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private int CURRENT_MAP_TYPE_INDEX = 1;

    int[] MAP_TYPES = {
            GoogleMap.MAP_TYPE_NORMAL,
            GoogleMap.MAP_TYPE_HYBRID,
            GoogleMap.MAP_TYPE_NONE,
            GoogleMap.MAP_TYPE_SATELLITE,
            GoogleMap.MAP_TYPE_TERRAIN
    };

    private GoogleMap mMap;

    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        loadPreferences();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ButterKnife.bind(this);

        if (mMap != null) {
            loadPreferences();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if(mMap!=null) {
            savePreferences();
        }
    }

    public void savePreferences(){
        SharedPreferences prefs = getSharedPreferences(
                "assignment.panos_lab",
                Context.MODE_PRIVATE
        );
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("mapType", CURRENT_MAP_TYPE_INDEX);
        editor.putFloat("lat", (float) mMap.getCameraPosition().target.latitude);
        editor.putFloat("long", (float) mMap.getCameraPosition().target.longitude);
        editor.putFloat("zoom", (float) mMap.getCameraPosition().zoom);
        editor.commit();
    }

    public void loadPreferences(){
        SharedPreferences prefs = getSharedPreferences(
                "assignment.panos_lab",
                Context.MODE_PRIVATE
        );
        CURRENT_MAP_TYPE_INDEX = prefs.getInt("mapType",GoogleMap.MAP_TYPE_NORMAL);

        float lat = prefs.getFloat("lat",0);
        float longg = prefs.getFloat("long",0);
        float zoom = prefs.getFloat("zoom",1);

        setMapType();
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat,longg)));
    }

    public void setMapType(){
        mMap.setMapType(MAP_TYPES[CURRENT_MAP_TYPE_INDEX]);
    }

    public void setZoom(float zoom){
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    @OnClick(R.id.zoomin)
    public void zoomIn(){
        setZoom(mMap.getCameraPosition().zoom-1);
    }

    @OnClick(R.id.zoomout)
    public void zoomOut(){
        setZoom(mMap.getCameraPosition().zoom+1);
    }

    @OnClick(R.id.savePref)
    public void saveButton(){
        savePreferences();
    }

}
