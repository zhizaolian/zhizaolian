package nju.zhizaolian.activities;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.DepartmentDesignConfirmFragment;
import nju.zhizaolian.fragments.DepartmentDesignEnteringFragment;
import nju.zhizaolian.fragments.DepartmentDesignSliceFragment;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.TaskNumber;

public class DepartmentDesignActivity extends ActionBarActivity implements OrderListFragment.OrderListItemClickedToGoFragment {

    OrderListFragment orderListFragment;
    ArrayList<ListInfo> listInfoArrayList = new ArrayList<>();
    public static final int SAMPLE_PRODUCTION=0;
    public static final int SLICE=1;
    public static final int VERSION_CONFIRM=2;
    int selectedSpinnerItem = SAMPLE_PRODUCTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.department_design_activity_layout);
        Account account =(Account) getIntent().getSerializableExtra("account");
        TaskNumber taskNumber =(TaskNumber) getIntent().getSerializableExtra("taskNumber");
        SpinnerAdapter spinnerAdapter= ArrayAdapter.createFromResource(this,
                R.array.design_department_list, R.layout.support_simple_spinner_dropdown_item);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        actionBar.setListNavigationCallbacks(spinnerAdapter, onNavigationListener);
        if(savedInstanceState==null){
            orderListFragment = new OrderListFragment();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.department_design_activity_layout,orderListFragment,"orderList");
            fragmentTransaction.commit();
        }
    }

    ActionBar.OnNavigationListener onNavigationListener = new ActionBar.OnNavigationListener() {
        @Override
        public boolean onNavigationItemSelected(int itemPosition, long itemId) {
            switch (itemPosition){
                case SAMPLE_PRODUCTION:
                    getSampleProduceList();
                    selectedSpinnerItem=SAMPLE_PRODUCTION;
                    break;
                case SLICE:
                    getSliceList();
                    selectedSpinnerItem=SLICE;
                    break;
                case VERSION_CONFIRM:
                    getVersionConfirmList();
                    selectedSpinnerItem=VERSION_CONFIRM;
                    break;
                default:break;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_department_design, menu);
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

        }

        return super.onOptionsItemSelected(item);
    }

    public void getVersionConfirmList(){
        Toast.makeText(this,"版型",Toast.LENGTH_SHORT).show();
        String url = "/fmc/design/mobile_getConfirmCadList.do";
        updateListFromServer(url);
    }

    public void getSliceList(){
        Toast.makeText(this,"切片",Toast.LENGTH_SHORT).show();
        String url ="/fmc/design/mobile_getTypeSettingSliceList.do";
        updateListFromServer(url);
    }

    public void getSampleProduceList(){
        Toast.makeText(this,"样衣",Toast.LENGTH_SHORT).show();
        String url ="/fmc/design/mobile_getUploadDesignList.do";
        updateListFromServer(url);
    }

    public void updateListFromServer(final String url){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        client.get(IPAddress.getIP()+url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    listInfoArrayList =ListInfo.fromJson(response.getJSONArray("list"));
                    orderListFragment.updateListView(listInfoArrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(DepartmentDesignActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void goFragmentByOrderListItem(int index) {
        Fragment fragment = null;
        String tag="";
        switch (selectedSpinnerItem){
            case SAMPLE_PRODUCTION:
                fragment=new DepartmentDesignEnteringFragment();
                tag="Entering";
                break;
            case SLICE:
                fragment=new DepartmentDesignSliceFragment();
                tag="Slice";
                break;
            case VERSION_CONFIRM:
                fragment=new DepartmentDesignConfirmFragment();
                tag="Confirm";
                break;
            default:
                break;
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.department_design_activity_layout, fragment, tag);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        getSupportActionBar().hide();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(getFragmentManager().getBackStackEntryCount()>0) {
                getFragmentManager().popBackStack();
                getSupportActionBar().show();
                switch (selectedSpinnerItem){
                    case SAMPLE_PRODUCTION:
                        getSampleProduceList();
                        break;
                    case SLICE:
                        getSliceList();
                        break;
                    case VERSION_CONFIRM:
                        getVersionConfirmList();
                        break;
                    default:
                        break;

                }
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
