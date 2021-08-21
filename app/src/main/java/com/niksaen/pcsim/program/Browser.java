package com.niksaen.pcsim.program;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.niksaen.pcsim.MainActivity;
import com.niksaen.pcsim.R;

public class Browser extends Program {

    public Browser(MainActivity activity){
        super(activity);
        Title = "Browser";
    }
    public void initProgram(){
        mainWindow = LayoutInflater.from(activity).inflate(R.layout.program_browser,null);
        style();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://google.com/");
        webView.getSettings().setSupportZoom(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webView.clearCache(true);
                super.onPageFinished(view, url);
            }
        });
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        super.initProgram();
    }
    WebView webView;
    private void style(){
        webView  = mainWindow.findViewById(R.id.web);
        titleTextView=mainWindow.findViewById(R.id.title);
        buttonClose = mainWindow.findViewById(R.id.close);
        buttonFullscreenMode = mainWindow.findViewById(R.id.fullscreenMode);
        buttonRollUp = mainWindow.findViewById(R.id.roll_up);
    }
    @Override
    public void closeProgram(int mode){
        super.closeProgram(mode);
    }
}
