package nju.zhizaolian.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import nju.zhizaolian.R;
import nju.zhizaolian.models.CustomerManagerInfo;


public class CustomerEditFragment extends Fragment implements View.OnClickListener{

    private Button confirmButton;
    private Button cancelButton;
    private EditText customer_login_name_edit_text;
    private EditText customer_name_edit_text;
    private EditText customer_password_edit_text;
    private EditText customer_telephone_edit_text;
    private EditText customer_company_name_edit_text;
    private EditText customer_purchase_contacts_name_edit_text;
    private EditText customer_purchase_contacts_telephone_edit_text;
    private EditText customer_register_date_edit_text;
    Handler handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_add,container,false);
        confirmButton=(Button)view.findViewById(R.id.customer_register_button);
        cancelButton=(Button)view.findViewById(R.id.customer_cancel_button);
        confirmButton.setText("修改");
        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        customer_login_name_edit_text=(EditText) view.findViewById(R.id.customer_login_name_edit_text);
        customer_name_edit_text=(EditText) view.findViewById(R.id.customer_name_edit_text);
        customer_password_edit_text=(EditText) view.findViewById(R.id.customer_password_edit_text);
        customer_telephone_edit_text=(EditText) view.findViewById(R.id.customer_telephone_edit_text);
        customer_company_name_edit_text=(EditText) view.findViewById(R.id.customer_company_name_edit_text);
        customer_purchase_contacts_name_edit_text=(EditText) view.findViewById(R.id.customer_purchase_contacts_name_edit_text);
        customer_purchase_contacts_telephone_edit_text=(EditText) view.findViewById(R.id.customer_purchase_contacts_telephone_edit_text);
        customer_register_date_edit_text=(EditText) view.findViewById(R.id.customer_register_date_edit_text);
        final CustomerManagerInfo info = (CustomerManagerInfo)getArguments().get("info");
        handler= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==101)
                notifyEditTextSetByCustomerInfo(info);
            }
        };
        Message message = new Message();
        message.what=101;
        handler.sendMessage(message);

        return view;
    }

    public interface CustomerEditButtonClickListener{
        void customerEditButtonClicked(CustomerManagerInfo info);
    }

    public interface CustomerCancelButtonClickListener{
        void customerCancelButtonClicked();
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.customer_register_button:
                String password_new =customer_password_edit_text.getText().toString();
                if(password_new!=null&&password_new.length()>0){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("检测到你更改了密码")
                            .setMessage("确认继续？\n将密码栏保持空即可不修改密码")
                            .setPositiveButton("嗯(⊙_⊙)",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CustomerManagerInfo info= new CustomerManagerInfo();
                                    setInfo(info);
                                    dialog.cancel();
                                  ((CustomerEditButtonClickListener) getActivity()).customerEditButtonClicked(info);
                                }
                            })
                            .setNegativeButton("不(>_<)", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .show();
                }else {
                    CustomerManagerInfo info = new CustomerManagerInfo();
                    setInfo(info);
                    ((CustomerEditButtonClickListener) getActivity()).customerEditButtonClicked(info);
                }
                break;
            case R.id.customer_cancel_button:
                ((CustomerCancelButtonClickListener) getActivity()).customerCancelButtonClicked();
                break;
        }
    }

    public void notifyEditTextSetByCustomerInfo(CustomerManagerInfo info){
        customer_login_name_edit_text.setText(info.getUser_name());
        customer_login_name_edit_text.setEnabled(false);
        customer_name_edit_text.setText(info.getCustomer_name());
        customer_password_edit_text.setText("");
        customer_telephone_edit_text.setText(info.getCustomer_phone());
        customer_company_name_edit_text.setText(info.getCompany_name());
        customer_purchase_contacts_name_edit_text.setText(info.getBuy_contact());
        customer_purchase_contacts_telephone_edit_text.setText(info.getContact_phone_1());
        customer_register_date_edit_text.setHint("注册时间不支持编辑");
        customer_register_date_edit_text.setEnabled(false);
        customer_register_date_edit_text.setVisibility(View.INVISIBLE);
    }

    public void setInfo(CustomerManagerInfo info){
        info.setCustomer_name(customer_name_edit_text.getText().toString());
        info.setUser_password(customer_password_edit_text.getText().toString());
        info.setCustomer_phone(customer_telephone_edit_text.getText().toString());
        info.setCompany_name(customer_company_name_edit_text.getText().toString());
        info.setBuy_contact(customer_purchase_contacts_name_edit_text.getText().toString());
        info.setContact_phone_1(customer_purchase_contacts_telephone_edit_text.getText().toString());
    }
}
