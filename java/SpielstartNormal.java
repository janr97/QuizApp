package com.example.kevin.quiz;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.ArrayList;


// Quellenangabe zur ProgressBar:
// https://www.myandroidsolutions.com/2015/07/04/android-countdown-progressbar/#.WzUYEdIzbIU
// Quellenangabe zum Timer:
// https://codinginflow.com/tutorials/android/countdowntimer/part-1
public class SpielstartNormal extends AppCompatActivity {

    private ObjectAnimator Fortschrittsanzeige; //Balken zur Angabe der verbleibenden Zeit

    Button antwortEinsButton, antwortZweiButton, antwortDreiButton, antwortVierButton;
    TextView frageTextView;
    int aktuellePosition = 0; //entspricht anfangs Position 0 in der ArrayList aus der Datenbank

    //zur statistischen Auswertung
    String     anzahlFragenBeantwortetGesamtString;
    public int anzahlFragenBeantwortetGesamt;
    String     anzahlFragenRichtigBeantwortetString;
    public int anzahlFragenRichtigBeantwortet;

    private CountDownTimer mCountDownTimer;
    private static final long beantwortungszeit = 25000; //25 Sekunden Zeit, um Antwort auszuwählen
    long verbleibendeZeit                       = beantwortungszeit;

    Datenbank groesseArrayList = new Datenbank();
    int groesse = groesseArrayList.ArrayListGroesse(); //gibt die Groesse der ArrayList an
    int[] reihenfolgeArray; //gibt eine (Zufalls-)Reihenfolge in einem Array an
    int aktuelleFrage; //gibt die aktuelle Frage aus der ArrayList an

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spielstart_normal);
        setTitle("Normales Spiel");

        //Schnittstellen Java - XML
        antwortEinsButton = (Button) findViewById(R.id.antwortEinsButton);
        antwortZweiButton = (Button) findViewById(R.id.antwortZweiButton);
        antwortDreiButton = (Button) findViewById(R.id.antwortDreiButton);
        antwortVierButton = (Button) findViewById(R.id.antwortVierButton);
        frageTextView = (TextView) findViewById(R.id.frageTextView);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.Fortschrittsanzeige);
        Fortschrittsanzeige = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
        Fortschrittsanzeige.setDuration(beantwortungszeit); // 25 Sekunden

        //ZufallsArrayReihenfolge aus Methode "holen", für gesamte Arraylänge
        reihenfolgeArray = zufallsArrayReihenfolge(0, groesse - 1);

        dateiVerifizierer();
        frageAnzeigen();
        lesenGesamtAntworten();
        lesenRichtigeAntworten();
        //ausgelesenen String zu int konvertieren
        anzahlFragenBeantwortetGesamt  = Integer.parseInt(anzahlFragenBeantwortetGesamtString);
        anzahlFragenRichtigBeantwortet = Integer.parseInt(anzahlFragenRichtigBeantwortetString);
    }

    //Frage mit Antwortmöglichkeiten anzeigen sowie Zufallsanordnung der Antwortmöglichkeiten,
    //Fortschrittsanzeige und Countdown starten
    private void frageAnzeigen() {

        Datenbank fragenDatenbank = new Datenbank();
        //alle Fragen aus der Datenbank holen
        ArrayList<String[]> fragenSammlung = fragenDatenbank.fragenHolen();
        aktuelleFrage = reihenfolgeArray[aktuellePosition];
        //aktuelle Frage inkl. Antwortmöglichkeiten nehmen
        String[] frage = fragenSammlung.get(aktuelleFrage);

        //zur zufälligen Anordnung der Antwortmöglichkeiten
        //max 4, min 0; +1 damit die 0 ausgeschlossen wird
        int zufallszahl = (int) (Math.random() * (4 - 0) + 1);
        frageTextView.setText(frage[0]);  //Position 0 aus ArrayList anzeigen (= die Frage)

        //Zufällige Anordnung der Antwortmöglichkeiten, 4 Möglichkeiten der Zuordnung
        switch (zufallszahl) {
            case 1:
                antwortEinsButton.setText(frage[1]);
                antwortZweiButton.setText(frage[2]);
                antwortDreiButton.setText(frage[3]);
                antwortVierButton.setText(frage[4]);
                break;
            case 2:
                antwortEinsButton.setText(frage[4]);
                antwortZweiButton.setText(frage[1]);
                antwortDreiButton.setText(frage[2]);
                antwortVierButton.setText(frage[3]);
                break;
            case 3:
                antwortEinsButton.setText(frage[3]);
                antwortZweiButton.setText(frage[4]);
                antwortDreiButton.setText(frage[1]);
                antwortVierButton.setText(frage[2]);
                break;
            case 4:
                antwortEinsButton.setText(frage[2]);
                antwortZweiButton.setText(frage[3]);
                antwortDreiButton.setText(frage[4]);
                antwortVierButton.setText(frage[1]);
                break;
        }

        Fortschrittsanzeige.start(); //Fortschrittsanzeige: Durchlauf starten
        startTimer();  //Countdown starten
    }


    //Methode zum Starten des Timers, nach Zeitablauf werden die Antwortmöglichkeiten ausgeblendet
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(verbleibendeZeit, 1000) {

            //Aktualisierung der verbleibenden Zeit
            @Override
            public void onTick(long millisUntilFinished) {
                verbleibendeZeit = millisUntilFinished;
            }

            //Bei Ende des Timers werden die Buttons ausgeblendet und gefärbt und
            //eine entsprechende Meldung (Toast) ausgegeben
            @Override
            public void onFinish() {
                buttonAusblenden();
                richtigeAntwortFaerben();
                toastAusgeben(3);
            }
        }.start();
    }

    //aktueller Timer wird abgebrochen und zurückgesetzt
    private void resetTimer() {
        mCountDownTimer.cancel();
        verbleibendeZeit = beantwortungszeit;
    }

    //Methode zur Prüfung der gegebenen Antwort
    private boolean auswerten(String gegebeneAntwort) {
        boolean bewerteteAntwort = false;
        Datenbank fragenDatenbank = new Datenbank();
        ArrayList fragenSammlung = fragenDatenbank.fragenHolen();
        //"Kopie" der Frage+Antworten zur Korrektheitsprüfung
        String[] speicher = (String[]) fragenSammlung.get(aktuelleFrage);
        if (speicher[1].equals(gegebeneAntwort)) { //prüfen, ob gegebene Antwort korrekt ist
            bewerteteAntwort = true;
            anzahlFragenRichtigBeantwortet++; //Statistikzähler erhöhen
        }
        anzahlFragenBeantwortetGesamt++; //Statistikzähler erhöhen
        return bewerteteAntwort;
    }

    //Statistiken zur Gesamtzahl der beantworteten Fragen in Datei speichern
    public void speichernGesamtAntworten() {

        //Zugriff auf Verzeichnis "DOCUMENTS"
        File documents = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        File ausgabedatei = new File(documents, "statistikGesamtAntworten.txt");

        try {
            //Stream erzeugen
            FileOutputStream fo = new FileOutputStream(ausgabedatei);
            PrintWriter pw = new PrintWriter(fo);
            //In Stream schreiben
            pw.println(anzahlFragenBeantwortetGesamt);
            //Stream schließen
            pw.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
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

            //aus Stream lesen, wie viele Fragen gesamt beantwortet wurden und
            //in einer String-Variable "speichern" bzw. "merken"
            do {
                zeile = br.readLine();
                anzahlFragenBeantwortetGesamtString = zeile;
            }
            while (zeile == null);

            // Stream schließen
            br.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
    }


    //Statistiken zur Anzahl der RICHTIG beantworteten Fragen in Datei speichern
    public void speichernRichtigeAntworten() {

        //Zugriff auf Verzeichnis "DOCUMENTS"
        File documents = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);

        File ausgabedatei = new File(documents, "statistikRichtigeAntworten.txt");

        try {
            //Stream erzeugen
            FileOutputStream fo = new FileOutputStream(ausgabedatei);
            PrintWriter pw = new PrintWriter(fo);
            //In Stream schreiben
            pw.println(anzahlFragenRichtigBeantwortet);
            //Stream schließen
            pw.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
    }


    //Gesamtzahl der RICHTIG beantworteten Fragen aus Datei lesen
    public void lesenRichtigeAntworten() {
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
                anzahlFragenRichtigBeantwortetString = zeile;
            }
            while (zeile == null);

            // Stream schließen
            br.close();
        } catch (IOException ex) {
            Log.d("meineApp", ex.getMessage());
        }
    }


    //Methode zur Färbung der richtigen Antwort
    private void richtigeAntwortFaerben () {

        //jeweilige Antwortmöglichkeiten holen und zu Strings konvertieren
        String ButtonEinsErgebnis = antwortEinsButton.getText().toString();
        String ButtonZweiErgebnis = antwortZweiButton.getText().toString();
        String ButtonDreiErgebnis = antwortDreiButton.getText().toString();
        String ButtonVierErgebnis = antwortVierButton.getText().toString();

        Datenbank fragenDatenbank = new Datenbank();
        ArrayList fragenSammlung = fragenDatenbank.fragenHolen();
        String[] speicher = (String[]) fragenSammlung.get(aktuelleFrage);
        if (speicher[1].equals(ButtonEinsErgebnis)) { //prüfen, ob gegebene Antwort korrekt ist
            //korrekte Antwort grün färben, den Rest rot färben
            antwortEinsButton.setBackgroundResource(R.drawable.runder_button_green);
            antwortZweiButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortDreiButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortVierButton.setBackgroundResource(R.drawable.runder_button_red);
        }
        if (speicher[1].equals(ButtonZweiErgebnis)) { //prüfen, ob gegebene Antwort korrekt ist
            antwortEinsButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortZweiButton.setBackgroundResource(R.drawable.runder_button_green);
            antwortDreiButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortVierButton.setBackgroundResource(R.drawable.runder_button_red);
        }
        if (speicher[1].equals(ButtonDreiErgebnis)) { //prüfen, ob gegebene Antwort korrekt ist
            antwortEinsButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortZweiButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortDreiButton.setBackgroundResource(R.drawable.runder_button_green);
            antwortVierButton.setBackgroundResource(R.drawable.runder_button_red);
        }
        if (speicher[1].equals(ButtonVierErgebnis)) { //prüfen, ob gegebene Antwort korrekt ist
            antwortEinsButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortZweiButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortDreiButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortVierButton.setBackgroundResource(R.drawable.runder_button_green);
        }
    }

    //Wenn Antwort richtig ist, dann wird Popup "Richtig" angezeigt
    //Fortschrittsanzeige anhalten und nächste Frage starten
    public void buttonEinsGeklickt (View view){

        //Falls gegebene Antwort richtig ist, wird entsprechendes Popup angezeigt und
        //dieser Button wird grün gefärbt, alle anderen werden rot gefärbt
        if (auswerten(antwortEinsButton.getText().toString())) {
            popup(1);
            antwortEinsButton.setBackgroundResource(R.drawable.runder_button_green);
            antwortZweiButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortDreiButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortVierButton.setBackgroundResource(R.drawable.runder_button_red);
        } else {
            popup(2);
            richtigeAntwortFaerben();
        }

        //Nach Buttonklick werden Fortschrittsanzeige und Timer zurückgesetzt + Buttons ausgeblendet
        Fortschrittsanzeige.cancel();
        resetTimer();
        buttonAusblenden();
    }

    public void buttonZweiGeklickt (View view){
        if (auswerten(antwortZweiButton.getText().toString())) {
            popup(1);
            antwortEinsButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortZweiButton.setBackgroundResource(R.drawable.runder_button_green);
            antwortDreiButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortVierButton.setBackgroundResource(R.drawable.runder_button_red);
        } else {
            popup(2);
            richtigeAntwortFaerben();
        }

        Fortschrittsanzeige.cancel();
        resetTimer();
        buttonAusblenden();
    }

    public void buttonDreiGeklickt (View view){
        if (auswerten(antwortDreiButton.getText().toString())) {
            popup(1);
            antwortEinsButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortZweiButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortDreiButton.setBackgroundResource(R.drawable.runder_button_green);
            antwortVierButton.setBackgroundResource(R.drawable.runder_button_red);
        } else {
            popup(2);
            richtigeAntwortFaerben();
        }

        Fortschrittsanzeige.cancel();
        resetTimer();
        buttonAusblenden();
    }

    public void buttonVierGeklickt (View view){
        if (auswerten(antwortVierButton.getText().toString())) {
            popup(1);
            antwortEinsButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortZweiButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortDreiButton.setBackgroundResource(R.drawable.runder_button_red);
            antwortVierButton.setBackgroundResource(R.drawable.runder_button_green);
        } else {
            popup(2);
            richtigeAntwortFaerben();
        }
        Fortschrittsanzeige.cancel();
        resetTimer();
        buttonAusblenden();
    }

    //Methode zum anzeigen der nächsten Frage
    public void naechsteFrage (View view){
        //falls alle Fragen in Datenbank durchlaufen wurden, zum "Hauptmenü" springen
        if (aktuellePosition >= groesse-1) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            speichernGesamtAntworten();
            speichernRichtigeAntworten();
        //falls noch Fragen in Datenbank vorhanden sind, nächste Frage anzeigen
        } else {
            aktuellePosition++;
            resetTimer();
            frageAnzeigen();
        }
        //Buttons wieder einblenden und Rot-/Grün-Färbung entfernen
        buttonEinblenden();
        antwortEinsButton.setBackgroundResource(R.drawable.runder_button);
        antwortZweiButton.setBackgroundResource(R.drawable.runder_button);
        antwortDreiButton.setBackgroundResource(R.drawable.runder_button);
        antwortVierButton.setBackgroundResource(R.drawable.runder_button);
    }

    //Bei Klick auf Spiel beenden erfolgt Sprung zum "Hauptmenü"
    public void spielBeenden (View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        resetTimer();
        speichernGesamtAntworten();
        speichernRichtigeAntworten();
    }

    //Methode zum Ausblenden von Buttons, dadurch kein Klicken möglich
    public void buttonAusblenden () {
        antwortEinsButton.setEnabled(false);
        antwortZweiButton.setEnabled(false);
        antwortDreiButton.setEnabled(false);
        antwortVierButton.setEnabled(false);
    }

    //Methode zum (Wieder-)Einblenden von Buttons, dadurch Klicken (wieder) möglich
    public void buttonEinblenden () {
        antwortEinsButton.setEnabled(true);
        antwortZweiButton.setEnabled(true);
        antwortDreiButton.setEnabled(true);
        antwortVierButton.setEnabled(true);
    }

    //Methode zum Ausgeben von Toast's bei richtiger/falscher Antwort oder abgelaufener Zeit
    public void toastAusgeben ( int toastNummer){
        Toast toast;
        TextView v;
        switch (toastNummer) {
            case 1:
                toast = Toast.makeText(this, "RICHTIG!", Toast.LENGTH_SHORT);
                v = (TextView) toast.getView().findViewById(android.R.id.message);
                v.setTextColor(Color.GREEN);
                toast.show();
                break;
            case 2:
                toast = Toast.makeText(this, "FALSCH!", Toast.LENGTH_SHORT);
                v = (TextView) toast.getView().findViewById(android.R.id.message);
                v.setTextColor(Color.RED);
                toast.show();
                break;
            case 3:
                toast = Toast.makeText(this, "Zeit abgelaufen!", Toast.LENGTH_SHORT);
                v = (TextView) toast.getView().findViewById(android.R.id.message);
                v.setTextColor(Color.RED);
                toast.show();
                break;
        }
    }

    //Methode zum Ausgeben von Popup's bzw. Aufrufen der Popup-Klassen (Pop=richtig, Pop2=falsch)
    public void popup(int popupnummer){
        switch(popupnummer) {
            case 1:
                startActivity(new Intent(SpielstartNormal.this, Pop.class));
                break;
            case 2:
                startActivity(new Intent(SpielstartNormal.this, Pop2.class));
                break;
        }
    }

    //Diese Methode erzeugt ein Array mit Zufallswerten von "von" bis "bis"
    //Diese Zufallswerte im Array werden für die Reihenfolge der Fragen genutzt
    static int[] zufallsArrayReihenfolge(int von, int bis){
        //"von" steht für den Anfangswert des Arrays, "bis" für den Endwert,
        //dadurch erhält man die Größe des Array's
        int groesse = bis - von + 1;

        //Array um alle Zahlen zu speichern
        int arrayZwischenspeicher[] = new int[groesse];
        for (int i = 0; i < groesse; i++) {
            arrayZwischenspeicher[i] = i;
        }
        //Array um Ergebnis(=Reihenfolge der int-Werte im Array) zu speichern
        int[] ergebnis = new int[groesse];
        int x = groesse;
        SecureRandom rd = new SecureRandom();
        for (int i = 0; i < groesse; i++) {
            //k ist ein zufälliger Index in [0,x]
            int k = rd.nextInt(x);
            ergebnis[i] = arrayZwischenspeicher[k];
            //nun haben wir einen Wert von a[k]. Wir ersetzen ihn durch den Wert des letzten Index
            arrayZwischenspeicher[k] = arrayZwischenspeicher[x - 1];
            //dann ziehen wir von x eins ab um einen zufälligen Index von 0 - x zu
            //erhalten --> keine Duplikate, jeder Wert kommt genau ein Mal dran
            x--;
        }
        return ergebnis;
    }

    // Diese Methode stellt sicher, dass die für die Statistik benötigten Text-Dateien vorhanden
    // sind. Sie prüft jede der 4 Text-Dateien auf Existenz und Inhalt und erstellt und füllt
    // diese mit einem Standardwert, sollte eine der beiden Bedingungen nicht erfüllt sein.
    public void dateiVerifizierer() {
        File documents = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);
        File ausgabedatei = new File(documents, "statistikGesamtAntworten.txt");
        ausgabedatei = new File(documents, "statistikRichtigeAntworten.txt");
        ausgabedatei = new File(documents, "statistikGesamtAntwortenSchnell.txt");
        ausgabedatei = new File(documents, "statistikRichtigeAntwortenSchnell.txt");

        if(!ausgabedatei.exists() || ausgabedatei.length()==0) {
            ausgabedatei = new File(documents,"statistikGesamtAntworten.txt");
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
            ausgabedatei = new File(documents,"statistikRichtigeAntworten.txt");
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
            ausgabedatei = new File(documents,"statistikGesamtAntwortenSchnell.txt");
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
            ausgabedatei = new File(documents,"statistikRichtigeAntwortenSchnell.txt");
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
    }
}


