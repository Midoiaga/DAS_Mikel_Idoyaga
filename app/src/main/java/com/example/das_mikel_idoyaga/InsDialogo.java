package com.example.das_mikel_idoyaga;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class InsDialogo extends DialogFragment {
    private String es = "Clicka en los monstruos hasta derrotar a todos y asi salvar el destino de la humanidad. Y recuerda pase lo que pase nunca te rindas. P.D. Puedes compartir tu progreso con el boton COMPARTIR";
    private String en = "Click to kill all the monster to save the destiny of humanity. And remember never surrender. P.D. You can share your progress with the  SHARE button";
    private String setMensaje;
    public InsDialogo(String s) {
        if ("es".equalsIgnoreCase(s)){
            setMensaje = es;
        }
        else {
            setMensaje = en;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("INSTRUCCIONES");
        builder.setMessage(setMensaje);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return builder.create();
    }

}