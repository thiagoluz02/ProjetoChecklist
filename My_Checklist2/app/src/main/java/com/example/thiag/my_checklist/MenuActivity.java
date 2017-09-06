package com.example.thiag.my_checklist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.thiag.my_checklist.Dadosquestionario.Questionario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ListView listaquestionario;

    //ArrayList + ArrayAdapter
    private ArrayAdapter<Questionario> adapter;
    private ArrayList<Questionario> questoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //list
        listaquestionario = (ListView) findViewById(R.id.lista_questionario);

        //ArrayList + ArrayAdapter
        questoes = new ArrayList<>();
        adapter = new ArrayAdapter<Questionario>(
                MenuActivity.this,
                android.R.layout.simple_list_item_1,
                questoes);

        listaquestionario.setAdapter(adapter);
        //Firebase
        FirebaseApp.initializeApp(MenuActivity.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("dadoschecklist");
        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                questoes.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Questionario c = data.getValue(Questionario.class);
                    c.setKey(data.getKey()); //Colocando key manualmente no objeto
                    questoes.add(c);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        listaquestionario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //objeto que tem a key
                final int posSelec = i;
                // Buscando objeto selecionado
                final Questionario c = questoes.get(i);
                Intent it = new Intent(MenuActivity.this,Responder_ChecklistActivity.class);
                it.putExtra("quest", c);
                startActivity(it);

            }
            //  }*/);
            //msg.setNegativeButton(getResources().getString(R.string.alert_botao_nao), null);
            //msg.show();

            //}

        });
    }//fecha oncreate
    public void abrircriarchecklist(View view) {
        Intent abri = new Intent(this,Cria_novo_ChecklistActivity.class);
        startActivity(abri);
    }

    public void Editar(View view) {
        Intent edt =new Intent(this,EditachecklistActivity.class);
        startActivity(edt);
    }


   /*@Override

        return super.onOptionsItemSelected(item);
    }*/


    // private void limpar() {
    //  Nome_questionario.setText(null);
    // }





}
