package nju.zhizaolian.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.ChangeQuoteFragment;
import nju.zhizaolian.fragments.CustomListOrderFragment;
import nju.zhizaolian.fragments.MergePriceFragment;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.fragments.QuoteAgreedFragment;
import nju.zhizaolian.fragments.SignContractFragment;
import nju.zhizaolian.fragments.UrgeRemainingBalance;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Operation;
import nju.zhizaolian.models.TaskNumber;

public class DepartmentSalesActivity extends ActionBarActivity   {
    private Account account=null;
    OrderListFragment orderListFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_department);
        SpinnerAdapter spinnerAdapter= ArrayAdapter.createFromResource(this,
                R.array.sales_list,R.layout.support_simple_spinner_dropdown_item);
        ActionBar actionBar=getSupportActionBar();
        account= (Account) getIntent().getSerializableExtra("account");


        TaskNumber taskNumber= (TaskNumber) getIntent().getSerializableExtra("taskNumber");
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(spinnerAdapter, onNavigationListener);

        orderListFragment=new OrderListFragment();
        getFragmentManager().beginTransaction().replace(R.id.salesDepartmentcontainers,orderListFragment).commit();


    }

    ActionBar.OnNavigationListener onNavigationListener =new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            Fragment newFragment=new Fragment();
            switch (itemPosition){
                case 0:newFragment=new CustomListOrderFragment(account);break;
                case 1:Toast.makeText(DepartmentSalesActivity.this, "暂不可用", Toast.LENGTH_SHORT).show();break;

                case 2:newFragment=new MergePriceFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("operation", String.valueOf(Operation.MERGEPRICE));
                    newFragment.setArguments(bundle);
                    break;

                case 3:newFragment=new QuoteAgreedFragment();break;
                case 4:newFragment=new ChangeQuoteFragment();break;
                case 5:newFragment=new SignContractFragment();break;
                case 6:newFragment=new UrgeRemainingBalance();break;
                case 7:Toast.makeText(DepartmentSalesActivity.this, "暂不可用", Toast.LENGTH_SHORT).show();break;
                default:break;
            }



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
