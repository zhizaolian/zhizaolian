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
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import nju.zhizaolian.R;
import nju.zhizaolian.help.FormFile;
import nju.zhizaolian.help.PictureUtil;
import nju.zhizaolian.help.UpLoadUtil;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;
import nju.zhizaolian.models.Quote;

/**
 *
 */
public class QuoteAgreedFragment extends Fragment {
    public static  int RESULT_LOAD_CONFIRM_QUOTE_IMAGE=0;
    private String confirmQuoteDetailUrl="/fmc/market/mobile_confirmQuoteDetail.do";
    private String confirmQuoteSubmitUrl="/fmc/market/mobile_confirmQuoteSubmit.do";

    private Button confirmQuotePictureButton;
    private ImageView confirmQuoteImage;
    private EditText confirmQuoteRemarkEdit;
    private Button ensureConfirmQuoteButton;
    private Button changeQuoteButton;
    private Button unableConfirmQuoteButton;
    private EditText profitPerClothesEdit;
    private TextView singleCostView;
    private TextView innerCostView;
    private TextView outerCostView;





    private byte[] confirmQuotePictureByte;
    private ListInfo listInfo;
    private Order order;
    private OrderInfo orderInfo;

    private String  confirmQuotePictureUrl;
    private ProgressDialog progressDialog;
    public QuoteAgreedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_quote_agreed, container, false);
        listInfo= (ListInfo) getArguments().getSerializable("info");
        progressDialog=ProgressDialog.show(container.getContext(),"请等待","数据更新中",true);
        getConfirmQuoteDetail();
        confirmQuotePictureButton=(Button)view.findViewById(R.id.confirm_quote_picture_button);
        confirmQuoteImage=(ImageView)view.findViewById(R.id.receive_sample_money_image);
        confirmQuoteRemarkEdit=(EditText)view.findViewById(R.id.quote_agreed_remark_edit);
        ensureConfirmQuoteButton=(Button)view.findViewById(R.id.ensure_quote_agreed_button);
        changeQuoteButton=(Button)view.findViewById(R.id.change_quote_button);
        unableConfirmQuoteButton=(Button)view.findViewById(R.id.unable_confirm_quote_button);
        profitPerClothesEdit=(EditText)view.findViewById(R.id.profit_per_clothes_edit);
        singleCostView=(TextView)view.findViewById(R.id.total_cost_view);
        innerCostView=(TextView)view.findViewById(R.id.produce_price_view);
        outerCostView=(TextView)view.findViewById(R.id.custom_quote_view);




        confirmQuotePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaImageFromGallery(RESULT_LOAD_CONFIRM_QUOTE_IMAGE);
            }
        });
        ensureConfirmQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(confirmQuoteRemarkEdit.getText().length()==0||confirmQuotePictureByte==null){
                    Toast.makeText(container.getContext(),"请填写完整信息",Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder builder =new AlertDialog.Builder(container.getContext());
                    builder.setMessage("确定操作?");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog=ProgressDialog.show(container.getContext(),"请稍等","提交中",true);
                            confirmQuoteSubmit();
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
        changeQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeQuoteSubmit();
            }
        });
        unableConfirmQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unableConfirmQuoteSubmit();
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
            if(requestCode == RESULT_LOAD_CONFIRM_QUOTE_IMAGE && resultCode == getActivity().RESULT_OK && null != data){
                Uri selectImage=data.getData();
                String[] filePathColumn={MediaStore.Images.Media.DATA};
                Cursor cursor=getActivity().getContentResolver().query(selectImage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                confirmQuotePictureUrl=cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap= BitmapFactory.decodeFile(confirmQuotePictureUrl);
                confirmQuotePictureByte= PictureUtil.Bitmap2Bytes(bitmap);
                confirmQuoteImage.setImageBitmap(bitmap);

            }else {
                Toast.makeText(getActivity().getApplicationContext(), "你还没有选择图片", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(),"出现异常",Toast.LENGTH_SHORT).show();
        }


    }

    public void changeQuoteSubmit(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId",order.getOrderId());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("result","1");
        params.put("taskId",orderInfo.getTaskId());
        params.put("orderId",order.getOrderId());
        client.post(IPAddress.getIP()+confirmQuoteSubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if(response.getString("isSuccess").equals("true")){
                        Toast.makeText(getActivity().getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity().getApplicationContext(), "出现异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void confirmQuoteSubmit(){

        ConfirmQuoteSubmitTask task=new ConfirmQuoteSubmitTask();
        task.execute((Void[]) null);


    }
    public class ConfirmQuoteSubmitTask extends AsyncTask<Void,Void,Boolean>{

        @Override
        protected Boolean doInBackground(Void... params) {
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
            String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
            FormFile formFile=new FormFile(confirmQuotePictureUrl,confirmQuotePictureByte,"confirmSampleMoneyFile",null);
            Map<String,String> requestParams=new HashMap<String,String>();
            requestParams.put("jsessionId",jsessionId);
            requestParams.put("result","0");
            requestParams.put("taskId",orderInfo.getTaskId());
            requestParams.put("orderId",order.getOrderId());
            requestParams.put("moneyremark",confirmQuoteRemarkEdit.getText().toString());
            try {
                UpLoadUtil.post(IPAddress.getIP()+confirmQuoteSubmitUrl,requestParams,formFile);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            progressDialog.dismiss();
            Toast.makeText(getActivity().getApplicationContext(),"上传成功",Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }
    }




    public void unableConfirmQuoteSubmit(){
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId",order.getOrderId());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("result","2");
        params.put("taskId",orderInfo.getTaskId());
        params.put("orderId",order.getOrderId());
        client.post(IPAddress.getIP()+confirmQuoteSubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    if(response.getString("isSuccess").equals("true")){
                        Toast.makeText(getActivity().getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getActivity().getApplicationContext(), "出现异常", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getConfirmQuoteDetail(){

        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        params.put("orderId", listInfo.getOrder().getOrderId());
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common",0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        client.get(IPAddress.getIP()+confirmQuoteDetailUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.d("success", response.toString());
                    try {
                        orderInfo= OrderInfo.fromJson(response.getJSONObject("orderInfo"));
                        order=orderInfo.getOrder();
                        Quote quote=orderInfo.getQuote();
                        profitPerClothesEdit.setText(orderInfo.getQuote().getProfitPerPiece());
                        profitPerClothesEdit.setEnabled(false);
                        singleCostView.setText(String.valueOf(quote.getSingleCost()));
                        innerCostView.setText(quote.getInnerPrice());
                        outerCostView.setText(quote.getOuterPrice());
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("failure",responseString);
            }
        });
    }


}
