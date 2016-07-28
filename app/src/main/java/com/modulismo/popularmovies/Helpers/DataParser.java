package com.modulismo.popularmovies.Helpers;

import android.util.Log;

import com.modulismo.popularmovies.Models.GridItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by alex on 7/18/16.
 */
public class DataParser {
    private final String LOG_TAG = DataParser.class.getSimpleName();
    private ArrayList<GridItem> mGridData;

    public ArrayList<GridItem> getMoviesDataFromJson(String moviesJsonStr)
            throws JSONException{

        this.mGridData = new ArrayList<GridItem>();
        final String OMM_LIST = "results";
        final String OMM_POSTER = "poster_path";
        final String OMM_TITLE = "title";
        final String OMM_OVERVIEW = "overview";
        final String OMM_RELEASE_DATE = "release_date";
        final String OMM_VOTE_AVERAGE = "vote_average";
        final String OMM_POPULARITY = "popularity";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray movieArray = moviesJson.getJSONArray(OMM_LIST);
        GridItem item;

        String[] resultStrs = new String[movieArray.length()];
        for(int i = 0; i < movieArray.length(); i++) {
            String poster;
            String title;
            String overview;
            String release_date;
            String vote_average;
            String popularity;

            JSONObject movie = movieArray.getJSONObject(i);
            poster = movie.getString(OMM_POSTER);
            title = movie.getString(OMM_TITLE);
            overview = movie.getString(OMM_OVERVIEW);
            release_date = movie.getString(OMM_RELEASE_DATE);
            vote_average = movie.getString(OMM_VOTE_AVERAGE);
            popularity = movie.getString(OMM_POPULARITY);
            if(release_date != null){
                try {
//                    DateFormat format = new SimpleDateFormat("EEE, MMM d, ''yy");
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(release_date);
                    String formattedDate = new SimpleDateFormat("EEE, MMM d, ''yy").format(date);
                    release_date = formattedDate.toString();
                }catch (ParseException e){
                    Log.v(LOG_TAG, e.getMessage());
                }
            }
            item = new GridItem();
            item.setImage("http://image.tmdb.org/t/p/w185" + poster);
            item.setTitle(title);
            item.setOverview(overview);
            item.setReleaseDate("Released on " + release_date);
            item.setVoteAverage("Vote Average " + vote_average);
            item.setPopularity("Popularity " + popularity);
            mGridData.add(item);
        }

        return mGridData;
    }
}
