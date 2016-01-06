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
@JsonRootName(value = "OnOffEndpoint")
public class OnOffEndpoint extends Endpoint implements Parcelable{
    @JsonProperty("state")
private boolean state;

    public OnOffEndpoint() {
    }

    public OnOffEndpoint(String name, String id, String groupId, String topic, boolean state) {
        super(name, id, groupId, topic);
        this.state = state;
    }

    public OnOffEndpoint(Parcel in) {
        super(in);
        this.state = in.readByte() != 0;

    }


    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeByte((byte) (state ? 1 : 0));
    }

    @SuppressWarnings("unused")
    public static final Creator<OnOffEndpoint> CREATOR = new Creator<OnOffEndpoint>() {
        @Override
        public OnOffEndpoint createFromParcel(Parcel in) {
            return new OnOffEndpoint(in);
        }

        @Override
        public OnOffEndpoint[] newArray(int size) {
            return new OnOffEndpoint[size];
        }
    };
}
