package nju.zhizaolian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import nju.zhizaolian.R;


/**
 * Created by ColorfulCode on 2015/4/20.
 */
public class MainMenuAdapter extends SimpleAdapter{
    Context context;
    List<? extends Map<String, ?>> data;
    int resource;
    String[] from;
    int[] to;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(resource,parent,false);
        for(int i=0;i<from.length;i++){
            ((TextView) view.findViewById(to[i])).setText((String)data.get(position).get(from[i]));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String department =((TextView)v.findViewById(R.id.item_department)).getText().toString();
                Toast.makeText(view.getContext(),department,Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public MainMenuAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context=context;
        this.data=data;
        this.resource=resource;
        this.from=from;
        this.to=to;

    }
}
