package com.ponyvillelive.android.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by berwyn on 7/9/14.
 */
public class Station {

    public int id;
    public String name;
    public String shortcode;
    public String genre;
    public String category;
    public String type;
    @SerializedName("image_url")
    public String imageUrl;
    @SerializedName("stream_url")
    public String streamUrl;
    @SerializedName("twitter_url")
    public String twitterUrl;
    public String irc;

}
