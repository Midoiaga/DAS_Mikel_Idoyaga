package com.example.das_mikel_idoyaga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Locale;

public class TopActivity extends AppCompatActivity {
    private MiBD GestorDB = new MiBD (this, "MiDB", null, 1);
    private Locale local;
    private int cont = 0;
    private String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            local = (Locale) extras.get("Idioma");
            cont  = (int) extras.get("cont");
            usuario = (String) extras.get("usuario");

        }
        if (savedInstanceState!= null)
        {
            Log.d("loca",savedInstanceState.getString("idioma"));
            local = new Locale(savedInstanceState.getString("idioma"));
            cont= savedInstanceState.getInt("cont");
            usuario=savedInstanceState.getString("usuario");
            Log.d("cont",Integer.toString(cont));
        }
        Locale.setDefault(local);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(local);
        configuration.setLayoutDirection(local);
        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        int[] personajes={R.drawable.uno,R.drawable.dos,R.drawable.tres};
        Log.d("top",Integer.toString(cont));
        Log.d("top",usuario);
        GestorDB.updateUsuario(usuario,cont);
        ArrayList<UsuarioTop> arrayUsu = GestorDB.top3();
        String[] nombres = {arrayUsu.get(0).getUsuario(),arrayUsu.get(1).getUsuario(),arrayUsu.get(2).getUsuario()};
        String[] puntuaciones = {arrayUsu.get(0).getPuntuacion(),arrayUsu.get(1).getPuntuacion(),arrayUsu.get(2).getPuntuacion()};
        setContentView(R.layout.activity_top);
        ListView simpsons= (ListView) findViewById(R.id.lista);
        AdaptadorListView eladap= new AdaptadorListView(getApplicationContext(),nombres,personajes,puntuaciones);
        simpsons.setAdapter(eladap);

    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("cont",cont);
        Log.d("cont",Integer.toString(cont));
        savedInstanceState.putString("idioma",local.toString());
        savedInstanceState.putString("usuario",usuario);


    }
}