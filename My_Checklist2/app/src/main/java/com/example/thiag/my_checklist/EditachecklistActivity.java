package com.example.thiag.my_checklist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thiag.my_checklist.Dadosquestionario.Questionario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditachecklistActivity extends AppCompatActivity {
    private ListView listaquestionario;
    private EditText etNomequestionario;
    private EditText etpergunta1;
    private EditText etpergunta2;

    //ArrayList + ArrayAdapter
    private ArrayAdapter<Questionario> adapter;
    private ArrayList<Questionario> questoes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editachecklist);
        //list
        listaquestionario =
                (ListView) findViewById(R.id.lista_questionario);

        //ArrayList + ArrayAdapter
        questoes = new ArrayList<>();
        adapter = new ArrayAdapter<Questionario>(
                EditachecklistActivity.this,
                android.R.layout.simple_list_item_1,
                questoes);

        listaquestionario.setAdapter(adapter);
        //Firebase
        FirebaseApp.initializeApp(EditachecklistActivity.this);
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

                AlertDialog.Builder msg = new AlertDialog.Builder(EditachecklistActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View EditarActivity = inflater.inflate(R.layout.activity_editar, null);
                msg.setView(EditarActivity);

                final EditText etNomequestionario = (EditText) EditarActivity.findViewById(R.id.et_Nomequestionario);
                final EditText etPergunta1 = (EditText) EditarActivity.findViewById(R.id.et_pergunta_1);
                final EditText etPergunta2 = (EditText) EditarActivity.findViewById(R.id.et_pergunta_2);;

                etNomequestionario.setText(c.getNomequestionario());
                etPergunta1.setText(String.valueOf(c.getPergunta1()));
                etPergunta2.setText(String.valueOf(c.getPergunta1()));

                msg.setTitle(getResources().getString(R.string.alert_titulo));
                msg.setMessage(getResources().getString(R.string.alert_alterar_msg));
                msg.setPositiveButton(getResources().getString(R.string.alert_botao_sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {


                        //Recebendo dados alterados
                        c.setNomequestionario(etNomequestionario.getText().toString());
                        c.setPergunta1(etPergunta1.getText().toString());
                        c.setPergunta2(etPergunta2.getText().toString());
                        //Alterando através da chave(key) no firebase setando o novo valor
                        banco.child(c.getKey()).setValue(c);

                        Toast.makeText(
                                getBaseContext(),
                                getResources().getString(R.string.toast_sucesso_alterar),
                                Toast.LENGTH_LONG).show();
                    }
                });
                msg.setNegativeButton(getResources().getString(R.string.alert_botao_nao), null);
                msg.show();
            }
        });

        listaquestionario.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {

                final int posSelec = i;

                AlertDialog.Builder msg = new AlertDialog.Builder(EditachecklistActivity.this);
                msg.setTitle(getResources().getString(R.string.alert_titulo));
                msg.setMessage(getResources().getString(R.string.alert_msg));
                msg.setPositiveButton(getResources().getString(R.string.alert_botao_sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Questionario c = questoes.get(posSelec);

                        //Removendo através da chave(key) no firebase
                        banco.child(c.getKey()).removeValue();

                        questoes.remove(posSelec);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(
                                getBaseContext(),
                                getResources().getString(R.string.toast_sucesso_remocao),
                                Toast.LENGTH_LONG).show();
                    }
                });
                msg.setNegativeButton(getResources().getString(R.string.alert_botao_nao), null);
                msg.show();
                return true;
            }
        });
    }

    private void limpar(){
        etNomequestionario.setText(null);
        etpergunta1.setText(null);
        etpergunta2.setText(null);
    
    

    
   }
 }