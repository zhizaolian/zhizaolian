package nju.zhizaolian.fragments;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Logistics;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;

/**
 *
 */
public class ReceiveSampleFragment extends Fragment {

    private   String  receiveSampleDetailUrl="/fmc/logistics/mobile_receiveSampleDetail.do";
    private String receiveSampleSubmitUrl="/fmc/logistics/mobile_receiveSampleSubmit.do";
    private TextView isProvideSampleView;
    private TextView expressTimeView;
    private TextView expressNameView;
    private  TextView expressNumberView;
    private TextView postManView;
    private  TextView postManPhoneView;
    private TextView expressAddressView;
    private ImageView samplePictureImage;
    private ImageView referencePictureImage;
    private Button ensureReceiveSampleButton;
    private Button unableReceiveSampleButton;

    private ListInfo listInfo;
    private OrderInfo orderInfo;
    private Order order;
    private Logistics logistics;
    public ReceiveSampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_receive__sample_, container, false);
        listInfo= (ListInfo) getArguments().getSerializable("info");
        order=listInfo.getOrder();

        getReceiveSampleDetail();




        isProvideSampleView= (TextView) view.findViewById(R.id.is_Provide_Sample_view);
        expressTimeView= (TextView) view.findViewById(R.id.express_time_view);
        expressNameView=(TextView)view.findViewById(R.id.express_name_view);
        expressNumberView=(TextView)view.findViewById(R.id.express_number_view);
        postManView=(TextView)view.findViewById(R.id.post_man_view);
        postManPhoneView=(TextView)view.findViewById(R.id.post_man_phone_view);
        expressAddressView=(TextView)view.findViewById(R.id.post_address_view);
        samplePictureImage=(ImageView)view.findViewById(R.id.sample_picture_image);
        referencePictureImage=(ImageView)view.findViewById(R.id.reference_picture_image);
        ensureReceiveSampleButton=(Button)view.findViewById(R.id.ensure_receive_sample_button);
        ensureReceiveSampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receiveSampleSubmit();
            }
        });
        unableReceiveSampleButton=(Button)view.findViewById(R.id.unable_receive_sample_button);
        unableReceiveSampleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unReceiveSampleSubmit();
            }
        });


        isProvideSampleView.setText("未收到样衣");


        Picasso.with(container.getContext()).load(IPAddress.getIP()+order.getSampleClothesPicture()).into(samplePictureImage);
        Picasso.with(container.getContext()).load(IPAddress.getIP()+order.getReferencePicture()).into(referencePictureImage);
        return view;
    }

    public void getReceiveSampleDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams requestParams=new RequestParams();
        requestParams.put("orderId",listInfo.getOrder().getOrderId());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        requestParams.put("jsessionId",jsessionId);
        client.get(IPAddress.getIP() + receiveSampleDetailUrl, requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("receivesample", response.toString());
                try {
                    orderInfo = OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                    logistics=orderInfo.getLogistics();
                    expressTimeView.setText(logistics.getInPostSampleClothesTime());
                    expressNameView.setText(logistics.getInPostSampleClothesType());
                    expressNumberView.setText(logistics.getInPostSampleClothesNumber());
                    postManView.setText(logistics.getSampleClothesName());
                    postManPhoneView.setText(logistics.getSampleClothesPhone());
                    expressAddressView.setText(logistics.getSampleClothesAddress());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("errorString", responseString);
            }

        });


    }

    public void receiveSampleSubmit(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams requestParams=new RequestParams();
        requestParams.put("orderId",order.getOrderId());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        requestParams.put("jsessionId",jsessionId);
        requestParams.put("result","2");
        requestParams.put("taskId",orderInfo.getTaskId());
        requestParams.put("orderId",order.getOrderId());
        client.post(IPAddress.getIP()+receiveSampleSubmitUrl,requestParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("response",response.toString());
                try {
                    String success=response.getString("isSuccess");
                    if(success.equals("true")){
                        getActivity().finish();
                    }else{
                        Toast.makeText(getActivity(), "出现异常", Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    getActivity().finish();
                }
            }
        });


    }
    public void unReceiveSampleSubmit(){

        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams requestParams=new RequestParams();
        requestParams.put("orderId",order.getOrderId());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        requestParams.put("jsessionId",jsessionId);
        requestParams.put("result","1");
        requestParams.put("taskId",orderInfo.getTaskId());
        requestParams.put("orderId",orderInfo.getOrderId());
        client.post(IPAddress.getIP()+receiveSampleSubmitUrl,requestParams,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("unreceive",response.toString());
                getActivity().finish();
            }
        });

    }

}
