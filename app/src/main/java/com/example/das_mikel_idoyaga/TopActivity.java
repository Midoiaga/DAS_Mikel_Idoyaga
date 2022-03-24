package com.example.das_mikel_idoyaga;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
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
        //Al crear la actividad se vincula con  el layout del TOP de usurios. Mantendremos el usuario, contador e idioma
        //por si el usuario quiere girar la pantalla, coge una llamada...
        super.onCreate(savedInstanceState);
        //Quitar notificaciones
        NotificationManager elmanager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        elmanager.cancelAll();
        //Extras guardados para mantener los atributos necesarios
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            local = (Locale) extras.get("Idioma");
            cont  = (int) extras.get("cont");
            usuario = (String) extras.get("usuario");

        }
        //Bundle guardado para mantener los atributos necesarios
        if (savedInstanceState!= null)
        {
            local = new Locale(savedInstanceState.getString("idioma"));
            cont= savedInstanceState.getInt("cont");
            usuario=savedInstanceState.getString("usuario");
        }
        //Definir idioma
        Locale.setDefault(local);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(local);
        configuration.setLayoutDirection(local);
        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        ///Definir contenido de la lista
        int[] personajes={R.drawable.uno,R.drawable.dos,R.drawable.tres};
        GestorDB.updateUsuario(usuario,cont);
        ArrayList<UsuarioTop> arrayUsu = GestorDB.top3();
        String[] nombres = {arrayUsu.get(0).getUsuario(),arrayUsu.get(1).getUsuario(),arrayUsu.get(2).getUsuario()};
        String[] puntuaciones = {arrayUsu.get(0).getPuntuacion(),arrayUsu.get(1).getPuntuacion(),arrayUsu.get(2).getPuntuacion()};
        setContentView(R.layout.activity_top);
        ListView topUsu= (ListView) findViewById(R.id.lista);
        AdaptadorListView eladap= new AdaptadorListView(getApplicationContext(),nombres,personajes,puntuaciones);
        topUsu.setAdapter(eladap);

    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //Guardar el bundle para que no se pierdan los atributos necesarios para el desarrollo de la actividad.
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("cont",cont);
        savedInstanceState.putString("idioma",local.toString());
        savedInstanceState.putString("usuario",usuario);
    }
}