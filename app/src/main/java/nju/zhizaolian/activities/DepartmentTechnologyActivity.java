package nju.zhizaolian.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Operation;
import nju.zhizaolian.models.TaskNumber;

public class DepartmentTechnologyActivity extends ActionBarActivity {

    OrderListFragment orderListFragment;
    public static final int DESIGN =0;
    public static final int SAMPLE =1;
    public static final int MASS =2;
    int selectedSpinnerItem = DESIGN;
    ArrayList<String> itemList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_technology_activity_layout);
        Account account =(Account) getIntent().getSerializableExtra("account");
        TaskNumber taskNumber =(TaskNumber) getIntent().getSerializableExtra("taskNumber");
        itemList = new ArrayList<>();
        itemList.add("设计验证("+taskNumber.getComputeDesignCost()+")");
        itemList.add("样衣制作("+taskNumber.getCraftSample()+")");
        itemList.add("大货制作("+taskNumber.getCraft()+")");
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,itemList);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(arrayAdapter, onNavigationListener);
        if(savedInstanceState==null){
            orderListFragment = new OrderListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("account",account);
            orderListFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.department_technology_activity_layout, orderListFragment, "orderList");
            fragmentTransaction.commit();
        }
    }

    ActionBar.OnNavigationListener onNavigationListener = new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            switch (itemPosition){
                case DESIGN:
                    getTechnologyDesignList();
                    selectedSpinnerItem= DESIGN;
                    break;
                case MASS:
                    getTechnologyMassList();
                    selectedSpinnerItem= MASS;
                    break;
                case SAMPLE:
                    getTechnologySampleList();
                    selectedSpinnerItem= SAMPLE;
                    break;
                default:break;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_technology, menu);
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

    public void getTechnologyDesignList(){
        String url ="/fmc/design/mobile_computeDesignCostList.do";
        orderListFragment.getListViewByURLAndOperation(url, Operation.TECHNOLOGY_DESIGN);
    }

    public void getTechnologyMassList(){
        String url ="/fmc/design/mobile_getNeedCraftProductList.do";
        orderListFragment.getListViewByURLAndOperation(url, Operation.TECHNOLOGY_MASS);
    }

    public void getTechnologySampleList(){
        String url ="/fmc/design/mobile_getNeedCraftSampleList.do";
        orderListFragment.getListViewByURLAndOperation(url, Operation.TECHNOLOGY_SAMPLE);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(getFragmentManager().getBackStackEntryCount()>0) {
               goBack();
               return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void goBack(){
        if(getFragmentManager().getBackStackEntryCount()>0) {
            getFragmentManager().popBackStack();
            getSupportActionBar().show();
            switch (selectedSpinnerItem){
                case DESIGN:
                    getTechnologyDesignList();
                    break;
                case MASS:
                    getTechnologyMassList();
                    break;
                case SAMPLE:
                    getTechnologySampleList();
                    break;
                default:
                    break;

            }
        }
    }
}
