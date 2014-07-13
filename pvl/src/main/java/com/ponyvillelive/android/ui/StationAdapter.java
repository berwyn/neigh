package com.ponyvillelive.android.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ponyvillelive.android.R;
import com.ponyvillelive.android.model.Station;
import com.ponyvillelive.android.net.API;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by berwyn on 9 / 7 /14.
 */
public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {

    private       Station[] stations;
    private       Context   context;
    private final API       api;

    @Inject
    public StationAdapter(API api) {
        this.api = api;
        stations = new Station[0];
        api.stations()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (response) -> {
                            Log.d("PVL STATION ADAPTER", "Request succeeded");
                            if (response.status.equals("success")) {
                                this.stations = response.result;
                                notifyDataSetChanged();
                            }
                        }, (error) -> {
                            Log.e("PVL STATION ADAPTER", "Request failed", error);
                            // TODO: Implement
                        }
                );
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if(context == null) {
            context = viewGroup.getContext();
        }

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_station_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int idx) {
        viewHolder.name.setText(stations[idx].name);
        viewHolder.genre.setText(stations[idx].genre);
        Picasso.with(context)
                .load(stations[idx].imageUrl)
                .placeholder(R.drawable.ic_logo_square)
                .into(viewHolder.icon);
    }

    @Override
    public int getItemCount() {
        return stations.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.img_station_icon)
        ImageView icon;
        @InjectView(R.id.text_station_name)
        TextView  name;
        @InjectView(R.id.text_station_genre)
        TextView  genre;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
