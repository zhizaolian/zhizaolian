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

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.Operation;
import nju.zhizaolian.models.TaskNumber;

public class DepartmentSweaterMakerActivity extends ActionBarActivity {

    OrderListFragment orderListFragment;
    public static final int SAMPLE=0;
    public static final int SEND=1;
    int selectedSpinnerItem = SAMPLE;
    ArrayList<String> itemList;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_sweater_maker_activity_layout);
        Account account =(Account) getIntent().getSerializableExtra("account");
        TaskNumber taskNumber =(TaskNumber) getIntent().getSerializableExtra("taskNumber");
        itemList = new ArrayList<>();
        itemList.add("样衣工艺("+taskNumber.getConfirmSweaterSampleAndCraft()+")");
        itemList.add("毛衣外发("+taskNumber.getSendSweater()+")");
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
            fragmentTransaction.add(R.id.department_sweater_maker_activity_layout,orderListFragment,"orderList");
            fragmentTransaction.commit();
        }
    }

    ActionBar.OnNavigationListener onNavigationListener = new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            switch (itemPosition){
                case SAMPLE:
                    getSweaterMakerSampleList();
                    selectedSpinnerItem=SAMPLE;
                    break;
                case SEND:
                    getSweaterMakerSendList();
                    selectedSpinnerItem=SEND;
                    break;
                default:break;
            }
            return false;
        }
    };

    public void getSweaterMakerSampleList(){
        String url = "/fmc/sweater/mobile_sweaterSampleAndCraftList.do";
        orderListFragment.getListViewByURLAndOperation(url, Operation.SWEATER_MAKER_CHECK);
    }

    public void getSweaterMakerSendList(){
        String url = "/fmc/sweater/mobile_sendSweaterList.do";
        orderListFragment.getListViewByURLAndOperation(url, Operation.SWEATER_MAKER_SENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_sweater_maker, menu);
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
                case SAMPLE:
                    getSweaterMakerSampleList();
                    break;
                case SEND:
                    getSweaterMakerSendList();
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
                    Toast.makeText(DepartmentSweaterMakerActivity.this, "登陆超时，退出重试", Toast.LENGTH_SHORT).show();
                }else {
                    TaskNumber taskNumber = TaskNumber.fromJson(response);
                    if (taskNumber == null) {
                        Toast.makeText(DepartmentSweaterMakerActivity.this, "服务器错误，稍后重试", Toast.LENGTH_SHORT).show();
                    } else {
                        itemList.clear();
                        itemList.add("样衣工艺("+taskNumber.getConfirmSweaterSampleAndCraft()+")");
                        itemList.add("毛衣外发("+taskNumber.getSendSweater()+")");
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(DepartmentSweaterMakerActivity.this,"网络连接错误，稍后重试",Toast.LENGTH_SHORT).show();
                Log.e("error", "error", throwable);
            }
        });
    }
}
