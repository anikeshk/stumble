package edu.northeastern.numad24fa_group15project.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import java.util.List;

public class Event implements Parcelable {
    private String id;
    private String title;
    private String description;
    private Timestamp dateTime;
    private String location;
    private String imageUrl;
    private List<String> interests;
    private Boolean isRecommended;
    private String organizer;

    public Event() {}

    protected Event(Parcel in) {
        id = in.readString();
        title = in.readString();
        description = in.readString();
        dateTime = in.readParcelable(Timestamp.class.getClassLoader());
        location = in.readString();
        imageUrl = in.readString();
        interests = in.createStringArrayList();
        isRecommended = in.readByte() != 0;
        organizer = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public String getLocation() {
        return location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getInterests() {
        return interests;
    }

    public Boolean getIsRecommended() {
        return isRecommended;
    }

    public String getOrganizer() {
        return organizer;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeParcelable(dateTime, i);
        parcel.writeString(location);
        parcel.writeString(imageUrl);
        parcel.writeStringList(interests);
        parcel.writeByte((byte) (isRecommended ? 1 : 0));
        parcel.writeString(organizer);
    }

    @NonNull
    @Override
    public String toString() {
        return getTitle() + " | " + getIsRecommended() + " | " + getDescription();
    }
}