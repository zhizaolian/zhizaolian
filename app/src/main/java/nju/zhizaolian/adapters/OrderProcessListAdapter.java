package nju.zhizaolian.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nju.zhizaolian.R;
import nju.zhizaolian.fragments.OrderListFragment;
import nju.zhizaolian.models.IPAddress;

/**
 * Created by ColorfulCode on 2015/4/6.
 */
public class OrderProcessListAdapter  extends SimpleAdapter{
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
    public OrderProcessListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to)  {
        super(context, data, resource, from, to);
        this.context=context;
        this.data=data;
        this.from=from;
        this.to=to;
    }

    @Override
    public View getView (final int position, View convertView, ViewGroup parent){

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder viewHolder;

        if(convertView==null) {
            convertView = layoutInflater.inflate(R.layout.order_process_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.styleName=(TextView) convertView.findViewById(R.id.order_process_list_item_style_name);
            viewHolder.orderId=(TextView) convertView.findViewById(R.id.order_process_list_item_order_number);
            viewHolder.orderTime=(TextView) convertView.findViewById(R.id.order_process_list_item_order_start_date);
            viewHolder.orderProcessStateName=(TextView) convertView.findViewById(R.id.order_process_list_item_order_state);
            viewHolder.employeeName=(TextView) convertView.findViewById(R.id.order_process_list_item_order_salesman_name);
            viewHolder.customerName=(TextView) convertView.findViewById(R.id.order_process_list_item_customer_name);
            viewHolder.customerCompany=(TextView) convertView.findViewById(R.id.order_process_list_item_custom_company_name);
//            viewHolder.clickableTextView = (TextView) convertView.findViewById(R.id.order_process_list_item_view_detail);
            viewHolder.sampleClothesThumbnailPicture = (ImageView) convertView.findViewById(R.id.order_detail_image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.styleName.setText((String)data.get(position).get("name"));
        viewHolder.orderId.setText((String) data.get(position).get("number"));
        viewHolder.orderTime.setText((String)data.get(position).get("date"));
        viewHolder.orderProcessStateName.setText((String)data.get(position).get("state"));
        viewHolder.employeeName.setText((String)data.get(position).get("s_name"));
        viewHolder.customerName.setText((String)data.get(position).get("c_name"));
        viewHolder.customerCompany.setText((String)data.get(position).get("cc_name"));
        Picasso.with(context).load(IPAddress.getIP()+data.get(position).get("image_url")).error(R.drawable.ic_action_cancel).into(viewHolder.sampleClothesThumbnailPicture);
        viewHolder.sampleClothesThumbnailPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView view = new ImageView(context);
                Picasso.with(context).load(IPAddress.getIP() + data.get(position).get("big_image_url")).into(view);
                new AlertDialog.Builder(context)
                        .setTitle(data.get(position).get("name") + "的样式大图")
                        .setView(view)
                        .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                }

                        ).show();
            }
        });
        return convertView;
    }


    public class ViewHolder{
        TextView styleName;
        TextView orderId;
        TextView orderTime;
        TextView orderProcessStateName;
        TextView employeeName;
        TextView customerName;
        TextView customerCompany;
        TextView clickableTextView;
        ImageView sampleClothesThumbnailPicture;
    }

}
