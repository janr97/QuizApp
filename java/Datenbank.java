package com.example.kevin.quiz;

import java.util.ArrayList;

public class Datenbank {

    ArrayList<String[]> fragenSammlung; //in dieser ArrayList werden die Fragen gespeichert

    //Konstruktor
    public Datenbank(){
        //Initialgröße = 10, quasi zum "Reservieren" vom Speicherplatz für effizientes Arbeiten
        fragenSammlung = new ArrayList<>(10);
        fragenUndAntwortmoeglichkeitenHinzufuegen();
    }

    //Fragen und Antwortmoeglichkeiten erstellen
    public void fragenUndAntwortmoeglichkeitenHinzufuegen() {
        String[] frage1 = new String[5]; /*speichert eine Frage + vier Antwortmöglichkeiten */
        frage1[0] = "Wer gewann die Fußball-Weltmeisterschaft im Jahr 2014?";
        frage1[1] = "Deutschland"; /*Die erste Antwortmoeglichkeit ist immer die Richtige!!! */
        frage1[2] = "Argentinien";
        frage1[3] = "Frankreich";
        frage1[4] = "Brasilien";

        String[] frage2 = new String[5];
        frage2[0] = "Welches Sternzeichen ist man, wenn man am 28. Oktober geboren wurde?";
        frage2[1] = "Skorpion";
        frage2[2] = "Krebs";
        frage2[3] = "Jungfrau";
        frage2[4] = "Wassermann";

        String[] frage3 = new String[5];
        frage3[0] = "Wie lauten die ersten drei Nachkommastellen der Zahl 'Pi'?";
        frage3[1] = "141";
        frage3[2] = "147";
        frage3[3] = "133";
        frage3[4] = "137";

        String[] frage4 = new String[5];
        frage4[0] = "Was passiert, wenn ein rohes Ei in der Mikrowelle erhitzt wird?";
        frage4[1] = "es explodiert";
        frage4[2] = "nichts";
        frage4[3] = "es färbt sich blau";
        frage4[4] = "es fängt Feuer";

        String[] frage5 = new String[5];
        frage5[0] = "In welchem Jahr fiel die Berliner Mauer?";
        frage5[1] = "1989";
        frage5[2] = "1945";
        frage5[3] = "2016";
        frage5[4] = "1987";

        String[] frage6 = new String[5];
        frage6[0] = "Wie hoch ist der Eiffelturm?";
        frage6[1] = "325 Meter";
        frage6[2] = "211 Meter";
        frage6[3] = "747 Meter";
        frage6[4] = "523 Meter";

        String[] frage7 = new String[5];
        frage7[0] = "In welchem Jahr fand die erste Mondlandung statt?";
        frage7[1] = "1969";
        frage7[2] = "1970";
        frage7[3] = "1996";
        frage7[4] = "1967";

        String[] frage8 = new String[5];
        frage8[0] = "Woher stammt die Siamkatze?";
        frage8[1] = "Thailand";
        frage8[2] = "China";
        frage8[3] = "Japan";
        frage8[4] = "Südkorea";

        String[] frage9 = new String[5];
        frage9[0] = "Welche Firma entwickelte Android Studio?";
        frage9[1] = "Google";
        frage9[2] = "Apple";
        frage9[3] = "Facebook";
        frage9[4] = "Microsoft";

        String[] frage10 = new String[5];
        frage10[0] = "In welchem US-Bundesstat spielt 'The Big Bang Theory'?";
        frage10[1] = "Kalifornien";
        frage10[2] = "Florida";
        frage10[3] = "New York";
        frage10[4] = "Illinois";

        String[] frage11 = new String[5];
        frage11[0] = "Wie viel Prozent der Weltbevölkerung sind Rechtshänder?";
        frage11[1] = "80 - 90%";
        frage11[2] = "50 - 60%";
        frage11[3] = "90 - 99%";
        frage11[4] = "60 - 70%";

        String[] frage12 = new String[5];
        frage12[0] = "Was war das erste Produkt von Sony?";
        frage12[1] = "Reiskocher";
        frage12[2] = "Waschmaschine";
        frage12[3] = "Kopfhörer";
        frage12[4] = "Auto";

        String[] frage13 = new String[5];
        frage13[0] = "In welcher Stadt liegt der Trafalgar Square?";
        frage13[1] = "London";
        frage13[2] = "Paris";
        frage13[3] = "Berlin";
        frage13[4] = "Rom";

        String[] frage14 = new String[5];
        frage14[0] = "Welcher Kontinent ist der Größte?";
        frage14[1] = "Asien";
        frage14[2] = "Afrika";
        frage14[3] = "Europa";
        frage14[4] = "Nordamerika";

        String[] frage15 = new String[5];
        frage15[0] = "Wie oft wurde Deutschland Fußball-Weltmeister?";
        frage15[1] = "4";
        frage15[2] = "3";
        frage15[3] = "7";
        frage15[4] = "5";

        String[] frage16 = new String[5];
        frage16[0] = "Welches ist das größte Bundesland Deutschlands?";
        frage16[1] = "Bayern";
        frage16[2] = "Niedersachsen";
        frage16[3] = "Baden-Württemberg";
        frage16[4] = "Nordrhein-Westfalen";

        String[] frage17 = new String[5];
        frage17[0] = "Was ist die Wurzel aus 169?";
        frage17[1] = "13";
        frage17[2] = "12";
        frage17[3] = "14";
        frage17[4] = "19";

        String[] frage18 = new String[5];
        frage18[0] = "Wie lang ist der Äquator?";
        frage18[1] = "40.000km";
        frage18[2] = "4.000km";
        frage18[3] = "400.000km";
        frage18[4] = "4.000.000km";

        String[] frage19 = new String[5];
        frage19[0] = "101 ist der Binärcode für ...?";
        frage19[1] = "5";
        frage19[2] = "4";
        frage19[3] = "8";
        frage19[4] = "10";

        String[] frage20 = new String[5];
        frage20[0] = "Wann kamen die ersten Disketten auf den Markt?";
        frage20[1] = "1970";
        frage20[2] = "1960";
        frage20[3] = "1980";
        frage20[4] = "1950";


        fragenSammlung.add(0, frage1); /*frage1 an Position 0 der Arraylist hinzufuegen*/
        fragenSammlung.add(1, frage2);
        fragenSammlung.add(2, frage3);
        fragenSammlung.add(3, frage4);
        fragenSammlung.add(4, frage5);
        fragenSammlung.add(5, frage6);
        fragenSammlung.add(6, frage7);
        fragenSammlung.add(7, frage8);
        fragenSammlung.add(8, frage9);
        fragenSammlung.add(9, frage10);
        fragenSammlung.add(10, frage11);
        fragenSammlung.add(11, frage12);
        fragenSammlung.add(12, frage13);
        fragenSammlung.add(13, frage14);
        fragenSammlung.add(14, frage15);
        fragenSammlung.add(15, frage16);
        fragenSammlung.add(16, frage17);
        fragenSammlung.add(17, frage18);
        fragenSammlung.add(18, frage19);
        fragenSammlung.add(19, frage20);
    }

    public ArrayList<String[]> fragenHolen(){
        return fragenSammlung;
    }

    public int ArrayListGroesse(){
        int groesse = fragenSammlung.size();
        return groesse;
    }
}
