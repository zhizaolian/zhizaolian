package nju.zhizaolian.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import nju.zhizaolian.R;

/**
 * Created by lk on 15/4/10.
 */
public class MaterialAdapter extends BaseAdapter {


    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView=View.inflate(parent.getContext(), R.layout.material_add_item,parent);
        }
        TextView materialaddName= (TextView) convertView.findViewById(R.id.materialaddname);
        TextView materialaddWeight= (TextView) convertView.findViewById(R.id.materialaddweight);


        return convertView;
    }
}
