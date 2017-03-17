package com.dhwanishah.newsfeed.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dhwanishah.newsfeed.R;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.webViewToolbar);
        setSupportActionBar(mainToolbar);

        final String webUrl = getIntent().getStringExtra("webUrl");
        webView = (WebView) findViewById(R.id.articleWebView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (!TextUtils.isEmpty(webUrl)) {
            webView.loadUrl(webUrl);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.web_view_toolbar, menu);
        return true;
    }
}
