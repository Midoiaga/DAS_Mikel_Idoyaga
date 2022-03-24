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
import android.view.View;

import java.util.Locale;

public class PrefActivity extends AppCompatActivity {
    private Locale local;
    private int cont;
    private String usuario;
    private int img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Al crear la actividad se vincula con  el layout que sirve para elegir las preferencias. Mantendremos el usuario, contador e imagen
        //por si el usuario quiere girar la pantalla, coge una llamada...
        super.onCreate(savedInstanceState);
        //Extras guardados para mantener los atributos necesarios
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            local = (Locale) extras.get("Idioma");
            cont  = (int) extras.get("cont");
            usuario = (String) extras.get("usuario");
            img = (int) extras.get("imagen");

        }
        //Bundle guardado para mantener los atributos necesarios
        if (savedInstanceState!= null)
        {
            local = new Locale(savedInstanceState.getString("idioma"));
            cont= savedInstanceState.getInt("cont");
            usuario=savedInstanceState.getString("usuario");
            img=savedInstanceState.getInt("imagen");
        }
        //Definir idioma
        Locale.setDefault(local);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(local);
        configuration.setLayoutDirection(local);
        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        setContentView(R.layout.activity_pref);
        //Cambiar color del fondo según tu preferencia
        cambiarColor();
    }

    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //Guardar el bundle para que no se pierdan los atributos necesarios para el desarrollo de la actividad.
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("cont", cont);
        savedInstanceState.putString("idioma", local.toString());
        savedInstanceState.putString("usuario", usuario);
        savedInstanceState.putInt("imagen", img);
    }

    public void onSave(View v) {
        //Método para volver la actividad del clicker con todos los atributos necesarios guardados
        Intent i = new Intent(this, GameActivity.class);
        i.putExtra("Idioma", local);
        i.putExtra("cont",cont);
        i.putExtra("usuario",usuario);
        i.putExtra("imagen",img);
        finish();
        startActivity(i);
    }

    private void cambiarColor() {
        //Método que una vez definidas las preferencias servira para cambiar el color del fondo
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