package nju.zhizaolian.fragments;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import nju.zhizaolian.R;
import nju.zhizaolian.help.DatePickerFragment;


public class OrderSampleDetailFragment extends android.support.v4.app.Fragment {
    private static int Result_LOAD_SAMPLE_IMAGE=1;
    private static int Result_LOAD_REFERENCE_IMAGE=2;
    private SaveSampleData saveSampleData;
    private Switch ifProvideSample;
    private Button expressTime;
    private Spinner expressName;
    private EditText expressNumber;
    private Switch ifMakeSample;
    private EditText postMan;
    private EditText postManPhone;
    private EditText expressAddress;
    private EditText otherRemark;
    private Button selectSamplePicture;
    private Button selectReferencePicture;
    private ImageView samplePicture;
    private ImageView referencePicture;
    private Button saveData;
    DatePickerFragment datePickerFragment=null;
    private String samplePictureUrl;
    private String referencePictureUrl;
    public OrderSampleDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_order_sample_detail, container, false);
        ifProvideSample= (Switch) view.findViewById(R.id.isProvideSampleSwitch);
        expressTime=(Button)view.findViewById(R.id.expressTimeButton);
        expressTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFragment=new DatePickerFragment();
                datePickerFragment.show(getActivity().getFragmentManager(),"datePicker");



            }
        });
        expressName=(Spinner)view.findViewById(R.id.express_name_spinner);
        expressName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expressTime.setText("已选择时间:" + datePickerFragment.getDate());
            }
        });
        expressNumber=(EditText)view.findViewById(R.id.express_number_edit);
        ifMakeSample=(Switch)view.findViewById(R.id.is_make_sample_switch);
        postMan=(EditText)view.findViewById(R.id.post_man_edit);
        postManPhone=(EditText)view.findViewById(R.id.express_man_phone_edit);
        expressAddress=(EditText)view.findViewById(R.id.express_address_edit);
        otherRemark=(EditText)view.findViewById(R.id.other_remark_edit);
        selectSamplePicture=(Button)view.findViewById(R.id.select_sample_picture_button);
        selectSamplePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaImageFromGallery(Result_LOAD_SAMPLE_IMAGE);
            }
        });
        selectReferencePicture=(Button)view.findViewById(R.id.select_reference_picture_button);
        selectReferencePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaImageFromGallery(Result_LOAD_REFERENCE_IMAGE);
            }
        });
        samplePicture=(ImageView)view.findViewById(R.id.sample_picture_image);
        referencePicture=(ImageView)view.findViewById(R.id.reference_picture_image);
        saveData=(Button)view.findViewById(R.id.save_sample_detail_button);
        saveData.setOnClickListener(new saveDataListener());
        return view;
    }
    private void loaImageFromGallery(int i){
        Intent galleryIntent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, i);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try{
            if(requestCode == Result_LOAD_SAMPLE_IMAGE && resultCode == getActivity().RESULT_OK && null != data){
                Uri selectImage=data.getData();
                String[] filePathColumn={MediaStore.Images.Media.DATA};
                Cursor  cursor=getActivity().getContentResolver().query(selectImage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                samplePictureUrl=cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap= BitmapFactory.decodeFile(samplePictureUrl);
                samplePicture.setImageBitmap(bitmap);

            }else if(requestCode == Result_LOAD_REFERENCE_IMAGE && resultCode==getActivity().RESULT_OK && null != data){
                Uri selectImage=data.getData();
                String[] filePathColumn={MediaStore.Images.Media.DATA};
                Cursor  cursor=getActivity().getContentResolver().query(selectImage,filePathColumn,null,null,null);
                cursor.moveToFirst();
                int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                referencePictureUrl=cursor.getString(columnIndex);
                cursor.close();
                Bitmap bitmap= BitmapFactory.decodeFile(referencePictureUrl);
                referencePicture.setImageBitmap(bitmap);

            }else {
                Toast.makeText(getActivity().getApplicationContext(),"你还没有选择图片",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getActivity().getApplicationContext(),"出现异常",Toast.LENGTH_SHORT).show();
        }


    }

    public class saveDataListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            boolean hasPostedSampleData=ifProvideSample.isChecked();
            String expressTimeData=expressTime.getText().toString();
            String expressNameData=expressName.getSelectedItem().toString();
            String expressNumberData=expressNumber.getText().toString();
            boolean isNeedSampleData=ifMakeSample.isChecked();
            String postManData=postMan.getText().toString();
            String postManPhoneData=postManPhone.getText().toString();
            String expressAddressData=expressAddress.getText().toString();
            String otherRemarkData=otherRemark.getText().toString();
            String result=hasPostedSampleData+"|"+expressTimeData+"|"+expressNameData+"|"+expressNumberData+"|"+isNeedSampleData+"|"+
                    postManData+"|"+postManPhoneData+"|"+expressAddressData+"|"+otherRemarkData+"|"+samplePictureUrl+"|"+referencePictureUrl;
            saveSampleData.saveSampleData(result);
            Toast.makeText(getActivity().getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            saveSampleData= (SaveSampleData) activity;
        }catch (ClassCastException e){
            Toast.makeText(getActivity().getApplicationContext(),"保存失败",Toast.LENGTH_SHORT).show();
        }
    }

    public interface SaveSampleData{
        public void saveSampleData(String sampleData);
    }



}
