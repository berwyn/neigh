package com.ponyvillelive.android.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ponyvillelive.android.PVL;
import com.ponyvillelive.android.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import dagger.ObjectGraph;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 */
public class StationFragment extends Fragment {

    @InjectView(android.R.id.list)
    RecyclerView listView;
    private RecyclerView.Adapter       adapter;
    private RecyclerView.LayoutManager layoutManager;

    // TODO: Rename and change types of parameters
    public static StationFragment newInstance() {
        StationFragment fragment = new StationFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Change Adapter to display your content
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_station_list, container, false);

        ButterKnife.inject(this, view);
        listView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this.getActivity());
        listView.setLayoutManager(layoutManager);

        ObjectGraph og = ((PVL) getActivity().getApplication()).getGraph();
        adapter = og.get(StationAdapter.class);
        listView.setAdapter(adapter);

        return view;
    }

}
