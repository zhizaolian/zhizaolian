package nju.zhizaolian.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.CustomerEditFragment;
import nju.zhizaolian.fragments.CustomerListFragment;
import nju.zhizaolian.fragments.CustomerRegisterFragment;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Custom;
import nju.zhizaolian.models.CustomerManagerInfo;
import nju.zhizaolian.models.IPAddress;

public class CustomerManagerActivity extends Activity implements
        CustomerRegisterFragment.CustomerRegisterButtonClickListener,
        CustomerRegisterFragment.CustomerCancelButtonClickListener,
        CustomerEditFragment.CustomerEditButtonClickListener,
        CustomerEditFragment.CustomerCancelButtonClickListener,
        CustomerListFragment.CustomerGoToRegisterFragmentButtonClickListener,
        CustomerListFragment.CustomerGoToEditFragmentButtonClickListener,
        CustomerListFragment.CustomerDeleteButtonClickListener{

    private CustomerEditFragment customerEditFragment;
    private CustomerListFragment customerListFragment;
    private CustomerRegisterFragment customerRegisterFragment;
    private Custom custom;
    private Account account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_manager_layout);

        if(savedInstanceState==null) {
            customerListFragment = new CustomerListFragment();
            if(!customerListFragment.isAdded()) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.customer_manager_content, customerListFragment, "ONE");
                fragmentTransaction.commit();
            }
        }
    }


    @Override
    public void customerCancelButtonClicked() {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
    }

    @Override
    public void customerRegisterButtonClicked(CustomerManagerInfo info) {
        addCustomerFromServer(info);
    }

    @Override
    public void customerEditButtonClicked(CustomerManagerInfo info) {
        editCustomerFromServer(info);
    }

    @Override
    public boolean customerDeleteButtonClicked(String id) {
        deleteCustomerFromServer(id);
        return false;
    }

    @Override
    public void customerGoToEditFragmentButtonClicked(final String id) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("customer_id",id);
        client.get(IPAddress.getIP() + "/fmc/account/mobile_modifyCustomerDetail.do", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    custom=Custom.fromJson(response.getJSONObject("customer_to_modify"));
                    account =Account.fromJson(response.getJSONObject("account_to_modify"));
                    CustomerManagerInfo info = CustomerManagerInfo.getCustomerManagerInfoFromAccountAndCustomer(custom,account);
                    info.setUser_id(id);
                    customerGoToEditFragment(info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(CustomerManagerActivity.this, "网络连接错误，稍后再试", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void customerGoToEditFragment(CustomerManagerInfo info){
        if(customerEditFragment==null){
            customerEditFragment = new CustomerEditFragment();
        }
        if(!customerEditFragment.isAdded()) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("info",info);
            customerEditFragment.setArguments(bundle);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.customer_manager_content, customerEditFragment, "TWO");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void customerGoToRegisterFragmentButtonClicked() {
        if(customerRegisterFragment==null){
            customerRegisterFragment = new CustomerRegisterFragment();
        }
        if(!customerRegisterFragment.isAdded()) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.customer_manager_content, customerRegisterFragment, "THREE");
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void deleteCustomerFromServer(final String id){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("customer_id",id);
        client.get(IPAddress.getIP() + "/fmc/account/mobile_deleteCustomerSubmit.do", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                customerListFragment.notifyDeleteDataSetChanged(id);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(CustomerManagerActivity.this, "网络连接错误，稍后再试", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void addCustomerFromServer(CustomerManagerInfo info){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("company_name",info.getCompany_name());
        params.put("customer_name",info.getCustomer_name());
        params.put("user_name",info.getUser_name());
        params.put("user_password",info.getUser_password());
        params.put("customer_phone",info.getCustomer_phone());
        params.put("buy_constact",info.getBuy_contact());
        params.put("buy_contact",info.getBuy_contact());
        params.put("contact_phone_1",info.getContact_phone_1());
        params.put("register_date",info.getRegister_date());
        params.put("company_id","");
        params.put("province","");
        params.put("city","");
        params.put("website_url","");
        params.put("website_type","独立网站");
        params.put("company_address","");
        params.put("company_fax","");
        params.put("company_phone","");
        params.put("contact_phone_2","");
        params.put("qq","");
        params.put("email","");
        params.put("boss_name","");
        params.put("boss_phone","");
        client.post(IPAddress.getIP() + "/fmc/account/mobile_addCustomerSubmit.do", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if(response.toString().contains("\"isSuccess\":false")){
                    Toast.makeText(CustomerManagerActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                    customerRegisterFragment.notifyUserNameEditTextSetError();
                }else {
                    Toast.makeText(CustomerManagerActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.popBackStack();
                    customerRegisterFragment.notifyEditTextSetNull();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(CustomerManagerActivity.this, "网络连接错误，稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void editCustomerFromServer(CustomerManagerInfo info){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("customer_id",custom.getCustomerId());
        params.put("company_id",custom.getCompanyId());
        params.put("company_name",info.getCompany_name());
        params.put("customer_name",info.getCustomer_name());
        params.put("province",custom.getProvince());
        params.put("city",custom.getCity());
        params.put("website_url",custom.getWebsiteUrl());
        params.put("website_type",custom.getWebsiteType());
        params.put("company_address",custom.getCompanyAddress());
        params.put("company_fax",custom.getCompanyFax());
        params.put("company_phone",custom.getCompanyPhone());
        params.put("buy_constact",info.getBuy_contact());
        params.put("contact_phone_1",info.getContact_phone_1());
        params.put("contact_phone_2",custom.getContactPhone2());
        params.put("qq",custom.getQq());
        params.put("email",custom.getEmail());
        params.put("customer_phone",info.getCustomer_phone());
        params.put("boss_name",custom.getBossName());
        params.put("boss_phone",custom.getBossPhone());
        params.put("register_employee_id",custom.getRegisterEmployeeId());
        params.put("password",info.getUser_password());
        client.post(IPAddress.getIP() + "/fmc/account/mobile_modifyCustomerSubmit.do", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    Toast.makeText(CustomerManagerActivity.this, "编辑成功", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.popBackStack();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(CustomerManagerActivity.this, "网络连接错误，稍后再试", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
