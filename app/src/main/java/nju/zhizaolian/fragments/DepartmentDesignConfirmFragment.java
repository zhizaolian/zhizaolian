package nju.zhizaolian.fragments;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentDesignActivity;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentDesignConfirmFragment extends Fragment {
    Button design_confirm_choose_version_file_button;
    CircularProgressButton design_confirm_accept_button;
    ImageButton design_confirm_back_button;
    ImageView design_confirm_version_file_image_view;
    String path;
    String taskId;
    ListInfo info;


    public DepartmentDesignConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_design_confirm_fragment_layout, container, false);
        info = (ListInfo) getArguments().getSerializable("info");
        design_confirm_choose_version_file_button=(Button) view.findViewById(R.id.design_confirm_choose_version_file_button);
        design_confirm_accept_button=(CircularProgressButton) view.findViewById(R.id.design_confirm_accept_button);
        design_confirm_accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(design_confirm_accept_button.getProgress()==0){
                    if (path != null) {
                        confirmCadSubmit();
                    }else{
                        Toast.makeText(getActivity(), "文件选择出错", Toast.LENGTH_SHORT).show();
                    }
                }else if(design_confirm_accept_button.getProgress()==-1){
                    design_confirm_accept_button.setProgress(0);
                }else if(design_confirm_accept_button.getProgress()==100){
                    Toast.makeText(getActivity(), "上传已完成等待跳转", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "上传中，稍候！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        design_confirm_accept_button.setEnabled(false);
        design_confirm_back_button =(ImageButton) view.findViewById(R.id.design_confirm_back_button);
        design_confirm_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentDesignActivity)getActivity()).goBack();
            }
        });
        design_confirm_version_file_image_view=(ImageView) view.findViewById(R.id.design_confirm_version_file_image_view);
        design_confirm_choose_version_file_button.setOnClickListener(new View.OnClickListener() {
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
            path=getRealFilePath(uri);
            ContentResolver contentResolver= getActivity().getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                design_confirm_version_file_image_view.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
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
        client.get(IPAddress.getIP()+"/fmc/design/mobile_getConfirmCadDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                taskId = response.getJSONObject("orderInfo").getString("taskId");
                design_confirm_accept_button.setEnabled(true);
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

    public void confirmCadSubmit(){
            File file = new File(path);
            if (file.exists() && file.length() > 0) {
                try {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
                    String jSessionId=settings.getString("jsessionId","");
                    params.put("jsessionId",jSessionId);
                    params.put("orderId",info.getOrder().getOrderId());
                    params.put("taskId",taskId);
                    params.put("CADFile", file);
                    client.post(IPAddress.getIP()+"/fmc/design/mobile_confirmCadSubmit.do",params,new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                boolean isSuccess=response.getBoolean("isSuccess");
                                if(isSuccess) {
                                    design_confirm_accept_button.setProgress(100);
                                    Toast.makeText(getActivity(), "成功\n进入下一步", Toast.LENGTH_LONG).show();
                                    ((DepartmentDesignActivity) getActivity()).goBack();
                                }else{
                                    Toast.makeText(getActivity(), "服务器出错", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                            design_confirm_accept_button.setProgress(-1);
                            Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onProgress(int bytesWritten, int totalSize) {
                            super.onProgress(bytesWritten, totalSize);
                            int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                            design_confirm_accept_button.setProgress(count);
                            if(count==100){
                                design_confirm_accept_button.setProgress(99);
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
