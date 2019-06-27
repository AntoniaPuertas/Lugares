package com.example.lugares;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.lugares.data.Lugar;

import java.util.ArrayList;

public class ListaLugaresAdapter extends ArrayAdapter<Lugar> {

    private ArrayList<Lugar> listaLugares;
    private Context context;

    public ListaLugaresAdapter(Context context, ArrayList<Lugar> listaLugares) {
        super(context, R.layout.activity_listado, listaLugares);

        this.listaLugares = listaLugares;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Lugar lugar = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_lista, parent, false);
        }

        TextView txtNombreLugar = (TextView) convertView.findViewById(R.id.txtNombreLugar);
        TextView txtDescripcionLugar = (TextView) convertView.findViewById(R.id.txtDescripcionLugar);
        RatingBar rbValoracionLugar = (RatingBar) convertView.findViewById(R.id.rbValoracionLugar);
        ImageView imgMapaLugar = (ImageView) convertView.findViewById(R.id.imgMapaLugar);
        ImageView imgEliminarLugar = (ImageView) convertView.findViewById(R.id.imgEliminarLugar);
        ImageView imgModificarLugar = (ImageView) convertView.findViewById(R.id.imgModificarLugar);

        txtNombreLugar.setText(lugar.getNombre());
        txtDescripcionLugar.setText(lugar.getDescripcion());
        rbValoracionLugar.setRating(lugar.getValoracion());

        imgMapaLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra("posicion", position);
                context.startActivity(intent);
            }
        });

        rbValoracionLugar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                lugar.setValoracion(rating);
            }
        });

        imgEliminarLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //eliminar lugar
                createSimpleDialog(position).show();
            }
        });

        imgModificarLugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //modificar lugar
                Intent intent = new Intent(context, ModificarLugarActivity.class);
                intent.putExtra("posicion", position);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public AlertDialog createSimpleDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Atención")
                .setMessage("¿Seguro que quieres borrar de la lista este lugar?")
                .setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //eliminar nota
                        listaLugares.remove(position);
                        notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //cancelar operación
                    }
                });
        return builder.create();
    }
}
