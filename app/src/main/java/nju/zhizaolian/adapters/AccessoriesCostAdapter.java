package nju.zhizaolian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nju.zhizaolian.R;
import nju.zhizaolian.models.AccessoryCost;

/**
 * Created by lk on 15/5/2.
 */
public class AccessoriesCostAdapter extends ArrayAdapter<AccessoryCost>{
    public AccessoriesCostAdapter(Context context, int resource, List<AccessoryCost> objects) {
        super(context, resource, objects);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AccessoryCost accessoryCost=getItem(position);
        if(convertView ==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.sublayout_accessories_price,parent,false);
        }
        TextView accessoriesCostNameView= (TextView) convertView.findViewById(R.id.accessory_cost_name_view);
        TextView accessoriesCostTearPerPieceView=(TextView)convertView.findViewById(R.id.accessory_cost_tear_per_piece_view);
        TextView accessoriesCostPrice=(TextView)convertView.findViewById(R.id.accessory_cost_price_view);
        TextView accessoriesCostPerPieceView=(TextView)convertView.findViewById(R.id.accessory_cost_per_piece_view);


        accessoriesCostNameView.setText(accessoryCost.getAccessoryName());
        accessoriesCostTearPerPieceView.setText(accessoryCost.getTearPerPiece().toString());
        accessoriesCostPrice.setText(accessoryCost.getPrice().toString());
        accessoriesCostPerPieceView.setText(accessoryCost.getCostPerPiece().toString());


        return convertView;

    }
}
