package com.example.vitalitypro;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database name
    private static final String DATABASE_NAME = "IngredientsDB";

    // Table name
    private static final String TABLE_INGREDIENTS = "ingredients";

    // Table columns
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_CALORIES = "calories";
    private static final String KEY_FAT = "fat";
    private static final String KEY_CARBS = "carbs";
    private static final String KEY_PROTEIN = "protein";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create ingredients table
        String CREATE_INGREDIENTS_TABLE = "CREATE TABLE " + TABLE_INGREDIENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_CALORIES + " REAL,"
                + KEY_FAT + " REAL,"
                + KEY_CARBS + " REAL,"
                + KEY_PROTEIN + " REAL"
                + ")";
        db.execSQL(CREATE_INGREDIENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTS);
        // Create tables again
        onCreate(db);
    }

    // Add method to insert ingredient into database
    public void addIngredient(Ingredient ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, ingredient.getName());
        values.put(KEY_CALORIES, ingredient.getCalories());
        values.put(KEY_FAT, ingredient.getFat());
        values.put(KEY_CARBS, ingredient.getCarbs());
        values.put(KEY_PROTEIN, ingredient.getProtein());

        // Inserting Row
        db.insert(TABLE_INGREDIENTS, null, values);
        // Closing database connection
        db.close();
    }

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();

        // Select all query
        String selectQuery = "SELECT * FROM " + TABLE_INGREDIENTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all rows and add to list
        if (cursor.moveToFirst()) {
            do {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                ingredient.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                ingredient.setCalories(cursor.getDouble(cursor.getColumnIndex(KEY_CALORIES)));
                ingredient.setFat(cursor.getDouble(cursor.getColumnIndex(KEY_FAT)));
                ingredient.setCarbs(cursor.getDouble(cursor.getColumnIndex(KEY_CARBS)));
                ingredient.setProtein(cursor.getDouble(cursor.getColumnIndex(KEY_PROTEIN)));

                // Add ingredient to list
                ingredients.add(ingredient);
            } while (cursor.moveToNext());
        }

        // Close cursor and database connection
        cursor.close();
        db.close();

        // Return list of ingredients
        return ingredients;
    }

    // Add more methods as needed for querying, updating, or deleting ingredients
}
