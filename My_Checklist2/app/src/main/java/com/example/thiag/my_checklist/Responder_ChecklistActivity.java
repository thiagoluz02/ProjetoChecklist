package com.example.thiag.my_checklist;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.thiag.my_checklist.Dadosquestionario.Questionario;


public class Responder_ChecklistActivity extends AppCompatActivity {

    Questionario q = null;

    private TextView tvpergunta1;
    private TextView tvpergunta2;
    private CheckBox ch_pergunta1;
    private CheckBox ch_pergunta1nao;
    private CheckBox ch_pergunta2;
    private CheckBox ch_pergunta2nao;
    private Button btn_Responder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responder__checklist);
        Questionario q = (Questionario) getIntent().getSerializableExtra("quest");

        // Log.d("TAG", "questionario: "+q.toString());

        tvpergunta1 = (TextView) findViewById(R.id.tv_pergunta1);

        tvpergunta1.setText(q.getPergunta1());

        tvpergunta2 = (TextView) findViewById(R.id.tv_pergunta2);

        tvpergunta2.setText(q.getPergunta2());

        /*tvpergunta1.setOnItemClickListener(new TextView.OnEditorActionListener().OnItemClickListener()


/*lisquestionario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //objeto que tem a key
                final int posSelec = i;

                // Buscando objeto selecionado
                final Questionario c = questoes.get(i);*/

        btn_Responder = (Button) findViewById(R.id.btn_Responder);
        btn_Responder.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                ch_pergunta1= (CheckBox)findViewById(R.id.ch_pergunta1);
                ch_pergunta2=(CheckBox)findViewById(R.id.ch_pergunta1);

                if (ch_pergunta1.isChecked()) {
                    tvpergunta1.setText("sim");

                } else {


                    tvpergunta1.setText("não");
                }

                if (ch_pergunta2.isChecked()) {
                    tvpergunta2.setText("sim");
                } else {
                    tvpergunta2.setText("não");


                }
                //Toast.makeText(Responder_ChecklistActivity.this, "não esta conforme de seu fidepace", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                //Intent intent = new Intent(Intent.ACTION_SEND);
                // intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"", ""});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Checklist");
                intent.putExtra(Intent.EXTRA_TEXT, "respotas : "+tvpergunta1+ch_pergunta1+tvpergunta2+ch_pergunta2);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });

    }//fecha oncreate




}//fecha classe
