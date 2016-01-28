package willcode4money.cs2340.gatech.edu.shoppingwithfriends;

import android.os.Handler;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import edu.gatech.cs2340.willcode4money.shoppingwithfriends.AddFriend;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.R;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.ShoppingWithFriends;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.User;


/**
 * Tests user addFriend
 * Hanbeen Kim
 */
public class UserAddFriendTest extends ActivityInstrumentationTestCase2<AddFriend> {

    public UserAddFriendTest() {
        super(AddFriend.class);
    }

    private AddFriend activity;
    TextView friendName;
    TextView friendEmail;

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        activity = getActivity();
    }


    public void testAddFriendGood() throws  Exception {
        Button addFriend = (Button) activity.findViewById(R.id.button4);
        friendName = (TextView) activity.findViewById(R.id.addFriend_name);
        friendEmail = (TextView) activity.findViewById(R.id.addFriend_email);
        TextView[] arr = {friendName, friendEmail};

        User userFriend = new User("k","k","k","k");

        for (final TextView t : arr) {
            activity.runOnUiThread(
                    new Runnable() {
                        public void run() {
                            t.requestFocus();
                        } // end of run() method definition
                    } // end of anonymous Runnable object instantiation
            );
            this.sendKeys(KeyEvent.KEYCODE_K);
        }

        if (((ShoppingWithFriends) activity.getApplication()).getUsers().get("k") ==  userFriend &&
                ((ShoppingWithFriends) activity.getApplication()).getUsers().get("k").getEmail() == userFriend.getEmail()) {
            TouchUtils.clickView(this, addFriend);
        }

        if(((ShoppingWithFriends) activity.getApplication()).getUsers().get("").getFriends().contains("k") &&
        ((ShoppingWithFriends) activity.getApplication()).getUsers().get("k").getFriends().contains("")) {
            ((ShoppingWithFriends) activity.getApplication()).getUsers().get("").getFriends().remove("k");
            ((ShoppingWithFriends) activity.getApplication()).getUsers().get("k").getFriends().remove("");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {assertTrue(activity.isFinishing()); }
            }, 1200);
        }
    }

    public void testAddFriendFail() throws Exception {
        Button addFriend = (Button) activity.findViewById(R.id.button4);
        friendName = (TextView) activity.findViewById(R.id.addFriend_name);
        friendEmail = (TextView) activity.findViewById(R.id.addFriend_email);
        TextView[] arr = {friendName, friendEmail};

        for (final TextView t : arr) {
            activity.runOnUiThread(
                    new Runnable() {
                        public void run() {
                            t.requestFocus();
                        } // end of run() method definition
                    } // end of anonymous Runnable object instantiation
            );
            this.sendKeys(KeyEvent.KEYCODE_K);
            this.sendKeys(KeyEvent.KEYCODE_AT);
            this.sendKeys(KeyEvent.KEYCODE_K);
            this.sendKeys(KeyEvent.KEYCODE_PERIOD);
            this.sendKeys(KeyEvent.KEYCODE_C);
            this.sendKeys(KeyEvent.KEYCODE_O);
            this.sendKeys(KeyEvent.KEYCODE_M);
        }

        if (((ShoppingWithFriends) activity.getApplication()).getUsers().get("k") !=  null &&
                ((ShoppingWithFriends) activity.getApplication()).getUsers().get("k").getEmail() != null) {
            ((ShoppingWithFriends) activity.getApplication()).getUsers().remove("k");
        }
        TouchUtils.clickView(this, addFriend);

        if(((ShoppingWithFriends) activity.getApplication()).getUsers().get("").getFriends().size() == 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {assertTrue(activity.isFinishing()); }
            }, 1200);
        }
    }
}