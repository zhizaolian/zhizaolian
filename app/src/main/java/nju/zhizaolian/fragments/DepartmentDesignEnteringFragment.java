package nju.zhizaolian.fragments;


import android.app.Notification;
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

import java.io.FileNotFoundException;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentDesignEnteringFragment extends Fragment {
    Button choose_version_file_button;
    EditText version_maker_name_edit_text;
    EditText version_done_time_edit_text;
    ImageButton design_entering_accept_button;
    ImageButton design_entering_cancel_button;
    ImageView version_file_image_view;

    public DepartmentDesignEnteringFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_design_entering_fragment_layout, container, false);
        choose_version_file_button=(Button) view.findViewById(R.id.choose_version_file_button);
        version_maker_name_edit_text=(EditText) view.findViewById(R.id.version_maker_name_edit_text);
        version_done_time_edit_text=(EditText) view.findViewById(R.id.version_done_time_edit_text);
        design_entering_accept_button=(ImageButton) view.findViewById(R.id.design_entering_accept_button);
        design_entering_cancel_button=(ImageButton) view.findViewById(R.id.design_entering_cancel_button);
        version_file_image_view = (ImageView) view.findViewById(R.id.version_file_image_view);
        choose_version_file_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==getActivity().RESULT_OK){
            //TODO upload the image to server
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = getActivity().getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                version_file_image_view.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
