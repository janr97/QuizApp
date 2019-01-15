package com.example.kevin.quiz;

import android.Manifest;
import android.app.Activity;
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

//https://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java
public class Statistik extends AppCompatActivity {

    private TextView anzahlAntwortenGesamt, anzahlRichtigeAntworten, anzahlFalscheAntworten,
            richtigeFragenProzent, anzahlAntwortenGesamtSchnell, anzahlRichtigeAntwortenSchnell,
            anzahlFalscheAntwortenSchnell, richtigeFragenProzentSchnell;
    public int richtigeAnworten, gesamtAntworten, falscheAntworten,
            richtigeAntwortenSchnell, gesamtAntwortenSchnell, falscheAntwortenSchnell;
    public String falscheAntwortenString, prozentwertAntwortenString,
            falscheAntwortenStringSchnell, prozentwertAntwortenStringSchnell;
    public float prozentwertAntworten, prozentwertAntwortenSchnell;
    final int REQUEST_CODE = 4711;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistik);
        setTitle("Statistik");

        //TextView's für das normale Spiel
        anzahlAntwortenGesamt   = findViewById(R.id.anzahlAntwortenGesamt);
        anzahlRichtigeAntworten = findViewById(R.id.anzahlRichtigeAntworten);
        anzahlFalscheAntworten  = findViewById(R.id.anzahlFalscheAntworten);
        richtigeFragenProzent   = findViewById(R.id.richtigeFragenProzent);

        //TextViews für das Blitzspiel
        anzahlAntwortenGesamtSchnell   = findViewById(R.id.anzahlAntwortenGesamtSchnell);
        anzahlRichtigeAntwortenSchnell = findViewById(R.id.anzahlRichtigeAntwortenSchnell);
        anzahlFalscheAntwortenSchnell  = findViewById(R.id.anzahlFalscheAntwortenSchnell);
        richtigeFragenProzentSchnell   = findViewById(R.id.richtigeFragenProzentSchnell);

        lesenGesamtAntworten();
        lesenRichtigeAntworten();
        falscheAntworten = berechnenFalscheAntworten(gesamtAntworten, richtigeAnworten);
        falscheAntwortenString = Integer.toString(falscheAntworten);
        anzahlFalscheAntworten.setText(falscheAntwortenString);
        prozentwertAntworten = berechnenProzentwert(gesamtAntworten,richtigeAnworten);
        prozentwertAntwortenString = Float.toString(prozentwertAntworten);
        richtigeFragenProzent.setText(prozentwertAntwortenString + "%");

        lesenGesamtAntwortenSchnell();
        lesenRichtigeAntwortenSchnell();
        falscheAntwortenSchnell = berechnenFalscheAntwortenSchnell(
                gesamtAntwortenSchnell, richtigeAntwortenSchnell);
        falscheAntwortenStringSchnell = Integer.toString(falscheAntwortenSchnell);
        anzahlFalscheAntwortenSchnell.setText(falscheAntwortenStringSchnell);
        prozentwertAntwortenSchnell = berechnenProzentwertSchnell(
                gesamtAntwortenSchnell,richtigeAntwortenSchnell);
        prozentwertAntwortenStringSchnell = Float.toString(prozentwertAntwortenSchnell);
        richtigeFragenProzentSchnell.setText(prozentwertAntwortenStringSchnell + "%");
    }

    //Gesamtzahl der beantworteten Fragen aus Datei lesen
    public void lesenGesamtAntworten() {
        String zeile;

        try {
            //Zugriff auf Verzeichnis
            File datei = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS);

            File ausgabedatei = new File(datei, "statistikGesamtAntworten.txt");

            // Stream erzeugen
            BufferedReader br = new BufferedReader(
                    new FileReader(ausgabedatei));

            // Aus Stream lesen
            do {
                zeile = br.readLine();
                gesamtAntworten = Integer.parseInt(zeile); //Konvertierung zu Int-Wert, zur Berechnung von falschen Antworten
                anzahlAntwortenGesamt.setText(zeile);
            }
            while (zeile == null);

            // Stream schließen
            br.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
    }

    public void lesenRichtigeAntworten(){
        String zeile;

        try {
            //Zugriff auf Verzeichnis
            File datei = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS);

            File ausgabedatei = new File(datei, "statistikRichtigeAntworten.txt");
            // Stream erzeugen
            BufferedReader br = new BufferedReader(
                    new FileReader(ausgabedatei));

            // Aus Stream lesen
            do {
                zeile = br.readLine();
                richtigeAnworten = Integer.parseInt(zeile);
                anzahlRichtigeAntworten.setText(zeile);
            }
            while (zeile == null);

            // Stream schließen
            br.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
    }

    //Wert 0 in Datei Anzahl Beantworteter Fragen setzen bzw Statistik dazu löschen
    public void statistikLoeschenGesamtFragen(){
        //Zugriff auf Verzeichnis
        File documents = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);

        File ausgabedatei = new File(documents, "statistikGesamtAntworten.txt");

        try {
            //Stream erzeugen
            FileOutputStream fo = new FileOutputStream(ausgabedatei);
            // FileOutputStream fos = openFileOutput("text.txt", Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fo);
            //In Stream schreiben
            pw.println("0");
            //Stream schließen
            pw.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
    }

    //Wert 0 in Datei Anzahl Richtiger Fragen setzen bzw Statistik dazu löschen
    public void statistikLoeschenRichtigeFragen(){
        //Zugriff auf Verzeichnis
        File documents = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);

        File ausgabedatei = new File(documents, "statistikRichtigeAntworten.txt");

        try {
            //Stream erzeugen
            FileOutputStream fo = new FileOutputStream(ausgabedatei);
            // FileOutputStream fos = openFileOutput("text.txt", Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fo);
            //In Stream schreiben
            pw.println("0");
            //Stream schließen
            pw.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
    }

    //gesamte Statistik des normalen Spiels löschen bzw. Wert 0 eintragen
    public void statistikLoeschen(View view) {

        statistikLoeschenGesamtFragen();
        statistikLoeschenRichtigeFragen();
        lesenGesamtAntworten();
        lesenRichtigeAntworten();

        falscheAntworten = 0;
        falscheAntwortenString = Integer.toString(falscheAntworten);
        anzahlFalscheAntworten.setText(falscheAntwortenString);

        prozentwertAntworten = 0.0f;
        prozentwertAntwortenString = Float.toString(prozentwertAntworten);
        richtigeFragenProzent.setText(prozentwertAntwortenString + "%");
    }

    //Methode zur Berechnung der Anzahl von falschen Fragen
    public int berechnenFalscheAntworten(int anzahlFragen, int anzahlRichtigeFragen) {
        int anzahlFalscheFragen = anzahlFragen - anzahlRichtigeFragen;
        return anzahlFalscheFragen;
    }

    //Methode zur Berchnung des Prozentwertes der richtig beantworteten Fragen
    public float berechnenProzentwert (int anzahlFragen, int anzahlRichtigeFragen){
        float prozentwertRichtigerFragen = (float)anzahlRichtigeFragen/anzahlFragen * 100;
        if (anzahlRichtigeFragen == 0 || anzahlFragen == 0){
            prozentwertRichtigerFragen = 0.0f;
        }
        //Ergebnis runden
        float prozentwertRichtigerFragenGerundet = (float) Math.round(prozentwertRichtigerFragen * 100) / 100;
        return (prozentwertRichtigerFragenGerundet);
    }





    /* --- STATISTIKEN FÜR SPIELMODUS BLITZSPIEL --- */


    //Gesamtzahl der beantworteten Fragen des Blitzspiels aus Datei lesen
    public void lesenGesamtAntwortenSchnell() {
        String zeile;

        try {
            //Zugriff auf Verzeichnis
            File datei = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS);

            File ausgabedatei = new File(datei, "statistikGesamtAntwortenSchnell.txt");
            // File-Objekt erzeugen
            // File datei = new File
            //        (getFilesDir(), "text.txt");
            // Stream erzeugen
            BufferedReader br = new BufferedReader(
                    new FileReader(ausgabedatei));

            // Aus Stream lesen
            do {
                zeile = br.readLine();
                gesamtAntwortenSchnell = Integer.parseInt(zeile);
                anzahlAntwortenGesamtSchnell.setText(zeile);
            }
            while (zeile == null);

            // Stream schließen
            br.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
    }

    //Anzahl der richtigen Antworten des Blitzspiels aus Datei lesen
    public void lesenRichtigeAntwortenSchnell(){
        String zeile;

        try {
            //Zugriff auf Verzeichnis
            File datei = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS);

            File ausgabedatei = new File(datei, "statistikRichtigeAntwortenSchnell.txt");

            // Stream erzeugen
            BufferedReader br = new BufferedReader(
                    new FileReader(ausgabedatei));

            // Aus Stream lesen
            do {
                zeile = br.readLine();
                richtigeAntwortenSchnell = Integer.parseInt(zeile);
                anzahlRichtigeAntwortenSchnell.setText(zeile);
            }
            while (zeile == null);

            // Stream schließen
            br.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
    }

    //Wert 0 in Datei Anzahl beantworteter Fragen setzen bzw Statistik dazu löschen
    public void statistikLoeschenGesamtFragenSchnell(){
        //Zugriff auf Verzeichnis
        File documents = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);

        File ausgabedatei = new File(documents, "statistikGesamtAntwortenSchnell.txt");

        try {
            //Stream erzeugen
            FileOutputStream fo = new FileOutputStream(ausgabedatei);
            // FileOutputStream fos = openFileOutput("text.txt", Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fo);
            //In Stream schreiben
            pw.println("0");
            //Stream schließen
            pw.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
    }

    //Wert 0 in Datei Anzahl Richtiger Fragen setzen bzw Statistik dazu löschen
    public void statistikLoeschenRichtigeFragenSchnell(){
        //Zugriff auf Verzeichnis
        File documents = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);

        File ausgabedatei = new File(documents, "statistikRichtigeAntwortenSchnell.txt");

        try {
            //Stream erzeugen
            FileOutputStream fo = new FileOutputStream(ausgabedatei);
            // FileOutputStream fos = openFileOutput("text.txt", Context.MODE_PRIVATE);
            PrintWriter pw = new PrintWriter(fo);
            //In Stream schreiben
            pw.println("0");
            //Stream schließen
            pw.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
    }

    //gesamte Statistik des Blitzspiels löschen
    public void statistikLoeschenSchnell(View view) {

        statistikLoeschenGesamtFragenSchnell();
        statistikLoeschenRichtigeFragenSchnell();
        lesenGesamtAntwortenSchnell();
        lesenRichtigeAntwortenSchnell();

        falscheAntwortenSchnell = 0;
        falscheAntwortenStringSchnell = Integer.toString(falscheAntwortenSchnell);
        anzahlFalscheAntwortenSchnell.setText(falscheAntwortenStringSchnell);

        prozentwertAntwortenSchnell = 0.0f;
        prozentwertAntwortenStringSchnell = Float.toString(prozentwertAntwortenSchnell);
        richtigeFragenProzentSchnell.setText(prozentwertAntwortenStringSchnell + "%");
    }

    //Methode zur Berechnung der Anzahl falsch beantworteter Fragen - Blitzspiel
    public int berechnenFalscheAntwortenSchnell(int anzahlFragenSchnell, int anzahlRichtigeFragenSchnell) {
        int anzahlFalscheFragenSchnell = anzahlFragenSchnell - anzahlRichtigeFragenSchnell;
        return anzahlFalscheFragenSchnell;
    }

    //Methode zur Berchnung des Prozentwertes der richtig beantworteten Fragen - Blitzspiel
    public float berechnenProzentwertSchnell(int anzahlFragen, int anzahlRichtigeFragen){
        float prozentwertRichtigerFragen = (float)anzahlRichtigeFragen/anzahlFragen * 100;
        if (anzahlRichtigeFragen == 0 || anzahlFragen == 0){
            prozentwertRichtigerFragen = 0.0f;
        }
        //Ergebnis runden
        float prozentwertRichtigerFragenGerundet = (float) Math.round(prozentwertRichtigerFragen * 100) / 100;
        return (prozentwertRichtigerFragenGerundet);
    }


    public void zumHauptmenue(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }
    }
