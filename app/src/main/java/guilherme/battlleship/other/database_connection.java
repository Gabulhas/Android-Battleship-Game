package guilherme.battlleship.other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class database_connection extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "leaderboard";
    public static final String TABLE_NAME = "scores";
    public static final String COLUMN1 = "id";
    public static final String COLUMN2 = "player";
    public static final String COLUMN3 = "difficulty";
    public static final String COLUMN4 = "points";
    public static final String COLUMN5 = "plays";
    public static final String COLUMN6 = "left_ships";


    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +
                    " ( " +
                    COLUMN1 + " INTEGER PRIMARY KEY, " +
                    COLUMN2 + " VARCHAR(60), " +
                    COLUMN3 + " VARCHAR(7), " +
                    COLUMN4 + " INTEGER, " +
                    COLUMN5 + " INTEGER, " +
                    COLUMN6 + " INTEGER " +
                    " );";

    private static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME + " ;";


    public database_connection(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void insertScore(String player, int points, int plays, int left_ships, String difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN2, player);
        contentValues.put(COLUMN3, difficulty);
        contentValues.put(COLUMN4, points);
        contentValues.put(COLUMN5, plays);
        contentValues.put(COLUMN6, left_ships);
        db.insert(TABLE_NAME, null, contentValues);

    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public void debugRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        res.moveToFirst();

        while (res.moveToNext()) {


            Log.d("DB_OUT", "debugRows: " + score.fromCursor(res).toString());
            res.moveToNext();
        }
    }
}
