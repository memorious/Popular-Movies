package com.modulismo.popularmovies.Helpers.Tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.modulismo.popularmovies.Helpers.DataParser;
import com.modulismo.popularmovies.Models.GridItem;
import com.modulismo.popularmovies.R;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FetchMovies extends AsyncTask<String, Void, ArrayList<GridItem>> {
    private final String LOG_TAG = FetchMovies.class.getSimpleName();

    private Context context;
    private String sort_by;

    public FetchMovies(Context context, String sort_by){
        this.context = context;
        this.sort_by = sort_by;
    }
    @Override
    protected ArrayList<GridItem> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String moviesJsonStr = null;

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
        if(this.sort_by == null){
            this.sort_by = sharedPreferences.getString(this.context.getString(R.string.pref_sort_key),
            this.context.getString(R.string.pref_sort_default));
        }

        String key = sharedPreferences.getString(this.context.getString(R.string.pref_apikey_key),
                this.context.getString(R.string.pref_apikey_default));

        try {

            final String MOVIES_BASE_URL = "https://api.themoviedb.org/3/movie/" + this.sort_by + "?";
            final String TYPE_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIES_BASE_URL).buildUpon()
                    .appendQueryParameter(TYPE_PARAM, key)
                    .build();

            URL url = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }
            moviesJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("MainActivityFragment", "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("MainActivityFragment", "Error closing stream", e);
                }
            }
        }

        try {
            DataParser dataParser = new DataParser();
            return dataParser.getMoviesDataFromJson(moviesJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

}