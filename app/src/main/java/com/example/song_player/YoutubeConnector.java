package com.example.song_player;


import android.content.Context;
import android.util.Log;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class YoutubeConnector {
    private YouTube youtube;
    private YouTube.Search.List query;

    public static final String KEY = "YOUR_API_KEY";
    private static final String PACKAGENAME = "com.example.song_player";
    //public static final String SHA1 = "SHA1_FINGERPRINT_STRING"
    private static final long MAXRESULT = 15;

    public YoutubeConnector(Context context){
        youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {

            //initialize method helps to add any extra details that may be required to process the query
            @Override
            public void initialize(HttpRequest request) throws IOException {

                //setting package name and sha1 certificate to identify request by server
                request.getHeaders().set("X-Android-Package", PACKAGENAME);
                //request.getHeaders().set("X-Android-Cert",SHA1);
            }
        }).setApplicationName("SearchYoutube").build();
        try {
            query =youtube.search().list("id,snippet");
            query.setKey(KEY);
            query.setType("video");
            query.setFields("items(id/kind,id/videoId,snippet/title,snippet/description,snippet/thumbnails/high/url)");
        }catch (IOException e){
            Log.d("YC", "Could not initialize: " + e);
        }
    }

    public List<VideoList> search(String Keywords){
        query.setQ(Keywords);
        query.setMaxResults(MAXRESULT);
        try {
            SearchListResponse response =query.execute();
            List<SearchResult> results =response.getItems();
            List<VideoList> items = new ArrayList<VideoList>();
            if (response !=null){
                items = setItemsList(results.iterator());
            }
            return items;
        }catch (IOException e){
            Log.d("YC", "Could not search: " + e);
            return null;
        }
    }

    //method for filling our array list
    private static List<VideoList> setItemsList(Iterator<SearchResult> iteratorSearchResults) {
        List<VideoList> tempSetItems =new ArrayList<>();

        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()){
            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();
            if (rId.getKind().equals("youtube#video")) {
                VideoList item = new VideoList();

                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getHigh();
                item.setId(singleVideo.getId().getVideoId());
                item.setTitle(singleVideo.getSnippet().getTitle());
                item.setDescription(singleVideo.getSnippet().getDescription());
                item.setThumbnailUrl(thumbnail.getUrl());

                //adding one Video item to temporary array list
                tempSetItems.add(item);
            }
        }

        return tempSetItems;
    }
}
