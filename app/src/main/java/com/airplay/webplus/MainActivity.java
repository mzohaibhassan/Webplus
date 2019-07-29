package com.airplay.webplus;

import android.annotation.TargetApi;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.airplay.webplus.Browser.BrowsingFragment;
import com.airplay.webplus.Browser.HomeScreenFragment;
import com.airplay.webplus.Browser.SearchableActivity;
import com.airplay.webplus.Settings.Settings;

public class MainActivity extends AppCompatActivity {


    private String url;
    private Toolbar mToolBar;
    private String urlEntered;

    private SearchInstance instance = new SearchInstance();
    private String selectedEngine;
    private SharedPreferences sharedPreferences;
    private String directedURL;
    private String lastUrl;

    private SearchView mSearchView;

    private WebView mWebView;
    private mWebViewClient webViewClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSearchView();
        doThisWhenCreated(savedInstanceState);
    }

    private void doThisWhenCreated (Bundle savedInstanceState){
        mToolBar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolBar);

        mWebView = findViewById(R.id.WebView);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        directedURL = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        selectedEngine = sharedPreferences.getString("defaultEngine", "https://www.google.com");
        lastUrl = sharedPreferences.getString("lastUrl", selectedEngine);
        if (Build.VERSION.SDK_INT >= 28){
            dataDirectorySuffix();
        }
        mWebView.requestFocus();
        url = getIntent().getDataString();


        if (savedInstanceState != null){
            mWebView.restoreState(savedInstanceState);}
        else {
            mWebView.loadUrl(lastUrl);}
            mWebView.loadUrl(directedURL);
        if (url != null){
            mWebView.loadUrl(url);
        }

        mSearchView = findViewById(R.id.search_bar);
        mSearchView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER && event.getAction() == event.ACTION_DOWN) {
                    urlEntered = mSearchView.getQuery().toString();
                    mWebView.loadUrl(instance.ProcessSearch(urlEntered, selectedEngine));
                    mWebView.requestFocus();
                }
                return false;
            }
        });

        webViewClient = new mWebViewClient();
        mWebView.setWebViewClient(webViewClient);

        mWebView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        getMenuInflater().inflate(R.menu.tab_menu, menu);
        return true;
    }

    private void setSearchView(){
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = findViewById(R.id.search_bar);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;


        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.googleHome:
                loadHome();
                break;

            case R.id.menuSettings:
                Intent settingsIntent = new Intent(this, Settings.class);
                startActivity(settingsIntent);
                break;

            case R.id.tabs:
                Intent tabsIntent = new Intent(this, TabActivity.class);
                startActivity(tabsIntent);
                break;
        }

        return true;
    }

    public void displayURL(String url) {
    }

    public void loadHome() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mWebView.loadUrl(sharedPreferences.getString("defaultEngine", "https://www.google.com"));
    }


    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        sharedPreferences.edit().putString("lastUrl", mWebView.getUrl())
                .apply();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
        if (PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getBoolean("dataSaving", false))
            mWebView.getSettings().setLoadsImagesAutomatically(false);
        else
            mWebView.getSettings().setLoadsImagesAutomatically(true);
    }
    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        mWebView.saveState(outState);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @TargetApi(28)
    public void dataDirectorySuffix() {
        mWebView.setRendererPriorityPolicy(WebView.RENDERER_PRIORITY_BOUND, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean choose = sharedPreferences.getBoolean("enableJavaScript", true);

        if (choose)
            mWebView.getSettings().setJavaScriptEnabled(true);
        else
            mWebView.getSettings().setJavaScriptEnabled(false);

    }

    void getLink(){
        String url = getIntent().getStringExtra(Intent.ACTION_VIEW);
        mWebView.loadUrl(url);
    }
//    class GestureListener extends GestureDetector.SimpleOnGestureListener {
//        boolean result;
//
//        @Override
//        public boolean onDown(MotionEvent event) {
//            // don't return false here or else none of the other
//            // gestures will work
//            return true;
//        }
//
//        @Override
//        public boolean onSingleTapConfirmed(MotionEvent e) {
//            return true;
//        }
//
//        @Override
//        public void onLongPress(MotionEvent e) {
//        }
//
//        @Override
//        public boolean onDoubleTap(MotionEvent e) {
//            return true;
//        }
//
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2,
//                                float distanceX, float distanceY) {
//            return true;
//        }
//
//        @Override
//        public boolean onFling(MotionEvent event1, MotionEvent event2,
//                               float velocityX, float velocityY) {
//            result = false;
//            if (Math.abs(event2.getY() - event1.getY()) < Math.abs(event2.getX() - event1.getX())) {
//                if ((event2.getX() - event1.getX()) > 0) {
//                    mWebView.goBack();
//                } else {
//                    mWebView.goForward();
//                }
//                result = true;
//            }
//            return result;
//        }
//    }
}