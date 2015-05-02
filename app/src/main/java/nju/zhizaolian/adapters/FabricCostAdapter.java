package nju.zhizaolian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nju.zhizaolian.R;
import nju.zhizaolian.models.FabricCost;

/**
 * Created by lk on 15/5/2.
 */
public class FabricCostAdapter extends ArrayAdapter<FabricCost> {
    public FabricCostAdapter(Context context, int resource, List<FabricCost> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FabricCost fabricCost= getItem(position);

        if (convertView == null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.sublayout_material_price,parent,false);
        }
        TextView fabricNameView= (TextView) convertView.findViewById(R.id.material_name_view);
        TextView fabricTearPerMeterView=(TextView)convertView.findViewById(R.id.material_per_meter_waste_view);
        TextView fabricPriceView=(TextView)convertView.findViewById(R.id.material_price_view);
        TextView fabricCostPerMeterView=(TextView)convertView.findViewById(R.id.material_per_cost_view);


        fabricNameView.setText(fabricCost.getFabricName());
        fabricTearPerMeterView.setText(fabricCost.getTearPerMeter().toString());
        fabricPriceView.setText(fabricCost.getPrice().toString());
        fabricCostPerMeterView.setText(fabricCost.getCostPerMeter().toString());

        return convertView;

    }
}
