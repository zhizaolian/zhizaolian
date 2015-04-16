package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentSweaterMakerCheckFragment extends Fragment {
    RadioGroup mission_choose_radio_group;
    EditText completed_date_edit_text;
    EditText check_leading_name_edit_text;
    EditText remark_info_edit_text;
    ListView operate_history_list;
    ImageButton sweater_maker_check_accept_button;
    RadioButton make_small_sample_radio;
    RadioButton technology_make_radio;
    RadioButton version_sample_radio;
    RadioButton sample_confirm_radio;
    RadioButton[] radioButtonList;


    public DepartmentSweaterMakerCheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.department_sweater_maker_check_fragment_layout, container, false);
        mission_choose_radio_group=(RadioGroup) view.findViewById(R.id.mission_choose_radio_group);
        completed_date_edit_text=(EditText) view.findViewById(R.id.completed_date_edit_text);
        check_leading_name_edit_text=(EditText) view.findViewById(R.id.check_leading_name_edit_text);
        remark_info_edit_text=(EditText) view.findViewById(R.id.remark_info_edit_text);
        operate_history_list=(ListView) view.findViewById(R.id.operate_history_list);
        sweater_maker_check_accept_button=(ImageButton) view.findViewById(R.id.sweater_maker_check_accept_button);
        make_small_sample_radio=(RadioButton) view.findViewById(R.id.make_small_sample_radio);
        technology_make_radio=(RadioButton) view.findViewById(R.id.technology_make_radio);
        version_sample_radio=(RadioButton) view.findViewById(R.id.version_sample_radio);
        sample_confirm_radio=(RadioButton) view.findViewById(R.id.sample_confirm_radio);
        radioButtonList = new RadioButton[]{make_small_sample_radio,technology_make_radio,version_sample_radio,sample_confirm_radio};

        return view;
    }


}
