package com.example.das_mikel_idoyaga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class GameActivity extends AppCompatActivity {
    private Locale local;
    private int cont = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Al crear la actividad se vincula con  el layout que sirve para iniciar sesion y con el idioma
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //Extras guardados para mantener el idioma
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            local = (Locale) extras.get("Idioma");
        }
        if (savedInstanceState!= null)
        {
            cont= savedInstanceState.getInt("cont");
            Log.d("cont",Integer.toString(cont));
            TextView tvCont = findViewById(R.id.tvCont);
            tvCont.setText(Integer.toString(cont));
        }
        Locale.setDefault(local);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(local);
        configuration.setLayoutDirection(local);
        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("cont",cont);
        Log.d("cont",Integer.toString(cont));

    }
    public void onIns (View v){
        DialogFragment dialogoalerta= new InsDialogo();
        dialogoalerta.show(getSupportFragmentManager(), "etiqueta");
    }
    public void onComp(View v){
        String subject = "Monster Clicker";
        String body = "";
        if("es".equalsIgnoreCase(local.toString())){
            body = "¡Mira! Ya he clickado "+cont+" veces, cada vez me falta menos para salvar a todos";
        }else{
            body = "Look! I have already clicked "+cont+" times, I am closer to save all of us";
        }
        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_SUBJECT, subject);
        email.putExtra(Intent.EXTRA_TEXT, body);

        email.setType("message/rfc822");

        startActivity(Intent.createChooser(email, "Choose an Email client :"));
    }
    public void onClicker(View v){
        cont++;
        TextView tvCont = findViewById(R.id.tvCont);
        tvCont.setText(Integer.toString(cont));
    }
}