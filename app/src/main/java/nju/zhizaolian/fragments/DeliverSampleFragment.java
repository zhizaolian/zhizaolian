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
import android.widget.ListView;
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
import nju.zhizaolian.adapters.SendRecordAdapter;
import nju.zhizaolian.help.MyUtils;
import nju.zhizaolian.models.DeliveryRecord;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;

/**
 *
 */
public class DeliverSampleFragment extends Fragment {
    private Spinner sendSampleExpressSpinner;
    private EditText sendSampleExpressNumberEdit;
    private EditText sendSampleExpressPriceEdit;
    private TextView sendSampleExpressTimeView;
    private Button sendSampleSaveButton;
    private Button sendSampleFinalButton;
    private ListView sendSampleRecordListView;
    private SendRecordAdapter sendRecordAdapter;



    private ListInfo listInfo;
    private Order order;
    private OrderInfo orderInfo;


    private ProgressDialog progressDialog;


    private static final String sendSampleDetailUrl="/fmc/logistics/mobile_sendSampleDetail.do";
    private static final String sendSampleSubmitUrl="/fmc/logistics/mobile_sendSampleSubmit.do";


    public DeliverSampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_deliver_sample, container, false);
        listInfo= (ListInfo) getArguments().getSerializable("info");


        sendSampleExpressSpinner=(Spinner)view.findViewById(R.id.send_sample_type_spinner);
        sendSampleExpressNumberEdit=(EditText)view.findViewById(R.id.send_sample_express_number_edit);
        sendSampleExpressPriceEdit=(EditText)view.findViewById(R.id.send_sample_express_price_edit);
        sendSampleExpressTimeView=(TextView)view.findViewById(R.id.send_sample_express_time_view);
        sendSampleSaveButton=(Button)view.findViewById(R.id.send_sample_save_button);
        sendSampleFinalButton=(Button)view.findViewById(R.id.send_sample_final_button);
        sendSampleRecordListView=(ListView)view.findViewById(R.id.send_sample_record_listView);


        progressDialog=ProgressDialog.show(container.getContext(),"请等待","数据下载中",true);
        getSendSampleDetail();
        sendSampleSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sendSampleExpressNumberEdit.getText().length()==0||sendSampleExpressPriceEdit.getText().length()==0){
                    Toast.makeText(container.getContext(),"请填写数据",Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(container.getContext());
                    builder.setMessage("确认操作?");
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog=ProgressDialog.show(container.getContext(),"请等待","正在上传",true);
                            sendSampleSaveSubmit();
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
        sendSampleFinalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(container.getContext());
                builder.setMessage("确认操作?");
                builder.setPositiveButton("确认",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog=ProgressDialog.show(container.getContext(),"请等待","正在上传",true);
                        sendSampleFinalSubmit();
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



    public void getSendSampleDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId",listInfo.getOrder().getOrderId());
        client.get(IPAddress.getIP()+sendSampleDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    orderInfo=OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                    order=orderInfo.getOrder();
                    sendRecordAdapter=new SendRecordAdapter(getActivity().getApplicationContext(),0,orderInfo.getDeliveryRecords());
                    sendSampleRecordListView.setAdapter(sendRecordAdapter);
                    sendRecordAdapter.notifyDataSetChanged();
                    sendSampleExpressTimeView.setText(MyUtils.getCurrentDate());
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }



            }
        });

    }

    public void sendSampleSaveSubmit(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",order.getOrderId());
        params.put("taskId",orderInfo.getTaskId());
        params.put("isFinal","false");
        params.put("name", sendSampleExpressSpinner.getSelectedItem().toString());
        params.put("number",sendSampleExpressNumberEdit.getText().toString());
        params.put("price",sendSampleExpressPriceEdit.getText().toString());
        params.put("time",sendSampleExpressTimeView.getText().toString());
        client.post(IPAddress.getIP()+sendSampleSubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("sendSampleSaveSubmit",response.toString());
                try {
                    String success=response.getString("isSuccess");
                    if(success.equals("true")){
                        DeliveryRecord deliveryRecord=new DeliveryRecord();
                        deliveryRecord.setExpressName(sendSampleExpressSpinner.getSelectedItem().toString());
                        deliveryRecord.setExpressNumber(sendSampleExpressNumberEdit.getText().toString());
                        deliveryRecord.setExpressPrice(sendSampleExpressPriceEdit.getText().toString());
                        deliveryRecord.setSendTime(sendSampleExpressTimeView.getText().toString());
                        sendRecordAdapter.add(deliveryRecord);
                        Toast.makeText(getActivity().getApplicationContext(),"添加成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity().getApplicationContext(),"发生错误，请重试",Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        });

    }


    public void sendSampleFinalSubmit(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",order.getOrderId());
        params.put("taskId",orderInfo.getTaskId());
        params.put("isFinal","true");
        client.post(IPAddress.getIP()+sendSampleSubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(getActivity().getApplicationContext(),"上传成功",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                getActivity().finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressDialog.dismiss();
                getActivity().finish();
            }
        });


    }




}
