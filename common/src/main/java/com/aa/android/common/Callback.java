package com.aa.android.common;

import android.support.annotation.Nullable;

/**
 * Callback to receive data.
 * <p/>
 * Created by layne on 10/21/14.
 */
public interface Callback<T>
{
    /**
     * The result is passed into this method.
     *
     * @param t
     *         the result
     */
    void onResult(@Nullable T t);
}
