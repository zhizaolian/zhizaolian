package nju.zhizaolian.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentPurchaseActivity;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentPurchaseSweaterFragment extends Fragment {

    RadioGroup inventory_radio_group;
    RadioButton enoughButton;
    RadioButton notEnoughButton;
    EditText supplier_name_edit_text;
    EditText purchase_person_name_edit_text;
    EditText purchase_date_edit_text;
    EditText sweater_type_edit_text;
    EditText weight_edit_text;
    EditText total_price_edit_text;
    EditText [] editTextGroup;
    ListInfo info;
    Account account;
    ImageButton sweater_purchase_back_button;
    ImageButton sweater_purchase_submit_button;
    boolean isEnough=true;
    String taskId;
    ProgressDialog progressDialog;
    public DepartmentPurchaseSweaterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.department_purchase_sweater_fragment_layout, container, false);
        info =(ListInfo) getArguments().getSerializable("info");
        account = (Account) getArguments().getSerializable("account");
        inventory_radio_group = (RadioGroup) view.findViewById(R.id.inventory_radio_group);
        enoughButton = (RadioButton) view.findViewById(R.id.inventory_enough_radio_button);
        notEnoughButton =(RadioButton) view.findViewById(R.id.inventory_not_enough_radio_button);
        supplier_name_edit_text =(EditText) view.findViewById(R.id.supplier_name_edit_text);
        purchase_person_name_edit_text =(EditText) view.findViewById(R.id.purchase_person_name_edit_text);
        purchase_person_name_edit_text.setText(account.getUserName());
        purchase_date_edit_text =(EditText) view.findViewById(R.id.purchase_date_edit_text);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate=new Date(System.currentTimeMillis());
        String timeString=dateFormat.format(curDate);
        purchase_date_edit_text.setText(timeString);
        sweater_type_edit_text =(EditText) view.findViewById(R.id.sweater_type_edit_text);
        weight_edit_text =(EditText) view.findViewById(R.id.weight_edit_text);
        total_price_edit_text =(EditText) view.findViewById(R.id.total_price_edit_text);
        editTextGroup= new EditText[]{supplier_name_edit_text,sweater_type_edit_text,weight_edit_text,total_price_edit_text};
        sweater_purchase_back_button = (ImageButton) view.findViewById(R.id.sweater_purchase_back_button);
        sweater_purchase_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentPurchaseActivity) getActivity()).goBack();
            }
        });
        sweater_purchase_submit_button = (ImageButton) view.findViewById(R.id.sweater_purchase_submit_button);
        sweater_purchase_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEnough) {
                    for (int i = 0; i < editTextGroup.length; i++) {
                        if (editTextGroup[i].getText().toString() == null || editTextGroup[i].getText().toString().length() < 1) {
                            Toast.makeText(getActivity(), "信息要填写完整", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                progressDialog = ProgressDialog.show(getActivity(),"请稍等","正在处理",true);
                purchaseSweaterMaterialSubmit();
            }
        });
        sweater_purchase_submit_button.setEnabled(false);
        inventory_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(enoughButton.getId()==checkedId){
                    setEditGroupEnableState(false);
                    isEnough=true;
                }else if(notEnoughButton.getId()==checkedId){
                    setEditGroupEnableState(true);
                    isEnough=false;
                }else {

                }
            }
        });
        setEditGroupEnableState(false);
        getTaskIdFromServer(info.getOrder().getOrderId());
        return view;
    }


    public void setEditGroupEnableState(boolean state){
        for(int i=0;i<editTextGroup.length;i++){
            editTextGroup[i].setEnabled(state);
        }
    }

    public void getTaskIdFromServer(String orderId){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",orderId);
        client.get(IPAddress.getIP()+"/fmc/buy/mobile_purchaseSweaterMaterialDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    taskId = response.getJSONObject("orderInfo").getString("taskId");
                    sweater_purchase_submit_button.setEnabled(true);
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

    public void purchaseSweaterMaterialSubmit(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",info.getOrder().getOrderId());
        params.put("taskId",taskId);
        if(isEnough) {
            params.put("task_name", "有库存");
            params.put("supplier", "");
            params.put("Wool_type", "");
            params.put("Wool_weight", "");
            params.put("total_price", "");
        }else{
            params.put("task_name", "无库存");
            params.put("supplier", supplier_name_edit_text.getText().toString());
            params.put("Wool_type", sweater_type_edit_text.getText().toString());
            params.put("Wool_weight", weight_edit_text.getText().toString());
            params.put("total_price", total_price_edit_text.getText().toString());
        }
        params.put("Purchase_director",purchase_person_name_edit_text.getText().toString());
        params.put("Purchase_time",purchase_date_edit_text.getText().toString());
        client.post(IPAddress.getIP() + "/fmc/buy/mobile_purchaseSweaterMaterialSubmit.do", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    boolean isSuccess = response.getBoolean("isSuccess");
                    if (isSuccess) {
                        Toast.makeText(getActivity(), "操作成功\n进入下一步", Toast.LENGTH_SHORT).show();
                        ((DepartmentPurchaseActivity) getActivity()).goBack();
                    } else {
                        Toast.makeText(getActivity(), "服务器错误", Toast.LENGTH_SHORT).show();
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
}
