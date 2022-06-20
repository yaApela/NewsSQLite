package com.example.newssqlite;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

public class UserModel implements Parcelable {
    public String login;
    public String password;
    public Boolean isAdmin;

    public UserModel(String login, String password, Boolean isAdmin) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected UserModel(Parcel in) {
        this.login = in.readString();
        this.password = in.readString();
        this.isAdmin = in.readBoolean();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(login);
        parcel.writeString(password);
        parcel.writeBoolean(isAdmin );
    }
}
