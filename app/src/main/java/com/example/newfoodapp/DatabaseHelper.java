package com.example.newfoodapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "FoodCriticApp.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ratings = "CREATE TABLE IF NOT EXISTS ratings (" +
                "Id integer primary key autoincrement," +
                "FoodItem string," +
                "RatingGiven integer," +
                "Price real,"+
                "RestaurantId integer," +
                "Description string," +
                "DateOfRating numeric" +
                ")";
        db.execSQL(ratings);

        String restaurant = "CREATE TABLE IF NOT EXISTS restaurant (" +
                "Id integer primary key autoincrement," +
                "Name string" +
                ")";
        db.execSQL(restaurant);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS restaurant");
        db.execSQL("DROP TABLE IF EXISTS ratings");
        onCreate(db);

    }

    public long addRestaurant(String restaurantName){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", restaurantName);

        return db.insert("restaurant", null, values);

    }

    public boolean addNewReview(String foodItem, String price, String description, String restaurantName, String ratingGiven, String dateOfRating){
        SQLiteDatabase PersonalDb = getWritableDatabase();
        Cursor restaurant = findRestaurantByName(restaurantName);
        restaurant.moveToFirst();
        int restaurantId;
        if(restaurant.getCount()>0)
            restaurantId = restaurant.getInt(0);
        //otherwise create a new restaurant
        else
            restaurantId = (int)addRestaurant(restaurantName);

        ContentValues values = new ContentValues();
        values.put("FoodItem", foodItem);
        values.put("RestaurantId", restaurantId);
        values.put("Description", description);
        values.put("RatingGiven", ratingGiven);
        values.put("DateOfRating", dateOfRating);
        long result = PersonalDb.insert("ratings", null, values);
        if(result==-1){
            return false;
        }else{
            return true;
        }
    }
    public Cursor findRestaurant(int id){
        SQLiteDatabase db = getReadableDatabase();
        String querySelection[] = {String.valueOf(id)};
        Cursor cursor = db.rawQuery("Select * from restaurant where id = ?", querySelection);
        return cursor;
    }

    public Cursor findRestaurantByName(String name){
        SQLiteDatabase db = getReadableDatabase();
        String querySelection[] = {name};

        Cursor cursor = db.rawQuery("Select * from restaurant where name = ?", querySelection);
        return cursor;
    }

    public boolean updateReview(String FoodItem, String Restaurant, String Description, String RatingGiven, String DateOfRating) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Restaurant Name", Restaurant);
        values.put("Description", Description);
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
        Cursor cursor = db.rawQuery("Select * from restaurant", null);
        return cursor;
    }

}