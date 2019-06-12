package com.example.lugares.utilidades;

import android.content.Context;

import com.example.lugares.data.ListaDatos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class GestionFicheros {

    public static void guardarDatos(Context context) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(ListaDatos.rutaArchivo, Context.MODE_PRIVATE);

            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ListaDatos.listaLugares);
            oos.close();
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public static void recuperarDatos(Context context){
    FileInputStream fis = null;
    try {
        fis = context.openFileInput(ListaDatos.rutaArchivo);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ListaDatos.listaLugares = (ArrayList) ois.readObject();
        ois.close();
        fis.close();
    } catch (Exception e) {
        e.printStackTrace();
    }

}
}
