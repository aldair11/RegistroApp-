package com.aldair.parcial.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aldair.parcial.Conexion.Controlador;
import com.aldair.parcial.Model.Persona;
import com.aldair.parcial.R;

public class Buscar extends AppCompatActivity implements View.OnClickListener{
    Controlador controlador;

    EditText edCedula;

    Button btBuscar, btRegresar;

    TextView tvCedula, tvNombre, tvEstrato, tvSalario, tvNivel;
    int cedula;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar);

        edCedula = findViewById(R.id.edCedula);
        btBuscar = findViewById(R.id.btBuscar);
        btRegresar = findViewById(R.id.btRegresar);
        tvCedula = findViewById(R.id.tvCedula);
        tvNombre = findViewById(R.id.tvNombre);
        tvEstrato = findViewById(R.id.tvEstrato);
        tvSalario = findViewById(R.id.tvSalario);
        tvNivel = findViewById(R.id.tvNivelEducativo);

        controlador = new Controlador(getApplicationContext());


        btBuscar.setOnClickListener(this);
        btRegresar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btBuscar:
                try {
                    cedula = Integer.parseInt(edCedula.getText().toString());
                } catch (NumberFormatException numEx) {
                    Toast.makeText(getApplicationContext(), "Ingrese una cedula valida", Toast.LENGTH_SHORT).show();
                }
                Persona persona = controlador.buscarPersona(cedula);
                if (persona != null) {
                    tvCedula.setText(String.valueOf(cedula));
                    tvNombre.setText(persona.getNombre());
                    tvEstrato.setText(String.valueOf(persona.getEstrato()));
                    tvSalario.setText(String.valueOf(persona.getSalario()));
                    switch (persona.getNivel_educativo()) {
                        case 0:
                            tvNivel.setText(getString(R.string.educativo_bachillerato));
                            break;
                        case 1:
                            tvNivel.setText(getString(R.string.educativo_pregado));
                            break;
                        case 2:
                            tvNivel.setText(getString(R.string.educativo_maestro));
                            break;
                        case 3:
                            tvNivel.setText(getString(R.string.educativo_doctorado));
                            break;
                    }
                } else {
                    tvCedula.setText(getString(R.string.invalido));
                    tvNombre.setText(getString(R.string.invalido));
                    tvEstrato.setText(getString(R.string.invalido));
                    tvSalario.setText(getString(R.string.invalido));
                    tvNivel.setText(getString(R.string.invalido));
                    Toast.makeText(getApplicationContext(), "No existe", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btRegresar:
                finish();
                break;
        }
    }
}
