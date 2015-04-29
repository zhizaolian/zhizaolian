package nju.zhizaolian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nju.zhizaolian.R;
import nju.zhizaolian.models.DeliveryRecord;

/**
 * Created by lk on 15/4/29.
 */
public class SendRecordAdapter extends ArrayAdapter<DeliveryRecord> {
    public SendRecordAdapter(Context context, int resource,ArrayList<DeliveryRecord> deliveryRecords) {
        super(context, resource,deliveryRecords);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DeliveryRecord deliveryRecord=getItem(position);
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sample_express_record,parent,false);
        }
        TextView sendExpressName= (TextView) convertView.findViewById(R.id.send_express_name_view);
        TextView sendExpressNumber=(TextView)convertView.findViewById(R.id.send_express_number_view);
        TextView sendExpressPrice=(TextView)convertView.findViewById(R.id.send_express_price_view);
        TextView sendExpressTime=(TextView)convertView.findViewById(R.id.send_express_time_view);
        sendExpressName.setText(deliveryRecord.getExpressName());
        sendExpressNumber.setText(deliveryRecord.getExpressNumber());
        sendExpressPrice.setText(deliveryRecord.getExpressPrice());
        sendExpressTime.setText(deliveryRecord.getSendTime());


        return convertView;
    }
}
