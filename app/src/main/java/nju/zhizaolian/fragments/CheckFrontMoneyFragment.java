package nju.zhizaolian.fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
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
 *.首定金
 */
public class CheckFrontMoneyFragment extends Fragment {
    private static  String confirmDepositDetailUrl="/fmc/finance/mobile_returnDepositDetail.do";
    private static  String confirmDepositSubmitUrl="/fmc//finance/mobile_returnDepositSubmit.do";

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
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
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
        params.put("result",String.valueOf(result));
        params.put("money_amount",remitMoneyView.getText().toString());
        params.put("money_state","");
        params.put("money_type","");
        params.put("money_bank",remitBankEdit.getText().toString());
        params.put("money_name",remitNameEdit.getText().toString());
        params.put("money_number",remitNumberEdit.getText().toString());
        params.put("money_remark",depositRemarkView.getText().toString());
        params.put("time",depositTimeView.getText().toString());
        params.put("account",depositAccountSpinner.getSelectedItem().toString());
    }


}
