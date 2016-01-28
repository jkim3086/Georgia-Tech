package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddFriend extends Activity {
    private String currUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        currUser = ((ShoppingWithFriends) this.getApplication()).getCurrentUser();
        if (!((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).getAuth()) finish();
    }

    /**
     * Searches for and adds a friend to the user's friends list
     * @param view the add friend button
     */
    public void addFriend(View view) {
        EditText nameBox = (EditText) findViewById(R.id.addFriend_name);
        EditText emailBox = (EditText) findViewById(R.id.addFriend_email);
        String name = nameBox.getText().toString();
        String email = emailBox.getText().toString();
        User[] users = new User[((ShoppingWithFriends) this.getApplication()).getUsers().size()];
        users = ((ShoppingWithFriends) this.getApplication()).getUsers().values().toArray(users);
        User cUser = ((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser);

        boolean foundFriend = false;
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getName().equals(name) && !user.equals(cUser)
                    && !((ShoppingWithFriends) this.getApplication()).getUsers().get(
                    currUser).getFriends().contains(user)) {
                ((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).addFriend(
                        user);
                foundFriend = true;
                break;
            }
        }

        if (foundFriend) {
            Toast.makeText(getApplicationContext(), "Friend Added",
                    Toast.LENGTH_LONG).show();
            ((ShoppingWithFriends) this.getApplication()).save();
        }
        else {
            Toast.makeText(getApplicationContext(), "Couldn't find user",
                    Toast.LENGTH_LONG).show();
        }
        nameBox.setText("");
        emailBox.setText("");
        nameBox.requestFocus();
    }

}
