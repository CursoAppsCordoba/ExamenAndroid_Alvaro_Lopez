package com.example.alvaro.casopractico3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditarActivity extends AppCompatActivity implements View.OnClickListener {
    EditText nombre, email, edad;
    Contacto contacto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        Button btn = (Button)findViewById(R.id.btnGuardar);
        btn.setOnClickListener(this);
        Button btn2 = (Button)findViewById(R.id.btnCancelar);
        btn2.setOnClickListener(this);

        nombre = (EditText)findViewById(R.id.txtNombre);
        email = (EditText)findViewById(R.id.txtEmail);
        edad = (EditText)findViewById(R.id.txtEdad);

        Intent intent = getIntent();
        contacto = intent.getParcelableExtra("contacto");
        nombre.setText(contacto.getNombre());
        email.setText(contacto.getEmail());
        edad.setText(String.valueOf(contacto.getEdad()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGuardar:
                Intent intent2 = new Intent(this, VerActivity.class);
                intent2.putExtra("contacto", new Contacto(nombre.getText().toString(), email.getText().toString(),  Integer.parseInt(edad.getText().toString())));
                setResult(RESULT_OK, intent2);
                finish();
                break;
            case R.id.btnCancelar:
                finish();
                break;
        }
    }
}
