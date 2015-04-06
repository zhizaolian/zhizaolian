package nju.zhizaolian.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import nju.zhizaolian.R;

/**
 * Created by ColorfulCode on 2015/4/6.
 */
public class OrderProcessListAdapter  extends SimpleAdapter{
    public int [] imageList;
    public Context context;
    public List<? extends Map<String, ?>> data;
    public String[] from;
    public int[] to;
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
    public OrderProcessListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to ,int [] imageList)  {
        super(context, data, resource, from, to);
        this.context=context;
        this.data=data;
        this.from=from;
        this.to=to;
        this.imageList=imageList;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent){
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itemView = layoutInflater.inflate(R.layout.order_process_list_item,parent,false);
        for(int i=0;i<from.length;i++){
            ((TextView) itemView.findViewById(to[i])).setText((String)data.get(position).get(from[i]));
        }
        TextView clickableTextView =(TextView)itemView.findViewById(R.id.order_process_list_item_view_detail);
        clickableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(),"第"+position+"个",Toast.LENGTH_SHORT).show();
            }
        });
        ImageView imageView = (ImageView) itemView.findViewById(R.id.order_detail_image);
        imageView.setImageResource(imageList[position]);
        return itemView;
    }







}
