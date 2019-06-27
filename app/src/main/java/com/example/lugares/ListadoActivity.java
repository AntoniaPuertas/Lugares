package com.example.lugares;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lugares.data.Lugar;
import com.example.lugares.utilidades.GestionFicheros;

import static com.example.lugares.data.ListaDatos.listaLugares;

public class ListadoActivity extends AppCompatActivity {

    ListView lvLugares;
    ListaLugaresAdapter listaLugaresAdapter;
    FloatingActionButton fbNuevoLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        lvLugares = findViewById(R.id.lvLugares);
        fbNuevoLugar = findViewById(R.id.fbNuevoLugar);

        GestionFicheros.recuperarDatos(this);
        /*
        listaLugares.add(new Lugar("Playa de la Concha", "Bonita playa de arenas doradas", 43.315289, -1.991470, 5));
        listaLugares.add(new Lugar("Playa de la Concha2", "Bonita playa de arenas doradas2", 43.315289, -1.991470, 4));
        listaLugares.add(new Lugar("Playa de la Concha3", "Bonita playa de arenas doradas3", 43.315289, -1.991470, 3));
        listaLugares.add(new Lugar("Playa de la Concha4", "Bonita playa de arenas doradas4", 43.315289, -1.991470, 2));
        */
        listaLugaresAdapter = new ListaLugaresAdapter(this, listaLugares);

        lvLugares.setAdapter(listaLugaresAdapter);

        fbNuevoLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListadoActivity.this, NuevvoLugarActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.it_nuevo_lugar:
                //TODO:hacer algo


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        listaLugaresAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GestionFicheros.guardarDatos(this);
    }
}
