package nju.zhizaolian.fragments;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerRegisterFragment extends Fragment implements View.OnClickListener {

    private Button registerButton;
    private Button cancelButton;

    public CustomerRegisterFragment() {
        // Required empty public constructor
    }

    public interface CustomerRegisterButtonClickListener{
        void customerRegisterButtonClicked();
    }

    public interface CustomerCancelButtonClickListener{
        void customerCancelButtonClicked();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view= inflater.inflate(R.layout.customer_add,container,false);
        registerButton=(Button) view.findViewById(R.id.customer_register_button);
        cancelButton=(Button) view.findViewById(R.id.customer_cancel_button);
        registerButton.setText("注册");
        registerButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        return  view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.customer_register_button:
                ((CustomerRegisterButtonClickListener) getActivity()).customerRegisterButtonClicked();
                break;
            case R.id.customer_cancel_button:
                ((CustomerCancelButtonClickListener) getActivity()).customerCancelButtonClicked();
                break;
        }
    }
}
