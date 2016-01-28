package edu.gatech.cs2340.willcode4money.shoppingwithfriends.email;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Date;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import edu.gatech.cs2340.willcode4money.shoppingwithfriends.ShoppingWithFriends;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.User;

/**
 * A class that sends an email message to allow users to recover their accounts.
 */
public class MailSender extends AsyncTask<String, Void, Boolean> {
    //Our app uses a Gmail account to send emails
    private String mailhost = "smtp.gmail.com";
    private String appName = "Shopping With Friends Team 39";
    //Username and password for application's Gmail account
    private String user;
    private String password;

    private String subject = "Account Recovery for Shopping With Friends";
    private String body;
    private Session session;
    private TextView message;
    private ShoppingWithFriends thisApp;
    private User thisUser;
    private String oldPass;

    public MailSender(TextView message, ShoppingWithFriends app, User thisUser, String oldPass, String user, String password) {
        super();
        this.user = user;
        this.password = password;
        this.message = message;
        this.thisApp = app;
        this.thisUser = thisUser;
        this.oldPass = oldPass;

        Properties props = System.getProperties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.host", mailhost);
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", this.user);
        props.setProperty("mail.smpt.password", this.password);
        session = Session.getInstance(props, new MailAuthenticator(this.user, this.password));
    }

    /**
     * Send the account recovery email to the specified user
     *
     * @param name - the name of the user
     * @param recipient - the user's email address
     * @param theirPass - the user's temporary password
     */
    public synchronized void sendMail(String name, String recipient, String theirPass) {
        try {
            this.execute(name, recipient, theirPass);
        } catch (Exception e) {
            Log.d("[Yikes}", "Something happened with the emails");
        }
    }

    /**
     * Asynchronously send an email containing the user's temporary password.
     * Should only be called through sendMail(String, String, String)
     * @param params - Three (3) Strings: user's name, user's email, user's password to send
     * @return true if there are 3 arguments and the email sends successfully; false otherwise
     */
    @Override
    protected synchronized Boolean doInBackground(String... params) {
        if (params.length != 3) {
            return false;
        }
        String name = params[0];
        String recipient = params[1];
        String theirPass = params[2];

        try {
            Log.d("[EMAIL]", "Sending email...");
            body = "Hello, " + name + "!\n\n\tUse the temporary password below to " +
                    "log into your account. " + "Please change your password once " +
                    "you've logged in.\n\n\n" + theirPass + "\n\n\nSincerely," +
                    "\n\nShopping With Friends, Team 39";
            MimeMessage message = new MimeMessage(session);

            //Construct message header
            message.setSender(new InternetAddress(user));
            message.setFrom(new InternetAddress(user, appName));
            message.setSubject(subject);
            message.setSentDate(new Date());
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recipient));
            message.setContent(body, "text/plain");
            message.saveChanges();

            //Send the thing!
            Transport.send(message);
            Log.d("[EMAIL]", "Email sent!");
        } catch(MessagingException e){
            Log.d("[EMAIL]", "Could not send message!");
           // e.printStackTrace();
            return false;
        } catch(Exception e) {
            Log.d("[EMAIL]", "Could not send message (Exception)!");
           // e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Lets the user know that the app is sending the email.
     */
    protected void onPreExecute() {
        message.setText("Sending Email...");
        message.setTextColor(Color.BLACK);
        message.setVisibility(View.VISIBLE);
    }

    /**
     * Sets the message to the correct value.
     * @param wasSent - Whether or not the email successfully sent
     */
    protected void onPostExecute(Boolean wasSent) {
        if (wasSent) {
            thisApp.save();
            message.setTextColor(Color.GREEN);
            message.setText("Email sent. Check your inbox.");
            message.setVisibility(View.VISIBLE);
        } else {
            thisUser.setPassword(oldPass);
            message.setText("Unable to send email. Are you online?");
            message.setTextColor(Color.RED);
            message.setVisibility(View.VISIBLE);
        }
    }
}