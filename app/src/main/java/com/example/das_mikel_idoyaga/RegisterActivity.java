package com.example.das_mikel_idoyaga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private Locale local;
    private MiBD GestorDB = new MiBD (this, "MiDB", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Al crear la actividad se vincula con  el layout que sirve para registrarse y el idioma que se ha seleccionado anteriormente
        super.onCreate(savedInstanceState);
        //Extras guardados para mantener los atributos necesarios
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            local = (Locale) extras.get("Idioma");
        }
        //Definir idioma
        Locale.setDefault(local);
        Configuration configuration = getBaseContext().getResources().getConfiguration();
        configuration.setLocale(local);
        configuration.setLayoutDirection(local);
        Context context = getBaseContext().createConfigurationContext(configuration);
        getBaseContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        setContentView(R.layout.activity_register);
    }

    public void onRegistrar(View view){
        //Metodo para que al pulsar en Registrase compruebe que los campos estan rellenados, compare las contraseñas y la existencia del nombre de usuario
        //si cumple las 4 condiciones se creare el nuevo usuario y empezara la actividad del clicker
        EditText etUsuario = findViewById(R.id.etUsuarioRegis);
        String usuario = etUsuario.getText().toString();
        EditText etContraseña = findViewById(R.id.epContraseñaRegis);
        String contraseña = etContraseña.getText().toString();
        EditText etContraseñaConfir = findViewById(R.id.epContraseñaConfir);
        String contraseñaConfir = etContraseñaConfir.getText().toString();
        if(usuario.equalsIgnoreCase("") || contraseña.equalsIgnoreCase("")) {
            hacerToast(R.string.tFaltaUsuCon);
        }else {
            if (contraseña.equalsIgnoreCase(contraseñaConfir)) {
                if (!GestorDB.existe(usuario)) {
                    GestorDB.crearUsuario(usuario, contraseña);
                    Intent i = new Intent(this, GameActivity.class);
                    i.putExtra("Idioma", local);
                    i.putExtra("cont", 0);
                    i.putExtra("usuario", usuario);
                    finish();
                    startActivity(i);
                } else {
                    hacerToast(R.string.tUsuarioCogido);
                }
            } else {
                hacerToast(R.string.tContraseñasDiferentes);
            }
        }
    }

    public void hacerToast(int s){
        //Metodo de apoyo para hacer notificaciones del tipo toast
        Toast toast= Toast.makeText(this,s,Toast.LENGTH_SHORT);
        toast.show();
    }
}