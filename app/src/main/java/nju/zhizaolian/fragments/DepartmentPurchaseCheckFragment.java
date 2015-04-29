package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import nju.zhizaolian.activities.DepartmentPurchaseActivity;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentPurchaseCheckFragment extends Fragment {
    EditText fabric_name_edit_text;
    EditText fabric_weight_edit_text;
    EditText unit_m_cost_edit_text;
    EditText price_per_m_edit_view;
    Button fabric_add_button;
    TableLayout fabric_table;
    EditText[] fabricTextList;
    EditText accessory_name_edit_text;
    EditText accessory_query_edit_text;
    EditText accessory_per_cost_number_edit_text;
    EditText accessory_per_price_edit_text;
    Button accessory_add_button;
    TableLayout accessory_table;
    EditText[] accessoryTextList;
    EditText purchase_check_advice_text;
    ArrayList<HashMap<String,String>> fabric_input_data_list = new ArrayList<>();
    String[] fabric_input_head = new String[]{"面料名称","面料克重","单位米耗","每米价格"};
    ArrayList<HashMap<String,String>> accessory_input_data_list = new ArrayList<>();
    String[] accessory_input_head = new String[]{"辅料名称","辅料要求","单件耗数","辅料单价"};
    String taskId;
    ImageButton production_check_back;
    ImageButton production_check_accept;
    ImageButton production_check_cancel;
    ListInfo info;
    String fabric_name;
    String fabric_amount;
    String tear_per_meter;
    String cost_per_meter;
    String accessory_name;
    String accessory_query;
    String tear_per_pieces;
    String cost_per_pieces;
    final int FABRIC_TABLE_TYPE =0;
    final int ACCESSORY_TABLE_TYPE =1;

    public DepartmentPurchaseCheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_purchase_check_fragment_layout, container, false);
        info = (ListInfo) getArguments().getSerializable("info");
        fabric_name_edit_text = (EditText) view.findViewById(R.id.fabric_name_edit_text);
        fabric_weight_edit_text = (EditText) view.findViewById(R.id.fabric_weight_edit_text);
        unit_m_cost_edit_text = (EditText) view.findViewById(R.id.unit_m_cost_edit_text);
        price_per_m_edit_view = (EditText) view.findViewById(R.id.price_per_m_edit_view);
        purchase_check_advice_text = (EditText) view.findViewById(R.id.purchase_check_advice_text);
        fabricTextList=new EditText[]{fabric_name_edit_text,fabric_weight_edit_text,unit_m_cost_edit_text,price_per_m_edit_view};
        fabric_table =(TableLayout) view.findViewById(R.id.department_purchase_check_fabric_table);
        fabric_add_button = (Button) view.findViewById(R.id.fabric_add_button);
        accessory_name_edit_text=(EditText) view.findViewById(R.id.accessory_name_edit_text);
        accessory_query_edit_text=(EditText) view.findViewById(R.id.accessory_query_edit_text);
        accessory_per_cost_number_edit_text=(EditText) view.findViewById(R.id.accessory_per_cost_number_edit_text);
        accessory_per_price_edit_text=(EditText) view.findViewById(R.id.accessory_per_price_edit_text);
        accessoryTextList=new EditText[]{accessory_name_edit_text,accessory_query_edit_text,accessory_per_cost_number_edit_text,accessory_per_price_edit_text};
        accessory_table=(TableLayout) view.findViewById(R.id.department_purchase_check_accessory_table);
        accessory_add_button=(Button) view.findViewById(R.id.accessory_add_button);
        fabric_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<fabricTextList.length;i++){
                    if(fabricTextList[i].getText().toString()==null||fabricTextList[i].getText().toString().length()<1){
                        fabricTextList[i].setError("填写必要选项");
                        return;
                    }
                }
                HashMap<String,String> map = new HashMap<String, String>();
                TableRow tableRow = new TableRow(getActivity());
                for(int i=0;i<4;i++){
                    TextView textView = new TextView(getActivity());
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(fabricTextList[i].getText().toString());
                    map.put(fabric_input_head[i], fabricTextList[i].getText().toString());
                    tableRow.addView(textView);
                }
                fabric_input_data_list.add(map);
                TextView textView = new TextView(getActivity());
                textView.setText("删除");
                textView.setTextColor(Color.RED);
                textView.setGravity(Gravity.CENTER);
                textView.setClickable(true);
                textView.setOnClickListener(new MyDeleteButtonListener(FABRIC_TABLE_TYPE));
                tableRow.addView(textView);
                fabric_table.addView(tableRow);
                for(int i=0;i<4;i++){
                    fabricTextList[i].setText(null);
                }

            }

        });
        accessory_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<accessoryTextList.length;i++){
                    if(accessoryTextList[i].getText().toString()==null||accessoryTextList[i].getText().toString().length()<1){
                        accessoryTextList[i].setError("填写必要选项");
                        return;
                    }
                }
                HashMap<String,String> map = new HashMap<String, String>();
                TableRow tableRow = new TableRow(getActivity());
                for(int i=0;i<4;i++){
                    TextView textView = new TextView(getActivity());
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(accessoryTextList[i].getText().toString());
                    map.put(accessory_input_head[i], accessoryTextList[i].getText().toString());
                    tableRow.addView(textView);
                }
                accessory_input_data_list.add(map);
                TextView textView = new TextView(getActivity());
                textView.setText("删除");
                textView.setTextColor(Color.RED);
                textView.setGravity(Gravity.CENTER);
                textView.setClickable(true);
                textView.setOnClickListener(new MyDeleteButtonListener(1));
                tableRow.addView(textView);
                accessory_table.addView(tableRow);
                for(int i=0;i<4;i++){
                    accessoryTextList[i].setText(null);
                }
            }
        });
        production_check_back = (ImageButton) view.findViewById(R.id.production_check_back);
        production_check_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentPurchaseActivity) getActivity()).goBack();
            }
        });
        production_check_accept = (ImageButton) view.findViewById(R.id.production_check_accept);
        production_check_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accessory_input_data_list.size()==0||fabric_input_data_list.size()==0){
                    Toast.makeText(getActivity(), "面料和辅料都要添加哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                computePurchaseCostSubmit(true);
            }
        });
        production_check_cancel = (ImageButton) view.findViewById(R.id.production_check_cancel);
        production_check_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(purchase_check_advice_text.getText().toString()==null||purchase_check_advice_text.getText().toString().length()<1){
                    purchase_check_advice_text.setError("拒绝采购要写意见");
                    return;
                }
                new AlertDialog.Builder(getActivity())
                        .setTitle("确定拒绝")
                        .setMessage("订单号："+info.getOrderId())
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                computePurchaseCostSubmit(false);
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
        production_check_accept.setEnabled(false);
        production_check_cancel.setEnabled(false);
        getTaskIdFromServer(info.getOrder().getOrderId());
        return  view;
    }


    class MyDeleteButtonListener implements View.OnClickListener{
        int tableType;

        public MyDeleteButtonListener(int tableType){
            this.tableType=tableType;

        }
        @Override
        public void onClick(View v) {
            if(tableType==FABRIC_TABLE_TYPE){
                int index = fabric_table.indexOfChild((TableRow)v.getParent());
                fabric_table.removeView((TableRow)v.getParent());
                fabric_input_data_list.remove(index-1);
            }else if(tableType==ACCESSORY_TABLE_TYPE){
                int index = accessory_table.indexOfChild((TableRow)v.getParent());
                accessory_table.removeView((TableRow)v.getParent());
                accessory_input_data_list.remove(index-1);
            }
        }
    }

    public void computePurchaseCostSubmit(boolean result){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",info.getOrder().getOrderId());
        params.put("taskId",taskId);
        params.put("result",result);
        params.put("suggestion",purchase_check_advice_text.getText().toString());
        if(result) {
            transListToString(fabric_input_data_list, fabric_input_head, FABRIC_TABLE_TYPE);
            params.put("fabric_name", fabric_name);
            params.put("fabric_amount", fabric_amount);
            params.put("tear_per_meter", tear_per_meter);
            params.put("cost_per_meter", cost_per_meter);
            transListToString(accessory_input_data_list, accessory_input_head, ACCESSORY_TABLE_TYPE);
            params.put("accessory_name", accessory_name);
            params.put("accessory_query", accessory_query);
            params.put("tear_per_piece", tear_per_pieces);
            params.put("cost_per_piece", cost_per_pieces);
        }
        client.post(IPAddress.getIP() + "/fmc/buy/mobile_computePurchaseCostSubmit.do", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    boolean isSuccess = response.getBoolean("isSuccess");
                    if(isSuccess){
                        Toast.makeText(getActivity(), "成功\n订单进入下一步", Toast.LENGTH_SHORT).show();
                        ((DepartmentPurchaseActivity) getActivity()).goBack();
                    }else {
                        Toast.makeText(getActivity(), "服务器显示错误", Toast.LENGTH_SHORT).show();
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

    public void getTaskIdFromServer(String orderId){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",orderId);
        client.get(IPAddress.getIP()+"/fmc/buy/mobile_computePurchaseCostDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    taskId = response.getJSONObject("orderInfo").getString("taskId");
                    production_check_accept.setEnabled(true);
                    production_check_cancel.setEnabled(true);
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


        public void transListToString(ArrayList<HashMap<String,String>> dataList,String[] input_head,int tableType){
            if(tableType==FABRIC_TABLE_TYPE) {
                fabric_name = "";
                fabric_amount = "";
                tear_per_meter = "";
                cost_per_meter = "";
                for (int i = 0; i < dataList.size(); i++) {
                    fabric_name = fabric_name + "," + dataList.get(i).get(input_head[0]);
                    fabric_amount = fabric_amount + "," + dataList.get(i).get(input_head[1]);
                    tear_per_meter = tear_per_meter + "," + dataList.get(i).get(input_head[2]);
                    cost_per_meter = cost_per_meter + "," + dataList.get(i).get(input_head[3]);
                }
            }else if(tableType==ACCESSORY_TABLE_TYPE){
                accessory_name="";
                accessory_query="";
                tear_per_pieces="";
                cost_per_pieces="";
                for (int i = 0; i < dataList.size(); i++) {
                    accessory_name = accessory_name + "," + dataList.get(i).get(input_head[0]);
                    accessory_query = accessory_query + "," + dataList.get(i).get(input_head[1]);
                    tear_per_pieces = tear_per_pieces + "," + dataList.get(i).get(input_head[2]);
                    cost_per_pieces = cost_per_pieces + "," + dataList.get(i).get(input_head[3]);
                }
            }
        }
}
