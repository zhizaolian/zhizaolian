package nju.zhizaolian.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.help.DatePickerFragment;


public class OrderProcessDetailFragment extends android.support.v4.app.Fragment {

    private TextView askNumberView;
    private Button askPeriodButton;
    private EditText askDeliverDateEdit;
    private EditText produceColorEdit;
    private EditText produceXSEdit;
    private EditText produceSEdit;
    private EditText produceMEdit;
    private EditText produceLEdit;
    private EditText produceXLEdit;
    private EditText produceXXLEdit;
    private EditText produceJEdit;
    private Button  produceAddButton;
    private EditText sampleColorEdit;
    private EditText sampleXSEdit;
    private EditText sampleSEdit;
    private EditText sampleMEdit;
    private EditText sampleLEdit;
    private EditText sampleXLEdit;
    private EditText sampleXXLEdit;
    private EditText sampleJEdit;
    private Button sampleAddButton;
    private Button  saveProcessDataButton;
    private LinearLayout produceLayout;
    private LinearLayout sampleLayout;
    private DatePickerFragment datePickerFragment=null;
    private SaveProcessData saveProcessData;
    private TextView askPeriodView;
    ArrayList<String> produceColorArray=new ArrayList<String>();
    ArrayList<String> produceXSArray=new ArrayList<String>();
    ArrayList<String> produceSArray=new ArrayList<String>();
    ArrayList<String> produceMArray=new ArrayList<String>();
    ArrayList<String> produceLArray=new ArrayList<String>();
    ArrayList<String> produceXLArray=new ArrayList<String>();
    ArrayList<String> produceXXLArray=new ArrayList<String>();
    ArrayList<String> produceJArray=new ArrayList<String>();


    ArrayList<String> sampleColorArray=new ArrayList<String>();
    ArrayList<String> sampleXSArray=new ArrayList<String>();
    ArrayList<String> sampleSArray=new ArrayList<String>();
    ArrayList<String> sampleMArray=new ArrayList<String>();
    ArrayList<String> sampleLArray=new ArrayList<String>();
    ArrayList<String> sampleXLArray=new ArrayList<String>();
    ArrayList<String> sampleXXLArray=new ArrayList<String>();
    ArrayList<String> sampleJArray=new ArrayList<String>();



    public OrderProcessDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view=inflater.inflate(R.layout.fragment_order_process_detail, container, false);
        askNumberView= (TextView) view.findViewById(R.id.ask_number_view);

