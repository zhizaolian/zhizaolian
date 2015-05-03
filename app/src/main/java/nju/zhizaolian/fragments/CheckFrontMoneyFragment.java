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
import nju.zhizaolian.models.Quote;

/**
 *.首定金
 */
public class CheckFrontMoneyFragment extends Fragment {
    private static  String confirmDepositDetailUrl="/fmc/finance/mobile_confirmDepositDetail.do";
    private static  String confirmDepositSubmitUrl="/fmc/finance/mobile_confirmDepositSubmit.do";

   private TextView moneyTypeView;
    private TextView moneyDiscountView;
    private TextView moneyPayView;
    private TextView goodsAmountView;
    private TextView goodsUnitPriceView;
    private TextView goodsTotalPriceView;
    private TextView sampleAmountView;
    private TextView sampleUnitPriceView;
    private TextView sampleTotalPriceView;
    private EditText remitNameEdit;
    private EditText remitNumberEdit;
    private EditText remitBankEdit;
    private TextView remitMoneyView;
    private Spinner depositAccountSpinner;
    private TextView depositTimeView;
    private TextView depositRemarkView;
    private ImageView depositMoneyImage;
    private Button ensureDepositMoneyButton;
    private Button unableDepositMoneyButton;




    private ListInfo listInfo;
    private Order order;
    private OrderInfo orderInfo;
    private ProgressDialog progressDialog;
    public CheckFrontMoneyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_check_front_money, container, false);
        listInfo= (ListInfo) getArguments().getSerializable("info");
         moneyTypeView=(TextView)view.findViewById(R.id.deposit_money_type_view);
        moneyDiscountView=(TextView)view.findViewById(R.id.deposit_money_discount_view);
         moneyPayView=(TextView)view.findViewById(R.id.deposit_money_pay_view);
        goodsAmountView=(TextView)view.findViewById(R.id.deposit_money_goods_amount_view);
         goodsUnitPriceView=(TextView)view.findViewById(R.id.deposit_money_goods_unit_price_view);
         goodsTotalPriceView=(TextView)view.findViewById(R.id.deposit_money_goods_total_price_view);
         sampleAmountView=(TextView)view.findViewById(R.id.deposit_money_sample_number_view);
         sampleUnitPriceView=(TextView)view.findViewById(R.id.deposit_money_sample_unit_price_view);
         sampleTotalPriceView=(TextView)view.findViewById(R.id.deposit_money_sample_total_price_view);
         remitNameEdit=(EditText)view.findViewById(R.id.deposit_money_remit_name_edit);
         remitNumberEdit=(EditText)view.findViewById(R.id.deposit_money_remit_number_edit);
         remitBankEdit=(EditText)view.findViewById(R.id.deposit_money_remit_bank_edit);
        remitMoneyView=(TextView)view.findViewById(R.id.deposit_money_remit_money_view);
         depositAccountSpinner=(Spinner)view.findViewById(R.id.deposit_money_account_spinner);
         depositTimeView=(TextView)view.findViewById(R.id.deposit_money_time_view);
         depositRemarkView=(TextView)view.findViewById(R.id.deposit_money_remark_view);
         depositMoneyImage=(ImageView)view.findViewById(R.id.deposit_money_image);
         ensureDepositMoneyButton=(Button)view.findViewById(R.id.ensure_deposit_money_button);
         unableDepositMoneyButton=(Button)view.findViewById(R.id.unable_deposit_money_button);




        progressDialog=ProgressDialog.show(container.getContext(),"请等待","数据更新中",true);

        getConfirmDepositDetail();
        ensureDepositMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remitNameEdit.getText().length() == 0 || remitNumberEdit.getText().length() == 0 ||
                        remitBankEdit.getText().length() == 0){
                    Toast.makeText(getActivity(),"请填写数据",Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

                    builder.setMessage("确定操作");
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog=ProgressDialog.show(getActivity(),"请等待","数据上传中",true,true);
                            confirmDepositSubmit(1);
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

        unableDepositMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

                builder.setMessage("确定操作");
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog=ProgressDialog.show(getActivity(),"请等待","数据上传中",true,true);
                        confirmDepositSubmit(0);
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

        return view;
    }


    public void  getConfirmDepositDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common", 0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",listInfo.getOrder().getOrderId());
        client.get(IPAddress.getIP()+confirmDepositDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("deposit",response.toString());
                try {
                    orderInfo=OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                    order=orderInfo.getOrder();
                    Quote quote=orderInfo.getQuote();
                    moneyTypeView.setText(orderInfo.getMoneyName());

                    float discount= Float.parseFloat(order.getDiscount());
                    float askAmount= Float.valueOf(order.getAskAmount());
                    float askUnitPrice= Float.valueOf(quote.getOuterPrice());
                    float totalPrice=askAmount*askUnitPrice;
                    float moneyPayPrice= (float) ((askAmount*askUnitPrice-discount)*0.3);
                    moneyDiscountView.setText(String.valueOf(discount));
                    moneyPayView.setText(String.valueOf(moneyPayPrice));
                    goodsAmountView.setText(String.valueOf(discount));
                    goodsUnitPriceView.setText(String.valueOf(askUnitPrice));
                    goodsTotalPriceView.setText(String.valueOf(totalPrice));
                    sampleAmountView.setText(orderInfo.getOrderSampleAmount());
                    sampleUnitPriceView.setText(String.valueOf(orderInfo.getSamplePrice()));
                    double sampleAmount= Double.parseDouble(orderInfo.getOrderSampleAmount());
                    double sampleUnitPrice= Double.parseDouble(orderInfo.getSamplePrice());
                    sampleTotalPriceView.setText(String.valueOf(sampleAmount*sampleUnitPrice));
                    remitMoneyView.setText(String.valueOf(moneyPayPrice));
                    depositTimeView.setText(MyUtils.getCurrentDate());
                    depositRemarkView.setText(order.getMoneyremark());
                    Picasso.with(getActivity()).load(IPAddress.getIP()+orderInfo.getUrl()).into(depositMoneyImage);

                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("30%定金",responseString);
                progressDialog.dismiss();
            }
        });

    }
    public void confirmDepositSubmit(int result){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common", 0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",order.getOrderId());
        params.put("taskId",orderInfo.getTaskId());
        params.put("result",String.valueOf(result));//收到传1，未收到传0
        params.put("money_amount",remitMoneyView.getText().toString());
        if(result == 1){
            params.put("money_state","已收到");
        }else {
            params.put("money_state","未收到");
        }

        params.put("money_type",orderInfo.getMoneyName());
        params.put("money_bank",remitBankEdit.getText().toString());
        params.put("money_name",remitNameEdit.getText().toString());
        params.put("money_number",remitNumberEdit.getText().toString());
        params.put("money_remark",depositRemarkView.getText().toString());
        params.put("time",depositTimeView.getText().toString());
        params.put("account",depositAccountSpinner.getSelectedItem().toString());
        client.post(IPAddress.getIP()+confirmDepositSubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String success=response.getString("isSuccess");
                    if(success.equals("true")){
                        Toast.makeText(getActivity(),"确认成功",Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getActivity(),"未能收到样衣",Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                    getActivity().finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }


}
