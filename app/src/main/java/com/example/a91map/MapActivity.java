package com.example.a91map;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        ArrayList<Item> items = dbHelper.getAllItems();

        for (Item item : items) {
            String loc = item.getLocation();
            if (loc != null && loc.contains(",")) {
                try {
                    String[] latLng = loc.split(",");
                    double lat = Double.parseDouble(latLng[0].trim());
                    double lng = Double.parseDouble(latLng[1].trim());

                    LatLng position = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions()
                            .position(position)
                            .title(item.getTitle())
                            .snippet(item.getDescription()));
                } catch (Exception ignored) {}
            }
        }

        if (!items.isEmpty()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-37.8136, 144.9631), 10)); // Melbourne
        }
    }
}
