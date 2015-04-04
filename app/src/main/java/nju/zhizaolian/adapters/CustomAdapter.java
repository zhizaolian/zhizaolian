package nju.zhizaolian.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.activities.OrderDetailActivity;
import nju.zhizaolian.models.Custom;

/**
 * Created by lk on 15/3/3.
 */
public class CustomAdapter extends ArrayAdapter<Custom> {



    public CustomAdapter(Context context, ArrayList<Custom> customs) {
        super(context, 0, customs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Custom custom=getItem(position);

        if(convertView == null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom,parent,false);
        }

        TextView customNameView= (TextView) convertView.findViewById(R.id.custom_name);
        TextView customPhoneView= (TextView) convertView.findViewById(R.id.custom_phone);
        TextView customCompanyView= (TextView) convertView.findViewById(R.id.custom_company);
        TextView customCompanyAddressView= (TextView) convertView.findViewById(R.id.custom_company_address);
        TextView customCompanyPhoneView= (TextView) convertView.findViewById(R.id.custom_company_phone);
        Button addOrder=(Button)convertView.findViewById(R.id.button_add_order);
        Button addDulicateOrder=(Button)convertView.findViewById(R.id.button_add_duplicate_order);
        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getContext(), OrderDetailActivity.class);
                i.putExtra("","");
                getContext().startActivity(i);
            }
        });
        addDulicateOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j=new Intent(getContext(),OrderDetailActivity.class);
                getContext().startActivity(j);
            }
        });
        customNameView.setText(custom.getName());
        customPhoneView.setText(custom.getPhone());
        customCompanyView.setText(custom.getCompanyName());
        customCompanyAddressView.setText(custom.getCompanyAddress());
        customCompanyPhoneView.setText(custom.getCompanyPhone());

        return convertView;
    }
}
