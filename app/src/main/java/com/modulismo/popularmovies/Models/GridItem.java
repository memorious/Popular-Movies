package com.modulismo.popularmovies.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class GridItem implements Parcelable{

    private String image;
    private String title;
    private String overview;
    private String release_date;
    private String vote_average;
    private String popularity;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeString(release_date);
        parcel.writeString(vote_average);
        parcel.writeString(popularity);
    }

    private GridItem(Parcel parcel){
        image = parcel.readString();
        title = parcel.readString();
        overview = parcel.readString();
        release_date = parcel.readString();
        vote_average = parcel.readString();
        popularity = parcel.readString();
    }


    // After implementing the `Parcelable` interface, we need to create the
    // `Parcelable.Creator<MyParcelable> CREATOR` constant for our class;
    // Notice how it has our class specified as its type.
    public static final Parcelable.Creator<GridItem> CREATOR
            = new Parcelable.Creator<GridItem>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public GridItem createFromParcel(Parcel parcel) {
            return new GridItem(parcel);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public GridItem[] newArray(int size) {
            return new GridItem[size];
        }
    };
    public GridItem() {
        super();
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {  this.overview = overview;}

    public String getReleaseDate() {
        return release_date;
    }
    public void setReleaseDate(String release) {
        this.release_date = release;
    }

    public String getVoteAverage() {
        return vote_average;
    }
    public void setVoteAverage(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPopularity() {
        return popularity;
    }
    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }
}