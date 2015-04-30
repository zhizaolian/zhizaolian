package nju.zhizaolian.fragments;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentTechnologyActivity;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Craft;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentTechnologySampleFragment extends Fragment {
    TextView sample_customer_technology_request_text;
    TextView sample_ironing_flower_cost_text;
    TextView sample_washing_cost_text;
    CircularProgressButton sample_image_uploading_button;
    TextView sample_laser_cost_text;
    TextView sample_embroidery_cost_text;
    TextView sample_crushed_cost_text;
    TextView sample_plate_charge_cost_text;
    EditText sample_technology_leading_name_edit_text;
    EditText sample_technology_mass_complete_date_edit_text;
    ImageButton sample_technology_mass_accept_button;
    ImageButton sample_technology_mass_back_button;
    Button sample_image_upload_button;
    ImageView sample_uploaded_image_view;
    ImageView sample_uploading_image_view;
    String path;
    String taskId;
    ListInfo info;
    Account account;
    ProgressDialog progressDialog;


    public DepartmentTechnologySampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.department_technology_sample_fragment_layout, container, false);
        info= (ListInfo) getArguments().getSerializable("info");
        account = (Account) getArguments().getSerializable("account");
        sample_customer_technology_request_text=(TextView) view.findViewById(R.id.mass_check_customer_technology_request_text);
        sample_ironing_flower_cost_text=(TextView) view.findViewById(R.id.check_ironing_flower_cost_edit_text);
        sample_washing_cost_text=(TextView) view.findViewById(R.id.check_washing_cost_edit_text);
        sample_laser_cost_text=(TextView) view.findViewById(R.id.check_laser_cost_text);
        sample_embroidery_cost_text=(TextView) view.findViewById(R.id.check_embroidery_cost_edit_text);
        sample_crushed_cost_text=(TextView) view.findViewById(R.id.check_crushed_cost_edit_text);
        sample_plate_charge_cost_text=(TextView) view.findViewById(R.id.check_plate_charge_cost_edit_text);
        sample_technology_leading_name_edit_text=(EditText) view.findViewById(R.id.sample_technology_leading_name_edit_text);
        sample_technology_leading_name_edit_text.setText(account.getNickName());
        sample_technology_mass_complete_date_edit_text=(EditText) view.findViewById(R.id.sample_technology_mass_complete_date_edit_text);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate=new Date(System.currentTimeMillis());
        String timeString=dateFormat.format(curDate);
        sample_technology_mass_complete_date_edit_text.setText(timeString);
        sample_technology_mass_accept_button=(ImageButton) view.findViewById(R.id.sample_technology_mass_accept_button);
        sample_technology_mass_accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sample_technology_leading_name_edit_text.getText().toString()==null||sample_technology_leading_name_edit_text.getText().toString().length()<1){
                    Toast.makeText(getActivity(),"加工方必须写",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog= ProgressDialog.show(getActivity(),"请等待","正在处理",true);
                needCraftSampleSubmit();
            }
        });
        sample_technology_mass_back_button = (ImageButton) view.findViewById(R.id.sample_technology_mass_back_button);
        sample_technology_mass_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentTechnologyActivity) getActivity()).goBack();
            }
        });

        sample_image_uploading_button=(CircularProgressButton) view.findViewById(R.id.sample_image_uploading_button);
        sample_image_uploading_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sample_image_uploading_button.getProgress()==0){
                    Toast.makeText(getActivity(),"path="+path,Toast.LENGTH_SHORT).show();
                    if(path==null){
                        Toast.makeText(getActivity(),"请先选择文件",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    uploadCraftFileSubmit();
                }else if(sample_image_uploading_button.getProgress()==100){
                    sample_image_uploading_button.setProgress(0);
                    sample_image_upload_button.setEnabled(true);
                }else if(sample_image_uploading_button.getProgress()==-1){
                    sample_image_uploading_button.setProgress(0);
                }else{
                    Toast.makeText(getActivity(),"正在上传",Toast.LENGTH_SHORT).show();
                }
            }
        });
        sample_image_upload_button=(Button) view.findViewById(R.id.sample_image_upload_button);
        sample_uploaded_image_view=(ImageView) view.findViewById(R.id.sample_uploaded_image_view);
        sample_uploading_image_view=(ImageView) view.findViewById(R.id.sample_uploading_image_view);


        sample_image_upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
        getTaskIdFromServer(info.getOrder().getOrderId());
        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==getActivity().RESULT_OK){
            Uri uri = data.getData();
            path = getRealFilePath(uri);
            ContentResolver cr = getActivity().getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                sample_uploading_image_view.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealFilePath(Uri uri) {
        if ( null == uri ) return null;
        String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor =  getActivity().getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void getTaskIdFromServer(String orderId){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",orderId);
        client.get(IPAddress.getIP()+"/fmc/design/mobile_needCraftSampleDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    taskId = response.getJSONObject("orderInfo").getString("taskId");
                    Craft craft = Craft.fromJson(response.getJSONObject("orderInfo").getJSONObject("sampleCraft"));
                    if(craft.getCraftFileUrl().length()>1) {
                        Picasso.with(getActivity()).load(IPAddress.getIP() + craft.getCraftFileUrl()).error(R.drawable.ic_action_cancel).into(sample_uploaded_image_view);
                        sample_image_upload_button.setEnabled(false);
                        sample_image_uploading_button.setProgress(100);
                    }else{
                        sample_image_uploading_button.setProgress(0);
                    }
                    sample_customer_technology_request_text.setText(response.getJSONObject("orderInfo").getString("designCadTech"));
                    sample_ironing_flower_cost_text.setText(""+craft.getStampDutyMoney());
                    sample_washing_cost_text.setText(""+craft.getWashHangDyeMoney());
                    sample_laser_cost_text.setText(""+craft.getLaserMoney());
                    sample_embroidery_cost_text.setText(""+craft.getEmbroideryMoney());
                    sample_crushed_cost_text.setText(""+craft.getCrumpleMoney());
                    sample_plate_charge_cost_text.setText(""+craft.getOpenVersionMoney());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void needCraftSampleSubmit(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",info.getOrder().getOrderId());
        params.put("craftLeader",sample_technology_leading_name_edit_text.getText().toString());
        params.put("completeTime",sample_technology_mass_complete_date_edit_text.getText().toString());
        params.put("taskId",taskId);
        client.post(IPAddress.getIP()+"/fmc/design/mobile_needCraftSampleSubmit.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getActivity(), "成功\n进入下一步", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                ((DepartmentTechnologyActivity) getActivity()).goBack();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void uploadCraftFileSubmit(){
        File file = new File(path);
        if (file.exists() && file.length() > 0) {
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
                String jSessionId=settings.getString("jsessionId","");
                params.put("jsessionId",jSessionId);
                params.put("orderId",info.getOrder().getOrderId());
                params.put("craftFile", file);
                client.post(IPAddress.getIP()+"/fmc/design/mobile_uploadCraftFileSubmit.do",params,new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Craft craft = Craft.fromJson(response.getJSONObject("orderInfo").getJSONObject("sampleCraft"));
                            Picasso.with(getActivity()).load(IPAddress.getIP() + craft.getCraftFileUrl()).error(R.drawable.ic_action_cancel).into(sample_uploaded_image_view);
                            sample_image_upload_button.setEnabled(false);
                            sample_image_uploading_button.setProgress(100);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        sample_image_uploading_button.setProgress(-1);
                        Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProgress(int bytesWritten, int totalSize) {
                        super.onProgress(bytesWritten, totalSize);
                        int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                        sample_image_uploading_button.setProgress(count);
                        if(count==100){
                            sample_image_uploading_button.setProgress(99);
                        }
                    }

                    @Override
                    public void onRetry(int retryNo) {
                        super.onRetry(retryNo);
                    }
                });


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Toast.makeText(getActivity(), "文件不存在", Toast.LENGTH_LONG).show();
        }


    }
}
