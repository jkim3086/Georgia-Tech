package willcode4money.cs2340.gatech.edu.shoppingwithfriends;

import android.test.ApplicationTestCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gatech.cs2340.willcode4money.shoppingwithfriends.ShoppingWithFriends;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.User;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.databases.UserDBHelper;

/**
 * Tests that the database saves user data correctly
 * Tyler Smith
 */
public class UsersDBTest extends ApplicationTestCase<ShoppingWithFriends> {

    private ShoppingWithFriends swf;
    private UserDBHelper usersDB;

    public UsersDBTest() {
        super(ShoppingWithFriends.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        createApplication();
        swf = this.getApplication();
        usersDB = new UserDBHelper(swf);
        usersDB.onUpgrade(usersDB.getWritableDatabase(), 1, 2);
    }

    /**
     * Tests that the database can handle 1000 Users
     */
    public void testSaveUser() {
        List<User> users = new ArrayList<User>();
        java.util.Random r = new java.util.Random();
        for (int i = 0; i < 1000; i++) {
            String s = String.valueOf(i);
            users.add(new User(s + "username", s + "pass", s + "email", s + "name"));
            for (User user : users) {
                if (!user.equals(users.get(i))) {
                    users.get(i).addFriend(user);
                }
            }
            for (int j = 0; j < 50; j++) {
                users.get(i).addRating(j % 5);
                users.get(i).addRequest(String.valueOf(j), j);
            }
            users.get(i).setAuth(r.nextBoolean());
        }
        Map<String, User> origUsers = new HashMap<>();
        //Save everything!
        for (User user : users) {
            usersDB.saveUser(usersDB.getWritableDatabase(), user);
            origUsers.put(user.getUsername(), user);
        }

        Map<String, User> readUsers = usersDB.readUsers();
        assertEquals("Sizes are not the same", origUsers.size(), readUsers.size());
        assertEquals("Do not contain the same users", origUsers.keySet(), readUsers.keySet());
        for (String s : origUsers.keySet()) {
            User origUser = origUsers.get(s);
            User readUser = readUsers.get(s);
            assertEquals("User mappings are not the same",
                    origUser.toString(), readUser.toString());
        }
    }

    /**
     * Tests that data is saved correctly if info is missing
     */
    public void testEmptyData() {
        User user = new User("user", "","","");
        usersDB.saveUser(usersDB.getWritableDatabase(), user);
        Map<String, User> users = usersDB.readUsers();
        User other = users.get(users.keySet().toArray(new String[1])[0]); //I'm so sorry for writing this in one line
        assertEquals("Data not correct", user.toString(), other.toString());
    }
}
