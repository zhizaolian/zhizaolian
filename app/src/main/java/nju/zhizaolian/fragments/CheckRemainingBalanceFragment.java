package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import nju.zhizaolian.R;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Logistics;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;

/**
 *
 */
public class CheckRemainingBalanceFragment extends Fragment {
    public static  String confirmFinalPaymentDetailUrl="/fmc/finance/mobile_confirmFinalPaymentDetail.do";
    public static  String confirmFinalPaymentSubmitUrl="/fmc/finance/mobile_confirmFinalPaymentSubmit.do";

    private ListInfo listInfo;
    private Order order;
    private OrderInfo orderInfo;


    private TextView moneyTypeView;
    private TextView discountView;
    private TextView deserveMoneyView;
    private TextView goodsNumberView;
    private TextView goodsUnitPriceView;
    private TextView goodsTotalPriceView;
    private TextView sampleNumberView;
    private TextView sampleUnitPriceView;
    private TextView sampleTotalPriceView;
    private TextView goodsLogisticCostView;
    private TextView sampleLogisticCostView;
    private TextView logisticTotalView;
    private EditText remitNameEdit;
    private EditText remitNumberEdit;
    private EditText remitBankEdit;
    private TextView remitMoneyView;
    private Spinner remainingSpinner;
    private TextView remainingTimeView;
    private EditText remarkInfoEdit;
    private ImageView remainingImageView;
    private Button ensureButton;
    private Button unableButton;

    private ProgressDialog progressDialog;

    public CheckRemainingBalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_check_remaining_balance, container, false);
        listInfo= (ListInfo) getArguments().getSerializable("info");

         moneyTypeView=(TextView)view.findViewById(R.id.remaining_money_type_view);
        discountView=(TextView)view.findViewById(R.id.remaining_discount_view);
         deserveMoneyView=(TextView)view.findViewById(R.id.remaining_deserve_money_view);
         goodsNumberView=(TextView)view.findViewById(R.id.remaining_goods_number_view);
         goodsUnitPriceView=(TextView)view.findViewById(R.id.remaining_goods_unit_price_view);
        goodsTotalPriceView=(TextView)view.findViewById(R.id.remaining_goods_total_price_view);
         sampleNumberView=(TextView)view.findViewById(R.id.remaining_sample_number_view);
         sampleUnitPriceView=(TextView)view.findViewById(R.id.remaining_sample_unit_price_view);
         sampleTotalPriceView=(TextView)view.findViewById(R.id.remaining_sample_total_price_view);
        goodsLogisticCostView=(TextView)view.findViewById(R.id.remaining_goods_logistic_cost_view);
         sampleLogisticCostView=(TextView)view.findViewById(R.id.remaining_sample_logistic_cost_view);
         logisticTotalView=(TextView)view.findViewById(R.id.remaining_logistic_total_cost_view);
         remitNameEdit=(EditText)view.findViewById(R.id.remaining_remit_name_edit);
        remitNumberEdit=(EditText)view.findViewById(R.id.remaining_number_edit);
         remitBankEdit=(EditText)view.findViewById(R.id.remaining_bank_edit);
         remitMoneyView=(TextView)view.findViewById(R.id.remaining_remit_money_view);
        remainingSpinner=(Spinner)view.findViewById(R.id.remaining_spinner);
         remainingTimeView=(TextView)view.findViewById(R.id.remaining_time_view);
        remarkInfoEdit=(EditText)view.findViewById(R.id.remaining_remark_info_edit);
         remainingImageView=(ImageView)view.findViewById(R.id.remaining_image_view);
         ensureButton=(Button)view.findViewById(R.id.ensure_remaining_button);
         unableButton=(Button)view.findViewById(R.id.unable_remaining_button);


        ensureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remitNameEdit.getText().length() == 0 || remitNumberEdit.getText().length() == 0||
                        remitBankEdit.getText().length() == 0 ||remarkInfoEdit.getText().length() == 0){
                    Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT).show();

                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setMessage("确定收到尾款？");
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog=ProgressDialog.show(getActivity(),"请等待","数据上传中",true,true);
                            confirmFinalPaymentSubmit(true);
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
        unableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("确定未收到尾款？");
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog=ProgressDialog.show(getActivity(),"请等待","数据上传中",true,true);
                        confirmFinalPaymentSubmit(false);
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
        });

        progressDialog=ProgressDialog.show(getActivity(),"请等待","数据更新中",true,true);
        getConfirmFinalPaymentDetail();

        return view;
    }



    public void getConfirmFinalPaymentDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId",listInfo.getOrder().getOrderId());
        client.get(IPAddress.getIP()+confirmFinalPaymentDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    orderInfo=OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                    order=orderInfo.getOrder();
                    Logistics logistics=orderInfo.getLogistics();
                    moneyTypeView.setText(orderInfo.getMoneyName());
                    discountView.setText(order.getDiscount());
                    double number= Double.parseDouble(order.getAskAmount());
                    double price= Double.parseDouble(orderInfo.getPrice());
                    double discount= Double.parseDouble(order.getDiscount());
                    double deposit= Double.parseDouble(orderInfo.getDeposit());
                    double deserveMoney=number*price-discount-deposit;
                    double sampleNumber= Double.parseDouble(orderInfo.getOrderSampleAmount());
                    double sampleUnitPrice= Double.parseDouble(orderInfo.getSamplePrice());


                    deserveMoneyView.setText(String.valueOf(deserveMoney));
                    goodsNumberView.setText(order.getAskAmount());
                    goodsUnitPriceView.setText(orderInfo.getPrice());
                    goodsTotalPriceView.setText(String.valueOf(number*price));
                    sampleNumberView.setText(orderInfo.getOrderSampleAmount());
                    sampleUnitPriceView.setText(orderInfo.getSamplePrice());
                    sampleTotalPriceView.setText(String.valueOf(sampleNumber*sampleUnitPrice));




                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressDialog.dismiss();
            }
        });
    }
    public void confirmFinalPaymentSubmit(boolean result){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",order.getOrderId());
        params.put("taskId",orderInfo.getTaskId());
        if(result){
            params.put("result","1");
            params.put("money_amount",remitMoneyView.getText().toString());
            params.put("money_state","已收到");
            params.put("money_type",moneyTypeView.getText().toString());
            params.put("money_bank",remitBankEdit.getText().toString());
            params.put("money_name",remitNameEdit.getText().toString());
            params.put("money_number",remitNumberEdit.getText().toString());
            params.put("money_remark",remarkInfoEdit.getText().toString());
            params.put("time",remainingTimeView.getText().toString());
            params.put("account",remainingSpinner.getSelectedItem().toString());

        }else {
           params.put("result","0");
        }

        client.post(IPAddress.getIP()+confirmFinalPaymentSubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    boolean result= Boolean.parseBoolean(response.getString("isSuccess"));
                    if(result){
                        Toast.makeText(getActivity(),"提交成功",Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }else {
                        Toast.makeText(getActivity(),"出现错误",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressDialog.dismiss();
            }
        });



    }

}
