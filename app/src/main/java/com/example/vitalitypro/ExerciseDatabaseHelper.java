package com.example.vitalitypro;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ExerciseDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "exercise_log.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "exercise_logs";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_EXERCISES = "exercises";

    private Gson gson;

    public ExerciseDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        gson = new Gson();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_EXERCISES + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void saveExerciseLog(String date, List<Exercise> exercises) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        Type listType = new TypeToken<List<Exercise>>() {}.getType();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_EXERCISES, gson.toJson(exercises, listType));

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public ExerciseLog getExerciseLog(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,
                new String[]{COLUMN_EXERCISES},
                COLUMN_DATE + "=?",
                new String[]{date},
                null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
            Type listType = new TypeToken<List<Exercise>>() {}.getType();
            ExerciseLog exerciseLog = new ExerciseLog(
                    gson.fromJson(cursor.getString(0), listType)
            );
            cursor.close();
            return exerciseLog;
        }
        return null;
    }

    public static class ExerciseLog {
        public List<Exercise> exercises;

        public ExerciseLog(List<Exercise> exercises) {
            this.exercises = exercises;
        }
    }
}

