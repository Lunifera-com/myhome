package com.lunifera.myhome.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.map.annotate.JsonRootName;

/**
 * Created by bernhardedler on 08.12.15.
 */
@JsonTypeInfo(include= JsonTypeInfo.As.PROPERTY, use= JsonTypeInfo.Id.CLASS, property = "@class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = OnOffEndpoint.class, name = "OnOffEndpoint"),
        @JsonSubTypes.Type(value = RGBWEndpoint.class, name = "RGBWEndpoint")
}
)
public abstract class Endpoint implements Parcelable{

    private String name;
    private String id;
    private String groupId;
    private String topic;

    public Endpoint(){

    }

    public Endpoint(String name, String id, String groupId, String topic) {
        this.name = name;
        this.id = id;
        this.groupId = groupId;
        this.topic = topic;
    }

    public Endpoint(Parcel in) {
        this.name = in.readString();
        this.id = in.readString();
        this.groupId = in.readString();
        this.topic = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(id);
        dest.writeString(groupId);
        dest.writeString(topic);
    }
}




