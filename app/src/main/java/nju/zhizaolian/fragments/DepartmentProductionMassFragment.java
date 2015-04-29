package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentProductionActivity;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Produce;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentProductionMassFragment extends Fragment {

    ImageButton acceptButton;
    ImageButton cancelButton;
    ImageButton back_button;
    TableLayout planTable;
    EditText processing_side_edit_text;
    String sizeList[] =new String[]{"颜色","XS","S","M","L","XL","XXL","均码"};
    ArrayList<HashMap<String,String>> planData;
    ArrayList<HashMap<String,String>> actuallyData = new ArrayList<>();
    String taskId;
    ListInfo info;
    String produce_color;
    String produce_xs;
    String produce_s;
    String produce_m;
    String produce_l;
    String produce_xl;
    String produce_xxl;
    String produce_j;
    String processing_side;
    ProgressDialog progressDialog;


    public DepartmentProductionMassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.department_production_mass_fragment_layout,container,false);
        info = (ListInfo) getArguments().getSerializable("info");
        acceptButton=(ImageButton)view.findViewById(R.id.production_process_accept_button);
        cancelButton=(ImageButton)view.findViewById(R.id.production_process_cancel_button);
        back_button=(ImageButton) view.findViewById(R.id.production_process_back_button);
        planTable =(TableLayout) view.findViewById(R.id.plan_production_table);
        processing_side_edit_text = (EditText)view.findViewById(R.id.processing_side_edit_text);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog= ProgressDialog.show(getActivity(),"请等待","正在处理",true);
                if(processing_side_edit_text.getText().toString()==null||processing_side_edit_text.getText().toString().length()<1){
                    Toast.makeText(getActivity(),"加工方一定要填写",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                for(int i=0;i<actuallyData.size();i++){
                    for(int j=0;j<sizeList.length;j++){
                        if(actuallyData.get(i).get(sizeList[j]).equals("")){
                            Toast.makeText(getActivity(),"所有表单都要填写",Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            return;
                        }
                    }
                }
                produceSubmit(true);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new AlertDialog.Builder(getActivity())
                       .setTitle("确定终止？")
                       .setMessage("订单号:"+info.getOrderId())
                       .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               progressDialog= ProgressDialog.show(getActivity(),"请等待","正在处理",true);
                               produceSubmit(false);
                               dialog.cancel();
                           }
                       })
                       .setNegativeButton("取消",new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.cancel();
                           }
                       })
                       .show();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentProductionActivity) getActivity()).goBack();
            }
        });
        Log.e("BBBYYYYOrderID",info.getOrder().getOrderId());
        getTaskIdFromServer(info.getOrder().getOrderId());
        return view;
    }

private  class MyTextWatcher implements TextWatcher{

