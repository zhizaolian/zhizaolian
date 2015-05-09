package nju.zhizaolian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nju.zhizaolian.R;
import nju.zhizaolian.models.*;
import nju.zhizaolian.models.Package;

/**
 * Created by soft on 2015/5/9.
 */
public class WarehouseScanningAdapter extends ArrayAdapter<Package> {
    private static class ViewHolder{
        TextView packageIdView;
        TextView warehouseIdView;
        TextView shelfIdView;
        TextView locationView;
    }
    public WarehouseScanningAdapter(Context context, int resource, List<Package> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Package p=getItem(position);
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder=new ViewHolder();
            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.warehouse_scanning_item,parent,false);
            viewHolder.packageIdView= (TextView) convertView.findViewById(R.id.ws_package_id_view);
            viewHolder.warehouseIdView=(TextView)convertView.findViewById(R.id.ws_warehouse_id_view);
            viewHolder.shelfIdView=(TextView)convertView.findViewById(R.id.ws_shelf_id_view);
            viewHolder.locationView=(TextView)convertView.findViewById(R.id.ws_location_id_view);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.packageIdView.setText(p.getPackageId());
        viewHolder.warehouseIdView.setText(p.getWarehouseId());
        viewHolder.shelfIdView.setText(p.getShelfId());
        viewHolder.locationView.setText(p.getLocation());

        return convertView;
    }
}
