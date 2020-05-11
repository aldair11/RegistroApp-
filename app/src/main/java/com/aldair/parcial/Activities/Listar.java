package com.aldair.parcial.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;

import com.aldair.parcial.Conexion.Controlador;
import com.aldair.parcial.Model.Adaptador;
import com.aldair.parcial.Model.Persona;
import com.aldair.parcial.R;

import java.util.ArrayList;

public class Listar extends AppCompatActivity implements LifecycleObserver {

    ListView lista;

    ArrayList<Persona> listaPersona;

    Controlador controlador;

    Adaptador adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar);

        lista = findViewById(R.id.listView);

        controlador = new Controlador(getApplicationContext());

        listaPersona = controlador.optenerRegistros();

        adapter = new Adaptador(getApplicationContext(), R.layout.item, listaPersona);
        lista.setAdapter(adapter);

        registerForContextMenu(lista);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                ArrayList<Persona> listaServis = controlador.optenerRegistros();
                Adaptador adap = new Adaptador(getApplicationContext(), R.layout.item, listaServis);
                lista.setAdapter(adap);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "modificacion cancelada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.listado, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.action_modificar_registro:
                modificarRegistro(menuInfo.position);
                return true;
            case R.id.action_borrar_registro:
                borrarRegistro(menuInfo.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }


    private void modificarRegistro(int posicion) {
        Intent intent = new Intent(this, Modificar.class);
        intent.putExtra("indice", posicion);
        startActivityForResult(intent, 2);
    }

    private void borrarRegistro(int posicion) {
        int retorno = controlador.borrarRegistro(listaPersona.get(posicion));
        if (retorno == 1) {
            Toast.makeText(getApplicationContext(), "Eliminado", Toast.LENGTH_SHORT).show();
            listaPersona = controlador.optenerRegistros();
            adapter = new Adaptador(getApplicationContext(), R.layout.item, listaPersona);
            lista.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "Ups Hubo un problema al eliminar", Toast.LENGTH_SHORT).show();
        }
    }
}
