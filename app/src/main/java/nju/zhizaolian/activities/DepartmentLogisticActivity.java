package nju.zhizaolian.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.fragments.RegisterAndScanningFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Operation;
import nju.zhizaolian.models.TaskNumber;

public class DepartmentLogisticActivity extends ActionBarActivity {
    private OrderListFragment orderListFragment;
    private Account account;
    TaskNumber taskNumber;
    ArrayList<String> itemList;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_logistic);
        taskNumber = (TaskNumber) getIntent().getSerializableExtra("taskNumber");
        itemList = new ArrayList<>();
        itemList.add("样衣收取("+taskNumber.getReceiveSample()+")");
        itemList.add("样衣发货("+taskNumber.getSendSample()+")");
        itemList.add("产品入库("+taskNumber.getWarehouse()+")");
        itemList.add("产品发货("+taskNumber.getSendClothes()+")");
        itemList.add("入库登记发货扫描");
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,itemList);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(arrayAdapter,onNavigationListener);
        account= (Account) getIntent().getSerializableExtra("account");
        if(savedInstanceState == null){

            Bundle bundle=new Bundle();
            bundle.putSerializable("account",account);
            orderListFragment=new OrderListFragment();
            orderListFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.logisticContainer,orderListFragment).commit();
        }
    }
    ActionBar.OnNavigationListener onNavigationListener=new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int i, long l) {

            switch (i){
                case 0:
                    orderListFragment.getListViewByURLAndOperation("/fmc/logistics/mobile_receiveSampleList.do", Operation.RECEIVESAMPLE);
                    break;
                case 1:orderListFragment.getListViewByURLAndOperation("/fmc/logistics/mobile_sendSampleList.do",Operation.DELIVERSAMPLE);break;
                case 2:orderListFragment.getListViewByURLAndOperation("/fmc/logistics/mobile_warehouseList.do",Operation.WAREHOUSE);break;

                case 3:orderListFragment.getListViewByURLAndOperation("/fmc/logistics/mobile_sendClothesList.do",Operation.SENDCLOTHES);break;

                case 4:
                    Fragment newFragment=new RegisterAndScanningFragment();
                    getFragmentManager().beginTransaction().replace(R.id.logisticContainer,newFragment).addToBackStack(null).commit();
                    break;
                default:break;
            }

            return false;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_logistic, menu);
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
