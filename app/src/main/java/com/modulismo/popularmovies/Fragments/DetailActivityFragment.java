package com.modulismo.popularmovies.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.modulismo.popularmovies.Models.GridItem;
import com.modulismo.popularmovies.R;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private String mTitleString;
    private String mOverviewString;
    private String mReleaseDateString;
    private String mVoteAverageString;
    private String mPopularityString;

    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            GridItem item = (GridItem) getActivity().getIntent().getParcelableExtra("mData");
            mTitleString = item.getTitle();
            mOverviewString = item.getOverview();
            mReleaseDateString = item.getReleaseDate();
            mVoteAverageString = item.getVoteAverage();
            mPopularityString = item.getPopularity();

            ((TextView) rootView.findViewById(R.id.detail_text))
                    .setText(mTitleString);

            ((TextView) rootView.findViewById(R.id.detail_overview))
                    .setText(mOverviewString);

            ((TextView) rootView.findViewById(R.id.detail_release_date))
                    .setText(mReleaseDateString);

            ((TextView) rootView.findViewById(R.id.detail_vote_average))
                    .setText(mVoteAverageString);

            ((TextView) rootView.findViewById(R.id.detail_popularity))
                    .setText(mPopularityString);

            ImageView imageView = (ImageView) rootView.findViewById(R.id.detail_image);
            Picasso.with(getActivity())
                    .load(item.getImage())
                    .into(imageView);
        }
        return rootView;
    }
}
