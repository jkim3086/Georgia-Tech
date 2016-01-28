package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;

import edu.gatech.cs2340.willcode4money.shoppingwithfriends.R;

/**
 * A login screen that offers login via username/password.
 */
public class Login extends Activity {

    // UI references.
    private EditText mUserView;
    private EditText mPasswordView;
    private TextView infoTextView;
    private Map<String, User> users;

    /**
     * Initializes the screen with text views.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUserView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        infoTextView = (TextView) findViewById(R.id.login_info);
        infoTextView.setVisibility(View.GONE);

        users = ((ShoppingWithFriends) this.getApplication()).getUsers();
    }

    /**
     * Checks the credentials and attempts a login.
     */
    public void login(View view) {
        String username = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();
        Intent intent;

        if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
            intent = new Intent(this, MainScreen.class);
            ((ShoppingWithFriends) this.getApplication()).setCurrentUser(username);
            ((ShoppingWithFriends) this.getApplication()).getUsers().get(username).setAuth(true);
            ((ShoppingWithFriends) this.getApplication()).save();
            startActivity(intent);

            this.finish();
        } else {
            infoTextView.setText("Error: Unknown username/password combination!");
            infoTextView.setTextColor(Color.RED);
            infoTextView.setVisibility(View.VISIBLE);
            mUserView.setText("");
            mPasswordView.setText("");
            mUserView.requestFocus();
        }
    }

    /**
     * Cancels login
     */
    public void cancel(View view) {
        finish();
    }

    /**
     * Allows the user to recover their account.
     */
    public void recovery(View view) {
        startActivity(new Intent(this, RecoverPassword.class));
        this.finish();
    }
}
