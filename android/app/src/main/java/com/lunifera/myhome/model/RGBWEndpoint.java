package com.lunifera.myhome.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Created by bernhardedler on 08.12.15.
 */
@JsonTypeInfo(include= JsonTypeInfo.As.PROPERTY, use= JsonTypeInfo.Id.CLASS)
@JsonRootName(value = "RGBWEndpoint")
public class RGBWEndpoint extends Endpoint implements Parcelable{
    @JsonProperty("state")
private String state;

    public RGBWEndpoint(){

    }

    public RGBWEndpoint(String name, String id, String groupId, String topic, String state) {
        super(name, id, groupId, topic);
        this.state = state;
    }

    public RGBWEndpoint(Parcel in) {
        super(in);
        this.state = in.readString();

    }


    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(state);
    }

    @SuppressWarnings("unused")
    public static final Creator<RGBWEndpoint> CREATOR = new Creator<RGBWEndpoint>() {
        @Override
        public RGBWEndpoint createFromParcel(Parcel in) {
            return new RGBWEndpoint(in);
        }

        @Override
        public RGBWEndpoint[] newArray(int size) {
            return new RGBWEndpoint[size];
        }
    };
}
