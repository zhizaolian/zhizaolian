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

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Operation;
import nju.zhizaolian.models.TaskNumber;

public class DepartmentPurchaseActivity extends ActionBarActivity {

    OrderListFragment orderListFragment;
    public static final int CONFIRM =0;
    public static final int SAMPLE =1;
    public static final int MASS =2;
    public static final int SWEATER =3;
    int selectedSpinnerItem = CONFIRM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_purchase_activity_layout);
        Account account =(Account) getIntent().getSerializableExtra("account");
        TaskNumber taskNumber =(TaskNumber) getIntent().getSerializableExtra("taskNumber");
        SpinnerAdapter spinnerAdapter= ArrayAdapter.createFromResource(this,
                R.array.purchase_department_list, R.layout.support_simple_spinner_dropdown_item);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(spinnerAdapter, onNavigationListener);
        if(savedInstanceState==null){
            orderListFragment = new OrderListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("account",account);
            orderListFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.department_purchase_activity_layout, orderListFragment, "orderList");
            fragmentTransaction.commit();
        }
    }

    ActionBar.OnNavigationListener onNavigationListener = new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            switch (itemPosition){
                case CONFIRM:
                    getPurchaseConfirmList();
                    selectedSpinnerItem= CONFIRM;
                    break;
                case MASS:
                    getPurchaseMassList();
                    selectedSpinnerItem= MASS;
                    break;
                case SAMPLE:
                    getPurchaseSampleList();
                    selectedSpinnerItem= SAMPLE;
                    break;
                case SWEATER:
                    getPurchaseSweaterList();
                    selectedSpinnerItem=SWEATER;
                    break;
                default:break;
            }
            return false;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_purchase, menu);
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

    public void getPurchaseConfirmList(){
        String url = "/fmc/buy/mobile_computePurchaseCostList.do";
        orderListFragment.getListViewByURLAndOperation(url, Operation.PURCHASE_CHECK);
    }

    public void getPurchaseMassList(){
        String url = "/fmc/buy/mobile_purchaseMaterialList.do";
        orderListFragment.getListViewByURLAndOperation(url, Operation.PURCHASE_MASS);
    }

    public void getPurchaseSampleList(){
        String url = "/fmc/buy/mobile_purchaseSampleMaterialList.do";
        orderListFragment.getListViewByURLAndOperation(url, Operation.PURCHASE_SAMPLE);
    }

    public void getPurchaseSweaterList(){
        String url = "/fmc/buy/mobile_purchaseSweaterMaterialList.do";
        orderListFragment.getListViewByURLAndOperation(url, Operation.PURCHASE_SWEATER);
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
                case CONFIRM:
                    getPurchaseConfirmList();
                    break;
                case MASS:
                    getPurchaseMassList();
                    break;
                case SAMPLE:
                    getPurchaseSampleList();
                    break;
                case SWEATER:
                    getPurchaseSweaterList();
                    break;
                default:
                    break;

            }
        }
    }

}
