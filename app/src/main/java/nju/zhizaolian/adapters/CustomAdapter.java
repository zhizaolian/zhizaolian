package nju.zhizaolian.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.OrderDetailActivity;
import nju.zhizaolian.models.Account;
import nju.zhizaolian.models.Custom;

/**
 * Created by lk on 15/3/3.
 */
public class CustomAdapter extends ArrayAdapter<Custom> {



    private Account account=null;
    public CustomAdapter(Context context, ArrayList<Custom> customs,Account account) {
        super(context, 0, customs);
        this.account=account;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         final Custom custom=getItem(position);

        if(convertView == null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom,parent,false);
        }

        TextView customNameView= (TextView) convertView.findViewById(R.id.custom_name);
        TextView customPhoneView= (TextView) convertView.findViewById(R.id.custom_phone);
        TextView customCompanyView= (TextView) convertView.findViewById(R.id.custom_company);

        TextView customCompanyPhoneView= (TextView) convertView.findViewById(R.id.custom_company_phone);
        Button addOrder=(Button)convertView.findViewById(R.id.button_add_order);

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), OrderDetailActivity.class);
                i.putExtra("custom",custom);
                i.putExtra("account",account);
                getContext().startActivity(i);
            }
        });

        customNameView.setText(custom.getCustomerName());
        customPhoneView.setText(custom.getCustomerPhone());
        customCompanyView.setText(custom.getCompanyName());

        if(custom.getCompanyPhone().length()==0){
            customCompanyPhoneView.setText("无");
        }else{
            customCompanyPhoneView.setText(custom.getCompanyPhone());
        }


        return convertView;
    }
}
