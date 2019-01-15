package com.example.kevin.quiz;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Einstellungen extends AppCompatActivity {

    final int REQUEST_CODE = 4711;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);

        setTitle("Einstellungen");
    }

    public void profil_bearbeiten(View view) {
        Intent i = new Intent(this, ProfilBearbeiten.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    public void zurueck(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

}
