package nju.zhizaolian.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentTechnologyMassFragment extends Fragment {

    TextView customer_technology_request_text;
    TextView ironing_flower_cost_text;
    TextView washing_cost_text;
    TextView laser_cost_text;
    TextView embroidery_cost_text;
    TextView crushed_cost_text;
    TextView plate_charge_cost_text;
    EditText technology_leading_name_edit_text;
    EditText technology_mass_complete_date_edit_text;
    ImageButton technology_mass_accept_button;
    ArrayList<String> dataList=new ArrayList<>();
    Bitmap sampleImage;


    public DepartmentTechnologyMassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_technology_mass_fragment_layout, container, false);
        customer_technology_request_text=(TextView) view.findViewById(R.id.customer_technology_request_text);
        ironing_flower_cost_text=(TextView) view.findViewById(R.id.ironing_flower_cost_text);
        washing_cost_text=(TextView) view.findViewById(R.id.washing_cost_text);
        laser_cost_text=(TextView) view.findViewById(R.id.laser_cost_text);
        embroidery_cost_text=(TextView) view.findViewById(R.id.embroidery_cost_text);
        crushed_cost_text=(TextView) view.findViewById(R.id.crushed_cost_text);
        plate_charge_cost_text=(TextView) view.findViewById(R.id.plate_charge_cost_text);
        technology_leading_name_edit_text=(EditText) view.findViewById(R.id.technology_leading_name_edit_text);
        technology_mass_complete_date_edit_text=(EditText) view.findViewById(R.id.technology_mass_complete_date_edit_text);
        technology_mass_accept_button=(ImageButton) view.findViewById(R.id.technology_mass_accept_button);
        TextView viewList[] = {
                customer_technology_request_text,
                ironing_flower_cost_text,
                washing_cost_text,
                laser_cost_text,
                embroidery_cost_text,
                crushed_cost_text,
                plate_charge_cost_text};
        dataList =(ArrayList<String>) getArguments().getSerializable("data");
        String time =(String) getArguments().getSerializable("time");
        technology_mass_complete_date_edit_text.setText(time);
        //TODO get an image
        for(int i=0;i<viewList.length;i++){
            viewList[i].setText(dataList.get(i));
        }
        technology_mass_accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),technology_leading_name_edit_text.getText().toString()+",\n"+technology_mass_complete_date_edit_text.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}
