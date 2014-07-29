package com.ponyvillelive.android.ui;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemClickedListener;
import android.support.v17.leanback.widget.OnItemSelectedListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.ponyvillelive.android.PVL;
import com.ponyvillelive.android.R;
import com.ponyvillelive.android.model.Station;
import com.ponyvillelive.android.net.API;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import javax.inject.Inject;


public class TVBrowseFragment extends BrowseFragment {
    private static final String TAG = "TVBrowseFragment";

    private static final int NUM_ROWS = 4;
    private static final int NUM_COLS = 15;

    private final Handler mHandler = new Handler();

    private ArrayObjectAdapter rowsAdapter;
    private ArrayObjectAdapter radioAdapter;
    private ArrayObjectAdapter videoAdapter;

    private Drawable            mDefaultBackground;
    private Target              mBackgroundTarget;
    private DisplayMetrics      mMetrics;
    private Timer               mBackgroundTimer;
    private URI                 mBackgroundURI;

    CardPresenter mCardPresenter;
    @Inject
    API api;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onActivityCreated(savedInstanceState);

        ((PVL) getActivity().getApplication()).getGraph().inject(this);

        prepareBackgroundManager();
        setupUIElements();
        loadRows();
        setupEventListeners();
    }

    private void loadRows() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        mCardPresenter = new CardPresenter();

        radioAdapter = new ArrayObjectAdapter(mCardPresenter);
        videoAdapter = new ArrayObjectAdapter(mCardPresenter);
        HeaderItem radioHeader = new HeaderItem(getString(R.string.header_radio), "");
        HeaderItem videoHeader = new HeaderItem(getString(R.string.header_video), "");

        rowsAdapter.add(new ListRow(radioHeader, radioAdapter));
        rowsAdapter.add(new ListRow(videoHeader, videoAdapter));

        api.stations().subscribe((response) -> {
           for(Station station : response.result) {
               if(station.category.equals(Station.CATEGORY_AUDIO)) {
                   radioAdapter.add(station);
               } else {
                   videoAdapter.add(station);
               }
           }
        });

        setAdapter(rowsAdapter);
    }

    private void prepareBackgroundManager() {
        BackgroundManager backgroundManager = BackgroundManager.getInstance(getActivity());
        backgroundManager.attach(getActivity().getWindow());
        mBackgroundTarget = new PicassoBackgroundManagerTarget(backgroundManager);

        mDefaultBackground = getResources().getDrawable(R.drawable.default_background);

        mMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void setupUIElements() {
        setTitle(getString(R.string.app_name));
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        // set fastLane (or headers) background color
        setBrandColor(getResources().getColor(R.color.colorPrimary));
        // set search icon color
        setSearchAffordanceColor(getResources().getColor(R.color.colorPrimaryDark));
    }

    private void setupEventListeners() {
//        setOnItemSelectedListener(getDefaultItemSelectedListener());
//        setOnItemClickedListener(getDefaultItemClickedListener());
    }

    protected OnItemSelectedListener getDefaultItemSelectedListener() {
        return (item, row) -> {
            if(item instanceof Station) {
                mBackgroundURI = URI.create(((Station) item).imageUrl);
                startBackgroundTimer();
            }
        };
    }

    protected OnItemClickedListener getDefaultItemClickedListener() {
        return (item, row) -> {
            // TODO: Start the player
        };
    }

    protected void setDefaultBackground(Drawable background) {
        mDefaultBackground = background;
    }

    protected void setDefaultBackground(int resourceId) {
        mDefaultBackground = getResources().getDrawable(resourceId);
    }

    protected void updateBackground(URI uri) {
        Picasso.with(getActivity())
                .load(uri.toString())
                .resize(mMetrics.widthPixels, mMetrics.heightPixels)
                .centerCrop()
                .error(mDefaultBackground)
                .into(mBackgroundTarget);
    }

    protected void updateBackground(Drawable drawable) {
        BackgroundManager.getInstance(getActivity()).setDrawable(drawable);
    }

    protected void clearBackground() {
        BackgroundManager.getInstance(getActivity()).setDrawable(mDefaultBackground);
    }

    private void startBackgroundTimer() {
        if (null != mBackgroundTimer) {
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();
        mBackgroundTimer.schedule(new UpdateBackgroundTask(), 300);
    }

    private class UpdateBackgroundTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(() -> {
                if (mBackgroundURI != null) {
                    updateBackground(mBackgroundURI);
                }
            });

        }
    }
}
