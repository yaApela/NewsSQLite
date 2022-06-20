package com.example.newssqlite;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsModel implements Parcelable {
    public int id;
    public String label;
    public String news;
    public String author;
    public String date;

    public NewsModel(int id, String lable, String news, String author, String date) {
        this.id = id;
        this.label = lable;
        this.news = news;
        this.author = author;
        this.date = date;
    }

    protected NewsModel(Parcel in) {
        id = in.readInt();
        label = in.readString();
        news = in.readString();
        author = in.readString();
        date = in.readString();
    }

    public static final Creator<NewsModel> CREATOR = new Creator<NewsModel>() {
        @Override
        public NewsModel createFromParcel(Parcel in) {
            return new NewsModel(in);
        }

        @Override
        public NewsModel[] newArray(int size) {
            return new NewsModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(label);
        parcel.writeString(news);
        parcel.writeString(author);
        parcel.writeString(date);
    }
}
