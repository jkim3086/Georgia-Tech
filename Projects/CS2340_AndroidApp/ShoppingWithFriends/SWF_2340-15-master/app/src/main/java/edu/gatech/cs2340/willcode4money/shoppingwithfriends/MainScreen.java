package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


/**
 * The home screen after login.
 */
public class MainScreen extends Activity {
    private String currUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        currUser = ((ShoppingWithFriends) this.getApplication()).getCurrentUser();
        //if (!((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).getAuth()) finish();

        if (!((ShoppingWithFriends) this.getApplication()).getValidReports().isEmpty()) {
            Toast.makeText(getApplicationContext(), "You have Reports!",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Logs the user out of the application.
     */
    public void logout(View view) {
        ((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).setAuth(false);
        ((ShoppingWithFriends) this.getApplication()).setCurrentUser("");
        ((ShoppingWithFriends) this.getApplication()).save();
        finish();
    }

    public void viewFriends(View view) { startActivity(new Intent(this, ViewFriend.class)); }

    public void addFriends(View view) { startActivity(new Intent(this, AddFriend.class)); }

    public void viewRequests(View view) { startActivity(new Intent(this, ViewItems.class)); }

    public void makeRequests(View view) { startActivity(new Intent(this, SaleRequest.class)); }

    public void viewReports(View view) { startActivity(new Intent(this, ViewReports.class)); }

    public void makeReport(View view) { startActivity(new Intent(this, SaleReport.class)); }

    public void changePass(View view) { startActivity(new Intent(this, ChangePass.class)); }
}
