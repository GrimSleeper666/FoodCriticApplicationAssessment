package com.example.newfoodapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText foodItemInput, priceInput, descriptionInput, restaurantInput, ratingInput, ratingDateInput;
    Button addToDbButton, deleteButton, updateButton, viewButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        foodItemInput = findViewById(R.id.foodItemInput);
        priceInput = findViewById(R.id.priceInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        restaurantInput = findViewById(R.id.restaurantInput);
        ratingInput = findViewById(R.id.ratingInput);
        ratingDateInput = findViewById(R.id.ratingDateInput);

        addToDbButton = findViewById(R.id.addToDbButton);
        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);
        viewButton = findViewById(R.id.viewButton);

        db = new DatabaseHelper(this);
        addToDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodItemText = foodItemInput.getText().toString();
                String priceInputText = priceInput.getText().toString();
                String descriptionText = descriptionInput.getText().toString();
                String restaurantText = restaurantInput.getText().toString();
                String ratingText = ratingInput.getText().toString();
                String ratingDateText = ratingDateInput.getText().toString();

                Boolean checkData = db.addNewReview(foodItemText, priceInputText, descriptionText, restaurantText, ratingText, ratingDateText);
                if (checkData==true)
                    Toast.makeText(MainActivity.this, "New Data Has Been Inserted Into The Database.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Has Not Been Inserted!", Toast.LENGTH_SHORT).show();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodItemText = foodItemInput.getText().toString();
                String descriptionText = descriptionInput.getText().toString();
                String restaurantText = restaurantInput.getText().toString();
                String ratingText = ratingInput.getText().toString();
                String ratingDateText = ratingDateInput.getText().toString();

                Boolean checkData = db.updateReview(foodItemText, descriptionText, restaurantText, ratingText, ratingDateText);
                if (checkData==true)
                    Toast.makeText(MainActivity.this, "Update Successful.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Update Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodItemText = foodItemInput.getText().toString();

                Boolean dataDelete = db.deleteReview(foodItemText);
                if (dataDelete==true)
                    Toast.makeText(MainActivity.this, "Data Has Been Deleted.", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Data Was Unable To Be Deleted.", Toast.LENGTH_SHORT).show();
            }
        });
        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor check = db.getReview();
                if (check.getCount()==0){
                    Toast.makeText(MainActivity.this, "Entry Does Not Exist. Please Enter A Valid Entry.", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer stringBuffer = new StringBuffer();
                while (check.moveToNext()){
                    stringBuffer.append("FoodItem :"+check.getString(0)+"\n");
                    stringBuffer.append("Price :"+check.getString(1)+"\n");
                    stringBuffer.append("Description :"+check.getString(2)+"\n");
                    stringBuffer.append("Restaurant :"+check.getString(3)+"\n");
                    stringBuffer.append("Rating :"+check.getString(4)+"\n");
                    stringBuffer.append("DateOfRating :"+check.getString(5)+"\n");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Entries");
                builder.setMessage(stringBuffer.toString());
                builder.show();

            }
        });


    }
}