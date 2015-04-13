package nju.zhizaolian.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignContractFragment extends Fragment {


    public SignContractFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_sign_contract,container);

        return view;
    }


}
