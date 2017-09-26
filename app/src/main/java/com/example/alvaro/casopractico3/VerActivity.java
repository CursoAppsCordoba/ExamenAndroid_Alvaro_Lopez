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

import java.util.ArrayList;
import java.util.List;

public class VerActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Contacto> listaContactos2 = new ArrayList<>();
    private List<String> arrayList = new ArrayList<>();
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
        arrayList = new ArrayList<String>();
        Intent intent = getIntent();
        listaContactos2 = (ArrayList)intent.getParcelableArrayListExtra("listado");
        for (Contacto c : listaContactos2) {
            arrayList.add(c.getNombre());
        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                contactoAEditar = listaContactos2.get(position);

                AlertDialog.Builder cuadro  = new AlertDialog.Builder(VerActivity.this);
                cuadro.setMessage("¿Desea editar el contacto?");
                cuadro.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(VerActivity.this, EditarActivity.class);
                        intent.putExtra("contacto", contactoAEditar);
                        startActivityForResult(intent, MainActivity.EDITAR);

                    }
                });
                cuadro.setNegativeButton(android.R.string.no, null);

                cuadro.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("lista", (ArrayList)listaContactos2);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MainActivity.EDITAR) {
            if (resultCode == RESULT_OK) {
                if (data.hasExtra("contacto")) {
                    contactoEditado = data.getParcelableExtra("contacto");
                    listaContactos2.remove(contactoAEditar);
                    listaContactos2.add(contactoEditado);

                    arrayList.removeAll(arrayList);
                    for (Contacto c : listaContactos2) {
                        arrayList.add(c.getNombre());
                    }
                    adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
                    lista.setAdapter(adapter);
                }
            }
        }
    }
}