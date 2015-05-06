package nju.zhizaolian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nju.zhizaolian.R;
import nju.zhizaolian.models.RepairRecord;

/**
 * Created by lk on 15/5/6.
 */
public class RepairRecordAdapter extends ArrayAdapter<RepairRecord> {
    public RepairRecordAdapter(Context context, int resource, List<RepairRecord> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RepairRecord repairRecord=getItem(position);
        if(convertView == null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.repair_record_layout,parent,false);
        }
        TextView dateView= (TextView) convertView.findViewById(R.id.repair_date_view);
        TextView processNameView=(TextView)convertView.findViewById(R.id.repair_process_name_view);
        TextView actualNameView=(TextView)convertView.findViewById(R.id.repair_actual_number_view);

        dateView.setText(repairRecord.getRepairTime());
        processNameView.setText(repairRecord.getRepairSide());
        actualNameView.setText(repairRecord.getQualifiedAmount());



        return convertView;
    }
}
