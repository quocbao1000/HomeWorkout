package vn.edu.hcmut.phatdo.finalday;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by peank on 19/01/2018.
 */

public class MyDatabase extends SQLiteOpenHelper {
    public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, name, factory, version);
    }

    public void queryData(String command){
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(command);
    }

    public Cursor getData(String command){
        return getReadableDatabase().rawQuery(command,null);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
