package nju.zhizaolian.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import nju.zhizaolian.R;


public class CustomerEditFragment extends Fragment implements View.OnClickListener{

    private Button confirmButton;
    private Button cancelButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_add,container,false);
        confirmButton=(Button)view.findViewById(R.id.customer_register_button);
        cancelButton=(Button)view.findViewById(R.id.customer_cancel_button);
        confirmButton.setText("修改");
        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        return view;
    }

    public interface CustomerEditButtonClickListener{
        void customerEditButtonClicked();
    }

    public interface CustomerCancelButtonClickListener{
        void customerCancelButtonClicked();
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.customer_register_button:
                ((CustomerEditButtonClickListener) getActivity()).customerEditButtonClicked();
                break;
            case R.id.customer_cancel_button:
                ((CustomerCancelButtonClickListener) getActivity()).customerCancelButtonClicked();
                break;
        }
    }
}
