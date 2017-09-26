package com.example.alvaro.casopractico3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AnadirActivity extends AppCompatActivity implements View.OnClickListener{
    private static AlertDialog ventana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir);

        Button btn = (Button)findViewById(R.id.btnAceptar);
        btn.setOnClickListener(this);
        Button btn2 = (Button)findViewById(R.id.btnCancelar);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnAceptar:
                Contacto contacto = crearContacto();

                Intent intent = new Intent();
                intent.putExtra("contacto", contacto);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.btnCancelar:
                if (ventana==null) {
                    ventana=crearVentana();
                }
                ventana.show();
                break;
        }
    }

    public AlertDialog crearVentana() {
        AlertDialog.Builder cuadro = new AlertDialog.Builder(this);
        cuadro.setMessage("¿Seguro que desea salir sin guardar?");
        cuadro.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent2 = new Intent();
                setResult(RESULT_CANCELED, intent2);
                finish();
            }
        });
        cuadro.setNegativeButton(android.R.string.no, null);

        return cuadro.create();
    }

    public Contacto crearContacto() {
        EditText nombre = (EditText) findViewById(R.id.txtNombre);
        EditText email = (EditText) findViewById(R.id.txtEmail);
        EditText edad = (EditText) findViewById(R.id.txtEdad);

        return new Contacto(nombre.getText().toString(), email.getText().toString(), Integer.parseInt(edad.getText().toString()));
    }
}
