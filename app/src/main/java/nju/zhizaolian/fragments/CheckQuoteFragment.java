package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

/**
 *
 */
public class CheckQuoteFragment extends Fragment {

    private EditText profitPerClothesEdit;
    private EditText masterIdeaEdit;
    private Button ensureVerifyQuoteButton;
    private Button unableVerifyQuoteButton;
    private TextView customQuoteView;

    private String checkQuoteDetailUrl="/fmc/market/mobile_verifyQuoteDetail.do";
    private String checkQuoteSubmitUrl="/fmc/market/mobile_verifyQuoteSubmit.do";


    private ListInfo listInfo;
    private Order order;
    private OrderInfo orderInfo;


    private ProgressDialog progressDialog;
    public CheckQuoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        listInfo= (ListInfo) getArguments().getSerializable("info");
        order=listInfo.getOrder();
        progressDialog=ProgressDialog.show(container.getContext(),"请等待","下载数据中...",true);
        verifyQuoteDetail();
        View view=inflater.inflate(R.layout.fragment_check_quote, container, false);
        profitPerClothesEdit=(EditText)view.findViewById(R.id.profit_per_clothes_edit);
        masterIdeaEdit=(EditText)view.findViewById(R.id.master_idea_edit);
        ensureVerifyQuoteButton=(Button)view.findViewById(R.id.ensure_verify_quote_button);
        customQuoteView=(TextView)view.findViewById(R.id.custom_quote_view);
        ensureVerifyQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(masterIdeaEdit.getText().length()==0){
                    Toast.makeText(getActivity(), "请填写意见", Toast.LENGTH_SHORT).show();
                }else {
                    final AlertDialog.Builder builder=new AlertDialog.Builder(container.getContext());
                    builder.setTitle("提示");
                    builder.setMessage("确定操作?");
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog=ProgressDialog.show(container.getContext(),"请等待","提交中",true);
                            verifyQuoteSubmit(true);
                        }
                    });
                    builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                        }
                    });

                    builder.create().show();
                }
            }
        });
        unableVerifyQuoteButton=(Button)view.findViewById(R.id.unable_verify_quote_button);
        unableVerifyQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(masterIdeaEdit.getText().length()==0){
                    Toast.makeText(getActivity(), "请填写意见", Toast.LENGTH_SHORT).show();
                }else {
                    verifyQuoteSubmit(false);
                }
            }
        });



        return view;
    }


    public void verifyQuoteSubmit(boolean verifyQuoteSuccessVal){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("profitPerPiece",profitPerClothesEdit.getText().toString());
        params.put("inner_price","");
        params.put("outer_price",customQuoteView.getText().toString());
        params.put("single_cost","");
        params.put("order_id",order.getOrderId());
        params.put("taskId", orderInfo.getTaskId());
        params.put("processId",orderInfo.getProcessInstanceId());
        params.put("suggestion",masterIdeaEdit.getText().toString());
        params.put("verifyQuoteSuccessVal",verifyQuoteSuccessVal);
        client.post(IPAddress.getIP()+checkQuoteSubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "审核成功", Toast.LENGTH_SHORT).show();
                getActivity().finish();

            }
        });
    }


    public void verifyQuoteDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId",order.getOrderId());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        client.get(IPAddress.getIP()+checkQuoteDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("success", response.toString());
                try {
                    orderInfo= OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                    profitPerClothesEdit.setText(orderInfo.getQuote().getProfitPerPiece());
                    customQuoteView.setText(orderInfo.getQuote().getOuterPrice());
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }
        });



    }



}
