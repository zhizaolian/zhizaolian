package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeQuoteFragment extends Fragment {


    public ChangeQuoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_quote, container, false);
    }


}
