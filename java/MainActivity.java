package com.example.kevin.quiz;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/* Autoren: Kevin Mass, Jan Rehberger
 * Erstelldatum: 10.07.2018
 * Version: 1.0
 *
 * Beschreibung der App:
 * Bei dieser App handelt es sich um ein Quiz-Spiel. In der Datenbank befindet sich eine
 * Fragensammlung, die jeweiligen Fragen mit den Antwortmöglichkeiten werden in einer ArrayList
 * gespeichert. Es gibt zwei verschiedene Spielmodi:
 * 1.Normales Spiel - Dem Nutzer wird eine Frage mit vier Antwortmöglichkeiten gestellt, für die
 * Beantwortung der Frage hat der Nutzer 25 Sekunden Zeit. Bei richtiger Beantwortung der Frage oder
 * nach Ablauf der Beantwortungszeit wird die richtige Antwort in grün angezeigt. Eine Frage gilt
 * nur dann als falsch beantwortet, wenn eine falsche Antwortmöglichkeit ausgewählt wurde.
 * Zeitablauf oder das Überspringen einer Frage gelten nicht als Fehler.
 * 2.Blitzspiel: Ein wesentlicher Unterschied zwischen dem normalen Spiel und dem Blitzspiel ist
 * zum Beispiel die Beantwortungszeit, diese beträgt hier nur 10 Sekunden. Nach Ablauf der Zeit gilt
 * eine Frage als falsch beantwortet, das Überspringen einer Frage ist ohne Fehlerpunkte möglich.
 * Es existiert für beide Spielarten jeweils eine separate Statistik, diese enthält
 * Informationen zur Gesamtanzahl der beantworteten Fragen, die Anzahl der richtigen oder
 * falschen Antworten sowie eine prozentuale Berechnung der Erfolgsquote.
 * Außerdem besteht die Möglichkeit, einen Spitznamen zu speichern, diese Option ist in
 * den Einstellungen zu finden.
 */

public class MainActivity extends AppCompatActivity {

    final int REQUEST_CODE = 4711;
    boolean permission_write_external_granted = false;  /* prüft, ob Berechtigung für Zugriff
                                                                    auf Speicher gegeben ist*/
   // File documents = Environment.getExternalStoragePublicDirectory(
    //        Environment.DIRECTORY_DOCUMENTS);

   // File ausgabedatei = new File(String.valueOf(documents));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        setTitle("Hauptmenü");
        }

    //Prüfen, ob Berechtigung für Speicherzugriff vorhanden ist
    private void checkPermission() {

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
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        try {
            if (requestCode == REQUEST_CODE) {
                // Permission prüfen
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Recht liegt vor, nun kann die gewünschte Operation ausgeführt werden.
                    permission_write_external_granted = true;
                }
            }
        } catch (SecurityException ex) {
            Log.d("meinApp", ex.getMessage());
        }
    }

    //Methode zur Spielauswahl
    public void spiel_starten(View view) {
        Intent i = new Intent(this, Spielauswahl.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    //Methode zum Hinzufuegen von Fragen
    public void fragen_hinzufuegen(View view) {
        Intent i = new Intent(this, FragenHinzufuegen.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    //Methode zum Anzeigen der Statistik
    public void statistik_anzeigen(View view) {
        Intent i = new Intent(this, Statistik.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    //Methode zum anzeigen/ändern der Einstellungen
    public void einstellungen_anzeigen(View view) {
        Intent i = new Intent(this, Einstellungen.class);
        startActivityForResult(i, REQUEST_CODE);
    }
}
