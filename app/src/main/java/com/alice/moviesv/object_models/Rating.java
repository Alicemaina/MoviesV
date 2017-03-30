package com.alice.moviesv.object_models;

/**
 * Created by alice on 3/29/17.
 */

public class Rating {
    public String id;
    public String author;
    public String url;
    public String description;

    public Rating(String id, String author, String url, String description) {
        this.id = id;
        this.author = author;
        this.url = url;
        this.description = description;
    }
}

