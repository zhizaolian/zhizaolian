package nju.zhizaolian.fragments;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import nju.zhizaolian.R;

/**
 *
 */
public class CheckSampleBalanceFragment extends Fragment {
    TextView checkSampleNumberView;
    TextView checkSampleNumberUnitPriceView;
    TextView checkSampleReceivableMoneyView;
    EditText checkSampleRemitPersonEdit;
    EditText checkSampleRemitCardNumberEdit;
    EditText checkSampleRemitBankEdit;
    TextView checkSampleRemitMoneyView;
    TextView checkSampleReceiveMoneyTimeView;
    Spinner  checkSampleReceiveMoneyAccountSpinner;
    TextView checkSampleRemarkView;
    ImageView checkSampleImageView;
    Button checkSampleEnsureMoneyButton;
    Button checkSampleUnableMonryButton;
    public CheckSampleBalanceFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_check_sample_balance, container, false);
        checkSampleNumberView=(TextView)view.findViewById(R.id.check_sample_money_clothes_number_view);
        checkSampleNumberUnitPriceView=(TextView)view.findViewById(R.id.check_sample_money_unit_money_view);
        checkSampleReceivableMoneyView=(TextView)view.findViewById(R.id.check_sample_money_receivable_money_view);
        checkSampleRemitPersonEdit=(EditText)view.findViewById(R.id.check_sample_money_remit_person_edit);
        checkSampleRemitCardNumberEdit=(EditText)view.findViewById(R.id.check_sample_money_remit_card_number_edit);
        checkSampleRemitBankEdit=(EditText)view.findViewById(R.id.check_sample_money_remit_bank_edit);
        checkSampleRemitMoneyView=(TextView)view.findViewById(R.id.check_sample_money_remit_money_view);
        checkSampleReceiveMoneyTimeView=(TextView)view.findViewById(R.id.check_sample_money_receive_money_time_view);
        checkSampleReceiveMoneyAccountSpinner=(Spinner)view.findViewById(R.id.check_sample_money_account_spinner);
        checkSampleRemarkView=(TextView)view.findViewById(R.id.check_sample_money_remark_view);
        checkSampleImageView=(ImageView)view.findViewById(R.id.check_sample_money_image);
        checkSampleEnsureMoneyButton=(Button)view.findViewById(R.id.ensure_receive_sample_money_button);
        checkSampleEnsureMoneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        checkSampleUnableMonryButton=(Button)view.findViewById(R.id.unable_receive_sample_money_button);
        checkSampleUnableMonryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }


}
