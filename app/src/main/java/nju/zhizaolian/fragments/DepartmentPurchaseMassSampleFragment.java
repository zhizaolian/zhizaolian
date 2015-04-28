package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentPurchaseActivity;
import nju.zhizaolian.models.AccessoryCost;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.FabricCost;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentPurchaseMassSampleFragment extends Fragment {

    TableLayout fabric_purchase_table;
    TableLayout accessory_purchase_table;
    EditText purchase_person_name_edit_text;
    EditText purchase_date_edit_text;
    EditText supplier_name_edit_text;
    ImageButton purchase_accept_button;
    ImageButton purchase_cancel_button;
    ImageButton purchase_back_button;
    String fabricTableHead[] = new String[]{"面料名称","单件米耗","件数","总采购米数"};
    String accessoryTableHead[] = new String[]{"辅料名称","单件耗数","件数","总采购个数"};
    ListInfo listInfo;
    String view_type;
    String taskId;
    final String MASS_VIEW = "MASS";
    final String SAMPLE_VIEW ="SAMPLE";
    final int FABRIC_TABLE =1;
    final int ACCESSORY_TABLE =2;
    ArrayList<FabricCost> fabricCostList;
    ArrayList<AccessoryCost> accessoryCostList;
    Account account;
    String processId;



    public DepartmentPurchaseMassSampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.department_purchase_mass_sample_fragment_layout,container,false);
       account =(Account) getArguments().getSerializable("account");
       fabric_purchase_table = (TableLayout) view.findViewById(R.id.fabric_purchase_table);
       accessory_purchase_table=(TableLayout) view.findViewById(R.id.accessory_purchase_table);
       purchase_person_name_edit_text=(EditText) view.findViewById(R.id.purchase_person_name_edit_text);
       purchase_person_name_edit_text.setText(account.getNickName());
       purchase_date_edit_text=(EditText) view.findViewById(R.id.purchase_date_edit_text);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate=new Date(System.currentTimeMillis());
        String timeString=dateFormat.format(curDate);
        purchase_date_edit_text.setText(timeString);
        supplier_name_edit_text=(EditText) view.findViewById(R.id.supplier_name_edit_text);
        purchase_accept_button =(ImageButton) view.findViewById(R.id.purchase_accept_button);
        purchase_accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String purchase_name = purchase_person_name_edit_text.getText().toString();
                String supplier_name = supplier_name_edit_text.getText().toString();
                if(purchase_name==null||purchase_name.length()<1){
                    purchase_person_name_edit_text.setError("填写这里");
                    return;
                }
                if(supplier_name==null||supplier_name.length()<1){
                    supplier_name_edit_text.setError("填写这里");
                    return;
                }
                String url=null;
                if(view_type.equals(MASS_VIEW)){
                    url="/fmc/buy/mobile_purchaseMaterialSubmit.do";
                }else if (view_type.equals(SAMPLE_VIEW)){
                    url="/fmc/buy/mobile_purchaseSampleMaterialSubmit.do";
                }
                submitMyTaskToServer(url,"1");

            }
        });
       purchase_cancel_button =(ImageButton) view.findViewById(R.id.purchase_cancel_button);
        purchase_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("确定终止？")
                        .setMessage("订单号："+listInfo.getOrderId())
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String url=null;
                                if(view_type.equals(MASS_VIEW)){
                                    url="/fmc/buy/mobile_purchaseMaterialSubmit.do";
                                }else if (view_type.equals(SAMPLE_VIEW)){
                                    url="/fmc/buy/mobile_purchaseSampleMaterialSubmit.do";
                                }
                                submitMyTaskToServer(url,"0");
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("不了",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
       purchase_back_button = (ImageButton) view.findViewById(R.id.purchase_back_button);
       purchase_back_button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ((DepartmentPurchaseActivity) getActivity()).goBack();
           }
       });
       listInfo = (ListInfo) getArguments().getSerializable("info");
       view_type =(String) getArguments().getSerializable("TYPE");
       String url=null;
       if(view_type.equals(MASS_VIEW)){
           url="/fmc/buy/mobile_purchaseMaterialDetail.do";
       }else if (view_type.equals(SAMPLE_VIEW)){
           url="/fmc/buy/mobile_purchaseSampleMaterialDetail.do";
       }
       getTaskIdFromServer(listInfo.getOrder().getOrderId(),url);
       return view;
    }

    public void generateTable(TableLayout table,ArrayList<HashMap<String,String>> data,int table_type){
        for(int i=0;i<data.size();i++) {
            TableRow row= new TableRow(getActivity());
            for (int j = 0; j < 4; j++) {
                TextView textView = new TextView(getActivity());
                if(table_type==FABRIC_TABLE) {
                    textView.setText(data.get(i).get(fabricTableHead[j]));
                }else {
                    textView.setText(data.get(i).get(accessoryTableHead[j]));
                }
                textView.setGravity(Gravity.CENTER);
                row.addView(textView);
            }
            table.addView(row);
        }
    }

    public void getTaskIdFromServer(String orderId,String url){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",orderId);
        client.get(IPAddress.getIP()+url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    taskId = response.getJSONObject("orderInfo").getString("taskId");
                    processId=response.getJSONObject("orderInfo").getString("processInstanceId");
                    fabricCostList = FabricCost.fromJson(response.getJSONObject("orderInfo").getJSONArray("fabricCosts"));
                    String number = response.getJSONObject("orderInfo").getJSONObject("order").getString("askAmount");
                    ArrayList<HashMap<String,String>> fabricMapList = new ArrayList<HashMap<String, String>>();
                    for(int i=0;i<fabricCostList.size();i++){
                        HashMap<String,String> fabricMap = new HashMap<String, String>();
                        fabricMap.put("面料名称",fabricCostList.get(i).getFabricName());
                        fabricMap.put("单件米耗",""+fabricCostList.get(i).getTearPerMeter());
                        fabricMap.put("件数",number);
                        fabricMap.put("总采购米数",""+fabricCostList.get(i).getTearPerMeterAskAmountProduct());
                        fabricMapList.add(fabricMap);
                    }
                    generateTable(fabric_purchase_table, fabricMapList,FABRIC_TABLE);
                    accessoryCostList =AccessoryCost.fromJson(response.getJSONObject("orderInfo").getJSONArray("accessoryCosts"));
                    ArrayList<HashMap<String,String>> accessoryMapList = new ArrayList<HashMap<String, String>>();
                    for(int i=0;i<accessoryCostList.size();i++){
                        HashMap<String,String> accessoryMap = new HashMap<String, String>();
                        accessoryMap.put("辅料名称",accessoryCostList.get(i).getAccessoryName());
                        accessoryMap.put("单件耗数",accessoryCostList.get(i).getTearPerPiece()+"");
                        accessoryMap.put("件数",number);
                        accessoryMap.put("总采购个数",""+accessoryCostList.get(i).getTearPerPieceAskAmountProduct());
                        accessoryMapList.add(accessoryMap);
                    }
                    generateTable(accessory_purchase_table,accessoryMapList,ACCESSORY_TABLE);
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


    public void submitMyTaskToServer(String url,String result){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",listInfo.getOrder().getOrderId());
        params.put("taskId",taskId);
        params.put("result",result);
        if(view_type==SAMPLE_VIEW){
            params.put("samplepurName",purchase_person_name_edit_text.getText().toString());
            params.put("samplepurDate",purchase_date_edit_text.getText().toString());
            params.put("samplesupplierName",supplier_name_edit_text.getText().toString());
            params.put("processId",processId);
        }else if(view_type==MASS_VIEW){
            params.put("masspurName",purchase_person_name_edit_text.getText().toString());
            params.put("masspurDate",purchase_date_edit_text.getText().toString());
            params.put("masssupplierName",supplier_name_edit_text.getText().toString());
        }
        client.get(IPAddress.getIP()+url,params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    boolean isSuccess =response.getBoolean("isSuccess");
                    if(isSuccess){
                        Toast.makeText(getActivity(), "操作成功\n进入下一步", Toast.LENGTH_SHORT).show();
                        ((DepartmentPurchaseActivity) getActivity()).goBack();
                    }else {
                        Toast.makeText(getActivity(), "服务器表示操作", Toast.LENGTH_SHORT).show();
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

}
