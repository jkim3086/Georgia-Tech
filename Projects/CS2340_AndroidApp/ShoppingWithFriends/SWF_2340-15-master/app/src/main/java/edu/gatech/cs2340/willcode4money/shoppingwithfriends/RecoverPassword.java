package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

import edu.gatech.cs2340.willcode4money.shoppingwithfriends.email.MailSender;

/**
 * Allows the user to retrieve a lost password.
 */
public class RecoverPassword extends Activity {
    private static final String USERNAME = "swf.team39@gmail.com";
    private static final String PASSWORD = "wc4mteam39";
    private static ShoppingWithFriends thisApp;

    private EditText userBox;
    private TextView message;

    /**
     * Called when the activity is first created.
     * */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisApp = ((ShoppingWithFriends) this.getApplication());
        setContentView(R.layout.activity_recover_password);
        userBox = (EditText) this.findViewById(R.id.recovery_user);
        message = (TextView) this.findViewById(R.id.recovery_message);
        message.setVisibility(View.GONE);
    }

    /**
     * Tries to send an email to the user
     * @param view - the "send email" button
     */
    public void recover(View view) {
        message.setVisibility(View.GONE);
        String username = userBox.getText().toString();
        User user = thisApp.getUsers().get(username);
        if (user == null) {
            this.notFound();
            return;
        }
        String name = user.getName();
        String email = user.getEmail();
        String oldPass = user.getPassword();
        String password = this.getNewPassword();
        user.setPassword(password);
        MailSender sender = new MailSender(message, thisApp, user, oldPass, USERNAME, PASSWORD);
        sender.sendMail(name, email, password);
        userBox.setText("");
        userBox.requestFocus();
    }

    //Constructs a new, random password for the user.
    private String getNewPassword() {
        char[] symbols;
        char[] pass = new char[8];
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ch++) {
            tmp.append(ch);
        }
        for (char ch = 'a'; ch <= 'z'; ch++) {
            tmp.append(ch);
        }
        for (char ch = 'A'; ch <= 'Z'; ch++) {
            tmp.append(ch);
        }
        symbols = tmp.toString().toCharArray();
        Random r = new Random();
        for (int i = 0; i < 8; i++) {
            pass[i] = symbols[r.nextInt(symbols.length)];
        }
        return String.valueOf(pass);
    }

    //Called when the username provided by the user is not registered
    private void notFound() {
        message.setText("That user does not exist.");
        message.setVisibility(View.VISIBLE);
        message.setTextColor(Color.RED);
        userBox.setText("");
        userBox.requestFocus();
    }
}
