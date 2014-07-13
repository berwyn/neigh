package com.ponyvillelive.android.net;

import com.google.gson.Gson;
import com.ponyvillelive.android.model.SongResponse;
import com.ponyvillelive.android.model.StationListResponse;
import com.ponyvillelive.android.model.StationResponse;

import retrofit.RestAdapter;
import retrofit.android.AndroidApacheClient;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

//import com.squareup.okhttp.OkHttpClient;

/**
 * API contract for Ponyville Live! as documented at
 * <a href="http://docs.ponyvillelive.apiary.io/">Apiary</a>
 */
public interface API {

    /*
     * Now Playing resources
     * TODO: Implement
     */

    @GET("/nowplaying")
    public Observable<?> nowPlaying();

    @GET("/nowplaying/index/id/{id}")
    public Observable<?> nowPlaying(@Path("id") int id);

    @GET("/nowplaying/index/station/{shortcode}")
    public Observable<?> nowPlaying(@Path("shortcode") String shortcode);

    /*
     * Schedule resources
     * TODO: Implement
     */

    @GET("/schedule")
    public Observable<?> schedule();

    @GET("/schedule/index/shortcode/{shortcode}")
    public Observable<?> schedule(@Path("shortcode") String shortcode);

    @GET("/schedule/conventions")
    public Observable<?> conventionSchedule();

    /*
     * Song resources
     */

    @GET("/song/index/id/{songId}")
    public Observable<SongResponse> song(@Path("songId") int songId);

    /*
     * Station resources
     */

    @GET("/station/list")
    public Observable<StationListResponse> stations();

    @GET("/station/list/category/{category}")
    public Observable<StationListResponse> stations(@Path("category") String category);

    @GET("/station/index/id/{id}")
    public Observable<StationResponse> station(@Path("id") int id);

    @GET("/station/index/station/{shortcode}")
    public Observable<StationResponse> station(@Path("shortcode") String shortcode);

    /*
     * Shows/Podcasts
     * TODO: Implement
     */

    @GET("/show/index")
    public Observable<?> shows();

    @GET("/show/index/id/{id}")
    public Observable<?> show(@Path("id") int id);

    @GET("/show/latest")
    public Observable<?> latestShows();

    public static class Builder {

        private String    host      = "https://ponyvillelive.apiary.io/api";
        private Converter converter = new GsonConverter(new Gson());
        /*
         * For Android L build LVP79 - https://github.com/square/okhttp/issues/967
         * Revert to OkClient with L update
         */
        private Client    client    = new AndroidApacheClient();

        public void setHost(String host) {
            this.host = host;
        }

        public void setConverter(Converter converter) {
            this.converter = converter;
        }

        public void setClient(Client client) {
            this.client = client;
        }

        public API build() {
            return new RestAdapter.Builder()
                    .setEndpoint(host)
                    .setConverter(converter)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(client)
                    .build()
                    .create(API.class);
        }

    }

}
