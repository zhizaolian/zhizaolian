package nju.zhizaolian.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Operation;

public class OrderListActivity extends ActionBarActivity {
    OrderListFragment orderListFragment;
    public static final int ORDER_ALL =0;
    public static final int ORDER_DOING=1;
    public static final int ORDER_DONE=2;
    public static final int ORDER_ABORT=3;
    int selectedSpinnerItem = ORDER_ALL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        Account account =(Account) getIntent().getSerializableExtra("account");
        SpinnerAdapter spinnerAdapter= ArrayAdapter.createFromResource(this,
                R.array.order_manager_option_list, R.layout.support_simple_spinner_dropdown_item);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(spinnerAdapter, onNavigationListener);
        if(savedInstanceState==null){
            orderListFragment = new OrderListFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.activity_order_list_layout,orderListFragment,"orderList");
            fragmentTransaction.commit();
        }
    }


    ActionBar.OnNavigationListener onNavigationListener = new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            switch (itemPosition){
                case ORDER_ALL:
                    getOrderAllList();
                    selectedSpinnerItem=ORDER_ALL;
                    break;
                case ORDER_DOING:
                    getOrderDoingList();
                    selectedSpinnerItem=ORDER_DOING;
                    break;
                case ORDER_DONE:
                    getOrderDoneList();
                    selectedSpinnerItem=ORDER_DONE;
                    break;
                case ORDER_ABORT:
                    getOrderAbortList();
                    break;
                default:break;
            }
            return false;
        }
    };

    public void getOrderAllList(){
        String url ="/fmc/order/mobile_orderList.do";
        orderListFragment.getListViewByURLAndOperation(url,Operation.ORDER_MANAGER_DETAIL);
    }

    public void getOrderDoingList(){
        String url ="/fmc/order/mobile_orderListDoing.do";
        orderListFragment.getListViewByURLAndOperation(url,Operation.ORDER_MANAGER_DETAIL);
    }

    public void getOrderDoneList(){
        String url ="/fmc/order/mobile_orderListDone.do";
        orderListFragment.getListViewByURLAndOperation(url,Operation.ORDER_MANAGER_DETAIL);
    }

    public void getOrderAbortList(){
        String url ="/fmc/order/mobile_endList.do";
        orderListFragment.getListViewByURLAndOperation(url,Operation.ORDER_MANAGER_DETAIL);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order_list_activity_nemu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }



}
