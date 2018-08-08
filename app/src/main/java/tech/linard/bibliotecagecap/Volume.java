package tech.linard.bibliotecagecap;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

public class Volume implements Parcelable {
    private String id;
    private String title;
    private String subtitle;
    private String authors;
    private String publisher;
    private String publishedDate;
    private String description;
    private int pageCount;
    private String categories;
    private HashMap industryIdentifiers;
    private String smallThumbnail;
    private String thumbnail;

    public Volume(String id, String title, String subtitle, String authors, String publisher, String publishedDate, String description, int pageCount, String categories, HashMap industryIdentifiers, String smallThumbnail, String thumbnail) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.pageCount = pageCount;
        this.categories = categories;
        this.industryIdentifiers = industryIdentifiers;
        this.smallThumbnail = smallThumbnail;
        this.thumbnail = thumbnail;
    }

    protected Volume(Parcel in) {
        id = in.readString();
        title = in.readString();
        subtitle = in.readString();
        authors = in.readString();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();
        pageCount = in.readInt();
        categories = in.readString();
        smallThumbnail = in.readString();
        thumbnail = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(subtitle);
        dest.writeString(authors);
        dest.writeString(publisher);
        dest.writeString(publishedDate);
        dest.writeString(description);
        dest.writeInt(pageCount);
        dest.writeString(categories);
        dest.writeString(smallThumbnail);
        dest.writeString(thumbnail);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Volume> CREATOR = new Creator<Volume>() {
        @Override
        public Volume createFromParcel(Parcel in) {
            return new Volume(in);
        }

        @Override
        public Volume[] newArray(int size) {
            return new Volume[size];
        }
    };
}
