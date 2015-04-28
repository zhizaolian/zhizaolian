package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
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

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentDesignActivity;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentDesignEnteringFragment extends Fragment {
    Button choose_version_file_button;
    EditText version_maker_name_edit_text;
    EditText version_done_time_edit_text;
    CircularProgressButton design_entering_accept_button;
    ImageButton design_entering_cancel_button;
    ImageView version_file_image_view;
    TextView cad_version_string;
    TextView cad_upload_time_string;
    TextView version_maker_name_string;
    TextView version_done_time_string;
    ImageButton design_undo_button;
    ImageButton design_entering_back_button;
    ListInfo listInfo;
    String taskId;
    String path=null;
    String orderSampleStatus;

    public DepartmentDesignEnteringFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_design_entering_fragment_layout, container, false);
        listInfo =(ListInfo) getArguments().get("info");
        choose_version_file_button=(Button) view.findViewById(R.id.choose_version_file_button);
        version_maker_name_edit_text=(EditText) view.findViewById(R.id.version_maker_name_edit_text);
        version_done_time_edit_text=(EditText) view.findViewById(R.id.version_done_time_edit_text);
        cad_version_string =(TextView) view.findViewById(R.id.cad_version_string);
        cad_upload_time_string =(TextView) view.findViewById(R.id.cad_upload_time_string);
        version_maker_name_string =(TextView) view.findViewById(R.id.version_maker_name_string);
        version_done_time_string =(TextView) view.findViewById(R.id.version_done_time_string);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate=new Date(System.currentTimeMillis());
        String timeString=dateFormat.format(curDate);
        version_done_time_edit_text.setText(timeString);
        version_done_time_edit_text.setEnabled(false);
        design_entering_accept_button=(CircularProgressButton) view.findViewById(R.id.design_entering_accept_button);
        design_entering_accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(design_entering_accept_button.getProgress()==0) {
                    if (path != null) {
                        uploadImageToServer("/fmc/design/mobile_uploadDesignSubmit.do", path);
                    } else {
                        Toast.makeText(getActivity(), "选择文件出错", Toast.LENGTH_SHORT).show();
                    }
                }else if(design_entering_accept_button.getProgress()==-1){
                    design_entering_accept_button.setProgress(0);
                }else if (design_entering_accept_button.getProgress()==100){
                    if(orderSampleStatus.equals("1")||orderSampleStatus.equals("2")){
                        new AlertDialog.Builder(getActivity())
                        .setTitle("警告")
                        .setMessage("样衣原料未准备好")
                        .setPositiveButton("知道了(⊙_⊙)",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
                    }else {
                        produceSampleSubmitToServer("1");
                    }
                }else {
                    Toast.makeText(getActivity(), "上传中，稍候", Toast.LENGTH_SHORT).show();
                }
            }
        });
        design_entering_cancel_button=(ImageButton) view.findViewById(R.id.design_entering_cancel_button);
        design_entering_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("确认终止订单？")
                        .setMessage("订单编号"+listInfo.getOrderId())
                        .setPositiveButton("嗯(⊙_⊙)",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                produceSampleSubmitToServer("0");
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("不(>_<)",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
        design_undo_button = (ImageButton) view .findViewById(R.id.design_undo_button);

        design_undo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        if(msg.what==101){
                            design_undo_button.setVisibility(View.GONE);
                            design_entering_accept_button.setProgress(0);
                            choose_version_file_button.setEnabled(true);
                            version_maker_name_edit_text.setEnabled(true);
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            Date curDate=new Date(System.currentTimeMillis());
                            String timeString=dateFormat.format(curDate);
                            version_done_time_edit_text.setText(timeString);
                        }
                    }
                };
                Message message = new Message();
                message.what=101;
                handler.sendMessage(message);

            }
        });
        version_file_image_view = (ImageView) view.findViewById(R.id.version_file_image_view);
        choose_version_file_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
        design_entering_back_button = (ImageButton) view.findViewById(R.id.design_entering_back_button);
        design_entering_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentDesignActivity) getActivity()).goBack();
            }
        });
        getTaskIdFromServer(listInfo.getOrder().getOrderId());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==getActivity().RESULT_OK){
            Uri uri = data.getData();
            path=getRealFilePath(uri);
            ContentResolver cr = getActivity().getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                version_file_image_view.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void produceSampleSubmitToServer(String result){
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
            String jSessionId=settings.getString("jsessionId","");
            params.put("jsessionId",jSessionId);
            params.put("orderId",listInfo.getOrder().getOrderId());
            params.put("taskId",taskId);
            params.put("result",result);
            client.post(IPAddress.getIP()+"/fmc/design/mobile_produceSampleSubmit.do",params,new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        boolean isSuccess=response.getBoolean("isSuccess");
                        if(isSuccess) {
                             Toast.makeText(getActivity(), "操作成功\n该订单跳转下一步", Toast.LENGTH_SHORT).show();
                            ((DepartmentDesignActivity) getActivity()).goBack();
                        }else{
                            Toast.makeText(getActivity(), "操作失败\n服务器表示亚历山大", Toast.LENGTH_SHORT).show();
                        }
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

    public void uploadImageToServer(String web_url, String path){
        String name =version_maker_name_edit_text.getText().toString();
        if(name==null||name.length()==0){
            Toast.makeText(getActivity(), "制版人名字不为空", Toast.LENGTH_LONG).show();
            version_maker_name_edit_text.setError("制版人名字不为空");
            return;
        }
        File file = new File(path);
        if (file.exists() && file.length() > 0) {
            try {
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
                String jSessionId=settings.getString("jsessionId","");
                params.put("jsessionId",jSessionId);
                params.put("orderId",listInfo.getOrder().getOrderId());
                params.put("taskId",taskId);
                params.put("cadSide",version_maker_name_edit_text.getText().toString());
                params.put("completeTime",version_done_time_edit_text.getText().toString());
                params.put("CADFile", file);
                client.post(IPAddress.getIP()+web_url,params,new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            JSONObject designCad = response.getJSONObject("orderInfo").getJSONObject("designCad");
                            orderSampleStatus = response.getString("orderSampleStatus");
                            choose_version_file_button.setEnabled(false);
                            version_maker_name_edit_text.setEnabled(false);
                            setVersionAllText(designCad.getString("cadVersion"), designCad.getString("uploadTime"), designCad.getString("cadSide"), designCad.getString("completeTime"));
                            design_undo_button.setVisibility(View.VISIBLE);
                            design_entering_accept_button.setProgress(100);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        design_entering_accept_button.setProgress(-1);
                        Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProgress(int bytesWritten, int totalSize) {
                        super.onProgress(bytesWritten, totalSize);
                        int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                        design_entering_accept_button.setProgress(count);
                        if(count==100){
                            design_entering_accept_button.setProgress(99);
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

    public void getTaskIdFromServer(String orderId){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",orderId);
        client.get(IPAddress.getIP()+"/fmc/design/mobile_getUploadDesignDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    taskId = response.getJSONObject("orderInfo").getString("taskId");
                    JSONObject designCad = response.getJSONObject("orderInfo").getJSONObject("designCad");
                    setVersionAllText(designCad.getString("cadVersion"),designCad.getString("uploadTime"),designCad.getString("cadSide"),designCad.getString("completeTime"));
                    orderSampleStatus = response.getString("orderSampleStatus");
                    if(designCad.getString("cadVersion").equals("1")){
                        design_entering_accept_button.setProgress(0);
                        design_undo_button.setVisibility(View.GONE);
                    }else {
                        design_entering_accept_button.setProgress(100);
                        design_undo_button.setVisibility(View.VISIBLE);
                        choose_version_file_button.setEnabled(false);
                        version_maker_name_edit_text.setEnabled(false);
                    }
                    design_entering_accept_button.setEnabled(true);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(),"网络连接错误",Toast.LENGTH_SHORT).show();
            }
        });
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

    public void setVersionAllText(String version,String uploadTime,String name,String completedTime){
        cad_version_string.setText(version);
        cad_upload_time_string.setText(uploadTime);
        version_maker_name_string.setText(name);
        version_done_time_string.setText(completedTime);
    }
}
