package edu.gatech.cs2340.willcode4money.shoppingwithfriends;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * User's requested item list with price
 */
public class ViewReports extends Activity {

    private SaleReport[] reportLIST;
    private int index;
    /**
     * Creates and displays the user's list of requested items
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        String currUser = ((ShoppingWithFriends) this.getApplication()).getCurrentUser();
        if (!((ShoppingWithFriends) this.getApplication()).getUsers().get(currUser).getAuth()) {
            finish();
        }

        int len = ((ShoppingWithFriends) this.getApplication()).getValidReports().size();
        reportLIST = new SaleReport[len];

        Object[] reportArray = ((ShoppingWithFriends) this.getApplication()).getValidReports().toArray();
        String[] reportList = new String[reportArray.length];

        for (int i = 0; i < reportArray.length; i++) {
            reportList[i] = reportArray[i].toString();
            reportLIST[i] = ((ShoppingWithFriends) this.getApplication()).getValidReports().get(i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_reports, reportList);
        ListView listview = (ListView) findViewById(R.id.report_list);
        listview.setAdapter(adapter);

        final Activity thisItem = this;
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {
                index = (int) id;
                Intent intent = new Intent(thisItem, SaleInfo.class);
                intent.putExtra("report_info",reportLIST[index]);
                startActivity(intent);
            }
        });
    }
}

