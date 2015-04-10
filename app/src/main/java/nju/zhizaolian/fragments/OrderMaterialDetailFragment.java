package nju.zhizaolian.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import nju.zhizaolian.R;


public class OrderMaterialDetailFragment extends android.support.v4.app.Fragment {
    Button materialButton=null;
    Button accessoriesButton=null;
    LinearLayout materiallayout=null;
    LinearLayout accessoriesLayout=null;
    EditText materialName=null;
    EditText materialWeight=null;
    EditText accessoriesName=null;
    EditText accessoresWeight=null;
    ListView materialView=null;

    Editable smaterialName=null;
    Editable smaterialWeight=null;

    public OrderMaterialDetailFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_order_material_detail, container, false);


        //初始化
        materialButton= (Button) view.findViewById(R.id.materialButton);
        accessoriesButton=(Button)view.findViewById(R.id.accessoriesButton);
        materiallayout= (LinearLayout) view.findViewById(R.id.materialLayout);
        accessoriesLayout=(LinearLayout)view.findViewById(R.id.accessoriesLayout);
        materialName= (EditText) view.findViewById(R.id.materialName);
        materialWeight=(EditText) view.findViewById(R.id.materialWeight);
        accessoriesName=(EditText)view.findViewById(R.id.accessoriesName);
        accessoresWeight=(EditText)view.findViewById(R.id.accessoriesWeight);
        materialView= (ListView) view.findViewById(R.id.materialaddListView);

        smaterialName=materialName.getText();
        smaterialWeight=materialWeight.getText();

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smaterialName.length()==0 || smaterialWeight.length()==0){
                    Toast.makeText(view.getContext(),"请把信息填写完整",Toast.LENGTH_SHORT).show();
                }else{




                }

            }
        });
        return view;
    }



}
