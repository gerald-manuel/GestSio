package fr.rb.gestsio;

/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

/**
 * Activity for entering a etudiant.
 */

public class NewEtudiantActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_NEW = "fr.rb.gestsio.REPLY_NEW";

    private EditText mEditNomView;
    private EditText mEditPrenomView;
    private EditText mEditNaissanceView;
    private EditText mEditSpeView;
    private EditText mEditAdresseView;
    private EditText mEditCpView;
    private EditText mEditVilleView;
    private EditText mEditTelView;
    private EditText mEditCourrielView;
    private EditText mEditObservationsView;
    DatePickerDialog datePickerDialog;


    private Etudiant etudiant;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_etudiant);
        mEditNomView = findViewById(R.id.editNom);
        mEditPrenomView = findViewById(R.id.editPrenom);
        mEditNaissanceView=findViewById(R.id.editNaissanceEtudiant);
        mEditSpeView=findViewById(R.id.editSpeEtudiant);
        mEditAdresseView=findViewById(R.id.editAdresseEtudiant);
        mEditCpView=findViewById(R.id.editCpEtudiant);
        mEditVilleView=findViewById(R.id.editVilleEtudiant);
        mEditTelView=findViewById(R.id.editTelEtudiant);
        mEditCourrielView=findViewById(R.id.editCourrielEtudiant);
        mEditObservationsView=findViewById(R.id.editObservationsEtudiant);
        


        final Button button = findViewById(R.id.button_save);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent replyIntent = new Intent();

                if ((TextUtils.isEmpty(mEditNomView.getText())) || (TextUtils.isEmpty(mEditPrenomView.getText())) ||
                        (TextUtils.isEmpty(mEditNaissanceView.getText()))) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String nomEtudiant = mEditNomView.getText().toString();
                    String prenomEtudiant = mEditPrenomView.getText().toString();
                    String naissanceEtudiant = mEditNaissanceView.getText().toString();
                    String speEtudiant = mEditSpeView.getText().toString();
                    String adresseEtudiant = mEditAdresseView.getText().toString();
                    String cpEtudiant = mEditCpView.getText().toString();
                    String villeEtudiant = mEditVilleView.getText().toString();
                    String telEtudiant = mEditTelView.getText().toString();
                    String courrierEtudiant = mEditCourrielView.getText().toString();
                    String observationsEtudiant = mEditObservationsView.getText().toString();

                    Etudiant etudiant = new Etudiant();
                    etudiant.setNomEtudiant(nomEtudiant);
                    etudiant.setPrenomEtudiant(prenomEtudiant);
                    etudiant.setNaissanceEtudiant(naissanceEtudiant);
                    etudiant.setSpeEtudiant((speEtudiant));
                    etudiant.setAdresseEtudiant(adresseEtudiant);
                    etudiant.setCpEtudiant(cpEtudiant);
                    etudiant.setVilleEtudiant(villeEtudiant);
                    etudiant.setTelEtudiant(telEtudiant);
                    etudiant.setCourrielEtudiant(courrierEtudiant);
                    etudiant.setObservationsEtudiant(observationsEtudiant);

                    replyIntent.putExtra(EXTRA_REPLY_NEW, etudiant);
                    setResult(RESULT_OK, replyIntent);

                }
                finish();
            }
        });
        mEditNaissanceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(NewEtudiantActivity.this,/*android.R.style.Theme_Holo_Light_Dialog_MinWidth,*/
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                mEditNaissanceView.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                // datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.setTitle("Date Naissance");
                datePickerDialog.show();

            }

        });

    }
}

