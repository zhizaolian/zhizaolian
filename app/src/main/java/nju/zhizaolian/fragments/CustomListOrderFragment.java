package nju.zhizaolian.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.LoginActivity;
import nju.zhizaolian.adapters.CustomAdapter;
import nju.zhizaolian.models.Custom;

/**
 *
 */
public class CustomListOrderFragment extends Fragment {
    private  ArrayList<Custom> customList;
    private  ListView customListView;
    private static final String baseUrl="http://172.17.188.91:8080/fmc/";
    private Context context;
    private CustomAdapter customAdapter;
    public CustomListOrderFragment(Context context) {
        this.context=context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_custom_list,container,false);
        customListView= (ListView) view.findViewById(R.id.custom_listView);
        customList=new ArrayList<Custom>();

       customAdapter=new CustomAdapter(container.getContext(),customList);

        customListView.setAdapter(customAdapter);
        fetchCustom();
        return view;
    }

    public void reLogin(Context context){
        Intent intent=new Intent(context, LoginActivity.class);
        startActivity(intent);

    }
    public void  fetchCustom(){
        AsyncHttpClient client=new AsyncHttpClient();

        client.get(baseUrl+"account/mobile_customerList.do",new JsonHttpResponseHandler(){


            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("customer_list");
                    ArrayList<Custom> customList=Custom.fromJson(jsonArray);
                    customAdapter.clear();
                    for(Custom c:customList){
                        customAdapter.add(c);
                    }
                    customAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("success",response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error",responseString);
                reLogin(context);
            }
        });

    }

}
