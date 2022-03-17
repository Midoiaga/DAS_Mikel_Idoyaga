package com.example.das_mikel_idoyaga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;


//MainActivity
public class LoginActivity extends AppCompatActivity {
    private Locale local = new Locale("es");
    private MiBD GestorDB = new MiBD (this, "MiDB", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Al crear la actividad se vincula con  el layout que sirve para iniciar sesion y con el idioma
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
        //Metodo para cambiar de idioma dependiendo a que idioma clickes
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
    public void onAqui(View view){
        Intent i = new Intent (this, RegisterActivity.class);
        i.putExtra("Idioma",local);
        startActivity(i);

    }
    public void onEntrar(View view){
        Log.d("hola","Entrar");
        EditText etUsuario = findViewById(R.id.etUsuario);
        String usuario = etUsuario.getText().toString();
        EditText etContraseña = findViewById(R.id.epContraseña);
        String contraseña = etContraseña.getText().toString();
        if(GestorDB.coincide(usuario,contraseña)){
            Intent i = new Intent (this, GameActivity.class);
            i.putExtra("Idioma",local);
            startActivity(i);
        }
        else{
            hacerToast(R.string.tUsuarioOContraseñaMal);
        }
    }
    public void hacerToast(int s){
        Toast toast= Toast.makeText(this,s,Toast.LENGTH_SHORT);
        toast.show();
    }
}