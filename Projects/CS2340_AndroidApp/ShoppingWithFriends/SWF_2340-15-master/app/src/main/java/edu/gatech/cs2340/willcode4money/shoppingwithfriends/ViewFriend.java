package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * The user's friends list.
 */
public class ViewFriend extends Activity {
    private String currUser;
    private User[] friendsList;
    private int index;

    /**
     * Creates and displays the user's friends list
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        currUser = ((ShoppingWithFriends) this.getApplication()).getCurrentUser();
        if (!((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).getAuth()) {
            finish();
        }

        int len = ((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).getFriends().size();
        friendsList = new User[len];
        String[] listName = new String[len];
        for (int i = 0; i < len; i++) {
            friendsList[i] = ((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).getFriends().get(i);
            listName[i] = friendsList[i].getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, listName);
        ListView listview = (ListView) findViewById(R.id.friends_list);
        listview.setAdapter(adapter);
        registerForContextMenu(listview);

        final Activity thisItem = this;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {
                index = (int) id;
                Intent intent = new Intent(thisItem, DetailInfo.class);
                intent.putExtra("friend_info",friendsList[index]);
                startActivity(intent);
            }
        });
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                index = (int) id;
                return false;
            }
        });
    }

    /**
     * Creates and displays the context menu for options
     *
     * @param menu     The context menu that is built.
     * @param v        The view of the context menu
     * @param menuInfo Extra info on the context menu
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Options");
        menu.add(1, 1, 1, "Details");
        menu.add(1, 2, 2, "Delete");
    }

    /**
     * Detect which item is selected on the context menu.
     *
     * @param item The selected item on the context menu.
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == 1)
        {
            Intent intent = new Intent(this, DetailInfo.class);
            intent.putExtra("friend_info",friendsList[index]);
            startActivity(intent);
        }
        if(itemId == 2)
        {
            ((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).removeFriend(friendsList[index]);
            ((ShoppingWithFriends) this.getApplication()).save();
            finish();
            startActivity(new Intent(this, ViewFriend.class));
        }
        return true;
    }
}