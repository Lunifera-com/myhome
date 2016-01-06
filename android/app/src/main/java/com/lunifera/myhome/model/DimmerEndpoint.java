package com.lunifera.myhome.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by bernhardedler on 08.12.15.
 */
public class DimmerEndpoint extends Endpoint implements Parcelable{

private int state;

    public DimmerEndpoint(String name, String id, String groupId, String topic, int state) {
        super(name, id, groupId, topic);
        this.state = state;
    }

    public DimmerEndpoint(Parcel in) {
        super(in);
        this.state = in.readInt();

    }


    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(state);
    }

    @SuppressWarnings("unused")
    public static final Creator<DimmerEndpoint> CREATOR = new Creator<DimmerEndpoint>() {
        @Override
        public DimmerEndpoint createFromParcel(Parcel in) {
            return new DimmerEndpoint(in);
        }

        @Override
        public DimmerEndpoint[] newArray(int size) {
            return new DimmerEndpoint[size];
        }
    };
}
