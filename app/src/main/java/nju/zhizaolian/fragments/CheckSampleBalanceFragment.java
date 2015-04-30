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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import nju.zhizaolian.R;
import nju.zhizaolian.help.MyUtils;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;

/**
 *
 */
public class CheckSampleBalanceFragment extends Fragment {
    private TextView checkSampleMoneyTypeView;
    private TextView checkSampleNumberView;
    private TextView checkSampleNumberUnitPriceView;
    private TextView checkSampleReceivableMoneyView;
    private  EditText checkSampleRemitPersonEdit;
    private  EditText checkSampleRemitCardNumberEdit;
    private  EditText checkSampleRemitBankEdit;
    private  TextView checkSampleRemitMoneyView;
    private  TextView checkSampleReceiveMoneyTimeView;
    private  Spinner  checkSampleReceiveMoneyAccountSpinner;
    private  TextView checkSampleRemarkView;
    private  ImageView checkSampleImageView;
    private  Button checkSampleEnsureMoneyButton;
    private  Button checkSampleUnableMoneyButton;


    private String confirmSampleMoneyDetailUrl="/fmc/finance/mobile_confirmSampleMoneyDetail.do";
    private String confirmSampleMoneySubmitUrl="/fmc//finance/mobile_confirmSampleMoneySubmit.do";

    private ListInfo listInfo;
    private Order order;
    private OrderInfo orderInfo;

    private ProgressDialog progressDialog;


    public CheckSampleBalanceFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_check_sample_balance, container, false);
        listInfo= (ListInfo) getArguments().getSerializable("info");
        checkSampleMoneyTypeView=(TextView)view.findViewById(R.id.money_type_view);
        checkSampleNumberView=(TextView)view.findViewById(R.id.check_sample_money_clothes_number_view);
        checkSampleNumberUnitPriceView=(TextView)view.findViewById(R.id.check_sample_money_unit_money_view);
        checkSampleReceivableMoneyView=(TextView)view.findViewById(R.id.check_sample_money_receivable_money_view);
        checkSampleRemitPersonEdit=(EditText)view.findViewById(R.id.check_sample_money_remit_person_edit);
        checkSampleRemitCardNumberEdit=(EditText)view.findViewById(R.id.check_sample_money_remit_card_number_edit);
        checkSampleRemitBankEdit=(EditText)view.findViewById(R.id.check_sample_money_remit_bank_edit);
        checkSampleRemitMoneyView=(TextView)view.findViewById(R.id.check_sample_money_remit_money_view);
        checkSampleReceiveMoneyTimeView=(TextView)view.findViewById(R.id.check_sample_money_receive_money_time_view);
        checkSampleReceiveMoneyAccountSpinner=(Spinner)view.findViewById(R.id.check_sample_money_account_spinner);
        checkSampleRemarkView=(TextView)view.findViewById(R.id.check_sample_money_remark_view);
        checkSampleImageView=(ImageView)view.findViewById(R.id.check_sample_money_image);
        checkSampleEnsureMoneyButton=(Button)view.findViewById(R.id.ensure_receive_sample_money_button);
        checkSampleEnsureMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkSampleRemitPersonEdit.getText().length() == 0||checkSampleRemitCardNumberEdit.getText().length()==0||checkSampleRemitBankEdit.getText().length() == 0){
                    Toast.makeText(container.getContext(),"请填写数据",Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(container.getContext());
                    builder.setMessage("确认收到样衣");
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog=ProgressDialog.show(container.getContext(),"请等待","正在上传",true);
                            confirmSampleMoneySubmit(true);
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
        checkSampleUnableMoneyButton=(Button)view.findViewById(R.id.unable_receive_sample_money_button);
        checkSampleUnableMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog=ProgressDialog.show(container.getContext(),"请等待","正在上传",true);
                confirmSampleMoneySubmit(false);

            }
        });
        progressDialog=ProgressDialog.show(container.getContext(),"请等待","数据下载中",true);
        getCheckSampleMoneyDetail();
        return view;
    }

    public void getCheckSampleMoneyDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",listInfo.getOrder().getOrderId());
        client.get(IPAddress.getIP()+confirmSampleMoneyDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("money",response.toString());
                try {
                    orderInfo=OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                    order=orderInfo.getOrder();
                    checkSampleMoneyTypeView.setText(orderInfo.getType());
                    int sampleAmount= Integer.parseInt(orderInfo.getOrderSampleAmount());
                    int sampleUnitPrice= Integer.parseInt(orderInfo.getPrice());
                    checkSampleNumberView.setText(orderInfo.getOrderSampleAmount());
                    checkSampleNumberUnitPriceView.setText(orderInfo.getPrice());

                    checkSampleReceivableMoneyView.setText(String.valueOf(sampleAmount*sampleUnitPrice));
                    checkSampleRemitMoneyView.setText(orderInfo.getTotal());
                    checkSampleReceiveMoneyTimeView.setText(MyUtils.getCurrentDate());
                    checkSampleRemarkView.setText(order.getMoneyremark());
                    Picasso.with(getActivity().getApplicationContext()).
                            load(IPAddress.getIP() + order.getConfirmSampleMoneyFile()).into(checkSampleImageView);
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }

            }
        });


    }

    public void confirmSampleMoneySubmit(boolean result){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",order.getOrderId());
        params.put("taskId",orderInfo.getTaskId());
        if(result){
            params.put("result",1);
        }else {
            params.put("result",0);
        }

        params.put("money_amount",checkSampleRemitMoneyView.getText().toString());
        params.put("money_state","");
        params.put("money_type","");
        params.put("money_bank",checkSampleRemitBankEdit.getText().toString());
        params.put("money_name",checkSampleRemitPersonEdit.getText().toString());
        params.put("money_number",checkSampleRemitCardNumberEdit.getText().toString());
        params.put("money_remark",checkSampleRemarkView.getText().toString());
        params.put("time",checkSampleReceiveMoneyTimeView.getText().toString());
        params.put("account",checkSampleReceiveMoneyAccountSpinner.getSelectedItem().toString());
        client.post(IPAddress.getIP()+confirmSampleMoneySubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                progressDialog.dismiss();
                Toast.makeText(getActivity(),"上传成功,请进入下一步",Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("error",responseString);

                progressDialog.dismiss();
                Toast.makeText(getActivity(),"出现错误",Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });

    }


}
