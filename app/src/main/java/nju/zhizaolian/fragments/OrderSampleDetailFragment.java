package nju.zhizaolian.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import nju.zhizaolian.R;
import nju.zhizaolian.help.DatePickerFragment;


public class OrderSampleDetailFragment extends android.support.v4.app.Fragment {

    private Switch ifProvideSample;
    private Button expressTime;
    private Spinner expressName;
    private EditText expressNumber;
    private Switch ifMakeSample;
    private EditText postMan;
    private EditText postManPhone;
    private EditText expressAddress;
    private EditText otherRemark;
    private Button selectSamplePicture;
    private Button selectReferencePicture;
    private ImageView samplePicture;
    private ImageView referencePicture;
    public OrderSampleDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order_sample_detail, container, false);
        ifProvideSample= (Switch) view.findViewById(R.id.isProvideSampleSwitch);
        expressTime=(Button)view.findViewById(R.id.expressTimeButton);
        expressTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePickerFragment=DatePickerFragment.newInstance();
            }
        });

        return view;
    }


}
