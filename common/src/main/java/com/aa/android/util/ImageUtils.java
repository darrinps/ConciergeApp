/*
 * ImageUtils.java
 * American Airlines Android App
 *
 * Created on 4/2/14 4:05 AM
 * Copyright (c) 2014 American Airlines. All rights reserved.
 */

package com.aa.android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.aa.android.common.CommonUtils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.Wearable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Class with static functions to aid in common Image/UI tasks.
 */
public final class ImageUtils
{
    private static final String TAG = ImageUtils.class.getSimpleName();

    private ImageUtils() {}

    /**
     * Converts a byte array to a {@link android.graphics.Bitmap}.
     *
     * @param bytes
     *         the byte array to convert
     *
     * @return the Bitmap or null if it could not be converted
     */
    public static Bitmap convert(byte[] bytes)
    {
        Bitmap bitmap = null;
        if (bytes != null && bytes.length > 0)
        {
            ByteArrayInputStream is = new ByteArrayInputStream(bytes);
            bitmap = BitmapFactory.decodeStream(is);
            CommonUtils.closeQuietly(is);
        }
        return bitmap;
    }

    /**
     * Converts a {@link android.graphics.Bitmap} into a byte array.
     *
     * @param bitmap
     *         the bitmap to convert
     *
     * @return the byte array or null if it could not be converted
     */
    public static byte[] convert(Bitmap bitmap)
    {
        return convert(bitmap, Bitmap.CompressFormat.PNG, 100);
    }

    /**
     * Converts a {@link android.graphics.Bitmap} into a byte array using a
     * specified format and quality.
     *
     * @param bitmap
     *         the bitmap to convert
     * @param format
     *         the conversion format
     * @param quality
     *         the quality of the output
     *
     * @return the byte array or null if it could not be converted
     */
    public static byte[] convert(Bitmap bitmap, Bitmap.CompressFormat format, int quality)
    {
        byte[] bytes = null;
        if (bitmap != null)
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bitmap.compress(format, quality, out);
            bytes = out.toByteArray();
            CommonUtils.closeQuietly(out);
        }
        return bytes;
    }

    public static Bitmap fromAsset(@NonNull GoogleApiClient apiClient, @NonNull Asset asset)
    {
        InputStream assetInputStream = Wearable.DataApi.getFdForAsset(apiClient, asset)
                .await()
                .getInputStream();

        if (assetInputStream == null)
        {
            DebugLog.w(TAG, "Requested an unknown Asset.");
            return null;
        }

        return BitmapFactory.decodeStream(assetInputStream);
    }
}
