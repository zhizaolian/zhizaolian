package nju.zhizaolian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nju.zhizaolian.R;
import nju.zhizaolian.models.Produce;

/**
 * Created by lk on 15/4/30.
 */
public class ProduceAdapter extends ArrayAdapter<Produce>{


    public ProduceAdapter(Context context, int resource, List<Produce> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Produce produce=getItem(position);
        if(convertView == null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.produce_item_layout,parent,false);
        }
        TextView produceColorView=(TextView)convertView.findViewById(R.id.produce_add_color_view);
        TextView produceXSView=(TextView)convertView.findViewById(R.id.produce_xs_add_view);
        TextView produceSView=(TextView)convertView.findViewById(R.id.produce_s_add_view);
        TextView produceMView=(TextView)convertView.findViewById(R.id.produce_m_add_view);
        TextView produceLView=(TextView)convertView.findViewById(R.id.produce_l_add_view);
        TextView produceXLView=(TextView)convertView.findViewById(R.id.produce_xl_add_view);
        TextView produceXXLView=(TextView)convertView.findViewById(R.id.produce_xxl_add_view);
        TextView produceJView=(TextView)convertView.findViewById(R.id.produce_j_add_view);

        produceColorView.setText(produce.getColor());
        produceXSView.setText(produce.getXs());
        produceSView.setText(produce.getS());
        produceMView.setText(produce.getM());
        produceLView.setText(produce.getL());
        produceXLView.setText(produce.getXl());
        produceXXLView.setText(produce.getXxl());
        produceJView.setText(produce.getJ());


        return convertView;

    }
}
