package com.example.alvaro.casopractico3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AnadirActivity extends AppCompatActivity implements View.OnClickListener{
    private static AlertDialog ventana;
    EditText nombre, email, edad;

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
        //switch que controla que botón ha sido pulsado
        switch(v.getId()) {
            case R.id.btnAceptar:
                //Guardamos un contacto llamando a la función crearContacto
                Contacto contacto = crearContacto();

                //Si el contacto es diferente a null creamos un intent, lo añadimos y lo mandamos de vuelta junto a RESULT_OK
                if (contacto != null) {
                    Intent intent = new Intent();
                    intent.putExtra("contacto", contacto);
                    setResult(RESULT_OK, intent);
                    finish();
                //si el contacto es null muestra un toast
                } else {
                    Toast.makeText(this, "Todos los campos deben estar rellenos", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnCancelar:
                //si ventana no ha sido inicialiada llama a la función crearVentana
                if (ventana==null) {
                    ventana=crearVentana();
                }
                ventana.show();
                break;
        }
    }

    public AlertDialog crearVentana() {
        //Creamos un alertDialog con dos botones,
        AlertDialog.Builder cuadro = new AlertDialog.Builder(this);
        cuadro.setMessage("¿Seguro que desea salir sin guardar?");
        //uno de aceptar que cerrará la actividad mandando RESULT_CANCELED
        cuadro.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent2 = new Intent();
                setResult(RESULT_CANCELED, intent2);
                finish();
            }
        });
        //y otro de cancelar que no hará nada
        cuadro.setNegativeButton(android.R.string.no, null);

        return cuadro.create();
    }

    public Contacto crearContacto() {
        Contacto contacto;

        nombre = (EditText) findViewById(R.id.txtNombre);
        email = (EditText) findViewById(R.id.txtEmail);
        edad = (EditText) findViewById(R.id.txtEdad);

        //Si alguno de los campos de texto está vacío establece contacto a null
        if (TextUtils.isEmpty(nombre.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(edad.getText().toString())) {
            contacto = null;
        //sino, crea un contacto con lso datos obtenidos de los EditText
        } else {
            contacto = new Contacto(nombre.getText().toString(), email.getText().toString(), Integer.parseInt(edad.getText().toString()));
        }
        return contacto;
    }
}
