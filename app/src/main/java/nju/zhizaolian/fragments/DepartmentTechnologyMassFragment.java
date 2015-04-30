package nju.zhizaolian.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentTechnologyActivity;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Craft;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentTechnologyMassFragment extends Fragment {

    TextView customer_technology_request_text;
    TextView ironing_flower_cost_text;
    TextView washing_cost_text;
    TextView laser_cost_text;
    TextView embroidery_cost_text;
    TextView crushed_cost_text;
    TextView plate_charge_cost_text;
    EditText technology_leading_name_edit_text;
    EditText technology_mass_complete_date_edit_text;
    ImageButton technology_mass_accept_button;
    ImageButton sample_technology_mass_back_button;
    ImageView technology_sample_image_view;
    ListInfo listInfo;
    Account account;
    String taskId;
    ProgressDialog progressDialog;


    public DepartmentTechnologyMassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_technology_mass_fragment_layout, container, false);
        listInfo =(ListInfo) getArguments().getSerializable("info");
        account =(Account) getArguments().getSerializable("account");
        customer_technology_request_text=(TextView) view.findViewById(R.id.mass_check_customer_technology_request_text);
        ironing_flower_cost_text=(TextView) view.findViewById(R.id.mass_check_ironing_flower_cost_edit_text);
        washing_cost_text=(TextView) view.findViewById(R.id.mass_check_washing_cost_edit_text);
        laser_cost_text=(TextView) view.findViewById(R.id.mass_check_laser_cost_text);
        embroidery_cost_text=(TextView) view.findViewById(R.id.mass_check_embroidery_cost_edit_text);
        crushed_cost_text=(TextView) view.findViewById(R.id.mass_check_crushed_cost_edit_text);
        plate_charge_cost_text=(TextView) view.findViewById(R.id.mass_check_plate_charge_cost_edit_text);
        technology_leading_name_edit_text=(EditText) view.findViewById(R.id.mass_sample_technology_leading_name_edit_text);
        technology_leading_name_edit_text.setText(account.getNickName());
        technology_mass_complete_date_edit_text=(EditText) view.findViewById(R.id.mass_sample_technology_mass_complete_date_edit_text);
        technology_mass_accept_button=(ImageButton) view.findViewById(R.id.sample_technology_mass_accept_button);
        sample_technology_mass_back_button =(ImageButton) view.findViewById(R.id.sample_technology_mass_back_button);
        technology_sample_image_view = (ImageView) view.findViewById(R.id.mass_technology_sample_image_view);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date curDate=new Date(System.currentTimeMillis());
        String timeString=dateFormat.format(curDate);
        technology_mass_complete_date_edit_text.setText(timeString);
        technology_mass_accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (technology_leading_name_edit_text.getText().toString() == null || technology_leading_name_edit_text.getText().toString().length() < 1) {
                    Toast.makeText(getActivity(), "必须填写负责人", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressDialog = ProgressDialog.show(getActivity(), "请等待", "正在处理", true);
                needCraftProductSubmit();
            }
        });
        technology_mass_accept_button.setEnabled(false);
        sample_technology_mass_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentTechnologyActivity) getActivity()).goBack();
            }
        });
        getTaskIdFromServer(listInfo.getOrder().getOrderId());
        return view;
    }


    public void getTaskIdFromServer(String orderId){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",orderId);
        client.get(IPAddress.getIP()+"/fmc/design/mobile_needCraftProductDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    technology_mass_accept_button.setEnabled(true);
                    taskId = response.getJSONObject("orderInfo").getString("taskId");
                    Craft craft = Craft.fromJson(response.getJSONObject("orderInfo").getJSONObject("craft"));
                    if(craft.getCraftFileUrl().length()>1) {
                    Picasso.with(getActivity()).load(IPAddress.getIP() + craft.getCraftFileUrl()).error(R.drawable.ic_action_cancel).into(technology_sample_image_view);
                    }
                    customer_technology_request_text.setText(response.getJSONObject("orderInfo").getString("designCadTech"));
                    ironing_flower_cost_text.setText(""+craft.getStampDutyMoney());
                    washing_cost_text.setText(""+craft.getWashHangDyeMoney());
                    laser_cost_text.setText(""+craft.getLaserMoney());
                    embroidery_cost_text.setText(""+craft.getEmbroideryMoney());
                    crushed_cost_text.setText(""+craft.getCrumpleMoney());
                    plate_charge_cost_text.setText(""+craft.getOpenVersionMoney());
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

    public void needCraftProductSubmit(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",listInfo.getOrder().getOrderId());
        params.put("crafsManName",technology_leading_name_edit_text.getText().toString());
        params.put("crafsProduceDate",technology_mass_complete_date_edit_text.getText().toString());
        params.put("taskId",taskId);
        client.post(IPAddress.getIP()+"/fmc/design/mobile_needCraftProductSubmit.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getActivity(), "成功\n进入下一步", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                ((DepartmentTechnologyActivity) getActivity()).goBack();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
