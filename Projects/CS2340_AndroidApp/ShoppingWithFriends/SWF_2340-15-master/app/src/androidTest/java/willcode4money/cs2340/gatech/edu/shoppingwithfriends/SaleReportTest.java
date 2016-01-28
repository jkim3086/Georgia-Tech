package willcode4money.cs2340.gatech.edu.shoppingwithfriends;

import android.os.Handler;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.TextView;
import junit.framework.Assert;
import java.util.ArrayList;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.R;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.SaleReport;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.SaleRequest;
import edu.gatech.cs2340.willcode4money.shoppingwithfriends.ShoppingWithFriends;

/**
 * Created by Jeongsoo on 4/2/2015.
 */
public class SaleReportTest extends ActivityInstrumentationTestCase2<SaleReport> {
    private TextView Item;
    private TextView Price;
    private TextView Location;
    private Button ADD;
    private Button CAN;

    public SaleReportTest(){
        super(SaleReport.class);
    }
    private SaleReport activity;
    protected void setUp() throws Exception{
        super.setUp();
        setActivityInitialTouchMode(true);
        activity = getActivity();
       /* ADD = (Button) activity.findViewById(R.id.reportBtn);
        CAN = (Button) activity.findViewById(R.id.cancelBtn);
        Item = (TextView) activity.findViewById(R.id.itemReport);
        Price = (TextView) activity.findViewById(R.id.priceReport);
        Location = (TextView) activity.findViewById(R.id.locationReport);*/
    }


    public void testComponent() throws Exception{
        ADD = (Button) activity.findViewById(R.id.reportBtn);
        CAN = (Button) activity.findViewById(R.id.cancelBtn);
        Item = (TextView) activity.findViewById(R.id.itemReport);
        Price = (TextView) activity.findViewById(R.id.priceReport);
        Location = (TextView) activity.findViewById(R.id.locationReport);
        assertEquals("Incorrect label", "Add Report", ADD.getText());
        assertEquals("Incorrect label", "Cancel", CAN.getText());
        assertEquals("Incorrect hint", "Item", Item.getHint());
        assertEquals("Incorrect hint", "Price", Price.getHint());
        assertEquals("Incorrect hint", "Location", Location.getHint());
    }

    public void testCancel() throws Exception{
        CAN = (Button) activity.findViewById(R.id.cancelBtn);
        TouchUtils.clickView(this, CAN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() { assertTrue(activity.isFinishing()); }
        }, 1200);
    }

    public void testReportGood() throws Exception {
        String testItem = "Pen";
        String testLocation = "Walmart";
        Double testPrice = (double) 10;
        String testOwner = "John";
        SaleReport testRepo = new SaleReport(testOwner,testItem,testPrice,testLocation);

        if (!testRepo.getItem().equals("") && !testRepo.getLocation().equals("")) {
            if (((ShoppingWithFriends) activity.getApplication()).getReportBucket().containsKey(testRepo.getItem())) {
                ((ShoppingWithFriends) activity.getApplication()).getReportBucket().get(testItem).add(testRepo);
            } else {
                ArrayList<SaleReport> newBucket = new ArrayList<>();
                newBucket.add(testRepo);
                ((ShoppingWithFriends) activity.getApplication()).getReportBucket().put(testRepo.getItem(), newBucket);
            }
        }

        boolean test1 = ((ShoppingWithFriends) activity.getApplication()).getReportBucket().get(testItem).contains(testRepo);
        Assert.assertTrue(test1);
    }

    public void testReportBad() throws Exception {
        String testItem = "";
        String testLocation = "";
        Double testPrice = (double) 10;
        String testOwner = "John";
        SaleReport testRepo = new SaleReport(testOwner,testItem,testPrice,testLocation);

        if (!testRepo.getItem().equals("") && !testRepo.getLocation().equals("")) {
            if (((ShoppingWithFriends) activity.getApplication()).getReportBucket().containsKey(testRepo.getItem())) {
                ((ShoppingWithFriends) activity.getApplication()).getReportBucket().get(testItem).add(testRepo);
            } else {
                ArrayList<SaleReport> newBucket = new ArrayList<>();
                newBucket.add(testRepo);
                ((ShoppingWithFriends) activity.getApplication()).getReportBucket().put(testRepo.getItem(), newBucket);
            }
        }

        boolean test1 = ((ShoppingWithFriends) activity.getApplication()).getReportBucket().containsKey(testRepo.getItem());
        Assert.assertFalse(test1);
    }

    public void testEqualGood() throws Exception{
        SaleReport testReport = new SaleReport("John", "JAM", 15, "Walmart");
        SaleReport testReport2 = new SaleReport("Smith", "JAM", 15, "Walmart");
        boolean test2 = testReport2.equals(testReport);
        Assert.assertTrue(test2);
    }

    public void testEqualBad() throws Exception{
        SaleRequest testRequest = new SaleRequest("John", "Pen", 10);
        boolean test1 = activity.equals(testRequest);
        Assert.assertFalse(test1);
    }
}