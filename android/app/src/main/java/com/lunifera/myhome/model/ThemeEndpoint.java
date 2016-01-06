package com.lunifera.myhome.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bernhardedler on 08.12.15.
 */
public class ThemeEndpoint extends Endpoint implements Parcelable{

private List<Endpoint> endpoints;

    public ThemeEndpoint(String name, String id, String groupId, String topic, List<Endpoint> endpoints) {
        super(name, id, groupId, topic);
        this.endpoints = endpoints;
    }

    public ThemeEndpoint(Parcel in) {
        super(in);
        endpoints = new ArrayList<>();
        in.readList(endpoints, null);

    }


    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeList(endpoints);
    }

    @SuppressWarnings("unused")
    public static final Creator<ThemeEndpoint> CREATOR = new Creator<ThemeEndpoint>() {
        @Override
        public ThemeEndpoint createFromParcel(Parcel in) {
            return new ThemeEndpoint(in);
        }

        @Override
        public ThemeEndpoint[] newArray(int size) {
            return new ThemeEndpoint[size];
        }
    };
}
