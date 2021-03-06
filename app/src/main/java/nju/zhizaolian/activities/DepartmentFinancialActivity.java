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
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Operation;
import nju.zhizaolian.models.TaskNumber;

public class DepartmentFinancialActivity extends ActionBarActivity {
    private OrderListFragment orderListFragment;
    private Account account;
    TaskNumber taskNumber;
    ArrayList<String> itemList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_financial);
        taskNumber = (TaskNumber) getIntent().getSerializableExtra("taskNumber");
        itemList = new ArrayList<>();
        itemList.add("样衣费确认("+taskNumber.getConfirmSampleMoney()+")");
        itemList.add("首定金确认("+taskNumber.getConfirmDeposit()+")");
        itemList.add("退还定金("+taskNumber.getReturnDeposit()+")");
        itemList.add("尾款金确认("+taskNumber.getConfirmFinalPayment()+")");
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,itemList);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(arrayAdapter,navigationListener);
        account= (Account) getIntent().getSerializableExtra("account");
        Bundle bundle=new Bundle();
        bundle.putSerializable("account",account);
        orderListFragment=new OrderListFragment();
        orderListFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.financialContainer,orderListFragment).commit();
    }
    ActionBar.OnNavigationListener navigationListener=new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int i, long l) {
            Fragment fragment=null;
            switch (i){
                case 0:orderListFragment.getListViewByURLAndOperation("/fmc/finance/mobile_confirmSampleMoneyList.do", Operation.CHECKSAMPLEBALANCE);break;
                case 1:orderListFragment.getListViewByURLAndOperation("/fmc/finance/mobile_confirmDepositList.do",Operation.CHECKFRONTMONEY);break;
                case 2: orderListFragment.getListViewByURLAndOperation("/fmc/finance/mobile_returnDepositList.do",Operation.RETURNMONEY);break;
                case 3:orderListFragment.getListViewByURLAndOperation("/fmc/finance/mobile_confirmFinalPaymentList.do",Operation.CHECKREMAININGBALANCE);break;


            }

            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_financial, menu);
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
