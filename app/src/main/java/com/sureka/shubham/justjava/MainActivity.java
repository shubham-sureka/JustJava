package com.sureka.shubham.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox whippedCream = (CheckBox)findViewById(R.id.whippedCream);
        boolean hasWhippedCream = whippedCream.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        EditText userName = (EditText) findViewById(R.id.userName);
        String uName = userName.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this

        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for " + uName);
        intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary(price, hasWhippedCream, hasChocolate, uName));

        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
    }

    /**
     * Calculates the price of the order.
     * @param addWhippedCream whether user wants whipped cream
     * @param addChocolate whether user wants chocolate
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int pricePerCup = 5;
        if(addWhippedCream)
            pricePerCup += 1;
        if(addChocolate)
            pricePerCup += 2;
        return (quantity * pricePerCup);
    }

    /**
     * Create summary of the order.
     *
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @param uName user name
     * @return text summary
     */
    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String uName){
        String printSummary = getString(R.string.sumName, uName);
        printSummary += "\n" + getString(R.string.sumWhippedCream, addWhippedCream);
        printSummary += "\n" + getString(R.string.sumChocolate, addChocolate);
        printSummary += "\n" + getString(R.string.sumQuantity, quantity);
        printSummary += "\n" + getString(R.string.sumTotal, NumberFormat.getCurrencyInstance().format(price)) + "\n" + getString(R.string.sumThankYou);
        return printSummary;
    }

    //Increment
    public void increment(View view){
        if(quantity==100) {
            Toast.makeText(this, "You cannot have more than 100 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity++;
        display(quantity);
    }

    //decrement
    public void decrement(View view){
        if(quantity==1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity--;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.format(Locale.getDefault(),"%d",number));
    }

}