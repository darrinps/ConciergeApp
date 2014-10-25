package com.aa.android.common.data;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.aa.android.util.ImageUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by layne on 10/24/14.
 */
public class SharedQuestion extends SharedData {
    public static final String PUT_PATH_PREFIX = "/question";
    public static final Creator<SharedQuestion> CREATOR = new Creator<SharedQuestion>() {
        @Override
        public SharedQuestion createFromDataMap(GoogleApiClient client, DataMap source) {
            return new SharedQuestion(client, source);
        }

        @Override
        public SharedQuestion createFromParcel(Parcel source) {
            return new SharedQuestion(source);
        }

        @Override
        public SharedQuestion[] newArray(int size) {
            return new SharedQuestion[size];
        }
    };

    private static final String KEY_TEXT = "text";
    private static final String KEY_BACKGROUND = "background";
    private static final String KEY_ANSWERS = "answers";

    @NonNull private final String mText;
    private final Bitmap mBackground;
    @NonNull private final List<String> mAnswers;

    public SharedQuestion(String text, Bitmap background, List<String> answers) {
        this.mText = text == null ? "" : text;
        this.mBackground = background;
        this.mAnswers = answers == null ? Collections.<String>emptyList() : answers;
    }

    private SharedQuestion(GoogleApiClient apiClient, DataMap in) {
        this(in.getString(KEY_TEXT),
                ImageUtils.fromAsset(apiClient, in.getAsset(KEY_BACKGROUND)),
                in.getStringArrayList(KEY_ANSWERS));
    }

    private SharedQuestion(Parcel in) {
        this.mText = in.readString();
        this.mBackground = in.readParcelable(Bitmap.class.getClassLoader());
        this.mAnswers = new ArrayList<>();
        in.readStringList(mAnswers);
    }

    @Override
    public void writeToDataMap(DataMap dataMap) {
        dataMap.putString(KEY_TEXT, mText);
        dataMap.putAsset(KEY_BACKGROUND, Asset.createFromBytes(ImageUtils.convert(mBackground)));
        dataMap.putStringArrayList(KEY_ANSWERS, new ArrayList<>(mAnswers));
    }

    @Override
    public String getPath() {
        return PUT_PATH_PREFIX;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mText);
        dest.writeParcelable(mBackground, 0);
        dest.writeStringList(mAnswers);
    }

    @NonNull
    public String getText() {
        return mText;
    }

    @Nullable
    public Bitmap getBackground() {
        return mBackground;
    }

    @NonNull
    public List<String> getAnswers() {
        return mAnswers;
    }
}
