package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nju.zhizaolian.R;


public class OrderBaseInfoFragment extends android.support.v4.app.Fragment {


    public OrderBaseInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_base_info, container, false);
    }




}
