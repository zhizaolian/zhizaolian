package nju.zhizaolian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import nju.zhizaolian.R;
import nju.zhizaolian.models.VersionData;

/**
 * Created by lk on 15/5/6.
 */
public class VersionAdapter extends ArrayAdapter<VersionData> {
    public VersionAdapter(Context context, int resource, List<VersionData> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VersionData versionData=getItem(position);
        if(convertView == null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.version_data,parent,false);
        }
        TextView versionSizeView=(TextView)convertView.findViewById(R.id.version_size_view);
        TextView versionCenterBackLengthView=(TextView)convertView.findViewById(R.id.version_center_back_length_view);
        TextView versionBustView=(TextView)convertView.findViewById(R.id.version_bust_view);
        TextView versionWaistLineView=(TextView)convertView.findViewById(R.id.version_waist_line_view);
        TextView versionShoulderView=(TextView)convertView.findViewById(R.id.version_shoulder_view);
        TextView versionButtockView =(TextView)convertView.findViewById(R.id.version_buttock_view);
        TextView versionHemView=(TextView)convertView.findViewById(R.id.version_Hem_view);
        TextView versionTrousersView=(TextView)convertView.findViewById(R.id.version_trousers_view);
        TextView versionSkirtView=(TextView)convertView.findViewById(R.id.version_skirt_view);
        TextView versionSleevesView=(TextView)convertView.findViewById(R.id.version_sleeve_view);

        versionSizeView.setText(versionData.getSize());
        versionCenterBackLengthView.setText(versionData.getCenterBackLength());
        versionBustView.setText(versionData.getBust());
        versionWaistLineView.setText(versionData.getWaistline());
        versionShoulderView.setText(versionData.getShoulder());
        versionButtockView.setText(versionData.getButtock());
        versionHemView.setText(versionData.getHem());
        versionTrousersView.setText(versionData.getTrousers());
        versionSkirtView.setText(versionData.getSkirt());
        versionSleevesView.setText(versionData.getSleeves());

        return convertView;
    }
}
