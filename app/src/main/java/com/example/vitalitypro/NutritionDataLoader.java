package com.example.vitalitypro;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NutritionDataLoader {

    private static final String TAG = "NutritionDataLoader";

    private static final String CSV_FILE_NAME = "nutrition.csv";

    private static final String DATABASE_NAME = "nutrition_database";
    private static final int DATABASE_VERSION = 1;

    // Define the required columns in your database
    private static final String TABLE_NAME = "nutrition_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SERVING_SIZE = "serving_size";
    private static final String COLUMN_CALORIES = "calories";
    private static final String COLUMN_CARBOHYDRATE = "carb";
    private static final String COLUMN_PROTEIN = "protein";
    private static final String COLUMN_FAT = "fat";
    private static final String COLUMN_FIBER = "fiber";
    private static final String COLUMN_SUGAR = "sugar";
    private static final String COLUMN_SATURATED_FATS = "saturated_fats";
    private static final String COLUMN_POLYUNSATURATED_FATS = "polyunsaturated_fats";
    private static final String COLUMN_MONOUNSATURATED_FATS = "monounsaturated_fats";
    private static final String COLUMN_TRANS_FATS = "trans_fats";
    private static final String COLUMN_CHOLESTEROL = "cholesterol";
    private static final String COLUMN_SODIUM = "sodium";
    private static final String COLUMN_POTASSIUM = "potassium";
    private static final String COLUMN_VITAMIN_A = "vitamin_a";
    private static final String COLUMN_VITAMIN_C = "vitamin_c";
    private static final String COLUMN_CALCIUM = "calcium";
    private static final String COLUMN_IRON = "iron";

    private Context context;
    private SQLiteDatabase database;

    public NutritionDataLoader(Context context) {
        this.context = context;
    }

    public void loadNutritionDataIfNeeded() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isDataLoaded = prefs.getBoolean("data_loaded", false);
        if (!isDataLoaded) {
            loadNutritionData();
            // Set the flag indicating that the data has been loaded
            prefs.edit().putBoolean("data_loaded", true).apply();
        }
    }

    public void loadNutritionData() {
        SQLiteOpenHelper dbHelper = new SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                // Create the database table
                String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_SERVING_SIZE + " TEXT, " +
                        COLUMN_CALORIES + " INTEGER, " +
                        COLUMN_CARBOHYDRATE + " TEXT, " +
                        COLUMN_PROTEIN + " TEXT, " +
                        COLUMN_FAT + " TEXT, " +
                        COLUMN_FIBER + " TEXT, " +
                        COLUMN_SUGAR + " TEXT, " +
                        COLUMN_SATURATED_FATS + " TEXT, " +
                        COLUMN_POLYUNSATURATED_FATS + " TEXT, " +
                        COLUMN_MONOUNSATURATED_FATS + " TEXT, " +
                        COLUMN_TRANS_FATS + " TEXT, " +
                        COLUMN_CHOLESTEROL + " TEXT, " +
                        COLUMN_SODIUM + " TEXT, " +
                        COLUMN_POTASSIUM + " TEXT, " +
                        COLUMN_VITAMIN_A + " TEXT, " +
                        COLUMN_VITAMIN_C + " TEXT, " +
                        COLUMN_CALCIUM + " TEXT, " +
                        COLUMN_IRON + " TEXT)";
                db.execSQL(createTableQuery);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // Handle database upgrade if needed
            }
        };

        database = dbHelper.getWritableDatabase();

        try {
            // Open the CSV file in the assets folder
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(CSV_FILE_NAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Skip the header line
            String line = reader.readLine();

            // Read data from CSV line by line and insert into the database
            while ((line = reader.readLine()) != null) {
                Log.d(TAG, "CSV Line: " + line); // Log the content of each CSV line

                String[] values = line.split(",");

                // Ensure that the values array has enough elements
                if (values.length < 60) {
                    Log.e(TAG, "Invalid CSV line: " + line);
                    continue; // Skip this line and proceed to the next one
                }

                // Extract required values from the CSV line
                int id = Integer.parseInt(values[0]);
                String name = values[1];
                String servingSize = values[2];
                int calories = Integer.parseInt(values[3]);
                String carbohydrate = values[4];
                String protein = values[5];
                String fat = values[6];
                String fiber = values[7];
                String sugar = values[8];
                String saturatedFats = values[49];
                String polyunsaturatedFats = values[50];
                String monounsaturatedFats = values[51];
                String transFats = values[52];
                String cholesterol = values[53];
                String sodium = values[54];
                String potassium = values[55];
                String vitaminA = values[56];
                String vitaminC = values[57];
                String calcium = values[58];
                String iron = values[59];

                // Insert the values into the database
                String insertQuery = "INSERT INTO " + TABLE_NAME + " (" +
                        COLUMN_ID + ", "+
                        COLUMN_NAME + ", " +
                        COLUMN_SERVING_SIZE + ", " +
                        COLUMN_CALORIES + ", " +
                        COLUMN_CARBOHYDRATE + ", " +
                        COLUMN_PROTEIN + ", " +
                        COLUMN_FAT + ", " +
                        COLUMN_FIBER + ", " +
                        COLUMN_SUGAR + ", " +
                        COLUMN_SATURATED_FATS + ", " +
                        COLUMN_POLYUNSATURATED_FATS + ", " +
                        COLUMN_MONOUNSATURATED_FATS + ", " +
                        COLUMN_TRANS_FATS + ", " +
                        COLUMN_CHOLESTEROL + ", " +
                        COLUMN_SODIUM + ", " +
                        COLUMN_POTASSIUM + ", " +
                        COLUMN_VITAMIN_A + ", " +
                        COLUMN_VITAMIN_C + ", " +
                        COLUMN_CALCIUM + ", " +
                        COLUMN_IRON + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                database.execSQL(insertQuery, new Object[]{
                        id,name, servingSize, calories, carbohydrate, protein, fat, fiber, sugar, saturatedFats,
                        polyunsaturatedFats, monounsaturatedFats, transFats, cholesterol, sodium, potassium,
                        vitaminA, vitaminC, calcium, iron
                });
            }

            // Close the file and the database connection
            reader.close();
            database.close();
        } catch (IOException | SQLiteException | NumberFormatException e) {
            Log.e(TAG, "Error loading nutrition data: " + e.getMessage());
        }
    }


}

