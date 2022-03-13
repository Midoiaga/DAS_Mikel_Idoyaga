package com.example.das_mikel_idoyaga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;


//MainActivity
public class LoginActivity extends AppCompatActivity {
    private Locale local = new Locale("es");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Al crear la actividad se vincula con  el layout y el idioma
        super.onCreate(savedInstanceState);
        //Extras guardados para mantener el idioma
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
        setContentView(R.layout.login_activity);
    }

    public void onIdioma(View view){
        //Función para cambiar de idioma dependiendo a que idioma clickes
        if (view.getId() == R.id.btnEspañol){
            local = new Locale("es");
        }
        else {
            local = new Locale("en");
        }
        Locale.setDefault(local);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(local);
        configuration.setLayoutDirection(local);
        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        finish();
        startActivity(getIntent().putExtra("Idioma",local));
    }
}