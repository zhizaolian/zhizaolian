package nju.zhizaolian.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.CheckQuoteFragment;
import nju.zhizaolian.fragments.OrderListFragment;

public class SalesMasterActivity extends ActionBarActivity  {
    CheckQuoteFragment checkQuoteFragment=null;
    OrderListFragment orderListFragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_master);
        checkQuoteFragment=new CheckQuoteFragment();
        orderListFragment=new OrderListFragment();

        getFragmentManager().beginTransaction().replace(R.id.salesMasterContainer,orderListFragment).commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sales_master, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




}
