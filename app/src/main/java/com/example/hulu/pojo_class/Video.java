package com.example.hulu.pojo_class;

import java.io.Serializable;

public class Video implements Serializable {
    private String title;
    private String description;
    private String authur;
    private String videoUrl;
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthur() {
        return authur;
    }

    public void setAuthur(String authur) {
        this.authur = authur;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
