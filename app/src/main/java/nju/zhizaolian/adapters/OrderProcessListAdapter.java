package nju.zhizaolian.adapters;

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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BinaryHttpResponseHandler;

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
    public ArrayList<Integer> downloaded= new ArrayList<>();
    public ArrayList<Bitmap> downloadedBitmap= new ArrayList<>();
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
        final View itemView = layoutInflater.inflate(R.layout.order_process_list_item,parent,false);
        for(int i=0;i<from.length;i++){
            ((TextView) itemView.findViewById(to[i])).setText((String)data.get(position).get(from[i]));
        }
        TextView clickableTextView =(TextView)itemView.findViewById(R.id.order_process_list_item_view_detail);
        clickableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OrderListFragment.OrderListItemClickedToGoFragment) context).goFragmentByOrderListItem(position);
            }
        });
        ImageView imageView = (ImageView) itemView.findViewById(R.id.order_detail_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView view = new ImageView(context);
                setMyImageView(view,(String) data.get(position).get("big_image_url"),-1);
                new AlertDialog.Builder(context)
                        .setTitle((String) data.get(position).get("name") + "样式大图")
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
        if (!downloaded.contains(position)) {
            setMyImageView(imageView, (String) data.get(position).get("image_url"), position);
        }else{
            imageView.setImageBitmap(downloadedBitmap.get(downloaded.indexOf(position)));
        }
        return itemView;
    }


    public void setMyImageView(final ImageView imageView,final String url,final int position){
        AsyncHttpClient client = new AsyncHttpClient();
        String[] allowedContentTypes = new String[] { "image/png", "image/jpeg" };
        client.post(IPAddress.getIP()+url,new BinaryHttpResponseHandler(allowedContentTypes) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] binaryData) {
                if(position!=-1) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(binaryData, 0, binaryData.length);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    int quality = 50;
                    bmp.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
                    ByteArrayInputStream isBm = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                    bmp = BitmapFactory.decodeStream(isBm, null, null);
                    bmp = small(bmp);
                    imageView.setImageBitmap(bmp);
                    downloadedBitmap.add(bmp);
                    downloaded.add(position);
                }else{
                    Bitmap bmp = BitmapFactory.decodeByteArray(binaryData, 0, binaryData.length);
                    imageView.setImageBitmap(bmp);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] binaryData, Throwable error) {

            }
        });
    }


    private static Bitmap small(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postScale(0.5f,0.5f);
        Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
        return resizeBmp;
    }

    public void updateMyAdapter(){
        downloaded.clear();
        downloadedBitmap.clear();
    }




}
