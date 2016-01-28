package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import edu.gatech.cs2340.willcode4money.shoppingwithfriends.R;

/**
 * Displays a friend's details.
 */
public class DetailInfo extends Activity {

    /**
     * Creates and displays the selected friend's detail info
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);
        ShoppingWithFriends app = (ShoppingWithFriends) this.getApplication();

        Intent intent = getIntent();
        User friend = (User)intent.getSerializableExtra("friend_info");

        TextView name = (TextView) findViewById(R.id.friend_name);
        TextView e_mail = (TextView) findViewById(R.id.friend_Email);
        TextView rating = (TextView) findViewById(R.id.friend_rate);
        TextView num_sales = (TextView) findViewById(R.id.friend_sales);

        name.setText("Name: " + friend.getName());
        e_mail.setText("E-mail: " + friend.getEmail());
        rating.setText("Rating: " + friend.getRating());
        List<SaleReport> reports = app.getValidReports();
        //Get number of reports to you from this user
        int count = 0;
        for (SaleReport report : reports) {
            if (report.getOwner().equals(friend.getUsername())) {
                count++;
            }
        }
        num_sales.setText("Reports: " + count);
    }

}
