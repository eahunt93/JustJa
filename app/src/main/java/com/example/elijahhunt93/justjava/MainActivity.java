package com.example.elijahhunt93.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

import static android.R.attr.name;
import static android.R.attr.order;
import static android.os.Build.VERSION_CODES.N;
import static com.example.elijahhunt93.justjava.R.string.price;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText Name = (EditText) findViewById(R.id.Name);
        String ordername = Name.getText().toString();

        CheckBox whippedcream = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean haswhippedcream = whippedcream.isChecked();


        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean haschocolate = chocolate.isChecked();

        int price = calculatePrice(haswhippedcream, haschocolate);
        String priceMessage = createOrderSummary(price, haswhippedcream, haschocolate, ordername);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java order for  " + ordername);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @return total price
     */
    private int calculatePrice(boolean addwhippedcream, boolean addchocolate) {
        int basePrice = 5;
        if (addwhippedcream) {
            basePrice = basePrice + 1;
        }

        if (addchocolate) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    /**
     * @param price of the order
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addwhippedcream, boolean addchocolate, String name) {
        String priceMessage = "Name: " + name;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage += "\nAdd whipped cream? " + addwhippedcream;
        priceMessage += "\nadd chocolate? " + addchocolate;
        priceMessage = priceMessage + "\nTotal: $" + price;
        priceMessage = priceMessage + "\nThank You!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int NumberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + NumberOfCoffees);
    }

    /**
     * @param message display price on the screen
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity += 1;
        }
        displayQuantity(quantity);
    }

    public void deincrement(View view) {
        if (quantity > 0) {
            quantity -= 1;
            displayQuantity(quantity);
        } else {
            quantity = 0;
        }
    }

}