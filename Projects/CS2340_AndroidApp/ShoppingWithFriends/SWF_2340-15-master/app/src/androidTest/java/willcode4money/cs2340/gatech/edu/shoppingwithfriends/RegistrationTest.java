package willcode4money.cs2340.gatech.edu.shoppingwithfriends;

import android.os.Handler;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;

import edu.gatech.cs2340.willcode4money.shoppingwithfriends.R;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.Register;

/**
 * Tests new user Registration.
 * Kyle Rabago-Banjo
 */
public class RegistrationTest extends ActivityInstrumentationTestCase2<Register> {

    public RegistrationTest() {
        super(Register.class);
    }

    private Register activity;
    TextView uBox;
    TextView name;
    TextView addr;
    TextView pw;
    TextView pwc;

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        activity = getActivity();

    }

    public void testUIElements() throws Exception {
        Button reg = (Button) activity.findViewById(R.id.registerBtn);
        Button can = (Button) activity.findViewById(R.id.cancelBtn);
        uBox = (TextView) activity.findViewById(R.id.usernameET);
        name = (TextView) activity.findViewById(R.id.nameText);
        addr = (TextView) activity.findViewById(R.id.emailText);
        pw = (TextView) activity.findViewById(R.id.passwordET);
        pwc = (TextView) activity.findViewById(R.id.passwordConfirmET);
        assertEquals("Incorrect label", "Register", reg.getText());
        assertEquals("Incorrect label", "Cancel", can.getText());
        assertEquals("Incorrect hint", "Username", uBox.getHint());
        assertEquals("Incorrect hint", "Name", name.getHint());
        assertEquals("Incorrect hint", "Email Address", addr.getHint());
        assertEquals("Incorrect hint", "Password", pw.getHint());
        assertEquals("Incorrect hint", "Confirm Password", pwc.getHint());
    }

    public void testFullBlank() throws Exception {
        Button reg = (Button) activity.findViewById(R.id.registerBtn);
        TouchUtils.clickView(this, reg);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { assertTrue(activity.isFinishing()); }
        }, 1200);
    }

    public void testGood() throws Exception {
        Button reg = (Button) activity.findViewById(R.id.registerBtn);
        uBox = (TextView) activity.findViewById(R.id.usernameET);
        name = (TextView) activity.findViewById(R.id.nameText);
        addr = (TextView) activity.findViewById(R.id.emailText);
        pw = (TextView) activity.findViewById(R.id.passwordET);
        pwc = (TextView) activity.findViewById(R.id.passwordConfirmET);
        TextView[] arr = {uBox, name, addr, pw, pwc};

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

        TouchUtils.clickView(this, reg);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {assertTrue(activity.isFinishing()); }
        }, 1200);
    }

    public void testMismatch() throws Exception {
        Button reg = (Button) activity.findViewById(R.id.registerBtn);
        uBox = (TextView) activity.findViewById(R.id.usernameET);
        name = (TextView) activity.findViewById(R.id.nameText);
        addr = (TextView) activity.findViewById(R.id.emailText);
        pw = (TextView) activity.findViewById(R.id.passwordET);
        pwc = (TextView) activity.findViewById(R.id.passwordConfirmET);
        TextView[] arr = {uBox, name, addr, pw, pwc};

        for (final TextView t : arr) {
            activity.runOnUiThread(
                    new Runnable() {
                        public void run() {
                            t.requestFocus();
                        } // end of run() method definition
                    } // end of anonymous Runnable object instantiation
            );
            if (t != pwc) {
                this.sendKeys(KeyEvent.KEYCODE_K);
                this.sendKeys(KeyEvent.KEYCODE_AT);
                this.sendKeys(KeyEvent.KEYCODE_K);
                this.sendKeys(KeyEvent.KEYCODE_PERIOD);
                this.sendKeys(KeyEvent.KEYCODE_C);
                this.sendKeys(KeyEvent.KEYCODE_O);
                this.sendKeys(KeyEvent.KEYCODE_M);
            } else {
                this.sendKeys(KeyEvent.KEYCODE_A);
            }
        }

        TouchUtils.clickView(this, reg);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { assertFalse(activity.isFinishing()); }
        }, 1200);
    }
}