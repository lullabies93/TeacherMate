package com.example.yannis.dianming.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.yannis.dianming.R;
import com.example.yannis.dianming.base.BaseActivity;
import com.example.yannis.dianming.base.ConstantValues;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AboutActivity extends BaseActivity {

    @InjectView(R.id.back)
    TextView back;
    @InjectView(R.id.webview)
    WebView webview;

    private ProgressDialog progressDialog;

    @Override
    public int getLayoutID() {
        return R.layout.activity_about;
    }

    @Override
    public void initViews() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        webview.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress>99 && progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void loadData() {
        progressDialog.show();
        webview.loadUrl(ConstantValues.ABOUT);
    }

    @Override
    public void initData(Bundle bundle) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("please waiting...");
    }
}
