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


public class BorrarActivity extends AppCompatActivity implements View.OnClickListener{
    private Contacto contacto;
    EditText nombre, email, edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar);

        Button btn = (Button)findViewById(R.id.btnBorrar);
        btn.setOnClickListener(this);
        Button btn2 = (Button)findViewById(R.id.btnCancelar);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnBorrar:
                contacto = crearContacto();

                if (contacto != null) {
                    AlertDialog.Builder cuadro = new AlertDialog.Builder(this);
                    cuadro.setMessage("¿Seguro que desea borrar?");
                    cuadro.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.putExtra("contacto", contacto);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                    cuadro.setNegativeButton(android.R.string.no, null);

                    cuadro.show();
                }
                else {
                    Toast.makeText(this, "Todos los campos deben estar rellenos", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnCancelar:
                finish();
                break;
        }
    }

    public Contacto crearContacto() {
        Contacto contacto;

        nombre = (EditText) findViewById(R.id.txtNombre);
        email = (EditText) findViewById(R.id.txtEmail);
        edad = (EditText) findViewById(R.id.txtEdad);

        if (TextUtils.isEmpty(nombre.getText().toString()) || TextUtils.isEmpty(email.getText().toString()) || TextUtils.isEmpty(edad.getText().toString())) {
            contacto = null;
        } else {
            contacto = new Contacto(nombre.getText().toString(), email.getText().toString(), Integer.parseInt(edad.getText().toString()));
        }
        return contacto;
    }
}
