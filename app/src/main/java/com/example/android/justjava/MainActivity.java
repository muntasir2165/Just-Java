/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match the package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameEditTextView = (EditText) findViewById(R.id.name);
        String name = nameEditTextView.getText().toString();
        CheckBox hasWhippedCreamCheckBoxView = (CheckBox) findViewById(R.id.has_whipped_cream);
        boolean hasWhippedCreamCheckBox = hasWhippedCreamCheckBoxView.isChecked();
        CheckBox hasChocolateCheckBoxView = (CheckBox) findViewById(R.id.has_chocolate);
        boolean hasChocolateCheckBox = hasChocolateCheckBoxView.isChecked();
        int price = calculatePrice(hasWhippedCreamCheckBox, hasChocolateCheckBox);
        createOrderSummary(name, price, hasWhippedCreamCheckBox, hasChocolateCheckBox);
    }

    /**
     * Calculates the price of the order.
     *
     * @param hasWhippedCream in the order or not
     * @param hasChocolate    in the order or not
     * @return the total price of the order
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        //Base price for 1 cup of coffee
        int price = 5;
        price += hasWhippedCream ? 1 : 0; //Add $1 to the coffee price for whipped cream topping
        price += hasChocolate ? 2 : 0; //Add $2 to the coffee price for chocolate topping
        return quantity * price;
    }

    /**
     * Create a summary of the order.
     *
     * @param price           of the order
     * @param hasWhippedCream in the order or not
     * @param hasChocolate    in the order or not
     * @return None
     * @aram name of the person who made the order
     */
    private void createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {
        String summary = getString(R.string.order_summary_name, name);
        summary += getString(R.string.order_summary_whipped_cream, hasWhippedCream);
        summary += getString(R.string.order_summary_chocolate, hasChocolate);
        summary += getString(R.string.order_summary_quantity, quantity);
        summary += getString(R.string.order_summary_price, price);
        summary += getString(R.string.thank_you);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called when the '+' button is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
        } else {
//            Toast toast = Toast.makeText(getApplicationContext(), "Cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT);
//            toast.show();
            Toast.makeText(this, getString(R.string.max_coffee_error_message), Toast.LENGTH_SHORT).show();
            return;

        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the '-' button is clicked.
     */
    public void decrement(View view) {
        if (quantity >= 2) {
            quantity = quantity - 1;
        } else {
//            Toast toast = Toast.makeText(getApplicationContext(), "Cannot order less than 1 cup of coffee", Toast.LENGTH_SHORT);
//            toast.show();
            Toast.makeText(this, getString(R.string.min_coffee_error_message), Toast.LENGTH_SHORT).show();
            return;
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantity) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);
    }


}