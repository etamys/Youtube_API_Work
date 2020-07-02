package com.example.song_player;

public class VideoList {
    private String title;
    private  String description;
    private String thumbnailUrl;
    private String id;
    private int mAudioResource;

    public  String  getId(){return  id;}
    public void setId(String id){
        this.id=id;
    }

    public String getTitle(){return  title;}
    public void setTitle(String title){
        this.title=title;
    }

    public String getDescription(){return description;}
    public void setDescription(String description){
        this.description=description;
    }

    public String getThumbnailUrl(){return thumbnailUrl;}
    public void setThumbnailUrl(String thumbnailUrl){
        this.thumbnailUrl=thumbnailUrl;
    }

    public int getmAudioResource()
    {
        return mAudioResource;
    }

}
