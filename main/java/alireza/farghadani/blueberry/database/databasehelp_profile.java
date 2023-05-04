package alireza.farghadani.blueberry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class databasehelp_profile extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "profile.sqlite";

    public static final String TABLE_NAME_class = "profile";
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String FAMILY_NAME = "FAMILY_NAME";


    public databasehelp_profile(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME_class +" (ID INTEGER,NAME TEXT,FAMILY_NAME TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS  "+ TABLE_NAME_class);
        onCreate(db);
    }

    public boolean incret_profile_db(String name,String family_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,"0");
        contentValues.put(NAME,name);
        contentValues.put(FAMILY_NAME,family_name);
        long result = db.insert(TABLE_NAME_class,null,contentValues);
        return result != -1;
    }

    public Cursor get_profile_db(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME_class, null );
        return res;
    }

    public boolean update_profile_db(int ID_edit,String new_name , String new_family){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,String.valueOf(ID_edit));
        contentValues.put(NAME,new_name);
        contentValues.put(FAMILY_NAME,new_family);
        db.update(TABLE_NAME_class , contentValues ,"ID = ?" ,new String[] {String.valueOf(ID_edit)});
        return true;
    }


    public Integer deleteprofileData(int ID_delete){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_class,"ID = ?",new String[] {String.valueOf(ID_delete)});

    }


}
