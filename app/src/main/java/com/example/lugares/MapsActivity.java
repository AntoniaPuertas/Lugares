package com.example.lugares;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.lugares.data.Lugar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.lugares.data.ListaDatos.listaLugares;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    Lugar lugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extras = getIntent().getExtras();
        lugar = listaLugares.get(extras.getInt("posicion"));

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        // Add a marker in Sydney and move the camera
        LatLng posicion = new LatLng(lugar.getLatitud(), lugar.getLongitud());
        mMap.addMarker(new MarkerOptions()
                .position(posicion)
                .title(lugar.getNombre())
                .snippet(lugar.getDescripcion())
                );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(posicion));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //TODO:abrir el street view
    }
}
