package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;


/**
 * Contains a request made by a user for certain items
 */
public class SaleRequest extends Activity implements Serializable {
    private final String owner;
    private final String item;
    private final double maxPrice;
    private String currUser;

    public SaleRequest(String owner, String item, double maxPrice) {
        this.owner = owner;
        this.item = item;
        this.maxPrice = maxPrice;
    }

    public SaleRequest() {
        this("", "", 0.0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_request);
        currUser = ((ShoppingWithFriends) this.getApplication()).getCurrentUser();
        if (!((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).getAuth()) finish();
    }

    /**
     * Adds a sale request for this specified item and price
     * @param view - the "make request" button
     */
    public void makeRequest(View view) {
        EditText input1 = (EditText) findViewById(R.id.item);
        EditText input2 = (EditText) findViewById(R.id.price);

        String itemRequested = input1.getText().toString();
        double price = Integer.parseInt(input2.getText().toString());

        ((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).addRequest(itemRequested, price);

        Toast.makeText(getApplicationContext(), "Request Made!",
                Toast.LENGTH_LONG).show();

        input1.setText("");
        input2.setText("");
        input1.requestFocus();
        ((ShoppingWithFriends) this.getApplication()).save();
    }

    /**
     * Cancels adding the sale request
     * @param view - the "cancel" button
     */
    public void cancel(View view) {
        finish();
    }

    /**
     * Gets the username of the creator of this request
     * @return the username of the creator
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Gets the item that this is for
     * @return the name of the requested item
     */
    public String getItem() {
        return item;
    }

    /**
     * Gets the maximum price the owner wants the item for
     * @return the max. price of the item
     */
    public double getMaxPrice() {
        return maxPrice;
    }

    /**
     * Returns a string representation of the Sale Request
     * @return - a string representing this object
     */
    @Override
    public String toString() {
        return getOwner() + ": " + getItem() + " @ $" + getMaxPrice();
    }
}
