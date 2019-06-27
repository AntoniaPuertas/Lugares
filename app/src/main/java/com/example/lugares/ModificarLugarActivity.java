package com.example.lugares;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lugares.data.Lugar;

import static com.example.lugares.data.ListaDatos.listaLugares;

public class ModificarLugarActivity extends AppCompatActivity {
    TextView txtTituloNuevoLugar;
    EditText etNombreNuevoLugar;
    EditText etDescripcionNuevoLugar;
    RatingBar rbNuevoLugar;
    ImageView imgSeleccionarNuevoLugar;
    Button btnCancelarNuevoLugar;
    Button btnCrearNuevoLugar;

    private final static int RESPUESTA_LOCALIZACION = 34;
    double latitud = 0;
    double longitud = 0;
    Lugar lugar;
    int posicion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevvo_lugar);

        txtTituloNuevoLugar = findViewById(R.id.txtTituloNuevoLugar);
        etNombreNuevoLugar = findViewById(R.id.etNombreNuevoLugar);
        etDescripcionNuevoLugar = findViewById(R.id.etDescripcionNuevoLugar);
        rbNuevoLugar = findViewById(R.id.rbNuevoLugar);
        imgSeleccionarNuevoLugar = findViewById(R.id.imgSeleccionarNuevoLugar);
        btnCancelarNuevoLugar = findViewById(R.id.btnCancelarNuevoLugar);
        btnCrearNuevoLugar = findViewById(R.id.btnCrearNuevoLugar);

        txtTituloNuevoLugar.setText("Modificar Datos");
        btnCrearNuevoLugar.setText("Guardar");

        Bundle extras = getIntent().getExtras();
        posicion = extras.getInt("posicion");
        lugar = listaLugares.get(posicion);
        latitud = lugar.getLatitud();
        longitud = lugar.getLongitud();
        etNombreNuevoLugar.setText(lugar.getNombre());
        etDescripcionNuevoLugar.setText(lugar.getDescripcion());
        rbNuevoLugar.setRating(lugar.getValoracion());
    }

    public void seleccionarNuevoLugar(View view){
        //abrir mapa con ubicacion actual
        Intent intent = new Intent(ModificarLugarActivity.this, LocalizacionActivity.class);
        intent.putExtra("posicion", posicion);

        intent.putExtra("accion", "modificar");
        startActivityForResult(intent, RESPUESTA_LOCALIZACION);
    }

    public void cancelarNuevoLugar(View view){
        finish();
    }

    public void crearNuevoLugar(View view){
        if(comprobarDatos()){
            //modifica el lugar
            lugar.setNombre(etNombreNuevoLugar.getText().toString());
            lugar.setDescripcion(etDescripcionNuevoLugar.getText().toString());
            lugar.setValoracion(rbNuevoLugar.getRating());
            lugar.setLatitud(latitud);
            lugar.setLongitud(longitud);
            finish();
        }
    }

    public boolean comprobarDatos(){
        boolean datosCorrectos = true;
        if(etNombreNuevoLugar.getText().toString().isEmpty()){
            etNombreNuevoLugar.setError("Introduce el nombre del lugar");
            datosCorrectos = false;
        }
        if(etDescripcionNuevoLugar.getText().toString().isEmpty()){
            etDescripcionNuevoLugar.setError("Introduce una descripción");
            datosCorrectos = false;
        }
        if(latitud == 0 && longitud == 0){
            Toast.makeText(this, "Selecciona el lugar en el mapa", Toast.LENGTH_SHORT).show();
            datosCorrectos = false;
        }
        return datosCorrectos;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == RESPUESTA_LOCALIZACION){
            latitud = data.getExtras().getDouble("latitud");
            longitud = data.getExtras().getDouble("longitud");

            Toast.makeText(this, "Posición " + latitud, Toast.LENGTH_SHORT).show();
        }
    }
}
