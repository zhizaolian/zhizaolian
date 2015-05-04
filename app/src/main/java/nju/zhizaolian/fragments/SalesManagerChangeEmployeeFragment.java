package nju.zhizaolian.fragments;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Employee;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class SalesManagerChangeEmployeeFragment extends Fragment {

    EditText change_employee_reason_edit_text,change_employee_advice_edit_text;
    Spinner change_employee_spinner;
    ImageButton change_employee_back_button,change_employee_submit_button,change_employee_cancel_button;
    ListInfo info;
    Account account;
    ArrayAdapter<String> adapter;
    ArrayList<String> employeeNameList= new ArrayList<>();
    ArrayList<Employee> employeeArrayList;
    String now_employee_id;
    ProgressDialog progressDialog;

    public SalesManagerChangeEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.sales_manager_change_employee_fragment_layout, container, false);
        change_employee_reason_edit_text = (EditText) view.findViewById(R.id.change_employee_reason_edit_text);
        change_employee_advice_edit_text = (EditText) view.findViewById(R.id.change_employee_advice_edit_text);
        change_employee_back_button = (ImageButton) view.findViewById(R.id.change_employee_back_button);
        change_employee_submit_button = (ImageButton) view.findViewById(R.id.change_employee_submit_button);
        change_employee_cancel_button = (ImageButton) view.findViewById(R.id.change_employee_cancel_button);
        change_employee_spinner = (Spinner) view.findViewById(R.id.change_employee_spinner);
        adapter = new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item,employeeNameList);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        info = (ListInfo) getArguments().getSerializable("info");
        account = (Account) getArguments().getSerializable("account");
        change_employee_spinner.setAdapter(adapter);
        change_employee_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                now_employee_id=employeeArrayList.get(position).getEmployeeId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }


    public void getTaskIdFromServer(String orderId){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",orderId);
        client.get(IPAddress.getIP()+"/fmc/market/mobile_verifyAlterDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    employeeArrayList =Employee.fromJson(response.getJSONArray("employeeList"));
                    for (int i=0;i<employeeArrayList.size();i++){
                        employeeNameList.add(employeeArrayList.get(i).getEmployeeName());
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



}
