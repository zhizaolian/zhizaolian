package nju.zhizaolian.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.CustomerManagerActivity;
import nju.zhizaolian.activities.OrderListActivity;
import nju.zhizaolian.adapters.MainMenuAdapter;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.IPAddress;
import nju.zhizaolian.models.TaskNumber;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener{

    private WelcomeFragment mFragment;
    private TextView inquirySheet;
    private TextView priceSheet;
    private TextView inquirySheetUploadTime;
    private TextView priceSheetUploadTime;
    private DrawerLayout drawerLayout;
    private ListView departmentList;
    private ArrayList<HashMap<String,String>> data =new ArrayList<>();
    Account account;
    TaskNumber taskNumber;
    private String[] departmentLists = {
            "市场主管",
            "市场部",
            "秘书部",
            "设计部",
            "工艺部",
            "采购部",
            "生产部",
            "毛衣制作部",
            "财务部",
            "物流部",
            "质检部",
            "人事部",
            "系统管理"
    };

    private int authorization[]={0,0,0,0,0,0,0,0,0,0,0,0,0}; //用户权限，1表示显示，0表示不显示

    private int departmentNumber[]={0,0,0,0,0,0,0,0,0,0,0,0,0};//待办项目默认为零

    public WelcomeFragment() {
        // Required empty public constructor
    }

    public interface InquirySheetDownloadListener{
        void inquirySheetDownload();
    }

    public interface PriceSheetDownloadListener{
        void priceSheetDownload();
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mFragment=this;
        View view = inflater.inflate(R.layout.welcome_layout,container,false);
        account =(Account) getArguments().getSerializable("account");
        inquirySheet=(TextView) view.findViewById(R.id.inquiry_sheet_download);
        inquirySheet.setOnClickListener(this);
        priceSheet=(TextView) view.findViewById(R.id.price_sheet_download);
        priceSheet.setOnClickListener(this);
        inquirySheetUploadTime=(TextView) view.findViewById(R.id.inquiry_sheet_upload_time);
        priceSheetUploadTime=(TextView) view.findViewById(R.id.price_sheet_upload_time);
        //Test Data
        inquirySheetUploadTime.setText("2015-03-31");
        priceSheetUploadTime.setText("2015-04-01");
        //Test Data End
        drawerLayout =(DrawerLayout) view.findViewById(R.id.main_drawer_layout);
        TextView usernameView =(TextView)drawerLayout.findViewById(R.id.user_name);
        usernameView.setText(account.getNickName());
        //usernameView加监听为了搞笑而已
        usernameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mFragment.getActivity(),"没错，就是你",Toast.LENGTH_SHORT).show();
            }
        });
        TextView homeView =(TextView) drawerLayout.findViewById(R.id.go_to_main_view);
        homeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
        TextView customerView = (TextView) drawerLayout.findViewById(R.id.go_to_customer_manager_view);
        customerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mFragment.getActivity(), CustomerManagerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("account",account);
                intent.putExtras(bundle);
                mFragment.getActivity().startActivity(intent);
            }
        });
        TextView orderView =(TextView) drawerLayout.findViewById(R.id.go_to_order_manager_view);
        orderView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mFragment.getActivity(), OrderListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("account",account);
                intent.putExtras(bundle);
                mFragment.getActivity().startActivity(intent);
            }
        });
        departmentList =(ListView) drawerLayout.findViewById(R.id.menuList);
        return  view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.inquiry_sheet_download:
                ((InquirySheetDownloadListener)getActivity()).inquirySheetDownload();
                break;
            case R.id.price_sheet_download:
                ((PriceSheetDownloadListener)getActivity()).priceSheetDownload();
                break;
        }
    }


    public void setMenuByDepartment(String role){
        switch (role){
            case "ADMIN":
                for(int i=0;i<authorization.length;i++)
                    authorization[i]=1;
                break;
            case "marketManager":
                authorization[0]=1;
                break;
            case  "marketStaff":
                authorization[1]=1;
                break;
            case  "marketSecretary":
                authorization[2]=1;
                break;
            case "designManager":
                authorization[3]=1;
                authorization[4]=1;
                break;
            case "purchaseManager":
                authorization[5]=1;
                break;
            case "produceManager":
                authorization[6]=1;
                break;
            case "SweaterMakeManager":
                authorization[7]=1;
                break;
            case "financeManager":
                authorization[8]=1;
                break;
            case "logisticsManager":
                authorization[9]=1;
                break;
            case "qualityManager":
                authorization[10]=1;
                break;
        }

        data.clear();
        for(int i=0;i<authorization.length;i++){
            if(authorization[i]==1) {
                HashMap<String, String> map = new HashMap<>();
                map.put("department", departmentLists[i]);
                map.put("number", ""+departmentNumber[i]);
                data.add(map);
            }
        }
        departmentList.setAdapter(new MainMenuAdapter(
                getActivity(),
                data,
                R.layout.left_drawer_menu_item,
                new String[]{"department","number"},
                new int[]{R.id.item_department,R.id.item_department_number},
                account,
                mFragment.getActivity(),
                taskNumber));
        Toast.makeText(mFragment.getActivity(),"亲爱的"+account.getNickName()+"\n你有"+taskNumber.getTaskNumber()+"条任务待处理",Toast.LENGTH_SHORT).show();
    }

    public void getTaskNumberFromServer(){
        SharedPreferences settings = getActivity().getSharedPreferences("common", 0);
        String jSessionId=settings.getString("jsessionId","");
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("jsessionId",jSessionId);
        asyncHttpClient.get(IPAddress.getIP()+"/fmc/common/mobile_getTaskNumber.do",params,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                if(response.toString().contains("notify")){
                    Toast.makeText(mFragment.getActivity(),"登陆超时，退出重试",Toast.LENGTH_SHORT).show();
                }else {
                    taskNumber = TaskNumber.fromJson(response);
                    if (taskNumber == null) {
                        Toast.makeText(mFragment.getActivity(), "服务器错误，稍后重试", Toast.LENGTH_SHORT).show();
                    } else {
                        departmentNumber = taskNumber.getDepartmentTaskNumbers();
                        setMenuByDepartment(account.getUserRole());
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(mFragment.getActivity(),"网络连接错误，稍后重试",Toast.LENGTH_SHORT).show();
                Log.e("error","error",throwable);
            }
        });
    }

    @Override
    public void onResume() {
        getTaskNumberFromServer();
        super.onResume();
    }

    public void closeDrawers(){
        drawerLayout.closeDrawers();
    }

    public boolean isDrawerOpen(){
        return drawerLayout.isDrawerOpen(Gravity.LEFT);
    }
}
