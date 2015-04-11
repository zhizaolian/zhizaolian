package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentPurchaseSweaterFragment extends Fragment {

    RadioGroup inventory_radio_group;
    RadioButton enoughButton;
    RadioButton notEnoughButton;
    EditText supplier_name_edit_text;
    EditText purchase_person_name_edit_text;
    EditText purchase_date_edit_text;
    EditText sweater_type_edit_text;
    EditText weight_edit_text;
    EditText total_price_edit_text;
    EditText [] editTextGroup;
    public DepartmentPurchaseSweaterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.department_purchase_sweater_fragment_layout, container, false);
        inventory_radio_group = (RadioGroup) view.findViewById(R.id.inventory_radio_group);
        enoughButton = (RadioButton) view.findViewById(R.id.inventory_enough_radio_button);
        notEnoughButton =(RadioButton) view.findViewById(R.id.inventory_not_enough_radio_button);
        supplier_name_edit_text =(EditText) view.findViewById(R.id.supplier_name_edit_text);
        purchase_person_name_edit_text =(EditText) view.findViewById(R.id.purchase_person_name_edit_text);
        purchase_date_edit_text =(EditText) view.findViewById(R.id.purchase_date_edit_text);
        sweater_type_edit_text =(EditText) view.findViewById(R.id.sweater_type_edit_text);
        weight_edit_text =(EditText) view.findViewById(R.id.weight_edit_text);
        total_price_edit_text =(EditText) view.findViewById(R.id.total_price_edit_text);
        editTextGroup= new EditText[]{supplier_name_edit_text,sweater_type_edit_text,weight_edit_text,total_price_edit_text};
        inventory_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(enoughButton.getId()==checkedId){
                    setEditGroupEditableState(false);
                }else if(notEnoughButton.getId()==checkedId){
                    setEditGroupEditableState(true);
                }else {

                }
            }
        });
        return view;
    }


    public void setEditGroupEditableState(boolean state){
        for(int i=0;i<editTextGroup.length;i++){
            editTextGroup[i].setEnabled(state);
        }
    }
}
