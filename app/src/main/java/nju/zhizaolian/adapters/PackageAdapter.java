package nju.zhizaolian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nju.zhizaolian.R;
import nju.zhizaolian.models.PackageDetail;

/**
 * Created by lk on 15/5/2.
 */
public class PackageAdapter extends ArrayAdapter<PackageDetail> {
    public PackageAdapter(Context context, int resource, List<PackageDetail> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PackageDetail packageDetail=getItem(position);
        if(convertView == null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.package_item_layout,parent,false);
        }
        TextView packageIdView=(TextView)convertView.findViewById(R.id.package_id_view);
        TextView packageColorView=(TextView)convertView.findViewById(R.id.package_color_view);
        TextView packageSizeView=(TextView)convertView.findViewById(R.id.package_size_view);
        TextView packageNumberView=(TextView)convertView.findViewById(R.id.package_number_view);

        if(packageDetail.getPackageId() == null){
            packageIdView.setText("暂无");
        }else {
            packageIdView.setText(String.valueOf(packageDetail.getPackageId()));
        }

        packageColorView.setText(packageDetail.getClothesStyleColor());
        packageSizeView.setText(packageDetail.getClothesStyleName());
        packageNumberView.setText(String.valueOf(packageDetail.getClothesAmount()));

        return convertView;
    }
}
