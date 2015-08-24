package dmitrybochkov.minesweeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Class to simplify the communication with the database.

public class DBManager {

    private static final String TABLE_NAME = "MinesweeperScoreTable";
    private static final String DB_NAME = "myDB";
    private static final int DB_VERSION = 1;

    public static final String KEY_ID = "_id";
    public static final String KEY_NICKNAME = "nickname";
    public static final String KEY_SCORE = "score";

    public static final int LIMIT_OF_RECORDS = 10; // How many records will we get in Top.

    private final Context mContext;
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBManager(Context context){
        mContext = context;
    }

    public void openConnection(){
        dbHelper = new DBHelper(mContext);
        db = dbHelper.getWritableDatabase();
    }

    public void closeConnection(){
        if (dbHelper != null)
            dbHelper.close();
    }

    public Cursor getTop10Data() {
        return db.query(TABLE_NAME,
                new String[] {KEY_ID, KEY_NICKNAME, KEY_SCORE}, null, null, null, null,
                KEY_SCORE + " DESC", String.valueOf(LIMIT_OF_RECORDS));
    }

    public void insertDataIntoDB(final String txt, final int num) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NICKNAME, txt);
        cv.put(KEY_SCORE, num);
        db.insert(TABLE_NAME, null, cv);
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                    + KEY_ID + " integer primary key autoincrement, "
                    + KEY_NICKNAME + " text, "
                    + KEY_SCORE + " integer"
                    + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
