package nju.zhizaolian.fragments;


import android.app.Fragment;
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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import nju.zhizaolian.R;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;

/**
 *
 */
public class CheckSampleBalanceFragment extends Fragment {
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
    public CheckSampleBalanceFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_check_sample_balance, container, false);
        listInfo= (ListInfo) getArguments().getSerializable("info");
        getCheckSampleMoneyDetail();




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

            }
        });
        checkSampleUnableMoneyButton=(Button)view.findViewById(R.id.unable_receive_sample_money_button);
        checkSampleUnableMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
                checkSampleReceiveMoneyTimeView.setText(response.toString());
            }
        });


    }

    public void confirmSampleMoneySubmit(){

    }


}
