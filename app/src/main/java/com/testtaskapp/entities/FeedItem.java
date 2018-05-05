package com.testtaskapp.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

@Entity
public class FeedItem {
    @Id(autoincrement = true)
    private long dbId;

    @Expose
    @SerializedName("id")
    private long id;

    @Expose
    @SerializedName("artistName")
    private String artistName;

    @Expose
    @SerializedName("name")
    private String name;

    @Expose
    @SerializedName("kind")
    private String kind;

    @Expose
    @SerializedName("artistId")
    private String artistId;

    @Expose
    @SerializedName("artworkUrl100")
    private String artworkUrl100;

    @Expose
    @SerializedName("url")
    private String url;

    @Generated(hash = 298406192)
    public FeedItem(long dbId, long id, String artistName, String name, String kind,
            String artistId, String artworkUrl100, String url) {
        this.dbId = dbId;
        this.id = id;
        this.artistName = artistName;
        this.name = name;
        this.kind = kind;
        this.artistId = artistId;
        this.artworkUrl100 = artworkUrl100;
        this.url = url;
    }

    @Generated(hash = 605716718)
    public FeedItem() {
    }

    public long getDbId() {
        return this.dbId;
    }

    public void setDbId(long dbId) {
        this.dbId = dbId;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArtistName() {
        return this.artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getArtistId() {
        return this.artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public String getArtworkUrl100() {
        return this.artworkUrl100;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
