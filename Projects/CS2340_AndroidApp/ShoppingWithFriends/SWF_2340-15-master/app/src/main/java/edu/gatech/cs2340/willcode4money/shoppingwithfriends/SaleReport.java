package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Report of sales found by users.
 */
public class SaleReport extends Activity implements Serializable {
    private String owner;
    private String item;
    private double price;
    //Temporary fix..
    private String location;

    public SaleReport(String owner, String item, double price, String location) {
        this.owner = owner;
        this.item = item;

        this.price = price;
        this.location = location;
    }

    public SaleReport() {
        this("", "", 0.0, "");
    }
    /**
     * Creates the activity to add requests
     * @param savedInstanceState - Saved information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_report);
        String currUser = ((ShoppingWithFriends) this.getApplication()).getCurrentUser();
        if (!((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).getAuth()) finish();
    }

    /**
     * Creates a sale report based on user data
     * @param view - the button pressed
     */
    public void makeReport(View view) {
        EditText input1 = (EditText) findViewById(R.id.itemReport);
        EditText input2 = (EditText) findViewById(R.id.priceReport);
        EditText input3 = (EditText) findViewById(R.id.locationReport);

        owner = ((ShoppingWithFriends) this.getApplication()).getCurrentUser();
        String itemReported = input1.getText().toString();
        String locationReported = input3.getText().toString();
        double priceReported;
        try {
            priceReported = Double.parseDouble(input2.getText().toString());
        } catch (Exception e) {
            priceReported = 0.0;
        }


        if (!itemReported.equals("") && !locationReported.equals("")) {
            this.price = priceReported;
            this.item = itemReported;
            this.location = locationReported;
            if (((ShoppingWithFriends) this.getApplication()).getReportBucket().containsKey(this.item)) {
                ((ShoppingWithFriends) this.getApplication()).getReportBucket().get(this.item).add(this);
            } else {
                ArrayList<SaleReport> newBucket = new ArrayList<>();
                newBucket.add(this);
                ((ShoppingWithFriends) this.getApplication()).getReportBucket().put(this.item, newBucket);
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please enter a valid item or location",
                    Toast.LENGTH_LONG).show();
        }
        input1.setText("");
        input2.setText("");
        input3.setText("");
        input1.requestFocus();
        ((ShoppingWithFriends) this.getApplication()).save();
        finish();
    }

    /**
     * Cancels adding a SaleReport
     * @param view - the "cancel" button
     */
    public void cancel(View view) {
        finish();
    }

    /**
     * Gets the item this is for
     * @return the name of the item this is reporting
     */
    public String getItem() {
        return item;
    }

    /**
     * Gets the price that the reported item is selling for
     * @return price of the reported item
     */
    public double getPrice() {
        return price;
    }

    /**
     * gets the location
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * get the owner
     * @return String the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Returns a string representation of this object
     * @return - a string representing this SaleReport
     */
    @Override
    public String toString() {
        return item + " @ $" + price + " located at: " + location + " posted by: " + owner;
    }

    /**
     * Determines if two Objects are equal
     * @param other - another Object(SaleReport)
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (null == other) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (!(other instanceof SaleReport)) {
            return false;
        }
        SaleReport tempSaleReport = (SaleReport) other;
        return this.getItem().equals(tempSaleReport.getItem())
                && this.getPrice() == tempSaleReport.getPrice()
                && this.getLocation().equals(tempSaleReport.getLocation());
    }
}
