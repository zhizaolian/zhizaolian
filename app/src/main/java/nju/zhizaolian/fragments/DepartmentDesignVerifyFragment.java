package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentDesignVerifyFragment extends Fragment {


    public DepartmentDesignVerifyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.department_design_verify_fragment_layout, container, false);
    }


}
