package alireza.farghadani.blueberry.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class databasehelp_class extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "classes.sqlite";

    public static final String TABLE_NAME_class = "class";
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String LESSON = "LESSON";



    public static final String TABLE_NAME_students = "students";
    public static final String student_ID = "ID";
    public static final String student_NAME = "NAME";
    public static final String student_family = "FAMILY";
    public static final String student_point = "points";
    public static final String student_present = "present";
    public static final String class_ID = "classID";
    public static final String Des = "Des";

    public static final String TABLE_NAME_PointTopic = "PointTopic";
    public static final String point_ID = "ID";
    public static final String point_classID = "classID";
    public static final String point_TOPIC = "TOPIC";
    public static final String point_DES = "DES";



    public databasehelp_class(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_NAME_class +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,LESSON TEXT)");
        db.execSQL("create table "+ TABLE_NAME_students +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,FAMILY TEXT,points TEXT,present TEXT,classID INTEGER,Des TEXT)");
        db.execSQL("create table "+TABLE_NAME_PointTopic+" (ID INTEGER PRIMARY KEY AUTOINCREMENT,TOPIC TEXT,DES TEXT,classID INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS  "+ TABLE_NAME_class);
        db.execSQL("DROP TABLE IF EXISTS  "+ TABLE_NAME_students);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_PointTopic);
        onCreate(db);
    }

    public boolean incret_class_db(String name,String lesson){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(LESSON,lesson);
        long result = db.insert(TABLE_NAME_class,null,contentValues);
        return result != -1;
    }

    public boolean incret_student_db(String name,String family,int class_id,String des){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(student_NAME,name);
        contentValues.put(student_family,family);
        contentValues.put(student_point,"{}");
        contentValues.put(student_present,"{}");
        contentValues.put(class_ID,class_id);
        contentValues.put(Des,des);

        long result = db.insert(TABLE_NAME_students,null,contentValues);
        return result != -1;
    }


    public boolean incret_PointTopic_db(String name,String Des,int classid){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(point_TOPIC,name);
        contentValues.put(point_DES,Des);
        contentValues.put(point_classID,classid);
        long result = db.insert(TABLE_NAME_PointTopic,null,contentValues);
        return result != -1;
    }

    public Cursor get_classs_db(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME_class, null );
        return res;
    }

    public Cursor get_students_db(int class_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME_students+" WHERE classID="+class_id, null );
        return res;
    }


    public Cursor get_PointTopic_db(int class_id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME_PointTopic+" WHERE classID="+class_id, null );
        return res;
    }

    public Cursor get_student_withID_db(int ID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME_students+" WHERE ID="+ID, null );
        return res;
    }

    public boolean update_class_db(int ID_edit,String new_name , String new_lesson){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID,String.valueOf(ID_edit));
        contentValues.put(NAME,new_name);
        contentValues.put(LESSON,new_lesson);

        db.update(TABLE_NAME_class , contentValues ,"ID = ?" ,new String[] {String.valueOf(ID_edit)});
        return true;
    }

    public boolean update_PointTopic_db(int ID_edit,String new_name , String new_Des){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(point_ID,String.valueOf(ID_edit));
        contentValues.put(point_TOPIC,new_name);
        contentValues.put(point_DES,new_Des);

        db.update(TABLE_NAME_PointTopic , contentValues ,"ID = ?" ,new String[] {String.valueOf(ID_edit)});
        return true;
    }

    public boolean update_student_db(int ID_edit,String new_name , String new_family,String new_point,String new_present,String des){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(student_ID,String.valueOf(ID_edit));

        if (!new_name.equals("")) {
            contentValues.put(student_NAME, new_name);
        }
        if (!new_family.equals("")) {
            contentValues.put(student_family, new_family);
        }
        if (!new_point.equals("")){
            contentValues.put(student_point,new_point);
        }
        if (!new_present.equals("")) {
            contentValues.put(student_present,new_present);
        }
        if (des != null) {
            contentValues.put(Des, des);
        }
        db.update(TABLE_NAME_students , contentValues ,"ID = ?" ,new String[] {String.valueOf(ID_edit)});
        return true;
    }


    public Integer deleteClassData(int ID_delete){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_students,"classID = ?",new String[] {String.valueOf(ID_delete)});
        return db.delete(TABLE_NAME_class,"ID = ?",new String[] {String.valueOf(ID_delete)});

    }

    public Integer deletePointTopicData(int ID_delete){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_PointTopic,"ID = ?",new String[] {String.valueOf(ID_delete)});

    }

    public Integer deleteStudentData(int ID_delete){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME_students,"ID = ?",new String[] {String.valueOf(ID_delete)});

    }




}
