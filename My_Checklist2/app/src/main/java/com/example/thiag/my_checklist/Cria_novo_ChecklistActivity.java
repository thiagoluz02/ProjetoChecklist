package com.example.thiag.my_checklist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class Cria_novo_ChecklistActivity extends AppCompatActivity {

      //Widgets
    private Button btnCadastrar;
    private EditText Nome_questionario;
    private Button btn_cadaquestionario;
    private android.app.AlertDialog.Builder lvQuestionario;
    private Button bt_Criarchecklist;
    private ListView lisquestionario;

    //ArrayList + ArrayAdapter
    private ArrayList<Questionario> questoes; //Questionario
    private ArrayAdapter<Questionario> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cria_novo__checklist);
        //Referencias
        bt_Criarchecklist =(Button)findViewById(R.id.bt_Criarchecklist);
        btn_cadaquestionario = (Button)findViewById(R.id.btn_cadaquestionario);

        Nome_questionario = (EditText) findViewById(R.id.Nome_questionario);
        lisquestionario = (ListView) findViewById(R.id.lisquestionario);

        //ArrayList + ArrayAdapter
        questoes = new ArrayList<>();
        adapter = new ArrayAdapter<Questionario>(
                Cria_novo_ChecklistActivity.this,
                android.R.layout.simple_list_item_1,
                questoes);


        lisquestionario.setAdapter(adapter);

        //Firebase

        FirebaseApp.initializeApp(Cria_novo_ChecklistActivity.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("dadoschecklist");

        btn_cadaquestionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Nome_questionario.getText().equals(null)) {

                    Questionario c = new Questionario();
                    c.setNomequestionario(Nome_questionario.getText().toString());

                    //Enviando para o Firebase
                    banco.push().setValue(c);

                    limpar();

                    Toast.makeText(
                            getBaseContext(),
                            getResources().getString(R.string.toast_sucesso_adicionar),
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(
                            getBaseContext(),
                            getResources().getString(R.string.toast_erro),
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        //fecha onclick button
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

        lisquestionario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //objeto que tem a key/
                final int posSelec = i;

                // Buscando objeto selecionado
                final Questionario c = questoes.get(i);


                AlertDialog.Builder msg = new AlertDialog.Builder(Cria_novo_ChecklistActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View PerguntasQuestionarioActivity = inflater.inflate(R.layout.activity_perguntas_questionario,null);
                msg.setView(PerguntasQuestionarioActivity);

                final EditText etpergunta = (EditText) PerguntasQuestionarioActivity.findViewById(R.id.et_iten_pergunta);
                final EditText etPergunta1 = (EditText) PerguntasQuestionarioActivity.findViewById(R.id.et_pergunta_1);
                final EditText etPergunta2 = (EditText) PerguntasQuestionarioActivity.findViewById(R.id.et_pergunta_2);

                //final EditText etRG = (EditText) perguntasQuestionario.findViewById(R.id.et_iten_pergunta);
                //final EditText etRenda = (EditText) perguntasQuestionario.findViewById(R.id.et_pergunta);

                etpergunta.setText(c.getNomequestionario());
                //etRG.setText(String.valueOf(c.getRG()));
                //etRenda.setText(String.valueOf(c.getRenda()));
                msg.setTitle(getResources().getString(R.string.alert_titulo));
                msg.setMessage(getResources().getString(R.string.alert_alterar_msg));
                msg.setPositiveButton(getResources().getString(R.string.alert_botao_sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Recebendo dados alterados

                        c.setNomequestionario(etpergunta.getText().toString());
                        c.setPergunta1(etPergunta1.getText().toString());
                        c.setPergunta2(etPergunta2.getText().toString());

                        //c.setRG(Integer.parseInt(etRG.getText().toString()));
                        //c.setRenda(Double.parseDouble(etRenda.getText().toString()));

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

        });}
    //fecha oncreate


    private void limpar() {
        Nome_questionario.setText(null);
    }



}



























     /*   etNome = (EditText) findViewById(R.id.et_nome);
        etRG = (EditText) findViewById(R.id.et_rg);
        etRenda = (EditText) findViewById(R.id.et_renda);
        btnCadastrar = (Button) findViewById(R.id.btn_cadastrar);
        lvClientes = (ListView) findViewById(R.id.lv_clientes);

        //ArrayList + ArrayAdapter
        clientes = new ArrayList<>();
        adapter = new ArrayAdapter<Questionario>(
                MainActivity.this,
                android.R.layout.simple_list_item_1,
                clientes);

        lvClientes.setAdapter(adapter);

        //Firebase
        FirebaseApp.initializeApp(MainActivity.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("Questionario");

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!etNome.getText().toString().isEmpty() &&
                        !etRG.getText().toString().isEmpty() &&
                        !etRenda.getText().toString().isEmpty()) {

                  /*  Cliente c = new Cliente();
                    c.setNome(etNome.getText().toString());
                    c.setRG(Integer.parseInt(etRG.getText().toString()));
                    c.setRenda(Double.parseDouble(etRenda.getText().toString()));

                    //Enviando para o Firebase
                    banco.push().setValue(c);

                    limpar();

                    Toast.makeText(
                            getBaseContext(),
                            getResources().getString(R.string.toast_sucesso_adicionar),
                            Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(
                            getBaseContext(),
                            getResources().getString(R.string.toast_erro),
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                clientes.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()){
                    Cliente c = data.getValue(Cliente.class);
                    c.setKey(data.getKey()); //Colocando key manualmente no objeto
                    clientes.add(c);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        lvClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //objeto que tem a key
                final int posSelec = i;

                //Buscando objeto selecionado
                final Cliente c = clientes.get(i);

                AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View editarCliente = inflater.inflate(R.layout.editar_cliente, null);
                msg.setView(editarCliente);

                final EditText etLogin = (EditText) editarCliente.findViewById(R.id.editar_et_nome);
                final EditText etRG = (EditText) editarCliente.findViewById(R.id.editar_et_rg);
                final EditText etRenda = (EditText) editarCliente.findViewById(R.id.editar_et_renda);

                etLogin.setText(c.getNome());
                etRG.setText(String.valueOf(c.getRG()));
                etRenda.setText(String.valueOf(c.getRenda()));

                msg.setTitle(getResources().getString(R.string.alert_titulo));
                msg.setMessage(getResources().getString(R.string.alert_alterar_msg));
                msg.setPositiveButton(getResources().getString(R.string.alert_botao_sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //Recebendo dados alterados
                        c.setNome(etLogin.getText().toString());
                        c.setRG(Integer.parseInt(etRG.getText().toString()));
                        c.setRenda(Double.parseDouble(etRenda.getText().toString()));

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

        lvClientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int i, long l) {

                final int posSelec = i;

                AlertDialog.Builder msg = new AlertDialog.Builder(MainActivity.this);
                msg.setTitle(getResources().getString(R.string.alert_titulo));
                msg.setMessage(getResources().getString(R.string.alert_msg));
                msg.setPositiveButton(getResources().getString(R.string.alert_botao_sim), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Cliente c = clientes.get(posSelec);

                        //Removendo através da chave(key) no firebase
                        banco.child(c.getKey()).removeValue();

                        clientes.remove(posSelec);
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
        etNome.setText(null);
        etRG.setText(null);
        etRenda.setText(null);
    }


}
//public class Cria_novo_ChecklistActivity extends AppCompatActivity {

// @Override
//protected void onCreate(Bundle savedInstanceState) {
// super.onCreate(savedInstanceState);
//setContentView(R.layout.activity_cria_novo__checklist);
// */
