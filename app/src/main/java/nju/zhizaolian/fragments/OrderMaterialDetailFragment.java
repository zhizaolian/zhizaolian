package nju.zhizaolian.fragments;

import android.app.Activity;
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

    private SaveFabricData saveFabricData;

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
        materialSaveButton.setOnClickListener(new saveMaterialListener());
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
                    fabricName.add(materialNameEdit.getText().toString());
                    fabricWeight.add(materialWeightEdit.getText().toString());

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
                    accessoriesName.add(accessoriesNameEdit.getText().toString());
                    accessoriesWeight.add(accessoriesWeightEdit.getText().toString());
                }
            }
        });
        return view;
    }


    private View createMaterialView(LayoutInflater inflater,ViewGroup container){
        final View addView=inflater.inflate(R.layout.material_additem_layout,container,false);
        final TextView materialName= (TextView) addView.findViewById(R.id.materialaddname);
        TextView materialWeight= (TextView) addView.findViewById(R.id.materialaddweight);
        materialName.setText(sMaterialName);
        materialWeight.setText(sMaterialWeight);
        Button deleteButton= (Button) addView.findViewById(R.id.materialdeletebutton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialLayout.removeView(addView);
                int i=fabricName.indexOf(materialName.getText().toString());
                fabricName.remove(i);
                fabricWeight.remove(i);
            }
        });
        return addView;
    }

    private View createAccessoriesView(LayoutInflater inflater,ViewGroup container){
        final View addAccessoriesView=inflater.inflate(R.layout.accessories_additem_layout,container,false);
        final TextView accessoriesAddName=(TextView)addAccessoriesView.findViewById(R.id.accessories_add_name);
        TextView accessoriesAddWeight=(TextView)addAccessoriesView.findViewById(R.id.accessories_add_weight);
        accessoriesAddName.setText(sAccessoriesName);
        accessoriesAddWeight.setText(sAccessoriesWeight);
        Button deleteButton= (Button) addAccessoriesView.findViewById(R.id.accessories_item_delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessoriesLayout.removeView(addAccessoriesView);
                int j=accessoriesName.indexOf(accessoriesAddName.getText().toString());
                accessoriesName.remove(j);
                accessoriesWeight.remove(j);

            }
        });

        return addAccessoriesView;
    }
    public class saveMaterialListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {

            String fabricNameData=null;
            String fabricWeightData=null;
            String accessoriesNameData=null;
            String accessoriesWeightData=null;
            for(String s:fabricName){
                if(fabricNameData==null){

                        fabricNameData=s+",";


                }else{
                    fabricNameData+=s+",";
                }
            }
            for(String s:fabricWeight){
                if(fabricWeightData==null){
                        fabricWeightData=s+",";
                }else{
                    fabricWeightData+=s+",";
                }
            }
            for(String s:accessoriesName){
                if(accessoriesNameData==null){

                        accessoriesNameData=s+",";


                }else {
                    accessoriesNameData+=s+",";
                }
            }
            for(String s:accessoriesWeight){
                if(accessoriesWeightData==null){

                        accessoriesWeightData=s+",'";


                }else{
                    accessoriesWeightData+=s+",";
                }
            }
            if(fabricNameData == null){
                fabricNameData="0";
            }
            if(fabricWeightData == null){
                fabricWeightData="0";
            }
            if(accessoriesNameData == null){
                accessoriesNameData="0";
            }
            if(accessoriesWeightData == null){
                accessoriesWeightData="0";
            }
           String result=fabricNameData+"@"+fabricWeightData+"@"+accessoriesNameData+"@"+accessoriesWeightData;

           saveFabricData.saveFabricData(result);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        saveFabricData= (SaveFabricData) activity;
    }

    public interface SaveFabricData{
        public void saveFabricData(String fabricdata);
    }
}
