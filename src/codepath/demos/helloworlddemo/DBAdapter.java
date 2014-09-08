package codepath.demos.helloworlddemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
 
public class DBAdapter {
    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_STATUS = "status";
    static final String KEY_DONEDATE = "donedate";
    static final String TAG = "DBAdapter";
 
    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "tasks";
    static final int DATABASE_VERSION = 1;
 
    static final String DATABASE_CREATE =
        "create table tasks (_id integer primary key autoincrement, "
        + "name text not null, status text not null, donedate integer);";
 
    final Context context;
 
    DatabaseHelper DBHelper;
    SQLiteDatabase db;
    
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }
 
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
 
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
 
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS tasks");
            onCreate(db);
        }
    }
 
    //---opens the database---
    public DBAdapter open() throws SQLException 
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }
 
    //---closes the database---
    public void close() 
    {
      DBHelper.close();
    }
 
    //---insert a task into the database---
    public long insertTask(String name, String status, int donedate) 
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_STATUS, status);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }
 
    //---deletes a particular task---
    public boolean deleteTask(long rowId) 
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }
 
    //---retrieves all the tasks---
    public Cursor getAllTasks()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_NAME,
                KEY_STATUS, KEY_DONEDATE}, null, null, null, null, null);
    }
 
    //---retrieves a particular task---
    public Cursor getTask(long rowId) throws SQLException 
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                KEY_NAME, KEY_STATUS, KEY_DONEDATE}, KEY_ROWID + "=" + rowId, null,
                null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
 
    //---updates a task---
    public boolean updateTask(long rowId, String name, String status, int donedate) 
    {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_STATUS, status);
        args.put(KEY_DONEDATE, donedate);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }
}  

