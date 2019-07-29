package com.airplay.webplus.Browser;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.airplay.webplus.MainActivity;
import com.airplay.webplus.R;

public class BrowsingFragment extends Fragment {
    private WebView webView;
    View view;
    View viewfragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.browsing_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = getLayoutInflater().inflate(R.layout.activity_main, null);
        viewfragment = getLayoutInflater().inflate(R.layout.browsing_fragment, null);
        webView = viewfragment.findViewById(R.id.WebView);
        setWebView();
        handleIntent(getActivity().getIntent());
    }

    private void setWebView(){
        webView.getSettings().setJavaScriptEnabled(true);
    }

    private void handleIntent(Intent intent){
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            performSearches(query);}
    }

    private void performSearches(String query){
        webView.loadUrl(query);
    }
}
