package nju.zhizaolian.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.adapters.VersionAdapter;
import nju.zhizaolian.models.VersionData;


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
    private ListView versionDataListView;
    private VersionAdapter versionAdapter;
    private EditText cadFabricEdit;
    private EditText cadPackageEdit;
    private EditText cadVersionEdit;
    private EditText cadBoxingEdit;
    private EditText cadTechnologyEdit;
    private EditText cadOtherEdit;



    ArrayList<VersionData> versionDatas=new ArrayList<VersionData>();

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
        versionSaveButton=(Button)view.findViewById(R.id.save_version_data);
        versionAddButton=(Button)view.findViewById(R.id.version_add_button);
        versionDataListView=(ListView)view.findViewById(R.id.order_version_list_view);
        versionAdapter=new VersionAdapter(container.getContext(),0,versionDatas);
        versionDataListView.setAdapter(versionAdapter);

        cadFabricEdit=(EditText)view.findViewById(R.id.order_cad_fabric_edit);
        cadPackageEdit=(EditText)view.findViewById(R.id.order_cad_package_edit);
        cadVersionEdit=(EditText)view.findViewById(R.id.order_cad_version_edit);
        cadBoxingEdit=(EditText)view.findViewById(R.id.order_boxing_edit);
        cadTechnologyEdit=(EditText)view.findViewById(R.id.order_technology_edit);
        cadOtherEdit=(EditText)view.findViewById(R.id.order_cad_other_edit);


        versionAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(versionSizeEdit.getText().length()==0||versionCenterBackLengthEdit.getText().length()==0||versionBustEdit.getText().length()==0||
                        versionWaistLineEdit.getText().length()==0|| versionShoulderEdit.getText().length()==0||versionButtockEdit.getText().length()==0||
                        versionHemEdit.getText().length()==0||versionTrousersEdit.getText().length()==0||versionSkirtEdit.getText().length()==0||versionSleevesEdit.getText().length()==0){

                    Toast.makeText(getActivity().getApplicationContext(), "请填写完整信息", Toast.LENGTH_SHORT).show();
                }else{

                    VersionData versionData=new VersionData();
                    versionData.setSize(versionSizeEdit.getText().toString());
                    versionData.setBust(versionBustEdit.getText().toString());
                    versionData.setButtock(versionButtockEdit.getText().toString());
                    versionData.setCenterBackLength(versionCenterBackLengthEdit.getText().toString());
                    versionData.setHem(versionHemEdit.getText().toString());
                    versionData.setShoulder(versionShoulderEdit.getText().toString());
                    versionData.setSkirt(versionSkirtEdit.getText().toString());
                    versionData.setWaistline(versionWaistLineEdit.getText().toString());
                    versionData.setTrousers(versionTrousersEdit.getText().toString());
                    versionData.setSleeves(versionSleevesEdit.getText().toString());
                    versionDatas.add(versionData);
                    versionAdapter.notifyDataSetChanged();
                }

            }
        });



        versionSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(versionDatas.size() == 0 ||cadFabricEdit.getText().length() == 0 ||cadPackageEdit.getText().length() == 0||
                        cadVersionEdit.getText().length() == 0 ||cadBoxingEdit.getText().length() == 0 ||cadTechnologyEdit.getText().length()  == 0||
                        cadOtherEdit.getText().length() == 0){
                    Toast.makeText(getActivity(),"请填写完整信息",Toast.LENGTH_SHORT).show();
                }else {
                    String versionSizeData="0";
                    String versionCenterBackLengthData="0";
                    String versionBustData="0";
                    String versionWaistLineData="0";
                    String versionShoulderData="0";
                    String versionButtockData="0";
                    String versionHemData="0";
                    String versionTrousersData="0";
                    String versionSkirtData="0";
                    String versionSleevesData="0";
                    for(VersionData version:versionDatas){
                        versionSizeData+=version.getSize()+",";
                        versionCenterBackLengthData+=version.getCenterBackLength()+",";
                        versionBustData+=version.getBust()+",";
                        versionWaistLineData+=version.getWaistline()+",";
                        versionShoulderData+=version.getShoulder()+",";
                        versionButtockData+=version.getButtock()+",";
                        versionHemData+=version.getHem()+",";
                        versionTrousersData+=version.getTrousers()+",";
                        versionSkirtData+=version.getSkirt()+",";
                        versionSleevesData+=version.getSleeves()+",";
                    }
                    String cadFabricData=cadFabricEdit.getText().toString();
                    String cadPackageData=cadPackageEdit.getText().toString();
                    String cadVersionData=cadVersionEdit.getText().toString();
                    String cadBoxingData=cadBoxingEdit.getText().toString();
                    String cadTechnologyData=cadTechnologyEdit.getText().toString();
                    String cadOtherData=cadOtherEdit.getText().toString();


                    String data=versionSizeData+"@"+versionCenterBackLengthData+"@"+versionBustData+"@"+versionWaistLineData+"@"+versionShoulderData+"@"+
                            versionButtockData+"@"+versionHemData+"@"+versionTrousersData+"@"+versionSkirtData+"@"+versionSleevesData+"@"+cadFabricData+"@"+
                            cadPackageData+"@"+cadVersionData+"@"+cadBoxingData+"@"+cadTechnologyData+"@"+cadOtherData;
                    saveVerisonData.saveVersionData(data);
                }


            }
        });


        versionDataListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                VersionData versionData= (VersionData) versionDataListView.getItemAtPosition(position);
                versionDatas.remove(versionData);
                versionAdapter.notifyDataSetChanged();

                return false;
            }
        });



        return view;
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
