package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Allows the user to change their password
 */
public class ChangePass extends ActionBarActivity {

    EditText newPass;
    EditText newPassConf;
    TextView message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        newPass = (EditText) findViewById(R.id.change_pass_new);
        newPassConf = (EditText) findViewById(R.id.change_pass_new_conf);
        message = (TextView) findViewById(R.id.new_pass_message);
        message.setVisibility(View.GONE);
    }

    /**
     * Saves the new password to the user's account
     * @param view - the button pressed
     */
    public void savePass(View view) {
        String pass = newPass.getText().toString();
        String passConf = newPassConf.getText().toString();

        if (pass.equals(passConf)) {
            String user = ((ShoppingWithFriends) this.getApplication()).getCurrentUser();
            User curr = ((ShoppingWithFriends) this.getApplication()).getUsers().get(user);
            curr.setPassword(pass);
            message.setText("Password changed!");
            message.setTextColor(Color.GREEN);
            message.setVisibility(View.VISIBLE);
        } else {
            message.setText("Password mismatch!");
            message.setTextColor(Color.RED);
            message.setVisibility(View.VISIBLE);
        }
        newPass.setText("");
        newPassConf.setText("");
        newPass.requestFocus();
        ((ShoppingWithFriends) this.getApplication()).save();
    }
}
