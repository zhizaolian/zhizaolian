package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import nju.zhizaolian.R;
import nju.zhizaolian.models.Custom;


public class OrderBaseInfoFragment extends android.support.v4.app.Fragment {

    private Custom custom;
    private Spinner isHaoDuoYi;
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
        return inflater.inflate(R.layout.fragment_order_base_info, container, false);
    }




}
