package com.example.vitalitypro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabaseHandler extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "vitalityproDB";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";

    // User Table Columns
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_GOAL = "goal";
    private static final String KEY_ACTIVITY_LEVEL = "activity_level";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_GOAL_WEIGHT = "goal_weight";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_AGE = "age";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_WEIGHT_CHANGE_GOAL = "weight_change_goal";
    private static final String KEY_CALORIES_EATEN = "calories_eaten";
    private static final String KEY_DAILY_CALORIE_INTAKE = "daily_calorie_intake";

    public UserDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableQuery = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_USERNAME + " TEXT," +
                KEY_PASSWORD + " TEXT," +
                KEY_GOAL + " TEXT," +
                KEY_ACTIVITY_LEVEL + " TEXT," +
                KEY_WEIGHT + " INTEGER," +
                KEY_GOAL_WEIGHT + " INTEGER," +
                KEY_HEIGHT + " INTEGER," +
                KEY_AGE + " INTEGER," +
                KEY_GENDER + " TEXT," +
                KEY_WEIGHT_CHANGE_GOAL + " REAL," +
                KEY_CALORIES_EATEN + " INTEGER," +
                // Add the new column
                KEY_DAILY_CALORIE_INTAKE + " INTEGER" +
                ")";
        db.execSQL(createUserTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_GOAL, user.getGoal());
        values.put(KEY_ACTIVITY_LEVEL, user.getActivityLevel());
        values.put(KEY_WEIGHT, user.getWeight());
        values.put(KEY_GOAL_WEIGHT, user.getGoalWeight());
        values.put(KEY_HEIGHT, user.getHeight());
        values.put(KEY_AGE, user.getAge());
        values.put(KEY_GENDER, user.getGender());
        values.put(KEY_WEIGHT_CHANGE_GOAL, user.getWeightChangeGoal());
        values.put(KEY_CALORIES_EATEN, user.getCaloriesEaten());
        values.put(KEY_DAILY_CALORIE_INTAKE, user.getDailyCalorieIntake());

        return db.insert(TABLE_USERS, null, values);
    }

    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, null, KEY_USERNAME + "=?", new String[]{username}, null, null, null);

        User user = null;

        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getString(cursor.getColumnIndex(KEY_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(KEY_GOAL)),
                    cursor.getString(cursor.getColumnIndex(KEY_ACTIVITY_LEVEL)),
                    cursor.getInt(cursor.getColumnIndex(KEY_WEIGHT)),
                    cursor.getInt(cursor.getColumnIndex(KEY_GOAL_WEIGHT)),
                    cursor.getInt(cursor.getColumnIndex(KEY_HEIGHT)),
                    cursor.getInt(cursor.getColumnIndex(KEY_AGE)),
                    cursor.getString(cursor.getColumnIndex(KEY_GENDER)),
                    cursor.getDouble(cursor.getColumnIndex(KEY_WEIGHT_CHANGE_GOAL)),
                    cursor.getInt(cursor.getColumnIndex(KEY_CALORIES_EATEN)),
                    cursor.getInt(cursor.getColumnIndex(KEY_DAILY_CALORIE_INTAKE))
            );
            cursor.close();
        }

        return user;
    }

    public boolean isUsernameExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_USERNAME}, KEY_USERNAME + "=?", new String[]{username}, null, null, null);
        boolean exists = (cursor != null && cursor.getCount() > 0);

        if (cursor != null) {
            cursor.close();
        }

        return exists;
    }

    public boolean isUsernameAndPasswordExists(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Define the columns you want to retrieve from the database
        String[] columns = {KEY_USERNAME};

        // Define the selection criteria
        String selection = KEY_USERNAME + "=? AND " + KEY_PASSWORD + "=?";

        // Define the selection arguments
        String[] selectionArgs = {username, password};

        // Query the database
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs, null, null, null);

        // Check if the cursor is not null and has at least one entry
        boolean exists = (cursor != null && cursor.getCount() > 0);

        // Close the cursor to free up resources
        if (cursor != null) {
            cursor.close();
        }

        // Return whether the username and password combination exists in the database
        return exists;
    }

    public int getDailyCalorieIntake(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[]{KEY_DAILY_CALORIE_INTAKE}, KEY_USERNAME + "=?", new String[]{username}, null, null, null);

        int dailyCalorieIntake = -1; // Default value indicating failure

        if (cursor != null && cursor.moveToFirst()) {
            dailyCalorieIntake = cursor.getInt(cursor.getColumnIndex(KEY_DAILY_CALORIE_INTAKE));
            cursor.close();
        }

        return dailyCalorieIntake;
    }

}
