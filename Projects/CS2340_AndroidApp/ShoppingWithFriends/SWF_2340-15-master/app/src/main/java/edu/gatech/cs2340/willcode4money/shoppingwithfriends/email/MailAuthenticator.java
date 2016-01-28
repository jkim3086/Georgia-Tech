package edu.gatech.cs2340.willcode4money.shoppingwithfriends.email;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * A class allowing the application to authenticate with
 * a mail server in order to send emails.
 */
class MailAuthenticator extends Authenticator {
    String user;
    String password;

    public MailAuthenticator (String username, String password) {
        super();
        this.user = username;
        this.password = password;
    }

    /**
     * Authenticates a Session to allow communication with email server.
     * @return password authentication
     */
    @Override
    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(user, password);
    }
}