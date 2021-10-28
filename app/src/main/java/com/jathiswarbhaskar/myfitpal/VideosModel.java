package com.jathiswarbhaskar.myfitpal;

class VideosModel {
    String title,description,link,Id;

    public VideosModel() {

    }

    public VideosModel(String title, String description, String link, String id) {
        this.title = title;
        this.description = description;
        this.link = link;
        Id = id;
    }

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

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}

