package nju.zhizaolian.fragments;


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
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentSweaterMakerActivity;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Produce;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentSweaterMakerSentFragment extends Fragment {

    TableLayout sweater_maker_plan_table;
    EditText processing_name_edit_text;
    EditText send_out_time_edit_text;
    EditText sent_leading_name_edit_text;
    ImageButton sweater_maker_sent_accept_button;
    ImageButton sweater_maker_sent_back_button;
    ListInfo info;
    Account account;
    String taskId;
    ArrayList<Produce> produceArrayList;

    public DepartmentSweaterMakerSentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_sweater_maker_sent_fragment_layout, container, false);
        info = (ListInfo) getArguments().getSerializable("info");
        account = (Account) getArguments().getSerializable("account");
        sweater_maker_plan_table=(TableLayout) view.findViewById(R.id.sweater_maker_plan_table);
        processing_name_edit_text=(EditText) view.findViewById(R.id.processing_name_edit_text);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate=new Date(System.currentTimeMillis());
        String timeString=dateFormat.format(curDate);
        send_out_time_edit_text=(EditText) view.findViewById(R.id.send_out_time_edit_text);
        send_out_time_edit_text.setText(timeString);
        sent_leading_name_edit_text=(EditText) view.findViewById(R.id.sent_leading_name_edit_text);
        sent_leading_name_edit_text.setText(account.getUserName());
        sweater_maker_sent_accept_button=(ImageButton) view.findViewById(R.id.sweater_maker_sent_accept_button);
        sweater_maker_sent_accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(processing_name_edit_text.getText().toString().equals("")){
                    Toast.makeText(getActivity(),"请填写加工方",Toast.LENGTH_SHORT).show();
                    return;
                }
                sendSweaterSubmit();
            }
        });
        sweater_maker_sent_back_button = (ImageButton) view.findViewById(R.id.sweater_maker_sent_back_button);
        sweater_maker_sent_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentSweaterMakerActivity) getActivity()).goBack();
            }
        });
        getTaskIdFromServer(info.getOrder().getOrderId());
        return  view;
    }

    public void getTaskIdFromServer(String orderId){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",orderId);
        client.get(IPAddress.getIP()+"/fmc/sweater/mobile_sendSweaterDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    taskId = response.getJSONObject("orderInfo").getString("taskId");
                    produceArrayList =Produce.fromJson(response.getJSONObject("orderInfo").getJSONArray("produce"));
                    updateTable(produceArrayList);
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

    public void  updateTable(ArrayList<Produce> list){
        for(int i=0;i<list.size();i++){
            TableRow row = new TableRow(getActivity());
            TextView view = new TextView(getActivity());
            view.setText(list.get(i).getColor());
            view.setPadding(20,20,0,0);
            view.setGravity(Gravity.CENTER);
            row.addView(view);
            TextView view2 = new TextView(getActivity());
            view2.setText(list.get(i).getXs());
            view2.setPadding(20,20,0,0);
            view2.setGravity(Gravity.CENTER);
            row.addView(view2);
            TextView view3 = new TextView(getActivity());
            view3.setText(list.get(i).getS());
            view3.setPadding(20,20,0,0);
            view3.setGravity(Gravity.CENTER);
            row.addView(view3);
            TextView view4 = new TextView(getActivity());
            view4.setText(list.get(i).getM());
            view4.setPadding(20,20,0,0);
            view4.setGravity(Gravity.CENTER);
            row.addView(view4);
            TextView view5 = new TextView(getActivity());
            view5.setText(list.get(i).getL());
            view5.setPadding(20,20,0,0);
            view5.setGravity(Gravity.CENTER);
            row.addView(view5);
            TextView view6 = new TextView(getActivity());
            view6.setText(list.get(i).getXl());
            view6.setPadding(20,20,0,0);
            view6.setGravity(Gravity.CENTER);
            row.addView(view6);
            TextView view7 = new TextView(getActivity());
            view7.setText(list.get(i).getXxl());
            view7.setPadding(20,20,0,0);
            view7.setGravity(Gravity.CENTER);
            row.addView(view7);
            TextView view8 = new TextView(getActivity());
            view8.setText(list.get(i).getJ());
            view8.setPadding(20,20,0,0);
            view8.setGravity(Gravity.CENTER);
            row.addView(view8);
            sweater_maker_plan_table.addView(row);
        }

    }

    public void sendSweaterSubmit(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",info.getOrder().getOrderId());
        params.put("taskId",taskId);
        params.put("result",true);
        params.put("processing_side",processing_name_edit_text.getText().toString());
        params.put("Purchase_director",sent_leading_name_edit_text.getText().toString());
        params.put("sendTime",send_out_time_edit_text.getText().toString());
        client.post(IPAddress.getIP()+"/fmc/sweater/mobile_sendSweaterSubmit.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getActivity(), "成功\n下一步", Toast.LENGTH_SHORT).show();
                ((DepartmentSweaterMakerActivity) getActivity()).goBack();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
