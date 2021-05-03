package fr.rb.gestsio;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ViewEtudiantActivity extends AppCompatActivity  {
    private TextView textreponse;
    private TextView mTextViewNom;
    private TextView mTextViewPrenom;
    private TextView mTextViewNaissance;
    private TextView mTextViewSpe;
    private TextView mTextViewAdresse;
    private TextView mTextViewCp;
    private TextView mTextViewVille;
    private TextView mTextViewTel;
    private TextView mTextViewCourriel;
    private TextView mTextViewObservations;
    private TextView mTextViewAppretiation;

    public static final String EXTRA_REPLY_VIEW = "fr.rb.gestsio.REPLY_VIEW";


    //@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // affectation du fichier de layout
        setContentView(R.layout.activity_view_etudiant);

         mTextViewNom=findViewById(R.id.textViewNom);
         mTextViewPrenom=findViewById(R.id.textViewPrenom);
         mTextViewNaissance=findViewById(R.id.textViewNaissance);
        mTextViewSpe=findViewById(R.id.textViewSpe);
        mTextViewAdresse=findViewById(R.id.textViewAdresse);
         mTextViewCp=findViewById(R.id.textViewCp);
         mTextViewVille=findViewById(R.id.textViewVille);
         mTextViewTel=findViewById(R.id.textViewTel);
         mTextViewCourriel=findViewById(R.id.textViewCourriel);
         mTextViewObservations=findViewById(R.id.textViewObservations);



        final Etudiant etudiant = (Etudiant) getIntent().getSerializableExtra(EtudiantListAdapter.EtudiantViewHolder.EXTRA_REPLY_ETUDIANT_LA);


        loadEtudiant(etudiant);
        textreponse=findViewById(R.id.textViewAppretiation);
//retrofilt builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://165.169.241.28:31195/MyApi/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//instance pour interface
        InterAppretiation interAppretiation = retrofit.create(InterAppretiation.class);
        Call<List<Apretiation>> call = interAppretiation.getAppretiation(etudiant.getIdEtudiant());

        call.enqueue(new Callback<List<Apretiation>>() {
            @Override
            public void onResponse(Call<List<Apretiation>> call, Response<List<Apretiation>> response) {
                //verifier la reponse
                if(response.code() != 200){
                    textreponse.setText("verifie la connetion" +response.code());
                    return;
                }
                if (!response.isSuccessful()){
                    textreponse.setText("verifie la connetion" +response.code());
                }
                List<Apretiation> apretiations = response.body();
                for (Apretiation apretiation: apretiations){
                    String responseTest = "";
                    responseTest += apretiation.getObservationEtudiant() + "\n\n";
                    textreponse.append(responseTest);
                }
              //  String json = "ID=" + response.body().getIdEtudiant() +
                //        "\n Appretiation= " + response.body().getObservationEtudiant();


            }

            @Override
            public void onFailure(Call<List<Apretiation>> call, Throwable t) {
                textreponse.setText(t.getMessage());
            }
        });


        //gestion de la modification

        final Button bm = findViewById(R.id.button_modifier);
        bm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewEtudiantActivity.this, UpdateEtudiantActivity.class);
                intent.putExtra(EXTRA_REPLY_VIEW,etudiant);
                intent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
                startActivity(intent);
                finish();
            }
        });


        //gestion de la suppression d'un étudiant

        findViewById(R.id.button_supprimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ViewEtudiantActivity.this);
                builder.setTitle(R.string.confirmation);

                builder.setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //deleteEtudiant(etudiant);

                        Intent replyIntent = new Intent();
                        replyIntent.putExtra(EXTRA_REPLY_VIEW, etudiant);
                        setResult(MainActivity.RESULT_SUPP, replyIntent);
                        finish();

                    }
                });

                builder.setNegativeButton(R.string.non, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });


    }

    private void loadEtudiant(Etudiant etudiant) {
        mTextViewNom.setText(etudiant.getNomEtudiant());
        mTextViewPrenom.setText(etudiant.getPrenomEtudiant());
        mTextViewNaissance.setText(etudiant.getNaissanceEtudiant());
        mTextViewSpe.setText(etudiant.getSpeEtudiant());
        mTextViewAdresse.setText(etudiant.getAdresseEtudiant());
        mTextViewCp.setText(etudiant.getCpEtudiant());
        mTextViewVille.setText(etudiant.getVilleEtudiant());
        mTextViewTel.setText(etudiant.getTelEtudiant());
        mTextViewCourriel.setText(etudiant.getCourrielEtudiant());
        mTextViewObservations.setText(etudiant.getObservationsEtudiant());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}