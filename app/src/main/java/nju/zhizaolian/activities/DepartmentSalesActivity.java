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

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.CustomListOrderFragment;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Operation;
import nju.zhizaolian.models.TaskNumber;

public class DepartmentSalesActivity extends ActionBarActivity   {
    private Account account=null;
    OrderListFragment orderListFragment;
    public static final int CUSTOMLIST=0;
    public static final int CHANGEORDER=1;
    public static  final int MERGEPRICE=2;
    public static final int QUOTEAGREES=3;
    public static final int CHANGEQUOTE=4;
    public static final int SIGNCONTRACT=5;
    public static final int URGEREMAININGMONEY=6;
    //public static final int CHANGECONTRACT=7;
    ArrayList<String> itemList;
    ArrayAdapter<String> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_department);
        account= (Account) getIntent().getSerializableExtra("account");
        TaskNumber taskNumber= (TaskNumber) getIntent().getSerializableExtra("taskNumber");
        itemList = new ArrayList<>();
        itemList.add("客户下单");
        itemList.add("修改询单("+taskNumber.getModifyOrder()+")");
        itemList.add("合并报价("+taskNumber.getMergeQuote()+")");
        itemList.add("报价商定("+taskNumber.getConfirmQuote()+")");
        itemList.add("修改报价("+taskNumber.getModifyQuote()+")");
        itemList.add("签订合同("+taskNumber.getConfirmProduceOrder()+")");
        itemList.add("催尾款("+taskNumber.getPushRest()+")");
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,itemList);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(arrayAdapter, onNavigationListener);
        if(savedInstanceState == null){
            Bundle bundle=new Bundle();
            bundle.putSerializable("account",account);
            orderListFragment=new OrderListFragment();
            orderListFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.salesDepartmentcontainers,orderListFragment).commit();
        }




    }

    ActionBar.OnNavigationListener onNavigationListener =new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId) {

            switch (itemPosition){
                case CUSTOMLIST:Fragment newFragment=new CustomListOrderFragment(account);

                       getFragmentManager().beginTransaction().replace(R.id.salesDepartmentcontainers,newFragment).addToBackStack(null).commit();

                    break;
                case CHANGEORDER:Toast.makeText(DepartmentSalesActivity.this, "请在电脑上操作....", Toast.LENGTH_SHORT).show();

                    break;

                case MERGEPRICE: orderListFragment.getListViewByURLAndOperation("/fmc/market/mobile_mergeQuoteList.do", Operation.MERGEPRICE);

                    break;


                case QUOTEAGREES:orderListFragment.getListViewByURLAndOperation("/fmc/market/mobile_confirmQuoteList.do",Operation.QUOTEAGREED);break;
                case CHANGEQUOTE:orderListFragment.getListViewByURLAndOperation("/fmc/market/mobile_mobile_modifyQuoteList.do",Operation.CHANGEQUOTE);break;
                case SIGNCONTRACT:orderListFragment.getListViewByURLAndOperation("/fmc/market/mobile_confirmProduceOrderList.do",Operation.SIGNCONTRACT);break;
                case URGEREMAININGMONEY:orderListFragment.getListViewByURLAndOperation("/fmc/market/mobile_getPushRestOrderList.do",Operation.URGEREMAININGBALANCE);break;
               // case 7:Toast.makeText(DepartmentSalesActivity.this, "暂不可用", Toast.LENGTH_SHORT).show();break;
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
