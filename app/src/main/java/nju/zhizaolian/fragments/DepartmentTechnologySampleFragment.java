package nju.zhizaolian.fragments;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentTechnologySampleFragment extends Fragment {
    TextView sample_customer_technology_request_text;
    TextView sample_ironing_flower_cost_text;
    TextView sample_washing_cost_text;
    TextView sample_laser_cost_text;
    TextView sample_embroidery_cost_text;
    TextView sample_crushed_cost_text;
    TextView sample_plate_charge_cost_text;
    EditText sample_technology_leading_name_edit_text;
    EditText sample_technology_mass_complete_date_edit_text;
    ImageButton sample_technology_mass_accept_button;
    Button sample_image_upload_button;
    ImageView sample_uploaded_image_view;


    public DepartmentTechnologySampleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =inflater.inflate(R.layout.department_technology_sample_fragment_layout, container, false);
        sample_customer_technology_request_text=(TextView) view.findViewById(R.id.check_customer_technology_request_text);
        sample_ironing_flower_cost_text=(TextView) view.findViewById(R.id.check_ironing_flower_cost_edit_text);
        sample_washing_cost_text=(TextView) view.findViewById(R.id.check_washing_cost_edit_text);
        sample_laser_cost_text=(TextView) view.findViewById(R.id.check_laser_cost_text);
        sample_embroidery_cost_text=(TextView) view.findViewById(R.id.check_embroidery_cost_edit_text);
        sample_crushed_cost_text=(TextView) view.findViewById(R.id.check_crushed_cost_edit_text);
        sample_plate_charge_cost_text=(TextView) view.findViewById(R.id.check_plate_charge_cost_edit_text);
        sample_technology_leading_name_edit_text=(EditText) view.findViewById(R.id.sample_technology_leading_name_edit_text);
        sample_technology_mass_complete_date_edit_text=(EditText) view.findViewById(R.id.sample_technology_mass_complete_date_edit_text);
        sample_technology_mass_accept_button=(ImageButton) view.findViewById(R.id.sample_technology_mass_accept_button);
        sample_image_upload_button=(Button) view.findViewById(R.id.sample_image_upload_button);
        sample_uploaded_image_view=(ImageView) view.findViewById(R.id.sample_uploaded_image_view);
        TextView textViewList[] = new TextView[]{
                sample_customer_technology_request_text,
                sample_ironing_flower_cost_text,
                sample_washing_cost_text,
                sample_laser_cost_text,
                sample_embroidery_cost_text,
                sample_crushed_cost_text,
                sample_plate_charge_cost_text};
        ArrayList<String> viewListData = (ArrayList) getArguments().getSerializable("data");
        String time  = (String) getArguments().getSerializable("time");
        if(viewListData!=null){
            for(int i=0;i<viewListData.size();i++){
                textViewList[i].setText(viewListData.get(i));
            }
        }
        sample_technology_mass_complete_date_edit_text.setText(time);
        sample_image_upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
        return  view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==getActivity().RESULT_OK){
            //TODO upload the image to server
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = getActivity().getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                sample_uploaded_image_view.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }

    }
}
