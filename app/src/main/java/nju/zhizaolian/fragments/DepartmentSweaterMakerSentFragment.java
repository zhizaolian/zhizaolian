package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentSweaterMakerSentFragment extends Fragment {

    TableLayout sweater_maker_plan_table;
    EditText processing_name_edit_text;
    EditText send_out_time_edit_text;
    EditText sent_leading_name_edit_text;
    ImageButton sweater_maker_sent_accept_button;

    public DepartmentSweaterMakerSentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_sweater_maker_sent_fragment_layout, container, false);
        sweater_maker_plan_table=(TableLayout) view.findViewById(R.id.sweater_maker_plan_table);
        processing_name_edit_text=(EditText) view.findViewById(R.id.processing_name_edit_text);
        send_out_time_edit_text=(EditText) view.findViewById(R.id.send_out_time_edit_text);
        sent_leading_name_edit_text=(EditText) view.findViewById(R.id.sent_leading_name_edit_text);
        sweater_maker_sent_accept_button=(ImageButton) view.findViewById(R.id.sweater_maker_sent_accept_button);
        return  view;
    }


}
