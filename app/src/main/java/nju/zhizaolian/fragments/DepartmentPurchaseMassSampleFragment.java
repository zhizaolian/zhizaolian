package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentPurchaseMassSampleFragment extends Fragment {

    TableLayout fabric_purchase_table;
    TableLayout accessory_purchase_table;
    EditText purchase_person_name_edit_text;
    EditText purchase_date_edit_text;
    EditText supplier_name_edit_text;
    ImageButton purchase_accept_button;
    ImageButton purchase_cancel_button;
    String fabricTableHead[] = new String[]{"面料名称","单件米耗","件数","总采购米数"};
    String accessoryTableHead[] = new String[]{"辅料名称","单件耗数","件数","总采购个数"};

    public DepartmentPurchaseMassSampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.department_purchase_mass_sample_fragment_layout,container,false);
       fabric_purchase_table = (TableLayout) view.findViewById(R.id.fabric_purchase_table);
       accessory_purchase_table=(TableLayout) view.findViewById(R.id.accessory_purchase_table);
       purchase_person_name_edit_text=(EditText) view.findViewById(R.id.purchase_person_name_edit_text);
       purchase_date_edit_text=(EditText) view.findViewById(R.id.purchase_date_edit_text);
       supplier_name_edit_text=(EditText) view.findViewById(R.id.supplier_name_edit_text);
       purchase_accept_button =(ImageButton) view.findViewById(R.id.purchase_accept_button);
       purchase_cancel_button =(ImageButton) view.findViewById(R.id.purchase_cancel_button);
       ArrayList<HashMap<String,String>> fabric_purchase_table_data =(ArrayList<HashMap<String,String>>)getArguments().getSerializable("fabric_purchase");
       ArrayList<HashMap<String,String>> accessory_purchase_table_data =(ArrayList<HashMap<String,String>>)getArguments().getSerializable("accessory_purchase");
       generateTable(fabric_purchase_table,fabric_purchase_table_data,0);
       generateTable(accessory_purchase_table,accessory_purchase_table_data,1);
       return view;
    }

    public void generateTable(TableLayout table,ArrayList<HashMap<String,String>> data,int type){
        for(int i=0;i<data.size();i++) {
            TableRow row= new TableRow(getActivity());
            for (int j = 0; j < 4; j++) {
                TextView textView = new TextView(getActivity());
                if(type==0) {
                    textView.setText(data.get(i).get(fabricTableHead[j]));
                }else {
                    textView.setText(data.get(i).get(accessoryTableHead[j]));
                }
                textView.setGravity(Gravity.CENTER);
                row.addView(textView);
            }
            table.addView(row);
        }
    }


}
