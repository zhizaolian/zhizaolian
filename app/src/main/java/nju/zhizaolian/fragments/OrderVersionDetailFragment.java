package nju.zhizaolian.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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


public class OrderVersionDetailFragment extends Fragment {

    private EditText versionSizeEdit;
    private EditText versionCenterBackLengthEdit;
    private EditText versionBustEdit;
    private EditText versionWaistLineEdit;
    private EditText versionShoulderEdit;
    private EditText versionButtockEdit;
    private EditText versionHemEdit;
    private EditText versionTrousersEdit;
    private EditText versionSkirtEdit;
    private EditText versionSleevesEdit;
    private Button versionAddButton;
    private Button versionSaveButton;
    private LinearLayout versionAddLayout;
    ArrayList<String> versionSizeArray=new ArrayList<String>();
    ArrayList<String> versionCenterBackLengthArray=new ArrayList<String>();
    ArrayList<String> versionBustArray=new ArrayList<String>();
    ArrayList<String> versionWaistLineArray=new ArrayList<String>();
    ArrayList<String> versionShoulderArray=new ArrayList<String>();
    ArrayList<String> versionButtockArray=new ArrayList<String>();
    ArrayList<String> versionHemArray=new ArrayList<String>();
    ArrayList<String> versionTrousersArray=new ArrayList<String>();
    ArrayList<String> versionSkirtArray=new ArrayList<String>();
    ArrayList<String> versionSleevesArray=new ArrayList<String>();

    private SaveVerisonData saveVerisonData;



