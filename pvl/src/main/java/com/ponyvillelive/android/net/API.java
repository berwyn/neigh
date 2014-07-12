package com.ponyvillelive.android.net;

import com.google.gson.Gson;
import com.ponyvillelive.android.model.SongResponse;
import com.ponyvillelive.android.model.StationListResponse;
import com.ponyvillelive.android.model.StationResponse;
import com.squareup.okhttp.OkHttpClient;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Client;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

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
    public void nowPlaying(Callback<?> callback);

    @GET("/nowplaying/index/id/{id}")
    public void nowPlaying(@Path("id") int id, Callback<?> callback);

    @GET("/nowplaying/index/station/{shortcode}")
    public void nowPlaying(@Path("shortcode") String shortcode, Callback<?> callback);

    /*
     * Schedule resources
     * TODO: Implement
     */

    @GET("/schedule")
    public void schedule(Callback<?> callback);

    @GET("/schedule/index/shortcode/{shortcode}")
    public void schedule(@Path("shortcode") String shortcode, Callback<?> callback);

    @GET("/schedule/conventions")
    public void conventionSchedule(Callback<?> callback);

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
    public void shows(Callback<?> callback);

    @GET("/show/index/id/{id}")
    public void show(@Path("id") int id, Callback<?> callback);

    @GET("/show/latest")
    public void latestShows(Callback<?> callback);

    public static class Builder {

        private String host         = "https://ponyvillelive.apiary.io/api";
        private Converter converter = new GsonConverter(new Gson());
        private Client client       = new OkClient();

        public void setHost(String host) { this.host = host; }

        public void setConverter(Converter converter) { this.converter = converter; }

        public void setClient(Client client) { this.client = client; }

        public API build() {
            return new RestAdapter.Builder()
                    .setEndpoint(host)
                    .setConverter(converter)
                    .setClient(client)
                    .build()
                    .create(API.class);
        }

    }

}
