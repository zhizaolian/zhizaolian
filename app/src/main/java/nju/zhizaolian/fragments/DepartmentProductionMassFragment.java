package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentProductionMassFragment extends Fragment {

    ImageButton acceptButton;
    ImageButton cancelButton;
    TableLayout planTable;
    String sizeList[] =new String[]{"颜色","XS","S","M","L","XL","XXL","均码"};
    ArrayList<HashMap<String,String>> planData;
    ArrayList<HashMap<String,String>> actuallyData = new ArrayList<>();

    public DepartmentProductionMassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.department_production_mass_fragment_layout,container,false);
        acceptButton=(ImageButton)view.findViewById(R.id.production_process_accept_button);
        cancelButton=(ImageButton)view.findViewById(R.id.production_process_cancel_button);
        planTable =(TableLayout) view.findViewById(R.id.plan_production_table);
        planData=(ArrayList<HashMap<String,String>>) getArguments().getSerializable("plan_table");
        if(planData!=null){
            //给实际的表单加数据
            for(int i=0;i<planData.size();i++){
                HashMap<String,String> map= new HashMap<>();
                actuallyData.add(map);
            }
            //计划任务
            TableRow row = new TableRow(getActivity());
            TextView tv = new TextView(getActivity());
            tv.setText("计划生产任务");
            row.addView(tv);
            planTable.addView(row);
            for(int i=0;i<planData.get(0).size();i++){
                TableRow tableRow = new TableRow(getActivity());
                TextView textView = new TextView(getActivity());
                textView.setText(sizeList[i]);
                tableRow.addView(textView);
                for(int j=0;j<planData.size();j++){
                    TextView textView1 = new TextView(getActivity());
                    textView1.setPadding(40,40,0,0);
                    textView.setGravity(Gravity.CENTER);
                    textView1.setText(planData.get(j).get(sizeList[i]));
                    tableRow.addView(textView1);
                }
                planTable.addView(tableRow);
            }
            //实际任务
            TableRow row1 = new TableRow(getActivity());
            TextView tv1 = new TextView(getActivity());
            tv1.setText("实际生产任务");
            row1.addView(tv1);
            planTable.addView(row1);
            for(int i=0;i<planData.get(0).size();i++){
                TableRow tableRow = new TableRow(getActivity());
                TextView textView = new TextView(getActivity());
                textView.setText(sizeList[i]);
                textView.setGravity(Gravity.CENTER);
                tableRow.addView(textView);
                for(int j=0;j<planData.size();j++){
                    if(i==0){
                        TextView textView1 = new TextView(getActivity());
                        textView1.setPadding(40,40,0,0);
                        textView.setGravity(Gravity.CENTER);
                        actuallyData.get(j).put("颜色",planData.get(j).get(sizeList[i]));
                        textView1.setText(planData.get(j).get(sizeList[i]));
                        tableRow.addView(textView1);
                    }else {
                        EditText editText = new EditText(getActivity());
                        editText.setEms(3);
                        editText.addTextChangedListener(new MyTextWatcher(i,j));
                        tableRow.addView(editText);
                    }
                }
                planTable.addView(tableRow);
            }
        }

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        return view;
    }

private  class MyTextWatcher implements TextWatcher{

    int i=0;
    int j=0;
    public MyTextWatcher (int x,int y){
        i=x;
        j=y;
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        actuallyData.get(j).put(sizeList[i],s.toString());
        Toast.makeText(getActivity(),actuallyData.get(j).get(sizeList[i]),Toast.LENGTH_SHORT).show();
    }
    }


}
