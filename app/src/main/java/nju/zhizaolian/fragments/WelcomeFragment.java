package nju.zhizaolian.fragments;


import android.os.Bundle;

import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;
import nju.zhizaolian.adapters.MainMenuAdapter;
import nju.zhizaolian.models.Account;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener{

    private TextView inquirySheet;
    private TextView priceSheet;
    private TextView inquirySheetUploadTime;
    private TextView priceSheetUploadTime;
    private DrawerLayout drawerLayout;
    private ArrayList<HashMap<String,String>> data =new ArrayList<>();
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

    public WelcomeFragment() {
        // Required empty public constructor
    }

    public DrawerLayout getDrawerLayout(){
        return drawerLayout;
    }
    public interface InquirySheetDownloadListener{
        void inquirySheetDownload();
    }

    public interface PriceSheetDownloadListener{
        void priceSheetDownload();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.welcome_layout,container,false);
        Account account =(Account) getArguments().getSerializable("account");
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
        ListView departmentList =(ListView) drawerLayout.findViewById(R.id.menuList);
        setMenuByDepartment(account.getUserType());
        departmentList.setAdapter(new MainMenuAdapter(
                getActivity(),
                data,
                R.layout.left_drawer_menu_item,
                new String[]{"department","number"},
                new int[]{R.id.item_department,R.id.item_department_number}));
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


    public void setMenuByDepartment(String type){
        switch (type){
            case "ADMIN":
                for(int i=0;i<departmentLists.length;i++){
                    HashMap<String,String> map= new HashMap<>();
                    map.put("department",departmentLists[i]);
                    map.put("number","0");
                    data.add(map);
                }


        }
    }

}
