package nju.zhizaolian.fragments;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import java.text.SimpleDateFormat;
import java.util.Date;

import nju.zhizaolian.R;
import nju.zhizaolian.models.Custom;
import nju.zhizaolian.models.CustomerManagerInfo;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerRegisterFragment extends Fragment implements View.OnClickListener {

    private Button registerButton;
    private Button cancelButton;
    private EditText customer_login_name_edit_text;
    private EditText customer_name_edit_text;
    private EditText customer_password_edit_text;
    private EditText customer_telephone_edit_text;
    private EditText customer_company_name_edit_text;
    private EditText customer_purchase_contacts_name_edit_text;
    private EditText customer_purchase_contacts_telephone_edit_text;
    private EditText customer_register_date_edit_text;

    public CustomerRegisterFragment() {
        // Required empty public constructor
    }

    public interface CustomerRegisterButtonClickListener{
        void customerRegisterButtonClicked(CustomerManagerInfo info);
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
        customer_login_name_edit_text=(EditText) view.findViewById(R.id.customer_login_name_edit_text);
        customer_name_edit_text=(EditText) view.findViewById(R.id.customer_name_edit_text);
        customer_password_edit_text=(EditText) view.findViewById(R.id.customer_password_edit_text);
        customer_telephone_edit_text=(EditText) view.findViewById(R.id.customer_telephone_edit_text);
        customer_company_name_edit_text=(EditText) view.findViewById(R.id.customer_company_name_edit_text);
        customer_purchase_contacts_name_edit_text=(EditText) view.findViewById(R.id.customer_purchase_contacts_name_edit_text);
        customer_purchase_contacts_telephone_edit_text=(EditText) view.findViewById(R.id.customer_purchase_contacts_telephone_edit_text);
        customer_register_date_edit_text=(EditText) view.findViewById(R.id.customer_register_date_edit_text);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        customer_register_date_edit_text.setText(dateFormat.format(date));
        return  view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.customer_register_button:
                String customer_login_name=customer_login_name_edit_text.getText().toString();
                String customer_name=customer_name_edit_text.getText().toString();
                String customer_password=customer_password_edit_text.getText().toString();
                String customer_telephone=customer_telephone_edit_text.getText().toString();
                String customer_company_name=customer_company_name_edit_text.getText().toString();
                String customer_purchase_contacts_name=customer_purchase_contacts_name_edit_text.getText().toString();
                String customer_purchase_contacts_telephone=customer_purchase_contacts_telephone_edit_text.getText().toString();
                String customer_register_date=customer_register_date_edit_text.getText().toString();
                if(!(customer_login_name!=null&&customer_login_name.length()>0)){
                    customer_login_name_edit_text.setError("客户登陆名不能为空");
                    break;
                  }
                if(!(customer_name!=null&&customer_name.length()>0)){
                    customer_name_edit_text.setError("客户名不能为空");
                    break;
                }
                if(!(customer_password!=null&&customer_password.length()>0)){
                    customer_password_edit_text.setError("客户密码不能为空");
                    break;
                }
                if(!(customer_telephone!=null&&customer_telephone.length()>0)){
                    customer_telephone_edit_text.setError("客户电话不能为空");
                    break;
                }
                if(!(customer_company_name!=null&&customer_company_name.length()>0)){
                    customer_company_name_edit_text.setError("客户公司名不能为空");
                    break;
                }
                if(!(customer_purchase_contacts_name!=null&&customer_purchase_contacts_name.length()>0)){
                    customer_purchase_contacts_name_edit_text.setError("客户采购联系人不能为空");
                    break;
                }
                if(!(customer_purchase_contacts_telephone!=null&&customer_purchase_contacts_telephone.length()>0)){
                    customer_purchase_contacts_telephone_edit_text.setError("客户采购联系人电话不能为空");
                    break;
                }
                    CustomerManagerInfo info = new CustomerManagerInfo();
                    info.setUser_name(customer_login_name);
                    info.setCustomer_name(customer_name);
                    info.setUser_password(customer_password);
                    info.setCustomer_phone(customer_telephone);
                    info.setCompany_name(customer_company_name);
                    info.setBuy_contact(customer_purchase_contacts_name);
                    info.setContact_phone_1(customer_purchase_contacts_telephone);
                    info.setRegister_date(customer_register_date);
                    ((CustomerRegisterButtonClickListener) getActivity()).customerRegisterButtonClicked(info);
               
                break;
            case R.id.customer_cancel_button:
                ((CustomerCancelButtonClickListener) getActivity()).customerCancelButtonClicked();
                break;
        }
    }

    public void notifyUserNameEditTextSetError(){
        customer_login_name_edit_text.setError("用户名重复，请修改");
    }

    public void notifyEditTextSetNull(){
        customer_login_name_edit_text.setText("");
        customer_name_edit_text.setText("");
        customer_password_edit_text.setText("");
        customer_telephone_edit_text.setText("");
        customer_company_name_edit_text.setText("");
        customer_purchase_contacts_name_edit_text.setText("");
        customer_purchase_contacts_telephone_edit_text.setText("");
        customer_register_date_edit_text.setText("");
    }
}
