package com.example.alvaro.casopractico3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final int ALTA = 100;
    public static final int BAJA = 200;
    public static final int LISTAR = 300;
    public static final int EDITAR = 400;
    private Set<Contacto> listaContactos = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btnAnadir);
        btn.setOnClickListener(this);
        Button btn2 = (Button)findViewById(R.id.btnBorrar);
        btn2.setOnClickListener(this);
        Button btn3 = (Button)findViewById(R.id.btnVer);
        btn3.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        int codigo = 0;
        switch(v.getId()) {
            case R.id.btnAnadir:
                intent = new Intent (this, AnadirActivity.class);
                codigo = ALTA;
                break;
            case R.id.btnBorrar:
                intent = new Intent (this, BorrarActivity.class);
                codigo = BAJA;
                break;
            case R.id.btnVer:
                intent = new Intent (this, VerActivity.class);
                intent.putParcelableArrayListExtra("listado", new ArrayList(listaContactos));
                codigo = LISTAR;
                break;
        }
        startActivityForResult(intent, codigo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case ALTA:
                if (resultCode == RESULT_OK) {
                    if (data.hasExtra("contacto")) {
                        Contacto c = data.getParcelableExtra("contacto");
                        if (listaContactos.add(c)) {
                            Toast.makeText(this, c.getNombre() + " Ha sido guardado", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "El contacto ya existía.", Toast.LENGTH_LONG).show();
                        }
                    }

                } else if(resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "No se ha guardado ningún contacto", Toast.LENGTH_LONG).show();
                }
                break;
            case BAJA:
                if (resultCode == RESULT_OK) {
                    if (data.hasExtra("contacto")) {
                        Contacto c =  data.getParcelableExtra("contacto");
                        Toast.makeText(this,listaContactos.remove(c)?"Se ha borrado un contacto":"No existe un contacto con esos datos", Toast.LENGTH_LONG).show();
                    }
                } else if(resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "No se ha borrado ningún contacto", Toast.LENGTH_LONG).show();
                }
                break;
            case LISTAR:
                if (resultCode == RESULT_OK) {
                    if (data.hasExtra("lista")) {
                        listaContactos = new HashSet(data.getParcelableArrayListExtra("lista"));
                    }
                }
                break;
        }
    }
}