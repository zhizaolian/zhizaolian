package nju.zhizaolian.fragments;


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
import nju.zhizaolian.activities.DepartmentDesignActivity;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.ListInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentDesignSliceFragment extends Fragment {

    ImageButton design_slice_accept_button;
    ImageButton design_slice_back_button;
    EditText version_sorter_name_edit_text;
    String taskId;
    ListInfo info;
    Account account;

    public DepartmentDesignSliceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_design_slice_fragment_layout, container, false);
        info = (ListInfo) getArguments().getSerializable("info");
        account = (Account) getArguments().getSerializable("account");
        version_sorter_name_edit_text = (EditText) view.findViewById(R.id.version_sorter_name_edit_text);
        version_sorter_name_edit_text.setText(account.getNickName());
        design_slice_accept_button = (ImageButton) view.findViewById(R.id.design_slice_accept_button);
        design_slice_accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(version_sorter_name_edit_text.getText().toString()==null||version_sorter_name_edit_text.getText().toString().length()<1){
                    version_sorter_name_edit_text.setError("排版切片人的名字忘了填写");
                    return;
                }
                getTypeSettingSliceSubmit();
            }
        });
        design_slice_accept_button.setEnabled(false);
        design_slice_back_button =(ImageButton) view.findViewById(R.id.design_slice_back_button);
        design_slice_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DepartmentDesignActivity) getActivity()).goBack();
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
        client.get(IPAddress.getIP()+"/fmc/design/mobile_getTypeSettingSliceDetail.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    taskId = response.getJSONObject("orderInfo").getString("taskId");
                    design_slice_accept_button.setEnabled(true);
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

    public void getTypeSettingSliceSubmit(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        SharedPreferences settings =getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        params.put("jsessionId",jSessionId);
        params.put("orderId",info.getOrder().getOrderId());
        params.put("taskId",taskId);
        params.put("cadding_side",version_sorter_name_edit_text.getText().toString());
        client.post(IPAddress.getIP()+"/fmc/design/mobile_getTypeSettingSliceSubmit.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(getActivity(), "成功，跳转下一步", Toast.LENGTH_SHORT).show();
                ((DepartmentDesignActivity) getActivity()).goBack();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getActivity(), "网络连接错误", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
