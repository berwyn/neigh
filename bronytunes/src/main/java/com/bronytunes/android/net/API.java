package com.bronytunes.android.net;

import com.google.gson.Gson;

import retrofit.RestAdapter;
import retrofit.android.AndroidApacheClient;
import retrofit.client.Client;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by berwyn on 8 / 8 /14.
 */
public interface API {

    @GET("/retrieve_songs.php?client_type=android")
    public Observable<?> getSongs();

    @GET("/songs/{id}.json")
    public Observable<?> getSong(@Path("id") int id);

    public static class Builder {

        private String    host      = "http://bronytunes.com";
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
