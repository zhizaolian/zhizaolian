package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.adapters.SendRecordAdapter;
import nju.zhizaolian.help.PictureUtil;
import nju.zhizaolian.models.DeliveryRecord;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;
import nju.zhizaolian.models.Quote;

/**
 *
 */
public class UrgeRemainingBalance extends Fragment {
    private static String urgeRemainingMoneyDetailUrl="/fmc/market/mobile_getPushRestOrderDetail.do";
    private static String urgeRemainingMoneySubmitUrl="/fmc/market/mobile_getPushRestOrderSubmit.do";
    private static int URGE_LOAD_IMAGE=0;


    private ListInfo listInfo;
    private Order order;
    private OrderInfo orderInfo;
    private File remainingFile;

    private TextView moneyTypeView;
    private TextView moneyDiscountView;
    private TextView moneyDeserveView;
    private TextView goodsNumberView;
    private TextView goodsUnitPriceView;
    private TextView goodsTotalPriceView;
    private TextView sampleNumberView;
    private TextView sampleUnitPriceView;
    private TextView sampleTotalPriceView;
    private Button selectPictureButton;
    private ImageView imageView;
    private EditText remarkEdit;
    private Button ensureButton;
    private Button unableButton;
    private ListView finalMoneyDeliveryListView;
    private SendRecordAdapter sendRecordAdapter;



