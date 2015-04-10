package nju.zhizaolian.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentPurchaseCheckFragment extends Fragment {
    EditText fabric_name_edit_text;
    EditText fabric_weight_edit_text;
    EditText unit_m_cost_edit_text;
    EditText price_per_m_edit_view;
    Button fabric_add_button;
    TableLayout fabric_table;
    EditText[] fabricTextList;
    EditText accessory_name_edit_text;
    EditText accessory_query_edit_text;
    EditText accessory_per_cost_number_edit_text;
    EditText accessory_per_price_edit_text;
    Button accessory_add_button;
    TableLayout accessory_table;
    EditText[] accessoryTextList;
    ArrayList<HashMap<String,String>> fabric_input_data_list = new ArrayList<>();
    String[] fabric_input_head = new String[]{"面料名称","面料克重","单位米耗","每米价格"};
    ArrayList<HashMap<String,String>> accessory_input_data_list = new ArrayList<>();
    String[] accessory_input_head = new String[]{"辅料名称","辅料要求","单件耗数","辅料单价"};
    public DepartmentPurchaseCheckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_purchase_check_fragment_layout, container, false);
        fabric_name_edit_text = (EditText) view.findViewById(R.id.fabric_name_edit_text);
        fabric_weight_edit_text = (EditText) view.findViewById(R.id.fabric_weight_edit_text);
        unit_m_cost_edit_text = (EditText) view.findViewById(R.id.unit_m_cost_edit_text);
        price_per_m_edit_view = (EditText) view.findViewById(R.id.price_per_m_edit_view);
        fabricTextList=new EditText[]{fabric_name_edit_text,fabric_weight_edit_text,unit_m_cost_edit_text,price_per_m_edit_view};
        fabric_table =(TableLayout) view.findViewById(R.id.department_purchase_check_fabric_table);
        fabric_add_button = (Button) view.findViewById(R.id.fabric_add_button);
        accessory_name_edit_text=(EditText) view.findViewById(R.id.accessory_name_edit_text);
        accessory_query_edit_text=(EditText) view.findViewById(R.id.accessory_query_edit_text);
        accessory_per_cost_number_edit_text=(EditText) view.findViewById(R.id.accessory_per_cost_number_edit_text);
        accessory_per_price_edit_text=(EditText) view.findViewById(R.id.accessory_per_price_edit_text);
        accessoryTextList=new EditText[]{accessory_name_edit_text,accessory_query_edit_text,accessory_per_cost_number_edit_text,accessory_per_price_edit_text};
        accessory_table=(TableLayout) view.findViewById(R.id.department_purchase_check_accessory_table);
        accessory_add_button=(Button) view.findViewById(R.id.accessory_add_button);

        fabric_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> map = new HashMap<String, String>();
                TableRow tableRow = new TableRow(getActivity());
                for(int i=0;i<4;i++){
                    TextView textView = new TextView(getActivity());
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(fabricTextList[i].getText().toString());
                    map.put(fabric_input_head[i], fabricTextList[i].getText().toString());
                    tableRow.addView(textView);
                }
                fabric_input_data_list.add(map);
                TextView textView = new TextView(getActivity());
                textView.setText("删除");
                textView.setTextColor(Color.RED);
                textView.setGravity(Gravity.CENTER);
                textView.setClickable(true);
                textView.setOnClickListener(new MyDeleteButtonListener(0));
                tableRow.addView(textView);
                fabric_table.addView(tableRow);
                for(int i=0;i<4;i++){
                    fabricTextList[i].setText(null);
                }

            }

        });
        accessory_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> map = new HashMap<String, String>();
                TableRow tableRow = new TableRow(getActivity());
                for(int i=0;i<4;i++){
                    TextView textView = new TextView(getActivity());
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(accessoryTextList[i].getText().toString());
                    map.put(accessory_input_head[i], accessoryTextList[i].getText().toString());
                    tableRow.addView(textView);
                }
                accessory_input_data_list.add(map);
                TextView textView = new TextView(getActivity());
                textView.setText("删除");
                textView.setTextColor(Color.RED);
                textView.setGravity(Gravity.CENTER);
                textView.setClickable(true);
                textView.setOnClickListener(new MyDeleteButtonListener(1));
                tableRow.addView(textView);
                accessory_table.addView(tableRow);
                for(int i=0;i<4;i++){
                    accessoryTextList[i].setText(null);
                }
            }
        });
        return  view;
    }


    class MyDeleteButtonListener implements View.OnClickListener{
        int tableType;

        public MyDeleteButtonListener(int tableType){
            this.tableType=tableType;

        }
        @Override
        public void onClick(View v) {
            if(tableType==0){
                int index = fabric_table.indexOfChild((TableRow)v.getParent());
                fabric_table.removeView((TableRow)v.getParent());
                fabric_input_data_list.remove(index-1);
            }else {
                int index = accessory_table.indexOfChild((TableRow)v.getParent());
                accessory_table.removeView((TableRow)v.getParent());
                accessory_input_data_list.remove(index-1);
            }
        }
    }
}
