package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentDesignSliceFragment extends Fragment {

    ImageButton design_slice_accept_button;
    EditText version_sorter_name_edit_text;

    public DepartmentDesignSliceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_design_slice_fragment_layout, container, false);
        version_sorter_name_edit_text = (EditText) view.findViewById(R.id.version_sorter_name_edit_text);
        design_slice_accept_button = (ImageButton) view.findViewById(R.id.design_slice_accept_button);
        return view;
    }


}