    private ProgressDialog progressDialog;
    public UrgeRemainingBalance() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_urge_remaining_balance, container, false);
        listInfo = (ListInfo) getArguments().getSerializable("info");
         moneyTypeView=(TextView)view.findViewById(R.id.urge_money_type_view);
         moneyDiscountView=(TextView)view.findViewById(R.id.urge_money_discount_view);
        moneyDeserveView=(TextView)view.findViewById(R.id.urge_money_view);
         goodsNumberView=(TextView)view.findViewById(R.id.urge_goods_number_view);
         goodsUnitPriceView=(TextView)view.findViewById(R.id.urge_goods_unit_price_view);
         goodsTotalPriceView=(TextView)view.findViewById(R.id.urge_goods_total_price_view);
         sampleNumberView=(TextView)view.findViewById(R.id.urge_sample_number_view);
        sampleUnitPriceView=(TextView)view.findViewById(R.id.urge_sample_unit_price_view);
         sampleTotalPriceView=(TextView)view.findViewById(R.id.urge_sample_total_price_view);
         selectPictureButton=(Button)view.findViewById(R.id.urge_remaining_money_picture_button);
        imageView=(ImageView)view.findViewById(R.id.urge_imageView);
        remarkEdit=(EditText)view.findViewById(R.id.urge_remark_info_edit);
         ensureButton=(Button)view.findViewById(R.id.ensure_receive_urge_money_button);
         unableButton=(Button)view.findViewById(R.id.unable_receive_urge_money_button);
         finalMoneyDeliveryListView=(ListView)view.findViewById(R.id.final_money_delivery_record_list_view);


        progressDialog=ProgressDialog.show(getActivity(),"请等待","数据下载中",true,true);
        geUrgeRemainingMoneyDetail();
        selectPictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaImageFromGallery(URGE_LOAD_IMAGE);
            }
        });
        ensureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(remarkEdit.getText().length() == 0 || remainingFile == null){
                    Toast.makeText(getActivity(),"请填写信息",Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setMessage("确定收到尾款？");
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog=ProgressDialog.show(getActivity(),"请等待","",true,true);
                             urgeRemainingMoneySubmit(true);
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

        unableButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                builder.setMessage("确定未收到尾款？");
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog=ProgressDialog.show(getActivity(),"请等待","",true,true);
                        urgeRemainingMoneySubmit(false);
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

    private void loaImageFromGallery(int i){
        Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, i);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode == URGE_LOAD_IMAGE && resultCode == getActivity().RESULT_OK && null != data){
                Uri selectImage=data.getData();
                String[] filePathColumn={MediaStore.Images.Media.DATA};
                Cursor cursor=getActivity().getContentResolver().query(selectImage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                String samplePictureUrl=cursor.getString(columnIndex);
                remainingFile=new File(samplePictureUrl);
                cursor.close();
                Bitmap bitmap= BitmapFactory.decodeFile(samplePictureUrl);
                byte[] samplePictureByte= PictureUtil.Bitmap2Bytes(bitmap);
                imageView.setImageBitmap(bitmap);

            }else {
                Toast.makeText(getActivity().getApplicationContext(), "你还没有选择图片", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(),"出现异常",Toast.LENGTH_SHORT).show();
        }


    }





    public void geUrgeRemainingMoneyDetail(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId",listInfo.getOrder().getOrderId());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        client.get(IPAddress.getIP()+urgeRemainingMoneyDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("urge",response.toString());
                try {
                    orderInfo=OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                    order=orderInfo.getOrder();
                    Quote quote=orderInfo.getQuote();
                    ArrayList<DeliveryRecord> deliveryRecordArrayList=orderInfo.getDeliveryRecords();
                    if(deliveryRecordArrayList != null){
                        moneyTypeView.setText(orderInfo.getMoneyName());
                        moneyDiscountView.setText(order.getDiscount());
                        double designCost=quote.getDesignCost();
                        double price= Double.parseDouble(orderInfo.getPrice());
                        double number= Double.parseDouble(order.getAskAmount());
                        double discount= Double.parseDouble(order.getDiscount());
                        double deposit= Double.parseDouble(orderInfo.getDeposit());

                        double sampleNumber= Double.parseDouble(order.getSampleAmount());
                        double samplePrice= Double.parseDouble(orderInfo.getSamplePrice());
                        double deliveryMoney=0;
                        for(DeliveryRecord d:deliveryRecordArrayList){
                            deliveryMoney+=Double.parseDouble(d.getExpressPrice());
                        }
                        double deserveMoney=designCost+deliveryMoney+price*number-discount-deposit;


                        moneyDeserveView.setText(String.valueOf(deserveMoney));
                        goodsNumberView.setText(String.valueOf(number));
                        goodsUnitPriceView.setText(String.valueOf(price));
                        goodsTotalPriceView.setText(String.valueOf(price*number));
                        sampleNumberView.setText(order.getSampleAmount());
                        sampleUnitPriceView.setText(orderInfo.getSamplePrice());
                        sampleTotalPriceView.setText(String.valueOf(sampleNumber*samplePrice));
                        sendRecordAdapter=new SendRecordAdapter(getActivity(),0,orderInfo.getDeliveryRecords());
                        finalMoneyDeliveryListView.setAdapter(sendRecordAdapter);
                    }else {
                        moneyTypeView.setText(orderInfo.getMoneyName());
                        moneyDiscountView.setText(order.getDiscount());
                        double designCost=quote.getDesignCost();
                        double price= Double.parseDouble(orderInfo.getPrice());
                        double number= Double.parseDouble(order.getAskAmount());
                        double discount= Double.parseDouble(order.getDiscount());
                        double deposit= Double.parseDouble(orderInfo.getDeposit());

                        double sampleNumber= Double.parseDouble(order.getSampleAmount());
                        double samplePrice= Double.parseDouble(orderInfo.getSamplePrice());

                        double deserveMoney=designCost+price*number-discount-deposit;


                        moneyDeserveView.setText(String.valueOf(deserveMoney));
                        goodsNumberView.setText(String.valueOf(number));
                        goodsUnitPriceView.setText(String.valueOf(price));
                        goodsTotalPriceView.setText(String.valueOf(price*number));
                        sampleNumberView.setText(order.getSampleAmount());
                        sampleUnitPriceView.setText(orderInfo.getSamplePrice());
                        sampleTotalPriceView.setText(String.valueOf(sampleNumber*samplePrice));

                    }
                        remarkEdit.setText(order.getMoneyremark());

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


    public void urgeRemainingMoneySubmit(boolean result){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",order.getOrderId());
        params.put("taskId",orderInfo.getTaskId());
        params.put("moneyremark",remarkEdit.getText().toString());
        if (result){
            params.put("result","1");
        }else {
            params.put("result","0");
        }
        try {
            params.put("confirmFinalPaymentFile",remainingFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        client.post(IPAddress.getIP()+urgeRemainingMoneySubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    boolean result= Boolean.parseBoolean(response.getString("isSuccess"));
                    if(result){
                        Toast.makeText(getActivity(),"上传成功",Toast.LENGTH_SHORT).show();
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
