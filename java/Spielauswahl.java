package com.example.kevin.quiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Spielauswahl extends AppCompatActivity {
    final int REQUEST_CODE= 4712;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielauswahl);
        setTitle("Spielauswahl");
    }

    public void spiel_starten_normal(View view) {
        Intent i = new Intent(this, SpielstartNormal.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    public void spiel_starten_schnell(View view) {
        Intent i = new Intent(this, SpielstartSchnell.class);
        startActivityForResult(i, REQUEST_CODE);
    }
}
