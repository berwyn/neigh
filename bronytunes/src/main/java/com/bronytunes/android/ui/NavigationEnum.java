package com.bronytunes.android.ui;

import android.support.annotation.StringRes;

import com.bronytunes.android.R;

/**
 * Created by berwyn on 8 / 8 /14.
 */
public enum NavigationEnum {

    FEATURED(R.string.title_featured),
    BEST(R.string.title_best),
    NEW(R.string.title_newest),
    LIBRARY(R.string.title_library);

    NavigationEnum(@StringRes int displayRes) {
        this.displayRes = displayRes;
    }

    private int displayRes;

    public int getDisplayRes() {
        return displayRes;
    }
}
