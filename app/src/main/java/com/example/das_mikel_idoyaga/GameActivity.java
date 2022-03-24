package com.example.das_mikel_idoyaga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {
    private Locale local;
    private int cont = 0;
    private String usuario;
    private int img = R.drawable.monstruo_colores_rojo_enfado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Al crear la actividad se vincula con  el layout del clicker. Mantendremos el usuario, contador, idioma e imagen
        //por si el usuario quiere girar la pantalla, coge una llamada...
        super.onCreate(savedInstanceState);
        //Filtro usado para saber cuando cerrar una actividad anterior y no tener muchas esta actividad abierta una vez terminada.
        IntentFilter filter = new IntentFilter("android.intent.CLOSE_ACTIVITY");
        registerReceiver(mReceiver, filter);
        //Extras guardados para mantener los atributos necesarios
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            local = (Locale) extras.get("Idioma");
            cont  = (int) extras.get("cont");
            usuario = (String) extras.get("usuario");
            if(extras.get("imagen")!=null){
                img = (int) extras.get("imagen");
            }
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
        setContentView(R.layout.activity_game);
        //Cambiar el color según tus preferencias
        cambiarColor();
        //Mantener imagen y contador o crear uno nuevo
        TextView tvCont = findViewById(R.id.tvCont);
        tvCont.setText(Integer.toString(cont));
        ImageView imgMosn = findViewById(R.id.imgMalo);
        imgMosn.setImageResource(img);



    }
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //Guardar el bundle para que no se pierdan los atributos necesarios para el desarrollo de la actividad.
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("cont",cont);
        savedInstanceState.putString("idioma",local.toString());
        savedInstanceState.putString("usuario",usuario);
        savedInstanceState.putInt("imagen",img);
    }

    public void onIns (View v){
        //Método para desplegar un dialogo con las instrucciones una vez que le des al botón de las instruciones
        DialogFragment dialogoalerta= new InsDialogo();
        dialogoalerta.show(getSupportFragmentManager(), "etiqueta");
    }

    public void onComp(View v){
        //Método utilizado para que el usuario pueda ir compartiendo por mail su progreso en el juego.
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
        //Método para terminar el juego sin haberlo completado dando al botón de rendirse y asi ir a la actividad del TOP
        finish();
        ficheroEsc();
        Intent i = new Intent (getApplicationContext(), TopActivity.class);
        i.putExtra("Idioma",local);
        i.putExtra("cont",cont);
        i.putExtra("usuario",usuario);
        startActivity(i);
    }
    public void onClicker(View v){
        //Método donde se desarrolara el clicker, dependiendo el número de clicks hara una cosa u otra y una vez llegado al número máximo
        //de clicks abrira la actividad del TOP
        cont++;
        TextView tvCont = findViewById(R.id.tvCont);
        ImageView imgMalo = findViewById(R.id.imgMalo);
        TextView tvMuerte = findViewById(R.id.tvMuerte);
        tvCont.setText(Integer.toString(cont));
        if(cont==10){
            tvMuerte.setText(R.string.tvMuerte);
            notificador(getResources().getString(R.string.rendirse10));
        }
        if(cont==11){
            tvMuerte.setText("");
            img = R.drawable.monstruo1;
            imgMalo.setImageResource(img);

        }
        if(cont==20){
            tvMuerte.setText(R.string.tvMuerte);
            notificador(getResources().getString(R.string.rendirse10));
        }
        if(cont==21){
            tvMuerte.setText("");
            img = R.drawable.monstruo2;
            imgMalo.setImageResource(img);

        }
        if(cont==30){
            tvMuerte.setText(R.string.tvMuerte);
            notificador(getResources().getString(R.string.rendirse10));
        }
        if(cont==31){
            tvMuerte.setText("");
            img = R.drawable.monstruo3;
            imgMalo.setImageResource(img);

        }
        if(cont==40){
            tvMuerte.setText(R.string.tvMuerte);
            notificador(getResources().getString(R.string.rendirse10));
        }
        if(cont==41){
            tvMuerte.setText("");
            img = R.drawable.mostruo4;
            imgMalo.setImageResource(img);

        }
        if(cont==50){
            tvMuerte.setText(R.string.tvMuerte);
            notificador(getResources().getString(R.string.rendirse10));
        }
        if(cont==51){
            tvMuerte.setText("");
            img = R.drawable.mosntruo5;
            imgMalo.setImageResource(img);

        }
        if(cont==60){
            tvMuerte.setText(R.string.tvVictoria);
        }
        if(cont==61){
            finish();
            ficheroEsc();
            Intent i = new Intent (getApplicationContext(), TopActivity.class);
            i.putExtra("Idioma",local);
            i.putExtra("cont",cont);
            i.putExtra("usuario",usuario);
            startActivity(i);

        }





    }
    public void onPref(View v){
        //Método definido para abrir la actividad de las preferencias cuando le das al botón de la estrellita
        Intent i = new Intent (this, PrefActivity.class);
        i.putExtra("Idioma",local);
        i.putExtra("cont",cont);
        i.putExtra("usuario",usuario);
        i.putExtra("imagen",img);
        finish();
        startActivity(i);
    }
    private void cambiarColor(){
        //Método que una vez definidas las preferencias servira para cambiar el color del fondo
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String asignaturapreferida = prefs.getString("colorpref","blanco");
        ConstraintLayout cLay = findViewById(R.id.layFrag);
        if (asignaturapreferida.equalsIgnoreCase("blanco")){
            cLay.setBackgroundColor(Color.WHITE);
        }else if (asignaturapreferida.equalsIgnoreCase("azul")){
            cLay.setBackgroundColor(Color.BLUE);
        }else {
            cLay.setBackgroundColor(Color.RED);
        }
    }
    private void notificador(String text){
        //Método creado para crear notificaciones de rendición
        NotificationManager elManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(this, "IdCanal");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel elCanal = new NotificationChannel("IdCanal", "NombreCanal",
                    NotificationManager.IMPORTANCE_DEFAULT);
            elManager.createNotificationChannel(elCanal);
        }
        //Creamos un intent para cerrar la actividad en la que estamos y esto le llegara al receiver
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
    public void ficheroEsc(){
        //Método que escribira todas las partidas que se hayan jugado en ese dispositivo con el usuario y puntuación
        //path: data/data/nombrepaqueteaplicación/files/
        try {
            OutputStreamWriter fichero = new OutputStreamWriter(openFileOutput("resultados.txt",
                    Context.MODE_APPEND));
            fichero.write(usuario+":" +cont+"\n");
            fichero.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            //Método que recibira que se ha cerrado la actividad y abrira la actividad de TOP
            finish();
            ficheroEsc();
            Intent i = new Intent (getApplicationContext(), TopActivity.class);
            i.putExtra("Idioma",local);
            i.putExtra("cont",cont);
            i.putExtra("usuario",usuario);
            startActivity(i);
        }

    };
}