    int i=0;
    int j=0;
    public MyTextWatcher (int x,int y){
        i=x;
        j=y;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        actuallyData.get(j).put(sizeList[i],s.toString());
    }
    }

    public void setData(){
        if(planData!=null){
            //给实际的表单加数据
            for(int i=0;i<planData.size();i++){
                HashMap<String,String> map= new HashMap<>();
                actuallyData.add(map);
            }
            //计划任务
            TableRow row = new TableRow(getActivity());
            TextView tv = new TextView(getActivity());
            tv.setText("计划生产任务");
            row.addView(tv);
            planTable.addView(row);
            for(int i=0;i<planData.get(0).size();i++){
                TableRow tableRow = new TableRow(getActivity());
                TextView textView = new TextView(getActivity());
                textView.setGravity(Gravity.CENTER);
                textView.setText(sizeList[i]);
                tableRow.addView(textView);
                for(int j=0;j<planData.size();j++){
                    TextView textView1 = new TextView(getActivity());
                    textView1.setPadding(40,40,0,0);
                    textView.setGravity(Gravity.CENTER);
                    textView1.setText(planData.get(j).get(sizeList[i]));
                    tableRow.addView(textView1);
                }
                planTable.addView(tableRow);
            }
            //实际任务
            TableRow row1 = new TableRow(getActivity());
            TextView tv1 = new TextView(getActivity());
            tv1.setText("实际生产任务");
            row1.addView(tv1);
            planTable.addView(row1);
            for(int i=0;i<planData.get(0).size();i++){
                TableRow tableRow = new TableRow(getActivity());
                TextView textView = new TextView(getActivity());
                textView.setText(sizeList[i]);
                textView.setGravity(Gravity.CENTER);
                tableRow.addView(textView);
                for(int j=0;j<planData.size();j++){
                    if(i==0){
                        TextView textView1 = new TextView(getActivity());
                        textView1.setPadding(40,40,0,0);
                        textView.setGravity(Gravity.CENTER);
                        actuallyData.get(j).put("颜色",planData.get(j).get(sizeList[i]));
                        textView1.setText(planData.get(j).get(sizeList[i]));
                        tableRow.addView(textView1);
                    }else {
                        EditText editText = new EditText(getActivity());
                        editText.setText(planData.get(j).get(sizeList[i]));
                        actuallyData.get(j).put(sizeList[i], planData.get(j).get(sizeList[i]));
                        editText.setEms(3);
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        editText.addTextChangedListener(new MyTextWatcher(i,j));
                        tableRow.addView(editText);
                    }
                }
                planTable.addView(tableRow);
            }
        }
    }

    public void getTaskIdFromServer(String orderId){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",orderId);
        client.get(IPAddress.getIP()+"/fmc/produce/mobile_produceDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    taskId = response.getJSONObject("orderInfo").getString("taskId");
                    ArrayList<Produce> list = Produce.fromJson(response.getJSONObject("orderInfo").getJSONArray("produce"));
                    planData = new ArrayList<HashMap<String, String>>();
                    for(int i=0;i<list.size();i++){
                        HashMap<String,String> map = new HashMap<String, String>();
                        map.put("颜色",list.get(i).getColor());
                        map.put("XS",list.get(i).getXs());
                        map.put("S",list.get(i).getS());
                        map.put("M",list.get(i).getM());
                        map.put("L",list.get(i).getL());
                        map.put("XL",list.get(i).getXl());
                        map.put("XXL",list.get(i).getXxl());
                        map.put("均码",list.get(i).getJ());
                        planData.add(map);
                    }
                    setData();
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

    public void produceSubmit(boolean result){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",info.getOrder().getOrderId());
        params.put("result",result);
        params.put("taskId",taskId);
        if(result){
            transDataToString();
            params.put("produce_color",produce_color);
            params.put("produce_xs",produce_xs);
            params.put("produce_s",produce_s);
            params.put("produce_m",produce_m);
            params.put("produce_l",produce_l);
            params.put("produce_xl",produce_xl);
            params.put("produce_xxl",produce_xxl);
            params.put("produce_j",produce_j);
            params.put("processing_side",processing_side);
        }
        client.post(IPAddress.getIP()+"/fmc/produce/mobile_produceSubmit.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    progressDialog.dismiss();
                    boolean isSuccess= response.getBoolean("isSuccess");
                    if(isSuccess){

                        Toast.makeText(getActivity(),"操作成功\n进入下一步",Toast.LENGTH_SHORT).show();
                        ((DepartmentProductionActivity) getActivity()).goBack();
                    }else {
                        Toast.makeText(getActivity(),"服务器报错",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"网络连接错误",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void transDataToString(){
        for(int i=0;i<actuallyData.size();i++){
            if(i==0){
                produce_color = actuallyData.get(0).get("颜色");
                produce_xs = actuallyData.get(0).get("XS");
                produce_s = actuallyData.get(0).get("S");
                produce_m = actuallyData.get(0).get("M");
                produce_l = actuallyData.get(0).get("L");
                produce_xl = actuallyData.get(0).get("XL");
                produce_xxl = actuallyData.get(0).get("XXL");
                produce_j = actuallyData.get(0).get("均码");
            }else{
                produce_color=produce_color+","+actuallyData.get(i).get("颜色");
                produce_xs=produce_xs+","+actuallyData.get(i).get("XS");
                produce_s=produce_s+","+actuallyData.get(i).get("S");
                produce_m=produce_m+","+actuallyData.get(i).get("M");
                produce_l=produce_l+","+actuallyData.get(i).get("L");
                produce_xl=produce_xl+","+actuallyData.get(i).get("XL");
                produce_xxl=produce_xxl+","+actuallyData.get(i).get("XXL");
                produce_j=produce_j+","+actuallyData.get(i).get("均码");
            }
        }
        processing_side=processing_side_edit_text.getText().toString();
    }
}
