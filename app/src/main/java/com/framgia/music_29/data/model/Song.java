package com.framgia.music_29.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

public class Song implements Parcelable {

    private String mArtworkUrl;
    private String mId;
    private String mTitle;
    private String mUri;
    private boolean mDownloadable;
    private boolean mStreamable;
    private String mDownloadUrl;
    private String mUserFullName;

    public Song(JSONObject track) throws JSONException {
        mId = String.valueOf(track.getLong(JSONKey.ID));
        mTitle = track.getString(JSONKey.TITLE);
        mArtworkUrl = track.getString(JSONKey.ARTWORK_URL);
        mDownloadable = track.getBoolean(JSONKey.DOWNLOAD_ABLE);
        mStreamable = track.getBoolean(JSONKey.STREAM_ABLE);
        mDownloadUrl = track.getString(JSONKey.DOWNLOAD_URL);
        mUri = track.getString(JSONKey.URI);
        mUserFullName =
                track.getJSONObject(JSONKey.USER).getString(JSONKey.FULL_USER);
    }

    protected Song(Parcel in) {
        mArtworkUrl = in.readString();
        mId = in.readString();
        mTitle = in.readString();
        mUri = in.readString();
        mDownloadable = in.readByte() != 0;
        mStreamable = in.readByte() != 0;
        mDownloadUrl = in.readString();
        mUserFullName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mArtworkUrl);
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mUri);
        dest.writeByte((byte) (mDownloadable ? 1 : 0));
        dest.writeByte((byte) (mStreamable ? 1 : 0));
        dest.writeString(mDownloadUrl);
        dest.writeString(mUserFullName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

    public String getArtworkUrl() {
        return mArtworkUrl;
    }

    public void setArtworkUrl(String artworkUrl) {
        mArtworkUrl = artworkUrl;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public boolean isDownloadable() {
        return mDownloadable;
    }

    public void setDownloadable(boolean downloadable) {
        mDownloadable = downloadable;
    }

    public boolean isStreamable() {
        return mStreamable;
    }

    public void setStreamable(boolean streamable) {
        mStreamable = streamable;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public String getUserFullName() {
        return mUserFullName;
    }

    public void setUserFullName(String userFullName) {
        mUserFullName = userFullName;
    }

    public static class JSONKey {
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String ARTWORK_URL = "artwork_url";
        public static final String DOWNLOAD_ABLE = "downloadable";
        public static final String DOWNLOAD_URL = "download_url";
        public static final String URI = "uri";
        public static final String USER = "user";
        public static final String FULL_USER = "full_name";
        public static final String STREAM_ABLE = "streamable";
    }
}