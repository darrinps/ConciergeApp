package com.aa.android.common.data;

import android.os.Parcel;
import android.support.annotation.NonNull;

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
        public SharedQuestion createFromDataMap(DataMap source) {
            return new SharedQuestion(source);
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
    private static final String KEY_ANSWERS = "answers";

    @NonNull private final String mText;
    @NonNull private final List<String> mAnswers;

    public SharedQuestion(String text, List<String> answers) {
        this.mText = text == null ? "" : text;
        this.mAnswers = answers == null ? Collections.<String>emptyList() : answers;
    }

    private SharedQuestion(DataMap in) {
        this(in.getString(KEY_TEXT),
                in.getStringArrayList(KEY_ANSWERS));
    }

    private SharedQuestion(Parcel in) {
        this.mText = in.readString();
        this.mAnswers = new ArrayList<>();
        in.readStringList(mAnswers);
    }

    @Override
    public void writeToDataMap(DataMap dataMap) {
        dataMap.putString(KEY_TEXT, mText);
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
        dest.writeStringList(mAnswers);
    }

    @NonNull
    public String getText() {
        return mText;
    }

    @NonNull
    public List<String> getAnswers() {
        return mAnswers;
    }
}
