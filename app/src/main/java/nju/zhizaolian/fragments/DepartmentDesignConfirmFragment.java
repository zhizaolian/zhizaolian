package nju.zhizaolian.fragments;


import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.net.URI;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DepartmentDesignConfirmFragment extends Fragment {
    Button design_confirm_choose_version_file_button;
    ImageButton design_confirm_accept_button;
    ImageView design_confirm_version_file_image_view;


    public DepartmentDesignConfirmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.department_design_confirm_fragment_layout, container, false);
        design_confirm_choose_version_file_button=(Button) view.findViewById(R.id.design_confirm_choose_version_file_button);
        design_confirm_accept_button=(ImageButton) view.findViewById(R.id.design_confirm_accept_button);
        design_confirm_version_file_image_view=(ImageView) view.findViewById(R.id.design_confirm_version_file_image_view);
        design_confirm_choose_version_file_button.setOnClickListener(new View.OnClickListener() {
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
        if(resultCode==getActivity().RESULT_OK){
            Uri uri = data.getData();
            ContentResolver contentResolver= getActivity().getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                design_confirm_version_file_image_view.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
