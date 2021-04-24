package com.example.newfoodapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "NewFoodApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ratings = "CREATE TABLE IF NOT EXISTS ratings (" +
                "FoodItem string," +
                "RatingGiven int" +
                ")";
        db.execSQL(ratings);

        String review = "CREATE TABLE IF NOT EXISTS Review (" +
                "Restaurant string," +
                "Details string," +
                "DateOfRating numeric" +
                ")";
        db.execSQL(review);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NewFoodApp");
        onCreate(db);

    }

    public boolean addNewReview(String FoodItem, String Restaurant, String Details, String RatingGiven, String Price, String DateOfRating){
        SQLiteDatabase PersonalDb = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FoodItem", FoodItem);
        values.put("Restaurant Name", Restaurant);
        values.put("Details", Details);
        values.put("Rating/Score", RatingGiven);
        values.put("DateOfRating", DateOfRating);
        long result =   PersonalDb.insert("NewFoodApp", null, values);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateReview(String FoodItem, String Restaurant, String Details, String RatingGiven, String DateOfRating) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Restaurant Name", Restaurant);
        values.put("Details", Details);
        values.put("Rating/Score", RatingGiven);
        values.put("Date Of Rating", DateOfRating);
        Cursor cursor = db.rawQuery("Select * from NewFoodApp where FoodItem = ?", new String[]{FoodItem});
        if (cursor.getCount() > 0) {
            long result = db.update("NewFoodApp", values, "FoodItem=?", new String[]{});
            if (result == -1) {
                return false;
            } else {return true;}
        }
        else {return false;}

    }

    public boolean deleteReview(String FoodItem) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from NewFoodApp where FoodItem = ?", new String[]{FoodItem});
        if (cursor.getCount() > 0) {
            long result = db.delete("NewFoodApp","FoodItem=?", new String[]{});
            if (result == -1) {
                return false;
            } else {return true;}
        }
        else {return false;}

    }

    public Cursor getReview() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from NewFoodApp", null);
        return cursor;
    }

}