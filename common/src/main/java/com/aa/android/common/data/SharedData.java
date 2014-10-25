package com.aa.android.common.data;

import android.os.Parcelable;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;

import java.lang.reflect.Field;

/**
 * This class allows to share data more easily between app and wear. Any class that extends from this, must be present
 * in the common module! This class requires a SharedData.Creator CREATOR field:
 * <pre>
 *     <code>
 *     public static final SharedData.Creator&lt;T&gt; CREATOR = new SharedData.Creator&lt;&gt;(){ ... };
 *     </code>
 * </pre>
 */
public abstract class SharedData implements Parcelable {
    /**
     * Creates a SharedData item from a {@link com.google.android.gms.wearable.DataItem}.
     *
     * @param clazz
     *         the SharedData class
     * @param dataItem
     *         the data item
     * @param <T>
     *         the type of SharedData to return
     *
     * @return the shared data item
     *
     * @throws IllegalArgumentException
     *         if the {@code dataItem} is null
     */
    public static <T extends SharedData> T fromDataItem(GoogleApiClient client, Class<T> clazz, DataItem dataItem) {
        if (dataItem == null) {
            throw new IllegalArgumentException("the data item must not be null");
        }
        return SharedData.fromDataMapItem(client, clazz, DataMapItem.fromDataItem(dataItem));
    }


    /**
     * Creates a SharedData item from {@link com.google.android.gms.wearable.DataMapItem}.
     *
     * @param client
     *         the Google api client
     * @param clazz
     *         the SharedData class
     * @param dataMapItem
     *         the data map item
     * @param <T>
     *         the type of SharedData to return
     *
     * @return the shared data item
     *
     * @throws IllegalArgumentException
     *         if the {@code dataMapItem is null} or if the {@code CREATOR} field cannot be found
     * @throws IllegalStateException
     *         if the {@code CREATOR} object cannot be retrieved
     */
    public static <T extends SharedData> T fromDataMapItem(GoogleApiClient client,
            Class<T> clazz,
            DataMapItem dataMapItem) {
        if (dataMapItem == null) {
            throw new IllegalArgumentException("The dataMapItem must not be null");
        }

        final Field CREATOR_FIELD;
        try {
            CREATOR_FIELD = clazz.getDeclaredField("CREATOR");
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Cannot find field 'CREATOR' in " + clazz, e);
        }

        try {
            @SuppressWarnings("unchecked")
            final Creator<T> CREATOR = (Creator<T>) CREATOR_FIELD.get(null);
            final DataMap map = dataMapItem.getDataMap();
            return CREATOR.createFromDataMap(client, map);
        } catch (ClassCastException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot get the CREATOR from class: " + clazz, e);
        }
    }


    /**
     * Flatten this object into a {@link com.google.android.gms.wearable.DataMap}.
     *
     * @param dataMap
     *         The DataMap in which the object should be written.
     */
    public abstract void writeToDataMap(DataMap dataMap);

    /**
     * Gets the path needed to create a {@link com.google.android.gms.wearable.PutDataRequest}.
     *
     * @return the path
     */
    public abstract String getPath();

    /**
     * Creates a {@link com.google.android.gms.wearable.PutDataRequest} from this object using {@link #getPath()} as the
     * path.
     *
     * @return the PutDataRequest to send
     */
    public PutDataRequest asPutDataRequest() {
        // TODO: it could possibly be helpful to store the class of this object in the data map
        PutDataMapRequest request = PutDataMapRequest.create(getPath());
        writeToDataMap(request.getDataMap());
        return request.asPutDataRequest();
    }

    /**
     * Interface that must be implemented and provided as a public CREATOR field that generates instances of your
     * SharedData item class from a DataMap.
     */
    public interface Creator<T> extends Parcelable.Creator<T> {
        /**
         * Create a new instance of the SharedData class, instantiating it from the given DataMap whose data had
         * previously been written by {@link com.aa.android.common.data.SharedData#writeToDataMap(com.google.android.gms.wearable.DataMap)}.
         *
         * @param client
         *         the google api client
         * @param source
         *         The DataMap to read the object's data from.
         *
         * @return Returns a new instance of the SharedData class.
         */
        public T createFromDataMap(GoogleApiClient client, DataMap source);
    }
}
