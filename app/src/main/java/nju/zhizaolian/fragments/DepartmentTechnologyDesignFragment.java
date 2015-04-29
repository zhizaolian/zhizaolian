package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.TextAttribute;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentTechnologyActivity;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentTechnologyDesignFragment extends Fragment {
    EditText check_ironing_flower_cost_edit_text;
    EditText check_washing_cost_edit_text;
    EditText check_laser_cost_text;
    EditText check_embroidery_cost_edit_text;
    EditText check_crushed_cost_edit_text;
    EditText check_plate_charge_cost_edit_text;
    TextView check_customer_technology_request_text;
    RadioGroup technology_radio_group;
    EditText[] editTexts;
    EditText purchase_check_advice_text;
    String taskId;
    ListInfo info;
    ImageButton technology_back_button;
    ImageButton technology_submit_button;
    ImageButton technology_cancel_button;
    int needCraft=1;

    public DepartmentTechnologyDesignFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_technology_design_fragment_layout, container, false);
        info = (ListInfo) getArguments().getSerializable("info");
        check_ironing_flower_cost_edit_text=(EditText) view.findViewById(R.id.check_ironing_flower_cost_edit_text);
        check_washing_cost_edit_text=(EditText) view.findViewById(R.id.check_washing_cost_edit_text);
        check_laser_cost_text=(EditText) view.findViewById(R.id.check_laser_cost_text);
        check_embroidery_cost_edit_text=(EditText) view.findViewById(R.id.check_embroidery_cost_edit_text);
        check_crushed_cost_edit_text=(EditText) view.findViewById(R.id.check_crushed_cost_edit_text);
        check_plate_charge_cost_edit_text=(EditText) view.findViewById(R.id.check_plate_charge_cost_edit_text);
        purchase_check_advice_text =(EditText) view.findViewById(R.id.purchase_check_advice_text);
        check_customer_technology_request_text = (TextView) view.findViewById(R.id.check_customer_technology_request_text);
        editTexts = new EditText[]{
                check_ironing_flower_cost_edit_text,
                check_washing_cost_edit_text,
                check_laser_cost_text,
                check_embroidery_cost_edit_text,
                check_crushed_cost_edit_text,
                check_plate_charge_cost_edit_text};
        technology_radio_group=(RadioGroup) view.findViewById(R.id.technology_radio_group);
        technology_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.technology_needed_radio_button){
                    setEditGroupState(true);
                    needCraft=1;
                }else if(checkedId==R.id.technology_not_needed_radio_button){
                    setEditGroupState(false);
                    needCraft=0;
                }else {

                }
            }
        });
        technology_back_button = (ImageButton)view.findViewById(R.id.technology_back_button);
        technology_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentTechnologyActivity) getActivity()).goBack();
            }
        });
        technology_submit_button = (ImageButton)view.findViewById(R.id.technology_submit_button);
        technology_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(needCraft==1){
                    for(int i=0;i<editTexts.length;i++){
                        if(editTexts[i].getText().toString()==null||editTexts[i].getText().toString().length()<1){
                            editTexts[i].setError("填写这里");
                            return;
                        }
                    }
                }
                computeDesignCostSubmit(true);
            }
        });
        technology_cancel_button = (ImageButton)view.findViewById(R.id.technology_cancel_button);
        technology_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String advice =purchase_check_advice_text.getText().toString();
                if(advice==null||advice.length()<1){
                    purchase_check_advice_text.setError("建议必须填写");
                    return;
                }
                new AlertDialog.Builder(getActivity())
                        .setTitle("确认拒绝？")
                        .setMessage("订单号："+info.getOrderId())
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                computeDesignCostSubmit(false);
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("取消",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();

            }
        });
        technology_submit_button.setEnabled(false);
        technology_cancel_button.setEnabled(false);
        getTaskIdFromServer(info.getOrder().getOrderId());
        return  view;
    }

    public void setEditGroupState(boolean state){
        for(int i=0;i<editTexts.length;i++){
            editTexts[i].setEnabled(state);
        }
    }

    public void getTaskIdFromServer(String orderId){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",orderId);
        client.get(IPAddress.getIP()+"/fmc/design/mobile_computeDesignCostDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                  taskId = response.getJSONObject("orderInfo").getString("taskId");
                  technology_submit_button.setEnabled(true);
                  technology_cancel_button.setEnabled(true);
                  check_customer_technology_request_text.setText(response.getJSONObject("orderInfo").getString("designCadTech"));
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

    public void computeDesignCostSubmit(boolean result){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",info.getOrder().getOrderId());
        params.put("taskId",taskId);
        params.put("result",result);
        params.put("needcraft",needCraft);
        params.put("suggestion",purchase_check_advice_text.getText().toString());
        params.put("stampDutyMoney",check_ironing_flower_cost_edit_text.getText().toString());
        params.put("washHangDyeMoney",check_washing_cost_edit_text.getText().toString());
        params.put("laserMoney",check_laser_cost_text.getText().toString());
        params.put("embroideryMoney",check_embroidery_cost_edit_text.getText().toString());
        params.put("crumpleMoney",check_crushed_cost_edit_text.getText().toString());
        params.put("openVersionMoney",check_plate_charge_cost_edit_text.getText().toString());
        client.post(IPAddress.getIP()+"/fmc/design/mobile_computeDesignCostSubmit.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getActivity(), "成功\n进入下一步", Toast.LENGTH_SHORT).show();
                ((DepartmentTechnologyActivity) getActivity()).goBack();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
