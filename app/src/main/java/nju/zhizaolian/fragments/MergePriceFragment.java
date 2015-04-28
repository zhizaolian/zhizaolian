package nju.zhizaolian.fragments;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import nju.zhizaolian.R;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;


public class MergePriceFragment extends Fragment {

  private  EditText profitPerClothesEdit;
    private  Button mergePriceEnsureButton;
    private  Button mergePriceCancelButton;
    private  Order order;
    private  ListInfo listInfo;
    private  OrderInfo orderInfo;
   private String mergeQuoteDetailUrl="/fmc/market/mobile_mergeQuoteDetail.do";
   private String mergeQuoteSubmitUrl="/fmc/market/mobile_mergeQuoteSubmit.do";
    public MergePriceFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_merge_price, container, false);
        listInfo= (ListInfo) getArguments().getSerializable("info");
        order=listInfo.getOrder();
        getMergePriceDetail();
        profitPerClothesEdit=(EditText)view.findViewById(R.id.profit_per_clothes_edit);
        mergePriceEnsureButton=(Button)view.findViewById(R.id.merge_price_ensure_button);
        mergePriceEnsureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profitPerClothesEdit.getText().length()==0){
                    Toast.makeText(getActivity(), "请填写信息", Toast.LENGTH_SHORT).show();
                }else{
                    mergePriceSubmit();
                }
            }
        });
        mergePriceCancelButton=(Button)view.findViewById(R.id.merge_price_cancel_button);
        mergePriceCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "暂不可用", Toast.LENGTH_SHORT).show();

            }
        });



        return view;
    }

    public void mergePriceSubmit(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("profitPerPiece",profitPerClothesEdit.getText().toString());
        params.put("inner_price","");
        params.put("outer_price","");
        params.put("single_cost","");
        params.put("order_id",order.getOrderId());
        params.put("taskId",orderInfo.getTaskId());
        params.put("processId",orderInfo.getProcessInstanceId());
        client.post(IPAddress.getIP()+mergeQuoteSubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String isSuccess=response.getString("isSuccess");
                    if (isSuccess.equals("true")){
                        Toast.makeText(getActivity(), "提交成功", Toast.LENGTH_SHORT).show();
                        onFinish();
                    }else {
                        Toast.makeText(getActivity(), "出现错误", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    public void getMergePriceDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId",order.getOrderId());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        client.get(IPAddress.getIP()+mergeQuoteDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("success",response.toString());
                try {
                    orderInfo= OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