        askPeriodButton=(Button)view.findViewById(R.id.ask_period_button);
        askPeriodView=(TextView)view.findViewById(R.id.ask_period_view);
        askPeriodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment=new DatePickerFragment(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        super.onDateSet(view, year, monthOfYear, dayOfMonth);
                            askPeriodView.setText(year+"-"+monthOfYear+"-"+dayOfMonth);
                    }
                };

                datePickerFragment.show(getActivity().getFragmentManager(),"datePicker2");




            }
        });

        askDeliverDateEdit=(EditText)view.findViewById(R.id.ask_deliver_date_edit);

        produceColorEdit=(EditText)view.findViewById(R.id.produce_color_edit);
        produceXSEdit=(EditText)view.findViewById(R.id.produce_xs_edit);
        produceSEdit=(EditText)view.findViewById(R.id.produce_s_edit);
        produceMEdit=(EditText)view.findViewById(R.id.produce_m_edit);
        produceLEdit=(EditText)view.findViewById(R.id.produce_l_edit);
        produceXLEdit=(EditText)view.findViewById(R.id.produce_xl_edit);
        produceXXLEdit=(EditText)view.findViewById(R.id.produce_xxl_edit);
        produceJEdit=(EditText)view.findViewById(R.id.produce_j_edit);
        produceAddButton=(Button)view.findViewById(R.id.produce_add_button);
        produceAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(produceColorEdit.getText().length()==0||produceXSEdit.getText().length()==0||
                        produceSEdit.getText().length()==0||produceMEdit.getText().length()==0||
                        produceLEdit.getText().length()==0||produceXLEdit.getText().length()==0||produceXXLEdit.getText().length()==0||
                        produceJEdit.getText().length()==0){
                    Toast.makeText(getActivity().getApplicationContext(), "请把信息填写完整", Toast.LENGTH_SHORT).show();

                }else {
                    View newView= createProduceView(inflater,container);
                    produceLayout.addView(newView);
                    int produceXSNumber= Integer.parseInt(produceXSEdit.getText().toString());
                    int produceSNumber= Integer.parseInt(produceSEdit.getText().toString());
                    int produceMNumber=Integer.parseInt(produceMEdit.getText().toString());
                    int produceLNumber=Integer.parseInt(produceLEdit.getText().toString());
                    int produceXLNumber=Integer.parseInt(produceXLEdit.getText().toString());
                    int produceXXLNumber=Integer.parseInt(produceJEdit.getText().toString());
                    int produceJNumber=Integer.parseInt(produceJEdit.getText().toString());
                    int askNumber= Integer.parseInt(askNumberView.getText().toString());
                    int total=askNumber+produceXSNumber+produceSNumber+produceMNumber+produceLNumber+
                            produceXLNumber+produceXXLNumber+produceJNumber;
                    askNumberView.setText(String.valueOf(total));
                    produceColorArray.add(produceColorEdit.getText().toString());
                    produceXSArray.add(produceXSEdit.getText().toString());
                    produceSArray.add(produceSEdit.getText().toString());
                    produceMArray.add(produceMEdit.getText().toString());
                    produceLArray.add(produceLEdit.getText().toString());
                    produceXLArray.add(produceXLEdit.getText().toString());
                    produceXXLArray.add(produceXXLEdit.getText().toString());
                    produceJArray.add(produceJEdit.getText().toString());
                }

            }
        });

        sampleColorEdit=(EditText)view.findViewById(R.id.sample_color_edit);
        sampleXSEdit=(EditText)view.findViewById(R.id.sample_xs_edit);
        sampleSEdit=(EditText)view.findViewById(R.id.sample_s_edit);
        sampleMEdit=(EditText)view.findViewById(R.id.sample_m_edit);
        sampleLEdit=(EditText)view.findViewById(R.id.sample_l_edit);
        sampleXLEdit=(EditText)view.findViewById(R.id.sample_xl_edit);
        sampleXXLEdit=(EditText)view.findViewById(R.id.sample_xxl_edit);
        sampleJEdit=(EditText)view.findViewById(R.id.sample_j_edit);
        sampleAddButton=(Button)view.findViewById(R.id.sample_add_button);
        sampleAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sampleColorEdit.getText().length()==0||sampleXSEdit.getText().length()==0||
                        sampleSEdit.getText().length()==0||sampleMEdit.getText().length()==0||
                        sampleLEdit.getText().length()==0||sampleXLEdit.getText().length()==0||sampleXXLEdit.getText().length()==0||
                        sampleJEdit.getText().length()==0){
                    Toast.makeText(getActivity().getApplicationContext(), "请把信息填写完整", Toast.LENGTH_SHORT).show();

                }else {
                    View newView= createSampleView(inflater, container);
                    sampleLayout.addView(newView);

                    sampleColorArray.add(sampleColorEdit.getText().toString());
                    sampleXSArray.add(sampleXSEdit.getText().toString());
                    sampleSArray.add(sampleSEdit.getText().toString());
                    sampleMArray.add(sampleMEdit.getText().toString());
                    sampleLArray.add(sampleLEdit.getText().toString());
                    sampleXLArray.add(sampleXLEdit.getText().toString());
                    sampleXXLArray.add(sampleXXLEdit.getText().toString());
                    sampleJArray.add(sampleJEdit.getText().toString());
                }
            }
        });



        saveProcessDataButton=(Button)view.findViewById(R.id.save_process_data_button);
        saveProcessDataButton.setOnClickListener(new SaveDataListener());


        produceLayout=(LinearLayout)view.findViewById(R.id.produce_add_layout);
        sampleLayout=(LinearLayout)view.findViewById(R.id.sample_add_layout);
        return view;
    }
        public View createProduceView(final LayoutInflater inflater,ViewGroup container){
            final View view=inflater.inflate(R.layout.produce_add_item_layout,container,false);
            final TextView produceColorAddView= (TextView) view.findViewById(R.id.produce_add_color_view);
            TextView produceXSAddView=(TextView)view.findViewById(R.id.produce_xs_add_view);
            TextView produceSAddView=(TextView)view.findViewById(R.id.produce_s_add_view);
            TextView produceMAddView=(TextView)view.findViewById(R.id.produce_m_add_view);
            TextView produceLAddView=(TextView)view.findViewById(R.id.produce_l_add_view);
            TextView produceXLAddView=(TextView)view.findViewById(R.id.produce_xl_add_view);
            TextView produceXXLAddView=(TextView)view.findViewById(R.id.produce_xxl_add_view);
            TextView produceJAddView=(TextView)view.findViewById(R.id.produce_j_add_view);
            Button produceAddDeleteButton=(Button)view.findViewById(R.id.produce_add_delete_button);
            produceAddDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    produceLayout.removeView(view);
                    int i=produceColorArray.indexOf(produceColorAddView.getText().toString());
                    int removeNumber=Integer.parseInt(produceXSArray.get(i).toString())+Integer.parseInt(produceSArray.get(i).toString())+
                            Integer.parseInt(produceMArray.get(i).toString())+Integer.parseInt(produceLArray.get(i).toString())+
                            Integer.parseInt(produceXLArray.get(i).toString())+Integer.parseInt(produceXXLArray.get(i).toString())+Integer.parseInt(produceJArray.get(i).toString());
                    int current= Integer.parseInt(askNumberView.getText().toString());
                    int total=current-removeNumber;
                    askNumberView.setText(String.valueOf(total));

                    produceColorArray.remove(i);
                    produceXSArray.remove(i);
                    produceSArray.remove(i);
                    produceMArray.remove(i);
                    produceLArray.remove(i);
                    produceXLArray.remove(i);
                    produceXXLArray.remove(i);
                    produceJArray.remove(i);
                }
            });
            produceColorAddView.setText(produceColorEdit.getText());
            produceXSAddView.setText(produceXSEdit.getText());
            produceSAddView.setText(produceSEdit.getText());
            produceMAddView.setText(produceMEdit.getText());
            produceLAddView.setText(produceLEdit.getText());
            produceXLAddView.setText(produceXLEdit.getText());
            produceXXLAddView.setText(produceXXLEdit.getText());
            produceJAddView.setText(produceJEdit.getText());
            return view;
        }
        public View createSampleView(LayoutInflater inflater,ViewGroup container){
            final View view=inflater.inflate(R.layout.sample_add_item_layout,container,false);
            final TextView sampleColorAddView= (TextView) view.findViewById(R.id.sample_add_color_view);
            TextView sampleXSAddView=(TextView)view.findViewById(R.id.sample_xs_add_view);
            TextView sampleSAddView=(TextView)view.findViewById(R.id.sample_s_add_view);
            TextView sampleMAddView=(TextView)view.findViewById(R.id.sample_m_add_view);
            TextView sampleLAddView=(TextView)view.findViewById(R.id.sample_l_add_view);
            TextView sampleXLAddView=(TextView)view.findViewById(R.id.sample_xl_add_view);
            TextView sampleXXLAddView=(TextView)view.findViewById(R.id.sample_xxl_add_view);
            TextView sampleJAddView=(TextView)view.findViewById(R.id.sample_j_add_view);
            Button sampleAddDeleteButton=(Button)view.findViewById(R.id.sample_add_delete_button);
            sampleAddDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sampleLayout.removeView(view);
                    int j=sampleColorArray.indexOf(sampleColorAddView.getText().toString());
                    sampleColorArray.remove(j);
                    sampleXSArray.remove(j);
                    sampleSArray.remove(j);
                    sampleMArray.remove(j);
                    sampleLArray.remove(j);
                    sampleXLArray.remove(j);
                    sampleXXLArray.remove(j);
                    sampleJArray.remove(j);

                }
            });
            sampleColorAddView.setText(sampleColorEdit.getText());
            sampleXSAddView.setText(sampleXSEdit.getText());
            sampleSAddView.setText(sampleSEdit.getText());
            sampleMAddView.setText(sampleMEdit.getText());
            sampleLAddView.setText(sampleLEdit.getText());
            sampleXLAddView.setText(sampleXLEdit.getText());
            sampleXXLAddView.setText(sampleXXLEdit.getText());
            sampleJAddView.setText(sampleJEdit.getText());

            return view;
        }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            saveProcessData= (SaveProcessData) activity;
        }catch (ClassCastException e){
            Toast.makeText(getActivity().getApplicationContext(),"保存失败",Toast.LENGTH_SHORT).show();
        }

    }

    public interface SaveProcessData{
        public void saveProcessData(String processData);
    }



    private class SaveDataListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            String askDeliverDateData=askPeriodView.getText().toString();
            String askNumberData=askNumberView.getText().toString();
            Log.d("asknumber",askNumberData);
            String askDeliverPeriodData=askDeliverDateEdit.getText().toString();
            if(produceColorArray.size()==0 || sampleColorArray.size()==0||askDeliverPeriodData.length()==0||askNumberData.length()==0){
                Toast.makeText(getActivity().getApplicationContext(), "请把信息填写完整", Toast.LENGTH_SHORT).show();
            }else{
                 String produceColor=null;
                 String produceXS=null;
                 String produceS=null;
                 String produceM=null;
                 String produceL=null;
                 String produceXL=null;
                 String produceXXL=null;
                 String produceJ=null;
                 String sampleProduceColor=null;
                 String sampleProduceXS=null;
                 String sampleProduceS=null;
                 String sampleProduceM=null;
                 String sampleProduceL=null;
                 String sampleProduceXL=null;
                 String sampleProduceXXL=null;
                 String sampleProduceJ=null;
                for(String s:produceColorArray){
                    if(produceColor==null){
                        produceColor=s;
                    }else {
                        produceColor+=s+",";
                    }

                }
                for(String s:produceXSArray){
                    if(produceXS==null){
                        produceXS=s;
                    }else {
                        produceXS+=s+",";
                    }

                }
                for(String s:produceSArray){
                    if(produceS==null){
                        produceS=s;
                    }
                    produceS+=s+",";
                }
                for(String s:produceMArray){
                    if(produceM==null){
                        produceM=s;
                    }
                    produceM+=s+",";
                }
                for (String s: produceLArray){
                    if(produceL==null){
                        produceL=s;
                    }
                    produceL+=s+",";
                }
                for(String s:produceXLArray){
                    if(produceXL==null){
                        produceXL=s;
                    }
                    produceXL+=s+",";
                }
                for(String s:produceXXLArray){
                    if(produceXXL==null){
                        produceXXL=s;
                    }
                    produceXXL+=s+",";
                }
                for(String s:produceJArray){
                    if(produceJ==null){
                        produceJ=s;
                    }
                    produceJ+=s+",";
                }

                for(String s:sampleColorArray){
                    if(sampleProduceColor==null){
                        sampleProduceColor=s;
                    }
                    sampleProduceColor+=s+",";
                }
                for(String s:sampleXSArray){
                    if(sampleProduceXS==null){
                        sampleProduceXS=s;
                    }
                    sampleProduceXS+=s+",";
                }
                for(String s:sampleSArray){
                    if(sampleProduceS==null){
                        sampleProduceS=s;
                    }
                    sampleProduceS+=s+",";
                }
                for(String s:sampleMArray){
                    if(sampleProduceM==null){
                        sampleProduceM=s;
                    }
                    sampleProduceM+=s+",";
                }
                for (String s: sampleLArray){
                    if(sampleProduceL==null){
                        sampleProduceL=s;
                    }
                    sampleProduceL+=s+",";
                }
                for(String s:sampleXLArray){
                    if(sampleProduceXL==null){
                        sampleProduceXL=s;
                    }
                    sampleProduceXL+=s+",";
                }
                for(String s:sampleXXLArray){
                    if(sampleProduceXXL==null){
                        sampleProduceXXL=s;
                    }
                    sampleProduceXXL+=s+",";
                }
                for(String s:sampleJArray){
                    if(sampleProduceJ==null){
                        sampleProduceJ=s;
                    }
                    sampleProduceJ+=s+",";
                }
                String processData=askNumberData+"@"+askDeliverDateData+"@"+askDeliverPeriodData+"@"+produceColor+"@"+produceXS+"@"+
                        produceS+"@"+produceM+"@"+produceL+"@"+produceXL+"@"+produceXXL+"@"+
                        produceJ+"@"+sampleProduceColor+"@"+sampleProduceXS+"@"+sampleProduceS+"@"+sampleProduceM+"@"+sampleProduceL+"@"
                        +sampleProduceXL+"@"+sampleProduceXXL+"@"+sampleProduceJ;
                saveProcessData.saveProcessData(processData);
                Log.d("saveprocesdata",processData);
            }


        }
    }


}
