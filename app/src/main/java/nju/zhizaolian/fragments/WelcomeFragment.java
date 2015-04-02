package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

import nju.zhizaolian.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener{

    private TextView inquirySheet;
    private TextView priceSheet;
    private TextView inquirySheetUploadTime;
    private TextView priceSheetUploadTime;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    public interface InquirySheetDownloadListener{
        void inquirySheetDownload();
    }

    public interface PriceSheetDownloadListener{
        void priceSheetDownload();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.welcome_layout,container,false);
        inquirySheet=(TextView) view.findViewById(R.id.inquiry_sheet_download);
        inquirySheet.setOnClickListener(this);
        priceSheet=(TextView) view.findViewById(R.id.price_sheet_download);
        priceSheet.setOnClickListener(this);
        inquirySheetUploadTime=(TextView) view.findViewById(R.id.inquiry_sheet_upload_time);
        priceSheetUploadTime=(TextView) view.findViewById(R.id.price_sheet_upload_time);
        //Test Data
        inquirySheetUploadTime.setText("2015-03-31");
        priceSheetUploadTime.setText("2015-04-01");
        //Test Data End
        return  view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.inquiry_sheet_download:
                ((InquirySheetDownloadListener)getActivity()).inquirySheetDownload();
                break;
            case R.id.price_sheet_download:
                ((PriceSheetDownloadListener)getActivity()).priceSheetDownload();
                break;
        }
    }
}
