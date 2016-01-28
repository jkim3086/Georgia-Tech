package willcode4money.cs2340.gatech.edu.shoppingwithfriends;

import android.os.Handler;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

import edu.gatech.cs2340.willcode4money.shoppingwithfriends.Login;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.R;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.ShoppingWithFriends;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.User;


/**
 * Tests user login
 * Jake Williams
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<Login> {

    public LoginActivityTest() {
        super(Login.class);
    }

    private Login activity;
    private TextView mUserViewTesting;
    private TextView mPasswordViewTesting;
    private Map<String, User> usersTesting;

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        activity = getActivity();
    }

//    public void testUIElementsLogin() throws Exception {
//        Button login = (Button) activity.findViewById(R.id.signinBtn);
//        Button cancel = (Button) activity.findViewById(R.id.button9);
//        mUserViewTesting = (TextView) activity.findViewById(R.id.username);
//        mPasswordViewTesting = (TextView) activity.findViewById(R.id.password);
//        assertEquals("Incorrect label", "Sign In", login.getText());
//        assertEquals("Incorrect label", "Cancel", cancel.getText());
//        assertEquals("Incorrect hint", "Username", mUserViewTesting.getHint());
//        assertEquals("Incorrect hint", "Password", mPasswordViewTesting.getHint());
//    }

    public void testCancel() throws Exception {
        Button cancel = (Button) activity.findViewById(R.id.button9);
        TouchUtils.clickView(this, cancel);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {assertTrue(activity.isFinishing()); }
        }, 1200);
    }

    public void testLoginGood() throws  Exception {
        Button login = (Button) activity.findViewById(R.id.signinBtn);
        ((ShoppingWithFriends) activity.getApplication()).addUser(new User("", "", "", ""));
        TouchUtils.clickView(this, login);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {assertTrue(activity.isFinishing()); }
        }, 1200);
    }

    public void testLoginFail() throws Exception {
        Button login = (Button) activity.findViewById(R.id.signinBtn);
        mUserViewTesting = (TextView) activity.findViewById(R.id.username);
        mPasswordViewTesting = (TextView) activity.findViewById(R.id.password);

        if (((ShoppingWithFriends) activity.getApplication()).getUsers().get("") != null) {
            ((ShoppingWithFriends) activity.getApplication()).getUsers().remove("");
        }

        TouchUtils.clickView(this, login);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {assertFalse(activity.isFinishing()); }
        }, 1200);
    }
}
