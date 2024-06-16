package com.example.vitalitypro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HealthDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "health_log.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "health_logs";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_WATER_DRINKED = "water_drinked";
    private static final String COLUMN_WEIGHT = "weight";

    public HealthDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_WATER_DRINKED + " REAL, " +
                COLUMN_WEIGHT + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void saveHealthLog(String date, double water_drinked, int weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DATE, date);
        values.put(COLUMN_WATER_DRINKED, water_drinked);
        values.put(COLUMN_WEIGHT, weight);

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public HealthLog getHealthLog(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_WATER_DRINKED, COLUMN_WEIGHT},
                COLUMN_DATE + "=?",
                new String[]{date},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            double waterDrinked = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WATER_DRINKED));
            int weight = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT));
            cursor.close();
            return new HealthLog(date, waterDrinked, weight);
        }
        return null;
    }

    public static class HealthLog {
        public String date;
        public double waterDrinked;
        public int weight;

        public HealthLog(String date, double waterDrinked, int weight) {
            this.date = date;
            this.waterDrinked = waterDrinked;
            this.weight = weight;
        }

        @Override
        public String toString() {
            return "HealthLog{" +
                    "date='" + date + '\'' +
                    ", waterDrinked=" + waterDrinked +
                    ", weight=" + weight +
                    '}';
        }
    }
}
