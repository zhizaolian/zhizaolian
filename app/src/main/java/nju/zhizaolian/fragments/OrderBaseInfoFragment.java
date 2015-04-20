package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import nju.zhizaolian.R;
import nju.zhizaolian.help.MyUtils;
import nju.zhizaolian.models.Custom;
import nju.zhizaolian.models.Order;


public class OrderBaseInfoFragment extends android.support.v4.app.Fragment {

    private Custom custom;
    private Order order;

    private Switch isHaoDuoYi;
    private TextView getOrderTime;
    private EditText orderSource;
    private TextView salesman;
    private TextView isDuplicate;
    private TextView customerId;
    private TextView customerName;
    private TextView company;
    private TextView companyFax;
    private TextView customerPhone1;
    private TextView customerPhone2;
    private TextView companyAddress;
    private EditText styleName;
    private Spinner clothesType;
    private Spinner styleSex;
    private Spinner styleSeason;
    private Spinner materialType;
    private CheckBox waterWash;
    private CheckBox laser;
    private CheckBox push;
    private CheckBox other1;
    private EditText otherEdit1;
    private CheckBox haveMainMark;
    private CheckBox haveTag;
    private CheckBox haveWaterWash;
    private CheckBox other2;
    private EditText otherEdit2;
    private EditText reference;
    public OrderBaseInfoFragment() {
        // Required empty public constructor
    }
    public OrderBaseInfoFragment(Custom custom){
        this.custom=custom;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order_base_info, container, false);
        isHaoDuoYi= (Switch) view.findViewById(R.id.is_hao_duo_yi);
        getOrderTime=(TextView)view.findViewById(R.id.get_order_time);
        orderSource=(EditText)view.findViewById(R.id.order_source);
        salesman=(TextView)view.findViewById(R.id.salesman_name);
        isDuplicate=(TextView)view.findViewById(R.id.Duplicate);
        customerId=(TextView)view.findViewById(R.id.custom_id);
        customerName=(TextView)view.findViewById(R.id.custom_name);
        company=(TextView)view.findViewById(R.id.custom_company);
        companyFax=(TextView)view.findViewById(R.id.company_fax);
        customerPhone1=(TextView)view.findViewById(R.id.customer_phone1);
        customerPhone2=(TextView)view.findViewById(R.id.customer_phone2);
        companyAddress=(TextView)view.findViewById(R.id.company_address);
        styleName=(EditText)view.findViewById(R.id.order_style_name);
        clothesType=(Spinner)view.findViewById(R.id.spinner_clothes);
        styleSex=(Spinner)view.findViewById(R.id.spinner_sex);
        styleSeason=(Spinner)view.findViewById(R.id.spinner_season);
        materialType=(Spinner)view.findViewById(R.id.spinner_material);
        waterWash=(CheckBox)view.findViewById(R.id.water_wash_checkbox);
        laser=(CheckBox)view.findViewById(R.id.laser_checkbox);
        push=(CheckBox)view.findViewById(R.id.push_checkbox);
        other1=(CheckBox)view.findViewById(R.id.other_checkbox);
        haveMainMark=(CheckBox)view.findViewById(R.id.main_mark_checkbox);
        haveTag=(CheckBox)view.findViewById(R.id.tag_checkbox);
        haveWaterWash=(CheckBox)view.findViewById(R.id.have_water_wash_checkbox);
        other2=(CheckBox)view.findViewById(R.id.other_checkbox2);
        otherEdit2=(EditText)view.findViewById(R.id.other_editText2);
        reference=(EditText)view.findViewById(R.id.reference);
        //初始化
        getOrderTime.setText(MyUtils.getCurrentDate());
        salesman.setText("管理员");
        isDuplicate.setText("否");
        customerName.setText(custom.getCustomerName());
        customerId.setText(custom.getCustomerId());
        company.setText(custom.getCompanyName());
        companyFax.setText(custom.getCompanyFax());
        customerPhone1.setText(custom.getContactPhone1());
        customerPhone2.setText(custom.getContactPhone2());
        companyAddress.setText(custom.getCompanyAddress());

        return view;
    }




}
