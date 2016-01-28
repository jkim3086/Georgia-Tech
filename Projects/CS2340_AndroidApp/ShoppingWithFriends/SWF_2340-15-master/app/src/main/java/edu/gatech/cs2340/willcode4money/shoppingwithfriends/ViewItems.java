package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * User's requested item list with price
 */
public class ViewItems extends Activity {

    /**
     * Creates and displays the user's list of requested items
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        String currUser = ((ShoppingWithFriends) this.getApplication()).getCurrentUser();
        if (!((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).getAuth()) {
            finish();
        }
        Object[] requestArray = ((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).getRequests().toArray();
        String[] itemsList = new String[requestArray.length];
        for (int i = 0; i < requestArray.length; i++) {
            itemsList[i] = requestArray[i].toString();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, itemsList);
        ListView listview = (ListView) findViewById(R.id.item_list);
        listview.setAdapter(adapter);
    }
}
