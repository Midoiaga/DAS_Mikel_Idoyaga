package com.example.das_mikel_idoyaga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class GameActivity extends AppCompatActivity {
    private Locale local;
    private int cont = 0;
    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Al crear la actividad se vincula con  el layout que sirve para iniciar sesion y con el idioma
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter("android.intent.CLOSE_ACTIVITY");
        registerReceiver(mReceiver, filter);
        //Extras guardados para mantener el idioma
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
        Log.d("loca",local.toString());
        Locale.setDefault(local);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(local);
        configuration.setLayoutDirection(local);
        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        setContentView(R.layout.activity_game);
        cambiarColor();
        TextView tvCont = findViewById(R.id.tvCont);
        tvCont.setText(Integer.toString(cont));



    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("cont",cont);
        Log.d("cont",Integer.toString(cont));
        savedInstanceState.putString("idioma",local.toString());
        savedInstanceState.putString("usuario",usuario);


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
    public void onRendirse(View v){
        finish();
        Intent i = new Intent (getApplicationContext(), TopActivity.class);
        i.putExtra("Idioma",local);
        i.putExtra("cont",cont);
        i.putExtra("usuario",usuario);
        startActivity(i);
    }
    public void onClicker(View v){
        cont++;
        String text ="";
        TextView tvCont = findViewById(R.id.tvCont);
        tvCont.setText(Integer.toString(cont));
        if(cont==10){
            notificador(getResources().getString(R.string.rendirse10));
        }
    }
    public void onPref(View v){
        Intent i = new Intent (this, PrefActivity.class);
        i.putExtra("Idioma",local);
        i.putExtra("cont",cont);
        finish();
        startActivity(i);
    }
    private void cambiarColor(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String asignaturapreferida = prefs.getString("colorpref","blanco");
        ConstraintLayout cLay = findViewById(R.id.layCons);
        if (asignaturapreferida.equalsIgnoreCase("blanco")){
            cLay.setBackgroundColor(Color.WHITE);
        }else if (asignaturapreferida.equalsIgnoreCase("azul")){
            cLay.setBackgroundColor(Color.BLUE);
        }else {
            cLay.setBackgroundColor(Color.RED);
        }
    }
    private void notificador(String text){
        NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(this, "IdCanal");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("IdCanal", "NombreCanal",
                    NotificationManager.IMPORTANCE_DEFAULT);
            elManager.createNotificationChannel(elCanal);
        }

        Intent i = new Intent("android.intent.CLOSE_ACTIVITY");
        PendingIntent intentEnNot = PendingIntent.getBroadcast(this, 0, i, 0);
        elBuilder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.monstruo_colores_rojo_enfado))
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setContentIntent(intentEnNot)
                .setContentTitle("Monster Clicker")
                .setContentText(text)
                .setVibrate(new long[]{0, 1000, 500, 1000})
                .setAutoCancel(true)
                .addAction(android.R.drawable.star_on,getResources().getString(R.string.notiRendir),intentEnNot);
        elManager.notify(1, elBuilder.build());

    }
    BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("cer","cerrado");
            finish();
            Intent i = new Intent (getApplicationContext(), TopActivity.class);
            i.putExtra("Idioma",local);
            i.putExtra("cont",cont);
            i.putExtra("usuario",usuario);
            startActivity(i);
        }

    };
}