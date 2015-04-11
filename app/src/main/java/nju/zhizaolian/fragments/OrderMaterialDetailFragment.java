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


    Editable smaterialName=null;
    Editable smaterialWeight=null;
    Editable saccessoriesName=null;
    Editable saccessoriesWeight=null;
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
        materiallayout= (LinearLayout) view.findViewById(R.id.materialLayout);
        accessoriesLayout=(LinearLayout)view.findViewById(R.id.accessoriesLayout);
        materialName= (EditText) view.findViewById(R.id.materialName);
        materialWeight=(EditText) view.findViewById(R.id.materialWeight);
        accessoriesName=(EditText)view.findViewById(R.id.accessoriesName);
        accessoresWeight=(EditText)view.findViewById(R.id.accessoriesWeight);


        smaterialName=materialName.getText();
        smaterialWeight=materialWeight.getText();
        saccessoriesName=accessoriesName.getText();
        saccessoriesWeight=accessoresWeight.getText();


        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(smaterialName.length()==0 || smaterialWeight.length()==0){
                    Toast.makeText(view.getContext(),"请把信息填写完整",Toast.LENGTH_SHORT).show();
                }else{
                   View newView=createMaterailView(inflater,container);
                    materiallayout.addView(newView);


                }

            }
        });

        accessoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(saccessoriesName.length()==0||saccessoriesWeight.length()==0){
                    Toast.makeText(view.getContext(),"请把信息填写完整",Toast.LENGTH_SHORT).show();
                }else{
                    TextView textView =new TextView(container.getContext());
                    textView.setText("辅料名称:"+saccessoriesName+"辅料克重:"+saccessoriesWeight);
                    accessoriesLayout.addView(textView);
                    //todo 添加数据
                }
            }
        });
        return view;
    }


    private View createMaterailView(LayoutInflater inflater,ViewGroup container){
        final View addview=inflater.inflate(R.layout.material_additem_layout,container,false);
        TextView materialname= (TextView) addview.findViewById(R.id.materialaddname);
        TextView materialweight= (TextView) addview.findViewById(R.id.materialaddweight);
        materialname.setText(smaterialName);
        materialweight.setText(smaterialWeight);
        Button deletebutton= (Button) addview.findViewById(R.id.materialdeletebutton);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materiallayout.removeView(addview);
                //todo 删除数据
            }
        });
        return addview;
    }



}
