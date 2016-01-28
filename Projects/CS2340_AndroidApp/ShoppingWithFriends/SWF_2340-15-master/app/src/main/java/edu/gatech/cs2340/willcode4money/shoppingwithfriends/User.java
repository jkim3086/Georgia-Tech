package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains User information needed by the application.
 */
public class User implements Serializable {
    private final String name;
    private final String username;
    private String password;
    private final String email;
    private final List<Integer> ratings;
    private boolean authenticated;

    private List<User> friends;
    private List<SaleRequest> requests;
    private final List<SaleReport> salesReported;

    public User(String username, String password, String email, String name) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.authenticated = false;
        this.ratings = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.salesReported = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    /**
     * Returns whether or not the user has authenticated
     * @return true if the user should be logged in. False if not
     */
    public boolean getAuth() { return authenticated; }

    /**
     * Sets whether or not the user has authenticated
     * @param auth true if the user should be logged in
     */
    public void setAuth(boolean auth) { authenticated = auth; }

    /**
     * Retrieves the list of friends of a user
     * @return the friends list
     */
    public List<User> getFriends() {
        return friends;
    }

    /**
     * Adds a friend to the user's friends list. Also adds this user to the other user's friends list
     * @param friend The friend to create a friendship with.
     */
    public void addFriend(User friend) {
        this.friends.add(friend);
        friend.friends.add(this);
    }

    /**
     * Removes a friend from the user's friends list. Also removes this user from the other user's friends list
     * @param friend The friend to delete the friendship with.
     */
    public void removeFriend(User friend) {
        this.friends.remove(friend);
        friend.friends.remove(this);
    }

    /**
     * Gets the name of this user
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the username of this user.
     * @return this user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's password
     * @return the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password
     * @param pass - the new password
     */
    public void setPassword(String pass) {
        this.password = pass;
    }

    /**
     * Returns the list of SaleRequests this user has made
     * @return a list of SaleRequests make by this user
     */
    public List<SaleRequest> getRequests() {
        return requests;
    }

    /**
     * Adds a sale request to this user's list of sale requests
     * @param item item will be requested
     * @param price price will be requested
     */
    public void addRequest(String item, double price) {
        SaleRequest request = new SaleRequest(this.username, item, price);
        this.requests.add(request);
    }

    /**
     * Returns the number of sales reported by this user
     * @return number of sale reports
     */
    int getNumSaleReports() { return salesReported.size(); }

    /**
     * Adds a new rating to this user
     * @param rating the rating to add
     */
    public void addRating(int rating) {
        ratings.add(rating);
    }

    /**
     * Returns the average rating of this user.
     * @return the average user rating rounded to 2 decimal places
     */
    public double getRating() {
        int total = 0;
        for (Integer rating : ratings) {
            total += rating;
        }
        double rating = ((double) total) / ratings.size();
        return ((double) Math.round(rating * 100)) / 100;
    }

    /**
     * Retrieves the email address of a user
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the full list of ratings
     * @return a list of ratings
     */
    public List<Integer> getRatingsList() { return ratings; }

    /**
     * Sets the user's friends list
     * @param friends - a list of Users to set the list to
     */
    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    /**
     * Sets the user's requests list
     * @param requests - a list of SaleRequests to set the list to
     */
    public void setRequests(List<SaleRequest> requests) {
        this.requests = requests;
    }

    /**
     * Determines if two users are the same by using their usernames
     * @param other the user to compare to
     * @return true if the usernames match
     */
    @Override
    public boolean equals(Object other) {
        if (null == other) {
            return false;
        }
        if (this == other) {
            return true;
        }
        if (!(other instanceof User)) {
            return false;
        }
        //It can never throw the exception
        //noinspection ConstantConditions
        User tempUser = (User) other;
        return (this.getName().equals(tempUser.getName())
                && this.getEmail().equals(tempUser.getEmail()));
    }

    /**
     * Returns a string representation of the user
     * @return a string representing the user
     */
    @Override
    public String toString() {
        return name + "\n" + email + "\n" + this.getRating() + "\n" + this.getNumSaleReports();
    }
}
