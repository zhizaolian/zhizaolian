package nju.zhizaolian.fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentQualityActivity;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Produce;
import nju.zhizaolian.models.RepairRecord;

/**
 *
 */
public class CheckQualityFragment extends Fragment {

    ListInfo info ;
    Account account;
    String taskId;
    ArrayList<Produce> produceArrayList;
    ArrayList<RepairRecord> repairRecordArrayList;
    String sizeList[] =new String[]{"颜色","XS","S","M","L","XL","XXL","均码"};
    String recordHeadList[] = new String[]{"日期","加工方","回修数量","合格实收数量","报废数量"};
    ArrayList<HashMap<String,String>> planData;
    ArrayList<HashMap<String,String>> actuallyData = new ArrayList<>();
    TableLayout planTable;
    TableLayout quality_check_record_table;
    ImageButton quality_check_back_button;
    ImageButton quality_check_submit_button;
    String processing_slide_name;
    String result;
    ProgressDialog progressDialog;
    EditText repair_side_name_edit_text;
    EditText repair_number_edit_text;
    EditText repair_date_edit_text;
    EditText invalid_number_edit_text;
    int totalNumber=0;
    int totalDoneNumber=0;
    String isFinal="false"; //别问我为什么用String而不用boolean，服务器逼的！
    String produce_color;
    String produce_xs;
    String produce_s;
    String produce_m;
    String produce_l;
    String produce_xl;
    String produce_xxl;
    String produce_j;

