package com.example.vitalitypro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.util.List;

public class FoodDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "food_log.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "food_logs";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_BREAKFAST = "breakfast";
    private static final String COLUMN_LUNCH = "lunch";
    private static final String COLUMN_SNACK = "snack";
    private static final String COLUMN_DINNER = "dinner";

    private Gson gson;

    public FoodDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        gson = new Gson();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_BREAKFAST + " TEXT, " +
                COLUMN_LUNCH + " TEXT, " +
                COLUMN_SNACK + " TEXT, " +
                COLUMN_DINNER + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void saveFoodLog(String date, List<Food> breakfast, List<Food> lunch, List<Food> snack, List<Food> dinner) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_DATE, date);
        values.put(COLUMN_BREAKFAST, gson.toJson(breakfast));
        values.put(COLUMN_LUNCH, gson.toJson(lunch));
        values.put(COLUMN_SNACK, gson.toJson(snack));
        values.put(COLUMN_DINNER, gson.toJson(dinner));

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public FoodLog getFoodLog(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_BREAKFAST, COLUMN_LUNCH, COLUMN_SNACK, COLUMN_DINNER},
                COLUMN_DATE + "=?",
                new String[]{date},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            FoodLog foodLog = new FoodLog(
                    gson.fromJson(cursor.getString(0), List.class),
                    gson.fromJson(cursor.getString(1), List.class),
                    gson.fromJson(cursor.getString(2), List.class),
                    gson.fromJson(cursor.getString(3), List.class)
            );
            cursor.close();
            return foodLog;
        }
        return null;
    }

    public List<Food> getBreakfastForDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        List<Food> breakfastList = null;

        try {
            cursor = db.query(TABLE_NAME,
                    new String[]{COLUMN_BREAKFAST},
                    COLUMN_DATE + "=?",
                    new String[]{date},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String breakfastJson = cursor.getString(0);
                breakfastList = gson.fromJson(breakfastJson, new com.google.gson.reflect.TypeToken<List<Food>>(){}.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return breakfastList;
    }

    public List<Food> getLunchForDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        List<Food> lunchList = null;

        try {
            cursor = db.query(TABLE_NAME,
                    new String[]{COLUMN_LUNCH},
                    COLUMN_DATE + "=?",
                    new String[]{date},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String lunchJson = cursor.getString(0);
                lunchList = gson.fromJson(lunchJson, new com.google.gson.reflect.TypeToken<List<Food>>(){}.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return lunchList;
    }

    public List<Food> getSnackForDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        List<Food> snackList = null;

        try {
            cursor = db.query(TABLE_NAME,
                    new String[]{COLUMN_SNACK},
                    COLUMN_DATE + "=?",
                    new String[]{date},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String snackJson = cursor.getString(0);
                snackList = gson.fromJson(snackJson, new com.google.gson.reflect.TypeToken<List<Food>>(){}.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return snackList;
    }

    public List<Food> getDinnerForDate(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        List<Food> dinnerList = null;

        try {
            cursor = db.query(TABLE_NAME,
                    new String[]{COLUMN_DINNER},
                    COLUMN_DATE + "=?",
                    new String[]{date},
                    null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String dinnerJson = cursor.getString(0);
                dinnerList = gson.fromJson(dinnerJson, new com.google.gson.reflect.TypeToken<List<Food>>(){}.getType());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return dinnerList;
    }





    public static class FoodLog {
        public List<Food> breakfast;
        public List<Food> lunch;
        public List<Food> snack;
        public List<Food> dinner;

        public FoodLog(List<Food> breakfast, List<Food> lunch, List<Food> snack, List<Food> dinner) {
            this.breakfast = breakfast;
            this.lunch = lunch;
            this.snack = snack;
            this.dinner = dinner;
        }
    }
}

