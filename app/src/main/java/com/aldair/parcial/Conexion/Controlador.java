package com.aldair.parcial.Conexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.aldair.parcial.Model.Persona;

import java.util.ArrayList;

public class Controlador {
    private Helper baseDatos;

    public Controlador(Context context) {
        this.baseDatos = new Helper(context, Model.NOMBRE_DB, null, 1);
    }

    public long agregarRegistro(Persona persona) {
        try {
            SQLiteDatabase database = baseDatos.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Model.COL_CEDULA, persona.getCedula());
            values.put(Model.COL_NOMBRE, persona.getNombre());
            values.put(Model.COL_ESTRATO, persona.getEstrato());
            values.put(Model.COL_SALARIO, persona.getSalario());
            values.put(Model.COL_NIVEL_EDUCATIVO, persona.getNivel_educativo());
            return database.insert(Model.NOMBRE_TABLA, null, values);
        } catch (Exception e) {
            return -1L;
        }
    }

    public int actualizarRegistro(Persona persona) {
        try {
            SQLiteDatabase database = baseDatos.getWritableDatabase();
            ContentValues valoresActualizados = new ContentValues();
            valoresActualizados.put(Model.COL_NOMBRE, persona.getNombre());
            valoresActualizados.put(Model.COL_ESTRATO, persona.getEstrato());
            valoresActualizados.put(Model.COL_SALARIO, persona.getSalario());
            valoresActualizados.put(Model.COL_NIVEL_EDUCATIVO, persona.getNivel_educativo());

            String campoParaActualizar = Model.COL_CEDULA + " = ?";
            String[] argumentosParaActualizar = {String.valueOf(persona.getCedula())};

            return database.update(Model.NOMBRE_TABLA, valoresActualizados, campoParaActualizar, argumentosParaActualizar);
        } catch (Exception e) {
            return 0;
        }
    }

    public int borrarRegistro(Persona persona) {
        try {
            SQLiteDatabase database = baseDatos.getWritableDatabase();
            String[] argumentos = {String.valueOf(persona.getCedula())};
            return database.delete(Model.NOMBRE_TABLA, Model.COL_CEDULA + " = ?", argumentos);
        } catch (Exception e) {
            return 0;
        }
    }

    public Persona buscarPersona(int cedula) {

        SQLiteDatabase database = baseDatos.getReadableDatabase();

        String[] columnasConsultar = {Model.COL_CEDULA, Model.COL_NOMBRE, Model.COL_ESTRATO
                , Model.COL_SALARIO, Model.COL_NIVEL_EDUCATIVO};
        String[] argumento = {String.valueOf(cedula)};
        Cursor cursor = database.query(Model.NOMBRE_TABLA, columnasConsultar
                , Model.COL_CEDULA + " = ?", argumento, null, null, null);

        if (cursor == null) {
            return null;
        }

        if (!cursor.moveToFirst()) {
            return null;
        }

        Persona persona = new Persona(cursor.getInt(0), cursor.getString(1)
                , cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
        cursor.close();
        return persona;
    }

    public ArrayList<Persona> optenerRegistros() {
        ArrayList<Persona> personas = new ArrayList<>();

        SQLiteDatabase database = baseDatos.getReadableDatabase();

        String[] columnasConsultar = {Model.COL_CEDULA, Model.COL_NOMBRE, Model.COL_ESTRATO
                , Model.COL_SALARIO, Model.COL_NIVEL_EDUCATIVO};

        Cursor cursor = database.query(Model.NOMBRE_TABLA, columnasConsultar
                , null, null, null, null, null);

        if (cursor == null) {
            return personas;
        }

        if (!cursor.moveToFirst()) {
            return personas;
        }

        do {
            Persona persona = new Persona(cursor.getInt(0), cursor.getString(1)
                    , cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
            personas.add(persona);
        } while (cursor.moveToNext());

        cursor.close();
        return personas;
    }
}