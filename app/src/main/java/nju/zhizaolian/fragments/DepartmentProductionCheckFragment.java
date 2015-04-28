package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentProductionActivity;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentProductionCheckFragment extends Fragment {
    EditText cut_cost_edit_text;
    EditText management_cost_edit_text;
    EditText make_up_cost_edit_text;
    EditText ironing_cost_edit_text;
    EditText lock_cost_edit_text;
    EditText packing_cost_edit_text;
    EditText other_cost_edit_text;
    EditText mass_logistics_cost_edit_text;
    EditText advice_edit_text;
    ImageButton production_accept_button;
    ImageButton production_cancel_button;
    ImageButton production_back_button;
    String taskId;
    ListInfo info;

    public DepartmentProductionCheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.department_production_check_fragment_layout, container, false);
        info =(ListInfo) getArguments().getSerializable("info");
        cut_cost_edit_text= (EditText) view.findViewById(R.id.cut_cost_text);
        management_cost_edit_text = (EditText) view.findViewById(R.id.management_cost_text);
        make_up_cost_edit_text = (EditText) view.findViewById(R.id.make_up_cost_text);
        ironing_cost_edit_text = (EditText) view.findViewById(R.id.ironing_cost_text);
        lock_cost_edit_text = (EditText) view.findViewById(R.id.lock_cost_text);
        packing_cost_edit_text = (EditText) view.findViewById(R.id.packing_cost_text);
        other_cost_edit_text = (EditText) view.findViewById(R.id.other_cost_text);
        mass_logistics_cost_edit_text = (EditText) view.findViewById(R.id.mass_logistics_cost_text);
        advice_edit_text = (EditText) view.findViewById(R.id.purchase_check_advice_text);
        production_accept_button=(ImageButton) view.findViewById(R.id.production_check_accept);
        production_accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cut_cost_edit_text.getText().toString()==null||cut_cost_edit_text.getText().length()<1){
                    cut_cost_edit_text.setError("这里必须填写");
                    return;
                }
                if(management_cost_edit_text.getText().toString()==null||management_cost_edit_text.getText().length()<1){
                    management_cost_edit_text.setError("这里必须填写");
                    return;
                }
                if(make_up_cost_edit_text.getText().toString()==null||make_up_cost_edit_text.getText().length()<1){
                    make_up_cost_edit_text.setError("这里必须填写");
                    return;
                }
                if(ironing_cost_edit_text.getText().toString()==null||ironing_cost_edit_text.getText().length()<1){
                    ironing_cost_edit_text.setError("这里必须填写");
                    return;
                }
                if(lock_cost_edit_text.getText().toString()==null||lock_cost_edit_text.getText().length()<1){
                    lock_cost_edit_text.setError("这里必须填写");
                    return;
                }
                if(packing_cost_edit_text.getText().toString()==null||packing_cost_edit_text.getText().length()<1){
                    packing_cost_edit_text.setError("这里必须填写");
                    return;
                }
                if(other_cost_edit_text.getText().toString()==null||other_cost_edit_text.getText().length()<1){
                    other_cost_edit_text.setError("这里必须填写");
                    return;
                }
                if(mass_logistics_cost_edit_text.getText().toString()==null||mass_logistics_cost_edit_text.getText().length()<1){
                    mass_logistics_cost_edit_text.setError("这里必须填写");
                    return;
                }
                computeProduceCostSubmit(true);
            }
        });
        production_accept_button.setEnabled(false);
        production_cancel_button=(ImageButton) view.findViewById(R.id.production_check_cancel);
        production_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String advice =advice_edit_text.getText().toString();
                if(advice==null||advice.length()<1){
                    Toast.makeText(getActivity(),"拒绝意见必须填写",Toast.LENGTH_SHORT).show();
                }else {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("确认拒绝?")
                            .setMessage("订单号："+info.getOrderId())
                            .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    computeProduceCostSubmit(false);
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("手误",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            }
        });
        production_cancel_button.setEnabled(false);
        production_back_button =(ImageButton) view.findViewById(R.id.production_check_back);
        production_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentProductionActivity) getActivity()).goBack();
            }
        });
        getTaskIdFromServer(info.getOrder().getOrderId());
        return view;
    }

    public void getTaskIdFromServer(String orderId){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",orderId);
        client.get(IPAddress.getIP()+"/fmc/produce/mobile_computeProduceCostDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    taskId = response.getJSONObject("orderInfo").getString("taskId");
                    production_accept_button.setEnabled(true);
                    production_cancel_button.setEnabled(true);
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

    public void computeProduceCostSubmit(boolean result){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",info.getOrder().getOrderId());
        params.put("taskId",taskId);
        params.put("result",result);
        params.put("suggestion",advice_edit_text.getText().toString());
        params.put("cut_cost",cut_cost_edit_text.getText().toString());
        params.put("manage_cost",management_cost_edit_text.getText().toString());
        params.put("nail_cost",make_up_cost_edit_text.getText().toString());
        params.put("ironing_cost",ironing_cost_edit_text.getText().toString());
        params.put("swing_cost",lock_cost_edit_text.getText().toString());
        params.put("package_cost",packing_cost_edit_text.getText().toString());
        params.put("other_cost",other_cost_edit_text.getText().toString());
        params.put("design_cost",mass_logistics_cost_edit_text.getText().toString()); //真的是么？
        client.post(IPAddress.getIP()+"/fmc/produce/mobile_computeProduceCostSubmit.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    boolean isSuccess = response.getBoolean("isSuccess");
                    if(isSuccess){
                        Toast.makeText(getActivity(),"成功\n订单进入下一步",Toast.LENGTH_SHORT).show();
                        ((DepartmentProductionActivity) getActivity()).goBack();
                    }else {
                        Toast.makeText(getActivity(),"服务器显示错误",Toast.LENGTH_SHORT).show();
                    }
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

}
