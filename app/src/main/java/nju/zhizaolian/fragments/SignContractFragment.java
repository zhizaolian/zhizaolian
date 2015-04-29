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
 *
 *
 */
public class SignContractFragment extends Fragment {
    private static String signContractSubmitUrl="/fmc/market/mobile_signContractSubmit.do";
    private static String signContractDetailUrl="/fmc/market/mobile_signContractDetail.do";
    private EditText signContractDiscountEdit;
    private TextView signContractTotalMoneyView;
    private TextView signContractRemarkView;
    private Button  signContractUploadContractButton;
    private Button signContractUploadDepositButton;
    private ImageView signContractImage;
    private ImageView signContractDepositImage;


    private ListInfo listInfo;
    private Order order;
    private OrderInfo orderInfo;
    private byte[] contractByte;
    private byte[] depositByte;
    private String contractImageUrl;
    private String depositImageUrl;


    private ProgressDialog progressDialog;
    public SignContractFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_sign_contract,container,false);
        listInfo= (ListInfo) getArguments().getSerializable("info");
        progressDialog=ProgressDialog.show(container.getContext(),"","",true);

        signContractDiscountEdit=(EditText)view.findViewById(R.id.sign_contract_discount_edit);
        signContractTotalMoneyView=(TextView)view.findViewById(R.id.sign_contract_total_money_view);
        signContractRemarkView=(TextView)view.findViewById(R.id.sign_contract_remark_view);
        signContractUploadContractButton=(Button)view.findViewById(R.id.upload_contract_button);
        signContractUploadDepositButton=(Button)view.findViewById(R.id.upload_deposit_button);
        signContractImage=(ImageView)view.findViewById(R.id.sign_contract_Image_View);
        signContractDepositImage=(ImageView)view.findViewById(R.id.sign_contract_deposit_image);






        getSignContractFragmentDetail();


        return view;
    }


    public void getSignContractFragmentDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",listInfo.getOrder().getOrderId());
        client.get(IPAddress.getIP()+signContractDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("signcontract",response.toString());
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



    public void signContractSubmit(){

    }






}
