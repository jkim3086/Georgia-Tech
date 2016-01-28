package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Gives information about a reported sale
 */
public class SaleInfo extends Activity {
    private String location;

    /**
     * Displays sale report information
     * @param savedInstanceState - saved information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saleinfo);

        Intent intent = getIntent();
        SaleReport Report = (SaleReport) intent.getSerializableExtra("report_info");

        TextView name = (TextView) findViewById(R.id.friend_name);
        TextView item = (TextView) findViewById(R.id.friend_Item);
        TextView price = (TextView) findViewById(R.id.friend_Price);
        TextView location = (TextView) findViewById(R.id.friend_Location);

        name.setText("Name: " + Report.getOwner());
        item.setText("Item: " + Report.getItem());
        price.setText("Price: " + Report.getPrice());
        location.setText("Location: " + Report.getLocation());

        this.location = Report.getLocation();
        final Activity thisItem = this;
        final Button button = (Button) findViewById(R.id.GoogleMap);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(thisItem, MapsActivity.class);
                intent.putExtra("Location_info", SaleInfo.this.location);
                startActivity(intent);
            }
        });
    }
}
