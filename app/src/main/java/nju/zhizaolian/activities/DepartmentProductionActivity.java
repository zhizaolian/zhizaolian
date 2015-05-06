package nju.zhizaolian.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.DepartmentProductionCheckFragment;
import nju.zhizaolian.fragments.DepartmentProductionMassFragment;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.Operation;
import nju.zhizaolian.models.TaskNumber;

public class DepartmentProductionActivity extends ActionBarActivity {

    OrderListFragment orderListFragment;
    public static final int CHECK=0;
    public static final int MASS=1;
    int selectedSpinnerItem = CHECK;
    ArrayList<String> itemList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_production_activity_layout);
        Account account =(Account) getIntent().getSerializableExtra("account");
        TaskNumber taskNumber =(TaskNumber) getIntent().getSerializableExtra("taskNumber");
        itemList = new ArrayList<>();
        itemList.add("成本验证核算("+taskNumber.getComputeProduceCost()+")");
        itemList.add("大货批量生产("+taskNumber.getProduce()+")");
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
            fragmentTransaction.add(R.id.department_production_activity_layout,orderListFragment,"orderList");
            fragmentTransaction.commit();
        }
    }

    ActionBar.OnNavigationListener onNavigationListener = new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            switch (itemPosition){
                case CHECK:
                    getProductionCheckList();
                    selectedSpinnerItem=CHECK;
                    break;
                case MASS:
                    getProductionMassList();
                    selectedSpinnerItem=MASS;
                    break;
                default:break;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_production, menu);
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

    public void getProductionCheckList(){
        String url ="/fmc/produce/mobile_computeProduceCostList.do";
        orderListFragment.getListViewByURLAndOperation(url, Operation.PRODUCTION_CHECK);
    }

    public void getProductionMassList(){
        String url ="/fmc/produce/mobile_produceList.do";
        orderListFragment.getListViewByURLAndOperation(url, Operation.PRODUCTION_MASS);
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
                case CHECK:
                    getProductionCheckList();
                    break;
                case MASS:
                    getProductionMassList();
                    break;
                default:
                    break;

            }
            updateTaskNumberFromServer();

        }
    }

    public void updateTaskNumberFromServer(){
        SharedPreferences settings = getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("jsessionId",jSessionId);
        asyncHttpClient.get(IPAddress.getIP()+"/fmc/common/mobile_getTaskNumber.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if(response.has("notify")){
                    Toast.makeText(DepartmentProductionActivity.this, "登陆超时，退出重试", Toast.LENGTH_SHORT).show();
                }else {
                    TaskNumber taskNumber = TaskNumber.fromJson(response);
                    if (taskNumber == null) {
                        Toast.makeText(DepartmentProductionActivity.this, "服务器错误，稍后重试", Toast.LENGTH_SHORT).show();
                    } else {
                        itemList.clear();
                        itemList.add("成本验证核算("+taskNumber.getComputeProduceCost()+")");
                        itemList.add("大货批量生产("+taskNumber.getProduce()+")");
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(DepartmentProductionActivity.this,"网络连接错误，稍后重试",Toast.LENGTH_SHORT).show();
                Log.e("error", "error", throwable);
            }
        });
    }
}
