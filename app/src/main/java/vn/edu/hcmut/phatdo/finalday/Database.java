package vn.edu.hcmut.phatdo.finalday;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

/**
 * Created by peank on 06/01/2018.
 */

public class Database extends SQLiteOpenHelper {
    //Auto generated constructor
    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Function to interact with database with no result return
    public void queryData(String command){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(command);
    }

    public void insertImage(byte[] img, String date, String phper){
        SQLiteDatabase database = getWritableDatabase();
        String sql = "INSERT INTO Images VALUES(?,?,?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindBlob(1,img);
        stmt.bindString(2,date);
        stmt.bindString(3,phper);
        stmt.executeInsert();
    }
    //Function to interact with database with return cursor
    public Cursor getData(String command) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(command,null);
        return cursor;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
