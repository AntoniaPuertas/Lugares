package com.example.lugares;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.lugares.data.Lugar;
import com.example.lugares.utilidades.GestionFicheros;

import static com.example.lugares.data.ListaDatos.listaLugares;

public class ListadoActivity extends AppCompatActivity {

    ListView lvLugares;
    ListaLugaresAdapter listaLugaresAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        lvLugares = findViewById(R.id.lvLugares);

        GestionFicheros.recuperarDatos(this);

        listaLugares.add(new Lugar("Playa de la Concha", "Bonita playa de arenas doradas", 43.315289, -1.991470, 5));
        listaLugares.add(new Lugar("Playa de la Concha2", "Bonita playa de arenas doradas2", 43.315289, -1.991470, 4));
        listaLugares.add(new Lugar("Playa de la Concha3", "Bonita playa de arenas doradas3", 43.315289, -1.991470, 3));
        listaLugares.add(new Lugar("Playa de la Concha4", "Bonita playa de arenas doradas4", 43.315289, -1.991470, 2));
        listaLugaresAdapter = new ListaLugaresAdapter(this, listaLugares);

        lvLugares.setAdapter(listaLugaresAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GestionFicheros.guardarDatos(this);
    }
}
