package nju.zhizaolian.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.DepartmentSecretaryActivity;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Employee;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;
import nju.zhizaolian.models.Produce;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentSecretaryChangeFragment extends Fragment {

    ImageButton secretary_back_button;
    ImageButton secretary_submit_button;
    Spinner secretary_spinner;
    ArrayList<String> employeeList= new ArrayList<>();
    String taskId;
    ListInfo info;
    Account account;
    ArrayList<Employee> employeeArrayList;
    ArrayAdapter<String> adapter;
    String now_employee_id;
    ProgressDialog progressDialog;

    public DepartmentSecretaryChangeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.department_secretary_change_fragment_layout, container, false);
        secretary_back_button = (ImageButton) view.findViewById(R.id.secretary_back_button);
        secretary_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentSecretaryActivity) getActivity()).goBack();
            }
        });
        secretary_submit_button = (ImageButton) view.findViewById(R.id.secretary_submit_button);
        secretary_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(getActivity(),"请等待","提交中",true);
                allocateOrderSubmit();
            }
        });
        secretary_spinner = (Spinner) view.findViewById(R.id.secretary_spinner);
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,employeeList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        info = (ListInfo) getArguments().getSerializable("info");
        account = (Account) getArguments().getSerializable("account");
        secretary_spinner.setAdapter(adapter);
        secretary_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                now_employee_id=employeeArrayList.get(position).getEmployeeId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        client.get(IPAddress.getIP()+"/fmc/market/mobile_allocateOrderDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    employeeArrayList =Employee.fromJson(response.getJSONArray("employeeList"));
                    for (int i=0;i<employeeArrayList.size();i++){
                        employeeList.add(employeeArrayList.get(i).getEmployeeName());
                    }
                    adapter.notifyDataSetChanged();
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

    public void allocateOrderSubmit(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("order_id",info.getOrder().getOrderId());
        params.put("nextEmployeeId",now_employee_id);
        client.post(IPAddress.getIP()+"/fmc/market/mobile_AllocateOrderSubmit.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getActivity(), "操作成功\n进入下一步", Toast.LENGTH_SHORT).show();
                ((DepartmentSecretaryActivity) getActivity()).goBack();
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
