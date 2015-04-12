package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentTechnologyDesignFragment extends Fragment {
    EditText check_ironing_flower_cost_edit_text;
    EditText check_washing_cost_edit_text;
    EditText check_laser_cost_text;
    EditText check_embroidery_cost_edit_text;
    EditText check_crushed_cost_edit_text;
    EditText check_plate_charge_cost_edit_text;
    RadioGroup technology_radio_group;
    EditText[] editTexts;

    public DepartmentTechnologyDesignFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_technology_design_fragment_layout, container, false);
        check_ironing_flower_cost_edit_text=(EditText) view.findViewById(R.id.check_ironing_flower_cost_edit_text);
        check_washing_cost_edit_text=(EditText) view.findViewById(R.id.check_washing_cost_edit_text);
        check_laser_cost_text=(EditText) view.findViewById(R.id.check_laser_cost_text);
        check_embroidery_cost_edit_text=(EditText) view.findViewById(R.id.check_embroidery_cost_edit_text);
        check_crushed_cost_edit_text=(EditText) view.findViewById(R.id.check_crushed_cost_edit_text);
        check_plate_charge_cost_edit_text=(EditText) view.findViewById(R.id.check_plate_charge_cost_edit_text);
        editTexts = new EditText[]{
                check_ironing_flower_cost_edit_text,
                check_washing_cost_edit_text,
                check_laser_cost_text,
                check_embroidery_cost_edit_text,
                check_crushed_cost_edit_text,
                check_plate_charge_cost_edit_text};
        technology_radio_group=(RadioGroup) view.findViewById(R.id.technology_radio_group);
        technology_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.technology_needed_radio_button){
                    setEditGroupState(true);
                }else if(checkedId==R.id.technology_not_needed_radio_button){
                    setEditGroupState(false);
                }else {

                }
            }
        });
        return  view;
    }

    public void setEditGroupState(boolean state){
        for(int i=0;i<editTexts.length;i++){
            editTexts[i].setEnabled(state);
        }
    }
}
