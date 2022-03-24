package com.example.das_mikel_idoyaga;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptadorListView extends BaseAdapter {
    private Context contexto;
    private LayoutInflater inflater;
    private String[] datos;
    private int[] imagenes;
    private String[] texto;

    //Getters
    @Override
    public int getCount() {
        return datos.length;
    }

    @Override
    public Object getItem(int i) {
        return datos[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Metodo usado para definir el layout y los elementos que va a tener cada fila de la ListView
        view=inflater.inflate(R.layout.fila,null);
        TextView nombre= (TextView) view.findViewById(R.id.tvUsuario);
        ImageView img=(ImageView) view.findViewById(R.id.imgtop);
        TextView tvPuntuacion= (TextView) view.findViewById(R.id.tvPuntuacion);
        nombre.setText(datos[i]);
        img.setImageResource(imagenes[i]);
        tvPuntuacion.setText(texto[i]);
        return view;
    }

    public AdaptadorListView(Context pcontext, String[] pdatos, int[] pimagenes, String[] pTexto)
    {
        //Contructor donde definimos que valor corresponde a cada atributo
        contexto = pcontext;
        datos = pdatos;
        imagenes=pimagenes;
        texto=pTexto;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
}
