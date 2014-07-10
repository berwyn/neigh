package com.ponyvillelive.android.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by berwyn on 9 / 7 /14.
 */
public class Song {

    public String id;
    public String text;
    public String artist;
    public String title;
    @SerializedName("created_at")
    public Date createdAt;

}
