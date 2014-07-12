package com.ponyvillelive.android.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.ponyvillelive.android.model.Station;
import com.ponyvillelive.android.net.API;

import javax.inject.Inject;

/**
 * Created by berwyn on 9 / 7 /14.
 */
public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {

    private Station[] stations;

    @Inject
    public StationAdapter(API api) {
        api.stations().first().subscribe((response) -> {
            if (response.status.equals("success")) {
                this.stations = response.result;
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return stations.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
