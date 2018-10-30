package com.charlyge.android.debakingapp.model;

import java.io.Serializable;

public class Steps implements Serializable{
  private int id;
  private String shortDescription;
  private String description;
  private String videoURL;
  private String thumbnailURL;


  public Steps(){}
  public Steps(int id, String shortDescription,String description,String videoURL,String thumbnailURL){
      this.description = description;
      this.thumbnailURL =thumbnailURL;
      this.shortDescription = shortDescription;
      this.id = id;
      this.videoURL = videoURL;


  }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getVideoURL() {
        return videoURL;
    }
}
