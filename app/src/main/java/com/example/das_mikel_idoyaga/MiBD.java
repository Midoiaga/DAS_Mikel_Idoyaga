package com.example.das_mikel_idoyaga;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

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
        bd.execSQL("INSERT INTO Usuarios ('Usuario','Contraseña') VALUES ('"+usuario+"','"+contraseña+"')");
        bd.close();
    }

}
