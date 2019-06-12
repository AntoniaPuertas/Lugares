package com.example.lugares;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class NuevvoLugarActivity extends AppCompatActivity {

    TextView txtTituloNuevoLugar;
    EditText etNombreNuevoLugar;
    EditText etDescripcionNuevoLugar;
    RatingBar rbNuevoLugar;
    ImageView imgSeleccionarNuevoLugar;
    Button btnCancelarNuevoLugar;
    Button btnCrearNuevoLugar;

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
    }

    
}
