package com.example.alvaro.casopractico3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VerActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Contacto> listaContactos2 = new ArrayList<>();
    private ArrayAdapter adapter;
    private ListView lista;
    private Contacto contactoAEditar, contactoEditado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver);

        Button btn = (Button)findViewById(R.id.btnVolver);
        btn.setOnClickListener(this);

        lista = (ListView)findViewById(R.id.lista);

        Intent intent = getIntent();
        if (intent.hasExtra("listado")) {
            //recogemos el arraylist pasado en el intent
            listaContactos2 = intent.getParcelableArrayListExtra("listado");
            //creamos el adaptador con el arraylist y lo asignamos al listView
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaContactos2);
            lista.setAdapter(adapter);

            //creamos el evento click de los elementos de la lista
            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //guardamos el contacto en el que se ha hecho click
                    contactoAEditar = listaContactos2.get(position);
                    //mostramos un alertdialog para editar el contacto con dos botones
                    AlertDialog.Builder cuadro  = new AlertDialog.Builder(VerActivity.this);
                    cuadro.setMessage("¿Desea editar el contacto?");
                    //uno para aceptar que llamará a la actividad editarActivity pasandole el contacto a editar
                    cuadro.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                            intent.putExtra("contacto", contactoAEditar);
                            startActivityForResult(intent, MainActivity.EDITAR);
                        }
                    });
                    //y otro para cancelar que no hace nada
                    cuadro.setNegativeButton(android.R.string.no, null);

                    cuadro.show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        //al pulsar en el botón volver devolvemos un intent con el arraylist de contactos y RESULT_OK
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("lista", (ArrayList)listaContactos2);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //al regresar a la actividad, si el requestCode es editar
        if (requestCode == MainActivity.EDITAR) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("contacto")) {
                    //si en el intent viene un contacto lo guardamos
                    contactoEditado = data.getParcelableExtra("contacto");

                    //buscamos el contacto editado en el arraylist y sino existe actualizamos los datos del contacto a editar
                    if (listaContactos2.indexOf(contactoEditado) == -1) {
                        listaContactos2.get(listaContactos2.indexOf(contactoAEditar)).setNombre(contactoEditado.getNombre());
                        listaContactos2.get(listaContactos2.indexOf(contactoAEditar)).setEmail(contactoEditado.getEmail());
                        listaContactos2.get(listaContactos2.indexOf(contactoAEditar)).setEdad(contactoEditado.getEdad());

                        //actualiza el adaptador y el listView
                        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaContactos2);
                        lista.setAdapter(adapter);
                    }
                    //si existe muestra un toast de que ya existe
                    else {
                        Toast.makeText(this, "El contacto ya existe", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}