    public CheckQualityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_check_quality, container, false);
        planTable = (TableLayout) view.findViewById(R.id.quality_check_plan_production_table);
        quality_check_record_table = (TableLayout) view.findViewById(R.id.quality_check_record_table);
        info = (ListInfo) getArguments().getSerializable("info");
        account =(Account) getArguments().getSerializable("account");
        repair_side_name_edit_text=(EditText) view.findViewById(R.id.repair_side_name_edit_text);
        repair_number_edit_text=(EditText) view.findViewById(R.id.repair_number_edit_text);
        repair_number_edit_text.setText("0");
        repair_date_edit_text=(EditText) view.findViewById(R.id.repair_date_edit_text);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date curDate=new Date(System.currentTimeMillis());
        String timeString=dateFormat.format(curDate);
        repair_date_edit_text.setText(timeString);
        invalid_number_edit_text=(EditText) view.findViewById(R.id.invalid_number_edit_text);
        invalid_number_edit_text.setText("0");
        quality_check_back_button=(ImageButton) view.findViewById(R.id.quality_check_back_button);
        quality_check_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentQualityActivity) getActivity()).goBack();
            }
        });
        quality_check_submit_button=(ImageButton) view.findViewById(R.id.quality_check_submit_button);
        quality_check_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog= ProgressDialog.show(getActivity(),"请等待","提交中",true);
                if(isFinal.equals("false")){
                    if(repair_side_name_edit_text.getText().toString().length()<1){
                        Toast.makeText(getActivity(), "加工方必须填写", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        return;
                    }
                    int totalDoingNumber=0;
                    for(int i=0;i<actuallyData.size();i++){
                        for(int j=0;j<sizeList.length;j++){
                            if(actuallyData.get(i).get(sizeList[j]).equals("")){
                                Toast.makeText(getActivity(),"所有表单都要填写",Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                return;
                            }else {
                               if(j!=0)
                               totalDoingNumber=totalDoingNumber+Integer.parseInt(actuallyData.get(i).get(sizeList[j]));
                            }
                        }
                    }
                    int invalid_number = invalid_number_edit_text.getText().toString().equals("")?0:Integer.parseInt(invalid_number_edit_text.getText().toString());
                    int repair_number = repair_number_edit_text.getText().toString().equals("")?0:Integer.parseInt(repair_number_edit_text.getText().toString());
                    totalDoingNumber = totalDoingNumber+ invalid_number+repair_number;
                    if(totalDoingNumber>totalNumber-totalDoneNumber){
                        Toast.makeText(getActivity(),"提交货物数大于待提交数，请核对",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        return;
                    }
                    checkQualitySubmit();
                }else{
                    checkQualitySubmit();
                }
            }
        });
        getTaskIdFromServer(info.getOrder().getOrderId());
        return view;
    }

    private  class MyTextWatcher implements TextWatcher {

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
            if(!s.toString().equals("")) {
                String planNumber =planData.get(j).get(sizeList[i]);
                int a = Integer.parseInt(planNumber);
                int b = Integer.parseInt(s.toString());
                if (b > a) {
                    Toast.makeText(getActivity(), "实收数量不能大于生产数量", Toast.LENGTH_SHORT).show();
                    s.clear();
                    s.insert(0,planNumber);
                    actuallyData.get(j).put(sizeList[i], planNumber);
                } else {
                    actuallyData.get(j).put(sizeList[i], s.toString());
                }
            }else{
                actuallyData.get(j).put(sizeList[i], s.toString());
            }
        }
    }

    public void setProductData(){
        if(planData!=null&&planData.size()!=0) {
            //给实际的表单加数据
            for (int i = 0; i < planData.size(); i++) {
                HashMap<String, String> map = new HashMap<>();
                actuallyData.add(map);
            }
            //计划任务
            TableRow row = new TableRow(getActivity());
            TextView tv = new TextView(getActivity());
            tv.setText("应收数量");
            tv.setTextColor(Color.BLUE);
            tv.setPadding(0, 20, 0, 0);
            row.addView(tv);
            planTable.addView(row);
            for (int i = 0; i < planData.get(0).size(); i++) {
                TableRow tableRow = new TableRow(getActivity());
                TextView textView = new TextView(getActivity());
                textView.setGravity(Gravity.CENTER);
                textView.setText(sizeList[i]);
                tableRow.addView(textView);
                for (int j = 0; j < planData.size(); j++) {
                    TextView textView1 = new TextView(getActivity());
                    textView1.setPadding(40, 40, 0, 0);
                    textView.setGravity(Gravity.CENTER);
                    textView1.setText(planData.get(j).get(sizeList[i]));
                    tableRow.addView(textView1);
                }
                planTable.addView(tableRow);
            }
            //加工方
            TableRow row3 = new TableRow(getActivity());
            TextView tv3 = new TextView(getActivity());
            tv3.setText("加工方");
            tv3.setTextColor(Color.BLUE);
            tv3.setPadding(0, 20, 0, 0);
            row3.addView(tv3);
            TextView tv4 = new TextView(getActivity());
            tv4.setText(processing_slide_name);
            tv4.setTextColor(Color.BLUE);
            tv4.setPadding(0, 20, 0, 0);
            row3.addView(tv4);
            planTable.addView(row3);

            if(result.equals("0")){
            //实际任务 完成的话就不显示了
            TableRow row1 = new TableRow(getActivity());
            TextView tv1 = new TextView(getActivity());
            tv1.setText("实收数量");
            tv1.setTextColor(Color.GREEN);
            tv1.setPadding(0, 20, 0, 0);
            row1.addView(tv1);
            planTable.addView(row1);
            for (int i = 0; i < planData.get(0).size(); i++) {
                TableRow tableRow = new TableRow(getActivity());
                TextView textView = new TextView(getActivity());
                textView.setText(sizeList[i]);
                textView.setGravity(Gravity.CENTER);
                tableRow.addView(textView);
                for (int j = 0; j < planData.size(); j++) {
                    if (i == 0) {
                        TextView textView1 = new TextView(getActivity());
                        textView1.setPadding(40, 40, 0, 0);
                        textView.setGravity(Gravity.CENTER);
                        actuallyData.get(j).put("颜色", planData.get(j).get(sizeList[i]));
                        textView1.setText(planData.get(j).get(sizeList[i]));
                        tableRow.addView(textView1);
                    } else {
                        EditText editText = new EditText(getActivity());
                        editText.setText(planData.get(j).get(sizeList[i]));
                        actuallyData.get(j).put(sizeList[i], planData.get(j).get(sizeList[i]));
                        if (Integer.parseInt(planData.get(j).get(sizeList[i])) == 0) {
                            editText.setEnabled(false);
                        }else{
                            totalNumber=totalNumber+Integer.parseInt(planData.get(j).get(sizeList[i]));
                        }
                        editText.setEms(3);
                        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                        editText.addTextChangedListener(new MyTextWatcher(i, j));
                        tableRow.addView(editText);
                    }
                }
                planTable.addView(tableRow);
            }
        }else{
                TableRow row1 = new TableRow(getActivity());
                TextView tv1 = new TextView(getActivity());
                tv1.setText("实收数量\n已完成\n无需编辑");
                tv1.setTextColor(Color.GREEN);
                tv1.setPadding(0, 20, 0, 0);
                row1.addView(tv1);
                planTable.addView(row1);
            }
        }
    }

    public void setRecordData(){
        if(repairRecordArrayList!=null&&repairRecordArrayList.size()!=0){
            TableRow row = new TableRow(getActivity());
            TextView tv = new TextView(getActivity());
            tv.setText("操作记录");
            tv.setTextColor(Color.RED);
            row.addView(tv);
            quality_check_record_table.addView(row);
            TableRow row2 = new TableRow(getActivity());
            for(int i=0;i<recordHeadList.length;i++){
                TextView tv1 = new TextView(getActivity());
                tv1.setText(recordHeadList[i]);
                tv1.setGravity(Gravity.CENTER);
                tv1.setPadding(40,20, 0, 0);
                row2.addView(tv1);
            }
            quality_check_record_table.addView(row2);
            for(int i=0;i<repairRecordArrayList.size();i++){
                TableRow tableRow5 = new TableRow(getActivity());
                TextView textView1 = new TextView(getActivity());
                textView1.setText(repairRecordArrayList.get(i).getRepairTime());
                textView1.setGravity(Gravity.CENTER);
                textView1.setPadding(40,20,0,0);
                tableRow5.addView(textView1);
                TextView textView2 = new TextView(getActivity());
                textView2.setText(repairRecordArrayList.get(i).getRepairSide());
                textView2.setGravity(Gravity.CENTER);
                textView2.setPadding(40,20,0,0);
                tableRow5.addView(textView2);
                TextView textView3 = new TextView(getActivity());
                textView3.setText(repairRecordArrayList.get(i).getRepairAmount());
                textView3.setGravity(Gravity.CENTER);
                textView3.setPadding(40,20,0,0);
                tableRow5.addView(textView3);
                TextView textView4 = new TextView(getActivity());
                textView4.setText(repairRecordArrayList.get(i).getQualifiedAmount());
                totalDoneNumber =totalDoneNumber+Integer.parseInt(repairRecordArrayList.get(i).getQualifiedAmount());
                textView4.setGravity(Gravity.CENTER);
                textView4.setPadding(40,20,0,0);
                tableRow5.addView(textView4);
                TextView textView5 = new TextView(getActivity());
                textView5.setText(repairRecordArrayList.get(i).getInvalidAmount());
                totalDoneNumber =totalDoneNumber+Integer.parseInt(repairRecordArrayList.get(i).getInvalidAmount());
                textView5.setGravity(Gravity.CENTER);
                textView5.setPadding(40,20,0,0);
                tableRow5.addView(textView5);
                quality_check_record_table.addView(tableRow5);
            }
        }
    }


    public void getTaskIdFromServer(String orderId){
            progressDialog= ProgressDialog.show(getActivity(),"请等待","生成表单中",true);
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
            String jSessionId=settings.getString("jsessionId","");
            params.put("jsessionId",jSessionId);
            params.put("orderId",orderId);
            client.get(IPAddress.getIP()+"/fmc/quality/mobile_checkQualityDetail.do",params,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        taskId = response.getJSONObject("orderInfo").getString("taskId");
//                      result =response.getJSONObject("orderInfo").getString("result"); //服务器返回的result有误改为自己判断
                        processing_slide_name=response.getJSONObject("orderInfo").getJSONObject("order").getString("payAccountInfo");
                        repairRecordArrayList=RepairRecord.fromJson(response.getJSONObject("orderInfo").getJSONArray("repairRecord"));
                        setRecordData();
                        produceArrayList = Produce.fromJson(response.getJSONObject("orderInfo").getJSONArray("produced"));
                        int myTotalNumber =getTotalNumber(produceArrayList);
                        if(myTotalNumber>totalDoneNumber){
                            result = "0";
                        }else{
                            result = "1";
                        }
                        planData = new ArrayList<HashMap<String, String>>();
                        for(int i=0;i<produceArrayList.size();i++){
                            HashMap<String,String> map = new HashMap<String, String>();
                            map.put("颜色",produceArrayList.get(i).getColor());
                            map.put("XS",produceArrayList.get(i).getXs());
                            map.put("S",produceArrayList.get(i).getS());
                            map.put("M",produceArrayList.get(i).getM());
                            map.put("L",produceArrayList.get(i).getL());
                            map.put("XL",produceArrayList.get(i).getXl());
                            map.put("XXL",produceArrayList.get(i).getXxl());
                            map.put("均码",produceArrayList.get(i).getJ());
                            planData.add(map);
                        }
                        setProductData();
                        if(result.equals("1")){
                            isFinal="true";
                            repair_side_name_edit_text.setEnabled(false);
                            repair_number_edit_text.setEnabled(false);
                            repair_date_edit_text.setEnabled(false);
                            invalid_number_edit_text.setEnabled(false);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish() {
                    progressDialog.dismiss();
                    super.onFinish();
                }
            });

    }

    public  void checkQualitySubmit(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",info.getOrder().getOrderId());
        params.put("taskId",taskId);
        params.put("isFinal",isFinal);
        params.put("repair_number",repair_number_edit_text.getText().toString());
        params.put("repair_time",repair_date_edit_text.getText().toString());
        params.put("repair_side",repair_side_name_edit_text.getText().toString());
        params.put("invalid_number",invalid_number_edit_text.getText().toString());
        if(isFinal.equals("true")){
            params.put("good_color","0");
            params.put("good_xs","0");
            params.put("good_s","0");
            params.put("good_m","0");
            params.put("good_l","0");
            params.put("good_xl","0");
            params.put("good_xxl","0");
            params.put("good_j","0");
        }else{
            transDataToString();
            params.put("good_color",produce_color);
            params.put("good_xs",produce_xs);
            params.put("good_s",produce_s);
            params.put("good_m",produce_m);
            params.put("good_l",produce_l);
            params.put("good_xl",produce_xl);
            params.put("good_xxl",produce_xxl);
            params.put("good_j",produce_j);
        }
        client.post(IPAddress.getIP() + "/fmc/quality/mobile_checkQualitySubmit.do", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if(response.has("notify")) {
                    try {
                        String notify = response.getString("notify");
                        Toast.makeText(getActivity(), notify, Toast.LENGTH_LONG).show();
                        ((DepartmentQualityActivity) getActivity()).goBack();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getActivity(),"操作成功\n进入下一步", Toast.LENGTH_LONG).show();
                    ((DepartmentQualityActivity) getActivity()).goBack();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
                super.onFinish();
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
    }

    public int getTotalNumber(ArrayList<Produce> list){
        int number=0;
        for(int i=0;i<list.size();i++){
            number=number+Integer.parseInt(list.get(i).getXs());
            number=number+Integer.parseInt(list.get(i).getS());
            number=number+Integer.parseInt(list.get(i).getM());
            number=number+Integer.parseInt(list.get(i).getL());
            number=number+Integer.parseInt(list.get(i).getXl());
            number=number+Integer.parseInt(list.get(i).getXxl());
            number=number+Integer.parseInt(list.get(i).getJ());
        }
        return  number;
    }

}
