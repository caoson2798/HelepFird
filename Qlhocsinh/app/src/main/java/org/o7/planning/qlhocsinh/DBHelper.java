package org.o7.planning.qlhocsinh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {
    Context context;

    private static final String DATABASE_NAME ="StudentsDB";
    private static final int DATABASE_VERSION =1;
    private static final String TABLE_NAME="Student";

    private static final String KEY_ID="id";
    private static final String KEY_NAME="name";
    private static final String KEY_ADDRESS="address";
    private static final String KEY_GENDER="gender";

    public DBHelper(Context context){
        super(context , DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_student_table = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s INT)", TABLE_NAME, KEY_ID, KEY_NAME, KEY_ADDRESS, KEY_GENDER);
        db.execSQL(create_student_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db , int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }
    public ArrayList<Student> getAllStudent(){
        ArrayList<Student> array_list= new ArrayList<>();
        SQLiteDatabase db=this.getWritableDatabase();
        String sql="SELECT * FROM Student";

        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            int gender =cursor.getInt(3);

             array_list.add(new Student(id,name,address,gender));
        }
        return array_list;
    }
    public void insertStudent( Student student ){
        SQLiteDatabase db;

        db=context.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE, null);
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", student.getId());
        contentValues.put("name", student.getName());
        contentValues.put("address", student.getAddress());
        contentValues.put("gender", student.getGender());

        if(db.insert("Student", null, contentValues) > 0){
            Toast.makeText(context,"Successful", Toast.LENGTH_SHORT).show();

        }
    }

    public void updateStudent(Student student){
        SQLiteDatabase db;
                db=context.openOrCreateDatabase(DATABASE_NAME,context.MODE_PRIVATE,null);

        ContentValues contentValues= new ContentValues();
        contentValues.put("name", student.getName());
        contentValues.put("name", student.getAddress());
        contentValues.put("gender", student.getGender());

        if(db.update ("Student", contentValues,"id=" + student.getId(),null) >0 ){
            Toast.makeText(context, "successful", Toast.LENGTH_SHORT).show();
        }

    }

    public void deleteStudent(Student student){
        SQLiteDatabase db;
        db = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE,null);

        if( db.delete("Student", "id=" + student.getId(), null) >0){
            Toast.makeText(context, " success", Toast.LENGTH_SHORT).show();
        }
    }


}
