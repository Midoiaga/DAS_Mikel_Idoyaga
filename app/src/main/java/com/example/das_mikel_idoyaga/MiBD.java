package com.example.das_mikel_idoyaga;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Locale;

public class MiBD extends SQLiteOpenHelper {
    //Constructor
    public MiBD(@Nullable Context context, @Nullable String name,
                @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    //Crear la case de datos con una tabla llamada Usuarios con las columnas Usuario, Contraseña y Puntuacion
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Usuarios ('Usuario' VARCHAR(255) PRIMARY KEY NOT NULL, 'Contraseña' VARCHAR(255),'Puntuacion' INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public boolean coincide(String usuario,String contraseña){
        boolean resultado = false;
        int cont = 0;
        SQLiteDatabase bd = getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT Usuario FROM Usuarios WHERE Usuario='"+usuario+"' AND Contraseña='"+contraseña+"'", null);
        while (c.moveToNext()){
            Log.d("usu",c.getString(0));
            cont++;
        }
        if (cont>=1){
            resultado = true;
        }
        c.close();
        bd.close();
        return resultado;
    }
    //Metodo para saber si existe un usuario con el mismo nombre en la base de datos
    public boolean existe(String usuario){
        boolean resultado = false;
        int cont = 0;
        SQLiteDatabase bd = getWritableDatabase();
        Cursor c = bd.rawQuery("SELECT Usuario FROM Usuarios WHERE Usuario='"+usuario+"'", null);
        while (c.moveToNext()){
            Log.d("usu",c.getString(0));
            cont++;
        }
        if (cont>=1){
            resultado = true;
        }
        c.close();
        bd.close();
        return resultado;
    }
    //Metodo para meter un nuevo usuario en la base de datos
    public void crearUsuario(String usuario, String contraseña){
        SQLiteDatabase bd = getWritableDatabase();
        bd.execSQL("INSERT INTO Usuarios ('Usuario','Contraseña','Puntuacion') VALUES ('"+usuario+"','"+contraseña+"',0)");
        bd.close();
    }
    public ArrayList<UsuarioTop> top3(){
        ArrayList<UsuarioTop> arrayUsu= new ArrayList<UsuarioTop>();
        int cont = 0;
        SQLiteDatabase bd = getWritableDatabase();
        for(int i = 0; i<3;i++){
            arrayUsu.add(new UsuarioTop("","0"));
        }
        Cursor c = bd.rawQuery("SELECT Usuario, Puntuacion FROM Usuarios ORDER BY Puntuacion DESC LIMIT 3", null);
        while (c.moveToNext()){
            Log.d("usu",c.getString(0));
            Log.d("usu", String.valueOf(c.getInt(1)));
            arrayUsu.get(cont).setUsuario(c.getString(0));
            arrayUsu.get(cont).setPuntuacion(String.valueOf(c.getInt(1)));
            cont++;
        }

        bd.close();
        return arrayUsu;
    }
    public void updateUsuario(String usuario, int puntuacion){
        SQLiteDatabase bd = getWritableDatabase();
        Log.d("hola",usuario);
        Log.d("hola", String.valueOf(puntuacion));
        bd.execSQL("UPDATE Usuarios SET Puntuacion= "+puntuacion+" WHERE  Puntuacion < "+puntuacion+" AND Usuario = '"+usuario+"'");
        bd.close();
    }

}
