package com.example.coffeeordering;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coffeeordering.database.OrderContract;

public class InfoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ImageView imageView;
    ImageButton plusquantity, minusquantity;
    TextView quantitynumber, drinkName, coffeePrice;
    CheckBox addToppings, addExtraCream;
    Button addtoCart;
    int quantity;
    public Uri mCurrentCartUri;
    boolean hasAllRequiredValues = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        imageView = findViewById(R.id.imageViewInfo);
        plusquantity = findViewById(R.id.addquantity);
        minusquantity  = findViewById(R.id.subquantity);
        quantitynumber = findViewById(R.id.quantity);
        drinkName = findViewById(R.id.drinkNameinInfo);
        coffeePrice = findViewById(R.id.coffeePrice);
        addToppings = findViewById(R.id.addToppings);
        addtoCart = findViewById(R.id.addtocart);
        addExtraCream = findViewById(R.id.addCream);

        // setting the name of drink
        drinkName.setText("Green Tea");

        addtoCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoActivity.this, SummaryActivity.class);
                startActivity(intent);

                // once this button is clicked we want to save our values in the database and send those values
                // right away to summary activity where we display the order info
                saveCart();
            }
        });

        plusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // coffee price
                int basePrice = 5;
                quantity++;
                displayQuantity();
                int coffePrice = basePrice * quantity;
                String setnewPrice = String.valueOf(coffePrice);
                coffeePrice.setText(setnewPrice);

                // checkBoxes functionality
                int ifCheckBox = calculatePrice(addExtraCream, addToppings);
                coffeePrice.setText("$ " + ifCheckBox);
            }
        });

        minusquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int basePrice = 5;
                // because we dont want the quantity go less than 0
                if (quantity == 0) {
                    Toast.makeText(InfoActivity.this, "Cant decrease quantity < 0", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    displayQuantity();
                    int coffePrice = basePrice * quantity;
                    String setnewPrice = String.valueOf(coffePrice);
                    coffeePrice.setText(setnewPrice);
                }
            }
        });
    }

    private boolean saveCart() {
        // getting the values from our views
        String name = drinkName.getText().toString();
        String price = coffeePrice.getText().toString();
        String quantity = quantitynumber.getText().toString();

        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderEntry.COLUMN_NAME, name);
        values.put(OrderContract.OrderEntry.COLUMN_PRICE, price);
        values.put(OrderContract.OrderEntry.COLUMN_QUANTITY, quantity);

        if (addExtraCream.isChecked()) {
            values.put(OrderContract.OrderEntry.COLUMN_CREAM, "Has Cream: YES");
        } else {
            values.put(OrderContract.OrderEntry.COLUMN_CREAM, "Has Cream: NO");

        }

        if (addToppings.isChecked()) {
            values.put(OrderContract.OrderEntry.COLUMN_HASTOPPING, "Has Toppings: YES");
        } else {
            values.put(OrderContract.OrderEntry.COLUMN_HASTOPPING, "Has Toppings: NO");

        }

        if (mCurrentCartUri == null) {
            Uri newUri = getContentResolver().insert(OrderContract.OrderEntry.CONTENT_URI, values);
            if (newUri==null) {
                Toast.makeText(this, "Failed to add to Cart", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Success  adding to Cart", Toast.LENGTH_SHORT).show();

            }
        }

        hasAllRequiredValues = true;
        return hasAllRequiredValues;
    }

    private int calculatePrice(CheckBox addExtraCream, CheckBox addToppings) {
        int basePrice = 5;

        if (addExtraCream.isChecked()) {
            // add the cream cost $2
            basePrice = basePrice + 2;
        }

        if (addToppings.isChecked()) {
            // topping cost is $3
            basePrice = basePrice + 3;
        }

        return basePrice * quantity;
    }

    private void displayQuantity() {
        quantitynumber.setText(String.valueOf(quantity));
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}