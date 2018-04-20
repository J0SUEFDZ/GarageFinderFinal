package rodrigojosuetec.garage_finder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "dbgaragefinder.db", null, 1);
    }

    SQLiteDatabase db;

    private static final String CREATE_USER = "create table users (id text primary key not null , " +
            "nombre text not null , correo text not null, password text not null, telefono text not null, favoritos text);";
    private static final String CREATE_AUTO = "create table autos (placa integer primary key not null , " +
            "marca text not null , modelo text not null, estilo text not null, combustible text not null);";
    private static final String CREATE_TALLER = "create table taller(ID text primary key not null,"+
            " name text, telefono text, email text, descripcion text, latitud real, longitud real, admin text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_AUTO);
        db.execSQL(CREATE_TALLER);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS autos");
        db.execSQL("DROP TABLE IF EXISTS taller");
        this.onCreate(db);
    }

    public void insertUserSingleton(UserSingleton us){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            String query = "select * from users";
            Cursor cursor = db.rawQuery(query, null);
            int count = cursor.getCount();

            values.put("id", count);
            values.put("nombre", us.getNombre());
            values.put("corre", us.getCorreo());
            values.put("password", us.getPass());
            values.put("telefono", us.getTelefono());
            ArrayList<Taller> favoritos = new ArrayList<>();
            JSONObject json = new JSONObject();
            json.put("uniqueArrays", new JSONArray(favoritos));
            String arrayList = json.toString();
            values.put("favoritos", arrayList);

            db.insert("users", null, values);
        }
        catch (Exception e) {
            Log.d("Error", "No se pudo realizar la insercion de usuarios");
        }
        db.close();
    }
    public boolean InsertaTaller(Taller t){
        db = this.getWritableDatabase();
        ContentValues Parametros = new ContentValues();
        Cursor cursor = db.rawQuery("select * from taller", null);
        int count = cursor.getCount();

        try {
            Parametros.put("ID", count);
            Parametros.put("name", t.getNombre());
            Parametros.put("telefono", t.getTelefono());
            Parametros.put("email", t.getEmail());
            Parametros.put("descripcion", t.getDescripcion());
            Parametros.put("latitud",t.getLatitud());
            Parametros.put("longitud",t.getLongitud());

            db.insert("taller", null, Parametros);

            db.close();
            return true;

        } catch (Exception e) {
            Log.d("Error", "No se pudo realizar la insercion de talleres");
        }
        return false;
    }
    public Taller ConsultarTaller(int id){
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(

                "select * from taller where id="+id, null);
        if (cursor.moveToFirst()){
            return new Taller(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),Double.parseDouble(cursor.getString(7)),Double.parseDouble(cursor.getString(8)),"Admin");
        }
        return null;
    }

    public String searchPass(String correo){
        db = this.getReadableDatabase();
        String query = "select correo, password from users";
        Cursor cursor = db.rawQuery(query, null);
        String mail, pass;
        pass = "No encontrado";
        if(cursor.moveToFirst()){
            do{
                mail = cursor.getString(0);
                if (mail.equals(correo)){
                    pass = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }
        return pass;
    }
}
