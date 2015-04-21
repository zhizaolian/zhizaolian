package nju.zhizaolian.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

    public OrderProcessDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view=inflater.inflate(R.layout.fragment_order_process_detail, container, false);
        askNumberView= (TextView) view.findViewById(R.id.ask_number_view);
        askPeriodButton=(Button)view.findViewById(R.id.ask_period_button);
        askPeriodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment=new DatePickerFragment();
                datePickerFragment.show(getActivity().getFragmentManager(),"datePicker");

            }
        });

        askDeliverDateEdit=(EditText)view.findViewById(R.id.ask_deliver_date_edit);
        askDeliverDateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPeriodButton.setText("已选择时间:"+datePickerFragment.getDate());
            }
        });
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
                View newView;

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
        saveProcessDataButton=(Button)view.findViewById(R.id.save_process_data_button);
        produceLayout=(LinearLayout)view.findViewById(R.id.produce_add_layout);
        sampleLayout=(LinearLayout)view.findViewById(R.id.sample_add_layout);
        return view;
    }
        public View createProduceView(LayoutInflater inflater,ViewGroup container){
          View view=inflater.inflate(R.layout.produce_add_item_layout,container,false);


            return view;
        }


}
