package com.project.cookbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GiveOrGet {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "person_name";
    public static final String KEY_AMOUNT = "amount";

    private static final String DATABASE_NAME = "GiveOrGetDB";
    private static final String DATABASE_TABLE = "moneyTable";
    private static int DATABASE_VERSION = 7;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public long createEntry(String name, String amt, int id) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWID, id);
        cv.put(KEY_NAME, name);
        cv.put(KEY_AMOUNT, amt);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }

    public String getData() {
        String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_AMOUNT };
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        int iAmt = c.getColumnIndex(KEY_AMOUNT);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = result + c.getString(iRow) + " "
                    + c.getString(iName) + " paid " + c.getString(iAmt) + "\n";
        }

        return result;
    }

    public String getData(int l) throws SQLException {
        String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_AMOUNT };
        //String query = "SELECT " + KEY_NAME + " FROM " + DATABASE_TABLE + " WHERE " + KEY_ROWID + "=" + l;
        //Cursor c = ourDatabase.rawQuery(query,null);
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
        String name = "Not Found";
        if (c != null) {
            c.moveToFirst();
            name = c.getString(c.getColumnIndex(KEY_NAME));
        }
        return name;
    }

    public String getName(int l) throws SQLException {
        String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_AMOUNT };
        String query = "SELECT " + KEY_NAME + " FROM " + DATABASE_TABLE + " WHERE " + KEY_ROWID + " = " + l;
        Cursor c = ourDatabase.rawQuery(query,null);
        //Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
        String name = "Not Found";
        if (c != null) {
            c.moveToFirst();
            name = c.getString(c.getColumnIndex(KEY_NAME));
        }
        return name;
    }

    public double getAmount(int l) throws SQLException{
        String[] columns = new String[] { KEY_ROWID, KEY_NAME, KEY_AMOUNT };
        String query = "SELECT " + KEY_AMOUNT + " FROM " + DATABASE_TABLE + " WHERE " + KEY_ROWID + "=" + l;
        Cursor c = ourDatabase.rawQuery(query,null);
        //Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
        double amt = 0.0;
        if (c != null) {
             c.moveToFirst();
             amt = c.getDouble(c.getColumnIndex(KEY_AMOUNT));
        }
        return amt;
    }

    public void updateEntry (int lRow, String db_modify_name, double db_modify_amt) throws SQLException {
        ContentValues cv_update = new ContentValues();

        cv_update.put(KEY_NAME, db_modify_name);
        cv_update.put(KEY_AMOUNT, db_modify_amt);
        ourDatabase.update(DATABASE_TABLE, cv_update, KEY_ROWID + "=" + lRow, null);

    }

    public void deleteEntry(long lRow1) throws SQLException{
        ourDatabase.delete(DATABASE_TABLE, KEY_ROWID + "=" + lRow1, null);
    }
    public void deleteAllEntry() throws SQLException{
        ourDatabase.delete(DATABASE_TABLE, null, null);
    }

    private static class DbHelper extends SQLiteOpenHelper {

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                            KEY_NAME + " TEXT NOT NULL, " +
                            KEY_AMOUNT + " TEXT NOT NULL);"
            );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }

    }

    public GiveOrGet(Context c) {
        ourContext = c;
    }

    public GiveOrGet open() throws SQLException {
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public GiveOrGet close() {
        ourHelper.close();
        return this;
    }


}
