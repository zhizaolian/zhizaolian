package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentProductionCheckFragment extends Fragment {
    EditText cut_cost_edit_text;
    EditText management_cost_edit_text;
    EditText make_up_cost_edit_text;
    EditText ironing_cost_edit_text;
    EditText lock_cost_edit_text;
    EditText packing_cost_edit_text;
    EditText other_cost_edit_text;
    EditText mass_logistics_cost_edit_text;
    EditText advice_edit_text;
    ImageButton production_accept_button;
    ImageButton production_cancel_button;

    public DepartmentProductionCheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.department_production_check_fragment_layout, container, false);
        cut_cost_edit_text= (EditText) view.findViewById(R.id.cut_cost_text);
        management_cost_edit_text = (EditText) view.findViewById(R.id.management_cost_text);
        make_up_cost_edit_text = (EditText) view.findViewById(R.id.make_up_cost_text);
        ironing_cost_edit_text = (EditText) view.findViewById(R.id.ironing_cost_text);
        lock_cost_edit_text = (EditText) view.findViewById(R.id.lock_cost_text);
        packing_cost_edit_text = (EditText) view.findViewById(R.id.packing_cost_text);
        other_cost_edit_text = (EditText) view.findViewById(R.id.other_cost_text);
        mass_logistics_cost_edit_text = (EditText) view.findViewById(R.id.mass_logistics_cost_text);
        advice_edit_text = (EditText) view.findViewById(R.id.purchase_check_advice_text);
        production_accept_button=(ImageButton) view.findViewById(R.id.production_check_accept);
        production_accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                Toast.makeText(getActivity(),cut_cost_edit_text.getText(),Toast.LENGTH_SHORT).show();
            }
        });
        production_cancel_button=(ImageButton) view.findViewById(R.id.production_check_cancel);
        production_cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });
        return view;
    }


}
