package com.airplay.webplus;
import android.net.Uri;
public class SearchInstance {

    public String ProcessSearch(String text, String searchEngine) {
        Uri url = Uri.parse(text);
        String mGetScheme = url.getScheme();
        String mDefaultScheme = "https://";
        if(!text.contains(".")){
            text = searchEngine + "/search?&q=" + text;
        }
        else if (mGetScheme == null) {
                text = mDefaultScheme + text;
        }
        return text;
    }
    public String[] getSearchEngines() {
        String[] availableSearchEngines = {
                "Google",
                "Bing",
                "Yahoo",
        };
        return availableSearchEngines;
    }

    public String[] getSearchEngineLinks() {
        String[] givenSearchEngineLinks = {
                "https://www.google.com",
                "https://www.bing.com",
                "https://www.yahoo.com",
        };
        return givenSearchEngineLinks;
    }

    public int getEngineIndex(String data) {
        int index = 0;
        for (String link:getSearchEngineLinks()){
            if (link == data){
                break;
            }
            else{
                index += 1;
            }
        }
        return index;
    }
}