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
import nju.zhizaolian.help.MyUtils;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Logistics;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;

/**
 * 完成最终发货
 */
public class SendClothesFragment extends Fragment {
    private static String sendClothesDetailUrl="/fmc/logistics/mobile_sendClothesDetail.do";
    private static String sendClothesSubmitUrl="/fmc/logistics/mobile_sendClothesSubmit.do";


    private TextView clothesNumberView;
    private TextView receiveGoodsNameView;
    private TextView sendClothesPhoneView;
    private TextView sendClothesPostAddressView;
    private TextView expressTimeView;
    private Spinner expressNameSpinner;
    private EditText expressNumberEdit;
    private EditText expressActualPriceEdit;
    private EditText expressRemarkEdit;
    private Button sendClothesButton;


    private ListInfo listInfo;
    private Order order;
    private OrderInfo orderInfo;


    private ProgressDialog progressDialog;
    public SendClothesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_send_clothes, container, false);
        listInfo= (ListInfo) getArguments().getSerializable("info");



        clothesNumberView=(TextView)view.findViewById(R.id.clothes_number_view);
        receiveGoodsNameView=(TextView)view.findViewById(R.id.receive_goods_name_view);
        sendClothesPhoneView=(TextView)view.findViewById(R.id.send_clothes_phone_view);
         sendClothesPostAddressView=(TextView)view.findViewById(R.id.send_clothes_post_address_view);
         expressTimeView=(TextView)view.findViewById(R.id.send_clothes_time_view);
        expressNameSpinner=(Spinner)view.findViewById(R.id.send_clothes_express_name_spinner);
         expressNumberEdit=(EditText)view.findViewById(R.id.send_clothes_express_number_edit);
         expressActualPriceEdit=(EditText)view.findViewById(R.id.send_clothes_actual_price_edit);
         expressRemarkEdit=(EditText)view.findViewById(R.id.send_clothes_remark_edit);
         sendClothesButton=(Button)view.findViewById(R.id.send_clothes_button);

        progressDialog=ProgressDialog.show(getActivity(),"请等待","数据下载中",true,true);
        getSendClothesDetail();


        sendClothesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(expressNumberEdit.getText().length() == 0||expressActualPriceEdit.getText().length() == 0||
                        expressRemarkEdit.getText().length() == 0){
                    Toast.makeText(getActivity(),"请填写信息",Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setTitle("提示");
                    builder.setMessage("确定提交?");
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog=ProgressDialog.show(getActivity(),"请等待","数据上传中",true,true);
                            sendClothesSubmit();

                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();

                }

            }
        });
        return view;
    }

    public void getSendClothesDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();

        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",listInfo.getOrder().getOrderId());
        client.get(IPAddress.getIP()+sendClothesDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("ddd",response.toString());
                try {
                    orderInfo=OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                    Logistics logistics=orderInfo.getLogistics();
                    order=orderInfo.getOrder();
                    clothesNumberView.setText(orderInfo.getPackageNumber());
                    receiveGoodsNameView.setText(logistics.getSampleClothesName());
                    sendClothesPhoneView.setText(logistics.getSampleClothesPhone());
                    sendClothesPostAddressView.setText(logistics.getSampleClothesAddress());
                    expressTimeView.setText(MyUtils.getCurrentDate());


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

    public void sendClothesSubmit(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",order.getOrderId());
        params.put("taskId",orderInfo.getTaskId());
        params.put("time",expressTimeView.getText().toString());
        params.put("name",expressNameSpinner.getSelectedItem().toString());
        params.put("number",expressNumberEdit.getText().toString());
        params.put("price",expressActualPriceEdit.getText().toString());
        params.put("remark",expressRemarkEdit.getText().toString());
        params.put("isFinal","true");
        client.post(IPAddress.getIP()+sendClothesSubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Toast.makeText(getActivity(),"完成最终发货",Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressDialog.dismiss();
            }
        });

    }



}
