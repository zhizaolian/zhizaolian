package nju.zhizaolian.activities;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.ChangeQuoteFragment;
import nju.zhizaolian.fragments.CustomListOrderFragment;
import nju.zhizaolian.fragments.MergePriceFragment;
import nju.zhizaolian.fragments.QuoteAgreedFragment;
import nju.zhizaolian.fragments.SignContractFragment;
import nju.zhizaolian.models.Account;

public class DepartmentSalesActivity extends ActionBarActivity {
    private Account account=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_department);
        SpinnerAdapter spinnerAdapter= ArrayAdapter.createFromResource(this,
                R.array.sales_list,R.layout.support_simple_spinner_dropdown_item);
        ActionBar actionBar=getSupportActionBar();
        account= (Account) getIntent().getSerializableExtra("account");
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(spinnerAdapter, onNavigationListener);
    }

    ActionBar.OnNavigationListener onNavigationListener =new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            Fragment newFragment=null;
            switch (itemPosition){
                case 0:newFragment=new CustomListOrderFragment(account);break;
                case 1:newFragment=new MergePriceFragment();break;
                case 2:newFragment=new MergePriceFragment();break;
                case 3:newFragment=new QuoteAgreedFragment();break;
                case 4:newFragment=new ChangeQuoteFragment();break;
                case 5:newFragment=new SignContractFragment();break;
                case 6:newFragment=new MergePriceFragment();break;
                case 7:newFragment=new MergePriceFragment();break;
                default:break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.salesDepartmentcontainers,newFragment).commit();
            return false;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sales_department, menu);
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
