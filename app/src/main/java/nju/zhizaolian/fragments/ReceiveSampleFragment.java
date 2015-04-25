package nju.zhizaolian.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import nju.zhizaolian.R;
import nju.zhizaolian.models.IPAddress;

/**
 *
 */
public class ReceiveSampleFragment extends Fragment {

    String  receiveSampleDetailUrl="/fmc/logistics/mobile_receiveSampleDetail.do";
    public ReceiveSampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getReceiveSampleDetail(receiveSampleDetailUrl);
        return inflater.inflate(R.layout.fragment_receive__sample_, container, false);
    }

    public void getReceiveSampleDetail(String url){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams requestParams=new RequestParams();
        requestParams.put("orderId","20150425000041");
        client.get(IPAddress.getIP()+url,requestParams, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("receivesample",response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("errorString",responseString);
            }

        });


    }
}
