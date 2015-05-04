package nju.zhizaolian.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.Operation;

public class SalesMasterActivity extends ActionBarActivity  {

    private OrderListFragment orderListFragment=null;
    private Account account;
    private int selectedSpinnerItem=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_master);
        SpinnerAdapter spinnerAdapter= ArrayAdapter.createFromResource(this,
                R.array.sales_master_list, R.layout.support_simple_spinner_dropdown_item);
        ActionBar actionBar=getSupportActionBar();

        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);


        actionBar.setListNavigationCallbacks(spinnerAdapter, onNavigationListener);
        account= (Account) getIntent().getSerializableExtra("account");
        if(savedInstanceState == null){
            Bundle bundle=new Bundle();
            bundle.putSerializable("account",account);
            orderListFragment=new OrderListFragment();
            orderListFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.salesMasterContainer,orderListFragment).commit();
        }


    }
    ActionBar.OnNavigationListener onNavigationListener=new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            switch (itemPosition){
                case 0:
                    selectedSpinnerItem=0;
                    orderListFragment.getListViewByURLAndOperation("/fmc/market/mobile_verifyQuoteList.do", Operation.CHECKQUOTE);
                    break;
                case 1:
//                    selectedSpinnerItem=1;
//                    orderListFragment.getListViewByURLAndOperation("/fmc/market/mobile_verifyAlterList.do", Operation.CHANGEEMPLOYEE);
                    Toast.makeText(SalesMasterActivity.this,"不可用",Toast.LENGTH_SHORT).show();
                    break;
            }


            return false;



        }
    };

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
                case 0:
                    orderListFragment.getListViewByURLAndOperation("/fmc/market/mobile_verifyQuoteList.do", Operation.CHECKQUOTE);
                    break;
//                case 1:
//                    orderListFragment.getListViewByURLAndOperation("/fmc/market/mobile_verifyAlterList.do", Operation.CHANGEEMPLOYEE);
//                    break;
                default:
                    break;

            }

        }
    }
}
