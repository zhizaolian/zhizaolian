package nju.zhizaolian.fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
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
import nju.zhizaolian.activities.DepartmentSweaterMakerActivity;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.SweaterOperateRecord;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentSweaterMakerCheckFragment extends Fragment {
    RadioGroup mission_choose_radio_group;
    EditText completed_date_edit_text;
    EditText check_leading_name_edit_text;
    EditText remark_info_edit_text;
    ListView operate_history_list;
    ImageButton sweater_maker_check_accept_button;
    ImageButton sweater_maker_check_back_button;
    RadioButton make_small_sample_radio;
    RadioButton technology_make_radio;
    RadioButton version_sample_radio;
    RadioButton sample_confirm_radio;
    String taskId;
    String taskName;
    ListInfo info;
    Account account;
    boolean buySweaterMaterialResult;  //true 要打小样
    ProgressDialog progressDialog;
    ArrayList<HashMap<String,String>> data;
    SimpleAdapter adapter;


    public DepartmentSweaterMakerCheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        progressDialog = ProgressDialog.show(getActivity(),"数据加载中","请稍候",true);
        View view=inflater.inflate(R.layout.department_sweater_maker_check_fragment_layout, container, false);
        info = (ListInfo) getArguments().getSerializable("info");
        account = (Account) getArguments().getSerializable("account");
        mission_choose_radio_group=(RadioGroup) view.findViewById(R.id.mission_choose_radio_group);
        completed_date_edit_text=(EditText) view.findViewById(R.id.completed_date_edit_text);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate=new Date(System.currentTimeMillis());
        String timeString=dateFormat.format(curDate);
        completed_date_edit_text.setText(timeString);
        check_leading_name_edit_text=(EditText) view.findViewById(R.id.check_leading_name_edit_text);
        check_leading_name_edit_text.setText(account.getUserName());
        remark_info_edit_text=(EditText) view.findViewById(R.id.remark_info_edit_text);
        operate_history_list=(ListView) view.findViewById(R.id.operate_history_list);
        data = new ArrayList<>();
        adapter= new SimpleAdapter(getActivity(),data,R.layout.sweater_operate_record_item_layout,
                new String[]{"taskName","operateTime","operatePerson","operateRemark"},
                new int[]{R.id.sweater_operate_name,R.id.sweater_operate_complete_date,R.id.sweater_operate_person_name,R.id.sweater_operate_remark});
        operate_history_list.setAdapter(adapter);
        sweater_maker_check_accept_button=(ImageButton) view.findViewById(R.id.sweater_maker_check_accept_button);
        sweater_maker_check_accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mission_choose_radio_group.getCheckedRadioButtonId()==sample_confirm_radio.getId()){
                    taskName="确认样衣";
                }else if(mission_choose_radio_group.getCheckedRadioButtonId()==version_sample_radio.getId()){
                    taskName="制版打样";
                }else if(mission_choose_radio_group.getCheckedRadioButtonId()==technology_make_radio.getId()){
                    taskName="制作工艺";
                }else if(mission_choose_radio_group.getCheckedRadioButtonId()==make_small_sample_radio.getId()){
                    taskName="打小样";
                }
                new AlertDialog.Builder(getActivity())
                        .setTitle("确认操作")
                        .setMessage("确认对该订单进行\" "+taskName+" \"操作么？")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sweaterSampleAndCraftSubmit();
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
        sweater_maker_check_back_button = (ImageButton) view.findViewById(R.id.sweater_maker_check_back_button);
        sweater_maker_check_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentSweaterMakerActivity) getActivity()).goBack();
            }
        });
        make_small_sample_radio=(RadioButton) view.findViewById(R.id.make_small_sample_radio);
        technology_make_radio=(RadioButton) view.findViewById(R.id.technology_make_radio);
        version_sample_radio=(RadioButton) view.findViewById(R.id.version_sample_radio);
        sample_confirm_radio=(RadioButton) view.findViewById(R.id.sample_confirm_radio);
        getTaskId(info.getOrder().getOrderId());
        return view;
    }


    public void getTaskId(String orderId){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",orderId);
        client.get(IPAddress.getIP()+"/fmc/sweater/mobile_sweaterSampleAndCraftDetail.do",params,new  JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    taskId = response.getJSONObject("orderInfo").getString("taskId");
                    buySweaterMaterialResult =response.getJSONObject("orderInfo").getJSONObject("order").getBoolean("buySweaterMaterialResult");
                    ArrayList<SweaterOperateRecord> records = SweaterOperateRecord.fromJson(response.getJSONObject("orderInfo").getJSONArray("sweaterOperateRecord"));
                    if(records.size()!=0){
                        HashMap<String,String> map = new HashMap<String, String>();
                        map.put("taskName","环节名称");
                        map.put("operateTime","完成时间");
                        map.put("operatePerson","负责人");
                        map.put("operateRemark","消息备注");
                        data.add(map);
                        int decide=0; //判断要进行到哪一步
                        for(int i=0;i<records.size();i++){
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            if(records.get(i).getTaskName().equals("打小样")){
                                decide=decide<1?1:decide;
                            }else if(records.get(i).getTaskName().equals("制作工艺")){
                                decide=decide<2?2:decide;
                            }else if(records.get(i).getTaskName().equals("制版打样")){
                                decide=decide<3?3:decide;
                            }
                            hashMap.put("taskName",records.get(i).getTaskName());
                            hashMap.put("operateTime",records.get(i).getOperateTime());
                            hashMap.put("operatePerson",records.get(i).getOperatePerson());
                            hashMap.put("operateRemark",records.get(i).getOperateRemark());
                            data.add(hashMap);
                        }
                        if(decide>=1){
                            make_small_sample_radio.setEnabled(false);
                            mission_choose_radio_group.check(technology_make_radio.getId());
                        }
                        if(decide>=2){
                        technology_make_radio.setEnabled(false);
                        mission_choose_radio_group.check(version_sample_radio.getId());
                        }
                        if(decide>=3){
                        version_sample_radio.setEnabled(false);
                        mission_choose_radio_group.check(sample_confirm_radio.getId());
                        }
                        if(!buySweaterMaterialResult){
                            make_small_sample_radio.setVisibility(View.GONE);
                        }
                        adapter.notifyDataSetChanged();
                    }else{
                        if(!buySweaterMaterialResult){
                            make_small_sample_radio.setVisibility(View.GONE);
                            mission_choose_radio_group.check(technology_make_radio.getId());
                        }else {
                            mission_choose_radio_group.check(make_small_sample_radio.getId());
                        }
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

    public void sweaterSampleAndCraftSubmit(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",info.getOrder().getOrderId());
        params.put("taskId",taskId);
        params.put("task_finish_date",completed_date_edit_text.getText().toString());
        params.put("person_in_charge",check_leading_name_edit_text.getText().toString());
        params.put("/sweaterremark",remark_info_edit_text.getText().toString());
        if(mission_choose_radio_group.getCheckedRadioButtonId()==sample_confirm_radio.getId()){
            params.put("isFinal",true);
        }else {
            params.put("isFinal",false);
        }
        params.put("task_name",taskName);

        client.post(IPAddress.getIP() + "/fmc/sweater/mobile_sweaterSampleAndCraftSubmit.do", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                 Toast.makeText(getActivity(), taskName+"成功\n进入下一步", Toast.LENGTH_SHORT).show();
                ((DepartmentSweaterMakerActivity) getActivity()).goBack();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
            }
        });




    }
}