    public OrderVersionDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order_version_detail, container, false);
        versionSizeEdit= (EditText) view.findViewById(R.id.version_size_edit);
        versionCenterBackLengthEdit=(EditText)view.findViewById(R.id.version_center_back_length_Edit);
        versionBustEdit=(EditText)view.findViewById(R.id.version_bust_edit);
        versionWaistLineEdit=(EditText)view.findViewById(R.id.version_waist_line);
        versionShoulderEdit=(EditText)view.findViewById(R.id.version_shoulder_edit);
        versionButtockEdit=(EditText)view.findViewById(R.id.version_buttock_edit);
        versionHemEdit=(EditText)view.findViewById(R.id.version_Hem_edit);
        versionTrousersEdit=(EditText)view.findViewById(R.id.version_trousers_edit);
        versionSkirtEdit=(EditText)view.findViewById(R.id.version_skirt_edit);
        versionSleevesEdit=(EditText)view.findViewById(R.id.version_sleeve_edit);

        versionAddButton=(Button)view.findViewById(R.id.version_add_button);
        versionAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(versionSizeEdit.getText().length()==0||versionCenterBackLengthEdit.getText().length()==0||versionBustEdit.getText().length()==0||
                        versionWaistLineEdit.getText().length()==0|| versionShoulderEdit.getText().length()==0||versionButtockEdit.getText().length()==0||
                        versionHemEdit.getText().length()==0||versionTrousersEdit.getText().length()==0||versionSkirtEdit.getText().length()==0||versionSleevesEdit.getText().length()==0){

                    Toast.makeText(getActivity().getApplicationContext(), "请填写完整信息", Toast.LENGTH_SHORT).show();
                }else{

                    View addView=createVersionAddView(inflater,container);
                    versionAddLayout.addView(addView);

                    versionSizeArray.add(versionSizeEdit.getText().toString());
                    versionCenterBackLengthArray.add(versionCenterBackLengthEdit.getText().toString());
                    versionBustArray.add(versionBustEdit.getText().toString());
                    versionWaistLineArray.add(versionWaistLineEdit.getText().toString());
                    versionShoulderArray.add(versionShoulderEdit.getText().toString());
                    versionButtockArray.add(versionButtockEdit.getText().toString());
                    versionHemArray.add(versionHemEdit.getText().toString());
                    versionTrousersArray.add(versionTrousersEdit.getText().toString());
                    versionSkirtArray.add(versionSkirtEdit.getText().toString());
                    versionSleevesArray.add(versionSleevesEdit.getText().toString());
                }




            }
        });


        versionSaveButton=(Button)view.findViewById(R.id.save_version_data);
        versionSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String versionSizeData=null;
                String versionCenterBackLengthData=null;
                String versionBustData=null;
                String versionWaistLineData=null;
                String versionShoulderData=null;
                String versionButtockData=null;
                String versionHemData=null;
                String versionTrousersData=null;
                String versionSkirtData=null;
                String versionSleevesData=null;
                if(versionSizeArray.size()==0){
                     versionSizeData="0";
                     versionCenterBackLengthData="0";
                     versionBustData="0";
                     versionWaistLineData="0";
                     versionShoulderData="0";
                     versionButtockData="0";
                     versionHemData="0";
                     versionTrousersData="0";
                     versionSkirtData="0";
                     versionSleevesData="0";
                }else{
                    for(String s:versionSizeArray){
                        if (versionSizeData==null){
                            versionSizeData=s+",";
                        }else {
                            versionSizeData+=s+",";
                        }
                    }
                    for(String s:versionCenterBackLengthArray){
                        if (versionCenterBackLengthData==null){
                            versionCenterBackLengthData=s+",";
                        }else {
                            versionCenterBackLengthData+=s+",";
                        }
                    }
                    for(String s:versionBustArray){
                        if (versionBustData==null){
                            versionBustData=s+",";
                        }else {
                            versionBustData+=s+",";
                        }
                    }
                    for(String s:versionWaistLineArray){
                        if (versionWaistLineData==null){
                            versionWaistLineData=s+",";
                        }else {
                            versionWaistLineData+=s+",";
                        }
                    }
                    for(String s:versionShoulderArray){
                        if (versionShoulderData==null){
                            versionShoulderData=s+",";
                        }else {
                            versionShoulderData+=s+",";
                        }
                    }
                    for(String s:versionButtockArray){
                        if (versionButtockData==null){
                            versionButtockData=s+",";
                        }else {
                            versionButtockData+=s+",";
                        }
                    }
                    for(String s:versionHemArray){
                        if (versionHemData==null){
                            versionHemData=s+",";
                        }else {
                            versionHemData+=s+",";
                        }
                    }
                    for(String s:versionTrousersArray){
                        if (versionTrousersData==null){
                            versionTrousersData=s+",";
                        }else {
                            versionTrousersData+=s+",";
                        }
                    }
                    for(String s:versionSkirtArray){
                        if (versionSkirtData==null){
                            versionSkirtData=s+",";
                        }else {
                            versionSkirtData+=s+",";
                        }
                    }
                    for(String s:versionSleevesArray){
                        if (versionSleevesData==null){
                            versionSleevesData=s+",";
                        }else {
                            versionSleevesData+=s+",";
                        }
                    }
                }

                String data=versionSizeData+"@"+versionCenterBackLengthData+"@"+versionBustData+"@"+versionWaistLineData+"@"+versionShoulderData+"@"+
                        versionButtockData+"@"+versionHemData+"@"+versionTrousersData+"@"+versionSkirtData+"@"+versionSleevesData;
                saveVerisonData.saveVersionData(data);

            }
        });


        versionAddLayout=(LinearLayout)view.findViewById(R.id.version_add_layout);



        return view;
    }



    public View createVersionAddView(LayoutInflater inflater,ViewGroup container){
        final View newview=inflater.inflate(R.layout.version_data,container,false);
        final TextView versionSizeView=(TextView)newview.findViewById(R.id.version_size_view);
        TextView versionCenterBackLengthView=(TextView)newview.findViewById(R.id.version_center_back_length_view);
        TextView versionBustView=(TextView)newview.findViewById(R.id.version_bust_view);
        TextView versionWaistLineView=(TextView)newview.findViewById(R.id.version_waist_line_view);
        TextView versionShoulderView=(TextView)newview.findViewById(R.id.version_shoulder_view);
        TextView versionButtockView =(TextView)newview.findViewById(R.id.version_buttock_view);
        TextView versionHemView=(TextView)newview.findViewById(R.id.version_Hem_view);
        TextView versionTrousersView=(TextView)newview.findViewById(R.id.version_trousers_view);
        TextView versionSkirtView=(TextView)newview.findViewById(R.id.version_skirt_view);
        TextView versionSleevesView=(TextView)newview.findViewById(R.id.version_sleeve_view);
        Button  versionDeleteButton=(Button)newview.findViewById(R.id.version_add_delete_button);
        versionDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                versionAddLayout.removeView(newview);
                int t=versionSizeArray.indexOf(versionSizeView.getText().toString());
                versionCenterBackLengthArray.remove(t);
                versionBustArray.remove(t);
                versionWaistLineArray.remove(t);
                versionShoulderArray.remove(t);
                versionButtockArray.remove(t);
                versionHemArray.remove(t);
                versionTrousersArray.remove(t);
                versionSkirtArray.remove(t);
                versionSleevesArray.remove(t);

            }
        });
        versionSizeView.setText(versionSizeEdit.getText());
        versionCenterBackLengthView.setText(versionCenterBackLengthEdit.getText());
        versionBustView.setText(versionBustEdit.getText());
        versionWaistLineView.setText(versionWaistLineEdit.getText());
        versionShoulderView.setText(versionShoulderEdit.getText());
        versionButtockView.setText(versionBustEdit.getText());
        versionHemView.setText(versionHemEdit.getText());
        versionTrousersView.setText(versionTrousersEdit.getText());
        versionSkirtView.setText(versionSkirtEdit.getText());
        versionSleevesView.setText(versionSleevesEdit.getText());
   return newview;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        saveVerisonData= (SaveVerisonData) activity;
    }

    public interface SaveVerisonData{
        public void saveVersionData(String versionData);
    }

}
