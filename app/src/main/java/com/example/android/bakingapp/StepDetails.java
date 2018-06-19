package com.example.android.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jordanhaynes on 4/17/18.
 */

public class StepDetails implements Parcelable {

    public StepDetails(String shortDescription, String description, String videoURL) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
    }

    public String toString() {
        return shortDescription + " " + description + " " + videoURL;
    }

    // Using default package access
    final String shortDescription;
    final String description;
    final String videoURL;

    private StepDetails(Parcel in) {
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
    }

    public static final Parcelable.Creator<StepDetails> CREATOR = new Parcelable.Creator<StepDetails>() {
        public StepDetails createFromParcel(Parcel in) {
            return new StepDetails(in);
        }

        public StepDetails[] newArray(int size) {
            return new StepDetails[size];
        }
    };

    public int describeContents() {
        return 0;
    }
}
