package com.modulismo.popularmovies.Fragments;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.modulismo.popularmovies.Activities.DetailActivity;
import com.modulismo.popularmovies.Adapters.PostersAdapter;
import com.modulismo.popularmovies.Models.GridItem;
import com.modulismo.popularmovies.R;
import com.modulismo.popularmovies.Helpers.Tasks.FetchMovies;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    //    private PostersAdapter mMoviesAdapter;
    private ArrayList<GridItem> mGridData;
    private PostersAdapter mGridAdapter;
    private GridView mGridView;
    private String sort_by;
    private int savedGridPosition = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.sort_by = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_sort_by_popularity) {
            this.sort_by = getResources().getString(R.string.action_sort_by_popularity_value);
            Log.v(LOG_TAG, this.sort_by);
            updateMovies();
            return true;
        }

        if (id == R.id.action_sort_by_rating) {
            this.sort_by = getResources().getString(R.string.action_sort_by_rating_value);
            Log.v(LOG_TAG, this.sort_by);
            updateMovies();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        mGridView = (GridView) rootView.findViewById(R.id.gridview_activity);
        mGridData = new ArrayList<GridItem>();
        mGridAdapter = new PostersAdapter(getActivity(), R.layout.grid_item_activity, mGridData);
        mGridView.setAdapter(mGridAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                GridItem item = mGridAdapter.getItem(position);

                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra("mData", item);
                startActivity(intent);

            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }


    public void updateMovies() {
        FetchMovies moviesTask = new FetchMovies(getActivity(), this.sort_by);
        moviesTask.execute();
        try {
            ArrayList<GridItem> result = moviesTask.get();
            if (result != null) {
                mGridAdapter.clear();
                mGridAdapter = new PostersAdapter(getActivity(), R.layout.grid_item_activity, result);
                mGridView.setAdapter(mGridAdapter);
            }
        } catch (InterruptedException ie) {
            Log.e(LOG_TAG, ie.getMessage());
            ie.printStackTrace();
        } catch (ExecutionException ee) {
            Log.e(LOG_TAG, ee.getMessage());
            ee.printStackTrace();
        }

    }
}
