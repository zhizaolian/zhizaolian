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
import android.text.Editable;
import android.text.TextWatcher;
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
import nju.zhizaolian.adapters.ProduceAdapter;
import nju.zhizaolian.help.PictureUtil;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Order;
import nju.zhizaolian.models.OrderInfo;
import nju.zhizaolian.models.Produce;
import nju.zhizaolian.models.Quote;

/**
 *
 *
 */
public class SignContractFragment extends Fragment {
    private static final int RESULT_LOAD_CONTRACT_IMAGE=0;
    private static final int RESULT_LOAD_DEPOSIT_IMAGE=1;

    private static String signContractSubmitUrl="/fmc/market/mobile_confirmProduceOrderSubmit.do";
    private static String signContractDetailUrl="/fmc/market/mobile_confirmProduceOrderDetail.do";
    private EditText signContractDiscountEdit;
    private TextView signContractTotalMoneyView;
    private TextView signContractRemarkView;
    private Button  signContractUploadContractButton;
    private Button signContractUploadDepositButton;
    private ImageView signContractImage;
    private ImageView signContractDepositImage;
    private Button ensureContractButton;
    private Button cancelOrderButton;
    private EditText signContractColorEdit;
    private EditText signContractXSEdit;
    private EditText signContractSEdit;
    private EditText signContractMEdit;
    private EditText signContractLEdit;
    private EditText signContractXLEdit;
    private EditText signContractXXLEdit;
    private EditText signContractJEdit;
    private ListView signContractProduceListView;
    private Button signContractAddProduceButton;

    private ListInfo listInfo;
    private Order order;
    private OrderInfo orderInfo;
    private ProduceAdapter produceAdapter;

    private byte[] contractByte;
    private byte[] depositByte;
    private String contractImageUrl;
    private String depositImageUrl;
    private File contractFile;
    private File depositFile;

    private ProgressDialog progressDialog;

    private ArrayList<Produce> produceArrayList=new ArrayList<Produce>();

    public SignContractFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
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
        ensureContractButton=(Button)view.findViewById(R.id.ensure_contract_button);
        cancelOrderButton=(Button)view.findViewById(R.id.cancel_contract_button);
        signContractColorEdit=(EditText)view.findViewById(R.id.sign_contract_color_edit);
        signContractXSEdit=(EditText)view.findViewById(R.id.sign_contract_xs_edit);
        signContractSEdit=(EditText)view.findViewById(R.id.sign_contract_s_edit);
        signContractMEdit=(EditText)view.findViewById(R.id.sign_contract_m_edit);
        signContractLEdit=(EditText)view.findViewById(R.id.sign_contract_l_edit);
        signContractXLEdit=(EditText)view.findViewById(R.id.sign_contract_xl_edit);
        signContractXXLEdit=(EditText)view.findViewById(R.id.sign_contract_xxl_edit);
        signContractJEdit=(EditText)view.findViewById(R.id.sign_contract_j_edit);
        signContractProduceListView=(ListView)view.findViewById(R.id.sign_contract_list_view);
        signContractAddProduceButton=(Button)view.findViewById(R.id.sign_contract_add_produce_button);

        getSignContractFragmentDetail();

        signContractAddProduceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(signContractColorEdit.getText().length() == 0||signContractXSEdit.getText().length() == 0||
                       signContractSEdit.getText().length() == 0||signContractMEdit.getText().length() == 0||
                       signContractLEdit.getText().length() == 0||signContractXLEdit.getText().length() == 0||
                       signContractXXLEdit.getText().length() == 0||signContractJEdit.getText().length() == 0){
                   Toast.makeText(container.getContext(),"请填写完整信息",Toast.LENGTH_SHORT).show();
               }else {
                   Produce produce=new Produce();
                   produce.setColor(signContractColorEdit.getText().toString());
                   produce.setXs(signContractXSEdit.getText().toString());
                   produce.setS(signContractSEdit.getText().toString());
                   produce.setM(signContractMEdit.getText().toString());
                   produce.setL(signContractLEdit.getText().toString());
                   produce.setXl(signContractXLEdit.getText().toString());
                   produce.setXxl(signContractXXLEdit.getText().toString());
                   produce.setJ(signContractJEdit.getText().toString());
                   produceAdapter.add(produce);
                   produceArrayList.add(produce);
                   produceAdapter.notifyDataSetChanged();
               }

            }
        });

        signContractDiscountEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int i= Integer.parseInt(signContractTotalMoneyView.getText().toString());
                int j=0;
                if(s.toString().equals("")){
                    Quote quote=orderInfo.getQuote();
                    int outerPrice= Integer.parseInt(quote.getOuterPrice());
                    int askAmount= Integer.parseInt(order.getAskAmount());
                    signContractTotalMoneyView.setText(String.valueOf(outerPrice*askAmount));
                }else {
                  j= Integer.parseInt(s.toString());
                    signContractTotalMoneyView.setText(String.valueOf(i-j));
                }


            }
        });
        signContractUploadContractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 loadImageFromGallery(RESULT_LOAD_CONTRACT_IMAGE);
            }
        });

        signContractUploadDepositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImageFromGallery(RESULT_LOAD_DEPOSIT_IMAGE);
            }
        });

        ensureContractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(signContractDiscountEdit.getText().length() == 0){
                    Toast.makeText(container.getContext(),"请填写折扣",Toast.LENGTH_SHORT).show();
                }else if (contractByte == null){
                    Toast.makeText(container.getContext(),"请上传合同图片",Toast.LENGTH_SHORT).show();
                }else if (depositByte == null){
                    Toast.makeText(container.getContext(),"请上传首定金图片",Toast.LENGTH_SHORT).show();
                }else {
                    AlertDialog.Builder builder=new AlertDialog.Builder(container.getContext());
                    builder.setMessage("确认提交合同信息?");
                    builder.setPositiveButton("确认",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            progressDialog=ProgressDialog.show(container.getContext(),"请等待","",true);

                            signContractSubmit();

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
        cancelOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(container.getContext(),"暂不可用",Toast.LENGTH_SHORT).show();
            }
        });

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
                    Quote quote=orderInfo.getQuote();
                    int i= Integer.parseInt(quote.getOuterPrice());
                    int j= Integer.parseInt(order.getAskAmount());
                    signContractTotalMoneyView.setText(String.valueOf(i*j));
                    signContractRemarkView.setText(order.getMoneyremark());
                    produceArrayList=orderInfo.getProduceArrayList();
                    produceAdapter=new ProduceAdapter(getActivity().getApplicationContext(),0,produceArrayList);
                    signContractProduceListView.setAdapter(produceAdapter);
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
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams params=new RequestParams();
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("common", 0);
        String jsessionId=sharedPreferences.getString("jsessionId", "wrong");
        params.put("jsessionId",jsessionId);
        params.put("orderId",order.getOrderId());
        params.put("taskId",orderInfo.getTaskId());
        params.put("processId",orderInfo.getProcessInstanceId());
        params.put("tof","1");

        for(Produce p:produceArrayList){
            String produceColor=p.getColor()+",";
            String produceXS=p.getXs()+",";
            String produceS=p.getS()+",";
            String produceM=p.getM()+",";
            String produceL=p.getL()+",";
            String produceXL=p.getXl()+",";
            String produceXXL=p.getXxl()+",";
            String produceJ=p.getJ()+",";
            params.put("produce_color",produceColor);
            params.put("produce_xs",produceXS);
            params.put("produce_s",produceS);
            params.put("produce_m",produceM);
            params.put("produce_l",produceL);
            params.put("produce_xl",produceXL);
            params.put("produce_xxl",produceXXL);
            params.put("produce_j",produceJ);
        }

        params.put("discount",signContractDiscountEdit.getText().toString());
        params.put("totalmoney",signContractTotalMoneyView.getText().toString());
        params.put("moneyremark",signContractRemarkView.getText().toString());
        try {
            params.put("contractFile",contractFile);
            params.put("confirmDepositFile",depositFile);
        } catch (FileNotFoundException e) {
            Toast.makeText(getActivity().getApplicationContext(),"上传图片失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        client.post(IPAddress.getIP()+signContractSubmitUrl,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Log.d("result",response.toString());
                progressDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(),"提交成功",Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                progressDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(),"提交失败，请稍后再试",Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void cancelProduct(){

    }
    private void loadImageFromGallery(int i){
        Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, i);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode == RESULT_LOAD_CONTRACT_IMAGE && resultCode == getActivity().RESULT_OK && null != data){
                Uri selectImage=data.getData();
                String[] filePathColumn={MediaStore.Images.Media.DATA};
                Cursor cursor=getActivity().getContentResolver().query(selectImage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                contractImageUrl=cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap= BitmapFactory.decodeFile(contractImageUrl);
                contractFile=new File(contractImageUrl);
                contractByte= PictureUtil.Bitmap2Bytes(bitmap);
                signContractImage.setImageBitmap(bitmap);

            }else if(requestCode == RESULT_LOAD_DEPOSIT_IMAGE && resultCode==getActivity().RESULT_OK && null != data){
                Uri selectImage=data.getData();
                String[] filePathColumn={MediaStore.Images.Media.DATA};
                Cursor  cursor=getActivity().getContentResolver().query(selectImage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                depositImageUrl=cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap= BitmapFactory.decodeFile(depositImageUrl);
                depositByte=PictureUtil.Bitmap2Bytes(bitmap);
                depositFile=new File(depositImageUrl);
                signContractDepositImage.setImageBitmap(bitmap);

            }else {
                Toast.makeText(getActivity().getApplicationContext(), "你还没有选择图片", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(),"出现异常",Toast.LENGTH_SHORT).show();
        }


    }




}
