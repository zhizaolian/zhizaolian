package nju.zhizaolian.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nju.zhizaolian.R;


public class OrderMaterialDetailFragment extends android.support.v4.app.Fragment {
    Button materialButton=null;
    Button accessoriesButton=null;
    Button materialSaveButton=null;
    LinearLayout materialLayout=null;
    LinearLayout accessoriesLayout=null;
    EditText materialNameEdit=null;
    EditText materialWeightEdit=null;
    EditText accessoriesNameEdit=null;
    EditText accessoriesWeightEdit=null;


    Editable sMaterialName=null;
    Editable sMaterialWeight=null;
    Editable sAccessoriesName=null;
    Editable sAccessoriesWeight=null;
    ArrayList<String> fabricName=new ArrayList<String>();
    ArrayList<String> fabricWeight=new ArrayList<String>();
    ArrayList<String>  accessoriesName=new ArrayList<String>();
    ArrayList<String> accessoriesWeight=new ArrayList<String>();
    public OrderMaterialDetailFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_order_material_detail, container, false);


        //初始化
        materialButton= (Button) view.findViewById(R.id.materialButton);
        accessoriesButton=(Button)view.findViewById(R.id.accessoriesButton);
        materialLayout= (LinearLayout) view.findViewById(R.id.materialLayout);
        accessoriesLayout=(LinearLayout)view.findViewById(R.id.accessoriesLayout);
        materialNameEdit= (EditText) view.findViewById(R.id.materialName);
        materialWeightEdit=(EditText) view.findViewById(R.id.materialWeight);
        accessoriesNameEdit=(EditText)view.findViewById(R.id.accessoriesName);
        accessoriesWeightEdit=(EditText)view.findViewById(R.id.accessoriesWeight);
        materialSaveButton=(Button)view.findViewById(R.id.material_save_button);

        sMaterialName=materialNameEdit.getText();
        sMaterialWeight=materialWeightEdit.getText();
        sAccessoriesName=accessoriesNameEdit.getText();
        sAccessoriesWeight=accessoriesWeightEdit.getText();


        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sMaterialName.length()==0 || sMaterialWeight.length()==0){
                    Toast.makeText(view.getContext(),"请把信息填写完整",Toast.LENGTH_SHORT).show();
                }else{
                   View newView=createMaterialView(inflater, container);
                    materialLayout.addView(newView);
                    

                }

            }
        });

        accessoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sAccessoriesName.length()==0||sAccessoriesWeight.length()==0){
                    Toast.makeText(view.getContext(),"请把信息填写完整",Toast.LENGTH_SHORT).show();
                }else{
                    View newView=createAccessoriesView(inflater,container);
                    accessoriesLayout.addView(newView);
                    //todo 添加数据
                }
            }
        });
        return view;
    }


    private View createMaterialView(LayoutInflater inflater,ViewGroup container){
        final View addView=inflater.inflate(R.layout.material_additem_layout,container,false);
        TextView materialName= (TextView) addView.findViewById(R.id.materialaddname);
        TextView materialWeight= (TextView) addView.findViewById(R.id.materialaddweight);
        materialName.setText(sMaterialName);
        materialWeight.setText(sMaterialWeight);
        Button deleteButton= (Button) addView.findViewById(R.id.materialdeletebutton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialLayout.removeView(addView);
                //todo 删除数据
            }
        });
        return addView;
    }

    private View createAccessoriesView(LayoutInflater inflater,ViewGroup container){
        final View addAccessoriesView=inflater.inflate(R.layout.accessories_additem_layout,container,false);
        TextView accessoriesAddName=(TextView)addAccessoriesView.findViewById(R.id.accessories_add_name);
        TextView accessoriesAddWeight=(TextView)addAccessoriesView.findViewById(R.id.accessories_add_weight);
        accessoriesAddName.setText(sAccessoriesName);
        accessoriesAddWeight.setText(sAccessoriesWeight);
        Button deleteButton= (Button) addAccessoriesView.findViewById(R.id.accessories_item_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessoriesLayout.removeView(addAccessoriesView);

            }
        });

        return addAccessoriesView;
    }

}
