package com.example.kevin.quiz;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;


// Quelle: https://www.youtube.com/watch?v=fn5OlqQuOCk
// Autor: Filip Vujovic

public class Pop extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*1.0),(int)(height*.05));

    }
}
