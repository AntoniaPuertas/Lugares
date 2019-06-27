package com.example.lugares;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lugares.data.Lugar;
import com.example.lugares.utilidades.PermissionUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.lugares.data.ListaDatos.listaLugares;

public class LocalizacionActivity extends AppCompatActivity implements
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener, GoogleMap.OnInfoWindowClickListener {


    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private boolean mPermissionDenied = false;

    private GoogleMap mMap;
    private boolean marcadorCreado = false;
    private String accion;
    private Lugar lugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizacion);


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle extra = getIntent().getExtras();
        accion = extra.getString("accion");
        if(accion.equals("modificar")){
            lugar = listaLugares.get(extra.getInt("posicion"));
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerDragListener(this);

        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        if(lugar != null){
            marcadorCreado = true;
            LatLng posicion = new LatLng(lugar.getLatitud(), lugar.getLongitud());
            mMap.addMarker(new MarkerOptions()
                    .position(posicion)
                    .title(lugar.getNombre())
                    .snippet("Modificar Localización")
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.point))
            );
            mMap.animateCamera(CameraUpdateFactory.newLatLng(posicion));
        }else{
            //map.animateCamera(CameraUpdateFactory.zoomTo(map.getCameraPosition().zoom - 0.5f));
        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(!marcadorCreado){
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Nuevo Lugar")
                    .snippet("Añadir este lugar a la lista")
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.add))
            );
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            marcadorCreado = true;
        }

    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        marker.hideInfoWindow();
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        marker.showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent intent = getIntent();
        intent.putExtra("latitud", marker.getPosition().latitude);
        intent.putExtra("longitud", marker.getPosition().longitude);
        setResult(RESULT_OK, intent);
        finish();
    }
}
