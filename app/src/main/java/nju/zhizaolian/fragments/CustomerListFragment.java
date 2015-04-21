package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;

import nju.zhizaolian.models.Custom;
import nju.zhizaolian.models.IPAddress;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerListFragment extends Fragment implements View.OnClickListener{
    private ListView listView;
    private Button registerButton;
    SimpleAdapter simpleAdapter;
    ArrayList<Custom> customers;
    CustomerListFragment mFragment;
    int itemClickNumber=0;
    ArrayList<HashMap<String,String>> dataList;




    private static final int ITEM_MODIFY = 1;
    private static final int ITEM_DELETE = 2;

    public CustomerListFragment() {
        // Required empty public constructor
    }

    public interface CustomerGoToEditFragmentButtonClickListener{
        void customerGoToEditFragmentButtonClicked(String id);
    }

    public interface  CustomerDeleteButtonClickListener{
        boolean customerDeleteButtonClicked(String id);
    }

    public interface CustomerGoToRegisterFragmentButtonClickListener{
        void customerGoToRegisterFragmentButtonClicked();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("选择操作");
        menu.add(0,ITEM_MODIFY,0,"编辑");
        menu.add(0,ITEM_DELETE,1,"删除");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        final String id =((TextView)info.targetView.findViewById(R.id.invisible_customer_id)).getText().toString();
        switch (item.getItemId()){
            case ITEM_DELETE:
                new AlertDialog.Builder(getActivity())
                        .setTitle("确认删除?")
                        .setMessage("该操作不可逆")
                        .setPositiveButton("嗯(⊙_⊙)",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((CustomerDeleteButtonClickListener) getActivity()).customerDeleteButtonClicked(id);
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("不(>_<)", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                break;
            case ITEM_MODIFY:
                ((CustomerGoToEditFragmentButtonClickListener) getActivity()).customerGoToEditFragmentButtonClicked(id);
                break;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragment=this;
        View view = inflater.inflate(R.layout.customer_list,container,false);
        listView = (ListView) view.findViewById(R.id.customer_info_list);
        registerForContextMenu(listView);
        fetchCustom();
        listView.setAdapter(simpleAdapter);
        registerButton=(Button) view.findViewById(R.id.customer_register_button);
        registerButton.setOnClickListener(this);
        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.customer_register_button:
                ((CustomerGoToRegisterFragmentButtonClickListener) getActivity()).customerGoToRegisterFragmentButtonClicked();
                break;
        }
    }


    public void  fetchCustom(){
        AsyncHttpClient client=new AsyncHttpClient();

        client.get(IPAddress.getIP()+"/fmc/account/mobile_customerList.do",new JsonHttpResponseHandler(){


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if (response.toString().contains("session overdue")){
                    Toast.makeText(mFragment.getActivity(),"登陆超时，请重新登陆",Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        JSONArray jsonArray = response.getJSONArray("customer_list");
                        ArrayList<Custom> customList = Custom.fromJson(jsonArray);
                        setListViewByCustomerList(customList);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d("success", response.toString());
                }
                }



            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error",responseString);
            }
        });

    }

    public void setListViewByCustomerList(ArrayList<Custom> customers){
        this.customers=customers;
        dataList = new ArrayList<>();
        for(Custom c:customers){
            HashMap<String,String> item = new HashMap<>();
            item.put("name",c.getCustomerName());
            item.put("phone",c.getCustomerPhone());
            item.put("address",c.getCompanyAddress());
            item.put("c_name",c.getCompanyName());
            item.put("c_phone",c.getCompanyPhone());
            item.put("id",""+c.getCustomerId());
            dataList.add(item);
        }
        simpleAdapter = new SimpleAdapter(getActivity(),dataList,R.layout.customer_list_item,
                new String[]{"name","phone","address","c_name","c_phone","id"},
                new int[]{R.id.customer_name_string,
                        R.id.customer_phone_string,
                        R.id.customer_address_string,
                        R.id.customer_company_name_string,
                        R.id.customer_company_phone_string,
                        R.id.invisible_customer_id});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(itemClickNumber<2) {
                    Toast.makeText(getActivity(), "长按显示操作", Toast.LENGTH_SHORT).show();
                    itemClickNumber++;
                }

            }
        });
    }

    public void notifyDeleteDataSetChanged(String id){
        int index=0;
        for(int i=0;i<dataList.size();i++){
            if(dataList.get(i).get("id").toString().equals(id)){
                index=i;
                break;
            }
        }
        dataList.remove(index);
        simpleAdapter.notifyDataSetChanged();
    }



}

