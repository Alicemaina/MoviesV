package com.alice.moviesv.object_models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alice on 3/29/17.
 */

public class Trailer {
    public String id;
    public String key;
    public String name;
    public String site;

    public Trailer(String id, String key, String name, String site) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
    }
}