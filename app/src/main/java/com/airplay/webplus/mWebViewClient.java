package com.airplay.webplus;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class mWebViewClient extends WebViewClient {
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) { }

    @Override
    public void onPageFinished(WebView view, String url) { }
    @TargetApi(28)
    @Override
    public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
        if (!detail.didCrash()) {
            Log.e("MY_APP_TAG", "System killed the WebView rendering process " +
                    "to reclaim memory. Recreating...");

            if (view != null) {
                view.destroy();
            }
            return true;
        }
        Log.e("MY_APP_TAG", "The WebView rendering process crashed!");
        return false;
    }
}
