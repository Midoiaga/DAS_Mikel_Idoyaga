package com.example.das_mikel_idoyaga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;

import java.util.Locale;

public class PrefActivity extends AppCompatActivity {
    private Locale local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);
        cambiarColor();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            local = (Locale) extras.get("Idioma");
        }
        Locale.setDefault(local);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(local);
        configuration.setLayoutDirection(local);
        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

    public void onSave(View v) {
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("Idioma", local);
        finish();
        startActivity(i);
    }

    private void cambiarColor() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String asignaturapreferida = prefs.getString("colorpref", "blanco");
        ConstraintLayout cLay = findViewById(R.id.layFrag);
        if (asignaturapreferida.equalsIgnoreCase("blanco")) {
            cLay.setBackgroundColor(Color.WHITE);
        } else if (asignaturapreferida.equalsIgnoreCase("azul")) {
            cLay.setBackgroundColor(Color.BLUE);
        } else {
            cLay.setBackgroundColor(Color.RED);
        }
    }
}