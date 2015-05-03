package nju.zhizaolian.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import nju.zhizaolian.R;
import nju.zhizaolian.models.IPAddress;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterAndScanningFragment extends Fragment {

    WebView webView;
    public RegisterAndScanningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_register_and_scanning, container, false);
        webView=(WebView)view.findViewById(R.id.web_view);
        webView.loadUrl(IPAddress.getIP()+"/fmc/logistics/mobile/index.do");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        return view;
    }


}
