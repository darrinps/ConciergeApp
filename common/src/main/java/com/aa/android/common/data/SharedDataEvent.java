package com.aa.android.common.data;

import android.net.Uri;

import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMapItem;

/**
 * Helper class that wraps a {@link com.google.android.gms.wearable.DataEvent} in order to get data more easily.
 * <p/>
 * Created by layne on 10/16/14.
 */
public class SharedDataEvent
{
    private final DataEvent mDataEvent;

    private SharedDataEvent(DataEvent dataEvent)
    {
        this.mDataEvent = dataEvent;
    }

    /**
     * Creates a SharedDataEvent from a DataEvent.
     *
     * @param dataEvent
     *         the data event
     *
     * @return the shared data event
     *
     * @throws IllegalArgumentException
     *         if the {@code dataEvent} is null.
     */
    public static SharedDataEvent from(DataEvent dataEvent)
    {
        if (dataEvent == null)
        {
            throw new IllegalArgumentException("The data event must not be null");
        }
        return new SharedDataEvent(dataEvent);
    }

    /**
     * Returns {@link com.google.android.gms.wearable.DataEvent#getType()}.
     *
     * @return the data event type
     */
    public int getType()
    {
        return mDataEvent.getType();
    }

    /**
     * Returns {@link com.google.android.gms.wearable.DataEvent#getDataItem()}.
     *
     * @return the data item
     */
    public DataItem getDataItem()
    {
        return mDataEvent.getDataItem();
    }

    /**
     * Returns {@link DataMapItem#fromDataItem(com.google.android.gms.wearable.DataItem)}.
     *
     * @return the data map item
     */
    public DataMapItem getDataMapItem()
    {
        return DataMapItem.fromDataItem(getDataItem());
    }

    /**
     * Returns {@link com.google.android.gms.wearable.DataItem#getUri()}.
     *
     * @return the uri of the data item
     */
    public Uri getUri()
    {
        return getDataItem().getUri();
    }

    /**
     * Returns the path from {@link #getUri()}.
     *
     * @return the path of the uri
     */
    public String getPath()
    {
        return getUri().getPath();
    }

    /**
     * Creates a {@link com.aa.android.common.data.SharedData} item from the {@link
     * com.google.android.gms.wearable.DataMap}.
     *
     * @param clazz
     *         the class of the shared data item
     * @param <T>
     *         the type of shared data
     *
     * @return the shared data item
     */
    public <T extends SharedData> T getSharedData(Class<T> clazz)
    {
        return SharedData.fromDataItem(clazz, getDataItem());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof SharedDataEvent)) return false;

        SharedDataEvent that = (SharedDataEvent) o;

        if (!mDataEvent.equals(that.mDataEvent)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        return mDataEvent.hashCode();
    }

    @Override
    public String toString()
    {
        return "SharedDataEvent{" +
               "mDataEvent=" + mDataEvent +
               '}';
    }
}
