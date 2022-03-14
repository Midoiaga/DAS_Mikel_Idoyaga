package com.example.das_mikel_idoyaga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private Locale local;
    private MiBD GestorDB = new MiBD (this, "MiDB", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Al crear la actividad se vincula con  el layout que sirve para registrarse y el idioma que se ha seleccionado anteriormente
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_register);
    }
    //Metodo para que al pulsar en Registrase compare las contraseñas y si la existencia del nombre de usuario
    //si cumple las 2 condiciones se creare el nuevo usuario
    public void onRegistrar(View view){
        EditText etUsuario = findViewById(R.id.etUsuarioRegis);
        String usuario = etUsuario.getText().toString();
        EditText etContraseña = findViewById(R.id.epContraseñaRegis);
        String contraseña = etContraseña.getText().toString();
        EditText etContraseñaConfir = findViewById(R.id.epContraseñaConfir);
        String contraseñaConfir = etContraseñaConfir.getText().toString();
        if (contraseña.equalsIgnoreCase(contraseñaConfir)){
            if(!GestorDB.existe(usuario)){
                GestorDB.crearUsuario(usuario,contraseña);
            }
        }
    }
}