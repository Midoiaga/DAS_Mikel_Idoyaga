package com.example.das_mikel_idoyaga;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;


import androidx.preference.PreferenceFragmentCompat;

import java.util.Locale;

public class Preferencias extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private Locale locale = Locale.getDefault();

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        //Al crear las preferencias vincularlo con su xml correspondiente y guardar el idioma
        if (bundle!= null)
        {
            locale = new Locale(bundle.getString("idioma"));
        }
        Locale.setDefault(locale);
        Configuration configuration = getContext().getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        Context context = getContext().createConfigurationContext(configuration);
        getContext().getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
        addPreferencesFromResource(R.xml.pref_config);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Guardar el bundle para que no se pierdan el idioma.
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("idioma",locale.toString());
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
    }

    @Override
    public void onResume() {
        //Método para guardar las preferencias
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onPause() {
        //Método para guardar las preferencias
        super.onPause();
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }
}
