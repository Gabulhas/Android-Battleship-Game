package guilherme.battlleship.other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class database_connection extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "leaderboard";
    private static final String TABLE_NAME = "scores";
    private static final String COLUMN1 = "id";
    private static final String COLUMN2 = "player";
    private static final String COLUMN3 = "difficulty";
    private static final String COLUMN4 = "points";
    private static final String COLUMN5 = "plays";
    private static final String COLUMN6 = "left_ships";


    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME +
                    " ( " +
                    COLUMN1 + " INT PRIMARY KEY, " +
                    COLUMN2 + " VARCHAR(60), " +
                    COLUMN3 + " VARCHAR(7), " +
                    COLUMN4 + " INT, " +
                    COLUMN5 + " INT, " +
                    COLUMN6 + " INT " +
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

        while(res.moveToNext()){
            res.getString(res.get)
            Log.d("DB_OUT", "debugRows: " + res.getString(res.getColumnIndex(TABLE_NAME)));
            res.moveToNext();
        }
    }
}
