package com.example.kevin.quiz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ProfilBearbeiten extends AppCompatActivity {

    private EditText eingabe;
    private TextView ausgabe;
    final int REQUEST_CODE = 4711;
    boolean permission_write_external_granted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_bearbeiten);
        setTitle("Profil bearbeiten");

        eingabe = findViewById(R.id.editSpitzname);
        ausgabe = findViewById(R.id.ausgabe);

        checkPermission();
        lesen();
    }

    //Berechtigung für Zugriff auf Dateien prüfen
    private void checkPermission(){

        int check = ContextCompat.checkSelfPermission
                (this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (check != PackageManager.PERMISSION_GRANTED) {
            // Rechte anfordern
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CODE);
            return;
        } else {
            // beabsichtigte Operation durchführen
            permission_write_external_granted = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        try {
            if (requestCode == REQUEST_CODE) {
                // Permission prüfen
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permission_write_external_granted = true;
                    // Recht liegt vor, nun kann die gewünschte Operation ausgeführt werden.
                }
            }
        } catch (SecurityException ex) {
            Log.d("meinApp", ex.getMessage());

        }
    }

    //Spitznamen in Datei speichern
    public void speichern(View view) {

        String eingabeString = eingabe.getText().toString();

        //Zugriff auf Verzeichnis
        File documents = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);

        File ausgabedatei = new File(documents, "text.txt");

        try {
            //Stream erzeugen
            FileOutputStream fo = new FileOutputStream(ausgabedatei);
            // FileOutputStream fos = openFileOutput("text.txt", Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fo);
            //In Stream schreiben
            pw.println(eingabeString);
            //Stream schließen
            pw.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
        lesen();
    }


    //Spitznamen aus Datei lesen
    public void lesen() {
        String zeile;

        try {
            //Zugriff auf Verzeichnis
            File datei = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS);

            File datei2 = new File(datei, "text.txt");
            // File-Objekt erzeugen
            // File datei = new File
            //        (getFilesDir(), "text.txt");
            // Stream erzeugen
            BufferedReader br = new BufferedReader(
                    new FileReader(datei2));

            // Aus Stream lesen
            do {
                zeile = br.readLine();
                ausgabe.setText(zeile);
            }
            while (zeile == null);

            // Stream schließen
            br.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
    }


    public void zurueck(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }
}