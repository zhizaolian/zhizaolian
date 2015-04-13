package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignContractFragment extends Fragment {

    ImageView contractView;
    public SignContractFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_sign_contract, container, false);
        contractView= (ImageView) view.findViewById(R.id.contractImageView);
        contractView.setImageResource(R.drawable.ic_launcher);

        return view;
    }


}
